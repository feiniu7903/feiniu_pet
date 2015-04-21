<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$('.tip_text').ui('lvtip',{
		    hovershow: 200
	    });
		
		$(".dayNavList li").click(function(){
			var _num = $(this).index();
			$(this).addClass('dayNavLi').siblings().removeClass('dayNavLi');
			$(this).parents('.dxcsmBox').find('.tabeJsBox').eq(_num).show().siblings('.tabeJsBox').hide();
		});
	});
	$(document).ready(function(){
		$('.tabeJsBox').eq(0).css({'display':'block'});
		$('.dayNavList li').eq(0).addClass('dayNavLi');
	});
</script>
<style>
.dayNavList{width:auto;height:36px;margin-top:10px;}
.dayNavList li{
	width:auto;
	padding:0 15px;
	text-align:center;
	line-height:36px;
	height:36px;
	color:#333;
	font-family:"微软雅黑";
	font-size:18px;
	border:1px soild #ccc;
	background:#ddd;
	cursor:pointer;
}
.dayNavList .dayNavLi{
	background:#999;
}
.tabeJsBox{
	display:none;
}
.tableLaleClass{
	width:60px;
	display:block;
	text-align:left;
	float:left;
}
.tabeJsBox textarea{
	float:left;
}
</style>
<div class="dxcsmBox">
	<ul class="dayNavList">
	<s:iterator var="ebkMultiJourney" value="ebkMultiJourneyList">
		<li>${ebkMultiJourney.journeyName}</li>
	</s:iterator>
	</ul>
	<s:iterator var="ebkMultiJourney" value="ebkMultiJourneyList">
	<div class="tabeJsBox">
		<table class="newfont06" border="0"  cellpadding="0"  >
			<input type="hidden" name="ebkProdProductId" value="ebkProdProduct.ebkProdProductId"/>			
			<tr>
				<td>${ebkMultiJourney.journeyName}
				</td>
			</tr>
			<tr>
				<td>行程天数:
				${ebkMultiJourney.days }天
				${ebkMultiJourney.nights }晚
				</td>
			</tr>
			<tr>
				<td>内容描述:${ebkMultiJourney.content }
				</td>
			</tr>
			<tr>
				<td>是否有效：
					<s:if test='#ebkMultiJourney.valid=="Y"'>有效</s:if>
					<s:if test='#ebkMultiJourney.valid=="N"'>无效</s:if>
				</td>
			</tr>
			
				<s:iterator var="ebkJourney" value="#ebkMultiJourney.viewJourneyList">
				<tr><td></td></tr>
				<tr>
					<td>第${ebkJourney.dayNumber}天：${ebkJourney.title}
					</td>
				</tr>
				<tr>
					<td>描述：${ebkJourney.content}
					</td>
				</tr>
				<tr>
					<td>用餐：${ebkJourney.dinner} 
					</td>
				</tr>
				<tr>
					<td>住宿：${ebkJourney.hotel}
					</td>
				</tr>
				<tr>
					<td>交通：${ebkJourney.trafficSplitZh}
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
			</s:iterator>
			<tr><td></td></tr>
				<s:iterator var="ebkProdContent" value="#ebkMultiJourney.ebkProdContentList">
				<tr>
					<td>
					<s:if test='#ebkProdContent.contentType=="NOCOSTCONTAIN"'>
						<lable class="tableLaleClass">费用不包含:</lable>
						<textarea rows="10" style="width: 500px" readonly="readonly">${ebkProdContent.content}</textarea>
					</s:if>
					<p/>
					<s:if test='#ebkProdContent.contentType=="COSTCONTAIN"'>
						<lable class="tableLaleClass">费用包含:</lable>
						<textarea rows="10" style="width: 500px" readonly="readonly">${ebkProdContent.content}</textarea>
					</s:if>
					</td>
				</tr>
				</s:iterator>

			
			
		</table>
	</div>
	</s:iterator>
</div>