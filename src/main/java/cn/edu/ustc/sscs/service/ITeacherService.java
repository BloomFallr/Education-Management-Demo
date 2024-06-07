package cn.edu.ustc.sscs.service;

import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.po.Teacher;
import cn.edu.ustc.sscs.domain.query.TeacherQuery;
import cn.edu.ustc.sscs.domain.vo.TeacherVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ITeacherService extends IService<Teacher> {

    /**
     * 管理员删除教师
     * @param id
     */
    void deleteTeacherById(Integer id);

    /**
     * 管理员分页条件查询教师
     * @param teacherQuery
     * @return
     */
    PageResult<TeacherVO> queryTeacherPage(TeacherQuery teacherQuery);

    /**
     * 教师获取个人信息
     * @param id
     * @return
     */
    TeacherVO getInfoById(Integer id);

    /**
     * 管理员添加教师
     * @param teacher
     */
    void saveTeacher(Teacher teacher);

    /**
     * 管理员修改教师
     * @param teacher
     */
    void updateTeacherById(Teacher teacher);

    /**
     * 教师修改密码
     * @param teacher
     */
    void editPassword(Teacher teacher);

    /**
     * 教师登录
     * @param loginDTO
     * @return
     */
    Teacher login(LoginDTO loginDTO);
}
