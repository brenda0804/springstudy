package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import user.member.model.MemberDAO;
import user.member.model.MemberVO;
import user.member.model.NotMemberException;

public class LoginEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1.사용자가 입력한 userid, pwd 받기
		String userid=req.getParameter("userid");
		String pwd=req.getParameter("pwd");
		
		//2.유효성 체크-> index.do로 redirect이동시키기
		if(userid==null||userid.trim().isEmpty()||pwd==null||pwd.trim().isEmpty()){
			String msg="입력한 값이 유효하지 않습니다.";
			String loc="index.do";
			MyUtil.addMsgLoc(req,msg,loc);
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			return;
		}
		
		//3.MemberDAO객체 생성 -> loginCheck(userid,pwd) 메소드 호출
		MemberDAO mDao = new MemberDAO();
		
		/////////
		try{
		MemberVO user = mDao.loginCheck(userid, pwd);
		/////////
		//아이디가 없거나 비번이 틀리면 NotMemberException이 발생된다.
		
		//4.반환받은 MemberVO를 세션에 저장
		HttpSession session=req.getSession();
		session.setAttribute("loginUser", user);
		
		//5.index.do페이지로 redirect이동
		String msg="로그인 성공";
		String loc="index.do";
		MyUtil.addMsgLoc(req,msg,loc);
		this.setRedirect(false);
		this.setViewPage("/memo/message.jsp");
		}catch(NotMemberException e){
			String msg=e.getMessage();
			String loc="index.do";
			MyUtil.addMsgLoc(req,msg,loc);
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
		}
	}

}
