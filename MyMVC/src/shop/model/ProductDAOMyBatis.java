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
	//세션팩토리를 얻는 메소드 구성
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
	/**카테고리 목록 가져오기*/
	public List<CategoryVO> getCategory(){
		try {
			ses=factory.openSession(true);
			List<CategoryVO> arr=ses.selectList(NS+".categoryList");
			return arr;
		} finally {
			close();
		}
	}
	
	/**Pspec별로(HIT,NEW,BEST..)상품정보를 가져오는 메소드*/
	public List<ProductVO> selectByPspec(String pspec){//세션 얻어와주는 것만 해주면된다.
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
	/**상품번호(Pnum) 상품정보 가져오기*/
	public ProductVO selectByPnum(Integer pnum) {
		try{
			ses=factory.openSession(true);
			ProductVO prod=ses.selectOne(NS+".selectByPnum",pnum);
			return prod;
		}finally{
			close();
		}
		
	}
	/**상품이름(Pname) 상품정보 가져오기*/
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











