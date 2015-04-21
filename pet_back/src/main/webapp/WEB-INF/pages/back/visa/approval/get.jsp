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
		<title>增补材料</title>
	</head>
	<body>
        <p class="lead">游玩人<span class="req">${visaApproval.name}</span>签证状态:</p>
        <div class="box_content center-item">
	        <table class="table" style="width:240px;">
	        	<tbody>
	                <tr>
	                    <td><label class="radio"><input name="type-status" value="VISA_OK" type="radio" <s:if test='visaApproval.visaStatus == "VISA_OK"'>checked</s:if> >出签</label></td>
	                    <td><label class="radio"><input name="type-status" value="NEED_FACE_VISA" type="radio" <s:if test='visaApproval.visaStatus == "NEED_FACE_VISA"'>checked</s:if> >面签</label></td>
	                    <td><label class="radio"><input name="type-status" value="VISA_FAIL" type="radio" <s:if test='visaApproval.visaStatus == "VISA_FAIL"'>checked</s:if> >拒签</label></td>
	                </tr>
	                <tr>
						<td><label class="radio"><input name="type-status" value="VISA_CANCEL" type="radio" <s:if test='visaApproval.visaStatus == "VISA_CANCEL"'>checked</s:if> >代销签</label></td>
	                    <td><label class="radio"><input name="type-status" value="NEED_FACE_VISA_CANCEL" type="radio" <s:if test='visaApproval.visaStatus == "NEED_FACE_VISA_CANCEL"'>checked</s:if> >面销</label></td>
						<td></td>
	                </tr>
	            </tbody>
	        </table>
        </div>

        <p class="tc mt20"><button class="btn btn-small w3" onclick="javascript:save()" type="button">保存</button>　　<button class="btn btn-small w3 btn-close" id="closeGetBtn" type="button">关闭</button></p>
  
	</body>
	<script type="text/javascript">
		 $(function(){
			 $("#closeGetBtn").click(function(){
				 $("#popDiv").dialog("close");
			 });
		 });
		 
		 function save() {
			 if ($(":radio[checked='true']").val() == 'undefined') {
				 alert("请选择合适的签证状态");
				 return;
			 }
			 $.ajax({
 	    	 	url: "<%=basePath%>/visa/approval/updateApprovalStatus.do",
 				type:"post",
 	    	 	data: {
 	    	 		"visaApproval.visaApprovalId":${visaApproval.visaApprovalId},
 					"visaApproval.visaStatus":$(":radio[checked='true']").val()
 				},
 				contentType: "application/x-www-form-urlencoded; charset=utf-8",
 	    	 	dataType:"json",
 	    	 	success: function(result) {
 					if (result.success) {
 						alert("操作成功");
 						$("#popDiv").dialog("close");
 					} else {
 						alert("操作失败，请重新尝试");
 					}
 	    	 	}
 	    	});
		 }
		 
	</script>
</html>