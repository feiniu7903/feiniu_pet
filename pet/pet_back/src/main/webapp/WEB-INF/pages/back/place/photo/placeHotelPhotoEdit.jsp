<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form action="${basePath}/place/uploadImg.do" id="uploadImageForm" name="uploadImageForm" method="post" enctype="multipart/form-data">
	<input name="placePhoto.placeId" value="${placeId}" type="hidden">
	<input name="placePhoto.placePhotoId" value="${placePhotoIds}" type="hidden">
	<input id="imgWidth"  type="hidden">
	<input id="imgHeight"  type="hidden">
	<s:if test="placePhoto.imagesUrl == null">
		<input id="imgUrl" type="hidden">
	</s:if>
	<s:else>
		<input id="imgUrl" value="http://pic.lvmama.com${placePhoto.imagesUrl}" type="hidden">
	</s:else>
	<input type="hidden" id="imgSizeStyle" />
	<table class="p_table" style="width:1100px;">
		<tr>
			<td  class="p_label" >图片标题(*)</td>
			<td >
				<input id="fileName" name="placePhoto.placePhotoName" type="text" value="${placePhoto.placePhotoName}" maxlength="20" placeholder="窗口边的千岛湖"/>(小于20个字)
			</td>
			<td  class="p_label" >图片类型(*)</td>
			<td >
			<select id="placePhotoType" name="placePhoto.placePhotoType">
					<option value=""></option>
					<option value="外观">外观</option>
					<option value="内景">内景</option>
					<option value="大堂">大堂</option>
					<option value="前台">前台</option>
					<option value="客房">客房</option>
					<option value="餐厅">餐厅</option>
					<option value="会议室">会议室</option>
					<option value="娱乐休闲设施">娱乐休闲设施</option>
					<option value="商务中心">商务中心</option>
					<option value="其他">其他</option>
			</select>
			<input id="placePhotoTypeTemp" type="hidden" value="${placePhoto.placePhotoType}" />
			<input id="photoTypeReal" name="placePhoto.type" type="hidden" value="${placePhoto.type}" />
			</td>
			<td  class="p_label" >排序值(*)</td>
			<td><input id="seq" name="placePhoto.seq"  theme="simple" type="text" value="${placePhoto.seq}"  maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td class="p_label" >图片描述</td>
			<td colspan="5">
				<textarea id="content" name="placePhoto.placePhotoContext"  rows="3" maxlength="100" style="width: 500px;" placeholder="例如：千岛湖的景色优美，住在一个离湖最近的房间，轻松惬意的午后，一杯茶一本书，生活也许本该如此">${placePhoto.placePhotoContext}</textarea>(小于100个字)
			</td>
		</tr>
	</table>
	</br>
	<div class="crop"> 
		<div style="float:left;">
			<div class="clear"></div>
			<input id="imagePath" name="placePhoto.imagePath" type="file"　 class="hide"/>
			<input class="btn btn-small w5"  type="button" value="上传图片" id="uploadImg-btn" ><br/><br/>
			<input class="btn btn-small w5 hide"  type="button" value="恢复图片" id="restoreImage" ><br/><br/>
			<input class="btn btn-small w5"  type="button" value="保存图片" id="crop"><br/><br/>
			<div id="movement" style="width:80px"></div>
		</div>
		
		<div id="cropzoom_container"></div>
	</div>
</form>

