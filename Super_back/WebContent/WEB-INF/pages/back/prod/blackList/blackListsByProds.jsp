<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>黑名单列表</title>
<s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
<s:include value="/WEB-INF/pages/back/base/timepicker.jsp"/>
<script src="${basePath}/js/base/jquery.form.js"></script>
<link href="${basePath}/style/prod/panel-content.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main main02" style="width: 820px;">
    <div class="row1">
			<input type="button" id="addBlackListByProds" value="新增" />
    </div>

    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" class="newTable" style="text-align: center;width: 820px;">
            <tr class="newTableTit">
                <td>序号</td>
                <td>限制方式</td>
                <td>被限制产品集</td>
	          	<td>限制条件</td>
	          	<td>限制开始时间</td>
	          	<td>限制结束时间</td>
	          	<td>限制周期/时间内可购买次数</td>
	          	<td>限制原因</td>
	          	<td>操作</td>
            </tr>
            <s:iterator value="pagination.records" status="index">
                <tr>
                <td><s:property value="#index.index+1"/></td>
                <td>
                	<s:if test="blackIsPhone == 2">单用户</s:if>
                	<s:elseif test="blackIsPhone == 3">单设备</s:elseif>
                	<s:elseif test="blackIsPhone == 4">单手机</s:elseif>
                </td>
		  		<td><s:property value="blackPhoneNum"/></td>
		  		<td>
		  			<s:if test="blackCirculation == 0">天</s:if>
		  			<s:elseif test="blackCirculation == 1">周</s:elseif>
					<s:elseif test="blackCirculation == 2">月</s:elseif>
					<s:elseif test="blackCirculation == 3">年</s:elseif>
		  		</td>
		  		<td><s:date name="blackStartTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  		<td><s:date name="blackEndTime" format="yyyy-MM-dd HH:mm:ss"/></td>
		  		<td><s:property value="blackLimit"/></td>
		  		<td><s:property value="blackReason"/></td>
		  		<td><a href="#" onclick="removeBlack(<s:property value="blackId"/>);">删除</a></td>
		  	</tr>
            </s:iterator>
        </table>
    </div>
    <table width="700" border="0" align="center">
        <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
    </table>
</div>
<div id="addBlackListByProdsDiv" style="display: none" class="p_box" style="width: 650px;">
	<form action="" id="addBlackListByProdsForm" method="post">
		<table  class="p_table table_center" style="width: 630px;">
				 <tr>
					<td width="200px" class="p_label"><em><span style="color:red;">*</span>限制方式：</em></td >
					<td>
						<s:radio name="prodBlackList.blackIsPhone" id="isPhoneId" list="#{'2':'单用户','3':'单设备','4':'单手机'}" labelSeparator="&nbsp;" value="2"></s:radio>
					</td>
				</tr>
				<tr>
					<td width="200px"  class="p_label"><em><span style="color:red;">*</span>需要限制的产品集：</em></td >
					<td>
						<textarea style="width:300px;" rows="6" cols="150" name="prodBlackList.blackPhoneNum"></textarea>产品以,分割
					</td>
				</tr>
				<tr>
					<td width="200px"  class="p_label"><em><span style="color:red;">*</span>时间条件：</em></td >
					<td>
						<input type="hidden" value="${prodBlackList.productId}" name="prodBlackList.productId"/>
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
</div>
<script type="text/javascript">
    $(function(){
    	$("#addBlackListByProdsDiv").dialog({
    		autoOpen: false,
    		modal: true,
    		width:700,
			height:550
    	});
    	 $('input.blackStartDate').datetimepicker({
       	  	timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
	   	$('input.blackEndDate').datetimepicker({
	       	 timeFormat: "hh:mm:ss",
	         dateFormat: "yy-mm-dd"
		});
    });
    $("#addBlackListByProds").click(function() {
		$("#addBlackListByProdsDiv").dialog("open");
	});
    function saleCompareDate(StartDate,EndDate){
    	var start=new Date(StartDate.replace("-", "/").replace("-", "/"));  
    	var end=new Date(EndDate.replace("-", "/").replace("-", "/"));
    	if(start>end){
    		return true;
    	}else{
    		return false;
    	}
    }
    function validateDate(){
		if($("textarea[name='prodBlackList.blackPhoneNum']").val() == ""){
			alert("输入需要限制的产品集!");
			return;
		}
		var blackPhoneNum = $("textarea[name='prodBlackList.blackPhoneNum']").val();
		var blackPhoneNums = blackPhoneNum.split(",");
		for(var i = 0 ; i < blackPhoneNums.length ; i ++){
			if(isNaN(blackPhoneNums[i])){
				alert(blackPhoneNums[i]+"非法产品Id");
				return;
			}
		}
		if($("input[name='prodBlackList.blackStartTime']").val() == ""){
			alert("请选择开始时间!");
			return;
		}
		if($("input[name='prodBlackList.blackEndTime']").val() == ""){
			alert("请选择结束时间!");
			return;
		}
		if(saleCompareDate($("input[name='prodBlackList.blackStartTime']").val(),$("input[name='prodBlackList.blackEndTime']").val())){
			alert("开始时间大于结束时间");
			return;
		}
		if($("input[name='prodBlackList.blackLimit']").val() == ""){
			alert("请输入限制笔数!");
			return;
		}
		if($("textarea[name='prodBlackList.blackReason']").val() == ""){
			alert("请输入限制原因!");
			return;
		}
		var action="${basePath}/prodblack/insertByProds.do";
 		var options = { 
            url:action,
            dataType:"json",
            async:false,
            type : "POST", 
            success:function(data){ 
            	if(data.success){
            		alert("黑名单新增成功");
            		window.location.href = "${basePath}/prodblack/queryByProds.do"; 
            	}
            }, 
            error:function(){ 
                alert("操作超时！"); 
            } 
        };
        $('#addBlackListByProdsForm').ajaxSubmit(options);
		
	}
	function removeBlack(blackId,product){
		if(confirm('您确定要删除些规则吗?')){
			$.ajax({
	            type: "POST",
	            async: false,
	            url: "${basePath}/prodblack/deleteByProds.do?prodBlackList.blackId="+blackId,
	            dataType: "json",
	            success:function(data){ 
	            	if(data.success){
	            		alert("黑名单删除成功!");
	            		window.location.href = "${basePath}/prodblack/queryByProds.do"; 
	            	}
	            }, 
	            error:function(){ 
	                alert("操作超时！"); 
	            } 
	        });
		}
	}
</script>
</body>
</html>