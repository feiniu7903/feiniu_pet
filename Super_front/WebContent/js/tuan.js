function ajaxQueryJoinGroupUsers(pageSize,page,productId){
		$.ajax({type:"POST",async:true,url:conextPath+"/group/ajaxQueryJoinGroupUsers.do",data:{productId:productId,page:page,pageSize:pageSize}, dataType:"json", 
		success:function (data) {
			var totalRowCount=data.joinUsersMap.totalCount;
			var rowHTML = "";
			var resultList = data.joinUsersMap.resultList;
			if(data.joinUsersMap.succFlag=='N'){
				return;
			}
			var clazz="class=even-td";
			$("#joinUsersBody").empty();
			for(var i=0;i<resultList.length;i++){
				if(i%2==0){
					clazz=""
				}else{
					clazz="class=even-td"
				}
				rowHTML="<tr "+ clazz;
				rowHTML=rowHTML+"><td>"+resultList[i].USER_NAME+"</td>";
				rowHTML=rowHTML+"<td>"+resultList[i].PRODUCT_NAME+"</td>";
				rowHTML=rowHTML+"<td>￥"+resultList[i].PRICE+"</td>";
				rowHTML=rowHTML+"<td>"+resultList[i].QUANTITY+"</td>";
				rowHTML=rowHTML+"<td>"+resultList[i].CREATE_TIME+"</td>";
				rowHTML=rowHTML+"<td>"+"成交"+"</td>";
				rowHTML=rowHTML+"</tr>";
				$("#joinUsersBody").append(rowHTML);
			}
			reDrawPagingBar(page,totalRowCount,pageSize,productId);
		}		
		});
}
function reDrawPagingBar(currentPage,totalRows,pageSise,productId){
	var rowHTML =" <tr><td colspan='6' align='right'>"
	var totalPage = Math.ceil(totalRows/pageSise);
	if(totalPage>0){
		rowHTML=rowHTML+"第"+currentPage+"页&nbsp;";
		rowHTML=rowHTML+"共"+totalPage+"页&nbsp;";
		rowHTML=rowHTML+"共"+totalRows+"条&nbsp;";

	}
	
	if(currentPage>1){
	 	rowHTML=rowHTML+"<a href=javascript:ajaxQueryJoinGroupUsers("+pageSise+",1,"+productId+")>首页</a>";
	 	rowHTML=rowHTML+"<a href=javascript:ajaxQueryJoinGroupUsers("+pageSise+","+(currentPage-1)+","+productId+")>上一页</a>";
	 }
	 if(currentPage<totalPage){
	 	rowHTML=rowHTML+"<a href=javascript:ajaxQueryJoinGroupUsers("+pageSise+","+(currentPage+1)+","+productId+")>下一页</a>";
	 }
	 if(totalPage>1 && currentPage!=totalPage){
		rowHTML=rowHTML+"<a href=javascript:ajaxQueryJoinGroupUsers("+pageSise+","+totalPage+","+productId+")>末页</a>";
	}
     rowHTML=rowHTML+"</tr>";
     $("#joinUsersBody").append(rowHTML);
}
function ajaxQueryEnjoyPrdList(productId,limit){
		$("#maybe_lover").empty();
		$.ajax({type:"POST",async:true,url:conextPath+"/group/ajaxQueryEnjoyPrdList.do",data:{productId:productId,pageSize:limit}, dataType:"json", 
		success:function (data) {
			var rowHTML = "";
			var resultList = data.enjoyPrdList;

			$("#maybe_lover").empty();
			for(var i=0;i<resultList.length;i++){
				rowHTML=rowHTML+"<li><a href='/product/"+resultList[i].productId+"'> "+resultList[i].productName+" </a><span>&yen;"+resultList[i].sellPrice/100+"</span></li>";
			}
			if(rowHTML!=""){
				$("#maybe_lover").append(rowHTML);	
			} else {
				$("#maybe_lover").parent().hide();
			}
		}		
		});
}
/**
*设置cookie
*secs 以秒为单位设置过期时间
*/
function setCookieKey(c_name,value,secs)
{	
		var exdate=new Date()
		exdate.setTime(exdate.getTime()+secs*1000)
		document.cookie=c_name+ "=" +escape(value)+";path=/;domain=.lvmama.com;expires="+exdate.toGMTString();
}
function getCookieByKey(c_name)
{
	if (document.cookie.length>0)
	  {
	  c_start=document.cookie.indexOf(c_name + "=")
	  if (c_start!=-1)
	    { 
	    c_start=c_start + c_name.length+1 
	    c_end=document.cookie.indexOf(";",c_start)
	    if (c_end==-1) c_end=document.cookie.length
	    return unescape(document.cookie.substring(c_start,c_end))
	    } 
	  }
	return ""
}

/**
*seed 时间种子
*cookieKey 记录提交标记
*
*/
function canSubmitDream(cookieKey,seed){
	
	
	var currentDate = new Date();
  	//种子小于0则不限制提交频率
  	if(seed<=0){
  		return true;
  	}
  	var submitFlag = getCookieByKey(cookieKey)
  	if(submitFlag==null || typeof(submitFlag)=='undefined' || submitFlag==""){
  			
  			setCookieKey(cookieKey,new Date().getTime(),3600*24);
  			return true;
  	}else{
  			var interaval = (currentDate.getTime()-submitFlag)/1000;
  			if(interaval>=seed){
  				setCookieKey(cookieKey,new Date().getTime(),3600*24);
  				return true;
  			}else{
  				return false;
  			}
  	}	
}

 /**
 **
 *图片轮播
 * class scrollNum 对象ID
 * class scroll Img 对象ID
 * imgCount 图片个数
 * interval 团片自动轮播频率
 *
 */
function ScrollImage(scrollNumId,scrollImgId,imgCount,interval)  
{  
		if(imgCount<=1){
			return;
		}
		var xx=0;
		var e = function () {
				$("#"+scrollImgId+" li").eq(xx).fadeIn(600).siblings().hide();
				$("#"+scrollNumId+" li").eq(xx).addClass("curNumLI").siblings().removeClass("curNumLI");
				xx+=1;
				if(xx>imgCount) xx=0;
		};
		
		$("#"+scrollNumId+" li").mouseover(function(num){
				var num = $(this).index();
				$("#"+scrollImgId+" li").eq(num).fadeIn(600).siblings().hide();
				$(this).addClass("curNumLI").siblings().removeClass("curNumLI");
		});
		window.setInterval(e,interval);
}

function outSinaWeibo( shareUrl,shareTitle ){
	var _w = 86 , _h = 16; 
	var param = { 
	url:shareUrl, type:'6', count:'', /**是否显示分享数，1显示(可选)*/ 
	appkey:'87692682', /**您申请的应用appkey,显示分享来源(可选)*/
	title:shareTitle, /**分享的文字内容(可选，默认为所在页面的title)*/ 
	pic:'', /**分享图片的路径(可选)*/ 
	ralateUid:'', /**关联用户的UID，分享微博会@该用户(可选)*/ 
	rnd:new Date().valueOf() 
	} 
	var temp = []; 
	for( var p in param ){ 
	temp.push(p + '=' + encodeURIComponent( param[p] || '' ) ) 
	}		 
	document.write('<iframe allowTransparency="true" frameborder="0" scrolling="no" src="http://hits.sinajs.cn/A1/weiboshare.html?' + temp.join('&') + '" width="'+ _w+'" height="'+_h+'"></iframe>')
}
function shareDouban(shareUrl,shareTitle){
var d=document,e=encodeURIComponent,s1=window.getSelection,s2=d.getSelection,s3=d.selection,s=s1?s1():s2?s2():s3?s3.createRange().text:'',r='http://www.douban.com/recommend/?url='+e(shareUrl)+'&amp;title='+e(shareTitle)+'&amp;sel='+e(s)+'&amp;v=1',x=function(){if(!window.open(r,'douban','toolbar=0,resizable=1,scrollbars=yes,status=1,width=450,height=330'))location.href=r+'&amp;r=1'};if(/Firefox/.test(navigator.userAgent)){setTimeout(x,0)}else{x()}
}
function shareKaixin(shareUrl,shareTitle){
d=document;t=d.selection?(d.selection.type!='None'?d.selection.createRange().text:''):(d.getSelection?d.getSelection():'');void(kaixin=window.open('http://www.kaixin001.com/~repaste/repaste.php?&amp;rurl='+escape(shareUrl)+'&amp;rtitle='+escape(shareTitle)+'&amp;rcontent='+escape(shareTitle),'kaixin'));kaixin.focus();
}
function shareRenRen(shareUrl,shareTitle){
	var s=screen;var d = document;var e = encodeURIComponent;if(/xiaonei\.com/.test(d.location))return;var f='http://share.xiaonei.com/share/buttonshare.do?link=',u=shareUrl,l=shareTitle,p=[e(u),'&amp;title=',e(l)].join('');function a(){if(!window.open([f,p].join(''),'xnshare',['toolbar=0,status=0,resizable=1,width=626,height=436,left=',(s.width-626)/2,',top=',(s.height-436)/2].join('')))document.location.href=[f,p].join('');};if(/Firefox/.test(navigator.userAgent))setTimeout(a,0);else a();
}

