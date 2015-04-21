<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的点评-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-comment">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>&gt;
				<a class="current">我的点评</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
	 
			<!-- 我的点评>> -->
			<div class="ui-box comment-box">
				<div class="ui-box-title"><h3>我的点评</h3></div>
				<ul class="integral">
					<li>奖金账户余额：<dfn><i>&yen;${awardBalanceYuan?number?string("0.00")}</i></dfn>　<a href="http://www.lvmama.com/public/help_429" target="_blank">什么是奖金？</a></li>
                    <li>您的积分余额：<dfn><i>${currentPoint?default("0")}</i>分</dfn></li>
					<li>您成功发表体验点评并审核通过后，点评奖金会在 3 个工作日内返至您的现金账户。(注：节假日点评审核时间会有所延迟，望谅解)</li>
					<li><b class="tips-c1">注：</b>景区支付用户，请至“<a href="http://www.lvmama.com/comment/">点评频道</a>”发表出游感受。</li>
				</ul>
			</div><!-- <<积分信息 -->
			<!-- 点评列表>> -->
			<div id="lv-tabs" class="ui-box comment-box">
				<div class="ui-tab-title"><span class="fr">您曾经购买产品的点评……</span>
				
				<ul class="lv-tabs-nav hor">	
					<@s.if test="needProductCommentPageConfig.items==null && alreadyCommentPageConfig.items==null">
						<li class="lv-tabs-nav-selected"><a href="http://www.lvmama.com/myspace/share/comment.do?currentNeedProductCommentPage=0&currentAlreadyCommentPage=0">待点评(${needProductCommentCount?default("0")})</a></li>
						<li><a href="http://www.lvmama.com/myspace/share/alreadyComment.do?currentNeedProductCommentPage=1&currentAlreadyCommentPage=1">已点评(${countOfUserCmt?default("0")})</a></li>
					 </@s.if>
					 
					<@s.elseif test="needProductCommentPageConfig.items!=null">
						<li class="lv-tabs-nav-selected"><a href="http://www.lvmama.com/myspace/share/comment.do?currentNeedProductCommentPage=${currentNeedProductCommentPage}&currentAlreadyCommentPage=${currentAlreadyCommentPage}">待点评(${needProductCommentCount?default("0")})</a></li>
						<li><a href="http://www.lvmama.com/myspace/share/alreadyComment.do?currentNeedProductCommentPage=${currentNeedProductCommentPage}&currentAlreadyCommentPage=${currentAlreadyCommentPage}">已点评(${countOfUserCmt?default("0")})</a></li>
					</@s.elseif>
					
					<@s.elseif test="alreadyCommentPageConfig.items!=null">
						<li><a href="http://www.lvmama.com/myspace/share/comment.do?currentNeedProductCommentPage=${currentNeedProductCommentPage}&currentAlreadyCommentPage=${currentAlreadyCommentPage}">待点评(${needProductCommentCount?default("0")})</a></li>
						<li class="lv-tabs-nav-selected"><a href="http://www.lvmama.com/myspace/share/alreadyComment.do?currentNeedProductCommentPage=${currentNeedProductCommentPage}&currentAlreadyCommentPage=${currentAlreadyCommentPage}">已点评(${countOfUserCmt?default("0")})</a></li>
					</@s.elseif>
				</ul>		
				</div>

					
				<@s.if test="needProductCommentPageConfig.items != null && needProductCommentPageConfig.items.size() > 0">
				<div id="tabs-1" class="ui-tab-box">
					<div class="comment-list">
					<table class="lv-table comment-table comment-todo">
						<thead>
							<tr class="thead">
								<th class="product-name">产品名称</th>
								<th class="order-num">订单号</th>
								<th class="product-num">产品数量</th>
								<th class="oper">操作</th>
							</tr>
						 </thead>
						 <tbody class="tbody">
						 <@s.iterator id="product" value="needProductCommentPageConfig.items" status="index">
							<tr>
								<td class="product-name">
								<#if onLine =="true">
								        <a href="http://www.lvmama.com/product/${product.productId}/comment" target="_blank" class="cmtpro_link">${product.titleName}</a>
								<#else>
		                          		${product.titleName}
		                        </#if>
								
								<p class="cmt_situation">
								    <span>游玩日期：${product.orderVisitTime?date?string("yyyy-MM-dd")}  
								             提示：<@s.if test="cashRefund!=0">
								      在游玩日过后三个月内点评将会获得<dfn>&yen;<i>${product.cashRefundYuan?number?string("0.00")}</i></dfn>奖金，100积分</@s.if><@s.else>点评将会获得100积分</@s.else></span>
		                           </br> <span class="has_click">未点评</span><span>未审核</span>
		                            <@s.if test="cashRefund > 0">
		                               <span>未返奖金</span>
		                            </@s.if>
		                            <span>未返积分</span>
		                        </p>
								</td>
								<td class="order-num">${product.orderId}</td>
								<td class="product-num">${product.countOfProduct}</td>
								<td class="oper"><a href="http://www.lvmama.com/comment/writeComment/fillComment.do?productId=${product.productId}&amp;orderId=${product.orderId}&amp;productType=${product.productType}" target="_blank" class="cmtclick_now">立即点评</a></td>
							</tr>
					     </@s.iterator>
						</tbody>
					</table>
				    <div class="pages">
				      <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(needProductCommentPageConfig.pageSize,needProductCommentPageConfig.totalPageNum,needProductCommentPageConfig.url,needProductCommentPageConfig.currentPage)"/>	
			       </div> 
				</div>
				</div>
                             </@s.if>


					
				<@s.if test="alreadyCommentPageConfig.items != null && alreadyCommentPageConfig.items.size() > 0">
				<div id="tabs-2" class="ui-tab-box">
					<div class="comment-list">
					<table class="lv-table comment-table">
						<thead>
							<tr class="thead">
								<th class="product-name">名称</th>
								<th class="reply">回复</th>
								<th class="comment-type">点评类型</th>
								<th class="order-num">订单号</th>
								<th class="oper">操作</th>
							</tr>
						 </thead>
						 <tbody class="tbody">
						 <@s.iterator id="comment" value="alreadyCommentPageConfig.items" status="index">
							<tr>
								<td class="product-name">
								<@s.if test="productId!=null">
								<a href="http://www.lvmama.com/product/${comment.productId}/comment" target="_blank" class="cmtpro_link">${comment.productName}</a>
								</@s.if>
		                        <@s.else>
		                        <a href="http://www.lvmama.com/comment/${comment.placeId}-1" target="_blank" class="cmtpro_link">${comment.placeName}</a>
		                        </@s.else>
								
								<p class="cmt_situation">
		                            <span class="has_click">已点评</span>
		                            <#if isAudit=="AUDIT_GOING"><span class="has_click">待审核</span></#if>
		                            <#if isAudit=="AUDIT_FAILED"><span class="has_click">审核不通过</span></#if>
		                            <#if isAudit=="AUDIT_SUCCESS"><span class="has_click">审核通过</span></#if>
		                            <#if cashRefund&gt;0><span class="has_click">已返奖金</span></#if>
		                        	<#if point&gt;0> <span class="has_click">已返积分 </span> <#else><span >未返积分 </span></#if>
		                        	
		                        	<#if cashRefund&gt;0>
		                        	提示：您将获得
		                           		 <b class="price">&yen;${comment.cashRefundYuan?number?string("0.00")}</b>奖金
		                           	</#if>
		                        </p>
								</td>
								<td class="reply">${comment.replyCount?default("0")}</td>
								<td class="comment-type">${comment.chCmtType}</td>
								<td class="order-num">${orderId?if_exists}</td>
								<td class="oper"><a href="http://www.lvmama.com/comment/${comment.commentId}" target="_blank">查看详情</a></td>
							</tr>
						   </@s.iterator>
						</tbody>
					</table>
					
				   <div class="pages">
				      <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(alreadyCommentPageConfig.pageSize,alreadyCommentPageConfig.totalPageNum,alreadyCommentPageConfig.url,alreadyCommentPageConfig.currentPage)"/>	
			       </div> 
					</div>
				</div>	
			 </@s.if>
			</div><!-- <<点评列表 -->
			
			<!-- 点评广告位\\ --> 
<div class="lv-ad mod-c-ad mt10 mb10"> 
    <!--AdForward Begin:-->
    <script type="text/javascript" src="http://lvmamim.allyes.com/main/s?dXNlcj1sdm1hbWFfMjAxMnxvdGhlcnBhZ2VfMjAxMnxvdGhlcnBhZ2VfYWNjb3VudF8yMDEyX2NtdGJhbm5lciZkYj1sdm1hbWltJmJvcmRlcj0wJmxvY2FsPXllcyZqcz1pZQ==" charset="gbk"></script>
    <!--AdForward End-->
</div> 
			
			<!-- 点评景点>> -->
			<div class="ui-box comment-box">
				<div class="ui-box-title"><span class="fr">您可能去过的相关景区……</span><h3>点评景区</h3></div>
				<@s.if test="needPlaceCommentPageConfig.items != null && needPlaceCommentPageConfig.items.size() > 0">
				<ul class="pro_scenic clearfix">
				   <@s.iterator id="place" value="needPlaceCommentPageConfig.items" status="index">
					<li>
						<a href="http://www.lvmama.com/comment/${place.placeId}-1" target="_blank" class="pics">		
						  <#if place.placeSmallImage!=null || place.placeSmallImage!="">
					        <img src='http://pic.lvmama.com${place.placeSmallImage}' height="100" width="138"/>
					      <#else>
					        <img src='http://www.lvmama.com/dest/img/myspace/img_120_90.jpg' height="100" width="138"/>
					      </#if>		
						</a>
						<a href="http://www.lvmama.com/comment/${place.placeId}-1" target="_blank">${place.titleName}</a><br>
						<span>总体评分：${place.avgScore?default("0")}&nbsp;&nbsp;|&nbsp;&nbsp;点评：${place.commentCount}&nbsp;&nbsp;|
						<a href="http://www.lvmama.com/dest/${place.pinYinUrl}/guide" target="_blank">攻略</a></span><br>
						<a href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.placeId}" target="_blank" class="cmt_write">
						写点评
						</a>
					</li>
				   </@s.iterator>
				</ul>
				   <div class="pages">
				      <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(needPlaceCommentPageConfig.pageSize,needPlaceCommentPageConfig.totalPageNum,needPlaceCommentPageConfig.url,needPlaceCommentPageConfig.currentPage)"/>	
			       </div>
			     </@s.if>
				
			</div><!-- <<点评景点 -->
			
			<!-- <<我的点评 -->

		</div>
	</div>
<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
<script src="http://pic.lvmama.com/js/new_v/my_lvmama/cmt.js?r=7071"></script>
	<script>
	if("<@s.property value="needProductCommentPageConfig.items"/>"!=""&&"<@s.property value="needProductCommentPageConfig.items.size()"/>" >0){
		cmCreatePageviewTag("我的点评-待点评", "D0001", null, null);
	}else if("<@s.property value="alreadyCommentPageConfig.items"/>"!=""&&"<@s.property value="alreadyCommentPageConfig.items.size()"/>" >0){
		cmCreatePageviewTag("我的点评-已点评", "D0001", null, null);
	}
	
	</script>
</body>
</html>