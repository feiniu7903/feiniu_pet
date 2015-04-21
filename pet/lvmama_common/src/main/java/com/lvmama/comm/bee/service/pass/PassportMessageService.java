/**
 * 
 */
package com.lvmama.comm.bee.service.pass;

import java.util.List;

import com.lvmama.comm.bee.po.pass.PassportMessage;

/**
 * @author yangbin
 *
 */
public interface PassportMessageService {

	/**
	 * 按主机参数拉取一个列表的数据
	 * @param hostname
	 * @param size
	 * @return
	 */
	public List<PassportMessage> selectList(final String hostname,List<String> processorList,int size);
	
	/**
	 * 删除一行记录
	 * @param PK
	 */
	public void deleteByPK(Long PK);
	
	/**
	 * 添加一个消息
	 * @param message
	 */
	public void add(PassportMessage message);
}
