<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Untitled Document</title>
  <link href="http://pic.lvmama.com/styles/zt/Disney/framework-style.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
</head>
<body class="iframebody">
  <div class="lvmama" id="lvmama">
    <ul class="lv_tab yahei test">
      <li class="cur">上 海</li>
      <li>成 都</li>
      <li>北 京</li>
      <li>广 州</li>
    </ul>
    <!--上海出发地-->
    <div class="dest_produ dis">
      <!--跟团游部分-->
      <div class="lv_products">
        <h3 class="yahei">上海跟团游</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_sh_gty')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a> <i>¥ <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起</i> 
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--自由行部分-->
      <div class="lv_products">
        <h3 class="yahei">自由行</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_sh_zyx')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a> <i>¥ <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起</i> 
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--门票及酒店部分-->
      <div class="lv_products">
        <h3 class="yahei">门票及酒店</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_sh_mpjd')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <div class="lv_bottom"></div>
    </div>
    <!--dest_produ end-->
    <!--成都出发地-->
    <div class="dest_produ">
      <!--跟团游部分-->
      <div class="lv_products">
        <h3 class="yahei">成都跟团游</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_cd_gty')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--自由行部分-->
      <div class="lv_products">
        <h3 class="yahei">自由行</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_cd_zyx')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--门票及酒店部分-->
      <div class="lv_products">
        <h3 class="yahei">门票及酒店</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_cd_mpjd')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <div class="lv_bottom"></div>
    </div>
    <!--dest_produ end-->
    <!--北京出发地-->
    <div class="dest_produ">
      <!--跟团游部分-->
      <div class="lv_products">
        <h3 class="yahei">北京跟团游</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_bj_gty')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--自由行部分-->
      <div class="lv_products">
        <h3 class="yahei">自由行</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_bj_zyx')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--门票及酒店部分-->
      <div class="lv_products">
        <h3 class="yahei">门票及酒店</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_bj_mpjd')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <div class="lv_bottom"></div>
    </div>
    <!--dest_produ end-->

    <!--广州出发地-->
    <div class="dest_produ">
      <!--跟团游部分-->
      <div class="lv_products">
        <h3 class="yahei">广州跟团游</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_gz_gty')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--自由行部分-->
      <div class="lv_products">
        <h3 class="yahei">自由行</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_gz_zyx')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <!--门票及酒店部分-->
      <div class="lv_products">
        <h3 class="yahei">门票及酒店</h3>
        <ul class="proList">
          <@s.iterator value="map.get('${station}_gz_mpjd')">
            <li>
              <a href="${url?if_exists}" target="_blank">${title?if_exists}</a>
              <i>
                ¥
                <strong>${memberPrice?if_exists?replace(".0","")}</strong>
                起
              </i>
            </li>
          </@s.iterator>
        </ul>
        <a class="more" href="">
          更多
          <span class="c_icon">+</span>
        </a>
      </div>
      <!--lv_products end-->
      <div class="lv_bottom"></div>
    </div>
    <!--dest_produ end--> </div>
  <script type="text/javascript">
$(function(){
  var $iframeHt,$ParentHt,_i=6,be_click="+",af_click="-";
  function iframeHeight(){
  $iframeHt=$("#lvmama").height();
  $ParentHt=$("#iframe-container,#iframe",window.parent.document).height($iframeHt);
  }
  /*tab选项卡*/
  $(".lv_tab li").bind("click",function(){
    var _index=$(this).index();
    $(this).addClass("cur").siblings().removeClass("cur");
    $(".dest_produ").eq(_index).show().siblings(".dest_produ").hide();
    iframeHeight();
  });
  /*对四个目的地的所有模块初始化，每个模块只显示前面i条产品*/
  $(".proList").each(function(index){
    var _length,_morebtn,_allgtList;
    _allgtList=$(".proList").find("li:gt(5)");//所有大于给定i的四个目的中所有的li
    _length=$(this).find("li").length;//每个产品模块的产品li个数
    _morebtn=$(this).next(".more");
    if(_length>_i) {
      _morebtn.css("visibility","visible");
      _allgtList.hide();
       }    
  });
  /*点击更多按钮显示隐藏的产品介绍代码*/
   function ProductShow(proList,assignList,parentSib,c_icon){
    parentSib.find("li:gt(5)").hide();
    parentSib.find(".c_icon").text(be_click);
    c_icon.text()==be_click?c_icon.text(af_click):c_icon.text(be_click);
    assignList.css("display")=="none"?proList.find("li:gt(5)").show():proList.find("li:gt(5)").hide();
    iframeHeight();
    } 
  $(".more").bind("click",function(){
    var _proList=$(this).prev();
    var _assignList=_proList.find("li").eq(_i);
    var _parentSib=$(this).parent(".lv_products").siblings(".lv_products");
    var _c_icon=$(this).find("span");
    ProductShow(_proList,_assignList,_parentSib,_c_icon);
     return false;
  });
  iframeHeight();

});
</script>
</body>
</html>