package com.lvmama.comm.pet.service.prod;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.view.PaginationVO;
import com.lvmama.comm.pet.po.prod.ProdGroupDream;
import com.lvmama.comm.pet.po.pub.ComPicture;
/**
 * 团购梦想Serivice
 * @author gengsiqiang
 * @author songlianjun
 *
 */
public interface ProdGroupDreamService {
	
	
	public Integer selectRowCount(Map searchConds);
	ProdGroupDream getGroupDream(Long dreamId);
	List<ProdGroupDream> getGroupDreams(Map param);
	/**
	 * 保存团购梦想
	 * @param groupDream 
	 * @param pictureList  图片列表
	 * @return
	 */
	Long addGroupDream(ProdGroupDream groupDream,List<ComPicture> pictureList,String operatorName);
	/**
	 * 更新
	 * @param groupDream
	 */
	void updateGroupDream(ProdGroupDream groupDream,String operatorName);
	/**
	 * 逻辑删除团购梦想
	 * @param groupDream
	 */
    void deleteGroupDream(ProdGroupDream groupDream);
    /**
     * 保存图片
     * @param picture
     */
    void saveGroupDreamPicture(ComPicture picture);
    /**
     * 根据图片ID删除图片
     * @param pictureId
     */
    void deleteGroupDreamPicture(Long pictureId);
    /**
     * 查询团购梦想产品的图片
     * @param dreamId
     * @return
     */
    List<ComPicture> getPictureList(Long dreamId);
    
    /**
     * 查询团购梦想参与的喜欢的提交信息
     * @param dreamId
     * @return
     */
    PaginationVO  getGroupDreamEnjoySubmitters(PaginationVO pageVo);
	
}
