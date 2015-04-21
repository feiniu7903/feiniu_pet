<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<input type="hidden" id="placeSearchPinyinId" />
		<table class="p_table">
			<tr>
				<td class="label" width="20%" style="width:none;float: none;"><span class="red">*</span>名称：</td>
				<td><input type="text" id="objectName" name="objectName" /> <button id="showPinyin" onclick="showpinyin()">显示拼音</button></td>
			</tr>
			<tr>
				<td class="label" width="20%" style="width:none;float: none;"><span class="red">*</span>拼音：</td>
				<td><input type="text" name="pinyintext" id="pinyintext" disabled="disabled" /></td>
			</tr>
		</table>
		<br/>
		<div id="pinyintd"></div>
		<select style="width: 100%" size="10" onclick="selectvalPinyin()" id="pinyinselectbox">
			<s:iterator value="pinyinList" var="pinyin">
				<option value="${pinyin.placeSearchPinyinId}">${pinyin.objectName} ${pinyin.pinYin}(${pinyin.jianPin})</option>
			</s:iterator>					
		</select>
		 
		<p class="tc mt20">
			<button class="btn btn-small w5" type="button" disabled="disabled" id="deletebutton" onclick="deletepinyinsearch()">删除选中项</button>
			<button class="btn btn-small w5" type="button" disabled="disabled" id="updatebutton" onclick="updatepinyinsearch()">更新选中项</button>
			<button class="btn btn-small w5" type="button" disabled="disabled" id="addbutton" onclick="addpinyinsearch()">新增数据</button>
		</p> 
	</body>
	<script type="text/javascript">
		function showpinyin() {
			$("#objectName").val($.trim($("#objectName").val()));
			var objectId = ${place.placeId};
			var objectType = "PLACE_HFKW";
			
			var objectName = $("#objectName").val(); 
			if ( objectName == '') {
				alert("亲，你不写名称我怎么写拼音呢?");
				$("#objectName").focus();
				return;
			}		
			var pattern = /^[\s(\u4e00-\u9fa5)a-zA-z0-9《》·•\-（）]+$/g;
			if (!pattern.test(objectName)) {
				alert("请输入正确的名称");
				$("#objectName").focus();
				return;
			}
			
			searchPinyin(objectId, objectType, objectName);				
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
			
			$("#addbutton").removeAttr("disabled");
		};		
		
		function selectvalPinyin() {
			$("#placeSearchPinyinId").val($("#pinyinselectbox").val());
			
			$("#updatebutton").removeAttr("disabled");
			$("#deletebutton").removeAttr("disabled");

		};
		
		/**
		 * 新增高频关键字
		 */
		function addpinyinsearch() {
			$("#addbutton").attr("disabled", true);
			$("#objectName").val($.trim($("#objectName").val()));
			
			var objectname = $("#objectName").val();
			var jianpin = $("#jianPin").val();
			var pinyin = $("#pinYin").val();
			var objectId = ${place.placeId};
			var objectType = "PLACE_HFKW";
			
			var pattern = /^[\s(\u4e00-\u9fa5)a-zA-z0-9《》·•\-（）]+$/g;
			if (!pattern.test(objectname)) {
				alert("请输入正确的字符");
				$("#addbutton").removeAttr("disabled");
				return;
			}
			if ('' == objectname || jianpin == '' || pinyin == '' ) {
				alert('请先选择正确的拼音');
				$("#addbutton").removeAttr("disabled");
				return;
			}
			
			var param = {
				"placeSearchPinyin.objectName" : objectname,
				"placeSearchPinyin.jianPin" : jianpin,
				"placeSearchPinyin.pinYin" : pinyin,
				"placeSearchPinyin.objectType" : objectType,
				"placeSearchPinyin.objectId" : objectId
			};
			savePlaceSearchPinyin(param);
			$("#addbutton").removeAttr("disabled");
		};
	
		/**
		 * 更新高频关键字
		 */		
		function updatepinyinsearch() {
			$("#updatebutton").attr("disabled", true);
			$("#objectName").val($.trim($("#objectName").val()));
			
			var objectname = $("#objectName").val();
			var jianpin = $("#jianPin").val();
			var pinyin = $("#pinYin").val();
			var objectId = ${place.placeId};
			var objectType = "PLACE_HFKW";
			
			var pattern = /^[\s(\u4e00-\u9fa5)a-zA-z0-9《》·•\-（）]+$/g;
			if (!pattern.test(objectname)) {
				alert("请输入正确的字符");
				return;
			}

			var placeSearchPinyinId = $("#placeSearchPinyinId").val();
			if(placeSearchPinyinId==null || placeSearchPinyinId=='') {
				alert("亲，请选择你要更新的项！");
				$("#updatebutton").removeAttr("disabled");
				return ;
			}
			
			if ('' == objectname || jianpin == '' || pinyin == '' ) {
				alert('请先选择正确的拼音');
				$("#updatebutton").removeAttr("disabled");
				return;
			}
			
			var param = {
					"placeSearchPinyin.placeSearchPinyinId" : placeSearchPinyinId,
					"placeSearchPinyin.objectName" : objectname,
					"placeSearchPinyin.jianPin" : jianpin,
					"placeSearchPinyin.pinYin" : pinyin,
					"placeSearchPinyin.objectType" : objectType,
					"placeSearchPinyin.objectId" : objectId
			};
			savePlaceSearchPinyin(param);
			$("#updatebutton").attr("disabled", true);
		};		
		
		function deletepinyinsearch() {
			var placeSearchPinyinId = $("#placeSearchPinyinId").val();
			var objectId = ${place.placeId};
			var objectType = "PLACE_HFKW";
			
			if(placeSearchPinyinId == null || placeSearchPinyinId=='') {
				alert("亲，请先选择你要删除的项！");
				return ;
			}
			
			$.ajax({
				type : "POST",
				url : "${basePath}/place/deletePlaceSearchPinyin.do",
				data :{
					"placeSearchPinyin.placeSearchPinyinId" : placeSearchPinyinId,
					"placeSearchPinyin.objectType" : objectType,
					"placeSearchPinyin.objectId" : objectId
				},
				dataType : "json",
				success : function(json) {
					fixOptionValue(json);
				}
			});
			$("#deletebutton").attr("disabled", true);
		};
		
		function savePlaceSearchPinyin(param) {
			$.ajax({
				type : "POST",
				url : "${basePath}/place/saveOrUpdateHFKWPlacePinYin.do",
				data :param,
				dataType : "json",
				success : function(json) {
					fixOptionValue(json);
					alert("操作成功!");
				}
			});
		};
		
		/**
		 * 重新刷新高频关键字的显示记录
		 **/
		function fixOptionValue(json) {
			if (json.success) {
				var pinyinOption = "";
				for ( var i = 0; (i < json.list.length); i++) {
					if (json.list[i] != null) {
						var pinyin = json.list[i].pinYin;
						var jianPin = json.list[i].jianPin;
						var text = json.list[i].objectName + pinyin + ' (' + jianPin + ')';
						pinyinOption = pinyinOption + '<option value="'+json.list[i].placeSearchPinyinId+'">' + text + '<br/>';
					}
				}
				$("#pinyinselectbox").html(pinyinOption);
				
				$("#place_hfkw").val(json.HFKW);
				
				$("#objectName").val('');
				$("pinyintext").val('');
				$("#jianPin").val('');
				$("#pinYin").val('');
				
				$("#addbutton").attr("disabled", true);
				$("#updatebutton").attr("disabled", true);
				$("#deletebutton").attr("disabled", true);
			} else {
				alert("更新失败，请重新尝试");
			}
		};
	</script>
</html>
