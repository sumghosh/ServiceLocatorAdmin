<div class="container-fluid">
  <div class="row">
    <div class="col-sm-3 col-md-2 sidebar">
      <div class="txt_img1"><span style="color:#fff">Welcome, </span></br>
        Atanu Bhattacharya</div>
      <div id="accordian">
        <ul>
          <li  class="active">
            <h3><span class="fa fa-dashboard"></span>AUTHOR ADMINISTRATION</h3>
            <ul>
              <li><a href="#"> GROUP </a></li>
              <li><a href="#"> USER </a></li>
            </ul>
          </li>
          <!-- This is the list item that is open by default -->
          <li>
            <h3><span class="fa fa-tasks"></span>SERVICE LOCATOR</h3>
            <ul>
              <li>
              	<form id="compform" method="post" action="CompanyDetails">
  					<input type="hidden" name="jdbc_query" value="value" /> 
  					<a href="#" onclick="document.getElementById('compform').submit();"><strong>COMPANIES</strong></a>
				</form>
			  </li>
              <li><form id="prodform" method="post" action="ProductDetails">
  					<input type="hidden" name="jdbc_query" value="value" /> 
  					<a href="#" onclick="document.getElementById('prodform').submit();"><strong>PRODUCTS</strong></a>
				</form>
			  </li>
              <li><a href="#"> TECHNICIANS </a></li>
              <li><a href="#"> TRAINING </a></li>
              <li><a href="#"> XML FEEDS </a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>