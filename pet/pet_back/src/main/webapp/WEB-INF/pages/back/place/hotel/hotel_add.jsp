<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<title>酒店添加</title>
<style type="text/css">
.form-inline label {line-height:30px;}
.form-inline #p_label1 label{width:85px;}
.form-inline #p_label2 label{width:120px;}
.form-inline #p_label3 label{width:60px;}
.form-inline #p_label4 label{width:90px;}
.form-inline #jiahong {color:red;font-weight:bold;}
</style>
</head>
<body>
<div class="p_box" style="width:1000px;">
	<form id="hotelForm"  method="post" enctype="multipart/form-data" class="mySensitiveForm">
		<s:hidden name="place.placeId"></s:hidden>
		<s:hidden name="place.placeHotel.placeId"></s:hidden>
		<input name="place.stage" type="hidden" value="3"/>
		<input id="oldPlaceName" name="oldPlaceName" value="${place.name}"  type="hidden"/>
		<s:hidden name="stage" value="3"></s:hidden>
		<s:hidden name="placeId"></s:hidden>
	    <input id="placeId" type="hidden"  name="placeId">
	    
	<table class="p_table table_center form-inline"   id="content0">
      <tr>
          <td  class="p_label" width="100px;"><span style="color:red;">*</span>层级关系:</td>
	      <td colspan="3"> <input type="hidden" name="parent_place_id" value="${place.parentPlaceId}" id="parent_place_id"/>
	        <span id="placeSuperior">${placeSuperior}</span>&nbsp;&nbsp;&nbsp;&nbsp;
	        <a href="javascript:addPlacePlaceDest('${place.placeId}');">[维护层级]</a>
	       </td>
      </tr>
      <tr>
        <td  class="p_label"><span style="color:red;">*</span>名称：</td>
       	<td><s:textfield disabled="true" name="place.name"   maxLength="200" id="placeName"  style="width:300px"  onblur="placeNameCheck('1')" cssClass="sensitiveVad" /></td>
        <td class="p_label"><span style="color:red;">*</span>拼音：</td>
        <td >
	        <input type="text" style="width:300px" id="place_pinYin" value='<s:iterator value="pinyinList" end="1"><s:property value="pinYin"/></s:iterator>' 
	        name="place.pinYin" disabled="disabled"   maxLength="100"/>
	        　        <a href="javascript:addPlaceNamePinyin('PLACE_NAME');">[修改]</a></td>
      </tr>
      <tr>
      	<td class="p_label"><span style="color:red;">*</span>URL：</td>
        <td>
        	<s:textfield name="place.pinYinUrl" disabled="true"   style="width:300px" maxLength="200"/>
        </td>
      	<td  class="p_label">英文名称：</td>
      	<td>
      		<s:textfield name="place.enName"   style="width:300px" maxLength="500"/>
      	</td>
      </tr>
      <tr>
		<td class="p_label" ><span style="color:red;">*</span>状态：</td>
		<td >
		     	<s:select list="isValidList"  name="place.isValid" headerKey="" headerValue="--请选择--"   listKey="elementCode" listValue="elementValue"></s:select>
		</td>
		<td class="p_label"><span style="color:red;">*</span>前台排序：</td>
		<td>
			<s:textfield name="place.seq"   style="width:100px" id="seq"/>
		</td>
      </tr>
      <tr>
        <td class="p_label"><span style="color:red;">*</span>省份：</td>
        <td><s:textfield name="place.province"   style="width:300px" maxLength="200"/></td>
        <td class="p_label"><span style="color:red;">*</span>城市：</td>
        <td><s:textfield name="place.city"   style="width:300px" maxLength="200"/></td>
       
      </tr>
      <tr>
       <td class="p_label">优化别名：</td>
       <td><s:textfield name="place.seoName"  style="width:300px"  maxLength="200"/></td>
       <td  class="p_label"><span style="color:red;">*</span>境内境外:</td>
       <td > <input id="placeType1" type="radio" name="place.placeType" value="ABROAD"
				<s:if test="place.placeType!=null && place.placeType=='ABROAD'">checked="checked"</s:if> />
			<label  class="radio" for="placeType1">境外&nbsp;&nbsp;</label>
			<input  id="placeType2"  type="radio" name="place.placeType" value="DOMESTIC"
				<s:if test="place.placeType==null || place.placeType=='DOMESTIC'">checked="checked"</s:if> />
			<label  class="radio" for="placeType2">境内&nbsp;&nbsp;</label> 
			</td>
      </tr>
      <tr>
        <td class="p_label">酒店地址：</td>
        <td>
        	<s:textarea name="place.address"   style="width:300px"  maxLength="250" cssClass="sensitiveVad">
        	</s:textarea>
        </td>
        <td class="p_label">英文地址：</td>
        <td>
        	<s:textarea name="place.placeHotel.addressEnglish"   style="width:300px"  maxLength="1000">
        	</s:textarea>
        </td>
      </tr>
      <tr>
        <td class="p_label">酒店位置：</td>
        <td colspan="3">
        	<s:textfield name="place.placeHotel.hotelPosition"   style="width:759px"  maxLength="250" cssClass="sensitiveVad"></s:textfield>
        </td>
        </tr>
       <tr>
        <td class="p_label">高频关键字：</td>
	   	<td colspan="3">
	   		<s:textfield name="place.hfkw" disabled="true"   maxLength="500" cssStyle="width:759px;" cssClass="sensitiveVad"/>
	        　        <a href="javascript:addPlacePinyin('PLACE_HFKW');">[维护]</a>
        </td>    
      </tr>
      <tr> 
      	<td class="p_label" ><span style="color:red;">*</span><span id="jiahong">列表显示图片：</span></td>
         <td>
         	<input id="picDisplay1" type="radio" name="place.placeHotel.picDisplay" value=BIG
				<s:if test="place.placeHotel ==null || place.placeHotel.picDisplay == null || place.placeHotel.picDisplay=='BIG'">checked="checked"</s:if> />
			<label  class="radio" for="picDisplay1">显示大图(非经济型酒店)</label>
			
			<input id="picDisplay2" type="radio" name="place.placeHotel.picDisplay" value="SMALL"
				<s:if test="place.placeHotel.picDisplay=='SMALL'">checked="checked"</s:if> />
			<label  class="radio" for="picDisplay2">显示小图(经济型酒店) </label>
         </td>
         <td class="p_label"> 房间数：</td>
         <td >
         	<s:textfield name="place.placeHotel.hotelRoomNum"   maxLength="500" cssStyle="width:300px;" id="hotelRoomNum"/>
         </td>
      </tr>
      <tr> 
         <td class="p_label"> 电话：</td>
         <td>
         	<s:textfield name="place.placeHotel.hotelPhone"   maxLength="50" cssStyle="width:300px;" id="hotelPhone" placeholder="例如:0086-021-6666666" />
         </td>
         <td class="p_label"> 传真：</td>
         <td>
         	<s:textfield name="place.placeHotel.hotelFax"   maxLength="50" cssStyle="width:300px;" id="hotelFax" placeholder="例如:0086-021-6666666"/>
         </td>
      </tr>
      <tr> 
         <td class="p_label"> 邮编：</td>
         <td>
         	<s:textfield name="place.placeHotel.hotelZipCode"   maxLength="50" cssStyle="width:300px;" id="hotelZipCode"/>
         </td>
         <td class="p_label"> Email：</td>
         <td>
         	<s:textfield name="place.placeHotel.hotelEmail"   maxLength="50" cssStyle="width:300px;" id="hotelEmail"/>
         </td>
      </tr>
      <tr>
        <td class="p_label">酒店集团：</td>
        <td>
			<select id="hotelCompany" name="place.placeHotel.hotelCompany" style="width:315px" val="${place.placeHotel.hotelCompany}">
				<option value="" >--请选择--</option>
        	</select>
        </td>
        <td class="p_label">酒店品牌：</td>
        <td>
        	<select id="hotelBrand" name="place.placeHotel.hotelBrand" style="width:315px" val="${place.placeHotel.hotelBrand}">
        		<option value="">--请选择--</option>
        	</select>
        </td>
        <script type="text/javascript" src="${basePath}/js/place/hotel_company_brand.js"></script>
      </tr>
     <tr>
        <td class="p_label">开业时间：</td>
        <td>
        	<s:textfield name="place.placeHotel.hotelOpenTimeStr"   style="width:150px"   id="hotelOpenTime" cssClass="Wdate"  cssStyle="cursor:pointer;" onFocus="WdatePicker({dateFmt:'yyyy年MM月'})"/>
        </td>
        <td class="p_label">最近装修：</td>
        <td>
        	<s:textfield name="place.placeHotel.hotelDecorationTimeStr"   style="width:150px"   id="hotelDecorationTime" cssClass="Wdate"  cssStyle="cursor:pointer;" onFocus="WdatePicker({dateFmt:'yyyy年MM月'})"/>
        </td>
      </tr>
     <tr>
			<td class="p_label"><span style="color:red;">*</span>星级标准:</td>
			<td colspan="3"> <input type="radio" id="fiveStarSolid" 
					name="place.placeHotel.hotelStar" value="8"
					<s:if test='place.placeHotel.hotelStar=="8"'>checked="checked"</s:if> />
					<label class="radio"	for="fiveStarSolid">五星级酒店&nbsp;&nbsp;</label> 
				<input type="radio"
					id="fiveStarHollow" name="place.placeHotel.hotelStar" value="7"
					<s:if test='place.placeHotel.hotelStar=="7"'>checked="checked"</s:if> /><label class="radio" 
					for="fiveStarHollow">豪华型酒店&nbsp;&nbsp;</label> 
				<input type="radio"
					id="fourStarSolid" name="place.placeHotel.hotelStar" value="6"
					<s:if test='place.placeHotel.hotelStar=="6"'>checked="checked"</s:if> /><label class="radio" 
					for="fourStarSolid">四星级酒店&nbsp;&nbsp;</label> 
				<input type="radio"
					id="fourStarHollow" name="place.placeHotel.hotelStar" value="5"
					<s:if test='place.placeHotel.hotelStar=="5"'>checked="checked"</s:if> /><label class="radio" 
					for="fourStarHollow">品质型酒店&nbsp;&nbsp;</label> 
				<input type="radio"
					id="threeStarSolid" name="place.placeHotel.hotelStar" value="4"
					<s:if test='place.placeHotel.hotelStar=="4"'>checked="checked"</s:if> /><label class="radio" 
					for="threeStarSolid">三星级酒店&nbsp;&nbsp;</label>
			    <input type="radio"
					id="threeStarHollow" name="place.placeHotel.hotelStar" value="3"
					<s:if test='place.placeHotel.hotelStar=="3"'>checked="checked"</s:if> /><label class="radio" 
					for="threeStarHollow">舒适型酒店&nbsp;&nbsp;</label> 
				<input type="radio"
					id="twoStarSolid" name="place.placeHotel.hotelStar" value="2"
					<s:if test='place.placeHotel.hotelStar=="2"'>checked="checked"</s:if> /><label class="radio" 
					for="twoStarSolid">二星级酒店&nbsp;&nbsp;</label> 
				<input type="radio"
					id="twoStarHollow" name="place.placeHotel.hotelStar" value="1"
					<s:if test='place.placeHotel.hotelStar=="1"'>checked="checked"</s:if> /><label class="radio" 
					for="twoStarHollow">简约型酒店&nbsp;&nbsp;</label>
				</td>
									 
		</tr>
		<!-- <tr>
				<td class="p_label">酒店级别:</td>
				<td colspan="3"><input type="radio" id="fiveStarHotel"
						name="place.placeHotel.hotelLevel" value="五星级/豪华"
						<s:if test="place.placeHotel.hotelLevel=='五星级/豪华'">checked="checked"</s:if> /><label class="radio" 
						for="fiveStarHotel">五星级/豪华&nbsp;&nbsp;</label> 
					<input type="radio"	id="fourStarHotel" name="place.placeHotel.hotelLevel" value="四星级/品质"
						<s:if test="place.placeHotel.hotelLevel=='四星级/品质'">checked="checked"</s:if> /><label class="radio" 
						for="fourStarHotel">四星级/品质&nbsp;&nbsp;</label>
					 <input type="radio"
						id="threeStarHotel" name="place.placeHotel.hotelLevel" value="三星级/舒适"
						<s:if test="place.placeHotel.hotelLevel=='三星级/舒适'">checked="checked"</s:if> /><label class="radio" 
						for="threeStarHotel">三星级/舒适&nbsp;&nbsp;</label> 
					<input type="radio"
						id="twoStarHotel" name="place.placeHotel.hotelLevel" value="二星级/简约"
						<s:if test="place.placeHotel.hotelLevel=='二星级/简约'">checked="checked"</s:if> /><label class="radio" 
						for="twoStarHotel">二星级/简约&nbsp;&nbsp;</label>
					</td>
		</tr> -->
		<tr>
        <td class="p_label"><span style="color:red;">*</span>酒店简介:</td>
        <td colspan="3">
        	<s:textarea name="place.remarkes" id="remarkesId"  cols="70" rows="4" style="width:800px" cssClass="sensitiveVad"></s:textarea>
         </td>
      </tr>
       <tr>        
        		<td class="p_label">酒店主题：</td>
        		<td colspan="3"> 
        			<s:iterator value="subjectList" var="s">
        				<input type="checkbox" name="place.placeHotel.hotelTopic"  value="<s:property value="subjectName"/>" id="place.placeHotel.hotelTopic-<s:property value="comSubjectId"/>"  
        				<s:if test="place.placeHotel.hotelTopic!=null && place.placeHotel.hotelTopic.contains(subjectName)">checked="checked"</s:if> />
        				<label class="checkbox"  for="place.placeHotel.hotelTopic-<s:property value="comSubjectId"/>"  <s:if test="comSubjectId == -1 ">style="color: red;" title="此主题已失效,请尽快删除." </s:if> >
        					<s:property value="subjectName"/>
        				</label>
        			</s:iterator>
				</td> 	  
      </tr>
       <tr>
			 <td class="p_label"><span style="color:red;">*</span>外宾接待：</td>
			  <td colspan="3">
			  		<input id="hotelForeigner1" type="radio" name="place.placeHotel.hotelForeigner" value="false" <s:if test="place.placeHotel.hotelForeigner == 'false'">checked="checked"</s:if> />
					<label class="radio" for="hotelForeigner1">不可接待</label>
					
					 <input  id="hotelForeigner2" type="radio" name="place.placeHotel.hotelForeigner"  value="true" <s:if test="place.placeHotel ==null || place.placeHotel.hotelForeigner == null || place.placeHotel.hotelForeigner == 'true'">checked="checked"</s:if> />
					<label class="radio" for="hotelForeigner2">可接待</label> 
				</td>
	    </tr>
	    <tr>
			 <td class="p_label">早餐：</td>
			  <td colspan="3">
			  		<input id="breakfast1" type="checkbox"  name="place.placeHotel.breakfastType" value="中式早餐"
					<s:if test="place.placeHotel.breakfastType!=null && place.placeHotel.breakfastType.contains('中式早餐')">checked="checked"</s:if> />
					<label for="breakfast1" class="checkbox">中式早餐</label>
					<span style="vertical-align:middle;padding-right:20px;">
						(价格：<input id="breakfast1-price" type="text" name="place.placeHotel.breakfastPrice" value="<s:property value="breakfastPriceMap['中式早餐']"/>" onblur="if(this.value == '')this.value='0';" style="width:40px;" /> 元/份)
					</span>
					 <input id="breakfast2" type="checkbox"  name="place.placeHotel.breakfastType" value="西式早餐"
					<s:if test="place.placeHotel.breakfastType!=null && place.placeHotel.breakfastType.contains('西式早餐')">checked="checked"</s:if> />
					<label for="breakfast2" class="checkbox" >西式早餐</label>
					<span style="vertical-align:middle;padding-right:20px;">
						(价格：<input id="breakfast2-price" type="text" name="place.placeHotel.breakfastPrice" value="<s:property value="breakfastPriceMap['西式早餐']"/>" onblur="if(this.value == '')this.value='0';" style="width:40px;" /> 元/份)
					</span>
					<input id="breakfast3" type="checkbox"  name="place.placeHotel.breakfastType" value="中西式自助早餐"
					<s:if test="place.placeHotel.breakfastType!=null && place.placeHotel.breakfastType.contains('中西式自助早餐')">checked="checked"</s:if> />
					<label for="breakfast3"  class="checkbox"  >中西式自助早餐</label>
					<span style="vertical-align:middle;padding-right:20px;">
						(价格：<input id="breakfast3-price" type="text" name="place.placeHotel.breakfastPrice"  value="<s:property value="breakfastPriceMap['中西式自助早餐']"/>" onblur="if(this.value == '')this.value='0';" style="width:40px;" /> 元/份)
					</span>
				</td>
	    </tr>
	    <tr>
			 <td class="p_label"><span style="color:red;">*</span>宠物携带：</td>
			  <td colspan="3">
						<input id="petCarry1" type="radio"  name="place.placeHotel.petCarry" value="true"
						<s:if test="place.placeHotel.petCarry == 'true' ">checked="checked"</s:if> /><label class="radio" for="petCarry1">可携带
					</label>
						<input id="petCarry2" type="radio"  name="place.placeHotel.petCarry" value="false"
						<s:if test="place.placeHotel == null || place.placeHotel.petCarry == null || place.placeHotel.petCarry == 'false'">checked="checked"</s:if> /><label class="radio" for="petCarry2">不可携带
					</label>
				</td>
	    </tr>
	    <tr>
			 <td class="p_label">接受信用卡：</td>
			  <td colspan="3">
			  		<input id="creditCard1"  type="checkbox"  name="place.placeHotel.creditCard" value="万事达(Master)" 
					<s:if test="place.placeHotel.creditCard!=null && place.placeHotel.creditCard.contains('万事达(Master)')">checked="checked"</s:if> />
					<label for="creditCard1" class="checkbox" >万事达(Master)</label>
					
					 <input  id="creditCard2"   type="checkbox"  name="place.placeHotel.creditCard" value="威士(VISA)" 
					<s:if test="place.placeHotel.creditCard!=null && place.placeHotel.creditCard.contains('威士(VISA)')">checked="checked"</s:if> />
					<label for="creditCard2" class="checkbox" >威士(VISA)</label>
					
					<input  id="creditCard3"   type="checkbox"  name="place.placeHotel.creditCard" value="运通(AMEX)" 
					<s:if test="place.placeHotel.creditCard!=null && place.placeHotel.creditCard.contains('运通(AMEX)')">checked="checked"</s:if> />
					<label for="creditCard3" class="checkbox" >运通(AMEX)</label>
					
					<input  id="creditCard4"   type="checkbox"  name="place.placeHotel.creditCard" value="大来(DinersClub)" 
					<s:if test="place.placeHotel.creditCard!=null && place.placeHotel.creditCard.contains('大来(DinersClub)')">checked="checked"</s:if> />
					<label for="creditCard4" class="checkbox" >大来(DinersClub)</label>

					<input  id="creditCard5"   type="checkbox"  name="place.placeHotel.creditCard" value="JCB" 
					<s:if test="place.placeHotel.creditCard!=null && place.placeHotel.creditCard.contains('JCB')">checked="checked"</s:if> />
					<label for="creditCard5" class="checkbox" >JCB</label>
					
					<input  id="creditCard6"   type="checkbox"  name="place.placeHotel.creditCard" value="国内发行银联卡" 
					<s:if test="place.placeHotel.creditCard!=null && place.placeHotel.creditCard.contains('国内发行银联卡')">checked="checked"</s:if> />
					<label for="creditCard6" class="checkbox" >国内发行银联卡</label>
				</td>
	    </tr>
       <tr>	   
				<td class="p_label">综合设施： </td>
 				<td colspan="3" id="p_label4">
					<input type="checkbox" id="integratedFacilities1" name="place.placeHotel.integratedFacilities" value="免费停车场"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('免费停车场')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities1">免费停车场</label>
					
					<input type="checkbox" id="integratedFacilities2" name="place.placeHotel.integratedFacilities" value="收费停车场"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('收费停车场')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities2">收费停车场</label>
					<input to="integratedFacilities2" type="text" class="facilitiesRemark sensitiveVad"  style="width:92px;" placeholder="限制输入20个字" value="${facilitiesRemarkMap['收费停车场']}"/>
					
       			  	<input type="checkbox" id="integratedFacilities3" name="place.placeHotel.integratedFacilities" value="电梯/自动扶梯"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('电梯/自动扶梯')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities3">电梯/自动扶梯</label>
					<input type="checkbox" id="integratedFacilities4" name="place.placeHotel.integratedFacilities" value="冲印店"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('冲印店')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities4">冲印店</label>
					<input type="checkbox" id="integratedFacilities5" name="place.placeHotel.integratedFacilities" value="商场"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('商场')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities5">商场</label>
					<br>
					<input type="checkbox" id="integratedFacilities6" name="place.placeHotel.integratedFacilities" value="理发美容室"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('理发美容室')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities6">理发美容室</label>
					<input type="checkbox" id="integratedFacilities7" name="place.placeHotel.integratedFacilities" value="自动取款机"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('自动取款机')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities7">自动取款机</label>
					<input type="checkbox" id="integratedFacilities9" name="place.placeHotel.integratedFacilities" value="医务室"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('医务室')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities9">医务室</label>
					<input type="checkbox" id="integratedFacilities8" name="place.placeHotel.integratedFacilities" value="商务中心"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('商务中心')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities8">商务中心</label>				
					<input type="checkbox" id="integratedFacilities10" name="place.placeHotel.integratedFacilities" value="花店"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('花店')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities10">花店</label>
					<input type="checkbox" id="integratedFacilities11" name="place.placeHotel.integratedFacilities" value="会议厅"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('会议厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities11">会议厅</label>
					<br>
					<input type="checkbox" id="integratedFacilities12" name="place.placeHotel.integratedFacilities" value="健身房/健身中心"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('健身房/健身中心')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities12">健身房/健身中心</label>
					<input type="checkbox" id="integratedFacilities13" name="place.placeHotel.integratedFacilities" value="桑拿浴室"
					<s:if test="place.placeHotel.integratedFacilities!=null && place.placeHotel.integratedFacilities.contains('桑拿浴室')">checked="checked"</s:if> />
					<label class="checkbox"  for="integratedFacilities13">桑拿浴室</label>
			</td>					
      </tr>
      <tr>	   
				<td class="p_label" >客房设施： </td>
 				<td colspan="3" id="p_label4">
					<input type="checkbox" id="roomFacilities1" name="place.placeHotel.roomFacilities" value="残疾人客房"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('残疾人客房')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities1">残疾人客房</label>
					<br>
					<input type="checkbox" id="roomFacilities1" name="place.placeHotel.roomFacilities" value="免费宽带上网"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('免费宽带上网')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities1">免费宽带上网</label>
					
					<input type="checkbox" id="roomFacilities2" name="place.placeHotel.roomFacilities" value="收费宽带上网"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('收费宽带上网')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities2">收费宽带上网</label>
					<input to="roomFacilities2"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['收费宽带上网']}"/>
					
					<br>
					<input type="checkbox" id="roomFacilities5" name="place.placeHotel.roomFacilities" value="24小时热水"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('24小时热水')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities5">24小时热水</label>
					
					<input type="checkbox" id="roomFacilities6" name="place.placeHotel.roomFacilities" value="非24小时热水"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('非24小时热水')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities6">非24小时热水</label>
					<input to="roomFacilities6"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['非24小时热水']}"/>
					<br>
					<input type="checkbox" id="roomFacilities7" name="place.placeHotel.roomFacilities" value="独立淋浴间"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('独立淋浴间')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities7">独立淋浴间</label>
					
					<input type="checkbox" id="roomFacilities8" name="place.placeHotel.roomFacilities" value="公共卫浴"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('公共卫浴')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities8">公共卫浴</label>
					
					<input type="checkbox" id="roomFacilities9" name="place.placeHotel.roomFacilities" value="浴缸"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('浴缸')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities9">浴缸</label>
					<br>
					<input type="checkbox" id="roomFacilities10" name="place.placeHotel.roomFacilities" value="中央空调"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('中央空调')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities10">中央空调</label>
					
					<input type="checkbox" id="roomFacilities11" name="place.placeHotel.roomFacilities" value="分体式空调"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('分体式空调')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities11">分体式空调</label>
					<br>
					<input type="checkbox" id="roomFacilities12" name="place.placeHotel.roomFacilities" value="110V电源插座"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('110V电源插座')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities12">110V电源插座</label>
					
					<input type="checkbox" id="roomFacilities13" name="place.placeHotel.roomFacilities" value="220V电源插座"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('220V电源插座')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities13">220V电源插座</label>
					<br>
					<input type="checkbox" id="roomFacilities14" name="place.placeHotel.roomFacilities" value="保险箱"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('保险箱')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities14">保险箱</label>
					
					<input type="checkbox" id="roomFacilities15" name="place.placeHotel.roomFacilities" value="迷你酒吧"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('迷你酒吧')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities15">迷你酒吧</label>
					
					<input type="checkbox" id="roomFacilities16" name="place.placeHotel.roomFacilities" value="小冰箱"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('小冰箱')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities16">小冰箱</label>
					
					<input type="checkbox" id="roomFacilities17" name="place.placeHotel.roomFacilities" value="电水壶"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('电水壶')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities17">电水壶</label>
					
					<input type="checkbox" id="roomFacilities18" name="place.placeHotel.roomFacilities" value="咖啡壶/茶壶"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('咖啡壶/茶壶')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities18">咖啡壶/茶壶</label>
					
					<input type="checkbox" id="roomFacilities19" name="place.placeHotel.roomFacilities" value="免费瓶装水"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('免费瓶装水')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities19">免费瓶装水</label>
					<br>
					<input type="checkbox" id="roomFacilities20" name="place.placeHotel.roomFacilities" value="可饮用管道水"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('可饮用管道水')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities20">可饮用管道水</label>
					
					<input type="checkbox" id="roomFacilities21" name="place.placeHotel.roomFacilities" value="洗漱用品"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('洗漱用品')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities21">洗漱用品</label>
					
					<input type="checkbox" id="roomFacilities22" name="place.placeHotel.roomFacilities" value="拖鞋"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('拖鞋')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities22">拖鞋</label>
					
					<input type="checkbox" id="roomFacilities24" name="place.placeHotel.roomFacilities" value="雨伞"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('雨伞')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities24">雨伞</label>
					
					<input type="checkbox" id="roomFacilities25" name="place.placeHotel.roomFacilities" value="熨斗/熨衣板"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('熨斗/熨衣板')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities25">熨斗/熨衣板</label>
					
					<input type="checkbox" id="roomFacilities23" name="place.placeHotel.roomFacilities" value="浴衣"
					<s:if test="place.placeHotel.roomFacilities!=null && place.placeHotel.roomFacilities.contains('浴衣')">checked="checked"</s:if> />
					<label class="checkbox"  for="roomFacilities23">浴衣</label>
					
				</td>
	</tr>
	<tr>	   
				<td class="p_label" >娱乐设施： </td>
 				<td colspan="3" id="p_label1">
					<input type="checkbox" id="recreationalFacilities1" name="place.placeHotel.recreationalFacilities" value="KTV"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('KTV')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities1">KTV</label>
					
					<input type="checkbox" id="recreationalFacilities2" name="place.placeHotel.recreationalFacilities" value="综艺厅"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('综艺厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities2">综艺厅</label>
					
					<input type="checkbox" id="recreationalFacilities3" name="place.placeHotel.recreationalFacilities" value="舞厅"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('舞厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities3">舞厅</label>
					
					<input type="checkbox" id="recreationalFacilities4" name="place.placeHotel.recreationalFacilities" value="儿童乐园"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('儿童乐园')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities4">儿童乐园</label>
					
					<input type="checkbox" id="recreationalFacilities5" name="place.placeHotel.recreationalFacilities" value="夜总会"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('夜总会')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities5">夜总会</label>
					
					<input type="checkbox" id="recreationalFacilities6" name="place.placeHotel.recreationalFacilities" value="电子游戏机"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('电子游戏机')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities6">电子游戏机</label>
					<br>
					<input type="checkbox" id="recreationalFacilities7" name="place.placeHotel.recreationalFacilities" value="保龄球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('保龄球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities7">保龄球</label>
					
					<input type="checkbox" id="recreationalFacilities8" name="place.placeHotel.recreationalFacilities" value="壁球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('壁球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities8">壁球</label>
					
					<input type="checkbox" id="recreationalFacilities9" name="place.placeHotel.recreationalFacilities" value="高尔夫球场"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('高尔夫球场')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities9">高尔夫球场</label>
					
					<input type="checkbox" id="recreationalFacilities10" name="place.placeHotel.recreationalFacilities" value="迷你高尔夫球场"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('迷你高尔夫球场')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities10">迷你高尔夫球场</label>
					
					<input type="checkbox" id="recreationalFacilities11" name="place.placeHotel.recreationalFacilities" value="高尔夫练习场"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('高尔夫练习场')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities11">高尔夫练习场</label>
					
					<input type="checkbox" id="recreationalFacilities12" name="place.placeHotel.recreationalFacilities" value="视听室"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('视听室')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities12">视听室</label>
					<br>
					<input type="checkbox" id="recreationalFacilities13" name="place.placeHotel.recreationalFacilities" value="棋牌室"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('棋牌室')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities13">棋牌室</label>
					
					<input type="checkbox" id="recreationalFacilities14" name="place.placeHotel.recreationalFacilities" value="乒乓球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('乒乓球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities14">乒乓球</label>
					
					<input type="checkbox" id="recreationalFacilities15" name="place.placeHotel.recreationalFacilities" value="羽毛球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('羽毛球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities15">羽毛球</label>
					
					<input type="checkbox" id="recreationalFacilities16" name="place.placeHotel.recreationalFacilities" value="网球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('网球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities16">网球</label>
					
					<input type="checkbox" id="recreationalFacilities17" name="place.placeHotel.recreationalFacilities" value="篮球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('篮球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities17">篮球</label>
					
					<input type="checkbox" id="recreationalFacilities18" name="place.placeHotel.recreationalFacilities" value="桌球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('桌球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities18">桌球</label>
					
					<input type="checkbox" id="recreationalFacilities19" name="place.placeHotel.recreationalFacilities" value="水上运动"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('水上运动')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities19">水上运动</label>
					
					<input type="checkbox" id="recreationalFacilities20" name="place.placeHotel.recreationalFacilities" value="潜水"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('潜水')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities20">潜水</label>
					<br>
					<input type="checkbox" id="recreationalFacilities21" name="place.placeHotel.recreationalFacilities" value="日光浴场"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('日光浴场')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities21">日光浴场</label>
					
					<input type="checkbox" id="recreationalFacilities22" name="place.placeHotel.recreationalFacilities" value="排球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('排球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities22">排球</label>
					
					<input type="checkbox" id="recreationalFacilities23" name="place.placeHotel.recreationalFacilities" value="沙滩排球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('沙滩排球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities23">沙滩排球</label>
					
					<input type="checkbox" id="recreationalFacilities24" name="place.placeHotel.recreationalFacilities" value="足球"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('足球')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities24">足球</label>
					
					<input type="checkbox" id="recreationalFacilities25" name="place.placeHotel.recreationalFacilities" value="室内游泳池"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('室内游泳池')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities25">室内游泳池</label>
					
					<input type="checkbox" id="recreationalFacilities26" name="place.placeHotel.recreationalFacilities" value="室外游泳池"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('室外游泳池')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities26">室外游泳池</label>
					
					<input type="checkbox" id="recreationalFacilities27" name="place.placeHotel.recreationalFacilities" value="儿童游泳池"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('儿童游泳池')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities27">儿童游泳池</label>
					<br>
					<input type="checkbox" id="recreationalFacilities28" name="place.placeHotel.recreationalFacilities" value="滑雪场"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('滑雪场')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities28">滑雪场</label>
					<input type="checkbox" id="recreationalFacilities29" name="place.placeHotel.recreationalFacilities" value="足浴"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('足浴')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities29">足浴</label>
					<input type="checkbox" id="recreationalFacilities30" name="place.placeHotel.recreationalFacilities" value="SPA"
					<s:if test="place.placeHotel.recreationalFacilities!=null && place.placeHotel.recreationalFacilities.contains('SPA')">checked="checked"</s:if> />
					<label class="checkbox"  for="recreationalFacilities30">SPA</label>
				</td>
	</tr>
	<tr>	   
				<td class="p_label" >餐饮设施： </td>
 				<td colspan="3" id="p_label3">

 					<input type="checkbox" id="diningFacilities2" name="place.placeHotel.diningFacilities" value="中餐厅"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('中餐厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities2">中餐厅</label>
					<input to="diningFacilities2"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['中餐厅']}"/>

					<input type="checkbox" id="diningFacilities3" name="place.placeHotel.diningFacilities" value="西餐厅"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('西餐厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities3">西餐厅</label>
					<input to="diningFacilities3"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['西餐厅']}"/>

					<input type="checkbox" id="diningFacilities4" name="place.placeHotel.diningFacilities" value="日餐厅"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('日餐厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities4">日餐厅</label>
					<input to="diningFacilities4"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字"  style="width:90px;" value="${facilitiesRemarkMap['日餐厅']}"/>

					<input type="checkbox" id="diningFacilities11" name="place.placeHotel.diningFacilities" value="餐　厅"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('餐　厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities11">餐　厅</label>
					<input to="diningFacilities11"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['餐　厅']}"/>

					<br>
					<input type="checkbox" id="diningFacilities1" name="place.placeHotel.diningFacilities" value="酒吧"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('酒吧')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities1">酒吧</label>
					<input to="diningFacilities1"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字"  style="width:90px;" value="${facilitiesRemarkMap['酒吧']}"/>
										
					<input type="checkbox" id="diningFacilities5" name="place.placeHotel.diningFacilities" value="咖啡厅"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('咖啡厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities5">咖啡厅</label>
					<input to="diningFacilities5"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['咖啡厅']}"/>
					
					<input type="checkbox" id="diningFacilities6" name="place.placeHotel.diningFacilities" value="茶室"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('茶室')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities6">茶室</label>
					<br>
					<input type="checkbox" id="diningFacilities7" name="place.placeHotel.diningFacilities" value="宴会厅"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('宴会厅')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities7">宴会厅</label>
					
					<input type="checkbox" id="diningFacilities8" name="place.placeHotel.diningFacilities" value="室外烧烤"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('室外烧烤')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities8">室外烧烤</label>
					<br>
					<input type="checkbox" id="diningFacilities9" name="place.placeHotel.diningFacilities" value="全天送餐"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('全天送餐')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities9">全天送餐</label>
					
					<input type="checkbox" id="diningFacilities10" name="place.placeHotel.diningFacilities" value="非全天送餐"
					<s:if test="place.placeHotel.diningFacilities!=null && place.placeHotel.diningFacilities.contains('非全天送餐')">checked="checked"</s:if> />
					<label class="checkbox"  for="diningFacilities10">非全天送餐</label>
					<input to="diningFacilities10"  type="text" class="facilitiesRemark sensitiveVad" placeholder="限制输入20个字" style="width:90px;" value="${facilitiesRemarkMap['非全天送餐']}"/>
					
				</td>
	</tr>
	<tr>	   
			<td class="p_label" >服务项目： </td>
				<td colspan="3" id="p_label2">
				<input type="checkbox" id="services1" name="place.placeHotel.services" value="DDD国内长途电话"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('DDD国内长途电话')">checked="checked"</s:if> />
				<label class="checkbox"  for="services1">DDD国内长途电话</label>
				
				<input type="checkbox" id="services2" name="place.placeHotel.services" value="IDD国际长途电话"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('IDD国际长途电话')">checked="checked"</s:if> />
				<label class="checkbox"  for="services2">IDD国际长途电话</label>
				
				<input type="checkbox" id="services3" name="place.placeHotel.services" value="公共区域免费无线上网"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('公共区域免费无线上网')">checked="checked"</s:if> />
				<label class="checkbox"  for="services3">公共区域免费无线上网</label>
				
				<input type="checkbox" id="services4" name="place.placeHotel.services" value="行李员"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('行李员')">checked="checked"</s:if> />
				<label class="checkbox"  for="services4">行李员</label>
				<br>
				<input type="checkbox" id="services5" name="place.placeHotel.services" value="行李存放"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('行李存放')">checked="checked"</s:if> />
				<label class="checkbox"  for="services5">行李存放</label>
				
				<input type="checkbox" id="services6" name="place.placeHotel.services" value="票务服务"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('票务服务')">checked="checked"</s:if> />
				<label class="checkbox"  for="services6">票务服务</label>
				
				<input type="checkbox" id="services7" name="place.placeHotel.services" value="叫车服务"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('叫车服务')">checked="checked"</s:if> />
				<label class="checkbox"  for="services7">叫车服务</label>
				
				<input type="checkbox" id="services8" name="place.placeHotel.services" value="叫醒服务"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('叫醒服务')">checked="checked"</s:if> />
				<label class="checkbox"  for="services8">叫醒服务</label>
				
				<input type="checkbox" id="services9" name="place.placeHotel.services" value="穿梭机场班车"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('穿梭机场班车')">checked="checked"</s:if> />
				<label class="checkbox"  for="services9">穿梭机场班车</label>
				<br>
				<input type="checkbox" id="services10" name="place.placeHotel.services" value="接机服务"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('接机服务')">checked="checked"</s:if> />
				<label class="checkbox"  for="services10">接机服务</label>
				
				<input type="checkbox" id="services11" name="place.placeHotel.services" value="免费交通图"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('免费交通图')">checked="checked"</s:if> />
				<label class="checkbox"  for="services11">免费交通图</label>
				
				<input type="checkbox" id="services12" name="place.placeHotel.services" value="前台贵重物品保险柜"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('前台贵重物品保险柜')">checked="checked"</s:if> />
				<label class="checkbox"  for="services12">前台贵重物品保险柜</label>
				
				<input type="checkbox" id="services13" name="place.placeHotel.services" value="洗衣服务"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('洗衣服务')">checked="checked"</s:if> />
				<label class="checkbox"  for="services13">洗衣服务</label>
				
				<input type="checkbox" id="services14" name="place.placeHotel.services" value="外币兑换"
				<s:if test="place.placeHotel.services!=null && place.placeHotel.services.contains('外币兑换')">checked="checked"</s:if> />
				<label class="checkbox"  for="services14">外币兑换</label>
			</td>
	</tr>
    </table>
 	<p class="tc mt10 mb10">
	    <input class="btn btn-small w5" value="确定" onclick="javascript:return checkAndSubmit('${basePath}/place/dohotelUpdate.do','hotelForm');"></input>
	</p>
     </form>
</div>
</body>
<script type="text/javascript">
$(function(){
	//校验
	$("#hotelForm").validate({
		rules: {    
			"place.name":{
				required:true
			},
			"place.pinYin":{
				required:true
			},
			"place.pinYinUrl":{
				required:true
			},
			"place.isValid":{
				required:true
			},
			"place.seq":{
				required:true,
				number:true
            },
            "place.province":{
            	required:true
            },
            "place.city":{
            	required:true
            },
			"place.placeType":{
				required:true
			},
            "place.placeHotel.picDisplay":{
            	required:true
            },
            "place.placeHotel.hotelStar":{
            	required:true
            },
            "place.remarkes":{
            	required:true,
            	maxlength:1000
            },
            "place.placeHotel.hotelForeigner":{
            	required:true
            },
            "place.placeHotel.petCarry":{
            	required:true
            }
		}, 
		messages: {    
			"place.name":{
				required:"酒店名称不能为空!"
			},
			"place.pinYin":{
				required:"请修改酒店名称拼音!"
			},
			"place.pinYinUrl":{
				required:"请输入设置酒店URL!"
			},
			"place.isValid":{
				required:"请选择酒店状态!"
			},	
			"place.seq":{
				required:"请输入酒店排序值!",
				number:"请输入整数!"
            },
            "place.province":{
            	required:"请输入省份"
            },
            "place.city":{
            	required:"请输入城市"
            },
			"place.placeType":{
				required:"请选择酒店境内境外!"
			},
            "place.placeHotel.picDisplay":{
            	required:"请选择列表显示图片大小!"
            }, 
            "place.placeHotel.hotelStar":{
            	required:"请选择星级标准!"
            }, 
            "place.remarkes":{
            	required:"请输入酒店简介!",
            	maxlength:"酒店简介字符长度大于1000"
            },
            "place.placeHotel.hotelForeigner":{
            	required:"请选择是否接待外宾!"
            },
            "place.placeHotel.petCarry":{
            	required:"请选择是否可以携带宠物!"
            }
		}
	});
	$("input[name=place.placeHotel.breakfastType]").change(function(){
		var id = $(this).attr("id");
		if( $(this).attr("checked") ){
			$("#"+id+"-price").removeAttr("disabled");
			$("#"+id+"-price").val(0);
		}else{
			$("#"+id+"-price").attr("disabled","disabled");
			$("#"+id+"-price").val('');
		}
	});
	$.each($("input[name=place.placeHotel.breakfastType]"),function(i,n){
			var id = $(n).attr("id");
			if( $(n).attr("checked") ){
				$("#"+id+"-price").removeAttr("disabled");
			}else{
				$("#"+id+"-price").attr("disabled","disabled");
			}
	});
});
	function addPlacePlaceDest(placeId){
		if($('#placeId').val()==null || $('#placeId').val()=='') {
			alert('亲，为了保证业务的严整性，请先保存基本信息！');
			return;
		}
		var url='${basePath}/place/parentPlaceList.do?placePlaceDest.placeId='+placeId+"&math="+Math.random();
		$.ajaxDialog({
			url:url,
			title:"目的地/景区关联关系",
			beforeclose:function(event, ui) {
				$.ajax({
					type:"POST",
					async:false,
					url:"${basePath}/place/loadParentPlaceJson.do",
					data:{
						"placePlaceDest.placeId":$("#placeId").val()
					},
					dataType:"json",
					success:function (data) {
						$("#parent_place_id").val('');
						for (var i = 0; i < data.placePlaceDests.length; i++) {
							if (data.placePlaceDests[i].isMaster=="Y") {
								flag = true;
								$("#parent_place_id").val(data.placePlaceDests[i].parentPlaceId);
								break;
							}
						}
						$("#placeSuperior").html(data.text);
						if (!flag) {
							alert("请制定上级目的地");
							return false;
						}
						return flag;
					},
					error:function (data) {
						return false;
					}
				});
				return flag;
			}
		});
	};
	function addPlacePinyin(type){
		if($('#placeId').val()==null || $('#placeId').val()=='') {
			alert('亲，为了保证业务的严整性，请先保存基本信息！');
			return;
		}
		var placeId = '<s:property value="place.placeId"/>';
		var title= '高频关键字添加拼音';
		var url='${basePath}/place/toAddPlacePinyin.do?placeSearchPinyin.objectId='+placeId+'&placeSearchPinyin.objectType='+type+'&math='+Math.random();
		$.ajaxDialog(url,title,'win_place');
	};
	function addPlaceNamePinyin(type){
		if($('#parent_place_id').val()==null || $('#parent_place_id').val()=="") {
			alert('亲，为了保证业务的严整性，请先填写层级关系！','win_place');
			return;
		}
		var placeId = '<s:property value="place.placeId"/>';
		var url='${basePath}/place/toAddPlacePinyin.do?placeSearchPinyin.objectId='+placeId+'&placeSearchPinyin.objectType='+type+'&math='+Math.random();
		$.ajaxDialog(url,'相关名称拼音的相关修改管理','win_place');
	};
	
	
	//提交之后，重刷新table
	function checkAndSubmit(url,form) {
		if(!$("#hotelForm").valid()){
			return false;
		}
		var sensitiveValidator=new SensitiveWordValidator($('#'+form), true);
		if(!sensitiveValidator.validate()){
			return;
		}
		
		$.each($(".facilitiesRemark"),function(i,n){
			var _v = $(n).val();
			_v = _v.replace("(","").replace(")","");
			var _checkbox = $("#"+$(n).attr("to"));
			if(_v != "" && _checkbox.attr("checked")){
				var _checkbox_val = _checkbox.val();
				var _index = _checkbox_val.indexOf("(");
				if(_index!=-1){
					_checkbox_val = _checkbox_val.substring(0,_index);
				}
				_checkbox.val(_checkbox_val+"("+_v+")");
			}
		});
		
		var options = {
				url:url,
				type : 'POST',
				dataType:'json',
					success:function(data){
		      		   if(data.success==true) {
						 alert("操作成功!");
						 $("#hotel_info_dialog").dialog("close");
		  				} else {
						  alert("操作失败："+data.message);
					    }
					},
				error:function(){
		                 alert("出现错误");
		             }
			};
	
		$('#'+form).ajaxSubmit(options); 
	}
	$(function(){
		$('#win_place').bind('dialogbeforeclose', function(event, ui) {
			var flag = false;
			$.ajax({
				type:"POST",
				async:false,
				url:"${basePath}/place/loadParentPlaceJson.do",
				data:{
					"placePlaceDest.placeId":$("#placeId").val()
				},
				dataType:"json",
				success:function (data) {
					$("#parent_place_id").val('');
					for (var i = 0; i < data.placePlaceDests.length; i++) {
						if (data.placePlaceDests[i].isMaster=="Y") {
							flag = true;
							$("#parent_place_id").val(data.placePlaceDests[i].parentPlaceId);
							break;
						}
					}
					$("#placeSuperior").html(data.text);
					if (!flag) {
						alert("请制定上级目的地");
						return false;
					}
					return flag;
				},
				error:function (data) {
					return false;
				}
			});
			return flag;
		});	
	});
</script>
</html>