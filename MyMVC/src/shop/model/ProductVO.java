package shop.model;

import java.io.Serializable;
import java.sql.Date;

public class ProductVO implements Serializable{
	private Integer pnum;
	private String pname;
	private String pcategory_fk;
	private String pcompany;
	private String pimage1;
	private String pimage2;
	private String pimage3;
	
	private int pqty;
	private int price;
	private int saleprice;
	private String pspec;
	private String pcontents;
	private int point;
	private java.sql.Date pindate;
	
	private int totalPrice;//총 판매가=판매가*수량
	private int totalPoint;//총 포인트=포인트*수량
	
	//주문처리시 필요한 프로퍼티: 주문번호
	private String onum;
	
	public ProductVO(){
		
	}

	public ProductVO(Integer pnum, String pname, String pcategory_fk, String pcompany, String pimage1, String pimage2,
			String pimage3, int pqty, int price, int saleprice, String pspec, String pcontents, int point,
			Date pindate) {
		super();
		this.pnum = pnum;
		this.pname = pname;
		this.pcategory_fk = pcategory_fk;
		this.pcompany = pcompany;
		this.pimage1 = pimage1;
		this.pimage2 = pimage2;
		this.pimage3 = pimage3;
		this.pqty = pqty;
		this.price = price;
		this.saleprice = saleprice;
		this.pspec = pspec;
		this.pcontents = pcontents;
		this.point = point;
		this.pindate = pindate;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPcategory_fk() {
		return pcategory_fk;
	}

	public void setPcategory_fk(String pcategory_fk) {
		this.pcategory_fk = pcategory_fk;
	}

	public String getPcompany() {
		return pcompany;
	}

	public void setPcompany(String pcompany) {
		this.pcompany = pcompany;
	}

	public String getPimage1() {
		return pimage1;
	}

	public void setPimage1(String pimage1) {
		this.pimage1 = pimage1;
	}

	public String getPimage2() {
		return pimage2;
	}

	public void setPimage2(String pimage2) {
		this.pimage2 = pimage2;
	}

	public String getPimage3() {
		return pimage3;
	}

	public void setPimage3(String pimage3) {
		this.pimage3 = pimage3;
	}

	public int getPqty() {
		return pqty;
	}
	//수정:수량이 결정되면 총 판매가와 총 포인트 점수가 결정된다.
	public void setPqty(int pqty) {
		this.pqty = pqty;
		//--------------------
		this.totalPrice=this.saleprice*this.pqty;
		this.totalPoint=this.point*this.pqty;
		//--------------------
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public String getPspec() {
		return pspec;
	}

	public void setPspec(String pspec) {
		this.pspec = pspec;
	}

	public String getPcontents() {
		return pcontents;
	}

	public void setPcontents(String pcontents) {
		this.pcontents = pcontents;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public java.sql.Date getPindate() {
		return pindate;
	}

	public void setPindate(java.sql.Date pindate) {
		this.pindate = pindate;
	}

	public String getOnum() {
		return onum;
	}

	public void setOnum(String onum) {
		this.onum = onum;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	/**할인률을 반환하는 메소드*/
	public int getPercent(){
		/*(정가-판매가)*100/정가*/
		return (price-saleprice)*100/price;
	}
	
	
	@Override
	public String toString() {
		return "ProductVO [pnum=" + pnum + ", pname=" + pname + ", pcategory_fk=" + pcategory_fk + ", pcompany="
				+ pcompany + ", pimage1=" + pimage1 + ", pimage2=" + pimage2 + ", pimage3=" + pimage3 + ", pqty=" + pqty
				+ ", price=" + price + ", saleprice=" + saleprice + ", pspec=" + pspec + ", pcontents=" + pcontents
				+ ", point=" + point + ", pindate=" + pindate + ", totalPrice=" + totalPrice + ", totalPoint="
				+ totalPoint + ", onum=" + onum + "]";
	}
	
}

