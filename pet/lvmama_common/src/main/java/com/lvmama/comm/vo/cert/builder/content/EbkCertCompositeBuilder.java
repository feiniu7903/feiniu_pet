/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.cert.CertificateItemVo;
import com.lvmama.comm.vo.cert.CertificateVo;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;

/**
 * @author yangbin
 *
 */
public class EbkCertCompositeBuilder extends EbkCertBuilder{

	private EbkCertificate oldEbkCertificate;
	private EbkCertificate newEbkCertificate;
	Map<String,Map<String,Object>> result=new HashMap<String, Map<String,Object>>();
	EbkCertificateService ebkCertificateService;
	private Map<String,String> keyvalue=new HashMap<String, String>();
	
	public EbkCertCompositeBuilder(EbkCertificate newEbkCertificate) {
		super();
		this.newEbkCertificate = newEbkCertificate;
		ebkCertificateService  = SpringBeanProxy.getBean(EbkCertificateService.class, "ebkCertificateService");
		this.oldEbkCertificate = ebkCertificateService
				.selectEbkCertificateDetailByPrimaryKeyAndValid(newEbkCertificate.getOldCertificateId());
		initKeyvalue();
	}

	private void initKeyvalue(){
		keyvalue.put("totalSettlementPrice", "结算总价");
		keyvalue.put("settlementPrice", "结算单价");
		keyvalue.put("metaProductName", "产品名");
		keyvalue.put("metaBranchName", "类别名");
		keyvalue.put("nights", "晚数");
		keyvalue.put("quantity", "数量");
		keyvalue.put("zhPaymentTarget", "收款方式");
		keyvalue.put("visitTime", "游玩日期");
		keyvalue.put("leaveTime", "离店日期");
		keyvalue.put("adultQuantity", "成人数");
		keyvalue.put("childQuantity", "成人数");
		keyvalue.put("mobile", "手机号");
		keyvalue.put("name", "姓名");
		keyvalue.put("pinyin", "拼音");
		keyvalue.put("certNo", "证件号码");
		keyvalue.put("zhCertType", "证件类型");
		keyvalue.put("faxMemo", "用户特殊要求");
		keyvalue.put("COSTCONTAIN", "费用包含");
		keyvalue.put("NOCOSTCONTAIN", "费用不包含");
	}


	@Override
	protected List<CertificateItemVo> getCertificateItemList(
			EbkCertificate ebkCertificate) {
		return null;
	}
	
	
	
	@Override
	public Map<String, Object> getCertContent(EbkCertificate certificate) {
		handle();
		Map<String,Object> rootMap=new HashMap<String, Object>();		
		rootMap.putAll(newMap);
		rootMap.put("changeInfo", log.toString());
		return rootMap;
	}

	private Map<String,Object> newMap;
	public void handle(){
		EbkCertBuilder builder = EbkCertBuilderFactory.create(oldEbkCertificate,false);
		Map<String,Object> map = builder.getCertContent(oldEbkCertificate);
		newMap = builder.getCertContent(newEbkCertificate);
		handleCertificate(map,newMap);
		handleCertificateItem(map, newMap);
	}
	
	@SuppressWarnings("unchecked")
	protected void handleCertificateItem(Map<String,Object> map,Map<String,Object> newMap){
		List<CertificateItemVo> list = (List<CertificateItemVo>)newMap.get("items");
		List<CertificateItemVo> oldList = (List<CertificateItemVo>)map.get("items");
		Set<CertificateItemVo> set = new HashSet<CertificateItemVo>();
		for(CertificateItemVo item:list){
			Map<String,Object> baseInfo = item.getBaseInfo();
			CertificateItemVo oldItem = getOldItem(oldList, item);
			if(oldItem!=null){
				String content = handleMap(oldItem.getBaseInfo(), baseInfo);
				if(StringUtils.isNotEmpty(content)){
					log.append(baseInfo.get("metaBranchName"));
					log.append(content);
				}
				set.add(oldItem);
			}
		}
		
		for(CertificateItemVo item:oldList){
			if(set.contains(item)){
				continue;
			}
			log.append(item.getBaseInfo().get("metaBranchName"));
			log.append("删除");
		}
	}
	
	
	protected CertificateItemVo getOldItem(List<CertificateItemVo> oldList,final CertificateItemVo newItem){
		final String key = (String)newItem.getBaseInfo().get("id");
		CertificateItemVo item = (CertificateItemVo)CollectionUtils.find(oldList,new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				CertificateItemVo old = (CertificateItemVo)arg0;
				String key1=(String)old.getBaseInfo().get("id");
				return StringUtils.equals(key, key1);
			}
		});
		return item;
	} 
	protected String handleMap(Map<String,Object> oldBaseInfo,Map<String,Object> baseInfo){
		return handleMap(oldBaseInfo,baseInfo,(String)null);
	}
	protected String handleMap(Map<String,Object> oldBaseInfo,Map<String,Object> baseInfo,String...skipKey){
		StringBuffer sb=new StringBuffer();
		try{
			Map<String,Object> changeMap = new HashMap<String, Object>();
			Set<String> keys = new HashSet<String>();
			for(Iterator<Map.Entry<String, Object> > it = baseInfo.entrySet().iterator(); it.hasNext();){
				Map.Entry<String, Object> entry=it.next();
				String key = entry.getKey(); 
				if(StringUtils.equals("id", key)){
					keys.add(key);
					continue;
				}
				if(skipKey!=null && ArrayUtils.contains(skipKey, key)){
					keys.add(key);
					continue;
				}
				Object obj = entry.getValue(); 
				
				//System.out.println("key::::"+key+"    "+obj);
				if(oldBaseInfo.containsKey(key)){
					//修改的判断
					Object oldObj = oldBaseInfo.get(key);
					if(obj == null){
						obj = "";
					}
					if(oldObj == null){
						oldObj = "";
					}
					if(!StringUtils.equals(obj.toString(), oldObj.toString())){//不相同的情况在新的当中标识出来
						//baseInfo.put(key+"_old", oldObj);
						changeMap.put(key+"_old", oldObj);
						sb.append(getZhLabel(key));
						sb.append("由");
						sb.append(oldObj);
						sb.append("更改为 ");
						sb.append(obj);
						sb.append(";");
					}
					keys.add(key);//去掉旧的数据
				}else{
					//新增的判断
					sb.append("新增");
					sb.append(obj);
					sb.append("; ");
				}
			}
			for(String key:keys){
				if(skipKey!=null && ArrayUtils.contains(skipKey, key)){
					continue;
				}
				oldBaseInfo.remove(key);
			}
			for(String key:oldBaseInfo.keySet()){
				if(keys.contains(key)){
					continue;
				}
				if(skipKey!=null && ArrayUtils.contains(skipKey, key)){
					continue;
				}
				Object obj = oldBaseInfo.get(key);
				sb.append("删除");
				sb.append(obj);
				sb.append(";");
			}
			if(!changeMap.isEmpty()){
				baseInfo.putAll(changeMap);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return sb.toString();
	}
	private String getZhLabel(String key){
		if(!keyvalue.containsKey(key)){
			return key;
		}
		return keyvalue.get(key);
	}
	
	private void handleCertificate(Map<String,Object> map,Map<String,Object> newMap){
		CertificateVo newCertificate = (CertificateVo)newMap.get("certificate");
		CertificateVo oldCertificate = (CertificateVo)map.get("certificate");
		Map<String,Object> baseInfo=newCertificate.getBaseInfo();
		Map<String,Object> oldBaseInfo = oldCertificate.getBaseInfo();
		String certBaseInfo = handleMap(oldBaseInfo, baseInfo,getSkipEbkCertificateKeysAll());
		if(StringUtils.isNotEmpty(certBaseInfo)){
			log.append(certBaseInfo);
		}
		if(!newCertificate.getEntity().isTicket()){
			List<Map<String,Object>> personList = newCertificate.getTravellerList();
			List<Map<String,Object>> oldPersonList = oldCertificate.getTravellerList();
			handleTraveller(oldPersonList, personList);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> getMap(List<Map<String,Object>> oldPersonList,Map<String,Object> newMap){
		final String key = (String)newMap.get("id");
		Map<String,Object> map = (Map<String,Object>)CollectionUtils.find(oldPersonList, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				Map<String,Object> old = (Map<String,Object>)arg0;
				String key1=(String)old.get("id");
				return StringUtils.equals(key, key1);
			}
		});
		return map;
	}
	
	protected void handleTraveller(List<Map<String,Object>> oldPersonList,List<Map<String,Object>> personList){		
//		List<Map<String,Object>> personList = newCert.getTravellerList();
//		List<Map<String,Object>> oldPersonList = oldCert.getTravellerList();
		if(CollectionUtils.isNotEmpty(personList)){
			for(Map<String,Object> map:personList){
				Map<String,Object> oldMap = getMap(oldPersonList, map);
				if(oldMap!=null){
					String  content=handleMap(oldMap, map);
					if(StringUtils.isNotEmpty(content)){
						log.append(content);
					}
				}else{
					log.append("新增游客");
					log.append(map.get("name"));
					log.append("; ");//添加分隔符
				}
			}
		}else if(CollectionUtils.isNotEmpty(oldPersonList)){
			log.append("删除了");
			for(Map<String,Object> map:oldPersonList){
				log.append("游客");
				log.append(map.get("name"));
				log.append(",");
			}
		}
	}
	
	private String[] getSkipEbkCertificateKeysAll(){
		List<String> array = new ArrayList<String>();
		array.add("showSettlementFlag");
		if(getSkipEbkCertificateKeys()!=null){
			array.addAll(Arrays.asList(getSkipEbkCertificateKeys()));
		}
		return array.toArray(new String[array.size()]);
	}
	
	protected String[] getSkipEbkCertificateKeys(){
		return null;
	}
	protected StringBuffer log= new StringBuffer();
}
