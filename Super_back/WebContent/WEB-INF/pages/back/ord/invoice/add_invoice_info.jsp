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
		<title>添加发布信息</title>
		<script type="text/javascript">
var userId = "${orderUserId}";
var basePath = "<%=basePath%>"
</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/remoteUrlLoad.js">
</script>
		<script type="text/javascript" src="<%=basePath%>js/ord/in_add.js">
</script>
		<script type="text/javascript" src="<%=basePath%>js/base/form.js">
</script>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_common.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>js/base/lvmama_dialog.js">
</script>
		<script type="text/javascript">
			/* function showAddAddressDialg() {
				//$("#bg").show();
				$("#addAddressDialg").openDialog();
				$("#addAddressDialg").attr("href", basePath + "usrReceivers/doAddAddress.do");
				$("#addAddressDialg").reload({userId:userId});
				$("#addAddressDialg").show();
			} */
		</script>
		<style type="text/css">
			#content_div{width:950px;padding:10px;border:1px solid #3366cc;margin:20px 30px;}
			.receipt{border:1px solid #3366cc;margin-bottom:10px;width:99%;padding:10px;}
			.receipt td{font-size:12px;}
			.newfont03 td{height:30px;}
			.btn09{text-align:center;}
		</style>
	</head>

	<body>
	<div style="margin:10px 20px" id="content_div">
		<form method="post" action="<%=basePath%>ord/saveCompositeInvoice.do" id="saveForm" >
		   本次可开票总金额：${invoice_req.totalYuan}		  
          <s:iterator value="invoiceNumberList" var="ordInvoiceList" status="st">             
          <p style="font-size:16px;">发票${ordInvoiceList+1}</p>    
			<table width="100%" class="receipt" id="table${ordInvoiceList}">			
				<tbody>
					<tr>
						<td>
							发票抬头：
							<input name="ordInvoiceList[${ordInvoiceList}].title" type="text" id="title${ordInvoiceList}" />
						</td>
						<td>
							发票内容：<s:property value="#session.invoice_req.zhInvoiceContent"/>							
						</td>
					</tr>
					<tr>
						<td>
							发票金额：
							           <s:if test="#st.last">
							              <input type="text" id="money" name="money" readonly="true" lastInvoice="true" value="以订单实际金额为准"/>
							           </s:if><s:else>
							              <input name="ordInvoiceList[${ordInvoiceList}].amountYuan" type="text" id="amount${ordInvoiceList}"/>
							           </s:else>
						</td>
						<td>
							发票备注：
							<textarea name="ordInvoiceList[${ordInvoiceList}].memo" id="memo${ordInvoiceList}"></textarea>
						</td>
					</tr>
					<tr>
						<td>
							送货方式:
							  <%-- <s:select name="ordInvoice[%{#pos}].deliveryType" list="deliveryTypeList"
								listKey="code" listValue="name"></s:select>  --%>	
								
								<select name="ordInvoiceList[${ordInvoiceList}].deliveryType" id="deliveryType${ordInvoiceList}">
								  <s:iterator value="deliveryTypeList">
								     <option value="<s:property value='code'/>"><s:property value='name'/></option>
								  </s:iterator>
								</select>								
						</td>
					</tr>
				</tbody>
			</table>				
			<div href="<%=basePath%>usrReceivers/loadAddresses.do?hidePhysical=true&hideButton=true"
				id="addressDiv${ordInvoiceList}" idx="${ordInvoiceList}" param="{userId: '${orderUserId}',index:'${ordInvoiceList}'}"></div>	
		    	
			<div class="btn09" id="addressBtns">
				<input type="button" value="新增地址" class="right-button08"
					name="editPassed" onclick="showAddAddressDialg()" />	
					<input type="hidden" name="index"  id="index" value="${ordInvoiceList}"/>					
			</div>
			</s:iterator> 
			<input type="button" value="保存发票" class="right-button08 saveForm" />
			
		</form>
		</div>
		<div id="bg" class="bg" style="display: none;">
			<iframe
				style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity =0); opacity =0; border-style: none; z-index: -1">
			</iframe>
		</div>
		
		<div id="addAddressDialg"
			style="position: absolute; z-index: 10001;display:none"></div>
		<script type="text/javascript">
			$(document).ready(function(){
				$("div[id^=addressDiv]").each(function(){
					$(this).loadUrlHtml();
				});
				$("#addAddressDialg").lvmamaDialog({modal:false,width:600,height:350,close:function(){}});
			});
			$(function(){				
				$("input.saveForm").click(function(){
					 var flag=true;
					 $("#[id^=title]").each(function(i,e){  
						   if($(e).val()==""){
							   alert("抬头不可以为空");
							   flag=false;
							   return false;
						   }						  					   
					 });					
					 var money=0;
					 var reg=/^[1-9]\d*$/;					
					 $("#[id^=amount]").each(function(i,e){  
						   if($(e).val()==""){
							   alert("发票金额不可以为空");
							   flag=false;
							   return false;
						   }else if(!reg.test($(e).val())){
							   alert("发票金额必须为整数");
							   flag=false;
							   return false;
						   }else{							   
							   money+=parseFloat($(e).val());
						   }				   
						   
					 });					
					if(money>$("#totalYuan").val()){
						alert("发票金额不可以超过可开票总金额！");
						 flag= false;
						 return false;
					}	
					var tem=0;
					$("#[id^=deliveryType]").each(function(i,e){						
						if($(e).val()!='SELF'){	
							tem++;													
							var invoiceAddressId=$("input[name^=invoiceAddressId]:checked");							
							if(invoiceAddressId.length<tem){
								alert("送货方式不是自取时必须选择收件地址");
								 flag=false;
								 return false;
							}
						}
					});						
 										
 					 if(flag==true){
 						checkAndSubmit("<%=basePath%>ord/saveCompositeInvoice.do","saveForm");
 					 }			
					
				});
			});
			//提交之后，重刷新table
			function checkAndSubmit(url,form) {
				var $form=$("#"+form);
				$.post($form.attr("action"),$form.serialize(),function(dt){	
					var data=eval("("+dt+")");
					if(data.success){
						$("#content_div").html("<h2>操作成功</h2><p><a href='<%=basePath%>ord/goInvoceForm.do'>返回申请发票页面</a></p>");
					}else{
						alert(data.msg);
					}
				});
			
			}
		</script>
	</body>
</html>
