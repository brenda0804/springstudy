package shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import common.util.MyUtil;
import shop.model.CartBean;
import user.member.model.MemberVO;

public class CartEditAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1.수정할 상품번호, 수량 받기
		String pnum=req.getParameter("pnum");
		String oqty=req.getParameter("oqty");
		//2.유효성 체크
		if(pnum==null||oqty==null||pnum.trim().isEmpty()||pnum.trim().isEmpty()){
			this.setRedirect(true);
			this.setViewPage("index.do");
			return;
		}
		//3.로그인 여부체크
		MemberVO loginUser=this.loginCheck(req);
		if(loginUser==null){
			MyUtil.addMsgLoc(req, "login please", "index.do");
			return;
		}
		//4.장바구니 빈 세션에서 꺼내오기
		HttpSession ses=req.getSession();
		CartBean cart=(CartBean)ses.getAttribute("cartBean"+loginUser.getUserid());
		try{
		if(cart!=null){
			//장바구니 특정 상품의 수량을 수정하는 메소드
			cart.setEdit(new Integer(pnum.trim()),Integer.parseInt(oqty));
			this.setRedirect(true);
			this.setViewPage("cartList.do#shop");
		}else{
			MyUtil.addMsgLoc(req, "장바구니가 비었어요", "index.do");
			this.setRedirect(false);
			this.setViewPage("memo/message.jsp");
		}}catch(NumberFormatException e){
			String msg=e.getMessage();
			String loc="javascript:history.back()";
			MyUtil.addMsgLoc(req, msg, loc);
			this.setRedirect(false);
			this.setViewPage("memo/message.jsp");
		}
		
	}

}
