<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驴妈妈_地标地图管理后台</title>
<meta name="keywords" content="" />
<meta name="description" content="" />

<link rel="stylesheet" href="${basePath}css/place/backstage_table.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>

<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="${basePath}/js/place/houtai.js"></script>

<script type="text/javascript">
		/**
		 * 更新坐标有效性---恢复坐标(已有就设置有效否则新增)
		 */
		function updatePlaceLandMark(placeLandMarkId,isValid) {
			var jsonData = {placeLandMarkId:placeLandMarkId,isValid:isValid};			
			$.ajax({type:"POST",
				url:"/pet_back/place/updatePlaceLandMark.do",
				data:jsonData, 
				dataType:"json", 
				success:function (json) {
		     	if (json.flag=='Y' || json.msg!=null) {
		     		alert("修改成功！");
		     	}
		     	$("#fom").submit();
			}});
		}
</script>
</head>
<body>
	<div class="cg_tab_list"></div>
	<div style="display: block;" class="cg_tab_list">
		<form name="fom" id="fom" method="POST"
			action="/pet_back/place/queryLandMarkMapList.do">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30"><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td height="62">
									<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
											<td width="280"><img src="<%=basePath %>/img/icon/system.gif"
												width="20" height="18" />此处只维护“地标”经纬度</td>
											<td width="452" colspan="2">查看内容：
												<input type="radio" name="isValidRadio" value="isAll"
												   <s:if test='isValidRadio=="isAll"'> checked="checked" </s:if> />不限
												   
												<input type="radio" name="isValidRadio" value="isCoordinateEmpty"
												    <s:if test='isValidRadio=="isCoordinateEmpty"'> checked="checked" </s:if> />经纬度为空
												<!--  
												<input type="radio" name="isValidRadio" value="isMarkValid"
												    <s:if test='isValidRadio=="isMarkValid"'> checked="checked" </s:if> />标记有问题
												-->
												<input style="width: 300px;" type="text" name="landMarkName" value="${landMarkName}" /> 
												<input id="submitId" type="submit"	class="right-button02" value="查 询" /> 
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td>

						<div style="color: red;" id="div_err">
							<s:fielderror />
							<s:actionerror />
						</div>
					</td>
				</tr>
			</table>
		</form>
		<table cellspacing="0" cellpadding="0" border="0" class="tupian_table" width="100%">
			<tbody>
				<tr>
					<th align="center" width="10%">标记状态</th>
					<th align="center" width="20%">地标名称</th>
					<th align="center" width="20%">地标地址</th>
					<th align="center" width="10%">经度</th>
					<th align="center" width="10%">纬度</th>
					<th align="center" width="10%">最近更新时间</th>
					<th align="center" width="20%">更改操作</th>
				</tr>
				<s:iterator value="pagination.items">
				<tr>
					<td width="10%"><s:if test='isValid=="N"'>无效</s:if><s:else>有效</s:else></td>
					<td width="20%"><s:property value="landMarkName"/>&nbsp;</td>
					<td width="20%"><s:property value="landMarkAddress"/></td>
					<td width="10%"><s:property value="longitude"/></td>
					<td width="10%"><s:property value="latitude"/></td>
					<td width="10%"><span><s:date name="updateTime" format="yyyy-MM-dd"/></span></td>
					<td width="20%">
						<a href="/pet_back/place/modifyPlaceLandMarkCoordinate.do?placeLandMarkId=<s:property value="placeLandMarkId"/>">更改</a>&nbsp;&nbsp;
						<!-- 
	                    <s:if test='isValid=="N"'>
	                    		<a onclick="javascipt:updatePlaceLandMark('<s:property value="placeLandMarkId"/>', 'Y')" style="cursor:pointer;">恢复坐标- </a>
	                    </s:if>
	                    <s:else>
	                    		<a onclick="javascipt:updatePlaceLandMark('<s:property value="placeLandMarkId"/>', 'N')"  style="cursor:pointer;">坐标标记失效-</a>
	                    </s:else>
	                     -->
					</td>
				</tr>
				</s:iterator>

			</tbody>
		</table>
	</div>

	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">		
		<tr>
			<td height="33">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="right-font08">
					<tr>
						<td width="70%" align="right">&nbsp;&nbsp;&nbsp;共<font color="red">${pagination.totalResultSize}</font>个地标&nbsp;&nbsp;&nbsp;</td>
						<td width="30%" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
