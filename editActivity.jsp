<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("manager") == null) {
        response.sendRedirect("managerLogin.jsp");
        return;
    }

    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Edit Activity</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
    }
    .box {
        width: 650px;
        margin: 40px auto;
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.12);
    }
    h1 {
        color: #003366;
    }
    label {
        display: block;
        margin-top: 12px;
        font-weight: bold;
    }
    input[type="text"], input[type="number"] {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        border: 1px solid #bbb;
        border-radius: 6px;
        box-sizing: border-box;
    }
    input[type="submit"] {
        margin-top: 20px;
        background-color: #003366;
        color: white;
        border: none;
        padding: 10px 18px;
        border-radius: 6px;
        cursor: pointer;
    }
    .error {
        color: red;
        font-weight: bold;
        margin-top: 15px;
    }
</style>
</head>
<body>
<div class="box">
    <h1>Edit Activity</h1>

    <form action="editActivity" method="POST">
        <label>Activity ID</label>
        <input type="text" name="id">

        <label>New name</label>
        <input type="text" name="name">

        <label>New description</label>
        <input type="text" name="description">

        <label>New start date</label>
        <input type="text" name="initial">

        <label>New cost</label>
        <input type="text" name="cost">

        <label>New pavillion name</label>
        <input type="text" name="pavname">

        <label>New total places</label>
        <input type="text" name="total">

        <label>New occupied places</label>
        <input type="text" name="occupied">

        <input type="submit" value="Edit activity">
    </form>

    <%
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
    %>

    <p><a href="manager.jsp">Return to manager menu</a></p>
</div>
</body>
</html>