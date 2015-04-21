/**
 * 
 */
package com.lvmama.back.sweb.prod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({@Result(name="input",location="/WEB-INF/pages/back/prod/searchViewContent.jsp")
	})
public class ViewContentUpdateAction extends ProductAction{

	private ComLogService comLogService;
	private ViewPageService viewPageService;
	
	private Long productId;
	private Long logId;
	boolean update=false;
	@Action("/prod/tmpUpdateViewContentAction")
	public void update(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("objectId", productId);
		map.put("objectType", "VIEW_PAGE");
		map.put("logType","editProductDescription");
		map.put("logId", logId);
		map.put("skipResults", 0);
		map.put("maxResults", 1);
		List<ComLog> list = comLogService.queryByMap(map);
		if(!list.isEmpty()){
			ComLog log = list.get(0);
			String content = log.getContent();
			ViewPage viewPage = viewPageService.getViewPage(productId);
			List<ViewContent> contents = viewPage.getContentList();
			System.out.println(content);
			System.out.println("-------------------------");
			try{
				for(ViewContent v:contents){
					if(Constant.VIEW_CONTENT_TYPE.FEATURES.name().equals(v.getContentType())){
						System.out.println(v.getContent());
						v.setContent(getData(content,"更新产品特色"));
						System.out.println("++++++++++++++++++++++++++++++");
						System.out.println(v.getContent());
						update =true;
					}
				}
			}catch(ArrayIndexOutOfBoundsException ex){
				
			}
			if(update){
				viewPageService.saveViewContent(viewPage,"System");	
				// 发送修改销售产品的消息
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(viewPage.getProductId()));
				//发送place变更消息
				productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(viewPage.getProductId()));
				//发送修改销售产品的通知ebk消息
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(product.getProductId()));
				removeProductCache(viewPage.getProductId());
			}
		}
	}
	
	private static String getData(final String content,String prefix){
		String tmp=prefix+";[原来值：";
		int pos = content.indexOf(tmp);
		int end = content.indexOf(", 新值：",pos);
		return content.substring(pos+tmp.length(),end);
	}

	@Override
	@Action("/prod/goUpdateViewContentAction")
	public String goEdit() {
		return "input";
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	public ViewPageService getViewPageService() {
		return viewPageService;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
	
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

}
