package shop.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import shop.model.CartBean;
import shop.model.ProductVO;
import user.member.model.MemberVO;

public class CartListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1.로그인 여부 체크
		HttpSession session=req.getSession();
		MemberVO loginUser=(MemberVO)session.getAttribute("loginUser");
		if(loginUser==null){
			MyUtil.addMsgLoc(req, "로그인해야 이용할 수 있어요", "index.do");
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			return;
		}
		//2.로그인 했다면 세션에서 장바구니 빈 꺼내오기
		CartBean cart=null;
		String userid=loginUser.getUserid();//로그인한 사람 아이디
		
		cart=(CartBean)session.getAttribute("cartBean"+userid);
		if(cart==null){
			cart=new CartBean();
		}
		//3.장바구니 상품 목록 가져오기
		List<ProductVO> cartList=cart.getCartList();
		
		//4.세션에 저장
		session.setAttribute("cartBean"+userid, cart);
		session.setAttribute("cartList", cartList);
		
		//5.장바구니 총액 가져오기
		Map<String,Integer> map=cart.getCartTotal();
		session.setAttribute("cartMap", map);
		
		//6.뷰페이지 지정 및 이동방식 지정
		this.setRedirect(false);
		this.setViewPage("/shop/cartList.jsp");
	}

}
