<%-- 
    Document   : Login
    Created on : Feb 16, 2025, 6:25:43 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGIN Page</title>
    </head>
    <body>
        <%@include file="header.jsp"  %>
        <h1>USER LOGIN</h1>
        <div style="min-height: 800px">
            <form action="UserController" method="post">
                <table>
                    <input type="hidden" name="action" value="login"/>
                    <tr>
                        <td>Username: </td>
                        <td><input type="text" name="strUserID"></td>
                    </tr>
                    <tr>
                        <td>Password: </td>
                        <td><input type="password" name="strPassword"></td>
                    </tr>
                </table>
                <input type="submit" value="Login"/>
            </form>
            <%                
                String ms = request.getAttribute("message") + "";
                if (ms.equals("null")) {
                    ms = "";
                }
            %>
            <div style="color: red"><%= ms%></div>

            <a href="Register.jsp"><input type="button" value="Register" /> </a>

            <%@include file="footer.jsp"  %>
    </body>
</html>
