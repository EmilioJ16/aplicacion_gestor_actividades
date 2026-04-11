<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="activities.util.HtmlUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Registration completed</title>
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
    a.button {
        display: inline-block;
        margin-top: 20px;
        background-color: #003366;
        color: white;
        text-decoration: none;
        padding: 10px 18px;
        border-radius: 6px;
    }
</style>
</head>
<body>
<div class="box">
    <h1>Registration completed successfully</h1>

    <p>Your user has been stored in the database.</p>
    
    <p>
        You can now log in with your login:
        
        <b><%= HtmlUtil.e((String) request.getAttribute("login")) %></b>
    </p>

    <p>Please go to the login page to access the application.</p>

    <a class="button" href="login.jsp">Go to login</a>
</div>
</body>
</html>