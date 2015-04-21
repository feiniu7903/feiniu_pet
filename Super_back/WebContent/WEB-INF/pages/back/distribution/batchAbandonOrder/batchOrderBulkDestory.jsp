<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title> 批量废券码管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }themes/cc.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath}js/base/log.js" ></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${basePath}/js/ord/ord.js"></script>
<script type="text/javascript" src="${basePath}/js/ord/ord_div.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_common.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/remoteUrlLoad.js"></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function() {
		$("input[name='up_createTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='up_createTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTimeStart']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("input[name='createTimeEnd']").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
	

	function importExcel() {
		$("#popDiv").html();
	    $("#popDiv").load("${basePath}/distribution/batch/importExcel.do", function() {
	        $(this).dialog({
	            modal:true,
	            title:"上传券码信息",
	            width:350,
	            height:200
	        });
	     });
	} 

</script>
</head>
<body>
<div id="popDiv" style="display: none"></div>
<form name='CouponCodeForm' id="CouponCodeForm" method='post' action='${basePath}/distribution/batch/destroyList.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">文件名称：</td>
			<td ><input type="text" id="fileName" name="fileName"  value="${fileName}" class="newtext1"/>
			</td>
			<td class="p_label">上传时间</td>
			<td>
			<input readonly="readonly" name="up_createTimeStart" value="${up_createTimeStart }" />~
			<input readonly="readonly" name="up_createTimeEnd" value="${up_createTimeEnd }" />
			</td>
		</tr>
		<tr>
			<td class="p_label">上传人</td>
			<td><input type="text" id="creator" name="creator"  value="${creator}"/></td>
			<td class="p_label">执行时间</td>
			<td colspan="4">
			<input readonly="readonly" name="createTimeStart" value="${createTimeStart }" />~
			<input readonly="readonly" name="createTimeEnd" value="${createTimeEnd }" />
			</td>
			<td colspan="2"><input type="submit" value="查 询" 
				class="right-button08 btn btn-small" id="btnAbandonOrderQuery" />
			</td>
		</tr>
	</table>
</form>
<table>
	<tbody>
		<tr>
			<td colspan="2" height="40px"><input type="button" value="上传辅助码文件" onclick="javascript:importExcel()"
				class="right-button08 btn btn-small" id="batchCouponCode" />
			</td>
		</tr>
	</tbody>
</table>
<table class="p_table table_center Padding5">
	<tbody>
		<tr>
			<th>券码废码文件原名称</th>
			<th>券码废码文件服务器名称</th>
			<th>上传人</th>
			<th>上传时间</th>
			<th>开始执行时间</th>
			<th>结束执行时间</th>
			<th>券码数量</th>
			<th>成功数量</th>
			<th>状态</th>
			<th>操作</th>			
		</tr>
		<s:iterator id="destroyCodeLog" var="destroyCodeLog" value="destroyCouponCodePage.items">
			<tr>
				<td>${destroyCodeLog.initFileName}</td>
				<td>${destroyCodeLog.fileName}</td>
				<td>${destroyCodeLog.creator}</td>
				<td><s:date name="#destroyCodeLog.uploadTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${destroyCodeLog.cnStartTime}</td>
				<td>${destroyCodeLog.cnEndTime}</td>
				<td>${destroyCodeLog.sumNum}</td>
				<td>${destroyCodeLog.successNum}</td>
				<td>${destroyCodeLog.cnStatus}</td>
				<td>
				<a href="${basePath}/distribution/batch/downExcel.do?logId=${destroyCodeLog.logId}&fId=${destroyCodeLog.pristineId}" target="_blank">下载废码文件</a>
				<s:if test="#destroyCodeLog.errorId!= null">
					<a href="${basePath}/distribution/tuangouorder/downExcel.do?logId=${destroyCodeLog.logId}&fId=${destroyCodeLog.errorId}" target="_blank">下载废码失败文件</a>
				</s:if>	
				</td>
			</tr>
		</s:iterator> 
		<tr>
			<td colspan="2" align="left">共有<s:property
					value="destroyCouponCodePage.totalResultSize"/>个
			</td>
			<td colspan="8" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(destroyCouponCodePage)"/>
			</td>
		</tr>
	</tbody>
</table>
</body>
</html>