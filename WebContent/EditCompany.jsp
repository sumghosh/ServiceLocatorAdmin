<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty loggedInUser || not empty GoogleloggedInUser}">
<%@ include file="header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
function submit_form(id){
	//alert(id);
	document.getElementById('button_type').value = id;
	//alert(document.getElementById('button_type').value);
	document.getElementById('editComp').action = 'UpdateCompany?compid=${company.id}';
	document.getElementById('editComp').submit();
}
$(document).ready(function(){
    $("div#region_selector select option").each(function(){
        if($(this).val()=="${company.compAddress.region}"){
            $(this).attr("selected","selected");    
        }
    });
});
$(document).ready(function(){
    $("div#country_selector select option").each(function(){
        if($(this).val()=="${company.compAddress.country}"){
            $(this).attr("selected","selected");    
        }
    });
});
$(document).ready(function() {
        /*$("#add").click(function() {
        	var count = $('#mytable tbody tr').size();
        	//alert(count);
			if(count == 1){
				$('#mytable').show();
				count++;
			}else{
				$('#mytable tbody>tr:last').clone(true).insertAfter('#mytable tbody>tr:last');
				
			}
			$('#mytable tbody>tr>td>input:last').val("");
          
          return false;
        });*/
        $("#add").click(function() {
			var count = $('#mytable:visible tbody tr').size();
			if(count == 0){
				$('#mytable').show();
			}else{
				$('#mytable:hidden').show();
				$('#mytable tbody>tr:first').clone(true).insertAfter('#mytable tbody>tr:last');
			}
		$('#mytable tbody tr:last td:first input').val("");
          return false;
        });
        var techs = "${technicians}";
        techs = techs.replace(/[\[\]\']+/g,''); 
        techs = techs.replace(/\s/g, ''); //remove spaces
        var arr = techs.split(',');
        for(var i=0 ; i<arr.length ; i++){
        	if(i == 0){
        		if(arr[i] > 0){
					$('#mytable').show();
        		}
			}else{
				$('#mytable tbody>tr:last').clone(true).insertAfter('#mytable tbody>tr:last');
			}
        	$('#mytable tbody>tr>td>input:last').val(arr[i]);
        }
        $("a[id^='lookup_technician']").click(function(){
        	window.open("TechnicianDetailsPopUp", "_blank", "scrollbars=1,resizable=1,height=600,width=900");
        	$('#mytable tbody>tr>td>input').attr("clicked",false);
        	$(this).siblings().attr("clicked",true);
        	
        });
    });

	/*function SomeDeleteRowFunction(btndel) {
		if (typeof(btndel) == "object") {
			$(btndel).closest("tr").remove();
		} else {
			return false;
		}
	}*/
	function SomeDeleteRowFunction(btndel) {
		var count = $('#mytable:visible tbody tr').size();
		if(typeof(btndel) == "object"){
			if (count == 1) {
				//$(btndel).parent().siblings().child('input').val('');
				$('#mytable:visible tbody tr td:first input').val('');
				$('#mytable').hide();
			}else{
				$(btndel).closest("tr").remove();
			}
		}else{
			return false;
		}
	}
</script>
<!-- Content Area Starts -->
 <div class="col-sm-8 col-md-12 col-lg-12 col-xs-12 main rightnav">
  <h1 class="page-header_content" style="margin-left:20px;">Edit Company</h1>
  <div class="bs-example gen">
    <ul class="breadcrumb ">
      <li><a href="dashboard.jsp">Home</a></li>
      <li><a href="CompanyDetails">Companies</a></li>
      <li class="active">Edit Company</li>
    </ul>
  </div>
  <form id="editComp" class="form-horizontal" data-toggle="validator" method="post" action="">
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">The form below contains adding a new company to the system</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Name:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-user"></span> </div>
                  <input type="text" id="name" name="name" value ="${company.compName}" class="form-control" placeholder="Input the name" required>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Company Location ID:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-map-marker"></span> </div>
                  <input type="text" id="complocid" name="complocid" value ="${company.compLocId}" class="form-control" placeholder="Input company location id" required>
                </div>
                <span class="help-block">LS MDM id</span> </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Phone:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-phone-alt"></span> </div>
                  <input type="text" id="phone" name="phone" value="${company.compPhone}" class="form-control" placeholder="Phone">
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">eMail:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-envelope"></span> </div>
                  <input type="text" id="email" name="email" value="${company.compEmail}" class="form-control" placeholder="email">
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">URL:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-globe"></span> </div>
                  <input type="text" id="url" name="url" value="${company.compUrl}" class="form-control" placeholder="url">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:20px;">
        <div class="panel-heading">
          <h3 class="panel-title">Address</h3>
        </div>
        <div class="panel-body">
          <div class="row" style="margin:20px;">
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Street address:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-map-marker"></span> </div>
                  <input type="text" id="addressline1" name="addressline1" value="${company.compAddress.addressLine1}" class="form-control" placeholder="street address" required>
                </div>
                <span class="help-block">E.g., 123 Main Street West</span> </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label" for="inputSuccess">Supplimentary address:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-map-marker"></span> </div>
                  <input type="text" id="addressline2" name="addressline2" value="${company.compAddress.addressLine2}"  class="form-control" placeholder="supplimentary address">
                </div>
                <span class="help-block">E.g., Unit 2</span> </div>
            </div>
            <div class="form-group">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">City:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="lyphicon glyphicon-picture"></span> </div>
                  <input type="text" id="city" name="city" value="${company.compAddress.city}" class="form-control" placeholder="city" required>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label1 col-xs-3  col-md-2">Region:</label>
              <div class="col-xs-9 col-md-4">
                <div id="region_selector" class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-globe"></span> </div>
                  <select id="region" name="region" class="form-control" required>
                  	<option value="">---------</option>
                   	<option value="AL">Alabama</option>
                   	<option value="AK">Alaska</option>
                    <option value="AB">Alberta</option>
                    <option value="AS">American Samoa</option>
                    <option value="AZ">Arizona</option>
                    <option value="AR">Arkansas</option>
                    <option value="AA">Armed Forces Americas</option>
                    <option value="AE">Armed Forces Europe</option>
                    <option value="AP">Armed Forces Pacific</option>
                    <option value="BC">British Columbia</option>
                    <option value="CA">California</option>
                    <option value="CO">Colorado</option>
                    <option value="CT">Connecticut</option>
                    <option value="DE">Delaware</option>
                    <option value="DC">District of Columbia</option>
                    <option value="FL">Florida</option>
                    <option value="GA">Georgia</option>
                    <option value="GU">Guam</option>
                    <option value="HI">Hawaii</option>
                    <option value="ID">Idaho</option>
                    <option value="IL">Illinois</option>
                    <option value="IN">Indiana</option>
                    <option value="IA">Iowa</option>
                    <option value="KS">Kansas</option>
                    <option value="KY">Kentucky</option>
                    <option value="LA">Louisiana</option>
                    <option value="ME">Maine</option>
                    <option value="MB">Manitoba</option>
                    <option value="MD">Maryland</option>
                    <option value="MA">Massachusetts</option>
                    <option value="MI">Michigan</option>
                    <option value="MN">Minnesota</option>
                    <option value="MS">Mississippi</option>
                    <option value="MO">Missouri</option>
                    <option value="MT">Montana</option>
                    <option value="NE">Nebraska</option>
                    <option value="NV">Nevada</option>
                    <option value="NB">New Brunswick</option>
                    <option value="NH">New Hampshire</option>
                    <option value="NJ">New Jersey</option>
                    <option value="NM">New Mexico</option>
                    <option value="NY">New York</option>
                    <option value="NL">Newfoundland and Labrador</option>
                    <option value="NC">North Carolina</option>
                    <option value="ND">North Dakota</option>
                    <option value="MP">Northern Mariana Islands</option>
                    <option value="NT">Northwest Territories</option>
                    <option value="NS">Nova Scotia</option>
                    <option value="NU">Nunavut</option>
                    <option value="OH">Ohio</option>
                    <option value="OK">Oklahoma</option>
                    <option value="ON">Ontario</option>
                    <option value="OR">Oregon</option>
                    <option value="PA">Pennsylvania</option>
                    <option value="PE">Prince Edward Island</option>
                    <option value="PR">Puerto Rico</option>
                    <option value="QC">Quebec</option>
                    <option value="RI">Rhode Island</option>
                    <option value="SK">Saskatchewan</option>
                    <option value="SC">South Carolina</option>
                    <option value="SD">South Dakota</option>
                    <option value="TN">Tennessee</option>
                    <option value="TX">Texas</option>
                    <option value="UT">Utah</option>
                    <option value="VT">Vermont</option>
                    <option value="VI">Virgin Islands</option>
                    <option value="VA">Virginia</option>
                    <option value="WA">Washington</option>
                    <option value="WV">West Virginia</option>
                    <option value="WI">Wisconsin</option>
                    <option value="WY">Wyoming</option>
                    <option value="YT">Yukon</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="form-group has-success">
              <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Postal Code:</label>
              <div class="col-xs-9 col-md-4">
                <div class="input-group">
                  <div class="input-group-addon"> <span class="lyphicon glyphicon-picture"></span> </div>
                  <input type="text" id="postalcode" name="postalcode" value="${company.compAddress.postalCode}" class="form-control" placeholder="Postal Code" required>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label class="control-label1 col-xs-3 col-md-2">Country:</label>
              <div class="col-xs-9 col-md-4">
                <div id="country_selector" class="input-group">
                  <div class="input-group-addon"> <span class="glyphicon glyphicon-globe"></span> </div>
                  <select name="country" class="form-control" required>
                  	<option value="">---------</option>
                    <option value="AX">Aaland Islands</option>
                    <option value="AF">Afghanistan</option>
                    <option value="AL">Albania</option>
                    <option value="DZ">Algeria</option>
                    <option value="AS">American Samoa</option>
                    <option value="AD">Andorra</option>
                    <option value="AO">Angola</option>
                    <option value="AI">Anguilla</option>
                    <option value="AQ">Antarctica</option>
                    <option value="AG">Antigua And Barbuda</option>
                    <option value="AR">Argentina</option>
                    <option value="AM">Armenia</option>
                    <option value="AW">Aruba</option>
                    <option value="AU">Australia</option>
                    <option value="AT">Austria</option>
                    <option value="AZ">Azerbaijan</option>
                    <option value="BS">Bahamas</option>
                    <option value="BH">Bahrain</option>
                    <option value="BD">Bangladesh</option>
                    <option value="BB">Barbados</option>
                    <option value="BY">Belarus</option>
                    <option value="BE">Belgium</option>
                    <option value="BZ">Belize</option>
                    <option value="BJ">Benin</option>
                    <option value="BM">Bermuda</option>
                    <option value="BT">Bhutan</option>
                    <option value="BO">Bolivia</option>
                    <option value="BA">Bosnia</option>
                    <option value="BA">Bosnia Herzegovina</option>
                    <option value="BA">Bosnia and Herzegovina</option>
                    <option value="BW">Botswana</option>
                    <option value="BV">Bouvet Island</option>
                    <option value="BR">Brazil</option>
                    <option value="IO">British Indian Ocean Territory</option>
                    <option value="BN">Brunei Darussalam</option>
                    <option value="BG">Bulgaria</option>
                    <option value="BF">Burkina Faso</option>
                    <option value="BI">Burundi</option>
                    <option value="KH">Cambodia</option>
                    <option value="CM">Cameroon</option>
                    <option value="CA">Canada</option>
                    <option value="CV">Cape Verde</option>
                    <option value="KY">Cayman Islands</option>
                    <option value="CF">Central African Republic</option>
                    <option value="TD">Chad</option>
                    <option value="CL">Chile</option>
                    <option value="CN">China</option>
                    <option value="CX">Christmas Island</option>
                    <option value="CC">Cocos (Keeling) Islands</option>
                    <option value="CO">Colombia</option>
                    <option value="KM">Comoros</option>
                    <option value="CD">Congo, Democratic Republic Of (Was Zaire)</option>
                    <option value="CG">Congo, Republic Of</option>
                    <option value="CK">Cook Islands</option>
                    <option value="CR">Costa Rica</option>
                    <option value="CI">Cote D&#39;Ivoire</option>
                    <option value="HR">Croatia</option>
                    <option value="CU">Cuba</option>
                    <option value="CY">Cyprus</option>
                    <option value="CZ">Czech Republic</option>
                    <option value="DK">Denmark</option>
                    <option value="DJ">Djibouti</option>
                    <option value="DM">Dominica</option>
                    <option value="DO">Dominican Republic</option>
                    <option value="EC">Ecuador</option>
                    <option value="EG">Egypt</option>
                    <option value="SV">El Salvador</option>
                    <option value="GQ">Equatorial Guinea</option>
                    <option value="ER">Eritrea</option>
                    <option value="EE">Estonia</option>
                    <option value="ET">Ethiopia</option>
                    <option value="FK">Falkland Islands (Malvinas)</option>
                    <option value="FO">Faroe Islands</option>
                    <option value="FJ">Fiji</option>
                    <option value="FI">Finland</option>
                    <option value="FR">France</option>
                    <option value="GF">French Guiana</option>
                    <option value="PF">French Polynesia</option>
                    <option value="TF">French Southern Territories</option>
                    <option value="GA">Gabon</option>
                    <option value="GM">Gambia</option>
                    <option value="GE">Georgia</option>
                    <option value="DE">Germany</option>
                    <option value="GH">Ghana</option>
                    <option value="GI">Gibraltar</option>
                    <option value="GR">Greece</option>
                    <option value="GL">Greenland</option>
                    <option value="GD">Grenada</option>
                    <option value="GP">Guadeloupe</option>
                    <option value="GU">Guam</option>
                    <option value="GT">Guatemala</option>
                    <option value="GN">Guinea</option>
                    <option value="GW">Guinea-Bissau</option>
                    <option value="GY">Guyana</option>
                    <option value="HT">Haiti</option>
                    <option value="HM">Heard And Mc Donald Islands</option>
                    <option value="HN">Honduras</option>
                    <option value="HK">Hong Kong</option>
                    <option value="HU">Hungary</option>
                    <option value="IS">Iceland</option>
                    <option value="IN">India</option>
                    <option value="ID">Indonesia</option>
                    <option value="IR">Iran (Islamic Republic Of)</option>
                    <option value="IQ">Iraq</option>
                    <option value="IE">Ireland</option>
                    <option value="IL">Israel</option>
                    <option value="IT">Italy</option>
                    <option value="JM">Jamaica</option>
                    <option value="JP">Japan</option>
                    <option value="JO">Jordan</option>
                    <option value="KZ">Kazakhstan</option>
                    <option value="KE">Kenya</option>
                    <option value="KI">Kiribati</option>
                    <option value="KP">Korea, Democratic People&#39;S Republic Of</option>
                    <option value="KR">Korea, Republic Of</option>
                    <option value="KW">Kuwait</option>
                    <option value="KG">Kyrgyzstan</option>
                    <option value="LA">Lao People&#39;S Democratic Republic</option>
                    <option value="LV">Latvia</option>
                    <option value="LB">Lebanon</option>
                    <option value="LS">Lesotho</option>
                    <option value="LR">Liberia</option>
                    <option value="LY">Libya</option>
                    <option value="LY">Libyan Arab Jamahiriya</option>
                    <option value="LI">Liechtenstein</option>
                    <option value="LT">Lithuania</option>
                    <option value="LU">Luxembourg</option>
                    <option value="MO">Macau</option>
                    <option value="MK">Macedonia, The Former Yugoslav Republic Of</option>
                    <option value="MG">Madagascar</option>
                    <option value="MW">Malawi</option>
                    <option value="MY">Malaysia</option>
                    <option value="MV">Maldives</option>
                    <option value="ML">Mali</option>
                    <option value="MT">Malta</option>
                    <option value="MH">Marshall Islands</option>
                    <option value="MQ">Martinique</option>
                    <option value="MR">Mauritania</option>
                    <option value="MU">Mauritius</option>
                    <option value="YT">Mayotte</option>
                    <option value="MX">Mexico</option>
                    <option value="FM">Micronesia, Federated States Of</option>
                    <option value="MD">Moldova, Republic Of</option>
                    <option value="MC">Monaco</option>
                    <option value="MN">Mongolia</option>
                    <option value="MS">Montserrat</option>
                    <option value="MA">Morocco</option>
                    <option value="MZ">Mozambique</option>
                    <option value="MM">Myanmar</option>
                    <option value="NA">Namibia</option>
                    <option value="NR">Nauru</option>
                    <option value="NP">Nepal</option>
                    <option value="NL">Netherlands</option>
                    <option value="AN">Netherlands Antilles</option>
                    <option value="NC">New Caledonia</option>
                    <option value="NZ">New Zealand</option>
                    <option value="NI">Nicaragua</option>
                    <option value="NE">Niger</option>
                    <option value="NG">Nigeria</option>
                    <option value="NU">Niue</option>
                    <option value="NF">Norfolk Island</option>
                    <option value="MP">Northern Mariana Islands</option>
                    <option value="NO">Norway</option>
                    <option value="OM">Oman</option>
                    <option value="PK">Pakistan</option>
                    <option value="PW">Palau</option>
                    <option value="PS">Palestinian Territory</option>
                    <option value="PA">Panama</option>
                    <option value="PG">Papua New Guinea</option>
                    <option value="PY">Paraguay</option>
                    <option value="PE">Peru</option>
                    <option value="PH">Philippines</option>
                    <option value="PN">Pitcairn</option>
                    <option value="PL">Poland</option>
                    <option value="PT">Portugal</option>
                    <option value="PR">Puerto Rico</option>
                    <option value="QA">Qatar</option>
                    <option value="RE">Reunion</option>
                    <option value="RO">Romania</option>
                    <option value="RU">Russian Federation</option>
                    <option value="RW">Rwanda</option>
                    <option value="SH">Saint Helena</option>
                    <option value="KN">Saint Kitts And Nevis</option>
                    <option value="LC">Saint Lucia</option>
                    <option value="PM">Saint Pierre And Miquelon</option>
                    <option value="VC">Saint Vincent And The Grenadines</option>
                    <option value="WS">Samoa</option>
                    <option value="SM">San Marino</option>
                    <option value="ST">Sao Tome And Principe</option>
                    <option value="SA">Saudi Arabia</option>
                    <option value="SN">Senegal</option>
                    <option value="RS">Serbia</option>
                    <option value="RS">Serbia And Montenegro</option>
                    <option value="SC">Seychelles</option>
                    <option value="SL">Sierra Leone</option>
                    <option value="SG">Singapore</option>
                    <option value="SK">Slovakia</option>
                    <option value="SI">Slovenia</option>
                    <option value="SB">Solomon Islands</option>
                    <option value="SO">Somalia</option>
                    <option value="ZA">South Africa</option>
                    <option value="GS">South Georgia And The South Sandwich Islands</option>
                    <option value="ES">Spain</option>
                    <option value="LK">Sri Lanka</option>
                    <option value="SD">Sudan</option>
                    <option value="SR">Suriname</option>
                    <option value="SJ">Svalbard And Jan Mayen Islands</option>
                    <option value="SE">Sweden</option>
                    <option value="CH">Switzerland</option>
                    <option value="SY">Syrian Arab Republic</option>
                    <option value="TW">Taiwan</option>
                    <option value="TJ">Tajikistan</option>
                    <option value="TZ">Tanzania, United Republic Of</option>
                    <option value="TH">Thailand</option>
                    <option value="TL">Timor-Leste</option>
                    <option value="TG">Togo</option>
                    <option value="TK">Tokelau</option>
                    <option value="TO">Tonga</option>
                    <option value="TT">Trinidad And Tobago</option>
                    <option value="TN">Tunisia</option>
                    <option value="TR">Turkey</option>
                    <option value="TM">Turkmenistan</option>
                    <option value="TC">Turks And Caicos Islands</option>
                    <option value="TV">Tuvalu</option>
                    <option value="US">USA</option>
                    <option value="UG">Uganda</option>
                    <option value="UA">Ukraine</option>
                    <option value="AE">United Arab Emirates</option>
                    <option value="GB">United Kingdom</option>
                    <option value="UM">United States Minor Outlying Islands</option>
                    <option value="UY">Uruguay</option>
                    <option value="UZ">Uzbekistan</option>
                    <option value="VU">Vanuatu</option>
                    <option value="VA">Vatican City State (Holy See)</option>
                    <option value="VE">Venezuela</option>
                    <option value="VN">Viet Nam</option>
                    <option value="VG">Virgin Islands (British)</option>
                    <option value="VI">Virgin Islands (U.S.)</option>
                    <option value="WF">Wallis And Futuna Islands</option>
                    <option value="EH">Western Sahara</option>
                    <option value="YE">Yemen</option>
                    <option value="ZM">Zambia</option>
                    <option value="ZW">Zimbabwe</option>
                    <option value="CW">Curacao</option>
                    <option value="BQ">Bonaire, Sint Eustatius and Saba</option>
                    <option value="GG">Guernsey</option>
                    <option value="IM">Isle of Man</option>
                    <option value="JE">Jersey</option>
                    <option value="MO">Macao</option>
                    <option value="ME">Montenegro</option>
                    <option value="BL">Saint Barthelemy</option>
                    <option value="MF">Saint Martin (French part)</option>
                    <option value="SX">Sint Maarten (Dutch part)</option>
                    <option value="SZ">Swaziland</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="bs-example">
      <div class="acc">
        <dl>
          <dt style="padding:10px;">Geocoding Information&nbsp;(&nbsp;<a href="#acc1" class="acc-title accTitle js-accTrigger gen">Show</a>&nbsp;)</dt>
          <dd class="acc-content accItem is-collapsed" id="acc1" aria-hidden="true">
            <div class="panel-body">
            <div class="row" style="margin:20px;">
              <div class="form-group">
                <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Lattitude:</label>
                <div class="col-xs-9 col-md-4">
                  <div class="input-group">
                    <div class="input-group-addon"> <span class="glyphicon glyphicon-map-marker"></span> </div>
                    <input class="form-control inputSuccess" placeholder="street address" id="lat" name="lat" step="1e-7" type="number" value="${company.compAddress.latitude}" required>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-xs-3 col-md-2 control-label1" for="inputSuccess">Longitude:</label>
                <div class="col-xs-9 col-md-4">
                  <div class="input-group">
                    <div class="input-group-addon"> <span class="glyphicon glyphicon-map-marker"></span> </div>
                    <input class="form-control inputSuccess" placeholder="street address" id="lng" name="lng" step="1e-7" type="number" value="${company.compAddress.longitude}" required>
                  </div>
                </div>
              </div>
              <div class="form-group has-success">
                <label class="col-xs-3 col-md-2 control-label" for="id_address_hash">Address hash:</label>
                <div class="col-xs-9 col-md-4">
                  <input type="text" id="address_hash" name="address_hash" class="form-control" placeholder="address hash" value="${company.compAddress.addressHash}">
                </div>
                <span class="help-block">Used for geocoding caching.</span> </div>
              <div class="form-group has-success">
                <label class="col-xs-3 col-md-2 control-label" for="id_location_type">Location type:</label>
                <div class="col-xs-9 col-md-4">
                  <input type="text" id="location_type" name="location_type" class="form-control" placeholder="location type" value="${company.compAddress.locationType}">
                </div>
                <span class="help-block">Location type returned by Google geocoding APIs.</span> </div>
              
              <!-- DATE TIME TESTING STRTS -->
              <div class="form-group has-success">
                <label class="col-xs-3 col-md-2 control-label" for="id_location_type">Geocoded On:</label>
                <div class="col-xs-9 col-md-4">
                  <div class="input-group date" id="datetimepicker1">
                  	<!--<fmt:parseDate value="${company.compAddress.geocodedOn}" type="BOTH" var="parsedDate" pattern="yyyy-MM-dd HH:mm:ss" />-->
                    <input type="text" class="form-control" id="geocoded_on" name="geocoded_on" value="${company.compAddress.geocodedOn}" readonly/>
                     <!-- <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span> </span> --> 
                  </div>
                </div>
              </div>
              <!-- 
              <script type="text/javascript">
                                $(function () {
                                    $('#datetimepicker1').datetimepicker();
                                    $('#datetimepicker1').datetimepicker('option','dateFormat','yyyy-MM-dd HH:mm:ss','showOn','')
                                });
              </script>   -->             
              <!-- DATE TIME TESTING ENDS --> 
              
            </div>
          </dd>
        </dl>
      </div>
    </div>
    <div class="bs-example">
      <div class="panel panel-success" style="margin-top:10px;">
    	<div class="panel-heading">
          <h3 class="panel-title">Technicians</h3>
        </div>
    	<div class="panel-body">
          <div class="table-responsive">
        	<table class="table table-hover" width="100%">
              <tr>
            	<td width="90%"><a style="margin-left:10px;" class="gen panel-heading">Technician</a></td>
            	<td width="10%"><a href="#" class="gen">Delete?</a></td>
          	</tr>
          	<tr class="text-default">
            <td colspan="2" class="col-sm-12 col-md-12 col-lg-12 col-xs-12" id="addTable">
              <table border="0" style="display: none; margin-bottom:0px;" id="mytable" width="100%">
                <tr>
                  <td>
                  	<input class="success" id="technician_id" name="technician_id" type="text" style="margin-right:10px; margin-bottom:10px; ">
                    <a href="#" class="text_success glyphicon glyphicon-search gen" id="lookup_technician" title="Technician Details"></a>
                  </td>
                  <td>
                  	<a class="glyphicon glyphicon-remove gen" style="margin-left:auto;" type="button" onclick="SomeDeleteRowFunction(this);"></a>
                  </td>
                </tr>
              </table>
            </td>
          	</tr>
            <tr class="info">
            <td colspan="2">
            	<table width="100%" border="0" class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
                <tr>
                  <td class="glyphicon-plus gen" id="add"><a style="margin-left:10px;">Add another Technician</a></td>
                </tr>
              </table></td>
          	</tr>
            </table>
      		</div>
        </div>
  		</div>
    </div>
    
    <div class="caption" style="margin-bottom:20px; float:right;">
      	<input type="hidden" id="button_type" name="button_type"/>
      	<input type="hidden" id="compid" name="compid" value="${Company.id}"/>
      	<button type="submit" id="saveadd" class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE AND ADD ANOTHER</button>
      	<button type="submit" id="saveedit"  class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE AND CONTINUE EDITING</button>
      	<button type="submit" id="save" class="btn btn-primary disabled" onClick="javascript:submit_form(this.id);">SAVE</button>
    </div>
  </form>
</div>

<!-- Content Area Ends -->
<script src="js/jquery.min.js"></script>
<script src="js/moment-with-locales.js"></script>
<script src="js/bootstrap-datetimepicker.js"></script> 
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<script src="js/drop.js"></script> 
<script>
$.noConflict()
$(document).ready(function() {
  $("#accordian h3").click(function() {
    //Slide up all the link lists
    $("#accordian ul ul").slideUp();
    //Slide down the link list below the h3 clicked - only if it's closed
    if(!$(this).next().is(":visible")) {
      $(this).next().slideDown();
    }
  })
})
</script>
</c:if>
<c:if test="${empty loggedInUser && empty GoogleloggedInUser}">
    <%@ include file="Login.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>
