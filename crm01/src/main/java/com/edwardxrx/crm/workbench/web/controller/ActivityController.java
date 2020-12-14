package com.edwardxrx.crm.workbench.web.controller;

import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.settings.service.impl.UserServiceImpl;
import com.edwardxrx.crm.utils.*;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.service.ActivityService;
import com.edwardxrx.crm.workbench.service.imple.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
