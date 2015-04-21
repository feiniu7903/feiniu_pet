package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.vo.Page;

/**
 * @author ShiHui
 */
public interface PassCodeService extends PassCodeMonitorService
,PassDeviceService
,PassEventService
,PassPortService
,PassPortCodeService
,PassProductService
,PassProviderService {
	/**
	 * 按条件查询
	 * 
	 * @param查询参数
	 */

	List<PassCode> queryPassCodes(Map<String, Object> params);

	/**
	 * 添加通关码信息
	 * 
	 * @param PassCode对象
	 */
	Long addPassCode(PassCode passCode);

	/**
	 * 修改
	 */
	void updatePassCode(PassCode passCode);

	/**
	 * 删除
	 */
	void deletePassCode(Long codeId);

	/**
	 * 通过申请流水号查询通关码信息
	 * 
	 * @author clj
	 * @param 查询参数
	 */
	PassCode getCodeBySerialNo(String SerialNo);
	/**
	 * 查询通关点的提供商信息
	 * 
	 * @author clj
	 * @param codeId
	 * 
	 * @return
	 */
	List<PassPortCode> queryProviderByCode(Long codeId);
	/**
	 * 生成申请流水号
	 * @return
	 */
	public Long getPassCodeSerialNo( );
	/**
	 *  查询通关点信息记录数
	 * @param params
	 * @return 
	 */
	public Integer selectPassCodeRowCount(Map<String,Object> params);
	
	/**
	 * 通过服务商和游玩日期查询通关点信息
	 * 
	 * @param params
	 * @return
	 */

	public List<PassCode> selectCodeByProviderIdAndValidTime(Map<String,Object> params);
	
	/**
	 * 通过组合参数查询通关码信息
	 * 
	 * @param 查询参数
	 */
	public PassCode getPassCodeByParams(Map<String, Object> params ) ;
	
	/**
	 * 查询订单是否已经履行
	 * @param params
	 * @return
	 */
	public boolean hasPassCodePerform(Map<String, Object> params );
	
	/**
	 * 通过辅助码MD5编号查询通关码信息
	 * 
	 * @param 查询参数
	 */
	public PassCode getCodeByAddCodeMd5(String addCodeMd5);
	
	/**
	 * 查询离线模式通关信息列表
	 * @param params
	 * @return
	 */
	public List<PassCode> selectVouchersByProviderId(Map<String,Object> params);
	
		
	Integer getSyncUpdatePassCodeCount(Map<String, Object> queryOption);

	Integer getUpdatePassCodeByCodeId(Map<String, Object> queryOption);

	Integer getUpdatePassCodeBySerId(Map<String, Object> queryOption);
	/**
	 * 通过订单编号和状态为申码成功查询通关点信息(返回通关码及通关编号等详细信息)
	 * 
	 * @param serialNo
	 * @return
	 */
	PassCode getPassCodeByOrderIdStatus(Long orderId);
	/**
	 * 修改
	 * 
	 * @update clj 2010-9-20
	 */
	void updatePassCodeBySerialNo(PassCode passCode);
	boolean successExecute(PassCode passCode, Passport passport);
	Page<PassCode> selectPassCodeAutoPerform(Map parameterObject);
	/**
	 * 通过主键查询通关点信息
	 * 
	 * @param serialNo
	 * @return
	 */
	PassCode getPassCodeByCodeId(Long codeId);
	/**
	 * 通过EventId查询通关码信息
	 * 
	 * @param 查询参数
	 */
	PassCode getPassCodeByEventId(Long eventId);
	List<PassCode> getPassCodeByAddCode(String addCode);
	public List<PassPortCode> queryPassPortCodeByParam(PassPortCode passPortCode) ;
	
	/**
	 * 通过订单编号和状态为申码成功查询通关点信息(返回通关码及通关编号等详细信息)
	 * 
	 * @param serialNo
	 * @return
	 */
	List<PassCode> getPassCodeByOrderIdStatusList(Long orderId);
	/**
	 * 查询发码成功的二维码
	 * @param orderId
	 * @param targetId
	 * @return
	 */
	List<PassCode> getPassCodeByOrderIdAndTargetIdList(Long orderId,Long targetId);

	/**
	 * 查询LVMAMA码服务生成的辅助码是否已经存在
	 * @param code
	 * @return
	 */
	boolean checkCodeHasExisting(String codeType,String code);

	/**
	 * LVMAMA码服务废码
	 * @param outStance
	 * @return
	 */
	boolean destoryCode(String outStance, String destoryStatus);

	/**
	 * 根据码查询可以通关的设备有哪些
	 * @param addCode
	 * @return
	 */
	List<PassDevice> getDeviceListByCode(String addCode, String currentUdid);

	List<String> getAddCodesByEBK(Map params);

	/**
	 * 查询辅助码是否在系统中存在
	 * @param param
	 * @return
	 */
	boolean addCodeExisting(String addCode);

	Object addCodeIsInTarget(Map<String, Object> param);
	
	void updatePassPortCodeByCodeId(PassPortCode passPortCode);

	/**
	 * 根据订单ID更新通关码为已履行
	 * @param ordId
	 */
	void updatePassPortPerform(Long ordId);
	
	public List<PassCode> getPassCodeBysupplierId(Map<String, Object> param);
	
	List<PassCode> selectPassCodeListByOrderIdAndStatus(Map<String,Object> params);
}
