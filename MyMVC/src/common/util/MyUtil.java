package common.util;
import javax.servlet.http.*;

public class MyUtil {

	public static void addMsgLoc(HttpServletRequest req,
			String msg, String loc){
		req.setAttribute("message",msg);
		req.setAttribute("loc",loc);
	}
}/////////////////////////////
