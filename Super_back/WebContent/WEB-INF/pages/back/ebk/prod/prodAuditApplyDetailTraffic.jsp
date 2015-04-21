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
		<td>发车信息：</td>
	</tr>
	<tr><td></td></tr>
	<s:iterator var="ebkProdContent" value="ebkProdContentList">
		<s:if test="#ebkProdContent.contentType=='TRAFFICEBKINFO'">
			<tr>
				<td><input type="text" style="margin:0;width: 500px" readonly="readonly" value="${ebkProdContent.content}"/>
					<s:iterator var="ebkProdContentOld" value="ebkProdContentListMapOld.ebkProdContentListOld">
						<s:if test="#ebkProdContentOld.contentId==#ebkProdContent.contentId && #ebkProdContentOld.content!=null">
							<span class="tip_text" tip-title="老数据:" tip-content="${ebkProdContentOld.content}">审</span>
						</s:if>
					</s:iterator>
				</td>
			</tr>
			<tr><td></td></tr>
		</s:if>
	</s:iterator>
</table>