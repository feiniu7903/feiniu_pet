<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
			<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate"> 
		<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT"> 
		<META HTTP-EQUIV="expires" CONTENT="0"> 	
	</head>
	<body>
		<div id="modifyMarkCouponCodeDiv" style="display: none"></div>
		<table class="p_table table_center">
			 <thead>
			 	<tr>
			 		<th>号码</th>
			 		<th>是否已用</th>
			 		<th>开始时间</th>
			 		<th>结束时间</th>
			 		<th>创建时间</th>
			 		<th>操作</th>
			 	</tr>
			 </thead>
			 <tbody>
			 	<s:iterator value="markCouponCodeList" var="markCouponCode">
			 		<tr>
			 			<td>${markCouponCode.couponCode} </td>
			 			<td>${markCouponCode.zhUsed} </td>
			 			<td><s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 			<td><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 			<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			 			<td>
			 			<s:if test="#markCouponCode.used == 'false'">
			 				<a href="javascript:modifyMarkCouponCode('${markCouponCode.couponCodeId}','${markCouponCode.couponCode}');">修改</a>
			 			</s:if>
			 			<s:else>
			 				修改
			 			</s:else>
			 			
			 			</td>
			 		</tr>
			 	</s:iterator>
			 	<tr>
		     		<td colspan="2"> 
		     			总条数：<s:property value="pagination.totalResultSize"/>			
		     		</td>
		     		<td colspan="5" align="left">
		     			<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage,'js')"/>
		     		</td>
		    	</tr>
			 </tbody>
		</table>

		<!-- 修改优惠码. -->
		<div id="modifyMarkCouponCode" style="display: none">
			<span>新的CODE:</span><span id="markCouponCodeStartWith"></span>
			<input id="markCouponCodeFragment" name="markCouponCodeFragment" type="text" maxlength="15"/>
			<input id="couponId" name="couponId" type="hidden"/>
			<input id="markCouponCodeBak" name="markCouponCodeBak" type="hidden" />
		</div>
		
		<script type="text/javascript">
			function refreshDiv(page) {
				_page = page
				$("#markCouponCodesDiv").load("<%=basePath%>/mark/coupon/queryMarkCouponCodes.do?couponId=${couponId}&page=" + _page + "&date=" + (new Date).getTime(),function(){});
			}
			
			//修改优惠码.
			function modifyMarkCouponCode(couponCodeId, couponCode) {
				$("#couponId").val("${couponId}");
	        	var markCouponCodeStartWith = "${markCoupon.couponType}" + "${markCoupon.firstCode}";
	        	$("#markCouponCodeStartWith").html(markCouponCodeStartWith);
	        	var markCouponCodeFragment = couponCode.substring(markCouponCodeStartWith.length,couponCode.length);
				$("#markCouponCodeFragment").val(markCouponCodeFragment);
	        	$("#markCouponCodeBak").val(couponCode);
	        	
	        	var $dlg=$("#modifyMarkCouponCode"); 
	        	$dlg.dialog({
	    			modal:true,
	    			title:"修改优惠码",
	    			width:650,
	    			buttons:{
	    				"修改":function(){
	    					var couponId = $("#couponId").val();
	    					var markCouponCode = markCouponCodeStartWith + $("#markCouponCodeFragment").val();
	    					var markCouponCodeBak = $("#markCouponCodeBak").val();
	    					if (markCouponCode == markCouponCodeBak) {
	    						$dlg.dialog("close");
	    						return;
	    					}
	    					if ($("#markCouponCodeFragment").val() == null || $("#markCouponCodeFragment").val() == "") {
	    						alert("请输入有效的优惠码!");
	    						return;
	    					}
	    					
	    					$.ajax({
	    						url:"<%=basePath%>/mark/coupon/modifyMarkCouponCode.do",
	    						data:{couponId:couponId,couponCodeId:couponCodeId,couponCode:markCouponCode},
	    						type:"POST",
	    						dataType:"json",
	    						success:function(dt){
	    							if (dt.success) {
	    								alert("优惠码修改成功!");
		    							$dlg.dialog("close");
		    							$("#markCouponCodesDiv").load("<%=basePath%>/mark/coupon/queryMarkCouponCodes.do?couponId=${couponId}&page=" + _page+"&date="+(new Date).getTime(),function(){});
	    							} else {
	    								alert(dt.errorMessage);
	    							}
	    						}
	    					});
	    				}
	    			}
	    		});		
			}
			
		</script>
	</body>
</html>


