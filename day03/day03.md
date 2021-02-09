# day03

- signup.html

```java
<!DOCTYPE html>
<html>
    <head>
        <title>고객 가입</title>
        <!--jQuery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        
        <script>
        	$(function(){
        		//DOM트리에서 class속성값이 btIdDupChk인 객체찾기
        		var $btIdDupChk = $("input[class=btIdDupChk]");
        		
        		//중복확인 버튼객체에서 클릭 이벤트가 발생하면
        		$btIdDupChk.click(function(){
        			console.log("중복확인-1");
        			//DOM트리에서 name속성값이 id인 객체찾기
        			var $idObj = $("input[name=id]");
        			
        			console.log("중복확인-2 idValue=" + idValue);
        			
        			//아이디 입력 유효성 검사
        			var idValue = $idObj.val();
        			if(idValue.trim() == '') { //trim메서드는 앞뒤의 공백을 제거해줌
        				console.log("중복확인-3 idValue.trim()");
        				alert("아이디를 입력하세요");
        				$idObj.focus();
        				return false;
        			}
        			$.ajax({
        				url : "./iddupchk",
        				method : "post",
        				data : {id: idValue},
        				success:function(responseObj){
        					if(responseObj.status == 1){ //아이디가 존재할 경우
        						alert("이미 존재하는 아이디 입니다.");
        					} else {
        						alert("사용 가능한 아이디 입니다.");
        					}
        				},
        				error: function(jqXHR) {
        					alert(jqXHR.status);
        				}
        			});
        		});
        		
        		//
        	});
        </script>
        
        <style>
            body {background-color:wheat;}
            input[type="button"] { background-color: mediumturquoise; color:white; }
            input[type="submit"] { background-color: black; color:white; }
            input[type="reset"] { background-color: black; color:white; }
            #div2 { border:3px solid white; }
        </style>
    </head>
    <body>
        <div id="div2" style="background-color: white; max-width: 400px">
        <form>
            <label>아이디: <input type="text" name="id" placeholder="아이디를 입력하세요" **required**></label>
            <input type="button" value="중복확인" class="btIdDupChk"><br>
            
            <lable>비밀번호: <input type="password" name="pwd" placeholder="비밀번호를 입력하세요"></lable><br>
            <lable>비밀번호확인: <input type="password" placeholder="비밀번호를 확인하세요"></lable><br>
            
            <label>이름: <input type="text" name="name" placeholder="이름을 입력하세요"></label><br>
            
            <label>우편번호: <input type="text" readonly size="5"></label>
            <input type="button" value="우편번호검색" class="btsearchDoro"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="hidden" name="bulidingno" value="123456789012345678901234">
            <input type="text" readonly value="서울시 중구 광화문로 세종대로"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="text" name="addr1" placeholder="상세주소를 입력하세요"><br>

            <input type="submit" value="가입"> &nbsp;&nbsp;&nbsp;
            <input type="reset" value="CLEAR">
        </form>
        </div>
    </body>
</html>
```

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled.png)

반드시 id값 입력해야 하고 서버 쪽에서는 요청 시 전달 되었는가 꼭 확인해야 한다.

소스 코드 복붙 했으나 반드시 id입력하라는 유도 부분을 빼버릴 경우 요청 시 전달이 안될 수 있다.

required 속성은 반드시 입력되기를 원하는 입력 양식이다.

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%201.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%201.png)

경고 창이 나타나는 것을 볼 수 있다. required효과는 submit 버튼을 눌렀을 때만 발생한다.

일반 버튼을 눌렀을 시 빈 문자열이 돼서 서버로 전송이 돼버린다. 

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%202.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%202.png)

중복 확인 버튼도 submit 버튼으로 만들거나, 일반 버튼으로 두되 아이디 직접 유효성 검사를 자바스크립트로 확인해야 한다. 유효성 검사를 통해 결과를 확인해보자~

---

```java
<!DOCTYPE html>
<html>
    <head>
        <title>고객 가입</title>
        <!--jQuery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        
        <script>
        	$(function(){
        		//DOM트리에서 class속성값이 btIdDupChk인 객체찾기
        		var $btIdDupChk = $("input[class=btIdDupChk]");
        		
        		//중복확인 버튼객체에서 클릭 이벤트가 발생하면
        		$btIdDupChk.click(function(){
        			console.log("중복확인-1");
        			//DOM트리에서 name속성값이 id인 객체찾기
        			var $idObj = $("input[name=id]");
        			
        			console.log("중복확인-2 idValue=" + idValue);
        			
        			//아이디 입력 유효성 검사
        			var idValue = $idObj.val();
        			if(idValue.trim() == '') { //trim메서드는 앞뒤의 공백을 제거해줌
        				console.log("중복확인-3 idValue.trim()");
        				alert("아이디를 입력하세요");
        				$idObj.focus();
        				return false;
        			}
        			$.ajax({
        				url : "./iddupchk",
        				method : "post",
        				data : {id: idValue},
        				success:function(responseObj){
        					if(responseObj.status == 1){ //아이디가 존재할 경우
        						alert("이미 존재하는 아이디 입니다.");
        					} else {
        						alert("사용 가능한 아이디 입니다.");
        						//DOM트리에서 type속성값이 submit인 input객체찾기
        						var $btSignup = $("input[type=submit]");
        						//input객체를 화면에 보여준다.
        						$btSignup.css("visibility", "visible");
        					}
        				},
        				error: function(jqXHR) {
        					alert(jqXHR.status);
        				}
        			});
        		});
        		
        		//아이디 입력란에 포커스를 받으면 가입버튼 화면에서 사라진다
        		var $idObj = $("input[name=id]");
        		$idObj.focus(function(){
        			//DOM트리에서 type속성값이 submit인 input객체찾기
        			var $btSignup = $("input[type=submit]");
        			//input객체를 화면에서 사라진다
        			$btSignup.css("visibility", "hidden");
        		});
        	});
        </script>
        
        <style>
            body {background-color:wheat;}
            input[type="button"] { background-color: mediumturquoise; color:white; }
            input[type="submit"] { background-color: black; color:white; }
            input[type="reset"] { background-color: black; color:white; }
            #div2 { border:3px solid white; }
            
            **input[type=submit]{
            	/*display: none;*/
            	visibility: hidden;
            }**
        </style>
    </head>
    <body>
        <div id="div2" style="background-color: white; max-width: 400px">
        <form>
            <label>아이디: <input type="text" name="id" placeholder="아이디를 입력하세요" **required**></label>
            <input type="button" value="중복확인" class="btIdDupChk"><br>
            
            <lable>비밀번호: <input type="password" name="pwd" placeholder="비밀번호를 입력하세요"></lable><br>
            <lable>비밀번호확인: <input type="password" placeholder="비밀번호를 확인하세요"></lable><br>
            
            <label>이름: <input type="text" name="name" placeholder="이름을 입력하세요"></label><br>
            
            <label>우편번호: <input type="text" readonly size="5"></label>
            <input type="button" value="우편번호검색" class="btsearchDoro"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="hidden" name="bulidingno" value="123456789012345678901234">
            <input type="text" readonly value="서울시 중구 광화문로 세종대로"><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="text" name="addr1" placeholder="상세주소를 입력하세요"><br>

            <input type="submit" value="가입"> &nbsp;&nbsp;&nbsp;
            <input type="reset" value="CLEAR">
        </form>
        </div>
    </body>
</html>
```

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%203.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%203.png)

우편번호 눌렀을 때의 도로 명 주소 찾기, 가입도 해야 한다. 가입 버튼이 무작정 화면에 보이지 않게 하자 사용 가능한 아이디일 경우만 보이게 하자

display는 영역도 사라지지만, visibility는 영역은 유지하되 보이지만 않는 것이다.

---

- searchpost.html

```java
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>searchpost.html</title>
<style>
html,body{margin:0px; overflow: hidden;height:100%;border:1px solid red;}
div.post{ width: 100%; height:100%;}
div.post>div.list{width: 100%; height:80%; border:2px double green; margin-top:10px;    ;overflow-y: auto;   }
div.post>div.list>ul{list-style-type: none; padding: 0px;}
div.post>div.list>ul>li>div.code{width:15%; display: inline-block;}
div.post>div.list>ul>li>div.baseAddr{display: inline-block;}
div.post>div.list>ul>li>div.buildingno{display: none;}

}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
$(function(){
	$("div.post>div.list>ul>li").click(function(e){
		var codeValue = $(this).find('div.code').html();
		var baseAddrValue = $(this).find('div.baseAddr').html();
		var buildingnoValue = $(this).children('div.buildingno').html();
		alert(codeValue + ":" + baseAddrValue + ":" + buildingnoValue);
				
		//메인창객체찾기
		var mainWindow = window.opener;
		mainWindow.document.querySelector("form>label>input[class=code]").value = codeValue;
		mainWindow.document.querySelector("form>input[name=buildingno]").value = buildingnoValue;
		mainWindow.document.querySelector("form>input[class=baseAddr]").value = baseAddrValue;
		
		self.close(); //현재창닫기
	});
});
</script>
</head>
<body>
<div class="post">
  <div class="search">
    <span>도로명주소</span>
    <input type="search" value="홍익길">
  </div>
  <div class="list">
  <ul>
    <li>
	    <div class="code">30065</div>
	    <div class="baseAddr">세종특별자치시 금송로</div>
	    <div class="buildingno">3611010500109680000000001</div>
    </li>
    <li><div class="code">30016</div><div class="baseAddr">세종특별자치시 조치원읍 홍익길 10</div><div class="buildingno">3611011200101720001000001</div></li>
    <li><div class="code">30016</div><div class="baseAddr">세종특별자치시 조치원읍 홍익길 12</div><div class="buildingno">3</div></li>
    <li><div class="code">30016</div><div class="baseAddr">세종특별자치시 조치원읍 홍익길 14</div><div class="buildingno">4</div></li>
    <li><div class="code">30016</div><div class="baseAddr">세종특별자치시 조치원읍 홍익길 14-1</div><div class="buildingno">5</div></li>
    <li><div class="code">30016</div><div class="baseAddr">세종특별자치시 조치원읍 홍익길 16</div><div class="buildingno">6</div></li>
    <li><div class="code">30016</div><div class="baseAddr">세종특별자치시 조치원읍 홍익길 16-1</div><div class="buildingno">7</div></li>
  </ul>
  </div>
</div>
</body>
</html>
```

- signup.html

```java
<!DOCTYPE html>
<html>
    <head>
        <title>고객 가입</title>
        <!--jquery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
        <script>
        $(function(){
        	
        	//DOM트리에서 class속성값이 btIdDupChk인 객체찾기
        	var $btIdDupChk = $("input[class=btIdDupChk]");
        	
        	//중복확인버튼객체에서 클릭이벤트가 발생하면
        	$btIdDupChk.click(function(){
        		//DOM트리에서 name속성값이 id인 객체찾기
        		var $idObj = $("input[name=id]");
        		
        		//아이디 입력값
        		var idValue = $idObj.val();
        		
        		//아이디 입력 유효성 검사
        		if(idValue.trim() ==''){
        			alert("아이디를 입력하세요");
        			$idObj.focus();
        			return false;
        		}
        		$.ajax({
        			url : "./iddupchk",
        			method : "post",
        			data : {id: idValue},
        			success:function(responseObj){
        				if(responseObj.status == 1){
        					alert("이미 존재하는 아이디입니다");
        				}else{
        					alert("사용가능한 아이디입니다");
        					
        					//DOM트리에서 type속성값이 submit인 input객체찾기
        					var $btSignup = $("input[type=submit]");
        					//input객체를 화면에 보여준다
        					$btSignup.css("visibility", "visible");
        				}
        			},
        			error: function(jqXHR){
        				alert(jqXHR.status);
        			}
        		});
        	});
        	
        	//아이디입력란에 포커스를 받으면 가입버튼 화면에서 사라진다
        	var $idObj = $("input[name=id]");
        	$idObj.focus(function(){
        		//DOM트리에서 type속성값이 submit인 input객체찾기
				var $btSignup = $("input[type=submit]");
				//input객체를 화면에서 사라진다
				$btSignup.css("visibility", "hidden");
        	});
        	
        	**//우편번호검색 버튼이 클릭되면 새창에 searchpost.html를 보여준다
        	$btSearchDoro = $("input[class=btSearchDoro]");
        	$btSearchDoro.click(function(){
        		var url = "./searchpost.html";
        		var target = "first";
                var feature = "width=300 height=300";
                w1 = window.open(url, target, feature);
                    
        	});**
        });
        </script>
        <style>
        	input[type="submit"]{
        		/*display: none;*/
        		visibility: hidden;
        	}
        	
        </style>
    </head>
    <body>
    	<form>    	
	        <label>아이디:
	        <input type="text" name="id" placeholder="아이디를 입력하세요" required></label>
	        <input type="button" value="중복확인"  class="btIdDupChk"><br>
	
	        <label>비밀번호:<input type="password" name="pwd" placeholder="비밀번호를 입력하세요" ></label><br>
	        <label>비밀번호확인:<input type="password"  placeholder="비밀번호를 입력하세요" ></label><br>
	
	        <label>이름:<input type="text" name="name" placeholder="이름을 입력하세요" ></label><br>
	
	        **<label>우편번호:<input type="text" readonly size="5" class="code"></label>**
	        
	        **<input type="button" value="우편번호검색" class="btSearchDoro"><br>**
	        <input type="hidden" name="buildingno"  value="123456789012345678901234">
	        <input type="text" readonly value="서울시 중구 광화문로 세종대로"  class="baseAddr"><br>
	        
	        <input type="text" name="addr1" placeholder="상세주소를 입력하세요"><br>
	
	        <input type="submit" value="가입">
	        <input type="reset" value="CLEAR">
        </form>
    </body>
</html>
```

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%204.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%204.png)

ajax요청이 아니라 새 창을 띄우는 거다. 클릭이 되면 window의 객체 open이라는 함수 이용 

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%205.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%205.png)

li기준으로 해서 자식 객체를 children으로 후손 객체로 find로 찾아낼 수 있다.

우편 번호를 구분 해주는 거는 건물 번호 값이다 즉 pk의 역할을 한다.

실행결과>

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%206.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%206.png)

---

```java
<!DOCTYPE html>
<html>
    <head>
        <title>고객 가입</title>
        <!--jquery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
        <script>
        **$(function(){
        	
        	//DOM트리에서 class속성값이 btIdDupChk인 객체찾기
        	var $btIdDupChk = $("input[class=btIdDupChk]");
        	
        	//중복확인버튼객체에서 클릭이벤트가 발생하면
        	$btIdDupChk.click(function(){
        		//DOM트리에서 name속성값이 id인 객체찾기
        		var $idObj = $("input[name=id]");
        		
        		//아이디 입력값
        		var idValue = $idObj.val();
        		
        		//아이디 입력 유효성 검사
        		if(idValue.trim() ==''){
        			alert("아이디를 입력하세요");
        			$idObj.focus();
        			return false;
        		}
        		$.ajax({
        			url : "./iddupchk",
        			method : "post",
        			data : {id: idValue},
        			success:function(responseObj){
        				if(responseObj.status == 1){
        					alert("이미 존재하는 아이디입니다");
        				}else{
        					alert("사용가능한 아이디입니다");
        					
        					//DOM트리에서 type속성값이 submit인 input객체찾기
        					var $btSignup = $("input[type=submit]");
        					//input객체를 화면에 보여준다
        					$btSignup.css("visibility", "visible");
        				}
        			},
        			error: function(jqXHR){
        				alert(jqXHR.status);
        			}
        		});
        	});
        	
        	//아이디입력란에 포커스를 받으면 가입버튼 화면에서 사라진다
        	var $idObj = $("input[name=id]");
        	$idObj.focus(function(){
        		//DOM트리에서 type속성값이 submit인 input객체찾기
				var $btSignup = $("input[type=submit]");
				//input객체를 화면에서 사라진다
				$btSignup.css("visibility", "hidden");
        	});
        	
        	//우편번호검색 버튼이 클릭되면 새창에 searchpost.html를 보여준다
        	$btSearchDoro = $("input[class=btSearchDoro]");
        	$btSearchDoro.click(function(){
        		var url = "./searchpost.html";
        		var target = "first";
                var feature = "width=500 height=300";
                w1 = window.open(url, target, feature);
                    
        	});
        });
        
        //가입버튼이 클릭되면 서버로 데이터 전송
        $("form").submit(function(){
        	var url = './signup';
        	var method = 'post';
        	var data = $('form').serialize();
        	alert(data); //ex).id=a&pwd=a&name=c&buildingno=4&addr1=d
        	$.ajax({
        		url: url, //:앞에 있는 것이 프로퍼티 이름이 되는 것이다.
        		method: method,
        		data: data,
        		success: function(responseObj){
        			if(responseObj.status == 1) {
        				alert("가입성공");
        			} else {
        				alert("가입실패");
        			}
        		},
        		error: function(jqXHR){
        			alert(jqXHR.status); //404, 403, 500(서버) error
        		}
        	});
        	return false; //event.stopPropagation(); event.preventDefault();
        });**
        </script>
        <style>
        	**input[type="submit"]{
        		/*display: none;*/
        		visibility: hidden;
        	}**
        	
        </style>
    </head>
    <body>
    	<form>    	
	        <label>아이디:
	        <input type="text" name="id" placeholder="아이디를 입력하세요" required></label>
	        <input type="button" value="중복확인"  class="btIdDupChk"><br>
	
	        <label>비밀번호:<input type="password" name="pwd" placeholder="비밀번호를 입력하세요" ></label><br>
	        <label>비밀번호확인:<input type="password"  placeholder="비밀번호를 입력하세요" ></label><br>
	
	        <label>이름:<input type="text" name="name" placeholder="이름을 입력하세요" ></label><br>
	
	        <label>우편번호:<input type="text" readonly size="5" class="code"></label>
	        
	        <input type="button" value="우편번호검색" class="btSearchDoro"><br>
	        <input type="hidden" name="buildingno"  value="123456789012345678901234">
	        <input type="text" readonly value="서울시 중구 광화문로 세종대로"  class="baseAddr"><br>
	        
	        <input type="text" name="addr1" placeholder="상세주소를 입력하세요"><br>
	
	        <input type="submit" value="가입">
	        <input type="reset" value="CLEAR">
        </form>
    </body>
</html>
```

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%207.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%207.png)

가입이 클릭이 되면 submit버튼이 클릭이 되는 것이고 그리고 클릭이 되면 form객체의 submit이벤트가 자동 발생한다. 

serialize 라는게 직렬화 인데 form 객체 안에 있는 name속성인 id가 id:"a", pwd속성은 pwd:"b" ....

사용자가 입력한 값이 프로퍼티 값으로 만들어지는 거다. 

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%208.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%208.png)

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%209.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%209.png)

action속성에 해당하는 url이 없으면 현재 사용중인 url로 이동하려고 한다.

method 속성이 없으면 default값이 get방식의 요청이다. 

기본 이벤트 처리를 막아줘야 한다.

실행결과>

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2010.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2010.png)

404error가 뜬 것을 볼 수 있다.

---

- SignupServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.AddException;
import com.my.service.CustomerService;
import com.my.vo.Customer;
import com.my.vo.Postal;

/**
 * Servlet implementation class SignupServlet
 */
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		CustomerService service = new CustomerService();
		Customer c = new Customer();
		c.setId(request.getParameter("id"));
		c.setPwd(request.getParameter("pwd"));
		c.setName(request.getParameter("name"));
		
		String buildingno = request.getParameter("buildingno");
		String addr1 = request.getParameter("addr1");
		Postal postal = new Postal();
		postal.setBuildingno(buildingno);
		c.setAddr1(addr1);
		c.setPostal(postal);
		
		try {
			service.add(c);
			out.print("{\"status\": 1}");
		} catch (AddException e) {
			e.printStackTrace();
			out.print("{\"status\": -1, \"msg\": " + e.getMessage() + "}");
		}
	}

}
```

- CustomerDAOOracle

    ```java
    package com.my.dao;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;

    import com.my.exception.AddException;
    import com.my.exception.FindException;
    import com.my.exception.ModifyException;
    import com.my.exception.RemoveException;
    import com.my.sql.MyConnection;
    import com.my.vo.Customer;

    public class CustomerDAOOracle implements CustomerDAO {

    	@Override
    	public void insert(Customer c) throws AddException {
    		Connection con = null;
    		try {
    			con = MyConnection.getConnection();
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new AddException("고객 추가 실패: 이유=" + e.getMessage());
    		}
    		PreparedStatement pstmt = null;
    		String insertSQL = 
    				**"INSERT INTO customer(id, pwd, name, buildingno, addr1) VALUES (?,?,?,?,?)";**
    		try {
    			pstmt = con.prepareStatement(insertSQL);
    			pstmt.setString(1, c.getId());
    			pstmt.setString(2, c.getPwd());
    			pstmt.setString(3, c.getName());
    			**pstmt.setString(4, c.getPostal().getBuildingno());//^
    			pstmt.setString(5, c.getAddr1());//^**
    			pstmt.executeUpdate();
    		} catch (SQLException e) {
    			//e.printStackTrace(); 고객들에게 배포를 할때는 왠만하면 안쓰는게 좋다
    			if(e.getErrorCode() == 1) { //PK중복
    				throw new AddException("이미 존재하는 아이디입니다");
    			} else {
    				e.printStackTrace(); // 그 외의 경우
    				throw new AddException(e.getMessage());
    			}
    		} finally {
    			MyConnection.close(con, pstmt);
    		}
    	}
    	
    	@Override
    	public List<Customer> selectAll() throws FindException {
    		Connection con = null; //db와의 역할하는 소켓
    		PreparedStatement pstmt = null; //출력 스트림
    		ResultSet rs = null; //입력 스트림
    		try {
    			con = MyConnection.getConnection();
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new FindException(e.getMessage());
    		}
    		//String selectSQL = "SELECT * FROM customer WHERE id>='id999' ORDER BY id"; //고객이 한명도 없었을때
    		String selectSQL = "SELECT * FROM customer ORDER BY id";
    		try { //try블록에서 예외가 발생하면 그 즉시 catch로가서 처리하고 finally로 간다. return을 만나게하거나 예외를 throw해줘야 한다.
    			pstmt = con.prepareStatement(selectSQL);
    			rs = pstmt.executeQuery(); //select구문 처리시 사용되는 메서드
    			List<Customer> list = new ArrayList<>();
    			while(rs.next()) {
    				String id = rs.getString("id");
    				String pwd = rs.getString("pwd");
    				String name = rs.getString("name");
    				String zipcode = "12345";
    				String addr1 = "6층";
    				Customer c = new Customer(id, pwd, name);
    				list.add(c);
    			}
    			if(list.size() == 0) {
    				throw new FindException("고객이 한명도 없습니다");
    			}
    			return list; //return하기 바로 직전에 finally 구문을 수행하고 return이 된다. 
    		} catch (SQLException e) {
    			e.printStackTrace();
    			throw new FindException(e.getMessage()); // try블록에서 예외 발생시 예외를 강제로 떠 넘긴다. // throw하기 직전에 finally수행하고 throw되는거다.
    		} finally {
    			MyConnection.close(con, pstmt, rs);
    		}
    	}

    	@Override
    	public Customer selectById(String id) throws FindException {
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		ResultSet rs = null;
    		try {
    			con = MyConnection.getConnection();
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new FindException(e.getMessage());
    		}
    		String selectByIdSQL = "SELECT * FROM customer WHERE id=?";
    		try { //sqlexception이 발생할수있으니까
    		pstmt = con.prepareStatement(selectByIdSQL);
    		pstmt.setString(1, id);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			String pwd = rs.getString("pwd");
    			String name = rs.getString("name");
    			String zipcode = "12345";
    			String addr1 = "6층";
    			return new Customer(id, pwd, name);
    		} else {
    			throw new FindException("아이디에 해당 고객이 없습니다");
    		}
    	} catch(SQLException e) {
    		throw new FindException(e.getMessage()); //다양하게 처리하기위해 getMessage
    	} finally {
    			MyConnection.close(con, pstmt, rs);
    	}
    }
    	@Override
    	public Customer update(Customer c) throws ModifyException {
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		try {
    			con = MyConnection.getConnection();
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new ModifyException(e.getMessage());
    		}
    		//비번,이름 모두 수정 UPDATE customer SET pwd=?, name=? WHERE id=?
    		//비번 수정 		 UPDATE customer SET pwd=?, WHERE id=?
    		//이름 수정		 UPDATE customer SET name=? WHERE id=?
    //		String updateSQL = "UPDATE customer SET pwd=?, name=? WHERE id=?"; //고객의 비번만 아니면 이름만 바꾸는 경우는? 이 sql구문은 적절치 않다. sql구문은 preparedStatement 효과에 적절치 않다
    //		try {
    //			pstmt = con.prepareStatement(updateSQL);
    //			pstmt.setString(1, c.getPwd()); //?위치 1부터. db의 규칙을 그대로 따라 1부터 시작해야한다.
    //			pstmt.setString(2, c.getName());
    //			pstmt.setString(3, c.getId());
    //			pstmt.executeUpdate(); //dml,ddl 처리해주는 메소드
    //		} catch(SQLException e) {
    //			throw new ModifyException(e.getMessage());
    //		} finally {
    //			MyConnection.close(con, pstmt);
    //		}
    		
    		Statement stmt = null;
    		String updateSQL = "UPDATE customer SET "; //비번이름모두 수정, 비번 수정, 이름 수정 하는 공통적으로 쓰일 경우
    		String updateSQLSet = "";
    		String updateSQL1 = "WHERE id='" + c.getId() + "'";
    		try {
    			stmt = con.createStatement();
    			boolean flag = false; //수정여부
    			if(c.getPwd() != null && !c.getPwd().equals("")) { //비번 수정 null도아니고 빈 문자열도 아닌경우
    				updateSQLSet = "pwd='" + c.getPwd() + "' ";
    				flag = true;
    			}
    			if(c.getName() != null && !c.getName().equals("")) { //이름 수정 null도아니고 빈 문자열도 아닌경우
    				if(flag) { //flag가 true인 경우 이름을 수정하는 경우에서 비번을 이미 수정하겠다라는 case. 이름 수정하러 왔을 때 이름을 수정하는 입장에서 비번도 같이 수정하겠다라는 의미
    					updateSQLSet += ",";
    				}
    				updateSQLSet += "name='" + c.getName() + "' ";
    				flag = true;
    			}
    			
    			if(flag) { //flag값이 true인 경우 
    				System.out.println(updateSQL + updateSQLSet + updateSQL1); //먼저 테스트 해봐야한다.
    				stmt.executeUpdate(updateSQL + updateSQLSet + updateSQL1); 
    				try {
    					return selectById(c.getId()); //변경 될 객체를 반환해야한다. 수정된 내용이 잘 들어가있는지 확인해야한다.
    				} catch (FindException e) {
    					e.printStackTrace();
    					throw new ModifyException(e.getMessage());
    				} 
    			} else { //비번수정, 비번 이름 수정하는 case에 들어오지 않는 경우
    				throw new ModifyException("수정할 내용이 없습니다");
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    			throw new ModifyException(e.getMessage());
    		} finally {
    			MyConnection.close(con, stmt);
    		}
    	}

    	@Override
    	public Customer delete(String id) throws RemoveException {
    		Customer c;
    		try { //삭제할 고객찾기
    			c = selectById(id); //먼저 고객정보 찾기
    		} catch (FindException e) {
    			//e.printStackTrace();
    			throw new RemoveException(e.getMessage()); //id가없어서 삭제 못할때
    		}
    		// 조회했다가 삭제하려는 중간 사이에 누군가가 id고객을 삭제할 수 있다. 다시한번 처리건수를 비교해야한다
    		Connection con = null;
    		PreparedStatement pstmt = null;
    		try {
    			con = MyConnection.getConnection();
    		} catch (Exception e) {
    			e.printStackTrace();
    			throw new RemoveException(e.getMessage()); //삭제가 될때 발생하는 예외니깐 감싸는거라 생각하면 된다.
    		}
    		String deleteSQL = "DELETE FROM customer WHERE id=?";
    		try {
    			pstmt = con.prepareStatement(deleteSQL);
    			pstmt.setString(1, id);
    			int rowcnt = pstmt.executeUpdate(); //rowcnt는 처리건수
    			if(rowcnt != 1) { //삭제건수가 0건
    				throw new RemoveException("아이디에 해당 고객이 없습니다");
    			}
    			return c;
    		} catch(SQLException e) {
    			throw new RemoveException(e.getMessage());
    		} finally {
    			MyConnection.close(con, pstmt);
    		}
    	}
    	public static void main(String[] args) {
    		CustomerDAOOracle dao = new CustomerDAOOracle();
    		//고객 삭제 테스트
    		String id = "id11";
    		try {
    			Customer c = dao.delete(id);
    			System.out.println("삭제 테스트 성공! 삭제된 고객정보:" + c);
    		} catch (RemoveException e) {
    			e.printStackTrace();
    		}
    		
    		//고객정보수정 테스트
    		/*Customer c = new Customer();
    		c.setId("id1");
    		//c.setPwd("updp"); c.setName("updn"); //1)비번이름 모두 수정 
    		
    		//c.setPwd("updp1"); //비번만 수정
    		
    		//c.setName("updn1"); //이름만 수정 
    		
    		try {
    			dao.update(c);
    		} catch (ModifyException e) {
    			e.printStackTrace();
    		}*/
    		
    		//고객 ID로 검색 테스트
    		/*String id = "id999"; //"id999";
    		try {
    			Customer c = dao.selectById(id);
    			System.out.println(c);
    		} catch (FindException e) {
    			e.printStackTrace();
    		}*/
    		
    		//고객전체검색 테스트
    		/*try {
    			List<Customer> list = dao.selectAll();
    			for(Customer c: list) {
    				System.out.println(c); //c.toString()가 자동 호출됨
    			}
    		} catch (FindException e) {
    			e.printStackTrace();
    		}*/
    		
    		
    		//고객추가 테스트
    		/*Customer c = new Customer();
    		//c.setId("id11"); c.setPwd("p11"); c.setName("n11");
    		c.setId("id12"); //c.setPwd("p11"); c.setName("n11");
    		c.setName("n12");
    		try {
    			dao.insert(c);
    			System.out.println("추가 테스트 성공");
    		} catch (AddException e) {
    			//e.printStackTrace();
    			System.out.println(e.getMessage());
    		}*/
    	}

    }
    ```

    ![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2011.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2011.png)

    ![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2012.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2012.png)

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2013.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2013.png)

Sqldeveloper 데이터를 보면 값이 들어간 것을 확인할 수 있다.

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2014.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2014.png)

엑셀 데이터를 넣기위해 데이터 임포트 해주기

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2015.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2015.png)

각각 차례대로 대상 테이블 이름에 맞게 맵핑 해준다. 

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2016.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2016.png)

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2017.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2017.png)

sido 컬럼의 자리수 조정 해준다.

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2018.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2018.png)

다시 insert해주고 마지막에 commit 해주기

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2019.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2019.png)

데이터가 들어간 것을 볼 수 있다.

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2020.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2020.png)

**fk로 참조할 때는 부모의 pk를 참조**한다.

customer는 postal를 참조하는 자식 역할을 한다. 일대 다의 관계이다.

화면 구성에서 sql문을 도출할 때 고객의 정보를 조회한다는 화면이 있다 하자. 이때 사용할 sql 구문은 SELECT id, pwd, name, p.zipcode, p.sido FROM customer c JOIN Postal p ON (c.buildingno=p.buildingno) WHERE id="id1"; 를 만들 거다. **고객 입장에서 조회**를 한다 하면 위와 같은 select 구문을 쓸 거다. 

또 다른 sql구문 도출을 한다 하면 고객을 조회하되 **건물 별로 고객을 조회** 한다면 SELECT FROM JOIN WHERE buildingno='123~'; 구문을 만들 거다.

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2021.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2021.png)

- Customer.java

```java
package com.my.vo;

public class Customer extends Person{	
	private String id;
	transient private String pwd;
	private Postal postal;
	private String addr1;
	public Customer() {
		super();
	}
	public Customer(String name) {
		super(name);
	}
	public Customer(String id, String pwd, Postal postal) {		
		this(id, pwd, null, postal, null);
	}
	public Customer(String id, String pwd, String name, Postal postal, String addr1) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.postal = postal;
		this.addr1 = addr1;
	}
	
	public Customer(String id, String pwd, String name) {
		this(id,pwd,name,null, null);
	}
	public Customer(String id, String pwd) {
		this(id,pwd,null,null, null);
	}
	
	
	public Customer(String id, String pwd, Postal postal, String addr1) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.postal = postal;
		this.addr1 = addr1;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", postal=" + postal + ", addr1=" + addr1 + "]";
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Postal getPostal() {
		return postal;
	}
	public void setPostal(Postal postal) {
		this.postal = postal;
	}
		
}
```

실행결과>

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2022.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2022.png)

![day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2023.png](day03%207baed4b1effd4a5a9a1be4ca1d4a22a1/Untitled%2023.png)