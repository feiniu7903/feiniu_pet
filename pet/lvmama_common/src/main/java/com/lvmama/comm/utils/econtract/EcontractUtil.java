package com.lvmama.comm.utils.econtract;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.XmlObjectUtil;
import com.lvmama.comm.utils.pdf.PdfUtil;
import com.lvmama.comm.vo.Constant;

public class EcontractUtil {
	private static final String PDF_FILE_SUFFIX = ".xml";
	private static final String IMAGE_FILE_SUFFIX=".png";
	public static final String PDF_DIRECT_RELATIVELY_URL = "/WEB-INF/resources/econtractTemplate/";// pdf模板相对路径 
	private static final String STAMP_DIRECT_IMG_URL = "http://pic.lvmama.com/img/order/stamp/";
	private static final Logger logger = Logger.getLogger(EcontractUtil.class);
	
	public static boolean isReplaceVal(String key){
		if(key.equals("alltouristInfo") || key.equals("complement") || 
				"allProductItem".equals(key) || "allTraveller".equals(key) || 
				"description".equals(key) || "description_1".equals(key) || 
				"travelExpensesDetail".equals(key)){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 创建 xhtml版本合同
	 * @param parameters
	 * @return
	 */
	public static String createContractHTML(final Map<String, Object> parameters){
		Object templateName = parameters.get("eContractTemplate");
		Object filialeName = parameters.get("filialeName");
		if (null == templateName) {
			logger.error("取得合同模板信息，找不合同模板名!");
			return null;
		}
		if (templateName instanceof String[]) {
			templateName = ((String[]) templateName)[0];
		}
		Iterator<String> iterator = parameters.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = parameters.get(key);
			if (value instanceof String[]) {
				value = ((String[]) value)[0];
			}
			if (null != value && isReplaceVal(key) ) {
				value = value.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\r\n", "<br/>").replaceAll("\n", "<br/>");
				parameters.put(key, value);
			}
		}
		String imgName = "SH_ECONTRACT";
		if("BJ_FILIALE".equals(filialeName)){
			imgName = "BJ_ECONTRACT";
		}else if("GZ_FILIALE".equals(filialeName)){
			imgName = "GZ_ECONTRACT";
		}else if("CD_FILIALE".equals(filialeName)){
			imgName = "SC_ECONTRACT";
		}
		String imgURL = STAMP_DIRECT_IMG_URL + imgName+IMAGE_FILE_SUFFIX;
		parameters.put("imageUrl", imgURL);
		String url =PDF_DIRECT_RELATIVELY_URL+ templateName + PDF_FILE_SUFFIX;
		String line = getTemplateContent(url);
		line = StringUtil.composeMessage(line,parameters);
		return line;
	}
	/**
	 * 获取电子合同文件路径
	 * @param order
	 * @return
	 */
	public static String getContractFilePath(final Long orderId) {
		String targetFilename = Constant.getInstance().getEContractDir() + "/Contract(" + orderId + ").pdf";
		return targetFilename;
	}
	/**
	 * 取得模板内容
	 * @param url
	 * @return
	 */
	public static String getTemplateContent(final String url){
		File file = ResourceUtil.getResourceFile(url);
		InputStream is=null;
		BufferedReader br=null;
		StringBuffer sb = new StringBuffer("");
		try {
			is=new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line = "";
			while (null != (line = br.readLine())) {
				sb.append(line);
				sb.append("\r\n");
			}
			br.close();
			is.close();
		}catch (IOException e) {
			logger.info("EcontractUtil IOException "+url+" :\r\n" + e);
		}finally{
			IOUtils.closeQuietly(br);	
			IOUtils.closeQuietly(is);	
		}
		return sb.toString();
	}
	
	/**
	 * 根据合同信息，订单信息生成合同编号
	 * @param ordContract
	 * @param order
	 * @return
	 */
	public static String initContractNo(final String contractNo,final Long orderId,final Date visitTime){
		if(null == contractNo){
			return DateUtil.getFormatDate(visitTime, "yyyyMMdd")+"-"+orderId+"-A";
		}else{
			if(contractNo.matches("^[0-9-]+[A-Z]$")){
				String begin = contractNo.replaceFirst("[A-Z]", "");
				String end = ""+((char)(contractNo.charAt(contractNo.length()-1)+1));
				return begin+end;
			}else{
				return DateUtil.getFormatDate(visitTime, "yyyyMMdd")+"-"+orderId+"-A";
			}
		}
	}
	
	/**
	 * 取得签约产品名称
	 * @param productName
	 * @return
	 */
	public String getContentProductName(String productName){
		productName = productName.replaceAll("[\\(（](成人|儿童).*[\\)）]", "");
		return productName;
	}
	
	/**
	 * 取一个可生成PDF的有数据的文件流
	 * @param order
	 * @return
	 */
	public static ByteArrayOutputStream createOutPutContractPdf(final String content){
		ByteArrayOutputStream baos=null;
		if(StringUtil.isEmptyString(content)){
			return new ByteArrayOutputStream();
		}
		baos=PdfUtil.createPdfFile(content);
		return baos;
	}
	
	public static ByteArrayInputStream createInPutContractPdf(final String content){
		ByteArrayOutputStream bos = createOutPutContractPdf(content);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		return bis;
	}
	/**
	 * 根据合同内容生成一个PDF文件
	 * @param order
	 * @param toUrl
	 */
	public static void createPdf(final String content,final String toUrl){
		if(StringUtils.isEmpty(content)){
			logger.error("没有找合同内容！不能生成PDF合同,查表 ORD_ECONTRACT 路径："+toUrl);
		}
		else{
			PdfUtil.createPdfFile(content, toUrl);
		}
	}
	
	public static String getContractComplementTemplate(final OrdOrder order){
		String template = "" ;
		//出境跟团游
		if(order.isGroupForeign()){
			template = "GROUP_FOREIGN_COMPLEMENT";
		//出境自由行
		}else if(order.isFreenessForeign()){
			template = "FREENESS_FOREIGN_COMPLEMENT";
		//国内跟团游
		}else if(Constant.SUB_PRODUCT_TYPE.GROUP.name().equalsIgnoreCase(order.getOrderType())||
				Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(order.getOrderType()) || 
				Constant.ORDER_TYPE.SELFHELP_BUS.name().equals(order.getOrderType())){
			template = "GROUP_COMPLEMENT";
		//国内自由行
		}else if(Constant.ORDER_TYPE.FREENESS.name().equals(order.getOrderType()) || Constant.ORDER_TYPE.FREENESS_LONG.name().equals(order.getOrderType())){
			template = "FREENESS_COMPLEMENT";
		}
		return template;
	}
	/**
	 * 根据模板名称取得合同模板内容
	 * @param templateName
	 * @return
	 */
	public  static String getContractConentTemplate(final String templateName){
		return memcachedTemplate("ORDER_CONTRACT_TEMPLATE_"+templateName,templateName);
	}
	/**
	 * 取得订单行程模板内容
	 * @return
	 */
	public  static String getOrderTravelTemplate(){
		return memcachedTemplate("ORDER_CONTRACT_TEMPLATE_TRAVEL_DESCRIPTION","TRAVEL_DESCRIPTION");
	}
	/**
	 * 取得订单行程模板内容(背景一日游)
	 * @return
	 */
	public  static String getOrderTravelTemplateBJONEDAY(){
		return memcachedTemplate("ORDER_CONTRACT_TEMPLATE_TRAVEL_DESCRIPTION","TRAVEL_DESCRIPTION_BJ_ONEDAY");
	}
	/**
	 * 取得订单行程子模板内容
	 * @return
	 */
	public static String getOrderTravelSubTemplate(){
		return memcachedTemplate("ORDER_CONTRACT_TEMPLATE_TRAVEL_DESCRIPTION_SUB","TRAVEL_DESCRIPTION_SUB");
	}
	/**
	 * 取得合同补充条款模板内容
	 * @param order
	 * @return
	 */
	public  static String getComplementTemplate(final OrdOrder order){
		return memcachedTemplate("ORDER_CONTRACT_TEMPLATE_"+getContractComplementTemplate(order),getContractComplementTemplate(order));
	}
	
	/**
	 * 根据模板名称取得合同模板内容
	 * @param templateName
	 * @return
	 */
	public  static String memcachedTemplate(final String key,final String templateName){
		String template =null;
		try{
			template = (String)MemcachedUtil.getInstance().get(key);
			template = null;
		}catch(Exception e){
			
		}
		if(StringUtil.isEmptyString(template)){
			template = getTemplateContent(PDF_DIRECT_RELATIVELY_URL+templateName+PDF_FILE_SUFFIX);
			MemcachedUtil.getInstance().set(key, 60*60*2, template);
		}
		return template;
	}
	
	public static String getContractContentAndComplement(final Map<String,Object> contractMap,final Map<String,Object> complementMap) throws Exception{
		 if(null!=complementMap){ 
			String complement = memcachedTemplate(complementMap.get("template").toString(), complementMap.get("template").toString());
			if (null != complement) {
				String[] array = complement.split("<body>");
				if (null != array && array.length > 1) {
					String[] suba = array[1].split("</body>");
					if (null != suba) {
						complement = suba[0].replace("txt-hetong buchongbox","");
					}
				}
			}
			complement = StringUtil.composeMessage(complement, complementMap);
			contractMap.put("complement", complement);
		}
		 String contentStr = createContractHTML(contractMap);
		 return contentStr;
	}
	public static String getContractContentAndComplement(final ComFile comFile,final String complementXml)throws Exception{
		String contractDataXml = new String(comFile.getFileData());
		Map<String,Object> contractDataMap =(Map<String,Object>) XmlObjectUtil.xml2Bean(contractDataXml, java.util.HashMap.class);
		Map<String,Object> complementDataMap =(Map<String,Object>) XmlObjectUtil.xml2Bean(complementXml, java.util.HashMap.class);
		return getContractContentAndComplement(contractDataMap,complementDataMap);
	}
	public static Map<String,Object> getComplementDataMap(final OrdOrder order,final ProdEContract prodContract){
		// 1.取供应商名称
		String supplierName = "";
		// 2.取补充条款
		if ("AGENCY".equals(prodContract.getGroupType())) {
			supplierName = "本订单订购产品由" + prodContract.getAgency() + "提供。";
		}
		// 创建LIST
		Map<String, Object> map = new HashMap<String, Object>();
		String template = getContractComplementTemplate(order);
		map.put("template", template);
		map.put("supplierName", supplierName);
		map.put("addition",
				null != prodContract.getComplement() ? prodContract
						.getComplement() : "");
		return map;
	}
	
	public static String getStampleUrl(final String templateName){
		return STAMP_DIRECT_IMG_URL + templateName+IMAGE_FILE_SUFFIX;
	}
	
	public static boolean isSimpleTemplate(final String templateName){
		if("SP_ECONTRACT".equals(templateName)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
}
