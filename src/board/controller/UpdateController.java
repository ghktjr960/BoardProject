package board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import board.model.BoardService;
import board.model.BoardUploadVo;
import board.model.BoardVo;

@WebServlet(
	urlPatterns = "/update.do",
	initParams = {
		@WebInitParam(
			name="writePath",
			value="C:\\writefile\\upload\\"
		)
	}
)

@MultipartConfig(maxFileSize = -1, maxRequestSize = -1, fileSizeThreshold = 1024)

public class UpdateController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private BoardService service = BoardService.getInstance();
	
	private static String uploadPath = "";
	private File uploadDir = null;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		uploadPath = config.getInitParameter("writePath");
		uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int postNum = Integer.parseInt(req.getParameter("num"));
		String pageNum = req.getParameter("pageNum");
		BoardVo postVo = service.getUpdatePostInfo(postNum);
		String writer = postVo.getWriter();
		
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("postVo", postVo);
		req.setAttribute("writer", writer);
		
		req.getRequestDispatcher("/WEB-INF/board/updateForm.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post요청 들어옴");

		BoardVo postVo = new BoardVo();
		postVo.setNum(Integer.parseInt(req.getParameter("num")));
		postVo.setSubject(req.getParameter("subject"));
		postVo.setContent(req.getParameter("content"));
		service.updatePosting(postVo);
		
		String contentType = req.getContentType();
		if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
			printPartInfo(req);
		} else {
			req.setAttribute("result", -1);
		}
		req.getRequestDispatcher("/WEB-INF/board/writeProc.jsp").forward(req, resp);
		
	}
	
	private void printPartInfo(HttpServletRequest req) throws ServletException, IOException{
		System.out.println("printPartInfo 들어옴");
		Collection<Part> parts = req.getParts();
		int uploadNum = Integer.parseInt(req.getParameter("num"));
		try {
			for(Part part : parts) {
				String fileName = null;
				long size = 0;
				String contentType = null;
				
				contentType = part.getContentType();
				
				if(contentType == null) {
					part.delete();
				} else {
					fileName = getFileName(part);
					System.out.println("getFileName다녀온 fileName : " + fileName);
					size = part.getSize();
					
					BoardUploadVo upVo = new BoardUploadVo();
					upVo.setNum(uploadNum);
					upVo.setContentType(contentType);
					upVo.setFileName(fileName);
					upVo.setFileSize(size);
					service.updateUploading(upVo);
					
					if(size > 0) {
						part.write(uploadPath + fileName);
						part.delete();
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			System.err.println("파일크기가 너무 큼");
		}
	}
	
	private String getFileName(Part part) throws UnsupportedEncodingException {
		System.out.println("getFileName들어옴");
		System.out.println("partName : " + part.getName());
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				System.out.println("cd : " + cd);
				String tmp = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				tmp = tmp.substring(tmp.indexOf(":") + 1);
				return tmp;
			}
		}
		return null;
	}
	
}

