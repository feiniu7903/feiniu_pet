<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
    <meta charset="utf-8">
    <title>我的攻略-驴妈妈旅游网</title>
    <#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
    <#include "/common/coremetricsHead.ftl">
</head>
<body  id="page-guide">
        <#include "/WEB-INF/pages/myspace/base/header.ftl"/>
        <div class="lv-nav wrap">
            <p>
                <a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>&gt;
                <a class="current">我的攻略</a>
            </p>
        </div>
        <div class="wrap ui-content lv-bd">
            <#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
            <div class="lv-content">
                <!-- 我的攻略 start -->
                <div id="tabs" class="ui-box guide-edit">
                    <div class="ui-tab-title">
                        <h3>我的攻略</h3>
                        <ul class="tab-nav hor">
                            <li><a href="#tabs-1">发表的攻略<span id="pub"></span></a></li>
                            <li><a href="#tabs-2">收藏的攻略<span id="keep"></span></a></li>
                            <li><a href="#tabs-3">下载的攻略<span id="down"></span></a></li>
                        </ul>
                    </div>
                    <div id="tabs-1" class="ui-tab-box">
                        <!-- 发表的攻略>> -->
                        <div class="no-list" >
                            <p id="tishi1"><a href="http://www.lvmama.com/guide/create.php?activeid=0" class="ui-btn ui-btn4"><i>&nbsp;发表攻略&nbsp;</i></a></p>
                        </div>
                        <div class="guide-box" id="hasMyArticleDiv">
                            <table class="lv-table guide-table">
                                <colgroup><col class="lvcol-1"><col class="lvcol-2"><col class="lvcol-3"><col class="lvcol-4"></colgroup>
                                <thead><tr class="thead"><th>攻略名称</th><th>发表日期</th><th>状态</th><th>操作</th></tr></thead>
                                    <tbody id="table_id1"></tbody>
                            </table>
                        </div>
                    <!-- <<发表的攻略 -->
                    </div>
                    <div id="tabs-2" class="ui-tab-box">
                        <!-- 收藏攻略>> -->
                        <div class="no-list">
                            <p id="tishi2"><a target="_blank" class="btn_golook" href="http://www.lvmama.com/guide">现在去看看</a></p>
                        </div>
                        <div class="guide-box" id="hasMyFavoriteDiv">
                            <table class="lv-table guide-table">
                                <colgroup><col class="lvcol-1"><col class="lvcol-2"><col class="lvcol-3"></colgroup>
                                <thead><tr class="thead"><th>攻略名称</th><th>收藏日期</th><th>操作</th></tr></thead>
                                <tbody id="table_id2"></tbody>
                            </table>
                        </div>
                        <!-- <<收藏攻略 -->
                    </div>
                    <div id="tabs-3" class="ui-tab-box">
                        <!-- 下载攻略>> -->
                        <div class="no-list">
                            <p id="tishi3"><a target="_blank" class="btn_golook" href="http://www.lvmama.com/guide">现在去看看</a></p>
                        </div>
                        <div class="guide-box" id="hasMyDownDiv" >
                            <table class="lv-table guide-table">
                                <colgroup><col class="lvcol-1"><col class="lvcol-2"><col class="lvcol-3"></colgroup>
                                <thead><tr class="thead"><th>攻略名称</th><th>下载日期</th><th>操作</th></tr></thead>
                                    <tbody id="table_id3"></tbody>
                            </table>
                        </div>
                        <!-- <<下载攻略 -->             
                    </div>
                </div>
            </div>
        </div>
    <#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/> 

<!-- 封住窗口的弹出框  -->  
<div class="xh_overlay"></div>

<script type="text/javascript">
//删除收藏攻略
 function del_myFavorite(deleteId){
      $("div.xh_overlay").show().height($(document).height());
      
     if (confirm("您确定要删除吗！")){
            $.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=delMyFavorite&id=" + deleteId + "&callback=?",function(json){
                if (json.code == 200) {
                    var id = "#getMyFavorite"+deleteId;
                    $(id).remove();
                    var num = $("#table_id2>tr").length;
                    $("#keep").html("("+ num +")");
                    if(num == 0){
                        $("#tishi2").html("您还没有收藏过旅游攻略！&nbsp;&nbsp;" + $("#tishi2").html()); 
                        $("#hasMyFavoriteDiv").hide();
                    }
                }else{
                    alert("由于某些原因操作失败.");
                }
            });
        }
        $("div.xh_overlay").hide();
  } 
 
 //删除发表的攻略
 function del_myArticle(delurl, deleteId){
      $("div.xh_overlay").show().height($(document).height());
        
        if (confirm("您确定要删除吗！")){
            $.getJSON(delurl,function(json){
                if (json.code == 200) {
                    var id = "#getMyArticle"+deleteId;
                    $(id).remove();
                    var num = $("#table_id1>tr").length;
                    $("#pub").html("("+ num +")");
                    if(num == 0){
                        $("#tishi1").html("您还没有发表过旅游攻略！&nbsp;&nbsp;" + $("#tishi1").html());
                        $("#hasMyArticleDiv").hide();
                    }
                }else{
                    alert("由于某些原因操作失败.");
                }
            });
        }
        $("div.xh_overlay").hide();
  } 
  
 //发表的攻略
  function getMyArticle(){
        $.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=getMyArticle&page=<@s.property value="publishPage" />&pageSize=15&format=jsonp&callback=?",function(json){ 
            if (json.code == 200) {
                var jsonList = json.data.data;
                var len=jsonList.length;
                if(len>0){
                    $.each(jsonList,function(i){
                        var title = jsonList[i].title.substring(0, 25);
                        var blank = jsonList[i].url=='javascript:void(0);'?'':' target="_blank" ';
                        if(jsonList[i].status == 99){
                            $("#table_id1").append("<tr id='getMyArticle"+ jsonList[i].articleid +"' ><td><a "+blank+" href='" + jsonList[i].url+ "'>"+title+"</a></td><td>"+jsonList[i].date+"</td><td>"+jsonList[i].statusname+"</td><td><a class='xh_del-this' target='_blank' href='" + jsonList[i].editurl+"'>编辑</a>&nbsp;&nbsp;</td></tr>");
                        
                        }else{
                            $("#table_id1").append("<tr id='getMyArticle"+ jsonList[i].articleid +"' ><td><a "+blank+" href='" + jsonList[i].url+ "'>"+title+"</a></td><td>"+jsonList[i].date+"</td><td>"+jsonList[i].statusname+"</td><td><a class='xh_del-this' target='_blank' href='" + jsonList[i].editurl+"'>编辑</a>&nbsp;&nbsp;<a class='xh_del-this' href='javascript:void(0);' onclick='del_myArticle(\""+jsonList[i].delurl+"\","+jsonList[i].articleid+");'>删除</a></td></tr>");
                        }
                    });
                    // 记录总数
                    $("#pub").append("("+json.data.recordCount+")");
                    $("#hasMyArticleDiv").show();
                } else {
                    $("#tishi1").html("您还没有发表过旅游攻略！&nbsp;&nbsp;" + $("#tishi1").html());
                    $("#hasMyArticleDiv").hide();
                 }
             }
        });
 }
 
 //收藏攻略
  function getMyFavorite(){
        $.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=getMyFavorite&page=<@s.property value="favoritePage" />&pageSize=15&format=jsonp&callback=?",function(json){ 
            if (json.code == 200) {
                var jsonList = json.data.data;
                var len=jsonList.length;
                if(len>0){
                    $.each(jsonList,function(i){
                        var title = jsonList[i].title.substring(0, 25);
                        $("#table_id2").append("<tr id='getMyFavorite"+ jsonList[i].id +"' ><td><a target='_blank' href='"+ jsonList[i].url+ "'>"+title+"</a></td><td>"+jsonList[i].date+"</td><td><a class='xh_del-this' href='javascript:void(0);' onclick='del_myFavorite("+jsonList[i].id+");'>删除</a></td></tr>");
                    });
                    // 记录总数
                    $("#keep").append("("+json.data.recordCount+")");
                    $("#hasMyFavoriteDiv").show();
                }else{
                    $("#tishi2").html("您还没有收藏过旅游攻略！&nbsp;&nbsp;" + $("#tishi2").html());
                    $("#hasMyFavoriteDiv").hide();
                }
            } 
        });
 }
 
 //下载攻略
 function getMyDown(){
        $.getJSON("http://www.lvmama.com/guide/ajax/api.php?action=getMyDown&page=<@s.property value="downloadPage" />&pageSize=15&format=jsonp&callback=?",function(json){
           if (json.code == 200) {
            var jsonList = json.data.data;
            var len=jsonList.length;
            if(len>0) {
                $.each(jsonList,function(i){
                    var title = jsonList[i].title.substring(0, 25);
                    $("#table_id3").append("<tr><td><a target='_blank' href='"+ jsonList[i].url+"'>"+title+"</a></td><td>"+jsonList[i].date+"</td><td><a class='ui-btn ui-btn2' href='" + jsonList[i].downurl + "'><i>下载PDF版本</i></a></td></tr>");
                });
                // 记录总数
                    $("#down").append("("+json.data.recordCount+")");
                    $("#hasMyDownDiv").show();
            } else {
                $("#tishi3").html("您还没有下载过旅游攻略！&nbsp;&nbsp;" + $("#tishi3").html());
                $("#hasMyDownDiv").hide();
            }
          }
      });
 }
 
    $(document).ready(function(){
          getMyArticle();
          getMyFavorite();
          getMyDown();
    });
</script>
	<script>
		cmCreatePageviewTag("我的攻略", "D0001", null, null);
	</script>
</body>
</html>