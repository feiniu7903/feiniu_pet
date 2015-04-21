<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<#setting classic_compatible=true>
<#setting number_format="#.##">
<html>
	<head>
		<title>AdminTableEditor</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<a href="/super_back/adminTableEditorFromPlace.do">Switch to Fromplace Editor</a>
		<form id="form2" method="post" action="/super_back/adminTableEditorQuery.do">
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
		
		<form id="form1" method="post" action="/super_back/adminTableEditor.do">
			<input type="hidden" name="selContainerCode" value="${selContainerCode}" />
			<input type="hidden" name="selFromPlaceId" value="${selFromPlaceId}" />
			<input type="hidden" name="selToPlaceId" value="${selToPlaceId}" />
			<input type="hidden" name="delId" id="delId" />
			
			<input type="submit" value="Save" />
			<table id="table1">
				<tr>
					<td>Add/Remove</td>
					<td></td>
					<td>ID</td>
					<td>CONTAINER_NAME</td>
					<td>CONTAINER_CODE</td>
					<td>FROM_PLACE_ID</td>
					<td>TO_PLACE_ID</td>
					<td>FROM_PLACE_NAME</td>
					<td>TO_PLACE_NAME</td>
					<td>DEST_ID</td>
					<td>TO_PLACE_SEQ</td>
					<td>IP_LOCATION_ID</td>
					<td>IS_TO_PLACE_HIDDEN</td>
					<td>IS_SHOWN_IN_MORE</td>
					<td>ZONE_NAME</td>
					<td>ZONE_SEQ</td>
					<td>DISPLAYED_TO_PLACE_NAME</td>
					<td>REFERRED_FROM_PLACE_ID</td>
				</tr>
				
				<#list containers as container>
				<tr>
					<td><input type="button" class="add" value="+" /><input type="button" value="-" onclick="del(${container.id});" /></td>
					<td>${container_index+1}</td>
					<td><input type="text" name="id" value="${container.id}" readonly="readonly" /></td>
					<td><input type="text" name="containerName" value="${container.containerName}" /></td>
					<td><input type="text" name="containerCode" value="${container.containerCode}" /></td>
					<td><input type="text" name="fromPlaceId" value="${container.fromPlaceId}" /></td>
					<td><input type="text" name="toPlaceId" value="${container.toPlaceId}" /></td>
					<td><input type="text" name="fromPlaceName" value="${container.fromPlaceName}" /></td>
					<td><input type="text" name="toPlaceName" value="${container.toPlaceName}" /></td>
					<td><input type="text" name="destId" value="${container.destId}" /></td>
					<td><input type="text" name="toPlaceSeq" value="${container.toPlaceSeq}" /></td>
					<td><input type="text" name="ipLocationId" value="${container.ipLocationId}" /></td>
					<td><input type="text" name="isToPlaceHidden" value="${container.isToPlaceHidden}" /></td>
					<td><input type="text" name="isShownInMore" value="${container.isShownInMore}" /></td>
					<td><input type="text" name="zoneName" value="${container.zoneName}" /></td>
					<td><input type="text" name="zoneSeq" value="${container.zoneSeq}" /></td>
					<td><input type="text" name="displayedToPlaceName" value="${container.displayedToPlaceName}" /></td>
					<td><input type="text" name="referredFromPlaceId" value="${container.referredFromPlaceId}" /></td>
					<td><input type="hidden" name="isChanged" /></td>
				</tr>
				</#list>
			</table>
			<input type="submit" value="Save" />
		</form>
		<script src="/super_back/js/base/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script>
		$(document).ready(function(){
			$("#form1 :text").live('change',function(){
				$(this).parent().parent().find(":hidden[name='isChanged']").val(1);
			});
			
			$(".add").live('click', function(){
				var row = '<tr>';
				row += '<td><input type="button" class="add" value="+" /><input type="button" class="remove" value="-" /></td>';
				row += '<td></td>';
				row += '<td><input type="text" name="id" value="" readonly="readonly" /></td>';
				row += '<td><input type="text" name="containerName" value="" /></td>';
				row += '<td><input type="text" name="containerCode" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceId" value="" /></td>';
				row += '<td><input type="text" name="toPlaceId" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceName" value="" /></td>';
				row += '<td><input type="text" name="toPlaceName" value="" /></td>';
				row += '<td><input type="text" name="destId" value="" /></td>';
				row += '<td><input type="text" name="toPlaceSeq" value="" /></td>';
				row += '<td><input type="text" name="ipLocationId" value="" /></td>';
				row += '<td><input type="text" name="isToPlaceHidden" value="" /></td>';
				row += '<td><input type="text" name="isShownInMore" value="" /></td>';
				row += '<td><input type="text" name="zoneName" value="" /></td>';
				row += '<td><input type="text" name="zoneSeq" value="" /></td>';
				row += '<td><input type="text" name="displayedToPlaceName" value="" /></td>';
				row += '<td><input type="text" name="referredFromPlaceId" value="" /></td>';
				row += '<td><input type="hidden" name="isChanged" /></td>';
				row += '</tr>';
				$(this).closest('tr').before(row);
			});
			
			$(".remove").live('click',function(){
				$(this).closest('tr').remove();
			});
		});
		
		function del(id) {
			if (confirm("Determine delete?")) {
				$("#form1").attr("action", "/super_back/adminTableEditor/del.do");
				$("#delId").val(id);
				$("#form1").submit();
			}
		}
		
		$("#queryMore").bind("click", function(){
          $("#form2").attr("action", "/super_back/adminTableEditorQueryMore.do");
          $("#form2").submit();
        });
		</script>
	</body>
</html>