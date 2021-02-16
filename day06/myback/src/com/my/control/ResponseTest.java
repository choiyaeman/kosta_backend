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
