package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.CategoryVO;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class ProductSearchAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pname=req.getParameter("pname");
		if(pname==null||pname.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("index.do");
		}
		ProductDAOMyBatis pdao=new ProductDAOMyBatis();
		List<ProductVO> productList=pdao.selectByPname(pname);
		req.setAttribute("productList", productList);
		req.setAttribute("pname",pname);
		this.setRedirect(false);
		this.setViewPage("/shop/searchView.jsp");
	}

}
