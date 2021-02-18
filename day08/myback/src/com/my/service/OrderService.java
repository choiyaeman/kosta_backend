package com.my.service;

import com.my.dao.OrderDAO;
import com.my.dao.OrderDAOOracle;
import com.my.exception.AddException;
import com.my.vo.OrderInfo;

public class OrderService {
	private OrderDAO dao = new OrderDAOOracle();
	public void add(OrderInfo info) throws AddException{
		dao.insert(info);
	}

}
