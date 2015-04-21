 <@s.if test="place.stage == 1 && currentTab == null">
    <title><@s.property value="place.seoName" escape="false"/>哪里好玩_<@s.property value="place.seoName" escape="false"/>有什么好玩地方_周边哪些景点_驴妈妈城市点评</title>
	<meta name="keywords" content="<@s.property value="place.seoName" escape="false"/>好玩吗,<@s.property value="place.seoName" escape="false"/>周边景点">
	<meta name="description" content="【<@s.property value="place.seoName" escape="false"/>点评】<@s.property value="place.seoName" escape="false"/>好玩吗？知道<@s.property value="place.seoName" escape="false"/>哪里最好玩？周边有什么好玩景点?地方？又是怎么样的印象？看网友们对<@s.property value="place.seoName" escape="false"/>的点评评价吧！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
 <@s.if test="currentTab == 'hotel'">
 	<title><@s.property value="place.seoName"/>住宿攻略_<@s.property value="place.seoName"/>旅游住宿团购_市区住哪便宜,方便_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>住宿团购,<@s.property value="place.seoName"/>住宿攻略,<@s.property value="place.seoName"/>住宿">
	<meta name="description" content="【<@s.property value="place.seoName"/>住宿攻略】：提供<@s.property value="place.seoName"/>旅游住宿信息查询-酒店、宾馆团购价预订服务！<@s.property value="place.seoName"/>市区便宜、经济、商务等多套型住宿产品！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'dish'">
  	<title><@s.property value="place.seoName"/>美食攻略_<@s.property value="place.seoName"/>特色美食团购_小吃街,哪些好吃地方_驴妈妈旅游网</title>
	<meta name="keywords" content="<@s.property value="place.seoName"/>美食,<@s.property value="place.seoName"/>小吃,<@s.property value="place.seoName"/>宵夜">
	<meta name="description" content="【<@s.property value="place.seoName"/>美食攻略】：推荐<@s.property value="place.seoName"/>特色美食，小吃，饭店等好吃的地方，<@s.property value="place.seoName"/>晚上去哪吃味道最好的宵夜，更多精选上海美食团购信息！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'traffic'">
  		<title><@s.property value="place.seoName"/>交通地图_<@s.property value="place.seoName"/>公交路线查询_自驾路书【下载】_驴妈妈旅游网</title>
		<meta name="keywords" content="<@s.property value="place.seoName"/>交通地图,<@s.property value="place.seoName"/>公交路线">
		<meta name="description" content="【<@s.property value="place.seoName"/>交通地图】：提供<@s.property value="place.seoName"/>公交路线/车站/地铁/换乘查询，<@s.property value="place.seoName"/>自驾路书手册,旅游景点分布地图，免费下载功能！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'entertainment'">
	  	<title><@s.property value="place.seoName"/>游玩好去处_<@s.property value="place.seoName"/>周边去哪玩_吃喝娱乐场所,推荐_驴妈妈旅游网</title>
		<meta name="keywords" content="<@s.property value="place.seoName"/>游玩,<@s.property value="place.seoName"/>好去处,<@s.property value="place.seoName"/>度假">
		<meta name="description" content="【<@s.property value="place.seoName"/>游玩好去处】：推荐<@s.property value="place.seoName"/>周边度假，白天、晚上去哪玩等等人气颇高的大众游玩场所信息。全方位介绍<@s.property value="place.seoName"/>娱乐场所大全！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'shop'">
  		<title><@s.property value="place.seoName"/>购物攻略_<@s.property value="place.seoName"/>旅游购物中心_去买什么,哪里便宜-驴妈妈旅游网</title>
		<meta name="keywords" content="<@s.property value="place.seoName"/>土特产,<@s.property value="place.seoName"/>购物攻略,<@s.property value="place.seoName"/>购物中心">
		<meta name="description" content="【<@s.property value="place.seoName"/>购物攻略】：介绍<@s.property value="place.seoName"/>购物中心，商业圈地理位置，到了<@s.property value="place.seoName"/>买什么土特产，享受疯狂的shopping，让您满载而归！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'weekendtravel'">
  		<title><@s.property value="place.seoName"/>一日游景点_<@s.property value="place.seoName"/>旅游行程安排_线路推荐,怎么玩_驴妈妈旅游网</title>
		<meta name="keywords" content="<@s.property value="place.seoName"/>一日游,<@s.property value="place.seoName"/>行程安排,<@s.property value="place.seoName"/>旅游路线">
		<meta name="description" content="【<@s.property value="place.seoName"/>一日游景点】：推荐<@s.property value="place.seoName"/>旅游行程安排、经典游玩路线介绍，不错过每一个好玩的地方！告诉你怎么玩转<@s.property value="place.seoName"/>的玩法！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
  <@s.if test="currentTab == 'place' || currentTab == 'scenery'">
  		<title><@s.property value="place.seoName"/>景点介绍_<@s.property value="place.seoName"/>周边好玩的景点_免费,著名,有哪些_驴妈妈旅游网</title>
		<meta name="keywords" content="<@s.property value="place.seoName"/>景点介绍,<@s.property value="place.seoName"/>周边景点,<@s.property value="place.seoName"/>好玩景点">
		<meta name="description" content="【<@s.property value="place.seoName"/>景点介绍】：推荐<@s.property value="place.seoName"/>著名景点，最好玩的景点，还有一些免费开放的景点，还有哪些不能错过的地方，更多<@s.property value="place.seoName"/>景点大全指南！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>
 <@s.if test="currentTab == 'photo'">
 	<title><@s.property value="place.seoName"/>图片_<@s.property value="place.seoName"/>风景图片_老照片,旅游摄影美图【高清】_驴妈妈旅游网</title>
  	<meta name="keywords" content="<@s.property value="place.seoName"/>照片,<@s.property value="place.seoName"/>图片,<@s.property value="place.seoName"/>摄影">
  	<meta name="description" content="【<@s.property value="place.seoName"/>图片大全】：藏有<@s.property value="place.seoName"/>风景图片，历史文化珍藏老照片，还有驴友分享<@s.property value="place.seoName"/>旅游摄影美图！给大家展示一个高清的上海印象！《自助游天下，就找驴妈妈！》-lvmama.com">
 </@s.if>  
 
 
