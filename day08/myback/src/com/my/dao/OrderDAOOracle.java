package com.my.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.sql.MyConnection;
import com.my.vo.OrderInfo;
import com.my.vo.OrderLine;

public class OrderDAOOracle implements OrderDAO {
	private void insertInfo(Connection con, OrderInfo info) throws AddException {
		PreparedStatement pstmt = null;
		String insertInfoSQL = "INSERT INTO order_info(order_no, order_id, order_dt) VALUES (order_seq.NEXTVAL, ?, SYSDATE)";
		try {
			pstmt = con.prepareStatement(insertInfoSQL);
			pstmt.setString(1, info.getC().getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			MyConnection.close(null, pstmt); //같은 connection을 써야해서 connection은 close를 하지 않고 pstmt만 close.
		}
	}
	private void insertLines(Connection con, List<OrderLine> lines) throws AddException {
		PreparedStatement pstmt = null;
		String insertLineSQL = "INSERT INTO order_line(order_no, order_prod_no, order_quantity) VALUES (order_seq.CURRVAL, ?, ?)";
		try {
			pstmt = con.prepareStatement(insertLineSQL);
			/*for(OrderLine line: lines) {
				pstmt.setString(1, line.getP().getProd_no()); //첫 번째 물음표인 order_prod_no
				pstmt.setInt(2, line.getOrder_quantity()); //두 번째 물음표인 order_quantity
				pstmt.executeUpdate(); //개별 처리한다
			}*/
			for(OrderLine line: lines) { 
				pstmt.setString(1, line.getP().getProd_no()); //첫 번째 물음표인 order_prod_no
				pstmt.setInt(2, line.getOrder_quantity()); //두 번째 물음표인 order_quantity
				pstmt.addBatch(); //일괄처리작업에 추가한다
			}
			pstmt.executeBatch(); //일괄처리한다
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(null, pstmt); //같은 connection을 써야해서 connection은 close를 하지 않고 pstmt만 close.
		}
	}
	
	@Override
	public void insert(OrderInfo info) throws AddException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		try {
			//JDBC는 기본 autocommit으로 설정되어 있어서 각각의 트랜잭션으로 처리
			//insertInfo(con, info); //insert 기본정보 -ex)OK ->자동커밋 ->복구X
			//insertLines(con, info.getLines()); //insert 상세정보들 -ex)FAIL ->복구X   //옳지않는 값이 들어가게된다면 원 상태로 복구해줘야 한다. 그렇지만 jdbc자체가 자동commit이다보니까 복구를 할 수 없다.
			
			//하나의 트랜잭션으로 처리[기본, 상세 insert]
			con.setAutoCommit(false); //자동커밋해제
			insertInfo(con, info); //insert 기본정보 -ex)OK
			insertLines(con, info.getLines()); //insert 상세정보들 -ex)FAIL
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddException e) {
			try {
				con.rollback(); //복구
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			MyConnection.close(con);
		}
	}

	@Override
	public List<OrderInfo> selectById(String order_id) throws FindException {
		return null;
	}

}
