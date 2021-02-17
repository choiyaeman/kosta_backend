<%@page import="com.my.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
*{
    box-sizing: border-box; /* width값이 전체 너비 */
    padding : 0;
    margin : 0;
}
div.productlist{
	/* position : relative; */
    background-color: white;
    width: 100%;
    height: 600px;
    overflow: auto;
}
div.productlist>div.product{
    width : 300px;
    height : 300px;
    text-align: center;
    border: 1px dotted;
    border-radius: 10px;
    overflow: hidden;
    float: left;

}
div.productlist>div.product>ul{
    list-style-type: none;
    padding-left: 0;
}
div.productlist>div.product>ul>li>img{
	width : 80%;
}
</style>
<!--jquery사용-->
<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>-->
<script>
$(function(){
	alert("productlist");
	//DOM트리에서 class속성값이 product인 div객체찾기
	var $productDivObj = $("div.productlist>div.product");
	
	$productDivObj.each(function(index, element){
		$(element).click(function(){
			var imgSrc = $(this).find("ul>li.prod_no>img").attr("src");
			
			var lastSlash = imgSrc.lastIndexOf("/"); //마지막 슬래시 위치
			var ext = imgSrc.indexOf(".jpg"); //.jpg파일확장자 위치
			var prod_no = imgSrc.substring(lastSlash+1, ext);
			alert("상품이 클릭되었습니다. 상품번호:" + prod_no);
			var prod_name = $(this).find("ul>li.prod_name").html();
			var prod_price = $(this).find("ul>li.prod_price").html();
			
			$("section>article").empty();
			var url = "./productdetail";
			var method = "get";
			var data = "prod_no=" + prod_no;
			$.ajax({
				url : url,	
				method : method,
				data : data,
				success: function(data){
					$("section>article").html(data);
				},
				error: function(jqXHR){
					alert("오류:" + jqXHR.status);
				}
			});
		});
	});
});
</script> 
<div class="productlist">    
<%@include file="searchproduct.jsp" %>
<%
//3. 요청속성얻기
List<Product> list = (List)request.getAttribute("list");
for(Product p: list){
%>
 <!--하나의 상품-->
 <div class="product">
     <ul>
         <li class="prod_no"><img src="./images/<%=p.getProd_no() %>.jpg" alt="<%=p.getProd_name() %>"></li>
         <li class="prod_name"><%=p.getProd_name() %></li>
         <li class="prod_price"><%=p.getProd_price() %></li>
     </ul>
 </div>
<%}
%>
</div>

