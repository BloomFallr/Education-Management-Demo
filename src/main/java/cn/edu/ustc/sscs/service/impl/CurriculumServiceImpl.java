package cn.edu.ustc.sscs.service.impl;

import cn.edu.ustc.sscs.common.constant.MessageConstant;
import cn.edu.ustc.sscs.common.constant.StateConstant;
import cn.edu.ustc.sscs.common.exception.AppException;
import cn.edu.ustc.sscs.domain.po.StudentCurriculum;
import cn.edu.ustc.sscs.domain.po.Teacher;
import cn.edu.ustc.sscs.domain.vo.CurriculumVO;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.po.Curriculum;
import cn.edu.ustc.sscs.domain.query.CurriculumQuery;
import cn.edu.ustc.sscs.mapper.CurriculumMapper;
import cn.edu.ustc.sscs.service.ICurriculumService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurriculumServiceImpl extends ServiceImpl<CurriculumMapper, Curriculum> implements ICurriculumService {

    @Override
    public PageResult<CurriculumVO> queryCurriculumPage(CurriculumQuery query) {
        //查询课程
        Page<Curriculum> page = Page.of(query.getCurrentPage(), query.getPageSize());
        String name = query.getName();
        String teacher = query.getTeacher();
        Integer year = query.getYear();
        String semester = query.getSemester();
        String time = query.getTime();
        String place = query.getPlace();
        Integer selectEndState = query.getSelectState();
        Integer courseEndState = query.getCourseState();
        page = lambdaQuery()
                .like(StrUtil.isNotEmpty(name), Curriculum::getName, name)
                .like(StrUtil.isNotEmpty(teacher), Curriculum::getTeacher, teacher)
                .eq(year != null, Curriculum::getYear, year)
                .eq(StrUtil.isNotEmpty(semester), Curriculum::getSemester, semester)
                .like(StrUtil.isNotEmpty(time), Curriculum::getTime, time)
                .like(StrUtil.isNotEmpty(place), Curriculum::getPlace, place)
                .eq(selectEndState != null, Curriculum::getSelectState, selectEndState)
                .eq(courseEndState != null, Curriculum::getCourseState, courseEndState)
                .page(page);
        //转VO
        List<CurriculumVO> curriculumVOList = BeanUtil.copyToList(page.getRecords(), CurriculumVO.class);
        PageResult<CurriculumVO> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(curriculumVOList);
        return pageResult;
    }

    @Override
    public List<CurriculumVO> querySelectedCurriculum(Integer id) {
        //获取已选课程id
        List<StudentCurriculum> studentCurriculumList = Db.lambdaQuery(StudentCurriculum.class)
                .eq(StudentCurriculum::getStudentId, id).list();
        if (CollUtil.isEmpty(studentCurriculumList)){
            return Collections.emptyList();
        }
        List<Integer> curriculumIdList = studentCurriculumList.stream().map(StudentCurriculum::getCurriculumId)
                .collect(Collectors.toList());
        //获取已选且进行中的课程
        List<Curriculum> curriculumList = listByIds(curriculumIdList)
                .stream().filter(curriculum -> StateConstant.ON.equals(curriculum.getCourseState()))
                .collect(Collectors.toList());
        //转VO
        return BeanUtil.copyToList(curriculumList, CurriculumVO.class);
    }

    @Override
    public List<CurriculumVO> queryTaughtCurriculum(Integer id) {
        //获取教师姓名
        Teacher teacher = Db.getById(id, Teacher.class);
        //获取进行中的课程
        List<Curriculum> curriculumList = lambdaQuery()
                .eq(Curriculum::getTeacher, teacher.getName())
                .eq(Curriculum::getCourseState, StateConstant.ON)
                .list();
        //转VO
        return BeanUtil.copyToList(curriculumList, CurriculumVO.class);
    }

    @Override
    public void saveCurriculum(Curriculum curriculum) {
        //判断教师是否存在
        String teacher = curriculum.getTeacher();
        boolean existed = Db.lambdaQuery(Teacher.class)
                .eq(Teacher::getName, teacher)
                .eq(Teacher::getState, StateConstant.ON)
                .exists();
        if(!existed) {
            throw new AppException(MessageConstant.TEACHER_NOT_FOUND);
        }
        save(curriculum);
    }

    @Transactional
    @Override
    public void deleteCurriculumById(Integer id) {
        Curriculum curriculum = getById(id);
        //判断课程是否结束
        if(StateConstant.OFF.equals(curriculum.getCourseState())) {
            throw new AppException(MessageConstant.ENDED_COURSE_DELETE_FAILED);
        }
        //删除与课程相关的选课记录
        Db.lambdaUpdate(StudentCurriculum.class)
                .eq(StudentCurriculum::getCurriculumId, id)
                .remove();
        removeById(id);
    }

    @Override
    public void updateCurriculumById(Curriculum curriculum) {
        if(StateConstant.OFF.equals(getById(curriculum.getId()).getCourseState())) {
            throw new AppException(MessageConstant.ENDED_COURSE_UPDATE_FAILED);
        }
        String teacher = curriculum.getTeacher();
        //是否需要判断teacher非空
        boolean existed = Db.lambdaQuery(Teacher.class)
                .eq(Teacher::getName, teacher)
                .eq(Teacher::getState, StateConstant.ON)
                .exists();
        if(!existed) {
            throw new AppException(MessageConstant.TEACHER_NOT_FOUND);
        }
        updateById(curriculum);
    }

    @Override
    public void updateSelectStateBatchById(List<Integer> ids, Integer state) {
        if (lambdaQuery().in(Curriculum::getId, ids).eq(Curriculum::getCourseState, StateConstant.OFF).exists()) {
            throw new AppException(MessageConstant.ENDED_COURSE_UPDATE_FAILED);
        }
        lambdaUpdate()
                .in(Curriculum::getId, ids)
                .set(Curriculum::getSelectState, state)
                .update();
    }

    @Override
    public void finishCourseById(Integer id) {
        if (StateConstant.ON.equals(getById(id).getSelectState())) {
            throw new AppException(MessageConstant.COURSE_SELECTION_ON_FINISH_FAILED);
        }
        if (Db.lambdaQuery(StudentCurriculum.class).eq(StudentCurriculum::getCurriculumId, id)
                .and(wrapper -> wrapper.isNull(StudentCurriculum::getIsPassed)
                        .or().isNull(StudentCurriculum::getGradePoint)).exists()) {
            throw new AppException(MessageConstant.COURSE_WITH_INCOMPLETE_GRADE_FINISH_FAILED);
        }
        lambdaUpdate()
                .eq(Curriculum::getId, id)
                .set(Curriculum::getCourseState, StateConstant.OFF)
                .update();
    }

}
