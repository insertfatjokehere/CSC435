<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
<head>
	<title>View All Users</title>
</head>
<body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/viewUsers">View All Users </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<%
String[] users = (String[])request.getAttribute("all_users");
	if (users.length != 0 && users != null) {
		out.println("<form action=\"followNewUser\" method=\"post\">");
		out.println("<table border=\"1\">\n<tr>\n<th>Watched Companies (by Symbol)</th>\n<th>Check to Follow</th>\n</tr>\n");
		for (int i = 0; i < users.length; i++){
			out.println("<tr>\n<td>" + users[i] + "</td> <td><input type=\"checkbox\" name=\"selectUsers\" value=\"" + users[i] + "\"></td>\n</tr>\n");
		}
		out.println("<br />");
		out.println("<input type=\"submit\" value=\"Submit\">");
		out.println("</form>");
	} else {
		out.println("<p>No users were able to be seen at this moment</p>");
	}
%>
</body>
</html>