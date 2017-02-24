package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;
public class MemoDeleteAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
		HttpServletResponse res) throws Exception {
		//1. ������ �۹�ȣ �ޱ�
		String idx =req.getParameter("idx");
		
		//2. ��ȿ�� üũ(��, ���ڿ�)
		if(idx==null||idx.trim().equals("")){
			this.setRedirect(true);
			this.setViewPage("memoList.do");
			return;
		}
		//3. MemoDAO���� => ���� ó�� �޼ҵ� ȣ��
		MemoDAO mDao = new MemoDAO();
		boolean n = mDao.deleteMemo(Integer.parseInt(idx.trim()));
		String str = (n)?"���� ����":"���� ����";
		String loc = (n)?"memoDelete.do#memoTab":"javascript:history.back()";
		
		req.setAttribute("msg", str);//key, value����
		req.setAttribute("loc", loc);
		
		//5. �������� ����
		this.setViewPage("memo/message.jsp");
		
		//6. �̵���� ����(forward ��� => false, redirect=> true)
		this.setRedirect(false);
	}

}
