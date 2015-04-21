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
<title>驴妈妈_景点/目的地管理后台</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="css/place/backstage_table.css" />
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="js/place/houtai.js"></script>
<script type="text/javascript">
		/**
		 * 更新坐标有效性
		 */
		function updateCoordinate(placeId,isValid) {
			var jsonData = {id:placeId,isValid:isValid};			
			$.ajax({type:"POST", 
				url:"/pet_back/updatePlaceCoordinatGoogle.do",
				data:jsonData, 
				dataType:"json", 
				success:function (json) {
		     	if (json.flag=='Y' || json.msg!=null) {
		     		alert("修改成功！");
		     	}
		     	$("#fom").submit();
			}});
		}
		/**
		 * 标记为境外酒店/景点
		 * @param {Object} placeId
		 * @param {Object} isValid
		 */
		function updatePlaceType(placeId) {			
			$.ajax({type:"POST", 
				url:"/pet_back/updateGooglePlaceType.do",
				data:{id:placeId}, 
				dataType:"json",
				success:function (json) {
			     	if (json.flag=='Y') {
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
			action="/pet_back/queryGoogleMapList.do">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30"><table width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td height="62">
									<table width="98%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="280"><img src="<%=basePath %>/img/icon/system.gif"
												width="20" height="18" />此处只维护“国内国外景点/酒店”经纬度</td>
											<td width="452" colspan="2">查看内容：
												<input type="radio" name="isValidRadio" value="isAll"
												<s:if test='isValidRadio=="isAll"'> checked="checked" </s:if> />不限
												<input type="radio" name="isValidRadio"
												value="isCoordinateEmpty"
												<s:if test='isValidRadio=="isCoordinateEmpty"'> checked="checked" </s:if> />经纬度为空
												<input type="radio" name="isValidRadio" value="isMarkValid"
												<s:if test='isValidRadio=="isMarkValid"'> checked="checked" </s:if> />标记有问题
												<input style="width: 300px;" type="text" name="placeName"
												value="${placeName}" /> 
												<input id="submitId" type="submit"	class="right-button02" value="查 询" /> 
												<!-- <input	id="convertCoodirnate" type="hidden"	value="转换Google坐标到baidu坐标"
												onclick="javascript:window.location.href='/pc/baiduMap!convertCoordinate.do?isValidRadio=isCoordinateNotEmpty';" /> -->
												<%-- <input type='hidden' name='page' value="${page}" /> --%>
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
		<table cellspacing="0" cellpadding="0" border="0" class="tupian_table">
			<tbody>
				<tr>
					<th width="10%">标记状态</th>
					<th width="20%">景区名称</th>
					<th width="20%">填写地址</th>
					<th width="20%">经纬度地址</th>
					<th width="10%">最近更新时间</th>
					<th width="20%">更改操作</th>
				</tr>

				<s:iterator value="pagination.items">
				<tr>
					<td width="10%"><s:if test='isValid=="N"'>是</s:if><s:else>否</s:else></td>
					<td width="20%"><s:property value="coordinateName"/>&nbsp;</td>
					<td width="20%"><s:property value="placeAddress"/></td>
					<td width="20%"><s:property value="coordinateAddress"/></td>
					<td width="10%"><span><s:date name="updateTime" format="yyyy-MM-dd"/></span></td>
					<td width="20%">
					<a href="/pet_back/modifyGoogleCoordinate.do?placeId=<s:property value="placeId"/>">更改-</a>&nbsp;&nbsp;
	                    <s:if test='isValid=="N"'>
	                    		<a onclick="javascipt:updateCoordinate('<s:property value="placeId"/>', 'Y')" style="cursor:pointer;">恢复坐标- </a>
	                    		</s:if>
	                    <s:else>
	                    		<a onclick="javascipt:updateCoordinate('<s:property value="placeId"/>', 'N')"  style="cursor:pointer;">坐标标记失效-</a>
	                    		</s:else>
	                    <a onclick="javascipt:updatePlaceType('<s:property value="placeId"/>')" style="cursor:pointer;">标记为境外景点/酒店</a>
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
						<td width="70%" align="right">&nbsp;&nbsp;&nbsp;共<font color="red">${pagination.totalResultSize}</font>个景区&nbsp;&nbsp;&nbsp;</td>
						<td width="30%" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
