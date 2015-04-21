<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<form id="myForm" action="/super_back//phoneOrder/makeorder.do" method="post">
<input type="hidden" name="orderId" value="${orderId}" />
<input type="hidden" name="productId" value=""/>
<input type="hidden" name="visitDate" value="<s:date name="trainVisitTime" format="yyyy-MM-dd"/>"/>
<input type="hidden" name="branch" value=""/>
<input type="hidden" name="testOrder" value="false"/>
<input type="hidden" name="paramsStr"/>
</form>
<table width="100%">
	<s:if test="productList!= null && !productList.isEmpty()">
		<s:iterator value="productList" id="product">
			<tr class="newTableTit">
				<td width="5%">
					<s:property value="productId" />
				</td>
				<td>
					<a href="javascript:void(0)" class="showPlaceInfo"
						productId="<s:property value='productId' />"><s:property
							value="productName" /> </a> &nbsp;&nbsp;&nbsp;(
					<s:property value="zhFilialeName" />
					)&nbsp;&nbsp;&nbsp;
					<a class="showImportantTips" href="javascript:void(0)"
						productId="<s:property value='productId' />"><font
						color="red">产品信息</font> </a>&nbsp;&nbsp;&nbsp;<%--
					<a href="javascript:void(0)" class="showCouponInfo"
						productId="<s:property value='productId' />">优惠活动 </a> --%>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table width="100%" class="newTable">
						<tr style="font-weight: bold;">
							<td width="18%">
								票种
							</td>
							<td width="8%">
								最小/最大预订量
							</td>
							<td width="6%">
								驴妈妈价
							</td>
							<td width="8%">
							</td>
						</tr>
						<s:iterator value="prodBranchList">
							<tr>
								<td>
									${branchName}<s:if test="hasBusinessCoupon==true"><font color="red">(惠)</font></s:if> 
								</td>
								<td>
									${minimum }/${maximum }
								</td>
								<td style="color: red;">
									￥${sellPriceYuan}
								</td>
								<td>
									<input type="button"
										onclick="quickBooker({id:${productId},mainProdBranchId: ${prodBranchId},testOrder: ${testOrder}, paramsStr: encodeURI('${paramsStr }')})"
										value="预订" class="button" />
								</td>
							</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr style="font-weight: bold;">
			<td>
				无搜索结果
			</td>
		</tr>
	</s:else>
</table>