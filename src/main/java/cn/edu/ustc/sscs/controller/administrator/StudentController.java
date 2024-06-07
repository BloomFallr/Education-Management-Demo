package cn.edu.ustc.sscs.controller.administrator;

import cn.edu.ustc.sscs.domain.result.PageResult;
import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.Student;
import cn.edu.ustc.sscs.domain.query.StudentQuery;
import cn.edu.ustc.sscs.domain.vo.StudentVO;
import cn.edu.ustc.sscs.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/adm/stu")
@RestController(value = "admStudentController")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "学生相关接口")
public class StudentController {

    private final IStudentService iStudentService;

    @Operation(summary = "添加学生")
    @PostMapping
    public Result saveStudent(@RequestBody Student student) {
        log.info("insertStudent, {}", student);
        iStudentService.saveStudent(student);
        return Result.success();
    }

    @Operation(summary = "根据学生编号删除学生")
    @Parameter(name = "id", description = "学生编号", required = true, in = ParameterIn.PATH)
    @DeleteMapping("/{id}")
    public Result deleteStudentById(@PathVariable("id") Integer id) {
        log.info("deleteStudentById, id = {}", id);
        iStudentService.deleteStudentById(id);
        return Result.success();
    }

    @Operation(summary = "根据学生编号修改学生")
    @PutMapping
    public Result updateStudentById(@RequestBody Student student) {
        log.info("updateStudentById, {}", student);
        iStudentService.updateStudentById(student);
        return Result.success();
    }

    @Operation(summary = "条件分页查询学生")
    @Parameters({
            @Parameter(name = "currentPage", description = "当前页数", in = ParameterIn.QUERY),
            @Parameter(name = "pageSize", description = "每页条数", in = ParameterIn.QUERY),
            @Parameter(name = "name", description = "姓名", in = ParameterIn.QUERY),
            @Parameter(name = "department", description = "学院", in = ParameterIn.QUERY)
    })
    @GetMapping("/page")
    public Result queryStudentPage(@ParameterObject StudentQuery studentQuery) {
        log.info("queryCurriculumPage, {}", studentQuery);
        PageResult<StudentVO> studentVOPageResult = iStudentService.queryStudentPage(studentQuery);
        return Result.success(studentVOPageResult);
    }
}
