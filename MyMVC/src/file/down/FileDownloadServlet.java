package file.down;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileDown")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public FileDownloadServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		download(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		download(request,response);
	}
	private void download(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		//1. 다운로드할 파일명 받아오기
		String filename = req.getParameter("filename");
		System.out.println("filename = "+filename);
		if(filename==null||filename.trim().isEmpty()){
			return;
		}
		
		//2. 업로드한 디렉토리의 절대경로 얻기
		//JSP에서는 application.getRealPath("/Upload");
		//application: ServletContext 타입
		ServletContext application = req.getServletContext();
		String upDir = application.getRealPath("/upload");
		
		//3. 다운로드 받을 파일의 절대경로 만들기
		String path = upDir +"/"+filename;
		
		/* 다운로드창을 띄워서 어디에 다운받을지 정하는 일을 해보자.
		 * response 헤더에 컨텐트 타입을 지정하면 브라우저는 해당 컨텐트 타입이 알 수 있는 형식이면 보여주고,
		 * 만약 잘 모르는 파일 형식이면 다운로드 창을 실행시킨다. 
		 * */
		//res.setContentType("application/unknown");
		res.setContentType("application/octet-stream");
		//잘모르는 파일 형식으로 다운로드(다이얼로그) 창을 실행시킨다.
		
		//한글 파일명이 깨지는 것을 막기 위해
		String filename_en //filename 을 byte단위로 분해해서 새로운 String 객체 생성
		= new String(filename.getBytes(),"ISO-8859-1");
		
		
		String attach="attachment; filename="+filename_en;
		res.setHeader("Content-Disposition", attach);
		
		//4. 3번 경로로 스트림 연결해서 파일을 읽어들이고 data를 클라이언트 쪽으로 내보낸다.
		/* 데이터 소스:파일(path)
		 * 		==>FileInputStream ==>BufferedInputStream
		 * 데이터 목적지:클라이언트(웹브라우저)
		 * 		==>ServletOuputStream ==>BufferedOutputStream
		 * */
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream());//->ServletOuputStream을 반환
		byte[] data = new byte[4000];
		int n=0;
		while((n=bis.read(data))!=-1){//파일의 끝(-1)이 아닐때까지 읽어들이기
			bos.write(data,0,n);
			bos.flush();
		}
		if(bis!=null)bis.close();
		if(bos!=null)bos.close();
	}	
}

















