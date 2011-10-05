package net.sqroc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sqroc.manage.CalPointMain;

public class AutoCalculate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CalPointMain calPointMain = new CalPointMain();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		String num = (String) session.getAttribute("num");
		String pass = (String) session.getAttribute("pass");
		String term = (String) session.getAttribute("term");
		String data = calPointMain.showdata(num, pass, term);
		out.println(data);
	}
}