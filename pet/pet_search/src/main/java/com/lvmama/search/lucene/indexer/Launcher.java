package com.lvmama.search.lucene.indexer;

import java.io.IOException;
import java.io.StringReader;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.index.IndexManager;

public class Launcher {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext-pet-search-index-builer.xml"});
		BeanFactory factory = (BeanFactory) context;
		TopicMessageProducer resourceMessageProducer = (TopicMessageProducer)factory.getBean("resourceMessageProducer");
		IndexManager indexManager= (IndexManager) factory.getBean("indexManager");
		LUCENE_INDEX_TYPE type = null;
		if(args.length == 0 ){
			type = LUCENE_INDEX_TYPE.ALL;
		}if(args.length == 1){
			type = LUCENE_INDEX_TYPE.valueOf(args[0]);
		}

			try {
//				indexManager.createIndex(LUCENE_INDEX_TYPE.VER_PLACE);\
				//type = LUCENE_INDEX_TYPE.VER_HOTEL;
				indexManager.createIndex(type);

				resourceMessageProducer.sendMsg(MessageFactory.newLuceneCreateMessage(type));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		ikSegmenter("上海外滩");
		System.exit(0); 
	}
	
	public static String ikSegmenter(String str) {
		StringReader reader = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(reader, true);// 后一个变量决定是否消歧
		String result = "";
		Lexeme lexeme = null;
		try {
			while ((lexeme = ik.next()) != null) {
				result = result + lexeme.getLexemeText() + " ";
			}
			result = result+ " ";
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
