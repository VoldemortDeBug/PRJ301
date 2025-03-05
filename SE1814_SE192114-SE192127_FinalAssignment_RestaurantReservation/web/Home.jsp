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

        <%@include file="header.jsp" %>

        <h1>Hello!</h1>
        <h1>welcome <%= user.getName()%></h1>
        <a href="UserProfile.jsp"><img src="users/img/<%= user.getProfilepic()%>"  style="width: 40px; height: 40px; border-radius: 50%"/></a>
        
        <br/>
        
        
        <form action="UserController">
            <input type="hidden" name="action" value="restList.jsp"/>
            <input type="submit" value="Restaurants"/>
        </form>


    </body>
</html>
