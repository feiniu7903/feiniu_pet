<!--春节点评。日期：2014-1-20 到 2014-2-20 结束-->
var _comentContent="";
<@s.iterator value="comments" status="sts">
_comentContent = _comentContent + '<li><div class=\"user\"><span><@s.property value='@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(10,userNameExp)' /></span><em> 点评  </em><i><@s.if test='${productId!=null}'><@s.property value='@com.lvmama.comm.utils.StringUtil@cutString2(20,productName)'/></@s.if><@s.else><@s.property value='@com.lvmama.comm.utils.StringUtil@cutString2(15,placeName)'/></@s.else></i></div><p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(50,contentDelEnter)" /><a href=http://www.lvmama.com/comment/<@s.property value="commentId"/> target="_blank">[详情]</a></p><b><@s.date name="createdTime" format="yyyy-MM-dd HH:mm"/></b></li>';
</@s.iterator>
document.getElementById("<@s.property value="name"/>").innerHTML =  _comentContent ;
