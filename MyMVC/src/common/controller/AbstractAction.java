package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.member.model.MemberVO;

//추상클래스 : Command라는 인터페이스를 상속받아서 execute()추상메소드를 멤버로 갖는다.

public abstract class AbstractAction implements Command{
	private String viewPage; //뷰 페이지값을 가질 예정(ex] index.jsp...)
	private boolean isRedirect; //페이지 이동방식과 관련된 값을 가질  예정//redirect방식으로 이동시 true, forward이동시 false를 갖는다.
	
	//setter , getter
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	public boolean isRedirect() {//boolean type의 getter는 is로 받는다. get아님
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	//로그인 체크를 위한 메소드
	public MemberVO loginCheck(HttpServletRequest req){
		HttpSession session=req.getSession();
		MemberVO user=(MemberVO)session.getAttribute("loginUser");
		return user;
	}
}///////////////////////////
