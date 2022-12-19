<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
											<p><a href="">1:1문의하기</a></p>
	<c:forEach var="qlist" items="${QnaList}">
				<p>${qlist.qnatitle}</p>
				<p>${qlist.name}</p>
				<p>${qlist.qnadate}</p>
				<p>${qlist.qnaanswer}</p>
				
	</c:forEach>
</body>
</html>