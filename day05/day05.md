# day05

상품 목록에 대한 응답은 jsp에게 맡기고 servlet은 요청에 대한 제어를 한다.

servlet객체에서 만들어낸 계산 값을 jsp페이지에서 응답을 하려면 그 계산 값을 같이 공유를 해야 한다. servlet과 jsp사이의 객체가 서로 다르다 보니 공유 공간이 필요 한다 requet객체를 사용.

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled.png)

jsp페이지가 응답을 하도록 응답은 html페이지 형태로..서블릿이 직접 응답하기에는 지저분 jsp페이지에게 역할 분담을 맡기자. servlet페이지에서는 페이지 이동 시키는 역할을 하자

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%201.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%201.png)

attribute는 저장되는 값이 java.lang 패키지의 Object 타입으로 upcasting돼서 저장이 된다.

- ProductDetailServlet.java

```java
package com.my.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		String prod_no = request.getParameter("prod_no");
		
		//비지니스로직 호출
		ProductService service = new ProductService();
		try {
			Product p = service.findByNo(prod_no);
			//요청속성(속성명: "p", 속성값: p)으로 추가
			request.setAttribute("p", p);
			//VIEWER(JSP)로 이동
			String path = "/productdetailresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("e", e);
			String path = "/errorresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}

}
```

- productdetailresult.jsp

```java
<%@page import="com.my.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Product p = (Product)request.getAttribute("p");
%>
<div class="productdetail" style="width:500px;">
	<img src="./images/<%=p.getProd_no()%>.jpg" style="float:left; width:35%">
	<div class="detail" style="float:right;">
		<ul style="list-style-type: none; padding: 0 10px; margin: 0">
			<li>상품번호: <%=p.getProd_no()%></li>
			<li>상품명: <%=p.getProd_name()%></li>
			<li>가격: <%=p.getProd_price()%></li>
			<li>수량: <input type="number" value="1" min="1" max="99">
			<li><input type="button" value="장바구니 넣기">
		</ul>
	</div>
</div>
```

실행결과>

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%202.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%202.png)

Servlet에서 제어의 역할이지 jsp에서 제어 역할은 아니다. jsp는 결과 값을 보여주는 역할로 마무리 한다. 

Servlet소스 코드를 만들면 web.xml바뀌므로 그 때만 tocmat restart하는 것이고 jsp는 굳이 restart 안해도 된다.

- errorresult.jsp

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Exception e = (Exception)request.getAttribute("e");
%>
<script>
alert('<%=e.getMessage()%>');
</script>
```

실행결과>

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%203.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%203.png)

'<%=e.getMessage()%>' → ''는 자바스크립트 문법이고 ''안에 있는 문법은 jsp문법이다. jsp문법이 먼저 실행이 되고 out.write 이런 내용으로 클라이언트에게 응답이 된 다음에 클라이언트에서 자바스크립트가 실행되는 구조이다.

즉 서버에서 jsp먼저 실행하고 out. 클라이언트에게 응답 클라이언트에서 자바스크립트가 실행되는 구조이다.

- productlistresult.jsp

```java
<%@page import="com.my.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
            *{
                box-sizing: border-box; /* width값이 전체 너비 */
                padding: 0;
                margin: 0;
            }
            div.productlist{
                background-color: white;
                width: 100%;
                height: 600px;
                /* margin: 10px; */
                overflow: auto;
            }
            div.productlist>div.product{
                width: 320px;
                height: 400px;
                border: 1px solid;
                float: left;
            }
            div.productlist>div.product>ul{
                list-style-type: none;
                padding-left: 0;
            }
            div.productlist>div.product>ul>li>img{
            	widht : 80%;
            }
        </style>
        <!-- jquery사용 -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
        $(function(){
        	alert("productlist");
        	//DOM트리에서 class속성값이 product인 div객체찾기
        	var $productDivObj = $("div.productlist>div.product");
        	$productDivObj.each(function(index, element){
        		$(element).click(function(){
        			alert("상품이 클릭되었습니다.");
        			var imgSrc = $(this).find("ul>li.prod_no>img").attr("src");
        			
        			var lastSlash = imgSrc.lastIndexOf("/"); //마지막 슬래시 위치
        			var ext = imgSrc.indexOf(".jpg"); //.jpg파일확장자 위치
        			var prod_no = imgSrc.substring(lastSlash+1, ext); 
        			alert("상품이 클릭되었습니다. 상품번호 " +prod_no);
        			var prod_name = $(this).find("ul>li.prod_name").html();
        			var prod_price = $(this).find("ul>li.prod_price").html();
        			
        			$("section>article").empty();
        			var url = "./productdetail";
        			var method = "get";
        			var data = "prod_no=" + prod_no;
        			$.ajax({
        				url: url,
        				method: method,
        				data: data,
        				success: function(data){
        					$("section>article").html(data);
        				},
        				error: function(jqXHR){
        					alert("오류:" + jqXHR.status);
        				}
        			});
        		});
        	});
        });
        </script>
<div class="productlist">
<%
//3. 요청속성얻기
List<Product> list = (List)request.getAttribute("list");
for(Product p: list) {
%>
 <!-- 하나의 상품 -->
 <div class="product">
	 <ul>
		 <li class="prod_no"><img src="./images/<%=p.getProd_no() %>.jpg" alt="<%=p.getProd_name() %>"></li>
		 <li class="prod_name"><%=p.getProd_name() %></li>
		 <li class="prod_price"><%=p.getProd_price() %></li>
	 </ul>
 </div>
<%}
%>
</div>
```

콜백 함수의 매개변수 jquery each메서드는 첫 번째가 index매개변수, 두 번째가 element매개변수이다

- first.jsp

```java
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
```

- pagedirectivetest.jsp

```java
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
```

- errorresult.jsp

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isErrorPage = "true" %>
<%
System.out.println("예외내용:" + exception.getMessage());
%>
<%
Exception e = (Exception)request.getAttribute("e");
if( e == null) {
	return;
}
%>
<script>
alert('<%=e.getMessage()%>');
</script>
```

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%204.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%204.png)

페이지 지시자 에서 %와@이는 반드시 붙여야 한다. language속성은 java언어로 generate한다. 다른 언어로는 못한다. 기본 값이 자바이므로 항상 .java파일로만 만들어지게 되어있다. contentType은 응답 형식을 의미한다.language속성과 pageEncoding 생략 가능하다.

import, contentType 정도만 알아도 된다~

errorPage속성은 현재 페이지에서 에러 발생하면 특정 페이지 그 즉시 자동 포워드, isErrorPage를 true로 주게 되면 일반 페이지가 아니라 에러처리전용페이지이다. exception이라는 미리 선언되어 있는 변수를 미리 사용할 수 있다

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%205.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%205.png)

out은 JspWriter으로 미리 선언이 되어있다. 그러면 out은 JspWriter타입인데 응답의 버퍼에 쓰기를 하는 거다. li 내용이 버퍼에 쌓이는 거다. 버퍼의 기본 크기는 8키로 바이트이다. forward가 될 때 버퍼 내용을 clear하고 이동을 하므로 errorresult.jsp로 forward가 되는 거다. 

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%206.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%206.png)

JSPWriter는 PrintWriter + BufferedWriter 합쳐 놓은 형태이다. BufferedWriter는 8kb이다. 클라이언트 쪽에게 응답을 할 때 out객체의 내부에 버퍼 크기가 8키로 바이트가 있는 거다. 버퍼가 다 쓰여지지 않는 상태에서 그 다음 코드를 실행.. 자동 forward될 때 기존 내용을 지워 버리고 에러 페이지로 포워드가 된다. 그러므로 결과 값으로 화면에 보이지 않는다. 

근데 100번 반복 수행 했을 때와 10000번 반복 수행 했을 때 다른 이유? 응답 내용이 버퍼에 쌓이는 것인데 8kb 를 계속 쓰면서 만번 반복 을 통해서 8kb를 다 쓰게 되어있다. 버퍼가 꽉 채워지면 자동 플러쉬가 된다. 플러쉬란? 즉각 흘려 보낸다 의 의미 즉 8kb버퍼가 꽉 채워져 있으니깐 자동 응답이 돼버린다. 그 다음 버퍼가 클리어가 된다. 그 다음에 또 li요소를 채운다 예외 발생 시 내용을 다 clear시키고 에러 특정 페이지로 포워드 된다. 

결론적으로, 버퍼 크기에 따라 아주 많은 내용이 작성되었다가 아래 쪽에서 예외가 발생하게 되면 예외 발생된 위치로 자동 포워드가 될 수 있는데 자동 포워드 된 결과 값을 못 볼 수도 있고 지워지지 않고 보일 수도 있다. 

---

- a.jsp

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>a.jsp입니다</h1>
요청전달 데이터 opt=<%=request.getParameter("opt")%>입니다.
```

- includetest.jsp

```java
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
```

지시자를 이용한 포함과 태그를 이용한 포함의 차이점? include directive를 이용은 jsp용 점 자바 파일이 만들어질 때 소스 코드에 포함이 되는 것이고, action태그를 이용하면 include메소드가 호출 될 때 즉 실행 시 포함이 되는 거다.

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%207.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%207.png)

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%208.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%208.png)

b.jsp 점 자바 파일이 만들어지면  _jspService내부에 include 지시자 내용이 포함이 되고 출력하는 코드가 generate된다.

c.jsp 태그를 이용해서 포함한다면 _jspService메소드가 만들어질 때 a.jsp가 전달이 되도록 코드로 만들어진다. 출력 하는 코드는 i변수가 없으므로 만들어지지 않아 에러가 발생한다. 컴파일 절차가 거쳐야 실행이 되는 거다. include메소드도 당연히 실행이 될 수 없다.

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%209.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%209.png)

b.jsp 페이지에서 include 지시자를 이용해서 포함하고 int타입의 i변수를 99값을 대입하는 코드를 만들어 보면 자바에서 변수 선언이 중복되면 에러 클라이언트에게 500번오류가 나올것이고 . c.jsp같은 경우에도 i변수를 99값을 대입하는 코드를 만들고 _jspService안에 i변수가 선언되고 클라이언트에게 200 성공이 뜬다.

실행 결과 값이 포함되기를 원한다 하면 태그를 이용. 여러 페이지에서 공통으로 쓰일 share 개념은 아니지만  필요한 변수, 타이틀 같은 경우 지시자를 사용한다.

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%2010.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%2010.png)

상품을 검색할 수 있도록 상품 번호로 또는 상품 이름으로 검색 할 수 있도록 검색란을 포함 시키고 싶다 이 바깥쪽 전체가 productlistresult라면 jsp페이지 안쪽에 searchproduct.jsp페이지를 포함을 시키고 싶다.

- searchproduct.jsp

```java
<%@ page contentType="text/html; charset=UTF-8"%>
<div class="searchproduct">
	<input type="text" placeholder="검색할 상품번호를 입력하세요" name="product">
	<input type="button" value="검색">
</div>
```

- productlistresult.jsp

```java
<%@page import="com.my.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
            *{
                box-sizing: border-box; /* width값이 전체 너비 */
                padding: 0;
                margin: 0;
            }
            div.productlist{
                background-color: white;
                width: 100%;
                height: 600px;
                /* margin: 10px; */
                overflow: auto;
            }
            div.productlist>div.product{
                width: 320px;
                height: 400px;
                border: 1px solid;
                float: left;
            }
            div.productlist>div.product>ul{
                list-style-type: none;
                padding-left: 0;
            }
            div.productlist>div.product>ul>li>img{
            	widht : 80%;
            }
        </style>
        <!-- jquery사용 -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
        $(function(){
        	alert("productlist");
        	//DOM트리에서 class속성값이 product인 div객체찾기
        	var $productDivObj = $("div.productlist>div.product");
        	$productDivObj.each(function(index, element){
        		$(element).click(function(){
        			alert("상품이 클릭되었습니다.");
        			var imgSrc = $(this).find("ul>li.prod_no>img").attr("src");
        			
        			var lastSlash = imgSrc.lastIndexOf("/"); //마지막 슬래시 위치
        			var ext = imgSrc.indexOf(".jpg"); //.jpg파일확장자 위치
        			var prod_no = imgSrc.substring(lastSlash+1, ext); 
        			alert("상품이 클릭되었습니다. 상품번호 " +prod_no);
        			var prod_name = $(this).find("ul>li.prod_name").html();
        			var prod_price = $(this).find("ul>li.prod_price").html();
        			
        			$("section>article").empty();
        			var url = "./productdetail";
        			var method = "get";
        			var data = "prod_no=" + prod_no;
        			$.ajax({
        				url: url,
        				method: method,
        				data: data,
        				success: function(data){
        					$("section>article").html(data);
        				},
        				error: function(jqXHR){
        					alert("오류:" + jqXHR.status);
        				}
        			});
        		});
        	});
        });
        </script>
<div class="productlist">
**<%@include file="searchproduct.jsp" %>**
<%
//3. 요청속성얻기
List<Product> list = (List)request.getAttribute("list");
for(Product p: list) {
%>
 <!-- 하나의 상품 -->
 <div class="product">
	 <ul>
		 <li class="prod_no"><img src="./images/<%=p.getProd_no() %>.jpg" alt="<%=p.getProd_name() %>"></li>
		 <li class="prod_name"><%=p.getProd_name() %></li>
		 <li class="prod_price"><%=p.getProd_price() %></li>
	 </ul>
 </div>
<%}
%>
</div>
```

- semanticcssjq

```java
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>시멘틱태그-CSS-jQuery</title>
        <style>
            * { 
                box-sizing: border-box;

                color : #000000; 
                /* font-size: 1.25em; */
            }
            header { background: #f6f5ef; 
                     margin: 5px auto;
                     position: relative;
            } 
            /*section{background-color:#eef2f3; height:500px; margin-bottom: 5px;}
            section>article.one{background-color: #b3ffd9}
            section>article.two{background-color: #1e3932;color:white;}*/
            
            footer{ background-color: #2C2A29;  color:#fff;  }
                    header, footer { height:100px; width: 1100px;  }
                    header>nav>ul{ list-style-type: none; padding: 0px;}
                    header>nav>ul>li{ 
                    width:100px; 
                    display: inline-block; 
                    margin: 0px 10px; 
                    text-align: center;
                    }

            header>nav>ul>li>a{
                text-decoration: none;
            }
            header>nav>ul>li>a:hover{
                background-color: black;
                color: white;
                font-weight: bold;
            }
            
            header>h1{
                width: 30%;    
                margin: 0 auto;
                height: 100%;
                display: inline-block;
                position: relative;
                border: 1px solid;
            }
            /* 이미지 로고 */
            header>h1>a{
                display: block;
                width : 100%;
                height:100%;
                margin: 0;
                padding: 0;
                
                position: absolute;
                top: 10px;
                left: 0px;
                
                background-image: url('./images/logo.png');
                background-repeat: no-repeat;
            }
            header>nav{
                display: inline-block;
                width : 60%;
                height: 100%;
                border: 1px solid;
                position: absolute;
                top: 0px;
                right: 0px;
            }
            section{background-color:#eef2f3; 
                width: 1100px;
                height:500px; 
                margin-bottom : 5px;
            }
            section>article{
                width: 60%;
                height: 100%;
                float: left;
            }
            /* section>div{
                width: 60%;
                height: 100%;
                float: left;
            } */
            /* section>div>article.one{background-color: #b3ffd9;
                width: 100%;
                height:40%;
            } */
            /* section>div>article.two{background-color: #1e3932;color:white;
                width: 100%;
                height:40%;
            } */
            section>aside{
                width: 30%;
                height:100%;
                float: right;               
            }
            
            /* 광고이미지 반응*/
            section>aside .pc-badge{
                display: block;
            }
            section>aside .mobile-badge {
                display: none;
            }
            
            @media screen and (max-width: 960px){
                section>aside .pc-badge {
                    display: none;
                }
               section>aside .mobile-badge {
                   display: block;
                }
            }

            /*메뉴 반응*/
            header>nav.small{
                display:none;
            }
            @media screen and (min-width: 641px) and (max-width: 960px){
                header>nav.small{
                    display:inline-block;
                }
                header>nav.large{
                    display:none;
                }
            }
        </style>
        <!--jquery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(function(){
                //DOM트리에서 메뉴객체들 모두찾기
                var $menuObj = $("header>nav>ul>li>a");
                //메뉴가 클릭되면 
                $menuObj.click(function(){
                    $("section>article").empty(); //article영역 지우기
                    //메뉴객체의 href속성값 얻기
                    //var hrefValue = $menuObj.attr("href"); // (X) 배열의 무조건 0번index객체
                    //var hrefValue = $(this).attr("href");
                    var hrefValue = $(event.target).attr("href");//ex)login.html, signup.html
                    console.log(hrefValue);

                    //AJAX
                    $.ajax({
                        url: hrefValue, //요청URL
                        method: "get", //요청방식.   method속성은 생략가능하다 그러면 무조건 get방식이 되는거다. post방식으로 요청하려면 method속성값을 post라 쓰면 된다.
                        success: function(data){ //성공응답 data는 응답내용
                           //alert("AJAX요청응답 성공");
                           //console.log(data);
                           //article의 영역의 innterHTML로 설정
                           $("section>article").html(data);
                        },
                        error: function(jqXHR){ //실패응답 jqXHR는 jquery xmlRequest객체
                            alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                        } 
                    });
                    return false; //event.preventDefault();와 event.stopPropagation(); 실행한것과 같음
                });
            });
        </script>
    </head>
    <body>
        <header>
            <!--<h1>스타벅스</h1>-->
            <h1><a href="#"></a></h1>
            <nav class="large">
                <ul>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
                    <li><a href="productlist">상품목록</a></li>
                    <li><a href="viewcart.html">장바구니</a></li>
                </ul>
            </nav>
            <!-- <nav class="small">
                <ul>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>                    
                </ul>
            </nav> -->
			<!-- 
			~~<jsp:include page="menusmall.jsp"/>
			%include file="menusmall.jsp"%>~~
			 -->
        </header>
        <section>
            <article>article...</article>
            <!-- <div>
                <article class="one">
                스타벅스만의 특별한 혜택, 스타벅스 <mark>리워드</mark>
    스타벅스 회원이세요? 로그인을 통해 나만의 리워드를 확인해보세요.
    스타벅스 회원이 아니세요? 가입을 통해 리워드 혜택을 즐기세요.
                </article>
                <article class="two">
                    푸드 간편하지만 든든하게 채워지는 만족감을 느껴보세요. 신선함과 영양이 가득한 스타벅스 푸드가 완벽한 하루를 만들어 드립니다.
                </article>
            </div> -->
            <aside>
                <div class="strawberry">
                    <a href="https://www.starbucks.co.kr/whats_new/newsView.do?seq=4012" title="자세히 보기">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2021/strawberrymd_pc_210112.png" alt="" class="pc-badge">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2021/strawberrymd_mo_210112.png" alt="" class="mobile-badge">
                    </a>
                </div>
                <div class="plcc">      
                    <a href="/plcc/promotionView.do?eventCode=STH02" title="hyundai card + starbucks">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2020/plcc_badge_pc.png" alt="" class="pc-badge">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2020/plcc_badge_mobile.png" alt="" class="mobile-badge">
                    </a>
                </div>
            </aside>
        </section>
        <footer>
            사업자등록번호 : 201-81-21515 (주)스타벅스커피 코리아 대표이사 : 송 데이비드 호섭 TEL : 1522-3232 개인정보 책임자 : 장석현
ⓒ 2021 Starbucks Coffee Company. All Rights Reserved.
        </footer>        
    </body>
</html>
```

- menusmall.jsp

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="small">
     <ul>
        <li><a href="login.html">로그인</a></li>
        <li><a href="signup.html">가입</a></li>                    
     </ul>
</nav>
```

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%2011.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%2011.png)

jsp:include태그는 jsp문법이라서 html파일에서 쓰면 안된다.  html은 jsp를 포함할 수 없다.

방법 두 가지가 있다. 1. semanticcssjq.html를 jsp페이지가 되도록 하던지 2. html페이지를 유지하되 이 페이지가 요청되자마자 ajax로 menusmall.jsp를 요청해서 결과로 가져와서 메뉴 영역에 쓰기를 하는 방법이 있다.

---

실습>

![day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%2012.png](day05%201c15b7a85caa4e84b637d64d8edec879/Untitled%2012.png)