<!--精彩点评小列表-->
var _comentContent = "";
<@s.iterator value="bestCommentList" status="sts">
    _comentContent = _comentContent + '<div class="cmtbox"><div class="cmtboxtit"><p><span class="cmtname"><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(20,userNameExp)" /></span><span class="cmtpro">点评 <a href=http://www.lvmama.com/dest/${pinYinUrl} target="_blank">${placeName?if_exists} </a></span>    [ ${placeName?if_exists} · <#if stage==1>目的地<#elseif stage==3>酒店<#else>景点</#if> ]</p></div><div class="cmtboxtxt"><@s.property value="@com.lvmama.eshop.util.StringUtil@cutString2(80,commentDelEnter)" /> <a href=http://www.lvmama.com/comment/${commentId} target="_blank">查看全文>></a></div></div>';
</@s.iterator>
document.write(_comentContent);
