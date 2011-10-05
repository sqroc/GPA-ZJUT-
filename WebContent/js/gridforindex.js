$("document").ready(function() {
	$("#flex1").flexigrid({
		url : 'GetData',
		dataType : 'json',
		method : 'POST',
		colModel : [ {
			display : '实际学期',
			name : 'term',
			width : 100,
			align : 'center'
		}, {
			display : '执行课程名称',
			name : 'name',
			width : 295,
			align : 'center'
		}, {
			display : '考试性质',
			name : 'property',
			width : 60,
			align : 'center'
		}, {
			display : '成绩',
			name : 'score',
			width : 50,
			align : 'center',
			hide : false
		}, {
			display : '学时',
			name : 'time',
			width : 50,
			align : 'center'
		}, {
			display : '学分',
			name : 'credit',
			width : 50,
			align : 'center'
		} ],
		buttons : [ {
			name : '自动计算平均绩点',
			bclass : 'autocal',
			onpress : toolbarItem
		}, {
			separator : true
		}, {
			name : '计算选中的课程的平均绩点',
			bclass : 'cal',
			onpress : toolbarItem
		}, {
			separator : true
		} ],
		sortname : "term",
		sortorder : "asc",
		usepager : false,
		title : '成绩列表',
		useRp : true,
		rp : 15,
		showTableToggleBtn : true,
		procmsg : '请等待,数据正在加载中 …',
		width : 700,
		height : 200
	})
});

function toolbarItem(com, grid) {
	if (com == '计算选中的课程的平均绩点') {
		if ($('.trSelected', $('#flex1')).length == 0) {
			hiAlert("请选择至少一个数据", "提示");
		} else {
			var scores = "";
			var credits = "";
			$('.trSelected td:nth-child(4)', grid).each(function() {
				scores += "," + $(this).text();
			});
			$('.trSelected td:nth-child(6)', grid).each(function() {
				credits += "," + $(this).text();
			});
			scores = scores.substring(1);
			credits = credits.substring(1);
			$.ajax({
				type : "POST",
				url : "Calculate",
				data : "scores=" + scores + "&credits=" + credits,
				dataType : "text",
				success : function(msg) {
					hiAlert(msg, "计算结果");
				},
			});
		}
	} else if (com == '自动计算平均绩点') {
		$.ajax({
			type : "POST",
			url : "AutoCalculate",
			data : "",
			dataType : "text",
			success : function(msg) {
				hiAlert(msg, "自动计算结果");
			},
		});
	}
}

jQuery(document).ready(function($) {
	$('a[rel*=facebox]').facebox();
});