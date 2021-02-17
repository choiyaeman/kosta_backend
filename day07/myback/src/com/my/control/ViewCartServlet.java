package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		if(cart == null || cart.size() == 0) { //장바구니가 없거나 장바구니가 비어있다면
			out.print("{\"status\": -1}");
			return;
		}
		//장바구니가 있는 경우
		//맵생성
		Map<Product, Integer> map = new HashMap<>();
		
		//장바구니의 상품번호별 상품 정보얻기
		Set<String>prod_nos = cart.keySet();
		ProductService service = new ProductService();
		for(String prod_no: prod_nos) {
			
			try {
				Product p = service.findByNo(prod_no); //상품번호별 상품정보얻기
				int quantity = cart.get(prod_no); //장바구니에 담긴 수량
				map.put(p, quantity); //맵에 추가
			} catch (FindException e) { //갑자기 db와의 연결 끊기거나 장바구니 넣을때가지도 상품이 있었는데 잠깐 자리를 비운 사이 db에 상품정보가 삭제 된 경우
				e.printStackTrace();
			}
			
		}
		
		//맵의 내용을 JSON형태로 응답하기
		int index = 0;
		out.print("[");
		for(Product p: map.keySet()) {
			if(index > 0) {
				out.print(",");
			}
			int quantity = map.get(p);
			out.print("{\"prod_no\": \""+ p.getProd_no() +"\", "
					+ "\"prod_name\":\""+ p.getProd_name() +"\", "
					+ "\"prod_price\": "+ p.getProd_price() +", "
					+ "\"quantity\":   "+ quantity +" }");
			index++;
		}
		out.print("]");
	}

}
