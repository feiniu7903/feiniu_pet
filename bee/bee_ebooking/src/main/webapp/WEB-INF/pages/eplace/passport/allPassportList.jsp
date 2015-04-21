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
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" type="text/css" href="${contextPath }/css/base/jquery-ui-timepicker-addon.css"  />
<link rel="stylesheet" type="text/css" href="${contextPath }/css/base/jquery.ui.all.css" />
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${contextPath}/js/base/jquery.validate.js"></script>
<style type="text/css">
	.error{ color:#ff0000;}
</style>
</head>
<body id="body_ddgl">
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
		<ebk:perm permissionId="4" >
	    <dd><a href="${contextPath }/eplace/queryPassPort.do">未游玩订单</a></dd>
	    </ebk:perm>
	    <ebk:perm permissionId="5" >
	    <dd><a href="${contextPath }/eplace/tongJi.do">统计订单</a></dd>
	    </ebk:perm>
    	<dd class="order_nav_dd"><a href="${contextPath }/eplace/allPassportList.do">全部订单</a></dd>
	</dl>
	
	<div class="order_all">
	  <form id="queryAllPassPortForm" action="${contextPath }/eplace/allPassportList.do" method="post">
		  <ul class="search_ul_t snspt_sclist2 search_ul_t2">
		      <li>
		          <label>游玩时间：</label><input id="playTimeStart" type="text" name="playTimeStart" class="snspt_init_input" value="${playTimeStart }"> 
		          		至 <input type="text" id="playTimeEnd" name="playTimeEnd" class="snspt_init_input" value="${playTimeEnd }">
		      </li>
	          <li>
		          <label>订单号：</label><s:textfield name="orderId"/>
		      </li>
		      <li>
		          <label>下单时间：</label><input id="createTimeStart" type="text" name="createTimeStart" class="snspt_init_input" value="${createTimeStart }"> 
		          		至 <input type="text" id="createTimeEnd" name="createTimeEnd" class="snspt_init_input" value="${createTimeEnd }">
		      </li>
	          <li>
		          <label>手机号：</label><s:textfield name="moblieNumber"/>
		      </li>
	          <li>
		          <label>游玩状态：</label>
					<s:select name="status" list="statusList" listKey="code" listValue="name" />
		      </li>
	          <li>
		          <label>取票人姓名：</label><s:textfield name="travellerName"/>
		      </li>
		      <li>
		          <label>产品名称：</label>
		          	<s:if test="ebkProductList != null">
		          		<s:select id="productId" name="productId" list="ebkProductList" listKey="metaProductId" listValue="productName" headerKey="" headerValue="全部"/>
				  	</s:if>
				  	<s:else>
				  		<select id="productId" name="productId">
			              	<option value="">全部</option>
			            </select>
				  	</s:else>
				  	
				  	<s:if test="ebkMetaBranchList != null">
		          		<s:select id="branchId" name="branchId" list="ebkMetaBranchList" listKey="metaBranchId" listValue="branchName" headerKey="" headerValue="全部"/>
				  	</s:if>
				  	<s:else>
				  		<select id="branchId" name="branchId" disabled="disabled">
		                	<option value="">全部</option>
		              	</select>
				  	</s:else>
		      </li> 
	          <li>
		          <label>辅助码：</label><s:textfield name="passport"/>
		      </li>
		      <li>
		          <label>订单是否取消：</label>
		          	<s:select name="orderStatus" list="orderStatusList" listKey="code" listValue="name" headerKey="" headerValue="全部"/>
		      </li>
		      <li>
		          <label>付款方式：</label>
	              	<s:select name="paymentTarget" list="#{'':'全部','TOLVMAMA':'在线付款','TOSUPPLIER':'景区现付'}"/>
		      </li>
		      <li>
		      </li>
		      <li class="anspt_alR"><a href="javascript:submitFn()" class="snspt_Btn snspt_srBtn">查找</a></li>
		  </ul>	
		</form>
	  <div class="tableWrap">
		<p class="table01Header"><a href="javascript:void(0)" id="doExport">下载订单列表</a>订单列表</p>
		<table width="960" border="0" class="table01">
		    <tr>
		      <th width="60">订单号</th>
              <th width="60">订单状态</th>
		      <th width="80">游玩时间</th>
		      <th width="80">取票人</th>
		      <th width="60">手机号</th>
		      <th width="120">产品名称</th>
		      <th width="120">订购票数</th>
              <th width="120">实际取票数</th>
		      <th width="60">游玩状态</th>
		      <th width="60">付款方式</th>
              <th>操作</th>
		    </tr>
		    <s:if test="performDetailPage.items != null">
		    	<s:iterator id="item" value="performDetailPage.items">
		    		<tr>
				      <td>
					      <a href="javascript:void(0)" class="snspt_ordindex" orderId="${orderId }">
				      		${orderId }
				      	  </a>
				      	  <s:if test="isAperiodic=='true'">
							<span style="color:red;"><br>(期票)</span>
						  </s:if>
					 	   <input id="${orderId}" type="hidden" value="${orderItemMetaId }" />
			      	  </td>
				      <s:if test="orderStatus=='CANCEL'">
						<td class="f_red">
							取消
					  	</td>
					  </s:if>
					  <s:elseif test="orderStatus=='FINISHED'">
					  	<td>完成</td>
					  </s:elseif>
					  <s:else>
					  	<td>正常</td>
					  </s:else>
				      <td>${strDeadlineTime }</td>
				      <td>${contactName }</td>
				      <td>${contactMobile }</td>
				      <td>
				         ${metaProductName }
			          </td>
				      <td><ul>
			               <li>${quantity }张（${adultQuantity }成人,${childQuantity }儿童）</li>
			               </ul>
		              </td>
		              <td><ul>
			               <li>
			               		${realQuantity }张（${realAdultQuantity }成人,${realChildQuantity }儿童）
			               	</li>
			               </ul>
		              </td>
		              <s:if test="isPass || performStatus=='PERFORMED'">
		              	<td>已游玩</td>
		              </s:if>
		              <s:else>
		              	<td class="f_red">未游玩</td>
		              </s:else>
				      <td>
						<s:if test="paymentTarget=='TOLVMAMA'">
		              		在线付款
		              	</s:if>
		              	<s:else>
		              		景区现付
		              	</s:else>
					  </td>
				      <td class="snspt_optwrap">
		                 <a href="javascript:void(0)" data_attr="{orderItemMetaId:${orderItemMetaId}}" class="msg_getTkt">取票人留言<s:if test="userMemo!=null">(1)</s:if></a>
		                 <a href="javascript:void(0)" data_attr="{orderItemMetaId:${orderItemMetaId}}" class="msg_optr">操作人留言<s:if test="performMemo!=null">(1)</s:if></a>
		                 <a href="javascript:void(0)" data_attr="{orderItemMetaId:${orderItemMetaId}}" class="log_optr">操作日志</a>
		              </td>
				    </tr>
		    	</s:iterator>
		    </s:if>
        </table>    
        <div class="table01Footer" style="padding-left: 0px;">
			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(performDetailPage,'')"/>
	    </div>
       </div><!--tableWrap--> 
	</div><!--order_all-->
</div><!--wrap-->

<div class="snspt_pop_contwrap1">
</div>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<script type="text/javascript">
	$.validator.addMethod("numberCheck", function(value, element) {       
	    return this.optional(element) || /^\s*(([0-9])|([1-9][0-9]*))\s*$/.test(value);       
	}, "请输入数字."); 
	$("#queryAllPassPortForm").validate({
		rules: {    
			"orderId":{
				numberCheck:true
			},
			"passport":{
				numberCheck:true
			} 
		}, 
		messages: {    
			"orderId":{
				numberCheck:"请输入数字."
			},
			"passport":{
				numberCheck:"请输入数字."
			} 
		}
	});
	
	function submitFn(){
			$('#queryAllPassPortForm').submit();
	}
	$(document).ready(function(){
		
		$('#playTimeStart,#playTimeEnd,#createTimeStart,#createTimeEnd').datepicker({ changeMonth:true,changeYear:true,dateFormat: "yy-mm-dd"});
		
		$('#productId').change(function(){
			var value=$('#productId').val();
			if(value!=''){
				$.ajax({
			   		url: '${contextPath }/eplace/getEbkMetaBranchByProductId.do',
			    	dataType: 'html',
			  		data: {productId:value},
			  		success: function(datas){
			  			$('#branchId').attr("disabled",false);
			  			$('#branchId').html("<option value=''>全部</option>");
			  			var arr=eval(datas);
			  			$.each(arr,function(e,v){
			  				$('#branchId').append("<option value='"+v.id+"'>"+v.text+"</option>");
			  			});
					},
					error : function(){
						alert("通关数据传输出错");
					} 
				});
			}else{
				$('#branchId').attr("disabled","disabled");
				$('#branchId').html("<option value=''>全部</option>");
			}
		});
		
		$('#doExport').click(function(){
			var input=$('#queryAllPassPortForm').find('input');
			var select=$('#queryAllPassPortForm').find('select');
			var paramsStr='';
			$.each(input,function(e,v){
				if(e>0)paramsStr+='&';
				paramsStr+=$(v).attr('name')+'='+v.value;
			});
			$.each(select,function(e,v){
				paramsStr+='&'+$(v).attr('name')+'='+v.value;
			});
			var url='${contextPath }/eplace/doExcel.do?'+paramsStr;
			window.open(url,'_blank');
		});
	});
	$("#orderId,#moblieNumber,#travellerName,#passport").change(function(){
		$("#playTimeStart").val("");
		$("#playTimeEnd").val("");
		$("#createTimeStart").val("");
		$("#createTimeEnd").val("");
		$("#status").val("");
		$("#productId").val("");
		$("#productId").change();
		$("#orderStatus").val("");
		$("#paymentTarget").val("");
		$("#status").val("");
	});
</script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
<script type="text/javascript" src="${contextPath }/js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="${contextPath }/js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${contextPath }/js/base/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${contextPath }/js/eplace/snspt_pop.js"></script>
</body>
</html>