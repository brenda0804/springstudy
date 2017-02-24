package shop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import shop.model.CartBean;
import shop.model.ProductDAOMyBatis;
import shop.model.ProductVO;
import user.member.model.MemberVO;

public class CartAddAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//pnum, oqty정보를 goCart()를 통해 넘어온게된다.
		//1.상품번호, 수량 받아오기
		String pnumStr=req.getParameter("pnum");
		String oqtyStr=req.getParameter("oqty");
		
		//2.유효성 체크
		if(pnumStr==null||oqtyStr==null||pnumStr.trim().isEmpty()||pnumStr.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		
		//3.회원만 이용할 수 있으므로 로그인 여부를 체크
		HttpSession session=req.getSession();
		MemberVO loginUser=(MemberVO)session.getAttribute("loginUser");
		if(loginUser==null){
			MyUtil.addMsgLoc(req, "로그인해야 이용할 수 있어요", "index.do");
			this.setRedirect(false);
			this.setViewPage("/memo/message.jsp");
			return;
		}
		String userid=loginUser.getUserid();
		
		//4.상품번호로 장바구니에 추가할 상품정보를 DB에서 가져오자.
		ProductDAOMyBatis pdao=new ProductDAOMyBatis();
		ProductVO item=pdao.selectByPnum(new Integer(pnumStr.trim()));
		
		//5.세션에 저장된 CartBean이 있는지 꺼내본다.
		CartBean cart=null;
		cart=(CartBean)session.getAttribute("cartBean"+userid);
		
		//6.해당 상품을 CartBean의 addProduct()로 장바구니에 추가		
		if(cart==null) cart=new CartBean();
		Integer pnum=new Integer(pnumStr.trim());
		int oqty=Integer.parseInt(oqtyStr.trim());
		cart.addProduct(pnum, oqty, item);
		
		//7.세션에 CartBean을 저장한다.
		session.setAttribute("cartBean"+userid, cart);//catBean+로그인한아이디
		
		//8.장바구니 목록 가져오기
		List<ProductVO> cartList=cart.getCartList();
		req.setAttribute("cartList", cartList);
		
		//9.뷰페이지 및 이동방식 지정
		/*this.setRedirect(false);
		this.setViewPage("/shop/cartList.jsp");*/
		//forward로 이동시 문제발생
		//이전 URL을 사용하므로 새로고침시 수량이 계속 증가하는 문제가 발생된다.
		this.setRedirect(true);
		this.setViewPage("cartList.do");//redirect의 이동일경우 do로 controller거쳐야된다.
	}
}










