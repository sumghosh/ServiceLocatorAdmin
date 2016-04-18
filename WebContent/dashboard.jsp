<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>

<script src="js/jquery.min.js"></script>
<!-- Content Area Starts -->
<div class="col-sm-1 col-xs-1 col-md-1 col-lg-1"></div>
<div class="col-sm-12 col-xs-12 col-md-10 col-lg-10 rightnav">
  <div class="main ">
    <h1 class="page-header_content">Service Locator Dashboard</h1>
    <div class="panel panel-default">
      <div class="bs-example">
        <div class="panel panel-warning" style="margin-top:20px;">
          <div class="panel-heading">
            <h3 class="panel-title">SERVICE LOCATOR</h3>
          </div>
          <div class="panel-body">
            <div class="row table-responsive" style="float:left; width:98%; margin:10px;">
              <table border="0" width="100%">
                <tr> 
                  <td><div style="padding-left:10px; padding-right:10px;"> <a href="CompanyDetails" class="thumbnail"> <img src="img/comp_icon.jpg" alt="Companies"> </a>
                      <div class="caption" style="text-align:center;">
                        <p><a href="CompanyDetails" class="btn btn-primary btn-sm" style="text-align:center;">COMPANIES</a></p>
                      </div>
                    </div></td>
                  <td><div  style="padding-left:10px; padding-right:10px;"> <a href="ProductDetails" class="thumbnail"> <img src="img/prod_icon.jpg" alt="Products"> </a>
                      <div class="caption" style="text-align:center;">
                        <p><a href="ProductDetails" class="btn btn-primary btn-sm" style="text-align:center;">PRODUCTS</a></p>
                      </div>
                    </div></td>
                  <td><div  style="padding-left:10px; padding-right:10px;"> <a href="TechnicianDetails" class="thumbnail"> <img src="img/tech_icon.jpg" alt="Technicians"> </a>
                      <div class="caption" style="text-align:center;">
                        <p><a href="TechnicianDetails" class="btn btn-primary btn-sm" style="text-align:center;">TECHNICIANS</a></p>
                      </div>
                    </div></td>
                  <td><div  style="padding-left:10px; padding-right:10px;"> <a href="TrainingDetails" class="thumbnail"> <img src="img/train_icon.jpg" alt="Training"> </a>
                      <div class="caption" style="text-align:center;">
                        <p><a href="TrainingDetails" class="btn btn-primary btn-sm" style="text-align:center;">TRAINING</a></p>
                      </div>
                    </div></td>
                  <td><div style="padding-left:10px; padding-right:10px;"> <a href="XMLFeedDetails" class="thumbnail"> <img src="img/feed_icon.jpg" alt="Training"> </a>
                      <div class="caption" style="text-align:center;">
                        <p><a href="XMLFeedDetails" class="btn btn-primary btn-sm" style="text-align:center;">XML FEED</a></p>
                      </div>
                    </div></td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
<!--       
      <div class="bs-example">
        <div class="panel panel-warning" style="margin-top:20px;">
          <div class="panel-heading">
            <h3 class="panel-title">SOCIAL AUTHENTICATION ADMINISTRATION</h3>
          </div>
          <div class="panel-body" style="margin-left:10px; margin-top:5px; margin-right:10px;">
            <div class="row">
              <div class="col-sm-4 col-xs-6 col-md-4 col-lg-4"> <a href="#" class="thumbnail"> <img src="img/comp_icon.jpg" alt="Associations"> </a>
                <div class="caption" style="text-align:center;">
                  <p><a href="#" class="btn btn-primary btn-sm">ASSOCIATIONS</a></p>
                </div>
              </div>
              <div class="col-sm-4 col-xs-6 col-md-4 col-lg-4"> <a href="#" class="thumbnail"> <img src="img/prod_icon.jpg" alt="Nonces"> </a>
                <div class="caption" style="text-align:center;">
                  <p><a href="#" class="btn btn-primary btn-sm">NONCES</a></p>
                </div>
              </div>
              <div class="col-sm-4 col-xs-6 col-md-4 col-lg-4"> <a href="#" class="thumbnail"> <img src="img/tech_icon.jpg" alt="User Social Auths"> </a>
                <div class="caption" style="text-align:center;">
                  <p><a href="#" class="btn btn-primary btn-sm">USER SOCIAL AUTHS</a></p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
 -->      
      <!-- Two section starts -->
      
      <div class="bs-example">
        <div class="panel panel-success" style="margin-top:20px;">
          <div class="panel-body">
            <div class="row table-responsive" style="float:left; width:98%; margin:10px;">
              <table border="0" width="100%">
          <tr>
            <td><div class="bs-example" style="margin-right:10px;">
      <div class="panel panel-success">
        <div class="panel-heading">
          <h3 class="panel-title">User Administration</h3>
        </div>
        <div class="panel-body" style="margin-left:10px; margin-top:5px; margin-right:10px;">
          <div class="row">
            <div class="col-sm-3 col-xs-6 col-md-6 col-lg-6"> <a href="addUser" class="thumbnail"> <img src="img/add_usr_icon.jpg" alt="Add User"> </a>
              <div class="caption" style="text-align:center;">
                <p><a href="addUser" class="btn btn-success" style="text-align:center;">Add User</a></p>
              </div>
            </div>
            <div class="col-sm-3 col-xs-6 col-md-6 col-lg-6"> <a href="UserDetails" class="thumbnail"> <img src="img/mod_usr_icon.jpg" alt="Modify User"> </a>
              <div class="caption" style="text-align:center;">
                <p><a href="UserDetails" class="btn btn-success"  style="text-align:center;">Modify User</a></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div></td>
<!--             <td><div class="bs-example" style="margin-left:10px;">
  <div class="panel panel-success">
    <div class="panel-heading">
      <h3 class="panel-title">Group Administration</h3>
    </div>
    <div class="panel-body" style="margin-left:10px; margin-top:5px; margin-right:10px;">
      <div class="row">
        <div class="col-sm-3 col-xs-6 col-md-6 col-lg-6"> <a href="#" class="thumbnail"> <img src="img/add_grp_icon.jpg" alt="Add Group"> </a>
          <div class="caption" style="text-align:center;">
            <p><a href="#" class="txt btn btn-success" style="text-align:center;">Add Group</a> </p>
          </div>
        </div>
        <div class="col-sm-3 col-xs-6 col-md-6 col-lg-6"> <a href="GroupDetails" class="thumbnail"> <img src="img/mod_grp_icon.jpg" alt="Modify Group"> </a>
          <div class="caption" style="text-align:center;">
            <p><a href="GroupDetails" class="btn btn-success" style="text-align:center;">Modify Group</a></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div></td> -->
          </tr>
        </table>
            </div>
          </div>
        </div>
      </div>
      <!-- Two section starts -->
      

    </div>
  </div>
</div>
<div class="col-sm-1 col-xs-1 col-md-1 col-lg-1"></div>

<!-- Content Area Ends -->
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>  