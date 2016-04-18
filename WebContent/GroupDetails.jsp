<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<%@ page import="com.lexmark.Group" %> 
<script type="text/javascript">
function delete_group(){
	if (confirm('Are you sure you want to delete this User?')) {
		document.getElementById('delgroup').submit();
	}
}
</script>

<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <div class="row col-sm-6 col-md-12 col-lg-12"> <span class="header_top" style="margin-left:20px;">Select group to change</span>
    <div style="float:right; margin-top:7px;">
      <a type="button" class="btn btn-success" href="addGroup">Add Group</a>
    </div>
    <div class="caption" style="float:right; margin-right:15px; margin-top:7px;">
      <button type="button" href="#myModal" class="btn btn-default" data-toggle="modal">Delete Selected</button>
      <!-- Modal HTML -->
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
      <!-- Modal HTML --> 
    </div>
  </div>
  <div class="bs-example row col-sm-6 col-md-12 col-lg-12">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="active">Group Details</li>
    </ul>
  </div>
  <div class="panel_search panel-default col-sm-4 col-md-6 col-lg-6" style="margin-left:20px; margin-bottom:0px !important;">
    <form id="group-search" action="" method="get">
      <div class="form-group_search has-success col-sm-6 col-md-10 col-lg-10">
        <div class="input-group">
          <div class="input-group-addon"> <span class="glyphicon glyphicon-search"></span> </div>
          <input type="text" id="searchbar" name="q" value="${searchtext}" class="form-control" placeholder="group search">
        </div>
        <span class="help-block">
        	<c:if test="${nosReturned > 0}">
				<span class="acc-title accTitle js-accTrigger gen"><c:out value="${nosReturned}"/> results (<a href="?q="><c:out value=" ${nosTotal}"/> total </a>)</span>
			</c:if>
        </span> </div>
      <div class="form-group_search col-sm-6 col-md-2 col-lg-2">
        <button type="button" value="Search" class="btn btn-success" onClick="document.getElementById('group-search').submit();">SEARCH</button>
      </div>
    </form>
  </div>
  <div class="container-fluid col-sm-4 col-md-12 col-lg-12">
     <ul class="pagination pagination-sm pagination-lg pagination-md gen">
         <li>
         	<c:if test="${currentPage != 1}">
             	<a href="GroupDetails?page=${currentPage - 1}&q=${searchtext}" aria-label="Previous">
             	<span aria-hidden="true">&laquo;</span>
             	</a>
             </c:if>
         </li>
         <li>
         	<c:forEach begin="1" end="${noOfPages}" var="i">
         		<c:choose>
	                <c:when test="${currentPage eq i}">
	                	<c:if test="${i > 2}">
	                		<li class="gen"><a href="GroupDetails?page=${i-2}&q=${searchtext}">${i-2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 1}">
	                		<li class="gen"><a href="GroupDetails?page=${i-1}&q=${searchtext}">${i-1}</a></li>
	                	</c:if>
	                    <li class="active gen_wht"><a href="#">${i}</a></li>
	                    <c:if test="${i > 1}">
	                    	<li class="gen"><a href="GroupDetails?page=${i+1}&q=${searchtext}">${i+1}</a></li>
	                    </c:if>
	                	<c:if test="${i > 2}">
	                		<li class="gen"><a href="GroupDetails?page=${i+2}&q=${searchtext}">${i+2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 3}">
	                		<li class="gen"><a href="GroupDetails?page=${i+3}&q=${searchtext}">${i+3}</a></li>
	                	</c:if>
	                	<c:if test="${i > 4}">
	                		<li class="gen"><a href="GroupDetails?page=${i+4}&q=${searchtext}">${i+4}</a></li>
	                	</c:if>
	                </c:when>
            	</c:choose>
         	</c:forEach>
         </li>
        <c:if test="${currentPage lt noOfPages}">
        	<li>
          	<a href="GroupDetails?page=${currentPage + 1}&q=${searchtext}" aria-label="Next">
            	<span aria-hidden="true">&raquo;</span>
          	</a>
			</li>
		</c:if>
       </ul>
  </div>
  <div class="panel-body">
    <div class="table-responsive col-xm-3 col-md-12 col-lg-12">
      <table id="result_list" class="table table-hover">
        <thead>
        <th scope="col" class="warning gen" width="20px">
        <input type="checkbox" id="selectall">
          </th>
          <th scope="col" class="warning gen"><a style="float:left;" href="#">Group</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a style="float:right; margin-right:20px" href="#">Action</a>
            <div class="clear"></div>
          </th>
        </tr>
        </thead>       
          <c:forEach var="Group" varStatus="loopStatus" items="${groups}">
				<tr class="${loopStatus.index % 2 == 0 ? 'success' : 'row1'}">
					<td><input class="case" name="case" type="checkbox" value="" /></td>
					<td><a class="gen" href="EditGroup?groupid=${Group.id}"><c:out value="${Group.name}"></c:out></a></td>
					<td>
					<table width="15%" style="float:right;">
					<tr><td>
						<form id="delgroup" method="get" action="deleteGroup">
  							<input type="hidden" name="groupid" value="${Group.id}" />
 							<a class="glyphicon glyphicon-remove remove" href="deleteGroup?groupid=${Group.id}" onclick="javascript:delete_group();"></a>
						</form>
					</td>
					<td>
						<form id="editgroup" method="get" action="">
							<input type="hidden" name="edit" value="${Group.id}" />
							<a class="glyphicon glyphicon-pencil edit" href="EditGroup?groupid=${Group.id}" onclick="document.getElementById('editgroup').submit();"></a>
						</form></td>
						</tr>
					</table>
					</td>
				</tr>
			</c:forEach>
        <tbody>
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
<%@ include file="footer.jsp" %>