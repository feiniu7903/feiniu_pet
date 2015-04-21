package com.lvmama.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.UsrReceiversDAO;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.vo.Constant;

public class ReceiverUserServiceImpl implements IReceiverUserService{
	
	private UsrReceiversDAO usrReceiversDAO;
	
	
	@Override
	public UsrReceivers getUserReceiversByReceiverId(String receiverId)
	{
		if(receiverId!=null){
			return usrReceiversDAO.getRecieverByPk(receiverId);
		} else {
			return  new UsrReceivers();
		}
	}
 
	@Override
	public void createContact(List<UsrReceivers> list, String userId) {
		for (UsrReceivers uri : list) {
			if (uri.getReceiverId() != null && !"".equals(uri.getReceiverId())) {
				UsrReceivers ur = usrReceiversDAO.getRecieverByPk(uri
						.getReceiverId());
				ur.setReceiverId(uri.getReceiverId());
				ur.setCardNum(uri.getCardNum());
				ur.setCardType(uri.getCardType());
				ur.setMobileNumber(uri.getMobileNumber());
				ur.setReceiverName(uri.getReceiverName());
				ur.setEmail(uri.getEmail());
				ur.setAddress(uri.getAddress());
				ur.setPostCode(uri.getPostCode());
				ur.setGender(uri.getGender());
				ur.setBrithday(uri.getBrithday());
				usrReceiversDAO.update(ur);
			} else {
				if ("true".equals(uri.getUseOffen())) {
					UUIDGenerator gen = new UUIDGenerator();
					if (uri.getReceiverName() != null
							&& !"".equals(uri.getReceiverName())) {
						uri.setCreatedDate(new Date());
						uri.setIsValid("Y");
						uri.setIsMobileChecked("N");
						uri.setUserId(userId);
						uri.setReceiverId(gen.generate().toString());
						usrReceiversDAO.insertSelective(uri);
					}
				}
			}
		}
	}
	 
	@Override
	public void deleteContact(List<UsrReceivers> list)
	{
		for (UsrReceivers uri : list) {
			if (uri.getReceiverId() != null && !"".equals(uri.getReceiverId())) {
				UsrReceivers ur = usrReceiversDAO.getRecieverByPk(uri
						.getReceiverId());
				ur.setReceiverId(ur.getReceiverId());
				ur.setIsValid("N");//逻辑删除
				usrReceiversDAO.update(ur);
			} 
		}
	}
	
	@Override
    public Long loadReceiversByPageConfigCount(String userId){
    	return (Long) usrReceiversDAO.loadReceiversByPageConfigCount(userId);
    }
    
	@Override
    public List<UsrReceivers> loadReceiversByPageConfig(long beginRow, long endRow,String userId,String receiversType)
    {
    	Map<String,Object> param = new HashMap<String,Object>();
	  	param.put("userId", userId);
	   	param.put("_startRow", (beginRow)+"");
	   	param.put("_endRow", endRow+"");
		param.put("receiversType", receiversType+"");
    	return usrReceiversDAO.loadReceiversByPageConfig(param);
    }
	
	
	@Override
    public List<UsrReceivers> loadReceiversByPageConfig(long beginRow, long endRow,String userId)
    {
    	Map<String,Object> param = new HashMap<String,Object>();
	  	param.put("userId", userId);
	   	param.put("_startRow", (beginRow)+"");
	   	param.put("_endRow", endRow+"");
    	return usrReceiversDAO.loadReceiversByPageConfig(param);
    }

	public void setUsrReceiversDAO(UsrReceiversDAO usrReceiversDAO) {
		this.usrReceiversDAO = usrReceiversDAO;
	}
	
	@Override
	public String insert(UsrReceivers record) {
		return usrReceiversDAO.insert(record);
	}
	@Override
	public UsrReceivers getRecieverByPk(String receiverId) {
		return usrReceiversDAO.getRecieverByPk(receiverId);
	}
	@Override
	public void update(UsrReceivers record) {
		 usrReceiversDAO.update(record);
	}
	
	/***
	 * 新修改地址
	 */
	@Override
	public void updatepostCode(UsrReceivers record) {
		 usrReceiversDAO.updatepostCode(record);
	}
	
	
	@Override
	public void delete(String receiverId) {
		usrReceiversDAO.deleteReciverById(receiverId);
	}

	@Override
	public List<UsrReceivers> loadRecieverByParams(Map params) {
		return usrReceiversDAO.loadRecieverByParams(params);
	}

	@Override
	public List<UsrReceivers> loadUserReceiversByUserId(String userId) {
		if(userId!=null){
			Map params = new HashMap();
			params.put("userId", userId);
			params.put("receiversType", Constant.RECEIVERS_TYPE.CONTACT.name());
			return usrReceiversDAO.loadRecieverByParams(params);
		} else {
			return  new ArrayList<UsrReceivers>();
		}	
	}





	
}
