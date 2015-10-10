<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.css')}" type="text/css">
	<title>Login Successful</title>
</head>
<body>

<br />

<div class="container">
<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title">Login Successful</h3>
    </div>
    <div class="panel-body">
        <form id="loginLink" method="post" action="/hw1/dash">
            <input type="hidden" name="sub" value="" />
            <a onclick="document.getElementById('loginLink').submit();"> <u> Click to continue to dashboard </u> </a>
        </form>
    </div>
</div>
</div>

<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="${resource(dir: 'javascripts', file: 'bootstrap.js')}"></script>
</body>
</html>