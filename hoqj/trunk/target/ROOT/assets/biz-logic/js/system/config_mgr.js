$(function() {
    $('#configEditDialog').dialog({
        buttons:[{text:'保存',handler:function(){
            if(!$('#configEditForm').form('validate')){return;}
            $('#configEditForm')._ajaxForm(function(r){
                if(r.r){$('#configEditDialog').dialog('close');$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
            });
        }},{text:'关闭',handler:function(){$('#configEditDialog').dialog('close');}}]
    });
    var grid = $('#grid')._datagrid({
        toolbar : [{
                    text : '创建参数',
                    iconCls : 'icon-add',
                    handler : handler_add
                }]
    });
    $('#queryButton').click(function(){
        var params = $('#queryForm')._formToJson();
        $(grid).datagrid('load',params);
    });

    /*新增参数*/
    function handler_add() {
        $('#configEditForm').attr('action','conf/add.html').resetForm();
        $('#cfgId').removeAttr('readonly');
        $('#cfgKey').removeAttr('readonly');
        $('#configEditDialog').dialog('open').dialog("setTitle","新增参数");
    }
});
var formatter = {
    status : function(value, rowData, rowIndex) {
        if(value == 1){ return '<font color=green>正常</font>'; } else { return '<font color=red>停用</font>'; }
    },
    opt : function(value, rowData, rowIndex) {
        var html = '<a class="spacing a-blue" onclick="updConfig('+rowIndex+');" href="javascript:void(0);">修改</a>';
            html+= '<a class="spacing a-red" onclick="delConfig('+rowIndex+');" href="javascript:void(0);">删除</a>';
        return html;
    }
};
/*修改参数*/
function updConfig(rowIndex) {
    $('#configEditForm').attr('action','conf/upd.html').resetForm();
    $('#cfgId').attr('readonly', 'readonly');
    $('#cfgKey').attr('readonly', 'readonly');
    var data = $('#grid').datagrid('getRows')[rowIndex];
    $('#configEditForm')._jsonToForm(data);
    $('#configEditDialog').dialog('open').dialog('setTitle','修改参数');
}
/*删除参数*/
function delConfig(rowIndex) {
    $.messager.confirm('操作提示', '确定要删除该参数？', function(r){
        if (r){
            var data = $('#grid').datagrid('getRows')[rowIndex];
            $._ajaxPost('conf/del.html',{cfgId:data.cfgId,cfgKey:data.cfgKey}, function(r){
                if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
            });
        }
    });
}
