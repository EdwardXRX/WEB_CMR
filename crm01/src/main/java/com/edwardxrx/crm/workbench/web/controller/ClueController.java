package com.edwardxrx.crm.workbench.web.controller;

import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.settings.service.impl.UserServiceImpl;
import com.edwardxrx.crm.utils.*;
import com.edwardxrx.crm.workbench.domain.Activity;
import com.edwardxrx.crm.workbench.domain.Clue;
import com.edwardxrx.crm.workbench.domain.Tran;
import com.edwardxrx.crm.workbench.service.ActivityService;
import com.edwardxrx.crm.workbench.service.ClueService;
import com.edwardxrx.crm.workbench.service.imple.ActivityServiceImpl;
import com.edwardxrx.crm.workbench.service.imple.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入线索控制器");


        String path = request.getServletPath();



        if("/workbench/clue/getUserList.do".equals(path))
        {
            getUserList(request,response);
        }
        else if("/workbench/clue/save.do".equals(path))
        {
            save(request,response);
        } else if("/workbench/clue/detail.do".equals(path))
        {
            detail(request,response);
        }
        else if("/workbench/clue/getActivityListByClueId.do".equals(path))
        {
            getActivityListByClueId(request,response);
        }
        else if("/workbench/clue/unbund.do".equals(path))
        {
            unbund(request,response);
        }
        else if("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path))
        {
            getActivityListByNameAndNotByClueId(request,response);
        }else if("/workbench/clue/bundActivity.do".equals(path))
        {
            bundActivity(request,response);
        }else if("/workbench/clue/getActivityListByName.do".equals(path))
        {
            getActivityListByName(request,response);
        }else if("/workbench/clue/convert.do".equals(path))
        {
            convert(request,response);
        }

    }


    //重点中得难点，难点中得最简单
    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("执行线索转换操作");
        //获取线索id
        String clueId = request.getParameter("clueId");
        String createBy = ((User)(request.getSession().getAttribute("user"))).getName();

        String flag = request.getParameter("flag");

        Tran t = null;
        //勾选了复选框得情况
        if("a".equals(flag))
        {
            t = new Tran();
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String expectedDate = request.getParameter("expectedDate");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            t.setId(id);
            t.setActivityId(activityId);
            t.setName(name);
            t.setStage(stage);
            t.setMoney(money);
            t.setExpectedDate(expectedDate);
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);

        }

        /*
        * 为业务层传递的参数：
        * 1. clueId
        * 2. t对象 可能临时创建一笔交易
        * 当然t可能为空
        *
        * */

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag1 = cs.convert(clueId,t,createBy);

        if(flag1)
        {
            response.sendRedirect(request.getContextPath()+ "/workbench/clue/index.jsp");
        }



    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行搜索活动");
        String aname = request.getParameter("aname");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByName(aname);

        PrintJson.printJsonObj(response,aList);

    }

    private void bundActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行为线索绑定活动");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        String clueId = request.getParameter("clueId");
        System.out.println("clueId:" + clueId);

        //接收的是一整串字符，我们需要将字符串分割成存有activityId的字符串数组
        String activityId = request.getParameter("activityIds");
        System.out.println(activityId);
        String activityIds[] = activityId.split(",");
        //输出测试
        for (int i = 0; i < activityIds.length; i++) {
            System.out.println(i+":"+activityIds[i]);
        }

        //集合存储map
        List<Map<String,String>> list = new ArrayList<>();

        for (int i = 0; i < activityIds.length; i++) {
            Map<String,String> map = new HashMap<>();
            map.put("id",UUIDUtil.getUUID());
            map.put("clueId",clueId);
            map.put("activityId",activityIds[i]);
            list.add(map);
        }

        boolean flag = cs.bundActivity(list);

        PrintJson.printJsonFlag(response,flag);


    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("线索关联模态窗口");
        String clueId = request.getParameter("clueId");
        String aname = request.getParameter("aname");

        System.out.println("clueId:" + clueId);
        System.out.println("aname:" + aname);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,String> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("aname",aname);

        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);

        PrintJson.printJsonObj(response,aList);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入接触市场关联控制器");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(response,flag);


    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到通过线索id查询关联的市场活动列表");

        String clueId = request.getParameter("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList = as.getActivityListByClueId(clueId);

        PrintJson.printJsonObj(response,aList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入细节");

        String id = request.getParameter("id");


        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue clue = cs.detail(id);



        request.setAttribute("clue",clue);

        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("保存线索用户");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        String createTime = DateTimeUtil.getSysTime();

        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Clue clue = new Clue();

        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);

        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());


        boolean flag = clueService.save(clue);

        System.out.println(flag);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取用户列表");

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());


        List<User> userList = userService.getUserList();


        PrintJson.printJsonObj(response,userList);
    }


}
