<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
<head>
	<title>Added Stocks</title>
</head>
<body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory">Select Sector </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<%
String[] symbol = (String[])request.getAttribute("added_stocks");
out.println("<p>To view all your stocks, go back to your <a href=\"/hw1/dash\">dash</a>");
out.println("<br /> or you can go directly <a href=\"/hw1/viewStocks\">here</a></p>");
out.println("<table border=\"1\">\n<tr>\n<th>Added Companies to watch (by Symbol)</th>\n</tr>\n");
	for (int i = 0; i < symbol.length; i++) {
		out.println("<tr>\n<td>" + symbol[i] + "</td>\n<tr>");
	}
out.println("</table>");
%>
</body>
</html>