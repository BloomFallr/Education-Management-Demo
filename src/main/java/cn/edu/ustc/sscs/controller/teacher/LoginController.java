package cn.edu.ustc.sscs.controller.teacher;

import cn.edu.ustc.sscs.common.constant.IdentityConstant;
import cn.edu.ustc.sscs.common.properties.JwtProperties;
import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.po.Teacher;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.vo.LoginVO;
import cn.edu.ustc.sscs.service.ITeacherService;
import cn.edu.ustc.sscs.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "teaLoginController")
@Slf4j
@RequestMapping("/tea")
@RequiredArgsConstructor
@Tag(name = "登录相关接口")
public class LoginController {

    private final JwtProperties jwtProperties;
    private final ITeacherService iTeacherService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.info("login, {}", loginDTO);
        Teacher teacher = iTeacherService.login(loginDTO);
        //生成token
        String token = JWTUtil.createJWT(jwtProperties.getTeaSecret(), jwtProperties.getTtlMillis(),
                teacher.getId(), IdentityConstant.TEACHER);
        return Result.success(LoginVO.builder().id(teacher.getId()).token(token).build());
    }
}
