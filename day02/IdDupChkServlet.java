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
