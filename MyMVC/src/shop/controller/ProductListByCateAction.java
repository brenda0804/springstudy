package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class ProductListByCateAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1.code,cname �Ķ���� �� �޾ƿ���
		String code = req.getParameter("code");
		String cname= req.getParameter("cname");
		//2.��ȿ��üũ (null �̸� index.do�� redirect�̵���Ų��.)
		if(code==null||cname==null||code.trim().isEmpty()||cname.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		//3.ProductDAOMyBatis��ü ������ ī�װ� �ڵ�� ��ǰ��� �������� �޼ҵ� ȣ��
		//List<ProductVO> selectByCategory(code)
		ProductDAOMyBatis dao=new ProductDAOMyBatis();
		List<ProductVO> pList=dao.selectByCategory(code);
		//4.req�� ��ǰ��� ����
		req.setAttribute("pList", pList);
		req.setAttribute("code", cname);
		req.setAttribute("cname", cname);
		
		//5.�������� ���� �� �̵���� ����
		this.setRedirect(false);
		this.setViewPage("/shop/mallByCategory.jsp");
		//this.setViewPage("/shop/mallNew.jsp");
	}

}
