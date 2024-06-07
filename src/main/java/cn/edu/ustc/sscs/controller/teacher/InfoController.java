package cn.edu.ustc.sscs.controller.teacher;

import cn.edu.ustc.sscs.domain.po.Teacher;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.vo.TeacherVO;
import cn.edu.ustc.sscs.service.ITeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController(value = "teaInfoController")
@Slf4j
@RequestMapping("/tea")
@RequiredArgsConstructor
@Tag(name = "信息相关接口")
public class InfoController {

    private final ITeacherService iTeacherService;

    @GetMapping("/info/{id}")
    @Operation(summary = "查询个人信息")
    @Parameter(name = "id", description = "教师编号", required = true, in = ParameterIn.PATH)
    public Result getInfoById(@PathVariable("id") Integer id) {
        log.info("getInfoById, id = {}", id);
        TeacherVO teacherVO = iTeacherService.getInfoById(id);
        return Result.success(teacherVO);
    }

    @PutMapping("/password")
    @Operation(summary = "修改密码")
    public Result editPassword(@RequestBody Teacher teacher) {
        log.info("editPassword, {}", teacher);
        iTeacherService.editPassword(teacher);
        return Result.success();
    }
}
