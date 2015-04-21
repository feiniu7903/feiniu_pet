<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ebk" uri="/tld/lvmama-ebk-tags.tld"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${contextPath}/js/base/jquery.validate.js"></script>
<style type="text/css">
	.error{ color:#ff0000;}
</style>
</head>
<body id="body_ddgl" >
<jsp:include page="../../common/head.jsp"></jsp:include>
	<div>
		<div class="breadcrumb">
		<ul class="breadcrumb_nav">
			<li class="home">首页</li>
			<li>订单处理</li>
			<li>门票订单处理</li>
	    </ul>
	</div>
		<!--订单处理开始-->
		<dl class="order_nav">
			<dt>订单处理</dt>
			<dd class="order_nav_dd">
				<a href="${contextPath }/eplace/queryPassPort.do">未游玩订单</a>
			</dd>
			<ebk:perm permissionId="5" >
			<dd>
				<a href="${contextPath }/eplace/tongJi.do">统计订单</a>
			</dd>
			</ebk:perm>
			<ebk:perm permissionId="6" >
			<dd>
				<a href="${contextPath }/eplace/allPassportList.do">全部订单</a>
			</dd>
			</ebk:perm>
		</dl>
		<div class="order_all">
			<form id="queryPassPortForm" action="${contextPath }/eplace/queryPassPort.do" method="post">
			<ul class="search_ul_t">
				<li><label>订单号：</label><input type="text" name="port_orderId"></li>
				<li><label>手机号：</label><input type="text" name="port_mobile"></li>
				<li><label>取票人姓名：</label><input type="text" name="port_userName"></li>
				<li><label>辅助码：</label><input type="text" name="port_passPort"></li>
				<li><a href="javascript:submitFn()" class="snspt_Btn snspt_srBtn">查找</a></li>
			</ul>
			</form>
			<div class="tableWrap">
				<p class="table01Header">订单列表</p>
				<table width="960" border="0" class="table01">
					<tr>
						<th width="80">订单号</th>
						<th width="80">游玩时间</th>
						<th width="120">取票人</th>
						<th width="80">手机号</th>
						<th width="180">产品名称</th>
						<th width="180">订购票数</th>
						<th width="80">付款状态</th>
						<th width="120">操作</th>
					</tr>
					<s:if test="orderMap != null">
						<s:iterator value="orderMap">
							<tr>
						      <td>
						      	<a href="javascript:void(0)" class="snspt_ordindex" orderId="${value[0].orderId }">
						      		${value[0].orderId }
						      	</a>
						      	<s:if test="value[0].isAperiodic=='true'">
									<span style="color:red;"><br>(期票)</span>
								</s:if>
					          	<s:iterator value="value">
						 			<input id="${orderId}" type="hidden" value="${orderItemMetaId }" />
						 		</s:iterator>
						      </td>
						      <td>${value[0].strDeadlineTime }</td>
						      <td>${value[0].contactName }</td>
						      <td>${value[0].contactMobile }</td>
						      <td>
						          <ul>
						          	<s:iterator value="value">
							 			<li>${metaProductName }</li>
							 		</s:iterator>
						          </ul>
					          </td>
				              <td>
				              	<ul>
					               	<s:iterator value="value">
							 			<li>${quantity }张（${adultQuantity }成人,${childQuantity }儿童）</li>
							 		</s:iterator>
					            </ul>
				              </td>
						      
								<s:if test="value[0].paymentTarget=='TOLVMAMA'">
				              		<td>已付款</td>
				              	</s:if>
				              	<s:else>
				              		<td class="f_red">未付款</td>
				              	</s:else>
							  
						     <td>
				              	<a href="javascript:void(0)" data_attr="{orderId:${value[0].orderId},targetId:${value[0].targetId},paymentTarget:'${value[0].paymentTarget}',isAperiodic:'${value[0].isAperiodic}'}" class="snspt_Btn snspt_optBtn">通关处理</a>
							  </td>
						    </tr>
						</s:iterator>
				    </s:if>
				</table>
				<div class="table01Footer"/>
				<!--tablefooter-->
			</div>
			<!--tableWrap-->
		</div>
		<!--order_all-->
	</div>
	<!--wrap-->
	<!--popcont-->
	<div class="snspt_pop_contwrap">
		<p class="lv_pop_btnbox">
			<a class="lv_pop_btn_rl snspt_tg_opt_s" href="javascript:void(0)">确定通关</a>
			<a class="lv_pop_btn_gl snspt_tg_opt_c" href="javascript:void(0)">取消</a>
		</p>
	</div>
	<div class="snspt_pop_contwrap1">
	</div>
	<!--popcont-->
	<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>
	<script type="text/javascript">
	// 数字验证，允许前后有空格       
	 $.validator.addMethod("numberCheck", function(value, element) {       
	     return this.optional(element) || /^\s*(([0-9])|([1-9][0-9]*))\s*$/.test(value);       
	 }, "请输入数字."); 
	$("#queryPassPortForm").validate({
		rules: {    
			"port_orderId":{
				numberCheck:true
			},
			"port_passPort":{
				numberCheck:true
			} 
		}, 
		messages: {    
			"port_orderId":{
				numberCheck:"请输入数字."
			},
			"port_passPort":{
				numberCheck:"请输入数字."
			} 
		}
	});
		function submitFn(){
			$('#queryPassPortForm').submit();
		}
	</script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
<script src="${contextPath }/js/eplace/snspt_pop.js"></script>
</body>
</html>

