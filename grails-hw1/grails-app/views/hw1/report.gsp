<!DOCTYPE html>
<html>
<head>
	<title>Generate Report</title>
	<link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.css')}" type="text/css">
	<meta name="viewport" content="width=device-width, initial-scale=1">


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

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-2">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/hw1/dash">${session.user["username"]}'s Home</a>
		</div>

		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
			<ul class="nav navbar-nav">
				%{--<li class="active"><a href="/hw1/dash">Dashboard <span class="sr-only">(current)</span></a></li>--}%
				%{--<li><a href="#">Link</a></li>--}%
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dashboard <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li class="divider"></li>
						<li class="dropdown-header">Stocks</li>
						<li><a href="/hw1/selectCategory"> Add Company / Stocks to Watch </a></li>
						<li><a href="/hw1/viewStocks">View Watched Stocks</a></li>
						<li class="divider"></li>
						<li class="dropdown-header">Users</li>
						<li><a href="/hw1/viewUsers">Follow Other users</li>
						<li><a href="/hw1/getAllFollowers">Manage Your Followings</a></li>
						<li class="divider"></li>
						<li class="dropdown-header">Report</li>
						<li><a href="/hw1/report">Generate Report</a></li>
					</ul>
				</li>
				<li class="active"><a href="/hw1/report">Report <span class="sr-only">(current)</span></a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li><a href="/hw1/logout">Logout</a></li>
			</ul>
		</div>
	</div>
</nav>

 %{--<div style="text-align:left;"><a href="/hw1/dash">Home </a> &rArr; <a href="/hw1/report">Generate Report </a><span style="float:right;"><a href="/hw1/logout.jsp">logout</a></span></div>--}%

<hr>
<div class="container">
<form class="form-horizontal">
	<!-- IDs -->
	<div class="form-group">
		<label for="select" class="col-lg-2 control-label">Stock Symbol</label>
		<div class="col-lg-10">
			<select class="form-control" name="stocksym" id="stocksym">
			<g:each in="${list}" var="stocks">
				<option value="${stocks}"> ${stocks} </option>
			</g:each>
			</select>
		</div>
	</div>

<br>

<!-- Time Span -->


	<div class="form-group">
		<label class="col-lg-2 control-label">Time Span</label>
		<div class="input-group">

			<input class="form-control" type="number" name="time" id="time">
			<span class="input-group-btn">
				<select class="btn btn-default" name="duration" id="duration">
					<option value="d">Day(s)</option>
					<option value="m">Month(s)</option>
					<option value="y">Year(s)</option>
					<option value="my">Maximum</option>
					<option value="Default">Default</option>
				</select>
			</span>
		</div>
	</div>

	<br />

<!-- Chart Type -->

	<div class="form-group">
		<label for="select" class="col-lg-2 control-label">Chart Type</label>
		<div class="col-lg-10">
			<select class="form-control" name="cType" id="cType">
				<option value="l">Line</option>
				<option value="b">Bar</option>
				<option value="c">Candle</option>
			</select>
		</div>
	</div>
<br />

<!-- Chart Scale -->

	<div class="form-group">
		<label for="select" class="col-lg-2 control-label">Chart Scaling</label>
		<div class="col-lg-10">
			<select class="form-control" name="cScale" id="cScale">
				<option value="off">Arithmetic</option>
				<option value="on">Logrithmic</option>
			</select>
		</div>
	</div>

<br />

<input class="btn btn-default btn-lg btn-block" type="button" id="thisButton" value="Generate Chart" onclick="makeImage()">
</form>

	<br />
	<img alt="" src="" class="img-responsive" id="chart" align="middle"/>
</div>

<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="${resource(dir: 'javascripts', file: 'bootstrap.js')}"></script>
</body>
</html>