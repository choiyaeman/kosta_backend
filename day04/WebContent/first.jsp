<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
첫번째 JSP입니다
JSP의 구성요소
html element

jsp element
    1.scripting element
      1)scriptlet : .java파일 _jspService()내부에 들어감 <br>
      				<% int i=10; %>
      				<% out.print(i); %>
      				<% String a = request.getParameter("a"); %>
      2)expression : .java파일 _jspService()내부에 들어감
      				 out.print()가 자동 호출됨.
      				<%=i %>
      3)declaration : .java파일 _jspService()외부에 들어감
      	    메서드, 인스턴스변수 선언시에 사용
      	            <%!int i;//인스턴스변수 선언 %>
      <hr>
      i변수값 : <%=i %>
      i인스턴스변수값: <%=this.i %>
            
    2.directive element
      page directive
      include directive
      taglib directive
      
      
    3.action tag element
</body>
</html>