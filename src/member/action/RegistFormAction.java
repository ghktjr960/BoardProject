package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandAction;

public class RegistFormAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		return "/member/registForm.jsp";
	}
}
