<%--
  Created by IntelliJ IDEA.
  User: EdwardX
  Date: 2020/12/9
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>


<%--

4. log4j.logger.org.mybatis.example.BlogMapper=TRACE

--%>
                $.ajax({
                url : "",
                data : {

                },
                type : "",
                dataType : "json",
                success : function (data) {

                }

})

                String createTime = DateTimeUtil.getSysTime();
                //创建人：当前登录用户
                String createBy = ((User)request.getSession().getAttribute("user")).getName();

</body>
</html>
