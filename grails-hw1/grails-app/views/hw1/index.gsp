<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.css')}" type="text/css">

    <title>Welcome</title>
</head>
<body>
<br />
<form id="loginLink" method="post" action="/hw1/loginCheck">
    <input type="hidden" name="sub" value="" />
    <a onclick="document.getElementById('loginLink').submit();" class="btn btn-default btn-lg btn-block">Click to Login</a>
</form>

<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="${resource(dir: 'javascripts', file: 'bootstrap.js')}"></script>
</body>
</html>