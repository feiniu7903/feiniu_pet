<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的积分-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-points">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap"><p><a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a> &gt; <a class="current">我的积分</a></p></div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">


<!-- 我的积分>> -->
<div class="ui-box mod-top mod-info">
	<div class="ui-box-container clearfix">
    	<div class="info-detail fl">
        	<div class="hv_blank fr"></div>
        	<h3>积分余额</h3>
            <p><dfn class="info-num"><i>${currentPoint}</i></dfn><a href="http://www.lvmama.com/points" class="ui-btn ui-btn5"><i>兑 换</i></a><a target="_blank" href="http://www.lvmama.com/points/help">[积分说明]</a></p>
            <p>已使用积分：<dfn><i>${usedPoint}</i></dfn><span class="hv_line"></span>年底到期积分：<dfn><i>${aboutToExpiredPoint}</i></dfn></p>
        </div>
        
        <div class="info-tips lv-cc fl">
	        <h4>提示：</h4><a target="_blank" href="http://www.lvmama.com/info/hytuijian/2013-1106-203539.html" target="_blank">2013年底积分过期公告!</a>
        </div>
    </div>
</div>


<div id="lv-tabs" class="ui-box mod-edit points-edit">
	<div class="ui-tab-title"><h3>积分明细</h3>
		<ul class="lv-tabs-nav hor">
		
			 <@s.if test="userPointLogWithDescriptionList==null && shopOrderList==null">
				<li class="lv-tabs-nav-selected"><a href="/myspace/account/points.do?userPointPageIndex=0&orderPageIndex=0">积分记录(${userPointCount})</a></li>
				<li><a href="/myspace/account/points_order.do?userPointPageIndex=1&orderPageIndex=1">积分兑换(${orderCount})</a></li>
			 </@s.if>
			 
			<@s.elseif test="userPointLogWithDescriptionList!=null">
				<li class="lv-tabs-nav-selected"><a href="/myspace/account/points.do?userPointPageIndex=${userPointPageIndex}&orderPageIndex=${orderPageIndex}">积分记录(${userPointCount})</a></li>
				<li><a href="/myspace/account/points_order.do?userPointPageIndex=${userPointPageIndex}&orderPageIndex=${orderPageIndex}">积分兑换(${orderCount})</a></li>
			</@s.elseif>
			
			<@s.elseif test="shopOrderList!=null">
				<li><a href="/myspace/account/points.do?userPointPageIndex=${userPointPageIndex}&orderPageIndex=${orderPageIndex}">积分记录(${userPointCount})</a></li>
				<li class="lv-tabs-nav-selected"><a href="/myspace/account/points_order.do?userPointPageIndex=${userPointPageIndex}&orderPageIndex=${orderPageIndex}">积分兑换(${orderCount})</a></li>
			</@s.elseif>
		</ul>
	</div>
	
 <@s.if test="userPointLogWithDescriptionList!=null">
	<div id="tabs-1" class="lv-tabs-box lv-tabs-box-selected">
    	<!-- 获取积分>> -->
    	<div class="points-box">
	        <table class="lv-table points-table">
		        <colgroup>
		        <col class="lvcol-1">
		        <col class="lvcol-2">
		        <col class="lvcol-3">
		        <col class="lvcol-4">
		        </colgroup>
	        	<thead>
				    <tr class="thead">
				        <th>时间</th>
				        <th>获取积分</th>
				        <th>事由</th>
				        <th>备注</th>
				    </tr>
	              </thead>
	              <tbody>
			<@s.iterator id="upld" value="userPointLogWithDescriptionList" status="index">
	                  <tr>
	                    <td class="date"><@s.property value="zhCreatedDate" /></td>
	                    <td class="price"><@s.property value="point"/></td>
	                    <td><@s.property value="description"/></td>
	                    <td>
	                    	<@s.if test="ruleId=='POINT_FOR_ONLINE_ACTIVITY'"><@s.property value="memo"/></@s.if>
	                    </td>
	                  </tr>
			</@s.iterator>          
				</tbody>
			</table>
        	<div class="pages">
				<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>	
			</div> 
        </div>
        <!-- <<获取积分 -->
	</div>
 </@s.if>

<@s.elseif test="shopOrderList!=null">
    <div id="tabs-2" class="lv-tabs-box lv-tabs-box-selected">
    	<!-- 使用积分>> -->
    	<div id="points-tooltip" class="points-box">
	        <table class="lv-table points-table">
		        <colgroup>
		        <col class="lvcol-1">
		        <col class="lvcol-2">
		        <col class="lvcol-3">
		        <col class="lvcol-4">
		        <col class="lvcol-5">
		        <col class="lvcol-6">
		        </colgroup>
	        	<thead>
				    <tr class="thead">
				        <th>时间</th>
				        <th>消耗积分</th>
				        <th>商品名称</th>
				        <th>订单ID</th>
				        <th>兑换数量</th> 
				        <th>操作</th>
				    </tr>
	              </thead>
	              <tbody>
			<@s.iterator id="orderListId" value="shopOrderList" status="index">
					<tr> 
					    <td class="date"><@s.date format="yyyy-MM-dd HH:mm" name="createTime"/></td>
				        <td class="price"><@s.property value="actualPay"/></td>
					    <td><@s.property value="productName"/></td>
					    <td><@s.property value="orderId" /></td>
					    <td><@s.property value="quantity"/></td>
					    <td><a class="view_detail" href="http://www.lvmama.com/myspace/shop/orderdetail/${orderId}">查看详情</a>
		                   <#--
		                    <div class="tooltip points-tooltip ie6png">
			                    <p>订单ID：<@s.property value="orderId" /><br>
								收件人姓名：<@s.property value="name"/><br>
								收件人地址：<@s.property value="address"/><br>
								收件人手机：<@s.property value="mobile"/><br> 
								商品信息：<@s.property value="productInfo"/><br>
								订单备注：<@s.property value="remark"/></p>
		                    </div> 
		                    -->
	                    </td>
					</tr>
			</@s.iterator>                 
				</tbody>
			</table>
			<div class="pages">
				<@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
			</div>
        </div>
        <!-- <<使用积分 -->
	</div>
</@s.elseif>
</div>
<!-- <<我的积分 -->

			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<script>
	if("<@s.property value="userPointLogWithDescriptionList"/>"!=""){
		cmCreatePageviewTag("我的积分-积分记录", "D0001", null, null);
	}else if("<@s.property value="shopOrderList"/>"!=""){
		cmCreatePageviewTag("我的积分-积分兑换", "D0001", null, null);
	}
      
 	</script>
</body>
</html>