<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>常用游客信息-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-visitor">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>
				&gt;
				<a href="http://www.lvmama.com/myspace/userinfo.do">我的信息</a>
				&gt;
				<a class="current">常用游客信息</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			<div class="lv-content">


<!-- 游客信息>> -->
<div class="ui-box mod-edit visitor-edit">
	<div class="ui-box-title"><h3>常用游客信息</h3></div>
	<div class="ui-box-container">
    	<!-- 游客信息>> -->
    	<p class="no-list"> <@s.if test="receiversList==null">  您还没有添加过常用游客！ </@s.if>
    	<a href="/myspace/userinfo/visitor.do?command=goToSet" class="ui-btn ui-btn4"><i>新增常用游客</i></a></p>
        <!-- <<游客信息 -->
<@s.if test='displaySetReceiverUserForm=="Y"'>
    	<!-- 添加游客>> -->
<form action="/myspace/userinfo/visitor.do" method="post" id="setUserForm">
<input type="hidden" name="command" value="set" />
<@s.if test="currentUserReceiver != null && currentUserReceiver.receiverId != null">
<input type="hidden" name="receiverId" value="<@s.property value="currentUserReceiver.receiverId" />" />
</@s.if>
    	<div class="edit-box clearfix visitor-edit-box">
        	<div class="edit-inbox">
            <p><label><span>*</span>姓名：</label>
            <@s.if test="currentUserReceiver != null && currentUserReceiver.receiverName != null">
            <input type="text" id="receiverNameId" name="receiverName" value="<@s.property value="currentUserReceiver.receiverName" />" class="input-text input-uname" />
            </@s.if>
            <@s.else>
            <input type="text" id="receiverNameId" name="receiverName" value="" class="input-text input-uname" />
            </@s.else><span id='receiverNameIdTip'></span>
            </p>
            <p><label><span>*</span>手机号码：</label>
            <@s.if test="currentUserReceiver != null && currentUserReceiver.mobileNumber != null">
            <input type="text" id="receiverMobileNumberId" name="receiverMobileNumber" value="<@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(currentUserReceiver.mobileNumber)" />" class="input-text input-phone" maxlength="13"/>
            </@s.if>
            <@s.else>
            <input type="text" id="receiverMobileNumberId" name="receiverMobileNumber" value="" class="input-text input-phone" maxlength="13" />
            </@s.else><span id='receiverMobileNumberIdTip'></span>
            </p>
            <p><label>证件类型：</label>
            <@s.if test="currentUserReceiver != null && currentUserReceiver.cardType != null">
            <select class="lv-select select-idtype" name="receiverCardType">
                <@s.if test='currentUserReceiver.cardType == "ID_CARD"'>
            	<option value="ID_CARD" selected="selected">身份证</option>
                <option value="HUZHAO">护照</option>
                </@s.if>
                <@s.elseif test='currentUserReceiver.cardType == "HUZHAO"'>
                 <option value="ID_CARD">身份证</option>
                <option value="HUZHAO" selected="selected">护照</option>
                </@s.elseif>
                <@s.else>
                 <option value="ID_CARD">身份证</option>
                 <option value="HUZHAO">护照</option>
                </@s.else>
            </select>
            </@s.if>
            <@s.else>
            <select class="lv-select select-idtype" name="receiverCardType">
            	<option value="ID_CARD">身份证</option>
                <option value="HUZHAO">护照</option>
            </select>
            </@s.else>
            </p>
            <p><label>证件号码：</label>
            <@s.if test="currentUserReceiver != null && currentUserReceiver.cardNum != null">
            <input type="text" name="receiverCardNum" value="<@s.property value="currentUserReceiver.cardNum" />" class="input-text input-idcards" />
            </@s.if>
            <@s.else>
            <input type="text" name="receiverCardNum" value="" class="input-text input-idcards" />
            </@s.else>
            </p>
            <p><label>邮寄地址：</label>
            <@s.if test="currentUserReceiver != null && currentUserReceiver.address != null">
            <input type="text" name="receiverAddress" value="<@s.property value="currentUserReceiver.address" />" class="input-text input-address" />
            </@s.if>
            <@s.else> 
            <input type="text" name="receiverAddress" value="" class="input-text input-address" />
            </@s.else>           
            </p>
            <p><label>邮编：</label>
            <@s.if test="currentUserReceiver != null && currentUserReceiver.postCode != null">
            <input type="text" name="receiverPostCode" value="<@s.property value="currentUserReceiver.postCode" />" class="input-text input-zipcode" />
            </@s.if>
            <@s.else>
            <input type="text" name="receiverPostCode" value="" class="input-text input-zipcode" />
            </@s.else>   
            </p>
            <p><a class="ui-btn ui-button" onclick="submitSetUser();"><i>&nbsp;确 定&nbsp;</i></a></p>
            </div>
        </div>
</form>
        <!-- <<添加游客 -->
 </@s.if>

 <@s.if test="receiversList!=null">       
        <!-- 游客列表>> -->
        <div class="visitor-list">
        	<table class="lv-table visitor-table">
            <colgroup>
            <col class="lvcol-1">
            <col class="lvcol-2">
            <col class="lvcol-3">
            <col class="lvcol-4">
            <col class="lvcol-5">
            <col class="lvcol-6">
            <col class="lvcol-7">
            </colgroup>
            <thead>
			    <tr class="thead">
			        <th>姓名</th>
			        <th>手机号码</th>
			        <th>证件类型</th>
			        <th>证件号码</th>
			        <th>邮寄地址</th>
			        <th>邮编</th>
			        <th>操作</th>
			    </tr>
             </thead>
             <tbody class="tbody">
             
             <@s.iterator id="upld" value="receiversList" status="index">
			    <tr>
				    <td><@s.property value="receiverName" /></td>
			        <td><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenMobile(mobileNumber)" /></td>
			        <@s.if test='cardType == "ID_CARD"'>
			        <td>身份证</td>
			         </@s.if>
			         <@s.elseif test='cardType == "HUZHAO"'>
			         <td>护照</td>
			         </@s.elseif>
                     <@s.else>
                     <td></td>
                     </@s.else>
				    <td><@s.property value="@com.lvmama.comm.utils.StringUtil@hiddenIDCard(cardNum)" /></td>
				    <td><@s.property value="address" /></td>
				    <td><@s.property value="postCode" /></td>
				    <td><a href="/myspace/userinfo/visitor.do?command=goToSet&receiverId=<@s.property value="receiverId" />">修改</a> <a class="xh_del-this" href="javascript:;" onclick="xh_dialog('<@s.property value="receiverId" />');">删除</a></td>
			    </tr>
			 </@s.iterator>  
               </tbody>
			</table>
			<div class="pages">
                    <@s.property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>	
		    </div> 
			     
        </div>
        <!-- <<游客列表 -->
 </@s.if>
	</div>
</div>
<!-- <<游客信息 -->

			</div>
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>

<div class="xh_overlay"></div>
<div class="xh_dialog">
	<div class="xh_dialog-bg"></div>
    <div class="xh_dialog-box">
    	<span class="close-this">&times;</span>
    	<h3>您确定要删除吗？</h3>
        <p>删除后，信息将不可恢复！</p>
        <div class="xh_dialog-btn">
        	<a class="ui-btn xh_btn-ok" href="#">确&nbsp;定</a><a class="ui-btn xh_btn-cancel" href="javascript:void(0);">取&nbsp;消</a>
        </div>
    </div>
</div>
<script type="text/javascript">
function submitSetUser()
{
	var flag=true;
	if(!checkUserName($('#receiverNameId').val())){
		flag=false;
		$('#receiverNameIdTip').html("<span class='tips-ico02'></span>");
		$('#receiverNameIdTip').show();
	}
	if( !checkMobile($('#receiverMobileNumberId').val().replace(/\s+/g,''))){
		flag=false;
		$('#receiverMobileNumberIdTip').html("<span class='tips-error'><span class='tips-ico02'></span>请输入有效手机号</span>");
		$('#receiverMobileNumberIdTip').show();
	}
	if(flag){
		$("#setUserForm").submit();
	}
}

$("span.close-this").click(function(){
   $(this).parents("div.xh_dialog").hide();
});

 function xh_dialog(deleteId){
  var top=$(document).scrollTop();
  $("div.xh_dialog").css("top",top+230+"px").show();
  /*$("div.xh_overlay").show().height($(document).height());*/
 
 $(".xh_btn-ok").attr("href","/myspace/userinfo/visitor.do?command=delete&receiverId="+deleteId);
 }

	$('#receiverMobileNumberId').bind("blur", function(){
		clearContent("#receiverMobileNumberIdTip");
		if(checkMobile($('#receiverMobileNumberId').val().replace(/\s+/g,''))){
			$('#receiverMobileNumberIdTip').html("<span class='tips-ico01'></span>");
			$('#receiverMobileNumberIdTip').show();
		}else{
			$('#receiverMobileNumberIdTip').html("<span class='tips-error'><span class='tips-ico02'></span>请输入有效手机号</span>");
			$('#receiverMobileNumberIdTip').show();
		}
	}).bind("focus", function(){
		clearContent("#receiverMobileNumberIdTip");
	});
	$('#receiverNameId').bind("blur", function(){
		clearContent("#receiverNameIdTip");
		if(checkUserName($('#receiverNameId').val())){
			$('#receiverNameIdTip').html("<span class='tips-ico01'></span>");
			$('#receiverNameIdTip').show();
		}else{
			$('#receiverNameIdTip').html("<span class='tips-error'><span class='tips-ico02'></span>请输入姓名</span>");
			$('#receiverNameIdTip').show();
		}
	}).bind("focus", function(){
		clearContent("#receiverNameIdTip");
	});
	function clearContent(name) {
		if ($(name).length > 0) {
			$(name).empty();
			$(name).hide();
		} 
	}
	function checkMobile(v){
		if(!(/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(v))&&!/^1[0-9]{2,2}[\*]{4,4}[0-9]{4,4}$/.test(v)){
			return false;
		}else{
			return true;
		}
	}
	function checkUserName(v){
		if(v!=null && $.trim(v).length>0){
			return true;
		}else{
			return false;
		}
	}
</script>
	<script>
		cmCreatePageviewTag("常用游客信息", "D0001", null, null);
	</script>
</body>
</html>