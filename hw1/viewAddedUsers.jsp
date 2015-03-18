<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
<head>
	<title>Added Users</title>
</head>
<body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/viewUsers">View Users </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<%
String[] users = (String[])request.getAttribute("added_users");

out.println("<table border=\"1\">\n<tr>\n<th>Added Users to Follow</th>\n</tr>\n");

for (int i = 0; i < users.length; i++) {
	out.println("<tr>\n<td>" + users[i] + "</td>\n<tr>");
}

out.println("</table>");
%>
</body>
</html>