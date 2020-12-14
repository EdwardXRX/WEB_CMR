package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.workbench.dao.ActivityDao;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.service.ActivityService;
import org.apache.ibatis.javassist.compiler.ast.CallExpr;

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

    @Override
    public boolean save(Activity a) {
        int count  = dao.save(a);

        boolean flag = true;
        if(count != 1)
        {
            flag = false;
        }
        return flag;
    }
}
