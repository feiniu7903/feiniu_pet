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
		<title>签证材料确认</title>
	</head>
	<body>
        <p class="lead">游玩人<span class="req">${visaApproval.name }</span>属于哪种人群:</p>
        <div class="box_content center-box">
	        <table class="table center-item form-inline">
	            <tbody>
	            	<s:iterator value="visaApplicationDocuments" id="document" status='st'>
	            		<s:if test="#st.index % 3 == 0"><tr></s:if>	
		                    <td width="90">
		                    	<label class="radio"><input name="type-people" value="${occupation }" type="radio" <s:if test="occupation == visaApproval.occupation">checked</s:if> >${cnOccupation }　</label>
		                    </td>
	            		<s:if test="#st.index % 3 == 2"></tr></s:if>
	            	</s:iterator>
	            	<%
	            		int i = ((java.util.List) request.getAttribute("visaApplicationDocuments")).size() % 3;
	            		for (int j = i; j % 3 != 0 ; j++ ) {
	            			out.println("<td></td>");
	            		}
	            		if (i != 0) {
	            			out.println("</tr>");
	            		}
	            	%>
	            </tbody>
	        </table>
    	</div>

        <p class="tc mt10">
        	<button class="btn btn-small w3" id="confirmBtn" type="button">保存</button>
        	<button class="btn btn-small w3" id="closeBtn" type="button">关闭</button>
        </p>
        
	</body>
	<script type="text/javascript">
	 $(function(){
		$("#confirmBtn").click(function(){
			if ($(":radio[checked='true']").val() == 'undefined') {
				alert('请选择合适的人群');
				return;
			}
			<s:if test="visaApproval.occupation != null">
				var flag = false;
				var flag = window.confirm("您确定需要更新合适人群吗？此更改会将以往的操作全部删除");
			</s:if>
			<s:else>
				var flag = true;
			</s:else>
			if (flag) {
				$.ajax({
	    	 		url: "<%=basePath%>/visa/approval/saveConfirmMaterial.do",
					type:"post",
	    	 		data: {
							"visaApproval.visaApprovalId":${visaApproval.visaApprovalId},
							"visaApproval.occupation":$(":radio[checked='true']").val()
						},
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
	    	 		dataType:"json",
	    	 		success: function(result) {
						if (result.success) {
							alert("修改成功");
							$("#popDiv").dialog("close");
						} else {
							alert("数据丢失，操作失败");
						}
	    	 		}
	    		});				
			}
		});	
		
		$("#closeBtn").click(function(){
			$("#popDiv").dialog("close");	
		});
	 });
	</script>
</html>