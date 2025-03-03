<%-- 
    Document   : UserProfile
    Created on : Mar 2, 2025, 5:02:29 PM
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
        <form method="post" action="MainController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="updateProfile"/>
            Name:  <%= user.getName() %><br/>
            Email:  <%= user.getEmail()%><br/>
            Phone: <%= user.getEmail()%><br/>

            New Profile Photo: <input type="file" name="photo" size="50" /><br/>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
