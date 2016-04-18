<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<%@ page import="com.lexmark.Product" %> 
<script type="text/javascript">
function delete_product(){
	if (confirm('Are you sure you want to delete this Product?')) {
		//alert('yes clicked');
		document.getElementById('delprod').submit();
	}
}
</script>
<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <div class="row col-sm-6 col-md-12 col-lg-12"> <span class="header_top" style="margin-left:20px;">Product details</span>
    <div style="float:right; margin-top:7px;">
      <a type="button" class="btn btn-success" href="AddProduct">Add Product</a>
    </div>
<!--   <div class="caption" style="float:right; margin-right:15px; margin-top:7px;">
      <button type="button" href="#myModal" class="btn btn-default" data-toggle="modal">Delete Selected</button>
      <div id="myModal" class="modal fade">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              <h4 class="modal-title">Confirmation</h4>
            </div>
            <div class="modal-body">
              <p>Select the checkbox which you like to delete or if you want all to be deleted then select check box at the top</p>
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
      <li class="active">Product Details</li>
    </ul>
  </div>
  <div class="panel_search panel-default col-sm-4 col-md-9 col-lg-9" style="margin-left:20px;">
    <form id="prodsearch" action="" method="get">
      <div class="form-group_search has-success col-sm-6 col-md-5 col-lg-5">
        <div class="input-group">
          <div class="input-group-addon"cstyle="float:left;"> <span class="glyphicon glyphicon-search"></span> </div>
          <input type="text" id="searchbar" name="q" value="${searchtext}" class="form-control" placeholder="Product Search">
        </div>
        <span class="help-block">		
        <c:if test="${nosReturned > 0}">
			<span class="acc-title accTitle js-accTrigger gen"><c:out value="${nosReturned}"/> results (<a href="?q="><c:out value=" ${nosTotal}"/> total </a>)</span>
		</c:if>
		</span> </div>
      <div class="form-group_search col-sm-6 col-md-2 col-lg-2">
        <a type="button" value="Search" class="btn btn-success" onclick="document.getElementById('prodsearch').submit();">SEARCH</a>
      </div>
      <div class="form-group_search col-sm-6 col-md-5 col-lg-5">
        <div class="input-group" style="margin-right:10px;">
          <div class="input-group-addon"> <span class="glyphicon glyphicon-filter"></span> </div>
          <select name="action" class="form-control">
            <option value="" selected="selected">ALL</option>
            <option value="Y" selected="selected">YES</option>
            <option value="N">NO</option>
          </select>
        </div>
        <span class="help-block">Filter by active flag</span> </div>
    </form>
  </div>
  	<div class="container-fluid col-sm-4 col-md-12 col-lg-12">
        <ul class="pagination pagination-sm pagination-lg pagination-md gen">
            <li>
            	<c:if test="${currentPage != 1}">
                	<a href="ProductDetails?page=${currentPage - 1}&q=${searchtext}" aria-label="Previous">
                	<span aria-hidden="true">&laquo;</span>
                	</a>
                </c:if>
            </li>
            <li>
         	<c:forEach begin="1" end="${noOfPages}" var="i">
         		<c:choose>
	                <c:when test="${currentPage eq i}">
	                	<c:if test="${i > 2}">
	                		<li class="gen"><a href="ProductDetails?page=${i-2}&q=${searchtext}">${i-2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 1}">
	                		<li class="gen"><a href="ProductDetails?page=${i-1}&q=${searchtext}">${i-1}</a></li>
	                	</c:if>
	                    <li class="active gen_wht"><a href="#">${i}</a></li>
	                    <c:if test="${i < noOfPages}">
	                    	<li class="gen"><a href="ProductDetails?page=${i+1}&q=${searchtext}">${i+1}</a></li>
	                    </c:if>
	                	<c:if test="${i < noOfPages -1}">
	                		<li class="gen"><a href="ProductDetails?page=${i+2}&q=${searchtext}">${i+2}</a></li>
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
             	<a href="ProductDetails?page=${currentPage + 1}&q=${searchtext}" aria-label="Next">
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
          <th scope="col" class="warning gen"><a href="ProductDetails?page=${currentPage}&q=${searchtext}&o=1">Name</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="ProductDetails?page=${currentPage}&q=${searchtext}&o=2">Slug</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="ProductDetails?page=${currentPage}&q=${searchtext}&o=3">Is active</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><span>Category</span>
            <div class="clear"></div>
          </th>
           <th scope="col" class="warning gen"><span>Action</span>
            <div class="clear"></div>
          </th>
        </tr>
          </thead>
        
        <tbody>
          	<c:forEach var="Product" varStatus="loopStatus" items="${products}">
				<tr class="${loopStatus.index % 2 == 0 ? 'success' : 'row1'}">
					<td><input class="case" name="case" type="checkbox" value="" /></td>
					<td><a class="gen" href="EditProduct?prodid=${Product.id}"><c:out value="${Product.name}"></c:out></a></td>
					<td><c:out value="${Product.slug}"></c:out></td>
					<!-- <td><a class="glyphicon glyphicon-check gen"></a><c:out value="${Product.active}"></c:out></td> -->
					<td>
						<c:choose>
							<c:when test="${Product.active == '1'}">
								<a class="glyphicon glyphicon-check gen"></a>
							</c:when>
							<c:otherwise>
								<a class="glyphicon glyphicon-unchecked gen"></a>
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>
						<c:choose>
    						<c:when test="${Product.category =='1'}">
        						Color Laser 
    						</c:when>
    						<c:when test="${Product.category =='2'}">
        						Mono Laser 
    						</c:when>
    						<c:when test="${Product.category =='4'}">
        						MFP 
    						</c:when>
    						<c:when test="${Product.category =='8'}">
        						Dot Matrix 
    						</c:when>
    						<c:when test="${Product.category =='16'}">
        						Inkjet 
    						</c:when>  
    						<c:otherwise>
					        	Others 
					        	<br />
					    	</c:otherwise>
						</c:choose>
					</td>
					<td>
						<table width="50%">
						<tr><td>
						<form id="delprod" method="get" action="DeleteProduct">
							<input type="hidden" name="prodid" value="${Product.id}" />
							<a class="glyphicon glyphicon-remove remove" href="DeleteProduct?prodid=${Product.id}" onclick="javascript:delete_product();"></a>
						</form>
						</td>
						<td>
						<form id="editprod" method="get" action="EditProduct">
							<input type="hidden" name="prodid" value="${Product.id}" />
							<a class="glyphicon glyphicon-pencil edit" href="EditProduct?prodid=${Product.id}" onclick="document.getElementById('editprod').submit();"></a>
						</form>
						</td></tr>
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
<%@ include file="footer.jsp" %>