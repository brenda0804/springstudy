package user.member.model;
/**로그인 및 회원인증 처리에 사용되는 사용자 정의 예외 클래스*/
public class NotMemberException extends Exception {  //Exception 상속받기
	public NotMemberException(){ // 기본생성자
		super("NotMemberException"); //super=Exception
	}
	public NotMemberException (String msg){
		super(msg); //예외메시지로 활용함
	}
}//////////////////////////////////////////
