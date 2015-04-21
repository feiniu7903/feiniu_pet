<div class="info-set fl">
<h3>账户设置</h3>
<p>手机：<span class="setitem"><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(user.mobileNumber)" /></span>
	<@s.if test="user.mobileNumber!=null && user.mobileNumber.length()>0 ">
		<@s.if test='user.isMobileChecked == "Y" '>
			<span class="setinfo" style="margin-left:15px;"><a href="/myspace/userinfo/phone.do">修改</a></span>
		</@s.if>
		<@s.else>
			<a class="pop link-btn ui-btn ui-btn1"><i>修改</i></a>
		</@s.else>
	</@s.if>
	<@s.else>
		未绑定 <a href="/myspace/userinfo/phone.do" class="link-btn ui-btn ui-btn1"><i>立即绑定</i></a>
	</@s.else>
</p>
</div>
<div class="lv_pop ie6png  mylv_ckzh_pop">
	<div class="lv_pop_inner">
		<div class="lv_pop_close"></div>
		<div class="lv_pop_tit">提示</div>
		<div class="lv_pop_cont">
			<p class="lv_pop_p">您登陆账号还未验证手机，请先验证手机</p>
		</div>
		<div class="lv_pop_btn">
			<a href="/myspace/userinfo/phone.do" class="mylv_ckzh_btn">验证手机</a>
		</div>
	</div>
</div>
<div id="pageOver"></div>
<script language="javascript" type="text/javascript">
function sucshow(showPop,evtobj,black_bg,selfevt){
	   $(evtobj).live("click",function(){
			   var evt_index=$(this).index();
			   var w_scroll =parseInt(document.body.offsetWidth/2);
			   var w_object =parseInt($(showPop).width()/2);
			   var e_obj_top=$(window).scrollTop()+$(window).height()/2-$(showPop).height()/2;
			   if(e_obj_top<$(window).scrollTop()){
				  e_obj_top=$(window).scrollTop();
			   }
			   var l_obj =w_scroll-w_object;
			   var t_obj =e_obj_top;
			   $(showPop).css({"left":l_obj,"top":t_obj,"margin":"auto"});
		   $(showPop).show();
		   var dh=document.body.scrollHeight;
		   var wh=window.screen.availHeight;
		   var yScroll;
		   dh>wh?yScroll =dh:yScroll = wh;
		   $(black_bg).css("height", yScroll);
		   $(black_bg).show();
	   })
}//弹出层
 $(".pop").each(function(){
    sucshow(".mylv_ckzh_pop",".pop","#pageOver");//弹出框对象，触发对象，蒙层
 })
 close_evt(".lv_pop_close","#pageOver",".mylv_ckzh_pop");//关闭弹出层
function close_evt(close_btn,black_bg,popdiv){	      
		$(close_btn).bind("click",function(){
		$(this).parents(popdiv).hide();
		$(black_bg).hide();
	 });
}//弹出层关闭
</script>