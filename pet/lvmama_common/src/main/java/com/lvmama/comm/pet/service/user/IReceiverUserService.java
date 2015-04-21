package com.lvmama.comm.pet.service.user;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.UsrReceivers;

public interface IReceiverUserService {

	/**
	 * 查找相关联系人 
	 * @param userId
	 * @return
	 */
	List<UsrReceivers> loadUserReceiversByUserId(String userId);
	
	
	/**
	 * 根据相关联系人ID获得联系人对象
	 * @param receiverId
	 * @return
	 */
	UsrReceivers getUserReceiversByReceiverId(String receiverId);
	
	/**
	 * 保存联系人 
	 * @param list
	 * @param userId
	 * @param orderId
	 */
	void createContact(List<UsrReceivers> list, String userId);
	
	/**
	 * 逻辑删除联系人
	 * @param list
	 */
	void deleteContact(List<UsrReceivers> list);
	
	/**
	 * 获取分页数量
	 * @param userId
	 * @return
	 */
   Long loadReceiversByPageConfigCount(String userId);
   
   /**
    * 获取分页数据
    * @param beginRow
    * @param endRow
    * @param userId
    * @return
    */
   List<UsrReceivers> loadReceiversByPageConfig(long beginRow, long endRow,String userId,String receiversType);
   
   List<UsrReceivers> loadReceiversByPageConfig(long beginRow, long endRow,String userId);
   
   List<UsrReceivers> loadRecieverByParams(Map params);
   
   String insert(UsrReceivers record);
   
   UsrReceivers getRecieverByPk(String receiverId);
   
   void update(UsrReceivers record);
   
   //修改地址邮编用的
   void updatepostCode(UsrReceivers record);

   
   void delete(String receiverId);
}
