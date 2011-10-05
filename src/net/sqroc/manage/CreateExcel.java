package net.sqroc.manage;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sqroc.model.Course;

public class CreateExcel {
	public void cExcel(String num, String pass, String term, OutputStream os) {
		WritableWorkbook book;
		try {
			book = Workbook.createWorkbook(os);

			WritableSheet sheet = book.createSheet(num, 0);

			sheet.addCell(new Label(0, 0, "实际学期"));
			sheet.addCell(new Label(1, 0, "执行课程名称"));
			sheet.addCell(new Label(2, 0, "考试性质"));
			sheet.addCell(new Label(3, 0, "成绩"));
			sheet.addCell(new Label(4, 0, "学时"));
			sheet.addCell(new Label(5, 0, "学分"));

			CalPointMain calPointMain = new CalPointMain();
			List courses = calPointMain.getScore(num, pass, term);
			for (int i = 0; i < courses.size(); ++i) {
				Course course = new Course();
				course = (Course) ((HashMap) courses.get(i)).get("cell");
				sheet.addCell(new Label(0, i + 1, course.getTerm()));
				sheet.addCell(new Label(1, i + 1, course.getName()));
				sheet.addCell(new Label(2, i + 1, course.getProperty()));
				sheet.addCell(new Label(3, i + 1, course.getScore()));
				sheet.addCell(new Label(4, i + 1, course.getTime()));
				sheet.addCell(new Label(5, i + 1, course.getCredit()+""));
			}

			book.write();
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}