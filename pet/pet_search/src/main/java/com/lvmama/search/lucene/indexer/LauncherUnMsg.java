package com.lvmama.search.lucene.indexer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.search.lucene.index.IndexManager;

public class LauncherUnMsg {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext-pet-search-index-builer.xml"});
		BeanFactory factory = (BeanFactory) context;
		IndexManager indexManager= (IndexManager) factory.getBean("indexManager");
		indexManager.createIndex(LUCENE_INDEX_TYPE.ALL);
		System.exit(0);
	}
}
