/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.vo.cert.CertificateItemVo;

/**
 * @author yangbin
 *
 */
public class EbkCertSingleRoomCompositeBuilder extends EbkCertCompositeBuilder{

	public EbkCertSingleRoomCompositeBuilder(EbkCertificate newEbkCertBuilder) {
		super(newEbkCertBuilder);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleCertificateItem(Map<String, Object> map,
			Map<String, Object> newMap) {
		List<CertificateItemVo> list = (List<CertificateItemVo>)newMap.get("items");
		List<CertificateItemVo> oldList = (List<CertificateItemVo>)map.get("items");
		Set<CertificateItemVo> set = new HashSet<CertificateItemVo>();
		for(CertificateItemVo item:list){
			Map<String,Object> baseInfo = item.getBaseInfo();
			CertificateItemVo oldItem = getOldItem(oldList, item);
			if(oldItem!=null){
				String content = handleMap(oldItem.getBaseInfo(), baseInfo,"nights");				
				if(StringUtils.isNotEmpty(content)){
					log.append(baseInfo.get("metaBranchName"));
					log.append(content);
				}
				List<Map<String,Object>> nigths = (List<Map<String,Object>>)baseInfo.get("nights");
				List<Map<String,Object>> oldNights = (List<Map<String,Object>>)oldItem.getBaseInfo().get("nights");
				for(Map<String,Object> n:nigths){
					Map<String,Object> oldN = getOldNight(oldNights,n);
					if(oldN!=null){
						String str=handleMap(oldN, n);
						if(StringUtils.isNotEmpty(str)){
							log.append(str);
						}
						oldNights.remove(oldN);
					}else{
						log.append("新增了");
						log.append(n.get("visitTime"));
					}
					
				}
				if(oldNights!=null&&!oldNights.isEmpty()){
					for(Map<String,Object> n:oldNights){
						log.append("删除游玩日期");
						log.append(n.get("visitTime"));
					}
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

	private Map<String,Object> getOldNight(List<Map<String,Object>> list,final Map<String,Object> map){
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return (Map<String,Object>)CollectionUtils.find(list, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				Map<String,Object> obj = (Map<String,Object>)arg0;
				String visitTime = (String)obj.get("visitTime");
				return StringUtils.equals((String)map.get("visitTime"),visitTime);
			}
		});
	}
}
