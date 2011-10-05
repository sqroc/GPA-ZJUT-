package net.sqroc.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sqroc.manage.CreateExcel;

public class ExportExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String num = "";
		num = (String) session.getAttribute("num");
		String pass = (String) session.getAttribute("pass");
		String term = (String) session.getAttribute("term");
		if (num.equals("")) {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("login.jsp");
			requestDispatcher.forward(request, response);
		} else {
			response.setContentType("application/vnd.ms-excel");
			CreateExcel excel = new CreateExcel();
			excel.cExcel(num, pass, term, response.getOutputStream());
		}
	}
}