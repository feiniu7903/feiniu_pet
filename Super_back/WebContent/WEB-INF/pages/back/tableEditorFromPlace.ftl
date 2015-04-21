<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<#setting classic_compatible=true>
<#setting number_format="#">
<html>
	<head>
		<title>TableEditorFromPlace</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<a href="/super_back/tableEditor.do">Switch to Toplace Editor</a>
		<form id="form1" method="post" action="/super_back/tableEditorFromPlace.do" onsubmit="return check();">
			<input type="submit" value="Save" />
			<table id="table1">
				<tr>
					<td>Add/Remove</td>
					<td>ID</td>
					<td>*出发地区域码</td>
					<td>出发地排序</td>
					<td>*PC后台推荐块ID</td>
					<td>*PC后台搜索块ID</td>
					<td></td>
					<td>是否隐藏</td>
					<td></td>
					<td>*容器代码</td>
					<td>出发地名称</td>
					<td>出发地ID</td>
				</tr>
				
				<#list fromPlaces as fromPlace>
				<tr>
					<td><input type="button" class="add" value="+" /></td>
					<td><input type="text" name="id" value="${fromPlace.id}" readonly="readonly" /></td>
					<td><input type="text" name="fromPlaceCode" value="${fromPlace.fromPlaceCode}" readonly="readonly" /></td>
					<td><input type="text" name="fromPlaceSeq" value="${fromPlace.fromPlaceSeq}" /></td>
					<td><input type="text" name="blockId" value="${fromPlace.blockId}" readonly="readonly" /></td>
					<td><input type="text" name="searchBlockId" value="${fromPlace.searchBlockId}" readonly="readonly" /></td>
					<td>
						<select class="isFromPlaceHiddenSel">
							<option value="">请选择</option>
							<option value="Y">是</option>
							<option value="">否</option>
						</select>
					</td>
					<td><input type="text" name="isFromPlaceHidden" value="${fromPlace.isFromPlaceHidden}" readonly="readonly" /></td>
					<td>
						<select class="containerCodeSel" disabled="disabled">
							<option value="">请选择</option>
							<option value="DZMP_RECOMMEND">打折门票频道-打折门票推荐</option>
							<option value="ZYZZ_RECOMMEND">自游自在频道-自游自在推荐</option>
							<option value="ZBY_RECOMMEND">周边游频道-周边游推荐</option>
							<option value="GNY_RECOMMEND">国内游频道-国内游推荐</option>
							<option value="CJY_RECOMMEND">出境游频道-出境游推荐</option>
						</select>
					</td>
					<td><input type="text" name="containerCode" value="${fromPlace.containerCode}" readonly="readonly" /></td>
					<td><input type="text" name="fromPlaceName" value="${fromPlace.fromPlaceName}" readonly="readonly" /></td>
					<td><input type="text" name="fromPlaceId" value="${fromPlace.fromPlaceId}" readonly="readonly" /></td>
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
				row += '<td><input type="text" name="id" value="" readonly="readonly" /></td>';
				row += '<td><input type="text" name="fromPlaceCode" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceSeq" value="" /></td>';
				row += '<td><input type="text" name="blockId" value="" /></td>';
				row += '<td><input type="text" name="searchBlockId" value="" /></td>';
				row += '<td>';
				row += '<select class="isFromPlaceHiddenSel">';
				row += '<option value="">请选择</option>';
				row += '<option value="Y">是</option>';
				row += '<option value="">否</option>';
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="isFromPlaceHidden" value="" readonly="readonly" /></td>';
				row += '<td>';
				row += '<select class="containerCodeSel">';
				row += '<option value="">请选择</option>';
				row += '<option value="DZMP_RECOMMEND">打折门票频道-打折门票推荐</option>';
				row += '<option value="ZYZZ_RECOMMEND">自游自在频道-自游自在推荐</option>';
				row += '<option value="ZBY_RECOMMEND">周边游频道-周边游推荐</option>';
				row += '<option value="GNY_RECOMMEND">国内游频道-国内游推荐</option>';
				row += '<option value="CJY_RECOMMEND">出境游频道-出境游推荐</option>';
				row += '</select>';
				row += '</td>';
				row += '<td><input type="text" name="containerCode" value="" readonly="readonly" /></td>';
				row += '<td><input type="text" name="fromPlaceName" value="" /></td>';
				row += '<td><input type="text" name="fromPlaceId" value="" /></td>';
				row += '<td><input type="hidden" name="isChanged" /></td>';
				row += '</tr>';
				$(this).closest('tr').before(row);
			});
			
			$(".remove").live('click',function(){
				$(this).closest('tr').remove();
			});
			
			$(".isFromPlaceHiddenSel").live('change', function(){
				$(this).closest('tr').find(":text[name='isFromPlaceHidden']").val($(this).val());
				$(this).closest('tr').find(":hidden[name='isChanged']").val(1);
			});
			
			$(".containerCodeSel").live('change', function(){
				$(this).closest('tr').find(":text[name='containerCode']").val($(this).val());
				$(this).closest('tr').find(":hidden[name='isChanged']").val(1);
			});
		});
		
		function check() {
			if ($.trim($("#form1 :text[name='fromPlaceCode']").val())=="") {
				alert("出发地区域码不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='blockId']").val())=="") {
				alert("PC后台推荐块ID不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='searchBlockId']").val())=="") {
				alert("PC后台搜索块ID不能为空.");
				return false;
			}
			
			if ($.trim($("#form1 :text[name='containerCode']").val())=="") {
				alert("容器代码不能为空.");
				return false;
			}
			
			return true;
		}
		</script>
	</body>
</html>