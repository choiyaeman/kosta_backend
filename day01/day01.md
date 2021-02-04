# day01

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled.png)

8888포트 번호를 열고 톰캣 서버가 기다리고 있는데 배포되어 있는 프로젝트들을 보면은 myfront라는 프로젝트였고 이 프로젝트에 semanticcssjq.html, login.html, images/logo.jpg 가 있다. WB에서 주소를 접속할 서버ip(http://127.0.0.1:8888/myfront/semanticcssjq.html)로 정확히 들어가야 한다. Network가 요청하고 찾아서 html내용이 그대로 응답이 되고 응답 내용을 렌더링 엔진에게 맡긴다.

백그라운드 이미지를 찾아가야 한다면 imgaes/logo.jpg를 찾아서 응답을 받아야 한다.

렌더링 엔진이 다 응답을 받아서 화면에 예쁘게 보여줘야 하는데 기존 내용을 지우고 보여준다. 기존 내용을 지우지 않겠다 하려면 자바스크립트의 해석기의 도움을 받아서 그 내장 객체 중에 XMLHttpRequest가 있는데 이 놈을 이용하면 특이하게도 요청과 응답을 할 수 있는 얘이다.

**XMLHttpRequest**를 사용하면 화면 렌더링 절차에서 기존 화면 전체 지우지 안하고 비동기 일 처리를 할 수 있다. 즉 요청을 했는데 서버가 너무 바쁘다 그래서 응답이 지연되고 있다면 응답을 받을 때 까지 계속 기다리는 게 아니고 그 사이에 다른 일을 할 수 있다.  사용자가 이벤트 처리를 하게 된다면 그 일 처리를 하게 해준다. 이런 것을 비동기 일 처리라 한다. 이 객체가 **ajax**용 객체이다. 

jQuery로 $.ajax( ); 를 쓰면 된다.

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%201.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%201.png)

프로젝트 안에 들어갈 수 있는 것은 Servlet이나 JSP 자바 기반의 기술이 들어갈 수 있다.

주소 url로 요청한다면 서블릿 하나에 맵핑이 a라고 하면 /a **서블릿을 찾는다 요청이 되면 응답은 서블릿이 서버에서 실행이 된다 실행 결과 값이 도출이 되면 그 결과 값만 응답 할 수 있다**. 서블릿의 실행 내용이 출력 되는 게 아니다.

html, css, image파일 등 클라이언트에게 내용이 고스란히 응답이 되어 클라이언트 웹 브라우저에서 렌더링되어 화면에 보여지는 처리를 한다. 서버에서 실행되서 결과 값만 응답 되는 애들이다. 

가가 요청한 서블릿이 다를 수 있고 나가 요청한 서블릿이 다를 수 있다. 서버 사이드에서는 실행 하기 위해서는 데이터베이스의 도움을 받을 것 이다. 가or나 요청했을 때의 전달한 데이터에 따라서 서로 실행 결과가 다를 수 있다. 이런 문서를 동적 문서라 한다. 서블릿과 jsp와 반대로 html이나 js는 db하고 일을 못한다. 

서버 쪽에서 실행되는 기술이 필요한 이유는 데이터베이스와 일을 처리하기 위해서 이다. 렌더링 엔진에서 db에 접속할 수 있는 방법이 없다.  JSP DB와 원래 일을 못하나 일을 할 수 있는 라이브러리가 있는데 그것이 Nodejs이다. Nodejs를 사용하면 db를 충분히 사용할 수 있다.

서버 사이드에서 실행되는 기술을 백엔드 프로그래밍이라 한다.

---

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%202.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%202.png)

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%203.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%203.png)

프로젝트를 둘로 쪼개자 새로운 프로젝트 이름은 myback이다.  여기에는 servlet하고 jsp가 들어갈 것이다. servelt용, jsp용 api를 제공해주는 그 api제공을 4.0버전을 사용 하겠다라는 뜻 

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%204.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%204.png)

톰켓, 레진, 웹로직, 웹스피어, 제우스는 웹로 서블릿/jsp 종류들이다.  아파치는 PHP, IIS는 Asp.NET이 있다. servlet/jsp를 실행시켜줄 수 있는 엔진이 톰캣, 레진, 웹로직, 웹스피어, 제우스가 있는 거다. 

플랫폼의 독립성 자바의 기반 특성을 생각해보자면 한 서버에 만든 프로젝트가 다른 서버에서도 똑같이 실행이 되어야 한다. 즉 톰캣, 레진, 웹로직, 웹스피어, 제우스에 그대로 실행이 될 수 있어야 한다. 톰캣에서 제공되는 라이브러리하고 레진, 웹로직, 웹스피어, 제우스 모두 같아야 똑같은 효과가 나는 거다. 그 라이브러리는 서로 표준화된 API 라이브러리를 사용한다. 이걸 자바에서 인터페이스, 추상클래스를 제시한다. 대표 추상 클래스로 HttpServletRequest가 있다. 

서블릿, jsp용 api를 표준화된 api를 인터페이스나 추상클래스를 제공하는 쪽은 자바에서 제공하고 각 회사에서 구현하는 것이다. JAVA 8, 톰캣 9, 서블릿API 4.0 이라 하면 톰캣 9버전이 중요한 게 아니라 **서블릿 API버전이 중요**한 거다. 제우스 서블릿 API버전만 맞춰주면 된다.

포트폴리오에 서블릿 API버전 몇 버전으로 구축했는지 적으면 된다.

---

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%205.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%205.png)

서블릿 버전을 2.5 버전으로 내려보자 하위 버전으로 만들어야 구조 이해하기 편하다

PATH에 해당하는 값을 Context root로 결정한다. deployement descriptor 배치 기술서 인데 어떻게 배치할거에요라고 적어 놓은 문서인데 그 이름이 web.xml 체크박스 해주면 된다.

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%206.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%206.png)

servlet-api.jar은 자바 쪽에서 제공되는 서블릿 api용 압축 파일이다. 톰켓도 자바 쪽에서 제공되는 api를 이용해서 구현해야 하기 때문에 해놨을 거다. 이 압축 파일이 톰켓에도 있고 제우스에도 있다. 그 압축 파일을 구현한 압축 파일들도 있다.

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%207.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%207.png)

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%208.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%208.png)

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%209.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%209.png)

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2010.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2010.png)

부모 HttpServlet로부터 상속 받는다 대표 메서드가 init, destory, service, doGet, doPost 메서드가 있다. 부모가 갖고 있는 메서드를 그냥 상속 받아 쓰고 있는 게 아니라 재정의 오버라이딩 했다.

서블릿에서는 메인 메서드가 없다

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2011.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2011.png)

web.xml는 배치 기술서라 했다. servlet과 servlet-mapping은 eclipse가 자동 element를 만들어 준 거다.

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2012.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2012.png)

tomcat서버 안에는 servlet-api.jar jsp-api.jar 실행 엔진이 들어 있다.

제우스에는 EJB를 실행 시켜줄 수 있는 엔진이 있다.

EJB어플이라는 것은 분산 환경을 만들기 위한 어플이다. 이것을 EE에서 제공 해준다.

JAVA EE를 완벽히 구현한 제품을 JAVA WAS부른다. Tocat은 WAS가 아니다. 제우스나 웹로직이나 웹스피어는 WAS라 부른다.

---

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2013.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2013.png)

Add and Remove> myback을 오른쪽으로 배포

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2014.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2014.png)

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2015.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2015.png)

실행에 필요한 건 class이지 자바 소스코드가 아닌 거다. 클래스들만 톰켓서버에 배포된다.

이클립스 구조를 add버튼 누르면 표준화 된 구조로 만들어지고 제우스, 웹로직 서버에 갖다 놓아도 똑같은 결과가 나타난다.

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2016.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2016.png)

WebContent에 a.html만들면 그대로 복사되서 myback밑에 가서 붙는다.

요청을 톰켓서버 url로 요청한다.

WEB-INF 주소 url로 직접 경로 접근 못한다!!

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2017.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2017.png)

http://localhost:8888/myback/first url 패턴 값을 적어주면 되는데 web.xml 파일에 명시되어 있다.

생성자 호출과 init메서드가 또 호출 되는게 아니고 서비스라는 메소드만 재 호출이 된다. 그 얘기는 servlet형태 객체가 매번 만들어지는 게 아니라 한번만 만들어진다.

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2018.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2018.png)

톰켓이 중지될 때 destroy메소드가 자동 호출 된다.

---

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2019.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2019.png)

myfront, myback 웹 컨텍스트가 서로 별개이기 때문에 각각 Servlet Context객체가 만들어진다.

Servlet Context에 param이라는 인스턴스 변수가 있는데 이름과 값으로 맵 자료 형태로 이루어져 있다.  param만 있는 게 아니고 version, realpath(실제 디렉토리 경로)정보도 들어 있다. **톰켓 구동**하고 **웹 컨텍스별 서블랙 컨텍스 타입**이 만들어졌다. 웹 브라우저 쪽에서 **요청**이 들어온다. 자원을 찾는데 url패턴이 /first해당하는 서블릿 네임 찾고 서블릿 엘리먼트 찾고 그와 연결되어있는 class를 찾아낸다. web.xml보고 클래스를 찾아내고 클래스 로딩도 하고 클래스 타입의 객체가 있는가 **존재 여부를 확인** **객체가** **없다 하면 클래스 타입의 객체를 생성**한다. **부모 영역부터 먼저 할당**이 된다. 키, 벨류 형태의 여러param형태를 가질 수 있다. **초기 값은 null**이다. 자식에서 init, service, destroy 메소드가 **오버라이딩 재정의** 되어있다. 객체가 생성한 다음에 객체의 **init()호출**이 된다. 부모 쪽에도 있고 자식에도 있는데 **재 정의된 메서드가 호출**이 된 거다. **객체가** **있는 상태라면 객체의 서비스 메서드가 자동 호출** 된다. 당연히 자식 쪽에 오버라이딩 된 메소드가 호출된다.

두 번째 클라이언트가 요청하면 web.xml에 해당하는 클래스를 찾고 **객체가 있으면 서비스 메서드만 호출**한다. **톰켓을 중지 시키면** 관리되어있는 메모리에서 사라져야 하는데 **destroy메소드는 객체 소멸될 때 한번 호출**이 된다.  생성될 때 그리고 소멸될 때 자동 호출 되는 메서드가 있다.

 

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2020.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2020.png)

요청 했으면 서블릿이 응답을 해야 한다. 요청과 응답에 관련된 객체가 생성되는데 서블릿 용 객체가 존재한다 판단 됐을 때 **요청전용객체(HttpServletRequest)**와 **응답전용객체(HttpServletResponse)**가 자동 만들어진다. 이 놈들이 **서비스 메서드가 호출이 될 때** 첫 번째 인자로 **요청에 관련된 객체가 전달**이 되고 두 번째 인자로 **응답에 관련된 객체가 전달**이 된다.

서비스 메소드가 호출이 되면 **요청 방식에 따라** get방식으로 요청 했을 경우 **doGet**, post방식 **doPost()**가 호출된다.

- FirstController.java

```java
package com.my.control;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstController
 */
public class FirstController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstController() {
        super();
        System.out.println("FirstController생성자 호출");
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("FirstController의 init()호출");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		System.out.println("FirstController의 destroy()호출");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FirstController의 service()호출");
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FirstController의 doGet()호출");
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("FirstController의 doPost()호출");
//		doGet(request, response);
	}

}
```

---

![day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2021.png](day01%20a772917e1d614930ba747ed320b03b3e/Untitled%2021.png)

요청 방식이 get방식으로 처리했을 때 doGet메서드만 오버라이딩 해주면 된다

- RequestTest.java

```java
package com.my.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 요청객체 이해하기
 */
public class RequestTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id"); //요청전달데이터 얻기 //한개의 값만 받아올수있다.
		
		System.out.println("전송된 아이디는" + id + "입니다.");
		
		String[] arr = request.getParameterValues("c"); //return 타입이 string 배열. //여러개의  값을 한꺼번에 배열형태로 받아올수있다.
		for(String cValue : arr) {
			System.out.println("전송된 checkbox값은" + cValue +"입니다.");
		}
		
		//요청경로에 관련된 메서드들
		StringBuffer url = request.getRequestURL(); //전체 path : http://localhost:8888/myback/requesttest
		String uri = request.getRequestURI(); //서브 path : /myback/requesttest
		String path = request.getContextPath(); //지금 사용되고 있는 path : /myback
		System.out.println("url=" + url);
		System.out.println("uri=" + uri);
		System.out.println("contextPath=" + path);
		
	}

}
```

요청 시에 전달된 데이터의 이름과 값이 request객체의 param에 채워진다

web.xml문서가 바뀌면 tomcat server 새로 restart

크롬 브라우저 시 한글 깨짐은 안 깨지나 다른 브라우저일 경우 깨짐 현상이 나타날 수 있다.

param에 채워지는 값이 아이디 이름의 값이 하나 이지만, 이름에 해당하는 값이 여러 개일 수 도 있다.

---

- ResponseTest.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 응답객체 이해하기
 */
public class ResponseTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
						HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8"); //응답형식지정
		PrintWriter out = response.getWriter(); //응답출력스트림 얻기
		out.print("응답내용"); //응답출력
		out.print("<h1>최예만's Backend Programming</h1>");
	}

}
```

http프로토콜 기본 encoding은 서유럽 국가 encoding을 쓴다. 

한글 같은 경우는 2byte or 3byte처리가 되어야 한다.  응답 결과를 charset=utf-8로 설정

println 줄 바꿈 있는 메서드이고 print는 줄 바꿈 없는 메서드이다.  줄 바꿈이 포함되고 싶으면 println  줄 바꿈으로 굳이 넣을 필요 없으면 print메서드를 사용하면 된다.