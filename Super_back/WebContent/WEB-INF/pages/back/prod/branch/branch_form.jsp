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
   			<td width="40%"><s:select list="branchCodeSetList" listKey="code" listValue="name" name="branch.branchType" cssClass="changeType"/>
   				<s:if test="product.productType=='TRAFFIC'">
   					&nbsp;&nbsp;&nbsp;&nbsp;
               		<span class="add_title"><font style="font-weight:bold;">舱位类型：</font></span><span class="require">[*]</span>
              		<s:select list="branch_1CodeSetList" listKey="code" listValue="name" name="branch.berth" cssClass="changeType"/>
	              	<s:if test="product.subProductType=='TRAIN'">
              		<span class="add_title"><font style="font-weight:bold;">站站类型：</font></span><span class="require">[*]</span>
              		<s:select list="branch_1CodeSetList" listKey="code" listValue="name" name="branch.stationStationId" cssClass="changeType"/>
              		</s:if>
   				</s:if>
   				(类别标记，前台不显示)
   			</td>
   			<td width="10%"><em>是否附加：</em></td>
   			<td width="40%"><input type="radio" name="branch.additional" value="true" <s:if test="product.hasSelfPack()"> disabled="disabled"</s:if>/>是&nbsp;<input type="radio" name="branch.additional" checked="checked" value="false" <s:if test="product.hasSelfPack()"> disabled="disabled"</s:if>/>否(是否为本产品的附加产品)</td>
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
   			<td>
   			<textarea name="branch.description" cols="50" rows="5" style="width:400px;height:215px;" class="sensitiveVad">
   			</textarea>
   			</td>
   			
   		<s:if test='product.productType=="TICKET"'>
   			<td colspan="2" valign="top">
	   			<strong>类别描述模板：</strong>(前台类别名称旁显示，不填则不显示)<br/>
		 		<h4>入园须知</h4> 
				<p>1.营业时间：8:00—17:00（16:00停止换票） </p>
				<p>取票时间：8：30—17：00</p> 
				<p>2.景区地址：上海市浦东新区南六公路178号</p>
				<p>取票地点：上海市松江佘山上海欢乐谷检票处电子门票专用通道。</p> 
				<p>3.入园凭证：刷二维码短信入园。短信游玩当日有效，不能删除、不能转发。如您在出行前一天17：00尚未收到短信，请速来电咨询。</p>
				<h4>
					适用范围
				</h4>
				<p>
					全日制大专至全日制研究生（不分国籍，凭借有效学生证件刷码入园）
				</p>
				<h4>
					退改规则
				</h4>
				<p>
					若需退改请提前一天晚上21：00前办理，逾期收取全额费用作为违约金。
				</p>
   			</td>
			</s:if>
			<s:else>
			<td colspan="2" rowspan="2" valign="middle">
   			(前台类别名称旁显示，不填则不显示)
   			</td>
			</s:else>
   		</tr>
   		
   		<tr>
   			<td><em>是否前台显示：</em></td>
   			<td>
   				<input type="radio" name="branch.visible" checked="checked" value="true"/>是&nbsp;<input type="radio" name="branch.visible" value="false"/>否
			(此处用于补单)
			</td>
			<s:if test='product.productType=="TICKET"'>
				<td><em>是否微信立减：</em></td>
				<td><input type="radio" name="branch.weixinLijian" value="Y"/>是&nbsp;<input type="radio" name="branch.weixinLijian" checked="checked" value="N"/>否</td>
			</s:if>
			<s:else>
				<td colspan="2">&nbsp;</td>
			</s:else>
   		</tr>
   	</table>  
     <p><em class="button button2 saveForm" form="branchForm">保存</em><em class="button button2 close">取消</em></p>
   </div><!--row3 end-->
        </form> 