<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("manager") == null) {
        response.sendRedirect("managerLogin.jsp");
        return;
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Manager Menu</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
    }
    .box {
        width: 600px;
        margin: 60px auto;
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.12);
    }
    h1 {
        color: #003366;
    }
    ul {
        line-height: 2;
    }
</style>
</head>
<body>
<div class="box">
    <h1>Manager Menu</h1>

    <ul>
        <li><a href="managerList">List all activities</a></li>
        <li><a href="addActivity.jsp">Add activity</a></li>
        <li><a href="editActivity.jsp">Edit activity</a></li>
    </ul>

    <p><a href="logout">Return / logout</a></p>
</div>
</body>
</html>