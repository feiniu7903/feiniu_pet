<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script>
	//弹出层
	function  addDetailDiv(id){
		var url = "${basePath}/distribution/searchDistributorIP.do?distributorInfoId=" + id;
		$("<iframe frameborder='0' id='saveIpDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "新增分销商IP",
	        position: 'top',
	        width: 324
		}).width(300).height(100).attr("src",url);
	}
	
	function  editDetailDiv(id){
		var url = "${basePath}/distribution/searchDistributorIP.do?distributorIpId=" +id;
		$("<iframe frameborder='0' id='updateIpDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "编辑分销商IP",
	        position: 'top',
	        width: 324
		}).width(300).height(100).attr("src",url);
	}
	
	function deleteIpDetailDiv(id){
		if(!confirm("确定要删除IP地址吗？")) {
			return false;
		}
		$.post(
				"${basePath}/distribution/deleteDistributorIP.do",
				{
					distributorIpId: id
				},
				function(data){
					var resultData = eval("(" + data + ")");
					if(resultData.result == '1'){
						alert("删除成功！");
						location.reload(true);
					}else{
						alert("删除失败！");
					}
				}
			);
	}
</script>
</head>
<body>
<div>
	<p style="width:90%; text-align: right;" >
		<a href="javascript:addDetailDiv('${distributorInfoId }');" class="right-button08 btn btn-small" >新增</a>
	</p>
	<table class="p_table form-inline">
	<tbody>
		<tr>
			<th width="15%">IP编号</th>
			<th width="30%">IP地址</th>
			<th width="35%">创建时间</th>
			<th width="20%">操作</th>
		</tr>
		<s:iterator id="distributorIp" value="listDistributorIp">
				 <tr bgcolor="FFFFFF" > 
				<td>${distributorIpId }</td>
				<td>${ip}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="createTime" />
				</td>
				<td>
					<a href="javascript:void(0);" onclick="editDetailDiv('${distributorIpId}');">修改</a> &nbsp;
					<a href="javascript:void(0);" onclick="deleteIpDetailDiv('${distributorIpId}');">删除</a>
				</td>
			</tr>
		</s:iterator> 
		
	</tbody>
</table>
</div>
</body>
</html>