package com.lvmama.ebk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyPlace;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdJourneyService;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ebk.dao.EbkMultiJourneyDAO;
import com.lvmama.ebk.dao.EbkProdJourneyDAO;

public class EbkProdJourneyServiceImpl implements EbkProdJourneyService {

	private ComPictureService comPictureService;
	public EbkMultiJourneyDAO getEbkMultiJourneyDAO() {
		return ebkMultiJourneyDAO;
	}

	public void setEbkMultiJourneyDAO(EbkMultiJourneyDAO ebkMultiJourneyDAO) {
		this.ebkMultiJourneyDAO = ebkMultiJourneyDAO;
	}

	private EbkMultiJourneyDAO ebkMultiJourneyDAO;
	private EbkProdJourneyDAO ebkProdJourneyDAO;
	private ComLogDAO comLogDAO;
    /**
     * 获取对象列表
     * @param ebkProdContentDO
     * @return 对象列表
     */
    public List<EbkProdJourney> findListByTerm(EbkProdJourney ebkProdJourneyDO) {
    	return ebkProdJourneyDAO.findListByTerm(ebkProdJourneyDO);
    }
    
    /**
     * 获取对象列表--默认使用day_number排序
     * @param ebkProdJourneyDO
     * @return 对象列表
     */
    public List<EbkProdJourney> findListOrderDayNumberByDO(EbkProdJourney ebkProdJourneyDO) {
    	List<EbkProdJourney> jou = ebkProdJourneyDAO.findListOrderDayNumberByDO(ebkProdJourneyDO);
    	String traffic = "";
    	if(jou!=null&&jou.size()>0){
    		for(EbkProdJourney ebkProdJourney : jou){
    			traffic = ebkProdJourney.getTraffic();
    	    	if(StringUtils.isNotBlank(traffic)){
    	    		List<String> traffics = new ArrayList<String>();
    	    		String tra[] = traffic.split(",");
    	    		for(String tr : tra){
    	    			traffics.add(tr);
    	    		}
    	    		ebkProdJourney.setTraffics(traffics);
    	    	}
    	    	List<ComPicture> comPictures = comPictureService.getComPictureByObjectIdAndTypeOrderBySeqNum(ebkProdJourney.getJourneyId(),Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
    	    	ebkProdJourney.setComPictureJourneyList(comPictures);
    		}
    	}
    	
    	return jou;
    }
    
    /**
     * 批量新增行程
     * @param ebkProdJourneyDOs
     * @param productId
     * @param mustSaveDayNum
     * @return 
     */
    public void editEbkProdJourneys(List<EbkProdJourney> ebkProdJourneyDOs,Long productId,Long mustSaveDayNum){
    	EbkProdJourney selectDO = new  EbkProdJourney();
    	selectDO.setProductId(productId);
    	List<EbkProdJourney> results = findListByTerm(selectDO);
    	if(results==null||results.size()==0){
    		for(EbkProdJourney journey : ebkProdJourneyDOs){
    			if(journey.getDayNumber().longValue()<=mustSaveDayNum.longValue()){
    				journey.setProductId(productId);
    				Long id = insertEbkProdJourney(journey);
    				journey.setJourneyId(id);
    				updatePicMes(journey);
    			}else{
    				deletePic(journey);
    			}
    		}
    	}else{
    		Map<String,EbkProdJourney> map = new HashMap<String,EbkProdJourney>();
    		for(EbkProdJourney ebkProdJourney : results){
    			map.put(ebkProdJourney.getJourneyId().toString(), ebkProdJourney);
    		}
    		for(EbkProdJourney journey : ebkProdJourneyDOs){
    			if(journey.getDayNumber().longValue()<=mustSaveDayNum.longValue()){
    				if(journey.getJourneyId()==null){
        				journey.setProductId(productId);
        				Long id = insertEbkProdJourney(journey);
        				journey.setJourneyId(id);
    				}else{
    					updateEbkProdJourney(journey);
    				}
    				updatePicMes(journey);
    				map.remove(journey.getJourneyId().toString());
    			}else{
    				deletePic(journey);
    			}
    		}
    		if(!map.isEmpty()){
    			Iterator<String> it = map.keySet().iterator();
    			while(it.hasNext()){
    				String id = it.next();
    				deletePic(map.get(id));
    				deleteEbkProdJourney(map.get(id).getJourneyId());
    			}
    		}
    	}
    }
    
    
    /**
     * 批量新增多行程 行程描述
     * @param ebkProdJourneyDOs
     * @param productId
     * @param mustSaveDayNum
     * @return 
     */
    public void editEbkMultiProdJourneys(List<EbkProdJourney> ebkProdJourneyDOs,Long multiJourneyId,Long productId,Long mustSaveDayNum){
    	EbkProdJourney selectDO = new  EbkProdJourney();
    	selectDO.setMultiJourneyId(multiJourneyId);
    	List<EbkProdJourney> results = findListByTerm(selectDO);
    	if(results==null||results.size()==0){
    		for(EbkProdJourney journey : ebkProdJourneyDOs){
    			if(journey.getDayNumber().longValue()<=mustSaveDayNum.longValue()){
    				journey.setMultiJourneyId(multiJourneyId);
    				journey.setProductId(productId);
    				Long id = insertEbkProdJourney(journey);
    				journey.setJourneyId(id);
    				updatePicMes(journey);
    			}else{
    				deletePic(journey);
    			}
    		}
    	}else{
    		Map<String,EbkProdJourney> map = new HashMap<String,EbkProdJourney>();
    		for(EbkProdJourney ebkProdJourney : results){
    			map.put(ebkProdJourney.getJourneyId().toString(), ebkProdJourney);
    		}
    		for(EbkProdJourney journey : ebkProdJourneyDOs){
    			if(journey.getDayNumber().longValue()<=mustSaveDayNum.longValue()){
    				if(journey.getJourneyId()==null){
    					journey.setMultiJourneyId(multiJourneyId);
    					journey.setProductId(productId);
        				Long id = insertEbkProdJourney(journey);
        				journey.setJourneyId(id);
    				}else{
    					updateEbkProdJourney(journey);
    				}
    				updatePicMes(journey);
    				map.remove(journey.getJourneyId().toString());
    			}else{
    				deletePic(journey);
    			}
    		}
    		if(!map.isEmpty()){
    			Iterator<String> it = map.keySet().iterator();
    			while(it.hasNext()){
    				String id = it.next();
    				deletePic(map.get(id));
    				deleteEbkProdJourney(map.get(id).getJourneyId());
    			}
    		}
    	}
    }
    /**
     * 新增对象
     * @param ebkProdJourneyDO
     * @return 
     */
    public Long insertEbkProdJourney(EbkProdJourney ebkProdJourneyDO) {
    	List<String> traffics = ebkProdJourneyDO.getTraffics();
    	StringBuffer sb = new StringBuffer();
    	if(traffics!=null&&traffics.size()>0){
    		for(String tra : traffics){
        		sb.append(tra.trim());
        		sb.append(",");
    		}
    	}
    	if(sb.lastIndexOf(",")>0){
    		ebkProdJourneyDO.setTraffic(sb.substring(0, sb.lastIndexOf(",")));
    	} 
    	return ebkProdJourneyDAO.insertEbkProdJourneyDO(ebkProdJourneyDO);
    }
    
  /*  @Override
	public Long insert(EbkProdJourney record, String operatorName) {
		Long id = EbkProdJourneyDAO.insertEbkProdJourneyDO(record);  
		comLogDAO.insert("EBK_PROD_JOURNEY",record.getProductId(),id, operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertMultiJourney.name(), "创建行程描述","名称为[ "+record.getTitle()+" ]的多行程", "EBK_PROD_PRODUCT");
		return id;
	}*/
    
    /**
     * 修改对象
     * @param ebkProdJourneyDO
     * @return 
     */
    public Integer updateEbkProdJourney(EbkProdJourney ebkProdJourneyDO) {
    	List<String> traffics = ebkProdJourneyDO.getTraffics();
    	StringBuffer sb = new StringBuffer();
    	if(traffics!=null&&traffics.size()>0){
    		for(String tra : traffics){
        		sb.append(tra.trim());
        		sb.append(",");
    		}
    	}
    	if(sb.lastIndexOf(",")>0){
    		ebkProdJourneyDO.setTraffic(sb.substring(0, sb.lastIndexOf(",")));
    	}
    	return ebkProdJourneyDAO.updateEbkProdJourneyDO(ebkProdJourneyDO);
    }
    
    /**
     * 删除对象
     * @param journeyId
     * @return 
     */
    public Integer deleteEbkProdJourney(Long journeyId) {
    	return ebkProdJourneyDAO.deleteEbkProdJourneyDOByPrimaryKey(journeyId);
    }
    
    
    /**
     * 删除行程描述图片
     * @param ebkProdJourneyDO
     * @return 
     */
    private void deletePic(EbkProdJourney ebkProdJourneyDO) {
    	List<ComPicture> comPictures = ebkProdJourneyDO.getComPictureJourneyList();
    	if(comPictures!=null&&comPictures.size()>0){
    		for(ComPicture comPicture : comPictures){
    			comPictureService.deletePicture(comPicture.getPictureId());
    		}
    	}
    }
    
    /**
     * 修改行程描述图片objectId
     * @param ebkProdJourneyDO
     * @return 
     */
    private void updatePicMes(EbkProdJourney ebkProdJourneyDO) {
    	List<ComPicture> comPictures = ebkProdJourneyDO.getComPictureJourneyList();
    	if(comPictures!=null&&comPictures.size()>0){
    		for(int i=0;i<comPictures.size();i++){
    			if(comPictures.get(i)!=null){
    				ComPicture comPicture=comPictures.get(i);
        			String filName = comPicture.getPictureName();
        			comPicture = comPictureService.getPictureByPK(comPicture.getPictureId());
        			comPicture.setPictureObjectId(ebkProdJourneyDO.getJourneyId());
        			comPicture.setPictureName(filName);
        			comPicture.setSeq(i+1);
        			comPictureService.updatePicture(comPicture);
    			}
    		}
    	}
    }
    
	public EbkProdJourneyDAO getEbkProdJourneyDAO() {
		return ebkProdJourneyDAO;
	}
	public void setEbkProdJourneyDAO(EbkProdJourneyDAO ebkProdJourneyDAO) {
		this.ebkProdJourneyDAO = ebkProdJourneyDAO;
	}

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public ComPictureService getComPictureService() {
		return comPictureService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	@Override
	public List<EbkProdJourney> getViewJourneyByMultiJourneyId(Long multiJourneyId) {
		List<EbkProdJourney> journeyList= ebkProdJourneyDAO.getEbkProdJourneyByMultiJourneyId(multiJourneyId);
		if(journeyList!=null&&journeyList.size()>0){
    		for(EbkProdJourney ebkProdJourney : journeyList){
    	    	List<ComPicture> comPictures = comPictureService.getComPictureByObjectIdAndTypeOrderBySeqNum(ebkProdJourney.getJourneyId(),Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_MULTIJOURNEY.name());
    	    	ebkProdJourney.setComPictureJourneyList(comPictures);
    		}
    	}
		/*for(int i=0;i<journeyList.size();i++){
			EbkProdJourney viewJourney =journeyList.get(i);
			String placeDesc="";
			List<ViewJourneyPlace> places=this.getSelectedProdTarget(viewJourney.getJourneyId());
			for(int j=0;j<places.size();j++){
				if (j==0) placeDesc = places.get(j).getPlaceName();
				else placeDesc += "," + places.get(j).getPlaceName();
			}
			viewJourney.setPlaceDesc(placeDesc);
		}*/
		return journeyList;
	}

	public void insertEbkJourney(EbkProdJourney viewJourney) {
		if(viewJourney.getMultiJourneyId() == null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", viewJourney.getProductId());
			List<EbkMultiJourney> list = ebkMultiJourneyDAO.queryMultiJourneyByParams(params);
			if(list != null && list.size() > 0) {
				viewJourney.setMultiJourneyId(list.get(0).getMultiJourneyId());
			} else {
				EbkMultiJourney vmj = new EbkMultiJourney();
				vmj.setProductId(viewJourney.getProductId());
				Long multiJourneyId = ebkMultiJourneyDAO.insert(vmj);
				viewJourney.setMultiJourneyId(multiJourneyId);
			}
		}
		ebkProdJourneyDAO.insertEbkProdJourneyDO(viewJourney);
		}
	
	
}
