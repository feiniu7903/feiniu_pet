<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<s:include value="/WEB-INF/pages/back/base/timepicker.jsp"/>
<link href="${basePath}/style/prod/panel-content.css" rel="stylesheet" type="text/css" />
<script src="${basePath}/js/base/jquery.form.js"></script>
</head>
<body>
<div class="p_box" style="width: 650px;">
	<table  class="p_table table_center" style="width: 630px;">
			 <tr>
				<td width="200px" class="p_label"><em><span style="color:red;">*</span>是否指定手机号：</em></td >
				<td>
					<s:radio name="isPhoneName" id="isPhoneId" list="#{'1':'否','0':'是'}" labelSeparator="&nbsp;" onchange="javascript:changeIsPhone(this.value);" value="1"></s:radio>
				</td>
			</tr>
			<tr>
				<td width="200px"  class="p_label"><em>需要限制的手机号：</em></td >
				<td>
					<form action="${basePath}/prodblack/upload.do" id="excelFrom" enctype="multipart/form-data">
						<s:file id="upload_file" name="phoneFile" label="文件" onchange="javascript:getFileName(this);" disabled="true"/>
						<input type="button" id="submitButton" value="导入" onclick="javascript:return checkForm();" disabled="true"/><font color="red">支持xls、xlsx</font>
					</form>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="padding:0px;">
					<form action="${basePath}/prodblack/insert.do" id="insertBlackListFormId">
						<table class="p_table table_center" style="width: 100%; margin:0px;padding:0px; border:none;" cellpadding="0" cellspacing="0">
							<tr>
								<td width="200px"  class="p_label"><em><span style="color:red;">*</span>时间条件：</em></td >
								<td>
									 <input type="hidden" value="${prodBlackList.productId}" name="prodBlackList.productId"/>
									 <input type="hidden" value="1" name="prodBlackList.blackIsPhone"/>
									 <input type="hidden" name="phoneNums"/>
									<s:select cssClass="text1" name="prodBlackList.blackCirculation" list="#{'0':'天','1':'周','2':'月','3':'年'}"/>
								</td>
							</tr>
							<tr>
								<td width="200px"  class="p_label"><em><span style="color:red;">*</span>限制的时间范围：</em></td >
								<td>
									<input class="blackStartDate" name="prodBlackList.blackStartTime"/>
									<input class="blackEndDate" name="prodBlackList.blackEndTime"/>
								</td>
							</tr>
							<tr>
								<td width="200px"  class="p_label"><em><span style="color:red;">*</span>指定时间条件内可购买笔数：</em></td >
								<td>
									<input name="prodBlackList.blackLimit" size="8" onkeyup="value=value.replace(/[^\d]/g,'')"/>笔
								</td>
							</tr>
							<tr>
								<td width="200px"  class="p_label"><em><span style="color:red;">*</span>限制原因：</em></td >
								<td>
									<textarea style="width:300px;" rows="6" cols="150" name="prodBlackList.blackReason"></textarea>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="center" valign="middle">
									<input type="button" value="保存" onclick="javascript:validateDate();"/>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
	</table>
</div>
	<script type="text/javascript">
		$(function(){
			//加载时间插件
			 $('input.blackStartDate').datetimepicker({
	       	  	timeFormat: "hh:mm:ss",
	            dateFormat: "yy-mm-dd"
	        });
		   	$('input.blackEndDate').datetimepicker({
		       	 timeFormat: "hh:mm:ss",
		         dateFormat: "yy-mm-dd"
			});
		});
		function getFileName(obj){
			var filePath = obj.value;
	 		if (filePath.lastIndexOf(".") == -1) {
				alert("文件类型错误");
				return;
			}else {
				 var suffix=filePath.substring(filePath.lastIndexOf("."));
				 if(!(suffix=='.xls'||suffix=='.xlsx')){
	 				 alert("文件名后缀不对请重新上传!");
				 }
			}
		}
		function changeIsPhone(obj){
			$("input[name='prodBlackList.blackIsPhone']").val(obj);
			if(obj == "0"){
				$("#submitButton").attr('disabled',false); 
				$("#upload_file").attr('disabled',false);
			}else{
				$("#submitButton").attr('disabled',true); 
				$("#upload_file").attr('disabled',true);
			}
		}
		
		// 判断是否为日期格式
		function isDateStr(dateString){
		   if(!dateString || $.trim(dateString).length == 0) return false;
		   var r = dateString.match(/^(\d{1,4})\-(\d{1,2})\-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/); 
		   if(!r){
		  	   return false;
		   }
		   return true;
		}
		
		// 比较秒杀日期
		function saleCompareDate(strStartDate,strEndDate){
			if(!strStartDate || !strEndDate) return true;
			var startDate = new Date(Date.parse(strStartDate.replace(/-/g,"/")));
			var endDate   = new Date(Date.parse(strEndDate.replace(/-/g,"/")));
			if(startDate >= endDate){
				return true;
			}
			return false;
		}
		
		// 比较秒杀日期时分秒
		function saleCompareDateHms(strStartDate,strEndDate){
			if(!strStartDate || !strEndDate) return true;
			var startDateSecond = getSecondByDateHms(strStartDate);
			var endDateSecond   = getSecondByDateHms(strEndDate);
			if(null == startDateSecond || null == endDateSecond) return true;
			if(startDateSecond >= endDateSecond){
				return true;
			}
			return false;
		}
		
		// 通过日期获取时分秒的总秒数
		function getSecondByDateHms(date){
			var arrDate = $.trim(date).split(' ');
			if(null == arrDate || arrDate.length != 2 ) return null;
			var arrDateHms = $.trim(arrDate[1]).split(':');
			if(null == arrDateHms || arrDateHms.length != 3 ) return null;
			return 60*60*parseInt(arrDateHms[0]) + 
				   60*parseInt(arrDateHms[1]) + 
				   parseInt(arrDateHms[2]);  
		}
		
		function validateDate(){
			var flag = true;
			if($("input[name='prodBlackList.blackIsPhone']").val() == "0"){
				if($("input[name='phoneNums']").val() == ""){
					flag = false;
					alert("请导入有效的手机号码!");
					return;
				}
			}
			if($("input[name='prodBlackList.blackStartTime']").val() == ""){
				flag = false;
				alert("请选择开始时间!");
				return;
			}
			if($("input[name='prodBlackList.blackEndTime']").val() == ""){
				flag = false;
				alert("请选择结束时间!");
				return;
			}
			if(!isDateStr($("input[name='prodBlackList.blackStartTime']").val())||
			   !isDateStr($("input[name='prodBlackList.blackEndTime']").val())){
				flag =false;
				alert("请输入格式正确的日期\n\r日期格式：yyyy-MM-dd H:mm:ss\n\r例    如：2008-08-08 00:00:00\n\r");
				return;
			}
			if(saleCompareDate($("input[name='prodBlackList.blackStartTime']").val(),$("input[name='prodBlackList.blackEndTime']").val())){
				flag =false;
				alert("结束时间应该大于开始时间!");
				return;
			}
			if(saleCompareDateHms($("input[name='prodBlackList.blackStartTime']").val(),$("input[name='prodBlackList.blackEndTime']").val())){
				flag =false;
				alert("结束时间时分秒应该大于开始时间时分秒!");
				return;
			}
			if($("input[name='prodBlackList.blackLimit']").val() == ""){
				flag = false;
				alert("请输入限制笔数!");
				return;
			}
			if($("textarea[name='prodBlackList.blackReason']").val() == ""){
				flag = false;
				alert("请输入限制原因!");
				return;
			}
			if(flag){
				var action="${basePath}/prodblack/insert.do";
		 		var options = { 
	                url:action,
	                dataType:"json",
	                async:false,
	                type : "POST", 
	                success:function(data){ 
	                	var message = data.message+"\n";
	                	if(data.valiMessage != null){
	                		message += data.valiMessage;
	                	}
	                	alert(message);
	                }, 
	                error:function(){ 
	                    alert("操作超时！"); 
	                } 
	            };
	            $('#insertBlackListFormId').ajaxSubmit(options);
			}
		}
		function checkForm(){
		    if($.trim($("#upload_file").val())==''){
		        alert("导入文件不可以为空");
		        return false;
		    }
		    var filePath =$("#upload_file").val();
		    if (filePath.lastIndexOf(".") == -1) {
		        alert("文件类型错误");
		        return;
		    }else {
		         var suffix=filePath.substring(filePath.lastIndexOf("."));
		         if(!(suffix=='.xls'||suffix=='.xlsx')){
		             alert("文件名后缀不对请重新上传!");
		         }
		    }
		    $('#submitButton').attr('disabled',true); 
	 		var action="${basePath}/prodblack/upload.do";
	 		var options = { 
	                url:action,
	                dataType:"json",
	                async:false,
	                type : "POST", 
	                success:function(data){ 
	                	if(data.success){
	                		var message="成功导入"+data.rigthNums+"条!\n";
	                		if(data.repetitionNums > 0){
	                			message+="重复号码有"+data.repetitionNums+"条!\n";
	                		}
	                		if(data.wrongNums > 0){
	                			message+="错误的号码有"+data.wrongNums+"条!";
	                		}
	                		$("input[name='phoneNums']").val(data.right);
	                		alert(message);
	                	}else{
	                		alert(data.message);
	                	}
	                	$('#submitButton').attr('disabled',false); 
	                }, 
	                error:function(){ 
	                    alert("操作超时！"); 
	                } 
	            };
	            $('#excelFrom').ajaxSubmit(options);
		    return true;
		}
	</script>
</body>
</html>