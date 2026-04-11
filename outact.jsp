<%@ page import="activities.util.HtmlUtil" %>
<tr>
    <td><%= request.getAttribute("id") %></td>
    <td><%= HtmlUtil.e((String) request.getAttribute("name")) %></td>
    <td><%= HtmlUtil.e((String) request.getAttribute("description")) %></td>
    <td><%= HtmlUtil.e((String) request.getAttribute("initial")) %></td>
    <td><%= request.getAttribute("cost") %></td>
    <td><%= HtmlUtil.e((String) request.getAttribute("pavname")) %></td>
    <td><%= request.getAttribute("total") %></td>
    <td><%= request.getAttribute("occupied") %></td>
</tr>
