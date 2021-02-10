package com.my.service;

import java.util.List;

import com.my.dao.ProductDAO;
import com.my.dao.ProductDAOOracle;
import com.my.exception.FindException;
import com.my.vo.Product;

public class ProductService {
	private ProductDAO dao;
	public ProductService() {
		dao = new ProductDAOOracle();
	}
	public Product findByNo(String prod_no) throws FindException {
		return dao.selectByNo(prod_no);
	}
	
	public List<Product> findAll() throws FindException {
		return dao.selectAll();
	}

}
