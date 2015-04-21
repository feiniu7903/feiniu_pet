package com.lvmama.service.handle;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.passport.overseaschinatown.Base64;
import com.lvmama.passport.overseaschinatown.DesSecret;
import com.lvmama.passport.overseaschinatown.model.CheckTicketBuyBean;
import com.lvmama.passport.overseaschinatown.model.RequestBean;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.service.CheckStockHandle;

import freemarker.template.TemplateException;
/**
 * 华侨城同日订单数校验
 * @author TangJing
 *
 */
public class SHhuanleguOrderHandle implements CheckStockHandle{
	
	private static final Log log = LogFactory.getLog(SHhuanleguOrderHandle.class);
	private static String baseTemplateDir = "/com/lvmama/passport/overseaschinatown/template";
	private static String SUCCESS_CODE = "200";
	private MetaProductBranchService metaProductBranchService;
	
	@Override
	public List<Item> check(BuyInfo buyinfo,List<Item> list) {
		log.info("SHhuanleguOrderHandle check");
		for (Item item : list) {
			try {
				MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
				String visitDate = DateUtil.formatDate(item.getVisitTime(), "yyyy-MM-dd");
				String productIdSupplier = metaBranch.getProductIdSupplier();
				String quantity = String.valueOf(item.getQuantity());
				if(getContract(buyinfo)!=null && StringUtils.isNotBlank(getContract(buyinfo).getMobile())){
					String mobile=getContract(buyinfo).getMobile();
					String retailPrice = String.valueOf(item.getSettlementPrice()/100+20);
					log.info("checkTicketBuyParams : productIdSupplier="+productIdSupplier+"mobile="+ mobile+"quantity="+ quantity);
					CheckTicketBuyBean checkTicketBuyBean = new CheckTicketBuyBean();
					checkTicketBuyBean.setGoodsId(productIdSupplier);
					checkTicketBuyBean.setQuantity(quantity);
					checkTicketBuyBean.setRetailPrice(retailPrice);
					checkTicketBuyBean.setAppointTripDate(visitDate);
					checkTicketBuyBean.setMobile(mobile);
					// 剩余订购数量检测
					String checkTicketBuyContent = TemplateUtils.fillFileTemplate(baseTemplateDir, "checkTicketBuyReq.xml", checkTicketBuyBean);
					String secretKey = WebServiceConstant.getProperties("shhuanlegu.secretKey");
					String checkTicketBuyCiphertext = Base64.encoder(DesSecret.encrypt(checkTicketBuyContent, secretKey),"UTF-8");
					String checkTicketBuyXml = getRequestXml(checkTicketBuyContent, checkTicketBuyCiphertext);
					String checkTicketBuyUrl = WebServiceConstant.getProperties("shhuanlegu.url")+"checkGoodsBuy/";
					Map<String, String> checkTicketBuyRequestParas = new HashMap<String, String>();
					checkTicketBuyRequestParas.put("xmlContent", checkTicketBuyXml);
					String checkTicketBuyResult = HttpsUtil.requestPostForm(checkTicketBuyUrl, checkTicketBuyRequestParas);
					log.info("checkTicketBuyResult:   "+ checkTicketBuyResult);
					if (checkTicketBuyResult.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
						item.setStock(SupplierProductInfo.STOCK.LACK);
						item.setLackReason("预定异常");
						log.info("供应商返回异常：" + checkTicketBuyResult.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
					}else {
						String checkTicketRspCode = TemplateUtils.getElementValue(checkTicketBuyResult, "//Trade/Head/StatusCode");
						String rspDesc = TemplateUtils.getElementValue(checkTicketBuyResult, "//Trade/Body/Message");
						if(SUCCESS_CODE.equals(checkTicketRspCode)){
							String checkTicketRspBody = TemplateUtils.getElementValue(checkTicketBuyResult, "//Trade/Body");
							String checkTicketRspBodyXml = "<Body>"+DesSecret.decrypt(Base64.decoder(checkTicketRspBody,"UTF-8"), secretKey)+"</Body>";
							String  isAllowBuy = TemplateUtils.getElementValue(checkTicketRspBodyXml, "//Body/IsAllowBuy"); //1允许0不允许
							if("1".equals(isAllowBuy)){
								item.setStock(SupplierProductInfo.STOCK.AMPLE);
							}else{
								item.setStock(SupplierProductInfo.STOCK.LACK);
								item.setLackReason("预定异常:商品不满足购买条件，请认真核实商品信息");
							}
						}else{
							
							log.info("SHhuanleguOrderHandle message: " + rspDesc);
							item.setStock(SupplierProductInfo.STOCK.LACK);
							item.setLackReason("预定异常:"+rspDesc);
						}
					}
				}
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预定异常");
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private String getRequestXml(String bodyContent, String bodyCiphertext)
			throws NoSuchAlgorithmException, IOException, TemplateException {
		RequestBean request = new RequestBean();
		fillHeadBean(request , bodyContent);
		request.setBody(bodyCiphertext);
		return TemplateUtils.fillFileTemplate(baseTemplateDir, "reqeust.xml", request);
	}
	
	private void fillHeadBean(RequestBean head , String bodyContent) throws NoSuchAlgorithmException{
		Date now = new Date();
		String sequenceId =String.valueOf(now.getTime());
		String distributorId = WebServiceConstant.getProperties("shhuanlegu.distributorId");
		String sign = Base64.encoder(MD5.encode(sequenceId + distributorId + bodyContent.length()));
		head.setDistributorId(distributorId);
		head.setSequenceId(sequenceId);
		head.setSigned(sign);
		String timeStamp = DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss");
		head.setTimeStamp(timeStamp);
		head.setVersion("1");
	}
	/**
	 * 先取游玩人，如果游玩人不存在，再取联系人
	 * @param buyinfo
	 * @return
	 */
	private Person getContract(BuyInfo buyinfo) {
		Person traveller=null;
		Person contract=null;
		for(Person p:buyinfo.getPersonList()){
			if(StringUtils.equals(p.getPersonType(),Constant.ORD_PERSON_TYPE.TRAVELLER.name()) && traveller==null){
				traveller=p;
			}
			if(StringUtils.equals(p.getPersonType(),Constant.ORD_PERSON_TYPE.CONTACT.name())){
				contract=p;
			}
		}
		if (traveller!=null && StringUtils.isNotBlank(traveller.getMobile())) {
			return traveller;
		} else {
			return contract;
		}
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
}
