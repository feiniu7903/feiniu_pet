<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>产品管理_旅行须知管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
<script type="text/javascript">
	$(function(){
		$("#form1 #continent").val('${travelTips.continent}');	
	});	
	//显示弹出层
	function addDetailDiv(){
		var url = "${basePath}/prod/editTravelTips.do";		
		$("<iframe frameborder='0' id='addDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "新增旅行须知基本信息",
	        position: 'top',
	        width: 720
		}).width(700).height(450).attr("src",url);
	}
	
	function editDetailDiv(id) {
		var url = "${basePath}/prod/editTravelTips.do?travelTips.travelTipsId=" + id;
		
		$("<iframe frameborder='0' id='editDetailWin'></iframe>").dialog({
			autoOpen: true, 
	        modal: true,
	        title : "修改旅行须知基本信息",
	        position: 'top',
	        width: 720
		}).width(700).height(450).attr("src",url);
	}
	
	function deleteTravelTips(travelTipsId){
		if(!confirm('确定删除旅游须知吗？会把绑定产品的旅游须知一并删除！')){
			return ;
		}
		var url = '${basePath}/prod/deleteTravelTips.do';
		$.get(url,
				{"travelTips.travelTipsId":travelTipsId}, 
				function(data){
					if (data.success){
						alert("删除成功!");
						location.reload();
					}else{
						alert(data.msg);
					}
				});
	}
	
	function checkAndSubmitTravelTipsCondition(){
		document.form1.submit();
	}
	
	function openWin(url, width, height) {
		window.open(url,"_blank","height="+ height + ", width="	+ width
					+ ", top=250, left=350, toolbar=no,menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
	}

</script>
</head>
<body>
<form name='form1' id="form1" method='post' action='${basePath}/prod/selectTravelTipsList.do'>
	<table class="p_table form-inline">
		<tr>
			<td class="p_label">所在洲域：</td>
			<td>
				<select id="continent" name="travelTips.continent">
				<option value="">所在洲域</option>
				<s:iterator value="continentList" var="con">
				<option value="${con.code}">${con.cnName}</option> 
				</s:iterator>
				</select>
			</td>
			<td class="p_label">所在国家：</td>
			<td>
				<input type="text" id="travelTips.country" name="travelTips.country"  value="${travelTips.country}"/>
			</td>
		</tr>
		<tr>
			<td class="p_label">须知名称</td>
			<td ><input type="text" id="travelTips.tipsName" name="travelTips.tipsName"  value="${travelTips.tipsName}" class="newtext1"/></td>
			<td colspan="2">
			<input type="button" value="查 询" onclick="checkAndSubmitTravelTipsCondition()"
				class="right-button08 btn btn-small" id="btnTravelTipsQuery" />
			&nbsp;				
			<a href="javascript:addDetailDiv();" class="right-button08 btn btn-small" >新增</a>
			</td>
		</tr>
	</table>
</form>
<table class="p_table table_center Padding5">
	<tbody>
		<tr>
			<th>洲域</th>
			<th>国家</th>
			<th>旅行须知名称</th>
			<th>操作</th>
		</tr>
		<s:iterator id="travelTips" var="travelTipsItem" value="travelTipsPage.items">
			<tr>
				<td>${travelTipsItem.continentCnName}</td>
				<td>${travelTipsItem.country}</td>
				<td>${travelTipsItem.tipsName}</td>
				<td>
					<a href="javascript:editDetailDiv('${travelTipsItem.travelTipsId}');">修改</a> &nbsp;&nbsp;
					<a href="javascript:deleteTravelTips('${travelTipsItem.travelTipsId}');">删除</a> &nbsp;&nbsp;
					<a href="javascript:openWin('/super_back/common/upload.do?objectId=${travelTipsItem.travelTipsId}&objectType=TRAVEL_TIPS',500,400)">上传文件</a>
				</td>
			</tr>
		</s:iterator> 
		
		<tr>
			<td colspan="2" align="left">共有<s:property
					value="travelTipsPage.totalResultSize" />个
			</td>
			<td colspan="2" align="right" style="text-align:right">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(travelTipsPage)"/>
			</td>
		</tr>
		
	</tbody>
</table>
</body>
</html>

