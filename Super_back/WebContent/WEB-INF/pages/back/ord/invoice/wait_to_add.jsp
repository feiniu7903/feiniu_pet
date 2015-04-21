<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<s:if test="#session.invoice_req!=null">
<form method="post" action="<%=basePath%>ord/goAddInvoiceInfo.do" onsubmit="return check();">
<div>添加的订单号列表</div>
			<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="600" class="newfont06"
							style="font-size: 12; text-align: center;">
							<tbody>
				<tr bgcolor="#eeeeee">
					<td>订单号</td><td>联系人</td><td>联系人电话</td><td>订单人</td><td>发票内容</td><td>操作</td>
				</tr>
				<s:iterator value="#session.invoice_req" id="ir">
				<tr bgcolor="#ffffff" id="tr_in_<s:property value="orderId"/>">
					<td><s:property value="orderId"/></td>
					<td>${contact.name}</td>
					<td>${contact.mobile}</td>
					<td>${userName}</td>
					<td align="left"><s:select list="%{getInvoiceContentList(#ir.orderType)}" listKey="code" listValue="name" name="orderContent_%{orderId}"/></td>
					<td><a href="#delete" result="<s:property value="orderId"/>" class="delete" id="deleteOrder">删除</a></td>
				</tr>
				</s:iterator>
				<tr bgcolor="#ffffff">
					<td colspan="45" align="right">总金额：${invoice_req.totalYuan}</td>					
				</tr>
				<tr bgcolor="#ffffff">
					<td colspan="45" align="left">
					开票数量：<input type="radio" id="anumber" name='number' checked value="1">1张发票
					  <input type="radio" id="manyNumber" name='number'/>开具<input type="text" size='5' readonly=true id="invoiceNumber" name="invoiceNumber" />张发票					
					</td>
				</tr>
				<s:if test="#session.invoice_req.isEmpty()==false">
				<tr bgcolor="#ffffff">
					<td colspan="6"><input type="submit" value="下一步" class="right-button08" /></td>
				</tr>
				</s:if>
			</table>
</form>
				</s:if>
				
<script type="text/javascript">
var num = ${InvoiceNum};
  $(function(){
	  hideInvoiceNumber();
	  
	  if(num>1){
		  $("#manyNumber").attr("disabled",false);
		  $("#invoiceNumber").attr("readonly",true);
	  }else{
		  $("#manyNumber").attr("disabled",true);
		  $("#invoiceNumber").attr("readonly",true);
	  }
  });
   function hideInvoiceNumber(){	
	  $(":radio[name='number']").click(function(){	     
	      if($(":radio[name='number']:checked").val()==1){
	    	  $("#invoiceNumber").val("");
	    	  $("#invoiceNumber").attr("readonly",true);
	      }else{
	    	  $("#invoiceNumber").attr("readonly",false);
	      }
	    });
   }  
   
   function check(){
	   var totalYuan=$("#totalYuan").val();
	   if(totalYuan<1){
		   alert("总金额小于1不能开发票");
		   return false;
	   }
	   
	   if($("#manyNumber").attr("checked")){
		   if($("tr[id^=tr_in_]").length>1){
			   alert("多订单只可申请合并开票");
			   return false;
		   }
		   var numInv=parseInt($("#invoiceNumber").val());
		   if(numInv==NaN||numInv<2){
			   alert("开票张数必须大于1");
			   return false;
		   }
		   if(num<numInv){
			   alert("根据游玩人数开票张数不能大于" + num);
			   return false;
		   }
	   }
	   return true;
   }
</script>