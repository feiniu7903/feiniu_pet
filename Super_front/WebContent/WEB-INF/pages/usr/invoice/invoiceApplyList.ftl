<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发票申请查询</title>

<#include "/common/commonJsIncluedTopNew.ftl"/>
<link href="http://pic.lvmama.com/styles/v4style/myspace_fapiao.css?r=3252" rel="stylesheet" type="text/css" />

	<link rel="stylesheet"
			href="/themes/base/jquery.ui.all.css" />
		<script type="text/javascript"
			src="/ui/jquery-ui-1.8.5.js">
</script>
<script type="text/javascript">
	var titleIdTemp = $('#titleId').val();
	var beginTimeIdTemp = $('#beginTimeId').val();
	var endTimeIdTemp = $('#endTimeId').val();
	//页面初始化:
	$(document).ready(function () {
		$("input[name='orderTimeRange.createInvoiceStart']" ).datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='orderTimeRange.createInvoiceEnd']" ).datepicker({dateFormat:'yy-mm-dd'});
	});
	
	$(function(){	
		//点击"查询"按钮.
		$('#searchId').click(function() {
			var titleIdValue = $('#titleId').val();
			var beginTimeIdValue = $('#beginTimeId').val();
			var endTimeIdValue = $('#endTimeId').val();
			//alert(titleIdValue);
			//alert(titleIdValue + "," + beginTimeIdValue + "," + endTimeIdValue);
			$('#invoiceApplyForm').submit();
		});
		//点击"按申请时间查询"单选按钮.
		$('#timeRadioId').click(function(){
			titleIdTemp = $('#titleId').val();
			$('#titleId').val('');
			$('#beginTimeId').val() == "" ? $('#beginTimeId').val(beginTimeIdTemp) : $('#beginTimeId').val();
			$('#endTimeId').val() == "" ? $('#endTimeId').val(endTimeIdTemp) : $('#endTimeId').val();
		});
		//点击"发票抬头查询"单选按钮.
		$('#titleRadioId').click(function(){
			beginTimeIdTemp = $('#beginTimeId').val();
			$('#beginTimeId').val('');
			endTimeIdTemp = $('#endTimeId').val();
			$('#endTimeId').val('');
			$('#titleId').val() == "" ? $('#titleId').val(titleIdTemp) : $('#titleId').val();
		});
		//点击"清空"按钮.
		$('#clearId').click(function(){
			$('#titleId').val('');
			titleIdTemp = "";
			$('#beginTimeId').val('');
			beginTimeIdTemp = "";
			$('#endTimeId').val('');
			endTimeIdTemp = "";
		});
		
		
		//点击"取消申请"按钮.
		$('a.cancel').click(function() {
			if(!confirm("确定要取消该发票")){
				return false;
			}
			var invoice_id=$(this).attr("result");
			if(!invoice_id){
				alert("发票信息不存在");
			}else{
				var $this=$(this);
				//alert(invoice_id);
				$.post('${base}/usr/cancelInvoiceApply.do',{"invoiceId":invoice_id},function(dt){
					var data=eval("("+dt+")");
					if(data.success){
						$this.remove();
						$("#status_"+invoice_id).html("废弃");
					}else{
						alert(data.msg);
					}
				});
			}
		});
	});
</script>
</head>

<body>
<#--
<div class="content">
<h2 class="fapiao_mess">发票申请查询</h2>-->
<form id="invoiceApplyForm" action="${base}/usr/invoiceApplyList.do" method="post">
<div class="mess_contant">
<div class="refer_over">
	<div class="refer_time">按申请时间查询</div>
    
    </div>
<div style="margin-top:15px;">

    	<input id="beginTimeId" name="orderTimeRange.createInvoiceStart" type="text" class="text_time" value="" />
        --
        <input id="endTimeId" name="orderTimeRange.createInvoiceEnd" type="text" class="text_time" value="" />
   
    </div>
    
    <div class="refer_over">
	<div class="refer_time">发票抬头查询</div>
  

    </div>
    <div style="margin-top:15px;">
    	<input id="titleId" name="invoiceRelate.title" type="text" class="text_time01" />
   
    </div>
      
         <div>
         	<input id="searchId" name="" type="button"  class="mess_bt001" value="查询" />
         	<input id="clearId" name="" type="button"  class="mess_bt001" value="清空" />
         </div>
</div>
</form>
<div class="mess_contant01">
		<table width="750" border="0" cellspacing="0" cellpadding="0">
  <tr class="mess_refer_top">
    <td align="center" class="mess_bg01">序号</td>
    <td align="center" class="mess_bg01">发票金额</td>
    <td align="center" class="mess_bg01">发票号</td>
    <td align="center" class="mess_bg01">发票抬头</td>
    <td align="center" class="mess_bg01">申请日期</td>
    <td align="center" class="mess_bg01">状态</td>
    <td align="center" class="mess_bg01">操作</td>
  </tr>
  <#list pageConfig.items as obj>
  <tr class="mess_bg">
    <td align="center" class="mess_bg001">${obj.invoiceId}</td>
    <td align="center" class="mess_bg001">${obj.amountYuan}</td>
    <td align="center" class="mess_bg001">${obj.invoiceNo}</td>
    <td align="center" class="mess_bg001">${obj.title}</td>
    <td align="center" class="mess_bg001">
    	<#if obj.createTime?exists>${obj.createTime?datetime}</#if>
    </td>
    <td align="center" class="mess_bg001" id="status_${obj.invoiceId}">${obj.zhStatus}</td>
    <td align="center" class="mess_bg001"><a href="${base}/usr/invoiceApplyDetail.do?invoiceForm.invoiceId=${obj.invoiceId}">查看</a>
    <#if obj.status=='UNBILL'||obj.status=='APPROVE'><br/>
    	<a href="javascript:void(0)" result="${obj.invoiceId}" class="cancel">取消</a>
    </#if>
    </td>
  </tr>
  </#list>
  <#-- 
<tr class="mess_bg">
    <td align="center" class="mess_bg001">12345</td>
    <td align="center" class="mess_bg001">20000</td>
    <td align="center" class="mess_bg001">1234556787</td>
    <td align="center" class="mess_bg001">上海市金沙江路3131号</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
    <td align="center" class="mess_bg001">未开票</td>
    <td align="center" class="mess_bg001"><a href="#">查看</a></td>
  </tr>
<tr class="mess_bg">
    <td align="center" class="mess_bg001">12345</td>
    <td align="center" class="mess_bg001">20000</td>
    <td align="center" class="mess_bg001">1234556787</td>
    <td align="center" class="mess_bg001">上海市金沙江路3131号</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
    <td align="center" class="mess_bg001">已邮件</td>
    <td align="center" class="mess_bg001"><a href="#">查看</a></td>
  </tr>
<tr class="mess_bg">
    <td align="center" class="mess_bg001">12345</td>
    <td align="center" class="mess_bg001">20000</td>
    <td align="center" class="mess_bg001">1234556787</td>
    <td align="center" class="mess_bg001">上海市金沙江路3131号</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
    <td align="center" class="mess_bg001">已邮件</td>
    <td align="center" class="mess_bg001"><a href="#">查看</a></td>
  </tr>
<tr class="mess_bg">
    <td align="center" class="mess_bg001">12345</td>
    <td align="center" class="mess_bg001">20000</td>
    <td align="center" class="mess_bg001">1234556787</td>
    <td align="center" class="mess_bg001">上海市金沙江路3131号</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
    <td align="center" class="mess_bg001">已邮件</td>
    <td align="center" class="mess_bg001"><a href="#">查看</a></td>
  </tr>
<tr class="mess_bg">
    <td align="center" class="mess_bg001">12345</td>
    <td align="center" class="mess_bg001">20000</td>
    <td align="center" class="mess_bg001">1234556787</td>
    <td align="center" class="mess_bg001">上海市金沙江路3131号</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
    <td align="center" class="mess_bg001">已邮件</td>
    <td align="center" class="mess_bg001"><a href="#">查看</a></td>
  </tr>
  -->
</table>

<div class="page_order">
	<@s.property escape="false" value="@com.lvmama.common.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
</div>
</div>
</body>
</html>
