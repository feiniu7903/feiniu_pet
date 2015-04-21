<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<script type="text/javascript"> 
	//提交之后，重刷新table
	function checkAndSubmitOnAddRecommend(url) {
		if($("#hotelRecommendNameId").val() == ""){
			alert("名称不能为空");
			return false;
		}else if($("#hotelRecommendId").val() == ""){
			alert("介绍不能为空");
			return false;
		}else{
			var sensitiveValidator=new SensitiveWordValidator($("#placeHotelRecommendForm"), true);
			if(!sensitiveValidator.validate()){
				return;
			}
			 $("#submitRecommendButton").attr("disabled","disabled"); 
			 var options = {
					url:url,
					type :'POST',
					dataType:'json',
					success:function(data){
			      		alert(data.message);
						$("#recommend_dialog").dialog("close");
			      		reloadIntroduce();
					},
					error:function(){alert("出现错误");$("#submitRecommendButton").removeAttr("disabled");}
				};
			$('#placeHotelRecommendForm').ajaxSubmit(options);  
		}
	}
</script>
<style type="text/css">
	label,input[type="radio"],input[type="checkbox"]{
		float: left;
		dispaly:inline;
	}
</style>
</head>
<body>
	<form id="placeHotelRecommendForm"  method="post" class="mySensitiveForm">
	<table class="p_table table_center" id="content0" style="width:880px;height:600px;">
      <tr>
          <td  class="p_label" style="width: 120px;"><span style="color:red;">*</span>名称:</td>
	      <td><input type="text" id="hotelRecommendNameId" name="placeHotelRecommend.recommendName" value='<s:property value="placeHotelRecommend.recommendName"/>' class="sensitiveVad" /><span id="hotelRoomNameMessageId"></span></td>
		  <td>排序</td>
		  <td><input type="text" name="placeHotelRecommend.seqNum" value="<s:property value="placeHotelRecommend.seqNum"/>" onkeyup="value=value.replace(/[^\d]/g,'')"/></td>	
      </tr>
	  <tr>
	  	  <td class="p_label" style="width: 120px;"><span style="color:red;">*</span>介绍：</td>
		  <td colspan="3">
		  		<textarea id="hotelRecommendId" name="placeHotelRecommend.recommentContent" class="sensitiveVad"><s:property value="placeHotelRecommend.recommentContent"/></textarea>
		  </td>
	  </tr>
      </table>
 	<p class="tc mt10 mb10">
 		<input type="hidden" name="placeHotelRecommend.placeId" value="<s:property value='placeHotelRecommend.placeId'/>"/>
 		<input type="hidden" name="placeHotelRecommend.recommendId" value="<s:property value='placeHotelRecommend.recommendId'/>"/>
 		<input type="hidden" name="placeHotelRecommend.recommentType" value="<s:property value='placeHotelRecommend.recommentType'/>"/>
		<input class="btn btn-small w5" value="确定" id="submitRecommendButton" onclick="javascript:return checkAndSubmitOnAddRecommend('${basePath}/place/addOrUpdatePlaceHotelRecommend.do');"></input>
	</p>
     </form>
</body>
</html>