package com.lvmama.comm.pet.service.user;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.po.user.UserReceivers;

/**
 * @deprecated 还需要吗？
 * @author Brian
 *
 */
public interface UserReceviersService {
	String insert(UserReceivers record,String operatorName);
	void update(UserReceivers record,String operatorName);
	UserReceivers getRecieverByPk(String receiverId);
	/**
	 * 根据用户id和receiversType查询已存在的地址
	 * */
	List<UserReceivers> loadRecieverByParams(Map params);
	void delete(String receiverId);
	List<ConnRecord> queryFeedBackByParams(ConnRecord feedBack, int beignIndex, int endIndex);
	Long queryFeedBackCountByParams(ConnRecord feedBack);
	List<String> getFeedBackTypes();
	List<String> getFeedBackStateIds();
	void update(ConnRecord feedBack);
	ConnRecord getFeedBackByKey(String userFeedbackId);
}
