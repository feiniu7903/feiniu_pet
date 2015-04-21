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
<script type="text/javascript" src="${basePath}/js/ebooking/task.js"></script>
<script type="text/javascript" src="${basePath}/js/base/lvmama_dialog.js"></script>
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
	 $("#groupAcviceNoteForm").validate({
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
					digits:"请输入合法销售ID",
					isNull:"请不要输入空格"
				}
			}
		});
}); 

function changeStatus(orderId,groupWordStatus){
	var flag = confirm("确认要修改状态？");
	if (flag){
		$.getJSON("${contextPath }/ebooking/task/changeGroupWordStatus.do",{orderId:orderId,groupWordStatus:groupWordStatus},function(myJSON){
        	if (myJSON.flag == "success") {
        		alert("修改成功!");
        		$("#groupAcviceNoteForm").submit();
        	} else {
        		alert(myJSON.msg);
        	}
        });
	}
}
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
    <dd><a href="${contextPath }/ebooking/task/confirmRouteTaskList.do">待处理任务</a></dd>
    <dd class="order_nav_dd"><a href="${contextPath }/ebooking/task/groupAdviceNoteList.do">出团通知书</a></dd>
    <dd><a href="${contextPath }/ebooking/task/allRouteTaskList.do">全部订单</a></dd>
    <dd><a href="${contextPath }/ebooking/task/aperiodicRouteTaskList.do">密码券订单</a></dd>
</dl>
<!--内容开始-->
<ul class="order_all">
	<li class="order_all_li">
		<div class="order_list">
		<form  action="${contextPath }/ebooking/task/groupAdviceNoteList.do" id="groupAcviceNoteForm">
    	<dl>
        	<dt>查找订单：</dt>
            <dd>
            	<ul class="search_ul_t hide_js">
            		<li>
                    	<label>订单号：<input type="text" value="${orderId }" id="orderId" name="orderId"></label>
                    </li>
                    
                    <li class="search_ul_b_3">
                    	<label>出发时间：<input id="Calendar1" readonly="readonly" type="text" value="${visitTimeStart }" name="visitTimeStart">
                    	~</label><input id="Calendar2" readonly="readonly" type="text" value="${visitTimeEnd }" name="visitTimeEnd">
                    </li>
                    
                    <li>
                    	<label>出团通知书状态：
                    		<s:select id="groupWordStatus" name="groupWordStatus" list="#{'':'请选择','0':'待发送','1':'已发送'}"></s:select>
						</label>
                    </li>
                    
                    <li>
                    	<label>销售ID：<input type="text" value="${metaProductId }" name="metaProductId"></label>
                    </li>
                    
                    <li>
                    	<label>驴妈妈产品名称：<input type="text" value="${metaProductName }" name="metaProductName"></label>
                    </li>
                    
                    
                    <li class="search_ul_b_but"><span onclick="$('#groupAcviceNoteForm').submit();">查找</span></li>
                </ul>
            </dd>
        </dl>
        <span class="zhankai"></span>
		</form>
    	</div>
    <div class="tableWrap">
	<div class="table01Header">出团通知书列表</div>
	<table width="960" border="0" class="table01">
    	<tr>
      	  <th width="90">订单号</th>
   		  <th width="100">供应商产品名称</th>
   		  <th width="100">驴妈妈产品名称</th>
   		  <th width="60">预定人数</th>
   		  <th width="70">出发日期</th>
   		  <th width="80">距出发剩余天数</th>
   		  <th width="50">发送状态</th>
   		  <th width="90">操作</th>
   		</tr>
   		<s:iterator value="ebkTaskPage.items" var="task">
	    	<tr>
	      	  <td>${orderId }</td>
	   		  <td>
	   		  	<s:iterator value="ebkCertificate.ebkCertificateItemList" var="item" status="i">
	   		  		${metaProductName }<s:if test="!#i.last"><hr/></s:if>
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
	   		  <td><s:date name="ebkCertificate.visitTime" format="yyyy-MM-dd"/></td>
	   		  <td>${ebkCertificate.startDays}</td>
	   		  <td>
	   		  	${task.groupWordStatusDesc}
	   		  </td>
	   		  <td>
	   		  	<s:if test="#task.groupWordStatusDesc=='未发送'">
	   		  		<a target="_blank" href="${basePath}/ebooking/task/getGroupNote.do?orderId=${orderId}">发送</a>
	   		  		 | <a href="javascript:void(0)" onclick="changeStatus('${orderId }','SENT_NO_NOTICE');">修改发送状态</a>
	   		  	</s:if>
				<s:else>
	   		  		<a target="_blank" href="${basePath}/ebooking/task/modifyGroupNote.do?orderId=${orderId}">修改</a>
	   		  		| <a href="javascript:openWin('http://super.lvmama.com/super_back/groupadvice/dwload.do?objectId=${orderId}&objectType=ORD_ORDER&operatorName=${operatorName}',500,400)">下载</a>
				</s:else>
	   		  <%-- | <a href="javascript:void(0)" onclick="showLog('${task.ebkTaskId}');">日志</a> --%>
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
		
		$(function() {
			$("#metaBranchId").val("${metaBranchId}");
			$("#certType").val("${certType}");
			$("#orderId").val("${orderId}");
			
		});
		 
	</script>
	<jsp:include page="../../common/footer.jsp"></jsp:include>

</body>
</html>