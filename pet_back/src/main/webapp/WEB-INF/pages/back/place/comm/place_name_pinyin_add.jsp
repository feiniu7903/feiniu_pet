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
		<input type="hidden" id="oldPlaceName" name="oldPlaceName" value="<s:property value='place.name' />"  />
		<input type="hidden" id="oldPinYinUrl" name="oldPinYinUrl" value="<s:property value='place.pinYinUrl' />" />
		<input type="hidden" name="pinYin" id="pinYin" value="" /> 
		<input type="hidden" name="jianPin" id="jianPin" value="" />
		<table class="p_table">
			<tr>
				<td class="label" width="20%" style="width:none;float: none;"><span class="red">*</span>名称：</td>
				<td><input type="text" id="objectName" name="objectName" value="<s:property value='place.name'/>"/> <button id="showPinyin" onclick="showpinyin()">显示拼音</button></td>
			</tr>
			<tr>
				<td class="label" width="20%" style="width:none;float: none;"><span class="red">*</span>拼音：</td>
				<td><input type="text" name="pinyintext" id="pinyintext" disabled="disabled" value="${placeNamePinYin }"/></td>
			</tr>
		</table>
		<br/>
		<div id="pinyintd"></div>
		 <p class="tc mt20"><button class="btn btn-small w3" type="button" onclick="saveOrUpdatePlacePinYinName()">保存</button></p> 
	</body>
	<script type="text/javascript">
		function showpinyin() {
			$("#objectName").val($.trim($("#objectName").val()));
			
			var objectName = $("#objectName").val(); 
			if ( objectName == '') {
				alert("亲，你不写目的地名字我怎么写拼音呢?");
				$("#objectName").focus();
				return;
			}		
			var pattern = /^[\s(\u4e00-\u9fa5)a-zA-z0-9《》·•\-（）]+$/g;
			if (!pattern.test(objectName)) {
				alert("请输入正确的名称");
				$("#objectName").focus();
				return;
			}
			
			var oldPinYinUrl=$("#oldPinYinUrl").val();
			if(oldPinYinUrl != ""){
				if(objectName!=$("#oldPlaceName").val()){
					$.ajax({
						type:"post",
					    url:"${basePath}/place/isExistCheck.do",
					    data:{
					    	"place.name" : 	objectName,
					    	"savePlaceNameFlag" : 'N'
					    },
					    success:function(data){
					    	if(data=="Y"){
					        	alert("名称已存在！请更换名称再试!");
					        	$('#objectName').val($("#oldPlaceName").val());
					        }else{
					        	searchPinyin("${place.placeId}", "PLACE_NAME", objectName);
					        }
					    }
					});
				}else{
					alert("名称已经存在，请更换名称再试!");
				}
			} else {
				searchPinyin("${place.placeId}", "PLACE_NAME", objectName);
			}				
		};
		
		function searchPinyin(objectId,objectType,objectName){
			$.ajax({
				type : "POST",
				async : false,
				url : "${basePath}/place/showObjectPinyinList.do",
				data : {
					"placeSearchPinyin.objectId" : objectId,
					"placeSearchPinyin.objectType" : objectType,
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
		}
		
		function checkpinyinradio(pinyin, jianpin) {
			$("#pinyintext").val(pinyin);
			$("#jianPin").val(jianpin);
			$("#pinYin").val(pinyin);			
		};
		
		function saveOrUpdatePlacePinYinName() {
			$("#objectName").val($.trim($("#objectName").val()));
			if ($("#objectName").val() == "") {
				alert("请输入正确的名称");
				$("#objectName").focus();
				return;
			}
			
			if ($("#pinyintext").val() == "" 
					|| $("#pinYin").val() == "" 
					|| $("#jianPin").val() == "") {
				alert("请先选择正确的拼音");
				return;
			}
			$.ajax({
				type : "POST",
				async : false,
				url : "${basePath}/place/saveOrUpdatePlacePinYinName.do",
				data : {
					"placeSearchPinyin.objectId" : "${place.placeId}",
					"placeSearchPinyin.objectType" : "PLACE_NAME",
					"placeSearchPinyin.pinYin" : $("#pinYin").val(),
					"placeSearchPinyin.jianPin" : $("#jianPin").val(),
					"placeSearchPinyin.objectName" : $("#objectName").val()
				},
				dataType : "json",
				success : function(json) {
					if (json.success) {
						$("#placeName").val(json.name);
						$("#place_pinYin").val(json.pinYin);
						$("#place_pinYinUrl").val(json.pinYinUrl);
						alert("更新成功 ");
						$("#win_name").dialog("close");
					} else {
						alert("更新失败，请重新尝试");
					}
				}
			});			
			
		}
	</script>
</html>
