package com.lvmama.tmall.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.tmall.dao.OrdTmallMapDAO;

public class OrdTmallMapServiceImpl implements OrdTmallMapService {
	
	private OrdTmallMapDAO ordTmallMapDAO;

	public OrdTmallMapDAO getOrdTmallMapDAO() {
		return ordTmallMapDAO;
	}
	public void setOrdTmallMapDAO(OrdTmallMapDAO ordTmallMapDAO) {
		this.ordTmallMapDAO = ordTmallMapDAO;
	}

	@Override
	public boolean selectTmallNo(String tmall_order_no) {
		return ordTmallMapDAO.selectTmallNo(tmall_order_no);
	}

	@Override
	public Long insert(OrdTmallMap record) {
		return ordTmallMapDAO.insert(record);
	}

	@Override
	public List<String>  selectOrdOfCreate() {
		return ordTmallMapDAO.selectOrdOfCreate();
	}

	@Override
	public int updateByTmallOrderNoSelective(OrdTmallMap record) {
		return ordTmallMapDAO.updateByTmallOrderNoSelective(record);
	}

	@Override
	public int updateByOrdSelective(OrdTmallMap record) {
		return ordTmallMapDAO.updateByOrdSelective(record);
	}

	@Override
	public List<OrdTmallMap> getFailedOrderList(Map<String, String> param) {
		return ordTmallMapDAO.getFailedOrderList(param);
	}

	@Override
	public Long getFailedOrderListCount(Map<String, String> param) {
		return ordTmallMapDAO.getFailedOrderListCount(param);
	}

	@Override
	public List<OrdTmallMap> getOrderList(Map<String, String> param) {
		return ordTmallMapDAO.getOrderList(param);
	}

	@Override
	public Long getOrderListCount(Map<String, String> param) {
		return ordTmallMapDAO.getOrderListCount(param);
	}
	@Override
	public int updateByPrimaryKeySelective(OrdTmallMap record) {
		return ordTmallMapDAO.updateByPrimaryKeySelective(record);
	}
	@Override
	public OrdTmallMap selectByPrimaryKey(Long tmallMapId) {
		return ordTmallMapDAO.selectByPrimaryKey(tmallMapId);
	}
	@Override
	public OrdTmallMap getOrderByUK(String tid, Long productId,
			Long branchId) {
		return ordTmallMapDAO.getOrderByUK(tid, productId, branchId);
	}
	@Override
	public boolean selectCertificateType(Long orderId) {
		return ordTmallMapDAO.selectCertificateType(orderId);
	}
	@Override
	public List<OrdTmallMap> selectByTmallNo(String tid) {
		return ordTmallMapDAO.selectByTmallNo(tid);
	}
	@Override
	public OrdTmallMap selectByLvOrderId(Long oid) {
		return ordTmallMapDAO.selectByLvOrderId(oid);
	}
	

}
