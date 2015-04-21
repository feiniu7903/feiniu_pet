<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<form action="<%=basePath%>prod/editProductBranch.do" onsubmit="return false" name="branchForm" style="display: none" class="section">
   		<input type="hidden" name="branch.productId"/><input type="hidden" name="branch.prodBranchId"/>
   		<!-- <input type="hidden" name="branch.defaultBranch"/> -->
   <div class="row3" style="display: block;clear:none;">
   	<table class="newTableB" border="0" cellspacing="0" cellpadding="0">
   		<tr>
   			<td width="10%"><em>类别类型：<span class="require">[*]</span></em></td>
   			<td width="30%"><s:select list="branchCodeSetList" listKey="code" listValue="name" name="branch.branchType"/>(类别标记，前台不显示)</td>
   			<td width="10%"><em>是否附加：</em></td>
   			<td width="40%"><input type="radio" name="branch.additional" value="true"/>是&nbsp;<input type="radio" name="branch.additional" checked="checked" value="false"/>否(是否为本产品的附加产品)</td>
   		</tr>
   		<tr>
   			<td><em>类别名称：<span class="require">[*]</span></em></td>
   			<td><input type="text" class="text1 sensitiveVad" name="branch.branchName" id="branch_branchName" />(展示给游客，前台显示)</td>
   			<td><em>计价单位：</em></td>
   			<td><input type="text" class="text1" name="branch.priceUnit"/></td>
   		</tr>
   		<tr>
   			<td><em>成人数：<span class="require">[*]</span></em></td>
   			<td><input type="text" class="text1" name="branch.adultQuantity" value="0"/></td>
   			<td><em>儿童数：</em></td>
   			<td><input type="text" class="text1" name="branch.childQuantity" value="0"/></td>
   		</tr>
   		<tr>
   			<td><em>最小起订量：<span class="require">[*]</span></em></td>
   			<td>
   			<input type="text" class="text1" name="branch.minimum" value="1"/>
   			</td>
   			<td><em>最大订购量：<span class="require">[*]</span></em></td>
   			<td><input type="text" class="text1" name="branch.maximum" value="100"/></td>
   		</tr>
   		<tr>
   			<td><em>类别描述：</em></td>
   			<td colspan="1"><textarea name="branch.description" cols="50" rows="5" class="sensitiveVad"></textarea></td>
		 <td colspan="2" >
		 	<div>
		 		<s:if test="product.IsAperiodic()">
		 			<strong>类别描述模版：</strong><br/>
					1、本产品为驴妈妈自在游产品，下单无需选择入住和离店时间，仅需在订单有效期内提前N天致电酒店进行预约即可，由于周末为预约高峰期，请尽量提早预约。<br/>
					3、含N份早餐<br/>
					4、早餐信息：中餐/自助餐/儿童餐 费用：xx元<br/>
					5、加床信息：不可加床/加床费用含n早/加床费用不含早 费用：xx元
		 		</s:if>
		 		<s:else>
			 		<strong>类别描述模板：</strong>(前台类别名称旁显示，不填则不显示)<br/>
					含n份早餐<br/>   
					早餐信息：中餐/自助餐/儿童餐                   费用：xx元<br/> 
					加床信息：不可加床/加床费用含n早/加床费用不含早 费用：xx元
				</s:else>
		 	</div>
		 </td>
   		</tr>
   		<tr>
   			<td><em>早餐</em></td>
   			<td><s:radio list="breakfastList" name="branch.breakfast" listKey="code" listValue="name"/></td>
   		</tr>
   		<tr>
   			<td><em>宽带</em></td>
   			<td><s:radio list="broadbandList" name="branch.broadband" listKey="code" listValue="name"/></td>   			
   		</tr>
   		<tr>
   			<td><em>床型</em></td>
   			<td><s:textfield name="branch.bedType"/></td>
   		</tr>
   		<tr>
   			<td><em>是否可加床：</em></td>
   			<td colspan="3">
   				<input type="radio" name="branch.extraBedAble" checked="checked" value="true"/>是&nbsp;<input type="radio" name="branch.extraBedAble" value="false"/>否
			</td>
   		</tr>
   		<tr>
   			<td><em>是否前台显示：</em></td>
   			<td colspan="3">
   				<input type="radio" name="branch.visible" checked="checked" value="true"/>是&nbsp;<input type="radio" name="branch.visible" value="false"/>否
			(此处用于补单)
			</td>
   		</tr>
   		
   	</table>  
     <p><em class="button button2 saveForm" style="float:left" form="branchForm">保存</em><em class="button button2 close" style="float:left">取消</em></p>
   </div><!--row3 end-->
        </form> 