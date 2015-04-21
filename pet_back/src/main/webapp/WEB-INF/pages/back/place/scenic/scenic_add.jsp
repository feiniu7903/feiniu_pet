<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<title>景区基本信息</title>
</head>
<body>
<div id="win_hk" style="display: none"></div>
<div id="win_place" style="display: none"></div>	
	<form action="${basePath}/place/doScenicUpdate.do" id="scenicInfoForm" method="post" class="mySensitiveForm">
	<s:hidden name="place.placeId" id="placeId"></s:hidden>
	<input id="oldPlaceName" name="oldPlaceName" value="<s:property value='place.name' />"  type="hidden"/>
	<input name="place.stage" type="hidden" value="2"/>
	<s:hidden name="stage" value="2"></s:hidden>
	<s:hidden name="placeId"></s:hidden>
	<table class="p_table">
		<tr>
 			<td class="p_label" width="15%"><span class="red">*</span>层级关系：</td>
 			<td colspan="3">
 				<input type="hidden" name="parent_place_id" value="${place.parentPlaceId}" id="parent_place_id"/><span id="placeSuperior"><s:property value="placeSuperior"/></span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-small w5" onclick="addPlacePlaceDest('<s:property value="place.placeId"/>');" value="维护层级关系"/>
 			</td>
		</tr>
		<tr>
  			<td class="p_label"><span class="red">*</span>名称：</td>
  			<td><s:textfield disabled="true" name="place.name" id="placeName"   onblur="placeNameCheck('1')" theme="simple" maxLength="200" cssClass="sensitiveVad" /></td>
  			<td class="p_label"><span class="red">*</span>拼音：</td>
  			<td>
  				<input type="text" value='<s:iterator value="pinyinList" end="1"><s:property value="pinYin"/></s:iterator>' id="place_pinYin" name="place.pinYin" disabled="true" theme="simple" maxLength="100"/>　
  				<input type="button" class="btn btn-small w5" onclick="javascript:addPlaceNamePinyin('PLACE_NAME');" value="修改"/>
  			</td>
		</tr>
	    <tr>
	        <td class="p_label"><span class="red">*</span>URL：</td>
	        <td><s:textfield name="place.pinYinUrl" id="place_pinYinUrl" theme="simple" disabled="true" maxLength="200"/> </td>
	        <td class="p_label"><span class="red">*</span>状态：</td>
	        <td><s:select list="isValidList"  name="place.isValid" id="edit_isValid" headerKey="" headerValue="--请选择--" theme="simple" listKey="elementCode" listValue="elementValue"></s:select></td>
	    </tr>  
		<tr>
 			<td class="p_label">省份：</td>
 			<td><s:textfield name="place.province" theme="simple" maxLength="200"/></td>
 			<td class="p_label">城市：</td>
 			<td><s:textfield name="place.city" theme="simple" maxLength="200"/></td>
		</tr>
		<tr>
 			<td class="p_label"><span class="red">*</span>优化别名：</td>
 			<td><s:textfield name="place.seoName" id="place_seoName" theme="simple" maxLength="200" cssClass="sensitiveVad"/></td>
 			<td class="p_label"><span class="red">*</span>境内境外：</td>
 			<td>
 			<label class="radio"><input type="radio" name="place.placeType" value="ABROAD" <s:if test="place.placeType!=null && place.placeType=='ABROAD'">checked="checked"</s:if> />境外</label>
 			
			<label class="radio"><input type="radio" name="place.placeType" value="DOMESTIC" <s:if test="place.placeType==null || place.placeType=='DOMESTIC'">checked="checked"</s:if> />国内</label>
			</td>
		</tr>
		<tr>
 			<td class="p_label"><span class="red">*</span>地址：</td>
 			<td><s:textfield name="place.address" id="place_address" theme="simple" maxLength="250" cssClass="sensitiveVad"/></td>
 			<td class="p_label"><span class="red">*</span>排序：</td>
 			<td><s:textfield name="place.seq" theme="simple" id="seq"/></td>
		</tr>
		<tr>
 			<td class="p_label"><span class="red">*</span>开放时间：</td>
 			<td><s:textfield name="place.scenicOpenTime" id="place_scenicOpenTime" theme="simple" maxLength="100"/></td>
 			<td class="p_label"><span class="red">*</span>推荐游玩时长：</td>
 			<td><s:textfield name="place.scenicRecommendTime" id="place_scenicRecommendTime" theme="simple" maxLength="50"/></td>
		</tr>
		<tr>
 			<td class="p_label"><span class="red">*</span>主主题：</td>
 			<td>
 			<input type="hidden" name="place.firstTopicOld" value="${place.firstTopic}" id="place.firstTopicOld"/>
 			<s:select list="subjectList" id="place_firstTopic"  name="place.firstTopic" headerKey="" headerValue="--请选择--" theme="simple" listKey="subjectName" listValue="subjectName"></s:select></td>
 			<td class="p_label">次主题：</td>
 			<td>
 			<input type="hidden" name="place.scenicSecondTopicOld" value="${place.scenicSecondTopic}" id="place.scenicSecondTopicOld"/>
 			<s:select list="subjectList" id="place_scenicSecondTopic" name="place.scenicSecondTopic" headerKey="" headerValue="--请选择--" theme="simple" listKey="subjectName" listValue="subjectName"></s:select></td>
		</tr>
       <tr>
        <td class="p_label">高频关键字：</td>
        <td colspan="3">
        				<s:textarea name="place.hfkw" id="place_hfkw" disabled="true" maxLength="500" cssStyle="width:85%" rows="3" cssClass="sensitiveVad" />
        				<input type="button" class="btn btn-small w5" onclick="addPlacePinyin('PLACE_HFKW');" value="修改"/>
      	</td>
      </tr>
      <tr>
        <td class="p_label"><strong class="jianjie"><span class="red">*</span>简介：</strong></td>
        <td colspan="3">
        	<s:textarea name="place.remarkes" onKeyUp="textCounter('remarkes', 500,'limitTips')" onkeypress="textCounter('remarkes', 500,'limitTips')"  id="remarkes"  theme="simple" cssStyle="width:90%" rows="8" cssClass="sensitiveVad"></s:textarea>
        	<div id="limitTips"></div>
        </td>
      </tr>
    </table>
   <p class="tc mt10"><input type="button" id="btn_ok" class="btn btn-small w3" onclick="return checkFormScenic();" value="提交" />
   </p>	
    </form>
</body>
	<script type="text/javascript">
	$(function(){
		if($('#place_seoName').val()==null || $('#place_seoName').val()==''){
			$("#place_seoName").val('${place.name}');
		}
	});
	
	function checkFormScenic(){
		var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");

		if($("#scenicInfoForm #edit_isValid").val()==""){
			alert("状态不能为空");
			$("#scenicInfoForm #edit_isValid").focus();
			return false;
		}
		if($("#scenicInfoForm #place_placeType").val()==""){
			alert("目的地类型不能为空");
			$("#scenicInfoForm #place_placeType").focus();
			return false;
		}
		if($("#scenicInfoForm #seq").val()==""){
			alert("排序不能为空");
			$("#scenicInfoForm #seq").focus();
			return false;
		}
		if($("#scenicInfoForm #remarkes").val()==""){
			alert("简介不能为空");
			$("#scenicInfoForm #remarkes").focus();
			return false;
		}
		if(!reg.test($("#scenicInfoForm #seq").val())){
			alert("排序值请输入数字!");
			$("#scenicInfoForm #seq").val();
			$("#scenicInfoForm #seq").focus();
			return false;
		}
		if($("#scenicInfoForm #place_address").val()==""){
			alert("地址不能为空!");
			$("#scenicInfoForm #place_address").focus();
			return false;
		}
		if($("#scenicInfoForm #place_scenicOpenTime").val()==""){
			alert("开放时间不能为空!");
			$("#scenicInfoForm #place_scenicOpenTime").focus();
			return false;
		}
		if($("#scenicInfoForm #place_scenicRecommendTime").val()==""){
			alert("推荐游玩时长不能为空!");
			$("#scenicInfoForm #place_scenicRecommendTime").focus();
			return false;
		}
		if($("#scenicInfoForm #place_firstTopic").val()==""){
			alert("主主题不能为空!");
			$("#scenicInfoForm #place_firstTopic").focus();
			return false;
		}
		if($("#scenicInfoForm #place_seoName").val()==""){
			alert("优化别名不能为空！");
			$("#scenicInfoForm #place_seoName").focus();
			return false;
		}
		var sensitiveValidator=new SensitiveWordValidator($("#scenicInfoForm"), true);
		if(!sensitiveValidator.validate()){
			return;
		}
		var options = { 
			url:"${basePath}/place/doScenicUpdate.do",
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
		$('#scenicInfoForm').ajaxSubmit(options);
	}
	

	function addPlacePlaceDest(placeId){
		if($('#placeId').val()==null || $('#placeId').val()=='') {
			alert('亲，为了保证业务的严整性，请先保存基本信息！');
			return;
		}
		$("#win_place").load("${basePath}/place/parentPlaceList.do?placePlaceDest.placeId="+ placeId + "&math=" + Math.random(), function() {
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

		$("#win_hk").load("${basePath}/place/toAddPlacePinyin.do?placeSearchPinyin.objectId="+ $('#placeId').val() +"&placeSearchPinyin.objectType=" + type + "&math=" + Math.random(), function() {
			$(this).dialog({
	        	modal:true,
	            title:"设置高频关键词/拼音",
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
				title: '相关名称拼音的相关修改管理',
	            width:400,
	            height:350
	        });
	     });			
	};		
	
	$(function(){
		$("#place.placeId").val('${place.placeId}');
		
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
</script>	
</html>