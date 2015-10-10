<!DOCTYPE html>
<html>
 <head>
 <title> Retrieve Categories </title>
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
     <li class="active"><a href="/hw1/selectCategory">Select Sector <span class="sr-only">(current)</span></a></li>
    </ul>

    <ul class="nav navbar-nav navbar-right">
     <li><a href="/hw1/logout">Logout</a></li>
    </ul>
   </div>
  </div>
 </nav>


 <div class="container">
     <h3 align="center">Fetched Yahoo finance Sectors <br />
         Select one of the Sectors to contiune to look at the industries</h3>
     <br />
     <ul class="list-group">

             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Basic Materials"> Basic Materials </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Consumer Goods"> Consumer Goods </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Financial"> Financial </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Healthcare"> Healthcare </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Industrial Goods"> Industrial Goods </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Services"> Services </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Technology"> Technology </a> </li>
             <li class="list-group-item"> <a href="/hw1/selectIndustry?industry=Utilities"> Utilities </a> </li>

     </ul>
 </div>

 <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
 <script src="${resource(dir: 'javascripts', file: 'bootstrap.js')}"></script>
 </body>
</html>