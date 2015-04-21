<#--显示一个行程当中的酒店列表--> 
<#macro showJourneyPackHotel product prodJourenyId>                 
    <div class="superFreeSubTitleByProductPack"  tt="hotel" prodJourneyId="${prodJourenyId}"  productId="${product.productId}"   style="display:none;"><h4 class="gaiTitle">酒店详情</h4></div>
	<div class="superFreeSubMainByProductPack"  tt="hotel" prodJourneyId="${prodJourenyId}" productId="${product.productId}"   category="hotel_${prodJourenyId}"  style="display:none;">
    	<div class="jdxqBox">
			<#assign img_index=0 />
			<#list product.toPlace.placePhoto as placePhoto>
				<#if img_index==0 && placePhoto.type=='LARGE' && placePhoto.imagesUrl!=null><#assign img_index = img_index+1 /><img class="jiudianImg"  width="400" height="200" src="http://pic.lvmama.com/pics/${placePhoto.imagesUrl}"></#if>
			</#list>			        	
            <div class="jdxqName">
            	<h4>${product.toPlace.name}</h4>
                <p>酒店地址：${product.toPlace.address}<br />
               <br /> <!--设施服务：${product.toPlace.hotelFacilities}--></p>
            </div>
            <p class="jdxqText">
            	开业时间：${product.toPlace.placeHotel.hotelOpenTimeStr}<br />
                酒店电话：${product.toPlace.placeHotel.hotelPhone}<br />
                酒店类型：${product.toPlace.placeHotel.hotelTopic}<br />
                是否涉外：<#if product.toPlace.placeHotel.hotelForeigner=='true'>是<#else>否</#if>
               <br />
                房间数量(间)：${product.toPlace.placeHotel.hotelRoomNum}
            </p>
            <b class="jdxqB">酒店介绍：</b>
            <p class="jdxqText">
            ${product.toPlace.remarkes}
            </p>
            <b class="jdxqB">交通信息：</b>
            <p class="jdxqText">
           ${product.toPlace.placeHotel.hotelTrafficInfoHtml}
            </p>
        </div>
    </div>
</#macro>

<#--显示单产品，包含当地游、门票等-->
<#macro showSinglePackProduct product prodJourenyId>
	<div class="superFreeSubTitleByProductPack"  tt="TICKET"  prodJourneyId="${prodJourenyId}" productId="${product.productId}"    style="display:none;"><h4 class="gaiTitle">门票</h4></div>
		<div class="superFreeSubMainByProductPack" tt="TICKET"  prodJourneyId="${prodJourenyId}" productId="${product.productId}"    category="hotel_${prodJourenyId}"  style="display:none;">
	    	<div class="jdxqBox">
				<#list product.toPlace.placePhoto as placePhoto>
					<#if placePhoto.type=='LARGE' && placePhoto.imagesUrl!=null><img class="jiudianImg"  width="400" height="200" src="http://pic.lvmama.com/pics/${placePhoto.imagesUrl}"></#if>
				</#list>			        	
	            <div class="jdxqName">
	            	<h4>${product.productName}</h4>
	                <p>所属地区：${product.toPlace.city}<br />
	                景点地址：${product.toPlace.address}<br />
	            	开放时间：${product.toPlace.hotelOpenTime}<br />
	                景区主题：${product.toPlace.firstTopic}<br/></p>
	            </div>
    			<p class="jdxqText">
    			</p>
	            <b class="jdxqB">景点介绍：</b>
	            <p class="jdxqText">
	            ${product.toPlace.description}
	            </p>
	            
	            <b class="jdxqB">预订须知：</b>
	            <p class="jdxqText">
	           ${product.toPlace.orderNotice}
	            </p>
	            
	            <b class="jdxqB">行前须知：</b>
	            <p class="jdxqText">
	           ${product.toPlace.importantTips}
	            </p>
	        </div>
	    </div>	
</#macro>
<#macro displayDiv prodProdutJourneyPackList>
	<#if prodProdutJourneyPackList!=null>
		<#list prodProdutJourneyPackList as ppk>
			<#list ppk.prodProductJourneys as ppj> 
				<#if ppj.prodJourneyGroupMap!=null >
			    	<#list ppj.prodJourneyGroupMap?keys as itemKey>
			    		<#list ppj.prodJourneyGroupMap[itemKey] as branchItem>
			    		<#if branchItem.prodBranch.prodProduct.productType=='HOTEL' && ppk.valid=='true'>
			    			<@showJourneyPackHotel branchItem.prodBranch.prodProduct ppj.prodJourenyId/>
			    		</#if>
			    		<#if branchItem.prodBranch.prodProduct.productType=='TICKET' && ppk.valid=='true'>
			    			<@showSinglePackProduct branchItem.prodBranch.prodProduct ppj.prodJourenyId/>
			    		</#if>
			    		</#list>
			    	</#list>
			    </#if>
	        </#list>    
        </#list>
    </#if>
</#macro>
