package cn.edu.ustc.sscs.service.impl;

import cn.edu.ustc.sscs.common.constant.MessageConstant;
import cn.edu.ustc.sscs.common.constant.PasswordConstant;
import cn.edu.ustc.sscs.common.constant.StateConstant;
import cn.edu.ustc.sscs.common.exception.AppException;
import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.po.Student;
import cn.edu.ustc.sscs.domain.query.StudentQuery;
import cn.edu.ustc.sscs.domain.vo.StudentVO;
import cn.edu.ustc.sscs.mapper.StudentMapper;
import cn.edu.ustc.sscs.service.IStudentService;
import cn.edu.ustc.sscs.utils.LoginCheckUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public StudentVO getInfoById(Integer id) {
        Student student = getById(id);
        return BeanUtil.copyProperties(student, StudentVO.class);
    }

    @Override
    public void deleteStudentById(Integer id) {
        lambdaUpdate()
                .eq(Student::getId, id)
                .set(Student::getState, StateConstant.OFF)
                .update();
    }

    @Override
    public PageResult<StudentVO> queryStudentPage(StudentQuery studentQuery) {
        //查询学生
        Page<Student> page = new Page<>(studentQuery.getCurrentPage(), studentQuery.getPageSize());
        String name = studentQuery.getName();
        String department = studentQuery.getDepartment();
        page = lambdaQuery()
                .like(StrUtil.isNotEmpty(name), Student::getName, name)
                .like(StrUtil.isNotEmpty(department), Student::getDepartment, department)
                .eq(Student::getState, StateConstant.ON)
                .page(page);
        //转VO
        PageResult<StudentVO> pageResult = new PageResult<>();
        pageResult.setTotal(page.getTotal());
        pageResult.setList(BeanUtil.copyToList(page.getRecords(), StudentVO.class));
        return pageResult;
    }

    @Override
    public void saveStudent(Student student) {
        //判断学号是否重复
        if(lambdaQuery().eq(Student::getUsername, student.getUsername()).exists()) {
            throw new AppException(MessageConstant.STUDENT_USERNAME_DUPLICATE);
        }
        //设置默认密码
        student.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        save(student);
    }

    @Override
    public void updateStudentById(Student student) {
        Integer id = student.getId();
        String username = student.getUsername();
        //判断学号是否重复
        if(StrUtil.isNotBlank(username) && lambdaQuery().eq(Student::getUsername, username).ne(Student::getId, id).exists()) {
            throw new AppException(MessageConstant.STUDENT_USERNAME_DUPLICATE);
        }
        updateById(student);
    }

    @Override
    public void editPassword(Student student) {
        student.setPassword(DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));
        updateById(student);
    }

    @Override
    public Student login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Student student = lambdaQuery().eq(Student::getUsername, username).one();
        LoginCheckUtil.loginCheck(password, student);
        return student;
    }
}
