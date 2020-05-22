<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
.error {
	color: red
}
</style>
</head>
<body>
	<h1>Spring Example</h1>
	<i>Fill out the form. Asterisk (*) means required.</i>
	<br>
	<br>
	<form:form action="processForm" modelAttribute="employee"
		id="employeeForm">
        	First Name:
        	<form:input path="firstName" id="firstName" />

		<br>
		<br> Last name (*):
            <form:input path="lastName" id="lastName" />
		<form:errors path="lastName" cssClass="error" />

		<br>
		<br> Free passes (*):
            <form:input path="freePasses" id="freePasses" />
		<form:errors path="freePasses" cssClass="error" />

		<br>
		<br> Email (*):
            <form:input path="email" id="email" />
		<form:errors path="email" cssClass="error" />

		<br>
		<br> Postal Code:
            <form:input path="postalCode" id="postalCode" />
		<form:errors path="postalCode" cssClass="error" />

		<br>
		<br>

		<input type="submit" value="Submit" />

	</form:form>
	<%--<script
		type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/js/validation.js"></script> --%>
</body>
</html>