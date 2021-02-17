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