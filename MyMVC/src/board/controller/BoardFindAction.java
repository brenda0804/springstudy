package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
//m22-517-742
//[12.05] �ۼ����� - ListAction ����
//http://localhost:9090/MyMVC/board-find.do
//?cpage=3&pageSize=5&findType=1&findString=123�� ���·� ��û�� ����

public class BoardFindAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		//1.���� ������ ������ �Ķ���Ͱ��� �޴´�.(cpage)
		String cpStr=req.getParameter("cpage");
		if(cpStr==null){
			cpStr="1";//����Ʈ �������� 1�������� ����
		}
		int cpage=Integer.parseInt(cpStr.trim());
		
		//[12.05]������ ������ �ޱ�(�� �������� ������ ��� ����) �ޱ�
		String psStr=req.getParameter("pageSize");
		HttpSession session=req.getSession();//session���
		if(psStr==null){
			//�Ķ���ͷ� �Ѿ���� �ʾҴٸ� ���ǿ� ����� pageSize�� ������.
			//jsp�� session���尴ü�� ������ java�� session��ü�� ���;��Ѵ�.
			psStr=(String)session.getAttribute("pageSize");//pageSize�� session�� �����ϴ� �ڵ�=>[_�ٷ� �Ʒ���_]
			if(psStr==null){//���´µ��� ���ٸ�
				psStr="5";
			}
		}
		//���ǿ� pageSize����
		session.setAttribute("pageSize", psStr);//<=[_����_]
		
		//[12.05]
		//findType, findString(�˻�����, �˻���) �ޱ�
		String findTypeStr=req.getParameter("findType");
		String findString=req.getParameter("findString");
		//find.jspó���� �Ϻ�
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
		//�˻������� �˻�� ���ǿ� ��������
		//������ �̵��� �˻� ����, �˻�� �����ϱ� ����
		session.setAttribute("findTypeStr", findTypeStr);
		session.setAttribute("findString", findString);
		String findType="";
		switch(findTypeStr){
		case "1": findType="subject";break;
		case "2": findType="name";break;
		case "3": findType="content";break;
		}
		
		//�� �������� ������ ��� ����
		int pageSize=Integer.parseInt(psStr.trim());
		//BoardDAO dao=new BoardDAO();
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
		
		//�� �Խù� �� ��������=====>[12.05] �˻��� ���� �� �Խù���
		//int totalCount=dao.getTotalCount();
		int totalCount=dao.getFindTotalCount(findType,findString);
		
		//�� ������ �� ������ ��� ����
		int pageCount=1;
		pageCount=(totalCount-1)/pageSize+1;
		if(cpage<=0){//������
			cpage=1;//1�������� �����ش�.
		}else if(cpage>=pageCount){
			cpage=pageCount;//������ �������� �����ش�.
		}
		//2.end,start�� ���ϱ�
		int end=cpage*pageSize;
		int start=end-pageSize+1;
		
		// �� ��� �������� =====>[12.05]
		//List<BoardVO> arr=dao.listBoard(start,end);
		List<BoardVO> arr=dao.findListBoard(start,end,findType,findString);//3.DAO�ʿ��� ��������ϹǷ� dao�Լ��� ���ڷ� �Ѱ��ش�.

		req.setAttribute("boardList", arr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("cpage", cpage);//�����صθ�� ������
		req.setAttribute("pageSize",pageSize);//���߿� ������ ����� �ٲ���
		req.setAttribute("findTypeStr",findTypeStr );
		req.setAttribute("findString",findString);
		
		this.setViewPage("/board/boardFind.jsp");//[12.05]
		this.setRedirect(false);

	}

}
/*
5.DB���� ���� �ö� ����� ������====>[12.05] �̺κе� �޶������Ѵ�. at dao. findListBoard(?,?,?,?).

select * from (
select rownum rn, A.* from
(select * from board order by idx desc) A)
where rn between 6 and 10;

6.between start and end ���� ���ϱ� ���� ����
=>controller(BoardListAction.java - MVC)(model1���� ������ jsp���� ��� ���� �ؾ���. �ڵ� ����)
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




