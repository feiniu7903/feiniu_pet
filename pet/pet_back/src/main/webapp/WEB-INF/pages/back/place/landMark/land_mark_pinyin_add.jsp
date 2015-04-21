<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="${basePath}/css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="${basePath}/css/panel-content.css"></link>
	</head>
	<body>
		<input type="hidden" name="pinYin" id="pinYin" value="" /> 
		<input type="hidden" name="jianPin" id="jianPin" value="" />
		<input type="hidden" name="objectType" id="objectType" value="${placeSearchPinyin.objectType}" />
		<table class="p_table">
			<tr>
				<td class="label" width="20%" style="width:none;float: none;"><span class="red">*</span>名称：</td>
				<td><input type="text" id="objectName" name="objectName" value="<s:property value='placeLandMark.landMarkName'/>"/> <button id="showPinyin" onclick="showpinyin()">显示拼音</button></td>
			</tr>
			<tr>
				<td class="label" width="20%" style="width:none;float: none;"><span class="red">*</span>拼音：</td>
				<td><input type="text" name="pinyintext" id="pinyintext" disabled="disabled"/></td>
			</tr>
		</table>
		<br/>
		<div id="pinyintd"></div>
		 <p class="tc mt20"><button class="btn btn-small w3" type="button" onclick="saveOrUpdatePinYinName()">保存</button></p> 
	</body>
	<script type="text/javascript">
		//显示拼音
		function showpinyin() {
			$("#objectName").val($.trim($("#objectName").val()));
			
			var objectName = $("#objectName").val(); 
			if ( objectName == '') {
				alert("不写名字怎么写拼音呢?");
				$("#objectName").focus();
				return;
			}		
			var pattern = /^[\s(\u4e00-\u9fa5)a-zA-z0-9《》·•\-（）]+$/g;
			if (!pattern.test(objectName)) {
				alert("请输入正确的名称");
				$("#objectName").focus();
				return;
			}
			$.ajax({
				type : "POST",
				async : false,
				url : "${basePath}/place/showObjectPinyinList.do",
				data : {
					"placeSearchPinyin.objectName" : objectName
				},
				dataType : "json",
				success : function(json) {
					var radiogroup="";
					for ( var i = 0; (i < json.list.length); i++) {
						if (json.list[i] != null) {
							var pinyin = json.list[i].pinYin;
							var jianPin = json.list[i].jianPin;
							var text = pinyin + ' (' + jianPin + ')';
							radiogroup = radiogroup + '<input type="radio" name="pinyinradio" onclick="javascript:checkpinyinradio(\''+pinyin+'\',\''+jianPin+'\')" value="" />&nbsp;&nbsp;' + text + '<br/>';
						}
					}
					$("#pinyintd").html(radiogroup);
				}
			});
		};
		
		//选中一个拼音
		function checkpinyinradio(pinyin, jianpin) {
			$("#pinyintext").val(pinyin);
			$("#jianPin").val(jianpin);
			$("#pinYin").val(pinyin);
		};
		
		function saveOrUpdatePinYinName() {
			$("#objectName").val($.trim($("#objectName").val()));
			var objectName = $("#objectName").val();
			var pinYin = $("#pinYin").val();
			var jianPin = $("#jianPin").val();
			var objectType = $("#objectType").val();
			
			if (objectName == "") {
				alert("请输入正确的名称");
				$("#objectName").focus();
				return;
			}
			if ($("#pinyintext").val() == "" 
					|| pinYin == "" 
					|| jianPin == "") {
				alert("请先选择正确的拼音");
				return;
		    }

			if (objectType == 'PLACE_LAND_MARK_NAME') {
				$("#placeLandMark_pinYin").val(pinYin);
				$("#placeLandMark_landMarkName1").val(objectName);
				
			} else if (objectType == 'PLACE_LAND_MARK_HFKW') {
				var hfkw = objectName + "~" + pinYin + "~" + jianPin + "";
				$("#placeLandMark_hfkw").val(hfkw);
			}
			$("#win_pinyin_hk").dialog("close");
		}
	</script>
</html>
