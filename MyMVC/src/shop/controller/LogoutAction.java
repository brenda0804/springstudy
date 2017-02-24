package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;

public class LogoutAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//세션 변수 모두 무효화
		HttpSession session=req.getSession();
		session.invalidate();//****외우자****
		
		String msg="로그아웃 되었습니다.";
		String loc="index.do";
		MyUtil.addMsgLoc(req,msg,loc);
		this.setRedirect(false);
		this.setViewPage("/memo/message.jsp");
	}

}
