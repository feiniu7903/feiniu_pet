<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<body>
		<!-- 门票 -->
		<s:if test="productType == 'TICKET'">
			<table width="100%">
				<thead>
					<tr bgcolor="#eeeeee">
						<th height="35" width="5%">
							ID
						</th>
						<th width="25%">
							产品名称
						</th>
						<th width="8%">
							最小/最大预订量
						</th>
						<th width="6%">
							市场价
						</th>
						<th width="6%">
							驴妈妈价
						</th>
						<th width="8%">
						</th>
					</tr>
				</thead>
				<s:iterator value="productList">
					<tr>
						<td>
							${productId }
						</td>
						<td>
							${productName }
						</td>
						<td>
							${minimum }/${maximum }
						</td>
						<td>
							${marketPriceYuan }
						</td>
						<td>
							${sellPriceYuan}
						</td>
						<td>
							<input type="button" value="预订" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</s:if>
		<!-- 酒店 -->
		<s:elseif test="productType == 'HOTEL'">
			<table width="100%">
				<thead>
					<tr bgcolor="#eeeeee">
						<th height="35" width="5%">
							房型
						</th>
						<th width="25%">
							可住人数
						</th>
						<th width="6%">
							市场价
						</th>
						<th width="6%">
							驴妈妈价
						</th>
						<th width="5%">
							早餐
						</th>
						<th width="5%">
							宽带
						</th>
						<th width="8%">
						</th>
					</tr>
				</thead>
			</table>
		</s:elseif>
		<s:elseif test="productType == 'ROUTE'">
			<!-- 线路 -->
			<table width="100%">
				<thead>
					<tr bgcolor="#eeeeee">
						<th height="35" width="5%">
							ID
						</th>
						<th width="25%">
							产品名称
						</th>
						<th width="8%">
							产品类型
						</th>
						<th width="10%">
							游玩日期
						</th>
						<th width="6%">
							游玩天数
						</th>
						<th width="8%">
						</th>
					</tr>
				</thead>
				<tr>
					<td>
						32123
					</td>
					<td>
						心驴行XXXXXXXXXXXXXXXXXXXXXXXX
					</td>
					<td>
						长途跟团游
					</td>
					<td>
						12-23、12-28、12-29、01-05
					</td>
					<td>
						4
					</td>
					<td>
						<input type="button" value="预订" />
					</td>
				</tr>
			</table>
		</s:elseif>
	</body>
</html>
