package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import common.util.MyUtil;

public class BoardEditAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. 글번호,비번 받기
		// 2. 유효성 체크
		String idxStr = req.getParameter("idx");
		String pwd = req.getParameter("pwd");

		// 3. 유효성 체크
		if (idxStr == null || pwd == null || pwd.trim().isEmpty()) {
			this.setRedirect(true);
			this.setViewPage("board-list.do#bbs");
			return;
		}
		// 3. db비번 가져와 비교하기
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		String dbPwd=dao.selectPwd(new Integer(idxStr.trim()));
		if(!pwd.equals(dbPwd)){
			String loc="javascript:history.back()";
			MyUtil.addMsgLoc(req, "비밀번호가 틀려요", loc);
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			return;
		}

		// 4. 비번이 일치하면 해당 글 내용 가져오기
		BoardVO board=dao.viewBoard(new Integer(idxStr.trim()));
		
		req.setAttribute("board", board);
		
		this.setRedirect(false);
		this.setViewPage("/board/boardEdit.jsp");
		
	}

}





