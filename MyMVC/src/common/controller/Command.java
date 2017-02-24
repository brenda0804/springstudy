package common.controller;
//인터페이스 : 추상메소드 + public static final 상수만 멤버로 갖는다.
//			>> 상속을 받아 완성시키기 위한 목적

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	void execute(HttpServletRequest req, HttpServletResponse res)throws Exception;
	//인터페이스의 추상메소드는 자동으로 public abstract지정자가 붙는다.
}
