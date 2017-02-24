package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import shop.model.CartBean;
import user.member.model.MemberVO;

public class CartDelAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//0. 로그인 여부 체크
		HttpSession session=req.getSession();
		MemberVO user=(MemberVO)session.getAttribute("loginUser");
			//null 체크 생략
		if(user==null)return;
		
		//1. 삭제할 상품번호 받기
		String pnum=req.getParameter("pnum");
		
		//2. 유효성 체크
		if(pnum==null||pnum.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		//3. 장바구니 빈을 세션에서 꺼내기
		CartBean cart=(CartBean)session.getAttribute("cartBean"+user.getUserid());
		if(cart!=null){
			//장바구니에 해당 상품 삭제 처리하는 메소드 호출
			cart.removeProduct(new Integer(pnum.trim()));
		}
		
		//4. 페이지 이동 처리
		this.setRedirect(true);
		this.setViewPage("cartList.do");		
		
	}

}
