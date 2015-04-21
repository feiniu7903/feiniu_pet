<!--驴友热评开始-->
    <div class="c_item_a fl w_item_a">
      <h3 class="c_title cbd_a"><strong>口碑榜单</strong><small>驴友口碑推荐 热门景区专题</small></h3>
      <div class="pd10">
      <@s.iterator value="cmtSpecialSubjectList" id="cmtSpecialSubject" status="sts">
      <@s.if test="#sts.index==0">
      <a href="${cmtSpecialSubject.url}" target="_blank"><img src="${cmtSpecialSubject.getAbsolutePictureUrl()}" width="310" height="205"></a>
        <h3 class="tc"><b class="cxh_ico2 f12">当期</b><a  target="_blank" href="${cmtSpecialSubject.url}">${cmtSpecialSubject.title}</a></h3>
        <p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(100,summary)" /></p>
       </@s.if>
       </@s.iterator>
        <div class="qikan_box clearfix">
          <ul class="list_qikan">
            <@s.iterator value="cmtSpecialSubjectList" id="cmtSpecialSubject" status="sts">
            <@s.if test="#sts.index!=0">
            	<li><span class="cxh_ico3">第<@s.property value="${versionNum}"/>期</span><a href="${cmtSpecialSubject.url}" target="_blank"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(16,title)" /></a></li>
            </@s.if>
       		</@s.iterator>
          </ul>
          <a class="view_more clr fr" href="http://www.lvmama.com/zt/" target="_blank">更多&gt;&gt;</a></div>
      </div>
    </div>
