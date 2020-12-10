package com.edwardxrx.crm.settings.dao;

import com.edwardxrx.crm.settings.domain.User;

import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.dao
 * @ClassName: StudentDao
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/9 15:50
 * @Version: 1.0
 */
public interface UserDao {

    User login(Map<String, Object> map);
}
