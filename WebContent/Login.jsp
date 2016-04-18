<%@page import="com.lexmark.google.auth.GoogleAuthHelper"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONException"%>
<%@page import="com.lexmark.QueryWithContext"%>
<%@page import="com.lexmark.User"%>
<%@page import="java.util.logging.Logger"%>
<!DOCTYPE html>
<html>
<head>
<title>Service Locator Administration</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=-1.0">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="css/login.css" />
<!-- This is a very simple parallax effect achieved by simple CSS 3 multiple backgrounds, made by http://twitter.com/msurguy -->

</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
        <div class="row">
          <div class="col-sm-10 col-md-10" style="background:#000; padding:10px;">
            <div class="box"> <img src="img/logo1.jpg" class="img-responsive" alt="Lexmark"> </div>
          </div>
          <div class="col-sm-2 col-md-2" style="background:#000; padding:5px;">
            <div class="box" style="float:right"> <img src="img/lexmark_logo.jpg" class="img-responsive" alt="Lexmark"> </div>
          </div>
        </div>
  	</div>
 </nav>

<div class="container-fluid">
    <div class="row vertical-offset-100">
    	<div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4col-lg-4 col-lg-offset-4">
    		<div class="panel panel-default">
			  	<div class="panel-heading">
			    	<h3 class="panel-title">Please sign in</h3>
			 	</div>
			  	<div class="panel-body">
			    	<form accept-charset="UTF-8" role="form" method="post" action="login">
                    <fieldset>
                    <div class="form-group">
                    <span class="success" style="margin-top:10px; margin-bottom:20px;">
      <%
			/*
			 * The GoogleAuthHelper handles all the heavy lifting, and contains all "secrets"
			 * required for constructing a google login url.
			 */
			final GoogleAuthHelper helper = new GoogleAuthHelper();
			if (request.getParameter("code") == null
					|| request.getParameter("state") == null) {
				/*
				 * initial visit to the page
				 */
				//Login via Lexmark <a href="http://localhost:8080/ServiceLocator/GoogleAuthentication?next=/ServiceLocator/dashboard.jsp" class="text-success">Google mail</a>
				out.println("Log in via"); 
				out.println("<a href='" + helper.buildLoginUrl()
						+ "'>Lexmark Google mail</a>");
						
				/*
				 * set the secure state token in session to be able to track what we sent to google
				 */
				session.setAttribute("state", helper.getStateToken());
				
			} else if (request.getParameter("code") != null && request.getParameter("state") != null
					&& request.getParameter("state").equals(session.getAttribute("state"))) {
				session.removeAttribute("state");
				//out.println("<pre>");
				/*
				 * Executes after google redirects to the callback url.
				 * Please note that the state request parameter is for convenience to differentiate
				 * between authentication methods (ex. facebook oauth, google oauth, twitter, in-house).
				 * 
				 * GoogleAuthHelper()#getUserInfoJson(String) method returns a String containing
				 * the json representation of the authenticated user's information. 
				 * At this point you should parse and persist the info.
				 */
				//out.println(helper.getUserInfoJson(request.getParameter("code")));
				String userJSON = helper.getUserInfoJson(request.getParameter("code"));
				JSONObject jObject;
				String firstName = "";
				String email = "";
				User user = null;
				try {
					jObject = new JSONObject(userJSON);
					firstName = jObject.getString("given_name");
					email = jObject.getString("email");
					QueryWithContext qwc = new QueryWithContext();
					if (!email.equals("")){
						user = qwc.getUserFromEmail(email);
						if (user != null && user.getIsActive() != 0){
							session.setAttribute("GoogleloggedInUser", firstName);
							RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
							view.forward(request, response);
						} else {
							//redirect to error page. User is successfully logged into Google but doesn't have access to the SL Admin
							RequestDispatcher view = request.getRequestDispatcher("LoginError.jsp");
							view.forward(request, response);
						}
					} else {
						Logger.getLogger("Email is empty");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.println(userJSON);
				out.println(firstName);
				//out.println("</pre>");
			}
		%>
                     <b>OR</b> <br>
                    Login using Service Locator credentials</span>
                    </div>
			    	  	<div class="form-group">
			    		    <input id="username" class="form-control" placeholder="username" name="username" type="text">
			    		</div>
			    		<div class="">
                    <span id="emailError" class="" style="margin-top:5px; margin-bottom:10px;">${userdoesnotexists}</span>
                    </div>
			    		<div class="form-group">
			    			<input class="form-control" placeholder="Password" name="password" type="password" id="password" value="">
			    		</div>
			    		<span id="passwordError" class="" style="margin-top:5px; margin-bottom:10px;">${passwordnotmatched}</span>
			    		<div class="checkbox">
			    	    	<label>
			    	    		<input name="remember" type="checkbox" value="Remember Me"> Remember Me
			    	    	</label>
			    	    </div>
			    		<input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
			    	</fieldset>
			      	</form>
			    </div>
			</div>
		</div>
	</div>
</div>



<!-- Bootstrap core JavaScript
    ================================================== --> 
<!-- Placed at the end of the document so the pages load faster --> 
<script type="text/javascript" src="js/TweenLite.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script src="js/jquery.min.js"></script> 
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script> 
<script src="js/bootstrap.min.js"></script> 
<!-- Just to make our placeholder images work. Don't actually copy the next line! --> 
<script src="js/holder.min.js"></script> 
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug --> 
<script src="js/ie10-viewport-bug-workaround.js"></script> 
<!-- jQuery --> 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
 
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</body>
</html>