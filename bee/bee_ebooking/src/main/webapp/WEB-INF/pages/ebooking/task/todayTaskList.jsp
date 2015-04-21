<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script type="text/javascript" src="${basePath}/js/ebooking/task.js"></script>
<script type="text/javascript" src="${contextPath}/js/base/jquery.validate.js"></script>
<script type="text/javascript">
$(function(){ 
	 jQuery.validator.addMethod("isNull",
		       function(value, element,param) {
		           if(value!=''){
		        	   return value.replace(/ /g,"")!='';
		           }else{
		        	   return true;
		           }
		       },
		       "请不要输入空格");
	 $("#confirmTaskForm").validate({
			rules: {    
				"orderId":{
					digits:true,
					isNull:true
				}
			}, 
			messages: {    
				"orderId":{
					digits:"请输入合法订单号",
					isNull:"请不要输入空格"
				}
			}
		});
}); 
</script>
</head>
<body id="body_ddgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>订单处理</li>
    	<li>酒店订单处理</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/dingdan-guide.ppt" class="ppt_xz">订单处理操作PPT下载</a>
</div><!--以上是公用部分-->

<!--订单处理开始-->
<dl class="order_nav">
	<dt>订单处理</dt>
    <dd><a href="${contextPath }/ebooking/task/confirmTaskList.do">未处理订单</a></dd>
    <dd><a href="${contextPath }/ebooking/task/todayVisitTaskList.do">今日入住订单</a></dd>
    <dd class="order_nav_dd"><a href="${contextPath }/ebooking/task/todayTaskList.do">今日已处理订单</a></dd>
    <dd><a href="${contextPath }/ebooking/task/allTaskList.do">全部订单</a></dd>
    <dd><a href="${contextPath }/ebooking/task/aperiodicTaskList.do">密码券订单</a></dd>
</dl>
<!--内容开始-->
<ul class="order_all">
	<li class="order_all_li">
		<div class="order_list">
		<form action="${contextPath }/ebooking/task/todayTaskList.do" id="confirmTaskForm">
    	<dl>
        	<dt>查找订单：</dt>
            <dd>
            	<ul class="search_ul_t hide_js">
                	<li>
                    	<label>订单号：<input type="text" value="${orderId }" name="orderId"></label>
                    </li>
                    <li>
                    	<label>客人姓名：<input type="text" value="${travellerName }" name="travellerName"></label>
                    </li>
                    
                    <li>
                    	<label>订单状态：
                        	<select name="orderStatus" id="orderStatus">
                            	<option value="">全部</option>
                                <s:iterator value="@com.lvmama.comm.vo.Constant$ORDER_STATUS@values()" var="enmu">
                            		<option value="${enmu}">${enmu.cnName}</option>
                            	</s:iterator>
                            </select>
                        </label>
                    </li>
                    <li>
                    	<label>酒店确认号：<input type="text" value="${supplierOrderNo }" name="supplierOrderNo"></label>
                    </li>
                    <li>
                    	<label>确认人：<input type="text" value="${confirmUser }" name="confirmUser"></label>
                    </li>
                    <li class="search_ul_li_p6">
                    	<label>支付状态：<select name="paymentStatus" id="paymentStatus">
                            	<option value="">全部</option>
                                <s:iterator value="@com.lvmama.comm.vo.Constant$PAYMENT_STATUS@values()" var="enmu">
                            		<option value="${enmu}">${enmu.cnName}</option>
                            	</s:iterator>
                            </select>
                        </label>
                    </li>
                    <li>
                    	<label>确认状态：
                        	<select name="confirmStatus" id="confirmStatus">
                            	<option value="">全部</option>
                                <s:iterator value="@com.lvmama.comm.vo.Constant$EBK_CERTIFICATE_TYPE_AND_STATUS@values()" var="enmu">
                            		<option value="${enmu}">${enmu.cnName}</option>
                            	</s:iterator>
                            </select>
                        </label>
                    </li>
                     <li class="search_ul_li_p27">
                    	<label>房型：
                        	<select class="width_auto" name="metaBranchId" id="metaBranchId">
                            	<option value="">全部</option>
                                <s:iterator var="mb" value="ebkMetaBranchList">
	                            	<option value="${mb.metaBranchId }">${mb.branchName }</option>
                                </s:iterator>
                            </select>
                        </label>
                    </li>
                    <li class="search_ul_b_but"><span onclick="$('#confirmTaskForm').submit();">查找</span></li>
                </ul>
            </dd>
        </dl>
        <span class="zhankai"></span>
		</form>
    	</div>
    <div class="tableWrap">
	<div class="table01Header"><a href="javascript:void(0)" onclick="down();">下载订单列表</a>订单列表</div>
	<table width="960" border="0" class="table01">
    	<tr>
      	  <th width="80">订单号</th>
   		  <th width="70">订单状态</th>
   		  <th width="100">确认状态</th>
   		  <th width="70">支付状态</th>
   		  <th width="70">房型</th>
   		  <th width="80">客人姓名</th>
   		  <th width="100">入住日期</th>
   		  <th width="100">离店日期</th>
   		  <th width="70">房间数</th>
   		  <th width="150">下单时间</th>
   		  <th width="90">操作</th>
   		</tr>
   		<s:iterator value="ebkTaskPage.items" var="task">
	    	<tr>
	      	  <td>${orderId }
	      	  	<s:if test="ebkCertificate.confirmChannel != null && ebkCertificate.confirmChannel != 'EBK'">
					<div style="color:red;">(${ebkCertificate.zhConfirmChannel })</div>
				</s:if>
	      	  </td>
	   		  <td>${zhOrderStatus}</td>
	   		  <td>${ebkCertificate.zhCertificateTypeStatus }</td>
	   		  <td>${zhPaymentStatus}</td>
	   		  <td>
				<s:iterator value="ebkCertificate.ebkCertificateItemList" var="item" status="i">
	   		  		${realProductName }<s:if test="!#i.last"><hr/></s:if>
	   		  	</s:iterator>
			  </td>
	   		  <td>${travellerName }</td>
	   		  <td><s:date name="ebkCertificate.visitTime" format="yyyy-MM-dd"/></td>
	   		  <td><s:date name="ebkCertificate.leaveTime" format="yyyy-MM-dd"/></td>
	   		   <td>${roomQuantity }
	   		  	<s:if test="resourceConfirm == 'false'"><span class="orange">(保留房)</span></s:if>
	   		  </td>
	   		  <td><s:date name="orderCreateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
	   		  <td><a target="_blank" href="${contextPath }/ebooking/task/orderck.do?ebkTaskId=${task.ebkTaskId}">查看</a> 
	   		  | <a href="javascript:void(0)" onclick="showLog('${task.ebkTaskId}');">日志</a>
	   		  	<s:if test="ebkCertificate.memo != null">
	   		  	<img class="yaoqiu_img" date="${ebkCertificate.memo }" src="http://pic.lvmama.com/img/ebooking/yaoqiu.gif" width="14" height="11" alt=""/>
	   		  	</s:if>
	   		  </td>
	   		</tr>
   		</s:iterator>
    </table>
	<div class="table01Footer" style="padding-left: 0px;">
		<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(ebkTaskPage,'')"/>
    </div>
    </div>
</div> 
    </li>
</ul>
<script type="text/javascript">
	function down() {
		var form = $('#confirmTaskForm');
		var action = form.attr("action");
		var target = form.attr("target");
		form.attr("action","${contextPath }/ebooking/task/downTodayTaskList.do");
		form.attr("target","_new");
		form.submit();
		form.attr("action",action);
		form.attr("target",target);
	} 
	$(function() {
		$("#metaBranchId").val("${metaBranchId}");
		$("#orderStatus").val("${orderStatus}");
		$("#confirmStatus").val("${confirmStatus}");
		$("#paymentStatus").val("${paymentStatus}");
	});
</script>
	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>