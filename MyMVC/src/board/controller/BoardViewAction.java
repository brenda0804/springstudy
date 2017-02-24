package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// [1] 글 번호 받아오기
		String idxStr=req.getParameter("idx");
		
		// [2] 유효성 체크
		if(idxStr==null||idxStr.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("board-list.do");
			return;
		}
		
		// [3] DAO 생성, 해당 글 가져오는 메소드 호출
		//BoardDAO dao=new BoardDAO();
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
		//     조회수 증가
		boolean b=dao.updateReadnum(new Integer(idxStr.trim()));
		
		BoardVO board=dao.viewBoard(new Integer(idxStr.trim()));
		
		// [4] 그 결과 req에 저장
		req.setAttribute("board", board);
		
		// [5] 뷰페이지 지정 및 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("/board/boardView.jsp");

	}

}///////////////////////////////////////////////
