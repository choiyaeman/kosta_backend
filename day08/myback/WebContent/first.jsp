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
      page directive : .java파일이 generated될때 필요한 정보를 기술(pagedirectivetest.jsp)
      속성들 - import,  
          contentType,
          buffer: 응답 내용이 쌓인 버퍼 크기를 설정 none또는 kb단위로 크기설정 가능. 기본값은 8kb, 
          autoflush,
          errorPage : 페이지에서 예외가 발생하면 자동 이동 될 url을 기술(ex: errorPage= "errorresult.jsp"), 
          isErrorPage : 일반페이지가 아니라 예외처리전용 페이지를 알릴 때 true값으로 기술. exception이라는 미리 선언된 변수 사용가능 (ex: isErrorPage="true")),
          session,
          language(생략가능), pageEncoding(생략가능)
         
      include directive : .java파일이 generated될때 다른 자원을 포함(정적포함)
      taglib directive
      
      
    3.action tag element
      jsp:include action : 실행시 포함 (동적포함)
</body>
</html>