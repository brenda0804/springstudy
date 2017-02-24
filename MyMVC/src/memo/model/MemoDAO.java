package memo.model;
import java.sql.*;
import java.util.Vector;
/* DAO : Data Access Object
: 데이터베이스에 접근하여 biz로직 (insert, delete, udate, select 등)
  을 수행하는 클래스

CRUD 작업 수행========>biz logic
c : create=> insert문
r : read  => select문
u : update=> update문
d : delete => delete문
*/
public class MemoDAO {
    private String url="jdbc:oracle:thin:@192.168.10.150:1521:ORCL";
    private String user="james", pwd="tiger";
    private Connection con;
    private PreparedStatement ps;
    
    public MemoDAO(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con=DriverManager.getConnection(url,user,pwd);
            System.out.println("db connected...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//생성자-----------------------
    public boolean insertMemo2(MemoDTO memo){
    	try {
    		String sql="{call MEMO_ADD(?,?)}";
    		CallableStatement cs=con.prepareCall(sql);
    		cs.setString(1,memo.getName());
    		cs.setString(2, memo.getMsg());
    		cs.execute();
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}    	
    }//---------------------------
    
    /**메모 내용을 등록하는 메소드=>insert문을 수행*/
    public boolean insertMemo(MemoDTO memo){
        try {
            //1. INSERT문을 작성
            String sql="INSERT INTO memo(idx, name, msg, wdate)";
            sql+=" VALUES(memo_idx_seq.nextval,?,?, sysdate)";
            //2. ps 얻어오기
            ps=con.prepareStatement(sql);
            ps.setString(1, memo.getName());
            ps.setString(2,memo.getMsg());
            //3. db에 전송=> executeXXX()
            int cnt=ps.executeUpdate();
            //4. 실행결과 성공이면 return true, 실패면 false반환
            boolean bool=(cnt>0)? true:false;
            if(ps!=null) ps.close();
            return bool;
            
        } catch (Exception e) {
            e.printStackTrace();
             return false;
        }
    }
    
    /**글번호(pk)로 특정 글을 가져오는 메소드*/
    public MemoDTO selectMemoByIdx(int idx){
        try {
            //select문 where절
            String sql="SELECT idx,name,msg,wdate FROM memo WHERE idx=?";
            ps=con.prepareStatement(sql);
            //? 셋팅
            ps.setInt(1, idx);
            //실행=> rs
            ResultSet rs=ps.executeQuery();
            MemoDTO m=null;
            while(rs.next()){
                int idx2=rs.getInt(1);
                String name=rs.getString(2);
                String msg=rs.getString(3);
                java.sql.Date wdate=rs.getDate(4);
                m=new MemoDTO(idx2,name,msg,wdate);                            
            }
            return m;              
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
       
    
    /**모든 메모 목록을 가져오는 메소드 - select문을 수행*/
    public Vector<MemoDTO> selectAllMemo(){
        try {
            //1.  SELECT문 작성 ORDER BY IDX DESC
            String sql="SELECT idx,name,rpad(msg,50,' ') msg,wdate FROM memo ORDER BY 1 DESC";            
            //2. PS 얻어오기
            ps=con.prepareStatement(sql);
            
            //3. EXECUTExxx()메소드로 RS받기
            ResultSet rs=ps.executeQuery();
            
            //4. 반복문 돌면서 컬럼데이터 꺼내기
            //=> MemoDTO를 생성해서 데이터를 setting
            Vector<MemoDTO> v=makeVector(rs);
           
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//----------------------------------
    
    
    
    /**글번호(PK)로 특정 글을 삭제하는 메소드-delete문 수행*/
    public boolean deleteMemo(int idx)
    {
        try {
            //1. sql문 작성
            String sql="DELETE FROM memo WHERE idx=?";
            //2. ps 얻기
            ps=con.prepareStatement(sql);
            
            //3. ? 값 셋팅
            ps.setInt(1, idx);
            
            //4. 실행
            int cnt=ps.executeUpdate();
            if(ps!=null) ps.close();
            
            //5. 그 결과 반환
            if(cnt>0) return true;
            else return false;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }       
    }//----------------------------------
    
    /** 특정 글을 수정하는 메소드 update문을 수행한다.*/
    public boolean updateMemo(MemoDTO memo){
        try {
            //update문 작성 where절
            String sql="UPDATE memo SET name=?, msg=?, wdate=sysdate";
                sql+=" WHERE idx=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,memo.getName());
            ps.setString(2, memo.getMsg());
            ps.setInt(3, memo.getIdx());
            int cnt=ps.executeUpdate();
            if(cnt>0) return true;
            else  return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }//----------------------------------

    /** 검색어로 메모 내용을 검색하는 메소드*/
    public Vector<MemoDTO> findMemo(int index, String keyword) {
        try {
            //0 => 작성자(name), 1=>메모내용(msg)  2=> 글번호=> idx
            String colName="";
            switch(index){
                case 0: colName="name"; break;
                case 1: colName="msg";break;
                case 2: colName="idx"; break;
            }//switch----------
            //SELECT문 작성 WHERE절  = 
            //String sql="SELECT idx,name,msg,wdate FROM memo WHERE "+colName+"=?";
            //SELECT문  WHERE절에 LIKE 연산자 사용해보세요.
            String sql="SELECT * FROM memo WHERE "+colName+" LIKE ?";
            
            System.out.println("sql="+sql);
            //PS얻어오기
            ps=con.prepareStatement(sql);
            ps.setString(1, "%"+keyword+"%");                        
            //실행해서 RS받기
            ResultSet rs=ps.executeQuery();
            //반복문 돌면서 데이터꺼내와 MEMODTO에 담기
            //vECTOR에 저장후 반환
            Vector<MemoDTO> v=makeVector(rs);
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//----------------------------------

    private Vector<MemoDTO> makeVector(ResultSet rs) 
    throws SQLException
    {
         Vector<MemoDTO> v=new Vector<MemoDTO>();
            while(rs.next()){
                int idx=rs.getInt("idx");
                String name=rs.getString("name");
                String msg=rs.getString("msg");
                java.sql.Date wdate=rs.getDate("wdate");
                MemoDTO record=new MemoDTO(idx,name,msg,wdate);
                v.add(record);
            }//while-------------
            return v;
    }//------------------------------------

    public void dbClose() {
        try{
            System.out.println("db disconnect...");
            if(ps!=null) ps.close();
            if(con!=null) con.close();
        }catch(SQLException e){
            
        }
        
    }//--------------------------
    
}//////////////////////////////////////////////////







