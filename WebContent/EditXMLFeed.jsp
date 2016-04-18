<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<script type="text/javascript">
function submit_form(id){
	//alert(id);
	document.getElementById('type_of_add').value = id;
	document.getElementById('addXMLFeed').submit();
}
</script>
<!-- Content Area Starts -->
<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Add Xml Feed</h1>
  <div class="bs-example gen">
    <ul class="breadcrumb ">
      <li><a href="dashboard.jsp">Home</a></li>
      <li><a href="XMLFeedDetails">XML Feed</a></li>
      <li class="active">Add Xml Feed</li>
    </ul>
  </div>
  <form id="addXMLFeed" class="form-horizontal" method="post" action="UpdateXMLFeed?feedid=${feed.id}">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">The form below contains adding a new xml feed to the system</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="id_xml_file">Xml file:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
            	    <div class="input-group">
                    <span class="input-group-btn">
                    <span class="btn btn-success btn-file">
                    Browse&hellip; <input type="file" multiple>
                    </span>
                    </span>
                    <input type="text" id="xml_file_name" name="xml_file_name" value="${feed.xml_file}" class="form-control" readonly>
                    </div>
                   <script>
						$(document).on('change', '.btn-file :file', function() {
							var input = $(this),
							numFiles = input.get(0).files ? input.get(0).files.length : 1,
							label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
							input.trigger('fileselect', [numFiles, label]);
							});
						
						$(document).ready( function() {
						$('.btn-file :file').on('fileselect', function(event, numFiles, label) {
						
							var input = $(this).parents('.input-group').find(':text'),
							log = numFiles > 1 ? numFiles + ' files selected' : label;
							
							if( input.length ) {
							input.val(log);
							} else {
							if( log ) alert(log);
							}
						
						});
						});			   
        			</script>
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Processed on:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group" style="padding-top:7px;">
                  <p>${feed.processed_on}</p>
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Status:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group" style="padding-top:7px;">
                  <p>${feed.status}</p>
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Log: </label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group" style="padding-top:7px;">
                  <p>${feed.log}</p>
                </div>
              </div>              
            </div>            
          </div>
        </div>
      </div>
    </div>
    <div class="caption" style="margin-bottom:20px; float:right;">
     	<input type="hidden" id="type_of_add" name="type_of_add"/>
      	<a type="button" id="saveadd" class="btn btn-success" onClick="javascript:submit_form(this.id);">SAVE AND ADD ANOTHER</a>
      	<a type="button" id="saveedit"  class="btn btn-success" onClick="javascript:submit_form(this.id);">SAVE AND CONTINUE EDITING</a>
      	<a type="button" id="save" class="btn btn-success" onClick="javascript:submit_form(this.id);">SAVE</a>
    </div>
  </form>
</div>
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>