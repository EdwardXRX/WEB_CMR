package com.edwardxrx.crm.web.filter;

import com.edwardxrx.crm.settings.domain.User;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.web.filter
 * @ClassName: LoginFilter
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/13 10:43
 * @Version: 1.0
 */
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("系统进入验证Filter");

        //需要强转
        //因为是让父类做子类（需要强迫）
        //本例中，就是让servletRequest 做 HttpServletRequest
        //属于强制转换

        /*
        向上转型 : 通过子类对象(小范围)实例化父类对象(大范围),这种属于自动转换
        向下转型 : 通过父类对象(大范围)实例化子类对象(小范围),这种属于强制转换
        */


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //注意是getServletPath(), 不是getContextPath();
        //因为我们获取的是服务器的servlet路径
        String path = httpServletRequest.getServletPath();

        System.out.println(path);

        //特殊的两个路径必须放行，不然会循环
        if("/settings/user/login.do".equals(path) || "/login.jsp".equals(path))
            filterChain.doFilter(servletRequest,servletResponse);
        else
        {
            HttpSession httpSession = httpServletRequest.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user != null) {
                //放行
                filterChain.doFilter(servletRequest,servletResponse);
            } else {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
            }
        }




    }
}
