<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>对账接口信息</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>

<script type="text/javascript">
$(function(){
	$("a.gotoEdit").click(function(){
		var glInterfaceId=$(this).attr("data");
		$("#gotoEdit").showWindow({
			url:basePath+"/recon/gotoEditFinInterface.do",
			data:{"glInterfaceId":glInterfaceId}});
	});
	$("#btnCountOrder").click(function(){
		if(!check_parmeters()) return false;
		var data = {};
		 $("#frm1").find("[name]").each(function(){
			 $(data).data($(this).attr("name"),$(this).val());
		 });
		 $(data).data("page",1);
		window.open("${basePath}/recon/queryOrderAccount.do?"+jQuery.param(data));
	});
	$("#buttonOrderMonitor").click(function(){
		if(!check_parmeters()) return false;
		if($("#beginTime").val()=='' && $("#endTime").val()=='' && $(":input[name=tickedNo]").val()==''){
			alert("请选择一个查询条件");
			return false;
		}
		if($(":input[name=tickedNo]").val()!='' && !/^\d+$/.test($(":input[name=tickedNo]").val())){
			alert("请输入一个订单号");
			return false;
		}
		var data = {};
		 $("#frm1").find("[name]").each(function(){
			 $(data).data($(this).attr("name"),$(this).val());
		 });
		 $(data).data("page",1);
		window.open("${basePath}/recon/selectFinanceOrderMonitor.do?"+jQuery.param(data));
	});
	$("#dateIntervals").change(function(){set_fin_date();});
});

function isDate(s) 
{ 
	var patrn=/^[1-9]{1}[0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/; 
	if (!patrn.exec(s)) {
		return false;
	}
	return true;
} 

function check_parmeters(){
	var bTime = $("#beginTime").val();
	var eTime = $("#endTime").val();
	
	if (bTime != "") {
		if (!isDate(bTime)) {
			alert("开始日期格式错误！");
			return false;
		}
	}
	if (eTime != "") {
		if (!isDate(eTime)) {
			alert("结束日期格式错误！");
			return false;
		}
	}
	if($("select[name=accountType]").val()=='' && bTime=='' && eTime=='' && $(":input[name=tickedNo]").val()==''){
		alert("请选择一个查询条件");
		return false;
	}else if(eTime!="" && bTime=="" && ($("select[name=accountType]").val()=='' || $(":input[name=tickedNo]").val()=='')){
		alert("请选择开始日期");
		return false;
	}else if(eTime=="" && bTime!="" && ($("select[name=accountType]").val()=='' || $(":input[name=tickedNo]").val()=='')){
		alert("请选择结束日期");
		return false;
	}
	if ($("#beginTime").val()!='' && $("#endTime").val()!=''){
		if ($("#beginTime").val()>$("#endTime").val()){
			alert("开始日期不能小于结束日期！");
			return false;
		}else if((((new Date(Date.parse(eTime.replace(/-/g,   "/")))).getTime() - (new Date(Date.parse(bTime.replace(/-/g,   "/")))).getTime())/ (24 * 60 * 60 * 1000))>92 && 
				($("select[name=accountType]").val()=='' || $(":input[name=tickedNo]").val()=='')){
			alert("日期跨度过长，请选择三个月以内的数据");
			return false;
		}
	}
	return true;
}
function check() {
	if(check_parmeters())
	$("#frm1").attr("action", "${basePath}/recon/queryFinInterface.do").submit();
}
function send() {
	if(confirm("确定要对全部入账失败的数据进行操作吗？"))
	 {
		$("#btnSend").attr("disabled", true);
		$.ajax({ 
			type: "post", 
			url: "${basePath}/recon/send.do",
			success: function(msg){
				alert("手动入账操作已执行！");
				$("#btnSend").attr("disabled", false);
				}
		});
	 }
	
}
Date.prototype.format = function(format)
{
 var o = {
 "M+" : this.getMonth()+1, //month
 "d+" : this.getDate(),    //day
 "h+" : this.getHours(),   //hour
 "m+" : this.getMinutes(), //minute
 "s+" : this.getSeconds(), //second
 "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 "S" : this.getMilliseconds() //millisecond
 }
 if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
 (this.getFullYear()+"").substr(4 - RegExp.$1.length));
 for(var k in o)if(new RegExp("("+ k +")").test(format))
 format = format.replace(RegExp.$1,
 RegExp.$1.length==1 ? o[k] :
 ("00"+ o[k]).substr((""+ o[k]).length));
 return format;
}
function set_fin_date(){
	 var dateIntervals = $("#dateIntervals").val();
	 if(dateIntervals!='' && dateIntervals!=null){
	 	var new_date = new Date(Date.parse(dateIntervals.replace("-","/")+"/01"));
	 	new_date.setMonth(new_date.getMonth()+1);
	 	$(":input[name=beginTime]").val(dateIntervals+"-01");
	 	$(":input[name=endTime]").val((new Date(new_date.getTime()-1000*60*60*24)).format('yyyy-MM-dd'));
	 }
}
function download() {
	if(check_parmeters())
	$("#frm1").attr("action", "${basePath}/recon/downloadFinInterface.do").submit();
}
function countData(){
	if(check_parmeters())
	$("#frm1").attr("action", "${basePath}/recon/queryFinInterfaceCount.do").submit();
}
function delete_finance_by_id(glInterfaceId){
	if(glInterfaceId!=null && glInterfaceId!=''){
		if(confirm("是否将此入账数据删除?")){
			$(":input[name=glInterfaceId]").val(glInterfaceId);
			$("#frm1").attr("action", "${basePath}/recon/deleteFinInterfaceById.do").submit();
		}else return false;
	}
}
</script>
</head>
<body>
<div><form id="frm1" action="${basePath}/recon/queryFinInterface.do" method="post">
	<input type="hidden" name="glInterfaceId"/>
	<table border="0" cellspacing="0" cellpadding="0" class="search_table">
			<tr>    
				<td>
				日期：
				<input type="text" name="beginTime" id="beginTime" class="date" value="<s:date name="beginTime" format="yyyy-MM-dd"/>" />
				~ <input type="text" name="endTime" id="endTime"  class="date" value="<s:date name="endTime" format="yyyy-MM-dd"/>" />
				&nbsp;
				<select  style="height: 22px;width: 185px;" id="dateIntervals">
					<option>请选择</option>
					<s:iterator var="obj" value="dateIntervals">
						<option value="${obj}">${obj}</option>
					</s:iterator>
				</select>
				</td>
				<td>
				状态：
				<s:select list="#{'':'请选择','success':'入账成功','fail':'入账失败','unsend':'未发送'}"
					name="finStatus" style="height: 22px;width: 185px;">
				</s:select>
				</td>
				<td>
				入账类型：
				<select name="accountType" style="height: 22px;width: 185px;">
					<option value="">请选择</option>
					<s:iterator var="obj" value="accountTypes">
						<option value="${obj.code}" <s:if test="#obj.code==accountType">selected="selected"</s:if>>${obj.cnName}</option>
					</s:iterator>
				</select>
				</td>
				<td>票号：
				<input type="text" value="${tickedNo}" name="tickedNo" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<input type="button" value="　查 询　" class="button" name="btnQuery" onclick="check();"/>&nbsp;&nbsp;
				<input type="button" value="手动入账 " class="button" id="btnSend" name="btnSend" onclick="send();"/>&nbsp;&nbsp;
				<input type="button" value="导出数据 " class="button" id="btndownload" name="btndownload" onclick="download();"/>&nbsp;&nbsp;
				<input type="button" value="统计数据 " class="button" id="btnCount" name="btnCount" onclick="countData();"/>
				<input type="button" value="统计订单入账数据" class="button" id="btnCountOrder" name="btnCountOrder"/>
				<input type="button" value="订单入账监控" class="button" id="buttonOrderMonitor" name="buttonOrderMonitor"/>
				</td>
			</tr>
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table" id="query_fin_gl_interface_id">
		<tr>
			<th width="20" align="center">票号</th>
			<th width="20" align="center">批量合并号 </th>
			<th width="20" align="center">制单日期(支付)</th>
			<th width="20" align="center">摘要</th>
			<th width="20" align="center">借方科目编码(支付平台)</th>
			<th width="20" align="center">借方金额</th>
			<th width="20" align="center">贷方科目编码</th>
			<th width="20" align="center">贷方金额</th>
			<th width="20" align="center">产品编码</th>
			<th width="30" align="center">产品名称</th>
			<th width="30" align="center">供应商ID</th>
			<th width="20" align="center">自定义项1(订单号)</th>
			<th width="100" align="center">自定义项10(团号)</th>
			<th width="20" align="center">自定义项4(游玩时间)</th>
			<th width="20" align="center">状态</th>
			<th width="20" align="center">备注</th>
			<th width="10" align="center">凭证类型</th>
			<th width="10" align="center">帐套号</th>
			<th width="15" align="center">U8凭证号</th>
			<th width="30" align="center">操作</th>
		</tr>
		<s:iterator value="pagination.items" var="obj">			
		<tr>
			<td>${obj.tickedNo}</td>
			<td>${obj.batchNo}</td>
			<td>
				<s:if test="#obj.makeBillTime!=null">
					<s:date name="#obj.makeBillTime" format="yyyy-MM-dd"/>
				</s:if>
			</td>
			<td>${obj.summary}</td>
			<td>${obj.borrowerSubjectCode}</td>
			<td>${obj.borrowerAmountFmt}</td>
			<td>${obj.lenderSubjectCode}</td>
			<td>
				<s:if test="#obj.lenderSubjectCode!=null">
					${obj.lenderAmountFmt}
				</s:if>
			</td>
			<td>${obj.productCode}</td>
			<td>${obj.productName}</td>
			<td>${obj.supplierCode}</td>
			<td>${obj.ext1}</td>
			<td>${obj.ext10}</td>
			<td>${obj.ext4}</td>
			<td>
				<s:if test="#obj.receivablesStatus!=null">
					<s:if test="#obj.receivablesStatus=='success'">
						入账成功
					</s:if>
					<s:else>
						入账失败
					</s:else>
				</s:if>
				<s:else>
					未发送
				</s:else>
			</td>
			<td>${obj.receivablesResult}</td>
			<td>${obj.proofType}</td>
			<td>${obj.accountBookId}</td>
			<td>${obj.inoId}</td>
			<td>
				<s:if test="#obj.receivablesStatus!=null">
					<s:if test="#obj.receivablesStatus!='success'">
						<a href="javascript:void(0)" data="${obj.glInterfaceId}" class="gotoEdit">修改</a>
						<a href="javascript:void(0)" onclick="delete_finance_by_id('${obj.glInterfaceId}');">删除</a>
					</s:if>
				</s:if>
				<s:if test="#obj.receivablesStatus==null">
					<a href="javascript:void(0)" onClick="delete_finance_by_id('${obj.glInterfaceId}');">删除</a>
				</s:if>
				<a target="_blank" href="findFinReconAndBizItemById.do?glInterfaceId=${obj.glInterfaceId}"  data="${obj.glInterfaceId}" class="findReconAndBizItem">反查询</a>
			</td>				
		</tr>	
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="19" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
	</table>
	<table  border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th>日期</th>
			<th>(代收+POS机代收)-(预收+部分支付+冲部分支付+废单重下冲旧订单)</th>
			<th>差额(分)</th>
		</tr>
		<s:iterator value="countList" var="obj">
		<tr>
			<td><s:property value="MAKE_BILL_TIME"/></td>
			<td>(<font color="green"><s:property value="INSTEAD_INCOME"/>+<s:property value="INSTEAD_INCOME_POS"/></font>)-
			(<font color="blue"><s:property value="BOOKING_INCOME"/>+<s:property value="BOOKING_INCOME_PARTPAY"/></font>+<font color="red"><s:property value="BOOKING_INCOME_PARTPAY_HEDGE"/>+<s:property value="CANCEL_INCOME_HEDGE"/></font>)</td>
			<td><s:property value="MINUS"/></td>
		</tr>
		</s:iterator>
	</table>
	<table  border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th>日期</th>
			<th>类型</th>
			<th>借方金额(分)</th>
			<th>贷方金额(分)</th>
		</tr>
		<s:iterator value="counts" var="obj">
		<tr>
			<td><s:property value="MAKE_BILL_TIME"/></td>
			<td><s:iterator var="obj" value="accountTypes">
						<s:if test="#obj.code==ACCOUNT_TYPE">${obj.cnName}</s:if>
				</s:iterator></td>
			<td><s:property value="BORROWER_AMOUNT"/></td>
			<td><s:property value="LENDER_AMOUNT"/></td>
		</tr>
		</s:iterator>
	</table>
	<div class="gl_table">可能不平票号</div>
	<table  border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th>票号</th>
			<th>相差(预收多为负)(分)</th>
			<th>相关订单</th>
		</tr>
		<s:iterator value="orderDatas" var="obj">
		<tr>
			<td><s:property value="TICKED_NO"/></td>
			<td><s:property value="MINUS_AMOUNT"/></td>
			<td><s:property value="RELATE_ID"/></td>
		</tr>
		</s:iterator>
	</table>
</div>

<div id="gotoEdit" url="${basePath}/recon/gotoEditFinInterface.do"></div>
<div id="orderAccount" url="${basePath}/recon/queryOrderAccount.do"></div>
</body>
</html>