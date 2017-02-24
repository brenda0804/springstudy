package shop.model;

import java.util.*;//자료구조
import java.io.*;

/**장바구니(ArrayList)에 상품을 추가, 삭제, 편집하는 로직을 갖는 서비스 빈즈*/

public class CartBean implements Serializable {
	private List<ProductVO> cartList=new ArrayList<ProductVO>();//장바구니
	
	//cartList getter 생성
	public List<ProductVO> getCartList() {
		return cartList;
	}
	/**장바구니에 상품을 추가하는 메소드*/
	public void addProduct(Integer pnum, int oqty, ProductVO item){//DB에서 가져온것을 인수로 받을 ProductVO item
		//추가할 상품번호를 받고 수량이랑 item을 추가해준다.
		//1.새로 추가하려는 상품이 이미 장바구니(carList)에 있는지 여부를 체크 
		//=>(1)있다면 수량만 변경-누적해서 oqty더해주기
		if(item==null)return;
		for(ProductVO cartP:cartList){
			if(cartP.getPnum().equals(pnum)){
				//장바구니 상품의 수량을 가져와서 새로 추가하는 수량을 누적한다.
				int qty=cartP.getPqty()+oqty;//새로 정해진 수량
				cartP.setPqty(qty);
				return;
			}
		}
		//=>(2)없다면 cartList에 저장
		item.setPqty(oqty);//수량 설정
		cartList.add(item);//장바구니에 저장
	}
	/**장바구니에 담긴 상품의 총금액과 총포인트를 구해 HashMap에 저장하여 반환하는 메소드*/
	public Map<String,Integer> getCartTotal(){
		Map<String,Integer> map=new HashMap<String,Integer>();
		int cartTotalPrice=0, cartTotalPoint=0;//누적시켜줄 변수
		for(ProductVO pd:cartList){
			cartTotalPrice+=pd.getTotalPrice();/*수량 x 단가*/
			cartTotalPoint+=pd.getTotalPoint();/*수량 x 포인트*/
		}
		map.put("cartTotalPrice", cartTotalPrice);
		map.put("cartTotalPoint", cartTotalPoint);
		return map;
	}
	/**장바구니 비우기*/
	public void clearAll(){
		cartList.clear();//ArrayList를 비워주는 메소드를 호출
	}
	/**장바구니에서 pnum값으로 해당 상품 삭제하기*/
	public void removeProduct(Integer pnum) {
		for(ProductVO cartPd:cartList){
			if(cartPd.getPnum().equals(pnum)){
				cartList.remove(cartPd);
				break;//반복문벗어나기
			}
		}
	}
	/**장바구니 특정 상품의 수량을 수정하는 메소드*/
	public void setEdit(Integer pnum, int oqty) {
		for(ProductVO cartPd:cartList){
			if(cartPd.getPnum().equals(pnum)){
				//찾았다면
				if(oqty==0){//삭제처리
					cartList.remove(cartPd);
				}else if(oqty>0){//양수: 수량 수정
					cartPd.setPqty(oqty);
				}else{//음수: 예외 발생
					throw new NumberFormatException("수량은 양수여야 해요");
				}
				break;
			}
		}
	}
}














