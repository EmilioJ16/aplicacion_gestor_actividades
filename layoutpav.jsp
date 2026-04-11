<%@ page errorPage="errorHandling.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Spoting Activities Searching Application</title>
</head>
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
<body>
<h1> List of all the pavillions </h1>
<table>
    <tr>
        <td><b>PAVILLION NAME</b></td>
        <td><b>LOCATION</b></td>
    </tr>
<p>