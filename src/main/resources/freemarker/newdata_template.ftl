<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <title>Create a new test entity</title>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Statistics home page</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <#--<#if username??>-->
                 <#--<li class="active">  <a href="/logout">Logout</a> </u> </li>-->
                <#--<#else>-->
                 <#--<li>  <a href="/login">Login</a> </u> </li>-->
                <#--</#if>-->
            </ul>
            <#--<form action="/search" method="POST" class="navbar-form navbar-right">-->
                <#--<input name="tag" type="text" class="form-control" placeholder="Search...">-->
            <#--</form>-->
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container" style="margin-top: 50px">

    <div class="starter-template">
        <form action="/addData" method="POST">
        ${errors!""}
            <h2>Device_name</h2>
            <input class="form-control" type="text" name="device_name" width="120"><br>

            <h2>Temperature</h2>
            <input class="form-control" type="text" name="temperature" width="120"><br>

            <h2>Co2 ppm</h2>
            <input class="form-control" type="text" name="co2" width="120" ><br>
            <p>
                <input type="submit" class="btn btn-primary" value="Submit">
		</form>
    </div>

</div><!-- /.container -->
<footer class="footer">
    <div class="container">
             <p class="text-muted" style="text-align: center">PZ-12-1 2015</p>
	<p class="text-muted" style="text-align: center">Tatarchenko, Sheremet, Kovalenko, Pleshkanovkiy, Sobol, Sych,</p>
    </div>
</footer>

</body>
</html>
