package com.lvmama.ebk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.service.ebooking.EbkProdContentService;
import com.lvmama.ebk.dao.EbkProdContentDAO;

public  class EbkProdContentServiceImpl implements EbkProdContentService {

	private EbkProdContentDAO ebkProdContentDAO;
    /**
     * 获取对象列表
     * @param ebkProdContentDO
     * @return 对象列表
     */
    public List<EbkProdContent> findListByTerm(EbkProdContent ebkProdContentDO) {
    	return ebkProdContentDAO.findListByTerm(ebkProdContentDO);
    }
	
    /**
     * 通过产品ID获取对象列表
     * @param productId
     * @return 对象列表
     */
    public List<EbkProdContent> findListByProductId(Long productId) {
    	return ebkProdContentDAO.findListByProductId(productId);
    }
    
    /**
     * 保存EBK产品特色及推荐，费用说明，其他条款等信息
     * @param ebkProdContents
     * @return 对象列表
     */
    public void saveEbkProdContentDAO(List<EbkProdContent> ebkProdContents) {
    	for(EbkProdContent content : ebkProdContents){
    		if(content.getContentId()==null){
    			EbkProdContent term=new EbkProdContent();
    			term.setProductId(content.getProductId());
    			term.setContentType(content.getContentType());
    			List<EbkProdContent> ebkProdContent=ebkProdContentDAO.findListByTerm(term);
    			if(null!=ebkProdContent&&ebkProdContent.size()>0){
    				ebkProdContentDAO.updateEbkProdContentDO(content);
    			}else{
    				ebkProdContentDAO.insertEbkProdContentDO(content);
    			}
    		}else{
    			ebkProdContentDAO.updateEbkProdContentDO(content);
    		}
    	}
    }
	
    /**
     * 保存EBK产品发车信息
     * @param ebkProdContents
     * @return 对象列表
     */
    public void saveEbkProdTrafficContentDAO(List<EbkProdContent> ebkProdContents,Long productId,String contentType) {
    	Map<String,Object> params = new HashMap<String,Object>();
		params.put("productId", productId);
		params.put("contentType", contentType);
		List<EbkProdContent> contents = ebkProdContentDAO.findEbkProdContentDOByIdAndType(params);
		if(ebkProdContents!=null&&ebkProdContents.size()>0){
			Map<Long,EbkProdContent> saveContent = new HashMap<Long,EbkProdContent>();
			for(EbkProdContent content : ebkProdContents){
				if(content!=null){
					content.setProductId(productId);
					content.setContentType(contentType);
					saveContent.put(content.getContentId(), content);
				}
			}
			for(EbkProdContent content : contents){
				if(!saveContent.containsKey(content.getContentId())){
		    		ebkProdContentDAO.deleteEbkProdContentDOByPrimaryKey(content.getContentId());
				}
	    	}
			for(EbkProdContent ebkProdContent : ebkProdContents){
				if(ebkProdContent!=null){
					if(ebkProdContent.getContentId()==null){
						ebkProdContentDAO.insertEbkProdContentDO(ebkProdContent);
					}else{
						ebkProdContentDAO.updateEbkProdContentDO(ebkProdContent);
					}
				}
	    	}
		}else{
			for(EbkProdContent content : contents){
	    		ebkProdContentDAO.deleteEbkProdContentDOByPrimaryKey(content.getContentId());
	    	}
		}
    }
    
    @Override
	public List<EbkProdContent> getEbkContentByMultiJourneyId(Long multiJourneyId, String contentType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("multiJourneyId", multiJourneyId);
		//params.put("contentType", contentType);
		return ebkProdContentDAO.findEbkProdContentDOByIdAndType(params);
	}
    
	public EbkProdContentDAO getEbkProdContentDAO() {
		return ebkProdContentDAO;
	}

	public void setEbkProdContentDAO(EbkProdContentDAO ebkProdContentDAO) {
		this.ebkProdContentDAO = ebkProdContentDAO;
	}

	@Override
	public void saveEbkMultiJourneyContentDAO(List<EbkProdContent> ebkProdContents) {
		for(int i = 0; i < ebkProdContents.size(); i++){
    		if(ebkProdContents.get(i).getContentId()==null){
    			ebkProdContentDAO.insertEbkProdContentDO(ebkProdContents.get(i));
    		}else{
    			ebkProdContentDAO.updateEbkProdContentDO(ebkProdContents.get(i));
    		}
    	}
		
	}

	@Override
	public List<EbkProdContent> findEbkProdContentDOByIdAndType(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return ebkProdContentDAO.findEbkProdContentDOByIdAndType(params);
	}
	

	@Override
	public void insertEbkProdContent(EbkProdContent ebkProdContent) {
		if(ebkProdContent.getMultiJourneyId() == null) {
			/*Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", ebkProdContent.getProductId());*/
			List<EbkProdContent> list = ebkProdContentDAO.findListByProductId(ebkProdContent.getProductId());
			if(list != null && list.size() > 0) {
				ebkProdContent.setMultiJourneyId(list.get(0).getMultiJourneyId());
			} else {
				EbkProdContent vmj = new EbkProdContent();
				vmj.setProductId(ebkProdContent.getProductId());
				Long multiJourneyId = ebkProdContentDAO.insertEbkProdContentDO(vmj);
				ebkProdContent.setMultiJourneyId(multiJourneyId);
			}
		}
		ebkProdContentDAO.insertEbkProdContentDO(ebkProdContent);
		
	}

	@Override
	public Long insert(EbkProdContent ebkProdContent) {
		return ebkProdContentDAO.insertEbkProdContentCopy(ebkProdContent);
	}
}
