<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
 <head>
 <title> Retrieve Industries </title>
 </head>
 <body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory">Select Sector </a> &rArr; <a href="/hw1/selectIndustry?industry=<%=(String)request.getAttribute("selected_sector")%>">Select Industry</a> <span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<p> Fetched Yahoo finance Industry via <a href="<%=(String)request.getAttribute("query")%>" target="_blank">query</a></p>
<p> Select one of the Industries to contiune to look at the companies </p>

<%
String[] names = (String[])request.getAttribute("industry_name");
int[] id = (int[])request.getAttribute("industry_ID");
out.println("<ol>");
for (int i = 0; i < names.length; i++) {
	out.println("<li><a href=\"/hw1/selectCompany?sector=" + (String)request.getAttribute("selected_sector") + "&industry=" + names[i] + "&id=" + id[i] + "\">" + names[i] + "</a></li>");
}
out.println("</ol>");
%>
 </body>
</html>