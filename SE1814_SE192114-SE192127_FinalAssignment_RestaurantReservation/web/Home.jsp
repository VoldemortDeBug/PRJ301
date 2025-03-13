<%-- 
    Document   : Home
    Created on : Mar 2, 2025, 5:02:16 PM
    Author     : Admin
--%>

<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dto.RestDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME</title>
    </head>
    <body>

        <%@include file="header.jsp" %>

        <h1>Hello!</h1>
        <h1>welcome <%= user.getName()%></h1>

        <br/>
        <%
            int hpage =1;
            if (request.getAttribute("hpage") != null) {
                hpage = (int) request.getAttribute("hpage");
            }
        %>
        <form action="UserController">
            <input type="hidden" name="action" value="home"/>
            <input type="hidden" name="searchTerm" value="<%= searchTerm %>"/>
            <input type="hidden" name="hpage" value="<%= hpage-1 %>"/>
            <input type="submit" value="<--"/>
        </form>
        <form action="UserController">
            <input type="hidden" name="action" value="home"/>
            <input type="hidden" name="searchTerm" value="<%= searchTerm %>"/>
            <input type="hidden" name="hpage" value="<%= hpage+1 %>"/>
            <input type="submit" value="-->"/>
        </form>
  
        <br/>

        <%
            ArrayList<RestDTO> lrest;
            if (request.getAttribute("allrest") != null) {
                lrest = (ArrayList<RestDTO>) request.getAttribute("allrest");
                for (int x = 0; x < lrest.size(); x++) {
                    RestDTO i = lrest.get(x);
        %>
        <a href="UserController?action=clientRestProfile&restID=<%= i.getResID()%>"><img src="users/rimg/<%= (i.getMainPhoto() == null) ? "Default.jpg" : i.getMainPhoto()%>" /></a><br/>
        <span><%=i%></span>
        <br/>
        <br/>
        <br/>

        <% } %>
        <% }%>


    </body>
</html>
