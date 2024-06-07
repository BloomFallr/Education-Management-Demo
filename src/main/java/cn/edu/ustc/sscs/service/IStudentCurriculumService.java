package cn.edu.ustc.sscs.service;

import cn.edu.ustc.sscs.domain.po.StudentCurriculum;
import cn.edu.ustc.sscs.domain.vo.EnrollmentVO;
import cn.edu.ustc.sscs.domain.vo.GradeReportVO;
import cn.edu.ustc.sscs.domain.vo.GradeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IStudentCurriculumService extends IService<StudentCurriculum> {

    /**
     * 学生选课
     * @param studentId
     * @param curriculumId
     */
    void insertStudentCurriculum(Integer studentId, Integer curriculumId);

    /**
     * 学生退课
     * @param studentId
     * @param curriculumId
     */
    void deleteStudentCurriculum(Integer studentId, Integer curriculumId);

    /**
     * 学生获取成绩
     * @param id
     * @return
     */
    List<GradeVO> getGradeListById(Integer id);

    /**
     * 学生获取成绩单（含已获学分，不及格学分，平均绩点以及详细成绩）
     * @param id
     * @return
     */
    GradeReportVO getGradeReportById(Integer id);

    /**
     * 教师获取课程选课记录
     * @param id
     * @return
     */
    List<EnrollmentVO> getEnrollmentById(Integer id);

    /**
     * 教师录入或者修改课程的成绩，同时同步学生成绩信息
     * @param studentCurriculumList
     */
    void updateGrade(List<StudentCurriculum> studentCurriculumList);

}
