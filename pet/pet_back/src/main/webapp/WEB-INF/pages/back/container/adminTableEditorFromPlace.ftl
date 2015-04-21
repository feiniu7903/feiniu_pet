<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<#setting classic_compatible=true>
<#setting number_format="#">
<html>
	<head>
		<title>AdminTableEditorFromPlace</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<a href="/pet_back/adminTableEditor.do">Switch to Toplace Editor</a>
		<form id="form1" method="post" action="/pet_back/adminTableEditorFromPlace.do">
			<input type="submit" value="Save" />
			<table id="table1">
				<tr>
					<td>Add/Remove</td>
					<td>ID</td>
					<td>FROM_PLACE_CODE</td>
					<td>FROM_PLACE_SEQ</td>
					<td>BLOCK_ID</td>
					<td>SEARCH_BLOCK_ID</td>
					<td>IS_FROM_PLACE_HIDDEN</td>
					<td>CONTAINER_CODE</td>
					<td>FROM_PLACE_NAME</td>
					<td>FROM_PLACE_ID</td>
				</tr>
				
				<#list fromPlaces as fromPlace>
				<tr>
					<td><input type="button" class="add" value="+" /><input type="button" value="-" onclick="del(${fromPlace.id});" /></td>
					<td><input type="text" name="id" value="${fromPlace.id}" readonly="readonly" /></td>
					<td><input type="text" name="fromPlaceCode" value="${fromPlace.fromPlaceCode}" /></td>
					<td><input type="text" name="fromPlaceSeq" value="${fromPlace.fromPlaceSeq}" /></td>
					<td><input type="text" name="blockId" value="${fromPlace.blockId}" /></td>
					<td><input type="text" name="searchBlockId" value="${fromPlace.searchBlockId}" /></td>
					<td><input type="text" name="isFromPlaceHidden" value="${fromPlace.isFromPlaceHidden}" /></td>
					<td><input type="text" name="containerCode" value="${fromPlace.containerCode}" /></td>
					<td><input type="text" name="fromPlaceName" value="${fromPlace.fromPlaceName}" /></td>
					<td><input type="text" name="fromPlaceId" value="${fromPlace.fromPlaceId}" /></td>
					<td><input type="hidden" name="isChanged" /></td>
				</tr>
				</#list>
			</table>
			<input type="submit" value="Save" />
		</form>
		<script src="/pet_back/js/base/jquery-1.4.4.min.js" type="text/javascript"></script>
		<script>
		$(document).ready(function(){
			$("#form1 :text").live('change',function(){
				$(this).parent().parent().find(":hidden[name='isChanged']").val(1);
			});
			
			$(".add").live('click', function(){
				var row = '<tr>';
				row += '<td><input type="button" class="add" value="+" /><input type="button" class="remove" value="-" /></td>';
				row += '<td><input type="text" name="id" value="" readonly="readonly" /></td>';
				row += '<td><input type="text" name="fromPlaceCode" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceSeq" value="" /></td>';
				row += '<td><input type="text" name="blockId" value="" /></td>';
				row += '<td><input type="text" name="searchBlockId" value="" /></td>';
				row += '<td><input type="text" name="isFromPlaceHidden" value="" /></td>';
				row += '<td><input type="text" name="containerCode" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceName" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceId" value="" /></td>';
				row += '<td><input type="hidden" name="isChanged" /></td>';
				row += '</tr>';
				$(this).closest('tr').before(row);
			});
			
			$(".remove").live('click',function(){
				$(this).closest('tr').remove();
			});
		});
		
		function del(id) {
			if (confirm("Are you sure?")) {
				window.location.href="/pet_back/adminTableEditorFromPlace/del.do?delId=" + id;
			}
		}
		</script>
	</body>
</html>