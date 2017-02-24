package memo.controller;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.MemoDAO;
import memo.model.MemoDTO;

public class MemoListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemoDAO dao = new MemoDAO();
		Vector<MemoDTO> v = dao.selectAllMemo();
		req.setAttribute("memoV", v);
		//view페이지 지정
		this.setViewPage("/memo/memoList.jsp");
		//이동방식 지정 - request를 공유하는 방식(forward방식)
		this.setRedirect(false);
		
	}

}
