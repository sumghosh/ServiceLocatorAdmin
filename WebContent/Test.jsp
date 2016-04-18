<%@ include file="header.jsp" %>
<title>Insert title here</title>
<script type="text/javascript">
			  function openPopUp()
			  {
				//alert('openPopUp');
			    //$('#divId').css('display','block');
			  	//$('#divId').dialog();
				  window.open("TechnicianDetails", "_blank", "scrollbars=1,resizable=1,height=600,width=900");
			  }
</script>			  
</head>
<body>


<!-- <form id="abcd" action="/TechnicianDetails" method="post" target="_blank"> -->
	<div id="divId">
		<a class="text_success glyphicon glyphicon-search gen" href='#' onclick='javascript:openPopUp()' title='Pop Up'></a>
	</div>
<!-- </form>-->
</body>
</html>