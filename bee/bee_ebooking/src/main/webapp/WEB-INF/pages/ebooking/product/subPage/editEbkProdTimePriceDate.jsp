<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
			<tr>
			<td colspan="2">
			<input type="hidden" id="type" value="d"/>
			<div class="tab2">
				<ul >
					<li for="day">按日期</li>
					<li for="week">按周</li>
				</ul>
			</div>
			</td></tr>
			
		<tr for="day">
			<td colspan="2">
				<div style="margin-left:10px">
				<div>点击日历中的日期添加开班日期:</div>
					<div class="left">
						<div id="divContainer"></div>
					</div>
					<div class="right">
					    	<div>已选日期:</div>
							<div id="divResult">
					        	<select multiple="true" id="selDate" name="paramModel.days">
					            </select>
					            <br/>
					            <span id="btnDel">删除</span>
					        </div>
					 </div>
					 <div class="clear"></div>
				</div>		
			</td>
	    </tr>
	    <tr for="week">
	    	<td width="105" align="right"><font color="red">*</font>选择日期：</td>
			<td><input type="text" class="date_bg Calendar27" id="begindateid" name="paramModel.beginDate"  errorlabel="开始日期">~
			<input type="text" class="date_bg Calendar28" id="endDateid" name="paramModel.endDate"  errorlabel="结束日期"></td>
	</tr>
<tr for="week">
	<td align="right"><font color="red">*</font>适用星期：</td>
	<td><input type="checkbox" id="timepriceweekall" checked>全部<input type="checkbox"
		name="paramModel.weeks" value="2" checked>星期一<input
		type="checkbox" name="paramModel.weeks" value="3" checked  errorlabel="适用星期">星期二<input
		type="checkbox" name="paramModel.weeks" value="4" checked>星期三<input
		type="checkbox" name="paramModel.weeks" value="5" checked>星期四<input
		type="checkbox" name="paramModel.weeks" value="6" checked>星期五<input
		type="checkbox" name="paramModel.weeks" value="7" checked>星期六<input
		type="checkbox" name="paramModel.weeks" value="1" checked>星期日</td>
</tr>
<tr>
	<td align="right"><font color="red">*</font>是否<s:if test='"HOTEL"==ebkProductViewType'>满房</s:if><s:else>关班</s:else></td>
	<td><input type="radio" name="paramModel.forbiddenSell" value="true">是
		<input type="radio" name="paramModel.forbiddenSell" value="false" checked>否</td>
</tr>

<script>
	$(function(){ 
	   switchTab("day");
	});

		
    	$("ul li").click(function(){
    		
    		var type=$(this).attr("for");
    		if(type){
    			switchTab(type);
    		}
    	});
		
		
		function switchTab(){
			var type=arguments[0];
			switch(type)
			{
				case "day":
					$("tr[for='day']").css("display","");
					$("tr[for='week']").css("display","none");
					$("li[for='day']").css("background-color","#CCF");
					$("li[for='week']").css("background-color","white");
					$("#type").val("d");
					break;
				case "week":
					$("tr[for='day']").css("display","none");
					$("tr[for='week']").css("display","");
					$("li[for='day']").css("background-color","white");
					$("li[for='week']").css("background-color","#CCF");
					$("#type").val("w");
					break;
			}
		}
	</script>