package common.controller;
//�������̽� : �߻�޼ҵ� + public static final ����� ����� ���´�.
//			>> ����� �޾� �ϼ���Ű�� ���� ����

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	void execute(HttpServletRequest req, HttpServletResponse res)throws Exception;
	//�������̽��� �߻�޼ҵ�� �ڵ����� public abstract�����ڰ� �ٴ´�.
}
