<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${resource(dir: 'stylesheets', file: 'bootstrap.css')}" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Login Form</title>
</head>
<body>
<br />
<div class="well bs-component">
<form class="form-horizontal" action="login" method="post">
    <fieldset>
        <legend>Login</legend>
        <div class="form-group">
            <label for="inputEmail" class="col-lg-2 control-label">Username</label>
            <div class="col-lg-10">
                <input type="text" class="form-control" id="inputEmail" name="username" placeholder="Username">
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="col-lg-2 control-label">Password</label>
            <div class="col-lg-10">
                <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="password">
            </div>
        </div>

        <div class="form-group">
            <div class="col-lg-10 col-lg-offset-2">
                <button type="reset" class="btn btn-default">Cancel</button>
                <button type="submit" class="btn btn-primary" value="login">Submit</button>
            </div>
        </div>
    </fieldset>
</form>
</div>

<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="${resource(dir: 'javascripts', file: 'bootstrap.js')}"></script>

</body>
</html>