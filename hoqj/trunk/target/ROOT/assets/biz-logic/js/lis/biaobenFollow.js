display("shoptype,shoparea"); // 需要翻译的类型
var shopSubclass = null;
var shopCity = null;
$(function() {
	var grid = $('#grid')._datagrid({
		checkOnSelect : false,
		selectOnCheck : false,
		url : "lis_biaobenFollow/list.html", 
		onBeforeLoad : function(param) {
			//实现不进行页面数据初始化
			var firstLoad =$(this).attr("firstLoad");
			if(firstLoad == "false" || typeof(firstLoad)=="undefined"){
				$(this).attr("firstLoad","true");
				return false;
			}
			if (!$('#queryForm').form('validate')) {
				return false;
			}
			var queryParams = $('#queryForm')._formToJson();
			for (i in queryParams) {
				param[i] = queryParams[i];
			}}
		/*,
		toolbar : [ {
			text : '加号',
			iconCls : 'icon-add',
			handler : handler_add
		} ]*/
	});
	$("#queryButton").bind("click",function(){
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
	}
};
