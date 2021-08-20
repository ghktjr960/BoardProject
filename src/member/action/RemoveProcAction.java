package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.MemberService;
import util.CommandAction;

public class RemoveProcAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		HttpSession session = req.getSession();
		String id = (String)session.getAttribute("id");
		MemberService service = MemberService.getInstance();
		session.removeAttribute("id");
		
		service.memberRemove(id);
		
		return "/member/removeProc.jsp";
	}
}
