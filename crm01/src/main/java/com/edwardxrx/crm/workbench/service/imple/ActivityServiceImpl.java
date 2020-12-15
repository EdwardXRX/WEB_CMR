package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.vo.PaginationVO;
import com.edwardxrx.crm.workbench.dao.ActivityDao;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.service.ActivityService;
import org.apache.ibatis.javassist.compiler.ast.CallExpr;

import java.util.List;
import java.util.Map;

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

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        System.out.println("进入到ActivityServiceImpl文件中得pageList中");
        //取得total
        int total = dao.getTotalByCondition(map);

        System.out.println("total:" + total);

        //取得dataList
        List<Activity> dataList = dao.getActivityListByCondition(map);

        //封装成vo

        PaginationVO<Activity> vo = new PaginationVO<Activity>();

        vo.setTotal(total);
        vo.setDataList(dataList);



        //返回

        return vo;
    }
}
