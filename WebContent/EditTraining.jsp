<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<script type="text/javascript">
function submit_form(id){
	document.getElementById('button_type').value = id;
	document.getElementById('editTraining').action = 'UpdateTraining?trainingid=${training.id}';
	document.getElementById('editTraining').submit();
}
function openTechPopUp(){
  window.open("TechnicianDetailsPopUp", "_blank", "scrollbars=1,resizable=1,height=600,width=900");
}
function openProdPopUp(){
	  window.open("ProductDetailsPopUp", "_blank", "scrollbars=1,resizable=1,height=600,width=900");
}
$(document).ready(function(){
    $("div#cert_selector select option").each(function(){
        if($(this).val()=="${training.type}"){
            $(this).attr("selected","selected");    
        }
    });
});
</script>

<!-- Content Area Starts -->

<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Add Training</h1>
  <div class="bs-example">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="gen"><a href="TrainingDetails">Training</a></li>
      <li class="active">Add Training</li>
    </ul>
  </div>
  <form id="editTraining" class="form-horizontal" data-toggle="validator" method="post" action="">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">The form below contains adding training to the system</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_technician">Technician:</label>
              <div class="col-xs-9 col-md-4 col-lg-4">
                <div class="input-group">
                	<span>
                  		<input type="text" id="technician_id" name="technician_id" value="${training.technicianId}" placeholder="Technician Search" required>
                  		<a class="glyphicon glyphicon-search gen" href="#" onclick='javascript:openTechPopUp()' title="Technician Details"></a>
                  	</span>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="id_product">Product:</label>
              <div class="col-xs-9 col-md-4 col-lg-4">
                <div class="input-group">
                  <span>
                  	<input type="text" id="product_id" name="product_id" class="" value="${training.productId}" placeholder="Product Search" required>
                  	<a class="glyphicon glyphicon-search gen" href="#" onclick='javascript:openProdPopUp()' title="Product Details"></a>
                  </span>
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="id_type">Training type:</label>
              <div class="col-xs-9 col-md-3 col-lg-3">
                <div id="cert_selector" class="input-group" style="margin-right:10px;">
                <div class="input-group-addon"> <span class="glyphicon glyphicon-filter"></span> </div>
                <select id="cert_type" name="cert_type" class="form-control">
					<option value="h">Gold Certification</option>
					<option value="o">Silver Certification</option>
                </select>
              </div>
              </div>
            </div>
          </div>
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
</div>
<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>