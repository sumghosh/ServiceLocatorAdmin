<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<%@ page import="com.lexmark.Group" %>
<%@ page import="com.lexmark.UserPermissionDummy" %>
<script type="text/javascript">
function submit_form(id){
	document.getElementById('button_type').value = id;
	document.getElementById('editUserForm').action = 'UpdateUser?userid=${user.id}'
	document.getElementById('editUserForm').submit();
}
</script>
<link rel="stylesheet" type="text/css" href="css/prettify.min.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap-duallistbox.css">
<script src="js/run_prettify.min.js"></script>
<script src="js/jquery.bootstrap-duallistbox.js"></script>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>

<!-- Content Area Starts -->
<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <div class="row col-sm-12 col-md-12 col-lg-12"> <span class="header_top" style="margin-left:20px;">Edit User Details</span>
  </div>
  <div class="bs-example row col-sm-6 col-md-12 col-lg-12">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="gen"><a href="UserDetails">User</a></li>
      <li class="active">Edit User</li>
    </ul>
  </div>
  <form class="form-horizontal" id="editUserForm" data-toggle="validator" action="" method="post">
    <div class="bs-example">
      <div class="panel panel-default rightnav" style="margin-top:100px;">
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group has-default">
              <label class="col-xs-3 col-md-2 control-label1" for="id_username">Username:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-user"></span> </div>
                  <input type="text" id="username" name="username" value="${user.username}" maxlength="30" class="form-control" name="username" placeholder="edit the username" required>    
                </div><span class="help-block">Required. 30 characters or fewer. Letters, digits and @/./+/-/_ only.</span>   
              </div>
            </div>
            <div class="form-group has-default">
              <label class="col-xs-3 col-md-2 control-label" for="id_password1">Password:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <span class="help-block">
                  	algorithm: pbkdf2_sha256 iterations: 10000 salt: j5xoYX****** hash: CyFU5B**************************************
Raw passwords are not stored, so there is no way to see this user's password, but you can change the password using
					<a class="gen" href="#" onClick="document.getElementById('changepassword').submit()">this form</a>.
                  </span> 
                </div>
              </div>
            </div>            
          </div>
        </div>
      </div>
    </div>
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">Personal info</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="id_fname">First name:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-user"></span> </div>
                  <input type="text" id="firstname" name="firstname" value="${user.firstName}" maxlength="30" class="form-control"  placeholder="Input the first name">    
                </div> 
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="id_lname">Last name:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-user"></span> </div>
                  <input type="text" name="lastname" id="lastname" value="${user.lastName}" class="form-control" placeholder="enter last name"> 
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="id_email">Email address:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-exclamation-sign"></span> </div>
                  <input type="text" id="email" name="email" value="${user.email}" class="form-control" placeholder="reenter enter password">
                </div>
                <span class="help-block">Enter the same password as above, for verification.</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">Permissions</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:10px;">
            <div class="form-group has-success">
            <div class="col-xs-9 col-md-9">
                <div class="input-group">
                  	<div class="checkbox" id="id_categories">
                		<label for="id_categories_0">
                		<c:if test="${user.isActive == 1}">
                			<input id="isActive" name="isActive" type="checkbox" checked="checked">
                		</c:if>
                		<c:if test="${user.isActive == 0}">
                			<input id="isActive" name="isActive" type="checkbox">
                		</c:if>
                		<b>Active</b>
                		</label>
                		<span class="help-block">Designates whether this user should be treated as active. Unselect this instead of deleting accounts.</span>
                	</div>
               		<div class="checkbox">
                		<label for="id_categories_1">
                		<c:if test="${user.isStaff == 1}">
                			<input id="isStaff" name="isStaff" type="checkbox" checked="checked">
                		</c:if>
                		<c:if test="${user.isStaff == 0}">
                			<input id="isStaff" name="isStaff" type="checkbox">
                		</c:if>
                		<b>Staff status</b>
                		</label>
                		<span class="help-block">Designates whether the user can log into this admin site.</span>
                	</div>
                	<div class="checkbox">
               		  	<label for="id_categories_2">
               		  	<c:if test="${user.isSuperuser == 1}">
               		  		<input id="isSuperuser" name="isSuperuser" type="checkbox" checked="checked"> 
               		  	</c:if>
               		  	<c:if test="${user.isSuperuser == 0}">
               		  		<input id="isSuperuser" name="isSuperuser" type="checkbox"> 
               		  	</c:if>
               		    <b>Superuser status</b></label>
                		<span class="help-block">Designates that this user has all permissions without explicitly assigning them.
						The groups this user belongs to. A user will get all permissions granted to each of his/her group.</span> </div>                    
             	</div>
            </div>
          </div> 
<!-- 
          <div class="panel panel-success" style="margin-top:20px;">
            <div class="panel-heading">
              <h3 class="panel-title">Groups</h3>
            </div>
        	<div class="panel-body">         
            <div class="form-group has-success" style="margin:20px;">
              <select multiple="multiple" size="15" id="duallistbox_group" name="duallistbox_group">
				<c:forEach var="Group" items="${groups}">
					<option value="${Group.id}"><c:out value="${Group.name}"></c:out></option>
				</c:forEach>
				<c:forEach var="SelectedGroup" items="${selectedGroups}">
					<option value="${SelectedGroup.id}" selected="selected"><c:out value="${SelectedGroup.name}"></c:out></option>
				</c:forEach>
              </select>
              <br>
              <script>
                    $('select[name="duallistbox_group"]').bootstrapDualListbox();
                    $("#editUserForm").submit(function() {
                      	//alert($('[name="duallistbox_group"]').val());
                      	var selectedVal= [];
                    	$('#duallistbox_group :selected').each(function(i, selected){ 
                       		selectedVal[i] = $(selected).text();
                       		alert(i + '::' + selectedVal[i]);
                    	});
                      return false;
                    });
                  </script>
            </div>
          </div>
          </div>
          <div class="panel panel-success" style="margin-top:20px;">
            <div class="panel-heading">
              <h3 class="panel-title">User Permissions</h3>
            </div>
        	<div class="panel-body">         
            <div class="form-group has-success" style="margin:20px;">
              <select multiple="multiple" size="15" id="duallistbox_user_permissions" name="duallistbox_user_permissions">
				<c:forEach var="UserPermissionDummy" varStatus="loopStatus" items="${userPermissions}">
					<option value="${loopStatus.index}"><c:out value="${UserPermissionDummy.appLabel} | ${UserPermissionDummy.contentName} | ${UserPermissionDummy.permissionName}"></c:out></option>
				</c:forEach>
				<c:forEach var="SelectedUserPermissionDummy" varStatus="loopStatus" items="${selectedUserPermissions}">
					<option value="${loopStatus.index}" selected="selected"><c:out value="${SelectedUserPermissionDummy.appLabel} | ${SelectedUserPermissionDummy.contentName} | ${SelectedUserPermissionDummy.permissionName}"></c:out></option>
				</c:forEach>
              </select>
              <br>
              <script>
                    $('select[name="duallistbox_user_permissions"]').bootstrapDualListbox();
                    $("#editUserForm").submit(function() {
                      alert($('[name="duallistbox_user_permissions"]').val());
                    	var selectedValUserPermissions = []; 
                  		$('#duallistbox_user_permissions :selected').each(function(i, selected){ 
                  			selectedValUserPermissions[i] = $(selected).text(); 
                     		alert(selectedValUserPermissions[i]);
                  		});
                      return false;
                    });
                  </script> 
            </div>
          </div>
        </div>
-->        
      </div>
    </div>
  </div>
</div>
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">Important Dates</h3>
        </div>
        <div class="panel-body">
			<!-- DATE TIME TESTING STRTS -->
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_location_type">Last Login:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group date" id="datetimepicker12">
                  <input type="text" class="form-control" id="last_login" name="last_login" value="${user.lastLogin}" required/>
                  <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span> </span> </div>
              </div>
            </div>
            <script type="text/javascript">
	            $(function () {
	                $('#datetimepicker1').datetimepicker();
	            });
            </script>               
            <!-- DATE TIME TESTING ENDS -->            
			<!-- DATE TIME TESTING STRTS -->
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_location_type">Date Joined:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group date" id="datetimepicker22">
                  <input type="text" class="form-control" id="date_joined" name="date_joined" value="${user.dateJoined}" required/>
                  <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span> </span> 
                </div>
              </div>
            </div>
            <script type="text/javascript">
	            $(function () {
	                $('#datetimepicker2').datetimepicker();
	            });
            </script>               
            <!-- DATE TIME TESTING ENDS -->            
            </div>
          </div>
        </div>
    <div class="caption" style="margin-bottom:20px; float:right;">
     	<input type="hidden" id="button_type" name="button_type"/>
      	<button type="submit" id="saveadd" class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE AND ADD ANOTHER</button>
      	<button type="submit" id="saveedit"  class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE AND CONTINUE EDITING</button>
      	<button type="submit" id="save" class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE</button>
    </div>
  </form>
  <form class="form-horizontal" id="changepassword" action="Password.jsp" method="post">
	<input type="hidden" id="userid" name="userid" value="${user.id}"/>
	<input type="hidden" id="username" name="username" value="${user.username}"/>
  </form>					
</div>
<script src="js/jquery.min.js"></script>
<script src="js/moment-with-locales.js"></script>
<script src="js/bootstrap-datetimepicker.js"></script> 
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script> 
<script src="js/drop.js"></script>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
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

<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>