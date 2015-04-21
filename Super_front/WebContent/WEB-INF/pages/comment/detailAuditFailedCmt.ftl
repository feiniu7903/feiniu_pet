<!DOCTYPE html>
<html lang="cn">
    <head>
        <meta charset="utf-8" />
        <title>审核未通过点评 - 驴妈妈旅游网</title>
		<meta name="keywords" content="${cmtComment.placeName},好玩吗,点评"/>
		<meta name="description" content="${cmtComment.userNameExp}关于${cmtComment.placeName}的旅游点评,<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(50,cmtComment.contentDelEnter)" />"/>
        <link href="http://pic.lvmama.com/min/index.php?g=ob_commentDetail" rel="stylesheet" type="text/css" media="screen" charset="utf-8"/>
        <script src="http://pic.lvmama.com/min/index.php?g=ob_commentJS" type="text/javascript" charset="utf-8"></script>
	 <script src="http://pic.lvmama.com/js/dest_xu/dianj.js?r=8447" type="text/javascript" charset="utf-8"></script>

        <link href="http://pic.lvmama.com/min/index.php?g=commonIncluedTop" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <#include "/common/header.ftl">
        
        	<!--页面导航-->
        	<#if place??>
				<#include "/WEB-INF/pages/comment/navigation.ftl">
			</#if>
    
   			<!--总体印象点评  , 我要点评-->
		    <div class="main">
				<div class="comment_detail_top">
				<strong>
				<div style="float:left;">
				<#if place??>
					<a  href="http://www.lvmama.com/dest/${place.pinYinUrl}" target="_blank"/>${place.name}</a>
				</#if>
				<em>
				总体印象</em>点评</div>
				<div style="float:right;"><a rel="nofollow" href="http://www.lvmama.com/comment/writeComment/fillComment.do?placeId=${place.id}"  target="_blank" style="font-size:12px;text-decoration:none; margin-left:388px;">我要点评>></a>
				&nbsp;&nbsp;&nbsp;
				<a href="http://www.lvmama.com/comment/${place.placeId}-1"  target="_blank" style="font-size:12px;text-decoration:none;">查看全部点评&gt;&gt;</a></div>
				</strong>
			</div>

			<!--点评主体内容-->
            <div class="mainContainer">
                <div class="mainCol">
                    <div class="userComments">
                        <dl>
                        	<#if cmtComment.userImg??>
                            	<dt><img height="76" width="76" src="http://pic.lvmama.com${cmtComment.userImg }" />
                            		<span><#if cmtComment.userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(16,cmtComment.userNameExp)" /><#else>匿名</#if></span>
                            	</dt>
                            <#else>
                            	<dt><img height="76" width="76" src="http://pic.lvmama.com/cmt/img/72x72.gif" />
                            		<span><#if cmtComment.userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(16,cmtComment.userNameExp)" /><#else>匿名</#if></span>
                            	</dt>
                            </#if>
                            <dd>
                                <#if cmtComment.isBest=="Y"><s class="psFine">精</s></#if>
                				<#if cmtComment.cmtType=="2"><strong class="yan">验</strong></#if>
                                <#if cmtComment.sumaryLatitude??>
                                	<h5>总体评价：</h5><i class="commentsStar${cmtComment.sumaryLatitude.score}"></i><span>${cmtComment.sumaryLatitude.score}</span>
                                <#else>
                                	<h5>总体评价：</h5><i class="commentsStar0"></i><span>0</span>
                                </#if>
                                <#if cmtComment.cashRefund??><em>点评奖金：<strong>&yen;${cmtComment.cashRefundYuan}</strong></em></#if>
                            </dd>
                            
                            <dd>
                                <@s.iterator value="cmtComment.cmtLatitudes" id="latitudeScore"><b>${latitudeScore.latitudeName} : ${latitudeScore.chScore}</b></@s.iterator>
                            </dd>
                            <dd>
                                ${cmtComment.content}
                            </dd>
                            
                            <!--图片-->
                            <#if cmtComment.cmtPictureList?? && cmtComment.cmtPictureList.size()!=0>
                                <dd>
                                    <!--<#list cmtComment.cmtPictureList as cmtPictureVO>
                                    	<a href="${cmtPictureVO.absoluteUrl}" target="_blank"><img src="${cmtPictureVO.absoluteUrl}" height="60" width="90"/></a>
                                    </#list>-->

				    
				        	<!--Start-->
        <div id="slide">
            <div id="slide_thumb">
            	<ul>   
		<#list cmtComment.cmtPictureList as cmtPictureVO>
                     <li> <a href="${cmtPictureVO.absoluteUrl}" target="_blank"><img src="${cmtPictureVO.absoluteUrl}" height="60" width="90"/></a> </li> 
				  
               </#list>
     
                </ul>
            </div>
            <a href="javascript:;" class="slide_nav unclick" id="pre_btn">上</a>
            <a href="javascript:;" class="slide_nav" id="next_btn">下</a>
        </div>
        <!--End-->
                                </dd>
                            </#if>                
                            
                        </dl>                       
                    </div>
                    
                </div>    
                
                <!--Right: 景区介绍导航-->
                <div class="rightCol">
                    <div class="placeDetail">
                        <#if place.middleImage??>
                            <a href="http://www.lvmama.com/comment/${place.placeId}-1" target="_blank">
                                <img class="scrollLoading" src="http://pic.lvmama.com${place.middleImage}" width="210px" height="150">
                            </a>
                        <#else>
                            <a href="http://www.lvmama.com/comment/${place.placeId}-1" target="_blank">
                                <img class="scrollLoading" src="http://www.lvmama.com/dest/img/myspace/img_120_90.jpg" width="210px" height="150">
                            </a>
                        </#if>
                        
                        <h2><a href="http://www.lvmama.com/comment/${place.placeId}-1" target="_blank">${place.name}</a></h2>
                        <p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(100,place.recommendReason)" /></p>
                        <p class="linkList">
                            <a href="http://www.lvmama.com/travel/${place.pinYinUrl}/place" target="_blank">景区介绍</a>
                            <a href="http://www.lvmama.com/comment/${place.placeId}-1" target="_blank">景区点评</a>
                            <a href="http://www.lvmama.com/dest/${place.pinYinUrl}" target="_blank">景区资讯</a>
                            <a href="http://www.lvmama.com/dest/${place.pinYinUrl}/guide" target="_blank">景区攻略</a>                            
                        </p>
                        
                        <div class="networkComments">
                        
                        <!--景点点评 统计数据 -->
                        <#include "/WEB-INF/comment/cmtLatitudeScoreInfo.ftl" />                       
                        <ul></ul>
                        </div>
                    </div>            
            </div>
        </div>
        <#include "/common/footer.ftl">

	
<div class="bg-tan"></div>
	<div id="slide_full">
        
    </div>    <script src="http://www.lvmama.com/dest/js/comment/detailComment.js" type="text/javascript"></script>
    	 <script src="http://pic.lvmama.com/js/dest_xu/dianj.js?r=8447" type="text/javascript" charset="utf-8"></script>
    </body>

</html>
