package com.lvmama.search.lucene.service.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.search.lucene.dao.IndexDAO;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.ProductBranchDocument;
import com.lvmama.search.util.PageConfig;

@Service("productBranchIndexService")
public class ProductBranchIndexServiceImpl implements IndexService{
	@Autowired
	private IndexDAO indexDao;
	
	//传入空页面，查询数据库DAO,得到document类的items列表页容器
	public PageConfig<Document> getDocumentPageConfig(PageConfig pageConfig) {
		//避免多次调用统计方法
		if(pageConfig.getTotalResultSize()<1){
			//初始化时TotalResultSize=0
			pageConfig.setTotalResultSize(indexDao.countProductBranchIndex());
		}
		//得到items中的bean列表
		List<ProductBranchBean> pList = indexDao.getProductBranchIndexDate(pageConfig.getStartResult() + 1, pageConfig.getCurrentRowNum());
		//清空items
		pageConfig.setItems(null);
		List<Document> documentList = new ArrayList<Document>();
		//实例化建立DOCUMENT的类ProductDocument
		AbstactDocument abstractDocument = new ProductBranchDocument();
		for (int i = 0; i < pList.size(); i++) {
			ProductBranchBean productBranchBean = pList.get(i);
			//调用ProductDocument类，传入一个PRUDUCTBEAN 建立一个DOCUMNET
			documentList.add(abstractDocument.createDocument(productBranchBean));
		}
		pageConfig.setItems(documentList);
		//得到document类的items列表
		return pageConfig; 
	}
	
	@Override
	public List<Document> getDocument(String... prodBranchId) {
		List<ProductBranchBean> pbList = indexDao.getProdBranch(prodBranchId);
		List<Document> documentList = new ArrayList<Document>();
		AbstactDocument abstractDocument = new ProductBranchDocument();
		for(ProductBranchBean pb : pbList){
			documentList.add(abstractDocument.createDocument(pb));
		}
		return documentList;
	}
	
	public void setIndexDao(IndexDAO indexDao) {
		this.indexDao = indexDao;
	}

}
