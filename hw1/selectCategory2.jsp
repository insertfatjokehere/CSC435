<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
 <head>
 <title> Retrieve Categories </title>
 </head>
 <body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory">Select Sector</a> <span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<p> Fetched Yahoo finance Sectors via <a href="<%=(String)request.getAttribute("query")%>" target="_blank">query</a></p>
<p> Select one of the Sectors to contiune to look at the industries </p>

<%
String[] names = (String[])request.getAttribute("sector_names");
out.println("<ol>");
for (int i = 0; i < names.length; i++) {
	if (!names[i].equals("Conglomerates")) {
		out.println("<li><a href=\"/hw1/selectIndustry?industry=" + names[i] + "\">" + names[i] + "</a></li>");
	}
}
out.println("</ol>");
%>
 </body>
</html>