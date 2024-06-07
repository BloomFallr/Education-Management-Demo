package cn.edu.ustc.sscs.utils;

import cn.edu.ustc.sscs.common.constant.MessageConstant;
import cn.edu.ustc.sscs.common.constant.StateConstant;
import cn.edu.ustc.sscs.common.exception.AppException;
import cn.edu.ustc.sscs.domain.po.User;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.DigestUtils;

/**
 * 登录账号密码检验工具类
 */
public class LoginCheckUtil {

    public static void loginCheck(String password, User user) {
        //判断用户是否存在
        if(ObjUtil.isEmpty(user)) {
            throw new AppException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //判断用户状态是否正常
        if(StateConstant.OFF.equals(user.getState())) {
            throw new AppException(MessageConstant.ACCOUNT_STATE_ABNORMAL);
        }
        //判断密码是否正确
        if(!StrUtil.equals(DigestUtils.md5DigestAsHex(password.getBytes()), user.getPassword())) {
            throw new AppException(MessageConstant.PASSWORD_ERROR);
        }
    }
}
