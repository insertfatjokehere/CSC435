<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
<head>
	<title>View Stocks</title>
</head>
<body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/viewStocks">View Stocks </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<%
String[] stocks = (String[])request.getAttribute("view_stocks");

	if (stocks != null && stocks.length != 0) {
		out.println("<table border=\"1\">\n<tr>\n<th>Watched Companies (by Symbol)</th>\n</tr>\n");

		for (int i = 0; i < stocks.length; i++) {
			out.println("<tr>\n<td>" + stocks[i] + "</td>\n<tr>");
		}
		out.println("</table>");
	} else {
		out.println("<p>No stocks were able to be seen at this moment</p>");
	}
%>
</body>
</html>