package board.model;

import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;
import java.util.*;
import java.io.*;

public class BoardDAOMyBatis {

	//어떤 mapper를 사용할지 정함(네임스페이스 지정 필수)
	private final String NS="board.model.BoardMapper";
	private SqlSession ses;
	
	//세션팩토리를 얻는 메소드 구성
	private SqlSessionFactory getSessionFactory(){
		String resource="config/mybatis-config.xml";
		InputStream is=null;
		try {
			is=Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlSessionFactory fac
		=new SqlSessionFactoryBuilder().build(is); 
		return fac;
	}//----------------------------------
	
	/**[게시판 목록 관련] 총 게시글 수 가져오기*/
	public int getTotalCount(){
		ses=this.getSessionFactory().openSession();
		int count=ses.selectOne(NS+".totalCount");
		if(ses!=null) ses.close();
		return count;
	}//----------------------------------

	public int insertBoard(BoardVO board) {
		ses=this.getSessionFactory().openSession();
		try{
			int n=ses.insert(NS+".insertBoard", board);
			if(n>0){
				ses.commit();
			}else{
				ses.rollback();
			}
			return n;
		}finally{
			close();
		}
	}//----------------------
	
	public List<BoardVO> listBoard(int start,int end) {
		try{
			ses=this.getSessionFactory().openSession();
			Map<String,Integer>map=new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			//List<BoardVO> arr=ses.selectList(NS+".listBoard");//paging 처리를 안했을 경우
			List<BoardVO> arr=ses.selectList(NS+".listBoardPaging",map);//paging처리를 할 경우
						//selectList가 2개 인자까지는 받을수 있으나 3개는 불가하여 start값과 end값을 hashmap등의 자료구조에 넣어서 보낸다.
			return arr;
		}finally{
			close();
		}
	}//---------------------------
	private void close(){
		if(ses!=null) ses.close();
	}//---------------------------

	/**[게시글 내용 보기 관련] 조회수 증가*/
	public boolean updateReadnum(Integer idx) {
		try{
			ses=this.getSessionFactory().openSession();
			int n=ses.update(NS+".updateReadnum", idx);
			if(n>0){
				ses.commit();
				return true;
			}else{
				ses.rollback();
				return false;
			}
		}finally{
			close();
		}
	}//---------------------------
	/**[게시글 내용 보기 관련]*/
	public BoardVO viewBoard(Integer idx) {
		try{
			ses=this.getSessionFactory().openSession();
			BoardVO vo=ses.selectOne(NS+".selectBoard", idx);
			return vo;		
		}finally{
			close();
		}
	}//---------------------------

	public String selectPwd(Integer idx) {
		try{
			ses=this.getSessionFactory().openSession();
			String dbPwd=ses.selectOne(NS+".selectPwd", idx);
			return dbPwd;
		}finally{
			close();
		}
	}//---------------------------

	/**글 번호로 첨부파일 가져오기*/	
	public String selectFile(Integer idx) {
		try {
			ses=getSessionFactory().openSession();
			String filename=ses.selectOne(NS+".selectFile", idx);
			return filename;
		} finally {
			close();
		}

	}//---------------------------
	/**글번호로 글 삭제 처리*/
	public int deleteBoard(Integer idx) {
		try{
			ses=this.getSessionFactory().openSession(true);
			//openSession()메소드에 true를 넘기면 auto commit이 됨
			int n=ses.delete(NS+".deleteBoard", idx);
			return n;
		}finally{
			close();
		}
	}//---------------------------
	/**글번호로 글 수정 처리*/
	public int updateBoard(BoardVO board) {
		try{
		ses=this.getSessionFactory().openSession(true);//true값 입력시 자동 commit해준다.
		int n=ses.update(NS+".updateBoard",board);
		return n;
		}finally{
			close();
		}
	}
	/**[12.05]검색한 글의 총 게시글 수 가져오기*/
	public int getFindTotalCount(String findType, String findString) {
		System.out.println(findType+"//"+findString);
		try{
			//board.model의 BoardMapper.xml DB쿼리를 수행하는 곳에 보낼수 있는 인자 수가 2개로 한정 namespace와 key값을 .으로 연결한 스트링(id)이 필수이므로
			//Map구조 활용해서 findType, findString를 하나의 인자로 보내기
			Map<String,String> map=new HashMap<String,String>();
			map.put("findType", findType);
			map.put("findString",findString);
			ses=this.getSessionFactory().openSession(true);
			int count=ses.selectOne(NS+".findTotalCount",map);
			return count;			
		}finally{
			close();
		}
	}
	/**[12.05]검색한 게시글 져오기*/
	public List<BoardVO> findListBoard(int start, int end, String findType, String findString) {
		try{
			Map<String,String> map=new HashMap<String,String>();
			map.put("findType", findType);
			map.put("findString",findString);
			map.put("start", String.valueOf(start));
			map.put("end", String.valueOf(end));
			ses=getSessionFactory().openSession(true);
			List<BoardVO> arr=ses.selectList(NS+".findListBoard",map);
			return arr;
		}finally{
			close();
		}
	}

	
	
}////////////////////////////////////




