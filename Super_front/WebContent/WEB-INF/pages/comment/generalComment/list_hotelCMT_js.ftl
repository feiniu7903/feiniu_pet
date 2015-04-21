<!--酒店点评招募。日期：2012-3-1 到 2012-3-31 结束-->
var _comentContent="";
<@s.iterator value="comments" status="sts">
_comentContent = _comentContent + '<li><@s.date name="createdTime" format="yyyy-MM-dd HH:mm"/><span><em><@s.property value='@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(-1,userNameExp)' /></em>对<strong><@s.if test='${productId!=null}'>驴妈妈产品</@s.if><@s.else><@s.property value='placeName'/></@s.else></strong>说</span>：<@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(35,contentDelEnter)" /><a href=http://www.lvmama.com/comment/<@s.property value="commentId"/> target="_blank">[详情]</a></li>';
</@s.iterator>
document.getElementById("<@s.property value="name"/>").innerHTML = '<ul>' + _comentContent + '</ul>';