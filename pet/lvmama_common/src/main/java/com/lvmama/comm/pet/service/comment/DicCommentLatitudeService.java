/**
 * 
 */
package com.lvmama.comm.pet.service.comment;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;

/**
 * @author liuyi
 * 点评维度字典获取SERVICE
 */
public interface DicCommentLatitudeService {
	
	
	/**
	 * 增加字典记录
	 * @param dic 字典对象
	 * @return 插入的对象标识
	 */
	Long insert(final DicCommentLatitude dic);

	/**
	 * 查询纬度字典列表
	 * @param parames 查询参数
	 * @return 字典表列表
	 */
	List<DicCommentLatitude> getDicCommentLatitudeList(final Map<String, Object> parames);

	/**
	 * 修改字典记录
	 * @param dic 字典对象
	 * @return 修改个数
	 */
	int update(final DicCommentLatitude dic);

	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return 字典对象
	 */
	DicCommentLatitude queryByKey(final String id);
	
	 /**
	  * 查询景点的4个纬度
	  * @param place 景点
	  * @return List<DicCommentLatitude>
	 */
	List<DicCommentLatitude> getDicCommentLatitudeListBySubject(Place place);
	 
	/**
	 * 获取产品的4个维度
	 * @param toPlaceId 产品目的地
	 * @param productType 产品类型
	 * @return
	 */
	List<DicCommentLatitude> getLatitudesOfProduct(Place toPlace, String productType);
	
}
