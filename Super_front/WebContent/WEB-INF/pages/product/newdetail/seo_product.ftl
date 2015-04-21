<@s.if test='prodCProduct.prodProduct.isTicket()'>
	<title><@s.property value="prodCProduct.prodProduct.productName" escape="false"/>-驴妈妈门票预订</title>
	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="<#if (comPlace)?? && (comPlace.name)??><@s.property value="comPlace.name" escape="false"/>,</#if>门票预订"/>
	<meta name="description" content='驴妈妈旅游网门票预订:<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(null!=viewPage.contents.get('FEATURES').content?viewPage.contents.get('FEATURES').content.replaceAll('－|(<[^>]*>)|(&[^;]*;)|(\\\\s)',''):'',100)" escape="false"/>'/>	
</@s.if>
<@s.elseif test='prodCProduct.prodProduct.isGroup() || prodCProduct.prodProduct.subProductType=="GROUP_FOREIGN"'>
	<title><@s.property value="prodCProduct.prodProduct.productName" escape="false"/>-驴妈妈跟团游</title>
	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="<#if (prodCProduct)??&&(prodCProduct.from)??><@s.property value="prodCProduct.from.name" escape="false"/></#if><#if (prodCProduct)??&&(prodCProduct.to)??>,<@s.property value="prodCProduct.to.name" escape="false"/></#if><@s.if test='prodCProduct.prodProduct.subProductType=="GROUP_FOREIGN"'>,出境游</@s.if><@s.else>,国内游</@s.else>"/>
	<meta name="description" content="驴妈妈旅游网跟团游:<@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(null!=viewPage.contents.get('FEATURES').content?viewPage.contents.get('FEATURES').content.replaceAll('－|(<[^>]*>)|(\\\\s)|(&[^;]*;)',''):'',100)"  escape="false"/>"/>     
</@s.elseif>
<@s.elseif test='prodCProduct.prodProduct.isFreeness() || prodCProduct.prodProduct.subProductType=="FREENESS_FOREIGN"'>
	<title><@s.property value="prodCProduct.prodProduct.productName" escape="false"/>-驴妈妈自由行</title>
	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="自由行<#if (prodCProduct)??&&(prodCProduct.from)??>,<@s.property value="prodCProduct.from.name" escape="false"/></#if><#if (prodCProduct)??&&(prodCProduct.to)??>,<@s.property value="prodCProduct.to.name" escape="false"/></#if>"/>
	<meta name="description" content="驴妈妈旅游网自由行:<@s.if test="null!=viewPage.contents.get('MANAGERRECOMMEND').content  && ''!=viewPage.contents.get('MANAGERRECOMMEND').content"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(viewPage.contents.get('MANAGERRECOMMEND').content.replaceAll('－|(<[^>]*>)|(\\\\s)|(&[^;]*;)',''),100)"  escape="false"/></@s.if><@s.else><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(null!=viewPage.contents.get('FEATURES').content?viewPage.contents.get('FEATURES').content.replaceAll('－|(<[^>]*>)|(\\\\s)|(&[^;]*;)',''):'',100)"  escape="false"/></@s.else>"/>	
</@s.elseif>
<@s.elseif test='prodCProduct.prodProduct.isHotel()'>
	<title><@s.property value="prodCProduct.prodProduct.productName" escape="false"/>_<@s.property value="prodCProduct.to.name" escape="false"/>-驴妈妈酒店预订</title>
	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="<@s.property value="prodCProduct.to.name" escape="false"/>,酒店预订"/>
	<meta name="description" content="驴妈妈旅游网酒店预订:<@s.if test="null!=viewPage.contents.get('MANAGERRECOMMEND').content  && ''!=viewPage.contents.get('MANAGERRECOMMEND').content"><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(viewPage.contents.get('MANAGERRECOMMEND').content.replaceAll('－|(<[^>]*>)|(\\\\s)|(&[^;]*;)',''),100)"  escape="false"/></@s.if><@s.else><@s.property value="@com.lvmama.comm.utils.StringUtil@subStringStr(null!=viewPage.contents.get('FEATURES').content?viewPage.contents.get('FEATURES').content.replaceAll('－|(<[^>]*>)|(\\\\s)|(&[^;]*;)',''):'',100)"  escape="false"/></@s.else>"/>
</@s.elseif>
<@s.else>
	<title><@s.property value="prodCProduct.prodProduct.productName" escape="false"/></title>
	<link rel="shortcut icon" href="http://www.lvmama.com/favicon.ico" type="image/x-icon" /> 
	<meta name="keywords" content="<@s.property value="prodCProduct.prodProduct.productName" escape="false"/>"/>
	<meta name="description" content="<@s.property value="prodCProduct.prodProduct.productName" escape="false"/>"/>
</@s.else>
