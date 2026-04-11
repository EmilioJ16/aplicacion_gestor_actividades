<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="activities.util.HtmlUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Confirm Registration Data</title>
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
    table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 25px;
    }

    td {
        border: 1px solid #ccc;
        padding: 12px;
        font-size: 15px;
    }

    td:first-child {
        background-color: #f4f7fb;
        font-weight: bold;
        color: #003366;
        width: 180px;
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
    <h1>Confirm Registration Data</h1>

    <table border="1">
    <tr><td><b>Name</b></td><td><%= HtmlUtil.e((String) request.getAttribute("name")) %></td></tr>
    <tr><td><b>Surname</b></td><td><%= HtmlUtil.e((String) request.getAttribute("surname")) %></td></tr>
    <tr><td><b>Address</b></td><td><%= HtmlUtil.e((String) request.getAttribute("address")) %></td></tr>
    <tr><td><b>Phone</b></td><td><%= HtmlUtil.e((String) request.getAttribute("phone")) %></td></tr>
    <tr><td><b>Login</b></td><td><%= HtmlUtil.e((String) request.getAttribute("login")) %></td></tr>
    <tr><td><b>Password</b></td><td><%= HtmlUtil.e((String) request.getAttribute("passwd")) %></td></tr>
    </table>

    <p>Are these data correct?</p>

    <form action="confirmRegister" method="POST">
        <input type="hidden" name="answer" value="yes">
        <input type="submit" value="Yes">
    </form>

    <form action="confirmRegister" method="POST">
        <input type="hidden" name="answer" value="no">
        <input type="submit" value="No">
    </form>

</div>
</body>
</html>