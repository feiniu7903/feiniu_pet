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
		$("#chk_all").click(function() {
            $('input[name="chk_list"]').attr("checked",this.checked);
          });
        var a_chk_list = $("input[name='chk_list']");
         a_chk_list.click(function(){
             $("#chk_all").attr("checked",a_chk_list.length == $("input[name='chk_list']:checked").length ? true : false);
         });
	});
	
	function addTravelTips(){
		var productId = ${productId};
		if($("input[name='chk_list']:checked").length == 0){
			alert("请选择要添加的旅游须知！");
			return;
		}
		var a_chk_list = $("input[name='chk_list']");
		var travelTipsIds = '';
		for (var i = 0; i < a_chk_list.length; i++) {
			var chk = a_chk_list[i];
			if($("#"+chk.id).attr("checked")){
				travelTipsIds += $("#"+chk.id).attr("value")+",";
			}
		}
		var url = "${basePath}/prod/addViewTravelTips.do";
		$.get(url,
				{"travelTipsIds":travelTipsIds,
				"productId":productId}, 
				function(data){
					if (data.success) {
						alert("添加成功!");
						parent.location.reload();
					} else {
						alert(data.msg);
					}
				});
	}
	
	function cancelTravelTips(){
		$("#chk_all").attr("checked",false);
		$('input[name="chk_list"]').attr("checked",false);
	}
	
	function checkAndSubmitTravelTipsCondition(){
		document.form1.submit();
	}

</script>
</head>
<body>
<form name='form1' id="form1" method='post' action='${basePath}/prod/searchTravelTipsToRouteProd.do'>
	<table class="p_table form-inline">
		<input type="hidden" name="productId" id="productId" value="${productId}"/>
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
			</td>
		</tr>
	</table>
</form>
<table class="p_table table_center Padding5" >
	<tbody>
		<tr>
			<th><input type="checkbox" id="chk_all" name="chk_all" value="全选"/></th>
			<th>国家</th>
			<th colspan="2">旅行须知名称</th>
		</tr>
		<s:iterator id="travelTips" var="travelTipsItem" value="travelTipsPage.items">
			<tr>
				<td><input type="checkbox" name="chk_list" id="_${travelTipsItem.travelTipsId}" value="${travelTipsItem.travelTipsId}"/></td>
				<td>${travelTipsItem.country}</td>
				<td colspan="2">${travelTipsItem.tipsName}</td>
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
		<tr>
			<td colspan="2" style="text-align:right;">
			<input type="button" value="添加" onclick="addTravelTips()"
				class="right-button08 btn btn-small" id="btnTravelTipsAdd" />
			</td>
			<td colspan="2"><input type="button" value="取消" onclick="cancelTravelTips()"
				class="right-button08 btn btn-small" id="btnTravelTipsCancel" />
			</td>
		</tr>
		<tr>
			<td colspan="4" ><font color="red">注意：添加过的国家请不要重复添加</font></td>
		</tr>
	</tbody>
</table>
</body>
</html>

