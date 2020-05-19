<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<%-- <html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Confirmation</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>Id</th>
			<th>First Name</th>
       		<th>Last Name</th>
       		<th>Free Passes</th>
       		<th>Postal Code</th>
       		<th>Email</th>
       		<th>Action</th>
		</tr>
		<c:forEach var="tempemployee" items="${employee}" varStatus="i">
			<c:url var="deleteLink" value="/employee/deleteEmployee">
        		<c:param name="employeeId" value="${tempemployee.id}" />
       		</c:url>
       		<c:url var="updateLink" value="/employee/updateEmployeeData">
        		<c:param name="employeeId" value="${tempemployee.id}" />
       		</c:url>
			<tr>
				<td>${i.index + 1}</td>
				<td>${tempemployee.firstName}</td>
				<td>${tempemployee.lastName}</td>
				<td>${tempemployee.freePasses}</td>
				<td>${tempemployee.postalCode}</td>
				<td>${tempemployee.email}</td>
				<td>
         		 <a href="${updateLink}">Update</a>
         |		 <a href="${deleteLink}">Delete</a>
        		</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>--%>
<html>
<head>
    <title>Select Direct Reports</title>
    <link rel="STYLESHEET" type="text/css" href="/codebase/dhtmlx.css">
    <script src="/codebase/dhtmlx.js"></script>
</head>
<body>
    <div id="gridbox" style="width:1140px;height:700px;"></div> 
    <script>
      mygrid = new dhtmlXGridObject('gridbox');
      //mygrid.setImagePath("include/imgs/");
      mygrid.setHeader("UID,FirstName,LastName,FreePasses,PostalCode,Email");
      mygrid.setInitWidths("100,200,100,100,150,150");
      mygrid.setColAlign("left,left,left,left,left,left");
      mygrid.setColumnIds("id,firstName,lastName,freePasses,postalCode,email");
      mygrid.setColTypes("ro,ed,ed,ed,ed,ed");
      mygrid.setColSorting("str,str,str,str,str,str");
      //mygrid.attachEvent("onCheck", doOnChecked);
      mygrid.init();
  
      mygrid.load("http://localhost:8080/simplespringproject/employee/showemployees", "js");
    </script>
</body>
</html>