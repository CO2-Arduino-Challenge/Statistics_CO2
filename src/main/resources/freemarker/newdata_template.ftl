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
    <title>Create a new post</title>

    <!-- Custom styles for this template -->
    <link href="starter-template.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
    </div>

</div><!-- /.container -->
<footer class="footer">
    <div class="container">
        <p class="text-muted" style="text-align: center">Denis Kovalenko 2015</p>
    </div>
</footer>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
