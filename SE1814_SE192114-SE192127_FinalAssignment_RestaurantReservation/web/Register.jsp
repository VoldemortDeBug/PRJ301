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
        <form method="post" action="MainController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="register"/>
            Username: <input type="text" name="txtUsername"/><br/>
            Password:  <input type="password" name="txtPassword"/><br/>
            Name:  <input type="text" name="txtName"/><br/>
            Email: <input type="email" name="txtEmail"/><br/>
            Phone:    <input type="tel" name="txtPhone" id="phone" name="phone" placeholder="0123456789" pattern="[0-9]{3}[0-9]{7}" required><br/>
            
            Profile Photo: <input type="file" name="photo" size="50" /><br/>
            <input type="submit" value="Save">
        </form>
    </body>
</html>
