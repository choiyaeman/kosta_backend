package com.my.control;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청전달데이터 얻기
		//String t = request.getParameter("t"); (x) 요청시의 form 태그를 보면 multipart/form-data의 enctype으로 getParameter로 얻으면 안된다.
		
		//ServletInputStream is = request.getInputStream();
		/*Scanner sc = new Scanner(is);
		while(sc.hasNextLine()) { //다음 한줄을 읽을 수 있는가 갖고 있는가의 의미
			String readLine = sc.nextLine();
			System.out.println(readLine);
		}*/
		
		//String saveDirectory = "c:\\upload"; //업로드된 파일이 저장될 경로
		//String saveDirectory = "C:\\workspace\\MyEE\\myback\\WebContent\\upload"; // (x) 이클립스경로
		//String saveDirectory = "C:\\workspace\\SW\\apache-tomcat-9.0.41\\apache-tomcat-9.0.41\\wtpwebapps\\myback\\upload"; //톰캣 절대경로
		String saveDirectory = this.getServletContext().getRealPath("upload"); //웹컨텍스트 실제배포경로
		System.out.println("saveDirectory:" + saveDirectory);
		
		int maxPostSize = 3000 * 1024;
		String encoding = "MS949";
		FileRenamePolicy policy = //new FileRenamePolicy();//추상화된 api이므로 직접 객체 생성x 하위클래스를 만들어서 직접 설정해서 해야한다.
				new DefaultFileRenamePolicy();
		MultipartRequest mr = //new MultipartRequest(request, saveDirectory);
				//new MultipartRequest(request, saveDirectory, encoding);
				//new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
				new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		//요청전달데이터 얻기
		String t = mr.getParameter("t");
		
		Enumeration<String> e = mr.getFileNames(); //Enumeration는 이터레이션(반복자)을 수행해주는 api
		while(e.hasMoreElements()) {
			String fileName = e.nextElement();
			System.out.println("fileName = " + fileName);
			System.out.println("OriginalFileName = " + mr.getOriginalFileName(fileName));
		}
		
	}

}
