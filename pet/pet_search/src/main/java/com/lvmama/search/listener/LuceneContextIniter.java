package com.lvmama.search.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.lvmama.search.lucene.LuceneContext;

public class LuceneContextIniter extends ContextLoaderListener implements
		ServletContextListener {
	
	private static Log log = LogFactory.getLog(LuceneContextIniter.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("=========begin init LuceneContext=========");
		// 初始化Lucene索引
		LuceneContext.init();
		log.info("=========init LuceneContext success=========");
	}
}
