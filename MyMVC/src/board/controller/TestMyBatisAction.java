package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.controller.AbstractAction;
import board.model.*;

public class TestMyBatisAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
		int count=dao.getTotalCount();
		
		req.setAttribute("totalCount", count);
		
		this.setViewPage("board/testMyBatis.jsp");
		this.setRedirect(false);

	}

}////////////////////////////////////////////
