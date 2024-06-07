package cn.edu.ustc.sscs.controller.administrator;

import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.Teacher;
import cn.edu.ustc.sscs.domain.query.TeacherQuery;
import cn.edu.ustc.sscs.domain.vo.TeacherVO;
import cn.edu.ustc.sscs.service.ITeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController(value = "admTeacherController")
@Slf4j
@RequestMapping("/adm/tea")
@RequiredArgsConstructor
@Tag(name = "教师相关接口")
public class TeacherController {

    public final ITeacherService iTeacherService;

    @Operation(summary = "添加教师")
    @PostMapping
    public Result saveTeacher(@RequestBody Teacher teacher) {
        log.info("saveTeacher, {}", teacher);
        iTeacherService.saveTeacher(teacher);
        return Result.success();
    }

    @Operation(summary = "根据教师编号删除教师", description = "若该教师有进行中的任教课程则不允许删除")
    @Parameter(name = "id", description = "教师编号", required = true, in = ParameterIn.PATH)
    @DeleteMapping("/{id}")
    public Result deleteTeacherById(@PathVariable Integer id) {
        log.info("deleteTeacherById, id = {}", id);
        iTeacherService.deleteTeacherById(id);
        return Result.success();
    }

    @Operation(summary = "根据教师编号修改教师")
    @PutMapping
    public Result updateTeacherById(@RequestBody Teacher teacher) {
        log.info("updateTeacherById, {}", teacher);
        iTeacherService.updateTeacherById(teacher);
        return Result.success();
    }

    @Operation(summary = "条件分页查询教师")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页数", in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页条数", in = ParameterIn.QUERY),
            @Parameter(name = "name", description = "姓名", in = ParameterIn.QUERY)
    })
    @GetMapping("/page")
    public Result queryTeacherPage(@ParameterObject TeacherQuery teacherQuery) {
        log.info("queryTeacherPage, {}", teacherQuery);
        PageResult<TeacherVO> teacherVOPageResult = iTeacherService.queryTeacherPage(teacherQuery);
        return Result.success(teacherVOPageResult);
    }
}
