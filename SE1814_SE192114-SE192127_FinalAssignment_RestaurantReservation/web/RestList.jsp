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

                if (rlist.size() > 0) {
        %>

        <h1>Restaurant owned by <%= user.getName()%></h1>

        <%
            String message = "";
            String color ="red";
            if(request.getAttribute("message")!=null){
                message=(String)request.getAttribute("message");
                if(message.equals("Restaurant is deleted.")){
                    color="green";
                }
            }
        %>

        <span style="color: <%= color %>"><%= message %></span>

        <table>
            <tr>
                <td>Name</td>
                <td>Location</td>
                <td>Tables/Rooms</td>
                <td>Reservations</td>
            </tr>

            <%
                for (RestDTO i : rlist) {
            %>
            <tr>
                <td><%= i.getName()%></td>
                <td><%= i.getLoc()%></td>
                <td><%= i.getEntites()%></td>
                <td><%= i.getReservations() %></td>
                <td>
                    <form action="UserController" method="post">
                        <input type="hidden" name="action" value="restProfile"/>
                        <input type="hidden" name="restID" value="<%= i.getResID()%>"/>
                        <input type="submit"  value="Details"/> 
                    </form>
                </td>
                <td>
                    <form action="UserController" method="post">
                        <input type="hidden" name="action" value="deleteRestaurant"/>
                        <input type="hidden" name="restID" value="<%= i.getResID()%>"/>
                        <input type="submit"  value="Delete"/> 
                    </form>
                </td>
            </tr>
            <%}%>
        </table>
        <%}
            }%>

        <%@include file="footer.jsp"  %>

    </body>
</html>
