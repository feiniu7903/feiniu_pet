package com.lvmama.ord.service.impl;

import com.lvmama.comm.bee.po.ord.*;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.complaint.NcComplaintService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.*;
import com.lvmama.prd.dao.ProdProductDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-29<p/>
 * Time: 下午2:28<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintServiceImpl implements NcComplaintService {

    private NcComplaintDAO ncComplaintDAO;
    private NcComplaintTrackingDAO ncComplaintTrackingDAO;
    private OrderItemProdDAO orderItemProdDAO;
    private ProdProductDAO prodProductDAO;
    private NcComplaintRemindDAO ncComplaintRemindDAO;
    private NcComplaintDutyDAO ncComplaintDutyDAO;
    private NcComplaintDutyDetailsDAO ncComplaintDutyDetailsDAO;
    private NcComplaintResultDAO ncComplaintResultDAO;


    public NcComplaint getNcComplaintByComplaintId(Long complaintId) {
        return ncComplaintDAO.getNcComplaintByComplaintId(complaintId);
    }

    public int updateNcComplaint(NcComplaint ncComplaint) {
        return ncComplaintDAO.updateNcComplaint(ncComplaint);
    }

    @Override
    public List<NcComplaint> queryComplaintByParams(Map<String, Object> params) {
        return ncComplaintDAO.queryComplaintByParams(params);
    }

    @Override
    public Long queryComplaintCount(Map<String, Object> params) {
        return ncComplaintDAO.queryComplaintCount(params);
    }

    public Long createNcComplaint(NcComplaint ncComplaint) {
        return ncComplaintDAO.insert(ncComplaint);
    }

    public Long createNcComplaintTracking(NcComplaintTracking ncComplaintTracking) {
        ncComplaintTracking.setTrackingId(ncComplaintTrackingDAO.getNcComplaintTrackingSequence());
        return ncComplaintTrackingDAO.insertNcComplaintTracking(ncComplaintTracking);
    }

    public List<NcComplaintTracking> getNcComplaintTrackingList(Map<String, Object> params) {
        List<NcComplaintTracking> ncComplaintTrackingList = ncComplaintTrackingDAO.selectNcComplaintTrackingList(params);
        return ncComplaintTrackingList;
    }

    public Long createNcComplaintRemind(NcComplaintRemind ncComplaintRemind) {
        return ncComplaintRemindDAO.insertNcComplaintRemind(ncComplaintRemind);
    }

    public int updateNcComplaintRemind(NcComplaintRemind ncComplaintRemind) {
        return ncComplaintRemindDAO.updateNcComplaintRemind(ncComplaintRemind);
    }

    public List<NcComplaintRemind> getNcComplaintRemindList(Map<String, Object> params) {
        return ncComplaintRemindDAO.selectNcComplaintRemindList(params);
    }

    public void deleteNcComplaintRemind(Long remindId) {
        ncComplaintRemindDAO.deleteNcComplaintRemind(remindId);
    }

    public NcComplaintRemind getNcComplaintRemindByRemindId(Long remindId) {
        return ncComplaintRemindDAO.selectNcComplaintRemindByRemindId(remindId);
    }

    public void transferComplaint(String complaintIds, String targetUserName) {

        String[] cids = complaintIds.split(",");
        for (String cid : cids) {
            NcComplaint ncComplaint = ncComplaintDAO.getNcComplaintByComplaintId(Long.valueOf(cid));
            if (ncComplaint != null) {
                ncComplaint.setCurrentProcessPeople(targetUserName);
                ncComplaintDAO.updateNcComplaint(ncComplaint);
            }
        }
    }

    public ProdProduct getProdProductByOrderId(Long orderId) {

        ProdProduct prodProduct = null;
        List<OrdOrderItemProd> ordOrderItemProdList = orderItemProdDAO.selectByOrderId(orderId);
        OrdOrderItemProd ordOrderItemProd = null;
        if (ordOrderItemProdList != null && ordOrderItemProdList.size() > 0) {
            for (OrdOrderItemProd prod : ordOrderItemProdList) {
                if ("true".equals(prod.getIsDefault())) {
                    ordOrderItemProd = prod;
                }
            }

            if (ordOrderItemProd == null) {
                ordOrderItemProd = ordOrderItemProdList.get(0);
            }

            prodProduct = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
        }

        return prodProduct;
    }

    public void createNcComplaintTracking(NcComplaintTracking ncComplaintTracking, NcComplaint ncComplaint) {
        ncComplaintTrackingDAO.insertNcComplaintTracking(ncComplaintTracking);
        if (!StringUtils.isBlank(ncComplaint.getProcessStatus())) {

            if (ncComplaint.getProcessStatus().equals(Constant.NC_COMPLAINT_PROCESSING_STATUS.CLOSED.getCode())) {
                ncComplaint.setCloseTime(new Date());
            }

            ncComplaintDAO.updateNcComplaint(ncComplaint);
        }
    }

    @Override
    public NcComplaintResult getComplaintResultByComplaintId(Long complaintId) {
        return ncComplaintDAO.selectComplaintReusltByComplaintId(complaintId);
    }

    public List<NcComplaintRemind> getNcComplaintRemindByComplaintId(Long complaintId) {
        return ncComplaintRemindDAO.selectNcComplaintRemindByComplaintId(complaintId);
    }

    public void saveNcComplaintDuty(NcComplaintDuty ncComplaintDuty, List<NcComplaintDutyDetails> ncComplaintDutyDetailsList) {

        if (ncComplaintDuty.getDutyId() == null) { //新增
            ncComplaintDutyDAO.insertNcComplaintDuty(ncComplaintDuty);
        } else {//更新
            ncComplaintDutyDAO.updateNcComplaintDuty(ncComplaintDuty);
        }

        if(Constant.NC_COMPLAINT_DUTY_TYPE.DUTY.getCode().equals(ncComplaintDuty.getType())){
            NcComplaint ncComplaint = ncComplaintDAO.getNcComplaintByComplaintId(ncComplaintDuty.getComplaintId());
            ncComplaint.setProcessStatus(Constant.NC_COMPLAINT_PROCESSING_STATUS.COMPLETE.getCode());
            ncComplaint.setCompletionTime(new Date(System.currentTimeMillis()));
            ncComplaintDAO.updateNcComplaintAll(ncComplaint);
        }

        ncComplaintDutyDetailsDAO.deleteNcComplaintDutyDetails(ncComplaintDuty.getDutyId());

        if (ncComplaintDutyDetailsList != null && ncComplaintDutyDetailsList.size() > 0) {
            for (NcComplaintDutyDetails details : ncComplaintDutyDetailsList) {
                details.setDutyId(ncComplaintDuty.getDutyId());
                ncComplaintDutyDetailsDAO.insertNcComplaintDutyDetails(details);
            }
        }

    }

    public NcComplaintDuty getNcComplaintDutyByComplaintId(Long complaintId) {
        return ncComplaintDutyDAO.getNcComplaintDutyByComplaintId(complaintId);
    }

    public List<NcComplaintDutyDetails> getNcComplaintDutyDetailsList(Long dutyId) {
        return ncComplaintDutyDetailsDAO.selectNcComplaintDutyDetailsByDutyId(dutyId);
    }

    public Long createNcComplaintResult(NcComplaintResult ncComplaintResult) {
        return ncComplaintResultDAO.insertNcComplaintResult(ncComplaintResult);
    }

    public int updateNcComplaintResult(NcComplaintResult ncComplaintResult) {
        return ncComplaintResultDAO.updateNcComplaintResult(ncComplaintResult);
    }

    public void updateRelatedComplaint(Long complaintId, String relatedComplaint) {

        NcComplaint ncComplaint = getNcComplaintByComplaintId(complaintId);
        ncComplaint.setRelatedComplaint(relatedComplaint);
        ncComplaintDAO.updateNcComplaintAll(ncComplaint);


        if(relatedComplaint!=null && StringUtils.isNotBlank(relatedComplaint.trim())) {
            List<String> rcs = Arrays.asList(relatedComplaint.trim().split(","));

            for (String rc : rcs) {
                NcComplaint nc = getNcComplaintByComplaintId(Long.valueOf(rc));

                if (nc.getRelatedComplaint() == null) {
                    nc.setRelatedComplaint(String.valueOf(ncComplaint.getComplaintId()));
                } else {
                    List<String> rcList = Arrays.asList(nc.getRelatedComplaint().split(","));

                    if (!rcList.contains(String.valueOf(ncComplaint.getComplaintId()))) {

                        nc.setRelatedComplaint(nc.getRelatedComplaint() + "," + String.valueOf(ncComplaint.getComplaintId()));
                    }
                }
                ncComplaintDAO.updateNcComplaintAll(nc);
            }
        }
    }

    public void updateNcComplaintAll(NcComplaint ncComplaint) {
        ncComplaintDAO.updateNcComplaintAll(ncComplaint);
    }

    public NcComplaintDuty getNcComplaintDuty(Map<String, Object> params) {
        return ncComplaintDutyDAO.getNcComplaintDuty(params);
    }

    public Long getNcComplaintTrackingSequence() {
        return ncComplaintTrackingDAO.getNcComplaintTrackingSequence();
    }

    public NcComplaintDAO getNcComplaintDAO() {
        return ncComplaintDAO;
    }

    public void setNcComplaintDAO(NcComplaintDAO ncComplaintDAO) {
        this.ncComplaintDAO = ncComplaintDAO;
    }

    public NcComplaintTrackingDAO getNcComplaintTrackingDAO() {
        return ncComplaintTrackingDAO;
    }

    public void setNcComplaintTrackingDAO(NcComplaintTrackingDAO ncComplaintTrackingDAO) {
        this.ncComplaintTrackingDAO = ncComplaintTrackingDAO;
    }

    public OrderItemProdDAO getOrderItemProdDAO() {
        return orderItemProdDAO;
    }

    public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
        this.orderItemProdDAO = orderItemProdDAO;
    }

    public ProdProductDAO getProdProductDAO() {
        return prodProductDAO;
    }

    public void setProdProductDAO(ProdProductDAO prodProductDAO) {
        this.prodProductDAO = prodProductDAO;
    }

    public NcComplaintRemindDAO getNcComplaintRemindDAO() {
        return ncComplaintRemindDAO;
    }

    public void setNcComplaintRemindDAO(NcComplaintRemindDAO ncComplaintRemindDAO) {
        this.ncComplaintRemindDAO = ncComplaintRemindDAO;
    }

    public NcComplaintDutyDAO getNcComplaintDutyDAO() {
        return ncComplaintDutyDAO;
    }

    public void setNcComplaintDutyDAO(NcComplaintDutyDAO ncComplaintDutyDAO) {
        this.ncComplaintDutyDAO = ncComplaintDutyDAO;
    }

    public NcComplaintDutyDetailsDAO getNcComplaintDutyDetailsDAO() {
        return ncComplaintDutyDetailsDAO;
    }

    public void setNcComplaintDutyDetailsDAO(NcComplaintDutyDetailsDAO ncComplaintDutyDetailsDAO) {
        this.ncComplaintDutyDetailsDAO = ncComplaintDutyDetailsDAO;
    }

    public NcComplaintResultDAO getNcComplaintResultDAO() {
        return ncComplaintResultDAO;
    }

    public void setNcComplaintResultDAO(NcComplaintResultDAO ncComplaintResultDAO) {
        this.ncComplaintResultDAO = ncComplaintResultDAO;
    }
}
