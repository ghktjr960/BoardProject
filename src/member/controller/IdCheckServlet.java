package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.MemberService;

@WebServlet(urlPatterns = "/idCheck")
public class IdCheckServlet  extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberService service = MemberService.getInstance();
		String id = req.getParameter("id");
		
		;
		PrintWriter pw = new PrintWriter(resp.getOutputStream());

		String responseString = "";
		
		if(service.idCheck(id)) {
			responseString += "<span style='color:red'>&nbsp;사용할 수 없는 아이디 입니다.</span>";
		}else {
			responseString += "<span style='color:green'>&nbsp;사용할 수 있는 아이디 입니다.</span>";
		}
		pw.println(responseString);
		pw.close();
	}
}
