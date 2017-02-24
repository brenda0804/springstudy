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
		//1.code,cname 파라미터 값 받아오기
		String code = req.getParameter("code");
		String cname= req.getParameter("cname");
		//2.유효성체크 (null 이면 index.do로 redirect이동시킨다.)
		if(code==null||cname==null||code.trim().isEmpty()||cname.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		//3.ProductDAOMyBatis객체 생성후 카테고리 코드로 상품목록 가져오는 메소드 호출
		//List<ProductVO> selectByCategory(code)
		ProductDAOMyBatis dao=new ProductDAOMyBatis();
		List<ProductVO> pList=dao.selectByCategory(code);
		//4.req에 상품목록 저장
		req.setAttribute("pList", pList);
		req.setAttribute("code", cname);
		req.setAttribute("cname", cname);
		
		//5.뷰페이지 지정 및 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("/shop/mallByCategory.jsp");
		//this.setViewPage("/shop/mallNew.jsp");
	}

}
