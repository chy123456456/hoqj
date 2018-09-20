
display("role,post"); // 需要翻译的类型
$(function() {
    $('#medicalSafeEditDialog').dialog({
        buttons:[{text:'上报',id:'sbid',handler:function(){
            if(!$('#medicalSafeEditForm').form('validate')){return;}
            if($("#sbflag").val()=="0"){
            	$.messager.alert('操作提示','详情查看时不能上报' , 'error');
            	return;
            }
            if($("[name='flag']:checked").val()=="有" && $("#ylaqyh").val()=="安全" && $("#yljfyh").val()=="安全"){
            	$.messager.alert('操作提示','当选择有安全隐患时，请填写医疗安全隐患或医疗纠纷隐患' , 'error');
            	return;
            }
            var d = {
            	sbry:$("#sbry").val(),
            	ks:$("#ks").val(),
            	sbrq:$("#sbrq").val(),
            	flag:$("[name='flag']:checked").val(),
            	ylaqyh:$("#ylaqyh").val(),
            	yljfyh:$("#yljfyh").val(),
            	ksjy:$("#ksjy").val()
            };
            $.ajax({
				type : 'post',
				url : 'medicalSafe/add',
				data : d,
				dataType : 'json',
				async : false,
				success : function(r) {
					if (r.r) {
						$.messager.alert('操作提示', '上报成功！', 'info');
						$('#medicalSafeEditDialog').dialog('close');
						$('#grid').datagrid('reload');
					} else {
						$.messager.alert('操作提示', r.m, 'error');
					}
				}
			});
        }},{text:'关闭',handler:function(){$('#medicalSafeEditDialog').dialog('close');}}]
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
        $('#medicalSafeEditForm').attr('action','medicalSafe/add').resetForm();
        $('#medicalSafename').removeAttr('readonly');
        $('#id').val('');
        $("#sbflag").val("1");
        $('#medicalSafeEditDialog').dialog('open').dialog("setTitle","上报");
    }
    
});

var formatter = {
    opt : function(value, rowData, rowIndex) {
        var html = '<a class="spacing a-green" onclick="detail('+rowIndex+');" href="javascript:void(0);">详情</a>';
        return html;
    }
};

function detail(rowIndex) {
    var data = $('#grid').datagrid('getRows')[rowIndex];
    $('#medicalSafeEditForm')._jsonToForm(data);
    $('#medicalSafeEditDialog').dialog('open').dialog('setTitle','详情');
    $("#sbid").attr('disabled',"true");
    $("#sbflag").val("0");
}
