<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.memoHeader{
    border-bottom: 2px solid #B8C9D6;
    color: #174B73;
    display: block;
    margin-bottom: 10px;
    padding: 0 0 2px 10px;
}
#orderMemoList {
	border-collapse:separate;
    border-spacing: 1px;
    background-color: #B8C9D6;
    width: 90%;
    font-size: 12px;
}
</style>
<ul class="dingdan_cl_list">
	<li><span style="font-weight:bold;margin-bottom: 10px;padding: 0 0 2px 10px;">用户要求: </span>
			<p class="width_800">
            	<s:if test="ebkOrderDetail.userMemo != null">${ebkOrderDetail.userMemo }</s:if>
            	<s:else>无</s:else>
			</p></li>
	<li>	
	<strong class="memoHeader">订单备注: <a href="javascript:void(0);" id="memolist-ctr">展示备注</a> </strong> 
	<table cellspacing="1" cellpadding="4" border="0" id="orderMemoTable" style="display: none;">
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
			</tr>
			<s:iterator id="memo" value="memoList">
				<tr bgcolor="#ffffff" align="center">
					<td height="25" >
						${memo.zhType } 
						<s:if test="userMemo=='true'"><b style="color: #E40000;">(在EBK中显示)</b></s:if>
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
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 沟通 -->
	<s:if test="ebkOrderDetail.followUser==currentUser">
		<li id="memo_tip" style="display: none;">
			<b style="color:red">操作提示：请与酒店电话确认是否能满足此备注要求，将沟通结果填在下方输入框中</b>
		</li>
		<li id="memo_result" style="display: none;">
			<p>沟通结果:</p>
			<p>
				<textarea id="faxMemo" style="width: 434px; height: 84px;"></textarea>
			</p>
		</li>
		<li id="memo_submit" style="margin-left:50px;margin-top:5px;display: none;">
			<input type="button" align="left" style="width: 100;margin-right: 15px;" value="酒店同意"  onclick="followOrderMemoSuccess()"/>
			<input type="button" align="left" style="width: 100" value="酒店驳回"  onclick="followOrderMemoReject()"/>
		</li>
	</s:if>
	</li>
</ul>
<!-- /沟通 -->
<script type="text/javascript">
function followOrderMemoSuccess() {
	if($("#faxMemo").val() == "") {
		alert("请输入沟通结果，并且将会记录到传真备注中。");
		return false;
	}
	$.ajax({
		type : "POST",
			url : "${basePath}/ebooking/order/followOrderMemoSuccess.do",
		data : {
			ebkTaskId : $("#ebkTaskId").val(),
			faxMemo : $("#faxMemo").val()
		},
		success : successReturn,
		failure : function(data) {
			alert(data);
		}
	});
}
function followOrderMemoReject() {
	$.ajax({
		type : "POST",
			url : "${basePath}/ebooking/order/followOrderMemoReject.do",
		data : {
			ebkTaskId : $("#ebkTaskId").val(),
			faxMemo : $("#faxMemo").val()
		},
		success : successReturn,
		failure : function(data) {
			alert(data);
		}
	});
}
$(function(){
	//控制显示
	$("#memolist-ctr").click(function(){
		if($(this).html()=="展示备注"){
			$("#orderMemoTable").show();
			$("#memo_tip").show();
			$("#memo_result").show();
			$("#memo_submit").show();
			$(this).html("隐藏备注");
		}else{
			$("#orderMemoTable").hide();
			$("#memo_tip").hide();
			$("#memo_result").hide();
			$("#memo_submit").hide();
			$(this).html("展示备注");
		}
	});
<s:if test="ebkOrderDetail.orderUpdate == 'true'">
	$("#memolist-ctr").click();
</s:if>
});
</script>