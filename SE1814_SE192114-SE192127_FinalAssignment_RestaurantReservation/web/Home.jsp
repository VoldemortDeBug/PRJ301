<%-- 
    Document   : Home
    Created on : Mar 2, 2025, 5:02:16 PM
    Author     : Admin
--%>

<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello!</h1>
        <%
            UserDTO user = new UserDTO();
            if (request.getSession().getAttribute("user") != null) {
                user = (UserDTO) request.getSession().getAttribute("user");
            }
        %>
        <h1>welcome <%= user.getName()%></h1>
        <a href="UserProfile.jsp"><img src="users/img/<%= user.getProfilepic()%>"  style="width: 40px; height: 40px; border-radius: 50%"/></a>

        <form action="UserController">
            <input type="hidden" name="action" value="logout"/>
            <input type="submit" value="Logout"/>
        </form>


    </body>
</html>
