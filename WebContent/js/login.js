$("document").ready(
		function() {
			var selector = $("#term");
			nowdate = new Date().getFullYear();
			for ( var i = 3; i >= 0; i--) {
				selector.append('<option value="' + "Y," + (nowdate - (i + 1))
						+ "/" + (nowdate - i) + ",学年" + '">'
						+ (nowdate - (i + 1)) + "/" + (nowdate - i) + "学年"
						+ '</option>');
			}
			for ( var i = 3; i >= 0; i--) {
				selector.append('<option value="' + (nowdate - (i + 1)) + "/"
						+ (nowdate - i) + "(1)" + '">' + (nowdate - (i + 1))
						+ "/" + (nowdate - i) + "(1)" + '</option>');
				selector.append('<option value="' + (nowdate - (i + 1)) + "/"
						+ (nowdate - i) + "(2)" + '">' + (nowdate - (i + 1))
						+ "/" + (nowdate - i) + "(2)" + '</option>');
			}
		});

function check() {
	pass = true;
	$(function() {
		if ($('input[name=time]').val() == "") {
			hiAlert("请选择所要查询的时间~~", "提示");
			pass = false;
		}
	});
	return pass;
}