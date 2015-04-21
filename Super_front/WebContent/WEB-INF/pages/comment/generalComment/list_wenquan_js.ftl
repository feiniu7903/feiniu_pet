<!--十泉十美温泉专题。日期:2011-12-1 到 2011-12-31 结束-->
var _comentContent="";
<@s.iterator value="comments" status="sts">
_comentContent = _comentContent + '<li><div class="list_nums"><@s.property value="#sts.index+1"/></div><div class="list_txt"><span class="list_time"><@s.date name="createdTime" format="yyyy-MM-dd HH:mm"/></span><font class="shuo"><@s.property value='@com.lvmama.comm.utils.StringUtil@replaceOrCutUserName(-1,userNameExp)' />对<font class="sel bold"><@s.property value='titleName'/></font>说：</font><span><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(27,contentDelEnter)" /></span><a class="detail" href=http://www.lvmama.com/comment/<@s.property value="commentId"/> target="_blank">[详情]</a></div></li>';
</@s.iterator>
document.getElementById("<@s.property value="name"/>").innerHTML = '<ul>' + _comentContent + '</ul>';
