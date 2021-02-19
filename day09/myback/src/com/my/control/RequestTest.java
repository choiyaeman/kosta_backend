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
