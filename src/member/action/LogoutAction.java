package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CommandAction;

public class LogoutAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		HttpSession session = req.getSession();
		session.removeAttribute("id");
		return "/member/logout.jsp";
	}
}
