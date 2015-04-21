<h3><s></s>产品信息</h3>
		<table class="h_order-list">
        <tr class="h_title">
          <th class="h_col_1">游玩日期</th>
          <th style="width:640px">名称</th>
          <th>数量</th>
        </tr>
        <tr>
          <td class="h_col_1">${buyInfo.visitTime}</td>
          <td>${mainProdBranch.fullName}</td>
          <td>${buyInfo.adult}成人<#if buyInfo.child gt 0>${buyInfo.child}儿童</#if></td>
        </tr>
      </table>
      <@s.if test="hasSelfPackTraffic()">
       <h3><s></s>机票信息</h3>
		<table class="h_order-list">
        <tr class="h_title">
          <th class="h_col_1">时间</th>
          <th class="h_col_2">往返地</th>
          <th class="h_col_3">航班</th>
          <th >舱位</th>
          <th class="h_col_4">起抵时间 </th>
          <th>数量</th>
        </tr>
        <#list selfpackProduct.TRAFFIC as branch>
        <tr>
          <td class="h_col_1">${branch.timeInfo.visitTime}</td>
          <td>${branch.prodProduct.goFlight.startPlaceName}- ${branch.prodProduct.goFlight.arrivePlaceName}</td>
          <td><#if branch.prodProduct.goFlight.airline??>${branch.prodProduct.goFlight.airline.airlineName}</#if><span class="h_space">${branch.prodProduct.goFlight.flightNo}</span></td>
          <td>${branch.zhBerth}<#if branch.branchType=='CHILD'>(儿童)</#if></td>
          <td>${branch.prodProduct.goFlight.startTime}  起飞<span class="h_space">${branch.prodProduct.goFlight.arriveTime}  抵达</span></td>
          <td>${branch.timeInfo.quantity}</td>          
        </tr>
        <#if branch.prodProduct.hasRound()>
        <tr>
          <td class="h_col_1">${branch.prodProduct.getBackDate(branch.timeInfo.visitDate)?string('yyyy-MM-dd')}</td>
          <td>${branch.prodProduct.backFlight.startPlaceName}- ${branch.prodProduct.backFlight.arrivePlaceName}</td>
          <td><#if branch.prodProduct.backFlight.airline??>${branch.prodProduct.backFlight.airline.airlineName}</#if><span class="h_space">${branch.prodProduct.backFlight.flightNo}</span></td>
          <td>${branch.zhBerth}</td>
          <td>${branch.prodProduct.backFlight.startTime}  起飞<span class="h_space">${branch.prodProduct.backFlight.arriveTime}  抵达</span></td>
        </tr>
        </#if>
        </#list>
      </table>
      </@s.if>
      <@s.if test="hasSelfPackHotel()">
        <!--酒店信息S-->
         <h3><s></s>酒店信息</h3>
	<table class="h_order-list h_hotel">
        <tr class="h_title">
          <th class="h_col_3">入住日期</th>
          <th class="h_col_3">退房日期</th>
          <th class="h_col_1">酒店房型</th>
          <th class="h_col_2">早餐</th>
          <th class="h_col_3">天数</th>
          <th>数量</th>
        </tr>
        <#list selfpackProduct.HOTEL as branch>
        <tr>
          <td>${branch.timeInfo.visitTime}</td>
          <td>${branch.timeInfo.leaveTime}</td>
          <td class="h_col_1">${branch.fullName}</td>
          <td>${branch.zhBreakFast}</td>
          <td>${branch.timeInfo.days} 晚</td>
          <td>${branch.timeInfo.quantity} ${branch.priceUnit}</td>
        </tr>
        </#list>        
      </table>
      </@s.if>
      <@s.if test="hasSelfPackRoute()">
        <!--酒店信息S-->
         <h3><s></s>当地游</h3>
		<table class="h_order-list h_hotel">
        <tr class="h_title">
          <th class="h_col_2">游玩时间</th>
          <th class="h_col_1" style="width:640px">产品名称</th>
          <th>数量</th>
        </tr>
        <#list selfpackProduct.ROUTE as branch>
        <tr>
          <td>${branch.timeInfo.visitTime}</td>          
          <td class="h_col_1">${branch.fullName}</td>
          <td>${branch.timeInfo.quantity} ${branch.priceUnit}</td>
        </tr>
        </#list>  
        </table>
      </@s.if>  
      <@s.if test="hasSelfPackTicket()">
        <h3><s></s>门票</h3>
		<table class="h_order-list h_hotel">
		<tr class="h_title">
          <th class="h_col_2">游玩时间</th>
          <th class="h_col_1" style="width:640px">产品名称</th>
          <th>数量</th>
        </tr>
        <#list selfpackProduct.TICKET as branch>
        <tr>
          <td>${branch.timeInfo.visitTime}</td>          
          <td class="h_col_1">${branch.fullName}</td>
          <td>${branch.timeInfo.quantity} ${branch.priceUnit}</td>
        </tr>
        </#list>       
      </table>
      </@s.if>
