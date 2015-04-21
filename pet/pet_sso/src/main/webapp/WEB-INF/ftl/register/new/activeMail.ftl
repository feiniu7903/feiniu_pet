<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>邮箱注册+激活邮箱</title>
<link href="http://pic.lvmama.com/styles/v7style/globalV1_0.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/header-air.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/ob_login/l_login.css"/>
<script src="http://pic.lvmama.com/js/new_v/jquery-1.7.2.min.js"></script>

</head>

<body>
	<div id="login_main" class="login_main">
		<div class="login_top">
			<span class="login_logo">
				<a href="http://www.lvmama.com"><img src="http://pic.lvmama.com/img/new_v/ob_login/login_logo.jpg"></a> <label class="text">|</label> <a class="text">免费注册</a>
			</span>
			<span class="login_hotline">1010-6060</span>
		</div>
		<div class="zhfs_center">
			<div class="zhfs_re regc_yx">
				<strong class="zhfs_re_strong">恭喜，邮箱验证成功。您获得了<label class="organge">300积分</label>的奖励。</strong>
				<ul class="regc_ul">
					<li class="reg_li_middle">您现在想去：
						<a href="http://www.lvmama.com" class="regc_sb regc_sy"></a>
						<a href="http://www.lvmama.com/myspace/account/store.do" class="regc_sb regc_wdlmmckzh"></a>
						<a href="http://www.lvmama.com/ticket" class="link_blue">打折门票</a>
						<a href="http://www.lvmama.com/freetour" class="link_blue">周边自由行</a>
						<a href="http://www.lvmama.com/around" class="link_blue">周边跟团游</a>
					</li>
					<li><i class="regc_sb reg_dian"></i><label id="lbl_djs">10</label>秒钟后将自动跳转到驴妈妈首页。</li>
					<li><i class="regc_sb reg_dian"></i>如果没有自动跳转，<a href="http://www.lvmama.com" class="link_blue">请点击这里>></a></li>
				</ul>
			</div>
		</div>
	</div>
<!-- 尾部 -->
<#include "/WEB-INF/ftl/comm/footer.ftl"/>
<!-- 尾部 -->
<script type="text/javascript">
	$(function(){
		var loop = function(){
			var lbl = $("#lbl_djs");
			var num = parseInt(lbl.html());
			num--;
			if(num<0){
				location.href = "http://www.lvmama.com";
			}else{
				lbl.html(num);
			}
			setTimeout(loop,1000);
		}
		loop();
	})
</script>
<script src="http://pic.lvmama.com/min/index.php?g=common"></script>
</body>
<#include "/WEB-INF/ftl/common/mvHost.ftl"/>
</html>