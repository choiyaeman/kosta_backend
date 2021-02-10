<%@page import="com.my.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
            *{
                box-sizing: border-box; /* width값이 전체 너비 */
                padding: 0;
                margin: 0;
            }
            div.productlist{
                background-color: white;
                width: 100%;
                height: 600px;
                /* margin: 10px; */
                overflow: auto;
            }
            div.productlist>div.product{
                width: 320px;
                height: 400px;
                border: 1px solid;
                float: left;
            }
            div.productlist>div.product>ul{
                list-style-type: none;
                padding-left: 0;
            }
            div.productlist>div.product>ul>li>img{
            	widht : 80%;
            }
        </style>
<div class="productlist">
<%
//3. 요청속성얻기
List<Product> list = (List)request.getAttribute("list");
for(Product p: list) {
%>
 <!-- 하나의 상품 -->
 <div class="product">
	 <ul>
		 <li><img src="./images/<%=p.getProd_no() %>.jpg" alt="<%=p.getProd_name() %>"></li>
		 <li><%=p.getProd_name() %></li>
		 <li><%=p.getProd_price() %></li>
	 </ul>
 </div>
<%}
%>
</div>