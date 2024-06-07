package cn.edu.ustc.sscs.controller.student;

import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.Student;
import cn.edu.ustc.sscs.domain.vo.StudentVO;
import cn.edu.ustc.sscs.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController(value = "stuInfoController")
@Slf4j
@RequestMapping("/stu")
@RequiredArgsConstructor
@Tag(name = "信息相关接口")
public class InfoController {

    private final IStudentService iStudentService;

    @GetMapping("/info/{id}")
    @Operation(summary = "查询个人信息")
    @Parameter(name = "id", description = "学生编号", required = true, in = ParameterIn.PATH)
    public Result getInfoById(@PathVariable("id") Integer id) {
        log.info("getInfoById, id = {}", id);
        StudentVO studentVO = iStudentService.getInfoById(id);
        return Result.success(studentVO);
    }

    @PutMapping("/password")
    @Operation(summary = "修改密码")
    public Result editPassword(@RequestBody Student student) {
        log.info("editPassword, {}", student);
        iStudentService.editPassword(student);
        return Result.success();
    }
}
