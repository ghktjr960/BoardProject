package board.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	urlPatterns = "/write.do",
	initParams = {
		@WebInitParam(
			name="writePath",
			value="C:\\writefile\\upload\\"
		)
	}
)

@MultipartConfig(maxFileSize = -1, maxRequestSize = -1, fileSizeThreshold = 1024)

public class WriteController extends HttpServlet{
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
		int num = 0;
		int ref = 1;
		int step = 0;
		int depth = 0;
		
		try {
			if(req.getParameter("num") != null) {
				num = Integer.parseInt(req.getParameter("num"));
				ref = Integer.parseInt(req.getParameter("ref"));
				step = Integer.parseInt(req.getParameter("step"));
				depth = Integer.parseInt(req.getParameter("depth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		req.setAttribute("num", new Integer(num));
		req.setAttribute("ref", new Integer(ref));
		req.setAttribute("step", new Integer(step));
		req.setAttribute("depth", new Integer(depth));
		req.getRequestDispatcher("/WEB-INF/board/writeForm.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post요청 들어옴");
		BoardVo vo = new BoardVo();
		vo.setNum(Integer.parseInt(req.getParameter("num")));
		vo.setWriter(req.getParameter("writer"));
		vo.setSubject(req.getParameter("subject"));
		vo.setRegdate(new Timestamp(System.currentTimeMillis()));
		vo.setRef(Integer.parseInt(req.getParameter("ref")));
		vo.setStep(Integer.parseInt(req.getParameter("step")));
		vo.setDepth(Integer.parseInt(req.getParameter("depth")));
		vo.setContent(req.getParameter("content"));
		service.posting(vo);
		
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
					upVo.setContentType(contentType);
					upVo.setFileName(fileName);
					upVo.setFileSize(size);
					service.uploading(upVo);
					
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

