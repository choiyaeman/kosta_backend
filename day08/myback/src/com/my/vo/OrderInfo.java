package com.my.vo;

import java.util.Date;
import java.util.List;

public class OrderInfo {
/*
 * ORDER_NO                                  NOT NULL NUMBER
 ORDER_ID                                  NOT NULL VARCHAR2(5)
 ORDER_DT                                           DATE
*/
	private int order_no;
	//private String order_id;//??
	private Customer c;
	private java.util.Date order_dt;
	private List<OrderLine> lines; //order_info가 order_line을 갖는다 -> has a 관계
	public OrderInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderInfo(int order_no, Customer c, Date order_dt, List<OrderLine> lines) {
		super();
		this.order_no = order_no;
		this.c = c;
		this.order_dt = order_dt;
		this.lines = lines;
	}
	
	@Override
	public String toString() {
		return "OrderInfo [order_no=" + order_no + ", c=" + c + ", order_dt=" + order_dt + ", lines=" + lines + "]";
	}
	
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public Customer getC() {
		return c;
	}
	public void setC(Customer c) {
		this.c = c;
	}
	public java.util.Date getOrder_dt() {
		return order_dt;
	}
	public void setOrder_dt(java.util.Date order_dt) {
		this.order_dt = order_dt;
	}
	public List<OrderLine> getLines() {
		return lines;
	}
	public void setLines(List<OrderLine> lines) {
		this.lines = lines;
	}

}
