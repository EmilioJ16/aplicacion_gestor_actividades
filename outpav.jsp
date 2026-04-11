<%@ page import="activities.util.HtmlUtil" %>
<tr>
    <td><%= HtmlUtil.e((String) request.getAttribute("name")) %></td>
    <td><%= HtmlUtil.e((String) request.getAttribute("location")) %></td>
</tr>
