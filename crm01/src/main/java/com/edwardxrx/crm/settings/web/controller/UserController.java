package com.edwardxrx.crm.settings.web.controller;

import com.edwardxrx.crm.settings.domain.User;
import com.edwardxrx.crm.settings.service.UserService;
import com.edwardxrx.crm.settings.service.impl.UserServiceImpl;
import com.edwardxrx.crm.utils.MD5Util;
import com.edwardxrx.crm.utils.PrintJson;
import com.edwardxrx.crm.utils.ServiceFactory;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入用户控制器");
        String path = request.getServletPath();



        if("/settings/user/login.do".equals(path))
        {
            login(request,response);
        } else if("/settings/user/login.do".equals(path))
        {

        }
    }

    //获取用户列表模块


    //用户登录模块
    private void login(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("进入到登录验证操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");

        //转化成MD5加密形式
        loginPwd = MD5Util.getMD5(loginPwd);

        //接受浏览器的id地址
        String ip = request.getRemoteAddr();
        System.out.println("---------ip"+ip);

        //未来的业务层开发，统一使用代理类形态接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        System.out.println("111111111111");
        //
        try {

            //一旦在login出问题，会直接跳转到catch块中
            User user = us.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);
            //如果程序运行到这儿，说明上面的取得User类没有出问题

            /*
               data:{
               "success" : true
               }
            */
            /*String str = "{\"success\":true}";
            response.getWriter().print(str);*/
            PrintJson.printJsonFlag(response,true);

        }catch (Exception e)
        {
            e.printStackTrace();
            //直接报错

            String msg = e.getMessage();
            System.out.println("-----------msg"+msg);

            //两种方法传递信息
            /*
            1. vo
            2. map

            */

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);

        }

    }
}
