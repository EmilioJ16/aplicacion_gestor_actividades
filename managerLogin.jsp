<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="activities.util.HtmlUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Manager Login</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
    }
    .box {
        width: 500px;
        margin: 60px auto;
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
        margin-top: 15px;
        font-weight: bold;
    }
    input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-top: 6px;
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
        margin-top: 15px;
        font-weight: bold;
    }
</style>
</head>
<body>
<div class="box">
    <h1>Manager Login</h1>

    <form action="managerLogin" method="POST">
        <label for="password">Manager password</label>
        <input type="password" name="password" id="password">

        <input type="submit" value="Login as manager">
    </form>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p class="error"><%= HtmlUtil.e(error) %></p>
    <%
        }
    %>

    <p><a href="index.html">Return to initial page</a></p>
</div>
</body>
</html>