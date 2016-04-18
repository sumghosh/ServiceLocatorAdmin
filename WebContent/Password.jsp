<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<script type="text/javascript">
function submit_form(){
	document.getElementById('ChangePassword').submit();
}
</script>
<!-- Content Area Starts -->

<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Change password</h1>
  <div class="bs-example">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="gen"><a href="UserDetails">User</a></li>
      <li class="gen"><a href="EditUser?userid=${param.userid}"><c:out value="${param.username}"></c:out></a></li>
      <li class="active">Change password</li>
    </ul>
  </div>
  <form id="ChangePassword" class="form-horizontal" data-toggle="validator" method="post" action="ChangePassword">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">Change password for the user <c:out value="${param.username}"></c:out></h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_password1">Password:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-lock"></span> </div>
                  <input name="password" type="password" id="password" class="form-control" placeholder="enter password" required> 
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_password2">Password confirmation:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-lock"></span> </div>
                  <input name="password2" type="password" id="password2" class="form-control" placeholder="reenter enter password" required>
                </div>
                <span class="help-block">Enter the same password as above, for verification.</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="caption" style="margin-bottom:20px; float:right;">
    	<input type="hidden" name="userid" value="${param.userid}">
      	<button type="submit" id="save" class="btn btn-primary disabled" onClick="javascript:submit_form();">SAVE</button>
    </div>
  </form>
</div>
<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>