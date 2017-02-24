package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
//m22-517-742
public class BoardListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		//1.���� ������ ������ �Ķ󤻹��Ͱ��� �޴´�.(cpage)
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
		//�� �������� ������ ��� ����
		int pageSize=Integer.parseInt(psStr.trim());
		
		
		//BoardDAO dao=new BoardDAO();
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		//�� �Խù� �� ��������
		int totalCount=dao.getTotalCount();
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
		
		// �� ��� �������� 
		List<BoardVO> arr=dao.listBoard(start,end);//3.DAO�ʿ��� ��������ϹǷ� dao�Լ��� ���ڷ� �Ѱ��ش�.

		req.setAttribute("boardList", arr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("cpage", cpage);//�����صθ�� ������
		req.setAttribute("pageSize",pageSize);//���߿� ������ ����� �ٲ���
		
		this.setViewPage("/board/boardList.jsp");
		this.setRedirect(false);

	}

}
/*
5.DB���� ���� �ö� ����� ������

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




