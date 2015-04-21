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
<script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>
 
<body>
<div id="addOrModifyProdBusinessCouponDiv" style="display: none"></div>

<div class="main main07">
	<div class="row1">
	   	<h3 class="newTit">销售产品信息 </h3>
   </div>

   <div class="row2">
   		<table border="0" cellspacing="0" cellpadding="0" id="place_tb" class="newTable">
          <tr class="newTableTit">
            <td>优惠绑定</td>
            <td>优惠类型</td>
            <td>优惠名称</td>
            <td>下单有效期</td>                        
            <td>游玩日期</td>
            <td>提前预定天数</td>
            <td>创建时间</td>
            <td>状态</td>
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
</div>

<script type="text/javascript">
	        $(function(){
	            //新增优惠(活动).
	            $("#addOrModifyProdBusinessCoupon").click(function() {
	            	$("#addOrModifyProdBusinessCouponDiv").load("<%=basePath%>prod/addOrEditProdBusinessCoupon.do?metaType=${metaType}&productId=${productId}&_=" + (new Date).getTime(),function() {
	            		$(this).dialog({
	            			modal:true,
	            			title:"新增优惠(活动)",
	            			width:950,
	            			height:450
	                	});
	            	});
	            });
	        });
	        
	        //修改优惠券(活动).
	        function editBindingProduct(businessCouponId) {
	        	$("#addOrModifyProdBusinessCouponDiv").load("<%=basePath%>prod/addOrEditProdBusinessCoupon.do?metaType=${metaType}&productId=${productId}&businessCouponId="+businessCouponId+"&_=" + (new Date).getTime(),function() {
	        		$(this).dialog({
	        			modal:true,
	        			title:"修改优惠(活动)",
	        			width:950,
	        			height:650
	            	});
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