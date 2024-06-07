package cn.edu.ustc.sscs.controller.student;

import cn.edu.ustc.sscs.common.constant.StateConstant;
import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.query.CurriculumQuery;
import cn.edu.ustc.sscs.domain.vo.CurriculumVO;
import cn.edu.ustc.sscs.service.ICurriculumService;
import cn.edu.ustc.sscs.service.IStudentCurriculumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController(value = "stuCurriculumController")
@RequestMapping("/stu/cur")
@RequiredArgsConstructor
@Tag(name = "课程相关接口")
public class CurriculumController {

    private final ICurriculumService iCurriculumService;
    private final IStudentCurriculumService iStudentCurriculumService;

    @Operation(summary = "条件分页查询进行中课程")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页数", in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页条数", in = ParameterIn.QUERY),
            @Parameter(name = "name", description = "课程名称", in = ParameterIn.QUERY),
            @Parameter(name = "teacher", description = "任课教师姓名", in = ParameterIn.QUERY),
            @Parameter(name = "time", description = "时间", in = ParameterIn.QUERY),
            @Parameter(name = "place", description = "地点", in = ParameterIn.QUERY)
    })
    @GetMapping("/page")
    public Result queryCurriculumPage(@ParameterObject CurriculumQuery query) {
        //学生只查询进行中课程
        query.setCourseState(StateConstant.ON);
        log.info("queryCurriculumPage, {}", query);
        PageResult<CurriculumVO> curriculumVOPageResult = iCurriculumService.queryCurriculumPage(query);
        return Result.success(curriculumVOPageResult);
    }

    @Operation(summary = "根据学生编号查询已选且进行中课程")
    @Parameter(name = "id", description = "学生编号", required = true, in = ParameterIn.PATH)
    @GetMapping("/selected-list/{id}")
    public Result querySelectedCurriculum(@PathVariable("id") Integer id) {
        log.info("querySelectedCurriculum, id = {}", id);
        List<CurriculumVO> curriculumVOList = iCurriculumService.querySelectedCurriculum(id);
        return Result.success(curriculumVOList);
    }

    @Operation(summary = "选课")
    @Parameters({
            @Parameter(name = "studentId", description = "学生编号", required = true, in = ParameterIn.PATH),
            @Parameter(name = "curriculumId", description = "课程编号", required = true, in = ParameterIn.PATH)
    })
    @PostMapping("/select/{studentId}/{curriculumId}")
    public Result insertStudentCurriculum(@PathVariable("studentId") Integer studentId, @PathVariable("curriculumId") Integer curriculumId) {
        log.info("insertStudentCurriculum, studentId = {}, curriculumId = {}", studentId, curriculumId);
        iStudentCurriculumService.insertStudentCurriculum(studentId, curriculumId);
        return Result.success();
    }

    @Operation(summary = "退课")
    @Parameters({
            @Parameter(name = "studentId", description = "学生编号", required = true, in = ParameterIn.PATH),
            @Parameter(name = "curriculumId", description = "课程编号", required = true, in = ParameterIn.PATH)
    })
    @DeleteMapping("/cancel/{studentId}/{curriculumId}")
    public Result deleteStudentCurriculum(@PathVariable("studentId") Integer studentId, @PathVariable("curriculumId") Integer curriculumId) {
        log.info("deleteStudentCurriculum: studentId = {}, curriculumId = {}", studentId, curriculumId);
        iStudentCurriculumService.deleteStudentCurriculum(studentId, curriculumId);
        return Result.success();
    }
}
