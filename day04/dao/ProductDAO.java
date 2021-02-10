package com.my.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Product;

public interface ProductDAO {
	/*
	 * 
	 * @param prod_no 상품번호
	 * @return 삼품객체
	 * @throws FindException 번호에 해당하는 상품이 없거나 저장소에 문제가 발생하면 FindException이 강제 발생한다
	 */
	/**
	 * 상품번호에 해당하는 상품을 검색한다
	 * @param prod_no
	 * @return
	 * @throws FindException
	 */
	Product selectByNo(String prod_no) throws FindException;
	/**
	 * 모든 상품을 검색한다
	 * @return 상품 객체들
	 * @throws FindException 상품이 없거나 저장소에 문제가 있으면 FindeException이 강제 발생한다
	 */
	List<Product> selectAll() throws FindException;
	
	void insert(Product product) throws AddException;
	Product update(Product product) throws ModifyException;
	Product delete(String prod_no) throws RemoveException;
}
