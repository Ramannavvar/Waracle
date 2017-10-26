<%@page import="com.waracle.cakemgr.beans.CakeEntity"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
 <h1>Cakes</h1>
 	 <c:if test="${not empty message}">
       <c:out value="${message}"/>
     </c:if>
     <table border="5" bordercolor="#aaa" cellspacing="10" cellpadding="10">
        <colgroup>
        <col span = "1" style="width: 10%";>
        <col span = "1" style="width: 20%";>
        <col span = "1" style="width: 50%";>
        </colgroup>
        <tr>
        <th>Title</th>
        <th>Description</th>
        <th>Image</th>
        </tr>
        <c:forEach items="${cakes}" var="cake">
        <tr>          
             <th style="width:10%; "><c:out value="${cake.title}" /></th>
             <th style="width: 20%;"><c:out value="${cake.description}" /></th>
             <th style="width: 50%; height: 60%"><img width = "10%" src="${cake.image}" /></th>                
        </tr>
        </c:forEach>
     </table>
     <br><br><br>
         
     <h2>Add New Cake Here</h2>
     <form method="post" action="cakes">     
		<table>
			<tr><td>Cake Title:</td><td><input type="text" name="addCakeTitle"></td></tr>
			<tr><td>Description:</td><td><input type="text" name="addCakeDesc"></td></tr>
			<tr><td>Image:</td><td><input type="text" name="addCakeImg"></td></tr>
			<tr><td></td><td><input type="submit" value="Add Cake"></td></tr>
		</table>
	</form>
	
	<br><br><br>
	<h2>Get the list of cakes</h2>
	<form method="get" action="download">
		<input type="submit" value = "Download">
	</form>
</body>
</html>