<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<%@ page import="com.lexmark.User" %> 
<script type="text/javascript">
function delete_user(){
	if (confirm('Are you sure you want to delete this User?')) {
		//alert('Yes clicked');
		document.getElementById('deluser').submit();
	}
}
</script>
<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <div class="row col-sm-6 col-md-12 col-lg-12"> <span class="header_top" style="margin-left:20px;">User Details</span>
    <div style="float:right; margin-top:7px;">
      <a type="button" class="btn btn-success" href="addUser">Add User</a>
    </div>
<!--     <div class="caption" style="float:right; margin-right:15px; margin-top:7px;">
      <button type="button" href="#myModal" class="btn btn-default" data-toggle="modal">Delete Selected</button>
      <div id="myModal" class="modal fade">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title">Confirmation</h4>
            </div>
            <div class="modal-body">
              <p>Select the check box which you like to delete or if you want all to be deleted then select check box at the top</p>
              <p class="text-warning"><small>If you don't delete, all data will be lost.</small></p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-success">Save changes</button>
            </div>
          </div>
        </div>
      </div>
    </div>-->
  </div>
  <div class="bs-example row col-sm-6 col-md-12 col-lg-12">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="active">User Details</li>
    </ul>
  </div>
  <div class="panel_search panel-default col-sm-4 col-md-11 col-lg-11" style="margin-left:20px;">
    <form id="user-search" action="" method="get">
        <div class="form-group_search has-success col-sm-3 col-md-4 col-lg-4">
        <div class="input-group">
          <div class="input-group-addon"> <span class="glyphicon glyphicon-search"></span> </div>
          <input type="text" id="searchbar" name="q" value="${searchtext}" class="form-control" placeholder="user search">
        </div>
        <span class="help-block">
        	<c:if test="${nosReturned > 0}">
				<span class="acc-title accTitle js-accTrigger gen"><c:out value="${nosReturned}"/> results (<a href="?q="><c:out value=" ${nosTotal}"/> total </a>)</span>
			</c:if>
        </span> </div>
      <div class="form-group_search col-sm-6 col-md-2 col-lg-2">
        <button type="button" value="Search" class="btn btn-success" onClick="document.getElementById('user-search').submit();">SEARCH</button>
      </div>
    <div class="form-group_search col-sm-2 col-md-3 col-lg-3">
        <div class="input-group" style="margin-right:10px;">
          <div class="input-group-addon"> <span class="glyphicon glyphicon-filter"></span> </div>
          <select id="mySelect" class="form-control">
            <option value="SS">STAFF STATUS</option>
            <option value="SUPS">SUPERUSER STATUS</option>
            <option value="ACT">ACTIVE</option>
          </select>
        </div>
        <span class="help-block">Filter by user status</span> </div>      
      <div class="form-group_search col-sm-2 col-md-3 col-lg-3">
        <div class="input-group" style="margin-right:10px;">
          <div class="input-group-addon"> <span class="glyphicon glyphicon-filter"></span> </div>
          <select id="mySelect1" class="form-control" >
            <option>ALL</option>
            <option value="Y">YES</option>
            <option value="N">NO</option>
          </select>
          <!--  <select id="mySelect2" disabled="disabled" class="form-control" >
            <option>ALL</option>
            <option value="DL">DEALER LOCATOR</option>
            <option value="LCCP">LCCP</option>
            <option value="AUT">AUTH</option>
            <option value="PJ">PICKLE JAR</option>
            <option value="OSL">OLD SERVICE LOCATOR</option>
            <option value="SL">SERVICE LOCATOR</option>
          </select> -->
        </div>
        <span class="help-block">Filter by selection status</span> </div>
        <script> 
        function check(elem) {
            document.getElementById('mySelect1').disabled = !elem.selectedIndex;
			document.getElementById('mySelect2').disabled = !elem.selectedIndex;
        }
        </script>
    </form>
  </div>
  <div class="container-fluid col-sm-4 col-md-12 col-lg-12">
     <ul class="pagination pagination-sm pagination-lg pagination-md gen">
         <li>
         	<c:if test="${currentPage != 1}">
             	<a href="UserDetails?page=${currentPage - 1}&q=${searchtext}" aria-label="Previous">
             	<span aria-hidden="true">&laquo;</span>
             	</a>
             </c:if>
         </li>
         <li>
         	<c:forEach begin="1" end="${noOfPages}" var="i">
         		<c:choose>
	                <c:when test="${currentPage eq i}">
	                	<c:if test="${i > 2}">
	                		<li class="gen"><a href="UserDetails?page=${i-2}&q=${searchtext}">${i-2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 1}">
	                		<li class="gen"><a href="UserDetails?page=${i-1}&q=${searchtext}">${i-1}</a></li>
	                	</c:if>
	                    <li class="active gen_wht"><a href="#">${i}</a></li>
	                    <c:if test="${i > 1}">
	                    	<li class="gen"><a href="UserDetails?page=${i+1}&q=${searchtext}">${i+1}</a></li>
	                    </c:if>
	                	<c:if test="${i > 2}">
	                		<li class="gen"><a href="UserDetails?page=${i+2}&q=${searchtext}">${i+2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 3}">
	                		<li class="gen"><a href="UserDetails?page=${i+3}&q=${searchtext}">${i+3}</a></li>
	                	</c:if>
	                	<c:if test="${i > 4}">
	                		<li class="gen"><a href="UserDetails?page=${i+4}&q=${searchtext}">${i+4}</a></li>
	                	</c:if>
	                </c:when>
	         		<c:otherwise>
	                     <!--  <li class="gen"><a href="UserDetails?page=${i}&q=${searchtext}">${i}</a></li>-->
	                </c:otherwise>
            	</c:choose>
         	</c:forEach>
         </li>
        <c:if test="${currentPage lt noOfPages}">
        	<li>
          	<a href="UserDetails?page=${currentPage + 1}&q=${searchtext}" aria-label="Next">
            	<span aria-hidden="true">&raquo;</span>
          	</a>
			</li>
		</c:if>
       </ul>
  </div>
  <div class="panel-body">
    <div class="table-responsive ">
      <table id="result_list" class="table table-hover">
        <thead>
        <th scope="col" class="warning gen"><input type="checkbox" id="selectall">
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="UserDetails?page=${currentPage}&q=${searchtext}&o=1">Username</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="UserDetails?page=${currentPage}&q=${searchtext}&o=2">Email address</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="UserDetails?page=${currentPage}&q=${searchtext}&o=3">First name</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="UserDetails?page=${currentPage}&q=${searchtext}&o=4">Last name</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="UserDetails?page=${currentPage}&q=${searchtext}&o=5">Staff status</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><span>Action</span>
            <div class="clear"></div>
          </th>
        </tr>
        </thead>
        
        <tbody>
          <c:forEach var="User" varStatus="loopStatus" items="${users}">
				<tr class="${loopStatus.index % 2 == 0 ? 'success' : 'row1'}">
					<td><input class="case" name="case" type="checkbox" value="" /></td>
					<td><a class="gen" href="EditUser?userid=${User.id}"><c:out value="${User.username}"></c:out></a></td>
					<td><c:out value="${User.email}"></c:out></td>
					<td><c:out value="${User.firstName}"></c:out></td>
					<td><c:out value="${User.lastName}"></c:out></td>
					<td>
						<c:choose>
							<c:when test="${User.isStaff == '1'}">
								<a class="glyphicon glyphicon-check gen"></a>
							</c:when>
							<c:otherwise>
								<a class="glyphicon glyphicon-unchecked gen"></a>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					<table width="100%">
					<tr><td>
						<form id="deluser" method="get" action="deleteUser">
  							<input type="hidden" name="techid" value="${User.id}" />
 							<a class="glyphicon glyphicon-remove remove" href="deleteUser?userid=${User.id}" onclick="javascript:delete_user();"></a>
						</form>
					</td>
					<td>
						<form id="edituser" method="get" action="EditUser">
							<input type="hidden" name="edit" value="${User.id}" />
							<a class="glyphicon glyphicon-pencil edit" href="EditUser?userid=${User.id}" onclick="document.getElementById('edituser').submit();"></a>
						</form></td>
						</tr>
					</table>
					</td>
				</tr>
			</c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>

<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
