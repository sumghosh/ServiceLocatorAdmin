<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<script type="text/javascript">
function submit_form(id){
	document.getElementById('type_of_add').value = id;
	document.getElementById('addUser').submit();
}
</script>
<!-- Content Area Starts -->

<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Add User</h1>
  <div class="bs-example">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="gen"><a href="UserDetails">User</a></li>
      <li class="active">Add User</li>
    </ul>
  </div>
  <form id="addUser" class="form-horizontal" data-toggle="validator" method="post" action="saveUser">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">First, enter a username and password. Then, you'll be able to edit more user options.</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_username">Username:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-user"></span> </div>
                  <input type="text" id="username" maxlength="30" class="form-control" name="username" placeholder="Input the username" required>    
                </div><span class="help-block">Required. 30 characters or fewer. Letters, digits and @/./+/-/_ only.</span>   
              </div>
            </div>
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
                  <input name="password2" type="password" id="password2" class="form-control" data-match="#password" data-match-error="Whoops, passwords don't match" placeholder="reenter enter password" required>
                </div>
                <span class="help-block">Enter the same password as above, for verification.</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="caption" style="margin-bottom:20px; float:right;">
     	<input type="hidden" id="type_of_add" name="type_of_add"/>
      	<button type="submit" id="saveadd" class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE AND ADD ANOTHER</button>
      	<button type="submit" id="saveedit"  class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE AND CONTINUE EDITING</button>
      	<button type="submit" id="save" class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE</button>
    </div>
  </form>
</div>
<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>