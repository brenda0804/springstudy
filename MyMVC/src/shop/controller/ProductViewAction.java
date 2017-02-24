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
		//1.상품번호(pnum)받기
		String pnumStr = req.getParameter("pnum");
		//2.유효성 체크 
		if(pnumStr.trim().isEmpty()||pnumStr==null){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		//3.ProductDAOMyBatis생성 메소드 호출
		ProductDAOMyBatis dao=new ProductDAOMyBatis();
		ProductVO prod=dao.selectByPnum(new Integer(pnumStr.trim()));
		
		//4.그 결과 req에 저장
		req.setAttribute("item", prod);
		//5.뷰페이지 지정 및 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("/shop/prodDetail.jsp");
	}

}
