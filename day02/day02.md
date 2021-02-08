# day02

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled.png)

베포한 프로젝트를 웹컨텍스트라 부른다.  

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%201.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%201.png)

톰켓의 conf 디렉토리에도 web.xml이 있다. web.xml을 웹 배치 기술서라고도 부른다. 

톰캣 web.xml 설정 파일을 보면 servlet element가 보이고 첫 번째 servlet 엘리먼트 맵핑을 보면 서블릿 네임과 서블릿 url 패턴이 들어있다. a.html요청이 되면 이 슬래쉬가 처리가 된다. e.jsp가 요청이 되면 두 번째 *.jsp url패턴이 처리된다.

톰캣의 web.xml과 test 웹컨텍스트용 web.xml이 있다. 둘 다 url패턴이 지정되어있는데 당연히 사용자 요청 시 자신의 웹컨텍스트용  우선순위가 높다. 없으면 우선순위 두 번째를 갖고 있는 톰캣 web.xml로 찾아간다.

 default에 해당하는 서블릿 맵핑 찾으러 가면 default 이름이 갖고 있는 DefaultServlet클래스가 있다.

서블릿 맵핑 엘리먼트를 보면 예를 들어 f.jsp 요청이 들어오는 경우 서블릿 네임이 jsp라 되어있고 jsp를 처리할 클래스가 누구인지 찾아보면 서블릿 엘리먼트 중에서 JspServlet 클래스로 되어있다. f.jsp 요청이 되면 JspServlet클래스가 처리해준다.

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%202.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%202.png)

요청이 들어왔을 때 DefaultServlet하는 일은 요청 된 자원을 찾는다. 예를 들어  a.html를 찾는다 그 자원의 내용을 한 줄 한 줄 그대로 응답한다. b.css를 post방식으로 요청한다면 doPost메서드가 b.css파일을 찾고 내용을 그대로 읽어서 쓰기 작업으로 응답을 한다. f.jsp를 get방식으로 요청하게 되면 요청된 자원인 f.jsp를 찾아서 한 줄 한 줄 내용을 읽어서 서버로 실행한다. 그리고 실행 결과 값을 클라이언트에게 응답한다.

jsp 요청은 JspServlet이 처리 해주고 그 이외의 html, css 등 DefaultServlet이 처리 해준다. 근데 서블릿 요청은 처리해주는 서블릿이 없다.  아무런 설정을 안 하면 DefaultServlet이 설정 해주고 아니면 따로 Servlet클래스를 새로 만드는 거다.

톰캣의 구동과, 웹 컨텍스트 용이 구동 되기 위해서는 web.xml이 있어야 한다.

eclipse실행 후 myfront 프로젝트에 img파일과 semanticcssjq.html, login.html를 myback프로젝트 WebContent에 붙여 넣자~

welcome-file-list란 환영 파일 목록이다.

- web.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>myback</display-name>
  <welcome-file-list>
    **<welcome-file>semanticcssjq.html</welcome-file>**
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
...
..
.
```

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%203.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%203.png)

로그인을 처리해줄 서블릿 클래스를 만들어준다.

- LoginServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답형식(MIME) 지정하기
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8"); //응답형식자체가 json형태
		//응답출력 스트림 얻기
		PrintWriter out = response.getWriter();
		
		//요청전달 데이터 얻기
		String id = request.getParameter("id"); //id 요청전달 데이터
		String pwd = request.getParameter("pwd"); //pwd 요청전달 데이터
		if(id.equals(pwd)) {
			out.print("{\"status\":1}"); //응답 출력하기
		} else {
			out.print("{\"status\":-1}");
		}
		
	}

}
```

MIME타입으로 text/plain, text/html, multipart/form-data, application/json 등이 있다.

- login.html

```html
...
..
.
$.ajax({
                       url: "./login", //'http://localhost:8888/myfront/login',  //요청URL
                       method:"post", //요청방식
                       //data : "id=" + idValue + "&pwd=" + pwdValue, // data : 서버로 전송할 데이터를 의미 //요청시 전달 데이터. 쿼리 스트링 형태로 전달
                       data : {"id": idValue, "pwd": pwdValue}, // 문자열이 아니라 객체형태로 만들어서 전달
                      // success:function(data){ // data는 응답내용, 자료형은 string        
                      //  var responseObj = JSON.parse(data); // 반환값은 객체 타입이 된다. string값을 object로 변환
                      **success: function(responseObj)** { //json객체 형태로 응답
                          if(responseObj.status == 1) {
                              alert("로그인 성공");
                          } else {
                              alert("로그인 실패");
                          }
                       },
                       error:function(jqXHR){
                          alert(jqXHR.status);
                       }
                    });
                    //event.preventDefault(); //기본이벤트 처리 막자 
                    return false; //!주의. event.preventDefault(); event.stopPropagation(); 두개의 효과를 다 포함하는 것이 return false이다.
                 });
            });
...
..
.
```

응답 형식을 application/json으로 바꿔버리면 클라이언트 json객체 형태로 응답을 받는다.

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%204.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%204.png)

CustomerService 서비스의 도움을 받을 거다. CustomerDAOOracle과 일을 할 데이터인 오라클이 있다.  

CRUD가 DAO가 할 일이다. 서비스는 **기능 별**로 내 정보 보기, 아이디 검색 같은 기능은 서비스 단에 묶어서 사용하면 된다.  다르다 하면 서비스 메서드를 만들면 된다. 이러한 전체 작업이 톰켓에서 이루어져야 한다.

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%205.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%205.png)

LIB에 있는 ojdbc6를 apache-tomcat밑에 lib에 붙여 넣자

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%206.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%206.png)

MYSE에 jdbc1>src>com>my에 파일 복사해서 MYEE에 myback>src>com>my밑에 붙여 넣자

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%207.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%207.png)

sqlplus scott/tiger로 접속하여 해당 아이디, 비번 확인 후 

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%208.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%208.png)

위의 sqlplus에서 확인 한 아이디 비번을 넣어 확인 해보기

- LoginServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.CustomerService;
import com.my.vo.Customer;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답형식(MIME) 지정하기
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8"); //응답형식자체가 json형태
		//응답출력 스트림 얻기
		PrintWriter out = response.getWriter();
		
		//요청전달 데이터 얻기
		String id = request.getParameter("id"); //id 요청전달 데이터
		String pwd = request.getParameter("pwd"); //pwd 요청전달 데이터
		/*if(id.equals(pwd)) {
			out.print("{\"status\":1}"); //응답 출력하기
		} else {
			out.print("{\"status\":-1}");
		}*/
		CustomerService service = new CustomerService();
		try {
			Customer c = service.login(id, pwd);
			out.print("{\"status\":1}"); //응답 출력하기
		} catch (FindException e) {
			e.printStackTrace();
			out.print("{\"status\":-1}");
		}
	}

}
```

---

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%209.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%209.png)

singup.html을 myback프로젝트의 WEB-INF에다 붙여 넣지 말고 Webcontent에다 붙여 넣어야 한다.

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%2010.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%2010.png)

버튼이 클릭 되었을 때는 자바스크립트 이벤트 처리 해주고 요청 url을 /iddupchk, 요청 방식을 post방식, 이름은 id이고 사용자가 입력한 값이 abc로 전달이 되도록 해보자

com.my.contorl에 있는 IdDupchkServlet클래스를 만들고 CustomerService에 findById메서드로 아이디에 해당하는 고객이 있는지 없는지 확인할 수 있고 service에서 CustomerDAOOracle을 부르고 여기서는 selectById메서드를 부른다.

아이디에 해당하는 고객이 있을 경우에는 status를 1값 json형식으로 응답할 것이다. 응답 내용은 자바스크립트 객체 형태로 status를 1값을 줄 건데 id가 존재할 경우 그 외의 경우는 id존재 안 할 경우는 status를 -1값으로 설정하자. 클릭에 대한 결과로는 아이디가 이미 존재할 경우에는 이미 존재하는 아이디 입니다를 경고 창에 보여주고  status가 1이 아닌 경우에는 사용한 아이디 입니다라고 경고 창에 보여주도록 하자.

![day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%2011.png](day02%20e4b41bf6d12e44e18695035cc1adc2f3/Untitled%2011.png)

일반 버튼이 두 개나 있다 근데 첫 번째 버튼이 클릭 되었을 때 할 일임으로 구분하려고 id값 설정할 수도 있고 class설정, value 설정할 수 있다. class설정을 해주자!!

- signup.html

```html
<!DOCTYPE html>
<html>
    <head>
        <title>고객 가입</title>
        <!--jQuery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        
        <script>
        	**$(function(){
        		//DOM트리에서 class속성값이 btIdDupChk인 객체찾기
        		var $btIdDupChk = $("input[class=btIdDupChk]");
        		
        		//중복확인 버튼객체에서 클릭 이벤트가 발생하면
        		$btIdDupChk.click(function(){
        			//DOM트리에서 name속성값이 id인 객체찾기
        			var $idObj = $("input[name=id]");
        			
        			//아이디 입력값
        			var idValue = $idObj.val();
        			
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
        	});**
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
            <label>아이디: <input type="text" name="id" placeholder="아이디를 입력하세요"></label>
            **<input type="button" value="중복확인" class="btIdDupChk"><br>**
            
            <lable>비밀번호: <input type="password" name="pwd" placeholder="비밀번호를 입력하세요"></lable><br>
            <lable>비밀번호확인: <input type="password" placeholder="비밀번호를 확인하세요"></lable><br>
            
            <label>이름: <input type="text" name="name" placeholder="이름을 입력하세요"></label><br>
            
            <label>우편번호: <input type="text" readonly size="5"></label>
            **<input type="button" value="우편번호검색" class="btsearchDoro">**<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

- IdDupChkServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.CustomerService;
import com.my.vo.Customer;

public class IdDupChkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, 
						  HttpServletResponse response) throws ServletException, IOException {
		//응답형식 지정하기
		response.setContentType("application/json;charset=UTF-8");
		//응답출력 스트림 얻기
		PrintWriter out = response.getWriter();	
		//요청전달데이터 얻기
		String id = request.getParameter("id");		
		CustomerService service = new CustomerService();
		try {
			Customer c = service.findById(id);
			out.print("{\"status\":1}"); //json형태의 자바스크립트 객체의 구성은 객체의 프로퍼티 바로 앞에 큰 따음표가 필요하다
		} catch (FindException e) {
			e.printStackTrace();
			out.print("{\"status\":-1}");
		}
	}
}
```