package cn.edu.ustc.sscs.controller.student;

import cn.edu.ustc.sscs.common.constant.IdentityConstant;
import cn.edu.ustc.sscs.common.properties.JwtProperties;
import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.Student;
import cn.edu.ustc.sscs.domain.vo.LoginVO;
import cn.edu.ustc.sscs.service.IStudentService;
import cn.edu.ustc.sscs.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "stuLoginController")
@Slf4j
@RequestMapping("/stu")
@RequiredArgsConstructor
@Tag(name = "登录相关接口")
public class LoginController {

    private final JwtProperties jwtProperties;
    private final IStudentService iStudentService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.info("login, {}", loginDTO);
        Student student = iStudentService.login(loginDTO);
        //生成token
        String token = JWTUtil.createJWT(jwtProperties.getStuSecret(), jwtProperties.getTtlMillis(),
                student.getId(), IdentityConstant.STUDENT);
        return Result.success(LoginVO.builder().id(student.getId()).token(token).build());
    }
}
