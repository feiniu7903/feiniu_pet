<!--驴友热评开始-->
        <div class="c_item_a c_hot_com">
          <h3 class="c_title cbd_a"><strong>口碑榜单</strong><small>驴友口碑推荐 热门景区专题</small></h3>
          <div>
           <@s.iterator value="cmtSpecialSubjectList" id="cmtSpecialSubject" status="sts">
           <@s.if test="#sts.index==0">
          <a href="${url}" target="_blank" class="c_pic_cen"><img src="${absolutePictureUrl}" width="280" height="200"></a>
            <h3 class="tc"><b class="cxh_ico2 f12">当期</b><a  target="_blank" href="${url}">${title}</a></h3>
            <p><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(96,summary)" /></p>
           </@s.if>
           </@s.iterator>
            
            <div class="qikan_box clearfix">
              <ul class="list_qikan">
                <@s.iterator value="cmtSpecialSubjectList" id="cmtSpecialSubject" status="sts">
                <@s.if test="#sts.index!=0">
                      <li><span class="cxh_ico3">第<@s.property value="${versionNum}"/>期</span><a  target="_blank" href="${url}"><@s.property value="@com.lvmama.comm.utils.StringUtil@cutString2(16,title)" /></a></li>
                </@s.if>
       		    </@s.iterator>
              </ul>
              <a class="view_more clr fr"  target="_blank" href="http://www.lvmama.com/zt/">更多&gt;&gt;</a></div>
          </div>
  	   </div>
