package member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.MemberService;
import util.CommandAction;

public class EditProcAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		MemberService service = MemberService.getInstance();
		
		System.out.println("EditProcAction에서 수정된 회원정보 확인");
		System.out.println("id : " + req.getParameter("id"));
		System.out.println("password : " + req.getParameter("password"));
		System.out.println("name : " + req.getParameter("name"));
		System.out.println("year : " + req.getParameter("year"));
		System.out.println("month : " + req.getParameter("month"));
		System.out.println("day : " + req.getParameter("day"));
		System.out.println("gender : " + req.getParameter("gender"));
		System.out.println("email : " + req.getParameter("email"));
		
		String resultCheck = "";
		
		if(service.memberEdit(req, resp)) {
			resultCheck = "true";
			req.setAttribute("resultCheck", resultCheck);
			return "/member/editProc.jsp";
		} else {
			resultCheck = "false";
			req.setAttribute("resultCheck", resultCheck);
			return "/member/editForm.jsp";
		}
	}
}
