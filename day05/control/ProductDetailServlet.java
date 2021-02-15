package com.my.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		String prod_no = request.getParameter("prod_no");
		
		//비지니스로직 호출
		ProductService service = new ProductService();
		try {
			Product p = service.findByNo(prod_no);
			//요청속성(속성명: "p", 속성값: p)으로 추가
			request.setAttribute("p", p);
			//VIEWER(JSP)로 이동
			String path = "/productdetailresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("e", e);
			String path = "/errorresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}

}
