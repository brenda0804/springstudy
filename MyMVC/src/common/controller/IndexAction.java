package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*Sub Controller(�ϲ�) ���� AbstractAction �߻�Ŭ������ ��ӹ޾Ƽ� execute()�޼ҵ带 �������̵� �ؾ��Ѵ�.
 * 
 * */

public class IndexAction extends AbstractAction{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("IndexAction�� execute()ȣ���...");
		
		req.setAttribute("mymsg", "everybody say hello");
		
		//�������� ����
		this.setViewPage("/index.jsp");
		//�̵���� ����(forward ��� => false, redirect=> true)
		this.setRedirect(false);
		//this.setRedirect(true);
	}

}
