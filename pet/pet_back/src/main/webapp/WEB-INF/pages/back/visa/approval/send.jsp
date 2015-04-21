<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>签证材料寄送</title>
		<script type="text/javascript" src="${basePath}/js/base/ajaxupload.js"></script>
	</head>
	<body>
        <p class="lead">游玩人<span class="req">${visaApproval.name}</span>签证材料寄送:</p>
        <div class="box_content">
	        <table class="p_table J_additem_box" style="width:100%">
	            <tbody>
	                <tr>
	                    <td width="90" class="p_label"><span class="req">*</span>快递单号：</td>
						<td>
							<div id="filename"></div>
							<input type="button" value="上传快递单扫描件" id="uploadChangeFile" serverType="VISA_APPROVAL_SENDLOG"/>
						</td>
	                </tr>
	                <tr>
	                    <td width="90" class="p_label">备注：</td>
						<td><textarea id="content" style="width: 90%"></textarea></td>
	                </tr>
	            </tbody>
	            <tfoot>
	                <tr>
	                    <td colspan="2"><p class="tc mt20"><button id="saveSendLog" class="btn btn-small w3" type="button">保存</button></p></td>
	                </tr>
	            </tfoot>
	        </table>
	        <br/>
	        <table class="p_table J_additem_box">
	            <tbody>
	            	<s:iterator value="logs" id="log">
						<tr>
							<td width="90">${log.operatorName }添加了快递信息：${log.content }</td>
						</tr>		                   
	            	</s:iterator>
	            </tbody>
	        </table>
        </div>
	</body>
	<script type="text/javascript">
	 $(function(){
		 $("#uploadChangeFile").fileUpload({
				onSubmit:function() {
					$("#uploadChangeFile").attr("disabled", true);
				},
				onComplete:function(file,dt){
					var data=eval("("+dt+")");
					if(data.success){
						$("#filename").text("上传成功");
						$("#filename").data("pid",data.file);
					}else{
						alert(data.msg);
					}
					$("#uploadChangeFile").removeAttr("disabled");
				}
			});
		 
		 $("#saveSendLog").click(function(){
			 if ($("#content").val() == "") {
				 alert("请输入备注后再保存");
				 $("#content").focus();
				 return;
			 }
			 $.ajax({
	    	 		url: "<%=basePath%>/visa/approval/saveSendLog.do",
					type:"post",
	    	 		data: {
							"pid":$("#filename").data("pid"),
							"content": $("#content").val(),
							"visaApproval.visaApprovalId":${searchVisaApprovalId}
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("新增成功");
							$("#popDiv").dialog("close");
						} else {
							alert("数据丢失，操作失败");
						}
	    	 		}
	    		});		
		 })
	 });
	</script>
</html>