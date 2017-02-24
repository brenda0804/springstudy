package file.down;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileDown")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public FileDownloadServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		download(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		download(request,response);
	}
	private void download(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException {
		//1. �ٿ�ε��� ���ϸ� �޾ƿ���
		String filename = req.getParameter("filename");
		System.out.println("filename = "+filename);
		if(filename==null||filename.trim().isEmpty()){
			return;
		}
		
		//2. ���ε��� ���丮�� ������ ���
		//JSP������ application.getRealPath("/Upload");
		//application: ServletContext Ÿ��
		ServletContext application = req.getServletContext();
		String upDir = application.getRealPath("/upload");
		
		//3. �ٿ�ε� ���� ������ ������ �����
		String path = upDir +"/"+filename;
		
		/* �ٿ�ε�â�� ����� ��� �ٿ������ ���ϴ� ���� �غ���.
		 * response ����� ����Ʈ Ÿ���� �����ϸ� �������� �ش� ����Ʈ Ÿ���� �� �� �ִ� �����̸� �����ְ�,
		 * ���� �� �𸣴� ���� �����̸� �ٿ�ε� â�� �����Ų��. 
		 * */
		//res.setContentType("application/unknown");
		res.setContentType("application/octet-stream");
		//�߸𸣴� ���� �������� �ٿ�ε�(���̾�α�) â�� �����Ų��.
		
		//�ѱ� ���ϸ��� ������ ���� ���� ����
		String filename_en //filename �� byte������ �����ؼ� ���ο� String ��ü ����
		= new String(filename.getBytes(),"ISO-8859-1");
		
		
		String attach="attachment; filename="+filename_en;
		res.setHeader("Content-Disposition", attach);
		
		//4. 3�� ��η� ��Ʈ�� �����ؼ� ������ �о���̰� data�� Ŭ���̾�Ʈ ������ ��������.
		/* ������ �ҽ�:����(path)
		 * 		==>FileInputStream ==>BufferedInputStream
		 * ������ ������:Ŭ���̾�Ʈ(��������)
		 * 		==>ServletOuputStream ==>BufferedOutputStream
		 * */
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		BufferedOutputStream bos = new BufferedOutputStream(res.getOutputStream());//->ServletOuputStream�� ��ȯ
		byte[] data = new byte[4000];
		int n=0;
		while((n=bis.read(data))!=-1){//������ ��(-1)�� �ƴҶ����� �о���̱�
			bos.write(data,0,n);
			bos.flush();
		}
		if(bis!=null)bis.close();
		if(bos!=null)bos.close();
	}	
}

















