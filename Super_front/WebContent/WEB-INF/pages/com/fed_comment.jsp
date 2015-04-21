<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
#msgDiv2 {
    z-index:51;
    width:420px;
    position:absolute;
    left:50%;
    top:45%;
    font-size:12px;
    margin-left:-225px;
    display: none;
    background-color:#FFFFFF;
    border:1px dashed #ff0000;
}
#bgDiv2 {
	z-index:50;
    display: none;
    position: absolute;
    top: 0px;
    background-color:#FFFFFF;
    left: 0px;
    right:0px;
    filter:alpha(opacity=50);
}
ts4{padding:10px 15px;}
.mt15{margin-top:15px;}
.mb5{margin-bottom:5px;}

.ts45{padding:10px 0 5px 0;}

.ts451{float:left; margin-right:10px;}

.ts452{border-top:1px solid #D8DFEA; border-bottom:1px solid #D8DFEA; padding:10px 0 10px 45px; margin:10px 0;}

.ts42{text-align:center; padding:5px;}
.r{float:right; clear:right;}
.rbs1{border:1px solid #f9a4b2; float:left;}
.rb1-12,.rb2-12{height:23px; color:#fff; font-size:12px; background:#D32C47; padding:3px 5px; border-left:1px solid #fff; border-top:1px solid #fff; border-right:1px solid #6a6a6a; border-bottom:1px solid #6a6a6a; cursor:pointer;}
.it1{background:#fff; border:1px solid #bebebe; color:#333;}
.rb2-12{background:#C71E3A;}
.flw5{float:left; width:5px;}
.gbs1{border:1px solid #e2e2e2; float:left;}
.gb1-12,.gb2-12{height:23px; color:#333; font-size:12px; background:#e5e5e5; padding:3px 5px; border-left:1px solid #fff; border-top:1px solid #fff; border-right:1px solid #6a6a6a; border-bottom:1px solid #6a6a6a; cursor:pointer;}
</style>

<div id="bgDiv2">
</div>
<div id="msgDiv2">
<div class="div_block" style="padding:5px;float:left;display:block;">
<img src="/images/assoi/close.gif" onclick="closeDetailP();" id="msgShut22" width="23" height="21" style="cursor: hand;float:right;"/>
<div style="background-color:#fff;">
<div class="ts4" id="add3">				
	<div class="ts45">
		<div style="float:left; clear:left;"><mis:photoTags height="50" imgURL="${user.imageUrl}" width="50" photoDefaultName="img_45_45" style="margin-left: 10px;_margin-left:5px;#margin-left:10px;"/></div>
		<div>
			<div style="float: left;margin-top: 5px;line-height: 18px;" >&nbsp;&nbsp;您需通过<s:property value="user.userName"/>的验证才能加他为好友。<br />
			&nbsp;&nbsp;给他发送信息：
			</div>
			<form name=formcontent onsubmit="return false;">
			<div style="padding:10px 0px 8px 80px ;">&nbsp;&nbsp;<span class="it_s " style="margin-top:-20px;margin-bottom:10px;"><textarea name="content" id="content" style="width:260px;height:60px;" class="it1"></textarea></span></div>
			</form>
		</div>
		<div class="r" style="margin-right: 10px;"><input type="button" id="btn_fb" value="确定" class="rb1-12" onclick="addFed('<s:property value="user.userId"/>');" /></div>
	</div>
	</div>
</div>
</div>
</div>
