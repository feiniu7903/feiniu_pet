<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/base.css?r=8686" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/c_common.css?r=8690" rel="stylesheet" />
<link href="http://pic.lvmama.com/styles/new_v/ob_comment/common.css?r=8515" rel="stylesheet" />

<script src="http://pic.lvmama.com/js/jquery142.js?r=8420"></script>
</head>

<body>
 <#import "/WEB-INF/pages/comment/generalComment/showProdLatitudeCssTemplate.ftl" as commentMacroTemplate>
                <div class="u_comment c_w_comment" id="main">
                	<@s.if test="relateProductPageConfig.items != null && relateProductPageConfig.items.size() > 0">
	 			 		<@s.iterator id="cmtCommentVO" value="relateProductPageConfig.items" status="index">
		                    <dl>
		                        <dt><img src="http://pic.lvmama.com${cmtCommentVO.userImg}" width="76" height="76" /><span><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(userNameExp,16)" /></span></dt>
		                        <dd>
		                            <@s.if test='isBest=="Y"'><strong class="jing">精</strong></@s.if><strong class="yan">验</strong>
		                            <span class="com_StarValueCon"><b>总体评价：</b><s class="star_bg cur_def"><i class="ct_Star${sumaryLatitude.score}"></i></s></span>
		                            <span class="re_jifen"><@s.if test='cashRefundYuan > 0'>奖金<em>${cashRefundYuan}元</em></@s.if><@s.else></@s.else>积分<em>${point}分</em></span>
		                            
		                        </dd>
		                        <dd class="c_score">
									 <!-- <@commentMacroTemplate.showLatitudeCss cmtLatitudes = cmtCommentVO.cmtLatitudes/> -->
									 <@s.iterator value="cmtLatitudes" id="latitudeScore">
					                 	<span style="background:none;padding:0">${latitudeScore.latitudeName}：<i>${latitudeScore.score}分</i></span>
			                 		 </@s.iterator>
		                        </dd>
		                        <dd class="c_comctext"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(contentDelEnter,80)" /><a href="/comment/${commentId}" target="_blank" class="c_w_more">查看全文&gt;&gt;</a></dd>
		                        <dd class="c_w_line text-ell">
		                        <#if cmtCommentVO.productOfCommentSellable==true>
		                        		<b>该点评出自于：</b><a target="_blank" rel="nofollow" href="http://www.lvmama.com/product/${cmtCommentVO.productId}#correla">${cmtCommentVO.productName}</a>
		                        <#else>
		                        		<b>该点评出自于：</b>${cmtCommentVO.productName}<i>该产品已售完</i>
		                        </#if>
		                        </dd>
		                        <dd class="c_reply"><a title="点击加心" class="h_xing" OnClick="javascript:return addUsefulCount(${shamUsefulCount},${commentId},this);"><i>（<big>${shamUsefulCount}</big>）</i></a><a href="/comment/${commentId}" target="_blank" class="h_fu">网友回复<i>（<big>${replyCount}</big>）</i></a><a href="/comment/${commentId}" target="_blank" class="c_canyu">驴妈妈官方回复<i>（
		                        <#if cmtCommentVO.lvmamaReplyCount!=0>
		                         		<big>${cmtCommentVO.lvmamaReplyCount}</big>
		                        <#else>
		                         		<big>0</big>
		                        </#if>                                                                           
		                                                                ）</i></a><small>${formatterCreatedTime}</small></dd>
		                    </dl>
	                    </@s.iterator>
                   </@s.if>
                    
                      <!--next page begin>>-->   
                   <#if relateProductPageFlag=='Y'>          
	                   <div class="paging">
	                    <div id="pages_" class="pages">
	                      <div id="lv_page">
	                        <div class="Pages">
	                         <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(relateProductPageConfig.pageSize,relateProductPageConfig.totalPageNum,relateProductPageConfig.url,relateProductPageConfig.currentPage)"/>
	                        </div>
	                      </div>
	                    </div>
	                   </div><!--paging end-->
                   </#if> 
                 </div>


<script type="text/javascript">

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
			 } else{
			 	xh_addClass(obj,"d_xing");
				var newUsefulCount = varUsefulCount + 1;
				obj.innerHTML= "<i>（<big>" + newUsefulCount + "</big>）</i>" ;
			 }
		}
	});
}

</script>  

<script src="http://pic.lvmama.com/js/iframe_auto/adaptive-frame.js?r=8650" data-frameid="relateProductCommentFrame" data-proxy="http://www.lvmama.com/js/comment/proxy.html" ></script>
<script src="http://pic.lvmama.com/js/new_v/ob_comment/x_comment.js?r=8683"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/common/losc.js?r=8673"></script>
  </body>

</html>
                
