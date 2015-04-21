package com.lvmama.search.lucene.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.search.lucene.index.IndexManager;

public class LuceneProcesser  implements MessageProcesser {

	protected transient final Log logger = LogFactory.getLog(getClass());
	
	private IndexManager indexManager;
	
	@Override
	public void process(Message message) {
		logger.info("LuceneProcesser revice message: " + message.toString());
		if (message.isLuceneIndexMsg()) {
			if(message.isLuceneIndexCreateMsg()){
				String type = message.getObjectType();
				indexManager.applyNewIndex(LUCENE_INDEX_TYPE.valueOf(type));
//				indexManager.createIndex(LUCENE_INDEX_TYPE.valueOf(type));
			}else if(message.isLuceneIndexUpdateMsg()){
				String[] ids  = message.getAddition().split(",");
				String type = message.getObjectType();
				indexManager.updateIndex(LUCENE_INDEX_TYPE.valueOf(type), ids);
			}
		}
	}
	
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}

}
