<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="orderpopmain">
	<div class="popbox">
		<strong>售后处理内容详情</strong>
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
					<td height="35" width="10%">
						申请售后服务
					</td>
					<td height="35">
						申请内容
					</td>
					<td width="12%">
						申请人
					</td>
					<td width="20%">
						申请时间
					</td>
				</tr>
				<s:iterator value="ordSaleServiceList">
					<tr bgcolor="#ffffff">
						<td height="30">
							${serviceTypeName}
						</td>
						<td>
							${applyContent}
						</td>
						<td>
							${operatorName}
						</td>
						<td>
							<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
	</div>
</div>
