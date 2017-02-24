package shop.model;

import java.util.*;//�ڷᱸ��
import java.io.*;

/**��ٱ���(ArrayList)�� ��ǰ�� �߰�, ����, �����ϴ� ������ ���� ���� ����*/

public class CartBean implements Serializable {
	private List<ProductVO> cartList=new ArrayList<ProductVO>();//��ٱ���
	
	//cartList getter ����
	public List<ProductVO> getCartList() {
		return cartList;
	}
	/**��ٱ��Ͽ� ��ǰ�� �߰��ϴ� �޼ҵ�*/
	public void addProduct(Integer pnum, int oqty, ProductVO item){//DB���� �����°��� �μ��� ���� ProductVO item
		//�߰��� ��ǰ��ȣ�� �ް� �����̶� item�� �߰����ش�.
		//1.���� �߰��Ϸ��� ��ǰ�� �̹� ��ٱ���(carList)�� �ִ��� ���θ� üũ 
		//=>(1)�ִٸ� ������ ����-�����ؼ� oqty�����ֱ�
		if(item==null)return;
		for(ProductVO cartP:cartList){
			if(cartP.getPnum().equals(pnum)){
				//��ٱ��� ��ǰ�� ������ �����ͼ� ���� �߰��ϴ� ������ �����Ѵ�.
				int qty=cartP.getPqty()+oqty;//���� ������ ����
				cartP.setPqty(qty);
				return;
			}
		}
		//=>(2)���ٸ� cartList�� ����
		item.setPqty(oqty);//���� ����
		cartList.add(item);//��ٱ��Ͽ� ����
	}
	/**��ٱ��Ͽ� ��� ��ǰ�� �ѱݾװ� ������Ʈ�� ���� HashMap�� �����Ͽ� ��ȯ�ϴ� �޼ҵ�*/
	public Map<String,Integer> getCartTotal(){
		Map<String,Integer> map=new HashMap<String,Integer>();
		int cartTotalPrice=0, cartTotalPoint=0;//���������� ����
		for(ProductVO pd:cartList){
			cartTotalPrice+=pd.getTotalPrice();/*���� x �ܰ�*/
			cartTotalPoint+=pd.getTotalPoint();/*���� x ����Ʈ*/
		}
		map.put("cartTotalPrice", cartTotalPrice);
		map.put("cartTotalPoint", cartTotalPoint);
		return map;
	}
	/**��ٱ��� ����*/
	public void clearAll(){
		cartList.clear();//ArrayList�� ����ִ� �޼ҵ带 ȣ��
	}
	/**��ٱ��Ͽ��� pnum������ �ش� ��ǰ �����ϱ�*/
	public void removeProduct(Integer pnum) {
		for(ProductVO cartPd:cartList){
			if(cartPd.getPnum().equals(pnum)){
				cartList.remove(cartPd);
				break;//�ݺ��������
			}
		}
	}
	/**��ٱ��� Ư�� ��ǰ�� ������ �����ϴ� �޼ҵ�*/
	public void setEdit(Integer pnum, int oqty) {
		for(ProductVO cartPd:cartList){
			if(cartPd.getPnum().equals(pnum)){
				//ã�Ҵٸ�
				if(oqty==0){//����ó��
					cartList.remove(cartPd);
				}else if(oqty>0){//���: ���� ����
					cartPd.setPqty(oqty);
				}else{//����: ���� �߻�
					throw new NumberFormatException("������ ������� �ؿ�");
				}
				break;
			}
		}
	}
}














