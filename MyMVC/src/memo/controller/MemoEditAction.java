package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;
public class MemoEditAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
		HttpServletResponse res) throws Exception {
		//1. ����ڰ� �Է��� �� �ޱ� - POST���
		String idx=req.getParameter("idx");
		//2. ��ȿ�� üũ
		if(idx==null||idx.trim().equals("")){
			this.setRedirect(true);
			this.setViewPage("memoList.do");
			return;
		}
		//3. DAO���� = >biz methodȣ��
		MemoDAO mDao = new MemoDAO();
		
		//4. ������ �޽��� ���� req�� ����
		MemoDTO dto = mDao.selectMemoByIdx(Integer.parseInt(idx));
		req.setAttribute("memo", dto);//key, value����
		
		//5. �������� ����
		this.setViewPage("memo/memoEdit.jsp");
		
		//6. �̵���� ����(forward ��� => false, redirect=> true)
		this.setRedirect(false);
	}

}
