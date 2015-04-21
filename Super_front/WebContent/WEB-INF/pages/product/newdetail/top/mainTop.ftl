<#import "/WEB-INF/pages/product/newdetail/buttom/view_content_func.ftl" as vcf>
               <#include "/WEB-INF/pages/product/newdetail/top/position.ftl">
               <div class="dtl_infobox clearfix">               
                    
                <!--主页右上角图标-->
                <@s.if test="prodCProduct.prodProduct.isTicket()">
                    <div id="dtl_zyx" class="dtl_zyx_icon_mp"></div>
                </@s.if>
                <@s.elseif test="prodCProduct.prodProduct.isGroup()">
                    <div id="dtl_zyx"  class="dtl_zyx_icon_gt"></div>
                    <@s.if test='prodCProduct.prodProduct.subProductType=="SELFHELP_BUS"'>
                    <p class="bookNotes">本产品由驴妈妈旅游网指定上海驴妈妈兴旅国际旅行社有限公司提供相关咨讯及服务</p>
                    </@s.if>
                    <@s.else>
                    <p class="bookNotes">本产品由驴妈妈旅游网指定上海驴妈妈兴旅国际旅行社有限公司及具有相关资质的合作旅行社提供相关咨讯及服务</p>
                    </@s.else>
                </@s.elseif>
                <@s.elseif test="prodCProduct.prodProduct.isFreeness()">
                    <@s.if test="prodCProduct.prodProduct.hasSelfPack()">
                		<div id="dtl_zyx"  class="dtl_zyx_icon_super"></div>
                	</@s.if>
                	<@s.else>
                		<div id="dtl_zyx"  class="dtl_zyx_icon"></div>
                	</@s.else>                	
                	<p class="bookNotes">本产品由驴妈妈旅游网指定上海驴妈妈兴旅国际旅行社有限公司及具有相关资质的合作旅行社提供相关资讯及服务</p>
                </@s.elseif>
                <@s.elseif test="prodCProduct.prodProduct.isForeign()">
                    <div id="dtl_zyx"  class="dtl_zyx_icon_cj"></div>
                    <p class="bookNotes">本产品由驴妈妈旅游网指定上海驴妈妈兴旅国际旅行社有限公司及具有相关资质的合作旅行社提供相关资讯及服务</p>
                </@s.elseif>
                <@s.elseif test="prodCProduct.prodProduct.isHotel()">
                    <div id="dtl_zyx"  class="dtl_zyx_icon_jd"></div>
                </@s.elseif>
                

                    <div class="dtl_tit">
                         <h1 class="dtl_tit_txt"><@s.property value="prodCProduct.prodProduct.productName" escape="false"/>
                         <#if tagList??>
	                     	<#list tagList as t>
				            	<#if t.tagGroupName!='优惠' && t.tagGroupName!='抵扣'><span <#if t.description!="">tip-content="${t.description}"</#if> class="${t.cssId}">${t.tagName}</span></#if>
				            </#list>
			            </#if>
                         </h1>
                            <!-- Baidu Button BEGIN -->
<div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
<!-- Baidu Button END -->
                    </div><!--infobox-tit--> 
                    
                    <div class="dtl_boxinner">
                         <#include "/WEB-INF/pages/product/newdetail/top/mainReft.ftl">
                         <#include "/WEB-INF/pages/product/newdetail/top/mainRight.ftl">
                    </div><!--boxinner-->   
                </div><!--infobox-->  
					<@s.if test="!prodCProduct.prodProduct.hasSelfPack() && viewPage.hasContent('MANAGERRECOMMEND')">
					<div class="dtl_tj">
						<h3 class="dtl_tj_tit">产品经理推荐</h3>
						<ul class="dtl_tj_list">                       
						   <@vcf.showViewContent 'MANAGERRECOMMEND' 'li' '★'/>
						</ul>
					</div>
					</@s.if>
