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
		var basePath = '<%=basePath%>';
		function doMemoOperator(url, data) {
			$.ajax({type:"POST", url:basePath + url, data:data, success:function (result) {
				var res = eval(result);
				if (res) {
					alert("\u64cd\u4f5c\u6210\u529f!");
					$("#memoDiv").reload({"orderId":orderId});
				} else {
					alert("\u64cd\u4f5c\u5931\u8d25!");
				}
			}});
		}
		//新增备注
		function addOrUpdateMemo() {
			var data = $("#memoForm").getForm({prefix:""});
			doMemoOperator("ord/saveOrUpdateMemo.do", data);
		}
		//修改填充数据
		function doGetMemo(memoId) {
			$("#memoDiv").reload({'orderId':orderId, 'orderMemo.memoId':memoId});
		}
		</script>
	</head>

	<body>
		<strong>订单备注</strong>
		<table style="font-size: 12px" cellspacing="1" cellpadding="4"
			border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
			<tbody>
				<tr bgcolor="#f4f4f4" align="center">
					<td height="30" width="20%">
						备注类别
					</td>
					<td width="50%">
						内容
					</td>
					<td width="10%">
						维护人
					</td>
					<td width="10%">
						创建时间
					</td>
					<!-- td width="10%">
						操作
					</td-->
				</tr>
				<s:iterator id="memo" value="orderMemos">
					<tr bgcolor="#ffffff" align="center">
						<td height="25" >
							${memo.zhType } 
							<s:if test="userMemo=='true'"><b style="color: #E40000;">(用户特殊要求)</b></s:if>
						</td>
						<td>
							${memo.content }
						</td>
						<td>
							${memo.operatorName }
						</td>
						<td>
							${memo.zhCreateTime }
						</td>
						<!-- td>
							张伟与客服决定订单备注只能添加，不得修改删除。s:if test="#memo.operatorName == currentUsr"
								<a href="javascript:doGetMemo('${memo.memoId }');">修改</a>
								<a href="javascript:doMemoOperator('ord/deleteMemo.do', {'orderMemo.memoId': ${memo.memoId }});">删除</a>
							/s:if>
						</td-->
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<div class="orderremark">
			<form id="memoForm" method="post">
				<input type="hidden" id="orderMemo.orderId" name="orderMemo.orderId"
					value="${orderId }" />
				<input type="hidden" id="orderMemo.memoId" name="orderMemo.memoId"
					value="${orderMemo.memoId }" />
				<p>
					备注类别：
					<select name="orderMemo.type">
						<s:iterator id="codeItem" value="memoTypes">
							<s:if
								test="orderMemo!=null&&orderMemo.type!=null&&orderMemo.type==#codeItem.code">
								<option value="${codeItem.code }" selected="selected">
									${codeItem.name }
								</option>
							</s:if>
							<s:else>
								<option value="${codeItem.code }">
									${codeItem.name }
								</option>
							</s:else>
						</s:iterator>
					</select>
				</p>
				<p>
					备注内容：
					<textarea name="orderMemo.content" cols="60" rows="5">${orderMemo.content }</textarea>
					<input type="checkbox" name="orderMemo.userMemo"  value="true"  />
					<span style="color: #E40000;">用户特殊要求审核</span>
					<a href="javascript:addOrUpdateMemo();">保存</a>
				</p>
			</form>
		</div>
	</body>
</html>
