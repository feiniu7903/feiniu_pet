package com.lvmama.pet.user.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserActionCollection;



/**
 * 用户操作行为的数据库操作实现类
 *
 * @author Brian
 *
 */
public class UserActionCollectionDAO extends BaseIbatisDAO {

	/**
	 * 插入用户行为信息
	 * @param uac 用户行为信息
	 */
	public void save(final UserActionCollection uac) {
		super.insert("USER_ACTION_COLLECTION.insert", uac);
	}

}
