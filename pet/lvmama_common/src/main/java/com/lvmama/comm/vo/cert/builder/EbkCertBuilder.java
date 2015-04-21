/**
 * 
 */
package com.lvmama.comm.vo.cert.builder;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.CertificateItemVo;
import com.lvmama.comm.vo.cert.CertificateVo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 内容生成工具，
 * 负责把凭证内容生成最终的页面
 * @author yangbin
 *
 */
public abstract class EbkCertBuilder {
	
	private static Configuration configuration;
	
	
	public void init(){
		if(configuration==null){
		configuration  =new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setOutputEncoding("UTF-8");
		configuration.setNumberFormat("###");
		configuration.setClassicCompatible(true);
		configuration.setClassForTemplateLoading(EbkCertBuilder.class, "/cert");
		}
	}
	/**
	 * 提取模板
	 * @param productType 
	 * @return
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public String makeCertContent(final EbkCertificate certificate) throws IOException, TemplateException{
		StringWriter sw = new StringWriter();
		makeCertContent(certificate, sw);
		return sw.toString();
	}
		
	/**
	 * 子类的数据构造
	 * @param ebkCertificate
	 * @return
	 */
	protected abstract List<CertificateItemVo> getCertificateItemList(EbkCertificate ebkCertificate);
	
	
	/**
	 * 各子类的数据另外处理
	 * @param model
	 */
	protected void exposeHelpers(Map<String,Object> model){};
	
	
	public void makeCertContent(final EbkCertificate certificate,Writer writer) throws IOException, TemplateException{
		init();
		Template template = configuration.getTemplate(joinPath(certificate));
		Map<String,Object> rootMap=getCertContent(certificate);
		rootMap.put("createTime",new Date());		
		
		//********************add by shihui   此处修改为获取每次变更的备注栏信息发送给供应商,详见http://pms.lvmama.com/zentao/story-view-1005.html需求2
		//取得本次变更单的备注栏信息
		Object changeInfo = rootMap.get("changeInfo");
		String cInfo = "";//保存上一次变更单的备注栏信息和本次变更单备注栏信息的结合值
		if(changeInfo != null) {
			cInfo = (String)changeInfo + "<br/><br/>";
		}

		//取上一次变更单的备注栏信息
		EbkCertificateService ebkCertificateService = SpringBeanProxy.getBean(EbkCertificateService.class, "ebkCertificateService");
		if(certificate.getOldCertificateId() != null) {
			EbkCertificate ec = ebkCertificateService.selectByPrimaryKey(certificate.getOldCertificateId());
			if(ec != null) {
				cInfo = (StringUtils.isNotEmpty(ec.getChangeInfo()) ? ec.getChangeInfo() + "<br/><br/>" : "") + cInfo;

			}

		}

		EbkCertificate ebc = ebkCertificateService.selectByPrimaryKey(certificate.getEbkCertificateId());

		//当前变更单数据库存储的备注信息若和结合信息不一致,则将结合信息存储到当前变更单的备注信息中,方便以后再产生变更单获取信息
		if((StringUtils.isNotEmpty(cInfo) && StringUtils.isNotEmpty(ebc.getChangeInfo()) && cInfo.equalsIgnoreCase(ebc.getChangeInfo())) 
				|| (StringUtils.isEmpty(cInfo) && StringUtils.isEmpty(ebc.getChangeInfo()))) {
		} else {
			ebkCertificateService.updateChangeInfo(cInfo, certificate.getEbkCertificateId());
		}
		
		rootMap.put("changeInfo", cInfo);
		//*************************************************************************************
		
		exposeHelpers(rootMap);
		template.process(rootMap, writer);
	}
	
	/**
	 * 解析凭证信息
	 * @param certificate
	 * @return
	 */
	public Map<String,Object> getCertContent(final EbkCertificate certificate){
		Map<String,Object> rootMap=new HashMap<String, Object>();		
		rootMap.put("certificate", getCertificate(certificate));
		rootMap.put("items", getCertificateItemList(certificate));
		if(certificate!=null && certificate.getEbkTask()!=null) {
			rootMap.put("confirmUser", certificate.getEbkTask().getConfirmUser());
			rootMap.put("zhConfirmTime", certificate.getEbkTask().getZhConfirmTime());
		}
		return rootMap;
	}
	
	protected CertificateVo getCertificate(EbkCertificate certificate){
		CertificateVo vo = new CertificateVo();
		List<Map<String,Object>> ordPerson=new ArrayList<Map<String,Object>>();
		for(EbkOrderDataRev rev:certificate.getEbkOrderDataRevList()){
			if(rev.hasCertificate()){
				Map<String,Object> map = converJsonToMap(rev.getValue());
				vo.setBaseInfo(map);
			}else if(rev.hasPerson()){
				//vo.setTravellerList(converTravellerList(rev.getValue()));
				ordPerson.add(converJsonToMap(rev.getValue()));
			}
		}
		vo.setTravellerList(ordPerson);
		vo.setEntity(certificate);
		return vo;
	}
	
	/**
	 * 转换游玩人信息
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<Map<String,Object>> converTravellerList(String value){
		JSONArray array = JSONArray.fromObject(value);
		List<Map<String,Object>> travellerList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<array.size();i++){
			Map<String,Object> map = (Map<String,Object>)JSONObject.toBean(array.getJSONObject(i),getConfig());
			travellerList.add(map);
		}
		return travellerList;
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> converJsonToMap(String value){
		JSONObject obj = JSONObject.fromObject(value);
		return (Map<String,Object>)JSONObject.toBean(obj,getConfig());
	}
	@SuppressWarnings("rawtypes")
	protected JsonConfig getConfig(){
		JsonConfig jsonCfg = new JsonConfig();
        jsonCfg.setRootClass(Map.class);
		Map<String,Class> classMap = new HashMap<String, Class>();
        classMap.put(".*", Object.class);
        classMap.put("orderId", Long.class);
        jsonCfg.setClassMap(classMap);
        
        return jsonCfg;
	}
	
	/**
	 * 合成一个凭证模板的相对路径
	 * @param certificate
	 * @return confirm.html,cancel.html
	 */
	private String joinPath(final EbkCertificate certificate){
		StringBuffer sb=new StringBuffer();
		sb.append(certificate.getProductType());
		sb.append("/");
		
		if(certificate.isHotel()){
			sb.append(certificate.getSubProductType());
			sb.append("_");
		}
		if (Constant.EBK_CERTIFICATE_TYPE.CHANGE.name().equals(
				certificate.getEbkCertificateType())) {
			sb.append("change");
		} else if (Constant.EBK_CERTIFICATE_TYPE.CONFIRM.name().equals(
				certificate.getEbkCertificateType())
				|| Constant.EBK_CERTIFICATE_TYPE.ENQUIRY.name().equals(
						certificate.getEbkCertificateType())) {
			sb.append(Constant.EBK_CERTIFICATE_TYPE.CONFIRM.name());
		} else {
			sb.append(certificate.getEbkCertificateType());
		}
		sb.append(".html");
		return sb.toString().toLowerCase();
	}
	
	protected static class MapComparator implements Comparator{

		private String key;
		
		@SuppressWarnings("unchecked")
		@Override
		public int compare(Object arg0, Object arg1) {
			Map<String,Object> map0=(Map<String,Object>)arg0;
			Map<String,Object> map1=(Map<String,Object>)arg1;
			String a=map0.get(key).toString();
			String b=map1.get(key).toString();
			return a.compareTo(b);
		}

		public MapComparator(String key) {
			super();
			Assert.notNull(key);
			this.key = key;
		}
	}
}
