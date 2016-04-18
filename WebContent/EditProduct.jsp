<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<script type="text/javascript">
function submit_form(id){
	document.getElementById('button_type').value = id;
	document.getElementById('editProd').action = 'UpdateProduct?prodid=${product.id}';
	document.getElementById('editProd').submit();
}
</script>
<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Add Products</h1>
  <div class="bs-example gen">
    <ul class="breadcrumb ">
      <li><a href="dashboard.jsp">Home</a></li>
      <li><a href="ProductDetails">Products</a></li>
      <li class="active">Edit Product</li>
    </ul>
  </div>
  <form id="editProd" class="form-horizontal" data-toggle="validator" method="post" action="">
        <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">The form below contains adding a new product to the system</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Name:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-user"></span> </div>
                  <input type="text" id="name" name="name" value ="${product.name}" class="form-control" placeholder="Input product name" required>
                </div><span class="help-block">Full marketing name, e.g., "Lexmark C540dw"</span>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="id_slug">Slug:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-folder-close"></span> </div>
                  <input type="text" id="slug" name="slug" value ="${product.slug}" class="form-control" placeholder="Slug">
                </div>
                <span class="help-block">Unique model lower case name, e.g., "c540dw". If left blank slug will be generated automatically.</span> </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Categories:</label>
            <div class="col-xs-9 col-md-9" style="margin:10px;">
                <div class="input-group">
                  	<div class="checkbox" id="id_categories">
                  		<c:choose>
						    <c:when test="${product.category == 1 || product.category == 5}">
						        <label for="id_categories_0"><input id="category_1" name="category_1" type="checkbox" checked="checked" value="1"> Color laser</label>
						    </c:when>    
						    <c:otherwise>
						        <label for="id_categories_0"><input id="category_1" name="category_1" type="checkbox" value="1"> Color laser</label>
						    </c:otherwise>
						</c:choose>
                	</div>
               		<div class="checkbox">
               			<c:choose>
						    <c:when test="${product.category == 2 || product.category == 6}">
						        <label for="id_categories_1"><input id="category_2" name="category_2" type="checkbox" checked="checked" value="2"> Mono laser</label>
						    </c:when>    
						    <c:otherwise>
						        <label for="id_categories_1"><input id="category_2" name="category_2" type="checkbox" value="2"> Mono laser</label>
						    </c:otherwise>
						</c:choose>
                	</div>
                	<div class="checkbox">
                		<c:choose>
						    <c:when test="${product.category == 4 || product.category == 5 || product.category == 6}">
						        <label for="id_categories_2"><input id="category_4" name="category_4" type="checkbox" checked="checked" value="4"> Multifunction</label>
						    </c:when>    
						    <c:otherwise>
						        <label for="id_categories_2"><input id="category_4" name="category_4" type="checkbox" value="4"> Multifunction</label>
						    </c:otherwise>
						</c:choose>
                	</div>
                    <div class="checkbox">
                    	<c:choose>
						    <c:when test="${product.category == 8}">
						        <label for="id_categories_3"><input id="category_8" name="category_8" type="checkbox" checked="checked" value="8"> Dot matrix</label>
						    </c:when>    
						    <c:otherwise>
						        <label for="id_categories_3"><input id="category_8" name="category_8" type="checkbox" value="8"> Dot matrix</label>
						    </c:otherwise>
						</c:choose>
                	</div>
                    <div class="checkbox">
                    	<c:choose>
						    <c:when test="${product.category == 16}">
						        <label for="id_categories_4"><input id="category_16" name="category_16" type="checkbox" checked="checked" value="16"> Inkjet</label>
						    </c:when>    
						    <c:otherwise>
						        <label for="id_categories_4"><input id="category_16" name="category_16" type="checkbox" value="16"> Inkjet</label>
						    </c:otherwise>
						</c:choose>
                	</div>
                    <div class="checkbox">
                    	<c:choose>
						    <c:when test="${product.category == 0 || product.category == 32}">
						        <label for="id_categories_5"><input id="category_32" name="category_32" type="checkbox" checked="checked" value="32"> Other</label>
						    </c:when>    
						    <c:otherwise>
						        <label for="id_categories_5"><input id="category_32" name="category_32" type="checkbox" value="32"> Other</label>
						    </c:otherwise>
						</c:choose>
                	</div>
             	</div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Status:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">                  
                  <div class="checkbox" id="id_active">
                  	<c:if test="${product.active == 1}">
                		<input id="active" name="active" type="checkbox" checked="checked"><label class="vCheckboxLabel" for="id_active">Is active</label>
                	</c:if>
                	<c:if test="${product.active == 0}">
                		<input id="active" name="active" type="checkbox"><label class="vCheckboxLabel" for="id_active">Is active</label>
                	</c:if>
                	</div><span class="help-block">Only active products will be shown on product filters.</span> 
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div></div>
    <div class="caption" style="margin-bottom:20px; float:right;">
    	<input type="hidden" id="button_type" name="button_type"/>
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
