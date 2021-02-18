package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MoveServlet
 */
public class MoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<a href=\"move?opt=forward&id=hello\">포워드</a><br>"); //?는 쿼리스트링이 만들어질수있다.
		out.print("<a href=\"move?opt=include\">인클루드</a><br>");
		out.print("<a href=\"move?opt=redirect&id=hello\">리다이렉트</a><br>");
	}
	protected void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //기존페이지 내용은 clear시키고 다른 페이지로 요청을 전달하고 돌아오지 않는다
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<h1>START</h1>");
		//서버단 웹컨텍스트내에서 페이지 이동
		String path = "/requesttest"; //"http://www.naver.com";//"/first";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		out.print("<h1>END</h1>");
	}
	protected void redirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트단에서 재요청
		String location = "http://www.naver.com";//"requesttest";//"first";
		response.sendRedirect(location);
	}
	protected void include(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { //기존페이제 내용에다 include 내용이 포함
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<h1>START</h1>");
		//서버단 웹컨텍스내 자원을 포함
		String path = "login.html";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.include(request, response);
		out.print("<h1>END</h1>");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		String opt = request.getParameter("opt");
		if(opt == null) {
			show(request, response);
		} else if(opt.equals("forward")) {
			forward(request, response);
		} else if(opt.equals("include")) {
			include(request, response);
		} else if(opt.equals("redirect")) {
			redirect(request, response);
		}
	}

}
