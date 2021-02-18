package com.my.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.vo.OrderInfo;

public interface OrderDAO {
	/**
	 * 주문기본정보와 상세정보들을 저장소에 추가한다.
	 * @param info 주문기본정보와 상세정보들
	 */
	void insert(OrderInfo info) throws AddException; //OrderInfo 안에 lines가 있으니까 info하나만 있어도 된다.
	
	/**
	 * 주문자아이디가 주문한 주문기본 정보들을 검색한다.
	 * @param order_id 주문자 아이디
	 * @return 주문 상세들을 포함한 주문 기본목록들
	 * @throws FindException 주문정보가 없을때 예외 발생한다.
	 */
	List<OrderInfo>selectById(String order_id) throws FindException;
}
