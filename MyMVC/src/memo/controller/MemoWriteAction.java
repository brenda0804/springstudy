package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;   

/*Sub Controller(일꾼) 들은 AbstractAction 추상클래스를 상속받아서 execute()메소드를 오버라이드 해야한다.
 * 
 * */
       
public class MemoWriteAction extends AbstractAction{

	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemoWriteAction의 execute()호출됨...");
		
		req.setAttribute("mymsg", "everybody say hello");
		
		//뷰페이지 지정
		this.setViewPage("/memo/memo.jsp");
		//이동방식 지정(forward 방식 => false, redirect=> true)
		this.setRedirect(false);
		//this.setRedirect(true);
	}

}
