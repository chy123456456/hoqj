$(function(){
	//���ͼƬ
    $("#phoAdd").click(function(){
      $("#fileImage").click();
    });
    var params = {
   	    fileInput: $("#fileImage").get(0),
   	    upButton: "",
   	    url: 'document/upload-economic.do',//�ϴ���ַ
   	    filter: function(files) {
   	        var arrFiles = [];
   	        for (var i = 0, file; file = files[i]; i++) {
//   	            if (file.type.indexOf("image") == 0) {
//   	                 if (file.size >= 5120000) {
//   	                	$.messager.alert('������ʾ', 'Picture "'+ file.name +'" too big.', 'error');
//   	                 } else {
   	                    arrFiles.push(file);    
//   	                 }           
//   	            } else {
//   	            	$.messager.alert('������ʾ', 'File "' + file.name + '" is not a picture.', 'error');
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
   	     	//�����������˽���excel
   	     	$.post('economic/analysis.html', {fileId:dataId}, function(r) {
	            if (r.r) {
	            	alert("�ĵ������ɹ���");
				} else {
					alert('�ĵ���������');
				}
         }, 'json');
   	    },
   	    onFailure: function(file) {
   	
   	    },
   	    onComplete: function() {
   	       //file�ؼ�value�ÿ�
   	      $("#fileImage").val("");
   	    }
   	};
   	ZXXFILE = $.extend(ZXXFILE, params);
   	ZXXFILE.init();
});