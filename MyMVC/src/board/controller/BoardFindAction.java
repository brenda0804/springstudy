package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
//m22-517-742
//[12.05] 작성시작 - ListAction 수정
//http://localhost:9090/MyMVC/board-find.do
//?cpage=3&pageSize=5&findType=1&findString=123의 형태로 요청이 들어옴

public class BoardFindAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		//1.현재 보여줄 페이지 파라미터값을 받는다.(cpage)
		String cpStr=req.getParameter("cpage");
		if(cpStr==null){
			cpStr="1";//디폴트 페이지를 1페이지로 지정
		}
		int cpage=Integer.parseInt(cpStr.trim());
		
		//[12.05]페이지 사이즈 받기(한 페이지당 보여줄 목록 갯수) 받기
		String psStr=req.getParameter("pageSize");
		HttpSession session=req.getSession();//session얻기
		if(psStr==null){
			//파라미터로 넘어오지 않았다면 세션에 저장된 pageSize를 꺼낸다.
			//jsp는 session내장객체가 있지만 java는 session객체를 얻어와야한다.
			psStr=(String)session.getAttribute("pageSize");//pageSize를 session에 저장하는 코드=>[_바로 아래에_]
			if(psStr==null){//꺼냈는데도 없다면
				psStr="5";
			}
		}
		//세션에 pageSize저장
		session.setAttribute("pageSize", psStr);//<=[_여기_]
		
		//[12.05]
		//findType, findString(검색유형, 검색어) 받기
		String findTypeStr=req.getParameter("findType");
		String findString=req.getParameter("findString");
		//find.jsp처리중 일부
		if(findTypeStr==null){
			findTypeStr=(String)session.getAttribute("findTypeStr");
			if(findTypeStr==null){
				findTypeStr="1";
			}
		}
		if(findString==null){
			findString=(String)session.getAttribute("findString");
			if(findString==null){
				findString="";
			}
		}
		//검색유형과 검색어를 세션에 저장하자
		//페이지 이동시 검색 유형, 검색어를 유지하기 위해
		session.setAttribute("findTypeStr", findTypeStr);
		session.setAttribute("findString", findString);
		String findType="";
		switch(findTypeStr){
		case "1": findType="subject";break;
		case "2": findType="name";break;
		case "3": findType="content";break;
		}
		
		//한 페이지당 보여줄 목록 갯수
		int pageSize=Integer.parseInt(psStr.trim());
		//BoardDAO dao=new BoardDAO();
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
		
		//총 게시물 수 가져오기=====>[12.05] 검색한 글의 총 게시물수
		//int totalCount=dao.getTotalCount();
		int totalCount=dao.getFindTotalCount(findType,findString);
		
		//한 페이지 당 보여줄 목록 갯수
		int pageCount=1;
		pageCount=(totalCount-1)/pageSize+1;
		if(cpage<=0){//음수값
			cpage=1;//1페이지를 보여준다.
		}else if(cpage>=pageCount){
			cpage=pageCount;//마지막 페이지를 보여준다.
		}
		//2.end,start값 구하기
		int end=cpage*pageSize;
		int start=end-pageSize+1;
		
		// 글 목록 가져오기 =====>[12.05]
		//List<BoardVO> arr=dao.listBoard(start,end);
		List<BoardVO> arr=dao.findListBoard(start,end,findType,findString);//3.DAO쪽에서 끊어줘야하므로 dao함수에 인자로 넘겨준다.

		req.setAttribute("boardList", arr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("cpage", cpage);//저장해두면더 안정적
		req.setAttribute("pageSize",pageSize);//나중에 페이지 사이즈도 바뀔예정
		req.setAttribute("findTypeStr",findTypeStr );
		req.setAttribute("findString",findString);
		
		this.setViewPage("/board/boardFind.jsp");//[12.05]
		this.setRedirect(false);

	}

}
/*
5.DB에서 끊어 올때 사용할 쿼리문====>[12.05] 이부분도 달라져야한다. at dao. findListBoard(?,?,?,?).

select * from (
select rownum rn, A.* from
(select * from board order by idx desc) A)
where rn between 6 and 10;

6.between start and end 값을 구하기 위한 공식
=>controller(BoardListAction.java - MVC)(model1으로 했으면 jsp에서 모든 것을 해야함. 코드 더럽)
--------------------------------------------
  cpage     pageSize  start     end
  1         5         1         5
  2         5         6         10
  3         5         11        15  
  4         5         16        20
--------------------------------------------
end=cpage*pageSize
start=end-pageSize+1
*/




