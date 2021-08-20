package member.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberService {
	private static MemberService instance = null;
	private MemberService() { }
	public static MemberService getInstance() {
		if (instance == null) {
			synchronized (MemberService.class) {
				instance = new MemberService();
			}
		}
		return instance;
	}
	private MemberDao dao = MemberDao.getInstance();
	
	//id중복확인
	public boolean idCheck(String id) {
		return dao.memberIdCheck(id);
	}
	
	//회원가입
	public boolean memberRegist(HttpServletRequest req, HttpServletResponse resp) {
		MemberVo vo = new MemberVo();
		vo.setId(req.getParameter("id"));
		vo.setPassword(req.getParameter("password"));
		vo.setName(req.getParameter("name"));
		vo.setYear(Integer.parseInt(req.getParameter("year")));
		vo.setMonth(Integer.parseInt(req.getParameter("month")));
		vo.setDay(Integer.parseInt(req.getParameter("day")));
		vo.setGender(req.getParameter("gender"));
		vo.setEmail(req.getParameter("email"));
		
		return dao.memberRegist(vo);
	}
	
	// login
	public boolean memberLogin(String id, String password) {
		boolean check = dao.memberLogin(id, password);
		if(check) {
			System.out.println("서비스 로그인 성공");
		} else {
			System.out.println("서비스 로그인 실패");
		} 
		return check;
	}
	
	// memberInfo
	public MemberVo memberInfo(String id) {
		return dao.getMemberInfo(id);
	}
	
	//회원 수정
	public boolean memberEdit(HttpServletRequest req, HttpServletResponse resp) {
		MemberVo vo = new MemberVo();
		vo.setId(req.getParameter("id"));
		vo.setPassword(req.getParameter("password"));
		vo.setName(req.getParameter("name"));
		vo.setYear(Integer.parseInt(req.getParameter("year")));
		vo.setMonth(Integer.parseInt(req.getParameter("month")));
		vo.setDay(Integer.parseInt(req.getParameter("day")));
		vo.setGender(req.getParameter("gender"));
		vo.setEmail(req.getParameter("email"));
		
		return dao.editMemberInfo(vo);
	}
	
	// 회원 탈퇴
	public boolean memberRemove(String id) {
		return dao.removeMember(id);
	}
}
