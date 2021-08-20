package board.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardService;
import board.model.BoardVo;
import util.CommandAction;

public class ListAction implements CommandAction {
	public String requestPro(HttpServletRequest req, HttpServletResponse resp) throws Throwable {
		BoardService service = BoardService.getInstance();
		List<BoardVo> postList = null;
		
		String pageNum = req.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		int pageSize = 10;
		int currentPage = Integer.parseInt(pageNum);
		
		int startRnum = (currentPage - 1) * pageSize + 1;
		int endRnum = currentPage * pageSize;
		
		int count = 0;
		count = service.postCount();
		if(count > 0) {
			postList = service.postList(startRnum, endRnum);
		}	else {
			postList = Collections.emptyList();
		}
		int number = 0;
		number = count - (currentPage - 1) * pageSize;
		
		req.setAttribute("currentPage", new Integer(currentPage));
		req.setAttribute("startRnum", new Integer(startRnum));
		req.setAttribute("endRow", new Integer(endRnum));
		req.setAttribute("count", new Integer(count));
		req.setAttribute("pageSize", new Integer(pageSize));
		req.setAttribute("number", new Integer(number));
		req.setAttribute("postList", postList);
		
		return "/board/list.jsp";
	}
}
