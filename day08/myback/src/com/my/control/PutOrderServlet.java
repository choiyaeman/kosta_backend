package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.exception.AddException;
import com.my.service.OrderService;
import com.my.vo.Customer;
import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;
import com.my.vo.Product;

public class PutOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ObjectMapper mapper = new ObjectMapper();
		//---로그인 여부----
		String loginedId = (String)session.getAttribute("loginInfo"); //attribte의 정보는 자바 최상클래스 타입으로 저장되기 때문에 원래 상태인 String 타입으로 casting
		if(loginedId == null) { //로그인 안된 경우
			Map<String, Integer> map = new HashMap<>();
			map.put("status", 0);
			String jsonStr = mapper.writeValueAsString(map);
			out.print(jsonStr);
			//out.print("{\"status\": 0}");
		} else { //로그인 된 경우
			Map<String, Integer> cart= (Map)session.getAttribute("cart"); //장바구니얻기
			
			OrderInfo info = new OrderInfo();
			//주문기본정보 설정
			Customer c = new Customer();
			c.setId(loginedId);
			info.setC(c); //주문자ID설정
			
			//장바구니내용을 주문상세정보 객체로 만들어서 리스트에 추가
			List<OrderLine> lines = new ArrayList<>();
			for(String prod_no: cart.keySet()) { //향상된 for문으로 장바구니에 있는 상품 번호들 키들만 가져오는 것
				int quantity = cart.get(prod_no);
				OrderLine line = new OrderLine();
				
				Product p = new Product();
				p.setProd_no(prod_no);
				line.setP(p);
				
				line.setOrder_quantity(quantity);
				
				//주문상세정보객체를 리스트에 추가
				lines.add(line);
			}
			//리스트를 주문기본에 설정
			info.setLines(lines);
			OrderService service = new OrderService();
			try {
				service.add(info); 
				session.removeAttribute("cart"); //장바구니 삭제
				Map<String, Object> map = new HashMap<>();
				map.put("status", 1);
				String jsonStr = mapper.writeValueAsString(map);
				out.print(jsonStr);
			} catch (AddException e) {
				e.printStackTrace();
				Map<String, Object> map = new HashMap<>();
				map.put("status", -1);
				map.put("msg", e.getMessage());
				String jsonStr = mapper.writeValueAsString(map);
				out.print(jsonStr);
			}
		}
		
	}

}
