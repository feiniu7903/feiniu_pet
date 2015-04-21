<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>邮件订阅-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-subscribe">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a class="current">邮件订阅</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">
				
				<!-- 邮件订阅>> -->
				<div class="ui-box mod-edit subscribe">
					<div class="ui-box-title"><a class="link-edit fr" href="javascript:show_edit_subscribe();"><span class="lvapp-icon icon-apps16-1012 fl"></span><i class="ie6_blank"></i>订阅邮箱修改</a><h3>邮件订阅</h3></div>
					<div class="ui-box-container">
				        <!-- 邮箱修改及验证>> -->
				    	<div class="edit-box clearfix subscribe-edit-box"  <@s.if test="null != edmSubscribe.email">style="display:none;"</@s.if>>
				        <div class="lv-arrow lv-bottom"><div class="pointy-bordr"></div><div class="pointy" ></div></div>
				        	<div class="edit-inbox lv-bd lv-doing" <@s.if test="null != edmSubscribe.email">style="display:none;"</@s.if> id="edit_subscribe_div">
				            <p><label><span>*</span>订阅邮箱：</label>
				            <input type="hidden" name="edmSubscribe.id" value="<@s.property value="edmSubscribe.id"/>"/>
				            <input type="hidden" name="edmSubscribe.mustWantedTravel" value="<@s.property value='edmSubscribe.mustWantedTravel'/>"/>
							<input type="hidden" name="edmSubscribe.travelTime" value="<@s.property value='edmSubscribe.travelTime'/>"/>
				            <input type="hidden" name="edmProvinceId" value="<@s.property value="edmSubscribe.province"/>"/>
				            <input type="hidden" name="edmCityId" value="<@s.property value="edmSubscribe.city"/>"/>
				            <input type="text" name="edmSubscribe.email" value="<@s.property value="edmSubscribe.email"/>" class="input-text input-email"/><span class="tips-error" style="display:none;" id="edm_subscribe_validate_id"><span class="tips-ico02"></span>请输入订阅邮箱</span></p>
				           	</p>
				            <p><label><span>*</span>所在地：</label>
					           <select name="edmSubscribe.province" id="provinceId"  class="dy_select">
									<@s.iterator value="provinceList" >
										<option value ="<@s.property value="provinceId"/>" <@s.property value="checked"/>><@s.property value="provinceName"/></option>
									</@s.iterator>
								</select> <select class="dy_select" name="edmSubscribe.city" id="cityId">
								<@s.if test="cityList.size()==0"><option>请选择</option>
								</@s.if>
								<@s.else>
									<@s.iterator value="cityList" >
										<option value="<@s.property value="cityId"/>" <@s.property value="checked"/>><@s.property value="cityName"/></option>
									</@s.iterator>
								</@s.else></select>
								<span class="tips-error city_error_msg" style="display:none;"><span class="tips-ico02"></span>请选择所在地</span>
				            </p>
				            <p>
				            	<label>平常出游时间：</label>
				            	<span class="check-box">
				            		<label><input type="checkbox" value="1" name="travelTime">周末、周边短途</label>
				            		<label><input type="checkbox" value="2" name="travelTime">端午、清明、五一小长假</label>
				            		<label><input type="checkbox" value="3" name="travelTime">十一黄金周</label>
				            		<label><input type="checkbox" value="6" name="travelTime">圣诞元旦</label>
				            		<label><input type="checkbox" value="4" name="travelTime">春节长假</label>
				            		<label><input type="checkbox" value="5" name="travelTime">其他时间</label>
				            	</span>
							</p>
							<p>
								<label>最想去玩的地方：</label>
								<span class="check-box">
				            		<select  class="dy_select" id="mustWantTravelProvince">
										<@s.iterator value="provinceList" >
											<option value ="<@s.property value="provinceId"/>"><@s.property value="provinceName"/></option>
										</@s.iterator>
									</select> <select class="dy_select" id="mustWantTravelCity"><option>请选择</option></select>
									<button class="dy_add">+ 添加</button> <i><front color="red">最多可添加5个</front></i></p>
				            	</span>
							</p>
							<p class="dy_chalist">
								<@s.iterator value="placeList" id="stat"><span class="dy_cha" value="<@s.property value="provinceId"/>-<@s.property value="cityId"/>"><@s.property value="cityName"/><img src="http://pic.lvmama.com/img/new_v/ob_yjdy/cha.jpg"/></span></@s.iterator>
							</p>
							<div class="hr_a"></div>
				            <p><a class="ui-btn ui-button" id="email_subscribe_button"><i>&nbsp;确 定&nbsp;</i></a></p>
				            </div>
				        </div>
				        <!-- <<邮箱修改及验证 -->
				        <!-- 邮箱订阅列表>> -->
				        <div id="subscribe-list" class="subscribe-item-list clearfix">
				        	<@s.iterator value="edmSubscribe.infoList" id="stat"><input type="hidden" name="type" value="<@s.property value="type"/>"/></@s.iterator>
				    		<dl class="subscribe-item clearfix">
				    			<dt class="item-new"><span class="lvapp-icon icon-apps60-1001"></span><i class="ie6png"></i></dt>
				    			<dd><h4>当季热卖</h4><p class="lv-cc">精选国内外优质旅游线路、景区门票，助您轻松安排自由行程</p>
				    			<p class="lv-done">
				    				<i class="lv-cc" id="subscribed_div">已订阅　</i><a class="ui-btn ui-btn2" id="subscribed_div" name="PRODUCT_EMAIL"><i>取消订阅</i></a>
				    				<a class="ui-btn ui-btn1 subscribe-btn" name="PRODUCT_EMAIL" id="email_subscribe_div"><i>订阅</i></a>
				    			</p>
				                </dd>
				    		</dl>
				    		<dl class="subscribe-item clearfix">
				    			<dt><span class="lvapp-icon icon-apps60-1003"></span></dt>
				    			<dd><h4>旅游资讯</h4><p class="lv-cc">最全旅游攻略，最新旅游资讯，最热景点推荐</p>
				    			<p class="lv-done">
				    				<i class="lv-cc" id="subscribed_div">已订阅　</i><a class="ui-btn ui-btn2" id="subscribed_div" name="MARKETING_EMAIL"><i>取消订阅</i></a>
				    				<a class="ui-btn ui-btn1 subscribe-btn"  name="MARKETING_EMAIL" id="email_subscribe_div"><i>订阅</i></a>
				    			</p>
				                </dd>
				    		</dl>
				    		<dl class="subscribe-item clearfix">
				    			<dt><span class="lvapp-icon icon-apps60-1004"></span></dt>
				    			<dd><h4>促销活动</h4><p class="lv-cc">超好玩的线上线下活动，更有精美奖品哦</p>
				    			<p class="lv-done">
				    				<i class="lv-cc" id="subscribed_div">已订阅　</i><a class="ui-btn ui-btn2" id="subscribed_div" name="SELF_HELP_EMAIL"><i>取消订阅</i></a>
				    				<a class="ui-btn ui-btn1 subscribe-btn" name="SELF_HELP_EMAIL" id="email_subscribe_div"><i>订阅</i></a>
				    			</p>
				                </dd>
				    		</dl>
				        </div>
				        <!-- <<邮箱订阅列表 -->
					</div>
				</div>
				<!-- <<邮件订阅 -->
		</div>
	</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>
	<#include "/WEB-INF/pages/myspace/sub/footer/subscribe_footer.ftl"/>
	<script>
		cmCreatePageviewTag("邮件订阅", "D0001", null, null);
	</script>
</body>
</html>