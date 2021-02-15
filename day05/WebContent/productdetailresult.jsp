<%@page import="com.my.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Product p = (Product)request.getAttribute("p");
%>
<div class="productdetail" style="width:500px;">
	<img src="./images/<%=p.getProd_no()%>.jpg" style="float:left; width:35%">
	<div class="detail" style="float:right;">
		<ul style="list-style-type: none; padding: 0 10px; margin: 0">
			<li>상품번호: <%=p.getProd_no()%></li>
			<li>상품명: <%=p.getProd_name()%></li>
			<li>가격: <%=p.getProd_price()%></li>
			<li>수량: <input type="number" value="1" min="1" max="99">
			<li><input type="button" value="장바구니 넣기">
		</ul>
	</div>
</div>