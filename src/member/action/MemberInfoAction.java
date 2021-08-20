package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.MemberService;
import member.model.MemberVo;
import util.CommandAction;

public class MemberInfoAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		HttpSession session = req.getSession();
		String id = (String)session.getAttribute("id");
		MemberService service = MemberService.getInstance();
		MemberVo memberInfo = service.memberInfo(id);
		
		req.setAttribute("memberInfo", memberInfo);
		
		return "/member/memberInfo.jsp";
	}
}
