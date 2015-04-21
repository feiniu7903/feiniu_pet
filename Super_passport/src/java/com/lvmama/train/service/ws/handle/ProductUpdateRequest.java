/**
 * 
 */
package com.lvmama.train.service.ws.handle;

import java.util.Date;

import com.lvmama.comm.bee.po.prod.ProdTrainFetchInfo;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.comm.vo.train.notify.ProductUpdateNotifyVo;

/**
 * 产品数据更新通知接口
 * @author yangbin
 */
public class ProductUpdateRequest extends AbstractNotifyRequest {

	private ProdTrainService prodTrainService;
	
	public ProductUpdateRequest() {
		prodTrainService = SpringBeanProxy.getBean(ProdTrainService.class, "prodTrainService");
	}
	
	@Override
	public Rsp handle(ReqVo vo) throws RuntimeException {
		if(!(vo instanceof ProductUpdateNotifyVo))
			throw new RuntimeException("未接受到正确的处理数据!");
		ProductUpdateNotifyVo pdnvo = (ProductUpdateNotifyVo)vo;
		
		Rsp rsp = check(pdnvo);
		if(rsp != null) 
			return rsp;
		
		ProdTrainFetchInfo info = new ProdTrainFetchInfo();
		info.setCreateTime(new Date());
		info.setVisitTime(getDate(pdnvo.getRequestDate()));
		info.setFetchCatalog(pdnvo.getRequestType());
		info.setFetchKey(pdnvo.getInterfaceId());
		info.setOperInfo(pdnvo.getOperInfo());
		prodTrainService.addFetch(info);
		return new Rsp(new BaseVo(Constant.REPLY_CODE.SUCCESS.getRetCode(), 
				Constant.REPLY_CODE.SUCCESS.getRetMsg()));
	}
	
	private Date getDate(String date){
		if(date.contains("T")){
			return DateUtil.stringToDate(date.substring(0,date.indexOf("T")),"yyyy-MM-dd");
		}
		return DateUtil.stringToDate(date,"yyyy-MM-dd");
	}

//	protected void parseBody(Element body) {
//		List<Element> list = body.element("UpdateItems").elements("UpdatedItemInfo");
//		for(Element ele:list){
//			handle(ele);
//		}
//		setSuccess(false);
//	}
//	
//	private void handle(Element ele){
//		String catalog = XmlUtils.getChildElementContent(ele, "Catalog");
//		String date = XmlUtils.getChildElementContent(ele, "DepartureDate");
//		String key = XmlUtils.getChildElementContent(ele, "Key");
//		StringBuffer sb = new StringBuffer();
//		sb.append(catalog);
//		sb.append(SPACE);
//		sb.append(getDate(date));
//		sb.append(SPACE);
//		sb.append(key);
//		ProdTrainFetchInfo info = new ProdTrainFetchInfo();
//		info.setCreateTime(new Date());
//		info.setVisitTime(getDate(date));
//		info.setFetchCatalog(catalog);
//		info.setFetchKey(key);
//		prodTrainService.addFetch(info);
//	}
//	
//	private final static String SPACE="======";

}
