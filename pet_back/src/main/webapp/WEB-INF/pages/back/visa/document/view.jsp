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
		<title>编辑签证材料明细</title>
	</head>
	<body>
		<table class="p_table form-inline">
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>国家:</td>
				<td>
					${visaApplicationDocument.country}
				</td>
				<td class="p_label" width="15%"><span class="red">*</span>签证类型:</td>
				<td>
					${visaApplicationDocument.cnVisaType}
				</td>
			</tr>
			<tr>
				<td class="p_label" width="15%"><span class="red">*</span>送签城市:</td>
				<td>
					${visaApplicationDocument.cnCity}
				</td>
				<td class="p_label" width="15%"><span class="red">*</span>所属人群:</td>
				<td>
					${visaApplicationDocument.cnOccupation}
				</td>
			</tr>					 			 			
	   </table>
	   <br/>
	   <s:if test="null != visaApplicationDocumentDetailsList && visaApplicationDocumentDetailsList.size() > 0">
			<table class="p_table form-inline">
				<tr>
					<th>材料名称</th>
					<th>材料要求</th>
					<th>序列</th>
					<th></th>
				</tr>
			   <s:iterator value="visaApplicationDocumentDetailsList" var="details">
				<tr class="tr_${details.detailsId}">
					<td class="p_label" width="20%">${details.title}</td>
					<td>${details.content}</td>
					<td>${details.seq}</td>
					<td><a href="javascript:modify(${details.detailsId});">修改</a>&nbsp;&nbsp;<a href="javascript:del(${details.detailsId});">删除</a></td>
				</tr>
			   </s:iterator>
			</table>
	   </s:if>
	   <br></br>
	   <input type="hidden" id="details_detailsId" name="details.detailsId" />
	   <table class="p_table form-inline">
			<tr>
				<td class="p_label" width="20%"><span class="red">*</span>材料名称</td>
				<td><input type="text" id="details_title" name="details.title"/></td>
			</tr>
			<tr>
				<td class="p_label"><span class="red">*</span>材料要求</td>
				<td><textarea class="p-textarea" id="details_content" name="details.content"></textarea></td>
			</tr>
			<tr>
				<td class="p_label"><span class="red">*</span>序列</td>
				<td><input type="text" id="details_seq" name="details.seq" /></td>
			</tr>
		</table>
		<p class="tc mt10">
			<input type="button" class="btn btn-small w3" id="addDetailsBtn" value="新增明细" />
			<input type="button" class="btn btn-small w3" id="updateDetailsBtn" value="修改明细" />
			<input type="button" class="btn btn-small w3" id="cancelUpdateBtn" value="取消" />
		</p>
		<p class="tc mt10">
			<li>换行请使用&lt;br&gt;</li>
			<li>超链接请使用&lt;a href="http://www.baidu.com"&gt;百度&lt;/a&gt;</li>
		</p>
		<script type="text/javascript">
			 $(function(){
				$("#updateDetailsBtn").hide();
				$("#cancelUpdateBtn").hide();
	
				$("#addDetailsBtn").click(function(){
					if ($("#details_title").val() == "") {
						alert("请输入材料名称");
						$("#details.title").focus();
						return;
					}
					if ($("#details_content").val() == "") {
						alert("请输入材料要求");
						$("#details_content").focus();
						return;
					}
					if (isNaN($("#details_seq").val()) || parseInt($("#details_seq").val()) >= 100) {
						alert("请输入合法的序列");
						$("#details_seq").focus();
						return;
					}
					$.ajax({
	        	 		url: "<%=basePath%>/visa/details/add.do",
						type:"post",
	        	 		data: {
								"details.documentId":${visaApplicationDocument.documentId},
								"details.title":$("#details_title").val(),
								"details.content":$("#details_content").val(),
								"details.seq":$("#details_seq").val()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
	        	 		dataType:"json",
	        	 		success: function(result) {
							if (result.success) {
								alert("新增成功");
								viewDocument(${visaApplicationDocument.documentId});
							} else {
								alert("新增失败");
							}
	        	 		}
	        		});
				});
				
				$("#updateDetailsBtn").click(function(){
					if ($("#details_title").val() == "") {
						alert("请输入材料名称");
						$("#details.title").focus();
						return;
					}
					if ($("#details_content").val() == "") {
						alert("请输入材料要求");
						$("#details_content").focus();
						return;
					}
					if (isNaN($("#details_seq").val()) || parseInt($("#details_seq").val()) >= 100) {
						alert("请输入合法的序列");
						$("#details_seq").focus();
						return;
					}
					$.ajax({
	        	 		url: "<%=basePath%>/visa/details/update.do",
						type:"post",
	        	 		data: {
								"details.detailsId":$("#details_detailsId").val(),
								"details.documentId":${visaApplicationDocument.documentId},
								"details.title":$("#details_title").val(),
								"details.content":$("#details_content").val(),
								"details.seq":$("#details_seq").val()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
	        	 		dataType:"json",
	        	 		success: function(result) {
							if (result.success) {
								alert("更新成功");
								viewDocument(${visaApplicationDocument.documentId});
							} else {
								alert("更新失败");
							}
	        	 		}
	        		});
				})
				
				$("#cancelUpdateBtn").click(function(){
					$("#details_detailsId").val("");
					$("#details_title").val("");
					$("#details_content").val("");
					$("#details_seq").val("");
	
					$("#addDetailsBtn").show();
					$("#updateDetailsBtn").hide();
					$("#cancelUpdateBtn").hide();
				});
			 });
	
			 function modify(detailsId) {
				var obj = $(".tr_" + detailsId + " > td").first();
				
				$("#details_detailsId").val(detailsId);
				$("#details_title").val(obj.html());
				$("#details_content").val(obj.next().html());
				$("#details_seq").val(obj.next().next().html());
	
				$("#addDetailsBtn").hide();
				$("#updateDetailsBtn").show();
				$("#cancelUpdateBtn").show();
			 }
			 
			 function del(detailsId) {
				 var flag = window.confirm("您确定需要删除此条目吗?删除此条目后请刷新签证材料。");
				 if (flag) {
					 $.ajax({
		        	 		url: "<%=basePath%>/visa/details/del.do",
							type:"post",
		        	 		data: {"details.detailsId":detailsId},
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
		        	 		dataType:"json",
		        	 		success: function(result) {
		        	 			viewDocument(${visaApplicationDocument.documentId});
		        	 		}
		        		});
				 }
			 }
		</script>		
	</body>
</html>