package board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import common.util.MyUtil;

public class BoardEditEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, 
			HttpServletResponse res) throws Exception {
		// 1. 파일 업로드 처리
		ServletContext app=req.getServletContext();
		String upDir=app.getRealPath("/upload");
		System.out.println(upDir);
		try{
		DefaultFileRenamePolicy df=new DefaultFileRenamePolicy();
		MultipartRequest mr
		=new MultipartRequest(req,upDir,10*1024*1024,"UTF-8",df); 
		
		// 2. 첨부 파일이 있다면 기존에 업로드 했던 파일은
		//    서버에서 삭제 처리
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		String idxStr=mr.getParameter("idx");
		Integer idx=new Integer(idxStr.trim());
		String filename=mr.getFilesystemName("filename");
		long filesize=0;
		if(filename!=null){
			//예전에 올린 파일명 받아오기
			String oldFile=dao.selectFile(idx);
			String path=upDir+"/"+oldFile;
			File delFile=new File(path);
			boolean b=delFile.delete();
			//새로 업로드한 파일의 크기 알아내기
			File newFile=mr.getFile("filename");
			filesize=newFile.length();
		}
		//3. 사용자가 입력한 값 받기 
		String name=mr.getParameter("name");
		String email=mr.getParameter("email");
		String homepage=mr.getParameter("homepage");
		String pwd=mr.getParameter("pwd");
		String subject=mr.getParameter("subject");
		String content=mr.getParameter("content");
		int readnum=0,refer=0,lev=0,sunbun=0;
		
		BoardVO board=new BoardVO(idx,name,email,homepage,pwd,subject,content,
				null,readnum,filename,filesize,refer,lev,sunbun);
		
		//4. 수정 처리 (DAO메소드 호출)
		int n=dao.updateBoard(board);
		
		//5. 글 결과 메시지 및 이동경로 처리
		String msg=(n>0)?"수정 성공":"수정 실패";
		String loc=(n>0)?"board-list.do#bbs":"javascript:history.back()";
		MyUtil.addMsgLoc(req, msg, loc);
		
		//6. 뷰페이지 지정, 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("/memo/message.jsp");
		
		}catch(IOException e){
			String msg
			="실패: 잘못된 경로 이거나\\n첨부파일 용량이 10M를 초과했어요";
			String loc="javascript:history.back()";
			MyUtil.addMsgLoc(req, msg, loc);
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
		}

	}
	
	
	
	
	
	

}
