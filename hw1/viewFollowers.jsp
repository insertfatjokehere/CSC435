<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<!DOCTYPE html>
<html>
<head>
	<title>View / Manage Followers</title>
</head>
<body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/getAllFollowers">Manage Followers </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<%
	String[] followers = (String[])request.getAttribute("follower_list");

	if (followers != null && followers.length != 0) {
		out.println("<form action=\"removeYourFollowers\" method=\"post\">");
		out.println("<table border=\"1\">\n<tr>\n<th>Followers</th>\n<th>Check to Unfollow</th>\n</tr>\n");

		for (int i = 0; i < followers.length; i++) {
			out.println("<tr>\n<td>" + followers[i] + "</td>\n<td><input type=\"checkbox\" name=\"unfollowUsers\" value=\"" + followers[i] + "\"></td>\n</tr>");
		}
		out.println("<br />");
		out.println("<input type=\"submit\" value=\"Submit\">");
		out.println("</form>");

	} else {
		out.println("<p>There was either an error, or you follow no one yet</p>");
	}
%>
</body>
</html>