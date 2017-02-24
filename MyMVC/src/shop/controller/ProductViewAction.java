package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class ProductViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1.��ǰ��ȣ(pnum)�ޱ�
		String pnumStr = req.getParameter("pnum");
		//2.��ȿ�� üũ 
		if(pnumStr.trim().isEmpty()||pnumStr==null){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		//3.ProductDAOMyBatis���� �޼ҵ� ȣ��
		ProductDAOMyBatis dao=new ProductDAOMyBatis();
		ProductVO prod=dao.selectByPnum(new Integer(pnumStr.trim()));
		
		//4.�� ��� req�� ����
		req.setAttribute("item", prod);
		//5.�������� ���� �� �̵���� ����
		this.setRedirect(false);
		this.setViewPage("/shop/prodDetail.jsp");
	}

}
