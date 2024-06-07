package cn.edu.ustc.sscs.service;

import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.po.Student;
import cn.edu.ustc.sscs.domain.query.StudentQuery;
import cn.edu.ustc.sscs.domain.vo.StudentVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IStudentService extends IService<Student> {

    /**
     * 学生获取个人信息
     * @param id
     * @return
     */
    StudentVO getInfoById(Integer id);

    /**
     * 管理员删除学生
     * @param id
     */
    void deleteStudentById(Integer id);

    /**
     * 管理员分页条件查询学生
     * @param studentQuery
     * @return
     */
    PageResult<StudentVO> queryStudentPage(StudentQuery studentQuery);

    /**
     * 管理员添加学生
     * @param student
     */
    void saveStudent(Student student);

    /**
     * 管理员修改学生信息
     * @param student
     */
    void updateStudentById(Student student);

    /**
     * 学生修改密码
     * @param student
     */
    void editPassword(Student student);

    /**
     * 学生登录
     * @param loginDTO
     * @return
     */
    Student login(LoginDTO loginDTO);
}
