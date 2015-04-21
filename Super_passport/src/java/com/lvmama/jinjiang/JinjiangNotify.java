/**
 * 
 */
package com.lvmama.jinjiang;

import org.dom4j.Element;

/**
 * 处理一次请求
 * @author yangbin
 *
 */
public interface JinjiangNotify {

	/**
	 * 结果处理
	 * @param doc
	 * @return
	 */
	NotifyResult process(Element root);
	
}
