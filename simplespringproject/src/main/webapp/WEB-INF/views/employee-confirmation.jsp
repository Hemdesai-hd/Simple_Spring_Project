<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
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
</html>