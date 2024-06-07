package cn.edu.ustc.sscs.controller.administrator;

import cn.edu.ustc.sscs.domain.result.Result;
import cn.edu.ustc.sscs.domain.po.Administrator;
import cn.edu.ustc.sscs.domain.vo.AdministratorVO;
import cn.edu.ustc.sscs.service.IAdministratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController(value = "admInfoController")
@Slf4j
@RequestMapping("/adm")
@RequiredArgsConstructor
@Tag(name = "信息相关接口")
public class InfoController {

    private final IAdministratorService iAdministratorService;

    @GetMapping("/info/{id}")
    @Operation(summary = "查询个人信息")
    @Parameter(name = "id", description = "管理员编号", required = true, in = ParameterIn.PATH)
    public Result getInfoById(@PathVariable("id") Integer id) {
        log.info("getInfoById, {}", id);
        AdministratorVO administratorVO = iAdministratorService.getInfoById(id);
        return Result.success(administratorVO);
    }

    @PutMapping("/password")
    @Operation(summary = "修改密码")
    public Result editPassword(@RequestBody Administrator administrator) {
        log.info("editPassword, {}", administrator);
        iAdministratorService.editPassword(administrator);
        return Result.success();
    }
}
