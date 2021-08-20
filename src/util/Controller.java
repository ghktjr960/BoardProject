package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> commandMap = new HashMap<>();
	
	public void init(ServletConfig config) throws ServletException{
		System.out.println("동작확인");
		String props = config.getInitParameter("propertyConfig");
		
		Properties pr = new Properties();
		FileInputStream fi = null;
		String path = config.getServletContext().getRealPath("/WEB-INF");
		try {
			fi = new FileInputStream(new File(path, props));
			pr.load(fi);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fi != null) {
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		Iterator<Object> keyIter = pr.keySet().iterator();
		
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();
			String className = pr.getProperty(command);

			System.out.println("command확인 : " + command);
			System.out.println("className확인 : " + className);
			
			try {
				Class<?> commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				commandMap.put(command, commandInstance);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requestPro(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		requestPro(req, resp);
	}

	private void requestPro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String view = null;
		CommandAction com = null;
		
		try {
			String command = req.getRequestURI();
			com = (CommandAction)commandMap.get(command);
			view = "/WEB-INF" + com.requestPro(req, resp);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		if(view.startsWith("redirect:")) {
			String redirectLocation = view.substring("redirect:".length());
			resp.sendRedirect(redirectLocation);
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(view);
			dispatcher.forward(req, resp);
		}
	}
}
