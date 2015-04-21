<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
		var orderId = '${orderDetail.orderId }';
		$(document).ready(function() {
		 $("#memoDiv").loadUrlHtml();
		});
		
		$("#closeRoom").click(function(){
			document.getElementById("closeRoomDiv").style.display ="block";
			document.getElementById("itembg").style.display = "block";
		 } 
		);
		function closeRoomDiv() {
			document.getElementById("closeRoomDiv").style.display ="none";
			document.getElementById("itembg").style.display = "none";
			
		}

		$("#itemcheckall").click( 
				function(){ 
					if(this.checked){ 
						$("input[name='specDates']").each(function(){
							var result=compareDate(this.value);
							if(result=="yes"){this.checked=false;}else{
						    this.checked=true;}
							}); 
					}else{ 
						$("input[name='specDates']").each(function(){this.checked=false;}); 
					} 
				} 
		 );
	    function compareDate(sepcdate) {
	    	var d = new Date();
			var nowtime = d.getFullYear()+"-"+d.getMonth() + 1+"-"+d.getDate();
	        var arys1= new Array();     
	        var arys2= new Array();       
	        arys1=nowtime.split('-');     
	        var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);     
	        arys2=sepcdate.split('-');  
	        var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);     
	        if(sdate>edate) {return "yes";}else {return "no";};  
	    }
		function doCloseRoom(){
			var checkall=document.getElementsByName("specDates");
			var count=0;
			for(var i=0;i<checkall.length;i++){
				if(checkall[i].checked){
					count=count+1;
				 }
			}
			if(count==0){
				alert("请选择关闭项进行操作!");
				return false;
			}else{
				var $form=$("#closeRoomForm");
				$.ajax({
					type:"post",
					url:"<%=basePath%>ordItem/operateCloseRoom.do",
					data:$form.serialize(),
					success:function(data){
					if (data=="OK") {
						alert("操作成功");
						closeRoomDiv();
					  }
					},
					error: function(er){
				    	alert("与服务器交互出现错误!请稍后再试!"+er);
					 }
				});	 
			 }
		}
		
		  
		</script>
	</head>

	<body>
		<!--=========================我的历史审核弹出层==============================-->
		<input type="hidden"  name="subprodtype" value="${orderItemMeta.subProductType}"/>
		<div class="orderpoptit">
			<strong>我的历史审核：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyDiv');">
			</p>
		</div>
		<div class="orderpopmain">
			<table width="100%" border="0" class="contactlist">
				<tr>
					<td width="25%">
						订单号：${orderDetail.orderId }
					</td>
					<td width="25%">
						下单时间：${orderDetail.zhCreateTime }
					</td>
					<td width="25%">
						下单人：${orderDetail.userName }
					</td>
					<td width="25%">
						支付等待时间： <s:if test="orderDetail.hasNeedPrePay()">
						<s:date name="orderDetail.aheadTime" format="yyyy-MM-dd HH:mm"/>
						</s:if><s:else>${orderDetail.zhWaitPayment}</s:else>
					</td>
				</tr>
				<tr>
					<td>
						应付金额：${orderDetail.oughtPayYuan }
					</td>
					<td>
						实付金额：${orderDetail.actualPayYuan }
					</td>
					<td>
						支付状态：${orderDetail.zhPaymentStatus }
					</td>
					<td>
						订单状态：${orderDetail.zhOrderStatus }
					</td>
				</tr>
				<tr>
					<td colspan="4">
						订单来源渠道：${orderDetail.zhProductChannel }
					</td>
				</tr>
				<tr>
					<td colspan="3">
						用户备注：${orderDetail.userMemo }
					</td>
				</tr>
			</table>

			<!--=============需审核的产品=============-->
			<div class="popbox">
				<strong>需审核产品</strong>
				<p class="paytime">
					游玩时间：
					<s:if test="orderDetail.orderType=='HOTEL'">
						<s:iterator value="orderDetail.ordOrderItemProds">
							<s:if test="subProductType=='SINGLE_ROOM'">${dateRange}</s:if>
							<s:else>${zhVisitTime}</s:else>
						</s:iterator>
					</s:if>
					<s:else>
						${orderDetail.zhVisitTime }
					</s:else>					
				</p>
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tbody>
						<tr bgcolor="#eeeeee">
							<td height="35" width="6%">
								子单号
							</td>
							<td width="15%">
								采购产品名称
							</td>
							<td width="5%">
								数量
							</td>
							<td width="8%">
								产品类型
							</td>
							<td width="10%">
								供应商
							</td>
							<td width="10%">
								游玩时间
							</td>
							<td width="20%">
								传真备注
							</td>
							<td width="10%">
								资源状态
							</td>
							<td width="10%">
								操作
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td height="30">
								${orderItemMeta.orderItemMetaId }
							</td>
							<td>
								<a href="javascript:openWin('/super_back/metas/view_index.zul?metaProductId=${orderItemMeta.metaProductId}&productType=${orderItemMeta.productType }',700,700)">${orderItemMeta.productName }</a>
							</td>
							<td>
								<s:if test="orderItemMeta.productType=='HOTEL'">
										${orderItemMeta.hotelQuantity }
									</s:if>
									<s:else>
										<s:property value="orderItemMeta.productQuantity*orderItemMeta.quantity"/>
								</s:else>
							</td>
							<td>
								${orderItemMeta.zhProductType }
							</td>
							<td>
								<a href="javascript:openWin('/pet_back/sup/detail.do?supplierId=${orderItemMeta.supplier.supplierId}',700,700)">${orderItemMeta.supplier.supplierName }</a>
							</td>
							<td>
								${orderItemMeta.strVisitTime }
							</td>
							<td>
								${orderItemMeta.faxMemo }
							</td>
							<td>
								${orderItemMeta.zhResourceStatus }
							</td>
							<td>
								<input type="button" value="沟通记录" name="editPassed">
								<s:if test="orderItemMeta.productType=='HOTEL' || (orderItemMeta.productType=='ROUTE' && orderItemMeta.subProductType=='FREENESS')">
								<s:if test="orderItemMeta.resourceStatus=='LACK'">
								<input type="button" value="关房" name="closeRoom" id="closeRoom">
								</s:if>
								</s:if>
							</td>
						</tr>
					</tbody>
				</table>
				<br/>
				<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
					width="100%" class="newfont05">
					<tbody>
						<tr bgcolor="#eeeeee">
							<td>
								日期
							</td>
							<td>
								购买时状态
							</td>
						</tr>
						<s:if test="orderDetail.orderType=='HOTEL'">
							<s:iterator value="orderDetail.ordOrderItemProds">
								<s:if test="subProductType=='SINGLE_ROOM'">
									<s:iterator value="orderItemMeta.allOrdOrderItemMetaTime">
										<tr bgcolor="#ffffff">
											<td>
												<s:property value="visitTime" />
											</td>
											<td>
												<s:property value="zhStockReduced" />
											</td>
										</tr>
									</s:iterator>
								</s:if>
							</s:iterator>
						</s:if>
				</table>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont04">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="30" width="8%">
								<br>
								类别
							</td>
							<td width="8%">
								姓名
							</td>
							<td width="6%">
								联系电话
							</td>
							<td width="9%">
								Email
							</td>
							<td width="10%">
								证件类型
							</td>
							<td width="10%">
								证件号码
							</td>
							<td width="5%">
								邮编
							</td>
							<td width="17%">
								地址
							</td>
							<td width="5%">
								座机号
							</td>
							<td width="5%">
								传真
							</td>
							<td width="11%">
								传真接收人
							</td>							
						</tr>
						<s:if test="orderDetail.contact!=null">
							<tr bgcolor="#ffffff" align="center">
								<td height="25">
									取票人/联系人
								</td>
								<td>
									${orderDetail.contact.name }
								</td>
								<td>
									${orderDetail.contact.mobile }
								</td>
								<td>
									${orderDetail.contact.email }
								</td>
								<td>
									${orderDetail.contact.certType }
								</td>
								<td>
									${orderDetail.contact.certNo }
								</td>
								<td>
									${orderDetail.contact.postcode }
								</td>
								<td>
									${orderDetail.contact.address }
								</td>
								<td>
									${orderDetail.contact.tel }
								</td>
								<td>
									${orderDetail.contact.fax }
								</td>
								<td>
									${orderDetail.contact.faxTo }
								</td>								
							</tr>							
						</s:if>
					</tbody>
				</table>

				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont04"
					style="margin-bottom: 20px;">
					<tbody>
						<tr bgcolor="#f4f4f4" align="center">
							<td height="30" width="8%">
								类别
							</td>
							<td width="8%">
								姓名
							</td>
							<td width="6%">
								联系电话
							</td>
							<td width="9%">
								Email
							</td>
							<td width="11%">
								证件类型
							</td>
							<td width="10%">
								证件号码
							</td>
							<td width="15%">
								备用联系方式
							</td>
							<td width="5%">
								邮编
							</td>
							<td width="20%">
								地址
							</td>
						</tr>
						<s:iterator id="person" value="orderDetail.personList">
							<s:if test="#person.personType == 'TRAVELLER'">
								<tr bgcolor="#ffffff" align="center">
									<td height="25">
										游客
									</td>
									<td>
										${person.name }
									</td>
									<td>
										${person.tel }
									</td>
									<td>
										${person.email }
									</td>
									<td>
										${person.zhCertType }
									</td>
									<td>
										${person.certNo }
									</td>
									<td>
										${person.memo }
									</td>
									<td>
										${person.postcode }
									</td>
									<td>
										${person.address }
									</td>									
								</tr>
								
							</s:if>							
						</s:iterator>
					</tbody>
				</table>
			</div>
			<!--popbox end-->
			<!--=============订单备注=============-->
			<div href="<%=basePath%>ord/loadMemos.do" id="memoDiv" class="popbox"
					param="{'orderId':'${orderDetail.orderId }'}"></div>
			<!--popbox end-->
			<p class="submitbtn2">
				<input type="button" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyDiv');">
			</p>
		</div>
		<!--=========================我的历史审核弹出层 end==============================-->
	<div class="orderpop" id="closeRoomDiv" style="display: none;">
	  <form id="closeRoomForm" method="post" name="closeRoomForm">
	  <input id="metaBranchId" name="metaBranchId" type="hidden" value="${orderItemMeta.metaBranchId}">
		<div class="orderpoptit">
			<strong>${orderItemMeta.productName}：</strong>
			<p class="inputbtn">
				<input type="button" class="button" value="关闭" onclick="javascript:closeRoomDiv()">
			</p>
		</div>
		<div class="orderpopmain">
			<table cellspacing="1" cellpadding="4" border="0" bgcolor="#666666"
				width="100%" class="newfont05">
				<tbody>
					<tr bgcolor="#eeeeee">
						<td><input type="checkbox" name="itemcheckall"
							id="itemcheckall" /></td>
						<td>
						<s:if test="orderItemMeta.subProductType=='SINGLE_ROOM'">入住日期</s:if>
						<s:else>游玩日期</s:else>
						</td>
					</tr>
					
					<s:if test="orderItemMeta.subProductType=='SINGLE_ROOM'">
						  <s:if test="orderItemMeta.allOrdOrderItemMetaTime!=null && orderItemMeta.allOrdOrderItemMetaTime.size()>0">
							<s:iterator value="orderItemMeta.allOrdOrderItemMetaTime">
							<s:set name="itemMetaVisitTime" ><s:date name="visitTime" format="yyyy-MM-dd" /></s:set>
								<tr bgcolor="#ffffff">
									<td>
									<s:if test='#itemMetaVisitTime>=currentDate'>
											<input type="checkbox" name="specDates"
												value="<s:date name="visitTime" format="yyyy-MM-dd"/>">
										</s:if>
										<s:else>
											<input type="checkbox" name="specDates"
												disabled="disabled"
												value="<s:date name="visitTime" format="yyyy-MM-dd"/>">
										</s:else></td>
									<td><s:date name="visitTime" format="yyyy-MM-dd" />
									</td>
								</tr>
							</s:iterator>
						 </s:if>
						<s:else>
						  <tr bgcolor="#ffffff">
								<td><s:if test='orderItemMeta.strVisitTime>=currentDate'>
										<input type="checkbox" name="specDates"
											value="<s:date name="orderItemMeta.visitTime" format="yyyy-MM-dd"/>">
									</s:if> <s:else>
										<input type="checkbox" name="specDates" disabled="disabled"
											value="<s:date name="orderItemMeta.visitTime" format="yyyy-MM-dd" />">
									</s:else></td>
								<td>${orderItemMeta.strVisitTime}</td>
							</tr>
						</s:else>
					</s:if>
					<s:if test="orderItemMeta.subProductType=='HOTEL_SUIT' ||orderItemMeta.subProductType=='FREENESS'">
						<tr bgcolor="#ffffff">
							<td><s:if test='orderItemMeta.strVisitTime>=currentDate'>
									<input type="checkbox" name="specDates"
										value="<s:date name="orderItemMeta.visitTime" format="yyyy-MM-dd"/>">
								</s:if> <s:else>
									<input type="checkbox" name="specDates" disabled="disabled"
										value="<s:date name="orderItemMeta.visitTime" format="yyyy-MM-dd" />">
								</s:else></td>
							<td>${orderItemMeta.strVisitTime}</td>
						</tr>
					</s:if>
					<tr bgcolor="#ffffff">
						<td colspan="2"><input type="button" value="确定"
							onclick="doCloseRoom()"/></td>
					</tr>
			</table>
		</div>
		<p class="submitbtn2">
			<input type="button" class="button" value="关闭" onclick="javascript:closeRoomDiv()">
		</p>
		 </form>
	 </div>
	<div id="itembg" class="bg" style="display: none;"></div>
	</body>
</html>
