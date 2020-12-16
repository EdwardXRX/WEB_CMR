package com.edwardxrx.crm.workbench.web.controller;

import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.settings.service.impl.UserServiceImpl;
import com.edwardxrx.crm.utils.*;
import com.edwardxrx.crm.vo.PaginationVO;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.domain.ActivityRemark;
import com.edwardxrx.crm.workbench.service.ActivityService;
import com.edwardxrx.crm.workbench.service.imple.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.settings.web.controller
 * @ClassName: UserController
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/9 15:58
 * @Version: 1.0
 */
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入市场活动控制器");


        String path = request.getServletPath();



        if("/workbench/activity/getUserList.do".equals(path))
        { 
            getUserList(request,response);
        } else if("/workbench/activity/save.do".equals(path))
        {
            save(request,response);
        }
        else if("/workbench/activity/pageList.do".equals(path))
        {
            pageList(request,response);
        }else if("/workbench/activity/delete.do".equals(path))
        {
            delete(request,response);
        }
        else if("/workbench/activity/getUserListAndActivity.do".equals(path))
        {
            getUserListAndActivity(request,response);
        }else if("/workbench/activity/update.do".equals(path))
        {
            update(request,response);
        }
        else if("/workbench/activity/getRemarkListByAid.do".equals(path))
        {
            getRemarkListByAid(request,response);
        }
        else if("/workbench/activity/detail.do".equals(path))
        {
            detail(request,response);
        }
        else if("/workbench/activity/deleteRemark.do".equals(path))
        {
            deleteRemark(request,response);
        }
        else if("/workbench/activity/saveRemark.do".equals(path))
        {
            saveRemark(request,response);
        }
        else if("/workbench/activity/updateRemark.do".equals(path))
        {
            updateRemark(request,response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行更新评论操作");

        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag= "1";

        ActivityRemark ar = new ActivityRemark();
        ar.setEditFlag(editFlag);
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag= as.updateRemark(ar);

        Map<String,Object> map = new HashMap<>();

        map.put("success",flag);
        map.put("ar",ar);

        PrintJson.printJsonObj(response,map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行保存评论操作");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();

        ar.setActivityId(activityId);
        ar.setNoteContent(noteContent);
        ar.setId(id);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);

        boolean flag = as.saveRemark(ar);

        //除了返回flag，还有ar

        Map<String,Object> map = new HashMap<>();

        map.put("success",flag);
        map.put("ar",ar);

        PrintJson.printJsonObj(response,map);

    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行删除评论操作");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");

        boolean flag = as.deleteRemark(id);

        PrintJson.printJsonFlag(response,flag);


    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行通过id获取评论列表");

        String activityId = request.getParameter("activityId");

        System.out.println("activityId:" + activityId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());


        List<ActivityRemark> arList = as.getRemarkListByAid(activityId);

        if(arList == null)
            System.out.println("空");
        else
            System.out.println("不为空");

        PrintJson.printJsonObj(response,arList);


    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入细节");

        String id = request.getParameter("id");


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = as.detail(id);



        request.setAttribute("a",a);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);

    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动修改操作");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");
        System.out.println("id"+id);

        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String editBy = ((User)request.getSession().getAttribute("user")).getName();


        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);


        boolean flag = activityService.update(a);


        PrintJson.printJsonFlag(response,flag);


    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动修改，根据id得到活动，获得一个uList");

        String id = request.getParameter("id");


        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,Object> map = as.getUserListAndActivity(id);



        PrintJson.printJsonObj(response,map);




    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到删除操作");

        String ids[] = request.getParameterValues("id");

        if(ids == null)
            System.out.println("1111111");
        else
            System.out.println("2222222");



        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());


        boolean flag = as.delete(ids);

        //传到前端
        PrintJson.printJsonFlag(response,flag);



    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息的列表的操作（结合条件查询+分页查询）");


        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNostr = request.getParameter("pageNo");
        System.out.println("-----------------------");

        //计算略过的数量
        int pageNo = Integer.valueOf(pageNostr);

        System.out.println("pageNo:" + pageNo);

        String pageSizestr = request.getParameter("pageSize");


        //每页的数字
        int pageSize = Integer.valueOf(pageSizestr);

        System.out.println("pageSize:" + pageSize);

        //这是应用在sql语句中的
        //sql语句，第一个位掠过的数量
        //第二个为每页查询的数量
        int skipCount = (pageNo -1) *pageSize;

        System.out.println("skipCount:" + skipCount);


        System.out.println("endDate:"+ endDate);
        System.out.println("startDate:"+ startDate);
        System.out.println("owner:"+ owner);

        //打包成一个map
        //vo，是给前端传送数据
        //不是前端给后端
        Map<String,Object> map =new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);





        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        System.out.println("1111111111111111111111111111111111");
        /*
        前端要：
        1. 市场列表
        2. 总数

        业务层拿到了数据之后，使用两种方法传送给前端
        1. map
        2. vo

        这里使用vo
        因为使用频率很大
        */
        PaginationVO<Activity> pageList= as.pageList(map);

        PrintJson.printJsonObj(response,pageList);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("添加活动信息页面");

         String id = UUIDUtil.getUUID();
         String owner = request.getParameter("owner");
         String name = request.getParameter("name");
         String startDate = request.getParameter("startDate");
         String endDate = request.getParameter("endDate");
         String cost = request.getParameter("cost");
         String description = request.getParameter("description");
         String createTime = DateTimeUtil.getSysTime();
         //创建人：当前登录用户
         String createBy = ((User)request.getSession().getAttribute("user")).getName();


        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);


        boolean flag = activityService.save(a);


        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息表");

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        System.out.println("111");
        List<User> userList = userService.getUserList();

        if(userList == null)
            System.out.println("空");
        else
            System.out.println("有");

        PrintJson.printJsonObj(response,userList);
    }




}
