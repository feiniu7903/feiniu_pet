<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>地标添加</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
		<script src="${basePath}/js/base/jquery.form.js"></script>
		<script src="${basePath}/js/place/place.js"></script>
	</head>
	<body>
		<div id="win_pinyin_hk" style="display: none"></div>
		
		<form id="form2" method="post">
			<s:hidden name="placeLandMark.placeLandMarkId" id="placeLandMarkId"></s:hidden>
			<table class="p_table">
      			<tr>
        			<td class="p_label" width="25%"><span class="red">*</span>地标名称：</td>
        			<td>
        				<input type="text" value='<s:property value="placeLandMark.landMarkName"/>' id="placeLandMark_landMarkName1" name="placeLandMark.landMarkName" disabled="true" theme="simple" maxLength="200"/>
	        			<input type="button" class="btn btn-small w5" onclick="addPlaceLandMarkNamePinyin('PLACE_LAND_MARK_NAME');" value="新增或修改"/>
        			</td>
      			</tr> 
      			<tr>
        		    <td class="p_label" width="15%"><span class="red">*</span>拼音：</td>
	        		<td>
	        			<input type="text" value='<s:property value="placeLandMark.pinYin"/>' id="placeLandMark_pinYin" name="placeLandMark.pinYin" disabled="true" theme="simple" maxLength="200"/>　
	        		</td>
      			</tr> 
      			<!-- 
      			<tr>
        			<td class="p_label" width="25%"><span class="red">*</span>地标地址：</td>
        			<td colspan="3">
        				<s:textfield name="placeLandMark.landMarkAddress" id="landMarkAddress"  theme="simple" cssStyle="width:98%"  maxLength="500"/>
        			</td>
      			</tr>
      			 -->
       			<tr>
	      		<tr>
	        			<td class="p_label" width="25%">高频关键字：</td>
	        			<td >
	        				<s:textfield name="placeLandMark.hfkw" id="placeLandMark_hfkw" disabled="true" maxLength="500" cssStyle="width:85%" rows="3"/>
	        				<input type="button" class="btn btn-small w5" onclick="addPlaceLandMarkNamePinyin('PLACE_LAND_MARK_HFKW');" value="新增或修改"/>
	        			</td>
	      		</tr>
      			<tr>
        			<td colspan="4">&nbsp;</td>
      			</tr>
      			<tr>
        			<td colspan="4">设置地标的上级目的地</td>
      			</tr>
      			<tr>
        			<td class="p_label" width="25%"><span class="red">*</span>上级目的地ID：</td>
        			<td > 
        				<s:textfield disabled="true" name="placeLandMark.placeId" id="placeId"  theme="simple" maxLength="100"/>
					</td>
      			</tr>
      			<tr>
					<td  class="p_label" width="25%">上级目的地名称：</td>
					<td ><s:textfield name="placeLandMark.parentPlaceName"  id="parentPlaceName" style="width:300px;height:20px;"/>
						<input type="button" class="btn btn-small w3" value="查询" id="loadPlaceBtn" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td >
						<select id="selectL" name="selectL"  style="width:100%;height:150px;" multiple="multiple"></select>
						<input type="button" class="btn btn-small w3" id="supDestBtn" name="supDestBtn" value="选择一个设为上级目的地" /><br />
					</td>
				</tr>
			
		   </table>
		   <p class="tc mt10"><input type="button" id="btn_ok" class="btn btn-small w3" onclick="return checkForm();" value="提交" />
		   </p>	
		</form> 	
	</body>
	<script type="text/javascript">
		$(function() {
			var leftSel = $("#selectL");

			/*设置当前目的地的直接上级目的地*/
			$("#supDestBtn").bind("click", function(){
				$("#selectL").find("option:selected").each(function() {
					$("#placeId").val($(this).val());
				});
			});	
				
			/*将查询结果加载到左侧列表框*/
			$("#loadPlaceBtn").click(function(){
				$("#parentPlaceName").val($.trim($("#parentPlaceName").val()));
				
				if ($("#parentPlaceName").val() == '') {
					alert('请先输入目的地名称');
					$("#parentPlaceName").focus();
					return;
				}
					
				$.ajax({
					type:"POST",
					url:"${basePath}/place/ajaxPlaceList.do",
					data:{
						"placeName":$("#parentPlaceName").val()
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
		});
	</script>
	
	<script type="text/javascript">
		function addPlaceLandMarkNamePinyin(type) {
			var title = "设置名称/拼音";
			if ("PLACE_LAND_MARK_HFKW" == type) {
				title = "设置高频关键词/拼音";
			}
			var data = "placeSearchPinyin.objectId="+ $('#placeLandMarkId').val() +"&placeSearchPinyin.objectType=" + type;
			$("#win_pinyin_hk").load("${basePath}/place/toAddLandMarkPinyinOrHFKW.do?" + data, function() {
				$(this).dialog({
		        	modal:true,
		            title:title,
		            width:450,
		            height:300
		        });
		     });
		};
	
		function popClose(){
			$("span.ui-icon-closethick").click();
		}

		function checkForm(){
			var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
			
			if($("#placeLandMark_pinYin").val()==""){
				alert("拼音不能为空");
				$("#placeLandMark_pinYin").focus();
				return false;
			} 
			if($("#placeLandMark_landMarkName1").val()==""){
				alert("地标名称不能为空");
				$("#placeLandMark_landMarkName1").focus();
				return false;
			}
			if($("#parentPlaceName").val()==""){
				alert("上级目的地不能为空");
				$("#parentPlaceName").focus();
				return false;
			} 
			
			$("#placeId").removeAttr("disabled");
			$("#placeLandMark_hfkw").removeAttr("disabled");
			$("#placeLandMark_pinYin").removeAttr("disabled");
			$("#placeLandMark_landMarkName1").removeAttr("disabled");
			
			var options = {
				url:"${basePath}/place/doPlaceLandMarkSave.do",
				dataType:"",
				type : "POST", 
				success:function(data){
					if(data== "success") {
						alert("操作成功!"); 
						popClose();
						window.location.reload();
					} else { 
						$("#placeId").attr("disabled","true");
						$("#placeLandMark_hfkw").attr("disabled","true");
						$("#placeLandMark_pinYin").attr("disabled","true");
						$("#placeLandMark_landMarkName1").attr("disabled","true");
						alert("操作失败，请稍后再试!"); 
					} 
				}, 
				error:function(){ 
					$("#placeId").attr("disabled","true");
					$("#placeLandMark_hfkw").attr("disabled","true");
					$("#placeLandMark_pinYin").attr("disabled","true");
					$("#placeLandMark_landMarkName1").attr("disabled","true");
					alert("出现错误"); 
				} 
			};
			$('#form2').ajaxSubmit(options);
		};
</script>	
</html>
