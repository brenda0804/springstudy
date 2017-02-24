package user.member.model;    

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.*;

//DAO : Data Access Object
public class MemberDAO {

	private DataSource ds;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public MemberDAO() {
		//System.out.println("MemberDAO()������");
		try {
			Context ctx=new InitialContext();
			//WAS���� --> resource���
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/myoracle");//"���� �� ���ҽ� �ѹ��� ã�´� env/������ ����"
			System.out.println("DataSource�������!");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}//-------------
	public void dbClose(){
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
			//DBCP�� con.close() ��� Ŀ�ؼ��� Ǯ�� �ݳ��Ѵ�.
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}//-------------
	
	
	/*���̵��� ��밡�� ���θ� üũ�ϴ� �޼ҵ�*/
	public boolean idCheck(String userid)throws SQLException{
		try{
			//con=DBUtil.getCon(); connection pool�� ������ �̷��� �����ϸ� �ȵ�./// ���� DBUtil�Ⱦ�
			con=ds.getConnection();
			String sql="SELECT idx FROM member WHERE userid=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, userid);
			rs=ps.executeQuery();
			//rs�� Ŀ�� �̵�
			boolean isRs=rs.next();
			return !isRs;
			
			/*if(isRs){
				//�ش���̵� member�� �ִ°��
				return false;
			}else{
				//���°��
				return true;
				
			}*/
			
		}finally{
			//DBUtil.close(rs, ps, con);  ���� �̷��� Ŭ�������
			dbClose();
		}
	}
	
	
	//**ȸ������ó�� �޼ҵ�/////////*/  
	public int insertMember(MemberVO user)	throws SQLException{
		
		try{
			con=ds.getConnection();
			String sql="INSERT INTO MEMBER(IDX, NAME,USERID,PWD,EMAIL,HP1,HP2,HP3,POST,ADDR1,ADDR2)"
					+ "VALUES(MEMBER_IDX_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			//?������
			ps.setString(1, user.getName());
			ps.setString(2, user.getUserid());
			ps.setString(3, user.getPwd());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getHp1());
			ps.setString(6, user.getHp2());
			ps.setString(7, user.getHp3());
			ps.setString(8, user.getPost());
			ps.setString(9, user.getAddr1());
			ps.setString(10, user.getAddr2());
			//���� executeXXX()
			int n = ps.executeUpdate();
			return n;
			
		}finally{
			//DBUtil.close(rs,ps,con); ���� �̷��� �Ⱦ� connection pool ����
			dbClose(); //�̷��Ը� ���ָ� �ȴ�.
		}
	}
	/*��ü ȸ�������� �������� �޼ҵ�*/ //ArrayList ���Ͷ� ������ �������� �긦 ��.. ���ͺ��� ������.
	public ArrayList<MemberVO> getAllMembers()throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="SELECT * FROM member ORDER BY idx DESC";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			return arr;
		}finally{//finally�� return�� �´��ѵ� �ѹ������ϰ� ����
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
		
		
	}//----------------
	/*ȸ����ȣ(PK)�� ȸ�� ������ �������� �޼ҵ�*/
	public MemberVO selectByIdx(Integer idx)throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="SELECT * FROM member WHERE idx=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, idx);
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			//ȸ�������� �ִٸ� => ���ڵ�� �ϳ�
			if(arr!=null && arr.size()==1){
				MemberVO user=arr.get(0);
				return user;
			}
			//ȸ�������� ���ٸ� 
			return null;
			
		}finally{//finally�� return�� �´��ѵ� �ѹ������ϰ� ����
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
	}//--------------------------------------------------
	/*ȸ�������� �����ϴ� �޼ҵ�*/
	public int updateMember(MemberVO user)throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="UPDATE member SET name=?, userid=?, email=?, hp1=?, hp2=?, hp3=?, post=?, addr1=?, addr2=?, mstate=? WHERE idx=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getUserid());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getHp1());
			ps.setString(5, user.getHp2());
			ps.setString(6, user.getHp3());
			ps.setString(7, user.getPost());
			ps.setString(8, user.getAddr1());
			ps.setString(9, user.getAddr2());
			ps.setInt(10, user.getMstate());
			ps.setInt(11, user.getIdx());
			int n= ps.executeUpdate();
			return n;
			
		}finally{
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
	}
	/*ȸ�������� �˻��ϴ� �޼ҵ�*/
	public ArrayList<MemberVO> findMember(String ftype,String fstr)throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String colName="";
			switch(ftype){
			case "1": colName="name"; break;
			case "2": colName="userid"; break;
			case "3": colName="addr1"; break;
			case "4": colName="email"; break;
			default: colName="name";
			}
			String sql="SELECT * FROM member WHERE "+colName+" LIKE ?";
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, "%"+fstr+"%"); //setString������ ',' ��� �ڿ� �ڵ����� Ȭ����ǥ ���̴� ȿ�� ������ ������ �Ⱥٿ��� ��
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			return arr;
			
		}finally{
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
	}
		
	
	
	
	
	private ArrayList<MemberVO> makeList(ResultSet rs2) throws SQLException {
		ArrayList<MemberVO> arr=new ArrayList<MemberVO>();
		while(rs.next()){
			int idx=rs.getInt("idx");
			String name=rs.getString("name");
			String userid=rs.getString("userid");
			String pwd=rs.getString("pwd");
			String email=rs.getString("email");
			String hp1=rs.getString("hp1");
			String hp2=rs.getString("hp2");
			String hp3=rs.getString("hp3");
			String post=rs.getString("post");
			String addr1=rs.getString("addr1");
			String addr2=rs.getString("addr2");
			java.sql.Date indate=rs.getDate("indate");
			int mileage=rs.getInt("mileage");
			int mstate=rs.getInt("mstate");
			MemberVO user=new MemberVO(idx, name,userid, pwd,email,hp1,hp2,hp3,post,addr1,addr2,indate,mileage,mstate);
			/////
			arr.add(user);
			/////
			
		}////while---------
		return arr;
		
	}//---------------------
	
	/*ȸ������ ���� ó�� �޼ҵ�*/
	public int deleteMember(Integer idx) throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="DELETE FROM member WHERE idx=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, idx);
			int n=ps.executeUpdate();
			return n;
			
		}finally{
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
		
		
	}
	/*ȸ�� ���̵�� ������� �α��� ���θ� üũ�ϴ� �޼ҵ�*/
	public MemberVO loginCheck(String userid, String pwd)throws SQLException,NotMemberException{
		MemberVO loginUser=selectByUserid(userid);
		if(loginUser!=null){
			//���̵� db�� ���� �Ѵٸ�=>��� ��ġ���θ� üũ
			String dbPwd=loginUser.getPwd();
			if(!dbPwd.equals(pwd)){
				throw new NotMemberException("��й�ȣ�� ��ġ���� �ʾƿ�!");
			}
			return loginUser;//����� ��ġ�ϸ� ȸ������ ��ȯ
		}
		return null;
	}//------------------------------
	/*���̵�(UK)�� ȸ�� ������ �������� �޼ҵ�*/
	public MemberVO selectByUserid(String userid) throws SQLException, NotMemberException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="SELECT * FROM member WHERE userid=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, userid);
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			//���̵� Unique ���������� �־��� ������ �ִٸ�, ���ڵ�� �ϳ�
			if(arr!=null && arr.size()==1){
				MemberVO user=arr.get(0);
				return user;
			}else{
				//�ش� ���̵� ���� ���=> ����� ���� ���� �߻�
				throw new NotMemberException(userid+"�� �������� �ʾƿ�");
				//NotMemberException ���� �������, ��������Ǵ� throw new�ν����ϰ� ������ ���������
			}
			
		}finally{
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
	}
	
	
}









