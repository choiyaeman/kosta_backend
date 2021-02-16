package com.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.sql.MyConnection;
import com.my.vo.Product;

public class ProductDAOOracle implements ProductDAO {

	@Override
	public Product selectByNo(String prod_no) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}	
		String selectByNoSQL = "SELECT * FROM product WHERE prod_no = ?";
		try {
			pstmt = con.prepareStatement(selectByNoSQL);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
			if(rs.next()) { //상품이 존재할 경우
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				Product p = new Product(prod_no, prod_name, prod_price);
				return p;
			} else { //상품이 존재하지 않을 경우
				throw new FindException("번호에 해당하는 상품이 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public List<Product> selectAll() throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectAllSQL = "SELECT *FROM product ORDER BY prod_name ASC";
		List<Product> all = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) { //상품이 여러개 이므로 반복문 처리
				Product p = new Product(rs.getString("prod_no"), rs.getString("prod_name"), rs.getInt("prod_price"));
				all.add(p);
			}
			if(all.size() == 0) { //한건도 없을 경우
				throw new FindException("상품이 하나도 없습니다.");
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public List<Product> selectByNoOrName(String prod) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectAllSQL = "SELECT * FROM product WHERE prod_no LIKE ? OR prod_name LIKE ? ORDER BY prod_name ASC";
		List<Product> all = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			pstmt.setString(1, "%"+prod+"%");
			pstmt.setString(2, "%"+prod+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Product p = new Product(rs.getString("prod_no"), rs.getString("prod_name"), rs.getInt("prod_price"));
				all.add(p);
			}
			if(all.size() == 0) {
				throw new FindException("상품이 하나도 없습니다");
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	@Override
	public void insert(Product product) throws AddException {
		// TODO Auto-generated method stub

	}

	@Override
	public Product update(Product product) throws ModifyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product delete(String prod_no) throws RemoveException {
		// TODO Auto-generated method stub
		return null;
	}

}
