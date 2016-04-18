<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<title>Service Locator Administration</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
<link href="css/dashboard.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="css/drop.css" rel="stylesheet" type="text/css">


</head>

<body>
  <nav class="navbar navbar-inverse navbar-fixed-top">
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
        <li class="active"><a href="dashboard.jsp">HOME</a></li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">AUTHOR ADMINISTRATION <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <!-- <li><a href="GroupDetails" class="gen"> GROUP </a></li> -->
            <li><a href="UserDetails" class="gen"> USER </a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">SERVICE LOCATOR <span class="caret"></span></a>
          <ul class="dropdown-menu">
             <li><a href="CompanyDetails" class="gen"> COMPANIES </a></li>
             <!--  <form id="compform" method="post" action="CompanyDetails">
  					<a href="#" class="gen" onclick="document.getElementById('compform').submit();">COMPANIES</a>
				</form>
             </li> -->
             <li><a href="ProductDetails" class="gen"> PRODUCTS </a></li>
             <li><a href="TechnicianDetails" class="gen"> TECHNICIANS </a></li>
             <li><a href="TrainingDetails" class="gen"> TRAININGS </a></li>
             <li><a href="XMLFeedDetails" class="gen"> XML FEEDS </a></li>
          </ul>
        </li>
<!--        <li><a href="#">Page 2</a></li>
        <li><a href="#">Page 3</a></li>-->
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="clearfix"><a class="navbar-brand" href="http://www.lexmark.com"><img src="img/lexmark_logo.jpg" height="40" /></a></li>
        <c:if test="${not empty loggedInUser}">
        	<li><a><span class="glyphicon glyphicon-user"></span> Welcome ${loggedInUser.firstName}</a></li>
        	<li style="margin-right:30px"><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</c:if>
        <c:if test="${not empty GoogleloggedInUser}">
        	<li><a><span class="glyphicon glyphicon-user"></span> Welcome ${GoogleloggedInUser}</a></li>
        	<li style="margin-right:30px"><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</c:if>
		<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    		<li style="margin-right:30px"><a href="login"><span class="glyphicon glyphicon-log-out"></span> Login</a></li>
		</c:if>
      </ul>
    </div>
  </div>
</nav>


<!-- Bootstrap core JavaScript
    ================================================== --> 
<!-- Placed at the end of the document so the pages load faster --> 
<script src="js/jquery.min.js"></script> 
<script src="js/jquery-1.10.2.min.js"></script> 
<script src="js/bootstrap.min.js"></script> 
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug --> 
<script src="js/ie10-viewport-bug-workaround.js"></script> 
<!-- jQuery --> 

<script language="javascript">
$(function(){
 
    // add multiple select / deselect functionality
    $("#selectall").click(function () {
          $('.case').attr('checked', this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".case").click(function(){
 
        if($(".case").length == $(".case:checked").length) {
            $("#selectall").attr("checked", "checked");
        } else {
            $("#selectall").removeAttr("checked");
        }
 
    });
});
</script>
</body>
</html>