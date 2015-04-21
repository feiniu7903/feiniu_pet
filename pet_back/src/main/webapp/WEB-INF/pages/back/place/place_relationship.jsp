<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	</head>
	<body>
		<input type="hidden" id="placeId" name="placePlaceDest.placeId" value="${placePlaceDest.placeId}" />
		<table class="p_table">
			<tr>
				<td width="25%" class="p_label" width="20%">目的地名称：</td>
				<td><input type="text" id="placeRelationName" style="width:300px;height:20px;" value=""/></td>
				<td width="5%"><input type="button" class="btn btn-small w3" value="查询" id="loadPlaceBtn" /></td>
			</tr>
		</table>
		<br/>
		<table class="p_table">
			<tr>
				<td width="40%">
					<select id="selectL" name="selectL"  style="width:100%;height:150px;" multiple="multiple"></select>
				</td>
				<td width="20%">
				    <br/><br/>
					<input type="button" class="btn btn-small w3" id="addBtn" name="addBtn" value="增加&nbsp;&gt;&gt;" />
					<br/><br/>
					<input type="button" class="btn btn-small w3" id="removeBtn" name="removeBtn" value="删除" />
					<br/><br/>
					<input type="button" class="btn btn-small w3" id="supDestBtn" name="supDestBtn" value="设为上级目的地" /><br /><br /><br />
				</td>
				<td width="40%">
					<select id="selectR" name="selectR"  style="width:100%;height:150px;" multiple="multiple">
						<s:iterator value="parentPlaceList">
							<option value="<s:property value="placePlaceDestId" />" title='<s:property value="parentPlaceId" />'><s:property value="parentPlaceName" /><s:if test='isMaster=="Y"'>--上级目的地</s:if></option>
						</s:iterator>
					</select>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
		$(function() {
			var leftSel = $("#selectL");
			var rightSel = $("#selectR");
			
			$("#placeName").keydown(function(e) {
				if (e.keyCode == 13) {
					$("#loadPlaceBtn").click();
				}
			});			
			
			/*将查询结果加载到左侧列表框*/
			$("#loadPlaceBtn").click(function(){
				$("#placeRelationName").val($.trim($("#placeRelationName").val()));
				
				if ($("#placeRelationName").val() == '') {
					alert('请先输入目的地名称');
					$("#placeRelationName").focus();
					return;
				}
					
				$.ajax({
					type:"POST",
					url:"${basePath}/place/ajaxPlaceList.do",
					data:{
						"placeName":$("#placeRelationName").val()
					},
					dataType:"json",
					success:function (places) {
						$("#selectL").empty();
						for (var i = 0; i < places.list.length; i++) {
							var optionObj = $("<option value='" + places.list[i].id + "'>" + places.list[i].name + "</option>");
							$("#selectL").append(optionObj);
						}
					}
				});				
			});
			
			/*点击“增加”按钮后，把左侧列表框选中的项存入数据库，并显示在右侧列表框*/
			$("#addBtn").click(function(){
				if(leftSel.find("option:selected").length==0){
					alert("请选择增加项!");
					return ;
				}else{
				$("#addBtn").attr("disabled","true");
				leftSel.find("option:selected").each(function() {
					var destName = $(this).text();
					$.ajax({
						type:"POST",
						url:"${basePath}/place/savePlaceRelationShip.do",
						data:{"placePlaceDest.placeId":$("#placeId").val(),"placePlaceDest.parentPlaceId":$(this).val()},
						dataType:"json",
						success:function (data) {
							loadPlacePlaceList(data, destName);
						}
					});
				});				
			 }
			});
			
			/*点击“删除”按钮后，从右侧列表框中清除选中项*/
			$("#removeBtn").click(function() {
				$("#removeBtn").attr("disabled","true");
				rightSel.find("option:selected").each(function() {
					var destName = $(this).text();
					if (destName.indexOf("--上级目的地") > 0) {
						alert("删除失败。“"+ destName + "”为当前目的地的上级。");
						$("#removeBtn").removeAttr("disabled");
						return;
					}
					if (confirm("您确定要删除“" + destName + "”吗？")) {
						$.ajax({
							type:"POST",
							url:"${basePath}/place/deletePlacePlaceDest.do",
							data:{"placePlaceDest.placeId":$("#placeId").val(),"placePlaceDest.placePlaceDestId":$(this).val()},
							dataType:"json",
							success:function (data) {
								loadPlacePlaceList(data, destName);
							}
						});
					} else {
						$("#removeBtn").removeAttr("disabled");
					}
				});
			});
			
			
			/*设置当前目的地的直接上级目的地*/
			$("#supDestBtn").bind("click", function(){
				$("#selectR").find("option:selected").each(function() {
					var destName = $(this).text();
					if (destName.indexOf("--上级目的地") < 0 && confirm("是否将“" + destName + "”设置为当前目的地的上级目的地？")) {
						$.ajax({
							type:"POST",
							url:"${basePath}/place/updateIsMaster.do",
							data:{"placePlaceDest.placeId":$("#placeId").val(),"placePlaceDest.placePlaceDestId":$(this).val(),"placePlaceDest.parentPlaceId":$(this).attr('title')},
							dataType:"json",
							success:function (data) {
								loadPlacePlaceList(data, destName);
							},
							error:function(){ 
								alert("出现错误"); 
							} 
						});
					}
				});
			});
		});
		
		function loadPlacePlaceList(data, destName) {
			$("#selectR").empty();
			if (data.message == "destExist") {
				alert("增加“" + destName + "”失败。不能重复添加。");
			} else if (data.message == "destExistInLevelTree") {
				alert("增加“" + destName + "”失败。已经存在于层级树中。");
			} else if (data.message == "addSelfIsNotAllowed") {
				alert("增加“" + destName + "”失败。不可以添加自身。");
			}else if(data.message =="ok"){
				alert("操作成功！");
			}
			for (var i = 0; i < data.placePlaceDests.length; i++) {
				var obj = "<option value='" + data.placePlaceDests[i].id + "' title='"+data.placePlaceDests[i].parentPlaceId+"'>" + data.placePlaceDests[i].name;
				if (data.placePlaceDests[i].isMaster=="Y") {
					obj = obj +"--上级目的地";
				}
				obj = obj + "</option>";
				
				$("#selectR").append(obj);
			}
			$("#addBtn").removeAttr("disabled");
			$("#removeBtn").removeAttr("disabled");
			$("#addBtn").removeAttr("disabled");
		}		
	</script>
	
</html>
