package shop.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ProductDAOMyBatis {
	private final String NS="shop.model.ShopMapper";
	private static SqlSessionFactory factory;
	private SqlSession ses;
	//�������丮�� ��� �޼ҵ� ����
	static{
		String resource="config/mybatis-config.xml";
		InputStream is=null;
		try {
			is=Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		factory
		=new SqlSessionFactoryBuilder().build(is); 
	}//----------------------------------
	/**ī�װ� ��� ��������*/
	public List<CategoryVO> getCategory(){
		try {
			ses=factory.openSession(true);
			List<CategoryVO> arr=ses.selectList(NS+".categoryList");
			return arr;
		} finally {
			close();
		}
	}
	
	/**Pspec����(HIT,NEW,BEST..)��ǰ������ �������� �޼ҵ�*/
	public List<ProductVO> selectByPspec(String pspec){//���� �����ִ� �͸� ���ָ�ȴ�.
		try{
			ses=factory.openSession(true);
			List<ProductVO> arr=ses.selectList(NS+".selectByPspec",pspec.toUpperCase());
			return arr;
		}finally{
			close();
		}
	}
	
	private void close(){
		if(ses!=null) ses.close();
	}

	public List<ProductVO> selectByCategory(String code) {
		try{
			ses=factory.openSession(true);
			List<ProductVO> arr=ses.selectList(NS+".selectByCategory",code);
			return arr;
		}
		finally{
			close();
		}
	}
	/**��ǰ��ȣ(Pnum) ��ǰ���� ��������*/
	public ProductVO selectByPnum(Integer pnum) {
		try{
			ses=factory.openSession(true);
			ProductVO prod=ses.selectOne(NS+".selectByPnum",pnum);
			return prod;
		}finally{
			close();
		}
		
	}
	/**��ǰ�̸�(Pname) ��ǰ���� ��������*/
	public List<ProductVO> selectByPname(String pname) {
		try{
			ses=factory.openSession(true);
			List<ProductVO> prod=ses.selectList(NS+".selectByPname",pname);
			return prod;
		}finally{
			close();
		}
		
	}
}











