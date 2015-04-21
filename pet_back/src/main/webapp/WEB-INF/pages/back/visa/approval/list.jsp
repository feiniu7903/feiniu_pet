<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.lvmama.comm.vo.Constant"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String OrderDetailUrl =Constant.getInstance().getProperty("superback.ordServiceUrl");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>签证材料审核列表</title>
		<!--调整后的js -->
		<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
		<script type="text/javascript">
		var basePath='<%=request.getContextPath() %>';	
		</script>
		<script type="text/javascript"
					src="<%=basePath%>js/base/jquery-1.4.4.min.js">
		</script>
		<link rel="stylesheet" type="text/css"
					href="<%=basePath%>themes/cc.css" />
				<link rel="stylesheet"
					href="<%=basePath%>themes/base/jquery.ui.all.css" />
		<script type="text/javascript"
					src="<%=basePath%>js/ui/jquery-ui-1.8.5.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
		
		<script type="text/javascript"
					src="<%=basePath%>js/base/remoteUrlLoad.js">
		</script>
				<script type="text/javascript" src="<%=basePath%>js/base/form.js">
		</script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js"
					type="text/javascript">
		</script>
		<script language="javascript" src="<%=basePath%>js/ord/ord.js"
					type="text/javascript">
		</script>

				
		<script type="text/javascript"
					src="<%=basePath%>js/base/lvmama_common.js">
		</script>
		<script type="text/javascript"
					src="<%=basePath%>js/base/lvmama_dialog.js">
		</script>

	</head>
	<body>
		<div id="popDiv" style="display: none"></div>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">签证材料审核列表</a></li>
			</ul>
		</div>
		
		<div class="iframe-content">
			<div class="p_box">
				<form action="<%=basePath%>/visa/approval/index.do" method="post" id="searchForm">
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">订单号:</td>
							<td>
								<s:textfield name="searchOrderId"/>	
							</td>
							<td class="p_label">团号:</td>
							<td>
								<s:textfield name="searchTravelGroupCode" />	
							</td>
							<td class="p_label">游玩日期:</td>
							<td>
								<input type="text" class="dateISO" value="${searchStartVisitDate }" name="searchStartVisitDate"/> -- <input type="text" class="dateISO" value="${searchEndVisitDate }" name="searchEndVisitDate" />	
							</td>
						</tr>
						<tr>
							<td class="p_label">订单状态:</td>
							<td>
							<select id="searchOrderStatus"  name="searchOrderStatus">
								<option value=""> 全部 </option>
								<option value="NORMAL"> 正常 </option>
								<option value="CANCEL"> 取消 </option>
								<option value="FINISHED"> 完成 </option>
							</select>
							</td>
							<td class="p_label">产品区域:</td>
							<td>
							<select id="searchRegionName" name="searchRegionName">
								<option value=""> 全部 </option>
								<option value="AOZHOU">澳洲</option>
								<option value="DONGNANYA">东南亚</option>
								<option value="DONGNANYAHAIDAO">东南亚海岛</option>
								<option value="GANGAO">港澳</option>
								<option value="MEIZHOU">美洲</option>
								<option value="NANYA">南亚</option>
								<option value="OUZHOU">欧洲</option>
								<option value="RIHAN">日韩</option>
								<option value="ZHONGDONGFEI">中东非</option>
								<option value="QIANZHEN">签证</option>
								<option value="YOULUN">邮轮</option>
								<option value="TESECHANPIN">特色产品</option>
							</select>
							</td>
						</tr>
						<tr>
							<td class="p_label">游玩人姓名:</td>
							<td>
								<s:textfield name="searchName" />	
							</td>
							<td class="p_label">出签状态:</td>
							<td>
								<select name="searchVisaStatus">
									<option value=""> -- 请选择 -- </option>
									<option value="UNVET" <s:if test='searchVisaStatus == "UNVET"'>checked</s:if> >未审核</option>
									<option value="INVET" <s:if test='searchVisaStatus == "INVET"'>checked</s:if> >审核中</option>
									<option value="PASS_APPROVAL" <s:if test='searchVisaStatus == "PASS_APPROVAL"'>checked</s:if> >审核通过</option>
									<option value="UNPASS_APPROVAL" <s:if test='searchVisaStatus == "UNPASS_APPROVAL"'>checked</s:if> >审核不通过</option>
									<option value="VISA_OK" <s:if test='searchVisaStatus == "VISA_OK"'>checked</s:if> >已出签</option>
									<option value="NEED_FACE_VISA" <s:if test='searchVisaStatus == "NEED_FACE_VISA"'>checked</s:if> >需面签</option>
									<option value="VISA_FAIL" <s:if test='searchVisaStatus == "VISA_FAIL"'>checked</s:if> >拒签</option>
								</select>	
							</td>
							<td colspan="2">
								<button class="btn btn-small w5" type="button" onclick="search()">查询</button>	
							</td>
						</tr>														
					</table>
					<p class="mt20">
						<button class="btn btn-small" type="button" onclick="batchSendMaterial()">批量寄送材料</button>　
						<button class="btn btn-small" type="button" onclick="batchGetVisa()">批量获签</button>　
						<button class="btn btn-small" type="button" onclick="batchFaceVisa()">批量上传文件</button>　
						<button class="btn btn-small" type="button" onclick="batchReturnMaterial()">批量退还材料</button>　
						<button class="btn btn-small" type="button" onclick="batchReturnDeposit()">批量退还保险金</button>　
						<button class="btn btn-small" type="button" onclick="showCreateApproval()">导入订单游玩人</button>　
						<button class="btn btn-small" type="button" onclick="deleteApproval()">删除订单审核记录</button>　
						<button id="batchExportVisa" class="btn btn-small" type="button" onclick="batchExportVisas()" >导出材料审核表</button>
 					</p>
					<p class="tc mt20"></p>
				</form>
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th width="18"><input class="J_select-all" type="checkbox"></th>
						<th>团号</th>
						<th>订单号</th>
						<th>游玩人</th>
						<th>签证状态</th>
						<th>签证产品</th>
						<th>材料最晚递交时间</th>
						<th>游玩日期</th>
						<th width="325">操作</th>
					</tr>										
					<s:iterator value="pagination.items" var="approval">
						<tr>
							<td><input type="checkbox" value="${approval.visaApprovalId }" nam="chk" id="${approval.orderId }" title="${approval.name }" name ="${approval.cnVisaStatus }" lang="${approval.travelGroupCode }"  /></td>
							<td>${approval.travelGroupCode }</td>
							<td>
							<a href="javascript:showDetailDiv('historyDiv', '${approval.orderId}');">${approval.orderId }</a>
								<input type="hidden" id="orderId"  name="orderId" value="${approval.orderId }" />
							</td>
							<td>${approval.name }</td>
							<td>${approval.cnVisaStatus }</td>
							<td>${approval.productName }</td>
							<td><s:date name="lastDays" format="yyyy-MM-dd" /></td>
							<td><s:date name="visitTime" format="yyyy-MM-dd" /></td>
							<td class="oper">
								<a href="javascript:confirmMaterial(${approval.visaApprovalId});">材料确认</a>&nbsp;
								<a href="javascript:vitMaterial(${approval.visaApprovalId});">材料审核</a>&nbsp;
								<a href="javascript:sendMaterial(${approval.visaApprovalId});">寄送快递</a>&nbsp;
								<a href="javascript:addMaterial(${approval.visaApprovalId});">增补材料</a>&nbsp;
								<a href="javascript:getVisa(${approval.visaApprovalId});">签证状态</a>&nbsp;
								<br/>
								<a href="javascript:getFace(${approval.visaApprovalId});">上传文件</a>&nbsp;
								<s:if test='returnMaterial == "Y"'>
									可退还材料	
								</s:if>
								<s:else>
									<a href="javascript:returnMaterial(${approval.visaApprovalId});">退还材料</a>&nbsp;
								</s:else>
								<s:if test='returnBail == "Y"'>
									可退还保证金	
								</s:if>
								<s:else>
									<a href="javascript:returnDeposit(${approval.visaApprovalId});">退还保证金</a>&nbsp;
								</s:else>
								<a href="javascript:void(0)" class="showLogDialog" param="{'objectId':'${approval.visaApprovalId}','objectType':'VISA_APPROVAL_TARGET'}">查看日志</a>&nbsp;
							</td>
						</tr>
					</s:iterator>
					<tr>
     					<td colspan="3" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="6" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
    				</tr>
				</table>
			</div>
		</div>
		<div class="orderpop" id="historyDiv" style="display: none;"
			href="<%=OrderDetailUrl%>/ord/showHistoryOrderDetail.do">
		</div>
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =                                           0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
	</body>
	<script type="text/javascript">
		  $(function(){
			 
			$(".J_select-all").change(function(){
				$(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
			});  
			
	/* 		if (trim($("#searchOrderId")).val() == "" && $("#searchTravelGroupCode").val() == "") {
				var batchExportVisa = document.getElementById("batchExportVisa");
				batchExportVisa.style.display="none";
			} */
		});
		  
		  function search() {
		  	$("#searchOrderId").val($.trim($("#searchOrderId").val()));
		  	if ($("#searchOrderId").val() != "" && isNaN($("#searchOrderId").val())) {
		  		alert("请输入正确的订单号");
		  		$("#searchOrderId").focus();
		  		return; 
		  	}	  	
		  	
		  	$("#searchForm").submit();
		  }
		  
		  function confirmMaterial(visaApprovalId) {
			  $("#popDiv").load("<%=basePath%>/visa/approval/confirmMaterial.do?_=" + (new Date).getTime() + "&searchVisaApprovalId=" + visaApprovalId,function() {
					$(this).dialog({
	            		modal:true,
	            		title:"签证材料确认",
	            		width:350,
	            		height:200
	                });
	            });		  
		  }
		  
		  function vitMaterial(visaApprovalId) {
			  $("#popDiv").load("<%=basePath%>/visa/approval/vitMaterial.do?_=" + (new Date).getTime() + "&searchVisaApprovalId=" + visaApprovalId,function() {
					$(this).dialog({
	            		modal:true,
	            		title:"签证材料审核",
	            		width:700,
	            		height:800
	                });
	            });		  
		  }
		  
		  function sendMaterial(visaApprovalId) {
			  $("#popDiv").load("<%=basePath%>/visa/approval/sendMaterial.do?_=" + (new Date).getTime() + "&searchVisaApprovalId=" + visaApprovalId,function() {
					$(this).dialog({
	            		modal:true,
	            		title:"快递签证材料",
	            		width:500,
	            		height:550
	                });
	            });		  
		  }
		  
		  function addMaterial(visaApprovalId) {
			  $("#popDiv").load("<%=basePath%>/visa/approval/addMaterial.do?_=" + (new Date).getTime() + "&searchVisaApprovalId=" + visaApprovalId,function() {
					$(this).dialog({
	            		modal:true,
	            		title:"增补签证材料",
	            		width:500,
	            		height:320
	                });
	            });		  
		  }
		  
		  function getVisa(visaApprovalId) {
			  $("#popDiv").load("<%=basePath%>/visa/approval/getVisa.do?_=" + (new Date).getTime() + "&searchVisaApprovalId=" + visaApprovalId,function() {
					$(this).dialog({
	            		modal:true,
	            		title:"签证状态",
	            		width:350,
	            		height:200
	                });
	            });		  
		  }
		  
		  function getFace(visaApprovalId) {
			  $("#popDiv").load("<%=basePath%>/visa/approval/faceVisa.do?_=" + (new Date).getTime() + "&searchVisaApprovalId=" + visaApprovalId,function() {
					$(this).dialog({
	            		modal:true,
	            		title:"上传文件",
	            		width:500,
	            		height:550
	                });
	            });		  
		  }
		  
		  function returnMaterial(visaApprovalId) {
			  if (confirm("您真的确认此人的材料可以退还了吗?")) {
				  $.ajax({
		    	 		url: "<%=basePath%>/visa/approval/returnMaterial.do",
						type:"post",
		    	 		data: {
								"visaApproval.visaApprovalId":visaApprovalId,
								"date":(new Date).getTime()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								alert("修改成功");
								document.location.reload();
							} else {
								alert("数据丢失，操作失败");
							}
		    	 		}
		    		});				  
			  }
		  }
		  
		  function returnDeposit(visaApprovalId) {
			  if (confirm("您真的确认此人的保证金可以退还了吗?")) {
				  $.ajax({
		    	 		url: "<%=basePath%>/visa/approval/returnDeposit.do",
						type:"post",
		    	 		data: {
								"visaApproval.visaApprovalId":visaApprovalId,
								"date":(new Date).getTime()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								alert("修改成功");
								document.location.reload();
							} else {
								alert("数据丢失，操作失败");
							}
		    	 		}
		    		});				  
			  }
		  }
		  
		  function batchSendMaterial() {
			  var arr = "";
			  $(":checkbox:checked").each(function(i,n){
				if (n.value != "on") {
					arr = arr + n.value + ",";	
				}
			  });
			  if (arr.length == 0) {
				  alert("请先选择您需要批量寄送材料的人员");
				  return;
			  }

			  if (confirm("您真的确认进行寄送材料吗?")) {
				  $("#popDiv").load("<%=basePath%>/visa/approval/batchSendMaterial.do?_=" + (new Date).getTime() + "&batchVisaApprovalId=" + arr,function() {
						$(this).dialog({
		            		modal:true,
		            		title:"签证状态",
		            		width:450,
		            		height:400
		                });
		            });				  
			  }			  
		  }
		  
		  function batchGetVisa() {
			  var arr = "";
			  $(":checkbox:checked").each(function(i,n){
				if (n.value != "on") {
					arr = arr + n.value + ",";	
				}
			  });
			  if (arr.length == 0) {
				  alert("请先选择您需要批量修改获签状态的人员");
				  return;
			  }

			  if (confirm("您真的确认进行批量修改获签状态吗?")) {
				  $("#popDiv").load("<%=basePath%>/visa/approval/batchGetVisa.do?_=" + (new Date).getTime() + "&batchVisaApprovalId=" + arr,function() {
						$(this).dialog({
		            		modal:true,
		            		title:"签证状态",
		            		width:350,
		            		height:200
		                });
		            });				  
			  }			  			  
		  }
		  
		  function batchFaceVisa() {
			  var arr = "";
			  $(":checkbox:checked").each(function(i,n){
				if (n.value != "on") {
					arr = arr + n.value + ",";	
				}
			  });
			  if (arr.length == 0) {
				  alert("请先选择您需要批量上传文件的人员");
				  return;
			  }

			  if (confirm("您真的确认进行批量上传文件吗?")) {
				  $("#popDiv").load("<%=basePath%>/visa/approval/batchFaceVisa.do?_=" + (new Date).getTime() + "&batchVisaApprovalId=" + arr,function() {
						$(this).dialog({
		            		modal:true,
		            		title:"批量上传文件",
		            		width:450,
		            		height:400
		                });
		            });				  
			  }			  
		  }
		  
		  function batchReturnMaterial() {
			  var arr = "";
			  $(":checkbox:checked").each(function(i,n){
				if (n.value != "on") {
					arr = arr + n.value + ",";	
				}
			  });
			  if (arr.length == 0) {
				  alert("请先选择您需要退还材料的人员");
				  return;
			  }

			  if (confirm("您真的确认进行批量材料可以退还了吗?")) {
				  $.ajax({
		    	 		url: "<%=basePath%>/visa/approval/batchReturnMaterial.do",
						type:"post",
		    	 		data: {
								"batchVisaApprovalId":arr,
								"date": (new Date).getTime()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								document.location.reload();
							} else {
								alert("数据丢失，操作失败");
							}
		    	 		}
		    		});				  
			  }			  
		  }
		  
		  function batchReturnDeposit() {
			  var arr = "";
			  $(":checkbox:checked").each(function(i,n){
				if (n.value != "on") {
					arr = arr + n.value + ",";	
				}
			  });
			  if (arr.length == 0) {
				  alert("请先选择您需要退还保证金的人员");
				  return;
			  }

			  if (confirm("您真的确认进行批量保证金可以退还了吗?")) {
				  $.ajax({
		    	 		url: "<%=basePath%>/visa/approval/batchReturnDeposit.do",
						type:"post",
		    	 		data: {
								"batchVisaApprovalId":arr,
								"date": (new Date).getTime()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
		    	 		dataType:"json",
		    	 		success: function(result) {
							if (result.success) {
								document.location.reload();
							} else {
								alert("数据丢失，操作失败");
							}
		    	 		}
		    		});				  
			  }			  
		  }
		  
		  function showCreateApproval() {
			  $("#popDiv").load("<%=basePath%>/visa/approval/showVisaApproval.do",function() {
					$(this).dialog({
	            		modal:true,
	            		title:"导入订单游玩人",
	            		width:550,
	            		height:400
	                });
	            });		
		  }
		  
		  function deleteApproval() {
			  $("#popDiv").load("<%=basePath%>/visa/approval/showDeleteApproval.do",function() {
					$(this).dialog({
	            		modal:true,
	            		title:"删除订单游玩人",
	            		width:550,
	            		height:400
	                });
	            });			  
		  }

			
		  function batchExportVisas(){
			  var batchVisaApprovalId = "";
			  var batchOrderId ="";
			  var userName = "";//
			  var lang ="";
			  //
			  //设定发送的参数
			  var travelGroupCode="";//团号
			  var visaApprovalIds="";//多个 签证id
			  var  orderIds="";//多个订单
 			  
			  $(":checkbox:checked").each(function(i,n){
				if (n.value != "on") {
 				  lang = lang+n.lang+",";//团号
				 if(n.name!=null && n.name=="未审核"){
 					    userName=userName+n.title+",";  
				  }else{
					batchVisaApprovalId = batchVisaApprovalId + n.value + ",";//+visaApprovalId
					batchOrderId = batchOrderId + n.id+",";//+orderID
				  }
				}
			  });
			  if(userName.length>0){
				  userName = userName.substring(0,userName.lastIndexOf(","));
				 if(!confirm("用户名为:'"+userName+"'未选择人群,无法下载材料! 是否继续下载?")){
					 return;
				 }
			  }
			  	
			  if (batchVisaApprovalId.length == 0) {
				  alert("请先选择您需要导出材料审核信息");
				  return;
			  }
			  
			    //设定团号
				if(lang.indexOf(",")>0){
 					var temp = lang.split(",");
					var flag = false;
					var next = temp[0];
					for(var i in temp){
						if(i<temp.length-1){
							if(temp[i]==next){
								flag = true;
							}else{
								flag = false;
								break;
							}
						}
					}
					if(!flag){
						alert("存在多个团号,不予以导出材料审核表!");
						return;
					}else{
						//设定团号参数
						travelGroupCode = next;
					}
				}
			    //设定签证id参数
				visaApprovalIds=batchVisaApprovalId.substring(0,batchVisaApprovalId.length-1);
			    //设定订单id参数
			    orderIds=batchOrderId.substring(0,batchOrderId.length-1);
			  if (confirm("您确认要导出材料审核表?")) {
				   var all=$(".J_select-all").attr("checked");
				   var url;
				   if(all==true){
	 				   url="<%=basePath%>/visa/approval/exportXLSForVisaList.do"+"?travelGroupCode="+travelGroupCode;
				   }else {
 				       url="<%=basePath%>/visa/approval/exportXLSForVisaList.do"+"?travelGroupCode="+travelGroupCode+"&visaApprovalIds="+visaApprovalIds+"&orderIds="+orderIds;
				   }
  		           window.open(url);
			  }
		  }
		//显示弹出层
		  function showDetailDiv(divName, orderId) {
		  	document.getElementById(divName).style.display = "block";
		  	document.getElementById("bg").style.display = "block";
		  	//请求数据,重新载入
		  	$("#" + divName).reload({"orderId":orderId});
		  }
	</script>
</html>
