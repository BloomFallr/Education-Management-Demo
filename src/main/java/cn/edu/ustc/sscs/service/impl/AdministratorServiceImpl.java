package cn.edu.ustc.sscs.service.impl;

import cn.edu.ustc.sscs.domain.dto.LoginDTO;
import cn.edu.ustc.sscs.domain.po.Administrator;
import cn.edu.ustc.sscs.domain.vo.AdministratorVO;
import cn.edu.ustc.sscs.mapper.AdministratorMapper;
import cn.edu.ustc.sscs.service.IAdministratorService;
import cn.edu.ustc.sscs.utils.LoginCheckUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements IAdministratorService {
    @Override
    public Administrator login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Administrator administrator = lambdaQuery().eq(Administrator::getUsername, username).one();
        LoginCheckUtil.loginCheck(password, administrator);
        return administrator;
    }

    @Override
    public AdministratorVO getInfoById(Integer id) {
        Administrator administrator = getById(id);
        //转VO
        return BeanUtil.copyProperties(administrator, AdministratorVO.class);
    }

    @Override
    public void editPassword(Administrator administrator) {
        administrator.setPassword(DigestUtils.md5DigestAsHex(administrator.getPassword().getBytes()));
        updateById(administrator);
    }
}
