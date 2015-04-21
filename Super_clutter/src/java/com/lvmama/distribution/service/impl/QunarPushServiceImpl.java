package com.lvmama.distribution.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.distribution.util.DistributionUtil;


public class QunarPushServiceImpl implements DistributionPushService {
	private static Log log = LogFactory.getLog(DistributionForQunarCommonServiceImpl.class); 
	private static final String stubUser = DistributionUtil.getPropertiesByKey("qunar.route.user.lvmama");
	private static final String stubPass = DistributionUtil.getPropertiesByKey("qunar.route.pass.lvmama");
	
	@Override
	public void push(String partnerCode, String body, String signed, String eventType) {

		String offlineUrl = "";
		if(EVENT_TYPE.offLine.name().equals(eventType)){
			offlineUrl = DistributionUtil.getPropertiesByKey("qunar.route.offline.url");
		}else if(EVENT_TYPE.product.name().equals(eventType)){
			offlineUrl = DistributionUtil.getPropertiesByKey("qunar.route.changeProduct.url");
			offlineUrl = String.format(offlineUrl,"%s","%s","%s","product");
		}else if(EVENT_TYPE.price.name().equals(eventType)){
			offlineUrl = DistributionUtil.getPropertiesByKey("qunar.route.changeProduct.url");
			offlineUrl = String.format(offlineUrl,"%s","%s","%s","price");
		}
		
		try {
			Document document = XmlUtils.createDocument(body);
			Element root = document.getRootElement();
			if(root.getName().equals("branchId")){
				String branchId = root.getText();
				offlineUrl = String.format(offlineUrl, stubUser,stubPass,branchId.toString());
			}else{
				List<Node> nodes = root.selectNodes("/product/productBranch/branch/branchId");
				String productOnLine = root.selectSingleNode("/product/productOnLine").getText();
				for(Node node : nodes){
					String branchId = node.getText();
					if("false".equals(productOnLine)){
						offlineUrl = DistributionUtil.getPropertiesByKey("qunar.route.offline.url");
					}
					offlineUrl = String.format(offlineUrl, stubUser,stubPass,branchId.toString());
				}
			}
			
			log.info("qunaerRoute push Url : "+offlineUrl);
			String result=HttpsUtil.requestGet(offlineUrl);
			log.info("qunaerRoute push Result："+result);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	
	}

}
