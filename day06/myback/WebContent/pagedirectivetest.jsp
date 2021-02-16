<%@page import="java.io.IOException"%>
<%@page import="java.io.FileReader"%>
<%@ page language="java" 
		contentType="text/html; charset=UTF-8"
    	pageEncoding="UTF-8"
    	errorPage="errorresult.jsp"
    	buffer="1024kb"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page지시자</title>
</head>
<body>
<ul>
<%
for(int i=0; i<10000; i++) {
%>
<li><%=i%></li>
<%
}
%>
</ul>
<%--
FileReader fr = null;
try {
	fr = new FileReader("a.txt");	
} catch(IOException e) {
	out.print("<h1>" + e.getMessage() + "</h1>");
}
--%>
<%
FileReader fr = null;
fr = new FileReader("a.txt"); //예외가 발생하면 page지시자의 errorPage속성값에 해당하는 URL로 자동 forward가 된다.
%>


</body>
</html>