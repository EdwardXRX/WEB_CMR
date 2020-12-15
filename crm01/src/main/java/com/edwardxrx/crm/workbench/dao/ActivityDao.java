package com.edwardxrx.crm.workbench.dao;

import com.edwardxrx.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.dao
 * @ClassName: ActivityDao
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/13 20:53
 * @Version: 1.0
 */
public interface ActivityDao {
    int save(Activity a);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);
}
