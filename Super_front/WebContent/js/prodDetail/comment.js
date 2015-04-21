//		function rgREC(data) {
//
//		}
		
		
	   function getProductComments(elementId, json, company) {
	      var productIDs = "";
	      for (var i = 0 ; i < json.length ; i++) {
	           if (company == "uguide") {
	              productIDs += json[i].productId;
	              if(i < json.length-1)
	              {
	                productIDs += ",";
	              }
	           }
	      }
	      if(productIDs != "")//for protect
	      {
				$.ajax({
					type: "post",
					url: "http://www.lvmama.com/comment/ajax/getProductComments.do",
					dataType: "json",
					data: {
						productIDs : productIDs
					},
					success: function(json) {
						bindProductComments("c_reline_ct",json);	
					}
				});	    	  
	      }else{
				$("#c_shade").hide();
				$("#c_reline_ct").hide();
			}
		}
		
		function bindProductComments(elementId, json) {
		  
			var rs = "";
			var commentList = json.data;

			rs += "<h5 class='c_line_num'><strong>相关点评</strong></h5>";
			rs += "<div class='u_comment'>";
			for (var i = 0 ; i < commentList.length ; i++) {
			   
			    rs += "<dl>";
			    rs += "<dt><img src='"+commentList[i].userImg+"' width='76' height='76' /><span>"+commentList[i].userName+"</span></dt>";
			    rs += "<dd>";  
			    if(commentList[i].isBest == "Y")
			    {
			      rs += "<strong class='jing'>精</strong>";
			    }
			    
			    if(commentList[i].sumaryLatitude.score > 0)
			    {
			       rs += "<strong class='yan'>验</strong><span class='com_StarValueCon'><b>总体评价：</b><s class='star_bg cur_def'><i class='ct_Star"+commentList[i].sumaryLatitude.score+"'></i></s></span>";
			    
			    }else
			    {
			       rs += "<strong class='yan'>验</strong><span class='com_StarValueCon'><b>总体评价：</b><s class='star_bg cur_def'><i class='ct_Star0'></i></s></span>";
			    }
			    
			    if(commentList[i].cashRefundYuan !=null && commentList[i].cashRefundYuan > 0)
			    {
			    	rs += "<span class='re_jifen'>返：奖金<em>"+commentList[i].cashRefundYuan+"元</em>积分<em>"+commentList[i].point+"分</em></span>"; 
			    }
			    else
			    {
			    	rs += "<span class='re_jifen'>积分<em>"+commentList[i].point+"分</em></span>"; 
			    }
			      
                rs += "</dd>";        
                rs += "<dd class='c_score'>";
                
                if(commentList[i].cmtLatitudes != null)
                {
                   for (var j = 0 ; j < commentList[i].cmtLatitudes.length ; j++)
                   {
                     rs += "<span style='background:none;padding:0'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                     
                	/**   
                 	if(commentList[i].cmtLatitudes[j].latitudeName == "酒店")
                      {
                         rs += "<span class='c_hotel'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "景点")
                      {
                        rs += "<span class='c_spot'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "交通")
                      {
                        rs += "<span class='c_traffic'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "服务")
                      {
                        rs += "<span class='c_serve'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "舒适")
                      {
                        rs += "<span class='c_shushi'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "性价")
                      {
                        rs += "<span class='c_xingjia'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "卫生")
                      {
                        rs += "<span class='c_weisheng'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "位置")
                      {
                        rs += "<span class='c_weizhi'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "通关")
                      {
                        rs += "<span class='c_tongguan'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "体验")
                      {
                        rs += "<span class='c_tiyan'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      }
                      else if(commentList[i].cmtLatitudes[j].latitudeName == "人气")
                      {
                        rs += "<span class='c_renqi'>"+commentList[i].cmtLatitudes[j].latitudeName+"：<i>"+commentList[i].cmtLatitudes[j].score+"分</i></span>";
                      } 
                      **/
                	   
                   }
                }           
                rs += "</dd>";            
                rs += "<dd class='c_comctext'>"+commentList[i].content.substring(0,80)+"...<a href='/comment/"+commentList[i].commentId+"' target='_blank' class='c_w_more'>查看全文&gt;&gt;</a></dd>";            
             
                if(commentList[i].sellable == "true")
                {
                	rs += "<dd class='c_w_line  text-ell'><b>该点评出自于：</b><a target='_blank' href='http://www.lvmama.com/product/"+commentList[i].productId+"?source=correla' rel='nofollow'>"+commentList[i].productName+"</a></dd>";
                }
//                else if(commentList[i].sellable != "true")
//                {
//                	rs += "<dd class='c_w_line  text-ell'><b>该点评出自于：</b>"+commentList[i].productName+"<i>该产品已售完</i></dd>";
//                }
//                else if(commentList[i].productChannel != "FRONTEND")
//                {
//                	rs += "<dd class='c_w_line  text-ell'><b>该点评出自于：</b>"+commentList[i].productName+"<i>该产品已售完</i></dd>";
//                }
                else
                {
                	rs += "<dd class='c_w_line  text-ell'><b>该点评出自于：</b>"+commentList[i].productName+"<i>该产品已售完</i></dd>";
                }
                
                rs += "<dd class='c_reply'><a title='点击加心' class='h_xing' OnClick='javascript:return addUsefulCount("+commentList[i].shamUsefulCount+","+commentList[i].commentId+",this);'><i>（<big>"+commentList[i].shamUsefulCount+"</big>）</i></a><a href='/comment/"+commentList[i].commentId+"' class='h_fu'>回复<i>（<big>"+commentList[i].replyCount+"</big>）</i></a><a href='/comment/"+commentList[i].commentId+"' class='c_canyu'>驴妈妈参与回复<i>（<big>"+commentList[i].lvmamaReplyCount+"</big>）</i></a><small>"+commentList[i].formatterCreatedDate+"</small></dd>";
                rs += "</dl>";
			}
			
			rs += "</div>";
			rs += "<p class='c_seemore'><a target='_blank' href='http://www.lvmama.com/product/"+rg_item_id+"/comment'>查看全部点评</a></p>";
			 if (commentList.length > 0 && document.getElementById(elementId) != null) {
				document.getElementById(elementId).innerHTML = rs;
			}else{
				$("#c_shade").hide();
				$("#c_reline_ct").hide();
			}
		}
		
	
	
			/**
 * 点评“有用”
 * @param {Object} varCommentId
 * @param {Object} obj
 * @param {Object} count
 */
function addUsefulCount(varUsefulCount,varCommentId,obj) {
	var newUsefulCount = varUsefulCount + 1;
	$.ajax({
		type: "post",
		url: "http://www.lvmama.com/comment/ajax/addUsefulCountOfCmt.do",
		data:{
			commentId: varCommentId
		},
		dataType:"json",
		success: function(jsonList, textStatus){
			 if(!jsonList.result){
			   alert("已经点过一次");
			 }else{
			   obj.innerHTML= "<i>（<big>" + newUsefulCount + "</big>）</i>" ;
			 }
		}
	});
}	

			

