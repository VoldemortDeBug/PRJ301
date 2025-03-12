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
        <%@include file="header.jsp" %>

        <%if (!user.getType().equals("Guest")) {%>
        Name:  <%= user.getName()%><br/>
        Email:  <%= user.getEmail()%><br/>
        Phone: <%= user.getPhone()%><br/>
        Coins: <%= user.getCoins()%><br/>
        <img src="users/img/<%= user.getProfilepic()%>"  style="width: 40px; height: 40px; border-radius: 50%"/>

        <h1>UPDATE PROFILE</h1>


        <form method="post" action="UserController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="updateProfile"/>
            New Name:  <input type="text" name="txtName" value="<%= user.getName()%>"/><br/>
            New Email: <input type="email" name="txtEmail"value="<%= user.getEmail()%>"/><br/>
            New Phone:    <input value="<%= user.getPhone()%>" type="tel" name="txtPhone" id="phone" name="phone" placeholder="0123456789" pattern="[0-9]{3}[0-9]{7}" required><br/>
            New Profile Photo: <input type="file" name="photo" size="50" /><br/>
            <input type="submit" value="Save">
            <%
                String message = "";
                if (request.getAttribute("updateProfilePicMessage") != null) {
                    message = (String) request.getAttribute("updateProfilePicMessage");
                }
            %>
            <span style="color: green"><%= message%></span>
        </form>
        <%}%>
        <%if (user.getType().equals("Owner")) {%>

        <h1>Add new restaurant</h1>

        <%

            String rnameE = "";
            String rname = request.getAttribute("rname") + "";
            if (rname.equals("")) {
                rnameE = "(*)";
                rname = "";
            }
            if (rname.equals("null")) {
                rname = "";
            }

            String rlocE = "";
            String rloc = request.getAttribute("rloc") + "";
            if (rloc.equals("")) {
                rlocE = "(*)";
                rloc = "";
            }
            if (rloc.equals("null")) {
                rloc = "";
            }

        %>

        <form method="post" action="UserController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="createRest"/>
            Name: <span style="color: red"><%= rnameE%></span>  <input type="text" name="rname" value="<%= rname%>"  /><br/>
            Location: <span style="color: red"><%= rlocE%></span> <input type="text" name="rloc" value="<%= rloc%>"  /> <br/>
            <input type="submit" value="Save">
            <%
                String rMessage = request.getAttribute("createRestMes") + "";
                if (rMessage.equals("null")) {
                    rMessage = "";
                }
            %>
            <span style="color: green"><%= rMessage%></span>
        </form>

        <% }%>



        <%@include file="footer.jsp"  %>
    </body>
</html>
