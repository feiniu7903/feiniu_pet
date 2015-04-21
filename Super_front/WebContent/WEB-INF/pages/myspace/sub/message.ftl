<!DOCTYPE html>
<#include "/WEB-INF/pages/myspace/base/doctype.ftl"/>
<head>
	<meta charset="utf-8">
	<title>站内消息-驴妈妈旅游网</title>
	<#include "/WEB-INF/pages/myspace/base/lv-meta.ftl"/>
	<#include "/common/coremetricsHead.ftl">
</head>
<body id="page-message">
		<#include "/WEB-INF/pages/myspace/base/header.ftl"/>
		<div class="lv-nav wrap">
			<p>
				<a href="http://www.lvmama.com/myspace/index.do">我的驴妈妈</a>&gt;
				<a class="current">站内消息</a>
			</p>
		</div>
		<div class="wrap ui-content lv-bd">
			<#include "/WEB-INF/pages/myspace/base/lv-nav.ftl"/>
			
			<!-- 站内消息>> -->
			<div class="lv-content">
				<div class="ui-box message-box">
					<div class="ui-box-title"><h3>站内消息&#12288;</h3></div>
						<div class="news-box" id="msgListDiv">
						</div>
				</div>
			</div>
			<!-- 站内消息>> -->
			
		</div>
	<#include "/WEB-INF/pages/myspace/base/lv-footer.ftl"/>	

<!-- 封住窗口的弹出框  -->	
<div class="xh_overlay"></div>

<script type="text/javascript">

	function getMyMessage(page){
		var currentPage=page;
		var url="http://www.lvmama.com/message/index.php?r=PrivatePm/Index&page="+currentPage+"&callback=?";
		$("#msgListDiv").html("");
		$.getJSON(url,function(json){
			if (json.code == 200) {
				var jsonList = json.data.records;
				var len=jsonList.length;
				if(len>0){
					var checkBoxAllUp = "checkBoxAllUp";
					var table="<div class='news-top'>"+
							  "共<span>"+json.data.summary.totalCount+"</span>条，未读<b id='unreadCountId'>"+json.data.summary.unreadCount+"</b>条，<a class='yidu-all' href='javascript:void(0);'>全部设为已读</a>"+
							  "</div>"+
							  "<dl class='news-list'>"+
							  "<dt><span class='news-input-box'><label class='js-news-input'><input type='checkbox' id='checkBoxAllUp' onclick='checkAAll(1)' >全选</label></span><span class='news-bt'>标题</span><span class='news-time'>发送时间</span><span class='news-cz'>操作</span></dt>";
							  
				     $.each(jsonList,function(i){
				        if(jsonList[i].status==0){
				        	var css="news-bt news-bt-bold";
				        }else{
				        	var css="news-bt";
				        }
				     	table+="<dd>"+
								"   <div class='news-list-box'>"+
								"      <span class='news-input-box'><input type='checkbox' onclick='checkAAll(2)' value='"+jsonList[i].pid+"'></span>"+
								"      <span class='"+css+"'><a href='javascript:void(0);' status='"+jsonList[i].status+"' pid='"+jsonList[i].pid+"'>"+
								                             jsonList[i].subject+"</a></span>"+
								"      <span class='news-time'>"+jsonList[i].create_time+"</span>"+
								"      <span class='news-cz'><a class='js-delete-one' href='javascript:void(0);' pid='"+jsonList[i].pid+"'>删除</a></span>"+
								"   </div>"+
								"   <div class='lv-tips-box clearfix lv-bd'>"+
								"       <div class='lv-arrow lv-bottom'><div class='pointy-bordr'></div><div class='pointy'></div></div>"+
								"       <div class='cont'>"+
											jsonList[i].message +
								"           <br/><span class='pub-time'>驴妈妈旅游网 </span>"+
								"       </div>"+
								"   </div>"+
 						        "</dd>";
				     });

				    table+="</dl>"+
				           "<div class='new-bot-box'>"+
				           "   <p class='new-bot'><span class='news-input-box'><label class='js-news-input'><input type='checkbox' onclick='checkAAll(1)' />全选</label></span><a class='js-delete-all' href='javascript:void(0);'>删除选中消息</a></p>"+
				           "   <div id='pageId'>"+
				           "   </div>"+
				           "</div>";
				           
					$("#msgListDiv").append(table);
					
			//分页 
			var defaultPageSize =json.data.page.pageSize;
			var currentPage = json.data.page.currentPage;
			var pageCount = json.data.page.pageCount;
			$.ajax({
				url: "/myspace/message/getPage.do",
				type:"get",
				data:{"defaultPageSize":defaultPageSize,"pageCount":pageCount,"currentPage":currentPage},
				dataType:"json",
				success:function(data){
					$("#pageId").append(data.pages);
				}
			});
			
			 }else{
			 	var table="<p class='no-list'>您当前没有站内消息！</p>";
			 	$("#msgListDiv").append(table);
			 }
			}
		});
		
		
	}
	/站内信收缩*/
	function onClickMessage(){
		$(".news-bt a").live("click",function () {
			var _textBox = $(this).parents('.news-list-box').next();
			$(this).parent('.news-bt').removeClass('news-bt-bold');
			if(_textBox.css('display')=='block'){
				_textBox.slideUp();
			}else{
				_textBox.slideDown();
				$(this).parents('dd').siblings('dd').find('.lv-tips-box').slideUp();
			}
			var pid=$(this).attr("pid");
			var status = $(this).attr("status");
			if(status == 0 && pid!=""){
				var current_id=$(this);
				var url="http://www.lvmama.com/message/index.php?r=PrivatePm/Read&id="+pid+"&callback=?";
				$.getJSON(url,function(json){
					if (json.code == 200) {
						current_id.attr("status",1);
						$("#unreadCountId").text(json.data.unreadCount);
					}
				});
			}
		});
	}
	/*点击右侧删除，默认选中当前行*/
	function deleteByPid(){
		$('.js-delete-one').live('click',function(){ 
			$(this).parent('.news-cz').siblings('.news-input-box').find('input').attr("checked", true);
			$(this).parents('dd').siblings('dd').find('.news-input-box').find('input').removeAttr('checked');
			var pid=$(this).attr("pid");
			if(confirm("您确定要删除吗？")){
				var url="http://www.lvmama.com/message/index.php?r=PrivatePm/Delete&id="+pid+"&callback=?";
				$.getJSON(url,function(json){
					if (json.code == 200) {
						document.location.reload();
					}
				});
			}
		});
	}
	/*删除选择的消息*/
	function deleteByAll(){
		$('.js-delete-all').live('click',function(){
			var pid="";
			$('.news-list dd').find('input:checked').each(function(){
				pid+=","+$(this).val();
			});
			if(pid==""){
				alert("请选择需要删除的消息！");
				return;
			}else{
				pid=pid.substring(1);
				if(confirm("您确定要删除吗？")){
					var url="http://www.lvmama.com/message/index.php?r=PrivatePm/Delete&id="+pid+"&callback=?";
					$.getJSON(url,function(json){
						if (json.code == 200) {
							document.location.reload();
						}
					});
				}
			}
			
		});
	}
	/*全部设置已读*/
	function setReadAll(){
		$(".yidu-all").live("click",function(){
			var unRead=$("#unreadCountId").text(); 
			if(unRead!=0){
				var url="http://www.lvmama.com/message/index.php?r=PrivatePm/Read&callback=?";
				$.getJSON(url,function(json){
					if (json.code == 200) {
						document.location.reload();
					}
				});
			}
		});
	}
	
    /*全选，反选*/
  
	function checkAAll(falg){
		var _allInput = $('.js-news-input').find('input');
		var _ddBoxInput = $('.news-list dd').find('.news-input-box').find('input:checked');
		var _ddBoxInputAll = $('.news-list dd').find('.news-input-box').find('input');
		if(falg == 1){
			if(_ddBoxInput.length == _ddBoxInputAll.length){
				_allInput.removeAttr('checked');
				$('.news-list dd').find('.news-input-box').find('input').removeAttr('checked');
			}else{
				_allInput.attr('checked', true);
				$('.news-list dd').find('.news-input-box').find('input').attr("checked", true);
			}
		}else if(falg == 2){
			if(_ddBoxInput.length == _ddBoxInputAll.length){
				_allInput.attr('checked', true);
			}else{
				_allInput.removeAttr('checked');
			}
		}
	}
	
	$(function(){
		getMyMessage(1);
		onClickMessage();
		//checkAAll();
		deleteByPid();
		setReadAll();
		deleteByAll();
		$('.news-list dd').live('mouseover',function(){ 
			$(this).addClass('news-list-bg');
		});
		$('.news-list dd').live('mouseout',function(){ 
			$(this).removeClass('news-list-bg');
		});
	});
	
</script>
	<script>
		cmCreatePageviewTag("站内信", "D0001", null, null);
	</script>
</body>
</html>