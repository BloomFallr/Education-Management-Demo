package cn.edu.ustc.sscs.controller.administrator;

import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.Curriculum;
import cn.edu.ustc.sscs.domain.query.CurriculumQuery;
import cn.edu.ustc.sscs.domain.vo.CurriculumVO;
import cn.edu.ustc.sscs.service.ICurriculumService;
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
@RestController(value = "admCurriculumController")
@RequestMapping("/adm/cur")
@RequiredArgsConstructor
@Tag(name = "课程相关接口")
public class CurriculumController {

    private final ICurriculumService iCurriculumService;

    @Operation(summary = "条件分页查询课程")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页数", in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页条数", in = ParameterIn.QUERY),
            @Parameter(name = "name", description = "课程名称", in = ParameterIn.QUERY),
            @Parameter(name = "teacher", description = "任课教师姓名", in = ParameterIn.QUERY),
            @Parameter(name = "year", description = "学年", in = ParameterIn.QUERY),
            @Parameter(name = "semester", description = "学期", in = ParameterIn.QUERY),
            @Parameter(name = "time", description = "时间", in = ParameterIn.QUERY),
            @Parameter(name = "place", description = "地点", in = ParameterIn.QUERY),
            @Parameter(name = "selectState", description = "选课状态", in = ParameterIn.QUERY),
            @Parameter(name = "courseState", description = "课程状态", in = ParameterIn.QUERY)
    })
    @GetMapping("/page")
    public Result queryCurriculumPage(@ParameterObject CurriculumQuery query) {
        log.info("queryCurriculumPage, {}", query);
        PageResult<CurriculumVO> curriculumVOPageResult = iCurriculumService.queryCurriculumPage(query);
        return Result.success(curriculumVOPageResult);
    }

    @Operation(summary = "添加课程")
    @PostMapping
    public Result saveCurriculum(@RequestBody Curriculum curriculum) {
        log.info("insertCurriculum, {}", curriculum);
        iCurriculumService.saveCurriculum(curriculum);
        return Result.success();
    }

    @Operation(summary = "根据课程编号删除课程", description = "对于已经结束的课程不允许删除；对于进行中的课程需要同时清除相关的选课记录")
    @Parameter(name = "id", description = "课程编号", required = true, in = ParameterIn.PATH)
    @DeleteMapping("/{id}")
    public Result deleteCurriculumById(@PathVariable("id") Integer id) {
        log.info("deleteCurriculumById, id = {}", id);
        iCurriculumService.deleteCurriculumById(id);
        return Result.success();
    }

    @Operation(summary = "根据课程编号修改课程", description = "对于已经结束的课程不允许更新")
    @PutMapping
    public Result updateCurriculumById(@RequestBody Curriculum curriculum) {
        log.info("updateCurriculumById, {}", curriculum);
        iCurriculumService.updateCurriculumById(curriculum);
        return Result.success();
    }

    @Operation(summary = "根据课程编号批量修改选课状态")
    @Parameters({
            @Parameter(name = "ids", description = "课程编号", required = true, in = ParameterIn.PATH),
            @Parameter(name = "state", description = "目标状态", required = true, in = ParameterIn.PATH)
    })
    @PutMapping("/selectstate/{ids}/{state}")
    public Result updateSelectStateBatchById(@PathVariable("ids") List<Integer> ids, @PathVariable("state") Integer state) {
        log.info("updateSelectStateBatchById, ids = {},state = {}", ids, state);
        iCurriculumService.updateSelectStateBatchById(ids, state);
        return Result.success();
    }
}
