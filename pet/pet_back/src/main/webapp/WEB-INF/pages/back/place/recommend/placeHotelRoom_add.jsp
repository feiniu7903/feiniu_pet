<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<script type="text/javascript"> 
	//提交之后，重刷新table
	function checkAndSubmitOnAddRoom(url) {
		if($("#hotelRoomNameId").val() == ""){
			alert("房型名称不能为空");
			return false;
		}else if(!valiateAddBed()){
			return false;
		}else if($("#roomRecommendId").val() == ""){
			alert("房型介绍不能为空");
			return false;
		}else{
			var sensitiveValidator=new SensitiveWordValidator($("#placeHotelRoomForm"), true);
			if(!sensitiveValidator.validate()){
				return;
			}
			$("#submitRoomButton").attr("disabled","disabled"); 
			var options = {
					url:url,
					type :'POST',
					dataType:'json',
					success:function(data){
			      		alert(data.message);
						$("#room_dialog").dialog("close");
			      		reloadIntroduce();
					},
					error:function(){alert("出现错误");$("#submitRoomButton").removeAttr("disabled");}
				};
			$('#placeHotelRoomForm').ajaxSubmit(options);
		}
	}
	function valiateAddBed(){
		var validate = false;
		var validatetwo = false;
		$("input[name='placeHotelRoomBed']").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出 
			if(this.checked){
				validate = true;
				if($("#"+$(this).val()).val() == "0" || $("#"+$(this).val()).val() == ""){
					alert("请设置床宽");
					validatetwo = false;
				}else{
					validatetwo = true;
				}
			}else{
				$("#"+$(this).val()).val("0");
			}
		});
		if(!validate){
			alert("请至少选择一种床宽类型");
		}
		return validate && validatetwo;
	}
</script>
<style type="text/css">
	label,input[type="radio"],input[type="checkbox"]{
		float: left;
		dispaly:inline;
	}
	label{
		margin-right:20px;
	}
</style>
	<div class="p_box" style="width:880px;">
	<form id="placeHotelRoomForm"  method="post" style="width:880px;" class="mySensitiveForm">
	<table class="p_table table_center form-inline" id="content0"  style="width:880px;">
      <tr>
          <td  class="p_label" style="width:120px;"><span style="color:red;">*</span>房型名称:</td>
	      <td style="width:320px; "><input type="text" id="hotelRoomNameId" name="placeHotelRoom.roomName" value='<s:property value="placeHotelRoom.roomName"/>' class="sensitiveVad"/><span id="hotelRoomNameMessageId"></span></td>
	      <td  class="p_label" style="width:120px;">加床:<s:property value="placeHotelRoom.addBed"/></td>
	      <td style="width:320px;">
	      		<input type="radio" name="placeHotelRoom.addBed" value='1' <s:if test="placeHotelRoom.addBed == 1">checked="checked"</s:if>/><label>否</label>
	      		<input type="radio" name="placeHotelRoom.addBed" value='2' <s:if test="placeHotelRoom.addBed == 2">checked="checked"</s:if>/><label>可以加床</label>
	      		<input type="text"  name="placeHotelRoom.addBedCost" style="width:30px;" value='<s:property value="placeHotelRoom.addBedCost"/>' onkeyup="value=value.replace(/[^\d\.]/g,'')"/>元<font color="red">(注:0元代代表免费)</font>
	      </td>
      </tr>
      <tr>
          <td class="p_label" style="width:120px;">房间数量:</td>
	      <td><input type="text" name="placeHotelRoom.roomNum" value='<s:property value="placeHotelRoom.roomNum"/>' onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="例如:10"/></td>
	      <td class="p_label" style="width:120px;">建筑面积:</td>
	      <td><input type="text"  name="placeHotelRoom.buildingArea" value='<s:property value="placeHotelRoom.buildingArea"/>' onkeyup="value=value.replace(/[^\d\.]/g,'')" placeholder="例如:10"/>平方米&nbsp;&nbsp;</td>
	  </tr>	
      <tr>
          <td class="p_label" style="width:120px;">楼层:</td>
	      <td style="width:320px; "><input type="text" name="placeHotelRoom.roomFloor" value='<s:property value="placeHotelRoom.roomFloor"/>'/></td>
	      <td class="p_label" style="width:120px;"><span style="color:red;">*</span>床宽:</td>
	      <td style="width:320px; ">
	      		<input type="checkbox" name="placeHotelRoomBed" <s:if test="placeHotelRoom.bigBedWide != 0 && placeHotelRoom.bigBedWide != null">checked="checked"</s:if> value="bigBedWideId"/>
	      			<label>大床(宽:<input type="text" name="placeHotelRoom.bigBedWide" style="width:30px;" id="bigBedWideId" value='<s:if test="placeHotelRoom.bigBedWide != null"><s:property value="placeHotelRoom.bigBedWide"/></s:if><s:else>0</s:else>' onkeyup="value=value.replace(/[^\d\.]/g,'')"/>米)</label>
	      		<input type="checkbox" name="placeHotelRoomBed" <s:if test="placeHotelRoom.doubleBedWide != 0 && placeHotelRoom.doubleBedWide != null">checked="checked"</s:if> value="doubleBedWideId"/>
	      			<label>双床(宽:<input type="text" name="placeHotelRoom.doubleBedWide" style="width:30px;" id="doubleBedWideId" value='<s:if test="placeHotelRoom.doubleBedWide != null"><s:property value="placeHotelRoom.doubleBedWide"/></s:if><s:else>0</s:else>' onkeyup="value=value.replace(/[^\d\.]/g,'')"/>米)</label>
	      </td>
	  </tr>
	  <tr>
		  <td class="p_label" style="width:120px;">无烟房：</td>
		  <td colspan="3" style="width:760px;">
		  		<input type="radio" id="" name="placeHotelRoom.isNonsmoking" value="1" <s:if test="placeHotelRoom.isNonsmoking == 1">checked="checked"</s:if>/><label>有无烟楼层</label>
		    	<input type="radio" id="" name="placeHotelRoom.isNonsmoking" value="2" <s:if test="placeHotelRoom.isNonsmoking == 2">checked="checked"</s:if>/><label>有无烟房</label>
		    	<input type="radio" id="" name="placeHotelRoom.isNonsmoking" value="3" <s:if test="placeHotelRoom.isNonsmoking == 3">checked="checked"</s:if>/><label>该房可做无烟处理</label>
		    	<input type="radio" id="" name="placeHotelRoom.isNonsmoking" value="4" <s:if test="placeHotelRoom.isNonsmoking == 4">checked="checked"</s:if>/><label>无</label>
		  </td>
	  </tr>
	  <tr>
		  <td class="p_label" style="width:120px;">宽带：</td>
		  <td style="width:320px;">
		  		<input type="radio" id="" name="placeHotelRoom.broadband" value="1" <s:if test="placeHotelRoom.broadband == 1">checked="checked"</s:if>/><label>免费</label>
		    	<input type="radio" id="" name="placeHotelRoom.broadband" value="2" <s:if test="placeHotelRoom.broadband == 2">checked="checked"</s:if>/><label>无</label>
		    	<input type="radio" id="" name="placeHotelRoom.broadband" value="3" <s:if test="placeHotelRoom.broadband == 3">checked="checked"</s:if>/><label>收费</label>
		    	<input type="text" id="" name="placeHotelRoom.broadbandCost" style="width: 30px;" value='<s:property value="placeHotelRoom.broadbandCost"/>' onkeyup="value=value.replace(/[^\d\.]/g,'')"/>元/天
		  </td>
		  <td class="p_label" style="width:120px;">排序值:</td>
	      <td style="width:320px; ">
	      	<input type="text"  name="placeHotelRoom.seqNum" value='<s:property value="placeHotelRoom.seqNum"/>' onkeyup="value=value.replace(/[^\d]/g,'')"/>&nbsp;&nbsp;</td>
	  </tr>
	  <tr>
		  <td class="p_label" style="width:120px;">窗户：</td>
		  <td colspan="3" style="width:760px;">
		  		<input type="radio" id="" name="placeHotelRoom.isWindow" value="1" <s:if test="placeHotelRoom.isWindow == 1">checked="checked"</s:if>/><label>有窗</label>
		    	<input type="radio" id="" name="placeHotelRoom.isWindow" value="2" <s:if test="placeHotelRoom.isWindow == 2">checked="checked"</s:if>/><label>无窗</label>
		    	<input type="radio" id="" name="placeHotelRoom.isWindow" value="3" <s:if test="placeHotelRoom.isWindow == 3">checked="checked"</s:if>/><label>部分无窗</label>
		  </td>
	  </tr>
	   <tr>
		  <td class="p_label" style="width:120px;">最大入住数：</td>
		  <td colspan="3" style="width:760px;">
		  		<input type="text" id="" name="placeHotelRoom.maxLive" value='<s:property value="placeHotelRoom.maxLive"/>' style="width: 30px;" onkeyup="value=value.replace(/[^\d]/g,'')"/>人
		  </td>
	  </tr>
	  <tr>
	  	  <td class="p_label" style="width:120px;"><span style="color:red;">*</span>房型介绍：</td>
		  <td colspan="3">
		  		<textarea id="roomRecommendId" name="placeHotelRoom.roomRecommend" class="sensitiveVad"><s:property value="placeHotelRoom.roomRecommend"/></textarea>
		  </td>
	  </tr>
      </table>
 	<p class="tc mt10 mb10">
 		<input type="hidden" name="placeHotelRoom.placeId" value="<s:property value='placeHotelRoom.placeId'/>"/>
 		<input type="hidden" name="placeHotelRoom.roomId" value="<s:property value='placeHotelRoom.roomId'/>"/>
		<input class="btn btn-small w5" value="确定" id="submitRoomButton"   onclick="javascript:return checkAndSubmitOnAddRoom('${basePath}/place/addOrUpdatePlaceHotelRoom.do');"></input>
	</p>
     </form>
     </div>