<script>
	var conextPath='';
</script>
<script type="text/javascript" src="/js/help/help.js"></script>
<input type="hidden" id="currentContentPageTypeId" value="${contentTypeId}"/>
<div class="hc_side">
    <h4 class="hc_hot_fq hc_side_title">帮助中心问题分类</h4>
    <div class="hc_side_list">
      <ul class="side_list_one">
      <@s.iterator value="helpCenterCategoryList" status="helpCenterCategory" id="outer">
      <@s.if test="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList.size()>0">
          <li id="contentPage<@s.property value="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList[0].typeId"/>"><a href="/public/help_<@s.property value="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList[0].typeId"/>">${typeName}</a>
      </@s.if>
       <@s.else>  
         <li id="contentPage"><a href="#">${typeName}</a>
      </@s.else> 
      
          <dl>
            <@s.iterator value="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList" status="childInfoQuesTypeHierarchy" id="inner">
             <input type="hidden" id="businessTypeName${typeId}" value="${outer.typeName}"/>
             <@s.if test="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList.size()>0">
                <input type="hidden" id="defaultContentTypeId${typeId}" value="<@s.property value="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList[0].typeId"/>"/>
             </@s.if>
             <@s.else>
                <input type="hidden" id="defaultContentTypeId${typeId}" value=""/>
             </@s.else>
             <input type="hidden" id="contentTypeName${typeId}" value="${inner.typeName}"/>
             <dd><a href="/public/help_<@s.property value="helpCenterCategoryList[#helpCenterCategory.index].childInfoQuesTypeHierarchyList[#childInfoQuesTypeHierarchy.index].typeId"/>" id="currentContentTypeHref${typeId}">${inner.typeName}</a></dd>
            </@s.iterator>
          </dl>
      </li>
      </@s.iterator>
      </ul>
    </div>
    <h4 class="hc_hot_fq hc_side_title side_margin">旅游百宝箱</h4>
    <div class="hc_side_list side_add_bg">
      <ul class="side_lvx">
        <li class="time_cha"><a href="/public/help_center_time">国际时差</a></li>
        <li class="cu_cha"><a href="/public/help_center_chazuo">各国插座</a></li>
        <li class="visa_icon"><a href="/public/help_passport_visa">各国签证</a></li>
        <li class="lv_gl"><a href="http://www.lvmama.com/guide/" target="_blank">旅游攻略</a></li>
        <li class="hl_suan"><a href="http://www.boc.cn/sourcedb/whpj/" target="_blank">汇率换算</a></li>
        <li class="weather_icon"><a href="http://www.weather.com.cn/textFC/europe.shtml" target="_blank">天气预报</a></li>
      </ul>
    </div>
    <h4 class="hc_hot_fq hc_side_title side_margin">服务时间</h4>
    <div class="hc_side_list side_add_bg side_add_bg01">
      <h5 class="kefu_bg">客服中心</h5>
      <p>服务时间：7 * 24小时</p>
      <p>出境业务：9:00-20:00</p>
      <p>国内长线业务：9:00-20:00</p>
      <p>客服热线：1010 6060</p>
      <p>客服传真：021-69108793</p>
      <p>客服邮箱：<a href="mailto:service@lvmama.com">service@lvmama.com</a></p>
      <h5 class="kefu_bg kefu_bg01">顾客投诉</h5>
      <p>Email：<a href="mailto:service@lvmama.com">service@lvmama.com</a></p>
      <p>投诉专线：1010 6060 转 3</p>
    </div>
  </div>
