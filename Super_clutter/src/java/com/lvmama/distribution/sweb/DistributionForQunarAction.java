package com.lvmama.distribution.sweb;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.model.qunar.PriceBuilder;
import com.lvmama.distribution.model.qunar.ProductBuilder;
import com.lvmama.distribution.service.DistributionForQunarCommonService;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 去哪儿线路接口实现
 * 
 * @author Alex.zhang
 */
@ParentPackage("struts-default")
@Results({ @Result(name = "price", location = "//qunar/timePrice.do", type = "dispatcher") })
public class DistributionForQunarAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8088942998169164104L;
	private static Log log = LogFactory.getLog(DistributionForQunarAction.class);

	private DistributionForQunarCommonService distributionForQunarCommonService;
	private ProdProductBranchService prodProductBranchService;
	private String resourceId;
	private String takeoffdate;
	private String type;
	private String user;
	private String pass;
	private String sign;

	private static Long distributorInfoId = Long.valueOf(DistributionUtil.getPropertiesByKey("qunar.route.distributior.id"));
	private static String lvmamaUrlPrefix =DistributionUtil.getPropertiesByKey("qunar.route.distributior.lvmama.url.prefix");
	private static String stubUser = DistributionUtil.getPropertiesByKey("qunar.route.user");
	private static String stubPass = DistributionUtil.getPropertiesByKey("qunar.route.pass");
	private static final String signkey = DistributionUtil.getPropertiesByKey("qunar.route.signkey");
	private static final String slash = "|";
	
	
	public static void main(String[] args) {
		String resourceId = "1141837";
		String type = "";
		StringBuffer sb = new StringBuffer();
		sb.append(signkey).append(slash);
		sb.append(stubUser).append(slash);
		sb.append(stubPass).append(slash);
		sb.append(StringUtils.isNotEmpty(resourceId)?resourceId:"").append(slash);
		sb.append(StringUtils.isNotEmpty(type)?type:"");
		try {
			String lvmamaSign = MD5.encode(sb.toString());
			log.info("lvmama sign :"+lvmamaSign);
		} catch (NoSuchAlgorithmException e) {
			log.error("md5 error",e);
		}
	}
	
	private int signed() {
		try {
			if(StringUtils.isEmpty(sign)) return 9;//签名为空
			
			log.info("去哪儿线路存根，用户名：" + stubUser + ", 密码：" + stubPass);
			log.info("去哪儿线路请求，用户名：" + user + ", 密码：" + pass);
			log.info("去哪儿线路请求，签名：" +sign);
			
			if (StringUtils.isEmpty(user)) {return 1;} //分销商用户名为空
			if (StringUtils.isEmpty(pass)) {return 2;} //分销商密码为空

			String stubMd5 = MD5.encode(stubUser + stubPass);
			if (!MD5.encode(user + pass).equals(stubMd5)) {return 3;}//签名不对（用户名或密码不对）
		
			//md5(key+|+user+|+password+|+resourceid+|+type)
			StringBuffer sb = new StringBuffer();
			sb.append(signkey).append(slash);
			sb.append(stubUser).append(slash);
			sb.append(stubPass).append(slash);
			sb.append(StringUtils.isNotEmpty(resourceId)?resourceId:"").append(slash);
			sb.append(StringUtils.isNotEmpty(type)?type:"");
			
			String lvmamaSign = MD5.encode(sb.toString());
			log.info("去哪儿线路lvmama sign :"+lvmamaSign);
			
			if(!lvmamaSign.equals(sign)) return 10; //签名不正确
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private int paramsCheck(){
		if(StringUtils.isEmpty(resourceId)){
			return 6; //产品标识（ID）为空
		}
		Map<String,Object> params = new HashMap<String,Object>();
		try{
			Long.parseLong(resourceId);
		}catch(Exception e){
			return 7; //产品标识（ID）数据类型不正确，必须为数字
		}
		return 0;
	}
	
	private String error(int code) {
		return ("<error code = \""+code+"\"/>");
	}

	@Action("/qunar/productIndex")
	public void productIndex() {
		// 去哪儿签名谁失败
		int code = signed();
		if (code>0) {
			sendXmlResult(error(code));
			return;
		}
		List<ProdProductBranch> list = index();
		log.info("去哪儿线路数据查询，全部数据量：" + list.size());
		StringBuffer xml = new StringBuffer();
		xml.append("<products>");
		for (ProdProductBranch branch : list) {
			xml.append("<product>");
			xml.append(DistributionUtil.buildXmlElement("resourceId", branch.getProdProduct().getProductId()+"-"+branch.getProdBranchId()));
			xml.append(DistributionUtil.buildXmlElement("apiUrl", lvmamaUrlPrefix+"/clutter/qunar/productDetail.do?resourceId=" + branch.getProdBranchId()));
			xml.append("</product>");
		}
		xml.append("</products>");

		sendXmlResult(xml.toString());
		cleanParams();
	}

	@Action("/qunar/productDetail")
	public String productDetail() {
		if(StringUtils.equals(this.type, "price")){
			return "price";
		}
		// 去哪儿签名谁失败
		int code = signed();
		if (code>0) {
			sendXmlResult(error(code));
			return null;
		}
		code = paramsCheck();
		if (code>0) {
			sendXmlResult(error(code));
			return null;
		}
		
		ProductBuilder productBuilder = new ProductBuilder();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("distributorInfoId", distributorInfoId);
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE.getCode());
		params.put("branchId", Long.parseLong(resourceId));
		log.info("去哪儿线路，获取产品详情："+params.toString());
		
		List<DistributionProduct> distributionProductList = distributionForQunarCommonService.getDistributionProduct(params);
		DistributionProduct distributionProduct = distributionProductList != null && distributionProductList.size() > 0 ? distributionProductList.get(0)
				: new DistributionProduct();

		ProdProduct prodProduct = distributionProduct.getProdProduct();
		if(prodProduct==null){
			sendXmlResult(error(8));//产品不存在
			return null;
		}
		List<ProdProductBranch> branchList = prodProduct.getProdBranchList();
		for (ProdProductBranch prodProductBranch : branchList) {
			String productDetail = productBuilder.buildSigleProductDetail(distributionProduct, prodProduct, prodProductBranch);
			sendXmlResult(productDetail);
		}
		cleanParams();
		return null;
	}

	@Action("/qunar/timePrice")
	public void timePrice() {
		// 去哪儿签名谁失败
		int code = signed();
		if (code>0) {
			sendXmlResult(error(code));
			return;
		}
		code = paramsCheck();
		if (code>0) {
			sendXmlResult(error(code));
			return;
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("branchId", Long.parseLong(resourceId));
		params.put("distributorInfoId", distributorInfoId);
		
		Date specDate = null;
		if(StringUtils.isNotEmpty(takeoffdate)){
			specDate = DateUtil.stringToDate(takeoffdate, "yyyy-MM-dd");
			if(specDate==null){
				sendXmlResult(error(5));//日期格式化异常
				return;
			}
		}
		if(specDate!=null){
			 params.put("beginDate",specDate);
			 params.put("endDate", DateUtil.getDateAfterDays(specDate,1));
		}
		List<DistributionProduct> list = distributionForQunarCommonService.getDistributionProductTimePriceList(params);
		if(list==null || list.isEmpty()){
			sendXmlResult(error(8));//产品不存在
			return;
		}
		for(DistributionProduct product : list ){
			for(ProdProductBranch branch : product.getProdProduct().getProdBranchList()){
				//查询房间类别
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("branchType", Constant.ROUTE_BRANCH.FANGCHA.getCode());
				map.put("productId", product.getProdProduct().getProductId());
				map.put("additional", "true");
				map.put("visible", "true");
				map.put("online", "true");
				List<ProdProductBranch> fangchaBranchList = prodProductBranchService.selectByParam(map);
				String priceDetail = PriceBuilder.buildSiglePriceDetail(product,branch,fangchaBranchList);
				sendXmlResult(priceDetail);
			}
		}

		
		cleanParams();
	}
	
	private List<ProdProductBranch> index() {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("distributorInfoId", distributorInfoId);

		List<String> productTypeList = new ArrayList<String>();
		productTypeList.add(Constant.PRODUCT_TYPE.ROUTE.getCode());

		params.put("productTypeList", productTypeList);
		params.put("isAperiodic", "false");// 排除期票
		params.put("volid", "true");
		List<ProdProductBranch> list = distributionForQunarCommonService.getDistributionProductBranchList(params, "WHITE_LIST");
		return list;
	}

	private void cleanParams(){
		setResourceId(null);
		setType(null);
		setUser(null);
		setPass(null);
		setSign(null);
		setTakeoffdate(null);
		
	}
	
	public void setDistributionForQunarCommonService(DistributionForQunarCommonService distributionForQunarCommonService) {
		this.distributionForQunarCommonService = distributionForQunarCommonService;
	}

	
	public void setType(String type) {
		this.type = type;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setTakeoffdate(String takeoffdate) {
		this.takeoffdate = takeoffdate;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
}
