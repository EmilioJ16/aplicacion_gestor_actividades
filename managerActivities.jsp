<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="activities.db.Activity" %>

<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("manager") == null) {
        response.sendRedirect("managerLogin.jsp");
        return;
    }

    ArrayList activities = (ArrayList) request.getAttribute("activities");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Manager - All Activities</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
    }
    .box {
        width: 1100px;
        margin: 40px auto;
        background: white;
        padding: 25px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.12);
    }
    h1 {
        color: #003366;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border: 1px solid #ccc;
        padding: 10px;
        text-align: left;
    }
    th {
        background-color: #003366;
        color: white;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    .error {
        color: red;
        font-weight: bold;
    }
    .links {
        margin-top: 20px;
    }
    .links a {
        margin-right: 15px;
    }
</style>
</head>
<body>
<div class="box">
    <h1>All Activities</h1>

    <%
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        } else if (activities == null || activities.size() == 0) {
    %>
        <p>No activities found.</p>
    <%
        } else {
    %>

    <table>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Start date</th>
            <th>Cost</th>
            <th>Pavillion</th>
            <th>Total places</th>
            <th>Occupied places</th>
        </tr>

        <%
            for (int i = 0; i < activities.size(); i++) {
                Activity a = (Activity) activities.get(i);
        %>
        <tr>
            <td><%= a.getid() %></td>
            <td><%= a.getname() %></td>
            <td><%= a.getdescription() %></td>
            <td><%= a.getinitial() %></td>
            <td><%= a.getcost() %></td>
            <td><%= a.getpavname() %></td>
            <td><%= a.gettotal() %></td>
            <td><%= a.getoccupied() %></td>
        </tr>
        <%
            }
        %>
    </table>

    <%
        }
    %>

    <div class="links">
        <a href="manager.jsp">Return to manager menu</a>
        <a href="logout">Logout</a>
    </div>
</div>
</body>
</html>