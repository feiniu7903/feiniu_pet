<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <table width="100%" class="datatable" style="margin-top: 10px;margin-left: 5px;margin-bottom: 10px;">
					<tbody>
						<tr >
							<td >
								交易流水号
							</td>
							<td >
								支付网关
							</td>
							<td>
								交易金额
							</td>
							<td >
								支付状态
							</td>
							<td>
								支付信息
							</td>
							<td >
								创建时间
							</td>
						</tr>
						<s:iterator value="ordPaymentList">
							<tr>
								<td>
									${serial }
								</td>
								<td>
									${payWayZh}
								</td>
								<td>
									${amountYuan}
								</td>
								<td>
									${statusZh }
								</td>
								<td>
									${callbackInfo}
								</td>
								<td>
									<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
  </body>
</html>
