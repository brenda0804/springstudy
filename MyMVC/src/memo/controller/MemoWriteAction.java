package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;   

/*Sub Controller(�ϲ�) ���� AbstractAction �߻�Ŭ������ ��ӹ޾Ƽ� execute()�޼ҵ带 �������̵� �ؾ��Ѵ�.
 * 
 * */
       
public class MemoWriteAction extends AbstractAction{

	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("MemoWriteAction�� execute()ȣ���...");
		
		req.setAttribute("mymsg", "everybody say hello");
		
		//�������� ����
		this.setViewPage("/memo/memo.jsp");
		//�̵���� ����(forward ��� => false, redirect=> true)
		this.setRedirect(false);
		//this.setRedirect(true);
	}

}
