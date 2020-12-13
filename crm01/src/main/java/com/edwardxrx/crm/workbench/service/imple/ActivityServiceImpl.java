package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.workbench.dao.ActivityDao;
import com.edwardxrx.crm.workbench.service.ActivityService;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.workbench.service.imple
 * @ClassName: ActivityServiceImpl
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/13 20:56
 * @Version: 1.0
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao dao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
