<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>使用用户模板-批量生成出团通知书</title>
		<style type="text/css">
		.item{display: block; margin: 20px auto; background: #eee; border-radius: 10px; padding: 15px}
		</style>
	</head>
	<body>
		<h3>上传模板文件</h3>
		<form method="post" enctype="multipart/form-data" action="<%=basePath%>groupadvice/doBatchUseUploadTpl.do" id="upload_form">
			<s:hidden id="objectIds" name="objectIds" />
			<div class="item">
				<div style="margin: 5px;">
					<span style="margin-left:32px">文件:</span>
					<s:file name="file" label="文件" /> <span style="color:red">注:模板文件只支持docx格式文件</span>
				</div>
				<div style="margin: 5px;"> 
					文件名称:
					<s:textfield name="batchAffixName"  label="文件名称" cssStyle="width:300px;"/>
				</div>
				<div style="margin: 5px;">
					<span style="vertical-align: top;">文件描述:</span>
					<s:textarea  name="batchAffixMemo" label="文件描述" cols="35" rows="1" cssStyle="width: 301px; height: 151px;"/>
				</div>
			</div>
			<div style="margin: 15px;">
				<input type="submit" id="batch-do-btn"  value="批量生成出团通知书"/>
			</div>
		</form>
	</body>
	<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/base/jquery.form.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		var doBtn=$("#batch-do-btn");
		//初始化异步表单
		$('#upload_form').ajaxForm({ 
	        dataType:  'json',
	        error:function(){
	        	alert('网络请求错误，请稍后再试');
	        	resetSubmitBtn();
	        },
	        beforeSubmit:function(){
	        	
	        	var path=$("#file").val();
	        	if($.trim(path)==""){
	        		alert("请选择要上传的模板文件");
	        		return false;
	        	}
	        	
	        	var ext=path.substring(path.lastIndexOf(".")+1,path.length).toLowerCase();
	        	if(ext!="docx"){
	        		alert("模板文件只支持docx格式文件");
	        		return false;
	        	}
	        	
	        	if($.trim($("#batchAffixName").val())==""){
	        		alert("文件名称不可以为空");
	        		return false;
	        	}
	        	
	        	if($.trim($("#batchAffixMemo").val())==""){
	        		alert("文件描述不可以为空");
	        		return false;
	        	}
	        	
	        	$(doBtn).attr("disabled",true);
	        	$(doBtn).val("正在生成，请稍后...");
	        },
	        success: function(data){
	        	alert(data.message);
	        	resetSubmitBtn();
	        } 
	    }); 
		
		//重置按钮
		function resetSubmitBtn(){
			$(doBtn).attr("disabled",false);
        	$(doBtn).val("批量生成出团通知书");
		}
		
		//自动生成文件名
		$("#file").bind("change", function(){
			var path=$(this).val();
			var pos=path.lastIndexOf("/");
			if(pos==-1){
			   pos=path.lastIndexOf("\\");
			}
			var extPos=path.lastIndexOf(".");
			if(extPos==-1){
				extPos=path.length;
			}
			var filename=path.substring(pos+1,extPos);
			if(filename.length>50){
				filename=filename.substring(0,50);
			}
			$("#batchAffixName").val(filename);
		});
		
	});
	</script>
</html>