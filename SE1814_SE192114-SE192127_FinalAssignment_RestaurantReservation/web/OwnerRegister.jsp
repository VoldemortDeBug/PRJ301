<%-- 
    Document   : Register
    Created on : Mar 2, 2025, 5:16:53 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%@include file="header.jsp" %>

        <%
            String unameE = "";
            String uname = request.getAttribute("username") + "";
            if (uname.equals("null") || uname.equals("")) {
                if (request.getAttribute("error") != null) {
                    unameE = "(*)";
                }
                uname = "";
            }

            String passE = "";
            String passw = request.getAttribute("password") + "";
            if (passw.equals("null") || passw.equals("")) {
                if (request.getAttribute("error") != null) {
                    passE = "(*)";
                }
                passw = "";
            }

            String nameE = "";
            String name = request.getAttribute("name") + "";
            if (name.equals("null") || name.equals("")) {
                if (request.getAttribute("error") != null) {
                    nameE = "(*)";
                }
                name = "";
            }

            String emailE = "";
            String email = request.getAttribute("email") + "";
            if (email.equals("null") || email.equals("")) {
                if (request.getAttribute("error") != null) {
                    emailE = "(*)";
                }
                email = "";
            }

            String phoneE = "";
            String phone = request.getAttribute("phone") + "";
            if (phone.equals("null") || phone.equals("")) {
                if (request.getAttribute("error") != null) {
                    phoneE = "(*)";
                }
                phone = "";
            }

        %>

        <h1>RESTAURANT OWNER REGISTER</h1>

        <form method="post" action="UserController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="register"/>
            <input type="hidden" name="type" value="Owner"/>
            Username:<span style="color: red"><%= unameE%></span> <input type="text" name="txtUsername" value="<%= uname%>" required/><br/>
            Password: <span style="color: red"><%= passE%></span>  <input type="password" name="txtPassword" value="<%= passw%>"  required/><br/>
            Name:  <span style="color: red"><%= nameE%></span> <input type="text" name="txtName" value="<%= name%>" required/><br/>
            Email:  <span style="color: red"><%= emailE%></span> <input type="email" name="txtEmail" value="<%= email%>" required/><br/>
            Phone:   <span style="color: red"><%= phoneE%></span>  <input type="tel" value="<%= phone%>" name="txtPhone" id="phone" name="phone" placeholder="0123456789" pattern="[0-9]{3}[0-9]{7}" required><br/>
            Profile Photo: <input type="file" name="photo" size="50" /><br/>
            <input type="submit" value="Save">
        </form>


    </body>
</html>
