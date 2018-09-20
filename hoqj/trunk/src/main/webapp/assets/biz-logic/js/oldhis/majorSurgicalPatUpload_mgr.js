display("role,post"); // 需要翻译的类型
$(function() {
    $('#majorSurgicalPatUploadEditDialog').dialog({
        buttons:[{text:'上报',handler:function(){
            if(!$('#majorSurgicalPatUploadEditForm').form('validate')){return;}
            if($("#sbflag").val()=="0"){
            	$.messager.alert('操作提示','详情查看时不能上报' , 'error');
            	return;
            }
            var d = {
            	zyh:$("#zyh").val(),
            	xm:$("#xm").val(),
            	xb:$("#xb").val(),
            	ks:$("#ks").val(),
            	ryrq:$("#ryrq").val(),
            	nlstr:$("#nl").val(),
            	zyzd:$("#zyzd").val(),
            	ssmc:$("#ssmc").val()
            };
            $.ajax({
				type : 'post',
				url : 'majorSurgicalPatUpload/add',
				data : d,
				dataType : 'json',
				async : false,
				success : function(r) {
					if (r.r) {
						$.messager.alert('操作提示', '上报成功！', 'info');
						$('#majorSurgicalPatUploadEditDialog').dialog('close');
						$('#grid').datagrid('reload');
					} else {
						$.messager.alert('操作提示', r.m, 'error');
					}
				}
			});
        }},{text:'关闭',handler:function(){$('#majorSurgicalPatUploadEditDialog').dialog('close');}}]
    });
    var grid = $('#grid')._datagrid({
        checkOnSelect:false,
        selectOnCheck:false,
        /*frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],*/
        toolbar : [{
                    text : '上报',
                    iconCls : 'icon-add',
                    handler : handler_add
                }]
    });
    $('#queryButton').click(function(){
        var params = $('#queryForm')._formToJson();
        $(grid).datagrid('load',params);
    });

    /*新增上报*/
    function handler_add() {
        $('#majorSurgicalPatUploadEditForm').attr('action','majorSurgicalPatUpload/add.html').resetForm();
        $('#majorSurgicalPatUploadname').removeAttr('readonly');
        $("#sbflag").val("0");
        $('#majorSurgicalPatUploadEditDialog').dialog('open').dialog("setTitle","上报-输入住院号后敲回车提取信息");
    }
    
    //如果在住院号处按回车，则自动获取该住院号信息
    $("#zyh").keyup(function(event){
    	  if(event.keyCode ==13){
    		  $.ajax({
					type : 'post',
					url : 'oldhisDifficultSevere/getZDxxByZYH',
					data : 'zyh=' + $("#zyh").val(),
					dataType : 'json',
					async : false,
					success : function(r) {
						if (r.r) {
							var d= r.d[0];
							$("#xm").val(d.xm);
							$("#xb").val(d.xb);
							$("#ks").val(d.ks);
							$("#ryrq").val(d.rysj);
							$("#nl").val(d.nl);
							$("#zyzd").val(d.zd);
							$("#xb").val(d.xb);
							$("#ssmc").val(d.ssrq);
						} else {
							$.messager.alert('操作提示', r.m, 'error');
						}
					}
				});
    	  }
    	});
});

var formatter = {
    opt : function(value, rowData, rowIndex) {
        var html = '<a class="spacing a-green" onclick="detail('+rowIndex+');" href="javascript:void(0);">详情</a>';
        return html;
    }
};

function detail(rowIndex) {
    var data = $('#grid').datagrid('getRows')[rowIndex];
    $('#majorSurgicalPatUploadEditForm')._jsonToForm(data);
    $('#majorSurgicalPatUploadEditDialog').dialog('open').dialog('setTitle','详情');
    $("#sbflag").val("0");
}
