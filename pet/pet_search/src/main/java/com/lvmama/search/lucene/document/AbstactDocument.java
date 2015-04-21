package com.lvmama.search.lucene.document;

import org.apache.lucene.document.Document;
/**
 * 索引document
 * @author yuzhibing
 *
 * @param <T>
 */
public abstract class AbstactDocument {
	/**
	 * 创建document
	 * @author yuzhibing
	 * @param o
	 * @return
	 */
	public abstract Document createDocument(Object o);
	/**
	 * 解析document
	 * @author yuzhibing
	 * @param o
	 * @return
	 */
	public abstract Object parseDocument(Document document);
}
