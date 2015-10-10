<!DOCTYPE html>
<html>
<head>
	<title>View All Users</title>
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.css')}" type="text/css">
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
                <li class="active"><a href="/hw1/viewUsers">Follow Users <span class="sr-only">(current)</span></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="/hw1/logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>


<div class="container">
    <form class="form-horizontal" action="followNewUser" method="post">
        <input type="submit" class="btn btn-default btn-md btn-block" value="Submit">
        <br />
        <g:each in="${list}" var="user">
            <div class="row">
                <div class="col-sm-12">
                    <div class="input-group">
                        <span class="input-group-addon">
                            <input type="checkbox" name="selectUsers" value="${user}">
                        </span>
                        <input type="text" class="form-control" value="${user}">
                    </div><!-- /input-group -->
                </div><!-- /.col-lg-6 -->
            </div><!-- /.row -->
        </g:each>
    </form>
</div>


<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="${resource(dir: 'javascripts', file: 'bootstrap.js')}"></script>
</body>
</html>