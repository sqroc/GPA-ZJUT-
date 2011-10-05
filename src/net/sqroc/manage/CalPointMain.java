package net.sqroc.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sqroc.model.Course;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CalPointMain {
	public String showdata(String num, String pass, String term) {
		double result = 0D;
		double[] results = new double[2];
		double[] results2 = new double[2];
		String scores = "";
		String[] flag = term.split(",");
		if (flag[0].equals("Y")) {
			String term1 = flag[1] + "(1)";
			results = termdata(num, pass, term1);
			String term2 = flag[1] + "(2)";
			results2 = termdata(num, pass, term2);
			result = (results[0] + results2[0]) / (results[1] + results2[1]);
		} else {
			results = termdata(num, pass, term);
			result = results[0] / results[1];
		}
		return "平均绩点：" + result;
	}

	public String getData(String num, String pass, String term) {
		String albumInfor = "";
		List courses = new ArrayList();
		int totalnum = 100;
		String[] flag = term.split(",");
		if (flag[0].equals("Y")) {
			String term1 = flag[1] + "(1)";
			courses = gettermdata(num, pass, term1, courses);
			String term2 = flag[1] + "(2)";
			courses = gettermdata(num, pass, term2, courses);
		} else {
			courses = gettermdata(num, pass, term, courses);
		}
		albumInfor = JSONArray.fromObject(courses).toString();
		if (albumInfor.equals("[]"))
			albumInfor = "[{\"id\":0,\"cell\":{\"credit\":\"\",\"name\":\"暂无数据\",\"property\":\"\",\"score\":\"\",\"term\":\"\",\"time\":\"\"}}]";

		albumInfor = "{\"page\":1,\"total\":" + totalnum + ", \"rows\":"
				+ albumInfor + "}";
		return albumInfor;
	}

	public List getScore(String num, String pass, String term) {
		List courses = new ArrayList();
		int totalnum = 100;
		String[] flag = term.split(",");
		if (flag[0].equals("Y")) {
			String term1 = flag[1] + "(1)";
			courses = gettermdata(num, pass, term1, courses);
			String term2 = flag[1] + "(2)";
			courses = gettermdata(num, pass, term2, courses);
		} else {
			courses = gettermdata(num, pass, term, courses);
		}
		return courses;
	}

	public String checklogin(String num, String pass) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpost = new HttpPost(
					"http://www.ycjw.zjut.edu.cn/logon.aspx");
			List nvps = new ArrayList();
			nvps.add(new BasicNameValuePair("Cbo_LX", "学生"));
			nvps.add(new BasicNameValuePair("Img_DL.x", "29"));
			nvps.add(new BasicNameValuePair("Img_DL.y", "13"));
			nvps.add(new BasicNameValuePair("Txt_UserName", num));
			nvps.add(new BasicNameValuePair("Txt_Password", pass));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"dDwyMDY5NjM4MDg7dDw7bDxpPDE+Oz47bDx0PDtsPGk8Mz47aTwxNT47PjtsPHQ8O2w8aTwxPjtpPDM+O2k8NT47aTw3PjtpPDk+O2k8MTE+O2k8MTM+O2k8MTU+O2k8MTc+Oz47bDx0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+Oz4+O3Q8dDw7dDxpPDM+O0A8LS3nlKjmiLfnsbvlnostLTvmlZnluIg75a2m55SfOz47QDwtLeeUqOaIt+exu+Weiy0tO+aVmeW4iDvlrabnlJ87Pj47Pjs7Pjs+Pjs+PjtsPEltZ19ETDs+PnAOxH2tzqCdmyQ3NVSABVhcGj4g"));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			CookieStore cookieStore = httpclient.getCookieStore();
			EntityUtils.consume(entity);
			httpost = new HttpPost(
					"http://www.ycjw.zjut.edu.cn//stdgl/cxxt/cjcx/Cjcx_Xsgrcj.aspx");
			nvps = new ArrayList();
			nvps.add(new BasicNameValuePair("1", "rbtnXq"));
			nvps.add(new BasicNameValuePair("Button1", "普通考试成绩查询"));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"dDw4Mjc4OTE1MTQ7dDw7bDxpPDE+Oz47bDx0PDtsPGk8NT47aTw3PjtpPDk+O2k8MTM+O2k8MTc+O2k8MjE+O2k8MzU+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPOWtpuWPt++8mjIwMDkyNjYzMDcxMzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya5rKI5aWH6bmPOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDznj63nuqfvvJrova/ku7blt6XnqIswOTA3Oz4+Oz47Oz47dDx0PDt0PGk8Mzc+O0A8XGU777yd5omA5pyJ5a2m5pyf77ydOzE5OTkvMjAwMCgxKTsxOTk5LzIwMDAoMik7MjAwMC8yMDAxKDEpOzIwMDAvMjAwMSgyKTsyMDAxLzIwMDIoMSk7MjAwMS8yMDAyKDIpOzIwMDIvMjAwMygxKTsyMDAyLzIwMDMoMik7MjAwMy8yMDA0KDEpOzIwMDMvMjAwNCgyKTsyMDA0LzIwMDUoMSk7MjAwNC8yMDA1KDIpOzIwMDUvMjAwNigxKTsyMDA1LzIwMDYoMik7MjAwNi8yMDA3KDEpOzIwMDYvMjAwNygyKTsyMDA3LzIwMDgoMSk7MjAwNy8yMDA4KDIpOzIwMDgvMjAwOSgxKTsyMDA4LzIwMDkoMik7MjAwOS8yMDEwKDEpOzIwMDkvMjAxMCgyKTsyMDEwLzIwMTEoMSk7MjAxMC8yMDExKDIpOzIwMTEvMjAxMigxKTsyMDExLzIwMTIoMik7MjAxMi8yMDEzKDEpOzIwMTIvMjAxMygyKTsyMDk4LzIwOTkoMSk7MjAxMy8yMDE0KDEpOzIwMTMvMjAxNCgyKTsyMDE0LzIwMTUoMSk7MjAxNC8yMDE1KDIpOzIwMTUvMjAxNigxKTsyMDE1LzIwMTYoMik7PjtAPFxlO++8neaJgOacieWtpuacn++8nTsxOTk5LzIwMDAoMSk7MTk5OS8yMDAwKDIpOzIwMDAvMjAwMSgxKTsyMDAwLzIwMDEoMik7MjAwMS8yMDAyKDEpOzIwMDEvMjAwMigyKTsyMDAyLzIwMDMoMSk7MjAwMi8yMDAzKDIpOzIwMDMvMjAwNCgxKTsyMDAzLzIwMDQoMik7MjAwNC8yMDA1KDEpOzIwMDQvMjAwNSgyKTsyMDA1LzIwMDYoMSk7MjAwNS8yMDA2KDIpOzIwMDYvMjAwNygxKTsyMDA2LzIwMDcoMik7MjAwNy8yMDA4KDEpOzIwMDcvMjAwOCgyKTsyMDA4LzIwMDkoMSk7MjAwOC8yMDA5KDIpOzIwMDkvMjAxMCgxKTsyMDA5LzIwMTAoMik7MjAxMC8yMDExKDEpOzIwMTAvMjAxMSgyKTsyMDExLzIwMTIoMSk7MjAxMS8yMDEyKDIpOzIwMTIvMjAxMygxKTsyMDEyLzIwMTMoMik7MjA5OC8yMDk5KDEpOzIwMTMvMjAxNCgxKTsyMDEzLzIwMTQoMik7MjAxNC8yMDE1KDEpOzIwMTQvMjAxNSgyKTsyMDE1LzIwMTYoMSk7MjAxNS8yMDE2KDIpOz4+O2w8aTwxPjs+Pjs7Pjt0PHQ8O3Q8aTwyOT47QDxcZTvvvJ3miYDmnInlrablubTvvJ07MTk5OS8yMDAwOzIwMDAvMjAwMTsyMDAxLzIwMDI7MjAwMi8yMDAzOzIwMDMvMjAwNDsyMDA0LzIwMDU7MjAwNS8yMDA2OzIwMDYvMjAwNzsyMDA3LzIwMDg7MjAwOC8yMDA5OzIwMDkvMjAxMDsyMDEwLzIwMTE7MjAxMS8yMDEyOzIwMTIvMjAxMzsyMDEzLzIwMTQ7MjAxNC8yMDE1OzIwMTUvMjAxNjsyMDE2LzIwMTc7MjAxNy8yMDE4OzIwMTgvMjAxOTsyMDE5LzIwMjA7MjAyMC8yMDIxOzIwMjEvMjAyMjsyMDIyLzIwMjM7MjAyMy8yMDI0OzIwMjQvMjAyNTsyMDk4LzIwOTk7PjtAPFxlO++8neaJgOacieWtpuW5tO+8nTsxOTk5LzIwMDA7MjAwMC8yMDAxOzIwMDEvMjAwMjsyMDAyLzIwMDM7MjAwMy8yMDA0OzIwMDQvMjAwNTsyMDA1LzIwMDY7MjAwNi8yMDA3OzIwMDcvMjAwODsyMDA4LzIwMDk7MjAwOS8yMDEwOzIwMTAvMjAxMTsyMDExLzIwMTI7MjAxMi8yMDEzOzIwMTMvMjAxNDsyMDE0LzIwMTU7MjAxNS8yMDE2OzIwMTYvMjAxNzsyMDE3LzIwMTg7MjAxOC8yMDE5OzIwMTkvMjAyMDsyMDIwLzIwMjE7MjAyMS8yMDIyOzIwMjIvMjAyMzsyMDIzLzIwMjQ7MjAyNC8yMDI1OzIwOTgvMjA5OTs+Pjs+Ozs+O3Q8dDw7dDxpPDM2PjtAPO+8neaJgOacieivvueoi++8nTtD56iL5bqP6K6+6K6hO+Wkp+WtpuiLseivrULvvIjkuInnuqfvvInihaA76auY562J5pWw5a2mQeKFoDvlt6XnqIvlm77lraZD4oWgO+iuoeeul+acuuenkeWtpuWvvOiuuuKFoDvnprvmlaPmlbDlrabihaA7576O5a2m5qaC6K664oWgO+aAneaDs+mBk+W+t+S/ruWFu+S4juazleW+i+WfuuehgOKFoDvkvZPogrLihaA757q/5oCn5Luj5pWwQuKFoDtDKyvnqIvluo/orr7orqHihaA76Zi/6YeM5be05be055S15a2Q5ZWG5Yqh6K666K+B4oWgO+eoi+W6j+iuvuiuoeWkp+Wei+WunumqjDvlpKflrabniannkIZD4oWgO+Wkp+WtpueJqeeQhuWunumqjELihaA75aSn5a2m6Iux6K+tQu+8iOWbm+e6p++8ieKFoDvmpoLnjoforrrkuI7mlbDnkIbnu5/orqFC4oWgO+mrmOetieaVsOWtpkHihaE76K6h566X5py65Zu+5b2i5a2m4oWgO+S9k+iCsuKFoTvkvZPotKjlgaXlurforq3nu4PihaA75Lit5Zu96L+R546w5Luj5Y+y57qy6KaB4oWgO0phdmHnqIvluo/orr7orqHihaA75aSn5a2m55SfS0FC5Yib5Lia5Z+656GA4oWgO+Wkp+WtpueUn+iBjOS4muWPkeWxleS4juWwseS4muaMh+WvvO+8iOS4re+8ieKFoDvlpKflrabniannkIZD4oWhO+WkmuWqkuS9k+aKgOacr+KFoDvpq5jnuqfoi7Hor60x77yI6K+75YaZ6K+R77yJ4oWgO+mrmOe6p+iLseivrTHvvIjop4blkKzor7TvvInihaA76ams5YWL5oCd5Li75LmJ5Z+65pys5Y6f55CG4oWgO+WFqOeQg+WIm+aWsOWVhuS4muaooeW8j+eglOeptuKFoDvmlbDmja7nu5PmnoQo5Y+v6YCJ5Y+M6K+tKTvmlbDmja7nu5PmnoTlpKflnovlrp7pqow75L2T6IKy4oWiO+WGm+iure+8iOWGm+S6i+eQhuiuuu+8ieKFoDs+O0A877yd5omA5pyJ6K++56iL77ydO0PnqIvluo/orr7orqE75aSn5a2m6Iux6K+tQu+8iOS4iee6p++8ieKFoDvpq5jnrYnmlbDlraZB4oWgO+W3peeoi+WbvuWtpkPihaA76K6h566X5py656eR5a2m5a+86K664oWgO+emu+aVo+aVsOWtpuKFoDvnvo7lrabmpoLorrrihaA75oCd5oOz6YGT5b635L+u5YW75LiO5rOV5b6L5Z+656GA4oWgO+S9k+iCsuKFoDvnur/mgKfku6PmlbBC4oWgO0MrK+eoi+W6j+iuvuiuoeKFoDvpmL/ph4zlt7Tlt7TnlLXlrZDllYbliqHorrror4HihaA756iL5bqP6K6+6K6h5aSn5Z6L5a6e6aqMO+Wkp+WtpueJqeeQhkPihaA75aSn5a2m54mp55CG5a6e6aqMQuKFoDvlpKflraboi7Hor61C77yI5Zub57qn77yJ4oWgO+amgueOh+iuuuS4juaVsOeQhue7n+iuoULihaA76auY562J5pWw5a2mQeKFoTvorqHnrpfmnLrlm77lvaLlrabihaA75L2T6IKy4oWhO+S9k+i0qOWBpeW6t+iuree7g+KFoDvkuK3lm73ov5HnjrDku6Plj7LnurLopoHihaA7SmF2Yeeoi+W6j+iuvuiuoeKFoDvlpKflrabnlJ9LQULliJvkuJrln7rnoYDihaA75aSn5a2m55Sf6IGM5Lia5Y+R5bGV5LiO5bCx5Lia5oyH5a+877yI5Lit77yJ4oWgO+Wkp+WtpueJqeeQhkPihaE75aSa5aqS5L2T5oqA5pyv4oWgO+mrmOe6p+iLseivrTHvvIjor7vlhpnor5HvvInihaA76auY57qn6Iux6K+tMe+8iOinhuWQrOivtO+8ieKFoDvpqazlhYvmgJ3kuLvkuYnln7rmnKzljp/nkIbihaA75YWo55CD5Yib5paw5ZWG5Lia5qih5byP56CU56m24oWgO+aVsOaNrue7k+aehCjlj6/pgInlj4zor60pO+aVsOaNrue7k+aehOWkp+Wei+WunumqjDvkvZPogrLihaI75Yab6K6t77yI5Yab5LqL55CG6K6677yJ4oWgOz4+Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+PjtsPHJidG5YcTtyYnRuWG47cmJ0blhuOz4+1UlPjXyXKOip7gykbCFHc78KjgQ="));
			nvps.add(new BasicNameValuePair("ddlKc", "＝所有课程＝"));
			nvps.add(new BasicNameValuePair("ddlXq", "＝所有学期＝"));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));
			httpclient.setCookieStore(cookieStore);
			response = httpclient.execute(httpost);
			entity = response.getEntity();
			if (entity != null) {
				String html = EntityUtils.toString(entity);
				html = html.replaceAll("&nbsp;", "0");
				Document doc = Jsoup.parse(html);
				Element masthead = doc.select("table#DataGrid1").get(0);
				Elements elements = doc.select("font[color=#000066]");
				if (elements.size() <= 0) {
					return "failed";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return "ok";
	}

	public List gettermdata(String num, String pass, String term, List courses) {
		int totalnum = 0;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpost = new HttpPost(
					"http://www.ycjw.zjut.edu.cn/logon.aspx");
			List nvps = new ArrayList();
			nvps.add(new BasicNameValuePair("Cbo_LX", "学生"));
			nvps.add(new BasicNameValuePair("Img_DL.x", "29"));
			nvps.add(new BasicNameValuePair("Img_DL.y", "13"));
			nvps.add(new BasicNameValuePair("Txt_UserName", num));
			nvps.add(new BasicNameValuePair("Txt_Password", pass));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"dDwyMDY5NjM4MDg7dDw7bDxpPDE+Oz47bDx0PDtsPGk8Mz47aTwxNT47PjtsPHQ8O2w8aTwxPjtpPDM+O2k8NT47aTw3PjtpPDk+O2k8MTE+O2k8MTM+O2k8MTU+O2k8MTc+Oz47bDx0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+Oz4+O3Q8dDw7dDxpPDM+O0A8LS3nlKjmiLfnsbvlnostLTvmlZnluIg75a2m55SfOz47QDwtLeeUqOaIt+exu+Weiy0tO+aVmeW4iDvlrabnlJ87Pj47Pjs7Pjs+Pjs+PjtsPEltZ19ETDs+PnAOxH2tzqCdmyQ3NVSABVhcGj4g"));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));
			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();
			CookieStore cookieStore = httpclient.getCookieStore();
			EntityUtils.consume(entity);
			httpost = new HttpPost(
					"http://www.ycjw.zjut.edu.cn//stdgl/cxxt/cjcx/Cjcx_Xsgrcj.aspx");
			nvps = new ArrayList();
			nvps.add(new BasicNameValuePair("1", "rbtnXq"));
			nvps.add(new BasicNameValuePair("Button1", "普通考试成绩查询"));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"dDw4Mjc4OTE1MTQ7dDw7bDxpPDE+Oz47bDx0PDtsPGk8NT47aTw3PjtpPDk+O2k8MTM+O2k8MTc+O2k8MjE+O2k8MzU+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPOWtpuWPt++8mjIwMDkyNjYzMDcxMzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya5rKI5aWH6bmPOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDznj63nuqfvvJrova/ku7blt6XnqIswOTA3Oz4+Oz47Oz47dDx0PDt0PGk8Mzc+O0A8XGU777yd5omA5pyJ5a2m5pyf77ydOzE5OTkvMjAwMCgxKTsxOTk5LzIwMDAoMik7MjAwMC8yMDAxKDEpOzIwMDAvMjAwMSgyKTsyMDAxLzIwMDIoMSk7MjAwMS8yMDAyKDIpOzIwMDIvMjAwMygxKTsyMDAyLzIwMDMoMik7MjAwMy8yMDA0KDEpOzIwMDMvMjAwNCgyKTsyMDA0LzIwMDUoMSk7MjAwNC8yMDA1KDIpOzIwMDUvMjAwNigxKTsyMDA1LzIwMDYoMik7MjAwNi8yMDA3KDEpOzIwMDYvMjAwNygyKTsyMDA3LzIwMDgoMSk7MjAwNy8yMDA4KDIpOzIwMDgvMjAwOSgxKTsyMDA4LzIwMDkoMik7MjAwOS8yMDEwKDEpOzIwMDkvMjAxMCgyKTsyMDEwLzIwMTEoMSk7MjAxMC8yMDExKDIpOzIwMTEvMjAxMigxKTsyMDExLzIwMTIoMik7MjAxMi8yMDEzKDEpOzIwMTIvMjAxMygyKTsyMDk4LzIwOTkoMSk7MjAxMy8yMDE0KDEpOzIwMTMvMjAxNCgyKTsyMDE0LzIwMTUoMSk7MjAxNC8yMDE1KDIpOzIwMTUvMjAxNigxKTsyMDE1LzIwMTYoMik7PjtAPFxlO++8neaJgOacieWtpuacn++8nTsxOTk5LzIwMDAoMSk7MTk5OS8yMDAwKDIpOzIwMDAvMjAwMSgxKTsyMDAwLzIwMDEoMik7MjAwMS8yMDAyKDEpOzIwMDEvMjAwMigyKTsyMDAyLzIwMDMoMSk7MjAwMi8yMDAzKDIpOzIwMDMvMjAwNCgxKTsyMDAzLzIwMDQoMik7MjAwNC8yMDA1KDEpOzIwMDQvMjAwNSgyKTsyMDA1LzIwMDYoMSk7MjAwNS8yMDA2KDIpOzIwMDYvMjAwNygxKTsyMDA2LzIwMDcoMik7MjAwNy8yMDA4KDEpOzIwMDcvMjAwOCgyKTsyMDA4LzIwMDkoMSk7MjAwOC8yMDA5KDIpOzIwMDkvMjAxMCgxKTsyMDA5LzIwMTAoMik7MjAxMC8yMDExKDEpOzIwMTAvMjAxMSgyKTsyMDExLzIwMTIoMSk7MjAxMS8yMDEyKDIpOzIwMTIvMjAxMygxKTsyMDEyLzIwMTMoMik7MjA5OC8yMDk5KDEpOzIwMTMvMjAxNCgxKTsyMDEzLzIwMTQoMik7MjAxNC8yMDE1KDEpOzIwMTQvMjAxNSgyKTsyMDE1LzIwMTYoMSk7MjAxNS8yMDE2KDIpOz4+O2w8aTwxPjs+Pjs7Pjt0PHQ8O3Q8aTwyOT47QDxcZTvvvJ3miYDmnInlrablubTvvJ07MTk5OS8yMDAwOzIwMDAvMjAwMTsyMDAxLzIwMDI7MjAwMi8yMDAzOzIwMDMvMjAwNDsyMDA0LzIwMDU7MjAwNS8yMDA2OzIwMDYvMjAwNzsyMDA3LzIwMDg7MjAwOC8yMDA5OzIwMDkvMjAxMDsyMDEwLzIwMTE7MjAxMS8yMDEyOzIwMTIvMjAxMzsyMDEzLzIwMTQ7MjAxNC8yMDE1OzIwMTUvMjAxNjsyMDE2LzIwMTc7MjAxNy8yMDE4OzIwMTgvMjAxOTsyMDE5LzIwMjA7MjAyMC8yMDIxOzIwMjEvMjAyMjsyMDIyLzIwMjM7MjAyMy8yMDI0OzIwMjQvMjAyNTsyMDk4LzIwOTk7PjtAPFxlO++8neaJgOacieWtpuW5tO+8nTsxOTk5LzIwMDA7MjAwMC8yMDAxOzIwMDEvMjAwMjsyMDAyLzIwMDM7MjAwMy8yMDA0OzIwMDQvMjAwNTsyMDA1LzIwMDY7MjAwNi8yMDA3OzIwMDcvMjAwODsyMDA4LzIwMDk7MjAwOS8yMDEwOzIwMTAvMjAxMTsyMDExLzIwMTI7MjAxMi8yMDEzOzIwMTMvMjAxNDsyMDE0LzIwMTU7MjAxNS8yMDE2OzIwMTYvMjAxNzsyMDE3LzIwMTg7MjAxOC8yMDE5OzIwMTkvMjAyMDsyMDIwLzIwMjE7MjAyMS8yMDIyOzIwMjIvMjAyMzsyMDIzLzIwMjQ7MjAyNC8yMDI1OzIwOTgvMjA5OTs+Pjs+Ozs+O3Q8dDw7dDxpPDM2PjtAPO+8neaJgOacieivvueoi++8nTtD56iL5bqP6K6+6K6hO+Wkp+WtpuiLseivrULvvIjkuInnuqfvvInihaA76auY562J5pWw5a2mQeKFoDvlt6XnqIvlm77lraZD4oWgO+iuoeeul+acuuenkeWtpuWvvOiuuuKFoDvnprvmlaPmlbDlrabihaA7576O5a2m5qaC6K664oWgO+aAneaDs+mBk+W+t+S/ruWFu+S4juazleW+i+WfuuehgOKFoDvkvZPogrLihaA757q/5oCn5Luj5pWwQuKFoDtDKyvnqIvluo/orr7orqHihaA76Zi/6YeM5be05be055S15a2Q5ZWG5Yqh6K666K+B4oWgO+eoi+W6j+iuvuiuoeWkp+Wei+WunumqjDvlpKflrabniannkIZD4oWgO+Wkp+WtpueJqeeQhuWunumqjELihaA75aSn5a2m6Iux6K+tQu+8iOWbm+e6p++8ieKFoDvmpoLnjoforrrkuI7mlbDnkIbnu5/orqFC4oWgO+mrmOetieaVsOWtpkHihaE76K6h566X5py65Zu+5b2i5a2m4oWgO+S9k+iCsuKFoTvkvZPotKjlgaXlurforq3nu4PihaA75Lit5Zu96L+R546w5Luj5Y+y57qy6KaB4oWgO0phdmHnqIvluo/orr7orqHihaA75aSn5a2m55SfS0FC5Yib5Lia5Z+656GA4oWgO+Wkp+WtpueUn+iBjOS4muWPkeWxleS4juWwseS4muaMh+WvvO+8iOS4re+8ieKFoDvlpKflrabniannkIZD4oWhO+WkmuWqkuS9k+aKgOacr+KFoDvpq5jnuqfoi7Hor60x77yI6K+75YaZ6K+R77yJ4oWgO+mrmOe6p+iLseivrTHvvIjop4blkKzor7TvvInihaA76ams5YWL5oCd5Li75LmJ5Z+65pys5Y6f55CG4oWgO+WFqOeQg+WIm+aWsOWVhuS4muaooeW8j+eglOeptuKFoDvmlbDmja7nu5PmnoQo5Y+v6YCJ5Y+M6K+tKTvmlbDmja7nu5PmnoTlpKflnovlrp7pqow75L2T6IKy4oWiO+WGm+iure+8iOWGm+S6i+eQhuiuuu+8ieKFoDs+O0A877yd5omA5pyJ6K++56iL77ydO0PnqIvluo/orr7orqE75aSn5a2m6Iux6K+tQu+8iOS4iee6p++8ieKFoDvpq5jnrYnmlbDlraZB4oWgO+W3peeoi+WbvuWtpkPihaA76K6h566X5py656eR5a2m5a+86K664oWgO+emu+aVo+aVsOWtpuKFoDvnvo7lrabmpoLorrrihaA75oCd5oOz6YGT5b635L+u5YW75LiO5rOV5b6L5Z+656GA4oWgO+S9k+iCsuKFoDvnur/mgKfku6PmlbBC4oWgO0MrK+eoi+W6j+iuvuiuoeKFoDvpmL/ph4zlt7Tlt7TnlLXlrZDllYbliqHorrror4HihaA756iL5bqP6K6+6K6h5aSn5Z6L5a6e6aqMO+Wkp+WtpueJqeeQhkPihaA75aSn5a2m54mp55CG5a6e6aqMQuKFoDvlpKflraboi7Hor61C77yI5Zub57qn77yJ4oWgO+amgueOh+iuuuS4juaVsOeQhue7n+iuoULihaA76auY562J5pWw5a2mQeKFoTvorqHnrpfmnLrlm77lvaLlrabihaA75L2T6IKy4oWhO+S9k+i0qOWBpeW6t+iuree7g+KFoDvkuK3lm73ov5HnjrDku6Plj7LnurLopoHihaA7SmF2Yeeoi+W6j+iuvuiuoeKFoDvlpKflrabnlJ9LQULliJvkuJrln7rnoYDihaA75aSn5a2m55Sf6IGM5Lia5Y+R5bGV5LiO5bCx5Lia5oyH5a+877yI5Lit77yJ4oWgO+Wkp+WtpueJqeeQhkPihaE75aSa5aqS5L2T5oqA5pyv4oWgO+mrmOe6p+iLseivrTHvvIjor7vlhpnor5HvvInihaA76auY57qn6Iux6K+tMe+8iOinhuWQrOivtO+8ieKFoDvpqazlhYvmgJ3kuLvkuYnln7rmnKzljp/nkIbihaA75YWo55CD5Yib5paw5ZWG5Lia5qih5byP56CU56m24oWgO+aVsOaNrue7k+aehCjlj6/pgInlj4zor60pO+aVsOaNrue7k+aehOWkp+Wei+WunumqjDvkvZPogrLihaI75Yab6K6t77yI5Yab5LqL55CG6K6677yJ4oWgOz4+Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+PjtsPHJidG5YcTtyYnRuWG47cmJ0blhuOz4+1UlPjXyXKOip7gykbCFHc78KjgQ="));
			nvps.add(new BasicNameValuePair("ddlKc", "＝所有课程＝"));
			nvps.add(new BasicNameValuePair("ddlXq", term));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));
			httpclient.setCookieStore(cookieStore);
			response = httpclient.execute(httpost);
			entity = response.getEntity();
			if (entity != null) {
				String html = EntityUtils.toString(entity);

				html = html.replaceAll("&nbsp;", "0");

				Document doc = Jsoup.parse(html);
				Element masthead = doc.select("table#DataGrid1").get(0);
				Elements elements = doc.select("font[color=#000066]");

				totalnum = elements.size();
				for (int i = 0; i < elements.size(); i += 6) {
					Map cellMap = new HashMap();
					Course course = new Course();
					course.setTerm(elements.get(i).text().trim());
					course.setName(elements.get(i + 1).text().trim());
					course.setProperty(elements.get(i + 2).text().trim());
					course.setScore(elements.get(i + 3).text().trim());
					course.setTime(elements.get(i + 4).text().trim());
					course.setCredit(Double.parseDouble(elements.get(i + 5)
							.text().trim()));
					cellMap.put("id", Integer.valueOf(i));
					cellMap.put("cell", course);
					courses.add(cellMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return courses;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return courses;
	}

	public double[] termdata(String num, String pass, String term) {
		double[] results = new double[2];
		String scores = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpost = new HttpPost(
					"http://www.ycjw.zjut.edu.cn/logon.aspx");

			List nvps = new ArrayList();
			nvps.add(new BasicNameValuePair("Cbo_LX", "学生"));
			nvps.add(new BasicNameValuePair("Img_DL.x", "29"));
			nvps.add(new BasicNameValuePair("Img_DL.y", "13"));
			nvps.add(new BasicNameValuePair("Txt_UserName", num));
			nvps.add(new BasicNameValuePair("Txt_Password", pass));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"dDwyMDY5NjM4MDg7dDw7bDxpPDE+Oz47bDx0PDtsPGk8Mz47aTwxNT47PjtsPHQ8O2w8aTwxPjtpPDM+O2k8NT47aTw3PjtpPDk+O2k8MTE+O2k8MTM+O2k8MTU+O2k8MTc+Oz47bDx0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+O3Q8cDxwPGw8QmFja0ltYWdlVXJsOz47bDxodHRwOi8vd3d3Lnljancuemp1dC5lZHUuY24vL2ltYWdlcy9iZzEuZ2lmOz4+Oz47Oz47dDxwPHA8bDxCYWNrSW1hZ2VVcmw7PjtsPGh0dHA6Ly93d3cueWNqdy56anV0LmVkdS5jbi8vaW1hZ2VzL2JnMS5naWY7Pj47Pjs7Pjt0PHA8cDxsPEJhY2tJbWFnZVVybDs+O2w8aHR0cDovL3d3dy55Y2p3LnpqdXQuZWR1LmNuLy9pbWFnZXMvYmcxLmdpZjs+Pjs+Ozs+Oz4+O3Q8dDw7dDxpPDM+O0A8LS3nlKjmiLfnsbvlnostLTvmlZnluIg75a2m55SfOz47QDwtLeeUqOaIt+exu+Weiy0tO+aVmeW4iDvlrabnlJ87Pj47Pjs7Pjs+Pjs+PjtsPEltZ19ETDs+PnAOxH2tzqCdmyQ3NVSABVhcGj4g"));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();

			CookieStore cookieStore = httpclient.getCookieStore();

			EntityUtils.consume(entity);

			httpost = new HttpPost(
					"http://www.ycjw.zjut.edu.cn//stdgl/cxxt/cjcx/Cjcx_Xsgrcj.aspx");

			nvps = new ArrayList();
			nvps.add(new BasicNameValuePair("1", "rbtnXq"));
			nvps.add(new BasicNameValuePair("Button1", "普通考试成绩查询"));
			nvps.add(new BasicNameValuePair("__EVENTTARGET", ""));
			nvps.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			nvps.add(new BasicNameValuePair(
					"__VIEWSTATE",
					"dDw4Mjc4OTE1MTQ7dDw7bDxpPDE+Oz47bDx0PDtsPGk8NT47aTw3PjtpPDk+O2k8MTM+O2k8MTc+O2k8MjE+O2k8MzU+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPOWtpuWPt++8mjIwMDkyNjYzMDcxMzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya5rKI5aWH6bmPOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDznj63nuqfvvJrova/ku7blt6XnqIswOTA3Oz4+Oz47Oz47dDx0PDt0PGk8Mzc+O0A8XGU777yd5omA5pyJ5a2m5pyf77ydOzE5OTkvMjAwMCgxKTsxOTk5LzIwMDAoMik7MjAwMC8yMDAxKDEpOzIwMDAvMjAwMSgyKTsyMDAxLzIwMDIoMSk7MjAwMS8yMDAyKDIpOzIwMDIvMjAwMygxKTsyMDAyLzIwMDMoMik7MjAwMy8yMDA0KDEpOzIwMDMvMjAwNCgyKTsyMDA0LzIwMDUoMSk7MjAwNC8yMDA1KDIpOzIwMDUvMjAwNigxKTsyMDA1LzIwMDYoMik7MjAwNi8yMDA3KDEpOzIwMDYvMjAwNygyKTsyMDA3LzIwMDgoMSk7MjAwNy8yMDA4KDIpOzIwMDgvMjAwOSgxKTsyMDA4LzIwMDkoMik7MjAwOS8yMDEwKDEpOzIwMDkvMjAxMCgyKTsyMDEwLzIwMTEoMSk7MjAxMC8yMDExKDIpOzIwMTEvMjAxMigxKTsyMDExLzIwMTIoMik7MjAxMi8yMDEzKDEpOzIwMTIvMjAxMygyKTsyMDk4LzIwOTkoMSk7MjAxMy8yMDE0KDEpOzIwMTMvMjAxNCgyKTsyMDE0LzIwMTUoMSk7MjAxNC8yMDE1KDIpOzIwMTUvMjAxNigxKTsyMDE1LzIwMTYoMik7PjtAPFxlO++8neaJgOacieWtpuacn++8nTsxOTk5LzIwMDAoMSk7MTk5OS8yMDAwKDIpOzIwMDAvMjAwMSgxKTsyMDAwLzIwMDEoMik7MjAwMS8yMDAyKDEpOzIwMDEvMjAwMigyKTsyMDAyLzIwMDMoMSk7MjAwMi8yMDAzKDIpOzIwMDMvMjAwNCgxKTsyMDAzLzIwMDQoMik7MjAwNC8yMDA1KDEpOzIwMDQvMjAwNSgyKTsyMDA1LzIwMDYoMSk7MjAwNS8yMDA2KDIpOzIwMDYvMjAwNygxKTsyMDA2LzIwMDcoMik7MjAwNy8yMDA4KDEpOzIwMDcvMjAwOCgyKTsyMDA4LzIwMDkoMSk7MjAwOC8yMDA5KDIpOzIwMDkvMjAxMCgxKTsyMDA5LzIwMTAoMik7MjAxMC8yMDExKDEpOzIwMTAvMjAxMSgyKTsyMDExLzIwMTIoMSk7MjAxMS8yMDEyKDIpOzIwMTIvMjAxMygxKTsyMDEyLzIwMTMoMik7MjA5OC8yMDk5KDEpOzIwMTMvMjAxNCgxKTsyMDEzLzIwMTQoMik7MjAxNC8yMDE1KDEpOzIwMTQvMjAxNSgyKTsyMDE1LzIwMTYoMSk7MjAxNS8yMDE2KDIpOz4+O2w8aTwxPjs+Pjs7Pjt0PHQ8O3Q8aTwyOT47QDxcZTvvvJ3miYDmnInlrablubTvvJ07MTk5OS8yMDAwOzIwMDAvMjAwMTsyMDAxLzIwMDI7MjAwMi8yMDAzOzIwMDMvMjAwNDsyMDA0LzIwMDU7MjAwNS8yMDA2OzIwMDYvMjAwNzsyMDA3LzIwMDg7MjAwOC8yMDA5OzIwMDkvMjAxMDsyMDEwLzIwMTE7MjAxMS8yMDEyOzIwMTIvMjAxMzsyMDEzLzIwMTQ7MjAxNC8yMDE1OzIwMTUvMjAxNjsyMDE2LzIwMTc7MjAxNy8yMDE4OzIwMTgvMjAxOTsyMDE5LzIwMjA7MjAyMC8yMDIxOzIwMjEvMjAyMjsyMDIyLzIwMjM7MjAyMy8yMDI0OzIwMjQvMjAyNTsyMDk4LzIwOTk7PjtAPFxlO++8neaJgOacieWtpuW5tO+8nTsxOTk5LzIwMDA7MjAwMC8yMDAxOzIwMDEvMjAwMjsyMDAyLzIwMDM7MjAwMy8yMDA0OzIwMDQvMjAwNTsyMDA1LzIwMDY7MjAwNi8yMDA3OzIwMDcvMjAwODsyMDA4LzIwMDk7MjAwOS8yMDEwOzIwMTAvMjAxMTsyMDExLzIwMTI7MjAxMi8yMDEzOzIwMTMvMjAxNDsyMDE0LzIwMTU7MjAxNS8yMDE2OzIwMTYvMjAxNzsyMDE3LzIwMTg7MjAxOC8yMDE5OzIwMTkvMjAyMDsyMDIwLzIwMjE7MjAyMS8yMDIyOzIwMjIvMjAyMzsyMDIzLzIwMjQ7MjAyNC8yMDI1OzIwOTgvMjA5OTs+Pjs+Ozs+O3Q8dDw7dDxpPDM2PjtAPO+8neaJgOacieivvueoi++8nTtD56iL5bqP6K6+6K6hO+Wkp+WtpuiLseivrULvvIjkuInnuqfvvInihaA76auY562J5pWw5a2mQeKFoDvlt6XnqIvlm77lraZD4oWgO+iuoeeul+acuuenkeWtpuWvvOiuuuKFoDvnprvmlaPmlbDlrabihaA7576O5a2m5qaC6K664oWgO+aAneaDs+mBk+W+t+S/ruWFu+S4juazleW+i+WfuuehgOKFoDvkvZPogrLihaA757q/5oCn5Luj5pWwQuKFoDtDKyvnqIvluo/orr7orqHihaA76Zi/6YeM5be05be055S15a2Q5ZWG5Yqh6K666K+B4oWgO+eoi+W6j+iuvuiuoeWkp+Wei+WunumqjDvlpKflrabniannkIZD4oWgO+Wkp+WtpueJqeeQhuWunumqjELihaA75aSn5a2m6Iux6K+tQu+8iOWbm+e6p++8ieKFoDvmpoLnjoforrrkuI7mlbDnkIbnu5/orqFC4oWgO+mrmOetieaVsOWtpkHihaE76K6h566X5py65Zu+5b2i5a2m4oWgO+S9k+iCsuKFoTvkvZPotKjlgaXlurforq3nu4PihaA75Lit5Zu96L+R546w5Luj5Y+y57qy6KaB4oWgO0phdmHnqIvluo/orr7orqHihaA75aSn5a2m55SfS0FC5Yib5Lia5Z+656GA4oWgO+Wkp+WtpueUn+iBjOS4muWPkeWxleS4juWwseS4muaMh+WvvO+8iOS4re+8ieKFoDvlpKflrabniannkIZD4oWhO+WkmuWqkuS9k+aKgOacr+KFoDvpq5jnuqfoi7Hor60x77yI6K+75YaZ6K+R77yJ4oWgO+mrmOe6p+iLseivrTHvvIjop4blkKzor7TvvInihaA76ams5YWL5oCd5Li75LmJ5Z+65pys5Y6f55CG4oWgO+WFqOeQg+WIm+aWsOWVhuS4muaooeW8j+eglOeptuKFoDvmlbDmja7nu5PmnoQo5Y+v6YCJ5Y+M6K+tKTvmlbDmja7nu5PmnoTlpKflnovlrp7pqow75L2T6IKy4oWiO+WGm+iure+8iOWGm+S6i+eQhuiuuu+8ieKFoDs+O0A877yd5omA5pyJ6K++56iL77ydO0PnqIvluo/orr7orqE75aSn5a2m6Iux6K+tQu+8iOS4iee6p++8ieKFoDvpq5jnrYnmlbDlraZB4oWgO+W3peeoi+WbvuWtpkPihaA76K6h566X5py656eR5a2m5a+86K664oWgO+emu+aVo+aVsOWtpuKFoDvnvo7lrabmpoLorrrihaA75oCd5oOz6YGT5b635L+u5YW75LiO5rOV5b6L5Z+656GA4oWgO+S9k+iCsuKFoDvnur/mgKfku6PmlbBC4oWgO0MrK+eoi+W6j+iuvuiuoeKFoDvpmL/ph4zlt7Tlt7TnlLXlrZDllYbliqHorrror4HihaA756iL5bqP6K6+6K6h5aSn5Z6L5a6e6aqMO+Wkp+WtpueJqeeQhkPihaA75aSn5a2m54mp55CG5a6e6aqMQuKFoDvlpKflraboi7Hor61C77yI5Zub57qn77yJ4oWgO+amgueOh+iuuuS4juaVsOeQhue7n+iuoULihaA76auY562J5pWw5a2mQeKFoTvorqHnrpfmnLrlm77lvaLlrabihaA75L2T6IKy4oWhO+S9k+i0qOWBpeW6t+iuree7g+KFoDvkuK3lm73ov5HnjrDku6Plj7LnurLopoHihaA7SmF2Yeeoi+W6j+iuvuiuoeKFoDvlpKflrabnlJ9LQULliJvkuJrln7rnoYDihaA75aSn5a2m55Sf6IGM5Lia5Y+R5bGV5LiO5bCx5Lia5oyH5a+877yI5Lit77yJ4oWgO+Wkp+WtpueJqeeQhkPihaE75aSa5aqS5L2T5oqA5pyv4oWgO+mrmOe6p+iLseivrTHvvIjor7vlhpnor5HvvInihaA76auY57qn6Iux6K+tMe+8iOinhuWQrOivtO+8ieKFoDvpqazlhYvmgJ3kuLvkuYnln7rmnKzljp/nkIbihaA75YWo55CD5Yib5paw5ZWG5Lia5qih5byP56CU56m24oWgO+aVsOaNrue7k+aehCjlj6/pgInlj4zor60pO+aVsOaNrue7k+aehOWkp+Wei+WunumqjDvkvZPogrLihaI75Yab6K6t77yI5Yab5LqL55CG6K6677yJ4oWgOz4+Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+PjtsPHJidG5YcTtyYnRuWG47cmJ0blhuOz4+1UlPjXyXKOip7gykbCFHc78KjgQ="));
			nvps.add(new BasicNameValuePair("ddlKc", "＝所有课程＝"));
			nvps.add(new BasicNameValuePair("ddlXq", term));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, "gb2312"));

			httpclient.setCookieStore(cookieStore);
			response = httpclient.execute(httpost);
			entity = response.getEntity();
			double goals = 0D;
			double credit = 0D;

			if (entity != null) {
				String html = EntityUtils.toString(entity);

				html = html.replaceAll("&nbsp;", "0");

				Document doc = Jsoup.parse(html);
				Element masthead = doc.select("table#DataGrid1").get(0);

				scores = masthead.toString();
				Elements elements = doc.select("font[color=#000066]");

				for (int i = 0; i < elements.size(); i += 6) {
					if (elements.get(i + 2).text().trim().equals("普通专业")) {
						double cre = Double.parseDouble(elements.get(i + 5)
								.text().trim());

						if (elements.get(i + 3).text().trim().equals("优秀")) {
							goals += 4.5D * cre;
						} else if (elements.get(i + 3).text().trim()
								.equals("良好")) {
							goals += 3.5D * cre;
						} else if (elements.get(i + 3).text().trim()
								.equals("中等")) {
							goals += 2.5D * cre;
						} else if (elements.get(i + 3).text().trim()
								.equals("及格")) {
							goals += 1.5D * cre;
						} else if (elements.get(i + 3).text().trim()
								.equals("不及格")) {
							goals += 0D;
						} else {
							goals = goals
									+ (Double.parseDouble(elements.get(i + 3)
											.text().trim()) - 50.0D) / 10.0D
									* cre;
						}

						credit = credit
								+ Double.parseDouble(elements.get(i + 5).text()
										.trim());
					}

				}

				if (credit != 0) {
					results[0] = goals;
					results[1] = credit;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return results;
	}
}