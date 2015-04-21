<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>积分商城</title>
<meta name="keywords" content=""/>
<meta name="description" content="">

<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/header-air.css,/styles/v3/core.css,/styles/v3/module.css" >
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v3/points.css">
<!-- <?php include("common/meta.html"); ?> -->
<#include "/common/coremetricsHead.ftl">
</head>
<body>

<!-- <?php include("common/header.html"); ?> -->
<#include "/common/header.ftl">

<!-- wrap\\ 1 -->
<div class="wrap">
    <div class="lv-crumbs oldstyle">
        <p>
            <strong>您当前所处的位置：</strong>
            <span><a href="http://www.lvmama.com/">首页</a>&gt; </span>
            <span><a href="/points">积分商城</a>&gt; </span>
            <span>专题列表页</span>
        </p>
    </div>
    
    <div class="brand-box">

        <div class="brand-list">
        
			<@s.iterator value="brandList" var="branRecInfo" status="sts">
				 <dl class="clearfix">
	                <dt><a href="${branRecInfo.url}"><img src="${branRecInfo.imgUrl}" width="600" height="200" alt="" /></a></dt>
	                <dd>
	                    <h3><span class="num"><@s.property value="#sts.index+${page.startRows}"/></span>${branRecInfo.title}<span class="cc1">${branRecInfo.bakWord1}</span></h3>
	                    <div class="text">
	                        <p class="gray">${branRecInfo.bakWord2}</p>
	                        <p>活动时间：<b>${branRecInfo.bakWord3}</b></p>
	                    </div>
	                    <p class="tc"><a href="${branRecInfo.url}" class="btn btn-orange">&nbsp;立即参与&nbsp;</a></p>
	                </dd>
	            </dl>
			</@s.iterator>
        </div>
        <div class="pages orangestyle">
            <div class="Pages" style="margin: 10px; text-align: right;">
					<@s.property escape="false"
					value="@com.lvmama.comm.utils.Pagination@pagination(page.totalPageNum,page.totalPageNum,page.url,page.currentPage)"/>
			</div>
        </div>
    </div>
    
</div> <!-- //.wrap 1 -->

<!-- <?php include("common/footer.html"); ?> -->
<#include "/common/orderFooter.ftl">
<script>
      cmCreatePageviewTag("会员活动", "D1001", null, null);
 	</script>
</body>
</html>
