<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>使用系统模板-批量生成出团通知书-预览</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/mis.css">
	</head>
	<body>
		<s:if test="#request.msg!=null">
			<span style="color:red">${msg }</span>
		</s:if>
		<s:else>
			<span style="color:red">注:&#36;{}为替换符号，请不要修改</span>
			<form id="viewContentForm"  method="post" action="<%=basePath%>groupadvice/doBatchUseSysTpl.do">
				<s:hidden id="objectIds" name="objectIds" />
				<s:hidden id="contentFirstFix" name="contentFirstFix"></s:hidden>
				<s:hidden id="contentEndFix" name="contentEndFix"></s:hidden>
				<table>
					<tr height="100">
						<td height="100">
							<textarea id="contentTxt" name="content" style="width: 780px; height: 400px; visibility: hidden;">
								${content}
							</textarea>
						</td>
					</tr>
				</table>
				<input type="submit" id="batch-do-btn"  value="批量生成出团通知书"/>
			</form>
		</body>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="<%=basePath%>kindeditor-3.5.1/kindeditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.form.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			
			//初始化富文本控件
			KE.show( {
				id : 'contentTxt'
			});
			
			var doBtn=$("#batch-do-btn");
			
			//初始化异步表单
			$('#viewContentForm').ajaxForm({ 
		        dataType:  'json',
		        error:function(){
		        	alert('网络请求错误，请稍后再试');
		        	resetSubmitBtn();
		        },
		        beforeSubmit:function(){
		        	var html = KE.html('contentTxt');
					if(html==""){
						alert("模板内容不能为空");
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
			
		});
		</script>
	</s:else>
</html>