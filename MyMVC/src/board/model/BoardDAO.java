package board.model;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;

/*DBCPĿ�ؼ� Ǯ�� �̿��� con�� ������.*/

public class BoardDAO {
	private DataSource ds;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public BoardDAO(){
		try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/myoracle");
			System.out.println("DS��� ����");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//������----------------
	
	/**�Խ��� �۾��� ó�� �޼ҵ�*/
	public int insertBoard(BoardVO b) throws SQLException{
		try{
			con=ds.getConnection();
			StringBuilder sb=new StringBuilder()
					.append("INSERT INTO board VALUES(")
					.append(" board_idx_seq.nextval,?,?,?,?,?,?,")
					.append(" systimestamp,?,?,?,?,?,?)");
			String sql=sb.toString();
			System.out.println(sql);
			ps=con.prepareStatement(sql);
			ps.setString(1, b.getName());
			ps.setString(2, b.getEmail());
			ps.setString(3, b.getHomepage());
			ps.setString(4, b.getPwd());
			ps.setString(5, b.getSubject());
			ps.setString(6, b.getContent());
			ps.setInt(7, b.getReadnum());
			ps.setString(8, b.getFilename());
			ps.setLong(9, b.getFilesize());
			ps.setInt(10, b.getRefer());
			//�ܼ���(0��), �亯���� �� �ٸ�(�۱׷��ȣ�� ��)
			ps.setInt(11, b.getLev());
			ps.setInt(12, b.getSunbun());
			int n=ps.executeUpdate();
			return n;
		}finally{
			close();
		}
	}//----------------------------------
	/**[�Խñ� ��� ����] �� �Խù� �� ��������*/
	public int getTotalCount() 
	throws SQLException
	{
		try{
			con=ds.getConnection();
			String sql
			="SELECT count(idx) cnt FROM board";
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt("cnt");
			return count;
		}finally{
			close();
		}
	}//----------------------------------
	
	/**[�ܼ��� �Խ���] �� ��� ��������
	 * @throws SQLException */
	public List<BoardVO> listBoard() throws SQLException {
		try{
			con=ds.getConnection();
			StringBuilder sb
			=new StringBuilder("SELECT * FROM board ")
			.append(" ORDER BY idx DESC");
			String sql=sb.toString();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			List<BoardVO> arr=makeList(rs);
			return arr;
		}finally{
			close();
		}
	}//----------------------------------	
	private List<BoardVO> makeList(ResultSet rs) 
	throws SQLException
	{
		List<BoardVO> arr=new ArrayList<BoardVO>();
		while(rs.next()){
			int idx=rs.getInt("idx");
			String name=rs.getString("name");
			String email=rs.getString("email");
			String homepage=rs.getString("homepage");
			String pwd=rs.getString("pwd");
			String subject=rs.getString("subject");
			String content=rs.getString("content");
			
			java.sql.Timestamp wdate
						=rs.getTimestamp("wdate");
			int readnum=rs.getInt("readnum");
			String filename=rs.getString("filename");
			long filesize=rs.getLong("filesize");
			int refer=rs.getInt("refer");
			int lev=rs.getInt("lev");
			int sunbun=rs.getInt("sunbun");
			BoardVO board
			=new BoardVO(idx,name,email,homepage,pwd,
					subject,content,wdate,readnum,filename,
					filesize,refer,lev,sunbun);
			/////////////////
			arr.add(board);
			//////////////////
		}//while--------------
		return arr;
	}//----------------------------------
	
	/**�Խñ� ���뺸�� ����-��ȸ���� �����ϴ� �޼ҵ�*/
	public boolean updateReadnum(Integer idx) 
			throws SQLException
	{
		try {
			con=ds.getConnection();
			String sql
			="update board set readnum=readnum+1 where idx=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, idx);
			int n=ps.executeUpdate();
			boolean b=(n>0)? true: false;
			return b;
		} finally {
			close();
		}
	}//----------------------------------

	/**�Խñ� ���� ���� (�۹�ȣ-PK�� �� ���� ��������)*/
	public BoardVO viewBoard(Integer idx) 
	throws SQLException
	{
		try {
			con=ds.getConnection();
			String sql
			="SELECT * FROM board WHERE idx=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, idx);
			rs=ps.executeQuery();
			List<BoardVO> arr=makeList(rs);
			if(arr!=null && arr.size()==1){
				BoardVO board=arr.get(0);
				return board;
			}
			return null;
		} finally {
			close();
		}
		
	}//----------------------------------
	
	
	
	public void close(){
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
		} catch (Exception e) {

		}
	}//------------------------

	




	

}//////////////////////////////////////
