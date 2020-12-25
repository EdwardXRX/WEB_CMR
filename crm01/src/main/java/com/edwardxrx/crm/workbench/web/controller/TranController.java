package com.edwardxrx.crm.workbench.web.controller;

import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.settings.service.impl.UserServiceImpl;
import com.edwardxrx.crm.utils.PrintJson;
import com.edwardxrx.crm.utils.ServiceFactory;
import com.edwardxrx.crm.workbench.domain.Customer;
import com.edwardxrx.crm.workbench.service.CustomerService;
import com.edwardxrx.crm.workbench.service.imple.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

        if("/workbench/transaction/add.do".equals(path))
        {
            add(request,response);
        }
        else if("/workbench/transaction/getCustomerName.do".equals(path))
        {
            getCustomerName(request,response);
        }



    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得客户名称列表，按照客户名称进行模糊查询");

        String name = request.getParameter("name");

        CustomerService cs = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        List<String> list = cs.getCustomerName(name);

        PrintJson.printJsonObj(response,list);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转交易添加页操作");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        request.setAttribute("uList",uList);
        request.getRequestDispatcher("save.jsp").forward(request,response);
    }

}
