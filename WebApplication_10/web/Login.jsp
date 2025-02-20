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
        <div style="min-height: 800px">
            <form action="MainController" method="post">
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
                String ms = request.getAttribute("message")+"";
            %>
            <div style="color: red"><%=ms.equals("null")?"": ms%></div>
            
        </div>
        <%@include file="footer.jsp"  %>
    </body>
</html>
