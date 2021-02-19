# day09

rowspan값을 배열의 length를 이용하면 된다.

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
            				if(responseObj.status == 1) {
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
<%
 if(session.getAttribute("loginInfo") != null){ //로그인성공된 경우
%>
                    <li><a href="vieworder">주문목록</a></li>
<%} %>
                    **<li><a href="upload.html">파일업로드</a>**
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

- upload.html

```html
<div class="upload">
	<form method="post" action="./upload" enctype="multipart/form-data">
		<input type="text" name="t" value="test">
		<input type="file" name="file1">
		<input type="submit" value="파일업로드실행">
	</form>
</div>
```

![1](https://user-images.githubusercontent.com/63957819/108464143-f552d380-72c2-11eb-95e2-dd07f643373e.png)

multipart/form-data 사용해야 파일 업로드 작업이 수행될 수 있다.

- UploadServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		//String t = request.getParameter("t"); (x) 요청시의 form 태그를 보면 multipart/form-data의 enctype으로 getParameter로 얻으면 안된다.
		
		//ServletInputStream is = request.getInputStream();
		/*Scanner sc = new Scanner(is);
		while(sc.hasNextLine()) { //다음 한줄을 읽을 수 있는가 갖고 있는가의 의미
			String readLine = sc.nextLine();
			System.out.println(readLine);
		}*/
		String saveDirectory = "c:\\upload"; //업로드된 파일이 저장될 경로
		int maxPostSize = 3000 * 1024;
		String encoding = "MS949";
		FileRenamePolicy policy = //new FileRenamePolicy();//추상화된 api이므로 직접 객체 생성x 하위클래스를 만들어서 직접 설정해서 해야한다.
				new DefaultFileRenamePolicy();
		MultipartRequest mr = //new MultipartRequest(request, saveDirectory);
				//new MultipartRequest(request, saveDirectory, encoding);
				//new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
				new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		//요청전달데이터 얻기
		String t = mr.getParameter("t");
		
		Enumeration<String> e = mr.getFileNames(); //Enumeration는 이터레이션(반복자)을 수행해주는 api
		while(e.hasMoreElements()) {
			String fileName = e.nextElement();
			System.out.println("fileName = " + fileName);
			System.out.println("OriginalFileName = " + mr.getOriginalFileName(fileName));
		}
		
	}

}
```

![2](https://user-images.githubusercontent.com/63957819/108464144-f5eb6a00-72c2-11eb-8a3d-df4e476fb83a.png)

요청의 바디 내용을 getInputStream이라는 메서드로 body의 내용을 조회해 볼 수 있다.

실행결과>

![3](https://user-images.githubusercontent.com/63957819/108464147-f6840080-72c2-11eb-9330-51a4db3836b0.png)

t에 해당하는 값이 test이다. application/octet-stream은 wb 찾아낸 파일의 내용을 서버 쪽으로 무조건 쓰기를 하겠다라는 의미.

![4](https://user-images.githubusercontent.com/63957819/108464148-f6840080-72c2-11eb-99a0-c9c05d752864.png)

실제 업로드 된 file이다.

![5](https://user-images.githubusercontent.com/63957819/108464149-f71c9700-72c2-11eb-8432-999f0c9067c0.png)

![6](https://user-images.githubusercontent.com/63957819/108464152-f71c9700-72c2-11eb-98bc-5c21b115e09c.png)

cos.jar 다운로드 후 복사해서 WEB-INF의 lib에 다 붙여 넣기

![7](https://user-images.githubusercontent.com/63957819/108464153-f7b52d80-72c2-11eb-88a0-15a2e4d2307f.png)

upload파일 만들어주자

덮어쓰기 하지 않도록 하려면 시리얼 번호처럼 번호가 붙게 하면 된다. 

---

![8](https://user-images.githubusercontent.com/63957819/108464131-f257e300-72c2-11eb-90f1-4733be15e0f4.png)

다운로드가 요청이 되면 서블릿이 요청 전달 데이터를 분석해서 file을 클라이언트 웹 브라우저에게 응답을 해주도록 하자

![9](https://user-images.githubusercontent.com/63957819/108464135-f2f07980-72c2-11eb-8e92-6496e03ff6cf.png)

DownloadServlet 파일 만들자~ get, post방식 구분하지 않고 service메소드 호출

- DownloadServlet.java

```java
package com.my.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파일이름 요청전달데이터 얻기
		String name = request.getParameter("name");
		System.out.println("요청전달데이터 파일이름:" + name);
		//다운로드할 파일의 실제 경로 얻기
		String path = this.getServletContext().getRealPath("upload"); //"c:/upload";
		
		//응답형식 : text/html, application/json, application/octet-stream(무조건다운로드)
		response.setContentType("application/octet-stream;charset=UTF-8");
		
		//다운로드시 파일이름 결정
		//response.setHeader("Content-Disposition", "attachment;filename=" + name);
		//response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("UTF-8"), "ISO-8859-1"));
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
		
		//응답출력스트림
		//PrintWriter out = response.getWriter(); (X) --문자형태로 응답출력
		ServletOutputStream sos = response.getOutputStream(); //--바이트형태로 파일을 출력
		
		//파일읽기
		FileInputStream fis = null;
		File f = new File(path, name);
		fis = new FileInputStream(f);
		byte[]buf = new byte[1024]; //한번에 1kb씩 읽을 버퍼
//		while(true) {
//			int readByteCnt = fis.read(buf); //readByteCnt : 읽은 바이트수
//			if(readByteCnt == -1) { //파일의 끝
//				break;
//			}
//		}
		
		int readByteCnt = -1;
		while( (readByteCnt = fis.read(buf)) != -1 ) {
			sos.write(buf, 0, readByteCnt); //쓰기
		}
	
	}

}
```

![10](https://user-images.githubusercontent.com/63957819/108464136-f3891000-72c2-11eb-88c0-4cfefb684dbd.png)

- uploadlist.html

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uploadlist.html</title>
<!--jquery사용-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script>
$(function(){
	/* $.ajax({
		url: "./uploadlist", //업로드된 파일목록들을 요청
		success: function() {
			//응답된 파일 목록을 section>article에 내용 보여주기
		}
	}); */
});
</script>
</head>
<body>
<section>
  <article>
  	<div class=:"uploadlist">
  		<ul>
  			<li><a href="download?name=logo.png">logo.png</a></li>
  			<li><a href="file://c:/upload/logo.png">logo.png</a></li>
  			<li><img src="http://localhost:8888/myback/upload/logo.png" width="100px"></li>
  			<li><img src="file://c:/upload/logo.png" alt="logo.png"></li>
  			<li>movie_image.jpg</li>
  		</ul>
  	</div>
  </article>
</section>
</body>
</html>
```

![11](https://user-images.githubusercontent.com/63957819/108464138-f3891000-72c2-11eb-8de7-e218def9fd5a.png)

밑에 경로로 하면 보안 문제 때문에 브라우저가 설치되어있는 컴퓨터 자원에 접근을 못한다.

이미지 파일을 보여주고 싶으면 background 이미지 속성을 활용해야 한다.

클라이언트 컴퓨터 쪽의 d 드라이브에 있어도 보안 이슈로 html태그로 절대 접근 불가 

![12](https://user-images.githubusercontent.com/63957819/108464139-f421a680-72c2-11eb-9d60-caaae3fa53c2.png)

톰캣 서버 안쪽에 upload밑에 이미지 파일이 있어야 보여진다. d드라이브에 있으며 안된다.

upload된 파일이 클라이언트 쪽에서 이미지를 보여져야 한다면 upload될 경로를 myback밑에 upload라는 경로로 바꾸자 

eclipse의 디렉토리에 업로드를 시키는게 아니라 톰켓에 배포되어있는 myback밑에 자료를 올려놔야 html페이지에서 요청할 때 보여진다.

- UploadServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		//String t = request.getParameter("t"); (x) 요청시의 form 태그를 보면 multipart/form-data의 enctype으로 getParameter로 얻으면 안된다.
		
		//ServletInputStream is = request.getInputStream();
		/*Scanner sc = new Scanner(is);
		while(sc.hasNextLine()) { //다음 한줄을 읽을 수 있는가 갖고 있는가의 의미
			String readLine = sc.nextLine();
			System.out.println(readLine);
		}*/
		
		//String saveDirectory = "c:\\upload"; //업로드된 파일이 저장될 경로
		//String saveDirectory = "C:\\workspace\\MyEE\\myback\\WebContent\\upload"; // (x) 이클립스경로
		//String saveDirectory = "C:\\workspace\\SW\\apache-tomcat-9.0.41\\apache-tomcat-9.0.41\\wtpwebapps\\myback\\upload"; //톰캣 절대경로
		String saveDirectory = this.getServletContext().getRealPath("upload"); //웹컨텍스트 실제배포경로
		System.out.println("saveDirectory:" + saveDirectory);
		
		int maxPostSize = 3000 * 1024;
		String encoding = "MS949";
		FileRenamePolicy policy = //new FileRenamePolicy();//추상화된 api이므로 직접 객체 생성x 하위클래스를 만들어서 직접 설정해서 해야한다.
				new DefaultFileRenamePolicy();
		MultipartRequest mr = //new MultipartRequest(request, saveDirectory);
				//new MultipartRequest(request, saveDirectory, encoding);
				//new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
				new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		//요청전달데이터 얻기
		String t = mr.getParameter("t");
		
		Enumeration<String> e = mr.getFileNames(); //Enumeration는 이터레이션(반복자)을 수행해주는 api
		while(e.hasMoreElements()) {
			String fileName = e.nextElement();
			System.out.println("fileName = " + fileName);
			System.out.println("OriginalFileName = " + mr.getOriginalFileName(fileName));
		}
		
	}

}
```

![13](https://user-images.githubusercontent.com/63957819/108464140-f4ba3d00-72c2-11eb-9b99-1c107a78aae2.png)

myback 밑에 WebContent에 upload 폴더 하나 만들자

![14](https://user-images.githubusercontent.com/63957819/108464141-f4ba3d00-72c2-11eb-848d-d6db30b36972.png)

tomcat 안에 upload파일을 만들고 안에 이미지가 들어간 것을 볼 수 있다.

![15](https://user-images.githubusercontent.com/63957819/108464142-f552d380-72c2-11eb-99fc-addfa6b2845c.png)
