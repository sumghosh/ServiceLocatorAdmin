<!-- Header Area Starts -->
<!DOCTYPE html>
<html>
<head>
<title>Service Locator Administration</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=-1.0">



<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
<link href="css/dashboard.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="css/drop.css" rel="stylesheet" type="text/css">



</head>

<body>
  <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#" style="margin-right:30px;"><img src="img/logo.jpg" /></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"></li>        
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="clearfix"><a class="navbar-brand" href="http://www.lexmark.com"><img src="img/lexmark_logo.jpg" height="40" /></a></li> 
        <li><a href="Login.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>
    </div>
  </div>
</nav>




<!-- Bootstrap core JavaScript
    ================================================== --> 
<!-- Placed at the end of the document so the pages load faster -->
<script src="js/jquery.min.js"></script> 
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script src="js/moment-with-locales.js"></script>
<!--<script src="js/bootstrap-datetimepicker.js"></script>-->
<script src="js/drop.js"></script>  
<!-- Just to make our placeholder images work. Don't actually copy the next line! --> 
<script src="js/holder.min.js"></script> 
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug --> 
<script src="js/ie10-viewport-bug-workaround.js"></script> 
<!-- jQuery --> 
 
 
<script>
$.noConflict()
$(document).ready(function() {
  $("#accordian h3").click(function() {
    //Slide up all the link lists
    $("#accordian ul ul").slideUp();
    //Slide down the link list below the h3 clicked - only if it's closed
    if(!$(this).next().is(":visible")) {
      $(this).next().slideDown();
    }
  })
})
</script> 
<!-- Header Area Ends -->
<!-- Content Area Starts -->

<div class="col-sm-4 col-md-12 main">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">Error occurred</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">  
            <div class="form-group has-success">
            <span class="edit">You have encountered a system error. Please contact system admin <b>sumghosh@lexmark.com</b></span>
            </div>
          </div>
        </div>
      </div>
    </div>
</div>

<!-- Content Area Ends -->
<!-- Footer Area Starts -->
<div class="col-sm-12 col-md-12 col-lg-12 col-xm-12">
    <div style="float:left; margin-left:10px;">
        <div class="lxk-footer">
    	<div style="margin-top:5px; margin-left:45px; margin-bottom:20px; width:200px;"><b>Lexmark International, Inc.</b> ©2016 All rights reserved.
        </div></div>
    </div>
</div>
<!-- Footer Area Ends -->
</body>
</html>