<%-- 
    Document   : example01
    Created on : Feb 10, 2025, 7:37:36 AM
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
        <%! int a = 15;%>
        <%
            double b;
            b = Math.sqrt(a);
            
        %>
        <b>Result  : sqrt (<%=a%>) = <span style="color: #ff9933"><%=b%> </span></b>  
    </body>
</html>
