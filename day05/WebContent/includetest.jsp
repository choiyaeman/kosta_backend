<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>includetest.jsp</title>
</head>
<body>
인클루드 지시자를 이용한 포함: <%@include file="a.jsp"%>
<hr>
인클루드 태그를 이용한 포함: <jsp:include page="a.jsp"/>
</body>
</html>