<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<style type="text/css">
	label,input[type="radio"],input[type="checkbox"]{
		float: left;
		dispaly:inline;
	}
</style>
<div class="p_box" style="width: 600px;">
	<form id="placeHotelNoticeForm"  method="post" class="mySensitiveForm">
	<table class="p_table table_center"  id="content0" style="width:600px;">
	  <s:if test="placeHotelNotice.noticeType == 'RECOMMEND' || placeHotelNotice.noticeType == 'SCENIC'">
	  	<input type="hidden" name="placeHotelNotice.noticeType" value='<s:property value="placeHotelNotice.noticeType"/>'/>
	  </s:if>
	  <s:else>
      <tr>
          <td  class="p_label" style="width: 120px;"><span style="color:red;">*</span>公告类型:</td>
	      <td style="width: 480px;">
		      	<input type="radio" id="noticeTypeId" name="placeHotelNotice.noticeType" value="ALL"
		      	    <s:if test="placeHotelNotice.noticeType == 'ALL' || placeHotelNotice.noticeType == null">checked="checked"</s:if>/>
		      		<label>全部&nbsp;&nbsp;</label>
					<%-- <input type="radio" id="noticeTypeId" name="placeHotelNotice.noticeType" value="INTERNAL"
				    <s:if test="placeHotelNotice.noticeType == 'INTERNAL'">checked="checked"</s:if>/>
				    <label>对内&nbsp;&nbsp;</label><span id="noticeTypeMessageId"></span> --%>
		  </td>
      </tr>
     </s:else>
      <tr>
        <td  class="p_label" style="width: 120px;"><span style="color:red;">*</span>公告内容：</td>
       	<td style="width: 480px;">
       		<textarea style="width:300px;" rows="6" cols="150" id="noticeContentId" name="placeHotelNotice.noticeContent" class="sensitiveVad"><s:property value='placeHotelNotice.noticeContent'/></textarea><span id="noticeContentMessageId">公告内容限制为100字符</span></td>
	  </tr>	
      <tr>
		 <td class="p_label" style="width: 120px;"><span style="color:red;">*</span>开始时间：</td>
		 <td style="width: 480px;">
		       	<input name="placeHotelNotice.noticeStartTime" id="noticeStartTimeId"
					value="<s:date name='placeHotelNotice.noticeStartTime' format='yyyy-MM-dd HH:mm:ss'/>"
					type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-#{%d}'})" />(不填,则默认当前时间)
		       </td>
	  </tr>
	  <tr>
		  <td class="p_label" style="width: 120px;"><span style="color:red;">*</span>结束时间：</td>
		  <td style="width: 480px;">
		        <input name="placeHotelNotice.noticeEndTime" id="notieceEndTimeId"
					value="<s:date name='placeHotelNotice.noticeEndTime'  format='yyyy-MM-dd HH:mm:ss'/>"
					type="text" class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'noticeStartTimeId\')}'})" />
		        	<span id="notieceEndTimeMessageId"></span>
		        </td>
		      </tr>
      </table>
 	<p class="tc mt10 mb10">
 		<input type="hidden" name="placeHotelNotice.placeId" value="<s:property value='placeHotelNotice.placeId'/>"/>
 		<input type="hidden" name="placeHotelNotice.noticeId" value="<s:property value='placeHotelNotice.noticeId'/>"/>
 		<input type="hidden" name="successUrl" value='<s:property value="successUrl"/>'/>
		<input class="btn btn-small w5" value="确定" id="submitNoticeButton" onclick="javascript:return checkAndSubmitOnAddNotice('${basePath}/place/addOrUpdatePlaceHotelNotice.do');"></input>
	</p>
     </form>
</div>
<script type="text/javascript">
	//提交之后，重刷新table
	function checkAndSubmitOnAddNotice(url) {
		if($("#noticeContentId").val() == ""){
			$("#noticeContentMessageId").html("<font color='red'>公告内容不能为空</font>");
			return false;
		}else if($("#noticeContentId").val().length > 100){
			$("#noticeContentMessageId").html("<font color='red'>公告内容长度超过100</font>");
			return false;
		}else if($("#notieceEndTimeId").val() == ""){
			$("#notieceEndTimeMessageId").html("<font color='red'>结束时间不能为空</font>");
			return false;
		}else if(validateDate()){
			if($("#noticeStartTimeId").val() == ""){
				$("#notieceEndTimeMessageId").html("<font color='red'>结束时间不能小于当前时间</font>");
			}else{
				$("#notieceEndTimeMessageId").html("<font color='red'>结束时间不能小于开始时间</font>");
			}
			return false;
		}else{
			var sensitiveValidator=new SensitiveWordValidator($('#placeHotelNoticeForm'), true);
			if(!sensitiveValidator.validate()){
				return;
			}
			$("#submitNoticeButton").attr("disabled","disabled"); 
			var options = {
					url:url,
					type :'POST',
					dataType:'json',
						success:function(data){
			      		   if(data.success==true) {
				      			 alert(data.message);
				      			 $("#placeHotel_TwoId").dialog("close");
				      			 if(data.noticeType == 'RECOMMEND'){
				      				$.ajaxDialog({url:"${basePath}/place/queryAllIntroduce.do?placeId=${placeHotelNotice.placeId}" + "&math=" + Math.random(),title:'酒店介绍',id:'introduce_dialog'});
				      			 }else{
				      			 	$.ajaxDialog({url:'${basePath}'+data.successUrl+'?placeId=${placeHotelNotice.placeId}&noticeType=${noticeType}',title:'公告',id:'placeHotel_OneId'}); 
				      			 }
			  				} else {
							  alert(data.message);
						    }
						},
					error:function(){
			           alert("出现错误");
			           $("#submitNoticeButton").removeAttr("disabled");
			        }
				};
			$('#placeHotelNoticeForm').ajaxSubmit(options); 
		}
	}
	function validateDate(){
		var startTime = $("#noticeStartTimeId").val()==""?new Date().Format("yyyy-MM-dd HH:mm:ss"):$("#noticeStartTimeId").val();
		var endTime = $("#notieceEndTimeId").val();
		return compareDate(startTime,endTime);
	}
	function compareDate(startDate,endDate) {  // 时间比较的方法，如果d1时间比d2时间大，则返回true   
		var date1 = new Date(Date.parse(startDate.replace(/-/g, "/")));
        var date2 = new Date(Date.parse(endDate.replace(/-/g, "/")));
        return date1 > date2;
    } 
	Date.prototype.Format = function (fmt) { //author: meizz 
		 var o = {     
			"M+" : this.getMonth()+1, //月份     
			"d+" : this.getDate(), //日     
			"h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时     
			"H+" : this.getHours(), //小时     
			"m+" : this.getMinutes(), //分     
			"s+" : this.getSeconds(), //秒     
			"q+" : Math.floor((this.getMonth()+3)/3), //季度     
			"S" : this.getMilliseconds() //毫秒     
		 };     
		if(/(y+)/.test(fmt)){     
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));     
		}     
		for(var k in o){if(new RegExp("("+ k +")").test(fmt))fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));}     
	    return fmt;
	}
</script>