<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>	
<s:iterator value="ebkFaxTaskList" var="item">
<s:if test="innerListFlag == 'true'">
					<tr id="inner_${item.ebkFaxTaskId }" style="background-color: #eee;" father="${fatherEbkFaxTaskId }">
</s:if>
<s:else>
					<tr id="father_${item.ebkFaxTaskId }">
</s:else>
						<td>
						<s:if test="innerListFlag != 'true'">
							<input type="checkbox" name="ebkFaxTaskId" value="${item.ebkFaxTaskId}"/>
						</s:if>
						</td>
						<td style="text-align: center;">${item.ebkFaxTaskId}
						<s:if test="ebkCertificate.testOrder == 'true'"><div style="color: red;">测试单</div></s:if>
						<s:if test="isNoReplied && innerListFlag != 'true' && ebkCertificate.oldCertificateId != null">
							<br/>
							<div style="cursor: pointer;" onclick="showNextCert(this,'${item.ebkFaxTaskId}')">+</div>
						</s:if>
						</td>
						<td>${item.ebkCertificate.zhEbkCertificateType}</td>
						<td><s:date name="#item.planTime" format="yyyy-MM-dd HH:mm"/></td>
						<td>
						    <s:if test="'false'.equals(#item.autoSend)">
							  非自动
							</s:if>
							<s:else>
							  自动
							</s:else>
						</td>
						<td><a href="javascript:void(0)" data="${item.ebkCertificate.supplierId}" class="showDetail">${item.ebkCertificate.supplierName }</a></td>
						<td><a href="#showTarget" url="${basePath}targets/certificatetarget/detailcertificatetarget.zul?targetId=${item.ebkCertificate.targetId}" class="showTarget">${item.ebkCertificate.targetName }</a></td>
						<td>${item.ebkCertificate.zhProductType }</td>
						<td class="noPadding">
						  <table class="p_label p_label_noP" width="100%">
						  <tr>
						    <th nowrap="nowrap">订单号</th>
						    <th></th>								
						    <th nowrap="nowrap">出游日期</th>
						    <th nowrap="nowrap">操作</th>
						  </tr>
						  <s:iterator value="#item.ebkCertificateItemOrderList" var="cerItem" status="ci">
						    <tr>
						      <td>
						      	<a href="<%=basePath%>ord/order_monitor_list!doOrderQuery.do?pageType=single&orderId=${cerItem.orderId}"
						      		target="_blank">
						      		${cerItem.orderId }
						      	</a>
						      </td>
							  <td>
								  <table class="p_label" width="100%">
								  <tr>
								    <th nowrap="nowrap">销售产品名称</th>
								    <th nowrap="nowrap">采购产品名称</th>
								    <th nowrap="nowrap">订购</th>
								  </tr>
								  <s:iterator value="#cerItem.ebkCertificateItemList" var="inCerItem" status="cii">
							         <tr>
									     <td>${inCerItem.productName }</td>
									     <td>${inCerItem.metaProductName }</td>
									     <td align="center">${inCerItem.quantity }</td>
								      </tr>
								  </s:iterator>
								  </table>
							  </td>
						      <td><s:date name="#cerItem.visitTime" format="yyyy-MM-dd"/></td>
						      <td><a href="javascript:void(0);" onclick="modifyMemo(${cerItem.orderId })">修改传真备注</a></td>
						    </tr>
						  </s:iterator>
						  </table>
						</td>
						<td>
						<s:if test="ebkCertificate.valid != 'false'">
							<a href="javascript:showUpdateFax(${item.ebkCertificateId },${item.ebkFaxTaskId});">内部备注</a> 
						</s:if>
							<span id="fax_inner_memo_${item.ebkFaxTaskId}">${item.ebkCertificate.memo}</span>
						</td>
						<s:if test="!isNoSend">
						<td><a href="javascript:void(0)" class="showFaxSend" ebkFaxTaskId="${item.ebkFaxTaskId}" ebkCertificateId="${item.ebkCertificate.ebkCertificateId }">${item.sendCount}</a></td>
						<td>${item.zhSendStatus }</td>
						<s:if test="ebkFaxTaskTab=='ALLFAX'"><td>${item.zhFaxSendRecvStatus }</td></s:if>
						</s:if>
						<td>
						<s:if test="ebkCertificate.userMemoStatus == 'false'">
							未审核
						</s:if>
						</td>
						<td nowrap="nowrap">
							<a target="_blank" href="${basePath}fax/showFax.do?faxTaskId=${item.ebkFaxTaskId}">查看传真</a><br>
							<s:if test="isNoSend&&sendStatus==0">
								<s:if test="'false'.equals(#item.disableSend)">
									<a href="javascript:void(0);" onclick="updateDisableSend(${item.ebkFaxTaskId},'true');">不发送传真</a><br />
								</s:if>
								<s:if test="'false'.equals(#item.autoSend)">
								   <a href="javascript:void(0);" onclick="sendFaxOver(${item.ebkFaxTaskId});">手工发送完成</a>&nbsp;&nbsp;
								   <a href="javascript:void(0);" onclick="updateAutoSend(${item.ebkFaxTaskId},'true');">改为自动发送</a>
								</s:if>
								<s:else>
								   <a href="javascript:void(0);" onclick="updateAutoSend(${item.ebkFaxTaskId},'false');">改为非自动发送</a>
								</s:else>
							 </s:if>
							 <s:elseif test="isNoReplied">
							    <a  href="javascript:void(0)" class="showFaxReceiveFile" data="${item.ebkCertificate.ebkCertificateId }">确认回传</a>
							    <a param="{'parentType':'EBK_FAX_TASK','parentId':${item.ebkFaxTaskId}}" class="showLogDialog" href="#log">查看操作日志</a>
							 </s:elseif>
							 <s:elseif test="isAllFax">
							    <s:if test="#item.hasRecv"><a href="javascript:void(0);" class="showFaxReceiveFile" data="${item.ebkCertificate.ebkCertificateId }">查看回传件</a></s:if>
							    <a param="{'parentType':'EBK_FAX_TASK','parentId':${item.ebkFaxTaskId}}" class="showLogDialog" href="#log">查看操作日志</a>
							 </s:elseif>
						</td>
					</tr>
</s:iterator>