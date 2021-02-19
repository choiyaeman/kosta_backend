package com.my.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.vo.Product;

public class JacksonTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println("JAVA객체를 JSON문자열로 쓰기");
		Product p = new Product("C0001", "아메리카노", 1000);
		String jsonStr = mapper.writeValueAsString(p);
		System.out.println(p); //p.toString()자동호출
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		System.out.println("List객체를 JSON문자열로 쓰기");
		List<String> list = new ArrayList<>();
		list.add("ONE");
		list.add("TWO");
		jsonStr = mapper.writeValueAsString(list);
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		System.out.println("Map객체를 JSON문자열로 쓰기");
		Map<String, Integer> map = new HashMap<>();
		map.put("one", 1);
		map.put("two", 2);
		jsonStr = mapper.writeValueAsString(map);
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		
		Map<String, Object> map1 = new HashMap<>();
		map1.put("prod_no", p.getProd_no());
		map1.put("prod_name", p.getProd_name());
		map1.put("prod_price", p.getProd_price());
		map1.put("quantity", 5);
		jsonStr = mapper.writeValueAsString(map1);
		System.out.println(jsonStr);
		System.out.println("----------------");
		
		
		List<Map> list2 = new ArrayList<>();
		for(int i=0; i<3; i++) {
			Map<String, Object> map2 = new HashMap<>();
			map2.put("prod_no", "C"+i);
			map2.put("prod_name", "Name"+i);
			map2.put("prod_price", i*1000);
			map2.put("quantity", i);
			list2.add(map2);
		}
		jsonStr = mapper.writeValueAsString(list2);
		System.out.println(jsonStr);
		System.out.println("----------------");

	}

}
