package com.lvmama.search.lucene.service.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.search.lucene.dao.IndexDAO;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.util.PageConfig;

@Service("placeIndexService")
public class PlaceIndexServiceImpl implements IndexService{
	
	@Autowired
	private IndexDAO indexDao;
	
	@Override
	public List<Document> getDocument(String... placeId){
		List<PlaceBean> pList = indexDao.getPlace(placeId);
		List<Document> list = new ArrayList<Document>();
		//获取中值map
		Map<String, Float> map=getmid();
		AbstactDocument abstractDocument = new PlaceDocument();
		for(PlaceBean pb : pList){
			pb =calculateMid(pb,map);
			list.add(abstractDocument.createDocument(pb));
		}
		return list;
	}
	public PageConfig<Document> getDocumentPageConfig(PageConfig pageConfig) {
		//避免多次调用统计方法
		if(pageConfig.getTotalResultSize()<1){
			pageConfig.setTotalResultSize(indexDao.countPlaceIndex());
		}
		//获取中值map
				Map<String, Float> map=getmid();
		//转换PlaceBean 为 document
		List<PlaceBean> pList = indexDao.getPlaceIndexDate(pageConfig.getStartResult() + 1, pageConfig.getCurrentRowNum());
		pageConfig.setItems(null);
		List<Document> documentList = new ArrayList<Document>();
		AbstactDocument abstractDocument = new PlaceDocument();
		for (int i = 0; i < pList.size(); i++) {
			PlaceBean placeBean = pList.get(i);
			placeBean =calculateMid(placeBean,map);
			documentList.add(abstractDocument.createDocument(placeBean));			
		}
		pageConfig.setItems(documentList);
		return pageConfig; 
	}
	//计算公示
		private PlaceBean calculateMid(PlaceBean placeBean,Map<String, Float> map) {
			float realweeksale=placeBean.getRealWeekSales();
			float subTypeSale=placeBean.getSubTypeSale();
			if(subTypeSale==0l){
				placeBean.setSalePer(0F);
			}
			else {
				placeBean.setSalePer(realweeksale/subTypeSale);
			}
			String stage=placeBean.getStage();//1,2
			if(stage==null || stage.equals("0")){
				placeBean.setMidSalePer(0F);
				placeBean.setSalePer(0F);
			}
			else{
				Float midSalePerF = map.get(stage);
				if(midSalePerF!=null){
					float midSalePer=midSalePerF;//
					placeBean.setMidSalePer(midSalePer);
				}
				
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
