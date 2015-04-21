<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>目的地添加</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
		<script src="${basePath}/js/base/jquery.form.js"></script>
		<script src="${basePath}/js/place/place.js"></script>
	</head>
	<body>
		<div id="win_hk" style="display: none"></div>
		<div id="win_place" style="display: none"></div>	
		<form id="form2" method="post">
			<s:hidden name="place.placeId" id="placeId"></s:hidden>
			<input type="hidden" id="oldPlaceName" name="oldPlaceName" value="<s:property value='place.name' />"/>
			<input type="hidden" name="place.stage" value="1"/>
			<table class="p_table">
				<tr>
        			<td class="label" width="15%"><span class="red">*</span>层级关系：</td>
        			<td colspan="3">
        				<input type="hidden" name="parent_place_id" value="${place.parentPlaceId}" id="parent_place_id"/><span id="placeSuperior"><s:property value="placeSuperior"/></span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-small w5" onclick="addPlacePlaceDest('<s:property value="place.placeId"/>');" value="维护层级关系"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="label" width="15%"><span class="red">*</span>名称：</td>
        			<td><s:textfield disabled="true" name="place.name" id="placeName"   onblur="placeNameCheck('1')" theme="simple" maxLength="200"/></td>
        			<td class="label" width="15%"><span class="red">*</span>拼音：</td>
        			<td>
        				<input type="text" value='<s:iterator value="pinyinList" end="1"><s:property value="pinYin"/></s:iterator>' id="place_pinYin" name="place.pinYin" disabled="true" theme="simple" maxLength="100"/>　
        				<input type="button" class="btn btn-small w5" onclick="javascript:addPlaceNamePinyin('PLACE_NAME');" value="修改"/>
        			</td>
      			</tr>
			    <tr>
			        <td class="label" width="15%"><span class="red">*</span>URL：</td>
			        <td><s:textfield name="place.pinYinUrl" id="place_pinYinUrl" theme="simple" disabled="true" maxLength="200"/> </td>
			        <td class="label" width="15%"><span class="red">*</span>状态：</td>
			        <td><s:select list="isValidList"  name="place.isValid" id="edit_isValid" headerKey="" headerValue="--请选择--" theme="simple" listKey="elementCode" listValue="elementValue"></s:select></td>
			    </tr>  
      			<tr>
        			<td class="label" width="15%">省份：</td>
        			<td><s:textfield name="place.province" theme="simple" maxLength="200"/></td>
        			<td class="label" width="15%">城市：</td>
        			<td><s:textfield name="place.city" theme="simple" maxLength="200"/></td>
      			</tr>
      			<tr>
        			<td class="label" width="15%"><span class="red">*</span>优化别名：</td>
        			<td><s:textfield name="place.seoName" id="place_seoName" theme="simple" maxLength="200" /></td>
        			<td class="label" width="15%"><span class="red">*</span>使用模板：</td>
        			<td><s:select list="templateList" id="edit_template"  name="place.template" headerKey="" headerValue="--请选择--" theme="simple" listKey="elementCode" listValue="elementValue"></s:select></td>
      			</tr>
      			<tr>
        			<td class="label" width="15%"><span class="red">*</span>目的地类型：</td>
        			<td><s:select list="placeTypeList" id="edit_placeType"  name="place.placeType" headerKey="" headerValue="--请选择--" theme="simple" listKey="elementCode" listValue="elementValue"></s:select></td>
        			<td class="label" width="15%"><span class="red">*</span>排序：</td>
        			<td><s:textfield name="place.seq"  theme="simple" id="seq" maxLength="200"/></td>
      			</tr>
      			 <tr>
        			<td class="label" width="15%">英文名称：</td>
        			<td><input name="place.enName" value="${place.enName}"  /></td>
        			<td class="label" width="15%">国内 /国外：</td>
        			<td>
        			 	<s:if test="%{placeSuperior.indexOf('中国')>=0}">  
        					<s:textfield theme="simple" id="placeGJ" disabled="true" maxLength="200" value="国内"/>
        					<input type="hidden" name="place.isExit" value="template_zhongguo" />
        				</s:if>
        				<s:else>
        					<s:textfield theme="simple" id="placeGJ" disabled="true" maxLength="200" value="国外"/>
        					<input type="hidden" name="place.isExit" value="template_abroad" />
        				</s:else>
      			</tr>
       			<tr>
        			<td class="label" width="15%">高频关键字：</td>
        			<td colspan="3">
        				<s:textarea name="place.hfkw" id="place_hfkw" disabled="true" maxLength="500" cssStyle="width:85%" rows="3"/>
        				<input type="button" class="btn btn-small w5" onclick="addPlacePinyin('PLACE_HFKW');" value="修改"/>
        			</td>
      			</tr>
      			<tr>
        			<td><span class="red">*</span>简介：</td>
        			<td colspan="3">
        				<s:textarea name="place.remarkes" onKeyUp="textCounter('remarkes', 500,'limitTips')" onkeypress="textCounter('remarkes', 500,'limitTips')"  id="remarkes" cssStyle="width:90%" rows="8"></s:textarea>
        				<div id="limitTips"></div>
        			</td>
      			</tr>      				 			
		   </table>
		   <p class="tc mt10"><input type="button" id="btn_ok" class="btn btn-small w3" onclick="return checkForm();" value="提交" />
		   <input type="button" id="btn_close" class="btn btn-small w3"  value="取消" />
		   </p>			
		</form> 	
	</body>
	<script type="text/javascript">
	$(function(){
		$("#place.placeId").val('${place.placeId}');
		if($('#place_seoName').val()==null || $('#place_seoName').val()==''){
			$("#place_seoName").val('${place.name}');
		}
	});
		$(function(){
			$('#win_place').bind('dialogbeforeclose', function(event, ui) {
				var flag = false;
				$.ajax({
					type:"POST",
					async:false,
					url:"${basePath}/place/loadParentPlaceJson.do",
					data:{
						"placePlaceDest.placeId":$("#placeId").val()
					},
					dataType:"json",
					success:function (data) {
						$("#parent_place_id").val('');
						for (var i = 0; i < data.placePlaceDests.length; i++) {
							if (data.placePlaceDests[i].isMaster=="Y") {
								flag = true;
								$("#parent_place_id").val(data.placePlaceDests[i].parentPlaceId);
								break;
							}
						}
						$("#placeSuperior").html(data.text);
						var isZhongguo = (data.text&&data.text.indexOf("中国")>=0);
						$("#placeGJ").val(isZhongguo?"国内":"国外");
						$("input[name='place.isExit']").val(isZhongguo?"template_zhongguo":"template_abroad");
						if (!flag) {
							alert("请制定上级目的地");
							return false;
						}
						return flag;
					},
					error:function (data) {
						return false;
					}
				});
				return flag;
			});	
			$("#btn_close").click(function(){
				popClose();
			});
		});
		function popClose(){
			$("span.ui-icon-closethick").click();
		}
		function addPlacePlaceDest(placeId){
			if($('#placeId').val()==null || $('#placeId').val()=='') {
				alert('亲，为了保证业务的严整性，请先保存基本信息！');
				return;
			}
			$("#win_place").load("${basePath}/place/parentPlaceList.do?placePlaceDest.placeId="+ placeId , function() {
				$(this).dialog({
		        	modal:true,
		            title:"设置层级关系",
		            width:550,
		            height:300
		        });
		     });
		};
		
		function addPlacePinyin(type) {
			if($('#placeId').val()==null || $('#placeId').val()=='') {
				alert('亲，为了保证业务的严整性，请先保存基本信息！');
				return;
			}
			var title = "设置名称/拼音";
			if ("PLACE_HFKW" == type) {
				title = "设置高频关键词/拼音";
			}
			$("#win_hk").load("${basePath}/place/toAddPlacePinyin.do?placeSearchPinyin.objectId="+ $('#placeId').val() +"&placeSearchPinyin.objectType=" + type , function() {
				$(this).dialog({
		        	modal:true,
		            title:title,
		            width:700,
		            height:600
		        });
		     });
		};		
		
		function addPlaceNamePinyin(type){
			if($('#placeId').val()==null || $('#placeId').val()=='') {
				alert('亲，为了保证业务的严整性，请先保存基本信息！');
				return;
			}
			if($('#parent_place_id').val()==null || $('#parent_place_id').val()=="") {
				alert('亲，为了保证业务的严整性，请先填写层级关系！里面的上级目的地！');
				return;
			}
			$("#win_hk").load("${basePath}/place/toAddPlacePinyin.do?placeSearchPinyin.objectId="+ $('#placeId').val() +"&placeSearchPinyin.objectType=" + type + "&math=" + Math.random(), function() {
				$(this).dialog({
		        	modal:true,
		            title:"设置拼音",
		            width:400,
		            height:350
		        });
		     });			
		};		
	
		function checkForm(){
			var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
			
			if($("#edit_isValid").val()==""){
				alert("状态不能为空");
				$("#edit_isValid").focus();
				return false;
			}
			if($("#place_seoName").val()==""){
				alert("优化别名不能为空！");
				$("#place_seoName").focus();
				return false;
			}
			if($("#edit_template").val()==""){
				alert("模板不能为空");
				$("#edit_template").focus();
				return false;
			}
			if($("#edit_placeType").val()==""){
				alert("目的地类型不能为空");
				$("#edit_placeType").focus();
				return false;
			}
			if($("#seq").val()==""){
				alert("排序不能为空");
				$("#seq").focus();
				return false;
			}
			if($("#remarkes").val()==""){
				alert("简介不能为空");
				$("#remarkes").focus();
				return false;
			}
			if(!reg.test($("#seq").val())){
				alert("排序值请输入数字!");
				$("#seq").val();
				$("#seq").focus();
				return false;
			}
			
			var options = { 
				url:"${basePath}/place/doPlaceUpdate.do",
				dataType:"",
				type : "POST", 
				success:function(data){ 
					if(data== "success") {
						alert("操作成功!"); 
						popClose();
						window.location.reload();
					} else { 
						alert("操作失败，请稍后再试!"); 
					} 
				}, 
				error:function(){ 
					alert("出现错误"); 
				} 
			};
			$('#form2').ajaxSubmit(options);
		};
</script>	
</html>