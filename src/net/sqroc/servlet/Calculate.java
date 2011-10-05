package net.sqroc.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Calculate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String scoresing = request.getParameter("scores");
		String creditsing = request.getParameter("credits");
		String[] scores1 = scoresing.split(",");
		String[] credits1 = creditsing.split(",");

		double goals = 0D;
		double credit = 0D;
		for (int i = 0; i < scores1.length; ++i) {
			double cre = Double.parseDouble(credits1[i]);
			if (scores1[i].equals("优秀"))
				goals += 4.5D * cre;
			else if (scores1[i].equals("良好"))
				goals += 3.5D * cre;
			else if (scores1[i].equals("中等"))
				goals += 2.5D * cre;
			else if (scores1[i].equals("及格"))
				goals += 1.5D * cre;
			else if (scores1[i].equals("不及格"))
				goals += 0D;
			else
				goals += (Double.parseDouble(scores1[i]) - 50.0D) / 10.0D * cre;

			credit += Double.parseDouble(credits1[i]);
		}
		Double result = Double.valueOf(goals / credit);
		out.println("平均绩点：" + result);
	}
}