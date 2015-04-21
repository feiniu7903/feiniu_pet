<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />

<#if product??>
<title>${product.productName}点评,<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,15)" />-驴妈妈旅游网</title>
<meta name="keywords" content="线路,点评"/>
<meta name="description" content="来自驴妈妈旅游网关于关于${product.productName}【线路】点评: <@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,40)" />"/>
<#elseif place??>
<@s.if test='place.stage == "1"'><!--目的地-->
	<title>${place.name}点评,<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,25)" />-驴妈妈旅游网</title>
	<meta name="keywords" content="${place.name},"${place.name}点评"/>
    <meta name="description" content="来自驴妈妈旅游网关于${place.name}【游玩】点评:<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,60)" />"/>
</@s.if>
<@s.if test='place.stage == "2"'><!--景区类型-->
	<title>${place.name}点评,<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,25)" />-驴妈妈旅游网</title>
	<meta name="keywords" content="${place.name},${place.name}点评"/>
    <meta name="description" content="来自驴妈妈旅游网关于${place.name}【游玩】点评:<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,60)" />"/>
</@s.if>
<@s.if test='place.stage == "3"'><!--酒店-->
	<title>${place.name}点评,<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,25)" />-驴妈妈旅游网</title>
	<meta name="keywords" content="${place.name}点评"/>
    <meta name="description" content="来自驴妈妈旅游网关于${place.name}【入住】点评: <@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.contentDelEnter,60)" />"/>
</@s.if>
</#if>


<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/new_v/ob_comment/base.css,/styles/new_v/ob_comment/c_common.css,/styles/new_v/ob_comment/common.css"/>

<title>点评详情页</title>
</head>
<body class="lvcomment">
<#import "/WEB-INF/pages/comment/generalComment/showProdLatitudeCssTemplate.ftl" as commentMacroTemplate>

<!--头部开始-->
<#include "/common/header.ftl">

<!--  页面导航先放着等左完成  -->
<#if place??>
	<#include "/WEB-INF/pages/comment/navigation.ftl">
</#if>

<div class="container clearfix">
	<div class="container_lt c_shadow">
    	<h1>
    	<#if place??>
				<a  href="http://www.lvmama.com/dest/${place.pinYinUrl}"  class="scenic_tit yahei"/>
				<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(place.name,28)" />
				</a>
		</#if>
    	<#if product??>
    		<@s.if test='product.valid == "Y"'>
				<a  href="http://www.lvmama.com/product/${product.productId}"  class="scenic_tit yahei"/>
					<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(product.productName,28)" />
				</a>
			</@s.if>
			<@s.else>
				<i class="scenic_tit yahei">
					<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(product.productName,30)" />
				</i>
			</@s.else>
		</#if>
		<span class="scenic_com">
		
		<#if place??>
		<a rel="nofollow" href="http://www.lvmama.com/comment/${place.placeId}-1">
			查看所有点评
		</a>
		<i>|</i>
		<a rel="nofollow" href="http://www.lvmama.com/comment/writeComment/fillComment.do?<@s.if test='place!=null'>placeId=${place.placeId}</@s.if>">
			我要写点评
		</a>
		</#if>
		<#if product??>
		<a href="http://www.lvmama.com/product/${product.productId}/comment">
				查看所有点评</a>
		</#if>
		</span>
		</h1>
		
        <div class="u_comment">
        	<dl>
        		<#if cmtComment.userImg??>
	            	<dt><img src="http://pic.lvmama.com${cmtComment.userImg}" width="76" height="76" />
	            	<span>
		            	<#if cmtComment.userName??>
		            		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.userNameExp,16)" />
		            	<#else>匿名</#if>
	            	</span>
	            	</dt>
            	<#else>
	            	<dt><img src="http://pic.lvmama.com/cmt/img/72x72.gif" width="76" height="76" />
	            	<span>
		            	<#if cmtComment.userName??>
		            		<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(cmtComment.userNameExp,16)" />
		            	<#else>匿名</#if>
	            	</span>
	            	</dt>
            	 </#if>
            	 
                <dd>
                	<#if cmtComment.isBest=="Y"><strong class="jing">精</strong></#if>
                	<#if product??><strong class="yan">验</strong></#if>
                	<#if place??>
                		<#if cmtComment.cmtType=="2"><strong class="yan">验</strong></#if>
					</#if>
                	<span class="com_StarValueCon"><b>总体评价：</b><s class="star_bg cur_def">
                	
                	<#if cmtComment.sumaryLatitude??>
                		<i class="ct_Star${cmtComment.sumaryLatitude.score}"></i>
                	<#else>
                		<i class="ct_Star0"></i>
                	</#if>
                	</s></span>
                	<span class="re_jifen">
                		<#if cmtComment.cashRefund != null || cmtComment.cashRefund != "">奖金<em>${cmtComment.cashRefundYuan?default("0")?number?string("0.00")}元</em></#if>
		            	<#if cmtComment.point != null || cmtComment.point != "">积分<em>${cmtComment.point}分</em></#if>
                    </span>
                </dd>
                
                <dd class="c_score">
	                <!-- if cmtComment.productId==null -->
	                	 <@s.iterator value="cmtComment.cmtLatitudes" id="latitudeScore">
			                 	<span style="background:none;padding:0">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i></span>
	                 	 </@s.iterator>
	                	<!-- <@commentMacroTemplate.showProdLatitudeCss cmtComment/> -->
                </dd>
                
                <dd id="shareID" class="c_comctext">${cmtComment.contentFixBR}</dd>
                <dd class="c_reply">
                
	                <a title="点击加心" class="h_xing" OnClick="javascript:return addUsefulCount(${cmtComment.shamUsefulCount},${cmtComment.commentId},this);">
	                	<i> （${cmtComment.shamUsefulCount}）</i>
	                </a>
	                <a class="h_fu">网友回复<i>（${cmtComment.replyCount}）</i></a>
	                
	                   <#if cmtComment.placeId != null && cmtComment.productId == null>
	            			<#if cmtComment.merchantReplyCount!=0>
	                           <a class="c_canyu">商家参与回复<i>（${cmtComment.merchantReplyCount}）</i></a>
	                        <#else>
	                             <a  class="c_canyu">商家参与回复<i>（0）</i></a>
	                        </#if> 
                       </#if>
                       
                        <#if cmtComment.productId >
	                        <#if cmtComment.lvmamaReplyCount!=0>
	                           <a class="c_canyu">驴妈妈官方回复<i>（<big>${cmtComment.lvmamaReplyCount}</big>）</i></a>
	                        <#else>
	                             <a class="c_canyu">驴妈妈官方回复<i>（<big>0</big>）</i></a>
	                        </#if>
                        </#if>  
	                	<small>${cmtComment.formatterCreatedTime}</small>
                </dd>
            </dl>
            
             <#if cmtComment.cmtPictureList?? && cmtComment.cmtPictureList.size()!=0>
			     <p class="up_pic" id="up_pic">
		             <#list cmtComment.cmtPictureList as cmtPictureVO>
			             	<a  target="_slef" title="${cmtPictureVO.title}" href="${cmtPictureVO.absoluteUrl}" ><img src="${cmtPictureVO.absoluteUrl}" width="210" height="140" /></a>
		             </#list>
			     </p>
             </#if>
             
            <!-- Baidu Button BEGIN -->
            <div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
            <!-- Baidu Button END -->
            
        </div><!--u_comment end-->
        
        <!--网友参与话题部分-->
        <div class="c_topic">
        	<p>已有<strong class="c_num">${cmtComment.replyCount}</strong>个<strong>网友参与</strong>话题</p>
        	
			<#if cmtComment.merchantReplies?? && cmtComment.merchantReplies.size()!=0> 
				 <@s.iterator value="cmtComment.merchantReplies">
		            <dl class="c_busi">
		                <dt>
		                	<em class="yahei"></em>
		                    <strong>商家回复</strong>
		                    <small><em><@s.date name="createTime" format="yyyy-MM-dd HH:mm"/></em></small>
		                </dt>
		                <dd>${content}</dd>
		            </dl>
		         </@s.iterator>
             </#if>
             
            <#if cmtComment.lvmamaReplies?? && cmtComment.lvmamaReplies.size()!=0>
            	<@s.iterator value="cmtComment.lvmamaReplies">
		            <dl class="c_busi">
		                <dt>
		                	<em class="yahei"></em>
		                    <strong>驴妈妈回复</strong>
		                    <small><em><@s.date name="createTime" format="yyyy-MM-dd HH:mm"/></em></small>
		                </dt>
		                <dd>${content}</dd>
		            </dl>
		           </@s.iterator>
             </#if>
             
             <#if cmtComment.replies && cmtComment.replies.size()!=0>
		             <@s.iterator value="cmtComment.replies">
			            <dl>
			                <dt>
			                	<span><#if userName??><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(userNameWithoutMobile,16)" /><#else>匿名网友</#if></span>
			                    <small><em><@s.date name="createTime" format="yyyy-MM-dd HH:mm"/></em></small>
			                </dt>
			                <dd>${contentFixBR} </dd>
			            </dl>
		  			</@s.iterator>
  		  	</#if>
        </div>
        <!--c_topic end-->
        
        <div class="c_revert">
        	<p class="yahei">回复</p>
            <form  enctype="multipart/form-data" method="post" name="myForm" id="myForm" action="/comment/replyComment.do">
	            <input type="hidden" id="commentId" name="commentId" value="${cmtComment.commentId}"/>
	            <input type="hidden" name="userName" id="userName" value="<@s.if test="users!=null">${users.userName}</@s.if>" />
	            
	             <ul class="mycomments">
		           	<li><textarea class="c_tarea" rows="input" cols="input" name="content" id="content" value="${replyContent}"></textarea></li>    	              
	              <li style="display: none;" id="login">
		            <p style="margin-bottom:10px"><label for="loginName">用户名：</label>
			            <span class="c_inp_tips fl">
			            	<input type="text" class="c_input_text" id="loginName" name="name">
			            </span>
			            <i id="errotLoginName"></i>
		            </p>
	                <p style="margin-bottom:10px">
			            <label id="pwd" for="password">密　码：</label>
			            <span class="c_inp_tips fl">
			            	<input type="password" class="c_input_pwd" id="password" name="passwrod">
			            </span>
	                	<i id="errorPassword"></i>
	                </p>
					<p>
			            <label id="pwd" for="password">验证码：</label>
			            <span class="c_inp_tips fl">
			            	<input type="text" style="width:80px" class="c_input_pwd" id="sso_verifycode1" name="verifycode" >
		                    <img id="image" src="http://login.lvmama.com/nsso/account/checkcode.htm" /> <a href="#" class="link_blue" onClick="refreshCheckCode('image');return false;">换一张</a>
		                </span>
						<i id="errorVerifyCode"></i>
	                </p>
		            <br>
		            <a href="http://login.lvmama.com/nsso/register/registering.do" target="_blank" class="c_account">还没有账号</a>
		            <p class="c_log_tips c_w_error">如您没有驴妈妈账号，请勿关闭或刷新当前页面。在新打开的注册页面完成注册后，再回来点击发表功能</p>
		          </li>
	              </ul>
	            <p class="c_butt"><input type="button" class="c_submit" value="回复" name="button" onClick="send()">　&nbsp;</p>
           
            </form>
        </div>
    </div>
    <!--container_lt end-->
    
    <!-- 右边 -->
    <div class="container_rt">
   		<div class="aside_box c_shadow">
          <p class="c_pt_pic">
          
            <@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.placeId > 0">
          	<span  class="xh_imgauto"><img src="http://pic.lvmama.com${cmtTitleStatisticsVO.placeLargeImage}" /><span class="c_t_bg"></span><em class="c_p_tit f14">${cmtTitleStatisticsVO.titleName}</em></span>
          	</@s.if>
          	
          	<@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.productId > 0">
          	<span class="xh_imgauto"><img src="${cmtTitleStatisticsVO.productLargeImgUrl}" /><span class="c_t_bg"></span><em class="c_p_tit f14">${cmtTitleStatisticsVO.titleName}</em></span>
          	</@s.if>
          </p>
          
          <p class="c_p_link"><span class="com_StarValueCon total_val_posi">
             <@s.if test="cmtTitleStatisticsVO != null && cmtTitleStatisticsVO.commentCount!=0">
              <font><em>#{cmtTitleStatisticsVO.avgScore;m1M1}</em>分</font><s class="star_bg cur_def"><i class="ct_Star${cmtTitleStatisticsVO.roundHalfUpOfAvgScore}"></i></s></span>
             </@s.if>
             <@s.else>
               <font><em>0</em>分</font><s class="star_bg cur_def"><i class="ct_Star0"></i></s></span>
             </@s.else>
             
             <@s.if test="cmtTitleStatisticsVO != null">
             	<#if product??>
             		<a href="http://www.lvmama.com/product/${product.productId}/comment">${cmtTitleStatisticsVO.commentCount}封点评</a>
                <#elseif place??>
               		<a href="http://www.lvmama.com/comment/${place.placeId}-1">${cmtTitleStatisticsVO.commentCount}封点评</a>
                </#if>
             </@s.if>
             
             <@s.else>
               <span>0封点评</span>
             </@s.else> 
          </p>
          <!--点评平均分维度统计开始-->
  	      <#include "/WEB-INF/pages/comment/cmtAvgLatitudeStatisticsInfo.ftl">
  	      
        </div>
        
	<div style="margin:0 0 10px;">
	<@s.property escape="false" value="@com.lvmama.comm.utils.KeJieAdsProxy@keJieAdsProxy('t019e093fa26cbed0001','js',null)"/>
	</div>
	<!--最新点评-->
	<#include "/WEB-INF/pages/comment/index/indexLastestComments.ftl" />
	 
    </div><!--container_rt-->

</div><!--container-->
<div id="c_overlay"></div>
<div id="c_mask">
            <div id="c_lightbox">
                <img  id="lightbox_image"/>
                <a id="c_jsnext"></a>
                <a id="c_jsprev"></a>
                <div id="c_jsdescribg"></div>
                <p id="c_jstext"><span id="c_jsdescri"></span><span id="c_jsnum">1</span>/<i>4</i></p>
            </div>
</div><!--c_mask end-->

<div id="d-buttons" class="c_shadow"></div>

<!--footer>>-->
<div class="c_footer">

<!--footer>>-->
<#include "/WEB-INF/pages/comment/generalComment/commentDetailFooter.ftl" />
<!--footer<<-->

<!--<script >
var shareText = document.getElementById("shareID").innerHTML;
var jiathis_config={
	summary:shareText,
	hideMore:false
}
</script>-->
</div>

<script>
			 /**
			  * 用户登录
			  **/
			  function login() {
						  	var mobileOrEMail=$('#loginName').val();
							if(mobileOrEMail==''){
								$('#loginName').focus(function(){$("#errotLoginName").html("");});
								$("#errotLoginName").html("<font color='red'>用户名不能为空</font>");
								return false;
							};
							
							var password = $('#password').val(); 
							if(password==''){
								$('#password').focus(function(){$("#errorPassword").html("");});	
								$("#errorPassword").html("<font color='red'>密码不能为空</font>");
								return false;
							};
							var verifycode = $('#sso_verifycode1').val(); 
							if(verifycode==''){
									$('#sso_verifycode1').focus(function(){$("#errorVerifyCode").html("");});	
									$("#errorVerifyCode").html("<font color='red'>验证码不能为空</font>");
									return false;
							};
							
							$.getJSON(
									"http://login.lvmama.com/nsso/ajax/login.do?jsoncallback=?",
									{
									 mobileOrEMail : mobileOrEMail,
									 password	  : password,
									 verifycode   : verifycode
									},
								 function(data) {
								 	if (data.success) {
										$("#myForm").submit();
									} else {
										$("#errotLoginName").html("<font color='red'>请确认用户名，密码，验证码是否正确!</font>");	
										$("#errorPassword").html("");
										$("#errorVerifyCode").html("");
									}
								}
							);	
				}						
</script>
<script>
function refreshCheckCode(s) {
		    var elt = document.getElementById(s);
		    elt.src = elt.src + "?_=" + (new Date).getTime();
		}
function send() {
	if($("#content").val()=="" ) {
		alert('回复内容长度应该在2-500个字符。');
		return false;
	}
	if(document.getElementById('login').style.display=='block'){
	    login();
	}
	var userName =$('#userName').val();
	if(userName != null && userName != ''){
	 	$("#myForm").submit();
	}else{
	    document.getElementById('login').style.display='block';
	}
}

/**
 * 点评“有用”
 * @param {Object} varCommentId
 * @param {Object} obj
 * @param {Object} count
 */
 function xh_addClass(target,classValue) {
			var pattern = new RegExp("(^| )" + classValue + "( |$)"); 
			if (!pattern.test(target.className)) { 
				if(target.className ==" ") { 
					target.className = classValue; 
				}else { 
					target.className = " " + classValue; 
				} 
			} 
			$(target).addClass(classValue);
			return true; 
		}; 

function addUsefulCount(varUsefulCount,varCommentId,obj) {
	
	$.ajax({
		type: "post",
		url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
		data:{
			commentId: varCommentId
		},
		dataType:"json",
		success: function(jsonList, textStatus){
			 if(!jsonList.result){
			   alert("已经点过一次");
			 }else{
			 	xh_addClass(obj,"d_xing");
				var newUsefulCount = varUsefulCount + 1;
				obj.innerHTML= "<i>（<big>" + newUsefulCount + "</big>）</i>" ;
			 }
		}
	});
}
</script>
<script src="http://pic.lvmama.com/min/index.php?f=/js/new_v/jquery-1.7.2.min.js,/js/ui/lvmamaUI/lvmamaUI.js,/js/new_v/top/header-air_new.js,/js/new_v/ob_comment/x_comment.js"></script>
<!--<script src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>
<script type="text/javascript" >
var jiathis_config={
	title: "我在驴妈妈旅游网发现了一篇实用又有趣儿的驴友点评，速速围观！",
	summary:"#分享真实感受 说说旅行那点事儿#",
	hideMore:false
}
</script>-->
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js"></script>
</body>
</html>
