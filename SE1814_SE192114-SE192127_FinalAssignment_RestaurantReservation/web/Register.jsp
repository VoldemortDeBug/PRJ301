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

        <h1>USER REGISTER</h1>
        
        <%
            String unameE ="";
            String uname = request.getAttribute("username")+"";
            if(uname.equals("null") || uname.equals("")){
                unameE="(*)";
                uname="";
            }
            
            String passE ="";
            String passw = request.getAttribute("password")+"";
            if(passw.equals("null") || passw.equals("")){
                passE="(*)";
                passw="";
            }
            
            String nameE ="";
            String name = request.getAttribute("name")+"";
            if(name.equals("null") || name.equals("")){
                nameE="(*)";
                name="";
            }
            
            String emailE ="";
            String email = request.getAttribute("email")+"";
            if(email.equals("null") || email.equals("")){
                emailE="(*)";
                email="";
            }
            
            String phoneE ="";
            String phone = request.getAttribute("phone")+"";
            if(phone.equals("null") || phone.equals("")){
                phoneE="(*)";
                phone="";
            }
            
        %>
        <form method="post" action="UserController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="register"/>
            Username:<span style="color: red"><%= unameE %></span> <input type="text" name="txtUsername" value="<%= uname %>"/><br/>
            Password: <span style="color: red"><%= passE %></span>  <input type="password" name="txtPassword" value="<%= passw %>" /><br/>
            Name:  <span style="color: red"><%= nameE %></span> <input type="text" name="txtName" value="<%= name %>"/><br/>
            Email:  <span style="color: red"><%= emailE %></span> <input type="email" name="txtEmail" value="<%= email %>"/><br/>
            Phone:   <span style="color: red"><%= phoneE %></span>  <input type="tel" value="<%= phone %>" name="txtPhone" id="phone" name="phone" placeholder="0123456789" pattern="[0-9]{3}[0-9]{7}" required><br/>

            Profile Photo: <input type="file" name="photo" size="50" /><br/>
            <input type="submit" value="Save">
        </form>

    </body>
</html>
