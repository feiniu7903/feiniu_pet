package com.lvmama.comm.bee.service.ord;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;


/**
 * 订单履行服务接口.
 *
 * <pre></pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface OrderPerformService {/**
	 * 新增履行信息.
	 *
	 * @param performTargetId
	 *            履行对象ID
	 * @param objectId
	 *            订单ID或订单子子项ID
	 * @param objectType
	 *            objectId指向的类型
	 * @param adultQuantity
	 *            该采购产品的成人数量
	 * @param childQuantity
	 *            该采购产品的儿童数量
	 * @param memo 备注           
	 *
	 * @return 订单号
	 */
	Long insertOrdPerform(Long performTargetId, Long objectId, String objectType, Long adultQuantity, Long childQuantity,String memo);
	/**
	 * 更新订单子子项的履行状态为自动履行
	 * 不添加履行记录
	 * @param orderItemMetaId
	 * @param performTargetId 
	 */
	boolean autoPerform(Long orderItemMetaId, Long performTargetId);

	/**
	 * 检查指定订单号的子子项是否全部都履行了
	 * @param orderId
	 * @return
	 */
	boolean checkAllPerformed(Long orderId);
	
	List<PerformDetail> getOrderPerformDetail(List<Long> orderItemMetaIds);
	
	Page<OrdOrderPerformResourceVO> queryOrderPerformByPage(Long pageSize,Long currentPage,List<Long> metaBranchIds,Map<String, Object> para);
	ResultHandle perform(Long orderId,Long targetId,Long[] quantity,Long[] orderItemMetaId,String remark,Long ebkUserId,Date performTime);
	/**
	 * E景通手机通关
	 * @param addCode
	 * @param udid
	 * @param quantity
	 * @param orderItemMetaId
	 * @param remark
	 * @param userId
	 * @return
	 */
	ResultHandle perform(String addCode, String udid, Long[] quantity, Long[] orderItemMetaId, String remark, Long ebkUserId,Date performTime);
	List<OrdOrderPerformResourceVO> queryOrderPerformByEBK(Map<String, Object> para);
	/**
	 * 用于客户端履行通关后，上传履行信息时判断传到服务器的itemMetas是否属于同一订单
	 * 为了防止异常数据上传到服务器
	 * @param orderItemMetaId
	 * @param orderId
	 * @return
	 */
	boolean isItemMetasNotInOrder(Long[] orderItemMetaId, Long orderId);
	
}
