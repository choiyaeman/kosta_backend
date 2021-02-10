# day04

자료가 너무 많을 경우 serialize 메서드를 이용

응답할 내용을 클라이언트 프로퍼티를 알고 있어야 된다. 

class속성은 선택자를 이용해서 스타일을 입힌다 거나 자바스크립트 도움을 이용해서 객체 찾는데 사용한다.

- signup.html

```java
<!DOCTYPE html>
<html>
    <head>
        <title>고객 가입</title>
        <!--jquery사용-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
        <script>
        $(function(){
        	
        	//DOM트리에서 class속성값이 btIdDupChk인 객체찾기
        	var $btIdDupChk = $("input[class=btIdDupChk]");
        	
        	//중복확인버튼객체에서 클릭이벤트가 발생하면
        	$btIdDupChk.click(function(){
        		//DOM트리에서 name속성값이 id인 객체찾기
        		var $idObj = $("input[name=id]");
        		
        		//아이디 입력값
        		var idValue = $idObj.val();
        		
        		//아이디 입력 유효성 검사
        		if(idValue.trim() ==''){
        			alert("아이디를 입력하세요");
        			$idObj.focus();
        			return false;
        		}
        		$.ajax({
        			url : "./iddupchk",
        			method : "post",
        			data : {id: idValue},
        			success:function(responseObj){
        				if(responseObj.status == 1){
        					alert("이미 존재하는 아이디입니다");
        				}else{
        					alert("사용가능한 아이디입니다");
        					
        					//DOM트리에서 type속성값이 submit인 input객체찾기
        					var $btSignup = $("input[type=submit]");
        					//input객체를 화면에 보여준다
        					$btSignup.css("visibility", "visible");
        				}
        			},
        			error: function(jqXHR){
        				alert(jqXHR.status);
        			}
        		});
        	});
        	
        	//아이디입력란에 포커스를 받으면 가입버튼 화면에서 사라진다
        	var $idObj = $("input[name=id]");
        	$idObj.focus(function(){
        		//DOM트리에서 type속성값이 submit인 input객체찾기
				var $btSignup = $("input[type=submit]");
				//input객체를 화면에서 사라진다
				$btSignup.css("visibility", "hidden");
        	});
        	
        	//우편번호검색 버튼이 클릭되면 새창에 searchpost.html를 보여준다
        	$btSearchDoro = $("input[class=btSearchDoro]");
        	$btSearchDoro.click(function(){
        		var url = "./searchpost.html";
        		var target = "first";
                var feature = "width=500 height=300";
                w1 = window.open(url, target, feature);
                    
        	});
        });
        
        //가입버튼이 클릭되면 서버로 데이터 전송
        $("form").submit(function(){
        	
        	**//비밀번호값이 같은가 유효성검사
        	var pwdValue = $("input.pwd").val();
        	var pwd1Value = $("input.pwd1").val();
        	if(pwdValue != pwd1Value) {
        		alert("비밀번호가 일치하지 않습니다");
        		$("input.pwd").focus();
        		return false;//아래작업 진행안하고 튕겨나가게
        	}**
        	
        	var url = './signup';
        	var method = 'post';
        	var data = $('form').serialize();
        	alert(data); //ex).id=a&pwd=a&name=c&buildingno=4&addr1=d
        	$.ajax({
        		url: url, //:앞에 있는 것이 프로퍼티 이름이 되는 것이다.
        		method: method,
        		data: data,
        		success: function(responseObj){
        			if(responseObj.status == 1) {
        				alert("가입성공");
        			} else {
        				alert("가입실패");
        			}
        		},
        		error: function(jqXHR){
        			alert(jqXHR.status); //404, 403, 500(서버) error
        		}
        	});
        	return false; //event.stopPropagation(); event.preventDefault();
        });
        </script>
        <style>
        	input[type="submit"]{
        		/*display: none;*/
        		visibility: hidden;
        	}
        	
        </style>
    </head>
    <body>
    	<form>    	
	        <label>아이디:
	        <input type="text" name="id" placeholder="아이디를 입력하세요" required></label>
	        <input type="button" value="중복확인"  class="btIdDupChk"><br>
	
	        <label>비밀번호:<input type="password" **calss="pwd"** name="pwd" placeholder="비밀번호를 입력하세요" ></label><br>
	        <label>비밀번호확인:<input type="password" **class="pwd1"** placeholder="비밀번호를 입력하세요" ></label><br>
	
	        <label>이름:<input type="text" name="name" placeholder="이름을 입력하세요" ></label><br>
	
	        <label>우편번호:<input type="text" readonly size="5" class="code"></label>
	        
	        <input type="button" value="우편번호검색" class="btSearchDoro"><br>
	        <input type="hidden" name="buildingno"  value="123456789012345678901234">
	        <input type="text" readonly value="서울시 중구 광화문로 세종대로"  class="baseAddr"><br>
	        
	        <input type="text" name="addr1" placeholder="상세주소를 입력하세요"><br>
	
	        <input type="submit" value="가입">
	        <input type="reset" value="CLEAR">
        </form>
    </body>
</html>
```

![1](https://user-images.githubusercontent.com/63957819/107487385-775b4200-6bc9-11eb-91df-64525bd8aac9.png)

---

![2](https://user-images.githubusercontent.com/63957819/107487386-775b4200-6bc9-11eb-914d-68aea6b2e3e1.png)

productlist.html파일 복사해서 myback에 WebContent에 붙이자

![3](https://user-images.githubusercontent.com/63957819/107487388-77f3d880-6bc9-11eb-959c-19babf24180c.png)

상품 메뉴를 클릭했을 때 요청할 url로 ./productlist, 요청 방식은 get방식으로, 전달 데이터는 없다.  이렇게 요청이 되면 서버사이드 쪽에는 com.my.control.ProductListServlet이라 하자 상품을 처리해 줄 service하고 dao를 만들어줘야 한다. 

ajax형태로 요청 할 거고 응답 결과를 받아와서 화면에서의 article영역에서 보여줄 거다. 응답되는 내용이 html에게 응답을 해줄 때 응답 형식을 html로 아님 json으로 할 것인지 두 개의 경우 수로 나뉜다. 첫 번째 경우 " text/html" 한다면 응답 내용도 html태그 형태로 응답 해주면 된다. 두 번째 경우 "application/json" 이다 하면 자바스크립트 객체 형태로 응답 해주면 되는데 배열 형태로 응답 해주면 된다. 

두 번째 같이 응답해주면 자바스크립트 객체 형태를 띄면서 자바스크립트 배열 형태로 응답 해야 한다. 두 번째 절차처럼 해야 한다. 왜냐하면 첫 번째처럼 하면 응답 형식이 html응답이 되는 거니깐 백엔드가 프론트엔드 쪽에 보여줄 것을 결정을 해버린다. 첫 번째 절차는 분리하기 힘들다. 

![4](https://user-images.githubusercontent.com/63957819/107487395-77f3d880-6bc9-11eb-8fad-15f417c6b333.png)

product조회 시 no rows selected →  데이터가 없다.

![5](https://user-images.githubusercontent.com/63957819/107487397-788c6f00-6bc9-11eb-8b32-d167118d7656.png)

![6](https://user-images.githubusercontent.com/63957819/107487399-788c6f00-6bc9-11eb-8c56-208437cddf26.png)

먼저 백엔드 쪽에는 db에 테이블 만드는 작업을 해야 한다. 화면을 보면 상품 번호, 상품 명, 가격 정보가 있다 이것을 db쪽에 저장되도록 해보자 그리고 셈플 데이터를 넣자 sqldeveloper 실행~

![7](https://user-images.githubusercontent.com/63957819/107487400-79250580-6bc9-11eb-9f77-56de56cfb4b8.png)

상품목록보기, 상세보기에 관련된 메서드를 만들어두자. 메소드의 반환 값을 결정해야 하는데 상품을 검색한다면 상품 번호로 검색 할 거고 상품 명, 상품 가격 정보가 모두 들어 있는 vo 클래스 타입이 되어야 한다. vo가 필요하다. 

product를 has a관계로 표시할 수 있지만 주문을 기준 해서 상품을 보는 거지 상품을 기준으로 주문을 보는 게 아니다. 관리자 입장에서 상품 별 주문 현황이 필요하다면 List 이런 관계가 필요한 거다.

그 다음 dao를 만들자 

- Product.java

```java
package com.my.vo;

public class Product {
	/*
	 PROD_NO                                   NOT NULL VARCHAR2(5)
	 PROD_NAME                                 NOT NULL VARCHAR2(50)
	 PROD_PRICE                                         NUMBER(7)
	 */
	private String prod_no; //외부에서 접근 못하도록 private설정
	private String prod_name;
	private int prod_price;
	public Product() {
		super();
	}
	public Product(String prod_no, String prod_name, int prod_price) {
		super();
		this.prod_no = prod_no;
		this.prod_name = prod_name;
		this.prod_price = prod_price;
	}
	
	@Override
	public String toString() {
		return "Product [prod_no=" + prod_no + ", prod_name=" + prod_name + ", prod_price=" + prod_price + "]";
	}
	
	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public int getProd_price() {
		return prod_price;
	}
	public void setProd_price(int prod_price) {
		this.prod_price = prod_price;
	}

}
```

![8](https://user-images.githubusercontent.com/63957819/107487341-6f9b9d80-6bc9-11eb-9939-0b687cdd64aa.png)

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
```

![9](https://user-images.githubusercontent.com/63957819/107487345-70ccca80-6bc9-11eb-8a72-72e58cedc5c3.png)

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

}
```

- ProductListServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ProductService service = new ProductService();
		try {
			List<Product> list = service.findAll();
			out.print("<div class=\"productlist\">");
			for(Product p: list) {
			out.print("<div class=\"product\">\r\n" + 
					"                <ul>\r\n" + 
					"                    <li><img src=\"./images/"+ p.getProd_no() +".jpg\" alt=\""+ p.getProd_name() +"\"></li>\r\n" + 
					"                    <li>"+ p.getProd_name() +"</li>\r\n" + 
					"                    <li>"+ p.getProd_price() +"</li>\r\n" + 
					"                </ul>\r\n" + 
					"            </div>");
			
			}
			out.print("</div>");
			
		} catch (FindException e) {
			e.printStackTrace();
		}
		
	}

}
```

실행결과>

![10](https://user-images.githubusercontent.com/63957819/107487347-70ccca80-6bc9-11eb-8652-18f6c34a4f79.png)

"text/html" 형태로 Servlet이 직접 응답하기에는 결코 좋은 방법이 아니다.

"text/html"형식으로 응답은 하는데 Servlet이 응답하는 게 아니고 jsp페이지로 이동해서 응답하게 해야 한다. 응답하는 부분을 jsp에 맡겨야 한다. jsp는 결과를 보여줄 view 역할을 할 거다. 안 그러면 Servlet코드가 너무 지저분해 진다.

만약 응답 형식을 json형태로 응답 한다면 jsp페이지가 필요 없다.

![11](https://user-images.githubusercontent.com/63957819/107487353-71fdf780-6bc9-11eb-9fe1-25c03dc53f5e.png)

서블릿에서 직접 응답하는 구조가 아니고 jsp페이지로 이동을 할 거다. jsp페이지가 응답하는 구조로 만들거다. 일단 요청은 서블릿이 받고 응답은 jsp페이지가 한다. 

서버사이드에서 jsp페이지로 이동하는 방법을 알아야 한다. RequestDispatcher의 foward 메서드를 이용하면 다른 페이지로 이동할 수 있다.

![12](https://user-images.githubusercontent.com/63957819/107487355-71fdf780-6bc9-11eb-94ec-48c4613425db.png)

RequestDispatcher는 요청 전달자이다. foward메서드는 앞으로 전진 뒤로 돌아오지 않는다는 뜻인데 즉 해당 페이지에게 요청과 응답을 전달하고 돌아오지 않는다는 뜻이다. 같은 request, response 객체를 쓴다. 해당 path페이지로 이동한다.

리다이렉트 링크가 클릭 됐을 때 재 요청 시에 주소가 바뀐다. 클라이언트가 요청할 때 마다 요청전용객체, 응답전용객체를 자동 만들어 낸다.

sendRedirect같은 경우 페이지가 요청되고 응답 그리고 클라이언트 단에서 재 요청이 되고 응답 된다. 재요청시에 재요청 객체를 또 만들어 낸다.

foward와 redirect의 차이점은? 서버사이드의 이동, 클라이언트 단에서 재요청이라는 차이점이 있고 요청, 응답 객체 수의 차이점도 있다

![13](https://user-images.githubusercontent.com/63957819/107487357-72968e00-6bc9-11eb-811b-40e47a173248.png)

요청 시 전달된 아이디가 forward된 requesttest에서도 찾아올 수 있다. 이게 forward다. 기존 페이지에서 쓰이고 있는 request객체를 이동 된 forwad된 페이지에서도 같이 쓰게 되는거다.

요청 시 전달된 아이디가 리다이렉트를 하게 되면 기존 페이지에서 쓰이는 request객체랑 새로 리다이렉트 된 요청 객체가 서로 다르다.

서버사이드에서의 이동은 자신의 웹 컨텍스트 안에서만 이동할 수 있다. 다른 웹 컨텍스트로 이동하거나 전혀 다른 도메인으로 가는 경우는 포워드 방식이 아닌 리다이렉트 작업을 해야 한다.

forward는 path에게 전달하고 돌아오지 않지만 include는 path에게 전달 하고 돌아온다.

- MoveServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MoveServlet
 */
public class MoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<a href=\"move?opt=forward&id=hello\">포워드</a><br>"); //?는 쿼리스트링이 만들어질수있다.
		out.print("<a href=\"move?opt=include\">인클루드</a><br>");
		out.print("<a href=\"move?opt=redirect&id=hello\">리다이렉트</a><br>");
	}
	protected void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//서버단에서 페이지 이동
		String path = "/requesttest"; //"/first";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
	protected void redirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트단에서 재요청
		String location = "requesttest";//"first";
		response.sendRedirect(location);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		String opt = request.getParameter("opt");
		if(opt == null) {
			show(request, response);
		} else if(opt.equals("forward")) {
			forward(request, response);
		} else if(opt.equals("include")) {
			
		} else if(opt.equals("redirect")) {
			redirect(request, response);
		}
	}

}
```

![14](https://user-images.githubusercontent.com/63957819/107487360-732f2480-6bc9-11eb-895b-010904c5356d.png)

a태그들이 응답이 되어서 화면에 이런 결과가 나타나게 할 거다. 그럴러면 응답을 해줘야 한다

실행결과>

![15](https://user-images.githubusercontent.com/63957819/107487362-732f2480-6bc9-11eb-904e-c00be195e7a8.png)

![16](https://user-images.githubusercontent.com/63957819/107487365-73c7bb00-6bc9-11eb-9dcf-cfba7ced181b.png)

- ProductListServlet.java

```java
package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.exception.FindException;
import com.my.service.ProductService;
import com.my.vo.Product;

public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ProductService service = new ProductService();
		try {
			List<Product> list = service.findAll();
			out.print("<div class=\"productlist\">");
			for(Product p: list) {
			out.print("<div class=\"product\">\r\n" + 
					"                <ul>\r\n" + 
					"                    <li><img src=\"./images/"+ p.getProd_no() +".jpg\" alt=\""+ p.getProd_name() +"\"></li>\r\n" + 
					"                    <li>"+ p.getProd_name() +"</li>\r\n" + 
					"                    <li>"+ p.getProd_price() +"</li>\r\n" + 
					"                </ul>\r\n" + 
					"            </div>");
			
			}
			out.print("</div>");
			
		} catch (FindException e) {
			e.printStackTrace();
		}
		
	}

}
```

![17](https://user-images.githubusercontent.com/63957819/107487366-73c7bb00-6bc9-11eb-89b4-cddf9049823b.png)

semanticcssjq.html에서 productlist.html → productlist로 수정 해주기

---

jsp파일 만들어주자~

WebContent오른쪽 클릭> New > Jsp File> first.java

![18](https://user-images.githubusercontent.com/63957819/107487368-74605180-6bc9-11eb-82b8-5956c274c6a3.png)

- first.jsp

```java
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
첫번째 JSP입니다
JSP의 구성요소
html element

jsp element
    1.scripting element
      1)scriptlet : .java파일 _jspService()내부에 들어감 <br>
      				<% int i=10; %>
      				<% out.print(i); %>
      				<% String a = request.getParameter("a"); %>
      2)expression : .java파일 _jspService()내부에 들어감
      				 out.print()가 자동 호출됨.
      				<%=i %>
      3)declaration : .java파일 _jspService()외부에 들어감
      	    메서드, 인스턴스변수 선언시에 사용
      	            <%!int i;//인스턴스변수 선언 %>
      <hr>
      i변수값 : <%=i %>
      i인스턴스변수값: <%=this.i %>
            
    2.directive element
      page directive
      include directive
      taglib directive
      
      
    3.action tag element
</body>
</html>
```

![19](https://user-images.githubusercontent.com/63957819/107487369-74605180-6bc9-11eb-9543-3116c750918a.png)

jsp는 주로 결과 값을 응답하는 용도로 쓴다. db하고 일, dao에서 직접 호출, service 호출은 하지 마라! 결과를 보여주는 뷰어의 역할만 하자~컨트롤러의 역할은 하면 안된다. 컨트롤러 역할은 Servlet이 하게 냅두자.

![20](https://user-images.githubusercontent.com/63957819/107487370-74f8e800-6bc9-11eb-9cd4-10441fea7143.png)

jsp용 .java파일을 만들어준다. 컴파일이 되고 실행이 되서 자동 호출이 돼서 응답을 해준 거다.

jsp가 실행이 되는게 아니고 jsp용.java파일이 컴파일 되고 실행이 자동 되고 객체가 생성돼서 객체의_jspService가 자동 호출이 돼서 응답을 받아가는 거다. 

![21](https://user-images.githubusercontent.com/63957819/107487372-74f8e800-6bc9-11eb-8466-645d72de4e3b.png)

first. 대신 fisrt_로 찾아간다. 클래스를 찾았는데 클래스가 없으면 자바 소스코드를 만들어낸다.  자바 파일이 만들어졌으면 컴파일이 돼서 파일이 만들어진다. 그 다음에 자바가 객체 지향 언어이기 때문에 객체인가 물어본다. 객체가 없다면 알아서 자동 객체 생성을 한다. 그리고 jsp형태이기 때문에 객체가 갖고 있는 jspInit메서드가 자동 호출이 된다. 그 다음 절차로는 요청 객체와 응답 객체 생성한 후에 객체의 특정 메서드가 호출 되어야 하는데 그 메서드가  _jspService이다. 요청 객체와 응답 객체 인자로 그대로 쓰인다. 그리고 응답 된다.

![22](https://user-images.githubusercontent.com/63957819/107487373-75917e80-6bc9-11eb-90c4-1f0337ee303e.png)

지역변수와 메서드 밖의 i변수가 있을텐데 지역변수를 우선 할 거다. 인스턴스 변수값이 출력되는게 아니다. 인스턴스 변수는 메서드 밖에서 선언되는 거기 때문에 자동 호출하면 _jspService 안쪽에 들어가기 때문에 지역변수 i가 출력 된다. 

인스턴스 변수 i값을 출력하고 싶으면 this.을 사용하면 된다. 

expression 사용할 때에는 ;붙이지 않는다.

out, request.getParameter  사용할 수 있다. 왜냐하면 _jspService안에 매개변수나 지역변수를 미리 선언되어 있기 때문이다.

---

![23](https://user-images.githubusercontent.com/63957819/107487375-762a1500-6bc9-11eb-865d-36507b31c4ec.png)

Control가 Service의 findAll()를 호출하고 받아와서 받아온 결과를 list형태로..list자료가 jsp에게 응답될 내용인 거다. list라는 변수 값을 다른 객체에게 전달해야 하는데 전달 방법이 없다. 그래서 중간에 forward방식을 이용  HttpServletRequest가 forward가 되어도 이동 된 jsp객체는 서로 같은 객체를 쓴다. param은 요청 시에 전달된 데이터를 자동 세팅이 되다 보니깐 개발자에게 세팅하는 메서드는 제공 되지 않는다. attribute이라는 멤버 변수가 있는데 자료형은 param처럼 맵 타입이다 키 벨류 형식을 갖는다. attribute의 가장 큰 특징으로 setting과 getting, remove 할 수 있다. 

서블릿단에서 jsp로 이동하기 전에 request객체에 attribute로 셋팅을 하자. 그래 놓고 페이지 이동을 하고 request.getAttribute로 키에 해당하는 자료를 꺼내오면 된다. 마지막으로 list를 활용해서 응답을 해주면 된다.

param vs attribute ? param은 값의 유형이 반드시 String타입으로 값이 저장 된다. attribute는 값의 유형이 자바의 최상위 클래스인 Object 타입으로 값이 저장된다.

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
```

object타입의 리턴 타입은 casting 해서 

jsp만 요청해서 테스트하면 안된다. jsp테스트할 때에는 반드시 서블릿 부터 요청해서 테스트를 해야 한다.

실행결과>

![24](https://user-images.githubusercontent.com/63957819/107487378-762a1500-6bc9-11eb-9451-5580aaf69542.png)

![25](https://user-images.githubusercontent.com/63957819/107487380-76c2ab80-6bc9-11eb-864c-b70c8d376cda.png)

productlist.html은 이제 필요 없으니까 지우자
