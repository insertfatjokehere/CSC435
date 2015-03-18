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
	String query = "select industry from yahoo.finance.sectors where name=\"" + request.getParameter("industry") + "\"";
	String url2 = "store://datatables.org/";
	String full = url1 + URLEncoder.encode(query, "UTF-8").replace("+", "%20") + 
                   "&format=json&diagnostics=false&env=" + URLEncoder.encode(url2, "UTF-8") + "alltableswithkeys&callback=";
    // Example with Basic Materials
	// String full =  "https://query.yahooapis.com/v1/public/yql?q=select%20industry%20from%20yahoo.finance.sectors%20where%20name%3D%22Basic%20Materials%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
%>

 <a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/selectCategory.jsp">Select Sector </a> &rArr; <a href="/hw1/selectIndustry.jsp?industry=<%=request.getParameter("industry")%>">Select Industry - <%=request.getParameter("industry")%></a>
 <hr>
<p> Fetched Yahoo Finance Sectors via <a href="<%=full%>" target="_blank">query</a></p>
<p> Select an Industry that are within the sector: <%=request.getParameter("industry")%></p>
<%
    if (!request.getParameter("industry").equals("Conglomerates")) {
        out.println("<ol>");        
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

        for (int i = 0; i < list.length(); i++) {
            JSONObject temp1 = list.getJSONObject(i);
            JSONObject temp2 = temp1.getJSONObject("industry");
            out.println("<li><a href=\"/hw1/selectCompany.jsp?industry=" + request.getParameter("industry") + "&id=" + temp2.get("id") + "\">" + temp2.get("name") + "</a></li>");
        } 

        out.println("</ol>");

    } else {
        out.println("<b>Yahoo! Finance Doesn't have anything stored within the Conglomerates table</b>");
    }
%>
 </body>
</html>