package common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import memo.controller.MemoDeleteAction;
import memo.controller.MemoEditAction;
import memo.controller.MemoEditEndAction;
import memo.controller.MemoInsertAction;
import memo.controller.MemoListAction;
import memo.controller.MemoWriteAction;

@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "config", 
							  value = "C:/MyJava/Workspace/MyMVC/WebContent/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,Object> cmdMap
		= new HashMap<String,Object>();
	@Override
	public void init(ServletConfig sc) throws ServletException{
		/*init():���긴����� ���� ��û�� ���� �� �� �ѹ��� ȣ��Ǵ� �޼ҵ�
		 * ���⼭ init-param�� ����Ǿ� �ִ� config�� �ش��ϴ� value��(Command.properties�� ������)�� ������.
		 * ������̼ǿ� ��õǾ�����*/
		String value=sc.getInitParameter("config");//������
		Properties props = new Properties();
		try{
			props.load(new FileInputStream(value));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//Properties ��ü�� ����� key ���鸸 ��������.
		Enumeration<Object> en = props.keys();
		while(en.hasMoreElements()){
			String key=en.nextElement().toString();//key ��
			String className=props.getProperty(key);//value��
			System.out.println(key+":"+className);
			if(className!=null){
				className=className.trim();//�յ� ���� ������ �ٽ� �Ҵ�
			}
			try {
				Class cls = Class.forName(className);
				Object cmdInstance=cls.newInstance();
				//�ش� Ŭ������ ��üȭ���� �޸𸮿� �÷��ش�.=>new �۾��� ������ش�.
				///////////////////////////////////
				cmdMap.put(key, cmdInstance);
				///////////////////////////////////
			}catch(Exception e) {//class�� ã�� ������ ����ó��
				e.printStackTrace();
				throw new ServletException(e);//web browser�� ���� �߻� ���ñ���� ����Ѵ�.
			}
		}
	}
	private void requestProcess(HttpServletRequest req, HttpServletResponse res)
			throws ServletException,IOException{
		System.out.println("FrontController�����.......");
		//1. Ŭ���̾�Ʈ�� ��û URI�� �м��غ���.
		String uri=req.getRequestURI();
		System.out.println("URI: "+uri);
		//���ؽ�Ʈ�� ���
		String myctx=req.getContextPath();
		System.out.println("myctx: "+myctx);//"/MyMVC"
		int len=myctx.length(); //=6
		System.out.println("leng: "+len);
		String cmd=uri.substring(len);//+1�ߴ��� ����,"/index.do"�� /�� �ٿ��� cmd�� ����
		System.out.println("cmd: "+cmd);
		AbstractAction action=null;
		if(cmd.equals("index.do")){
			action=new IndexAction();
		}
		//�б⹮�� ���� �ڸ�//hashmap(cmdMap)���� get�ؼ� ���´�. cmd�� �̿��ؼ�
		Object instance=cmdMap.get(cmd);
		//->action���� ����ȯ->
		action=(AbstractAction)instance;
		//-----------------//
		if(action==null){
			System.out.println("action�� ��");
			return;
		}
		try {
			///////////
			action.execute(req,res);//�̺κп��� db�۾��� �̷������. *****�߿��� �κ�*****
			///////////
			//�̵��� �������� ���
			String viewPage=action.getViewPage();
			System.out.println("viewPage: "+viewPage);
			//�̵���Ŀ� ���� �������� �̵�
			if(action.isRedirect()){
				//redirect�� �̵� (10~20%���� �����)
				res.sendRedirect(viewPage);
				
			}else{
				//forward������� �̵�(80~90%���� �����)
				RequestDispatcher disp=req.getRequestDispatcher(viewPage);
				disp.forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  	protected void doGet(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		requestProcess(request,response);
  	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
  		requestProcess(request,response);
	}
}
