package user.member.model;
/**�α��� �� ȸ������ ó���� ���Ǵ� ����� ���� ���� Ŭ����*/
public class NotMemberException extends Exception {  //Exception ��ӹޱ�
	public NotMemberException(){ // �⺻������
		super("NotMemberException"); //super=Exception
	}
	public NotMemberException (String msg){
		super(msg); //���ܸ޽����� Ȱ����
	}
}//////////////////////////////////////////
