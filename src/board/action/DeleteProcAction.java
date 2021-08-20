package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardService;
import util.CommandAction;

public class DeleteProcAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		int postNum = Integer.parseInt(req.getParameter("num"));
		
		BoardService service = BoardService.getInstance();
		service.deletePost(postNum);
		service.deleteUpload(postNum);
		
		return "/board/deleteProc.jsp";
	}
}
