<%@ page import="java.util.*" %>
<%@ page import="com.lexmark.Company" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/base.css" />
<link rel="stylesheet" type="text/css" href="css/changelists.css" />

<title>Select company to change</title>
</head>
<body class="change-list">
<!-- Container -->
<div id="container">
    <!-- Header -->
    <div id="header">
        <div id="branding">
			<h1 id="site-name">Service Locator administration</h1>
        </div>
    </div>
    <!-- END Header -->

    <!-- Content -->
    <div id="content" class="flex">
		<h1>Select Company to change</h1>
		<div id="content-main">
		<ul class="object-tools">
            <li>
              <a href="/kyadmin/service_locator/company/add/" class="addlink">
                Add Company
              </a>
            </li>
        </ul>
		<div class="module filtered" id="changelist">
        	<div id="toolbar">
        		<form id="changelist-search" action="companies" method="get">
        			<label for="searchbar"><img src="img/icon_searchbox.png" alt="Search" /></label>
					<input type="text" size="40" name="q" value="${searchtext}" id="searchbar" />
					<input type="submit" value="Search" />
					<c:if test="${nosReturned > 0}">
					<span class="small quiet"><c:out value="${nosReturned}"/> results (<a href="?q="><c:out value="${nosTotal}"/> total</a>)</span>
					</c:if>
        		</form>
        	</div>
        	<script type="text/javascript">document.getElementById("searchbar").focus();</script>
		<div class="results">
			<table id="result_list">
				<thead>
				<form id="columnsort" action="companies" method="get">
					<input type="hidden" size="40" name="q" value="${searchtext}" id="searchbar" />
					<tr>
						<th scope="col"  class="action-checkbox-column">
   							<div class="text"><span><input type="checkbox" id="action-toggle" /></span></div>
   							<div class="clear"></div>
						</th>
						<th scope="col"  class="sortable">
   							<div class="text"><a href="?q=${searchtext}&o=1">Name</a></div>
   							<div class="clear"></div>
						</th>
						<th scope="col"  class="sortable">
   							<div class="text"><a href="?q=${searchtext}&o=2">Company location id</a></div>
   							<div class="clear"></div>
						</th>
						<th scope="col"  class="sortable">
							<div class="text"><a href="?q=${searchtext}&o=3">Phone</a></div>
							<div class="clear"></div>
						</th>
						<th scope="col" >
						   <div class="text"><span>Address</span></div>
						   <div class="clear"></div>
						</th>
						<th scope="col"  class="sortable">
						   <div class="text"><a href="?q=${searchtext}&o=5">Country</a></div>
						   <div class="clear"></div>
						</th>
					</tr>
					</form>
				</thead>
				<tbody>
					<c:forEach var="Company" items="${companies}">
					<tr class="row1">
						<td class="action-checkbox">
						<input class="action-select" name="_selected_action" type="checkbox" value="" /></td>
						<th><a href="/companies/${Company.id}"><c:out value="${Company.compName}"></c:out></a></th>
						<th><c:out value="${Company.compLocId}"></c:out></th>
						<th><c:out value="${Company.compPhone}"></c:out></th>
						<th><c:out value="${Company.compAddress.addressLine1
									.concat(' ').concat(Company.compAddress.addressLine2)
									.concat(', ').concat(Company.compAddress.city)
									.concat(', ').concat(Company.compAddress.region)
									}"></c:out></th>
						<th><c:out value="${Company.compAddress.country}"></c:out></th>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
        </div>
		</div>
	</div>
</div>
</body>
</html>