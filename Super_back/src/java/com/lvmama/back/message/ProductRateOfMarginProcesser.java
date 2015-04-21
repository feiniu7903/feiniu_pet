package com.lvmama.back.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProductRateOfMarginConfigVO;
import com.lvmama.comm.vo.ProductRateOfMarginTimePriceVO;
/**
 * 产品价格修改或上线等操作引起的毛利率计算
 * <br/>1，产品上线时：判定产品各类别是否上线，就上线的类别的时间价格表进行毛利率计算，不符合要求的发邮件。
 * <br/>2，类别上线时：判定产品是否上线，若产品已上线，则对该类别的时间价格表进行毛利率计算，不符合要求的发邮件。
 * <br/>3，修改类别的销售价时：判断当前修改的类别是否上线，若上线则判断产品是否上线，若产品已上线，则对修改类别当前时间（包含当前时间）之后的时间价格表进行毛利率计算，不符合要求的发邮件。
 * <br/>4，修改结算价时：查找采购产品关联的销售产品，查询销售产品中上线产品的上线类别，对其当前时间（包含当前时间）之后的时间价格表每天的价格进行毛利率计算，不符合要求的发邮件。
 * <br/>5，邮件发送：一旦产品/类别上线；修改销售价/结算价操作保存，则触发邮件。
 * @author ranlongfei
 */
public class ProductRateOfMarginProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(ProductRateOfMarginProcesser.class);

	protected ProdProductService prodProductService;

	private ProdProductBranchService prodProductBranchService;

	private MetaProductBranchService metaProductBranchService;
	
	private Map<String, ProductRateOfMarginConfigVO> rateConfigMap;

	private EmailClient emailClient;

	/**
	 * 用户接口
	 * */
	private PermUserService permUserService;
	
	
	private String from;
	private String subject = "毛利率预警";
	private String fromName = "后台系统";
	
	public void process(Message message) {
		readConfig();
		if(message.isProductOnoffMsg()) {//销售产品上线
			log.info("receive ProductOnoff message objectId: " + message.getObjectId() + " addtion:" + message.getAddition());
			processProductOnoff(message);
		} else if(message.isProductBranchOnoffMsg()) {//销售类别上线
			log.info("receive ProductBranchOnoff message objectId: " + message.getObjectId() + " addtion:" + message.getAddition());
			processProductBranchOnoff(message);
		} else if(message.isChangeSellPriceMsg()) {//修改销售类别驴妈妈价
			log.info("receive ChangeSellPrice message objectId: " + message.getObjectId() + " addtion:" + message.getAddition());
			processChangeSellPrice(message);
		} else if(message.isChangeMarketPriceMsg()) {//修改采购产品结算价
			log.info("receive ChangeMarketPrice message objectId: " + message.getObjectId() + " addtion:" + message.getAddition());
			processChangeMarketPrice(message);
		}
	}

	/**
	 * 修改采购产品结算价
	 * 
	 * @author: ranlongfei 2013-5-28 下午6:15:53
	 * @param message
	 */
	private void processChangeMarketPrice(Message message) {
		Long metaBranchId = message.getObjectId();
		List<MetaBranchRelateProdBranch> relateList = metaProductBranchService.selectProdProductAndProdBranchByMetaBranchId(metaBranchId);
		Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap = new HashMap<String, List<ProductRateOfMarginTimePriceVO>>();
		for(MetaBranchRelateProdBranch relate : relateList) {
			if("true".equals(relate.getProdBranchState())) {
				Long prodBranchId = relate.getProdBranchId();
				ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
				ProdProduct pp = prodProductService.getProdProduct(relate.getProdProductId());
				if(branch != null && pp != null && pp.isOnLine()) {
					if(!hasSendMailProductBranch(pp, branch)) {
						continue;
					}
					//产品与类别都上线
					Long prodProductId = relate.getProdProductId();
					String prodBranchName = relate.getProdBranchName();
					String manager = relate.getProdManagerName();
					
					fillProductRateEmail(emailMap, pp, prodProductId, prodBranchId, prodBranchName, manager);
				}
			}
		}
		sendEmail(emailMap);
	}

	/**
	 * 修改销售类别驴妈妈价
	 * 
	 * @author: ranlongfei 2013-5-28 下午6:16:04
	 * @param message
	 */
	private void processChangeSellPrice(Message message) {
		Long prodBranchId = message.getObjectId();
		ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		if(branch != null && branch.hasOnline()) {
			ProdProduct pp = prodProductService.getProdProduct(branch.getProductId());
			if(pp != null && pp.isOnLine()){
				if(!hasSendMailProductBranch(pp, branch)) {
					return;
				}
				Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap = new HashMap<String, List<ProductRateOfMarginTimePriceVO>>();
				//产品与类别都上线
				Long prodProductId = branch.getProductId();
				String prodBranchName = branch.getBranchName();
				PermUser user = permUserService.getPermUserByUserId(pp.getManagerId());
				String manager = user.getRealName();
				
				fillProductRateEmail(emailMap, pp, prodProductId, prodBranchId, prodBranchName, manager);
				sendEmail(emailMap);
			}
		}
	}

	/**
	 * 销售类别上线
	 * 
	 * @author: ranlongfei 2013-5-28 下午6:16:12
	 * @param message
	 */
	private void processProductBranchOnoff(Message message) {
		Long productId = message.getObjectId();
		Long prodBranchId = null;
		if(!StringUtil.isEmptyString(message.getAddition())){
			prodBranchId = Long.valueOf(message.getAddition());
		}
		if(prodBranchId == null) {
			processProductOnoff(message);
			return;
		}
		ProdProduct pp=prodProductService.getProdProduct(productId);
		if(pp != null && pp.isOnLine()){
			ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
			if(branch != null && branch.hasOnline()) {
				if(!hasSendMailProductBranch(pp, branch)) {
					return;
				}
				Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap = new HashMap<String, List<ProductRateOfMarginTimePriceVO>>();
				//产品与类别都上线
				Long prodProductId = branch.getProductId();
				String prodBranchName = branch.getBranchName();
				PermUser user = permUserService.getPermUserByUserId(pp.getManagerId());
				String manager = user.getRealName();
				
				fillProductRateEmail(emailMap, pp, prodProductId, prodBranchId, prodBranchName, manager);
				sendEmail(emailMap);
			}
		}
	}

	/**
	 * 销售产品上线
	 * 
	 * @author: ranlongfei 2013-5-28 下午6:16:25
	 * @param message
	 */
	private void processProductOnoff(Message message) {
		Long productId = message.getObjectId();
		ProdProduct pp=prodProductService.getProdProduct(productId);
		if(pp != null && pp.isOnLine()){
			Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap = new HashMap<String, List<ProductRateOfMarginTimePriceVO>>();
			//产品与类别都上线
			List<ProdProductBranch> branchList = prodProductService.getProductBranchByProductId(productId, null);
			for(ProdProductBranch branch : branchList) {
				if(!branch.hasOnline()) {
					continue;
				}
				if(!hasSendMailProductBranch(pp, branch)) {
					continue;
				}
				Long prodProductId = branch.getProductId();
				String prodBranchName = branch.getBranchName();
				PermUser user = permUserService.getPermUserByUserId(pp.getManagerId());
				String manager = user.getRealName();
				
				fillProductRateEmail(emailMap, pp, prodProductId, branch.getProdBranchId(), prodBranchName, manager);
			}
			sendEmail(emailMap);
		}
	}

	/**
	 * 产品类型是否要发邮件。
	 * 
	 * @author: ranlongfei 2013-6-5
	 * @param pp
	 * @param branch
	 * @return
	 */
	private boolean hasSendMailProductBranch(ProdProduct pp, ProdProductBranch branch){
		/**
		FREENESS("目的地自由行"),
		GROUP("短途跟团游"),
		FREENESS_FOREIGN("出境自由行"),
		GROUP_FOREIGN("出境跟团游"),
		GROUP_LONG("长途跟团游"),
		FREENESS_LONG("长途自由行"),
		SELFHELP_BUS("自助巴士班");
		 */
		/*if(pp.isRoute()) {
			//短途跟团游		房差
			//目的地自由行	房差
			//自助巴士班		房差
			if(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.name().equals(pp.getSubProductType())
					||Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP.name().equals(pp.getSubProductType())
					||Constant.ROUTE_SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(pp.getSubProductType())
					) {
				if (!Constant.ROUTE_BRANCH.FANGCHA.name().equals(branch.getBranchType())) {
					return true;
				}
				return false;
			}
			//出境自由行	儿童价/房差/自定义
			//出境跟团游	儿童价/房差/自定义”
			if (Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(pp.getSubProductType()) 
					|| Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(pp.getSubProductType())) {
				if (Constant.ROUTE_BRANCH.ADULT.name().equals(branch.getBranchType())) {
					return true;
				}
				return false;
			}
		}*/
		return true;
	}
	/**
	 * 创建Email
	 * @author: ranlongfei 2013-5-29
	 * @param emailMap
	 * @param pp
	 * @param prodProductId
	 * @param prodBranchId
	 * @param prodBranchName
	 * @param manager
	 * @return
	 */
	private void fillProductRateEmail(Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap, ProdProduct pp, Long prodProductId, Long prodBranchId,
			String prodBranchName, String manager) {
		Date beginDate = DateUtil.getDayStart(new Date());
		Date endDate = DateUtil.getDateAfterDays(beginDate, 365);
		String rateType = getProdProductRateType(pp);
		ProductRateOfMarginConfigVO rateConfig = findRateConfig(rateType);
		if(rateConfig == null) {
			return;
		}
		String overRateDate = calcOverRateDateContent(pp, rateConfig, prodProductId, prodBranchId, beginDate, endDate); 
		if(!"".equals(overRateDate)) {
			createEmail(emailMap, pp, rateConfig, overRateDate, prodBranchName, manager);
		}
		return;
	}
	/**
	 * 发邮件
	 * @author: ranlongfei 2013-5-29
	 * @param emailMap
	 */
	private void sendEmail(Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap) {
		if(emailMap != null) {
			for(String emailAddress : emailMap.keySet()) {
				String content = buildEmailContent(emailMap.get(emailAddress));
				sendProductRateOfMarginEmail(emailAddress, content);
			}
		}
	}

	/**
	 * 组装Email内容
	 * 
	 * @author: ranlongfei 2013-5-29
	 * @param list
	 * @return
	 */
	private String buildEmailContent(List<ProductRateOfMarginTimePriceVO> list) {
		if(list == null || list.isEmpty()) {
			return null;
		}
		StringBuilder content = new StringBuilder(100);
		content.append("<html>");
		content.append("<head>");
		content.append("<meta charset='utf-8'/>");
		content.append("Dear all:<br/><br/>");
		
		content.append("<table border='1' cellpadding='0'>");
		
		content.append("<tr><th>产品类型</th><th>销售ID</th><th width='20%'>产品名称</th><th>类别</th><th>产品经理</th><th>毛利率标准</th><th width='35%'>未达标时间</th></tr>");		
		for(ProductRateOfMarginTimePriceVO vo : list) {
			content.append("<tr>");
			content.append("<td>");
			content.append(vo.getSubProductType());
			content.append("</td>");
			content.append("<td>");
			content.append(vo.getProdProductId());
			content.append("</td>");
			content.append("<td>"); 
			content.append(vo.getProductName()); 
			content.append("</td>");
			content.append("<td>");
			content.append(vo.getProdBranchName());
			content.append("</td>");
			content.append("<td>");
			content.append(vo.getManager());
			content.append("</td>");
			content.append("<td>");
			content.append(vo.getStandardRate());
			content.append("%");
			content.append("</td>");
			content.append("<td>");
			content.append(vo.getOverRateDate());
			content.append("</td>");
			content.append("</tr>");
		}
		
		content.append("</table>");
		
		content.append("<br/>已上线，请知晓！<br/>");
		content.append("</body>");
		content.append("</html>");
		System.out.println(content);
		System.out.println("邮件已发送~！！");
		return content.toString();
	}
	/**
	 * 组装Email
	 * @author: ranlongfei 2013-5-29
	 * @param emailMap
	 * @param pp
	 * @param rateConfig
	 * @param overRateDate
	 * @param prodBranchName
	 * @param manager
	 */
	private void createEmail(Map<String, List<ProductRateOfMarginTimePriceVO>> emailMap, ProdProduct pp, ProductRateOfMarginConfigVO rateConfig, String overRateDate, String prodBranchName,
			String manager) {
		List<ProductRateOfMarginTimePriceVO> tpVoList = emailMap.get(rateConfig.getEmailAddress());
		if(tpVoList == null) {
			tpVoList = new ArrayList<ProductRateOfMarginTimePriceVO>();
		}
		ProductRateOfMarginTimePriceVO tpVo = new ProductRateOfMarginTimePriceVO();
		tpVo.setSubProductType(getProdProductRateName(pp));
		tpVo.setProdProductId(pp.getProductId());
		tpVo.setProdBranchName(prodBranchName);
		tpVo.setManager(manager);
		tpVo.setStandardRate(rateConfig.getRate());
		tpVo.setOverRateDate(overRateDate);
		tpVo.setProductName(pp.getProductName());
		tpVoList.add(tpVo);
		emailMap.put(rateConfig.getEmailAddress(), tpVoList);
	}

	/**
	 * 计算毛利是否不达标
	 * @author: ranlongfei 2013-5-29
	 * @param pp
	 * @param rateConfig
	 * @param prodProductId
	 * @param prodBranchId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private String calcOverRateDateContent(ProdProduct pp, ProductRateOfMarginConfigVO rateConfig, Long prodProductId, Long prodBranchId, Date beginDate, Date endDate) {
		String overRateDate = "";
		List<TimePrice> tpList = prodProductService.selectProdTimePriceByProductId(prodProductId, prodBranchId, beginDate.getTime(), endDate.getTime());
		for(TimePrice tp : tpList) {
			if(hasLessRate(tp,rateConfig)) {
				//发邮件的内容
				overRateDate += DateUtil.getFormatDate(tp.getSpecDate(), "MM月dd") + " ";
			}
		}
		return overRateDate;
	}
	/**
	 * 小于标准毛利 
	 * 
	 * @author: ranlongfei 2013-5-29
	 * @param tp
	 * @param rateConfig
	 * @return
	 */
	private boolean hasLessRate(TimePrice tp, ProductRateOfMarginConfigVO rateConfig) {
		if(tp != null && tp.getPriceType() != null) {
			return getRate(tp.getPrice(), tp.getSettlementPrice()) < rateConfig.getRate();
		}
		//毛利率=（驴妈妈价-结算价）/驴妈妈价
		return false;
	}

	/**
	 * 组装类型标识
	 * @author: ranlongfei 2013-5-29
	 * @param pp
	 * @return
	 */
	protected String getProdProductRateType(ProdProduct pp) {
		if(pp.isTicket()) {
			return pp.getFilialeName()+"_"+pp.getProductType()+"_"+pp.getIsForegin();
		} else if(pp.isHotel()) {
			return pp.getFilialeName()+"_"+pp.getProductType()+"_"+pp.getIsForegin();
		} else if(pp.isRoute()) {
			String rateType = pp.getSubProductType();
			if("Y".equals(pp.getIsForegin())) {
				rateType = pp.getRegionName();
			}
			return pp.getFilialeName()+"_"+rateType;
		}
		return null;
	}
	/**
	 * 组装类型标识对应的名字
	 * @author: ranlongfei 2013-5-29
	 * @param pp
	 * @return
	 */
	protected String getProdProductRateName(ProdProduct pp) {
		String foreign = "境内";
		if("Y".equals(pp.getIsForegin())) {
			foreign = "出境";
		}
		if(pp.isTicket()) {
			return pp.getZhFilialeName()+" "+pp.getZhProductType()+" "+foreign;
		} else if(pp.isHotel()) {
			return pp.getZhFilialeName()+" "+pp.getZhProductType()+" "+foreign;
		} else if(pp.isRoute()) {
			if("Y".equals(pp.getIsForegin())) {
				return pp.getZhFilialeName()+" "+pp.getZhSubProductType() + " " + Constant.REGION_NAMES.getCnName(pp.getRegionName());
			}
			return pp.getZhFilialeName()+" "+pp.getZhSubProductType();
		}
		return "";
	}
	/**
	 * 毛利率=（驴妈妈价-结算价）/驴妈妈价
	 * 2  1.5/2
	 *     3 
	 * 
	 * @author: ranlongfei 2013-5-29 上午11:53:50
	 * @param price
	 * @param settlementPrice
	 * @return
	 */
	private float getRate(Long price, Long settlementPrice) {
		if (price == null || price == 0) {
			return 0;
		}
		if (settlementPrice == null || settlementPrice == 0) {
			return 100;
		}
		return ((price - settlementPrice) * 100f)/price;
	}

	/**
	 * 发邮件
	 * 
	 * @author: ranlongfei 2013-5-29
	 * @param emailAddress
	 * @param content
	 */
	private void sendProductRateOfMarginEmail(String emailAddress, String content) {
		EmailContent email = new EmailContent();
		email.setFromAddress(from);
		email.setFromName(fromName);
		email.setSubject(subject);
		email.setToAddress(emailAddress);
		email.setContentText(content);
		email.setCreateTime(new java.util.Date());
		emailClient.sendEmailDirect(email);
	}
	/**
	 * 取出配置
	 * 
	 * @author: ranlongfei 2013-5-29
	 * @param subProductType
	 * @return
	 */
	private ProductRateOfMarginConfigVO findRateConfig(String subProductType) {
//		readConfig();
		if(StringUtil.isEmptyString(subProductType)) {
			return null;
		}
		if(this.rateConfigMap != null) {
			ProductRateOfMarginConfigVO vo = rateConfigMap.get(subProductType);
			if(vo == null || vo.getRate() == null || StringUtil.isEmptyString(vo.getEmailAddress())) {
				return null;
			}
			return vo;
		}
		return null;
	}
	/**
	 * 加载配置
	 * 
	 * @author: ranlongfei 2013-5-29 
	 */
	private void readConfig(){
		if(StringUtil.isEmptyString(from)){
			from = Configuration.getConfiguration().getPropertyValue("mail.properties", "mail.from");
		}
		if(rateConfigMap == null) {
			rateConfigMap = new HashMap<String, ProductRateOfMarginConfigVO>();
			Properties p = Configuration.getConfiguration().getConfig("productrateofmargin.properties");
			if(p != null) {
				for(Object keyObject : p.keySet()) {
					String key = keyObject.toString();
					
					String productType = key.split("\\.")[0];
					String type = key.split("\\.")[1];
					ProductRateOfMarginConfigVO config = rateConfigMap.get(productType);
					if(config == null) {
						config = new ProductRateOfMarginConfigVO();
						config.setSubProductType(productType);
						rateConfigMap.put(productType, config);
					}
					String value = p.getProperty(key).replace(" ", "");
					if(type.equals("email")) {
						config.setEmailAddress(value);
					} else if(type.equals("rate")) {
						config.setRate(Long.valueOf(value));
					} else {
						rateConfigMap.remove(productType);
					}
				}
			}
		}
	}
	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public ProdProductBranchService getProdProductBranchService() {
		return prodProductBranchService;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public MetaProductBranchService getMetaProductBranchService() {
		return metaProductBranchService;
	}

	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public EmailClient getEmailClient() {
		return emailClient;
	}

	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	
}
