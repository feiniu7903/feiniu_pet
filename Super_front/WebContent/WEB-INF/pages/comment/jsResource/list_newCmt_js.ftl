<!--最新点评小列表-->
var _comentContent1 = '<h3><span><a href="http://www.lvmama.com/comment/${place.id}-1" target="_blank">查看更多点评&raquo;</a></span>最新点评</h3>';
var _comentContent2 = "";
<@s.iterator value="cmtCommentVOList" status="sts">
    _comentContent2 = _comentContent2 + '<li><a href=http://www.lvmama.com/comment/${commentId} target="_blank"><@s.property value="@com.lvmama.eshop.util.StringUtil@cutString2(50,contentDelEnter)" /></a><div><@s.property value="@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(20,userNameExp)" />&nbsp;&nbsp;<span><@s.date name="createdTime" format="yyyy-MM-dd"/></span></div></li>';
</@s.iterator>
document.write(_comentContent1 + '<ul>' + _comentContent2 + '</ul>');
