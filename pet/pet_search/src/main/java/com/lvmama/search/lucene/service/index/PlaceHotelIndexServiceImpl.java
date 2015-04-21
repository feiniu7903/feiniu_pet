package com.lvmama.search.lucene.service.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.search.lucene.dao.IndexDAO;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.util.PageConfig;

@Service("placeHotelIndexService")
public class PlaceHotelIndexServiceImpl  implements IndexService{

	@Autowired
	private IndexDAO indexDao;
	
	@Override
	public List<Document> getDocument(String... placeId) {
		List<PlaceHotelBean> pList = indexDao.getPlaceHotel(placeId);
		List<Document> list = new ArrayList<Document>();
		//获取中值map
		Map<String, Float> map=getmid();
		AbstactDocument abstractDocument = new PlaceHotelDocument();
		for(PlaceBean pb : pList){
			list.add(abstractDocument.createDocument(pb));
		}
		return list;
	}

	@Override
	public PageConfig<Document> getDocumentPageConfig(PageConfig pageConfig) {
		//避免多次调用统计方法
				if(pageConfig.getTotalResultSize()<1){
					pageConfig.setTotalResultSize(indexDao.countPlaceHotelIndex());
				}
				//转换PlaceBean 为 document
				//获取中值map
				Map<String, Float> map=getmid();
				List<PlaceHotelBean> pList = indexDao.getPlaceHotelIndexData(pageConfig.getStartResult() + 1, pageConfig.getCurrentRowNum());
				pageConfig.setItems(null);
				List<Document> documentList = new ArrayList<Document>();
				AbstactDocument abstractDocument = new PlaceHotelDocument();
				for (int i = 0; i < pList.size(); i++) {
					PlaceHotelBean placeHotelBean = pList.get(i);
					if(placeHotelBean.getId().equals("154098")||placeHotelBean.getId().equals("153562")){
						System.out.println(111);
					}
					placeHotelBean =calculateMid(placeHotelBean,map);
					documentList.add(abstractDocument.createDocument(placeHotelBean));
				}
				pageConfig.setItems(documentList);
				return pageConfig; 
	}

	//计算公示
			private PlaceHotelBean calculateMid(PlaceHotelBean placeBean,Map<String, Float> map) {
				float realweeksale=placeBean.getRealWeekSales();
				float subTypeSale=placeBean.getSubTypeSale();
				if(subTypeSale==0l){
					placeBean.setSalePer(0F);
				}
				else {
					placeBean.setSalePer(realweeksale/subTypeSale);
				}
				String stage=placeBean.getStage();
				if(stage==null || stage.equals("0")){
					placeBean.setMidSalePer(0F);
					placeBean.setSalePer(0F);
				}
				else{
					Float midSalePer=map.get(stage);
					placeBean.setMidSalePer(midSalePer);
				}
				//计算标签数量
				String tagsName=placeBean.getDestTagsName();
				if(tagsName==null || tagsName.equals("") || placeBean.getSubTypeMaxTagNum()==0l){
					placeBean.setTagnum(0F);
					placeBean.setTagratio(0F);
				}
				else{
					placeBean.setTagratio(tagsName.split(",").length/placeBean.getSubTypeMaxTagNum());
				}
				return placeBean;
			}
	
	private Map getmid() {
		List<PlaceBean> midmunList=indexDao.getPlaceMidType(new HashMap());
		Map<String, Float> map=new HashMap<String, Float>();
		if(midmunList!=null && midmunList.size()>0){
			for(PlaceBean placeBean:midmunList){
				PlaceBean midplaceBean=indexDao.getMidPlaceBean(placeBean);
				if(midplaceBean!=null){
				if(midplaceBean.getStage()!=null){
					map.put(midplaceBean.getStage(), midplaceBean.getMidSalePer());
				}
				}
				
			}
		}
		return map;
		
	}
	
	public void setIndexDao(IndexDAO indexDao) {
		this.indexDao = indexDao;
	}

	
}
