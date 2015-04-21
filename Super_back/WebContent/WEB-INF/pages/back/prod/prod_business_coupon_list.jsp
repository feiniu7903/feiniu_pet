<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——优惠管理</title>
<%@ include file="/WEB-INF/pages/back/base/jquery.jsp"%>
<%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp"%>
		<%@ include file="/WEB-INF/pages/back/base/timepicker.jsp" %>
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>
 
<body>
<div id="addOrModifyProdBusinessCouponDiv" style="display: none"></div>

<div class="main main07">
<s:if test="metaType=='SALES'">
	<div class="row1">
	   	<h3 class="newTit">销售产品信息
    	<s:if test="product != null">
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productId != null">
    			产品ID:${product.productId }
    		</s:if>
    		&nbsp;&nbsp;&nbsp;
    		<s:if test="product.productName != null">
    			产品名称：${product.productName }
    		</s:if>
    	</s:if>
	   	<s:if test="product.productId != null">
	   		<jsp:include page="/WEB-INF/pages/back/prod/goUpStep.jsp"></jsp:include>
	   	</s:if>
	   	</h3>
	    <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
   </div>
   </s:if>
   <s:else>
   	<div class="row1">
    	<h3 class="newTit">采购产品信息
    	<s:if test="product != null">
    		<jsp:include page="/WEB-INF/pages/back/meta/goUpStep.jsp"></jsp:include>
    	</s:if>
    	</h3>
        <s:include value="/WEB-INF/pages/back/meta/nav.jsp"/>
   </div>
   </s:else>
   <!--row1 end-->   
    
   <div  class="row2">
   		<div>
   			<p>
   				<span style="font-family:宋体;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">
   					优惠管理
   				</span>
   			</p>
   		</div>
   		<div>
   			<form action="<%=basePath%>prod/queryProdBusinessCouponList.do" method="post">
   				<input type="hidden" name="productId" value="${productId}"/>
   				<table>
   					<tr>
   						<td width="5%">
   							<input type="hidden" name="metaType" value="${metaType}" />
   						</td>
   						<td width="20%">
   							优惠绑定：
   							<s:select list="couponTargetMap" headerKey="" headerValue="全部"
									name="couponTarget" style="width:120px">
							</s:select>
   						</td>
   						<td width="20%">
   							优惠类型：
   							<s:select list="#{'':'全部','EARLY':'早订早惠','MORE':'多订多惠','SALE':'特卖会'}" 
   									name="couponType" style="width:120px" >
   							</s:select>
   						</td>
   						<td width="20%">
   							优惠状态：
   							<s:select list="#{'':'全部','notStart':'未开始','process':'进行中','over':'已结束','false':'已关闭'}" 
   									name="valid" style="width:120px">
   							</s:select>
   						</td>
   						<td width="10%">
   							<input type="submit" class="button" value="查询" />
   						</td>
   						<td width="25%">
   							<button id="addOrModifyProdBusinessCoupon" class="button" type="button">新增优惠活动</button>
   						</td>
   					</tr>
   				</table>
   			</form>
   		</div>
   </div>
   <!-- 特卖会查询字段 -->
   <s:if test="couponTypeMark =='SALE'">
   	<div class="row2">
   		<table border="0" cellspacing="0" cellpadding="0" id="place_tb" class="newTable">
          <tr class="newTableTit">
            <td>优惠绑定</td>
            <td>优惠类型</td>
            <td>优惠名称</td>
            <td>优惠时段</td>                        
            <td>创建时间</td>
            <td>操作</td>
          </tr>
 	 	<s:iterator value="pagination.records" var="businessCoupon">
          		<tr>
          			<td>
						<s:property value="currentBindBranchNames" />
          			</td>
          			<td>
          				<s:if test="couponType=='EARLY'">
          					早订早惠
          				</s:if>
          				<s:elseif test="couponType=='MORE'">
          					多订多惠
          				</s:elseif>
          				<s:else>
          					特卖会
          				</s:else>
          				
          			</td>
          			<td><s:property value="couponName"/></td>
          			<td>
          				<s:iterator value="seckillRuleVOs" var="seckillRuleVO" status="i">
          					<s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/> ~
          					<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></br>
          				</s:iterator>
          			</td>
          			<td>
          				<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
          			</td>
          			<td>
          				<input type="hidden" id="minStartTime_${businessCoupon.businessCouponId }" value="<s:date name='seckillRuleVOs[0].startTime' format='yyyy-MM-dd HH:mm:ss' />" />
          				<a href="javascript:delProdSaleRule(${businessCouponId},(new Date).getTime());">
          					删除
          				</a>&nbsp;
          				<a href="javascript:editBindingSaleRule(${businessCouponId},(new Date).getTime());" >修改</a>&nbsp;
          				<a href="#log" class="showLogDialog" param="{'objectId':${businessCouponId},'objectType':'PROD_COUPON_BUSINESS_BIND'}" >操作日志</a>
          			</td>
          		</tr>
           </s:iterator>	
        </table>
        <table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
    </div>
   </s:if>
  <!-- 早定早惠,多定多惠查询字段 -->
   <s:else>
   <div class="row2">
   		<table border="0" cellspacing="0" cellpadding="0" id="place_tb" class="newTable">
          <tr class="newTableTit">
            <td>优惠绑定</td>
            <td>优惠类型</td>
            <td>优惠名称</td>
            <td>下单有效期</td>                        
            <td>游玩日期</td>
            <td>提前预订天数</td>
            <td>创建时间</td>
            <td>状态</td>
            <td>操作</td>
          </tr> 
          <s:iterator value="pagination.records" var="businessCoupon">
          		<tr>
          			<td>
						<s:property value="currentBindBranchNames" />
          			</td>
          			<td>
          				<s:if test="couponType=='EARLY'">
          					早订早惠
          				</s:if>
          				<s:elseif test="couponType=='MORE'">
          					多订多惠
          				</s:elseif>
          				<s:else>
          					特卖会
          				</s:else>
          				
          			</td>
          			<td><s:property value="couponName"/></td>
          			<td>
          				<s:if test="beginTime!=null">
          					<s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/> ~<br/>
          					<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
          				</s:if>
          			</td>
          			<td>
          				<s:if test="playBeginTime!=null">
          					<s:date name="playBeginTime" format="yyyy-MM-dd HH:mm:ss"/> ~<br/>
          					<s:date name="playEndTime" format="yyyy-MM-dd HH:mm:ss"/>
          				</s:if>
          			</td>
          			<td>
          				<s:if test="couponType=='EARLY'">
          					<s:property value="argumentX"/>
          				</s:if>
          			</td>
          			<td>
          				<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
          			</td>
          			<td>
          				<s:property value="stateZh"/>
          			</td>
          			<td>
          				<s:if test='couponType =="SALE"'>
          					<a href="#log" class="showLogDialog" param="{'objectId':${businessCouponId},'objectType':'PROD_COUPON_BUSINESS_BIND'}" >操作日志</a>
          				</s:if>
          				<s:else>
          					<a href="javascript:editValidStatus(${businessCouponId},(new Date).getTime());">
	          					<s:if test="valid=='false'">开启</s:if>
	          					<s:else>关闭</s:else>
	          				</a>&nbsp;
	          				<a href="javascript:editBindingProduct(${businessCouponId});" >修改</a>&nbsp;
	          				<a href="#log" class="showLogDialog" param="{'objectId':${businessCouponId},'objectType':'PROD_COUPON_BUSINESS_BIND'}" >操作日志</a>
          				</s:else>
          			</td>
          		</tr>
           </s:iterator>
           <tr>
  		   		
   		   </tr>
        </table>
        <table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
    </div>
    <!--row2 end-->
    </s:else>
</div>

<script type="text/javascript">
	        $(function(){
	        	
	        	//新增优惠(活动).
	            $("#addOrModifyProdBusinessCoupon").click(function() {
	            	 $.ajax({
	 	        	 	url: "<%=basePath%>prod/checkSaleChannel.do?metaType=${metaType}&productId=${productId}",
	 	        	 	dataType:"json",
	 	        	 	success: function(result) {
	 		        		if (result.success) {
	 		        			$("#addOrModifyProdBusinessCouponDiv").load("<%=basePath%>prod/addOrEditProdBusinessCoupon.do?metaType=${metaType}&productId=${productId}&_=" + (new Date).getTime(),function() {
	 			            		$(this).dialog({
	 			            			modal:true,
	 			            			title:"新增优惠(活动)",
	 			            			width:1000,
	 			            			height:500
	 			                	});
	 			            	});
	 		        		} else {
	 		        			alert(result.errorMessage);
	 		        		}
	 	        	 	}
	 	        	 });
	            	
	            });
	        	
	        });
	        
	        //修改优惠券(活动).
	        function editBindingProduct(businessCouponId) {
	        	$("#addOrModifyProdBusinessCouponDiv").load("<%=basePath%>prod/addOrEditProdBusinessCoupon.do?metaType=${metaType}&productId=${productId}&businessCouponId="+businessCouponId+"&_=" + (new Date).getTime(),function() {
	        		$(this).dialog({
	        			modal:true,
	        			title:"修改优惠(活动)",
	        			width:1000,
	        			height:650
	            	});
	        	});
	        }
	        //修改秒杀规则
	        function editBindingSaleRule(businessCouponId,date) {
	        	var mintStartTime = $("#minStartTime_"+businessCouponId).val()
	        	var start=new Date(mintStartTime.replace("-", "/").replace("-", "/")).getTime();
	        	if(start-date>=1800000){//大于30分钟才能修改
		        	$("#addOrModifyProdBusinessCouponDiv").load("<%=basePath%>prod/addOrEditProdSaleRule.do?metaType=${metaType}&productId=${productId}&businessCouponId="+businessCouponId+"&_=" + (new Date).getTime(),function() {
		        		$(this).dialog({
		        			modal:true,
		        			title:"修改优惠(活动)",
		        			width:1000,
		        			height:650
		            	});
		        	});
	        	}else{
	        		alert("该时段无法修改");
	        	}
	        }
	       	//删除秒杀规则
	       	function delProdSaleRule(businessCouponId,date){
	       		if(!confirm("你确定要删除该特卖活动吗？")){
	       			return;
	       		}
	       		var url="<%=basePath%>prod/delProdSaleRule.do";
	       		$.ajax({
	       			url:url,
	       			data: {"businessCouponId":businessCouponId,"date":date},
	        	 	dataType:"json",
	        	 	success: function(result) {
		        		if (result.success) {
		        			alert(result.successMessage);
		        			document.location.reload();
		        		} else {
		        			alert(result.errorMessage);
		        		}
	        	 	}
	       		});
	       	}
	        //开启/关闭此优惠券(活动).
	        function editValidStatus(businessCouponId,date) {
	        	if(!confirm("你确定要开启/关闭该优惠策略吗？")){
	        		return;
	        	}
	        	 var url = "<%=basePath%>prod/editValidStatus.do";
	        	 $.ajax({
	        	 	url: url,
	        	 	data: {"businessCouponId":businessCouponId,"date":date},
	        	 	dataType:"json",
	        	 	success: function(result) {
		        		if (result.success) {
		        			alert('更新成功');
		        			document.location.reload();
		        		} else {
		        			alert(result.errorMessage);
		        		}
	        	 	}
	        	 });
	        }
</script>
	   
</body>
</html>