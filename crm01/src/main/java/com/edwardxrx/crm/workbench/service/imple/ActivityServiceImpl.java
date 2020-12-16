package com.edwardxrx.crm.workbench.service.imple;

import com.edwardxrx.crm.settings.dao.UserDao;
import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.utils.SqlSessionUtil;
import com.edwardxrx.crm.vo.PaginationVO;
import com.edwardxrx.crm.workbench.dao.ActivityDao;
import com.edwardxrx.crm.workbench.dao.ActivityRemarkDao;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.domain.ActivityRemark;
import com.edwardxrx.crm.workbench.service.ActivityService;

import java.util.HashMap;
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
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao remarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public boolean save(Activity a) {
        int count  = activityDao.save(a);

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
        int total = activityDao.getTotalByCondition(map);

        System.out.println("total:" + total);

        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        //封装成vo

        PaginationVO<Activity> vo = new PaginationVO<Activity>();

        vo.setTotal(total);
        vo.setDataList(dataList);



        //返回

        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;
        //首先查询我们需要删除的备注数量
        int count = remarkDao.getCountByAids(ids);

        //然后再查询我们删除的备注条数
        int count2 = remarkDao.deleteByAids(ids);
        //两者进行比较，如果成功之后再删除我们的真正要删除的活动记录

        if(count != count2)
        {
            System.out.println("不成功");
            flag = false;
        }

        int count3 = activityDao.delete(ids);

        if(count3 != ids.length)
        {
            System.out.println("删除活动失败");
            flag = false;
        }
        //返回一个标志位
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        //取uList
        List<User> uList = userDao.getUserList();

        //取得单条活动通过id值
        Activity a = activityDao.getActivityById(id);

        //将uList打包进map
        Map<String,Object> map = new HashMap<>();

        map.put("uList",uList);
        map.put("a",a);




        //返回map

        return map;
    }

    @Override
    public boolean update(Activity a) {

        int count = activityDao.update(a);

        boolean flag = true;
        if(count != 1)
        {
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {

        Activity a = activityDao.detail(id);

        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> arList = remarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {

        int count = remarkDao.deleteRemark(id);

        boolean flag = true;

        if(count == 1)
        {
            flag = true;
        }
        else
            flag = false;

        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;

        int count = remarkDao.saveRemark(ar);
        if(count != 1)
            flag = false;

        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        int count = remarkDao.updateRemark(ar);
        boolean flag = true;
        if(count!= 1)
            flag = false;
        return flag;
    }
}
