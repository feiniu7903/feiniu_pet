/**
 * 
 */
package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.utils.json.ResultHandle;

/**
 * @author yangbin
 *
 */
public abstract class ProdViewPageBaseAction extends ProductAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8495146533544663552L;
	protected ViewPageService viewPageService;

	/**
	 * @param viewPageService the viewPageService to set
	 */
	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}
	
	/**
	 * 判断viewPage的是否存在.并发送消息
	 * @param productId
	 */
	protected void checkViewPage(final Long productId){
		//对viewpage操作，让其有效或新建
		if(!viewPageService.isProductUsed(productId)){
			if(viewPageService.isProductUnUsed(productId)){
				viewPageService.updateValidByProductId(productId);
				sendUpdateProductMsg(productId);
			}else{
				ViewPage vp=new ViewPage();
				vp.setProductId(productId);
				ResultHandle handle = viewPageService.addViewPage(vp);
				if(handle.isFail()){
					throw new IllegalArgumentException(handle.getMsg());
				}
				try{
					productMessageProducer.sendMsg(MessageFactory.newProductCreateMessage(productId));
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}	
	}
}
