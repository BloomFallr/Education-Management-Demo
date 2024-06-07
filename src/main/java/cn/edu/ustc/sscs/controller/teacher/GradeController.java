package cn.edu.ustc.sscs.controller.teacher;

import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.StudentCurriculum;
import cn.edu.ustc.sscs.service.IStudentCurriculumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "teaGradeController")
@RequestMapping("/tea/grade")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "成绩相关接口")
public class GradeController {

    private final IStudentCurriculumService iStudentCurriculumService;

    @Operation(summary = "录入课程成绩")
    @PutMapping
    public Result updateGrade(@RequestBody List<StudentCurriculum> studentCurriculumList) {
        log.info("updateGrade, {}", studentCurriculumList);
        iStudentCurriculumService.updateGrade(studentCurriculumList);
        return Result.success();
    }

}
