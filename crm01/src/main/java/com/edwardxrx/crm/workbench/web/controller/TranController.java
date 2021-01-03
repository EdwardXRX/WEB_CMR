package com.edwardxrx.crm.workbench.web.controller;

import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.settings.service.impl.UserServiceImpl;
import com.edwardxrx.crm.utils.DateTimeUtil;
import com.edwardxrx.crm.utils.PrintJson;
import com.edwardxrx.crm.utils.ServiceFactory;
import com.edwardxrx.crm.utils.UUIDUtil;
import com.edwardxrx.crm.workbench.domain.Customer;
import com.edwardxrx.crm.workbench.domain.Tran;
import com.edwardxrx.crm.workbench.domain.TranHistory;
import com.edwardxrx.crm.workbench.service.CustomerService;
import com.edwardxrx.crm.workbench.service.TranService;
import com.edwardxrx.crm.workbench.service.imple.CustomerServiceImpl;
import com.edwardxrx.crm.workbench.service.imple.TranServiceImpl;
//import sun.plugin2.gluegen.runtime.StructAccessor;

import javax.servlet.ServletContext;
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
 * @Package: com.edwardxrx.crm.workbench.web.controller
 * @ClassName: TranController
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/23 19:49
 * @Version: 1.0
 */
public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入交易控制器");

        String path = request.getServletPath();

        if ("/workbench/transaction/add.do".equals(path)) {
            add(request, response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(path)) {
            getCustomerName(request, response);
        } else if ("/workbench/transaction/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/transaction/detail.do".equals(path)) {
            detail(request, response);
        }
        else if ("/workbench/transaction/getHistoryListByTranId.do".equals(path)) {
            getHistoryListByTranId(request, response);
        }
        else if ("/workbench/transaction/changeStage.do".equals(path)) {
            changeStage(request, response);
        }
        else if ("/workbench/transaction/getCharts.do".equals(path)) {
            getCharts(request, response);
        }


    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("获取交易统计图表的数据");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        /*
            业务层需要返回：
                    total
                    dataList

                    通过map打包以上两项进行返回
        */

        Map<String,Object> map = ts.getCharts();

        PrintJson.printJsonObj(response,map);



    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("改变阶段");

        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");

        String editTime = DateTimeUtil.getSysTime();
        //创建人：当前登录用户
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditBy(editBy);
        t.setEditTime(editTime);

        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(t.getStage());
        t.setPossibility(possibility);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        boolean flag = ts.changeStage(t);

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("t",t);

        PrintJson.printJsonObj(response,map);
    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据交易id获取交易历史列表");

        String tranId = request.getParameter("tranId");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> thList = ts.getHistoryListByTranId(tranId);

        //添加一个可能性得属性
        //为什么在控制层添加数据，因为application这里有
        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        for(TranHistory tranHistory:thList)
        {
            //根据每一条交易历史
            //取出每一个阶段
            String stage = tranHistory.getStage();
            String possibility = pMap.get(stage);
            tranHistory.setPossibility(possibility);
        }

        PrintJson.printJsonObj(response,thList);

        
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran t = ts.detail(id);

        //处理可能性
        /*
            阶段和可能性之间对应关系
        * */

        String stage = t.getStage();
        //以下三种方式都可以
        //任选其一
        /*ServletContext applivation1 = this.getServletContext();
        ServletContext applivation2 = request.getServletContext();
        ServletContext applivation3 = this.getServletConfig().getServletContext();*/

        Map<String,String> pMap = (Map<String, String>) this.getServletContext().getAttribute("pMap");
        String possibility = pMap.get(t.getStage());
        t.setPossibility(possibility);


        request.setAttribute("t",t);
        //因为到了详细信息也，用request转发，如果在页面刷新，就是刷。do/而不是jsp
        //因为jsp本身没数据
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);





    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("执行添加交易的操作");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        //此时只有customerName，没有customerId
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        //注意，此时还是name，没有取得id

        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        //在这里需要按照名字操作
        boolean flag = ts.save(t, customerName);

        if (flag) {
            //跳转到列表页
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
        }


    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得客户名称列表，按照客户名称进行模糊查询");

        String name = request.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> list = cs.getCustomerName(name);

        PrintJson.printJsonObj(response, list);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转交易添加页操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        request.setAttribute("uList", uList);
        request.getRequestDispatcher("save.jsp").forward(request, response);
    }

}
