<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<link rel="stylesheet" type="text/css" href="css/prettify.min.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap-duallistbox.css">
<script src="js/run_prettify.min.js"></script>
<script src="js/jquery.bootstrap-duallistbox.js"></script>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<!-- Content Area Starts -->

<div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Add Group</h1>
  <div class="bs-example">
    <ul class="breadcrumb ">
      <li class="gen"><a href="dashboard.jsp">Home</a></li>
      <li class="gen"><a href="GroupDetails">Groups</a></li>
      <li class="active">Add Group</li>
    </ul>
  </div>
  <form class="form-horizontal" id="addGroup" data-toggle="validator" action="saveGroup" method="post">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">The form below contains adding a new group to the system</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Name:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-list-alt"></span> </div>
                  <input type="text" for="id_name" class="form-control" placeholder="Input group name" required>
                </div>
            </div>
            <div class="form-group has-success">
              <label class="control-label col-xs-3 col-md-2" for="id_permissions">Permissions:</label>
              <script type="text/javascript">addEvent(window, "load", function(e) {SelectFilter.init("id_permissions", "permissions", 0, "/static/admin/"); });</script>
              <div class="col-xs-9 col-md-4">
                <div class="input-group"> <span><a class="glyphicon glyphicon-check gen" href="#" data-toggle="tooltip" data-placement="right" title="This is the list of available permissions. You may choose some by selecting them in the box below and then clicking the &quot;Choose&quot; arrow between the two boxes."></a> Available permissions</span> 
                  <script>
						$(document).ready(function(){
    					$('[data-toggle="tooltip"]').tooltip();   
						});
					</script> 
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <select multiple="multiple" size="15" name="duallistbox_demo1[]">
                <option value="1">admin | log entry | Can add log entry</option>
                <option value="2">admin | log entry | Can change log entry</option>
                <option value="3">admin | log entry | Can delete log entry</option>
                <option value="7">auth | group | Can add group</option>
                <option value="8">auth | group | Can change group</option>
                <option value="9">auth | group | Can delete group</option>
                <option value="4">auth | permission | Can add permission</option>
                <option value="5">auth | permission | Can change permission</option>
                <option value="6">auth | permission | Can delete permission</option>
                <option value="10">auth | user | Can add user</option>
                <option value="11">auth | user | Can change user</option>
                <option value="12">auth | user | Can delete user</option>
                <option value="13">contenttypes | content type | Can add content type</option>
                <option value="14">contenttypes | content type | Can change content type</option>
                <option value="15">contenttypes | content type | Can delete content type</option>
                <option value="25">lxkdealerlocator | company | Can add company</option>
                <option value="26">lxkdealerlocator | company | Can change company</option>
                <option value="27">lxkdealerlocator | company | Can delete company</option>
                <option value="22">lxkdealerlocator | dealer type | Can add dealer type</option>
                <option value="23">lxkdealerlocator | dealer type | Can change dealer type</option>
                <option value="24">lxkdealerlocator | dealer type | Can delete dealer type</option>
                <option value="28">lxkdealerlocator | location | Can add location</option>
                <option value="29">lxkdealerlocator | location | Can change location</option>
                <option value="30">lxkdealerlocator | location | Can delete location</option>
                <option value="19">lxkdealerlocator | locator instance | Can add locator instance</option>
                <option value="20">lxkdealerlocator | locator instance | Can change locator instance</option>
                <option value="21">lxkdealerlocator | locator instance | Can delete locator instance</option>
                <option value="34">service | product | Can add product</option>
                <option value="35">service | product | Can change product</option>
                <option value="36">service | product | Can delete product</option>
                <option value="31">service | product category | Can add product category</option>
                <option value="32">service | product category | Can change product category</option>
                <option value="33">service | product category | Can delete product category</option>
                <option value="37">service | technician | Can add technician</option>
                <option value="38">service | technician | Can change technician</option>
                <option value="39">service | technician | Can delete technician</option>
                <option value="40">service | training | Can add training</option>
                <option value="43">service | training | Can use RESTful API to upload training information</option>
                <option value="41">service | training | Can change training</option>
                <option value="42">service | training | Can delete training</option>
                <option value="44">service | xml feed | Can add xml feed</option>
                <option value="45">service | xml feed | Can change xml feed</option>
                <option value="46">service | xml feed | Can delete xml feed</option>
                <option value="50">service_locator | Company | Can add Company</option>
                <option value="51">service_locator | Company | Can change Company</option>
                <option value="52">service_locator | Company | Can delete Company</option>
                <option value="56">service_locator | employment | Can add employment</option>
                <option value="57">service_locator | employment | Can change employment</option>
                <option value="58">service_locator | employment | Can delete employment</option>
                <option value="47">service_locator | product | Can add product</option>
                <option value="48">service_locator | product | Can change product</option>
                <option value="49">service_locator | product | Can delete product</option>
                <option value="53">service_locator | technician | Can add technician</option>
                <option value="54">service_locator | technician | Can change technician</option>
                <option value="55">service_locator | technician | Can delete technician</option>
                <option value="59">service_locator | training | Can add training</option>
                <option value="62">service_locator | training | Can use RESTful API to upload training information</option>
                <option value="60">service_locator | training | Can change training</option>
                <option value="61">service_locator | training | Can delete training</option>
                <option value="63">service_locator | xml feed | Can add xml feed</option>
                <option value="64">service_locator | xml feed | Can change xml feed</option>
                <option value="65">service_locator | xml feed | Can delete xml feed</option>
                <option value="16">sessions | session | Can add session</option>
                <option value="17">sessions | session | Can change session</option>
                <option value="18">sessions | session | Can delete session</option>
              </select>
              <br>
              <script>
                    var demo1 = $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox();
                    $("#demoform").submit(function() {
                      alert($('[name="duallistbox_demo1[]"]').val());
                      return false;
                    });
                  </script> 
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="caption" style="margin-bottom:20px; float:right;">
      <button type="button" class="btn btn-default">SAVE AND ADD ANOTHER</button>
      <button type="button" class="btn btn-default">SAVE AND CONTINUE EDITING</button>
      <button type="submit" class="btn btn-success">SAVE</button>
    </div>
  </form>
</div>

<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>