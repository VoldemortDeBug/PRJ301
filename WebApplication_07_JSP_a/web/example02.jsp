<%-- 
    Document   : example02
    Created on : Feb 10, 2025, 8:00:46 AM
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
        <%
            for (int i = 0; i < 100; i++) {
        %>
        <b><%=i%></b> </br>
        <%
            }
        %>

        <h3>BANG CUU CHUONG: </h3> 
        <%
            for (int i = 2; i < 10; i++) {
                %>
                <h4><%=i%> :</h4>
                <table style="border: 1px solid black;">
                <%
                for (int j = 1; j <= 10; j++) {
                    %>
                    <tr>
                        <td><%=i%> x <%=j%> = </td>
                        <td><%=i * j%></td>
                    </tr> 
                    
                    <%
                }
                %>
                </table>
                <%
            }
        %>
    </body>
</html>
