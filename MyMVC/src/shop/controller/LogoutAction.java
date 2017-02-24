package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;

public class LogoutAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//���� ���� ��� ��ȿȭ
		HttpSession session=req.getSession();
		session.invalidate();//****�ܿ���****
		
		String msg="�α׾ƿ� �Ǿ����ϴ�.";
		String loc="index.do";
		MyUtil.addMsgLoc(req,msg,loc);
		this.setRedirect(false);
		this.setViewPage("/memo/message.jsp");
	}

}
