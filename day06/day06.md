# day06

![1](https://user-images.githubusercontent.com/63957819/108043786-de26a280-7084-11eb-8683-71aab2994d80.png)

- SearchProductServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class SearchProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prod = request.getParameter("prod");
		System.out.println(prod);
		ProductService service = new ProductService();
		
		try {
			//1.비지니스로직 호출
			List<Product> list = service.findByNoOrName(prod);
			
			//2.요청속성추가
			request.setAttribute("list", list);
			
			//3.페이지 이동
			String path = "/productlistresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("e", e);
			String path = "/errorresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}

}
```

- searchproduct.jsp

```java
<%@ page contentType="text/html; charset=UTF-8"%>
<style>
div.searchproduct{

    width: 180px;
    height: 32px;
    margin : 10px;
    top: 9px;
    border: 1px solid #ccc;
    border-radius: 5px;
    position: relative;
    vertical-align: baseline;
}

div.searchproduct>input[name=product]{
    border: none;
    color: #777;
    font-size: 12px;
    height: 22px;
    left: 0;
    padding: 0 10px;
    position: absolute;
    top: 5px;
    width: 80%;
}
div.searchproduct>img{
	position: absolute;
    z-index: 1;
    /* vertical-align: middle; */
    max-width: 100%;
    right: 0;
    top: 5px;
    
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script>
$(function(){
	$("div.searchproduct>img").click(function(){
		var url = "./searchproduct";
		var method = "post";
		var data = "prod=" + $("div.searchproduct>input[name=product]").val();
		
		$("section>article>div.productlist>div.product").remove();
		
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
		return false;
	});
});
</script>
<div class="searchproduct">
   <input type="text" placeholder="통합검색" name="product" >
  <!--  <input type="button" value="검색"> -->
   <img alt="통합검색" src="./images/icon_magnifier_black.png">
</div>
```

![2](https://user-images.githubusercontent.com/63957819/108043792-dff06600-7084-11eb-88ad-0bee6dcc190c.png)

Servlet 개수 많아지는 것을 모아보자. [SearchProductServlet.java](http://searchproductservlet.java) 파일 지우고 web.xml에서도 지워주자~ ProductListServlet으로 모은다.

- ProductListServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prod = request.getParameter("prod");
		System.out.println(prod);
		ProductService service = new ProductService();
		
		try {
			//1.비지니스로직 호출
			List<Product> list = service.findByNoOrName(prod);
			
			//2.요청속성추가
			request.setAttribute("list", list);
			
			//3.페이지 이동
			String path = "/productlistresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("e", e);
			String path = "/errorresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		ProductService service = new ProductService();
//		try {
//			List<Product> list = service.findAll();
//			out.print("<div class=\"productlist\">");
//			for(Product p: list) {
//			out.print("<div class=\"product\">\r\n" + 
//					"                <ul>\r\n" + 
//					"                    <li><img src=\"./images/"+ p.getProd_no() +".jpg\" alt=\""+ p.getProd_name() +"\"></li>\r\n" + 
//					"                    <li>"+ p.getProd_name() +"</li>\r\n" + 
//					"                    <li>"+ p.getProd_price() +"</li>\r\n" + 
//					"                </ul>\r\n" + 
//					"            </div>");
//			
//			}
//			out.print("</div>");
//			
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
		

		ProductService service = new ProductService();
		try {
			//1.비지니스로직 호출
			List<Product> list = service.findAll();
			
			//2.요청속성추가
			request.setAttribute("list", list);
			
			//3.페이지 이동
			String path = "/productlistresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (FindException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
```

- productlistresult.jsp

```java
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
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
```

- searchproduct.jsp

```java
<%@ page contentType="text/html; charset=UTF-8"%>
<style>
div.searchproduct{

    width: 180px;
    height: 32px;
    margin : 10px;
    top: 9px;
    border: 1px solid #ccc;
    border-radius: 5px;
    position: relative;
    vertical-align: baseline;
}

div.searchproduct>input[name=product]{
    border: none;
    color: #777;
    font-size: 12px;
    height: 22px;
    left: 0;
    padding: 0 10px;
    position: absolute;
    top: 5px;
    width: 80%;
}
div.searchproduct>img{
	position: absolute;
    z-index: 1;
    /* vertical-align: middle; */
    max-width: 100%;
    right: 0;
    top: 5px;
    
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script>
$(function(){
	$("div.searchproduct>img").click(function(){
		//var url = "./searchproduct";
		var url = "./productlist";
		
		var method = "post";
		var data = "prod=" + $("div.searchproduct>input[name=product]").val();
		
		$("section>article>div.productlist>div.product").remove();
		
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
		return false;
	});
});
</script>
<div class="searchproduct">
   <input type="text" placeholder="통합검색" name="product" >
  <!--  <input type="button" value="검색"> -->
   <img alt="통합검색" src="./images/icon_magnifier_black.png">
</div>
```

- web.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>myback</display-name>
  <welcome-file-list>
    <welcome-file>semanticcssjq.html</welcome-file>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  <servlet>
    <description></description>
    <display-name>FirstController</display-name>
    <servlet-name>FirstController</servlet-name>
    <servlet-class>com.my.control.FirstController</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>FirstController</servlet-name>
    <url-pattern>/first</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>RequestTest</display-name>
    <servlet-name>RequestTest</servlet-name>
    <servlet-class>com.my.control.RequestTest</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>RequestTest</servlet-name>
    <url-pattern>/requesttest</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>ResponseTest</display-name>
    <servlet-name>ResponseTest</servlet-name>
    <servlet-class>com.my.control.ResponseTest</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>ResponseTest</servlet-name>
    <url-pattern>/responsetest</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>LoginServlet</display-name>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.my.control.LoginServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>IdDupChkServlet</display-name>
    <servlet-name>IdDupChkServlet</servlet-name>
    <servlet-class>com.my.control.IdDupChkServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>IdDupChkServlet</servlet-name>
    <url-pattern>/iddupchk</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>SignupServlet</display-name>
    <servlet-name>SignupServlet</servlet-name>
    <servlet-class>com.my.control.SignupServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>SignupServlet</servlet-name>
    <url-pattern>/signup</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>ProductListServlet</display-name>
    <servlet-name>ProductListServlet</servlet-name>
    <servlet-class>com.my.control.ProductListServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>ProductListServlet</servlet-name>
    <url-pattern>/productlist</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>MoveServlet</display-name>
    <servlet-name>MoveServlet</servlet-name>
    <servlet-class>com.my.control.MoveServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>MoveServlet</servlet-name>
    <url-pattern>/move</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>ProductDetailServlet</display-name>
    <servlet-name>ProductDetailServlet</servlet-name>
    <servlet-class>com.my.control.ProductDetailServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>ProductDetailServlet</servlet-name>
    <url-pattern>/productdetail</url-pattern>
  </servlet-mapping>

</web-app>
```

- ProductService.java

```java
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
	public List<Product> findByNoOrName(String prod) throws FindException{
		return dao.selectByNoOrName(prod);
	}

}
```

- ProductDAO.java

```java
package com.my.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Product;

public interface ProductDAO {
	/**
	 * 상품번호에 해당하는 상품을 검색한다
	 * @param prod_no 상품번호
	 * @return 삼품객체 번호에 해당하는 상품이 없거나 저장소에 문제가 발생하면 FindException이 강제 발생한다
	 * @throws FindException
	 */
	Product selectByNo(String prod_no) throws FindException;
	/**
	 * 모든 상품을 검색한다
	 * @return 상품 객체들
	 * @throws FindException 상품이 없거나 저장소에 문제가 있으면 FindeException이 강제 발생한다
	 */
	List<Product> selectAll() throws FindException;
	/**
	 * 상품번호나 상품이름 검색
	 * @param prod
	 * @return
	 * @throws FindException
	 */
	List<Product> selectByNoOrName(String prod) throws FindException;
	
	void insert(Product product) throws AddException;
	Product update(Product product) throws ModifyException;
	Product delete(String prod_no) throws RemoveException;
	
}
```

- ProductDAOOracle.java

```java
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
```

---

- semanticcssjq.html

```java
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>시멘틱태그-CSS-jQuery</title>
        <style>
            * { 
                box-sizing: border-box;

                color : #000000; 
                /* font-size: 1.25em; */
            }
            header { background: #f6f5ef; 
                     margin: 5px auto;
                     position: relative;
            } 
            /*section{background-color:#eef2f3; height:500px; margin-bottom: 5px;}
            section>article.one{background-color: #b3ffd9}
            section>article.two{background-color: #1e3932;color:white;}*/
            
            footer{ background-color: #2C2A29;  color:#fff;  }
                    header, footer { height:100px; width: 1100px;  }
                    header>nav>ul{ list-style-type: none; padding: 0px;}
                    header>nav>ul>li{ 
                    width:100px; 
                    display: inline-block; 
                    margin: 0px 10px; 
                    text-align: center;
                    }

            header>nav>ul>li>a{
                text-decoration: none;
            }
            header>nav>ul>li>a:hover{
                background-color: black;
                color: white;
                font-weight: bold;
            }
            
            header>h1{
                width: 30%;    
                margin: 0 auto;
                height: 100%;
                display: inline-block;
                position: relative;
                border: 1px solid;
            }
            /* 이미지 로고 */
            header>h1>a{
                display: block;
                width : 100%;
                height:100%;
                margin: 0;
                padding: 0;
                
                position: absolute;
                top: 10px;
                left: 0px;
                
                background-image: url('./images/logo.png');
                background-repeat: no-repeat;
            }
            header>nav{
                display: inline-block;
                width : 60%;
                height: 100%;
                border: 1px solid;
                position: absolute;
                top: 0px;
                right: 0px;
            }
            section{background-color:#eef2f3; 
                width: 1100px;
                height:500px; 
                margin-bottom : 5px;
            }
            section>article{
                width: 60%;
                height: 100%;
                float: left;
            }
            /* section>div{
                width: 60%;
                height: 100%;
                float: left;
            } */
            /* section>div>article.one{background-color: #b3ffd9;
                width: 100%;
                height:40%;
            } */
            /* section>div>article.two{background-color: #1e3932;color:white;
                width: 100%;
                height:40%;
            } */
            section>aside{
                width: 30%;
                height:100%;
                float: right;               
            }
            
            /* 광고이미지 반응*/
            section>aside .pc-badge{
                display: block;
            }
            section>aside .mobile-badge {
                display: none;
            }
            
            @media screen and (max-width: 960px){
                section>aside .pc-badge {
                    display: none;
                }
               section>aside .mobile-badge {
                   display: block;
                }
            }

            /*메뉴 반응*/
            header>nav.small{
                display:none;
            }
            @media screen and (min-width: 641px) and (max-width: 960px){
                header>nav.small{
                    display:inline-block;
                }
                header>nav.large{
                    display:none;
                }
            }
        </style>
        <!--jquery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(function(){
                //DOM트리에서 메뉴객체들 모두찾기
                var $menuObj = $("header>nav>ul>li>a");
                //메뉴가 클릭되면 
                $menuObj.click(function(event){
                    $("section>article").empty(); //article영역 지우기
                    //메뉴객체의 href속성값 얻기
                    //var hrefValue = $menuObj.attr("href"); // (X) 배열의 무조건 0번index객체
                    //var hrefValue = $(this).attr("href");
                    var hrefValue = $(event.target).attr("href");//ex)login.html, signup.html
                    console.log(hrefValue);

                    //AJAX
                    $.ajax({
                        url: hrefValue, //요청URL
                        method: "get", //요청방식.   method속성은 생략가능하다 그러면 무조건 get방식이 되는거다. post방식으로 요청하려면 method속성값을 post라 쓰면 된다.
                        success: function(data){ //성공응답 data는 응답내용
                           //alert("AJAX요청응답 성공");
                           //console.log(data);
                           //article의 영역의 innterHTML로 설정
                           $("section>article").html(data);
                        },
                        error: function(jqXHR){ //실패응답 jqXHR는 jquery xmlRequest객체
                            alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                        } //실패응답
                    });
                    return false; //event.preventDefault();와 event.stopPropagation(); 실행한것과 같음
                });
            
              	//상품목록 메뉴클릭 이벤트를 강제 발생
            	//DOM트리에서 상품목록 메뉴 객체 찾기
            	//console.log($("header>nav>ul>li>a[href=productlist]"));
                $("header>nav>ul>li>a[href=productlist]").trigger("click");
            });
        </script>
    </head>
    <body>
        <header>
            <!--<h1>스타벅스</h1>-->
            <h1><a href="#"></a></h1>
            <nav class="large">
                <ul>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
                    <li><a href="productlist">상품목록</a></li>
                    <li><a href="viewcart.html">장바구니</a></li>
                </ul>
            </nav>
            <!-- <nav class="small">
                <ul>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>                    
                </ul>
            </nav> -->
			<!-- 
			<jsp:include page="menusmall.jsp"/>
			%include file="menusmall.jsp"%>
			 -->
        </header>
        <section>
            <article>article...</article>
            <!-- <div>
                <article class="one">
                스타벅스만의 특별한 혜택, 스타벅스 <mark>리워드</mark>
    스타벅스 회원이세요? 로그인을 통해 나만의 리워드를 확인해보세요.
    스타벅스 회원이 아니세요? 가입을 통해 리워드 혜택을 즐기세요.
                </article>
                <article class="two">
                    푸드 간편하지만 든든하게 채워지는 만족감을 느껴보세요. 신선함과 영양이 가득한 스타벅스 푸드가 완벽한 하루를 만들어 드립니다.
                </article>
            </div> -->
            <aside>
                <div class="strawberry">
                    <a href="https://www.starbucks.co.kr/whats_new/newsView.do?seq=4012" title="자세히 보기">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2021/strawberrymd_pc_210112.png" alt="" class="pc-badge">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2021/strawberrymd_mo_210112.png" alt="" class="mobile-badge">
                    </a>
                </div>
                <div class="plcc">      
                    <a href="/plcc/promotionView.do?eventCode=STH02" title="hyundai card + starbucks">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2020/plcc_badge_pc.png" alt="" class="pc-badge">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2020/plcc_badge_mobile.png" alt="" class="mobile-badge">
                    </a>
                </div>
            </aside>
        </section>
        <footer>
            사업자등록번호 : 201-81-21515 (주)스타벅스커피 코리아 대표이사 : 송 데이비드 호섭 TEL : 1522-3232 개인정보 책임자 : 장석현
ⓒ 2021 Starbucks Coffee Company. All Rights Reserved.
        </footer>        
    </body>
</html>
```

해당 페이지를 요청했을 때 상품 목록과 같은 효과를 내고 싶다. 자료를 찾아갈 때 화면의 클릭 횟수를 8번 이상이 되면 사용자가 힘들어 한다. 사용자들이 많이 쓰게 하려면 안 피곤하게 해야 한다.  커피를 파는 곳의 목적은 상품의 목적을 보여줘야 커피를 많이 사갈 거다. 1.myback프로젝트를 요청하자 마자 상품 목록 메뉴가 무조건 보여지게 하자 2.상품 목록을 클릭해도 상품 목록이 보이게 하자

트리거라는 개념이 한 테이블에 행이 추가 될 때 다른 테이블에 행이 업데이트가 된다 거나 다른 테이블 행에 추가 되는 것을 말한다. 트리거 메서드를 쓰면 자동으로 이벤트를 강제 발생 시킬 수 있다.

`.trigger( event [, extraParameters ] )` → 이벤트 종류로 첫 번째 적어주고 배열이나 또는 객체 형태로 만들어라는 뜻

![3](https://user-images.githubusercontent.com/63957819/108043793-e088fc80-7084-11eb-8432-2a2ad48bd4c6.png)

length=1은 찾아온 객체가 1개이다라는 뜻

trigger메서드 쓸 때 주의 한 페이지에서 다른 페이지 결과 값을 가져와서 seticon의 article에서 보여주고 있다.  semanticcssjq.html 에서도 jquery라이브러리 사용되어있고 가입 메뉴가 클릭 되었을 경우 signup.html section의 article 포함되는 jquery라이브러리가 있다. 그러므로 트리거 효과를 낼 수 없다. 효과를 내려면 jquery라이브러리 사용 스크립트는 한번만 기술되어 있어야 한다. wellcompage의 section의 article에 들어가야 하기 때문에 윗부분에 들어가야 하고 seciton의 aritcle포함되어있는 페이지들에서는 jquery사용하는 스크립트가 포함되면 안된다. 코멘트 처리해서 통합해야 한다.

![4](https://user-images.githubusercontent.com/63957819/108043796-e088fc80-7084-11eb-9356-e2e7e5eee1b8.png)

signup.html, login.html, productlistresult.jsp, searchpost.html 모두 jquery사용 스크립트 주석 처리하기

![5](https://user-images.githubusercontent.com/63957819/108043797-e1219300-7084-11eb-8450-a04a1542361f.png)

상품 별로 장바구니가 각각 있는 게 아니고 장바구니 하나에 여러 상품이 들어가 있는 거다.

장바구니 넣기 작업을 하면 여러 상품들 사이에서 공유되도록 해줘야 한다. 장바구니라는 객체가 필요한데 이 객체가 상품 하나 또 다른 상품 화면에서도 객체에 저장이 될 수 있도록 구성해야 한다. 

장바구니 객체를 어디에 저장 해야 할지 고민 해야 한다. 

![6](https://user-images.githubusercontent.com/63957819/108043801-e1219300-7084-11eb-952d-b8de4bb73063.png)

페이지가 요청이 될 때마다 서버사이드에서는 HttpServerletRequest, HttpServletResponse타입의 전용 객체가 자동 생성되고 응답하면 소멸되는 객체이다. 장바구니 객체를 요청 시마다 만들어지는 HttpServletRequest용 객체에 다가 장바구니를 저장 시켜 놓으면 attr로 request객체는 응답하면 자동 없어지는 객체이므로 장바구니도 없어진다. 부적합하다. 그래서 장바구니는 좀 더 라이프스콥이 좀 더 오래 동안 살 수 있도록 저장을 하고 싶은 거다.  

---

![7](https://user-images.githubusercontent.com/63957819/108043803-e1ba2980-7084-11eb-92ed-99fc68d84860.png)

WEB의 표준 언어는 HTML, 표준 프로토콜은 HTTP프로토콜이다. HTTP는 요청과 응답이 이루어진 후에는 연결이 끊어지는 프로토콜이다. 이런 프로토콜은 stateless프로토콜이라 부른다.  웹 브라우저에서 웹 서버 쪽으로 클라이언트들이 요청을 하는데 요청 후 응답 후 연결이 끊긴다. 같은 웹 브라우저에서 또 요청을 해도 서버 입장에서 요청을 한번 했던 브라우저에서 다시 요청이 들어온 것인지 새로운 브라우저에서 요청이 들어온 것인지 알 수가 없다. 연결이 유지되지 않는 프로토콜이라 한다.

그래서 Http프로토콜이 장점이 될 수 있고 단점이 될 수 있다. 장점으로는 요청과 응답 후에 연결이 끊어지기 때문에 다른 클라이언트들이 접속 기회가 많아진다. 단점은 연결이 유지되지 못하는 거다.

요청 시에 서버사이드에서 어떠한 정보를 클라이언트하고 계속 유지하고 싶다. 예를 들어 a 브라우저가 로그인 요청하고 로그인이 성공 된 경우 로그인 성공을 브라우저에서 보고 연결이 끊기고 다시 결제하기를 요청을 한다 하면 로그인 된 고객만 결제하고 로그인 안된 고객은 로그인 하세요라는 결과를 응답하고 싶다. 서버 입장에서는 요청과 응답이 끊긴 상태에서 다시 결제하기 페이지를 요청하러 왔기 때문에 방금 전 로그인이 성공 된 상태인지 로그인이 실패 된 상태인지 아니면 로그인 조차 시도했는지 알 길이 없는 거다.  

b 브라우저에서는 결제하기를 요청을 하고 a와 다르게 로그인도 안하고 결제해도 서버 입장에서 알 수 가 없다.

http프로토콜이 stateless프로토콜이기 때문이다. 마치 연결이 되는 것처럼 바꿔야 하는데 프로토콜을 바꿀 수는 없고 상태 정보 값이 유지 되어야 한다. 상태 정보 값이란 예를 들어  로그인 성공이라는 정보 값이 계속 유지가 되는 의미이다. 

상태 정보 값을 유지 시키는 기술을 Session Tracking이라 부른다. 기술 방법으로 쿠키라는 정보유지기술방법과 세션이라는 정보 유지 기술 방법이 있다. 

쿠키는 상태 정보 값을 Client에 저장, 세션은 상태 정보 값을 Server에 저장한다.

![8](https://user-images.githubusercontent.com/63957819/108043804-e1ba2980-7084-11eb-9c57-ec87676c1a07.png)

a클라이언트 입장에서 서버 쪽에서 일단 로그인이 성공 된 경우에 응답할 내용을 만들기만 하지 말고 조건에 만족 했을 때 **1.쿠키의 이름과 값을 문자열 형태로 쿠키를 생성**하고 **2.응답 헤더에 쿠키를 추가** 한다. **3.응답** 시에 헤더 정보가 따라갈 거다. 결국 서버에서 만들어낸 쿠키 정보가 클라이언트 쪽에 오는 거다.

이 쿠키는 같은 브라우저에서 요청이 이루어지면 요청 시에 자동 쿠키가 서버로 전송이 된다. 특이하게 클라이언트 쿠키는 요청 시 서버로 자동 전송이 된다. 로그인 된 고객인지 아닌지 충분히 확인 할 수 있다. 

결제 페이지에서는 **1.요청 헤더의 쿠키를 조회**하고 **2. 그 중에  로그인 성공 관련 쿠키인가 확인**을 한다. 쿠키 배열 타입으로 확인을 하고 null이 아니라면 if 구문으로 향상된 for문을 이용해서 반복 수행을 하고 반복문 for문으로 쿠키의 이름을 확인해 보는 거다. 안쪽 if문으로 로그인 정보를 비교하고 안쪽 if 조건에 만족하지 않았다면 로그인이 안된 경우로 판단하면 된다. 이때에는 로그인하세요라고 응답을 한다 거나 또는 status를 -1 형식으로 코드를 서블릿 단에서 처리를 한다. 

b클라이언트 입장에서 요청이 이루어졌는데 b 브라우저에는 쿠키가 하나도 없고 이 결제하기 페이지에 왔을 때 쿠키를 조회해보고 로그인 성공 관련 쿠키를 확인해본다. 근데 로그인 관련 쿠키를 확인 할 수 없다. 이때에는 로그인하세요라고 응답한다.

쿠키가 자바스크립트, 자바 쪽에서 생성할 수 있는데 유효 기간이 설정이 안되어 있으면 무조건 브라우저가 닫힐 때 소멸이 된다. 유효 기간을 설정은 쿠키 생성한 다음에 하면 된다.

쿠키는 클라이언트 쪽에 저장되는 정보이기 때문에 보안 상 위험하다. 외부에 노출되면 안되는 정보들은 쿠키로 저장하면 안된다. 

![9](https://user-images.githubusercontent.com/63957819/108043805-e252c000-7084-11eb-968e-cda0dd134a83.png)

세션 객체를 얻어 올 건데 로그인 요청해서 성공 될 경우 **1.HttpSession객체 얻고** **2.Httpsession 객체의 속성으로 상태 정보 값 추가**한다.  톰캣 엔진이 만들어주는 객체인데 그 객체는 바로 HttpSession객체이다. 인스턴스 변수가 session id 정보 값, 사용 시간, attr 를 가진다. HttpSession객체는 클라이언트 별로 만들어진다. 

같은 웹 브라우저에서 결제하기 페이지가 요청이 되면 **1.HttpSession객체를 얻어내고 2. 1번 객체의 속성 찾기**를 한다.

attribute가 저장이 될 때에는 자바 최상위 타입인 object타입으로 저장이되고 String 타입으로 캐스팅 한 후 변수에 담아주면 된다. 찾아온 아이디 값을 가지고 아이디 값에 해당하는 정보가 있는지 확인해 보면 된다. **3. 속성 중 로그인 관련 속성 확인**을 한다. if 조건으로 id가 null인 경우 out.print로 "로그인 하세요"라고 응답하고 그렇지 않은 경우에는 아이디가 있는 경우이므로 "결제 처리"로 응답 해주면 된다.

**Awb 브라우저 전용 세션 객체는 로그인 페이지에서 사용한 session객체와 결제 페이지에서 사용한 session객체는 같은 객체를 참조한다.**

Bwb 에서 요청 시 쿠키가 없으므로 결제 페이지에서  b웹 브라우저 전용 세션 객체는 id값이 null이다. out.print로 "로그인 하세요"라고 응답한다. **Bwb에서는 결제만 하고 a에서 사용한 session과 b에서 사용한 session이 다른 객체를 참조하는 거다.**

request.getSession 메서드만 호출하면 알아서 생성이 된다. 서버가 절대 클라이언트를 감시하면 안된다. 이것은 해킹이다.. 따라서 Httpsession객체는 웹 브라우저가 닫히건 새로 열리건 관계없이 최종 사용 시간 이후 삼십 분 동안 사용하지 않게 되면 알아서 session객체는 자동 소멸이 된다. 이것은 톰켓 엔진이 해준다.

 

![10](https://user-images.githubusercontent.com/63957819/108043807-e2eb5680-7084-11eb-8383-ff5401b4eed3.png)

c라는 클라이언트가 요청을 했다고 가정 해보자! 서블릿에서 request.getSession 메서드에서 하는 일은 먼저 **1)요청 헤더의 쿠키 중 JSESSIONID쿠키 얻는다.** **2) 1)쿠키 값과 같은 id를 갖는 wb전용 HttpSession객체 존재 여부 확인** 해 본다. 있으면 HttpSession객체를 반환해 낸다. **1)HttpSession객체가 없는 경우 생성**한 후 **2)중복되지 않는 id값을 설정**을 한다. 알아서 자동 톰켓 엔진이 아이디 값을 자동 발급을 하는 거다. 그 다음 **3)id값을 쿠키로 생성을 하고 응답 헤더에 추가**를 한다. 쿠키 명은 JSESSIONID가 되고 값은 ccc 가 클라이언트 웹 브라우저에게 응답이 된다.

![11](https://user-images.githubusercontent.com/63957819/108043809-e2eb5680-7084-11eb-8a38-7f685d3804ae.png)

![12](https://user-images.githubusercontent.com/63957819/108043811-e383ed00-7084-11eb-90f1-dc9434836a38.png)

 attr 갖고 있는 api들은HttpServletRequest, HttpSession, ServletContext 가 있다. 

이들 중에서 제일 빨리 죽는 객체는 HttpServletRequest객체이다. 요청 시마다 만들어지고 응답하면 없어지는 객체이다. HttpServelet객체는 서블릿 클래스 변경 시 객체가 소멸이 된다.  그거보다 좀 더 오래 사는 얘가 HttpSession이다.  요청 시 소스 코드로 requestGetSession 호출하고 없으면 객체 생성하고 30분동안 사용하지 않으면 자동 소멸이 된다. ServletContext 객체는 톰캣 구동 될 때 자동 생성이 되고 톰캣이 중지되지 않는 한 계속 남아있는 객체이고 톰캣 중지 시 메모리가 소멸이 된다.

**쿠키와 세션 설명!! http프로토콜 속성을 설명하고 상태 정보 유지 기술을 설명하면 된다.**

---

- semanticcssjq.jsp

```java
<%@page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>시멘틱태그-CSS-jQuery[JSP]</title>
        <style>
            * { 
                box-sizing: border-box;

                color : #000000; 
                /* font-size: 1.25em; */
            }
            header { background: #f6f5ef; 
                     margin: 5px auto;
                     position: relative;
            } 
            /*section{background-color:#eef2f3; height:500px; margin-bottom: 5px;}
            section>article.one{background-color: #b3ffd9}
            section>article.two{background-color: #1e3932;color:white;}*/
            
            footer{ background-color: #2C2A29;  color:#fff;  }
                    header, footer { height:100px; width: 1100px;  }
                    header>nav>ul{ list-style-type: none; padding: 0px;}
                    header>nav>ul>li{ 
                    width:100px; 
                    display: inline-block; 
                    margin: 0px 10px; 
                    text-align: center;
                    }

            header>nav>ul>li>a{
                text-decoration: none;
            }
            header>nav>ul>li>a:hover{
                background-color: black;
                color: white;
                font-weight: bold;
            }
            
            header>h1{
                width: 30%;    
                margin: 0 auto;
                height: 100%;
                display: inline-block;
                position: relative;
                border: 1px solid;
            }
            /* 이미지 로고 */
            header>h1>a{
                display: block;
                width : 100%;
                height:100%;
                margin: 0;
                padding: 0;
                
                position: absolute;
                top: 10px;
                left: 0px;
                
                background-image: url('./images/logo.png');
                background-repeat: no-repeat;
            }
            header>nav{
                display: inline-block;
                width : 60%;
                height: 100%;
                border: 1px solid;
                position: absolute;
                top: 0px;
                right: 0px;
            }
            section{background-color:#eef2f3; 
                width: 1100px;
                height:500px; 
                margin-bottom : 5px;
            }
            section>article{
                width: 60%;
                height: 100%;
                float: left;
            }
            /* section>div{
                width: 60%;
                height: 100%;
                float: left;
            } */
            /* section>div>article.one{background-color: #b3ffd9;
                width: 100%;
                height:40%;
            } */
            /* section>div>article.two{background-color: #1e3932;color:white;
                width: 100%;
                height:40%;
            } */
            section>aside{
                width: 30%;
                height:100%;
                float: right;               
            }
            
            /* 광고이미지 반응*/
            section>aside .pc-badge{
                display: block;
            }
            section>aside .mobile-badge {
                display: none;
            }
            
            @media screen and (max-width: 960px){
                section>aside .pc-badge {
                    display: none;
                }
               section>aside .mobile-badge {
                   display: block;
                }
            }

            /*메뉴 반응*/
            header>nav.small{
                display:none;
            }
            @media screen and (min-width: 641px) and (max-width: 960px){
                header>nav.small{
                    display:inline-block;
                }
                header>nav.large{
                    display:none;
                }
            }
        </style>
        <!--jquery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(function(){
                //DOM트리에서 메뉴객체들 모두찾기
                var $menuObj = $("header>nav>ul>li>a");
                //메뉴가 클릭되면 
                $menuObj.click(function(event){
                    $("section>article").empty(); //article영역 지우기
                    //메뉴객체의 href속성값 얻기
                    //var hrefValue = $menuObj.attr("href"); // (X) 배열의 무조건 0번index객체
                    //var hrefValue = $(this).attr("href");
                    var hrefValue = $(event.target).attr("href");//ex)login.html, signup.html
                    console.log(hrefValue);

                    //AJAX
                    $.ajax({
                        url: hrefValue, //요청URL
                        method: "get", //요청방식.   method속성은 생략가능하다 그러면 무조건 get방식이 되는거다. post방식으로 요청하려면 method속성값을 post라 쓰면 된다.
                        success: function(data){ //성공응답 data는 응답내용
                           //alert("AJAX요청응답 성공");
                           //console.log(data);
                           //article의 영역의 innterHTML로 설정
                           $("section>article").html(data);
                        },
                        error: function(jqXHR){ //실패응답 jqXHR는 jquery xmlRequest객체
                            alert("AJAX요청응답 실패 : 에러코드=" + jqXHR.status);
                        } //실패응답
                    });
                    return false; //event.preventDefault();와 event.stopPropagation(); 실행한것과 같음
                });
            
              	//상품목록 메뉴클릭 이벤트를 강제 발생
            	//DOM트리에서 상품목록 메뉴 객체 찾기
            	//console.log($("header>nav>ul>li>a[href=productlist]"));
                $("header>nav>ul>li>a[href=productlist]").trigger("click");
            });
        </script>
    </head>
    <body>
        <header>
            <!--<h1>스타벅스</h1>-->
            <h1><a href="#"></a></h1>
            <nav class="large">
                <ul>
**<%
if(session.getAttribute("loginInfo") == null){
%>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>**
                    <li><a href="productlist">상품목록</a></li>
                    <li><a href="viewcart.html">장바구니</a></li>
                </ul>
            </nav>
            <nav class="small">
               <ul>
**<%
if(session.getAttribute("loginInfo") == null){
%>
                    <li><a href="login.html">로그인</a></li>
                    <li><a href="signup.html">가입</a></li>
<%
}else{
%>					<li><a href="logout">로그아웃</a></li>
<%
}
%>**
                </ul>
            </nav>
			
        </header>
        <section>
            <article>article...</article>
            <!-- <div>
                <article class="one">
                스타벅스만의 특별한 혜택, 스타벅스 <mark>리워드</mark>
    스타벅스 회원이세요? 로그인을 통해 나만의 리워드를 확인해보세요.
    스타벅스 회원이 아니세요? 가입을 통해 리워드 혜택을 즐기세요.
                </article>
                <article class="two">
                    푸드 간편하지만 든든하게 채워지는 만족감을 느껴보세요. 신선함과 영양이 가득한 스타벅스 푸드가 완벽한 하루를 만들어 드립니다.
                </article>
            </div> -->
            <aside>
                <div class="strawberry">
                    <a href="https://www.starbucks.co.kr/whats_new/newsView.do?seq=4012" title="자세히 보기">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2021/strawberrymd_pc_210112.png" alt="" class="pc-badge">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2021/strawberrymd_mo_210112.png" alt="" class="mobile-badge">
                    </a>
                </div>
                <div class="plcc">      
                    <a href="/plcc/promotionView.do?eventCode=STH02" title="hyundai card + starbucks">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2020/plcc_badge_pc.png" alt="" class="pc-badge">
                        <img src="https://image.istarbucks.co.kr/upload/common/img/main/2020/plcc_badge_mobile.png" alt="" class="mobile-badge">
                    </a>
                </div>
            </aside>
        </section>
        <footer>
            사업자등록번호 : 201-81-21515 (주)스타벅스커피 코리아 대표이사 : 송 데이비드 호섭 TEL : 1522-3232 개인정보 책임자 : 장석현
ⓒ 2021 Starbucks Coffee Company. All Rights Reserved.
        </footer>        
    </body>
</html>
```

- web.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
  <display-name>myback</display-name>
  <welcome-file-list>
    **<welcome-file>semanticcssjq.jsp</welcome-file>**
    <welcome-file>semanticcssjq.html</welcome-file>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  <servlet>
    <description></description>
    <display-name>FirstController</display-name>
    <servlet-name>FirstController</servlet-name>
    <servlet-class>com.my.control.FirstController</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>FirstController</servlet-name>
    <url-pattern>/first</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>RequestTest</display-name>
    <servlet-name>RequestTest</servlet-name>
    <servlet-class>com.my.control.RequestTest</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>RequestTest</servlet-name>
    <url-pattern>/requesttest</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>ResponseTest</display-name>
    <servlet-name>ResponseTest</servlet-name>
    <servlet-class>com.my.control.ResponseTest</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>ResponseTest</servlet-name>
    <url-pattern>/responsetest</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>LoginServlet</display-name>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.my.control.LoginServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>IdDupChkServlet</display-name>
    <servlet-name>IdDupChkServlet</servlet-name>
    <servlet-class>com.my.control.IdDupChkServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>IdDupChkServlet</servlet-name>
    <url-pattern>/iddupchk</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>SignupServlet</display-name>
    <servlet-name>SignupServlet</servlet-name>
    <servlet-class>com.my.control.SignupServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>SignupServlet</servlet-name>
    <url-pattern>/signup</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>ProductListServlet</display-name>
    <servlet-name>ProductListServlet</servlet-name>
    <servlet-class>com.my.control.ProductListServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>ProductListServlet</servlet-name>
    <url-pattern>/productlist</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>MoveServlet</display-name>
    <servlet-name>MoveServlet</servlet-name>
    <servlet-class>com.my.control.MoveServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>MoveServlet</servlet-name>
    <url-pattern>/move</url-pattern>
  </servlet-mapping>
  <servlet>
    <description></description>
    <display-name>ProductDetailServlet</display-name>
    <servlet-name>ProductDetailServlet</servlet-name>
    <servlet-class>com.my.control.ProductDetailServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>ProductDetailServlet</servlet-name>
    <url-pattern>/productdetail</url-pattern>
  </servlet-mapping>

</web-app>
```

- login.html

```java
<!DOCTYPE html>
<html>
    <head>
        <!-- <title>jQuery로 작성한 로그인</title> -->
        <!--jQuery사용-->
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>-->

        <script>
            $(function(){ //jQuery(document).ready(function(){})
                //localStorage에 id라는 이름의 아이템이 있으면 그 값을 아이디 입력란에 보여준다.
                let idValue = localStorage.getItem("id");
                if(idValue != null || idValue != '') {
                    //document.querySelector("input[name=id]").value = idValue;
                    //$document.querySelector("input[name=id]").value(idValue);
                    $("input[name=id]").val(idValue);
                 }
                 //DOM트리에서 type속성값이 checkbox인 input객체 찾기
                 let $chkObj = $("input[type=checkbox]");

                 //로그인버튼에서 클릭이벤트 발생하면 폼에서 서브밋 이벤트가 자동 발생한다.
                 //DOM트리에서 form객체 찾기
                 let $formObj = $("form");
                 $formObj.submit(function(event) { //event기술한 목적은 기본 이벤트 처리기를 막기위해서이다.
                     //checkbox가 선택되었는가 localstorage에 아이디값을 저장한다.
                     //if($chkObj.checked){
                     if($chkObj.prop("checked")) { //!주의. 체크박스가 선택되었는지 안되었는지 구분하려면 .prop메서드의 인자로 checked를 써야한다 그래야 선택여부를 확인 할 수있다
                        let $idObj = $("input[name=id]");
                        localStorage.setItem("id", $idObj.val());
                    } else {
                        //checkbox가 선택안되면 localstorage의 아이디값을 제거한다.
                        localStorage.removeItem("id");
                    }

                    //입력된 아이디값
                    var idValue = $("input[name=id]").val(); //서버로 전송될 id값

                    //입력된 비밀번호값
                    var pwdValue = $("input[name=pwd]").val(); //서버로 전송될 pwd값

                    $.ajax({
                       url: "./login", //'http://localhost:8888/myfront/login',  //요청URL
                       method:"post", //요청방식
                       //data : "id=" + idValue + "&pwd=" + pwdValue, // data : 서버로 전송할 데이터를 의미 //요청시 전달 데이터. 쿼리 스트링 형태로 전달
                       data : {"id": idValue, "pwd": pwdValue}, // 문자열이 아니라 객체형태로 만들어서 전달
                      success: function(responseObj) { //json객체 형태로 응답
                          if(responseObj.status == 1) {
                              alert("로그인 성공");
                              **location.href = "http://localhost:8888/myback"; //웰컴페이지를 요청**
                          } else {
                              alert("로그인 실패");
                          }
                       },
                       error:function(jqXHR){
                          alert(jqXHR.status);
                       }
                    });
                    //event.preventDefault(); //기본이벤트 처리 막자 
                    return false; //!주의. event.preventDefault(); event.stopPropagation(); 두개의 효과를 다 포함하는 것이 return false이다.
                 });
            });
        //     window.addEventListener("load", function() { //load이벤트가 발생 했다는 것은 dom트리가 완성됐다는 뜻
        //         //localStorage에 id라는 이름의 아이템이 있으면 그 값을 아이디 입력란에 보여준다.
        //         let idValue = localStorage.getItem("id");
        //         if(idValue != null || idValue != '') {
        //             document.querySelector("input[name=id]").value = idValue;
        //         }
        
        //         //DOM트리에서 type속성값이 checkbox인 input객체 찾기
        //         let chkObj = document.querySelector("input[type=checkbox]");
        
        //         //로그인버튼에서 클릭이벤트 발생하면 폼에서 서브밋 이벤트가 자동 발생한다. 
        //         //DOM트리에서 form객체 찾기
        //         let formObj = document.querySelector("form");
        
        //         formObj.addEventListener("submit", function(event) {
        //             //checkbox가 선택되었는가 localstorage에 아이디값을 저장한다.
        //             if(chkObj.checked) { //if(chkObj.checked == true){
        //                 let idObj = document.querySelector("input[name=id]");
        //                 localStorage.setItem("id", idObj.value);
        //             } else {
        //                 //checkbox가 선택안되면 localstorage의 아이디값을 제거한다.
        //                 localStorage.removeItem("id");
        //             }
        //             event.preventDefault(); //기본이벤트 처리 막자  
        //         });
        //     });
        // </script>

        <style>
             input[name="id"]{ background-color: yellow; }
             input:not([type ="text"]) { background-color: green; color: white; }
             /* form span.saveid{ color: blue; font-weight: bold;} */
             /* form>label>input[type=checkbox]+span.saveid { color: blue; font-weight: bold; } */
             form>label>input[type=checkbox]~span.saveid{ color: blue; font-weight: bold; }
        </style>
    </head>
    <body>        
    <div class="login">
        <form>
            <label>아이디<input type="text" name="id"></label><br>
            <label>비밀번호<input type="password" name="pwd" maxlength="5"></label><br>
            <label><input type="checkbox" checked>아이디 저장</label>
            <input type="submit" value="로그인">
        </form>
    </div>
    </body>
</html>
```

- LoginServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.my.exception.FindException;
import com.my.service.CustomerService;
import com.my.vo.Customer;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//응답형식(MIME) 지정하기
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("application/json;charset=UTF-8"); //응답형식자체가 json형태
		//응답출력 스트림 얻기
		PrintWriter out = response.getWriter();
		
		//요청전달 데이터 얻기
		String id = request.getParameter("id"); //id 요청전달 데이터
		String pwd = request.getParameter("pwd"); //pwd 요청전달 데이터
		/*if(id.equals(pwd)) {
			out.print("{\"status\":1}"); //응답 출력하기
		} else {
			out.print("{\"status\":-1}");
		}*/
		CustomerService service = new CustomerService();
		try {
			Customer c = service.login(id, pwd);
			**//세션객체(WB별 객체)얻기
			HttpSession session = request.getSession();
			
			//속성추가("속성명: loginInfo, 값: id값");
			session.setAttribute("loginInfo", id);**
			
			out.print("{\"status\":1}"); //응답 출력하기
		} catch (FindException e) {
			e.printStackTrace();
			out.print("{\"status\":-1}");
		}
	}

}
```

로그인이 성공 되면 메뉴가 로그인이 아니라 로그아웃으로 바뀌고 싶다. 하나는 자바스크립트 if~else로 아니면 이 페이지 자체를 서버 쪽에서 실행될 수 있는 servlet이나 jsp로 처리가 있다. 로그인 html 페이지에서 요청을 하고 응답을 받을 거다.

![13](https://user-images.githubusercontent.com/63957819/108043814-e383ed00-7084-11eb-8538-bc9246413e41.png)

semanticcssjq.html파일 복사해서 그대로 붙여 넣기 이름을 semanticcssjq.jsp로 바꾸자

![14](https://user-images.githubusercontent.com/63957819/108043815-e41c8380-7084-11eb-86b2-61dceeb99cb3.png)

한글이 깨지므로 닫은 후 properties로 들어가 UTF-8로 설정 해주기

![15](https://user-images.githubusercontent.com/63957819/108043818-e4b51a00-7084-11eb-9514-fc6ad72a2c81.png)

web.xml의 welcome-file 맨 위에 semanticcssjq.jsp 추가 해주기

실행결과>

![16](https://user-images.githubusercontent.com/63957819/108043820-e4b51a00-7084-11eb-9031-9355cdac73fb.png)

로그인 성공 시 메인 페이지로 돌아가고 로그인에서 로그아웃으로 바뀐 것을 볼 수 있다. 

설정에 들어가서 캐쉬 지우고 새로고침 하면 로그아웃에서 로그인으로 바뀐다.
