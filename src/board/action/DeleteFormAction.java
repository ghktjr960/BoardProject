package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.CommandAction;

public class DeleteFormAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		int postNum = Integer.parseInt(req.getParameter("num"));
		String writer = req.getParameter("writer");
		req.setAttribute("postNum", postNum);
		req.setAttribute("writer", writer);
		return "/board/deleteForm.jsp";
	}
}
