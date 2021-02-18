# day08

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
        <style>
        div.viewcart{
            box-sizing: border-box;
            width: 100%;
            height: 300px;
        }
        div.viewcart>table{
            border-collapse: collapse;
        }
        div.viewcart>table tr{
            
        }
        div.viewcart>table, div.viewcart>table th, div.viewcart>table td{
            border: 1px solid;
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
                    				**$tableObj.html(tableData);
                    				var $viewcartObj = $("<div class=viewcart>");
                    				var $h1Obj = $("<h1>장바구니</h1>");
                    				$viewcartObj.append($h1Obj);
                    				$viewcartObj.append($tableObj);
                    				var $btObj = $('<button>주문하기</button>'); //'<input type="button" value="주문하기">'
                    				$viewcartObj.append($btObj);
                    				$("section>article").append($viewcartObj);**
                    				//$("section>article").append($tableObj);
                    				
                    				**/* //DOM에 버튼객체 생성된 후 주문하기버튼 클릭시
                    				$btObj.click(function(){
                    					alert("주문하기 버튼 클릭 됨");
                    				}); */**
                    				
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
            	
            	**//DOM에 장바구니보기의 주문하기 버튼 객체가 없어서 click()함수가 효과없음!
            	/* $("div.viewcart>button").click(function(){
            		alert("주문하기 버튼 클릭!!!!");
            	}); */**
            	
            	**//--------------이벤트처리---------------------**
            	**//DOM에 향후 추가될 자식객체의 이벤트 처리
            	//$(부모객체).on(이벤트종류, 자식객체, 콜백함수)
            	//---장바구니보기 메뉴의 주문하기 버튼 클릭이벤트 START---
            	$("section>article").on('click', 'div.viewcart>button', function(){
            		alert("주문하기 버튼 클릭!!!!");
            	});
            	//---장바구니보기 메뉴의 주문하기 버튼 클릭이벤트 END---**
            	
            	
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

실행결과>

![1](https://user-images.githubusercontent.com/63957819/108333722-9afb3980-7214-11eb-9876-8275e10a6d38.png)

첫 번째 방법으로 돔트리에서 버튼 객체가 만들어진 상태에서 이벤트 처리와 두 번째 방법으로 첫 번째와 반대로 메뉴들이 클릭 되기 전 상태에 DOM트리에 객체가 있을리 없다. DOM 트리의 객체가 없으면 이벤트 처리가 효과가 없다. 효과를 내려면 DOM에 향후 추가될 자식 객체의 이벤트 처리를 하면 된다. on함수를 사용해서 click에 관련된 이벤트 처리는 따로 묶음 처리를 한다. 그러면 유지 보수 하기가 편리해진다.

---

![2](https://user-images.githubusercontent.com/63957819/108333730-9c2c6680-7214-11eb-9afa-5556bb1d6809.png)

![3](https://user-images.githubusercontent.com/63957819/108333732-9cc4fd00-7214-11eb-99da-01c603b0c95a.png)

![4](https://user-images.githubusercontent.com/63957819/108333734-9cc4fd00-7214-11eb-978b-66c35f02a66a.png)

\" 내용을 쉽게 처리해 줄 수 있는 jackson 라이브러리를 쓰자~ 라이브러리들 끼리의 의존성 의존 관계 즉 dependency라 한다. 

[https://mvnrepository.com/](https://mvnrepository.com/) → maven repository 아주 많은 라이브러리들이 등록되어 있다. 

![5](https://user-images.githubusercontent.com/63957819/108333736-9d5d9380-7214-11eb-8014-8cea32bb1c56.png)

- JacksonTest.java

```java
package com.my.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.vo.Product;

public class JacksonTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println("JAVA객체를 JSON문자열로 쓰기");
		Product p = new Product("C0001", "아메리카노", 1000);
		String jsonStr = mapper.writeValueAsString(p);
		System.out.println(p); //p.toString()자동호출
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		System.out.println("List객체를 JSON문자열로 쓰기");
		List<String> list = new ArrayList<>();
		list.add("ONE");
		list.add("TWO");
		jsonStr = mapper.writeValueAsString(list);
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		System.out.println("Map객체를 JSON문자열로 쓰기");
		Map<String, Integer> map = new HashMap<>();
		map.put("one", 1);
		map.put("two", 2);
		jsonStr = mapper.writeValueAsString(map);
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		
		Map<String, Object> map1 = new HashMap<>();
		map1.put("prod_no", p.getProd_no());
		map1.put("prod_name", p.getProd_name());
		map1.put("prod_price", p.getProd_price());
		map1.put("quantity", 5);
		jsonStr = mapper.writeValueAsString(map1);
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		
		List<Map> list2 = new ArrayList<>();
		for(int i=0; i<3; i++) {
			Map<String, Object> map2 = new HashMap<>();
			map2.put("prod_no", "C"+i);
			map2.put("prod_name", "Name"+i);
			map2.put("prod_price", i*1000);
			map2.put("quantity", i);
			list2.add(map2);
		}
		jsonStr = mapper.writeValueAsString(list2);
		System.out.println(jsonStr);
		System.out.println("----------------");

	}

}
```

실행결과>

![6](https://user-images.githubusercontent.com/63957819/108333740-9d5d9380-7214-11eb-96e7-a475d8c4c390.png)

`writeValueAsString()` 메서드는 json형태의 문자열로 바꿔주는 역할을 한다.

`readValue()` 메서드는 json문자열을 자바 객체로 변환할 때 쓰인다.

- ViewCartServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
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
//		int index = 0;
//		out.print("[");
//		for(Product p: map.keySet()) {
//			if(index > 0) {
//				out.print(",");
//			}
//			int quantity = map.get(p);
//			out.print("{\"prod_no\": \""+ p.getProd_no() +"\", "
//					+ "\"prod_name\":\""+ p.getProd_name() +"\", "
//					+ "\"prod_price\": "+ p.getProd_price() +", "
//					+ "\"quantity\":   "+ quantity +" }");
//			index++;
//		}
//		out.print("]");
		
		**//Jackson라이브러리 활용해서 맵의 내용을 JSON형태로 응답하기
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> list = new ArrayList<>();
		for(Product p: map.keySet()) {
			Map<String, Object> map1 = new HashMap<>();
			map1.put("prod_no", p.getProd_no());
			map1.put("prod_name", p.getProd_name());
			map1.put("prod_price", p.getProd_price());
			int quantity = map.get(p);
			map1.put("quantity", quantity);
			list.add(map1);
		}
		out.print(mapper.writeValueAsString(list));**
		
	}

}
```

---

![7](https://user-images.githubusercontent.com/63957819/108333741-9df62a00-7214-11eb-9086-35b008b778ae.png)

Customer 테이블에는 id라는 컬럼이 있고 Product테이블에는 prod_no 컬럼이 있다.

order_info에 Customer의 id를 참조, order_line이 Product의 prod_no 참조

orderline의 order_no가 order_info의 order_no를 참조

order_info에서 order_no가 pk

```sql
ALTER TABLE order_info ADD CONSTRAINT order_id_fk FOREIGN KEY(order_id)
  REFERENCES customer(id);

ALTER TABLE order_line ADD CONSTRAINT order_prod_no_fk FOREIGN KEY(order_prod_no)
  REFERENCES product(prod_no);

ALTER TABLE order_line ADD CONSTRAINT order_no_fk FOREIGN KEY(order_no)
  REFERENCES order_info(order_no);

SQL> SELECT order_seq.NEXTVAL FROM dual;

   NEXTVAL
----------
         1

SQL> DROP sequence order_seq;

Sequence dropped.

SQL> CREATE SEQUENCE order_seq;

Sequence created.
SQL> select * from order_info;

  ORDER_NO ORDER_ID   ORDER_DT
---------- ---------- --------
         2 id1        21/01/15
       999 id1        21/01/18

SQL> select * from order_line;

no rows selected

SQL> delete from order_info;

2 rows deleted.

SQL> commit;

Commit complete.
```

1.먼저 제약조건이름을 확인
select constraint_name, constraint_type, table_name FROM user_constraints 
WHERE table_name = 'ORDER_INFO';

2.삭제할 제약조건이름을 찾았으면
ALTER TABLE 테이블이름
DROP CONSTRAINT 제약조건이름;

![8](https://user-images.githubusercontent.com/63957819/108333744-9df62a00-7214-11eb-8e89-0fa251a8ffe5.png)

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
        <style>
        div.viewcart{
            box-sizing: border-box;
            width: 100%;
            height: 300px;
        }
        div.viewcart>table{
            border-collapse: collapse;
        }
        div.viewcart>table tr{
            
        }
        div.viewcart>table, div.viewcart>table th, div.viewcart>table td{
            border: 1px solid;
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
                    				var $viewcartObj = $("<div class=viewcart>");
                    				var $h1Obj = $("<h1>장바구니</h1>");
                    				$viewcartObj.append($h1Obj);
                    				$viewcartObj.append($tableObj);
                    				var $btObj = $('<button>주문하기</button>'); //'<input type="button" value="주문하기">'
                    				$viewcartObj.append($btObj);
                    				$("section>article").append($viewcartObj);
                    				//$("section>article").append($tableObj);
                    				
                    				/* //DOM에 버튼객체 생성된 후 주문하기버튼 클릭시
                    				$btObj.click(function(){
                    					alert("주문하기 버튼 클릭 됨");
                    				}); */
                    				
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
            	
            	//DOM에 장바구니보기의 주문하기 버튼 객체가 없어서 click()함수가 효과없음!
            	/* $("div.viewcart>button").click(function(){
            		alert("주문하기 버튼 클릭!!!!");
            	}); */
            	
            	**//--------------이벤트처리---------------------
            	//DOM에 향후 추가될 자식객체의 이벤트 처리
            	//$(부모객체).on(이벤트종류, 자식객체, 콜백함수)
            	//---장바구니보기 메뉴의 주문하기 버튼 클릭이벤트 START---
            	$("section>article").on('click', 'div.viewcart>button', function(){
            		//alert("주문하기 버튼 클릭!!!!");
            		$.ajax({
            			url: './putorder',
            			success: function(responseObj) {
            				if(responseObj.status == undefined) {
            					alert("주문성공");
            				} else if(responseObj.status == 0) { //로그인 안한경우
            					alert("로그인부터 하세요");
            				} else if(responseObj.status == -1) { //주문실패
            					alert("주문실패: " + responseObj.msg);
            				}
            			}
            		});
            		return false;
            	});
            	//---장바구니보기 메뉴의 주문하기 버튼 클릭이벤트 END---**
            	
            	
            	
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

---

- OrderInfo.java

```java
package com.my.vo;

import java.util.Date;
import java.util.List;

public class OrderInfo {
/*
 * ORDER_NO                                  NOT NULL NUMBER
 ORDER_ID                                  NOT NULL VARCHAR2(5)
 ORDER_DT                                           DATE
*/
	private int order_no;
	//private String order_id;//??
	private Customer c;
	private java.util.Date order_dt;
	private List<OrderLine> lines; //order_info가 order_line을 갖는다 -> has a 관계
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderInfo(int order_no, Customer c, Date order_dt, List<OrderLine> lines) {
		super();
		this.order_no = order_no;
		this.c = c;
		this.order_dt = order_dt;
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		return "OrderInfo [order_no=" + order_no + ", c=" + c + ", order_dt=" + order_dt + ", lines=" + lines + "]";
	}
	
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public Customer getC() {
		return c;
	}
	public void setC(Customer c) {
		this.c = c;
	}
	public java.util.Date getOrder_dt() {
		return order_dt;
	}
	public void setOrder_dt(java.util.Date order_dt) {
		this.order_dt = order_dt;
	}
	public List<OrderLine> getLines() {
		return lines;
	}
	public void setLines(List<OrderLine> lines) {
		this.lines = lines;
	}

}
```

![9](https://user-images.githubusercontent.com/63957819/108333747-9e8ec080-7214-11eb-8d14-0d4d37da81c9.png)

주문정보뿐만아니라 주문자 정보도 담아야 할 필요도 있다면 vo가 has a관계가 있어야 한다.

- OrderLine.java

```java
package com.my.vo;

public class OrderLine {
/*
 * ORDER_NO                                  NOT NULL NUMBER
 ORDER_PROD_NO                             NOT NULL VARCHAR2(5)
 ORDER_QUANTITY                            NOT NULL NUMBER(2)
 */
	private int order_no;
	//private String order_prod_no;
	private Product p;
	private int order_quantity;
	//private OrderInfo info; //관리자 입장에서 주문상세에대한 내역을 보려할때 
	public OrderLine() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderLine(int order_no, Product p, int order_quantity) {
		super();
		this.order_no = order_no;
		this.p = p;
		this.order_quantity = order_quantity;
	}
	
	@Override
	public String toString() {
		return "OrderLine [order_no=" + order_no + ", p=" + p + ", order_quantity=" + order_quantity + "]";
	}
	
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	
	
}
```

![10](https://user-images.githubusercontent.com/63957819/108333749-9f275700-7214-11eb-8a3e-a31ff92453e2.png)

~~private String order_prod_no~~; → private Product p;

![11](https://user-images.githubusercontent.com/63957819/108333752-9fbfed80-7214-11eb-9070-0cf461f1f743.png)

사용자 입장에서의 주문 내역보기

주문번호에 하나에 해당하는 여러 주문 상세내역을 보는거다. 1의 입장에서 many의 자료를 화면에 보여줘야 하기 때문에 order_info 1의 입장에서 여러 order_line들을 has a관꼐로 갖고 있는 것이 select 구문을 만족하는 처리다. 

 

- OrderDAO.java

```java
package com.my.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.OrderInfo;

public interface OrderDAO {
	/**
	 * 주문기본정보와 상세정보들을 저장소에 추가한다.
	 * @param info 주문기본정보와 상세정보들
	 */
	void insert(OrderInfo info) throws AddException; //OrderInfo 안에 lines가 있으니까 info하나만 있어도 된다.
	
	/**
	 * 주문자아이디가 주문한 주문기본 정보들을 검색한다.
	 * @param order_id 주문자 아이디
	 * @return 주문 상세들을 포함한 주문 기본목록들
	 * @throws FindException 주문정보가 없을때 예외 발생한다.
	 */
	List<OrderInfo>selectById(String order_id) throws FindException;
}
```

![12](https://user-images.githubusercontent.com/63957819/108333755-9fbfed80-7214-11eb-87d7-bcaeb3d2afad.png)

테이블이 order_info, order_line 두 개의 테이블이 있다. order_info 테이블에도 주문정보가 추가 되어야하고 order_line에도 주문정보가 추가 되어야 한다. 주문을 누르게 되면 OrderInfoDAO, OrderLineDAO 이렇게 따로 나누지 않고 OrderDAO 인터페이스 하나로 했다. 추가하거나 삭제 또는 조회할 때 info하고 line하고 항상 같이 다녀야 한다.

OrderDAO 인터페이스를 보면서 OrderService와 OrderDAOOracle 의 구현 작업을 한다.

![13](https://user-images.githubusercontent.com/63957819/108333757-a0588400-7214-11eb-850a-e2545ef38983.png)

- OrderDAOOracle.java

```java
package com.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.sql.MyConnection;
import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;

public class OrderDAOOracle implements OrderDAO {
	private void insertInfo(Connection con, OrderInfo info) throws AddException {
		PreparedStatement pstmt = null;
		String insertInfoSQL = "INSERT INTO order_info(order_no, order_id, order_dt) VALUES (order_seq.NEXTVAL, ?, SYSDATE)";
		try {
			pstmt = con.prepareStatement(insertInfoSQL);
			pstmt.setString(1, info.getC().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(null, pstmt); //같은 connection을 써야해서 connection은 close를 하지 않고 pstmt만 close.
		}
	}
	private void insertLines(Connection con, List<OrderLine> lines) throws AddException {
		PreparedStatement pstmt = null;
		String insertLineSQL = "INSERT INTO order_line(order_no, order_prod_no, order_quantity) VALUES (order_seq.CURRVAL, ?, ?)";
		try {
			pstmt = con.prepareStatement(insertLineSQL);
			/*for(OrderLine line: lines) {
				pstmt.setString(1, line.getP().getProd_no()); //첫 번째 물음표인 order_prod_no
				pstmt.setInt(2, line.getOrder_quantity()); //두 번째 물음표인 order_quantity
				pstmt.executeUpdate(); //개별 처리한다
			}*/
			for(OrderLine line: lines) { 
				pstmt.setString(1, line.getP().getProd_no()); //첫 번째 물음표인 order_prod_no
				pstmt.setInt(2, line.getOrder_quantity()); //두 번째 물음표인 order_quantity
				pstmt.addBatch(); //일괄처리작업에 추가한다
			}
			pstmt.executeBatch(); //일괄처리한다
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(null, pstmt); //같은 connection을 써야해서 connection은 close를 하지 않고 pstmt만 close.
		}
	}
	
	@Override
	public void insert(OrderInfo info) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		try {
			//JDBC는 기본 autocommit으로 설정되어 있어서 각각의 트랜잭션으로 처리
			//insertInfo(con, info); //insert 기본정보 -ex)OK ->자동커밋 ->복구X
			//insertLines(con, info.getLines()); //insert 상세정보들 -ex)FAIL ->복구X   //옳지않는 값이 들어가게된다면 원 상태로 복구해줘야 한다. 그렇지만 jdbc자체가 자동commit이다보니까 복구를 할 수 없다.
			
			//하나의 트랜잭션으로 처리[기본, 상세 insert]
			con.setAutoCommit(false); //자동커밋해제
			insertInfo(con, info); //insert 기본정보 -ex)OK
			insertLines(con, info.getLines()); //insert 상세정보들 -ex)FAIL
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddException e) {
			try {
				con.rollback(); //복구
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			MyConnection.close(con);
		}
	}

	@Override
	public List<OrderInfo> selectById(String order_id) throws FindException {
		return null;
	}

}
```

내부에서만 쓰일 메서드 private으로 접근 

![14](https://user-images.githubusercontent.com/63957819/108333760-a0588400-7214-11eb-874f-c75b95b24b55.png)

insertInfo, insertLines에 쓰이는 Connection은 서로 다른 거다. 한 세션 만들어서 세션 닫고 또 다른 세연 만들어서 일하고 닫고 하는 거다. 즉 세션이 서로 다르다. 이때 문제가 있다. seq.CURRVAL NEXTVAL를 사용하지 않으면 CURRVAL을 쓸 수 없다.  시퀀스의 NEXTVAL 얻은 다음에 CURRVAL하는건 관계없으나 다른 세션에서는 CURRVAL만 할 수 없다. 반드시 NEXTVAL하고 CURRVAL 해야 한다. 문제를 해결하려면 세션1, 세션2를 같은 세션을 써야 한다.

![15](https://user-images.githubusercontent.com/63957819/108333761-a0f11a80-7214-11eb-9cc1-327cb09c18c5.png)

try블록에서 예외가 발생하면 그 예외를 catch로 잡아야 하는데 catch가 없으면 그 예외가 throws로 떠 넘겨진다. 그냥 throws로 떠 넘겨지는게 아니라 finally 수행하고 떠 넘겨지는 거다.

![16](https://user-images.githubusercontent.com/63957819/108333763-a0f11a80-7214-11eb-80d9-b39fbac05c87.png)

DAOOOracle이 여기저기서 재 사용이 되어야 할 클래스이다. 그래서 일반 어플리케이션할 때에는 HttpSession는 웹에서만 쓰이는 종속적인 api이다. 그래서 재사용성이 높아야하는 DAOOracle에는 적합하지 않다. HttpSession이 웹 전용 api이므로 가장 사용 적절한 곳은 Servlet밖에 없다. 그 외에는 권장되지 않는다.

- OrderService.java

```java
package com.my.service;

import com.my.dao.OrderDAO;
import com.my.dao.OrderDAOOracle;
import com.my.exception.AddException;
import com.my.vo.OrderInfo;

public class OrderService {
	private OrderDAO dao = new OrderDAOOracle();
	public void add(OrderInfo info) throws AddException{
		dao.insert(info);
	}

}
```

- PutOrderServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.service.OrderService;
import com.my.vo.Customer;
import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;
import com.my.vo.Product;

public class PutOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		//---로그인 여부----
		String loginedId = (String)session.getAttribute("loginInfo"); //attribte의 정보는 자바 최상클래스 타입으로 저장되기 때문에 원래 상태인 String 타입으로 casting
		if(loginedId == null) { //로그인 안된 경우
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 0);
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
			//out.print("{\"status\": 0}");
		} else { //로그인 된 경우
			Map<String, Integer> cart= (Map)session.getAttribute("cart"); //장바구니얻기
			
			OrderInfo info = new OrderInfo();
			//주문기본정보 설정
			Customer c = new Customer();
			c.setId(loginedId);
			info.setC(c); //주문자ID설정
			
			//장바구니내용을 주문상세정보 객체로 만들어서 리스트에 추가
			List<OrderLine> lines = new ArrayList<>();
			for(String prod_no: cart.keySet()) { //향상된 for문으로 장바구니에 있는 상품 번호들 키들만 가져오는 것
				int quantity = cart.get(prod_no);
				OrderLine line = new OrderLine();
				
				Product p = new Product();
				p.setProd_no(prod_no);
				line.setP(p);
				
				line.setOrder_quantity(quantity);
				
				//주문상세정보객체를 리스트에 추가
				lines.add(line);
			}
			//리스트를 주문기본에 설정
			info.setLines(lines);
			OrderService service = new OrderService();
			try {
				service.add(info); 
				session.removeAttribute("cart"); //장바구니 삭제
				Map<String, Object> map = new HashMap<>();
				map.put("status", 1);
				String jsonStr = mapper.writeValueAsString(map);
				out.print(jsonStr);
			} catch (AddException e) {
				e.printStackTrace();
				Map<String, Object> map = new HashMap<>();
				map.put("status", -1);
				map.put("msg", e.getMessage());
				String jsonStr = mapper.writeValueAsString(map);
				out.print(jsonStr);
			}
		}
		
	}

}
```

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
        <style>
        div.viewcart{
            box-sizing: border-box;
            width: 100%;
            height: 300px;
        }
        div.viewcart>table{
            border-collapse: collapse;
        }
        div.viewcart>table tr{
            
        }
        div.viewcart>table, div.viewcart>table th, div.viewcart>table td{
            border: 1px solid;
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
                    				var $viewcartObj = $("<div class=viewcart>");
                    				var $h1Obj = $("<h1>장바구니</h1>");
                    				$viewcartObj.append($h1Obj);
                    				$viewcartObj.append($tableObj);
                    				var $btObj = $('<button>주문하기</button>'); //'<input type="button" value="주문하기">'
                    				$viewcartObj.append($btObj);
                    				$("section>article").append($viewcartObj);
                    				//$("section>article").append($tableObj);
                    				
                    				/* //DOM에 버튼객체 생성된 후 주문하기버튼 클릭시
                    				$btObj.click(function(){
                    					alert("주문하기 버튼 클릭 됨");
                    				}); */
                    				
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
            	
            	//DOM에 장바구니보기의 주문하기 버튼 객체가 없어서 click()함수가 효과없음!
            	/* $("div.viewcart>button").click(function(){
            		alert("주문하기 버튼 클릭!!!!");
            	}); */
            	
            	//--------------이벤트처리---------------------
            	//DOM에 향후 추가될 자식객체의 이벤트 처리
            	//$(부모객체).on(이벤트종류, 자식객체, 콜백함수)
            	//---장바구니보기 메뉴의 주문하기 버튼 클릭이벤트 START---
            	$("section>article").on('click', 'div.viewcart>button', function(){
            		//alert("주문하기 버튼 클릭!!!!");
            		$.ajax({
            			url: './putorder',
            			success: function(responseObj) {
            				**if(responseObj.status == 1) {**
            					alert("주문성공");
            				} else if(responseObj.status == 0) { //로그인 안한경우
            					alert("로그인부터 하세요");
            				} else if(responseObj.status == -1) { //주문실패
            					alert("주문실패: " + responseObj.msg);
            				}
            			}
            		});
            		return false;
            	});
            	//---장바구니보기 메뉴의 주문하기 버튼 클릭이벤트 END---
            	
            	
            	
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
**<%
 if(session.getAttribute("loginInfo") != null){ //로그인성공된 경우
%>
                    <li><a href="vieworder">주문목록</a></li>
<%} %>**
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

![17](https://user-images.githubusercontent.com/63957819/108333764-a189b100-7214-11eb-9c1b-1e191d997553.png)

장바구니 내용을 가져와서 db에 저장해보자 장바구니 정보는 세션에 카트로 있다. 그 값을 가지고 order_info ,order_line 객체를 만들 거다. 로그인 안하고 주문하기 버튼 누를 시 "로그인 하세요"가 뜨고 로그인 시 "주문성공"이 뜬다. 마지막으로 db에 잘 들어갔나 확인해보자~

개별적으로 반복문으로 수행을 했으나 처리할 건수가 많을 경우 Batch 처리로 일괄 처리 작업을 할 수 있다. 장바구니에 잘 들어가 있는지 확인하기

select* from order_info;

select * from order_line;

실행결과>

![18](https://user-images.githubusercontent.com/63957819/108333766-a2224780-7214-11eb-9913-5177318cce19.png)
