package cn.edu.ustc.sscs.service;

import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.po.Administrator;
import cn.edu.ustc.sscs.domain.vo.AdministratorVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IAdministratorService extends IService<Administrator> {

    /**
     * 管理员登录
     * @param loginDTO
     * @return
     */
    Administrator login(LoginDTO loginDTO);

    /**
     * 管理员获取个人信息
     * @param id
     * @return
     */
    AdministratorVO getInfoById(Integer id);

    /**
     * 管理员修改密码
     * @param administrator
     */
    void editPassword(Administrator administrator);
}
