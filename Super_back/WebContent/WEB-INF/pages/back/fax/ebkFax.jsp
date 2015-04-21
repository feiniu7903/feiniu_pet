<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>传真管理</title>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }style/ui-components.css" >
<link rel="stylesheet" type="text/css" href="${basePath}style/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}style/panel-content.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath }themes/base/jquery.jsonSuggest.css"></link>
<script type="text/javascript" src="${basePath }js/base/jquery-1.4.4.min.js" ></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-1.8.5.js" ></script>
<script type="text/javascript" src="${basePath }js/base/jquery.validate.min.js" ></script>
<script type="text/javascript" src="${basePath }js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery.jsonSuggest-2.min.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${basePath }js/base/log.js" ></script>
<script type="text/javascript" src="${basePath }js/meta/target.js"></script>
<script type="text/javascript" src="${basePath}/js/ebk/ebk_fax_receive.js"></script>
<script type="text/javascript" src="${basePath }js/base/jquery-ui-timepicker-addon.js" ></script>
<script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>

<style type="text/css">
 .p_label th{ background-color:#fff;color: #596682; border-color: #CCCCCC}
  .p_box .p_table .p_label_noP td{ padding:5px 0;}
  .p_box .p_table .noPadding{padding:5px 0;}
 .p_box .Padding5 td,.p_box .Padding5 th{ padding:5px;}
</style>
</head>
<body style="height: auto;">
<div class="ui_title">
		<ul class="ui_tab">
			<li <s:if test="isNoSend">class="active"</s:if>><a href="${basePath }fax/ebkFax.do?ebkFaxTaskTab=NOSEND">未发送(${isNoSendCount })</a></li>
			<li <s:if test="isNoReplied">class="active"</s:if>><a href="${basePath }fax/ebkFax.do?ebkFaxTaskTab=NOREPLIED">未确认回传(${isNoRepliedCount })</a></li>
			<li <s:if test="isAllFax">class="active"</s:if>><a href="${basePath }fax/ebkFax.do?ebkFaxTaskTab=ALLFAX">所有传真</a></li>
		</ul>
		</div>
		<form id="searchForm" action="${basePath }fax/ebkFax.do" method="post" onsubmit="return checkSearchForm();">
		<input type="hidden" name="ebkFaxTaskTab" value="<s:property value="ebkFaxTaskTab"/>"/>
        <table class="p_table form-inline">
            <tbody>
                <tr>
                	<td class="p_label" width="120">订单号：</td>
                    <td width="160"><input id="orderId" type="text" name="orderId" value="${orderId}"></td>
                     <td class="p_label">确认类型:</td>
                    <td>
                      <select name="ebkCertificate.ebkCertificateType">
                       <option value="">-选择-</option>
                       <option value="CONFIRM">确认单</option>
                       <option value="CHANGE">变更单</option>
                       <option value="CANCEL">取消单</option>
                      </select>
                    </td>
                     <td class="p_label">分公司：</td>
                    <td>
                     <select name="ebkCertificate.filialeName">
                       <option value="">-全部-</option>
                       <option value="SH_FILIALE">上海总部</option>
                       <option value="BJ_FILIALE">北京分部</option>
                       <option value="GZ_FILIALE">广州分部</option>
                       <option value="CD_FILIALE">成都分部</option>
                       <option value="SY_FILIALE">三亚分部</option>
                       <option value="HS_FILIALE">黄山办事处</option>
                       <option value="HZ_FILIALE">杭州分部</option>
                      </select>
                    </td>
                </tr>
                <tr>
                    <td class="p_label">采购产品名称：</td>
                    <td><input id="metaProductName" type="text" name="ebkCertificateItem.metaProductName" value="${ebkCertificateItem.metaProductName }"></td>
                    <td class="p_label">凭证对象：</td>
                    <td>
                      <input type="text" name="ebkCertificate.targetName" id="targetName" value="${ebkCertificate.targetName }"/>
                      <input type="hidden" name="ebkCertificate.targetId" id="targetId" value="${ebkCertificate.targetId }"/>
                    </td>
                    <td class="p_label">供应商名称：</td>
                    <td>
                    	<input type="text" id="supplierName" name="ebkCertificate.supplierName" value="${ebkCertificate.supplierName }"/>
                    	<input type="hidden" id="supplierId" name="ebkCertificate.supplierId" value="${ebkCertificate.supplierId }"/>
                    </td>
                </tr>
                <s:if test="!isNoReplied">
                <tr>
                	<td class="p_label">销售产品名称：</td>
                    <td><input type="text" name="ebkCertificateItem.productName" value="${ebkCertificateItem.productName }"></td>
                    <td class="p_label">计划发送时间：</td>
                    <td>
                      <input type="text" class="dateTime" name="planTimeStart" id="planTimeStartInput" value="${planTimeStart }"/>
                      <input type="text" class="dateTime"  name="planTimeEnd" id="planTimeEndInput" value="${planTimeEnd }"/>
					</td>
					<td class="p_label"> <s:if test="isAllFax">实际发送时间：</s:if><s:if test="isNoSend">自动发送：</s:if></td>
                    <td>
                    <s:if test="isAllFax">
                           <input type="text" class="dateTime"  name="sendTimeStart" id="sendTimeStartInput" value="${sendTimeStart }"/>
                           <input type="text" class="dateTime"  name="sendTimeEnd" id="sendTimeEndInput" value="${sendTimeEnd }"/>
                    </s:if>
                    <s:if test="isNoSend">
                      <select name="ebkFaxTask.autoSend">
                       <option value="">-全部-</option>
                       <option value="true">自动发送</option>
                       <option value="false">非自动发送</option>
                      </select>
                    </s:if>
                    </td>
                </tr>
                <tr>
                	<td class="p_label"> 游玩人姓名 ：</td>
                    <td>
                        <input type="text" name="ebkCertificate.travellerName" value="${ebkCertificate.travellerName }"/>
					</td>
					<td class="p_label">手机号码：</td>
                    <td>
                          <input type="text" name="ebkCertificate.mobile" value="${ebkCertificate.mobile }"/>
                    </td>
                    <td class="p_label">游玩时间：</td>
                    <td>
                        <input id="visitTimeStartInput" class="dateTime"  type="text" name="visitTimeStart" value="${visitTimeStart }"/>
                        <input id="visitTimeEndInput" class="dateTime"  type="text" name="visitTimeEnd" value="${visitTimeEnd }"/>
					</td>
                </tr>
               </s:if>
               <tr>
               	<td class="p_label"> 凭证代码 ：</td>
                   <td>
                       <input type="text" id="ebkCertificateId" name="ebkCertificate.ebkCertificateId" value="${ebkCertificate.ebkCertificateId }"/>
				</td>
				<td class="p_label">发送编号：</td>
                   <td>
                         <input type="text" id="ebkFaxSendId" name="ebkFaxSendId" value="${ebkFaxSendId }"/>
                   </td>
                   <td class="p_label"></td>
                   <td></td>
               </tr>
               <s:if test="isNoReplied">
					<tr>
						<td class="p_label">计划发送时间：</td>
						<td>
						  <input type="text" class="dateTime" name="planTimeStart" id="planTimeStartInput" value="${planTimeStart }"/>
						  <input type="text" class="dateTime"  name="planTimeEnd" id="planTimeEndInput" value="${planTimeEnd }"/>
						</td>
						<td class="p_label">实际发送时间：</td>
						<td>
							<input type="text" class="dateTime"  name="sendTimeStart" id="sendTimeStartInput" value="${sendTimeStart }"/>
							<input type="text" class="dateTime"  name="sendTimeEnd" id="sendTimeEndInput" value="${sendTimeEnd }"/>
						</td>
						<td class="p_label">游玩时间：</td>
						<td>
							<input id="visitTimeStartInput" class="dateTime"  type="text" name="visitTimeStart" value="${visitTimeStart }"/>
							<input id="visitTimeEndInput" class="dateTime"  type="text" name="visitTimeEnd" value="${visitTimeEnd }"/>
						</td>
					</tr>
			   </s:if>
               <tr>
                    <td class="p_label">订单类型：</td>
                    <td colspan="5">
						<input id="orderType0" name="ebkCertificate.orderType" type="checkbox" size="15" value="TICKET">
						门票
						<input id="orderType8" name="ebkCertificate.orderType" type="checkbox" size="15" value="HOTEL">
						酒店
						<input id="orderType1" name="ebkCertificate.orderType" type="checkbox" size="15" value="GROUP">
						短途跟团游
						<input id="orderType2" name="ebkCertificate.orderType" type="checkbox" size="15" value="GROUP_LONG">
						长途跟团游
						<input id="orderType3" name="ebkCertificate.orderType" type="checkbox" size="15" value="GROUP_FOREIGN">
						出境跟团游
						<input id="orderType4" name="ebkCertificate.orderType" type="checkbox" size="15" value="FREENESS">
						目的地自由行
						<input id="orderType5" name="ebkCertificate.orderType" type="checkbox" size="15" value="FREENESS_FOREIGN">
						出境自由行
						<input id="orderType6" name="ebkCertificate.orderType" type="checkbox" size="15" value="FREENESS_LONG">
						长途自由行
						<input id="orderType7" name="ebkCertificate.orderType" type="checkbox" size="15" value="SELFHELP_BUS">
						自助巴士班
                    </td>
                </tr>
                
               <s:if test="!isNoSend">
                <tr>
                 <td class="p_label">处理状态：</td>
                 <td colspan="5">
	                 <input type="checkbox" name="ebkFaxTask.sendStatus" value="" id="sendStatusAll"/>全部
	                 <s:iterator value="ebkFaxTaskStatusList" var="s">
						 <label><input type="checkbox" name="ebkFaxTask.sendStatus" value="${status }"/>${cnName }</label>
	                 </s:iterator>
                 </td>
                </tr>
                </s:if>
                <s:if test="isAllFax">
                <tr>
                 <td class="p_label">回传状态 ：</td>
                 <td colspan="5">           		
                 	<input type="checkbox" name="ebkFaxTask.faxSendRecvStatus" id="faxSendRecvStatusrecvok" value="FAX_SEND_STATUS_RECVOK"/>确认回传同意
                 	<input type="checkbox" name="ebkFaxTask.faxSendRecvStatus" id="faxSendRecvStatusrecvno" value="FAX_SEND_STATUS_RECVNO"/>确认回传不同意
                 </td>
                </tr>
                </s:if>
            </tbody>
        </table>
        <p class="tc mt20">
			<button onclick="" type="submit" class="btn btn-small w5">查询</button>&#12288;
		</p>
        </form>
		<div class="iframe-content">
			<div class="p_box">
			 <p class="tl mb10">
			  <input value="批量修改内部备注" type="button" class="btn btn-small w5" onclick="showUpdateFax(null,null)"/>
			  <s:if test="!isNoReplied"><input value="批量发送" type="button" class="btn btn-small w5" onclick="batchSendFax()"/>(说明：是对所选中的传真做批量发送动作，各发各的，不是合并发送)</s:if>
			  </p>
				<table class="p_table table_center Padding5">
					<tr>
						<th><input type="checkbox" id="checkAllFax"/></th>
						<th>编号</th>
						<th width="20">确认类型</th>
						<th>计划发送时间</th>
						<th>自动发送</th>
						<th>供应商名称</th>
						<th>凭证对象</th>
						<th width="20">产品类型</th>
						<th></th>
						<th>内部备注</th>
						<s:if test="!isNoSend">
						<th width="20">发送次数</th>
						<th width="20">处理状态</th>
						<s:if test="ebkFaxTaskTab=='ALLFAX'"><th width="20">回传状态</th></s:if>
						</s:if>
						<th>待审特殊要求</th>
						<th nowrap="nowrap" width="50">操作</th>
					</tr>
					<s:include value="/WEB-INF/pages/back/fax/ebkFaxTaskList.jsp"/>
					<tr>
	    				<td colspan="2" align="right">总条数：${ebkFaxTaskPage.totalResultSize}</td>
						<td colspan="12" align="right" style="text-align:right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(ebkFaxTaskPage)"/></td>
   				    </tr>
				</table>
			</div>
		</div>
		
		<div id="updateFax" style="display: none">
			<form action="${basePath}fax/updateFaxMemo.do" method="post" id="updateFaxForm">
			    <input type="hidden" name="ebkFaxTaskTab" value="<s:property value="ebkFaxTaskTab"/>"/>
			    <input type="hidden" value="" name="ebkFaxTaskIds" id="ebkFaxTaskId"/>
			    <input type="hidden" value="" name="ebkFaxTask.ebkFaxTaskId" id="e_ebkFaxTaskId"/>
			    <input type="hidden" value="" name="ebkCertificate.ebkCertificateId" id="ebkCertificateId"/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="p_table form-inline">
				<tr>
					<td  class="p_label" nowrap="nowrap">
					内容备注：
					</td>
					<td bgcolor="#FFFFFF" >
						<textarea rows="10" cols="100" style="width: 334px; height: 185px;" name="ebkCertificate.memo" id="memo"></textarea>
					</td>
				</tr>
				</table>
				<p class="tc mt20">
					<button onclick="checkMemo()" type="button" class="btn btn-small w5">提交</button>&#12288;
				</p>
			</form>
		</div>
		<div id="show_receive_file_div" url="${basePath }/fax/faxReceive/showFaxRecvFileDetail.do"></div>
		<div id="showFaxSend_div" url="${basePath }/fax/showFaxSend.do"></div>
		<div id="supplierDetail" url="/pet_back/sup/detail.do"></div>
		<div id="showTargetDiv"></div>
	    <div id="target_list_div"></div>
</body>

		<script type="text/javascript">
		 $(function(){
			 var dataParam={
					 	timeFormat:'hh:mm',
						changeMonth: true,
						changeYear: true,
						showOtherMonths: true,
						selectOtherMonths: true,
						buttonImageOnly: true,
						showButtonPanel:false
					};
			 $( "input.dateTime" ).datetimepicker(dataParam);
			 
			 $('#supplierName').jsonSuggest({
					url:'${basePath }supplier/searchSupplier.do',
					maxResults: 10,
					minCharacters: 2,
					onSelect: function(obj){
						$("#supplierId").val(obj.id);
					}
			});
			 
			 $('#targetName').jsonSuggest({
					url:'/pet_back/sup/target/queryCertificateList.do',
					maxResults: 10,
					minCharacters: 2,
					onSelect: function(obj){
						$("#targetId").val(obj.id);
					}
			});
			 
			 $("a.showDetail").click(function(){
				var supplierId=$(this).attr("data");
				$("#supplierDetail").showWindow({
					data:{"supplierId":supplierId}
				});
			 });
			 $(".showFaxReceiveFile").die("click");//去掉ebk_fax_receiver.js当中的live事件绑定
			 $(".showFaxReceiveFile").click(function() {
					var data = $(this).attr("data");
					if (data == undefined || data == null || data == "") {
						alert("无法查看该回传件！");
						return false;
					}
					$("#show_receive_file_div").showWindow({
						width : 1000,
						title : '回传件',
						data : {
							'ebkCertificateId' : data,
							'modifyCertIdflag' : 'false'
						}
					});
				});
				
			 $(".showFaxSend").bind("click", function() {
					var ebkFaxTaskId = $(this).attr("ebkFaxTaskId");
					var ebkCertificateId = $(this).attr("ebkCertificateId");
					$("#showFaxSend_div").showWindow({
						width : 800,
						title : '传真发送日志',
						data : {
							'ebkFaxTask.ebkFaxTaskId' : ebkFaxTaskId,
							'ebkCertificate.ebkCertificateId':ebkCertificateId
						}
					});
					
				});
			 
			 $("#checkAllFax").click(function(){
				    $("[name='ebkFaxTaskId']").attr("checked",this.checked);//全选
			  });
			 
			 $("#sendStatusAll").click(function(){
				    $("[name='ebkFaxTask.sendStatus']").attr("checked",this.checked);//全选
			  });

			});
		 
			function showUpdateFax(ebkCertificateId,e_ebkFaxTaskId) {
				if(ebkCertificateId==null){
					var ebkFaxTaskIds = $("[name='ebkFaxTaskId']:checked");
					if (ebkFaxTaskIds.length == 0) {
						alert("请选择所需要修改的备注");
						return;
					} else {
						var ids="";
						$(ebkFaxTaskIds).each(function(){
							ids = ids+","+this.value;
						}); 
						$("#ebkFaxTaskId").val(ids.substring(1,ids.length));
						$("#updateFaxForm").find("#ebkCertificateId").val("");
						$("#memo").val("");
						
						showUpdateFaxBox();
					}
				}else{
					//单独加载
					var param = {'ebkCertificate.ebkCertificateId':ebkCertificateId,'ebkFaxTask.ebkFaxTaskId':e_ebkFaxTaskId};
		    		$.ajax({type:"POST", url:"${basePath}fax/loadEbkCertificate.do", data:param, dataType:"json", success:function (obj) {
			    		$("#e_ebkFaxTaskId").val(e_ebkFaxTaskId);	
			    		$("#ebkFaxTaskId").val("");	
		    			$("#updateFaxForm").find("#ebkCertificateId").val(obj.ebkCertificateId);
			    			if(obj.memo!='null'){
			    			 $("#memo").val(obj.memo);
			    			}else{
			    			 $("#memo").val("");
			    			}
			    			showUpdateFaxBox();
						},error: function(){
						    alert("加载失败");
						}
		    		});
				}
			}
			//是否发送传真，true：不发送，false：发送
			function updateDisableSend(ebkFaxTaskId,disableSend){
				if(window.confirm("确定不发送传真吗？")){
					var param = {'ebkFaxTask.ebkFaxTaskId':ebkFaxTaskId,'ebkFaxTask.disableSend':disableSend};
		    		$.ajax({type:"POST", url:"${basePath}fax/updateFaxTask.do", data:param, dataType:"json", success:function (obj) {
			    		 if("true"==obj.flag){
			    			 alert("禁止成功");
			    			 $("#searchForm").submit();
			    		 }else
		    			 {
		    			 	alert("无法禁止，传真不为未处理状态");
		    			 }
						},error: function(){
						    alert("禁止失败");
						}
		    		});
				}
			}
			
			function sendFaxOver(ebkFaxTaskId){
				if(window.confirm("确定要执行手工发送完成？")){
					var param = {'ebkFaxTaskId':ebkFaxTaskId};
		    		$.post("${basePath}fax/sendFaxOver.do",param, function (obj) {
		    			var data=eval("("+obj+")");
			    		 if(data.success){
			    			 alert("修改手工发送完成");
			    			 location.reload();
			    		 }else{
			    			 alert(data.msg);
			    		 }
		    		});
				}
			}
			
			function updateAutoSend(ebkFaxTaskId,autoSend){
				if(window.confirm("确定要修改自动发送？")){
					var param = {'ebkFaxTask.ebkFaxTaskId':ebkFaxTaskId,'ebkFaxTask.autoSend':autoSend};
		    		$.ajax({type:"POST", url:"${basePath}fax/updateFaxTask.do", data:param, dataType:"json", success:function (obj) {
			    		 if("true"==obj.flag){
			    			 alert("修改自动发送成功");
			    			 $("#searchForm").submit();
			    		 }
			    		 else
		    			 {
		    			 	alert("无法修改，传真不为未处理状态");
		    			 }
						},error: function(){
						    alert("修改失败");
						}
		    		});
				}
			}
			function showNextCert(ele, id) {
				if($(ele).html() == "+") {
					if($("[father='"+id+"']").size() > 0) {
						
						$("[father='"+id+"']").each(function(){
							$(this).show();
						});
					} else {
						$.ajax({
							url:"${basePath}fax/loadOldEbkFaxTask.do?fatherEbkFaxTaskId="+id, 
							dataType:"html",
							success:function (obj) {
								$("#father_"+id).after(obj);
							},
							error: function(){
							}
			 			});
					}
					$(ele).html("-");
				} else {
					$("[father='"+id+"']").each(function(){
						$(this).hide();
					});
					$(ele).html("+");
				}
			}
			function showUpdateFaxBox(){
				$("#updateFax").dialog({
					modal : true,
					title : "编辑备注",
					width : 450,
					height : 300
				});
			}

			function batchSendFax() {
				var ebkFaxTaskIds = $("[name='ebkFaxTaskId']:checked");
				if (ebkFaxTaskIds.length == 0) {
					alert("请选择所需要发送的传真");
					return;
				} 
				
				var params="?";
				$(ebkFaxTaskIds).each(function(){
					params=params+"faxTaskIds="+this.value+"&";
				}); 
				
				if (window.confirm("是否需要批量发送？")) {
					params = params.substring(0,params.length-1);
			 		$.ajax({type:"POST", url:"${basePath}fax/sendFax.do"+params, dataType:"json",success:function (obj) {
			 			      if(true==obj.success){
				    			 alert("发送完成");
			 			      }
							},error: function(){
							    alert("发送失败");
							}
			 		});
				}
				
			}
			
			function checkMemo(){
				if($("#memo").val().length>1000){
					alert("您输入的传真备注信息过长，请重新填写~！");
					return;
				}
				$.post($("#updateFaxForm").attr("action"),$("#updateFaxForm").serialize(),function(dt){
					var data=eval("("+dt+")");
					if(data.success){
						if($("#updateFaxForm [name='ebkFaxTaskIds']").val()=="") {
							$("#fax_inner_memo_"+$("#updateFaxForm [name='ebkFaxTask.ebkFaxTaskId']").val()).html($("#updateFaxForm [name='ebkCertificate.memo']").val());
							$("#updateFax").dialog("close");
						} else {
							window.location.reload();
						}
					}else{
						alert(data.msg);
					}
				});
			}
			
			function checkSearchForm(){
				 var reg = new RegExp("^[0-9]*$");
				 if(!(reg.test(
						 $.trim($("#searchForm #orderId").val())
						 ))){
					 alert("订单号请填写数字！");
			        return false;
				 }
				 if(!(reg.test(
						 $.trim($("#searchForm #ebkCertificateId").val())
						 ))){
					 alert("凭证代码请填写数字！");
			        return false;
				 }
				 if(!(reg.test(
						 $.trim($("#searchForm #ebkFaxSendId").val())
						 ))){
					 alert("发送编号请填写数字！");
			        return false;
				 }
				 reg = new RegExp("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29) ([0-1]?[0-9]|2[0-3]):([0-5][0-9])$");
				 var bool = true;
				 $( "input.dateTime" ).each(function(){
					 if($(this).val()!="" && !(reg.test($(this).val()))){
						 bool = false;
					 }
				 });
				 if(!bool){
					 alert("日期格式填写错误！");
					 return false;
				 }
				 return true;
			}
			function modifyMemo(orderId){
				$("<iframe frameborder='0' ></iframe>").dialog({
					autoOpen: true, 
			        modal: true,
			        title : "修改备注",
			        position: 'center',
			        width: 920, 
			        height: 620
				}).width(900).height(600).attr("src","${basePath}ord/showOrderMemoCheck.do?orderId="+orderId);
			}
			
			//$("#searchForm #faxSendRecvStatusrecvok,#faxSendRecvStatusrecvno").click(function(){
	    		//alert($("#supplierFlag").size());
	    		//var checked = $(this).attr("checked");
	    		//$("#searchForm #faxSendRecvStatusrecvok").removeAttr("checked");
	    		//$("#searchForm #faxSendRecvStatusrecvno").removeAttr("checked");
	    		//$(this).attr("checked",checked);
	    	//});
			
			var typeArray = new Array();
			<s:iterator value="ebkCertificate.orderTypeVo" var="sp">typeArray.push("${sp}");</s:iterator>
			<s:iterator value="ebkFaxTask.sendStatusVo" var="sp">typeArray.push("${sp}");</s:iterator>
			<s:iterator value="ebkFaxTask.faxSendRecvStatusVo" var="sp">typeArray.push("${sp}");</s:iterator>
			$(function(){
				for(var t = 0; t < typeArray.length; t++){
					$("#searchForm [value='"+typeArray[t]+"']").attr("checked", "checked");
				}
				$("#searchForm [name='ebkCertificate.ebkCertificateType']").val("${ebkCertificate.ebkCertificateType}");
				$("#searchForm [name='ebkCertificate.filialeName']").val("${ebkCertificate.filialeName}");
				$("#searchForm [name='ebkFaxTask.autoSend']").val("${ebkFaxTask.autoSend}");
			});
					//$("#searchForm name=['ebkCertificate.productType'] [value='"+t+"']").attr("checked");
		</script>
</html>