<%@page import="com.pravin.dblog4j.util.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
</head>
<body>
	<%
	    User user = (User) session.getAttribute("User");
	%>
	<h3>
		Hi
		<%=user.getName()%>
	</h3>
	Email ::<%=user.getEmail()%>
	<br />
	Country ::<%=user.getCountry()%>
	<br />
	<form action="LogoutServlet" method="post">
		<input type="submit" value="Lgout"
			style="border-bottom: gray; border-top-style: hidden;">
	</form>
</body>
</html>