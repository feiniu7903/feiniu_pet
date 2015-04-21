package com.lvmama.comm.pet.service.conn;

import java.util.List;

import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.po.conn.ConnType;
import com.lvmama.comm.pet.vo.Page;


/**
 * 电话沟通记录
 * @author yuzhibing
 *
 */
public interface ConnRecordService {
	/**
	 * save
	 * @param connRecord
	 * @return
	 */
	Long saveConnRecord(ConnRecord connRecord);
	/**
	 * 
	 * @param connType
	 * @return
	 */
	List<ConnType> quueryConnTypeByConnType(ConnType connType);
	/**
	 * 
	 * @param mobile
	 * @param pageSize
	 * @param maxSize
	 * @return
	 */
	Page<ConnRecord> queryConnRecordPage(String mobile,Long pageSize,Long currentPage);
	Page<ConnRecord> queryConnRecordPage(Long userId,Long pageSize,Long currentPage);

	/**
	 * 查询layer=1的connType
	 * @return
	 */
	List<ConnType> queryConnTypeCallBack(String callBack);
	/**
	 * 多条件查询电话沟通记录
	 * 
	 * @author: ranlongfei 2012-8-9 下午5:37:42
	 * @param connRecord
	 * @param pageSize
	 * @param page
	 * @return
	 */
	Page<ConnRecord> queryConnRecordWithPage(ConnRecord connRecord, long pageSize, int page);
}
