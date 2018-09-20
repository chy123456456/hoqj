display("role,post"); // 需要翻译的类型
$(function() {
    $('#deviceEditDialog').dialog({
        buttons:[{text:'保存',handler:function(){
            if(!$('#deviceEditForm').form('validate')){return;}
            $('#deviceEditForm')._ajaxForm(function(r){
                if(r.r){$('#deviceEditDialog').dialog('close');$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
            });
        }},{text:'获取当前电脑ip、mac地址',handler:function(){
        	$._ajaxPost('common/getRemotePcInfo',{}, function(r){
                if(r.r){var d=r.d;$("#ip").val(d.ip);$("#mac").val(d.mac);}
            });
        }},{text:'关闭',handler:function(){$('#deviceEditDialog').dialog('close');}}]
    });
    var grid = $('#grid')._datagrid({
        checkOnSelect:false,
        selectOnCheck:false,
        /*frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],*/
        toolbar : [/*{
                    text : '创建设备',
                    iconCls : 'icon-add',
                    handler : handler_add
                }, '-', {
                    text : '删除所选',
                    iconCls : 'icon-remove',
                    handler : batch_del
                }, '-' */]
    });
    $('#queryButton').click(function(){
        var params = $('#queryForm')._formToJson();
        $(grid).datagrid('load',params);
    });

    /*新增设备*/
    function handler_add() {
        $('#deviceEditForm').attr('action','device/add.html').resetForm();
        $('#devicename').removeAttr('readonly');
        $('#id').val('');
        $('#deviceEditDialog').dialog('open').dialog("setTitle","新增设备");
    }
    /*批量删除*/
    function batch_del() {
        var check = $('#grid').datagrid('getChecked');
        if(check.length > 0){
            $.messager.confirm('操作提示', '确定要删除所选设备？', function(r){
                if (r){
                    var deviceIds = new Array();
                    for(var i in check){
                        deviceIds[i] = check[i].deviceId;
                    }
                    $._ajaxPost('device/batch_del.html',{deviceIds:deviceIds.join('|')},function(r){
                        if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
                    });
                }
            });
        }
    }
});
var formatter = {
    status : function(value, rowData, rowIndex) {
        if(value == 1){ return '<font color=green>正常</font>'; } else { return '<font color=red>报损</font>'; }
    },
    type : function(value, rowData, rowIndex) {
        return deviceType[rowData.type];
    },
    opt : function(value, rowData, rowIndex) {
        var html = '<a class="spacing a-blue" onclick="upddevice('+rowIndex+');" href="javascript:void(0);">修改</a>';
        return html;
    }
};
