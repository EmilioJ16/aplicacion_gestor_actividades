<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("userLogin") == null) {
        response.sendRedirect("index.html");
        return;
    }

    String userLogin = (String) ses.getAttribute("userLogin");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Sporting Activities Searching Application</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
        margin: 0;
        padding: 0;
    }
    .container {
        width: 750px;
        margin: 40px auto;
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.12);
    }
    .topbar {
        text-align: right;
        margin-bottom: 20px;
    }
    h1 {
        color: #003366;
    }
    label {
        display: block;
        margin-top: 15px;
        font-weight: bold;
    }
    select, input[type="text"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #bbb;
        border-radius: 6px;
        box-sizing: border-box;
    }
    .buttons {
        margin-top: 20px;
        text-align: center;
    }
    input[type="submit"], input[type="reset"] {
        background-color: #003366;
        color: white;
        border: none;
        padding: 10px 18px;
        margin: 0 8px;
        border-radius: 6px;
        cursor: pointer;
    }
</style>
</head>
<body>

<div class="container">
    <div class="topbar">
        Logged user: <b><%= userLogin %></b> |
        <a href="logout">Logout</a>
    </div>

    <h1>Sporting Activities Searching Application</h1>

    <form action="list" method="POST">
        <select name="type">
            <option value="all_activities" selected>List all sporting activities</option>
            <option value="all_pavillions">List all pavillions</option>
            <option value="free_places">List activities for which there are currently free places</option>
            <option value="cost">List activities for which there are free places costing less than a certain amount</option>
            <option value="text">List activities whose name or description contains a text</option>
            <option value="activity">Print all information about one activity</option>
        </select>

        <label for="text1">Introduce the cost, activity name, text or pavillion name depending on your selection</label>
        <input type="text" name="text1" id="text1">

        <div class="buttons">
            <input type="submit" value="SUBMIT">
            <input type="reset" value="REMOVE">
        </div>
    </form>
</div>

</body>
</html>