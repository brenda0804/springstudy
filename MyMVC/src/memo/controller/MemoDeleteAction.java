package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;
public class MemoDeleteAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
		HttpServletResponse res) throws Exception {
		//1. 삭제할 글번호 받기
		String idx =req.getParameter("idx");
		
		//2. 유효성 체크(널, 빈문자열)
		if(idx==null||idx.trim().equals("")){
			this.setRedirect(true);
			this.setViewPage("memoList.do");
			return;
		}
		//3. MemoDAO생성 => 삭제 처리 메소드 호출
		MemoDAO mDao = new MemoDAO();
		boolean n = mDao.deleteMemo(Integer.parseInt(idx.trim()));
		String str = (n)?"삭제 성공":"삭제 실패";
		String loc = (n)?"memoDelete.do#memoTab":"javascript:history.back()";
		
		req.setAttribute("msg", str);//key, value저장
		req.setAttribute("loc", loc);
		
		//5. 뷰페이지 지정
		this.setViewPage("memo/message.jsp");
		
		//6. 이동방식 지정(forward 방식 => false, redirect=> true)
		this.setRedirect(false);
	}

}
