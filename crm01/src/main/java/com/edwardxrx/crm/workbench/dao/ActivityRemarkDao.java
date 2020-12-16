package com.edwardxrx.crm.workbench.dao;

import com.edwardxrx.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.dao
 * @ClassName: ActivityRemarkDao
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/14 16:58
 * @Version: 1.0
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);

}
