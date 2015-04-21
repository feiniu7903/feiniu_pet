package com.lvmama.search.lucene.service.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.dao.IndexDAO;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.util.PageConfig;

@Service("productIndexService")
public class ProductIndexServiceImpl implements IndexService{
	
	@Autowired
	private IndexDAO indexDao;
	
	@Override
	public List<Document> getDocument(String... productId) {
		List<ProductBean> pbList = indexDao.getProduct(productId);
		List<Document> documentList = new ArrayList<Document>();
		//获取中值map
				Map<String, Float> map=getmid();
		AbstactDocument abstractDocument = new ProductDocument();
		for(ProductBean pb : pbList){
			pb =calculateMid(pb,map);
			documentList.add(abstractDocument.createDocument(pb));
		}
		return documentList;
	}
	
	//传入空页面，查询数据库DAO,得到document类的items列表页容器
	public PageConfig<Document> getDocumentPageConfig(PageConfig pageConfig) {
		//避免多次调用统计方法
		if(pageConfig.getTotalResultSize()<1){
			//初始化时TotalResultSize=0
			pageConfig.setTotalResultSize(indexDao.countProductIndex());
		}
		//获取中值map
		Map<String, Float> map=getmid();
		
		//得到items中的bean列表
		List<ProductBean> pList = indexDao.getProductIndexDate(pageConfig.getStartResult() + 1, pageConfig.getCurrentRowNum());
		//清空items
		pageConfig.setItems(null);
		List<Document> documentList = new ArrayList<Document>();
		//实例化建立DOCUMENT的类ProductDocument
		AbstactDocument abstractDocument = new ProductDocument();
		for (int i = 0; i < pList.size(); i++) {
			ProductBean productBean = pList.get(i);
			productBean =calculateMid(productBean,map);
			//调用ProductDocument类，传入一个PRUDUCTBEAN 建立一个DOCUMNET
			documentList.add(abstractDocument.createDocument(productBean));
		}
		pageConfig.setItems(documentList);
		//得到document类的items列表
		return pageConfig; 
	}


	//计算公示
	private ProductBean calculateMid(ProductBean productBean,Map<String, Float> map) {
		float realweeksale=productBean.getRealWeekSales();
		float subTypeSale=productBean.getSubTypeSale();
		if(subTypeSale==0l){
			productBean.setSalePer(0F);
		}
		else {
			productBean.setSalePer(realweeksale/subTypeSale);
		}
		String subProductType=productBean.getSubProductType();
		if(subProductType==null){
			productBean.setMidSalePer(0F);
			productBean.setSalePer(0F);
		}
		else{
			float midSalePer=map.get(subProductType);
			productBean.setMidSalePer(midSalePer);
		}
		//计算标签数量
		String tagsName=productBean.getTagsName();
		if(tagsName==null || tagsName.equals("") || productBean.getSubTypeMaxTagNum()==0l){
			productBean.setTagnum(0F);
			productBean.setTagratio(0F);
		}
		else{
			productBean.setTagratio(tagsName.split(",").length/productBean.getSubTypeMaxTagNum());
		}
		//计算品牌数量
		String brandsName=productBean.getPlayBrand();
		if(brandsName==null || brandsName.equals("")|| productBean.getSubTypeMaxBrandNum()==0l){
			productBean.setBrandnum(0F);
			productBean.setBrandratio(0F);
		}
		else{
			productBean.setBrandratio(brandsName.split(",").length/productBean.getSubTypeMaxBrandNum());
		}
		return productBean;
	}

	public void setIndexDao(IndexDAO indexDao) {
		this.indexDao = indexDao;
	}
	
	private Map getmid() {
		List<ProductBean> midmunList=indexDao.getProductMidType(new HashMap());
		List<ProductBean> noSaleList=indexDao.getNoSaleProductType(new HashMap());
		Map<String, Float> map=new HashMap<String, Float>();
		if(midmunList!=null && midmunList.size()>0){
			for(ProductBean paraBean:midmunList){
				ProductBean midproductBean=indexDao.getMidproductBean(paraBean);
				if(midproductBean!=null){
				if(midproductBean.getSubProductType()!=null){
					map.put(midproductBean.getSubProductType(), midproductBean.getMidSalePer());
				}
				}
				//加入销量为0的类别,因为place的类别只有stage=1,2,3，所以不需要判断
			}
		}
		if(noSaleList!=null && noSaleList.size()>0 ){
			for(ProductBean paraBean:noSaleList){
				map.put(paraBean.getSubProductType(), 0f);
			}
		}
		return map;
		
	}
	

}
