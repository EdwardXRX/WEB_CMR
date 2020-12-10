package com.edwardxrx.crm.settings.service;

import com.edwardxrx.crm.exception.LoginException;
import com.edwardxrx.crm.settings.domain.User;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.service
 * @ClassName: UserService
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/9 15:54
 * @Version: 1.0
 */
public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}

