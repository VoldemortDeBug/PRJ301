<%-- 
    Document   : header
    Created on : Feb 17, 2025, 8:57:14 AM
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

        <%
            UserDTO user = new UserDTO();
            if (request.getSession().getAttribute("user") != null) {
                user = (UserDTO) request.getSession().getAttribute("user");
            }
            

        %>

        <h1>This is header!</h1>
        
        <form action="UserController">
            <input type="hidden" name="action" value="logout"/>
            <input type="submit" value="Logout"/>
        </form>
    </body>
</html>
