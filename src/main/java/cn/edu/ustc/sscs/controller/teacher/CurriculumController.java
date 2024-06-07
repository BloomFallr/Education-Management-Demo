package cn.edu.ustc.sscs.controller.teacher;

import cn.edu.ustc.sscs.domain.vo.CurriculumVO;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.vo.EnrollmentVO;
import cn.edu.ustc.sscs.service.ICurriculumService;
import cn.edu.ustc.sscs.service.IStudentCurriculumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController(value = "teaCurriculumController")
@RequestMapping("/tea/cur")
@RequiredArgsConstructor
@Tag(name = "课程相关接口")
public class CurriculumController {

    private final ICurriculumService iCurriculumService;
    private final IStudentCurriculumService iStudentCurriculumService;

    @Operation(summary = "根据教师编号查询任教且进行中课程")
    @Parameter(name = "id", description = "教师编号", required = true, in = ParameterIn.PATH)
    @GetMapping("/taught-list/{id}")
    public Result queryTaughtCurriculum(@PathVariable("id") Integer id) {
        log.info("queryTaughtCurriculum, id = {}",id);
        List<CurriculumVO> curriculumVOList = iCurriculumService.queryTaughtCurriculum(id);
        return Result.success(curriculumVOList);
    }

    @Operation(summary = "根据课程编号获取选课选课情况")
    @Parameter(name = "id", description = "课程编号", required = true, in = ParameterIn.PATH)
    @GetMapping("/enrollment/{id}")
    public Result getEnrollmentById(@PathVariable("id") Integer id) {
        log.info("getEnrollmentById, id = {}", id);
        List<EnrollmentVO> enrollmentVOList = iStudentCurriculumService.getEnrollmentById(id);
        return Result.success(enrollmentVOList);
    }

    @Operation(summary = "根据课程编号结束课程")
    @Parameter(name = "id", description = "课程编号", required = true, in = ParameterIn.PATH)
    @PutMapping("/finish/{id}")
    public Result finishCourseById(@PathVariable("id") Integer id) {
        log.info("finishCourseById, id = {}", id);
        iCurriculumService.finishCourseById(id);
        return Result.success();
    }
}
