<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<%@ page import="com.lexmark.Company" %> 
<script type="text/javascript">
function delete_company(comp_id){
	if (confirm('Are you sure you want to delete this company?')) {
		//alert(comp_id);
		document.getElementById('delcompany').action = "deleteCompany?compid=" + comp_id;
		document.getElementById('delcompany').submit();
	}
}
function delete_multiple(){
	var comps = "${companies}";
    //comps = comps.replace(/[\[\]\']+/g,''); 
    //comps = comps.replace(/\s/g, ''); //remove spaces
    var arr = comps.split(',');
    //alert(arr.length);
    for(var i=0 ; i<arr.length ; i++){
    	//alert(arr[i]);
    	if (document.getElementById("select_row"+i).checked){
    		//alert('checked');
    		delete_company(document.getElementById("dummycompid"+i).value);
    	} else {
    		//alert('unchecked');
    	}
    }
}
$(document).ready(function () {
    var usedNames = {};
    $("select > option").each(function () {
        if (usedNames[this.value]) {
            $(this).remove();
        } else {
            usedNames[this.value] = this.text;
        }
    });   
    $('select').change(function(){
        var opt = $('select option:selected').text().trim();
        if(opt != 'ALL'){
            var size = $('tr#comprow').length;
            for(var i=0; i<size; i++){
            		//alert($('tr#comprow:eq('+i+') td:eq(5)').text()+':::::'+opt);
                    if($('tr#comprow:eq('+i+') td:eq(5)').text() != opt){
                            $('tr#comprow:eq('+i+')').hide();
                    }else{
                            $('tr#comprow:eq('+i+')').show();
                    }
            }
        } else {
            $('tr#comprow').show();
        }
	});
});
</script>
<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <div class="row col-sm-6 col-md-12 col-lg-12"> <span class="header_top" style="margin-left:20px;">Company Details</span>
    <div style="float:right; margin-top:7px;">
      <a type="button" class="btn btn-success" href="addCompany">Add Company</a>
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
              <p>Select the check boxes which you like to delete or if you want to delete all then select check box at the top</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <a type="button" onClick="javascript:delete_multiple();" class="btn btn-success">Save changes</a>
            </div>
          </div>
        </div>
      </div>
    </div> -->
  </div>
  <div class="bs-example row col-sm-6 col-md-12 col-lg-12">
    <ul class="breadcrumb">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="active">Company Details</li>
    </ul>
  </div>
  <div class="panel_search panel-default col-sm-4 col-md-9 col-lg-9" style="margin-left:20px;">
    <form id="company-search" action="" method="get">
      <div class="form-group_search has-success col-sm-6 col-md-5 col-lg-5">
        <div class="input-group">
          <div class="input-group-addon"cstyle="float:left;"> <span class="glyphicon glyphicon-search"></span> </div>
          <input type="text" id="searchbar" name="q" value="${searchtext}" class="form-control" placeholder="Company Search">
        </div>
        <span class="help-block">
		<c:if test="${nosReturned > 0}">
			<span class="acc-title accTitle js-accTrigger gen"><c:out value="${nosReturned}"/> results (<a href="?q="><c:out value=" ${nosTotal}"/> total </a>)</span>
		</c:if>
		</span>
	  </div>
      <div class="form-group_search col-sm-6 col-md-2 col-lg-2">
        <a type="button" class="btn btn-success" onclick="document.getElementById('company-search').submit();">SEARCH</a>
      </div>
      <div class="form-group_search col-sm-6 col-md-5 col-lg-5">
        <div class="input-group" style="margin-right:10px;">
          <div class="input-group-addon"> <span class="glyphicon glyphicon-filter"></span> </div>
          <select id="countryoption" name="action" class="form-control">
          	<option value="" selected="selected">ALL</option>
          	<c:forEach var="Company" items="${companies}">
          		<option value="${Company.compAddress.country}" >
          			<c:out value="${Company.compAddress.country}"></c:out>
          		</option>
          	</c:forEach>
          </select>
        </div>
        <span class="help-block">Filter by country</span> 
      </div>
    </form>
  </div>
  	<div class="container-fluid col-sm-4 col-md-12 col-lg-12">
        <ul class="pagination pagination-sm pagination-lg pagination-md gen">
            <li>
            	<c:if test="${currentPage != 1}">
                	<a href="CompanyDetails?page=${currentPage - 1}&q=${searchtext}" aria-label="Previous">
                	<span aria-hidden="true">&laquo;</span>
                	</a>
                </c:if>
            </li>
            <li>
            <c:forEach begin="1" end="${noOfPages}" var="i">
            	<c:choose>
                    <c:when test="${currentPage eq i}">
	               	    <c:if test="${i > 2}">
	                		<li class="gen"><a href="CompanyDetails?page=${i-2}&q=${searchtext}">${i-2}</a></li>
	                	</c:if>
	                	<c:if test="${i > 1}">
	                		<li class="gen"><a href="CompanyDetails?page=${i-1}&q=${searchtext}">${i-1}</a></li>
	                	</c:if>
	                    <li class="active gen_wht"><a href="#">${i}</a></li>
	                    <c:if test="${i < noOfPages}">
	                    	<li class="gen"><a href="CompanyDetails?page=${i+1}&q=${searchtext}">${i+1}</a></li>
	                    </c:if>
	                	<c:if test="${i < noOfPages -1}">
	                		<li class="gen"><a href="CompanyDetails?page=${i+2}&q=${searchtext}">${i+2}</a></li>
	                	</c:if>
               		</c:when>
               		<c:otherwise>
               		</c:otherwise>
               	</c:choose>
            </c:forEach>
            </li>
           <c:if test="${currentPage lt noOfPages}">
           	<li>
             	<a href="CompanyDetails?page=${currentPage + 1}&q=${searchtext}" aria-label="Next">
               	<span aria-hidden="true">&raquo;</span>
             	</a>
   			</li>
   			</c:if>
        </ul>
	</div>
  <div class="panel-body">
    <div class="table-responsive">
      <table id="result_list" class="table table-hover" style="overflow-x:auto;">
        <thead>
        <th scope="col" class="warning gen"><input type="checkbox" id="selectall">
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="CompanyDetails?page=${currentPage}&q=${searchtext}&o=1">Name</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="CompanyDetails?page=${currentPage}&q=${searchtext}&o=2">Location id</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="CompanyDetails?page=${currentPage}&q=${searchtext}&o=3">Phone</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><span>Address</span>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen"><a href="CompanyDetails?page=${currentPage}&q=${searchtext}&o=5">Country</a>
            <div class="clear"></div>
          </th>
          <th scope="col" class="warning gen">Action
            <div class="clear"></div>
          </th>
        </tr>
          </thead>
        
        <tbody>
          <c:forEach var="Company" varStatus="loopStatus" items="${companies}">
				<tr id="comprow" class="${loopStatus.index % 2 == 0 ? 'success' : 'row1'}">
					<td><input class="case" id="select_row${loopStatus.index}" name="select_row" type="checkbox"/></td>
					<td><a class="gen" href="EditCompany?compid=${Company.id}"><c:out value="${Company.compName}"></c:out></a></td>
					<td><c:out value="${Company.compLocId}"></c:out></td>
					<td><c:out value="${Company.compPhone}"></c:out></td>
					<td><c:out value="${Company.compAddress.addressLine1
								.concat(' ').concat(Company.compAddress.addressLine2)
								.concat(', ').concat(Company.compAddress.city)
								.concat(', ').concat(Company.compAddress.region)
								}"></c:out></td>
					<td id="${Company.compAddress.country}"><c:out value="${Company.compAddress.country}"></c:out></td>
					
					<td>
					<table width="100%">
					<tr><td>
						<form id="delcompany" method="get" action="deleteCompany?compid=${Company.id}">
  							<input type="hidden" id="compid" name="compid" value="${Company.id}" />
  							<input type="hidden" id="dummycompid${loopStatus.index}" name="dummycompid${loopStatus.index}" value="${Company.id}" />
 							<a class="glyphicon glyphicon-remove remove" href="#" onclick="javascript:delete_company(${Company.id});"></a>
						</form>
					</td>
					<td>
						<form id="editform" method="get" action="EditCompany">
							<input type="hidden" name="edit" value="${Company.id}" />
							<a class="glyphicon glyphicon-pencil edit" href="EditCompany?compid=${Company.id}" onclick="document.getElementById('editform').submit();"></a>
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
