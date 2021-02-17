# day07

![day07%200c12339652dd4c37908d2ffaea340815/Untitled.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled.png)

로그아웃이 클릭이 되면 요청할 서버 쪽의 url을 결정 해줘야 한다. ./logout 을 요청하면 서버 쪽에서는 Servlet이 요청을 받을 거다. 먼저 세션객체를 얻는다는 것은 서버사이드 쪽에서 클라이언트 전용 객체 이다. 섹션이 갖고 있는 id로 구분한다. 그리고 로그인 정보 용 속성 제거하는 작업으로 구성이 되어 있다. 같은 웹 브라우저상에 로그인에 사용하는 세션 객체랑 로그아웃에 사용하는 세션 객체는 서로 같다.

서버가 클라이언트 쪽으로 응답을 할 때 요청 자체를 ajax 자체로 요청이 되고 응답도 ajax 형태로 구성이 되어 있다. 응답된 내용을 가지고서 로그인이 되면 alert창으로 응답 내용이 {status:1} 이라는 json형태 객체로 응답이 된다. 메뉴가 바뀌게 위해서 주소 url을 바꿔야 한다. 주소 url을 바꾸면 클라이언트 쪽에서 서버로 다시 welcom-page가 요청이 된다. 확장자가 jsp이기 때문에 서버 쪽에서 실행이 된다. jsp에서는 해당 세션이라는 변수가 미리 선언이 되어 있어서 request.getSession을 선언한 것과 같은 효과를 나타냄으로 안 써도 된다.

로그인 서블릿, 시멘팃, 로그아웃 세션이 모두 같은 섹션 객체를 참조한다.

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%201.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%201.png)

로그아웃일 경우 응답 내용을 section의 article에 출력하지 않고 주소 url만 바꿔주자

- semanticcssjq.jsp

```jsx
<%@page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>시멘틱태그-CSS-jQuery[JSP]</title>
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
            header, footer { height:100px; /* width: 1100px; */ width:100%; }
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
                height: 100%;
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
            section{
                background-color:#eef2f3; 
                width: 100%;
                height:500px; 
                margin : 5px;
            }
            section>article{
                width: 80%;
                height: 100%;
                float: left;
            }
           
            section>aside{
                width: 20%;
                height:100%;
                float: right;               
            }
            section>aside>div.strawberry, section>aside>div.plcc{
            	width: 100%;
            }
            /* 광고이미지 반응*/
            section>aside .pc-badge{
            	width: 80%;
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
               		width: 80%;
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
                $menuObj.click(function(event){
                    $("section>article").empty(); //aricle영역 지우기
                    //메뉴객체의 href속성값 얻기
                    var hrefValue = $(event.target).attr("href");//ex)login.html, signup.html
                    switch(hrefValue){//메뉴별
                    case 'logout': //로그아웃메뉴
                    	$.ajax({
                    		url:hrefValue,
                    		method: 'get',
                    		success: function(data){//성공응답
                    			location.href='http://localhost:8888/myback';
                    		},
                    		error: function(jqXHR){//실패응답
                                alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                            } 
                    	});
                    	break;
                    default: //그외의 메뉴
                    	//AJAX
                        $.ajax({
                            url: hrefValue, //요청URL
                            method: "get", //요청방식
                            success: function(data){ //data는 응답내용
                                	$("section>article").html(data);
                            }, //성공응답
                            error: function(jqXHR){
                                alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                            } //실패응답
                        });
                    	break;
                    }
                	return false; //event.preventDefault();와 event.stopPropagation();실행한것과 같음
                });
            
              	//상품목록메뉴 클릭이벤트를 강제 발생
            	//DOM트리에서 상품목록메뉴객체 찾기
            	//console.log($("header>nav>ul>li>a[href=productlist]"));
            	$("header>nav>ul>li>a[href=productlist]").trigger("click");
            	
            });
        </script>
    </head>
    <body>
        <header>
            <!--<h1>스타벅스</h1>-->
            <h1><a href="#"></a></h1>
            <nav class="large">
                <ul>
 <%
 if(session.getAttribute("loginInfo") == null){ //로그인성공안된 경우
 %>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>
                    <li><a href="productlist">상품목록</a></li>
                    <li><a href="viewcart.html">장바구니</a></li>
                </ul>
            </nav>
            <nav class="small">
                <ul>
 <%
 if(session.getAttribute("loginInfo") == null){
 %>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>                 
                </ul>
            </nav>
			
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

- LogoutServlet.java

```java
package com.my.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo");
	}

}
```

---

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%202.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%202.png)

장바구니를 클릭했을 때 ajax로 요청할 url을 요청할지 결정 해야 한다. post방식이 아닌 get방식으로 요청을 할 거고 요청 시 전달할 데이터는 상품 번호와 수량 정보만 

장바구니에 넣어야 할 자료가 1)상품 번호만 저장이 될 것인지 아니면 2)상품 번호, 상품 명, 가격 모두가 저장이 되어야 하는지 고려 해야 한다. 

1)상품 번호만 저장이 된다면 장바구니 보기 메뉴를 볼 때 세션에 저장되어있던 화면에 보여줘야 하는데 상품 번호만을 볼 수 없으니깐 상품 명, 가격이 보여야 하는데 없으니깐 db에서 자료를 조회를 해야 하는 경우이다.  

2)상품 번호, 상품 명, 가격이 모두 장바구니에 저장이 됨으로 db에 자료를 조회할 필요가 없다.

근데 여기서 2)하게되면 모든 자료를 장바구니에 저장 해놨으면 사용하는 와중에 db의 상품 가격을 변경 시켰으면 장바구니의 가격 정보와 db의 가격 정보가 달라진다. 문제가 된다.

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%203.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%203.png)

그래서 장바구니에 넣을 때는 상품 번호와 수량만 넣자 상품 번호, 상품 명, 가격은 DB에서 조회를 할 거다.

Servlet 세션 객체에 로그인 정보, 장바구니 정보를 저장 할 거다. attr에 대한 값은 Object타입으로upcasting 되어서 저장이 된다. 근데 "cart" 값은 어떤 타입이 되어야 할지 생각해야 한다. String타입으로 저장하기에는 역 부족이다. 가장 적합한 자료형은 자료구조 형태가 되어야 한다. List, Map 중 어느게 적합할지 고려 해야 한다. 두 개의 자료가 저장이 되어야 한다. 여기서는 Map타입이 가장 적합한 자료구조 형태이다. 

cart에 저장되는 형태를 List 형태로 Product타입으로 설계를 하면 안되나? 하면 잘못된 방법이다

왜냐하면 quantitiy는 Product클래스의 멤버 변수로 설정하면 안된다. 아키텍쳐 설계를 잘못한 거다.

이거는 상품에 대한 정보와 장바구니에 대한 정보가 섞여서 정보가 됨으로 단일 책임 원칙에 어긋나는 거다. 즉 SOLID(객체지향설계)에 어긋난다. 그래서 cart에 대한 정보는 카트vo를 따로 만들거나 해야 한다.

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%204.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%204.png)

요청 전달 데이터 얻고 세션 얻고 그리고 cart속성 값을 얻는다. 만약 cart가 null이라면 즉 cart 자체가 없는 경우 객체 생성하고 속성을 추가해야 한다. 그리고 cart의 값으로 상품 번호와 수량을 추가해야 한다. 

응답 된 내용이 빈 내용이면 ajax로 콜백함수에 처리 해주면 되는데 두 개의 경우 수로 보여줄 거다. 팝업 창 띄우기를 해서 "계속하기" 또는 "장바구니 보기" 두 개의 메뉴를 갖고 있는 팝업 창을 띄울 거다. 

- PutCartServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PutCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달 데이터 얻기
		String prod_no = request.getParameter("prod_no");
		String quantity = request.getParameter("quantity"); //request.getParameter는 String타입으로 받는다 그러므로 int타입으로 변환 시켜줘야 한다.
		int intQuantity = Integer.parseInt(quantity);
		
		//세션(클라이언트별객체)얻기
		HttpSession session = request.getSession();
		//cart라는 이름의 속성값 얻기
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		
		//cart가 없으면
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		
		//cart에서 상품번호에 해당하는 수량을 얻기
		Integer quantity2 = cart.get(prod_no);
		if(quantity2 != null) { //cart에 상품번호가 있는경우
			intQuantity += quantity2; //수량을 증가
		}
		System.out.println("장바구니에 넣기 할 상품번호:" + prod_no +", 수량:" + intQuantity);
		//cart에 상품번호, 수량추가
		cart.put(prod_no, intQuantity);
		
		//장바구니확인 테스트
		System.out.println("장바구니 넣기 확인");
		Set<String> keys = cart.keySet();
		for(String key: keys) {
			System.out.println(key + ":" + cart.get(key));
		}
	}

}
```

- productlistresult.jsp

```java
<%@page import="com.my.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Product p = (Product)request.getAttribute("p"); //requst에 저장되는 Attribute는 저장될 때 무조건 객체 형태로 저장되기때문에 ()원래타입으로 강제 캐스팅해야한다.
%>
<script>
$(function() {
	$('div.productdetail>div.detail>ul>li>input[type=button]').click(function(){
		var prod_no = '<%=p.getProd_no()%>'; //JSP로 찾은 걸 javascript 변수에 넣음
		var quantity = 
$('div.productdetail>div.detail>ul>li>input[type=number]').val();
		var url = "./putcart";
		var method = "get";
		var data = {prod_no: prod_no, 
				    quantity: quantity};
		$.ajax({
			url: url,
			method: method,
			data: data,
			success: function(data) {
				alert('장바구니 넣기 성공: 상품번호=' + prod_no + ", 수량=" + quantity);
			}
		})
	});
});
</script>
<div class="productdetail" style="width:500px;">
	<img src="./images/<%=p.getProd_no()%>.jpg" style="float:left; width:35%">
	<div class="detail" style="float:right;">
		<ul style="list-style-type: none; padding: 0 10px; margin: 0">
			<li>상품번호: <%=p.getProd_no()%></li>
			<li>상품명: <%=p.getProd_name()%></li>
			<li>가격: <%=p.getProd_price()%></li>
			<li>수량: <input type="number" value="1" min="1" max="99"></li>
			<li><input type="button" value="장바구니 넣기"></li>
		</ul>
	</div>
</div>
```

장바구니 수량 계속 유지 하면서 다시 또 수량을 추가하여 누적 시키게 할 것인지 아님 추가한 거로 업데이트 할 것인지 방법들이 있는데 누적되는 방법으로 해보자

실행결과>

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%205.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%205.png)

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%206.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%206.png)

---

---

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%207.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%207.png)

myfront에 있는 viewcart.html 복사해서 myback에 있는 WebContent에 붙여넣자

cart이름의 attribute에 실제 장바구니에 해당하는 상품 번호, 상품 값들이 들어있다. 얘를 서블릿에서 세션을 얻고 카트들을 얻어낸다. 상품 이름이나 값은 저장 되어 있지 않기 때문에 1)세션을 얻고 세션에 저장되어 있는 2)속성 cart얻고 3) Map형태로 생성하고 4)cart 상품 별 상품 정보를 DB에서 검색한다. product객체(key)와 수량(value)을 Map에 추가 그리고 5-1)응답 5-2)request의 속성으로 Map을 추가 시키고 JSP로 이동한다. 그리고 응답한다. 

5-1), 5-2) 둘 중 골라야 한다. 5-2)는 응답 형식을text/html 방식이다. 응답 내용은 Map을 html태그로 처리 한다. 5-1)는 응답 형식을 text/html로 하게 되면 out.print("<table>")형식으로 응답 해야 하므로 복잡하다. 그래서 application/json 형태로 응답 하자~ 그러면 결국 자바스크립트 객체 형태로 응답 해야 하는데 응답 내용은 추가 해 놓은 Map을 JSON형식으로 응답 해줘야 된다.

html 형식으로 응답은 이미 전에 해봤기 때문에 5-1) JSON형식으로 응답을 해보자

장바구니 넣기에서 성공 뿐만 아니라 팝업 창에 띄어서 계속하기, 장바구니 보기까지 만들어 보자

- semanticcssjq.jsp

```java
<%@page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>시멘틱태그-CSS-jQuery[JSP]</title>
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
            header, footer { height:100px; /* width: 1100px; */ width:100%; }
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
                height: 100%;
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
            section{
                background-color:#eef2f3; 
                width: 100%;
                height:500px; 
                margin : 5px;
            }
            section>article{
                width: 80%;
                height: 100%;
                float: left;
            }
           
            section>aside{
                width: 20%;
                height:100%;
                float: right;               
            }
            section>aside>div.strawberry, section>aside>div.plcc{
            	width: 100%;
            }
            /* 광고이미지 반응*/
            section>aside .pc-badge{
            	width: 80%;
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
               		width: 80%;
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
                $menuObj.click(function(event){
                    $("section>article").empty(); //aricle영역 지우기
                    //메뉴객체의 href속성값 얻기
                    var hrefValue = $(event.target).attr("href");//ex)login.html, signup.html
                    switch(hrefValue){//메뉴별
                    case 'logout': //로그아웃메뉴
                    	$.ajax({
                    		url:hrefValue,
                    		method: 'get',
                    		success: function(data){//성공응답
                    			location.href='http://localhost:8888/myback';
                    		},
                    		error: function(jqXHR){//실패응답
                                alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                            } 
                    	});
                    	break;
                    default: //그외의 메뉴
                    	//AJAX
                        $.ajax({
                            url: hrefValue, //요청URL
                            method: "get", //요청방식
                            success: function(data){ //data는 응답내용
                                	$("section>article").html(data);
                            }, //성공응답
                            error: function(jqXHR){
                                alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                            } //실패응답
                        });
                    	break;
                    }
                	return false; //event.preventDefault();와 event.stopPropagation();실행한것과 같음
                });
            
              	//상품목록메뉴 클릭이벤트를 강제 발생
            	//DOM트리에서 상품목록메뉴객체 찾기
            	//console.log($("header>nav>ul>li>a[href=productlist]"));
            	$("header>nav>ul>li>a[href=productlist]").trigger("click");
            	
            });
        </script>
    </head>
    <body>
        <header>
            <!--<h1>스타벅스</h1>-->
            <h1><a href="#"></a></h1>
            <nav class="large">
                <ul>
 <%
 if(session.getAttribute("loginInfo") == null){ //로그인성공안된 경우
 %>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>
                    <li><a href="productlist">상품목록</a></li>
                    **<li><a href="viewcart">장바구니</a></li>**
                </ul>
            </nav>
            <nav class="small">
                <ul>
 <%
 if(session.getAttribute("loginInfo") == null){
 %>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>                 
                </ul>
            </nav>
			
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

장바구니 요청 url을 viewcart.html → viewcart로 만들자

- productlistresult.jsp

```java
<%@page import="com.my.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Product p = (Product)request.getAttribute("p");
%>
<script>
$(function(){
	$('div.productdetail>div.detail>ul>li>input[type=button]').click(function(){
		var prod_no = '<%=p.getProd_no()%>';
		var quantity = 
$('div.productdetail>div.detail>ul>li>input[type=number]').val();
		var url = "./putcart";
		var method = "get";
		var data = {prod_no:prod_no, 
				    quantity: quantity};
		$.ajax({
			url:url,
			method: method,
			data: data,
			success: function(data){
alert('장바구니 넣기 성공: 상품번호=' + prod_no + ", 수량=" + quantity);
				$("div.bg").show();
			}
		});
	});
	
	var bg = $('div.bg');
	
    $("div.modal").find("span.productlist").click(function(event){
    	alert("계속하기 클릭");
	    bg.hide();//배경레이어용DIV 사라지기
	    
	    //상품목록메뉴에 클릭이벤트 강제발생
	    $("body > header > nav.large > ul > li:nth-child(3) > a[href=productlist]").trigger(event);
	    
	});

	$("div.modal").find("span.viewcart").click(function(){
	    alert("장바구니보기 클릭");
	    bg.hide();//배경레이어용DIV 사라지기
	});

});
</script>
<style>
    div.bg{
        display: none;
        position: fixed;
        z-index: 9999;
        border: 1px solid;
        left: 0px;
        top: 100px;
        width: 100%;
        height: 100%;
        margin: 10px;
        overflow: auto;
        /* 레이어 색깔 */
        background-color: rgba(199, 196, 196, 0.4)
    }
    div.modal{
        display: block;
        position: relative;
        border: 1px solid orange;
        border-radius: 10px;
        background-color: papayawhip;
        /* box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); */

        /* 배경레이어 보다 앞에 보이기*/
        z-index:10000;
        width: 40%;
        height: 100px;
        /* div center 정렬*/
        top: 40%;
        /* left: 50%; */
        margin: 0 auto;
        text-align: center;
        line-height: 100px;
    }
    #my_modal>span{
        margin-left: 10px;
        margin-right: 10px;
    }
    #my_modal>span:hover{
        background-color: darkblue;
        color: white;
    }
</style>
<div class="productdetail" style="width:500px;">
   <img src="./images/<%=p.getProd_no()%>.jpg" style="float:left; width:40%">
   <div class="detail" style="float:right;">
      <ul style="list-style-type: none; padding: 0 10px; margin: 0">
         <li>상품번호: <%=p.getProd_no()%></li>
         <li>상품명: <%=p.getProd_name()%></li>
         <li>가격: <%=p.getProd_price()%></li>
         <li>수량: <input type="number" value="1" min="1" max="99"></li>
         <li><input type="button" value="장바구니 넣기"></li>
      </ul>
   </div>
</div>
<div class="bg">
    <div class="modal">
        <span class="productlist" >계속하기</span> <span class="viewcart">장바구니보기</span>
    </div>
</div>
```

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%208.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%208.png)

fixed는 완전히 고정된 위치, relative 부모의 상대적 위치 article영역 기준

실행결과>

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%209.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%209.png)

계속하기 클릭 시 상품 목록들이 나타나도록  하자

---

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%2010.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%2010.png)

Jsp이동되서 응답하는 게 아니라 Servelt controller가 JSON형태로 응답해보자

배열 묶음 처리가 돼서 응답이 되어야 한다. JSON 처리를 편리하게 도와줄 수 있는 라이브러리를 쓸 거다. 이 라이브러리의 종류는 굉장히 많다. 종류로는 Jackson, Gson 등 있다. 라이브러리를 사용하면out.print작업들을 직접 안 해도 된다.

- ViewCartServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		if(cart == null || cart.size() == 0) { //장바구니가 없거나 장바구니가 비어있다면
			out.print("{\"status\": -1}");
			return;
		}
		//장바구니가 있는 경우
		//맵생성
		Map<Product, Integer> map = new HashMap<>();
		
		//장바구니의 상품번호별 상품 정보얻기
		Set<String>prod_nos = cart.keySet();
		ProductService service = new ProductService();
		for(String prod_no: prod_nos) {
			
			try {
				Product p = service.findByNo(prod_no); //상품번호별 상품정보얻기
				int quantity = cart.get(prod_no); //장바구니에 담긴 수량
				map.put(p, quantity); //맵에 추가
			} catch (FindException e) { //갑자기 db와의 연결 끊기거나 장바구니 넣을때가지도 상품이 있었는데 잠깐 자리를 비운 사이 db에 상품정보가 삭제 된 경우
				e.printStackTrace();
			}
			
		}
		
		//맵의 내용을 JSON형태로 응답하기
		int index = 0;
		out.print("[");
		for(Product p: map.keySet()) {
			if(index > 0) {
				out.print(",");
			}
			int quantity = map.get(p);
			out.print("{\"prod_no\": \""+ p.getProd_no() +"\", "
					+ "\"prod_name\":\""+ p.getProd_name() +"\", "
					+ "\"prod_price\": "+ p.getProd_price() +", "
					+ "\"quantity\":   "+ quantity +" }");
			index++;
		}
		out.print("]");
	}

}
```

- semanticcssjq.jsp

```java
<%@page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>시멘틱태그-CSS-jQuery[JSP]</title>
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
            header, footer { height:100px; /* width: 1100px; */ width:100%; }
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
                height: 100%;
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
            section{
                background-color:#eef2f3; 
                width: 100%;
                height:500px; 
                margin : 5px;
            }
            section>article{
                width: 80%;
                height: 100%;
                float: left;
            }
           
            section>aside{
                width: 20%;
                height:100%;
                float: right;               
            }
            section>aside>div.strawberry, section>aside>div.plcc{
            	width: 100%;
            }
            /* 광고이미지 반응*/
            section>aside .pc-badge{
            	width: 80%;
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
               		width: 80%;
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
                $menuObj.click(function(event){
                    $("section>article").empty(); //aricle영역 지우기
                    //메뉴객체의 href속성값 얻기
                    var hrefValue = $(event.target).attr("href");//ex)login.html, signup.html
                    switch(hrefValue){//메뉴별
                    case 'logout': //로그아웃메뉴
                    	$.ajax({
                    		url: hrefValue,
                    		method: 'get',
                    		success: function(data){//성공응답
                    			location.href='http://localhost:8888/myback';
                    		},
                    		error: function(jqXHR){//실패응답
                                alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                            } 
                    	});
                    	break;
                    case 'viewcart':
                    	$.ajax({
                    		url: hrefValue,
                    		method: 'get',
                    		success: function(responseObj){
                    			alert(responseObj.status);
                    			if(responseObj.status == undefined) { //장바구니가 있는경우
                    				var $tableObj = $("<table>"); //객체생성 //$("table") <- 선택자 이용해서 객체를 DOM에서 찾기
                    				//$tableObj.html("<tr><th>상품번호</th><th>상품명</th><th>가격</th><th>수량</th></tr>");
                    				var tableData = "<tr><th>상품번호</th><th>상품명</th><th>가격</th><th>수량</th></tr>";
                    				var arr = responseObj;
                    				$(arr).each(function(index, element){
                    					//console.log("장바구니 내용 상품번호-" + element.prod_no + ", 상품명-" + element.prod_name + ", 상품가격-" + element.prod_price + ", 수량-" + element.quantity);
                    					tableData += '<tr>';
                    					tableData += '<td>';
                    					tableData += element.prod_no;
                    					tableData += '</td>';
                    					
                    					tableData += '<td>';
                    					tableData += element.prod_name;
                    					tableData += '</td>';
                    					
                    					tableData += '<td>';
                    					tableData += element.prod_price;
                    					tableData += '</td>';
                    					
                    					tableData += '<td>';
                    					tableData += element.quantity;
                    					tableData += '</td>';
                    					tableData += '</tr>';
                    				});
                    				$tableObj.html(tableData);
                    				$("section>article").append($tableObj);
                    			} else if(responseObj.status == -1) { //장바구니가 없는경우
                    				
                    			}
                    		}
                    	});
                    	break;
                    default: //그외의 메뉴
                    	//AJAX
                        $.ajax({
                            url: hrefValue, //요청URL
                            method: "get", //요청방식
                            success: function(data){ //data는 응답내용
                                	$("section>article").html(data);
                            }, //성공응답
                            error: function(jqXHR){
                                alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                            } //실패응답
                        });
                    	break;
                    }
                	return false; //event.preventDefault();와 event.stopPropagation();실행한것과 같음
                });
            
              	//상품목록메뉴 클릭이벤트를 강제 발생
            	//DOM트리에서 상품목록메뉴객체 찾기
            	//console.log($("header>nav>ul>li>a[href=productlist]"));
            	$("header>nav>ul>li>a[href=productlist]").trigger("click");
            	
            });
        </script>
    </head>
    <body>
        <header>
            <!--<h1>스타벅스</h1>-->
            <h1><a href="#"></a></h1>
            <nav class="large">
                <ul>
 <%
 if(session.getAttribute("loginInfo") == null){ //로그인성공안된 경우
 %>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>
                    <li><a href="productlist">상품목록</a></li>
                    <li><a href="viewcart">장바구니</a></li>
                </ul>
            </nav>
            <nav class="small">
                <ul>
 <%
 if(session.getAttribute("loginInfo") == null){
 %>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>                 
                </ul>
            </nav>
			
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

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%2011.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%2011.png)

메모장에 먼저 JSON코드를 만들고 복사해서 out.print(""); ""안에 붙여 넣기

실행결과>

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%2012.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%2012.png)

이 내용이 표 형태로 보여질 수 있도록 만들어 줘야 한다.

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%2013.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%2013.png)

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%2014.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%2014.png)

---

![day07%200c12339652dd4c37908d2ffaea340815/Untitled%2015.png](day07%200c12339652dd4c37908d2ffaea340815/Untitled%2015.png)

장바구니 목록 보기에서는 응답 내용을 front가 직접 테이블 객체를 생성해서 json 내용을 끼워 놓고 article영역에 보여주기 까지 front가 해야 한다. 서버와 클라이언트를 완전히 분리 해주는 역할을 하면서 front에게 일 처리를 맡기는 역할을 하는 거다.  서블릿이 직접 결과 값을 응답하는 일을 한다. 컨트롤러의 역할을 하면서 결과 값을 응답하는 역할을 한다. 

반대로 상품목록보기에서는 서블릿이 응답을 안하고 jsp페이지로 forward 해서 html형태로 응답하는 구조로 했다. 이런 구조를 MVC 패턴이라 부른다

요즘 트랜드는 왼쪽처럼 하는 것이 요즘 사용되는 트랜드이다. 장점으로는 서버 쪽의 코딩하고 프론트 쪽의 코드가 완벽히 분리될 수 있다. jsp안 쓴다 그러면 jsp 안에 html태그가 들어갈 일도 없다. html 태그는 클라이언트 사이드에서 실행되는 확장자가 .html페이지만 갖는 거다.

오른쪽은 html, div 등 jsp페이지에서 만든다 그래서 내용이 많아지면 유지 보수가 어려워 진다. 그래서 왼쪽처럼 하는 것을 권장한다.