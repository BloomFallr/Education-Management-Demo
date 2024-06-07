package cn.edu.ustc.sscs.service.impl;

import cn.edu.ustc.sscs.common.constant.MessageConstant;
import cn.edu.ustc.sscs.common.exception.AppException;
import cn.edu.ustc.sscs.domain.po.Curriculum;
import cn.edu.ustc.sscs.domain.po.Student;
import cn.edu.ustc.sscs.domain.po.StudentCurriculum;
import cn.edu.ustc.sscs.domain.vo.EnrollmentVO;
import cn.edu.ustc.sscs.domain.vo.GradeReportVO;
import cn.edu.ustc.sscs.domain.vo.GradeVO;
import cn.edu.ustc.sscs.mapper.StudentCurriculumMapper;
import cn.edu.ustc.sscs.service.IStudentCurriculumService;
import cn.edu.ustc.sscs.utils.GradeUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentCurriculumServiceImpl extends ServiceImpl<StudentCurriculumMapper, StudentCurriculum> implements IStudentCurriculumService {

    @Transactional
    @Override
    public void insertStudentCurriculum(Integer studentId, Integer curriculumId) {
        //查询所选择课程
        Curriculum curriculum = Db.getById(curriculumId, Curriculum.class);
        Integer selectedStudents = curriculum.getSelectedStudents();
        Integer maxStudents = curriculum.getMaxStudents();
        //判断是否选课
        if(lambdaQuery().eq(StudentCurriculum::getCurriculumId, curriculumId)
                .eq(StudentCurriculum::getStudentId, studentId).exists()) {
            throw new AppException(MessageConstant.DO_NOT_SELECT_DUPLICATE_COURSE);
        }
        //判断课程是否已满
        if(selectedStudents >= maxStudents) {
            throw new AppException(MessageConstant.COURSE_CAPACITY_FULL);
        }
        //添加选课记录
        save(StudentCurriculum
                .builder()
                .studentId(studentId)
                .curriculumId(curriculumId)
                .build());
        //更新课程已选人数
        Db.lambdaUpdate(Curriculum.class)
                .eq(Curriculum::getId, curriculumId)
                .set(Curriculum::getSelectedStudents, selectedStudents + 1)
                .update();
    }

    @Transactional
    @Override
    public void deleteStudentCurriculum(Integer studentId, Integer curriculumId) {
        //判断是否选课
        if(!lambdaQuery().eq(StudentCurriculum::getCurriculumId, curriculumId)
                .eq(StudentCurriculum::getStudentId, studentId).exists()) {
            throw new AppException(MessageConstant.COURSE_NOT_SELECTED);
        }
        //删除选课记录
        lambdaUpdate()
                .eq(StudentCurriculum::getStudentId, studentId)
                .eq(StudentCurriculum::getCurriculumId, curriculumId)
                .remove();
        //更新课程已选人数
        Integer selectedStudents = Db.getById(curriculumId, Curriculum.class).getSelectedStudents();
        Db.lambdaUpdate(Curriculum.class)
                .eq(Curriculum::getId, curriculumId)
                .set(Curriculum::getSelectedStudents, selectedStudents - 1)
                .update();
    }

    @Override
    public List<GradeVO> getGradeListById(Integer id) {
        //获取所有已出的成绩信息
        List<StudentCurriculum> studentCurriculumList = lambdaQuery()
                .eq(StudentCurriculum::getStudentId, id)
                .isNotNull(StudentCurriculum::getIsPassed)
                .list();
        if(CollUtil.isEmpty(studentCurriculumList)) {
            return Collections.emptyList();
        }
        Map<Integer, StudentCurriculum> studentCurriculumMap = studentCurriculumList.stream()
                .collect(Collectors.toMap(StudentCurriculum::getCurriculumId, studentCurriculum -> studentCurriculum));
        //获取所有已出成绩的课程id
        List<Integer> curriculumIdList = studentCurriculumList.stream().map(StudentCurriculum::getCurriculumId)
                .collect(Collectors.toList());
        //获取所有已出成绩的课程信息
        List<Curriculum> curriculumList = Db.listByIds(curriculumIdList, Curriculum.class);
        //转VO
        List<GradeVO> gradeList = new ArrayList<>(curriculumList.size());
        for(Curriculum curriculum : curriculumList) {
            GradeVO gradeVO = BeanUtil.copyProperties(curriculum, GradeVO.class);
            gradeVO.setIsPassed(studentCurriculumMap.get(curriculum.getId()).getIsPassed());
            gradeVO.setGradePoint(studentCurriculumMap.get(curriculum.getId()).getGradePoint());
            gradeList.add(gradeVO);
        }
        return gradeList;
    }

    @Override
    public GradeReportVO getGradeReportById(Integer id) {
        Student student = Db.getById(id, Student.class);
        return GradeReportVO.builder()
                .obtainedCredit(student.getObtainedCredit())
                .failedCredit(student.getFailedCredit())
                .gpa(student.getGpa())
                .gradeList(getGradeListById(id))
                .build();
    }


    @Override
    public List<EnrollmentVO> getEnrollmentById(Integer id) {
        //查询选课信息
        List<StudentCurriculum> studentCurriculumList = lambdaQuery()
                .eq(StudentCurriculum::getCurriculumId, id).list();
        //查询相关学生信息
        List<Integer> studentIdList = studentCurriculumList.stream()
                .map(StudentCurriculum::getStudentId)
                .collect(Collectors.toList());
        List<Student> studentList = Db.listByIds(studentIdList, Student.class);
        Map<Integer, Student> studentMap = studentList.stream()
                .collect(Collectors.toMap(Student::getId, student -> student));
        //转VO
        List<EnrollmentVO> enrollmentVOList = new ArrayList<>(studentCurriculumList.size());
        for (StudentCurriculum studentCurriculum : studentCurriculumList) {
            EnrollmentVO enrollmentVO = BeanUtil.copyProperties(studentCurriculum, EnrollmentVO.class);
            enrollmentVO.setStudentUsername(studentMap.get(studentCurriculum.getStudentId()).getUsername());
            enrollmentVO.setStudentName(studentMap.get(studentCurriculum.getStudentId()).getName());
            enrollmentVOList.add(enrollmentVO);
        }
        return enrollmentVOList;
    }

    @Override
    @Transactional
    public void updateGrade(List<StudentCurriculum> studentCurriculumList) {
        for (StudentCurriculum studentCurriculum : studentCurriculumList) {
            updateById(studentCurriculum);
            Student student = Db.getById(getById(studentCurriculum.getId()).getStudentId(), Student.class);
            List<GradeVO> gradeList = getGradeListById(student.getId());
            //更新学生成绩信息
            student.setObtainedCredit(GradeUtil.calObtainedCredit(gradeList));
            student.setFailedCredit(GradeUtil.calFailedCredit(gradeList));
            student.setGpa(GradeUtil.calGpa(gradeList));
            Db.updateById(student);
        }
    }

}
