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
