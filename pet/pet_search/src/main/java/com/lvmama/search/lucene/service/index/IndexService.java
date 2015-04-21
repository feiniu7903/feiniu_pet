package com.lvmama.search.lucene.service.index;

import java.util.List;

import org.apache.lucene.document.Document;

import com.lvmama.search.util.PageConfig;

public interface IndexService {
	
	public List<Document> getDocument(String... primarykey);
	
	/**
	 *  生成索引对象document
	 * 
	 * @param pageConfig 分页对象
	 * @return
	 */
	public PageConfig<Document> getDocumentPageConfig(PageConfig pageConfig);
	 
}
