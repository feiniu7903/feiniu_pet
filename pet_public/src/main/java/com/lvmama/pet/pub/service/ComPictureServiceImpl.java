package com.lvmama.pet.pub.service;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.pet.pub.dao.ComPictureDAO;

/**
 * 图片信息管理实现
 * 
 * @author ranlongfei 2012-8-1
 * @version
 */
public class ComPictureServiceImpl implements ComPictureService {

	private ComPictureDAO comPictureDAO;

	@Override
	public Long savePicture(ComPicture pic) {
		return this.comPictureDAO.insert(pic);
	}

	@Override
	public Long savePictureList(List<ComPicture> pictureList) {
		Long result = 0L;
		for (ComPicture comPicture : pictureList) {
			this.comPictureDAO.insert(comPicture);
			result ++;
		}
		return result;
	}

	@Override
	public void deletePicture(Long pkId) {
		this.comPictureDAO.deleteByPrimaryKey(pkId);
	}
	@Override
	public void updatePicture(ComPicture comPicture) {
		this.comPictureDAO.updateByPrimaryKey(comPicture);
	}

	@Override
	public ComPicture getPictureByPK(Long pkId) {
		return comPictureDAO.selectByPrimaryKey(pkId);
	}

	@Override
	public List<ComPicture> getPictureByPKs(Long[] pkIds) {
		List<ComPicture> resultList = new ArrayList<ComPicture>();
		for(Long pkId : pkIds) {
			resultList.add(getPictureByPK(pkId));
		}
		return resultList;
	}

	@Override
	public boolean changeSeq(Long pictureId, String type) {
		ComPicture cp = comPictureDAO.selectByPrimaryKey(pictureId);

		// 读取图片对应的位置
		ComPicture other = comPictureDAO.getNextPictureBySeq(cp.getPictureObjectId(), type, cp.getSeq());
		if (other == null) {
			return false;
		}
		// 交换位置
		int seq = cp.getSeq();
		cp.setSeq(other.getSeq());
		other.setSeq(seq);
		comPictureDAO.updateByPrimaryKey(cp);
		comPictureDAO.updateByPrimaryKey(other);
		return true;
	}
	

	@Override
	public List<ComPicture> getPictureByPageId(long pageId) {
		return this.comPictureDAO.getComPictureByPageId(pageId);
	}

	@Override
	public List<ComPicture> getPictureByObjectIdAndType(Long pictureObjectId, String pictureObjectType) {
		return this.comPictureDAO.getComPictureByObjectIdAndType(pictureObjectId, pictureObjectType);
	}
	@Override
	public List<ComPicture> getComPictureByObjectIdOrderBySeqNum(Long pictureObjectId) {
		return this.comPictureDAO.getComPictureByObjectIdOrderBySeqNum(pictureObjectId);
	}
	
	@Override
	public List<ComPicture> getComPictureByObjectIdOrderBySeqNumForEbk(Long pictureObjectId) {
		return this.comPictureDAO.getComPictureByObjectIdOrderBySeqNumForEbk(pictureObjectId);
	}
	
	
	@Override
	public List<ComPicture> getComPictureByObjectIdAndTypeOrderBySeqNum(Long pictureObjectId, String pictureObjectType) {
		return this.comPictureDAO.getComPictureByObjectIdAndTypeOrderBySeqNum(pictureObjectId, pictureObjectType);
	}

	public ComPictureDAO getComPictureDAO() {
		return comPictureDAO;
	}

	public void setComPictureDAO(ComPictureDAO comPictureDAO) {
		this.comPictureDAO = comPictureDAO;
	}

}
