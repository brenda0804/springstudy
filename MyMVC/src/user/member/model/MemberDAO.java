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
		//System.out.println("MemberDAO()생성자");
		try {
			Context ctx=new InitialContext();
			//WAS서버 --> resource룩업
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/myoracle");//"톰켓 과 리소스 한번에 찾는다 env/까지가 톰켓"
			System.out.println("DataSource룩업성공!");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}//-------------
	public void dbClose(){
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
			//DBCP는 con.close() 경우 커넥션을 풀에 반납한다.
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}//-------------
	
	
	/*아이디의 사용가능 여부를 체크하는 메소드*/
	public boolean idCheck(String userid)throws SQLException{
		try{
			//con=DBUtil.getCon(); connection pool을 했으면 이렇게 접근하면 안됨./// 이제 DBUtil안씀
			con=ds.getConnection();
			String sql="SELECT idx FROM member WHERE userid=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, userid);
			rs=ps.executeQuery();
			//rs의 커서 이동
			boolean isRs=rs.next();
			return !isRs;
			
			/*if(isRs){
				//해당아이디가 member에 있는경우
				return false;
			}else{
				//없는경우
				return true;
				
			}*/
			
		}finally{
			//DBUtil.close(rs, ps, con);  이제 이렇게 클로즈안함
			dbClose();
		}
	}
	
	
	//**회원가입처리 메소드/////////*/  
	public int insertMember(MemberVO user)	throws SQLException{
		
		try{
			con=ds.getConnection();
			String sql="INSERT INTO MEMBER(IDX, NAME,USERID,PWD,EMAIL,HP1,HP2,HP3,POST,ADDR1,ADDR2)"
					+ "VALUES(MEMBER_IDX_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			//?값셋팅
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
			//실행 executeXXX()
			int n = ps.executeUpdate();
			return n;
			
		}finally{
			//DBUtil.close(rs,ps,con); 이제 이렇게 안씀 connection pool 이후
			dbClose(); //이렇게만 해주면 된다.
		}
	}
	/*전체 회원정보를 가져오는 메소드*/ //ArrayList 백터랑 같은데 웹에서는 얘를 씀.. 백터보다 빠르다.
	public ArrayList<MemberVO> getAllMembers()throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="SELECT * FROM member ORDER BY idx DESC";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			return arr;
		}finally{//finally는 return이 온다한들 한번실행하고 끝냄
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
		
		
	}//----------------
	/*회원번호(PK)로 회원 정보를 가져오는 메소드*/
	public MemberVO selectByIdx(Integer idx)throws SQLException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="SELECT * FROM member WHERE idx=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, idx);
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			//회원정보가 있다면 => 레코드는 하나
			if(arr!=null && arr.size()==1){
				MemberVO user=arr.get(0);
				return user;
			}
			//회원정보가 없다면 
			return null;
			
		}finally{//finally는 return이 온다한들 한번실행하고 끝냄
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
	}//--------------------------------------------------
	/*회원정보를 수정하는 메소드*/
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
	/*회원정보를 검색하는 메소드*/
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
			ps.setString(1, "%"+fstr+"%"); //setString에서는 ',' 요거 뒤에 자동으로 홑따옴표 붙이는 효과 때문에 ㅈ따로 안붙여도 됨
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
	
	/*회원정보 삭제 처리 메소드*/
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
	/*회원 아이디와 비번으로 로그인 여부를 체크하는 메소드*/
	public MemberVO loginCheck(String userid, String pwd)throws SQLException,NotMemberException{
		MemberVO loginUser=selectByUserid(userid);
		if(loginUser!=null){
			//아이디가 db에 존재 한다면=>비번 일치여부를 체크
			String dbPwd=loginUser.getPwd();
			if(!dbPwd.equals(pwd)){
				throw new NotMemberException("비밀번호가 일치하지 않아요!");
			}
			return loginUser;//비번이 일치하면 회원정보 반환
		}
		return null;
	}//------------------------------
	/*아이디(UK)로 회원 정보를 가져오는 메소드*/
	public MemberVO selectByUserid(String userid) throws SQLException, NotMemberException{
		try{
			//con=DBUtil.getCon();
			con=ds.getConnection();
			String sql="SELECT * FROM member WHERE userid=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, userid);
			rs=ps.executeQuery();
			ArrayList<MemberVO> arr=makeList(rs);
			//아이디가 Unique 제약조건을 주었기 때문에 있다면, 레코드는 하나
			if(arr!=null && arr.size()==1){
				MemberVO user=arr.get(0);
				return user;
			}else{
				//해당 아이디가 없는 경우=> 사용자 정의 예외 발생
				throw new NotMemberException(userid+"는 존재하지 않아요");
				//NotMemberException 예외 만들거임, 사용자정의는 throw new로시작하고 위에다 던저줘야함
			}
			
		}finally{
			//DBUtil.close(rs, ps, con);
			dbClose();
		}
	}
	
	
}










