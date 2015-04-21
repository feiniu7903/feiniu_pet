package com.lvmama.pet.pub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.vo.Constant.EBK_PROD_PICTURE_TYPE;

public class ComPictureDAO extends BaseIbatisDAO{

	public int deleteByPrimaryKey(Long pictureId) {
		ComPicture key = new ComPicture();
		key.setPictureId(pictureId);
		int rows = super.delete("COM_PICTURE.deleteByPrimaryKey", key);
		return rows;
	}

	private int getNextSeq(ComPicture record){
		try{
			return (Integer)super.queryForObject("COM_PICTURE.getNextSeq",record);
		}catch(NullPointerException ex){
			return 1;
		}
	}
	private int getNextSeqEBK(ComPicture record){
		try{
			return (Integer)super.queryForObject("COM_PICTURE.getNextSeqEBK",record);
		}catch(NullPointerException ex){
			return 1;
		}
	}
	
	public Long insert(ComPicture record) {
		//如果上传的类型是view_page下的力图片，读取seq位置
		if(StringUtils.equals("VIEW_PAGE", record.getPictureObjectType())){
			record.setSeq(getNextSeq(record));
		}
		//如果上传的类型是EBK_PROD_PRODUCT下的图片，读取seq位置(EBK产品设置时使用)
		if(EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.name().equals(record.getPictureObjectType())
				||EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.name().equals(record.getPictureObjectType())){
			record.setSeq(getNextSeqEBK(record));
		}
		return (Long)super.insert("COM_PICTURE.insert", record);
	}

	public ComPicture selectByPrimaryKey(Long pictureId) {
		ComPicture key = new ComPicture();
		key.setPictureId(pictureId);
		ComPicture record = (ComPicture) super.queryForObject("COM_PICTURE.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKey(ComPicture record) {
		int rows = super.update("COM_PICTURE.updateByPrimaryKey", record);
		return rows;
	}

	@SuppressWarnings("unchecked")
	public List<ComPicture> getComPictureByPageId(long pageId) {
		return super.queryForList("COM_PICTURE.getComPictureByPageId", pageId);
	}
	/**
	 * 根据对象ID以及对象类型查询其对应的图片
	 * @param pictureObjectId 
	 * @param pictureObjectType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComPicture> getComPictureByObjectIdAndType(Long pictureObjectId,String pictureObjectType){
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		paramMap.put("pictureObjectId", pictureObjectId);
		paramMap.put("pictureObjectType", pictureObjectType);
		return super.queryForList("COM_PICTURE.getComPictureByObjectIdAndType", paramMap);
	}
	/**
	 * 根据对象ID以及对象类型查询其对应的图片默认使用seq_num正序
	 * @param pictureObjectId 
	 * @param pictureObjectType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComPicture> getComPictureByObjectIdAndTypeOrderBySeqNum(Long pictureObjectId,String pictureObjectType){
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		paramMap.put("pictureObjectId", pictureObjectId);
		paramMap.put("pictureObjectType", pictureObjectType);
		return super.queryForList("COM_PICTURE.getComPictureByObjectIdAndTypeOrderBySeqNum", paramMap);
	}
	
	/**
	 * 根据对象ID查询其对应的图片默认使用seq_num正序
	 * @param pictureObjectId 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComPicture> getComPictureByObjectIdOrderBySeqNum(Long pictureObjectId){
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		paramMap.put("pictureObjectId", pictureObjectId);
		return super.queryForList("COM_PICTURE.getComPictureByObjectIdOrderBySeqNum", paramMap);
	}
	
	/**
	 * 根据对象ID查询其对应的图片默认使用seq_num正序EBK产品管理使用
	 * @param pictureObjectId 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComPicture> getComPictureByObjectIdOrderBySeqNumForEbk(Long pictureObjectId){
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		paramMap.put("pictureObjectId", pictureObjectId);
		return super.queryForList("COM_PICTURE.getComPictureByObjectIdOrderBySeqNumForEbk", paramMap);
	}
	

	/**
	 * 根据类型更换图片的位置
	 * @param pictureId
	 * @param type up/down
	 * @return
	 */
	public ComPicture getNextPictureBySeq(Long pageId,String type,int seq){
		if(!ArrayUtils.contains(new String[]{"up","down"}, type)){
			throw new IllegalArgumentException("参数错误");
		}
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pictureObjectId", pageId);
		param.put("type", type);
		param.put("seq", seq);
		return (ComPicture)super.queryForObject("COM_PICTURE.selectNextPicture",param);
	}
	
	
	
}