package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.service.prod.ProdPlaceTagService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;
import com.lvmama.prd.dao.ProdPlaceTagDAO;
import com.lvmama.prd.dao.ProdTagDAO;
import com.lvmama.prd.dao.ProdTagGroupDAO;

public class ProdPlaceTagServiceImpl implements ProdPlaceTagService {
//	private ProdPlaceDAO prodPlaceDAO;
	private ProdPlaceTagDAO prodPlaceTagDAO;
	private ProdTagGroupDAO prodTagGroupDAO;
	private ProdTagDAO prodTagDAO;
	private ComPlaceDAO comPlaceDAO;
	
	/** 目的地 和 标签 添加时 处理 关联数据的 flag:1 */
	private int EXECUTE_FLAG_ADD=1;
	
	/** 目的地 和 标签 强制  添加时 处理 关联数据的 flag:2
	         非强制目的地再关联新的标签 ，尽管组是单选 */
	private int EXECUTE_FLAG_ADD_FORCE=2;
	
	/** 删除重复的目的地标签关联 新增关联 */
	public List<ProdPlaceTag> addPlaceTagAndDeleteConflictsPlaceTag(long tagGroupId, List<ProdPlaceTag> prodPlaceTags) {
		return doProgPlaceTags(tagGroupId, prodPlaceTags, null, EXECUTE_FLAG_ADD_FORCE);
	}
	
	/** 添加 目的地 标签 关联 */
	public List<ProdPlaceTag> addProgPlaceTags(Long tagGroupId,List<ProdPlaceTag> placeTags,
			List<ProdPlaceTag> alreadyAddPlaceTags){
		return doProgPlaceTags(tagGroupId, placeTags, alreadyAddPlaceTags, EXECUTE_FLAG_ADD);
	}
	
	/**
	 * 公用方法，处理 目的地 和 标签的关联数据
	 * @param tagGroupId 标签组
	 * @param List<ProdPlaceTag> placeTags 添加的新标签 项
	 * @param List<ProdPlaceTag> alreadyAddPlaceTags 目的地的已有关联
	 * @param int executeFlag
	 * @param String creator 创建方式(人工/自动)
	 * @return
	 */
	private List<ProdPlaceTag> doProgPlaceTags(Long tagGroupId,List<ProdPlaceTag> placeTags,
			List<ProdPlaceTag> alreadyAddPlaceTags,int executeFlag){
		
		ProdTagGroup prodTagGroup = this.prodTagGroupDAO.selectByPrimaryKey(tagGroupId);
		for (int i = 0; i < placeTags.size(); i++) {
			//新增标签
			ProdPlaceTag placeTag = placeTags.get(i);
			List<ProdPlaceTag> progTagList=null;//
			
			//标签组的单多选条件处理
			if (prodTagGroup.isSingleOption()) {
				progTagList = this.prodPlaceTagDAO
						.selectPlaceTagByPlaceIdAndTagGroupId(placeTag.getPlace().getPlaceId(),
						tagGroupId);
			} else {
				progTagList = prodPlaceTagDAO.selectPlaceTagByProdPlaceTag(placeTag);
			}
			
			//非强制可以保留以前的记录
			if(executeFlag==EXECUTE_FLAG_ADD){
				if (progTagList!=null && !progTagList.isEmpty()) {//重复添加
					Place place = comPlaceDAO.load(placeTag.getPlace().getPlaceId());
					placeTag.setPlace(place);
					//查询的是页面选择的tag 用于显示
//					ProdTag tag = prodTagDAO.selectByPrimaryKey(placeTag.getTag().getTagId());
					
					ProdTag tag = prodTagDAO.selectByPrimaryKey(progTagList.get(0).getTag().getTagId());
//					ProdPlaceTag tempPlaceTag=prodPlaceTagDAO.selectBytagId(progTagList.get(0).getTag().getTagId());
					
					placeTag.setTag(tag);
//					placeTag.setBeginTime(tempPlaceTag.getBeginTime());
//					placeTag.setEndTime(tempPlaceTag.getEndTime());
					alreadyAddPlaceTags.add(placeTag);
				}else{
					//没有重复关联，则添加关联
					prodPlaceTagDAO.insertSelective(placeTag);
				}
			}else if(executeFlag==EXECUTE_FLAG_ADD_FORCE){
				//删除以前所有关联，新建
				if(progTagList!=null){
					for (int j = 0; j < progTagList.size(); j++) {
						ProdPlaceTag pt = progTagList.get(j);
						prodPlaceTagDAO.deleteByPlaceTagId(pt);
					}
				}
				prodPlaceTagDAO.insertSelective(placeTag);
			}
		}
		return alreadyAddPlaceTags;
	}
	
	public void deleteProdPlaceTagTimeOut()
	{
		this.prodPlaceTagDAO.deleteProdPlaceTagTimeOut();
	}
	/** 通过标签和 目的地类型 查询 目的地的记录数 */
	public Integer queryPlaceByTagAndPlaceTypeCount(Map<String, Object> params)
			throws Exception{
		return prodPlaceTagDAO.queryPlaceByTagAndPlaceTypeCount(params);
	}
	
	/** 通过标签和 目的地类型 查询 目的地 */
	public List<Place> queryPlaceByTagAndPlaceType(Map<String, Object> params)
			throws Exception{
		return prodPlaceTagDAO.queryPlaceByTagAndPlaceType(params);
	}
	
	public Integer getPlaceTagsTotalCount(Long tagId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagId", tagId);
		return prodPlaceTagDAO.selectRowCount(params);
	}
	
	public List<ProdPlaceTag> selectByParams(Map<String, Object> params) {
		List<ProdPlaceTag> prodPlaceTags = prodPlaceTagDAO.selectByParams(params);
//		for (ProdPlaceTag tag : prodPlaceTags) {
//			ProdPlace prodPlace = prodPlaceDAO.selectByPrimaryKey(tag.getPlaceId());
//			tag.setPlaceName(prodPlace.getPlaceName());
//			tag.setPlaceType(prodPlace.getZhPlaceType());
//		}
		return prodPlaceTags;
	}
	
	@Override
	public void delPlaceTags(List<ProdPlaceTag> prodPlaceTags) {
//		for (int i = 0; i < prodPlaceTags.size(); i++) {
//			prodPlaceTagDAO.deleteProdPlaceTag(prodPlaceTags.get(i));
//		}
		for (ProdPlaceTag prodPlaceTag : prodPlaceTags) {
			prodPlaceTagDAO.deleteProdPlaceTag(prodPlaceTag);
		}
		
	}
	
	/** 通过 目的地 查询 目的地关联的标签 */
	public List<ProdPlaceTag> selectPlaceTagsByPlace(Place place){
		return prodPlaceTagDAO.selectPlaceTagsByPlace(place);
	}
	

//	public ProdPlaceDAO getProdPlaceDAO() {
//		return prodPlaceDAO;
//	}
//
//	public void setProdPlaceDAO(ProdPlaceDAO prodPlaceDAO) {
//		this.prodPlaceDAO = prodPlaceDAO;
//	}

	public ProdPlaceTagDAO getProdPlaceTagDAO() {
		return prodPlaceTagDAO;
	}

	public void setProdPlaceTagDAO(ProdPlaceTagDAO prodPlaceTagDAO) {
		this.prodPlaceTagDAO = prodPlaceTagDAO;
	}

	public void setProdTagGroupDAO(ProdTagGroupDAO prodTagGroupDAO) {
		this.prodTagGroupDAO = prodTagGroupDAO;
	}

	public void setProdTagDAO(ProdTagDAO prodTagDAO) {
		this.prodTagDAO = prodTagDAO;
	}

	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}

	
}
