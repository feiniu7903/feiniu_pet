/**
 * 
 */
package com.lvmama.pet.sweb;

/**
 * 操作一个实体操作
 * @author yangbin
 *
 */
public interface EditAction {
	/**
	 * 进入添加的请求处理
	 * @return
	 */
	String toAdd();
	/**
	 * 进入编辑的请求处理
	 * @return
	 */
	String toEdit();
	
	/**
	 * 进入保存的请求处理
	 */
	void save();
}
