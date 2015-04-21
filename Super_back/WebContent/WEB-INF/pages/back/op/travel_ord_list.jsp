<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>订单处理后台_订单审核</title>
		<script type="text/javascript">
	   		var path='<%=basePath%>';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css">
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>	
		<script language="javascript" src="<%=basePath%>js/ord/ord.js" type="text/javascript"></script>
		<script src="${basePath}js/phoneorder/important_tips.js" type="text/javascript"></script>
        <script src="${basePath}js/ord/batchSendGroupAdvice.js" type="text/javascript"></script>
<style >
     
    .ordergroup {
        position: absolute;left:50%;top:50%;
		margin-top:-150px;
		margin-left:-175px;
        padding:10px;background:#f1f1f1;z-index:99;
        width: 600px;height:100px;
       /*left:50%;top: 0%;
       margin-left:-400px!important;
       margin-top:0px;position:absolute;*/
		z-index:1002;
		}
    .orderpoptit{ overflow:hidden;zoom:1;margin:0px 0px 10px;}
	.orderpoptit strong{font-size:16px;color:#000;float:left;margin-left:10px;display:inline;}
	.orderpoptit .inputbtn{float:right;margin-right:10px;display:inline;}
	.popbox{margin:5px 10px 20px;}
	.popbox strong{display:block;border-bottom:2px solid #B8C9D6;margin-bottom:10px;color:#174B73;padding:0px 0px 2px 10px;}
	.popbox td{font-size:12px;}

</style>
		<script type="text/javascript">
		 
		//显示弹出层
			function showDetailDiv3(face,divName,objectID,flag,subProductType) {
				//alert(face.attr("subProductType").val());
				  document.getElementById(divName).style.display = "block";
				  document.getElementById("bg").style.display = "block";
				  if(flag==true){//上传出团
				      //长途自由行、长途跟团游--可以使用在线编辑模板.
				  	  if (subProductType == 'FREENESS_LONG' || subProductType == 'GROUP_LONG') {
		 		  		$("#chutuan1").show();
		 		  		$("#chutuan1").attr("href", "<%=request.getContextPath()%>/groupadvice/ReUploadOnlineHtmlTemplate.do?objectId="+objectID+"&objectType=ORD_ORDER");
		 		  	} else {
		 		  		$("#chutuan1").hide();
		 		  	}
					  $("#chutuan1").attr("href", "<%=request.getContextPath()%>/groupadvice/uploadOnlineHtmlTemplate.do?objectId="+objectID+"&objectType=ORD_ORDER");
				   	  $("#chutuan2").attr("href", "<%=request.getContextPath()%>/groupadvice/uploadDirectly.do?objectId="+objectID+"&objectType=ORD_ORDER");
				      $("#chutuan3").attr("href", "<%=request.getContextPath()%>/groupadvice/uploadFileTemplate.do?objectId="+objectID+"&objectType=ORD_ORDER");
		 		  }else {//重新上传出团
		 		  	if (subProductType == 'FREENESS_LONG' || subProductType == 'GROUP_LONG') {
		 		  		$("#chutuan4").show();
		 		  		$("#chutuan4").attr("href", "<%=request.getContextPath()%>/groupadvice/ReUploadOnlineHtmlTemplate.do?objectId="+objectID+"&objectType=ORD_ORDER");
		 		  	} else {
		 		  		$("#chutuan4").hide();
		 		  	}
		 			 
				   	  $("#chutuan5").attr("href", "<%=request.getContextPath()%>/groupadvice/ReUploadFileDirectly.do?objectId="+objectID+"&objectType=ORD_ORDER");
				 		
				   }

				   //定位：
				    var top = $(face).offset().top;
			        var left = $(face).offset().left;
			        $(".ordergroup").css("left","400px");
			        $(".ordergroup").css("top",top+$(face).outerHeight());
			}

			// 
			function closeDetailDiv(div){
					document.getElementById(div).style.display = "none";
					if(document.getElementById("bg"))
						document.getElementById("bg").style.display = "none";
					$("#"+div).closeDialog();
				}
			function showDetailDiv2(orderId){
				$("#historyDiv").openDialog();
				showDetailDiv('historyDiv',orderId);
			}
			
			$(function(){				
				$("a.visaStatus").click(function(){
					var $dlg=$("#visa_status_dialog");
					var orderId=$(this).attr("orderId");
					var $td=$("#visa_status_"+orderId);
					var current=$td.attr("result");
					$dlg.find("option[value="+current+"]").attr("selected","selected");
					$dlg.dialog({
						"title":"修改签证状态",
						"width":300,
						"modal":true,
						buttons:{
							"保存":function(){
								var newVal=$dlg.find("select option:selected").val();
								if(newVal===current){
									alert("您没有选中要修改的新状态!!");
									return false;
								}else{
									$.post("<%=basePath%>/op/opChangeVisa.do",{"ordId":orderId,"visaStatus":newVal},function(dt){
										var data=eval("("+dt+")");
										if(data.success){
											$td.attr("result",newVal);
											$td.html($dlg.find("select option:selected").text());
											$dlg.dialog("close");
										}else{
											alert(data.msg);
										}
									});
								}
							},
							"取消":function(){
								$dlg.dialog("close");
							}
						}
					});
				});
				
				$("a.trafficTicketStatus").click(function(){
					if(!confirm("您确定要修改开票状态")){
						return false;
					}
					var $this=$(this);					
					var orderId=$(this).attr("orderId");
						$.post("<%=basePath%>/op/opChackOrderTicket.do",{"ordId":orderId},function(dt){
							var data=eval("("+dt+")");
							if(data.success){								
								$("#trafficTicketStatus_"+orderId).html("已开票");
								$this.remove();
								alert("修改成功");
							}else{
								alert(data.msg);
							}
						});
				});
				
							
			});
		</script>
	</head>
	<body>
		<div id="table_box">
					<!--=========================主体内容==============================-->
					<div class="table_box"  id=tags_content_1>
						<div class="mrtit3">
							<form name='form1' method='post'
								action='<%=basePath%>/op/opOrderList.do'>
								<table width="98%" border="0"  class="newfont06"
								style="font-size: 12; text-align: left;">
									<tr>
									    <td> 团     号:</td>
										<td> <input name="travelCode" type="text" value="${travelCode}"/> </td>
										<td> 游玩时间:</td>
										<td> <input name="visitTimeStart" type="text" class="date" value="<s:date name="visitTimeStart" format="yyyy-MM-dd"/>"  />
											~
											<input name="visitTimeEnd"  type="text" class="date" value="<s:date name="visitTimeEnd" format="yyyy-MM-dd"/>"  />
										</td>
										<td> 订单状态:</td>
										<td>
										<s:select  list="#{'':'全部','NORMAL':'正常','CANCEL':'取消','FINISHED':'完成'}" name="order_Status"></s:select>
                                        </td>
                                         <td>出团通知书状态：</td>
									   <td>	
								        <!--   <s:checkboxlist name="groupWordStatus" list="#{'NEEDSEND':'待发送','SENT_NO_NOTICE':'已发送未通知','SENT_NOTICE':'已发送已通知','MODIFY_NO_NOTICE':'修改未通知','MODIFY_NOTICE':'修改已通知'}"/>		 -->			
						                  <s:select  list="#{'':'全部','NEEDSEND':'待发送','UPLOADED_NOT_SENT':'已上传待发送','SENT_NO_NOTICE':'已发送未通知','SENT_NOTICE':'已发送已通知','MODIFY_NO_NOTICE':'修改未通知','MODIFY_NOTICE':'修改已通知'}" name="groupWordStatus"></s:select>
						               </td>
									</tr>	
									<tr>	
										<td> 订 单 号:</td>
										<td> <input name="orderId" type="text"   value="${orderId}"/> </td>
										 <td> 下单时间: </td>
										<td> <input name="createTimeStart" type="text" class="date" value="<s:date name="createTimeStart" format="yyyy-MM-dd"/>" />
											~
											<input name="createTimeEnd" type="text" class="date" value="<s:date name="createTimeEnd" format="yyyy-MM-dd"/>"  />
										</td>
										<td> 审核状态:</td>
										<td>
										<s:select  list="#{'':'全部','UNVERIFIED':'未审核','VERIFIED':'已审核','INFOPASS':'信息审核通过','RESOURCEPASS':'资源审核通过','RESOURCEFAIL':'资源审核不通过'}" name="approveStatus"></s:select>
                                          </td>
                                          <td>所属分公司:</td>
										<td><s:select list="filialeNameList" name="filialeName" listKey="code" listValue="name"/></td>
										
									</tr>	
									<tr>
										<td>产品ID :</td>
										<td><input name="productId" type="text" value="${productId}"/></td>										
										<td>供应商:</td>
										<td><input type="text" name="supplierName" value="${supplierName}"/></td>
									<td> 支付状态:</td>
										<td> 
										<s:select  list="#{'':'全部','PAYED':'已经支付','PARTPAY':'部分支付','UNPAY':'未支付'}" name="paymentStatus"></s:select>
                                        </td>
                                         <td> 开票状态: </td>
										<td>
										<s:select  list="#{'':'全部','true':'已开票','false':'未开票'}" name="trafficTicketStatus"></s:select>
                                        </td>
									</tr>
								   
									<tr>
										<td>销售产品经理名称:</td>
										<td><input name="prodProductManagerName" type="text" value="${prodProductManagerName}"/></td>	
										<td colspan="6" align="right">
										
											<input type='submit' name="btnOrdListQuery" value="查 询" class="right-button08" />
											
											<input type='button' value="返  回" class="right-button08" onclick="javascript:history.go(-1)" />
											
											<input type="button" id="batchUploadShowBtn" title="批量上传出团通知书" value="上传出团通知书" class="right-button08" />
											
											<input type="button" id="batchSendNotifyBtn" title="批量发送出团通知书"  value="发送出团通知书" class="right-button08" />
											
										</td>
									</tr>
								</table>
							</form>
						</div>
						
						<table style="font-size: 12px" cellspacing="1" cellpadding="4"
							border="0" bgcolor="#666666" width="98%" class="newfont06">
							<tbody>
								<tr bgcolor="#eeeeee">
									<td height="35" width="15px" align="center"> <input type="checkbox" id="check-all" /> </td>
								    <td height="35" width="9%" align="center"> 团号 </td>
									<td height="35" width="5%" align="center"> 订单号 </td>
									<td height="35" width="5%" align="center"> 处理人 </td>
									<td width="10%" align="center"> 联系人/电话 </td>
									<td width="15%" align="center"> 产品名 </td>
									<td width="3%" align="center"> 数量 </td>
									<td width="4%" align="center"> 订单金额 </td>
									<td width="4%" align="center"> 应付金额 </td>
									<td width="4%" align="center"> 实付金额 </td>
									<td width="5%" align="center"> 订单状态 </td>
									<td width="8%" align="center"> 审核状态 </td>
									<td width="5%" align="center"> 支付状态 </td>
									<td width="5%" align="center"> 开票状态 </td>
									<td width="3%" align="center"> 合同</td>
									<td width="5%" align="center"> 出团通知书</td>
									<td width="5%" align="center"> 签证状态</td>
									<td width="40%" align="center"> 操作</td>
								</tr>
								<s:iterator id="order" value="ordersList">
									<tr bgcolor="#ffffff">
										<td height="30" align="center">
										  <s:set name="index" value="1" />
                                  	      <s:iterator id="orderItem" value="#order.ordOrderItemProds">
	                                  	      <!-- 是路线产品-产品子类型:长途自由行、长途跟团游，出境自由行、出境跟团游 ,可以勾选-->
											  <s:if test="(#orderItem.subProductType  =='FREENESS_LONG' || #orderItem.subProductType  =='GROUP_LONG' || #orderItem.subProductType  =='GROUP_FOREIGN' || #orderItem.subProductType  =='FREENESS_FOREIGN')&&#index==1">
												     <s:if test="#order.paymentStatus=='PAYED'&&(#order.groupWordStatus=='NEEDSEND'||#order.groupWordStatus=='UPLOADED_NOT_SENT')">                                   	      
			                                  	          <input type="checkbox" name="orderCheckbox" value="${order.orderId }"/>
													 </s:if>
												    <s:set name="index" value="2" />
											  </s:if>
										  </s:iterator>
										</td>
									    <td height="30">${order.travelGroupCode}</td>
										<td height="30">${order.orderId }</td>
										<td height="30"><span class="perm">${order.takenOperator}</span></td>
										<td height="30">${order.contact.name}/${order.contact.mobile}</td>
										<td>
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											<a class="showImportantTips" href="javascript:void(0)"
											productId="${orderItem.productId}" prodBranchId="${orderItem.prodBranchId}">${orderItem.productName }</a><br />
											</s:iterator>
										</td>
										<td  align="center">
											<s:iterator id="orderItem" value="#order.ordOrderItemProds">
											
											<s:if test="#orderItem.productType=='HOTEL'">
										${orderItem.hotelQuantity }<br />
									</s:if>
									<s:else>
										${orderItem.quantity }<br />
									</s:else>
											</s:iterator>
										</td>
									<td  align="center">${order.orderPayFloat}</td>
									<td  align="center">${order.oughtPayFloat} </td>
									<td  align="center">${order.actualPayFloat} </td>
									<td  align="center">
										<s:property value="#order.zhOrderStatus"/>
									</td>
									<td align="center">
										<s:property value="#order.zhApproveStatus"/> 
									</td>
									<td   align="center">
										<s:property value="#order.zhPaymentStatus"/>
									</td>
									<td   align="center" id="trafficTicketStatus_${order.orderId}">
									    <s:if test="#order.trafficTicketStatus=='false'">未开票 </s:if>
									    <s:if test="#order.trafficTicketStatus=='true'">已开票</s:if>
									</td>
									<td align="center">
										<s:if test="#order.needContract=='NEED_ECONTRACT'"> 
											<s:if test="#order.eContractStatus=='CONFIRM'">已签</s:if> 
											<s:else>未签</s:else> 
										</s:if>
									</td>
									<td align="center" id="groupWordStatus_${order.orderId}">
									  <s:property value="#order.zhGroupWordStatus"/>
									</td>
									<td align="center" class="visaStatus" result="${order.visaStatus}" id="visa_status_${order.orderId}">
										<s:property value="#order.zhVisaStatus"/>
									</td>
									<td align="center"> 
	                                     <a href="javascript:showDetailDiv2(${order.orderId})">查看</a><br />
	                                     <s:if test="#order.isGroupForeign()||#order.isFreenessForeign()">
	                                    	<a href="#visaStatus" class="visaStatus" orderId="${order.orderId}">修改签证状态</a><br/>
	                                     </s:if>                                    
	                                     <s:if test="#order.trafficTicketStatus=='false'">
	                                     <a href="#trafficTicketStatus" class="trafficTicketStatus" orderId="${order.orderId}">开票</a><br/>
	                                     </s:if>
	                                     <a href="javascript:openWin('<%=request.getContextPath()%>/common/upload.do?objectId=${order.orderId}&objectType=ORD_ORDER',500,400)">上传文件</a><br/>
		                                <!--  <s:if test="#order.groupWordStatus=='NEEDSEND'||#order.groupWordStatus==''">  
		                                      <a href="javascript:void(0)" onclick="showDetailDiv3(this,'div_upload_chutuan');">上传出团通知书</a><br/>
		                                 </s:if>
	                                     <s:else>
		                                      <a href="javascript:openWin('<%=request.getContextPath()%>/groupadvice/dwload.do?objectId=${order.orderId}&objectType=ORD_ORDER',500,400)">下载出团通知书</a><br/>
		                                      <a href="javascript:void(0)" onclick="showDetailDiv3(this,'div_reupload_chutuan');">重新上传文件</a><br />
                                  	     </s:else>   
                                  	      -->
                                  	      
                                  	      <s:set name="index" value="1" />
                                  	      <s:iterator id="orderItem" value="#order.ordOrderItemProds">
                                  	     <!-- 是路线产品-产品子类型:长途自由行、长途跟团游，出境自由行、出境跟团游 ,显示上传出团通知书链接-->
											 <s:if test="(#orderItem.subProductType  =='FREENESS_LONG' || #orderItem.subProductType  =='GROUP_LONG' || #orderItem.subProductType  =='GROUP_FOREIGN' || #orderItem.subProductType  =='FREENESS_FOREIGN')&&#index==1">
													 <s:if test="#order.paymentStatus=='PAYED'&&(#order.groupWordStatus=='NEEDSEND')">                                   	      
			                                  	         <a href="javascript:void(0)" onclick="showDetailDiv3(this,'div_upload_chutuan',${order.orderId},true,'${order.mainProduct.subProductType}');">上传出团通知书</a><br/>
													 </s:if>
													 <s:elseif test="#order.paymentStatus=='PAYED'&&(#order.groupWordStatus=='SENT_NOTICE'||(#order.groupWordStatus=='SENT_NO_NOTICE')||(#order.groupWordStatus=='UPLOADED_NOT_SENT')||(#order.groupWordStatus=='MODIFY_NOTICE')||(#order.groupWordStatus=='MODIFY_NO_NOTICE'))">
													      <a href="javascript:openWin('<%=request.getContextPath()%>/groupadvice/dwload.do?objectId=${order.orderId}&objectType=ORD_ORDER',500,400)">下载出团通知书</a><br/>
					                                      <a href="javascript:void(0)" onclick="showDetailDiv3(this,'div_reupload_chutuan',${order.orderId},false,'${order.mainProduct.subProductType}');" >重新上传出团通知书</a><br />
													 </s:elseif>
													 <s:else >
														 <span style="color:#ccc;" >上传出团通知书</span><br/>
													 </s:else>
												     <s:set name="index" value="2" />
											 </s:if>
										  </s:iterator>
                                  	   <!-- 
                                  	      <s:if test="#order.paymentStatus=='PAYED'&&(#order.groupWordStatus=='NEEDSEND'||#order.groupWordStatus=='')">                                   	      
                                  	         <a href="javascript:void(0)" onclick="showDetailDiv3(this,'div_upload_chutuan',${order.orderId},true);">上传出团通知书</a>
                                  	         <br/>
										  </s:if>
										  <s:elseif test="#order.paymentStatus=='PAYED'&&#order.travelGroupCode!=''&&(#order.groupWordStatus!='NEEDSEND'&&#order.groupWordStatus!='')">
										      <a href="javascript:openWin('<%=request.getContextPath()%>/groupadvice/dwload.do?objectId=${order.orderId}&objectType=ORD_ORDER',500,400)">下载出团通知书</a><br/>
		                                      <a href="javascript:void(0)" onclick="showDetailDiv3(this,'div_reupload_chutuan',${order.orderId},false);">重新上传出团通知书</a><br />
										  </s:elseif>
										 -->                             	   
                                    </td>
									</tr>
								</s:iterator>
								<s:if test="ordOrderSum!=null">
									<tr bgcolor="#ffffff">
										
										<td  align="right" colspan="6">
											总金额								
										</td>
									<td  align="center">${ordOrderSum.orderPayYan}</td>
									<td  align="center">${ordOrderSum.oughtPayYan} </td>
									<td  align="center">${ordOrderSum.actualPayYan} </td>
									<td height="30" colspan="9">&nbsp;</td>									
									</tr>
								</s:if>
								<tr><td colspan="18"  bgcolor="#eeeeee"> </td></tr>
							</tbody>
						</table>
					</div>
					<!--=========================主体内容 end==============================-->
			<table width="98%" border="0" align="center">
				<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
			</table>
 			<!--wrap end-->
			<!------------------------------查看  （弹出层灰色背景）-------------->
			<div id="bg" class="bg" style="display: none;"></div>
			<div class="orderpop" id="historyDiv" style="display: none;"
				href="<%=basePath%>ord/orderDetail.do">
			</div>
	<!-- ----------------------------上传出团通知书 -------------------------->
		  <div class="ordergroup" id="div_upload_chutuan" style="display:none;">
 			<div class="orderpoptit" style="background-color:#ccc">
				<strong>选择上传出团通知方式：</strong>
				<p class="inputbtn">
				 <input type="button" name="btnCloseOrder" class="button" value="关闭" onclick="javascript:closeDetailDiv('div_upload_chutuan');">
				</p>
			</div>
			<div class="popbox" >
				<form style="float: left;width:100;height:50;">
				 <a  id="chutuan1" target="blank" href="">使用出团通知书模版</a><br />  
				 <a  id="chutuan2" target="blank" href="">上传文件</a><br />
				 <a  id="chutuan3" target="blank" href="">上传模版文件</a><br />   
	            </form>
	        </div>               
		</div>
		<!-- 重新上传出团通知书 -->
		 <div class="ordergroup" id="div_reupload_chutuan" style=" display: none;">
			<div class="orderpoptit" style="background-color:#ccc">
				<strong>选择重新上传出团通知方式：</strong>
				<p class="inputbtn">
				 <input type="button" name="btnCloseOrder" class="button" value="关闭" onclick="javascript:closeDetailDiv('div_reupload_chutuan');">
				</p>
			</div>
			<div class="popbox"  >
				<form style=" float: left;width:100;height:50;" >
			       <a  id="chutuan4" target="blank" href="">使用出团通知书模版</a><br />  
				   <a  id="chutuan5" target="blank" href="">上传文件</a><br />   
				</form>
	        </div>               
		</div>
		<!------------------------------------main2 end--------------------------------->
		
		
		<!-- 给变更状态使用 ，其他的人也请不要删除  @author yangbin-->		
		<div id="visa_status_dialog" style="display: none">
			<div>修改当前订单签证状态</div>
			<form>
				状态:<select>
					<s:iterator value="visaStatusList" id="vs">
						<option value="${vs.code}">${vs.name}</option>
					</s:iterator>
				</select>
			</form>
		</div>
		</div>
		
		<!-- 批量上传出团通知书面板 -->
		<div id="batch-upload-panel" title="批量上传出团通知书" style="display: none">
			 <form method="post" id="batch-upload-form" target="_blank">
			 	 <input name="objectIds" value="" type="hidden"/>
				 <a href="javascript:;" id="batch_usesystplbtn" >使用出团通知书模版</a><br />  
				 <a href="javascript:;" id="batch_uploadbtn" >上传文件</a><br />
				 <a href="javascript:;" id="batch_useusertplbtn" >上传模版文件</a><br />
			 </form>
        </div>     
		<!-- 批量上传出团通知书面板 /-->
		
		
	</body>
</html>
