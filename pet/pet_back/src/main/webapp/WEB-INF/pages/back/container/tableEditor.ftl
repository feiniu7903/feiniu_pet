<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<#setting classic_compatible=true>
<#setting number_format="#.##">
<html>
	<head>
		<title>TableEditor</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<a href="/pet_back/tableEditorFromPlace.do">Switch to Fromplace Editor</a>
		<form id="form2" method="post" action="/pet_back/tableEditorQuery.do">
			<select name="selContainerCode">
				<option value="">请选择</option>
				<option value="DZMP_RECOMMEND"<#if selContainerCode=="DZMP_RECOMMEND"> selected="selected"</#if>>打折门票频道-打折门票推荐</option>
				<option value="ZYZZ_RECOMMEND"<#if selContainerCode=="ZYZZ_RECOMMEND"> selected="selected"</#if>>自游自在频道-自游自在推荐</option>
				<option value="ZBY_RECOMMEND"<#if selContainerCode=="ZBY_RECOMMEND"> selected="selected"</#if>>周边游频道-周边游推荐</option>
				<option value="KXLX"<#if selContainerCode=="KXLX"> selected="selected"</#if>>周边游频道-开心驴行</option>
				<option value="GNY_RECOMMEND"<#if selContainerCode=="GNY_RECOMMEND"> selected="selected"</#if>>国内游频道-国内游推荐</option>
				<option value="CJY_RECOMMEND"<#if selContainerCode=="CJY_RECOMMEND"> selected="selected"</#if>>出境游频道-出境游推荐</option>
			</select>
			
			<select name="selFromPlaceId">
				<option value="">请选择</option>
			<#list fromPlaces as fromPlace>
				<#if fromPlace.fromPlaceId??>
				<option value="${fromPlace.fromPlaceId}"<#if selFromPlaceId=="${fromPlace.fromPlaceId}"> selected="selected"</#if>>${fromPlace.fromPlaceName}</option>
				</#if>
			</#list>
			</select>
			
			<select name="selToPlaceId">
				<option value="">请选择</option>
			<#list toPlaces as toPlace>
				<option value="${toPlace.toPlaceId}"<#if selToPlaceId=="${toPlace.toPlaceId}"> selected="selected"</#if>>${toPlace.toPlaceName}</option>
			</#list>
			</select>
			<input type="submit" value="Query" />
			<input id="queryMore" type="button" value="Query More" />
		</form><hr />
		
		<form id="form1" method="post" action="/pet_back/tableEditor.do" onsubmit="return check();">
			<input type="hidden" name="selContainerCode" value="${selContainerCode}" />
			<input type="hidden" name="selFromPlaceId" value="${selFromPlaceId}" />
			
			<input type="submit" value="Save" />
			<input type="button" value="Cancel" onclick="window.location.reload();" />
			<table id="table1">
				<tr>
					<td>Add/Remove</td>
					<td></td>
					<td>ID</td>
					<td></td>
					<td>*容器代码</td>
					<td>*容器名称</td>
					<td></td>
					<td>出发地ID</td>
					<td>出发地名称</td>
					<td></td>
					<td>*目的地ID</td>
					<td>*目的地名称</td>
					<td title="目的地显示在频道页前台的显示名称">*显示名称</td>
					<td title="当目的地为省时：存usr_capital表的capital_id；当目的地为市时：存usr_city表的city_id">对应IP库里的ID</td>
					<td>上级目的地ID</td>
					<td>目的地排序</td>
					<td></td>
					<td>是否隐藏目的地</td>
					<td></td>
					<td>是否显示在更多里</td>
					<td title="区域名称，如：华东、华北">区域名称</td>
					<td>区域排序</td>
					<td>引用出发地ID</td>
				</tr>
				
				<#list containers as container>
				<tr>
					<td><input type="button" class="add" value="+" /></td>
					<td>${container_index+1}</td>
					<td><input type="text" name="id" value="${container.id}" readonly="readonly" /></td>
					<td>
						<select class="containerSel" disabled="disabled">
							<option value="">请选择</option>
							<option value="DZMP_RECOMMEND">打折门票频道-打折门票推荐</option>
							<option value="ZYZZ_RECOMMEND">自游自在频道-自游自在推荐</option>
							<option value="ZBY_RECOMMEND">周边游频道-周边游推荐</option>
							<option value="KXLX">周边游频道-开心驴行</option>
							<option value="GNY_RECOMMEND">国内游频道-国内游推荐</option>
							<option value="CJY_RECOMMEND">出境游频道-出境游推荐</option>
						</select>
					</td>
					<td><input type="text" name="containerCode" value="${container.containerCode}" readonly="readonly" /></td>
					<td><input type="text" name="containerName" value="${container.containerName}" readonly="readonly" /></td>
					<td>
						<select class="fromPlaceSel" disabled="disabled">
							<option value="">请选择</option>
						<#list fromPlaces as fromPlace>
							<#if fromPlace.fromPlaceId??>
							<option value="${fromPlace.fromPlaceId}">${fromPlace.fromPlaceName}</option>
							</#if>
						</#list>
						</select>
					</td>
					<td><input type="text" name="fromPlaceId" value="${container.fromPlaceId}" readonly="readonly" maxlength="10" /></td>
					<td><input type="text" name="fromPlaceName" value="${container.fromPlaceName}" readonly="readonly" maxlength="50" /></td>
					<td>
						<select class="toPlaceSel" disabled="disabled">
							<option value="">请选择</option>
							<option value="DEPART_AREA_2">欧洲</option>
							<option value="DEPART_AREA_4">美洲</option>
							<option value="DEPART_AREA_6">大洋洲</option>
							<option value="DEPART_AREA_8">中东非洲</option>
							<option value="DEPART_AREA_7">东南亚</option>
							<option value="DEPART_AREA_1">南亚</option>
							<option value="DEPART_AREA_5">日韩</option>
							<option value="DEPART_AREA_9">港澳</option>
							<option value="SHIP">邮轮</option>
							<option value="ISLAND">海岛</option>
						</select>
					</td>
					<td><input type="text" name="toPlaceId" value="${container.toPlaceId}" readonly="readonly" /></td>
					<td><input type="text" name="toPlaceName" value="${container.toPlaceName}" readonly="readonly" /></td>
					<td><input type="text" name="displayedToPlaceName" value="${container.displayedToPlaceName}" /></td>
					<td><input type="text" name="ipLocationId" value="${container.ipLocationId}" readonly="readonly" /></td>
					<td><input type="text" name="destId" value="${container.destId}" /></td>
					<td><input type="text" name="toPlaceSeq" value="${container.toPlaceSeq}" maxlength="3" /></td>
					<td>
						<select class="isToPlaceHiddenSel">
							<option value="">请选择</option>
							<option value="Y">是</option>
							<option value="">否</option>
						</select>
					</td>
					<td><input type="text" name="isToPlaceHidden" value="${container.isToPlaceHidden}" readonly="readonly" /></td>
					<td>
						<select class="isShownInMoreSel">
							<option value="">请选择</option>
							<option value="Y">是</option>
							<option value="">否</option>
						</select>
					</td>
					<td><input type="text" name="isShownInMore" value="${container.isShownInMore}" readonly="readonly" /></td>
					<td><input type="text" name="zoneName" value="${container.zoneName}" maxlength="50" /></td>
					<td><input type="text" name="zoneSeq" value="${container.zoneSeq}" maxlength="3" /></td>
					<td><input type="text" name="referredFromPlaceId" value="${container.referredFromPlaceId}" maxlength="50" /></td>
					<td><input type="hidden" name="isChanged" /></td>
				</tr>
				</#list>
			</table>
			<input type="submit" value="Save" />
			<input type="button" value="Cancel" onclick="window.location.reload();" />
		</form>
		<script src="/pet_back/js/base/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script>
		$(document).ready(function(){
			$("#form1 :input").live('change',function(){
				$(this).parent().parent().find(":hidden[name='isChanged']").val(1);
			});
			
			$(".add").live('click', function(){
				var row = '<tr>';
				row += '<td><input type="button" class="add" value="+" /><input type="button" class="remove" value="-" /></td>';
				row += '<td></td>';
				row += '<td><input type="text" name="id" readonly="readonly" /></td>';
				row += '<td>';
				row += '<select class="containerSel">';
				row += '<option value="">请选择</option>';
				row += '<option value="DZMP_RECOMMEND">打折门票频道-打折门票推荐</option>';
				row += '<option value="ZYZZ_RECOMMEND">自游自在频道-自游自在推荐</option>';
				row += '<option value="ZBY_RECOMMEND">周边游频道-周边游推荐</option>';
				row += '<option value="KXLX">周边游频道-开心驴行</option>';
				row += '<option value="GNY_RECOMMEND">国内游频道-国内游推荐</option>';
				row += '<option value="CJY_RECOMMEND">出境游频道-出境游推荐</option>';
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="containerCode" readonly="readonly" /></td>';
				row += '<td><input type="text" name="containerName" readonly="readonly" /></td>';
				row += '<td>';
				row += '<select class="fromPlaceSel">';
				row += '<option value="">请选择</option>';
				<#list fromPlaces as fromPlace>
					<#if fromPlace.fromPlaceId??>
					row += '<option value="${fromPlace.fromPlaceId}">${fromPlace.fromPlaceName}</option>';
					</#if>
				</#list>
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="fromPlaceId" maxlength="10" /></td>';
				row += '<td><input type="text" name="fromPlaceName" maxlength="50" /></td>';
				row += '<td>';
				row += '<select class="toPlaceSel">';
				row += '<option value="">请选择</option>';
				row += '<option value="DEPART_AREA_2">欧洲</option>';
				row += '<option value="DEPART_AREA_4">美洲</option>';
				row += '<option value="DEPART_AREA_6">大洋洲</option>';
				row += '<option value="DEPART_AREA_8">中东非洲</option>';
				row += '<option value="DEPART_AREA_7">东南亚</option>';
				row += '<option value="DEPART_AREA_1">南亚</option>';
				row += '<option value="DEPART_AREA_5">日韩</option>';
				row += '<option value="DEPART_AREA_9">港澳</option>';
				row += '<option value="SHIP">邮轮</option>';
				row += '<option value="ISLAND">海岛</option>';
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="toPlaceId" maxlength="50" /></td>';
				row += '<td><input type="text" name="toPlaceName" maxlength="50" /></td>';
				row += '<td><input type="text" name="displayedToPlaceName" maxlength="50" /></td>';
				row += '<td><input type="text" name="ipLocationId" readonly="readonly" /></td>';
				row += '<td><input type="text" name="destId" maxlength="50" value="3548" /></td>';
				row += '<td><input type="text" name="toPlaceSeq" maxlength="3" /></td>';
				row += '<td>';
				row += '<select class="isToPlaceHiddenSel">';
				row += '<option value="">请选择</option>';
				row += '<option value="Y">是</option>';
				row += '<option value="">否</option>';
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="isToPlaceHidden" readonly="readonly" /></td>';
				row += '<td>';
				row += '<select class="isShownInMoreSel">';
				row += '<option value="">请选择</option>';
				row += '<option value="Y">是</option>';
				row += '<option value="">否</option>';
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="isShownInMore" readonly="readonly" /></td>';
				row += '<td><input type="text" name="zoneName" maxlength="50" /></td>';
				row += '<td><input type="text" name="zoneSeq" maxlength="3" /></td>';
				row += '<td><input type="text" name="referredFromPlaceId" maxlength="50" /></td>';
				row += '<td><input type="hidden" name="isChanged" /></td>';
				row += '</tr>';
				$(this).closest('tr').before(row);
			});
			
			$(".remove").live('click',function(){
				$(this).closest('tr').remove();
			});
			
			$("#form1 :text[name='toPlaceName']").live('keyup',function(){
				$(this).parent().parent().find(":text[name='displayedToPlaceName']").val($(this).val());
				setIpLocationId($(this));
				setToPlaceId($(this));
			});
			
			$(".containerSel").live('change', function(){
				$(this).closest('tr').find(":text[name='containerCode']").val($(this).val());
				$(this).closest('tr').find(":text[name='containerName']").val(selectionFilter($(this).find("option:selected").text()));
				setIpLocationId($(this).closest('tr').find(":text[name='toPlaceName']"));
			});
			
			$(".fromPlaceSel").live('change', function(){
				$(this).closest('tr').find(":text[name='fromPlaceId']").val($(this).val());
				$(this).closest('tr').find(":text[name='fromPlaceName']").val(selectionFilter($(this).find("option:selected").text()));
			});
			
			$(".toPlaceSel").live('change', function(){
				$(this).closest('tr').find(":text[name='toPlaceId']").val($(this).val());
				$(this).closest('tr').find(":text[name='toPlaceName']").val(selectionFilter($(this).find("option:selected").text()));
			});
			
			$(".isToPlaceHiddenSel").live('change', function(){
				$(this).closest('tr').find(":text[name='isToPlaceHidden']").val($(this).val());
				if ($(this).val()=='Y') {
					$(this).closest('tr').find(":text[name='toPlaceSeq']").val("");
				}
			});
			
			$(".isShownInMoreSel").live('change', function(){
				$(this).closest('tr').find(":text[name='isShownInMore']").val($(this).val());
			});
		});
		
		function selectionFilter(txt) {
			if (txt == "请选择") {
				return "";
			}
			return txt;
		}
		
		function setIpLocationId(obj) {
			var obj1 = obj.parent().parent().find(":text[name='containerCode']");
			var obj2 = obj.parent().parent().find(":text[name='ipLocationId']");
			if (obj1.val()=='DZMP_RECOMMEND') {
				$.ajax({
					type: 'POST',
					url: '/pet_back/tableEditor/ajaxGetIpLocationId.do',
					data: {placeName:obj.val()},
					dataType: 'text',
					success: function(data) {
						obj2.val(data);
					}
				});
			} else {
				obj2.val("");
			}
		}
		
		function setToPlaceId(obj) {
			$.ajax({
				type: 'POST',
				url: '/pet_back/tableEditor/ajaxGetToPlaceId.do',
				data: {placeName:obj.val()},
				dataType: 'text',
				success: function(data) {
					obj.parent().parent().find(":text[name='toPlaceId']").val(data);
				}
			});
		}
		
		function isNumber(val) {
			if (/\d+/.test(val)) {
				return true;
			}
			return false;
		}
		
		function check() {
			if ($.trim($("#form1 :text[name='containerCode']").val())=="") {
				alert("容器代码不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='containerName']").val())=="") {
				alert("容器名称不能为空.");
				return false;
			}
			
			var fromPlaceId = $("#form1 :text[name='fromPlaceId']").val();
			if (fromPlaceId!="" && !isNumber(fromPlaceId)) {
				alert("出发地ID必须填数字.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='toPlaceId']").val())=="") {
				alert("目的地ID不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='toPlaceName']").val())=="") {
				alert("目的地名称不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='displayedToPlaceName']").val())=="") {
				alert("显示名称不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='destId']").val())=="") {
                alert("上级目的地ID不能为空.");
                return false;
            }
			
			var toPlaceSeq = $("#form1 :text[name='toPlaceSeq']").val();
			if (toPlaceSeq!="" && !isNumber(toPlaceSeq)) {
				alert("目的地排序必须填数字.");
				return false;
			}
			
			var zoneSeq = $("#form1 :text[name='zoneSeq']").val();
			if (zoneSeq!="" && !isNumber(zoneSeq)) {
				alert("区域排序必须填数字.");
				return false;
			}
			
			return true;
		}
		
		$("#queryMore").bind("click", function(){
		  $("#form2").attr("action", "/pet_back/tableEditorQueryMore.do");
		  $("#form2").submit();
		});
		</script>
	</body>
</html>