<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>旅游团购-驴妈妈旅游网</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/v7style/globalV1_0.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/super_v2/group.css"/>
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/new_v/top/header-air_new.js"></script>
<script src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"></script>
</head>
<script>
	var conextPath='';
</script>
<script type="text/javascript" src="/js/tuan.js"></script>
<body id="dream-body">
<@s.set name="currentTab" value="'dream'"/>
<#include "/WEB-INF/pages/group/head.ftl"/>
<!--Content Area-->
<div class="shortinner">
	<!--Ad-->
   <#include "/WEB-INF/pages/group/include/ad.ftl" />
    <!--leftContent-->
    <div class="gropu-l-c">
    	<!---product-->
      <@s.iterator value="dreamList" status="dream">
        <div class="group-bor">
        	<div class="group-pro">
        		<div class="wid"><div class="proNum proNum${10+dream.index+1}">梦想</div></div>
                <h1>
                <@s.if test="${productType=='TICKET'}">
					<span class="proType"></span><strong> 门票
				</@s.if>
				<@s.elseif test="${productType=='GROUP'}">
				     <span class="proType proType3"></span><strong>国内游
				</@s.elseif>
				<@s.elseif test="${productType=='FREENESS'}">
					<span class="proType proType2"></span><strong>自由行
				</@s.elseif>
				<@s.elseif test="${productType=='FOREIGN'}">
					<span class="proType proType4"></span><strong>出境游
				</@s.elseif>
				<@s.elseif test="${productType=='HOTEL'}">
					<span class="proType proType5"></span><strong>国内酒店
				</@s.elseif>
				• ${city}</strong>
				${productName}</h1>
                <!--leftContent-->
                <div class="g-c-l">
                	<div class="group-infor">
                    	<div class="price-look group-bg">
                        	<p class="group-bg">预计价格</p>
                            <p><em>&yen;</em>${lowDreamPrice/100}—<em>&yen;</em>${highDreamPrice/100}</p> 
                        </div>
						<p class="infor-border">团前门市价：<span><em>&yen;</em>${marketPrice/100}</span></p>
                        <div class="interact-block">
                            <h5>参与投票，实现梦想!</h5>
                            <p class="interact-nav"><span class="group-bg" onclick="showLikeGroup('${dreamId}');">我喜欢</span><em  class="LoveNum" id="dreamEnjoy${dreamId}" >${enjoyCount}</em></p>
                            <p class="interact-nav dislike"><span class="group-bg"  onclick="showNotLikeGroup('${dreamId}');" >不喜欢</span><em class="LoveNum" id="dreamNotEnjoy${dreamId}">${notEnjoyCount}</em></p>
                            <p><strong><em id="dreamJoinTotalCount${dreamId}">${enjoyCount+notEnjoyCount}</em>人已参与投票</strong></p>
                        </div>
                    </div>
                    <!--otherBg-->
                    <div class="share-group">
                    	<p>分享到：</p>
                    	<ul>
                        <li style="width:93px;">
								<script type="text/javascript" charset="utf-8"> 
									outSinaWeibo(document.location.href,document.title);
								</script>
                            </li>
                            <li>
								<a title="分享到豆瓣网" rel="nofollow" class="fav_douban" href="javascript:void(shareDouban(document.location.href,document.title))()"><img width="16" height="16" src="http://pic.lvmama.com/img/pro_share_douban.jpg">豆瓣</a>
                            </li>
                            <li style="width:93px;">
								<a title="分享到人人网"  href="javascript:void(shareRenRen(document.location.href,document.title));"><img width="16" height="16" src="http://pic.lvmama.com/img/pro_share_renren.jpg">人人网</a>
                            </li>
                            <li>
								<a title="分享到开心网" href="javascript:shareKaixin(document.location.href,document.title)"><img width="16" height="16" src="http://pic.lvmama.com/img/pro_share_kaixin.jpg">开心网</a>
                            </li>
                    	</ul>
                   
                    </div>
                </div>
                <!--rightContent-->
                <div class="g-c-r">
                	   <@s.if test="comPictureList.size>0">
	                		<div class="scrollDiv">
								<ul class="scrollNum" id="scrollNum_${dreamId}">
									<@s.iterator value="comPictureList" status="comPic">
										<li <@s.if test="#comPic.index==0"> class="curNumLI"</@s.if> >${comPic.index+1}</li>
									</@s.iterator>
								</ul>
								<ul class="scrollImg"  id="scrollImg_${dreamId}">
									<@s.iterator value="comPictureList" status="comPic">
										<li <@s.if test="#comPic.index==0"> class="curImgLI" </@s.if> > <img width=512 height=256 src="${absolute580x290Url}" alt="${productName}" /> </li>
									</@s.iterator>
								</ul>
							</div>
							<@s.if test="comPictureList.size>1">
		                		<script>
		                				var scrollImage${prodProduct.productId} = new ScrollImage("scrollNum_${dreamId}","scrollImg_${dreamId}",${comPictureList.size()},3000);
		                		</script>
	                		</@s.if>
                	</@s.if>
                    <p class="introduce-con"><span class="group-bg"></span>${introduction}</p>
                </div>
                <p class="clear"></p>
            </div>
        </div>
       </@s.iterator>
     </div>
    <!--rightContent-->
    <div class="gropu-r-c">
    	<div class="gropu-r-bg">
            <!--显示团公告-->
            <#include "/WEB-INF/pages/group/include/groupNotice.ftl" />
            <!--今日其他团购-->
           <#include "/WEB-INF/pages/group/include/todayOtherGroupPrd.ftl" >
        </div>  
        <!--business-->
        <dl class="business">
        	<dt>商务合作</dt>
            <dd class="right-margin"><img src="http://pic.lvmama.com/img/test/img1.jpg" /></dd>
            <dd><img src="http://pic.lvmama.com/img/test/img2.jpg" /></dd>
            <dd class="right-margin"><img src="http://pic.lvmama.com/img/test/img3.jpg" /></dd>
            <dd><img src="http://pic.lvmama.com/img/test/img4.jpg" /></dd>
        </dl>  
    </div>
    <!--clear-->
    <p class="clear"></p>
</div>
<!--background-->
<div class="group-body-bg">

</div>
	<#include "/WEB-INF/pages/group/include/group_footer.ftl">
<!--popup-->
<div class="popup-email">
	<span class="group-bg" >关闭</span>
	<p><strong>梦想成功，请邮件通知我！</strong></p>
    <input name="dreamEmail" type="text"  id="dreamEmail" value="请输入邮箱地址" class="email-input" onblur="checkMyEmail();"/>
    <a class="group-bg" id="btnEmailSubmit" href="javascript:submitLikeGroup();">确认提交</a>
    
</div>
<!--Js Area-->

<script language="javascript">
  var currentDream = null;
  var EMAIL_REGX = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  function checkMyEmail() {
  	var email = $("#dreamEmail").val();
  	if(!EMAIL_REGX.test(email)) {
  		$('#btnEmailSubmit').attr('disabled','disabled');
  	}else{
  		$('#btnEmailSubmit').removeAttr('disabled');
  	}
  }
  function submitLikeGroup() {
  	if(EMAIL_REGX.test($('#dreamEmail').val())) {
  		ajaxSubmitDream('Y')
  	};
  }
  function showLikeGroup(dreamId) {
  	currentDreamId=dreamId; 
  	showGroup();
  	checkMyEmail(); 
  }
  function showNotLikeGroup(dreamId) {
  	currentDreamId=dreamId; 
  	ajaxSubmitDream('N');
  }

  function ajaxSubmitDream(enjoyFlag){
  		if(!canSubmitDream("submitDream_"+currentDreamId,${maxSubmitDreamSeed})){
  			alert("您提交的太频繁了！");
  			return ;
  		}
  		var email = "";
  		if(currentDreamId==null){
  			return;
  		}
  		if(enjoyFlag=='Y'){
  			email=  $("#dreamEmail").val();
  		}
  		
  		$.ajax(
  			{type:"POST",
  			 async:true,
  			 url:"/group/submitDream.do",
  			 data:{dreamId:currentDreamId,email:email,enjoyFlag:enjoyFlag}, 
  			 dataType:"json", 
			 success:function (data) {
				var groupDream  = data.groupDream;
				$("#dreamEnjoy"+groupDream.dreamId).html(groupDream.enjoyCount);
				$("#dreamNotEnjoy"+groupDream.dreamId).html(groupDream.notEnjoyCount);
				$("#dreamJoinTotalCount"+groupDream.dreamId).html(groupDream.joinTotalCount);
			}
			}		
		);
		$("#dreamEmail").val("");
		$('#btnEmailSubmit').removeAttr('disabled');
  }
</script>
</body>
</html>

