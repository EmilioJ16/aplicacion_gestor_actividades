<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="activities.util.HtmlUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>User Registration</title>
<meta charset="UTF-8">
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
    <h1>User Registration Form</h1>

    <%@ page import="java.net.URLDecoder" %>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%
    String name = "";
    String surname = "";
    String address = "";
    String phone = "";
    String login = "";
    String passwd = "";

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("reg_name")) name = URLDecoder.decode(c.getValue(), "UTF-8");
            else if (c.getName().equals("reg_surname")) surname = URLDecoder.decode(c.getValue(), "UTF-8");
            else if (c.getName().equals("reg_address")) address = URLDecoder.decode(c.getValue(), "UTF-8");
            else if (c.getName().equals("reg_phone")) phone = URLDecoder.decode(c.getValue(), "UTF-8");
            else if (c.getName().equals("reg_login")) login = URLDecoder.decode(c.getValue(), "UTF-8");
            else if (c.getName().equals("reg_passwd")) passwd = URLDecoder.decode(c.getValue(), "UTF-8");
        }
    }
    %>

    <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
    %>
        <p style="color:red; font-weight:bold;"><%= HtmlUtil.e(error) %></p>
    <%
    }
    %>

    <form action="register" method="POST">
        Name:
        <input type="text" name="name" size="30" value="<%= HtmlUtil.e(name) %>"><br><br>

        Surname:
        <input type="text" name="surname" size="30" value="<%= HtmlUtil.e(surname) %>"><br><br>

        Address:
        <input type="text" name="address" size="30" value="<%= HtmlUtil.e(address) %>"><br><br>

        Phone:
        <input type="text" name="phone" size="30" value="<%= HtmlUtil.e(phone) %>"><br><br>

        Login:
        <input type="text" name="login" size="30" value="<%= HtmlUtil.e(login) %>"><br><br>

        Password:
        <input type="password" name="passwd" size="30" value="<%= HtmlUtil.e(passwd) %>"><br><br>

        <input type="submit" value="Send registration data">
        <input type="reset" value="Clear">
    </form>

    <p>
    <a href="cancelRegister">Return to initial page</a>
    </p>
</div>
</body>
</html>