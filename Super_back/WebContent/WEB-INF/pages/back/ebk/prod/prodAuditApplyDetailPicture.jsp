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
			<td width="10">小图：</td>
		</tr>
		<tr>
			<td class="icon"><s:if test="comPictureSmall.pictureUrl!=null"><img src="http://pic.lvmama.com/pics/${comPictureSmall.pictureUrl}" width="200" height="100"/></s:if></td>
			<td>${comPictureSmall.pictureName}</td>
		</tr>
		<tr>
			<td>大图：</td>
		</tr>
		<s:iterator var="comPictureBig" value="comPictureBigList">
			<tr>
				<td class="icon"><s:if test="#comPictureBig.pictureUrl!=null"><img src="http://pic.lvmama.com/pics/${comPictureBig.pictureUrl}" width="440" height="220"/></s:if></td>
				<td>${comPictureBig.pictureName}</td>
			</tr>
		</s:iterator>
</table>