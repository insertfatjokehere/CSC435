<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONTokener"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
 <head>
 <title> Select Industry </title>
 </head>
 <body>
  <%
	String url1 = "https://query.yahooapis.com/v1/public/yql?q=";
	String query = "select * from yahoo.finance.industry where id=\"" + request.getParameter("id") + "\"";
	String url2 = "store://datatables.org/";
	String full = url1 + URLEncoder.encode(query, "UTF-8").replace("+", "%20") + 
                   "&format=json&diagnostics=false&env=" + URLEncoder.encode(url2, "UTF-8") + "alltableswithkeys&callback=";
    // Example with ID = 110
	// String full = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.industry%20where%20id%3D%22110%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
%>
 <a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory.jsp">Select Sector </a> &rArr; <a href="/hw1/selectIndustry.jsp?industry=<%=request.getParameter("industry")%>">Select Industry - <%=request.getParameter("industry")%> </a> &rArr; <a href="/hw1/selectCompany.jsp?industry=<%=request.getParameter("industry")%>&id=<%=request.getParameter("id")%>">Select Companies</a>
 <hr>
<p> Fetched Yahoo Finance Companies (by Industry) via <a href="<%=full%>" target="_blank">query</a></p>
<p> Select an Company that are within the Industry: <%=request.getParameter("industry")%></p>
<ol>
<%
	URL getQuery = new URL(full);
    InputStream input = getQuery.openStream();
    JSONObject getResults = new JSONObject(new JSONTokener(input));

    // JSON PARSER
         
    JSONObject jsonQuery = getResults.getJSONObject("query");
         
    // DEBUG
    // <p> Query: " + jsonQuery.toString() + "</p>\n");
    JSONObject results = jsonQuery.getJSONObject("results");

    JSONObject ind = results.getJSONObject("industry");
    // DEBUG
    // <p> Results " + results.toString() + "</p>\n");
    JSONArray list = ind.getJSONArray("company");

    for (int i = 0; i < list.length(); i++) {
    JSONObject temp1 = list.getJSONObject(i);

%>
<li>Name: <%=temp1.get("name")%> --- Stock Symbol: <%=temp1.get("symbol")%></a></li>
<%	} %>
</ol>
<p> Query Time Executed: <%=jsonQuery.get("created")%></p>
</body>
</html>