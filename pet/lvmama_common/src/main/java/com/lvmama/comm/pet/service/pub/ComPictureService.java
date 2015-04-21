package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComPicture;

/**
 * 图片信息管理
 * @author ranlongfei 2012-8-1
 * @version 1.0
 */
public interface ComPictureService {

	/**
	 * 保存图片信息
	 * 
	 * @author: ranlongfei 2012-8-1 下午12:56:12
	 * @param pic
	 * @return
	 */
	Long savePicture(ComPicture pic);
	/**
	 * 批量保存图片信息
	 * 
	 * @author: ranlongfei 2012-8-2 下午2:54:08
	 * @param pictureList
	 * @return
	 */
	Long savePictureList(List<ComPicture> pictureList);
	/**
	 * 删除图片信息
	 * 
	 * @author: ranlongfei 2012-8-1 下午12:56:40
	 * @param pkId
	 */
	void deletePicture(Long pkId);
	/**
	 * 修改图片信息
	 * 
	 * @author: zhangjie 
	 * @param comPicture
	 */
	void updatePicture(ComPicture comPicture);
	/**
	 * 根据主键查询图片信息
	 * 
	 * @author: ranlongfei 2012-8-1 下午12:56:56
	 * @param pkId
	 * @return
	 */
	ComPicture getPictureByPK(Long pkId);
	/**
	 * 根据主键列表查询图片列表
	 * 
	 * @author: ranlongfei 2012-8-2 下午7:24:28
	 * @param pkIds 主键列表
	 * @return
	 */
	List<ComPicture> getPictureByPKs(Long[] pkIds);
	/**
	 * 更改图片顺序
	 * 
	 * @author: ranlongfei 2012-8-1 下午12:57:18
	 * @param pictureId
	 * @param type down/up
	 * @return
	 */
	boolean changeSeq(Long pictureId, String type);
	/**
	 * 根据pageId查询图片列表
	 * 
	 * @author: ranlongfei 2012-8-1 下午12:58:29
	 * @param pageId
	 * @return
	 */
	List<ComPicture> getPictureByPageId(long pageId);
	/**
	 * 根据对象ID以及对象类型查询其对应的图片
	 * 
	 * @author: ranlongfei 2012-8-1 下午12:59:41
	 * @param pictureObjectId
	 * @param pictureObjectType
	 * @return
	 */
	List<ComPicture> getPictureByObjectIdAndType(Long pictureObjectId,String pictureObjectType);
	/**
	 * 根据对象ID以及对象类型查询其对应的图片默认使用seq_num正序
	 * @param pictureObjectId 
	 * @param pictureObjectType
	 * @return
	 */
	public List<ComPicture> getComPictureByObjectIdAndTypeOrderBySeqNum(Long pictureObjectId, String pictureObjectType);
	/**
	 * 根据对象ID查询其对应的图片默认使用seq_num正序
	 * @param pictureObjectId 
	 * @return
	 */
	public List<ComPicture> getComPictureByObjectIdOrderBySeqNum(Long pictureObjectId);
	/**
	 * 根据对象ID查询其对应的图片默认使用seq_num正序EBK产品管理使用
	 * @param pictureObjectId 
	 * @return
	 */
	public List<ComPicture> getComPictureByObjectIdOrderBySeqNumForEbk(Long pictureObjectId);
}
