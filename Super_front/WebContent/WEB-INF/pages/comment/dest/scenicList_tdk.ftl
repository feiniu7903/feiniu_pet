 <@s.if test='place.stage == 2 && (currentTab == null || currentTab == "")'>
    <title><@s.property value="place.seoName" escape="false"/>好玩吗_<@s.property value="place.seoName" escape="false"/>有什么好玩,怎么样_驴妈妈景区点评</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>好玩吗,<@s.property value="place.seoName"/>怎么样">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>点评】<@s.property value="place.seoName" escape="false"/>好玩吗？附近还有什么好玩的吗？又是怎么样的印象？看看网友们对<@s.property value="place.seoName" escape="false"/>的点评评价吧！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
 <@s.if test='place.stage == 3 && (currentTab == null || currentTab == "")'>
    <title><@s.property value="place.seoName" escape="false"/>怎么样_服务环境好不好【入住满意度100%】_驴妈妈酒店点评</title>
	<meta name="keywords" content="<@s.property value="place.seoName" escape="false"/>怎么样">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>点评】<@s.property value="place.seoName" escape="false"/>怎么样？服务、环境、入住满意吗？看看网友们对<@s.property value="place.seoName" escape="false"/>的点评评价吧！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
 <@s.if test="currentTab == 'hotel'">
 	<title><@s.property value="place.seoName"/>附近住宿_<@s.property value="place.seoName"/>周边酒店_宾馆,住哪便宜_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>附近住宿,<@s.property value="place.seoName"/>周边酒店">
	<meta name="description" content="【<@s.property value="place.name" escape="false"/>附近住宿】：提供<@s.property value="place.name" escape="false"/>周边酒店，宾馆住宿预订服务，选择舒适/经济/便宜的酒店入住，满意度100%！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'dish'">
	<title><@s.property value="place.name" escape="false"/>美食_<@s.property value="place.name" escape="false"/>特色小吃_附近饭店,餐厅_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.name"/>美食,<@s.property value="place.name"/>小吃">
	<meta name="description" content="【<@s.property value="place.name" escape="false"/>美食】：不能错过的<@s.property value="place.name" escape="false"/>特色小吃一定要去尝一尝哟，还可以选择景区餐厅或附近吃饭的地方尝到当地特色美食！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'traffic'">
  	<title><@s.property value="place.seoName" escape="false"/>地址_<@s.property value="place.seoName" escape="false"/>在哪里_怎么去,附近公交,交通_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName" escape="false"/>地址,<@s.property value="place.seoName" escape="false"/>在哪里">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>地址】：<@s.property value="place.seoName" escape="false"/>在哪里？怎么去？附近公交有哪些？交通路线怎么去？等等的问题，由我们来为您一一解答！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'entertainment'">
  	 <title><@s.property value="place.seoName" escape="false"/>贴士_<@s.property value="place.seoName" escape="false"/>注意事项_开园时间,景区电话_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>贴士,<@s.property value="place.seoName"/>注意事项">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>贴士】：提示<@s.property value="place.seoName" escape="false"/>注意事项，开园时间，景区电话等信息查询服务！提供<@s.property value="place.seoName" escape="false"/>#打折门票#在线预订！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'shop'">
  	<title><@s.property value="place.seoName" escape="false"/>购物_<@s.property value="place.seoName" escape="false"/>纪念品_土特产,附近有什么_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>购物,<@s.property value="place.seoName" escape="false" />纪念品">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>购物】：<@s.property value="place.seoName" escape="false"/>纪念品，景区独有的土特产！介绍景区附近好玩的商圈，方便游客一体式的旅游购物！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'weekendtravel'">
  	<title><@s.property value="place.seoName" escape="false"/>一日游_<@s.property value="place.seoName" escape="false"/>游玩线路_指南,怎么玩-驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>一日游,<@s.property value="place.seoName"/>游玩">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>一日游】：推荐<@s.property value="place.seoName" escape="false"/>游玩线路，旅游指南，告诉你怎么玩转体验！提供<@s.property value="place.seoName" escape="false"/>#打折门票#在线预订！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'place' || currentTab == 'scenery'">
  	<title><@s.property value="place.seoName" escape="false"/>介绍_<@s.property value="place.seoName" escape="false"/>简介_导游词,游览线路_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>介绍,<@s.property value="place.seoName" escape="false" />导游词">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>介绍】：<@s.property value="place.seoName" escape="false"/>导游词简介大全，告诉你有哪些必玩项目,不可错过的线路！提供<@s.property value="place.seoName" escape="false"/>#打折门票#在线预订！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'photo'">
  	<title><@s.property value="place.seoName"/>图片_<@s.property value="place.seoName"/>景点地图_摄影照片【高清】_驴妈妈旅游网</title>
  	<meta name="keywords" content="<@s.property value="place.seoName"/>图片,<@s.property value="place.seoName"/>地图">
  	<meta name="description" content="【<@s.property value="place.name" escape="false"/>图片大全】：藏有<@s.property value="place.name" escape="false"/>景点地图，摄影照片旅游摄影美图！给大家展示一个高清的印象！还有驴友分享《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>  
