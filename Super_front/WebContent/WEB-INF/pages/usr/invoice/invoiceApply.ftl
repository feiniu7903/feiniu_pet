<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>发票申请</title>
		<#include "/common/commonJsIncluedTopNew.ftl"/>
		<link href="http://pic.lvmama.com/styles/v4style/myspace_fapiao.css?r=3252" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			/**
			*分号分隔符.
			*/
			var SEMICOLON_SEPARATOR = ";";
			/**
			 * 游客在页面上申请发票的最大金额100000元.
			 */
			var INVOICE_AMOUNT_MAX_VALUE = 100000;

			    //初始化页面:
				$(document).ready(function () {
				if ('${companyType}' == "COMPANY_1") {
						$('#company1Id').attr('selected','selected');
					}
				if ('${companyType}' == "COMPANY_2") {
						$('#company2Id').attr('selected','selected');
					}
					if ('${companyType}' == "COMPANY_3") {
						$('#company3Id').attr('selected','selected');
					}
				});
			
			$(function(){
				//点击"填写发票信息"按钮.
				$('#addInvoiceApplyId').click(function(){
				   //var array = $("input[name='orderIdName']");
				   var array = $("input:checked");
				   if (array.length == 0) {
				   	   alert("请至少选中一条记录!");
				   	   return;
				   }
				  // alert($(array[0]).attr("name"));
				  var orderIds = array2string(array,SEMICOLON_SEPARATOR);
				  if (orderIds == 'false') {
				  	return;
				  }
				  var companyType = $('#companyTypeId').val();
				  window.location.href='${base}/usr/newUserAddAdds.do?orderIds=' + orderIds + '&companyType=' + companyType;
				});
				
				$('#companyTypeId').change(function() {
					var companyType = $('#companyTypeId').val();
					window.location.href='${base}/usr/invoiceApply.do?companyType=' + companyType;
				});
				
				
			});
			
			
			
			/**
			 * 将一个对象数组中对象的value属性以separator为分隔符,拼接成字符串.
			 * @param array 对象数组.
			 * @param separator 分隔符.
			 * @return  返回拼接后的字符串.
			 */
			function array2string(array,separator) {
				var result = "";
				var sumActualYuan = 0;
				
				
				if (array == null || array.length == 0) {
					return;
				}
				var tempSeparator = SEMICOLON_SEPARATOR;
				if (separator != null) {
					tempSeparator = separator;
				} 
				
				for (var i = 0; i < array.length; i++) {
					//result += $(array[i]).val();
					var value = $(array[i]).val().split('_');
					result += value[0];
					sumActualYuan += parseFloat(value[1]);
					result += tempSeparator;
				}
				if (array.length > 0) {
					result = result.substring(0,result.length - tempSeparator.length)
				}
				if (sumActualYuan >= INVOICE_AMOUNT_MAX_VALUE) {
					alert("您当前的申请发票金额为:" + sumActualYuan + "元,已大于可申请的最大值:" + INVOICE_AMOUNT_MAX_VALUE + "元,请拨打400电话申请.");
					return "false";
				}
				return result;
				 
			}
		</script>
	</head>
	<body>
		<#--
			<h2 class="fapiao_mess">
				发票申请
				<span class="tips"><strong>[温馨提示]</strong> 如果您需要将多个订单开具在一张发票上，你可同时选择多个订单后一同填写发票信息。</span>
			</h2>-->
			<div class="mess_contant" style=" position: relative;">
			<div class="tan_list">不同类型产品无法进行合并开票，我们已为您进行了分类，如给您带来不便，敬请谅解！</div>
				<div class="mess_apper">
					可申请发票
					<select id="companyTypeId" name="companyType" size="1" class="select_list">
						<option id="company1Id" value="COMPANY_1">
							门票，酒店，自由行
						</option>
						<option id="company2Id" value="COMPANY_2" >
							国内游
						</option>
						<option id="company3Id" value="COMPANY_3">
							出境游
						</option>
					</select>
				<a href="#" class="tips_pic"><img width="10" height="10" src="http://pic.lvmama.com/img/myspace/myspace_fapiao/wenhao.gif"></a>
				</div>
     <div class="tips004"><strong>[温馨提示]</strong> 如果您需要将多个订单开具在一张发票上，你可同时选择多个订单后一同填写发票信息。</div>
				<div style="margin-top: 20px;">
					<table width="740" border="0" cellspacing="0" cellpadding="0">
						<tr class="mess_refer_top">
							<td align="center" class="mess_bg01">
								订单号
							</td>
							<td align="center" class="mess_bg01">
								产品名称
							</td>
							<td align="center" class="mess_bg01">
								订单金额
							</td>
							<td align="center" class="mess_bg01">
								下单时间
							</td>
						</tr>
						<#list pageConfig.items as obj>
						<tr class="mess_bg">
							<td align="center" class="mess_bg001">
								<input name="orderIdName" type="checkbox" value="${obj.orderId}_${obj.actualPayFloat}" class="check_nums" />
								${obj.orderId}
							</td>
							<td align="left" class="mess_bg001">
								<#list obj.ordOrderItemProds as itemObj>
	    	<#if itemObj.additional?default("false")=="false">
		    <#if itemObj.wrapPage?default("false")=="true">
		    <a href="/product/${itemObj.productId?if_exists}" target="_blank">
		    </#if>
		    <#if itemObj.productName?length gt 28>
		    &nbsp;${itemObj.productName?substring(0,27)}...&nbsp;<br/>
		    <#else>
		    &nbsp;${itemObj.productName?if_exists}&nbsp;<br/>
		    </#if>
		    <#if itemObj.wrapPage?default("false")=="true"></a></#if>
	    </#if>
    </#list>
							</td>
							<td align="center" class="mess_bg001">
								￥${obj.actualPayFloat}
							</td>
							<td align="center" class="mess_bg001">
								${obj.createTime?datetime}
							</td>
						</tr>
						</#list>
						<!-- 
  <tr class="mess_bg">
    <td align="center" class="mess_bg001"><input name="" type="checkbox" value="" class="check_nums" />123456</td>
    <td align="center" class="mess_bg001">上海欢乐谷门票</td>
    <td align="center" class="mess_bg001">￥180</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
  </tr>
  <tr class="mess_bg">
    <td align="center" class="mess_bg001"><input name="" type="checkbox" value="" class="check_nums" />123456</td>
    <td align="center" class="mess_bg001">上海欢乐谷门票</td>
    <td align="center" class="mess_bg001">￥180</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
  </tr>
  <tr class="mess_bg">
    <td align="center" class="mess_bg001"><input name="" type="checkbox" value="" class="check_nums" />123456</td>
    <td align="center" class="mess_bg001">上海欢乐谷门票</td>
    <td align="center" class="mess_bg001">￥180</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
  </tr>
  <tr class="mess_bg">
    <td align="center" class="mess_bg001"><input name="" type="checkbox" value="" class="check_nums" />123456</td>
    <td align="center" class="mess_bg001">上海欢乐谷门票</td>
    <td align="center" class="mess_bg001">￥180</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
  </tr>
  <tr class="mess_bg">
    <td align="center" class="mess_bg001"><input name="" type="checkbox" value="" class="check_nums" />123456</td>
    <td align="center" class="mess_bg001">上海欢乐谷门票</td>
    <td align="center" class="mess_bg001">￥180</td>
    <td align="center" class="mess_bg001">2011-11-16</td>
  </tr>
  -->
					</table>
					<div class="page_order">
						<@s.property escape="false" value="@com.lvmama.common.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
					</div>
					<div class="app_bt">
						<input id="addInvoiceApplyId" name="addInvoiceApply" type="button" class="mess_bt01" value=" "  />
					</div>
				</div>
			</div>

			<div class="apper_info">
				<p>
					说明
				</p>
				<p>
					1、发票金额在300元以上，我们将免费为您快递发票。
				</p>
				<p>
					2、发票金额小于300元，您需在发票送达时向快递公司支付相应费用。
				</p>
				<p>
				3、您可累计多张发票一同申请。
				</p>
					<p>
				4、您可申请发票的最晚时间为游玩时间后的2个月内。
				</p>
					<p>
		5、如需更多帮助，请致电1010-6060
				</p>
				
								<p>
		6、红色区域内文字修改为：温馨提示：开票地为上海，如需申请其他分公司所开发票，请先致电1010-6060询问。
				</p>

			</div>
			
			
		<script type="text/javascript">
		$(function()
		{ 
		$('.tips_pic').mouseover(function(){ 
	
		$('.tan_list').show(); 
	
		}) 
		
		
		
		$('.tan_list').mouseout(function(){ 
		$('.tan_list').hide(); 
		}) 
		
		
		});
		</script>	
	</body>
</html>
