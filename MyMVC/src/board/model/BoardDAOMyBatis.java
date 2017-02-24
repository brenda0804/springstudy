package board.model;

import org.apache.ibatis.io.*;
import org.apache.ibatis.session.*;
import java.util.*;
import java.io.*;

public class BoardDAOMyBatis {

	//� mapper�� ������� ����(���ӽ����̽� ���� �ʼ�)
	private final String NS="board.model.BoardMapper";
	private SqlSession ses;
	
	//�������丮�� ��� �޼ҵ� ����
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
	
	/**[�Խ��� ��� ����] �� �Խñ� �� ��������*/
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
			//List<BoardVO> arr=ses.selectList(NS+".listBoard");//paging ó���� ������ ���
			List<BoardVO> arr=ses.selectList(NS+".listBoardPaging",map);//pagingó���� �� ���
						//selectList�� 2�� ���ڱ����� ������ ������ 3���� �Ұ��Ͽ� start���� end���� hashmap���� �ڷᱸ���� �־ ������.
			return arr;
		}finally{
			close();
		}
	}//---------------------------
	private void close(){
		if(ses!=null) ses.close();
	}//---------------------------

	/**[�Խñ� ���� ���� ����] ��ȸ�� ����*/
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
	/**[�Խñ� ���� ���� ����]*/
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

	/**�� ��ȣ�� ÷������ ��������*/	
	public String selectFile(Integer idx) {
		try {
			ses=getSessionFactory().openSession();
			String filename=ses.selectOne(NS+".selectFile", idx);
			return filename;
		} finally {
			close();
		}

	}//---------------------------
	/**�۹�ȣ�� �� ���� ó��*/
	public int deleteBoard(Integer idx) {
		try{
			ses=this.getSessionFactory().openSession(true);
			//openSession()�޼ҵ忡 true�� �ѱ�� auto commit�� ��
			int n=ses.delete(NS+".deleteBoard", idx);
			return n;
		}finally{
			close();
		}
	}//---------------------------
	/**�۹�ȣ�� �� ���� ó��*/
	public int updateBoard(BoardVO board) {
		try{
		ses=this.getSessionFactory().openSession(true);//true�� �Է½� �ڵ� commit���ش�.
		int n=ses.update(NS+".updateBoard",board);
		return n;
		}finally{
			close();
		}
	}
	/**[12.05]�˻��� ���� �� �Խñ� �� ��������*/
	public int getFindTotalCount(String findType, String findString) {
		System.out.println(findType+"//"+findString);
		try{
			//board.model�� BoardMapper.xml DB������ �����ϴ� ���� ������ �ִ� ���� ���� 2���� ���� namespace�� key���� .���� ������ ��Ʈ��(id)�� �ʼ��̹Ƿ�
			//Map���� Ȱ���ؼ� findType, findString�� �ϳ��� ���ڷ� ������
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
	/**[12.05]�˻��� �Խñ� ������*/
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




