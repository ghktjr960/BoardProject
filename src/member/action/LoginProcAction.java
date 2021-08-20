package member.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.MemberService;
import util.CommandAction;

public class LoginProcAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		MemberService service = MemberService.getInstance();
		String id = req.getParameter("id");
		String password = req.getParameter("password");
	
		if(service.memberLogin(id, password)) {
			HttpSession session = null;
			session = req.getSession();
			session.setAttribute("id", id);
		}
		return "/member/loginProc.jsp";
		
		
//		if(service.memberLogin(id, password)) {
//			Cookie loginSuccCk = new Cookie("login", "success");
//			loginSuccCk.setPath("/");
//			resp.addCookie(loginSuccCk);
//			
//			Cookie idCk = new Cookie("id", id);
//			idCk.setMaxAge(60*60);
//			idCk.setPath("/*");
//			resp.addCookie(idCk);
//	
//			System.out.println("로그인 쿠키 만들어짐");
//			return "/member/loginProc.jsp";
//		} else {
//			Cookie loginFaleCk = new Cookie("login", "fale");
//			loginFaleCk.setPath("/");
//			resp.addCookie(loginFaleCk);
//			System.out.println("로그인 쿠키 안 만들어짐");
//			return "/member/loginProc.jsp";
//		}
		
	}
}
