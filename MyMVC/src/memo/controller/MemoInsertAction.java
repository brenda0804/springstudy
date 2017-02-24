package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;
public class MemoInsertAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
		HttpServletResponse res) throws Exception {
		//0. �ѱ�ó�� - POST���
		req.setCharacterEncoding("UTF-8");
		//1. ����ڰ� �Է��� �� �ޱ� - POST���
		String name=req.getParameter("name");
		String msg =req.getParameter("msg");
		//2. ��ȿ�� üũ
		if(name==null||msg==null||name.trim().equals("")){
			this.setRedirect(true);
			this.setViewPage("memoWrite.do");
			return;
		}
		//3. DAO���� = >biz methodȣ��
		MemoDAO mDao = new MemoDAO();
		MemoDTO memo = new MemoDTO(0,name,msg,null);
		
		//4. ������ �޽��� ���� req�� ����
		boolean n = mDao.insertMemo(memo);
		String str =(n)?"�۾��� ����":"�۾��� ����";
		String loc =(n)?"memoList.do":"javascript:history.back()";
		
		req.setAttribute("msg", str);//key, value����
		req.setAttribute("loc", loc);
		
		//5. �������� ����
		this.setViewPage("memo/message.jsp");
		
		//6. �̵���� ����(forward ��� => false, redirect=> true)
		this.setRedirect(false);
	}

}
