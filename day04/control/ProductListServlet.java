package com.my.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		ProductService service = new ProductService();
//		try {
//			List<Product> list = service.findAll();
//			out.print("<div class=\"productlist\">");
//			for(Product p: list) {
//			out.print("<div class=\"product\">\r\n" + 
//					"                <ul>\r\n" + 
//					"                    <li><img src=\"./images/"+ p.getProd_no() +".jpg\" alt=\""+ p.getProd_name() +"\"></li>\r\n" + 
//					"                    <li>"+ p.getProd_name() +"</li>\r\n" + 
//					"                    <li>"+ p.getProd_price() +"</li>\r\n" + 
//					"                </ul>\r\n" + 
//					"            </div>");
//			
//			}
//			out.print("</div>");
//			
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
		

		ProductService service = new ProductService();
		try {
			//1.비지니스로직 호출
			List<Product> list = service.findAll();
			
			//2.요청속성추가
			request.setAttribute("list", list);
			
			//3.페이지 이동
			String path = "/productlistresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
