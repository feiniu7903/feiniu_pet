<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>我的收藏-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-favorites">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a target='_blank' href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>&gt;
				<a class="current">我的收藏</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
 				 <!-- 我的收藏  -->
					<div id="tabs" class="ui-box favorites-edit">
						<div class="ui-tab-title"><h3>我的收藏</h3>
					    <ul class="tab-nav hor">
						    <li <@s.if test="objectType=='PLACE'">class="ui-tabs-selected ui-state-active"</@s.if>><a href="#tabs-1" >景点(<span  id="placeCount">${placeCount?if_exists}</span>)</a></li>
						    <li <@s.if test="objectType=='PRODUCT'">class="ui-tabs-selected ui-state-active"</@s.if>  ><a href="#tabs-2"  >度假线路(<span  id="productCount">${productCount?if_exists}</span>)</a></li>
						    <li <@s.if test="objectType=='GUIDE'">class="ui-tabs-selected ui-state-active"</@s.if>><a href="#tabs-3" >攻略(<span id="guideCount">${guideCount?if_exists}</span>)</a></li>
						    <li <@s.if test="objectType=='TUANGOU'">class="ui-tabs-selected ui-state-active"</@s.if>><a href="#tabs-4" >团购(<span id="tuangouCount">${tuangouCount?if_exists}</span>)</a></li>
					    </ul></div>
						<div id="tabs-1" class="ui-tab-box">
					    	<!-- 景点>> -->
					    	<@s.if test="placeCount!=null&&placeCount==0">
					    	<div class="no-list">
					            <p>您还没有收藏任何景点！</p>
					        </div>
					        </@s.if>
					        <@s.else>
					        <div class="favorites-box">
					        <table class="lv-table favorites-table">
					        	<thead>
								    <tr class="thead">
								        <th style="width:280px;">景点名称</th>
								        <th>价格</th>
								        <th>评分</th>
								        <th style="width:110px;">星级</th>
								        <th>操作</th>
								    </tr>
					            </thead>
					            <tbody>
					             <@s.iterator id="placeFavorite" value="placePage.items" var="var" status="st">
								    <tr id="placeFavorite_${id}">
									    <td><a target='_blank'  href='http://ticket.lvmama.com/scenic-<@s.property value="objectId"/>'>${objectName?if_exists}</a></td>
								        <td><@s.if test="sellPriceYuan!=null&&sellPriceYuan!=''"><dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn>起</@s.if></td>
									    <td><b class="lv-pink">${avgScore?if_exists}</b>（<a target='_blank'  href='http://ticket.lvmama.com/scenic-<@s.property value="objectId"/>#comments'>${commentCount?if_exists}封点评</a>）</td>
									    <td><span class="re-star"><span class="starbg"><i style="width:${roundHalfUpOfAvgScore}%"></i></span></span></td>
									    <td><a href="javascript:del_myFavorite(${id},'placeFavorite_${id}','placeCount');">取消收藏</a></td>
								    </tr>
								  </@s.iterator>
								   
								</tbody>
					        </table>
 			                  <div class="pages">
							      <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(placePage.pageSize,placePage.totalPageNum,placePage.url,placePage.currentPage)"/>	
						       </div> 
					        </div>
					        </@s.else>
					        <!-- 景点 -->
						</div>
					    <div id="tabs-2" class="ui-tab-box">
					    	<!-- 度假线路  -->
					         <@s.if test="productCount==0">
					    	<div class="no-list"><p>您还没有收藏任何度假线路！</p></div>
					        </@s.if>
					        <@s.else>
					        <div class="favorites-box">
					        <table class="lv-table favorites-table">
					        	<thead>
								    <tr class="thead">
								        <th style="width:280px;">景点名称</th>
								        <th>价格</th>
								        <th>评分</th>
								        <th style="width:110px;">星级</th>
								        <th>操作</th>
								    </tr>
					            </thead>
					            <tbody>
					            	<@s.iterator id="productFavorite" value="productPage.items" status="st">
								    <tr id="productFavorite_${id}">
									    <td><a target='_blank'  href="http://www.lvmama.com/product/${objectId?if_exists}">
										    <@s.if test="objectName!=null && objectName.length()>10">
											<@s.property value="objectName.substring(0,10)" escape="false"/>...</@s.if>
											<@s.else>${objectName?if_exists}</@s.else></a></td>
								        <td><dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn>起</td>
									    <td><b class="lv-pink">${avgScore?if_exists}</b>（<a target='_blank' href="http://www.lvmama.com/product/${objectId?if_exists}#row_5">${commentCount?if_exists}封点评</a>）</td>
									    <td><span class="re-star"><span class="starbg"><i style="width:${roundHalfUpOfAvgScore}%"></i></span></span></td>
									    <td><a href="javascript:del_myFavorite(${id},'productFavorite_${id}','productCount');">取消收藏</a></td>
								    </tr>
								    </@s.iterator>
								</tbody>
					        </table>
					           <div class="pages">
					             	  <@s.property escape="false" value="productPage.pagination"/>	
 						       </div> 
					        </div>
					        </@s.else>
					        <!-- <<度假线路 -->
						</div>
					    <div id="tabs-3" class="ui-tab-box">
					    	<!-- 攻略>> -->
					    	<@s.if test="guideCount==0">
					    	<div class="no-list">您还没有收藏任何攻略！</div>
					        </@s.if>
					        <@s.else>
					        <div class="favorites-box favorites-guide clearfix">
					        	<@s.iterator id="guideFavorite" value="guidePage.items" status="st">
					            <div class="imgtext-lr" id="guideFavorite_${id}">
					                <a target="_blank" class="img" href="${guidePinYinUrl?if_exists}"><img src="${objectImageUrl?if_exists}" width="89" height="124"></a>
					                <div class="text">
					                    <h4><a target="_blank" href="${guidePinYinUrl?if_exists}">${objectName?if_exists}</a></h4>
					                    <p class="gray">收藏时间<br>${createdTime?if_exists?string("yyyy-MM-dd HH:mm:ss")}</p>
					                    <a class="ui-btn ui-btn2" href="javascript:del_myFavorite(${id},'guideFavorite_${id}','guideCount');"><i>取消收藏</i></a>
					                </div>
					             </div>
					             </@s.iterator>
					        </div>
					        <div class="pages">
					             	  <@s.property escape="false" value="guidePage.pagination"/>	
 						       </div> 
					         </@s.else>
					        <!-- <<攻略 -->
						</div>
						<div id="tabs-4" class="ui-tab-box">
                            <!-- 团购  -->
                             <@s.if test="tuangouCount==0">
                            <div class="no-list"><p>您还没有收藏任何团购产品！</p></div>
                            </@s.if>
                            <@s.else>
                            <div class="favorites-box">
                            <table class="lv-table favorites-table">
                                <thead>
                                    <tr class="thead">
                                        <th style="width:280px;">产品名称</th>
                                        <th>价格</th>
                                        <th>评分</th>
                                        <th style="width:110px;">星级</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <@s.iterator id="tuangouFavorite" value="tuangouPage.items" status="st">
                                    <tr id="tuangouFavorite">
                                        <td><a target='_blank'  href="http://www.lvmama.com/product/${objectId?if_exists}">
                                            <@s.if test="objectName!=null && objectName.length()>10">
                                            <@s.property value="objectName.substring(0,10)" escape="false"/>...</@s.if>
                                            <@s.else>${objectName?if_exists}</@s.else></a></td>
                                        <td><dfn>&yen;<i>${sellPriceYuan?if_exists}</i></dfn>起</td>
                                        <td><b class="lv-pink">${avgScore?if_exists}</b>（<a target='_blank' href="http://www.lvmama.com/product/${objectId?if_exists}#row_5">${commentCount?if_exists}封点评</a>）</td>
                                        <td><span class="re-star"><span class="starbg"><i style="width:${roundHalfUpOfAvgScore}%"></i></span></span></td>
                                        <td><a href="javascript:del_myFavorite(${id},'tuangouFavorite_${id}','tuangouCount');">取消收藏</a></td>
                                    </tr>
                                    </@s.iterator>
                                </tbody>
                            </table>
                               <div class="pages">
                                      <@s.property escape="false" value="tuangouPage.pagination"/>  
                               </div> 
                            </div>
                            </@s.else>
                            <!-- <<团购 -->
                        </div>
					</div>
					<!-- <<我的收藏 -->
			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	

<!-- 封住窗口的弹出框  -->	
<div class="xh_overlay"></div>

<script type="text/javascript">
//删除收藏
 function del_myFavorite(deleteId,deleteViewObject,countId){
 	 $("div.xh_overlay").show().height($(document).height());
	 if (confirm("您确定要删除吗！")){
        	$.ajax({
			type:"post",
	        url:"/myspace/share/delFavorites.do",
	        data:"id="+deleteId,
	        success:function(data){
	        	if(data=="success"){
					$("#"+deleteViewObject).remove();
					var start=$("#"+countId).text();
					$("#"+countId).text(parseInt(start)-1)
					alert("设置成功！");
	        	}else{
	        		alert("设置失败!");
	        	}
	          }
	        });
		}
		 $("div.xh_overlay").hide();
		 
  }	
</script>
	<script>
		cmCreatePageviewTag("手机收藏", "D0001", null, null);
	</script>
</body>
</html>