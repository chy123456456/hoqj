if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '  共{pages} 页';
	$.fn.pagination.defaults.displayMsg = '显示第 {from} 到 {to}, 共 {total} 条记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = 'Please wait。。。';
}
if ($.fn.treegrid && $.fn.datagrid){
	$.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
}
if ($.messager){
	$.messager.defaults.ok = '确认';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = 'This entry is required';
	$.fn.validatebox.defaults.rules.email.message = 'Please enter a valid email address';
	$.fn.validatebox.defaults.rules.url.message = 'Please enter a valid URL address';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = 'This entry is required';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = 'This entry is required';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = 'This entry is required';
}
if ($.fn.combogrid){
	$.fn.combogrid.defaults.missingMessage = 'This entry is required';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ["日","一","二","三","四","五","六"];
	$.fn.calendar.defaults.months = ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确认';
	$.fn.datebox.defaults.missingMessage = 'This entry is required';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}