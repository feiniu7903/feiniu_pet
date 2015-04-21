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
				},
				"metaProductId":{
					digits:true,
					isNull:true
				}
			}, 
			messages: {    
				"orderId":{
					digits:"请输入合法订单号",
					isNull:"请不要输入空格"
				},
				"metaProductId":{
					digits:"请输入合法产品ID",
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
    	<li>线路订单处理</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/dingdanRoute-guide.ppt" class="ppt_xz">订单处理操作PPT下载</a>
</div><!--以上是公用部分-->

<!--订单处理开始-->
<dl class="order_nav">
	<dt>订单处理</dt>
    <dd class="order_nav_dd"><a href="${contextPath }/ebooking/task/confirmRouteTaskList.do">待处理任务</a></dd>
    <dd><a href="${contextPath }/ebooking/task/groupAdviceNoteList.do">出团通知书</a></dd>
    <dd><a href="${contextPath }/ebooking/task/allRouteTaskList.do">全部订单</a></dd>
    <dd><a href="${contextPath }/ebooking/task/aperiodicRouteTaskList.do">密码券订单</a></dd>
</dl>
<!--内容开始-->
<ul class="order_all">
	<li class="order_all_li">
		<div class="order_list">
		<form  action="${contextPath }/ebooking/task/confirmRouteTaskList.do" id="confirmTaskForm">
    	<dl>
        	<dt>查找订单：</dt>
            <dd>
            	<ul class="search_ul_t hide_js">
                	<li>
                    	<label>订单号：<input type="text" value="${orderId }" id="orderId" name="orderId"></label>
                    </li>
                    <li>
                    	<label>产品ID：<input type="text" value="${metaProductId }" name="metaProductId"></label>
                    </li>
                    <li>
                    	<label>产品名称：<input type="text" value="${metaProductName }" name="metaProductName"></label>
                    </li>
                    <li class="search_ul_b_3">
                    	<label>出发时间：<input id="Calendar1" readonly="readonly" type="text" value="${visitTimeStart }" name="visitTimeStart">
                    	~</label><input id="Calendar2" readonly="readonly" type="text" value="${visitTimeEnd }" name="visitTimeEnd">
                    </li>
                    <li>
                    	<label>排序：
                    	<select id="orderBy" class="u38" name="orderBy"  >
							<OPTION selected value="">默认排序</OPTION>
							<OPTION  value="visitTimeDesc">出发日期从近到远</OPTION>
							<OPTION  value="visitTimeAsc">出发日期从远到近</OPTION>
							<OPTION  value="ordCreateTimeDesc">下单时间从近到远</OPTION>
							<OPTION  value="ordCreateTimeAsc">下单时间从远到近</OPTION>
						</SELECT>
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
	<div class="table01Header"><a href="javascript:void(0)" onclick="down()">下载订单列表</a>订单列表</div>
	<table width="960" border="0" class="table01">
    	<tr>
      	  <th width="90">订单号</th>
   		  <th width="80">类型</th>
   		  <th width="110">确认状态</th>
   		  <th width="80">产品ID</th>
   		  <th width="80">产品名称</th>
   		  <th width="110">份数</th>
   		  <th width="110">游客姓名</th>
   		  <th width="70">出发时间</th>
   		  <th width="160">结算总额</th>
   		  <th width="90">操作</th>
   		</tr>
   		<s:iterator value="ebkTaskPage.items" var="task">
	    	<tr>
	      	  <td>${orderId }</td>
	   		  <td>${ebkCertificate.zhEbkCertificateType }</td>
	   		  <td>${ebkCertificate.zhCertificateStatus }</td>
	   		  <td>
	   		  	<s:iterator value="ebkCertificate.ebkCertificateItemList" var="item" status="i">
	   		  		${metaProductId }<s:if test="!#i.last"><hr/></s:if>	   	
	   		  	</s:iterator>
	   		  </td>
	   		  <td>
	   		  	<s:iterator value="ebkCertificate.ebkCertificateItemList" var="item" status="i">
	   		  		${metaProductName }<s:if test="!#i.last"><hr/></s:if>
	   		  	</s:iterator>
	   		  </td>
	   		  <td>
	   		  	<s:iterator value="ebkCertificate.ebkCertificateItemList" var="item" status="i">
	   		  		${quantity }<s:if test="!#i.last"><hr/></s:if>
	   		  	</s:iterator>
	   		  </td>
	   		  <td>${travellerName }</td>
	   		  <td><s:date name="ebkCertificate.visitTime" format="yyyy-MM-dd"/></td>
	   		  <td>${ebkCertificate.totalSettlementPriceYuan }</td>
	   		  <td>
	   		  	<a target="_blank" href="${contextPath }/ebooking/task/enquireSeatOrderTask.do?ebkTaskId=${task.ebkTaskId}">处理订单</a>
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
			form.attr("action","${contextPath }/ebooking/task/downConfirmRouteTaskList.do");
			form.attr("target","_new");
			form.submit();
			form.attr("action",action);
			form.attr("target",target);
		} 
		$(function() {
			$("#metaBranchId").val("${metaBranchId}");
			$("#certType").val("${certType}");
			$("#orderId").val("${orderId}");
			
		});
		 
	</script>
	<jsp:include page="../../common/footer.jsp"></jsp:include>

</body>
</html>