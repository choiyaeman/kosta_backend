package com.my.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PutCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달 데이터 얻기
		String prod_no = request.getParameter("prod_no");
		String quantity = request.getParameter("quantity"); //request.getParameter는 String타입으로 받는다 그러므로 int타입으로 변환 시켜줘야 한다.
		int intQuantity = Integer.parseInt(quantity);
		
		//세션(클라이언트별객체)얻기
		HttpSession session = request.getSession();
		//cart라는 이름의 속성값 얻기
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		
		//cart가 없으면
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		
		//cart에서 상품번호에 해당하는 수량을 얻기
		Integer quantity2 = cart.get(prod_no);
		if(quantity2 != null) { //cart에 상품번호가 있는경우
			intQuantity += quantity2; //수량을 증가
		}
		System.out.println("장바구니에 넣기 할 상품번호:" + prod_no +", 수량:" + intQuantity);
		//cart에 상품번호, 수량추가
		cart.put(prod_no, intQuantity);
		
		//장바구니확인 테스트
		System.out.println("장바구니 넣기 확인");
		Set<String> keys = cart.keySet();
		for(String key: keys) {
			System.out.println(key + ":" + cart.get(key));
		}
	}

}
