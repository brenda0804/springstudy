package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.*;
public class MemoEditEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
		HttpServletResponse res) throws Exception {
		req.setCharacterEncoding("UTF-8");
		String idx =req.getParameter("idx");
		String name=req.getParameter("name");
		String msg =req.getParameter("msg");
		if(idx==null||name==null||msg==null||idx.trim().equals("")||name.trim().equals("")){
			this.setRedirect(true);
			this.setViewPage("memoList.do");
			return;
		}
		MemoDAO mDao = new MemoDAO();
		MemoDTO memo = new MemoDTO(Integer.parseInt(idx),name,msg,null);
		boolean n = mDao.updateMemo(memo);
		String str =(n)?"업데이트 성공":"업데이트 실패";
		String loc =(n)?"javascript:self.close()":"javascript:history.back()";
		req.setAttribute("msg", str);//key, value저장
		req.setAttribute("loc", loc);
		req.setAttribute("mode", "pop");
		
		this.setViewPage("memo/message.jsp");
		this.setRedirect(false);
	}

}
