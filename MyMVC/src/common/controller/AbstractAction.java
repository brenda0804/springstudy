package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.member.model.MemberVO;

//�߻�Ŭ���� : Command��� �������̽��� ��ӹ޾Ƽ� execute()�߻�޼ҵ带 ����� ���´�.

public abstract class AbstractAction implements Command{
	private String viewPage; //�� ���������� ���� ����(ex] index.jsp...)
	private boolean isRedirect; //������ �̵���İ� ���õ� ���� ����  ����//redirect������� �̵��� true, forward�̵��� false�� ���´�.
	
	//setter , getter
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	public boolean isRedirect() {//boolean type�� getter�� is�� �޴´�. get�ƴ�
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	//�α��� üũ�� ���� �޼ҵ�
	public MemberVO loginCheck(HttpServletRequest req){
		HttpSession session=req.getSession();
		MemberVO user=(MemberVO)session.getAttribute("loginUser");
		return user;
	}
}///////////////////////////
