package common.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import memo.controller.*;

@WebServlet("*.do2") //.do로 끝나는 거 모든지 받을것이다.
public class FrontControllerOld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestProcess(request, response);
	}
	private void RequestProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
		System.out.println("FrontControllerOld실행됨.......");
		//1. 클라이언트의 요청 URI를 분석해보자.
		String uri=req.getRequestURI();
		System.out.println("URI: "+uri);
		//컨텍스트명 얻기
		String myctx=req.getContextPath();
		System.out.println("myctx: "+myctx);//"/MyMVC"
		int len=myctx.length(); //=6
		System.out.println("leng: "+len);
		String cmd=uri.substring(len+1);
		System.out.println("cmd: "+cmd);
		AbstractAction action=null;
		if(cmd.equals("index.do")){
			action=new IndexAction();
		}
		if(cmd.equals("memoWrite.do")){
			action=new MemoWriteAction();
		}else if(cmd.equals("memoInsert.do")){
			action=new MemoInsertAction();
		}else if(cmd.equals("memoList.do")){
			action=new MemoListAction();
		}else if(cmd.equals("memoDelete.do")){
			action=new MemoDeleteAction();
		}else if(cmd.equals("memoEdit.do")){
			action=new MemoEditAction();
		}else if(cmd.equals("memoEditEnd.do")){
			action=new MemoEditEndAction();
		}
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestProcess(request, response);
	}

}
