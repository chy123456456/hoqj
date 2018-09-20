var com = {
  ym : {
    base : {
      util : {
        datagrid : {},
        tester : {},
        logger : {},
        exportUtil: {},
        listUtil : {}
      }
    }
  }
};

com.ym.base.util.exportUtil._exportURL = "/export/export.html";

com.ym.base.util.tester.assert = function(expressions){
  try{
    if (console && console.assert){
      console.assert(expressions);
    }
  } catch (e){
  }
}

com.ym.base.util.logger.trace = function(){
  try{
    if (console && console.trace){
      console.trace();
    }
  } catch (e){
  }
}

com.ym.base.util.logger.log = function(szPh, content){
  try{
    if (console && console.log){
      if (content) console.log(szPh, content);
      else console.log(szPh);
    }
  } catch (e){
  }
}

com.ym.base.util.logger.debug = function(content){
	try{
		if (console && console.debug){
			console.debug(content);
		}
	} catch (e){
	}
}

com.ym.base.util.logger.info = function(content){
	try{
		if (console && console.log){
			console.log(content);
		}
	} catch (e){
	}
}

com.ym.base.util.logger.error = function(content){
	try{
		if (console && console.error){
			console.error(content);
		}
	} catch (e){
	}
}


com.ym.base.util.datagrid.addExportExcelButton = function(grid, title, columnFilter) {
	return com.ym.base.util.datagrid._addExportExcelButton({"grid":grid, "title":title, "columnFilter":columnFilter})
}


com.ym.base.util.datagrid._addExportExcelButton = function(options) {
	var grid = options.grid;
	var title = options.title;
	var columnFilter = options.columnFilter;
	
  var pager = grid.datagrid('getPager'); // get the pager of datagrid
  pager.pagination( {
    buttons : [ {
      iconCls : 'icon-save',
      title : '导出当页数据',
      handler : function() {
    	  rows = grid.datagrid("getRows");
        com.ym.base.util.exportUtil.exportDataToExcelColumnAsDatagrid(rows, grid, title, columnFilter);
      }
    } ]
  });
}

com.ym.base.util.exportUtil.exportDataToExcelColumnAsDatagrid = function(data, grid, title, columnFilter){
	var gridData = {
	  "title" : title ? title : "导出列表"
	};
	var i = 0, j = 0;
	var szText = "";
	var columns = [];
	var fields = [];
	var columnsLine = [];
	var newColumnsLine = [];
	var rows = [];
	var column = {};
	var row = {};
	var formatters = [];
	var options = {};
	
  if (!columnFilter){
    columnFilter = com.ym.base.util.datagrid._defaultExportExcelColumnFilter;
  }
	
	options = grid.datagrid("options");
	for (i = 0; i < options.columns.length - 1; i++) {
	  columns.push(options.columns[i]);
	}
	columnsLine = options.columns[options.columns.length - 1];
	for (i in columnsLine) {
	  column = columnsLine[i];
	  if(column.isExport==true){
		    continue;
	  }
	  if (!columnFilter(column, i)) {
	    continue;
	  }
	  
	  if (column.formatter){
	    formatters.push(column);
	  }
	  newColumnsLine.push(column);
	}
	columns.push(newColumnsLine);
	
	var columnFields = grid.datagrid("getColumnFields");
	for (i in columnFields) {
	var fie = grid.datagrid("getColumnOption",columnFields[i]);
	 if(fie.isExport==true){
		    continue;
		}
	  if (columnFields[i] == "opts") {
	    continue;
	  }
	  column = {"field" : columnFields[i], "dataType":"string"};
	  fields.push(column);
	}
	
	gridData.columns = columns;
	gridData.fields = fields;
	
	rows = data;
	if (formatters.length > 0){
	  for (i in rows){
	    row = rows[i];
	    for (j in formatters){
	      column = formatters[j];
	      szText = column.formatter(row[column.field], row, i);
	      row[column.field] = szText;
	      /*row[column.field] = szText ? $("<p>" + szText + "</p>").text() : null;*/
	    }
	  }
	}
	gridData.rows = rows;
	
  com.ym.base.util.exportUtil.exportDataToExcel(gridData);
}

com.ym.base.util.exportUtil.exportDataToExcel = function(data){
  $.post(window.rootPath + com.ym.base.util.exportUtil._exportURL, {
    "data" : JSON.stringify(data),
    "fileName" : "导出列表"
  }, function(data) {
    if (data.success) {
      // DO download...
      location.href = data.url;
    } else {
      $.messager.alert('操作提示', data.msg, 'error');
    }
  }, "json");
}

com.ym.base.util.datagrid._defaultExportExcelColumnFilter = function(column, i) {
	return (column && column.field != "opts")
}

/**
 * js 乘法
 *乘法函数，用来得到精确的乘法结果
 *说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
 *调用：accMul(arg1,arg2)
 *返回值：arg1乘以arg2的精确结果
 */
accMul=function(arg1,arg2){
    //给Number类型增加一个mul方法，调用起来更加方便。
    Number.prototype.mul = function (arg){
        return accMul(arg, this);
    }
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

//系统常量设置
var systemProp={
		// 订单状态
		OS_INIT:1,/** 代付款(未付款) */
		OS_PAYED:2,/** 待分配(已付款) */
		OS_ALLOCATED:3,/** 待收货(已分配) */
		OS_DELIVERED:4,/** 已送达 */
		OS_RECEIVED:5,/** 交易完成(已收货) */
		OS_CLOSED:6,/** 交易关闭 */
		// 退款状态
		RS_REFOUNDING:1,/** 已申请退款 */
		RS_EGIS:2,/** 审核通过 */
		RS_REJECT:3,/** 审核拒绝 */
		RS_REFOUNDED:4,/** 已退款 */
		// 配送状态
		SS_INIT:1,/** 注册 */
		SS_ACTIVE:2,/** 已激活 */
		SS_DISABLE:3,/** 禁用 */
		// 设置表常用字段
		SHOPTYPE:"SHOPTYPE",/** 城市查询cfgId */
		AREA_ID:"AREA"/** 商户类型查询cfgId */
}

//付款类型
payType={
		1:"支付宝"
}

//正常订单状态
var deviceType={
		1:"台式机",
		2:"笔记本",
		3:"打印机",
		4:"交换机",
		5:"服务器",
		6:"硬盘",
		7:"其它"
}

//退款单状态
var refoundStatus={
		1:"退款中",
		2:"审核成功",
		3:"审核拒绝",
		4:"退款完成"
}