<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>

<!DOCTYPE html>
<html>
<head>
	<title>View Stocks</title>

	<script type="text/javascript">
		// https://code.google.com/p/yahoo-finance-managed/wiki/miscapiImageDownload
		function makeImage() {
			var base = "http://chart.finance.yahoo.com/z?s=";
			// id
			var selectBox = document.getElementById("stocksym");
    		var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    		base = base + selectedValue;
    		base = base + "&z=l"; // make image large

    		// time span
    		selectBox = document.getElementById("duration");
    		selectedValue = selectBox.options[selectBox.selectedIndex].value;

    		if (selectedValue != "Default") {
    			if (selectedValue == "my") {
    				base = base + "&t=my";
    			} else {
    				var time = document.getElementById("time").value;
    				base = base + "&t=" + time + selectedValue;
    			}
    		}

    		// Chart Type
			var selectBox = document.getElementById("cType");
    		var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    		base = base + "&q=" + selectedValue;

    		// Chart Scaling
			var selectBox = document.getElementById("cScale");
    		var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    		base = base + "&l=" + selectedValue;

    		// add image
    		document.getElementById("chart").src = base;
    		// alert(base);
		}

	</script>

</head>
<body>

 <div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/report">Generate Report </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>

<hr>
<%
String[] stocks = (String[])request.getAttribute("view_stocks");

	if (stocks != null && stocks.length != 0) {
		out.println("<form>");
		// ID
		out.println("Stock Symbol <select name=\"stocksym\" id=\"stocksym\">");
		for (int i = 0; i < stocks.length; i++) {
			out.println("<option value=\"" + stocks[i] + "\">" + stocks[i] + "</option>");
		}
		out.println("</select>\n<br>");
		
		// Time Span
		out.println("Time Span <input type=\"number\"name=\"time\" id=\"time\"> <select name=\"duration\" id =\"duration\">");
		out.println("<option value=\"d\">Day(s)</option>");
		out.println("<option value=\"m\">Month(s)</option");
		out.println("<option value=\"y\">Year(s)</option>");
		out.println("<option value=\"my\">Maximum</option>");
		out.println("<option vaule=\"Defualt\">Default</option>\n</select>\n<br />");
		
		// Chart Type
		out.println("Chart Type <select name=\"cType\" id=\"cType\">");
		out.println("<option value=\"l\">Line</option>");
		out.println("<option value=\"b\">Bar</option>");
		out.println("<option value=\"c\">Candle</option>");
		out.println("</select>\n<br />");

		// Chart Type
		out.println("Chart Scaling <select name=\"cScale\" id=\"cScale\">");
		out.println("<option value=\"off\">Arithmetic</option>");
		out.println("<option value=\"on\">Logrithmic</option>");
		out.println("</select>\n<br />");


		out.println("<input type=\"button\" id=\"thisButton\" value=\"Generate Chart\" onclick=\"makeImage()\">");
		out.println("</form>");

	} else {
		out.println("<p>No stocks were able to be seen at this moment</p>");
	}
%>
<br />
<img alt="" src="" id="chart"/>

</body>
</html>