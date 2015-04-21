package com.lvmama.search.lucene.service.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.dao.IndexDAO;
import com.lvmama.search.lucene.dao.IndexVerDAO;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.document.VerhotelDocument;
import com.lvmama.search.util.PageConfig;

@Service("verHotelIndexService")
public class VerHotelIndexServiceImpl implements IndexService{
	
	
	@Autowired
	private IndexVerDAO indexVerDAO;
	
	@Override
	public List<Document> getDocument(String... productId) {
//		List<ProductBean> pbList = indexDao.getProduct(productId);
//		List<Document> documentList = new ArrayList<Document>();
//		AbstactDocument abstractDocument = new ProductDocument();
//		for(ProductBean pb : pbList){
//			documentList.add(abstractDocument.createDocument(pb));
//		}
//		return documentList;
		return null;
	}
	
	//传入空页面，查询数据库DAO,得到document类的items列表页容器
	public PageConfig<Document> getDocumentPageConfig(PageConfig pageConfig) {
		//避免多次调用统计方法
		if(pageConfig.getTotalResultSize()<1){
			//初始化时TotalResultSize=0
			pageConfig.setTotalResultSize(indexVerDAO.countVerHotelIndex());
		}
		
		//得到items中的bean列表
		List<VerHotelBean> pList = indexVerDAO.getVerhotelIndexDate(pageConfig.getStartResult() + 1, pageConfig.getCurrentRowNum());
		//清空items
		pageConfig.setItems(null);
		List<Document> documentList = new ArrayList<Document>();
		AbstactDocument abstractDocument = new VerhotelDocument();
		for (int i = 0; i < pList.size(); i++) {
			VerHotelBean verHotelBean = pList.get(i);
			verHotelBean =calculateMid(verHotelBean);
			documentList.add(abstractDocument.createDocument(verHotelBean));
		}
		pageConfig.setItems(documentList);
		//得到document类的items列表
		return pageConfig; 
	}



	private VerHotelBean calculateMid(VerHotelBean verHotelBean) {
		//排序公示 是否有可售商品 * 推荐级别 * (1/最低售价)
		double isHasSaleCommodity=0;
		double recommendlevel=0;
		double minhotelprice=verHotelBean.getMinproductsprice();
		double normalscore=0;
		if(StringUtil.isNotEmptyString(verHotelBean.getHassalecommodity())){
			//1是有可售
			if(verHotelBean.getHassalecommodity().equals("1")){
				isHasSaleCommodity=1;
			}
			
		}
		if(StringUtil.isNotEmptyString(verHotelBean.getRecommedlevel()) && verHotelBean.getRecommedlevel().matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")){
			recommendlevel=Double.parseDouble(verHotelBean.getRecommedlevel());
		}
		if(minhotelprice!=0){
			normalscore=isHasSaleCommodity+recommendlevel+(1/minhotelprice);
		}
		
		verHotelBean.setNormalscore(normalscore);
		
		return verHotelBean;
	}

	public void setIndexVerDAO(IndexVerDAO indexVerDAO) {
		this.indexVerDAO = indexVerDAO;
	}

	
}
