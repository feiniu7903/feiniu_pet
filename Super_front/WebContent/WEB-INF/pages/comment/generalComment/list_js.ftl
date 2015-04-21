<!--通用点评列表-->
var _comentContent = document.getElementById("<@s.property value="name"/>").innerHTML;
<@s.iterator value="comments">
	_comentContent = _comentContent + "<p><strong><@s.property value='@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(-1,userNameExp)' /></strong> 对 <a href=http://www.lvmama.com/dest/<@s.property value="pinYinUrl"/>><@s.property value="titleName"/></a> 发表了点评：<br /> \"<@s.property value="@com.lvmama.eshop.util.base.StringUtil@cutString2(40,@com.lvmama.eshop.util.base.StringUtil@replaceHardReturn(content))" />\"[<a href=http://www.lvmama.com/comment/<@s.property value="commentId"/>>详细</a>]</p>";
</@s.iterator>
document.getElementById("<@s.property value="name"/>").innerHTML = _comentContent;
