<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>传真发送日志</title>
</head>
<body>
	<div class="iframe-content">
		<table class="p_table form-inline">
		  <tbody>
			<tr>
				<th>发送编号</th>
				<th>发送传真号</th>
				<th>请求发送时间</th>
				<th>实际发送时间</th>
				<th>发送状态</th>
				<th>操作人</th>
			</tr>
			<s:iterator value="ebkFaxSendPage.items" var="faxSend">
			<tr>
				<td>${faxSend.ebkFaxSendId }</td>
				<td>${faxSend.toFax }</td>
				<td><s:date name="#faxSend.createTime" format="yyyy-MM-dd HH:mm"/></td>
				<td><s:date name="#faxSend.sendTime" format="yyyy-MM-dd HH:mm"/></td>
				<td>${faxSend.zhSendStatus }</td>
				<td>${faxSend.operatorName }</td>
			</tr>
			</s:iterator>
			<s:if test="ebkFaxSendPage.totalResultSize>10">
			<tr>
   				<td colspan="2" align="right">总条数：${ebkFaxSendPage.totalResultSize}</td>
				<td colspan="4" align="right" style="text-align:right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(ebkFaxSendPage)"/></td>
 			</tr>
 			</s:if>
			</tbody>
		</table>
		<br/>
		<s:if test="ordFaxRecvList.size>0">
		<table class="p_table form-inline">
		  <tbody>
			<tr>
				<th>传真编号</th>
				<th>发送号码</th>
				<th>接收时间</th>
				<th>传真文件</th>
				<th>操作</th>
			</tr>
			<s:iterator value="ordFaxRecvList" var="recv">
			<tr>
			  <td>${recv.ordFaxRecvId }</td>
			  <td>${recv.callerId }</td>
			  <td><s:date name="#recv.recvTime" format="yyyy-MM-dd HH:mm"/></td>
			  <td>${recv.fileUrl }</td>
			  <td><a href="javascript:void(0);" class="showFaxReceiveFile2" data="${recv.ordFaxRecvId }">查看回传件</a></td>
			</tr>
			</s:iterator>
		 </tbody>
		</table>
		</s:if>
	</div>
	<div id="show_receive_file_div2" url="${basePath }/fax/faxReceive/showFaxRecvFileDetail.do"></div>
	<script type="text/javascript">
	$(function(){
		 $(".showFaxReceiveFile2").bind("click", function() {
			var data = $(this).attr("data");
			if (data == undefined || data == null || data == "") {
				alert("无法查看该回传件！");
				return false;
			}
			$("#show_receive_file_div2").showWindow({
				width : 1000,
				title : '查看回传件',
				data : {
					ordFaxRecvId : data,
					modifyCertIdflag : 'true'
				}
			});
		 });
	});
	</script>
</body>
</html>