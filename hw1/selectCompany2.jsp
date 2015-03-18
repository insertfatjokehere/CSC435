<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html>
 <head>
 <title> Retrieve Companies </title>
 </head>
 <body>
 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory">Select Sector </a> &rArr; <a href="/hw1/selectIndustry?industry=<%=(String)request.getAttribute("selected_sector")%>">Select Industry </a> &rArr; Select Company <span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>
<hr>
<p> Fetched Yahoo finance Companies via <a href="<%=(String)request.getAttribute("query")%>" target="_blank">query</a></p>
<p> Select one of the Industries to contiune to look at the companies </p>
<%
String[] namesTemp = (String[])request.getAttribute("company_name");
String[] symbolTemp = (String[])request.getAttribute("company_symbol");

// String[] names = namesTemp.toArray(new String[namesTemp.size()]);
// String[] symbol = symbolTemp.toArray(new String[symbolTemp.size()]);
// out.println("<ol>");

// Note: name of the input tag is the parameter 
out.println("<form action=\"followStocks\" method=\"post\">");
out.println("<table border=\"1\">\n<tr>\n<th>Comapnies</th>\n<th>Stock Symbol</th>\n</tr>\n");
for (int i = 0; i < namesTemp.length; i++) {
	// out.println("<li> Names: " + namesTemp[i] + " === Symbol " + symbolTemp[i] + "</li>");
	out.println("<tr>\n<td>" + namesTemp[i] + "</td>\n<td>" + symbolTemp[i] + "</td>\n<td><input type=\"checkbox\" name=\"stockSymbol\" value=\"" + symbolTemp[i] + "\"></td>\n</tr>\n");
}
out.println("<br />");
out.println("<input type=\"submit\" value=\"Submit\">");
out.println("</form>");
// out.println("</table>")
%>
 </body>
</html>