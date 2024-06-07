package cn.edu.ustc.sscs.controller.student;


import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.vo.GradeReportVO;
import cn.edu.ustc.sscs.service.IStudentCurriculumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController(value = "stuGradeController")
@RequestMapping("/stu/grade")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "成绩相关接口")
public class GradeController {

    private final IStudentCurriculumService iStudentCurriculumService;

    @Operation(summary = "根据学生编号获取成绩单")
    @Parameter(name = "id", description = "学生编号", required = true, in = ParameterIn.PATH)
    @GetMapping("/{id}")
    public Result getGradeReportById(@PathVariable("id") Integer id) {
        log.info("getGradeById， id = {}", id);
        GradeReportVO gradeReportVO = iStudentCurriculumService.getGradeReportById(id);
        return Result.success(gradeReportVO);
    }
}
