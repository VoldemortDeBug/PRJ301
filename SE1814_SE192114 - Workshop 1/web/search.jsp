<%-- 
    Document   : user
    Created on : Feb 16, 2025, 6:55:32 PM
    Author     : Admin
--%>

<%@page import="org.apache.coyote.ErrorState"%>
<%@page import="java.sql.Date"%>
<%@page import="dto.ProjectDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="java.rmi.ServerError"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
        <style>
            table {
                border: 2px solid black; /* Outer border */
                border-collapse: collapse; /* Merges cell borders */
            }
            td, th {
                border: 1px solid black; /* Borders for each cell */
                padding: 8px;
            }
        </style>
    </head>
    <body>
        <%        
             String action = request.getParameter("action");
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            if(user!=null){
            
         %>
        <%@include file="header.jsp"%>
            <h1>welcome <%= user.getName()%></h1>
            <form action = "MainController" method="get">
                <input type="hidden" name="action" value="logout"/>
                <input type="submit" value="logout"/>
            </form>
            
            <h2>Create new project</h2>
            <%
                String pname="";
                String pdes="";
                String pstat="";
                Date pdate=null;
                
                String errorName="";
                String errorStat="";
                String errorDate="";
                
                if(request.getAttribute("projectError") !=null){
                    ProjectDTO pError = (ProjectDTO)request.getAttribute("projectError");
                    pname=pError.getName();
                    pdes=pError.getDescription();
                    pstat=pError.getStatus();
                    pdate=pError.getEst_launch();
                    if(pname.equals("")){
                        errorName="(*)";
                    }
                    if(pstat.equals("")){
                        errorStat="(*)";
                    }
                    if(pdate==null){
                        errorDate="(*)";
                    }
                }
                
            %>
            <form action="MainController" method="get">
                <table>
                <input type="hidden" name="action" value="createProject"/>
                <tr><td>Project Name <span style="color: red"><%= errorName%></span>  </td><td><input type="text" name="txtPname" value="<%= pname%>"/> </td></tr>
                <tr><td>Description </td><td><input type="text" name="txtPdes" value="<%= pdes%>"/> </td></tr>
                <tr><td>Status<span style="color: red"><%= errorStat%></span>  </td><td><input type="text" name="txtPstat" value="<%= pstat%>"/> </td></tr>
                <tr><td>Estimated Launch<span style="color: red"><%= errorDate %></span></td><td><input type="date" name="txtPdate" value="<%=pdate %>"/> </td></tr>
                
                </table>
                <input type="submit" value="Create"/><input type="reset" value="Delete"/> 
            </form>
            
            <h2>ALL PROJECTS:</h2>
            <table>
                <tr>
                    <td>Project_NAME</td>
                    <td>Description</td>
                    <td>Status</td>
                    <td>Estimated launch</td>
                    <td>New Status</td>
                    <td>Update</td>
                    
                </tr>
                
            <%
                ArrayList<ProjectDTO> plist = new ArrayList<>();
                
                if(request.getAttribute("allProjects") !=null){
                    plist = (ArrayList<ProjectDTO>) request.getAttribute("allProjects");
                } 
                        
                String updated="";
                for(ProjectDTO i: plist){
                    if( request.getAttribute("updatedOn")!=null &&  (int)request.getAttribute("updatedOn")== i.getId()){
                        updated="SUCCESS";
                    }else{
                        updated = "";
                    }
            %>
            <tr>
                <td><%= i.getName() %></td>
                <td><%= i.getDescription() %></td>
                <td><%= i.getStatus() %></td>
                <td><%= i.getEst_launch() %></td>
            <form action="MainController" method="get">
                <input type="hidden" name="action" value="updateProjectStatus"/>
                <input type="hidden" name="projectID" value="<%= i.getId() %>" />
                <td><input type="text" name="newInfo"/> </td>
                <td><input type="submit" value="Update"/> <span style="color: blue"><%= updated %></span></td>
            </form>
            </tr>
            <%}%>
            </table>
            <br>
            <br>
            
            <form action="MainController" method="get">
                <%
                    String searchInfo="";
                    if(request.getAttribute("searchInfo")!=null){
                        searchInfo = (String)request.getAttribute("searchInfo");
                    }
                %>
                <input type="hidden" name="action" value="searchName"/>
                <input type="text" name="searchInfo" value="<%= searchInfo %>"/>
                <input type="submit" value="SEARCH"/>
            </form>
                <br>
                

           <table>
                <tr>
                    <td>Project_ID</td>
                    <td>Project_NAME</td>
                    <td>Description</td>
                    <td>Status</td>
                    <td>Estimated launch</td>
                    
                </tr>
                
            <%
                ArrayList<ProjectDTO> splist = new ArrayList<>();
                
                if(request.getAttribute("searchedProjects") !=null){
                    splist = (ArrayList<ProjectDTO>) request.getAttribute("searchedProjects");
                } 
                        
                for(ProjectDTO i: splist){
            %>
            <tr>
                <td><%= i.getId() %></td>
                <td><%= i.getName() %></td>
                <td><%= i.getDescription() %></td>
                <td><%= i.getStatus() %></td>
                <td><%= i.getEst_launch() %></td>
           
            </tr>
            <%}%>
            </table>
            
            
                
                
                
                
                
                
        <%@include file="footer.jsp"  %>
        
        <%}else{%>
        <h1>You're not logged in.</h1>
        <%}%>
        
</html>
