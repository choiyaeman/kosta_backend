package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			//세션객체(WB별 객체)얻기
			HttpSession session = request.getSession();
			
			//속성추가("속성명: loginInfo, 값: id값");
			session.setAttribute("loginInfo", id);
			
			out.print("{\"status\":1}"); //응답 출력하기
		} catch (FindException e) {
			e.printStackTrace();
			out.print("{\"status\":-1}");
		}
	}

}
