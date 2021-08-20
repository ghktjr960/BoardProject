package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardService;
import board.model.BoardUploadVo;
import board.model.BoardVo;
import util.CommandAction;

public class ContentAction implements CommandAction{
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		System.out.println("contentAction 요청들어옴?");
		BoardService service = BoardService.getInstance();
		int postNum = Integer.parseInt(req.getParameter("num"));
		
		BoardVo postVo = service.postInfo(postNum);
		BoardUploadVo uploadVo = service.uploadInfo(postNum);
		
		String pageNum = req.getParameter("pageNum");
		
		req.setAttribute("num", postNum);
		req.setAttribute("postVo", postVo);
		req.setAttribute("uploadVo", uploadVo);
		req.setAttribute("pageNum", pageNum);
		return "/board/content.jsp";
	}
}
