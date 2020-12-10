package com.edwardxrx.crm.settings.service.impl;

import com.edwardxrx.crm.exception.LoginException;
import com.edwardxrx.crm.settings.dao.UserDao;
import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.utils.DateTimeUtil;
import com.edwardxrx.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.service.impl
 * @ClassName: UserServiceImpl
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/9 15:55
 * @Version: 1.0
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if(user == null)
        {
            throw new LoginException("账号密码错误");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();

        //判断失效时间
        //小于0 ，说明账号失效
        if(expireTime.compareTo(currentTime) <0)
        {
            throw new LoginException("账号已失效");
        }

        //判断锁定
        String lockState = user.getLockState();
        if("0".equals(lockState))
        {
            throw new LoginException("账号已锁定");
        }


        //判断ip地址
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip))
        {
            throw new LoginException("IP受限！");
        }

        //如果程序成功执行到本行
        //说明账号密码正常
        return user;
    }
}
