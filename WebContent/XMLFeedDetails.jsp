<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<script type="text/javascript">
function delete_xmlfeed(){
	if (confirm('Are you sure you want to delete this feed?')) {
		//alert('Yes clicked');
		document.getElementById('delxmlfeed').submit();
	}
}
</script>
<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <div class="row col-sm-6 col-md-12 col-lg-12"> <span class="header_top" style="margin-left:20px;">Select xml feed to change</span>
    <div style="float:right; margin-top:7px;">
      <a type="button" class="btn btn-success" href="addXMLFeed">Add xml feed</a>
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
      <li class="active">Xml feeds</li>
    </ul>
  </div>
  	<div class="container-fluid col-sm-4 col-md-12 col-lg-12">
        <ul class="pagination pagination-sm pagination-lg pagination-md gen">
            <li>
            	<c:if test="${currentPage != 1}">
                	<a href="XMLFeedDetails?page=${currentPage - 1}&q=${searchtext}" aria-label="Previous">
                	<span aria-hidden="true">&laquo;</span>
                	</a>
                </c:if>
            </li>
            <li>
         	<c:forEach begin="1" end="${noOfPages}" var="i">
         		<c:choose>
	                <c:when test="${currentPage eq i}">
	                	<c:if test="${i > 2}">
	                		<li class="gen"><a href="XMLFeedDetails?page=${i-2}&q=${searchtext}">${i-2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 1}">
	                		<li class="gen"><a href="XMLFeedDetails?page=${i-1}&q=${searchtext}">${i-1}</a></li>
	                	</c:if>
	                    <li class="active gen_wht"><a href="#">${i}</a></li>
	                    <c:if test="${i < noOfPages}">
	                    	<li class="gen"><a href="XMLFeedDetails?page=${i+1}&q=${searchtext}">${i+1}</a></li>
	                    </c:if>
	                	<c:if test="${i < noOfPages -1}">
	                		<li class="gen"><a href="XMLFeedDetails?page=${i+2}&q=${searchtext}">${i+2}</a></li>
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
             	<a href="XMLFeedDetails?page=${currentPage + 1}&q=${searchtext}" aria-label="Next">
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
          <th scope="col" class="warning gen"><a href="XMLFeedDetails?page=${currentPage}&o=1"">Created on</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="XMLFeedDetails?page=${currentPage}&o=2">Processed on</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="XMLFeedDetails?page=${currentPage}&o=3">Status</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="XMLFeedDetails?page=${currentPage}&o=4">Xml file</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><span>Action</span>
            <div class="clear"></div>
          </th>
        </tr>
          </thead>
        
        <tbody>
          <c:forEach var="XMLFeed" varStatus="loopStatus" items="${feeds}">
				<tr class="${loopStatus.index % 2 == 0 ? 'success' : 'row1'}">
					<td><input class="case" name="case" type="checkbox" value="" /></td>
					<td><a class="gen" href="EditXMLFeed?feedid=${XMLFeed.id}"><c:out value="${XMLFeed.created_on}"></c:out></a></td>
					<td><c:out value="${XMLFeed.processed_on}"></c:out></td>
					<td><c:out value="${XMLFeed.status}"></c:out></td>
					<td><c:out value="${XMLFeed.xml_file}"></c:out></td>
					<td><table width="100%">
					<tr><td>
						<form id="delxmlfeed" method="get" action="deleteXMLFeed">
  							<input type="hidden" name="feedid" value="${XMLFeed.id}" />
 							<a class="glyphicon glyphicon-remove remove" href="deleteXMLFeed?feedid=${XMLFeed.id}" onclick="javascript:delete_xmlfeed();"></a>
						</form>
					</td>
					<td>
						<form id="editxmlfeed" method="get" action="EditXMLFeed">
							<input type="hidden" name="edit" value="${XMLFeed.id}" />
							<a class="glyphicon glyphicon-pencil edit" href="EditXMLFeed?feedid=${XMLFeed.id}" onclick="document.getElementById('editxmlfeed').submit();"></a>
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

</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<!-- Content Area Ends -->
<%@ include file="footer.jsp" %>
