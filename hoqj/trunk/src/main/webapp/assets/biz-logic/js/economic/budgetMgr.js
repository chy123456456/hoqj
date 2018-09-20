$(function(){
	//添加图片
    $("#phoAdd").click(function(){
      $("#fileImage").click();
    });
    var params = {
   	    fileInput: $("#fileImage").get(0),
   	    upButton: "",
   	    url: 'document/upload-economic.do',//上传地址
   	    filter: function(files) {
   	        var arrFiles = [];
   	        for (var i = 0, file; file = files[i]; i++) {
//   	            if (file.type.indexOf("image") == 0) {
//   	                 if (file.size >= 5120000) {
//   	                	$.messager.alert('操作提示', 'Picture "'+ file.name +'" too big.', 'error');
//   	                 } else {
   	                    arrFiles.push(file);    
//   	                 }           
//   	            } else {
//   	            	$.messager.alert('操作提示', 'File "' + file.name + '" is not a picture.', 'error');
//   	            }
   	        }
   	        return arrFiles;
   	    },
   	    onSelect: function(files) {
   	        var i = 0;
   	        var funAppendImage = function() {
   	            file = files[i];
   	            if (file) {
   	                var reader = new FileReader()
   	                reader.onload = function(e) {
   	                	$("#imagePhoto").attr("src", e.target.result );
   	                	i++;
   	                    funAppendImage();
   	                }
   	                reader.readAsDataURL(file);
   	            } else {
   	            	ZXXFILE.funUploadFile();
   	                ZXXFILE.fileFilter=[];
   	            }
   	        };
   	        funAppendImage();       
   	    },
   	    onProgress: function(file, loaded, total) {
   	    	
   	    },
   	    onSuccess: function(file, response) {
   	        var obj = eval("("+response+")");  
   	        var dataId = obj['d']['fileToUpload']['fileId'];
   	        $("#shopCover").val(dataId);
   	     	$("#imagePhoto").attr("src", 'document/file-'+dataId+'.do' );
   	     	//请求服务器后端解析excel
   	     	$.post('economic/analysis.html', {fileId:dataId}, function(r) {
	            if (r.r) {
	            	alert("文档解析成功！");
				} else {
					alert('文档解析出错！');
				}
         }, 'json');
   	    },
   	    onFailure: function(file) {
   	
   	    },
   	    onComplete: function() {
   	       //file控件value置空
   	      $("#fileImage").val("");
   	    }
   	};
   	ZXXFILE = $.extend(ZXXFILE, params);
   	ZXXFILE.init();
});