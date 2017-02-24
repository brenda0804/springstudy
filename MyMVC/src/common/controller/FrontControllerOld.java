package common.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import memo.controller.*;

@WebServlet("*.do2") //.do�� ������ �� ����� �������̴�.
public class FrontControllerOld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestProcess(request, response);
	}
	private void RequestProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException{
		System.out.println("FrontControllerOld�����.......");
		//1. Ŭ���̾�Ʈ�� ��û URI�� �м��غ���.
		String uri=req.getRequestURI();
		System.out.println("URI: "+uri);
		//���ؽ�Ʈ�� ���
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
			System.out.println("action�� ��");
			return;
		}
		try {
			///////////
			action.execute(req,res);//�̺κп��� db�۾��� �̷������. *****�߿��� �κ�*****
			///////////
			//�̵��� �������� ���
			String viewPage=action.getViewPage();
			System.out.println("viewPage: "+viewPage);
			
			//�̵���Ŀ� ���� �������� �̵�
			if(action.isRedirect()){
				//redirect�� �̵� (10~20%���� �����)
				res.sendRedirect(viewPage);
				
			}else{
				//forward������� �̵�(80~90%���� �����)
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
