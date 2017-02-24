package user.member.model;
import java.io.*;
import java.sql.Date;

public class MemberVO implements Serializable{
	//멤버변수(인스턴스 변수)=> JSP에서는 프로퍼티(property)라고 부른다.
	//                  프로퍼티들은 가능하면 html form의 input name과 일치시켜 주는 것이 좋다.
	
	private Integer idx;
	private String name;
	private String userid;
	private String pwd;
	private String email;
	private String hp1,hp2,hp3;
	private String post;
	private String addr1,addr2;
	private java.sql.Date indate;
	private int mileage;
	private int mstate;
	

	public MemberVO() {
		System.out.println("MemberVO()생성자");
		// 기본 생성자 반드시 필요
		// 기본 생성자가 없으면 jsp 에서 userBean 액션으로 객체 생성을 할 수 없다.
	}
	//생성자 오버로드

	public MemberVO(Integer idx, String name, String userid, String pwd, String email, String hp1, String hp2,
			String hp3, String post, String addr1, String addr2, Date indate, int mileage, int mstate) {
		super();
		this.idx = idx;
		this.name = name;
		this.userid = userid;
		this.pwd = pwd;
		this.email = email;
		this.hp1 = hp1;
		this.hp2 = hp2;
		this.hp3 = hp3;
		this.post = post;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.indate = indate;
		this.mileage = mileage;
		this.mstate = mstate;
	}

	//setter, getter-----------------------
	
	public Integer getIdx() {
		return idx;
	}


	public void setIdx(Integer idx) {
		this.idx = idx;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getHp1() {
		return hp1;
	}


	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}


	public String getHp2() {
		return hp2;
	}


	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}


	public String getHp3() {
		return hp3;
	}


	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}


	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}


	public String getAddr1() {
		return addr1;
	}


	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}


	public String getAddr2() {
		return addr2;
	}


	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}


	public java.sql.Date getIndate() {
		return indate;
	}


	public void setIndate(java.sql.Date indate) {
		this.indate = indate;
	}


	public int getMileage() {
		return mileage;
	}


	public void setMileage(int mileage) {
		this.mileage = mileage;
	}


	public int getMstate() {
		return mstate;
	}


	public void setMstate(int mstate) {
		this.mstate = mstate;
	}
	
	public String getAllHp(){
		return hp1+"-"+hp2+"-"+hp3;
	}
	public String getAllAddr(){
		return addr1+" "+addr2;
	}
	//toString() 오버라이드

	@Override
	public String toString() {
		return "memberVO [idx=" + idx + ", name=" + name + ", userid=" + userid + ", pwd=" + pwd + ", email=" + email
				+ ", hp1=" + hp1 + ", hp2=" + hp2 + ", hp3=" + hp3 + ", post=" + post + ", addr1=" + addr1 + ", addr2="
				+ addr2 + ", indate=" + indate + ", mileage=" + mileage + ", mstate=" + mstate + "]";
	}
	
	

	
	
	
	
	
	
	
	
	

	
}//////////////////////////////////////////////////////////////
