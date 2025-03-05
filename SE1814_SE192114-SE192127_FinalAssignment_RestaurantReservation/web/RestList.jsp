<%-- 
    Document   : RestList
    Created on : Mar 5, 2025, 4:14:47 PM
    Author     : Admin
--%>

<%@page import="java.util.List"%>
<%@page import="dto.RestDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="header.jsp"  %>


        <%            if (request.getAttribute("rlist") != null) {
                List<RestDTO> rlist = (List<RestDTO>) request.getAttribute("rlist");
            
            if (rlist.size()>0)
            {
        %>

        <h1>Restaurant owned by <%= user.getName()%></h1>

        <table>
            <tr>
                <td>Name</td>
                <td>Location</td>
            </tr>

            <%
                for (RestDTO i : rlist) {
            %>
            <tr>
                <td><%= i.getName()%></td>
                <td><%= i.getLoc()%></td>
            </tr>
            <%}%>
        </table>
        <%}}%>
        
        <a href="Home.jsp"><input type="button" value="Home" /></a>

        <%@include file="footer.jsp"  %>

    </body>
</html>
