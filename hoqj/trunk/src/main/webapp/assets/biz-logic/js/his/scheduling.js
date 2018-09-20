display("shoptype,shoparea"); // 需要翻译的类型
var shopSubclass = null;
var shopCity = null;
$(function() {
	var grid = $('#grid')._datagrid({
		checkOnSelect : false,
		selectOnCheck : false,
		url : "scheduling/list.html", 
		onBeforeLoad : function(param) {
			if (!$('#queryForm').form('validate')) {
				return false;
			}
			var queryParams = $('#queryForm')._formToJson();
			for (i in queryParams) {
				param[i] = queryParams[i];
			}
		},
		toolbar : [ {
			text : '加号',
			iconCls : 'icon-add',
			handler : handler_add
		},{
			text : '修改挂号起止时间',
			iconCls : 'icon-edit',
			handler : handler_edit
		} ]
	});
	// 号源新增弹出框ghTimeEditDialog
	$('#schedulingAddDialog').dialog({
		buttons : [ {
			text : '加号',
			handler : function() {
				if (!$('#schedulingAddForm').form('validate')) {
					return;
				}
				if ($("[name=ghdeptId]").val() == "") {
					$.messager.alert('操作提示', '请选择科室！', 'error');
					return;
				}
				if ($("#ghdocId").val() == "") {
					$.messager.alert('操作提示', '请选择医生！', 'error');
					return;
				}
				if ($("#ghnumId").val() == "") {
					$.messager.alert('操作提示', '请选择加号数量！', 'error');
					return;
				}
				$.messager.confirm('加号确认', "确认对"+$("#ghdocId").find("option:selected").text()+"医生加"+$("#ghnumId").val()+"个号？", function(r) {
				    if (r) {
				    	$.ajax({
							type : 'post',
							url : 'scheduling/addGH',
							data : 'ghdeptId=' + $("[name=ghdeptId]").val() + '&docId=' + $("#ghdocId").val()+'&ghnum='+$("#ghnumId").val(),
							dataType : 'json',
							async : false,
							success : function(r) {
								if (r.r) {
									$.messager.alert('操作提示', '加号成功！', 'info');
									$('#schedulingAddDialog').dialog('close');
									$('#grid').datagrid('reload');
								} else {
									$.messager.alert('操作提示', r.m, 'error');
								}
							}
						});
				    }
				  });
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#schedulingAddDialog').dialog('close');
			}
		} ]
	});
	// 挂号起止时间修改弹出框
	$('#ghTimeEditDialog').dialog({
		buttons : [ {
			text : '保存',
			handler : function() {
				if (!$('#ghTimeEditForm').form('validate')) {
					return;
				}
				if ($("#c_value").val() == "") {
					$.messager.alert('操作提示', '请输入挂号起止时间！', 'error');
					return;
				}
				$.messager.confirm('挂号起止时间修改确认', "确认将挂号起止时间修改为"+$("#c_value").val()+"？", function(r) {
				    if (r) {
				    	$.ajax({
							type : 'post',
							url : 'scheduling/editGhTime',
							data : 'c_code=' + $("#c_code").val() + '&c_value=' + $("#c_value").val(),
							dataType : 'json',
							async : false,
							success : function(r) {
								if (r.r) {
									$.messager.alert('操作提示', '修改成功！', 'info');
									$('#ghTimeEditDialog').dialog('close');
									$('#grid').datagrid('reload');
								} else {
									$.messager.alert('操作提示', r.m, 'error');
								}
							}
						});
				    }
				  });
			}
		}, {
			text : '关闭',
			handler : function() {
				$('#ghTimeEditDialog').dialog('close');
			}
		} ]
	});
	/* 新增号源 */
	function handler_add() {
		$('#schedulingAddForm').attr('action', 'scheduling/addGH').resetForm();
		$('#id').val('');
		$('#schedulingAddDialog').dialog('open').dialog("setTitle", "排队叫号加号");
	}
	/* 修改挂号起止时间 */
	function handler_edit() {
		$('#ghTimeEditForm').attr('action', 'scheduling/editGhTime').resetForm();
		$('#id').val('');
		$.ajax({
			type : 'post',
			url : 'scheduling/getGhTime',
			data : null,
			dataType : 'json',
			async : false,
			success : function(r) {
				if (r.r) {
					var d=r.d;
					$("#c_code").val(d.c_code);
					$("#c_name").val(d.c_name);
					$("#c_value").val(d.c_value);
					$('#schedulingAddDialog').dialog('close');
				} else {
					$.messager.alert('操作提示', r.m, 'error');
				}
			}
		});
		$('#ghTimeEditDialog').dialog('open').dialog("setTitle", "修改挂号起止时间");
	}
	$('#queryButton').click(function() {
		var params = $('#queryForm')._formToJson();
		$(grid).datagrid('load', params);
	});
});
var formatter = {
	status : function(value, rowData, rowIndex) {
		if (value == '1') {
			return '<font color=green>已挂号</font>';
		}else if(value == '0'){
			return '<font color=red>未挂号</font>';
		}else if(value == '-1'){
			return '<font color=red>已停诊</font>';
		}
	},
	opt : function(value, rowData, rowIndex) {
		var html = '<a class="spacing a-blue" onclick="updShop(' + rowIndex
				+ ');" href="javascript:void(0);">修改</a>';
		html += '<a class="spacing a-green" onclick="resetPassword(' + rowIndex
				+ ');" href="javascript:void(0);">重置密码</a>';
		html += '<a class="spacing a-blue" onclick="resetPosPassword('
				+ rowIndex + ');" href="javascript:void(0);">重置pos密码</a>';
		html += '<a class="spacing a-green" onclick="resetRefundPassword('
				+ rowIndex + ');" href="javascript:void(0);">重置退款密码</a>';
		return html;
	}
};


//根据科室id联动医生
//function chooseDoc(){
	var deptId = $("[name='ghdeptId']").val();
//	alert(deptId);
//	$._ajaxPost('scheduling/getDocByDeptId', {
//		deptId : deptId
//	}, function(r) {
//		if (r.r) {
//			$("#ghdocId").empty();
//			$("#ghdocId").append("<option value=''>==请选择==</option>");
//			var list = r.d;
//			for (var i = 0; i < list.length; i++) {
//				$("#ghdocId").append(
//						"<option value='" + $.trim(list[i].c_id) + "'>" + list[i].c_name
//								+ "</option>");
//			}
////			$('#shopCity').val(shopCity);
//			$('#ghdocId')._pullDownList('refresh');
//		} else {
//			$.messager.alert('操作提示', r.m, 'error');
//		}
//	});
//}

function getDocsByDeptId(){
	var deptId = $("[name='ghdeptId']").val();
	$._ajaxPost('scheduling/getDocByDeptId', {
		deptId : deptId
	}, function(r) {
		if (r.r) {
			$("#ghdocId").empty();
			$("#ghdocId").append("<option value=''>==请选择==</option>");
			var list = r.d;
			for (var i = 0; i < list.length; i++) {
				$("#ghdocId").append(
						"<option value='" + $.trim(list[i].c_id) + "'>" + list[i].c_name
								+ "</option>");
			}
			$('#ghdocId')._pullDownList('refresh');
		} else {
			$.messager.alert('操作提示', r.m, 'error');
		}
	});
}


// google定位获取经纬度信息
function google() {
	var address = $("#shopAddress").val();
	if (address != "" && address != null) {
		var url = "http://ditu.google.cn/maps/api/geocode/json?address="
				+ encodeURIComponent(address) + "&sensor=false" + "&randomNum="
				+ Math.random();
		$.ajax({
			url : url,
			dataType : 'json',
			success : function(data) {
				if (data.status == 'OK') {
					// 经度
					var lng = data.results[0].geometry.location.lng;
					$("#shopLog").val(lng);
					// 纬度
					var lat = data.results[0].geometry.location.lat;
					$("#shopLat").val(lat);
				} else {
					$("#shopLat").val("");
					$("#shopLog").val("");
					$.messager.alert('操作提示', "没找到你要查询的位置，请重新输入！", 'error');
				}
			},
			error : function() {
				$("#shopLat").val("");
				$("#shopLog").val("");
				$.messager.alert('操作提示', "网络繁忙，请重试！", 'error');
			}
		});
	} else {
		$.messager.alert('操作提示', "请输入需要定位的商家地址,谢谢！", 'error');
	}
}
$(function() {
	// 添加图片
	$("#phoAdd").click(function() {
		$("#fileImage").click();
	});
	var params = {
		fileInput : $("#fileImage").get(0),
		upButton : "",
		url : 'document/upload-img.do',// 上传地址
		filter : function(files) {
			var arrFiles = [];
			for (var i = 0, file; file = files[i]; i++) {
				if (file.type.indexOf("image") == 0) {
					if (file.size >= 5120000) {
						$.messager.alert('操作提示', 'Picture "' + file.name
								+ '" too big.', 'error');
					} else {
						arrFiles.push(file);
					}
				} else {
					$.messager.alert('操作提示', 'File "' + file.name
							+ '" is not a picture.', 'error');
				}
			}
			return arrFiles;
		},
		onSelect : function(files) {
			var i = 0;
			var funAppendImage = function() {
				file = files[i];
				if (file) {
					var reader = new FileReader()
					reader.onload = function(e) {
						$("#imagePhoto").attr("src", e.target.result);
						i++;
						funAppendImage();
					}
					reader.readAsDataURL(file);
				} else {
					ZXXFILE.funUploadFile();
					ZXXFILE.fileFilter = [];
				}
			};
			funAppendImage();
		},
		onProgress : function(file, loaded, total) {

		},
		onSuccess : function(file, response) {
			var obj = eval("(" + response + ")");
			var dataId = obj['d']['fileToUpload']['fileId'];
			$("#shopCover").val(dataId);
			$("#imagePhoto").attr("src", 'document/file-' + dataId + '.do');
		},
		onFailure : function(file) {

		},
		onComplete : function() {
			// file控件value置空
			$("#fileImage").val("");
		}
	};
	ZXXFILE = $.extend(ZXXFILE, params);
	ZXXFILE.init();
});
/** 导出 */
$(function() {
	$('#excelExport')
			.click(
					function() {
						var shopName = $("#shopName").val();
						var id = $("#id").val();
						var channelName = $("#channelName").val();
						var shopType = $("#shopType1").val();
						var state = $("#state").val();
						var shopSubclass = $("#shopSubclass1").val();
						var payEnable = $("#payEnable").val();
						var channelId = $("#channelId").val();
						document.location.href = "/businessExport.html?shopName="
								+ shopName
								+ "&id="
								+ id
								+ "&channelName="
								+ channelName
								+ "&shopType="
								+ shopType
								+ "&state="
								+ state
								+ "&shopSubclass="
								+ shopSubclass
								+ "&payEnable="
								+ payEnable
								+ "&channelId=" + channelId;
					});
});

// 拼接营业时间方法
function addHours() {
	var data = "";
	var time1 = document.getElementById("time1");
	var time2 = document.getElementById("time2");
	var time3 = document.getElementById("time3");
	var time4 = document.getElementById("time4");
	var time5 = document.getElementById("time5");
	var time6 = document.getElementById("time6");
	var time7 = document.getElementById("time7");
	if (time1.checked) {
		data += "1" + "," + document.getElementById("startTime1").value + ","
				+ document.getElementById("endTime1").value + "=";
	}
	if (time2.checked) {
		data += "2" + "," + document.getElementById("startTime2").value + ","
				+ document.getElementById("endTime2").value + "=";
	}
	if (time3.checked) {
		data += "3" + "," + document.getElementById("startTime3").value + ","
				+ document.getElementById("endTime3").value + "=";
	}
	if (time4.checked) {
		data += "4" + "," + document.getElementById("startTime4").value + ","
				+ document.getElementById("endTime4").value + "=";
	}
	if (time5.checked) {
		data += "5" + "," + document.getElementById("startTime5").value + ","
				+ document.getElementById("endTime5").value + "=";
	}
	if (time6.checked) {
		data += "6" + "," + document.getElementById("startTime6").value + ","
				+ document.getElementById("endTime6").value + "=";
	}
	if (time7.checked) {
		data += "7" + "," + document.getElementById("startTime7").value + ","
				+ document.getElementById("endTime7").value;
	}
	document.getElementById("businessHour").value = data;
}

// 批量选择营业时间显示model
function addHour() {
	var shezhi = document.getElementById("shezhi");
	if (shezhi.style.display == "none") {
		shezhi.style.display = "";
	} else {
		shezhi.style.display = "none";
	}
}
// 确认批量设置时间
function saveHours() {
	var t1 = document.getElementById("t1");
	var t2 = document.getElementById("t2");
	var t3 = document.getElementById("t3");
	var t4 = document.getElementById("t4");
	var t5 = document.getElementById("t5");
	var t6 = document.getElementById("t6");
	var t7 = document.getElementById("t7");
	var time1 = document.getElementById("time1");
	var time2 = document.getElementById("time2");
	var time3 = document.getElementById("time3");
	var time4 = document.getElementById("time4");
	var time5 = document.getElementById("time5");
	var time6 = document.getElementById("time6");
	var time7 = document.getElementById("time7");
	var start = $("#startTime").val();
	var end = $("#endTime").val();
	if (t1.checked) {
		$("#time1").attr("checked", true);
		$("#startTime1").val(start);
		$("#endTime1").val(end);
	} else {
		if (!time1.checked) {
			$("#time1").attr("checked", false);
			$("#startTime1").val("");
			$("#endTime1").val("");
		}
	}

	if (t2.checked) {
		$("#time2").attr("checked", true);
		$("#startTime2").val(start);
		$("#endTime2").val(end);
	} else {
		if (!time2.checked) {
			$("#time2").attr("checked", false);
			$("#startTime2").val("");
			$("#endTime2").val("");
		}
	}

	if (t3.checked) {
		$("#time3").attr("checked", true);
		$("#startTime3").val(start);
		$("#endTime3").val(end);
	} else {
		if (!time3.checked) {
			$("#time3").attr("checked", false);
			$("#startTime3").val("");
			$("#endTime3").val("");
		}
	}

	if (t4.checked) {
		$("#time4").attr("checked", true);
		$("#startTime4").val(start);
		$("#endTime4").val(end);
	} else {
		if (!time4.checked) {
			$("#time4").attr("checked", false);
			$("#startTime4").val("");
			$("#endTime4").val("");
		}
	}

	if (t5.checked) {
		$("#time5").attr("checked", true);
		$("#startTime5").val(start);
		$("#endTime5").val(end);
	} else {
		if (!time5.checked) {
			$("#time5").attr("checked", false);
			$("#startTime5").val("");
			$("#endTime5").val("");
		}
	}

	if (t6.checked) {
		$("#time6").attr("checked", true);
		$("#startTime6").val(start);
		$("#endTime6").val(end);
	} else {
		if (!time6.checked) {
			$("#time6").attr("checked", false);
			$("#startTime6").val("");
			$("#endTime6").val("");
		}
	}

	if (t7.checked) {
		$("#time7").attr("checked", true);
		$("#startTime7").val(start);
		$("#endTime7").val(end);
	} else {
		if (!time7.checked) {
			$("#time7").attr("checked", false);
			$("#startTime7").val("");
			$("#endTime7").val("");
		}
	}
	// 清空操作
	$("#t1").attr("checked", false);
	$("#t2").attr("checked", false);
	$("#t3").attr("checked", false);
	$("#t4").attr("checked", false);
	$("#t5").attr("checked", false);
	$("#t6").attr("checked", false);
	$("#t7").attr("checked", false);
	$("#startTime").val("");
	$("#endTime").val("");
}