<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
	});
</script>
<table class="newfont06" border="0"  cellpadding="0"  >
	<input type="hidden" name="ebkProdProductId" value="ebkProdProduct.ebkProdProductId"/>
	<tr>
		<td>行程天数：${ebkProdJourneyListSize}天
			<s:if test="ebkProdJourneyListMapOld.containsKey('tripNumber')">
				<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdJourneyListSizeOld}天">审</span>
			</s:if>
		</td>
	</tr>
	<tr><td></td></tr>
	<s:iterator var="ebkProdJourney" value="ebkProdJourneyList">
		<tr>
			<td>第${ebkProdJourney.dayNumber}天：${ebkProdJourney.title}
				<s:iterator var="ebkProdJourneyOld" value="ebkProdJourneyListMapOld.ebkProdJourneyListOld">
					<s:if test="#ebkProdJourneyOld.dayNumber==#ebkProdJourney.dayNumber && #ebkProdJourneyOld.title!=null">
						<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdJourneyOld.title}">审</span>
					</s:if>
				</s:iterator>
			</td>
		</tr>
		<tr>
			<td>描述：${ebkProdJourney.content}
				<s:iterator var="ebkProdJourneyOld" value="ebkProdJourneyListMapOld.ebkProdJourneyListOld">
					<s:if test="#ebkProdJourneyOld.dayNumber==#ebkProdJourney.dayNumber && #ebkProdJourneyOld.content!=null">
						<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdJourneyOld.content}">审</span>
					</s:if>
				</s:iterator>
			</td>
		</tr>
		<tr>
			<td>用餐：${ebkProdJourney.dinner} 
				 <s:iterator var="ebkProdJourneyOld" value="ebkProdJourneyListMapOld.ebkProdJourneyListOld">
					<s:if test="#ebkProdJourneyOld.dayNumber==#ebkProdJourney.dayNumber && #ebkProdJourneyOld.dinner!=null">
						<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdJourneyOld.dinner}">审</span>
					</s:if>
				</s:iterator>
			</td>
		</tr>
		<tr>
			<td>住宿：${ebkProdJourney.hotel}
				 <s:iterator var="ebkProdJourneyOld" value="ebkProdJourneyListMapOld.ebkProdJourneyListOld">
					<s:if test="#ebkProdJourneyOld.dayNumber==#ebkProdJourney.dayNumber && #ebkProdJourneyOld.hotel!=null">
						<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdJourneyOld.hotel}">审</span>
					</s:if>
				</s:iterator>
			</td>
		</tr>
		<tr>
			<td>交通：${ebkProdJourney.trafficSplitZh}
				 <s:iterator var="ebkProdJourneyOld" value="ebkProdJourneyListMapOld.ebkProdJourneyListOld">
					<s:if test="#ebkProdJourneyOld.dayNumber==#ebkProdJourney.dayNumber && #ebkProdJourneyOld.traffic!=null">
						<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdJourneyOld.trafficSplitZh}">审</span>
					</s:if>
				</s:iterator>
			</td>
		</tr>
		
		<s:iterator value="#ebkProdJourney.comPictureJourneyList" var="comPictureJourney">
		<tr>
			<td>
				<s:if test="#comPictureJourney.pictureUrl!=null">
					<img src="http://pic.lvmama.com/pics/${comPictureJourney.pictureUrl}"/>${comPictureJourney.pictureName}
				</s:if>
			</td>
		</tr>
		</s:iterator>
		<tr><td></td></tr>
	</s:iterator>
</table>