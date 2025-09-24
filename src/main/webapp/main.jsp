<%--
  Created by IntelliJ IDEA.
  User: kemal
  Date: 24.09.2025
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Main page</title>
</head>

<body>
<%
    String user = "";
    String sessionUser = session.getAttribute("user").toString();
    if (sessionUser == null) {
        response.sendRedirect("login.html");
    } else {
        user = sessionUser;
    }
    String cookieUser = "";
    String sessionId = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("user".equalsIgnoreCase(cookie.getName())) {
                cookieUser = cookie.getValue();
            } else if ("jsessionId".equalsIgnoreCase(cookie.getName())) {
                sessionId = cookie.getValue();
            }
        }
    } else {
        sessionId = session.getId();
    }
%>
<h3>
    Hello <%=user%>! Login successful.
    <br>
    Session ID: <%=sessionId%>
    <br>
    CookieUser: <%=cookieUser%>
    <br>
    <a href="/">Вернуться на главную</a>
</h3>
</body>
</html>
