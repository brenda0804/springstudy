package common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memo.controller.MemoDeleteAction;
import memo.controller.MemoEditAction;
import memo.controller.MemoEditEndAction;
import memo.controller.MemoInsertAction;
import memo.controller.MemoListAction;
import memo.controller.MemoWriteAction;

@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "config", 
							  value = "C:/MyJava/Workspace/MyMVC/WebContent/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,Object> cmdMap
		= new HashMap<String,Object>();
	@Override
	public void init(ServletConfig sc) throws ServletException{
		/*init():서브릿실행시 최초 요청이 있을 때 딱 한번만 호출되는 메소드
		 * 여기서 init-param에 기술되어 있는 config에 해당하는 value값(Command.properties의 절대경로)을 얻어오자.
		 * 어노테이션에 명시되어있음*/
		String value=sc.getInitParameter("config");//절대경로
		Properties props = new Properties();
		try{
			props.load(new FileInputStream(value));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//Properties 객체에 저장된 key 값들만 추출하자.
		Enumeration<Object> en = props.keys();
		while(en.hasMoreElements()){
			String key=en.nextElement().toString();//key 값
			String className=props.getProperty(key);//value값
			System.out.println(key+":"+className);
			if(className!=null){
				className=className.trim();//앞뒤 공백 제거후 다시 할당
			}
			try {
				Class cls = Class.forName(className);
				Object cmdInstance=cls.newInstance();
				//해당 클래스를 객체화시켜 메모리에 올려준다.=>new 작업을 대신해준다.
				///////////////////////////////////
				cmdMap.put(key, cmdInstance);
				///////////////////////////////////
			}catch(Exception e) {//class를 찾지 못했을 예외처리
				e.printStackTrace();
				throw new ServletException(e);//web browser에 예외 발생 스택기록을 출력한다.
			}
		}
	}
	private void requestProcess(HttpServletRequest req, HttpServletResponse res)
			throws ServletException,IOException{
		System.out.println("FrontController실행됨.......");
		//1. 클라이언트의 요청 URI를 분석해보자.
		String uri=req.getRequestURI();
		System.out.println("URI: "+uri);
		//컨텍스트명 얻기
		String myctx=req.getContextPath();
		System.out.println("myctx: "+myctx);//"/MyMVC"
		int len=myctx.length(); //=6
		System.out.println("leng: "+len);
		String cmd=uri.substring(len);//+1했던거 빼고,"/index.do"로 /가 붙여서 cmd에 저장
		System.out.println("cmd: "+cmd);
		AbstractAction action=null;
		if(cmd.equals("index.do")){
			action=new IndexAction();
		}
		//분기문이 들어갔던 자리//hashmap(cmdMap)에서 get해서 얻어온다. cmd를 이용해서
		Object instance=cmdMap.get(cmd);
		//->action으로 형변환->
		action=(AbstractAction)instance;
		//-----------------//
		if(action==null){
			System.out.println("action이 널");
			return;
		}
		try {
			///////////
			action.execute(req,res);//이부분에서 db작업이 이루어진다. *****중요한 부분*****
			///////////
			//이동할 뷰페이지 얻기
			String viewPage=action.getViewPage();
			System.out.println("viewPage: "+viewPage);
			//이동방식에 따라 뷰페이지 이동
			if(action.isRedirect()){
				//redirect로 이동 (10~20%정도 사용함)
				res.sendRedirect(viewPage);
				
			}else{
				//forward방식으로 이동(80~90%정도 사용함)
				RequestDispatcher disp=req.getRequestDispatcher(viewPage);
				disp.forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  	protected void doGet(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		requestProcess(request,response);
  	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
  		requestProcess(request,response);
	}
}
