package cn.edu.ustc.sscs.service.impl;

import cn.edu.ustc.sscs.common.constant.MessageConstant;
import cn.edu.ustc.sscs.common.constant.PasswordConstant;
import cn.edu.ustc.sscs.common.constant.StateConstant;
import cn.edu.ustc.sscs.common.exception.AppException;
import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.po.Curriculum;
import cn.edu.ustc.sscs.domain.po.Teacher;
import cn.edu.ustc.sscs.domain.query.TeacherQuery;
import cn.edu.ustc.sscs.domain.vo.TeacherVO;
import cn.edu.ustc.sscs.mapper.TeacherMapper;
import cn.edu.ustc.sscs.service.ITeacherService;
import cn.edu.ustc.sscs.utils.LoginCheckUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    public void deleteTeacherById(Integer id) {
        Teacher teacher = Db.getById(id, Teacher.class);
        //判断是否有进行中的任教课程
        if(Db.lambdaQuery(Curriculum.class)
                .eq(Curriculum::getTeacher, teacher.getName())
                .eq(Curriculum::getCourseState, StateConstant.ON).exists()) {
            throw new AppException(MessageConstant.TEACHER_WITH_ONGOING_COURSES_DELETE_FAILED);
        }
        teacher.setState(StateConstant.OFF);
        updateById(teacher);
    }

    @Override
    public PageResult<TeacherVO> queryTeacherPage(TeacherQuery teacherQuery) {
        //查询教师
        Page<Teacher> page = new Page<>(teacherQuery.getCurrentPage(), teacherQuery.getPageSize());
        String name = teacherQuery.getName();
        page = lambdaQuery()
                .like(StrUtil.isNotEmpty(name), Teacher::getName, name)
                .eq(Teacher::getState, StateConstant.ON)
                .page(page);
        //转VO
        PageResult<TeacherVO> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(BeanUtil.copyToList(page.getRecords(), TeacherVO.class));
        return pageResult;
    }

    @Override
    public TeacherVO getInfoById(Integer id) {
        Teacher teacher = getById(id);
        return BeanUtil.copyProperties(teacher, TeacherVO.class);
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        String name = teacher.getName();
        String username = teacher.getUsername();
        //判断姓名是否重复
        if(lambdaQuery().eq(Teacher::getName, name).exists()) {
            throw new AppException(MessageConstant.TEACHER_NAME_DUPLICATE);
        }
        //判断工号是否重复
        if(lambdaQuery().eq(Teacher::getUsername, username).exists()) {
            throw new AppException(MessageConstant.TEACHER_USERNAME_DUPLICATE);
        }
        teacher.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        save(teacher);
    }

    @Override
    public void updateTeacherById(Teacher teacher) {
        Integer id = teacher.getId();
        String name = teacher.getName();
        String username = teacher.getUsername();
        //判断姓名是否重复
        if(StrUtil.isNotBlank(name) && lambdaQuery().eq(Teacher::getName, name).ne(Teacher::getId, id).exists()) {
            throw new AppException(MessageConstant.TEACHER_NAME_DUPLICATE);
        }
        //判断工号是否重复
        if(StrUtil.isNotBlank(username) && lambdaQuery().ne(Teacher::getId, id).eq(Teacher::getUsername, username).exists()) {
            throw new AppException(MessageConstant.TEACHER_USERNAME_DUPLICATE);
        }
        updateById(teacher);
    }

    @Override
    public void editPassword(Teacher teacher) {
        teacher.setPassword(DigestUtils.md5DigestAsHex(teacher.getPassword().getBytes()));
        updateById(teacher);
    }

    @Override
    public Teacher login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Teacher teacher = lambdaQuery().eq(Teacher::getUsername, username).one();
        LoginCheckUtil.loginCheck(password, teacher);
        return teacher;
    }
}
