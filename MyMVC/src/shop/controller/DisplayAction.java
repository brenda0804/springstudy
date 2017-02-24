package shop.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import shop.model.CategoryVO;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;

public class DisplayAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//쇼핑몰의 상품을 Displqy하는 컨트롤러
		//HIT,NEW상품을 가져와 req에 저장한다.
		ProductDAOMyBatis pdao=new ProductDAOMyBatis();
		//0.카테고리 목록 가져오기
		List<CategoryVO> cList=pdao.getCategory();
		
		//1.HIT상품가져오기
		List<ProductVO> hitList=pdao.selectByPspec("HIT");
		//2.NEW상품가져오기
		List<ProductVO> newList=pdao.selectByPspec("NEW");
		
		//3.req에 저장
		req.setAttribute("hitList", hitList);//->뷰페이지 지정한 곳에서 ${hitList}로 값을 가져다가 쓸수 있음.
		req.setAttribute("newList", newList);
		//req.setAttribute("categoryList",cList);
		//카테고리 목록은 모든 페이지에서 출력되어야 하기 때문에 scope가 가장 큰 application에 저장하자.
		ServletContext application=req.getServletContext();
		application.setAttribute("categoryList", cList);
		
		//4.뷰 페이지 지정 및 이동방식 지정
		this.setViewPage("/shop/mall.jsp");
		this.setRedirect(false);
	
	}

}
