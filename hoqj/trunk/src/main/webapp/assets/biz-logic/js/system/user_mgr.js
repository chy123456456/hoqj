display("role,post"); // 需要翻译的类型
$(function() {
    $('#userEditDialog').dialog({
        buttons:[{text:'保存',handler:function(){
            if(!$('#userEditForm').form('validate')){return;}
            var qq=$("#userEdit-qq").val();
            if(qq!=null &&qq!=""){
            	var rec;  
	        	rec =  /^\d{5,12}$/;
            	if(!rec.test(qq)){
            		$.messager.alert('操作提示', "请填入正确的qq格式！",'error');
            		return;
            	}
            }
            $('#userEditForm')._ajaxForm(function(r){
                if(r.r){$('#userEditDialog').dialog('close');$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
            });
        }},{text:'关闭',handler:function(){$('#userEditDialog').dialog('close');}}]
    });
    var grid = $('#grid')._datagrid({
        checkOnSelect:false,
        selectOnCheck:false,
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        toolbar : [{
                    text : '创建用户',
                    iconCls : 'icon-add',
                    handler : handler_add
                }, '-', {
                    text : '删除所选',
                    iconCls : 'icon-remove',
                    handler : batch_del
                }, '-' ]
    });
    $('#queryButton').click(function(){
        var params = $('#queryForm')._formToJson();
        $(grid).datagrid('load',params);
    });

    $('#roleId')._pullDownTree({url:'role/get_roles.html', width:150, height:200,
    	treeName: 'roleName', treeId:'roleId', treePId : 'parentRole',chkboxType:{ "Y" : "", "N" : ""},onCheck:function(zTree,treeNode,targ, hide){
        var nodes = zTree.getCheckedNodes(true);
        var tar="";
        var rid="";
        for(var i in nodes){
        	tar+=nodes[i].roleName+",";
        	rid+=nodes[i].roleId+",";
        }
        targ.val(tar.substring(0, tar.length-1));
        hide.val(rid.substring(0, rid.length-1));
    },successCallback : function(zTree) {
        node = zTree.getNodeByParam("roleId",window.user.roleId, null);
        node.nocheck = true;
        zTree.updateNode(node);
    }});
    /*新增用户*/
    function handler_add() {
        $('#userEditForm').attr('action','user/add.html').resetForm();
        $('#username').removeAttr('readonly');
        $('#userId').val('');
        $('#roleId')._pullDownTree('clear');
        $('#userEditDialog').dialog('open').dialog("setTitle","新增用户");
    }
    /*批量删除*/
    function batch_del() {
        var check = $('#grid').datagrid('getChecked');
        if(check.length > 0){
            $.messager.confirm('操作提示', '确定要删除所选用户？', function(r){
                if (r){
                    var userIds = new Array();
                    for(var i in check){
                        userIds[i] = check[i].userId;
                    }
                    $._ajaxPost('user/batch_del.html',{userIds:userIds.join('|')},function(r){
                        if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
                    });
                }
            });
        }
    }
});
var formatter = {
    status : function(value, rowData, rowIndex) {
        if(value == 1){ return '<font color=green>正常</font>'; } else { return '<font color=red>停用</font>'; }
    },
    roles : function(value, rowData, rowIndex) {
        var arr = [];
        for(i in value) {
            arr.push($.fn.display.role[value[i].roleId]);
        }
        return arr.join(',');
    },
    opt : function(value, rowData, rowIndex) {
        var html = '<a class="spacing a-blue" onclick="updUser('+rowIndex+');" href="javascript:void(0);">修改</a>';
            html+= '<a class="spacing a-red" onclick="delUser('+rowIndex+');" href="javascript:void(0);">删除</a>';
            html+= '<a class="spacing a-green" onclick="resetPassword('+rowIndex+');" href="javascript:void(0);">密码重置</a>';
        return html;
    }
};
/*修改用户*/
function updUser(rowIndex) {
    $('#userEditForm').attr('action','user/upd.html').resetForm();
    $('#code').attr('readonly', 'readonly');
    var data = $('#grid').datagrid('getRows')[rowIndex];
    $('#userEditForm')._jsonToForm(data);
    $('#roleId')._pullDownTree({checkeds:data.roles, treeName: 'roleName', treeId:'roleId'});
    $('#userEditDialog').dialog('open').dialog('setTitle','修改用户');
}
/*删除用户*/
function delUser(rowIndex) {
    $.messager.confirm('操作提示', '确定要删除该用户？', function(r){
        if (r){
            var data = $('#grid').datagrid('getRows')[rowIndex];
            $._ajaxPost('user/del.html',{userId:data.userId}, function(r){
                if(r.r){$('#grid').datagrid('reload');}else{$.messager.alert('操作提示', r.m,'error');}
            });
        }
    });
}
function resetPassword(rowIndex){
    $.messager.confirm('操作提示', '确定重置该用户密码？', function(r){
        if (r){
            var data = $('#grid').datagrid('getRows')[rowIndex];
            $._ajaxPost('user/resetPassword.html',{id:data.id}, function(r){
                if(r.r){$('#grid').datagrid('reload');$.messager.alert('操作提示', r.m,'info');}else{$.messager.alert('操作提示', r.m,'error');}
            });
        }
    });
}