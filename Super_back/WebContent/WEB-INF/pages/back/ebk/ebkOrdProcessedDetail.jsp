<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>订单处理后台_Ebooking待处理订单监控</title>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<!-- LVMAMA -->
<script type="text/javascript" src="${basePath}js/base/lvmama_common.js"></script>
<script type="text/javascript" src="${basePath}js/base/lvmama_dialog.js"></script>
<!-- url -->
<link rel="stylesheet" type="text/css"
	href="http://pic.lvmama.com/styles/ebooking/base.css" />
<!-- css -->
<link rel="stylesheet" type="text/css" href="${basePath }/themes/base/jquery.ui.all.css" >
<link rel="stylesheet" type="text/css" href="${basePath }/themes/ebk/admin.css" >
</head>
<body>
<div>
	<!-- 订单详情 -->
	<div class="dingdan_cl">
		<h3>订单详情</h3>
	</div>
	<div class="dingdan_cl">

		<ul class="dingdan_cl_list">
			<li>
	            <span class="cl_p_1">订单号：</span>
	            <p class="cl_p_1">${ebkTask.orderId }<s:if test="ebkTask.resourceConfirm == 'false'"><span class="orange">(保留房)</span></s:if>
	            　　　　订单状态：${ebkTask.zhOrderStatus}　　　　类型：${ebkTask.ebkCertificate.zhEbkCertificateType }</p>
	        </li>
			<li><span>产品名称：</span>
				<p class="orange">${ebkTask.ebkCertificate.mainMetaProductName }</p>
			</li>
			<li><span>酒店电话：</span>
				<p class="orange">${ebkTask.ebkCertificate.toTel}</p>
			</li>
			<li>            
			<span>客人姓名：</span>
            <s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.certificate.travellerList" id="person" status="per">
            	<p><s:if test="#per.index!=0">、</s:if>${person.name }</p> 
            </s:iterator>
			</li>
			<li><span>住店日期：</span>
				<p>
					<font class="orange"><s:date name="ebkTask.ebkCertificate.visitTime" format="yyyy-MM-dd"/></font> 
	            	至 <font class="orange"><s:date name="ebkTask.ebkCertificate.leaveTime" format="yyyy-MM-dd"/></font> 
	            	<s:iterator var="item" value="ebkTask.ebkCertificate.ebkCertificateItemList">
						（共${item.nights }晚）
                    </s:iterator>
            	</p>
			</li>
			<li>            
				<span>预订房型：</span>   
	            <p>
	            <s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.entity.subProductType=='SINGLE_ROOM'">     
		            <s:iterator id="item" value="ebkTask.ebkCertificate.ebkCertificateData.items" status="i">
				             <font class="orange">${item.baseInfo.metaBranchName }、<s:property value="baseInfo.nights.get(0).quantity"/>间</font> /
				             <s:property value="baseInfo.nights.size()"/>晚
				             <s:if test="breakfastCount != null && breakfastCount > 0"> 
				                                      、共 <font class="orange"><s:property value="breakfastCount"/> 份早餐</font></s:if> 
				               <s:if test="ebkTask.resourceConfirm == 'false'"><font class="orange">(保留房)</font></s:if><br>
		            </s:iterator>
		       	 </s:if>
		       	 <s:else>
		       	  <s:iterator id="item" value="ebkTask.ebkCertificate.ebkCertificateData.items">
		             <font class="orange">${item.baseInfo.metaBranchName }、${item.baseInfo.quantity }间</font> /${item.baseInfo.nights}晚
		             <s:if test="baseInfo.breakfastCount != null"> 
		                                      、共 <font class="orange">${item.baseInfo.breakfastCount} 份早餐</font></s:if> 
		               <s:if test="ebkTask.resourceConfirm == 'false'"><font class="orange">(保留房)</font></s:if> 
		             <br>
		             </s:iterator>
	            </s:else>
	            </p>
			</li>
			<li><span>付款方式：</span>
				<p>${ebkTask.ebkCertificate.zhPaymentTarget }</p> 
			</li>
			<li>
				<span>
		            <s:if test="ebkTask.totalSuggestionPrice != null">
		           		 建议售价：
		            </s:if>
		            <s:else>
		          		 结算价：
		            </s:else>
		        </span>
				<p>
					<s:iterator value="time" var="t">
						<s:date name="visitTime" format="yyyy-MM-dd" /> 
		            	RMB <font class="orange">
			            	<s:if test="ebkTask.totalSuggestionPrice != null">
			            		 ${suggestPriceYuan*ebkTask.roomQuantity }  
			            	</s:if> 
			            	<s:else>
			            		${settlementPriceYuan*ebkTask.roomQuantity }  
							</s:else>
						</font> 元<br>
					</s:iterator>
					共计 RMB <font class="orange bold"><s:property value="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.totalSettlementPrice"/></font> 元（含服务费）
				</p>
			</li>
			<s:if test="ebkTask.paymentStatus == 'PAYED'">
				<li><span>支付状态：</span>
					<p>
						<font class="orange bold">已支付</font>
					</p>
				</li>
			</s:if>
			<li><span>特殊要求：</span>
				<p class="width_800">
	            	<s:if test="ebkTask.ebkCertificate.allUserMemo !=''">
	            		${ebkTask.ebkCertificate.allUserMemo }
	            	</s:if>
	            	<s:else>无</s:else>
				</p></li>
			</ul>
	</div>
	<div class="dingdan_cl">
		<ul class="dingdan_cl_list">
			<li><span>订单提交时间：</span>
				<p>${ebkTask.zhOrderCreateTime}</p>
			</li>
			<li><span>目前耗时：</span>
				<p class="orange">${ebkTask.consumeTime}</p>
			</li>
			<li><span>酒店确认结果：</span>
				<p>${ebkTask.ebkCertificate.zhCertificateStatus}</p>
			</li>
			<s:if test="ebkTask.status =='REJECT'">
			<li><span>订单提示：</span>
				<p>请联系产品经理与供应商协调处理此订单，待其告知结果后，再做如下操作</p></li>
			</s:if>	 
		</ul>
	</div>

	<!-- 订单备注 -->
	<div class="dingdan_cl">
		<jsp:include page="/WEB-INF/pages/back/ebk/ebkOrdMemoList.jsp" />
	</div>
	<!-- /订单备注 -->

	<div class="dingdan_cl">
		<ul class="dingdan_cl_list">
			<li><span>跟进人：</span> <span>${ebkTask.followUser}
			</span></li>
			<li><span>最后跟进时间：</span> <span>${ebkTask.zhFollowTime}
			</span></li>
			<li style="text-align: center;"><input type="button" align="right" style="width: 100" value="关闭"
				onclick="parent.window.closePopupWin();"
				style="color:red;" /></li>
		</ul>
	</div>
	
	<!-- 操作日志 -->
	<div class="dingdan_cl">
		<strong>操作日志</strong>
		<table class="newfont03">
			<tr align="center">
				<td height="30">日志名称</td>
				<td style="width: 340px" nowrap="nowrap">内容</td>
				<td>操作人</td>
				<td>创建时间</td>
				<td>备注</td>
			</tr>
			<s:iterator var="log" value="logList" status="i">
				<tr>
					<td align="center"><p>${log.logName }</p></td>
					<td align="center"><p>${log.content }</p></td>
					<td align="center">${log.operatorName }</td>
					<td align="center"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center">${log.memo }</td>
				</tr>
			</s:iterator>
		</table>
	</div>
</div>
</body>
</html>