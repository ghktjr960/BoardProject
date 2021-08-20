package board.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private static String uploadPath = "";
	private File uploadDir = null;
	
	@Override
	public void init() throws ServletException {
		System.out.println("download init들어옴");
		uploadPath = getServletContext().getInitParameter("writePath");
		uploadDir = new File(uploadPath);
		if(uploadPath == null) {
			System.out.println("uploadPath가 없음");
		}
		if(uploadDir == null) {
			System.out.println("uploadDir가 없음");
		}
		System.out.println("download init 끝");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		String file = uploadPath + fileName;
		String mimeType = getServletContext().getMimeType(file);
		if(mimeType == null) {
			mimeType = "application/octet-stream";
		}
		fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");

		resp.setContentType(mimeType + "; charset=utf-8");
		resp.setHeader("Content-Disposition", "attatchment; filename="+fileName);

		byte[] buffer = new byte[1024];
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		ServletOutputStream out = resp.getOutputStream();
		
		int readCnt = 0;
		while((readCnt = in.read(buffer, 0, buffer.length)) != -1) {
			out.write(buffer, 0, readCnt);
		}
		
		out.flush();
		out.close();
		
		in.close();
	}
}
