<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="google-site-verification" content="yqEVAdMcIMlxJlEa-IbHZak0JerbA8uLyOVRFUFKLhM" />

    <title>Statistic received from Arduino</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
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
                <#--<li class="active"><a href="#">Home</a></li>-->

                 <li>   <a href="/addData">New entity</a> </li>

            </ul>

        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container" style="margin-top: 30px">



    <div class="starter-template">
        <h1>CO2 Arduino Statistics</h1>

    <#assign  size =  data?size >
    <#if per_page?? >
        <#assign rows_perpage = per_page>
    <#else>
        <#assign rows_perpage = 10>
    </#if>

    <ul class="pagination">
        <#if 0 < page >
            <li> <a href="/page/${page-1}">prev</a></li>
        </#if>

        <#if rows_perpage-1 < size >
            <li><a href="/page/${page+1}">next</a>  </li>
        </#if>

    </ul>

    <#assign elementCount =0>

        <table bordercolor="black" border="1">
            <tr style="background: #84e5ba;">
                <th style="padding:10px; text-align:center;">Date/Time</th>
                <th style="padding:10px; text-align:center;">Posted by</th>
                <th style="padding:10px; text-align:center;">Temperature (*C)</th>
                <th style="padding:10px; text-align:center;">CO2 (ppm)</th>
            </tr>
        <#list data as entity>

            <tr>
                <td style="padding-left:10px; padding-right:10px;">${entity["date"]?datetime?string("dd/MM/yyyy hh:mm:ss a")}</td>
                <td style="padding-left:10px; padding-right:10px;">${entity["device_id"]}</td>
                <td style="text-align:center;">${entity["temperature"]}</td>
                <td style="text-align:center;">${entity["co2"]}</td>
            </tr>

            <#assign elementCount=elementCount+1>
        </#list>
        </table>
        <#--<table class="table" >-->
            <#--<tr style="background: #84e5ba;" >-->
                <#--<th >Date/Time</th>-->
                <#--<th >Posted by</th>-->
                <#--<th >Temperature (*C)</th>-->
                <#--<th >CO2 (ppm)</th>-->
            <#--</tr>-->
        <#--<#list data as entity>-->

            <#--<tr>-->
                <#--<td >${entity["date"]?datetime?string("dd/MM/yyyy hh:mm:ss a")}</td>-->
                <#--<td >${entity["device_name"]}</td>-->
                <#--<td >${entity["temperature"]}</td>-->
                <#--<td >${entity["co2"]}</td>-->
            <#--</tr>-->

            <#--<#assign elementCount=elementCount+1>-->
        <#--</#list>-->
        <#--</table>-->
        <br>

    <ul class="pagination">
        <#if tag??>
            <#if 0 < page >
                <li><a href="/tag/${tag}/${page-1}">prev</a></li>
            </#if>

            <li><a href="/tag/${tag}/${page+1}">next</a></li>
        <#else>
            <#if 0 < page >
                <li> <a href="/page/${page-1}">prev</a></li>
            </#if>

            <#if rows_perpage -1 < elementCount>
                <li><a href="/page/${page+1}">next</a></li>
            </#if>
        </#if>
    </ul>


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
