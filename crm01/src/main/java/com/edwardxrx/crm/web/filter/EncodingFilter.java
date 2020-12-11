package com.edwardxrx.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ProjectName: WEB_CMR
 * @Package: com.edwardxrx.crm.web.filter
 * @ClassName: EncodingFilter
 * @Author: EdwardX
 * @Description:
 * @Date: 2020/12/11 16:38
 * @Version: 1.0
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入编码过滤器");

        servletRequest.setCharacterEncoding("UTF-8");

        servletResponse.setContentType("text/html;charset=utf-8" );

        //放行

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
