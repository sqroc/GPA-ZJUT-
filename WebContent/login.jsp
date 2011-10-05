<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>浙江工业大学学生绩点查询系统</title>
<link rel="stylesheet" href="css/flexigrid.css" type="text/css"></link>
<script type="text/javascript" src="js/jquery-1.3.2.js"></script>
<script type="text/javascript" src="js/flexigrid.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jquery.alert.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<link rel="stylesheet" href="css/alert.css" type="text/css"></link>
<link rel="stylesheet" href="css/style.css" type="text/css"></link>
</head>
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
				<form action="CalServlet" method="post" onsubmit="return check();">
					<table width="100%" height="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tbody>
							<tr>
								<td align="center" valign="middle"><table width="500"
										border="0" cellpadding="3" cellspacing="1" bgcolor="#333333">
										<tbody>
											<tr>
												<td height="39" colspan="2" bgcolor="#85ACF7"><p
														style="text-align: center; font-size: 15px; margin-top: 5px; font-weight: bold; color: #FFFFFF">登录信息</p>
												</td>
											</tr>
											<tr>
												<td width="22%" height="26" align="center" bgcolor="#85ACF7"
													class="a1">查询学号：</td>
												<td width="78%" bgcolor="#FFFFFF"><input name="num"
													class="input" id="userid" type="text">
												</td>
											</tr>
											<tr>
												<td height="26" align="center" class="a1" bgcolor="#85ACF7">查询密码：</td>
												<td bgcolor="#FFFFFF"><input name="pass" class="input"
													id="passwd" type="password">
												</td>
											</tr>

											<tr>
												<td height="26" align="center" class="a1" bgcolor="#85ACF7">查询学期：</td>
												<td bgcolor="#FFFFFF"><select name="term" id="term">
														<option selected="selected" value="＝所有学期＝">＝所有学期＝</option>
												</select></td>
											</tr>
											<tr>
												<td height="26" colspan="2" align="center" bgcolor="#FFFFFF"><label>
														<input type="submit" name="submit" id="button"
														value="绩点查询"> <%
 	if (request.getAttribute("error") != null) {
 		out.println("<span style=\"text-align: center;color: red\">【提示】：用户名或密码错误！</span>");
 	}
 %> </label></td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div id="clear"></div>
		<div id="bottom"></div>
	</div>
	<div id="footer">Design by Sqroc</div>
</body>
</html>