package board.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import common.controller.AbstractAction;

public class BoardRemoveAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// 1. 삭제할 글 번호 받기
		// 2. 비밀번호 받기
		String idxStr=req.getParameter("idx");
		String pwd=req.getParameter("pwd");
		
		//3. 유효성 체크
		if(idxStr==null||pwd==null||pwd.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("board-list.do#bbs");
			return;
		}
		
		//4. DAO의 메소드 호출
		// DB board테이블의 pwd와 사용자가 입력한 pwd
		// 가 일치하는지 여부를 체크
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		String dbPwd=dao.selectPwd(new Integer(idxStr.trim()));
		
		if(!pwd.equals(dbPwd)){
			req.setAttribute("message", "비밀번호가 일치하지 않아요");
			req.setAttribute("loc", "javascript:history.back()");
			this.setRedirect(false);
			this.setViewPage("memo/message.jsp");
			return;
		}
		//5. 비번이 일치한다면 
		//   (1) 첨부 파일이 있을 경우 첨부파일을 서버에서 삭제 처리
		String filename=dao.selectFile(new Integer(idxStr.trim()));
		if(filename!=null){
			//업로드 dir 절대경로 구하기
			ServletContext app=req.getServletContext();
			String upDir=app.getRealPath("/upload");
			String path=upDir+"/"+filename;
			File delFile=new File(path);
			boolean b=delFile.delete();
			System.out.println("첨부파일 삭제 여부: "+b);
		}//if---------
		
		//   (2) 삭제 처리 =>db에서 레코드 삭제
		int n=dao.deleteBoard(new Integer(idxStr.trim()));
		
		//6. 그 결과 메시지 처리
		String msg=(n>0)?"글 삭제 성공":"삭제 실패";
		String loc="board-list.do#bbs";
		req.setAttribute("message", msg);
		req.setAttribute("loc", loc);
		
		//7. 뷰페이지 지정 및 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("/memo/message.jsp");

	}

}


