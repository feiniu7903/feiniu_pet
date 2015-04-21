<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ebk" uri="/tld/lvmama-ebk-tags.tld"%>
	<script type="text/javascript">
		basePath = "${contextPath}";
	</script>
	<div class="header">
	    <span  class="logotit" >供应商管理系统</span >
	    <div style="display: none;" id="sessionUserName">${sessionUser.userName}</div>
	    <div class="header_side">
	        <em>
	       	 	${sessionUser.supplierName}
	       	 </em>
	        <span>欢迎您，
	        	<s:if test="sessionUser.name == null">${sessionUser.userName}</s:if>
	        	<s:else>${sessionUser.name}</s:else>
	        	</span>
	        <a href="${contextPath}/ebk_user/to_change_pwd.do">修改密码</a>
	        <a href="${contextPath}/loginOut.do">退出</a>
	        <span class="header_conn">
	             	合作方：<i>${sessionUser.lvmamaContactName }</i>        
	             	电话：<i>${sessionUser.lvmamaContactPhone }</i>
	        </span>
	    </div>
	</div>
<!--以上是公用部分-->
	<div class="nav_all">
		<ul id="menuUl" class="nav_left">
			<!-- EBOOKING酒店 -->
			<ebk:perm permissionId="7" >
				<li class="nav_index"><a href="${contextPath}/ebookingindex.do">首页</a></li>
			</ebk:perm>
			<ebk:perm permissionId="33" >
				<li class="nav_dingdan nav_cpgl">
		        	<a href="#">产品管理</a>
		            <div class="nav_dd_b">
               			<ebk:perm permissionId="34" >
		                	<a href="${basePath}product/queryProduct.do?ebkProductViewType=SURROUNDING_GROUP" class="nav_dd_b_a" >周边跟团游</a>
						</ebk:perm>
						<ebk:perm permissionId="35" >
							<a href="${basePath}product/queryProduct.do?ebkProductViewType=DOMESTIC_LONG">国内长线</a>
						</ebk:perm>
						<ebk:perm permissionId="36" >
							<a href="${basePath}product/queryProduct.do?ebkProductViewType=ABROAD_PROXY">出境代理</a>
						</ebk:perm>
             			<%--
             			<ebk:perm permissionId="31" >
		            		<a class="nav_dd_b_a" href="${basePath}product/query.do?ebkProductViewType=HOTEL">酒店</a>
		               </ebk:perm>
		                --%>
						<div class="zhex"></div>
		            </div>
        		</li>
			</ebk:perm>
 			<ebk:perm permissionId="8" >
			    <li class="nav_dingdan nav_ddgl">
		        	<a class="nav_dd" href="#"><span class="dd_l_txet">订单处理</span>
		        		<ebk:perm permissionId="13,14" >
				        	<p class="nav_dd_r">
				        		<span id="menuConfirmOrderCountSpan">0</span>
				        	</p>
			        	</ebk:perm>
		        	</a>
		            <div class="nav_dd_b">
             			<%-- <ebk:perm permissionId="13" >
		            		<a class="nav_dd_b_a" href="${contextPath }/ebooking/task/confirmTaskList.do">酒店订单处理(<samp id="menuConfirmHotelOrderCountSpan">0</samp>)</a>
		               </ebk:perm> --%>
               			<ebk:perm permissionId="14" >
		                	<a href="${contextPath }/ebooking/task/confirmRouteTaskList.do">线路订单处理(<samp id="menuConfirmRouteOrderCountSpan">0</samp>)</a>
						</ebk:perm>
						<ebk:perm permissionId="3" >
							<a href="${contextPath }/eplace/passOrderIndex.do">门票订单处理</a>
						</ebk:perm>
						<div class="zhex"></div>
		            </div>
        		</li>
			</ebk:perm>
			<ebk:perm permissionId="9" >
				<li class="nav_fjwh"><a href="${contextPath }/ebooking/houseprice/changePriceSuggest.do">变价申请</a></li>
			</ebk:perm>
			<ebk:perm permissionId="10" >
				<li class="nav_dingdan nav_ftwh">
		        	<a href="#">库存维护</a>
		            <div class="nav_dd_b">
             			<%-- <ebk:perm permissionId="15" >
		            		<a class="nav_dd_b_a" href="${contextPath }/ebooking/housestatus/applyHouseRoomStatus.do">酒店房态维护</a>
		              	</ebk:perm> --%>
           	 			<ebk:perm permissionId="16" >
		              	  	<a href="${contextPath }/ebooking/routeStock/maintainRouteStockStatus.do">线路库存维护</a>
						</ebk:perm>
						<div class="zhex"></div>
		            </div>
        		</li>
			</ebk:perm>
			<ebk:perm permissionId="17" >
				<li class="nav_dingdan nav_sjgl">
		        	<a href="#">数据管理</a>
		            <div class="nav_dd_b">
             			<ebk:perm permissionId="18" >
		            		<a class="nav_dd_b_a" href="${contextPath }/report/product/onSaleProductList.do">产品表</a>
		              	</ebk:perm>
           	 			<ebk:perm permissionId="19" >
		              	  	<a href="${contextPath }/report/product/productSalesList.do">收客表</a>
						</ebk:perm>
           	 			<ebk:perm permissionId="20" >
		              	  	<a href="${contextPath }/report/product/productVisitorList.do">出团游客表</a>
						</ebk:perm>
						<div class="zhex"></div>
		            </div>
        		</li>
			</ebk:perm>
			<ebk:perm permissionId="11" >
				<li class="nav_ggxx"><a href="${contextPath }/announcement/ebkAnnouncementList.do">公告信息</a></li>
			</ebk:perm>
			<!-- 系统权限 -->
			<ebk:perm permissionId="1" >
				<li class="nav_yhgl"><a href="${contextPath}/ebk_user/index.do?valid=true">用户管理</a></li>
			</ebk:perm>
		</ul>
	</div>