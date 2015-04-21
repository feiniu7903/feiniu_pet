	<@s.if test='currentTab=="products" && maps!="true"'>
		<title><@s.property value="seoIndexPage.seoTitle"/></title>
		<meta name="keywords" content="<@s.property value="seoIndexPage.seoKeyword"/>">
	 	<meta name="description" content="<@s.property value="seoIndexPage.seoDescription"/>">
	</@s.if>
	<@s.else>
	    <title><@s.property value="seoIndexPage.seoTitle"/></title>
		<meta name="keywords" content="<@s.property value="seoIndexPage.seoKeyword"/>">
	 	<meta name="description" content="<@s.property value="seoIndexPage.seoDescription"/>">
	</@s.else>