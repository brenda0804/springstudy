package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;
public class MemoEditAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
		HttpServletResponse res) throws Exception {
		//1. 사용자가 입력한 값 받기 - POST방식
		String idx=req.getParameter("idx");
		//2. 유효성 체크
		if(idx==null||idx.trim().equals("")){
			this.setRedirect(true);
			this.setViewPage("memoList.do");
			return;
		}
		//3. DAO생성 = >biz method호출
		MemoDAO mDao = new MemoDAO();
		
		//4. 실행결과 메시지 등을 req에 저장
		MemoDTO dto = mDao.selectMemoByIdx(Integer.parseInt(idx));
		req.setAttribute("memo", dto);//key, value저장
		
		//5. 뷰페이지 지정
		this.setViewPage("memo/memoEdit.jsp");
		
		//6. 이동방식 지정(forward 방식 => false, redirect=> true)
		this.setRedirect(false);
	}

}
