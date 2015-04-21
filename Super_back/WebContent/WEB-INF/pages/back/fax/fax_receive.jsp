<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>传真回传</title>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<link rel="stylesheet" type="text/css"
	href="${basePath}/themes/base/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/style/panel-content.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/style/ui-common.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/style/ui-components.css" />
<script type="text/javascript"
	src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/form.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ebk/ebk_fax_receive.js"></script>
<script type="text/javascript">
	$(function() {
		$("#receive_link_certificate_btn").click(function() {
			$("#receive_link_certificate_div").showWindow({
				width : 1000,
				title : '查询关联记录'
			});
		});
		
		 $("#deleteItem").click(function(){
			    $("[name='valid']").attr("checked",this.checked);//全选
		  });
	})
	
	function batchDeleteItem() {
	var ordFaxRecvIds = $("[name='valid']:checked");
	if (ordFaxRecvIds.length == 0) {
		alert("请选择所需要删除的记录");
		return;
	} 
	
	var params="?";
	$(ordFaxRecvIds).each(function(){
		params=params+"ordFaxRecvIds="+this.value+"&";
	}); 
	
	if (window.confirm("是否需要批量删除？")) {
		params = params.substring(0,params.length-1);//去掉最后一个地址符号
 		$.ajax({
	 			type:"POST", 
	 			url:"${basePath}/fax/faxReceive/deleteOrdFaxRecvItem.do"+params,
	 			dataType:"json",
	 			success:function (obj) {
	 			      if(true==obj.success){
		    			 alert("删除成功");
		    			 window.location.reload();
	 			      }
					},
				error: function(){
					 alert("删除失败");
				}
 		});
	}
	
}
	function submitHandler(){
		$("#ordFaxRecvId").val($("#ordFaxRecvId").val().trim());
		if(!/^\d*$/.test($("#ordFaxRecvId").val()) && $("#ordFaxRecvId").val()!=""){
			alert("回传编号只能填写数字!\n");
			return false;
		} 
		var orderId=$("#orderId").val();
		if(!/^\d*$/.test(orderId) && orderId!=""){
			alert("订单编号只能填写数字!\n");
			return false;
		} 
		return true;
	}
</script>
</head>
<body>
	<div class="iframe-content">
		<div class="p_box">
			<form action="${basePath }/fax/faxReceive/search.do" id="searchForm" method="post">
				<table border="0" cellspacing="0" cellpadding="0"
					class="p_table form-inline">
					<tr>
						<td class="p_label">回传编号：</td>
						<td><s:textfield name="ordFaxRecv.ordFaxRecvId" id="ordFaxRecvId" /></td>
						<td class="p_label">订单编号：</td>
						<td><s:textfield name="orderId" id="orderId" /></td>
						<td class="p_label">发送号码：</td>
						<td><s:textfield name="ordFaxRecv.callerId" /></td>
					</tr>
					<tr>
						<td class="p_label">接收时间：</td>
						<td><s:textfield name="minRecvTime" cssClass="date" />~<s:textfield
								name="maxRecvTime" cssClass="date" /></td>
						<td class="p_label">回传状态：</td>
						<td><s:select list="recvStatusList"
								name="ordFaxRecv.recvStatus" listKey="code" listValue="name"
								headerKey="" headerValue="全部" /></td>
						<td></td>
						<td></td>
					</tr>
				</table>
		        <p class="tc mt20">
						<input type="submit" onclick="return submitHandler();"
							class="btn btn-small w6" value="查询" style="width: 100px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn btn-small w6" value="查询关联记录"
							id="receive_link_certificate_btn" style="width: 100px;" />&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn btn-small w6" value="上传附件"
							id="upload_receive_file_btn" style="width: 100px;" />
				</p>
			</form>
		</div>
		<input type="button" onclick="batchDeleteItem()" class="btn btn-small w5" value="批量删除记录"/>
		<div class="p_box">
			<table class="p_table table-center">
				<tr>
					<th><input type="checkbox" id="deleteItem"/></th>
					<th>回传编号</th>
					<th>发送号码</th>
					<th>接收时间</th>
					<th>与订单关联状态</th>
					<th>操作</th>
				</tr>
				<s:iterator value="ordFaxRecvPage.items">
					<tr>
						<td><input type="checkbox" name="valid" value="${ordFaxRecvId}"/></td>
						<td>${ordFaxRecvId }</td>
						<td>${callerId }</td>
						<td>${zhRecvTime }</td>
						<td>${zhRecvStatus }</td>
						<td class="oper"><s:if
								test='fileUrl != null && fileUrl != ""'>
								<a href="javascript:void(0);" class="showFaxReceiveFile"
									data="${ordFaxRecvId }">查看</a>
							</s:if></td>
					</tr>
				</s:iterator>
				<tr>
					<td colspan="2">总条数：<s:property
							value="ordFaxRecvPage.totalResultSize" />
					</td>
					<td colspan="5" align="right">${ordFaxRecvPage.pagination }</td>
				</tr>
			</table>
		</div>
	</div>
	<div id="receive_link_certificate_div"
		url="${basePath }/fax/faxReceive/receiveLinkIndex.do"></div>
	<div id="upload_receive_file_div"
		url="${basePath }/fax/faxReceive/uploadFileIndex.do"></div>
	<div id="show_receive_file_div"
		url="${basePath }/fax/faxReceive/showFaxRecvFileDetail.do"></div>
</body>
</html>