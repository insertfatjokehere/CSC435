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
 <title> Retrieve Categories </title>
 </head>
 <body>
<%
	String url1 = "https://query.yahooapis.com/v1/public/yql?q=";
	String query = "select name from yahoo.finance.sectors";
	String url2 = "store://datatables.org/";
	String full = url1 + URLEncoder.encode(query, "UTF-8").replace("+", "%20") + 
                   "&format=json&diagnostics=false&env=" + URLEncoder.encode(url2, "UTF-8") + "alltableswithkeys&callback=";
	// String full =  "https://query.yahooapis.com/v1/public/yql?q=select%20name%20from%20yahoo.finance.sectors&format=json&diagnostics=false&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
%>

<a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory.jsp">Select Sector</a>
<hr>
<p> Fetched Yahoo finance Sectors via <a href="<%=full%>" target="_blank">query</a></p>
<p> Select one of the Sectors to contiune to look at the industries </p>
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

    // DEBUG
    // <p> Results " + results.toString() + "</p>\n");
    JSONArray list = results.getJSONArray("sector");
    String check = "Conglomerates";
    for (int i = 0; i < list.length(); i++) {
    JSONObject temp = list.getJSONObject(i);
    
%>
<li><a href="/hw1/selectIndustry.jsp?industry=<%=temp.get("name")%>"><%= temp.get("name") %></a></li>
<%            
    }      
%>

</ol>
<p> Query Time Executed: <%=jsonQuery.get("created")%></p>
</body>
</html>