<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("num") == null) {
		response.sendRedirect("login.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绩点查询计算</title>
<link rel="stylesheet" href="css/flexigrid.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="js/flexigrid.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.alert.js"></script>
<script type="text/javascript" src="js/gridforindex.js"></script>
<script type="text/javascript" src="js/facebox.js"></script>
<link rel="stylesheet" href="css/alert.css" type="text/css"></link>
<link rel="stylesheet" href="css/style.css" type="text/css"></link>
<link rel="stylesheet" href="css/facebox.css" type="text/css"></link>
<style type="text/css">
.flexigrid div.fbutton .cal {
	background: url(css/images/last.gif) no-repeat center left;
}

.flexigrid div.fbutton .autocal {
	background: url(css/images/load.png) no-repeat center left;
}
</style>
<body>
	<div id="wrap">
		<div id="content">
			<div id="top"></div>
			<div class="header">
				<h1>
					浙工大学生绩点查询系统 <a>(beta 1.5)</a>
				</h1>
				<div id="logo"></div>
				<h2>Automatic GPA Calculate System For ZJUT</h2>
			</div>

			<div id="main">
				<div align="center">
					<table id="flex1" style="display: none"></table>
					<img src="css/images/iconindex.GIF"><a href="login.jsp">返回登录界面</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
						src="css/images/iconexcel.GIF"><a href="ExportExcel">导出excel</a>
					<div id="know">
						<a href="#mydiv" rel="facebox"><img
							src="css/images/iconreadme.GIF">使用说明(必看)</a>
					</div>
				</div>
			</div>
		</div>
		<div id="clear"></div>
		<div id="bottom"></div>
	</div>
	<div id="mydiv" style="display: none">
		本系统用于计算平均学分绩点(GPA, GradePointAverage)，算法采用我们学校统一的算法，即：<br> 绩点 =
		(成绩 - 50)/10 &nbsp;&nbsp;&nbsp;&nbsp;(成绩小于60分绩点为0)<br> 课程学分绩点=绩点
		* 学分数<br> GPA = 课程学分绩点总和 / 课程学分总和<br> 部分课程的五级分值换算成百分制如下：<br>
		<div id="cal" align="center">
			<table width="200" border="1">
				<tbody>
					<tr>
						<td>百分制</td>
						<td>五级分制</td>
						<td>折算百分制</td>
					</tr>
					<tr>
						<td>90-100</td>
						<td>优</td>
						<td>95</td>
					</tr>
					<tr>
						<td>80-89</td>
						<td>良</td>
						<td>85</td>
					</tr>
					<tr>
						<td>70-79</td>
						<td>中</td>
						<td>75</td>
					</tr>
					<tr>
						<td>60-69</td>
						<td>及格</td>
						<td>65</td>
					</tr>
					<tr>
						<td>60以下</td>
						<td>不及格</td>
						<td>59</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h3>使用说明</h3>
		<div>
			<img src="css/images/001.gif"><br>1.点击后即可根据默认学分算法计算出平均绩点。<br>
			<img src="css/images/002.gif"><br>2.选择你想要计算的课程后再点击此即可算出对应的平均绩点。<br>
			<img src="css/images/003.gif"><br>3.将你所查询的成绩导出成excel文件提供下载。<br>
		</div>
		<h3>支持</h3>
		<div>欢迎提交建议，多多找BUG，提升本系统的可靠性。<br>
		建议提交地址：<a href="http://www.sqroc.net/gpa.html" target="_blank">http://www.sqroc.net/gpa.html</a><br>
		Email:sqrocer@gmail.com<br>
		Thanks for your support.</div>
	</div>
	<div id="footer">Design by Sqroc</div>
</body>
</html>