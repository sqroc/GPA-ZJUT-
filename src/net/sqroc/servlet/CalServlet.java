package net.sqroc.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sqroc.manage.CalPointMain;

public class CalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher;
		request.setCharacterEncoding("utf-8");
		String num = request.getParameter("num");
		String pass = request.getParameter("pass");
		String term = request.getParameter("term");
		HttpSession session = request.getSession(true);
		session.setAttribute("num", num);
		session.setAttribute("pass", pass);
		session.setAttribute("term", term);
		CalPointMain calPointMain = new CalPointMain();
		String ok = calPointMain.checklogin(num, pass);
		if (ok.endsWith("ok")) {
			requestDispatcher = request.getRequestDispatcher("index.jsp");
			requestDispatcher.forward(request, response);
		} else {
			request.setAttribute("error", "error");
			requestDispatcher = request.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
		}
	}
}