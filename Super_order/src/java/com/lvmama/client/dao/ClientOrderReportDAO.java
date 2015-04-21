package com.lvmama.client.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.client.ClientOrderReport;

public class ClientOrderReportDAO extends BaseIbatisDAO {

    public ClientOrderReportDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long id) {
        ClientOrderReport key = new ClientOrderReport();
        key.setId(id);
        int rows = super.delete("CLIENT_ORDER_REPORT.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ClientOrderReport record) {
        Object newKey = super.insert("CLIENT_ORDER_REPORT.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(ClientOrderReport record) {
        Object newKey = super.insert("CLIENT_ORDER_REPORT.insertSelective", record);
        return (Long) newKey;
    }

    public ClientOrderReport selectByPrimaryKey(Long id) {
        ClientOrderReport key = new ClientOrderReport();
        key.setId(id);
        ClientOrderReport record = (ClientOrderReport) super.queryForObject("CLIENT_ORDER_REPORT.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ClientOrderReport record) {
        int rows = super.update("CLIENT_ORDER_REPORT.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ClientOrderReport record) {
        int rows = super.update("CLIENT_ORDER_REPORT.updateByPrimaryKey", record);
        return rows;
    }
    
    public Long countUdidOrder(String udid) {
    	Long count = (Long) super.queryForObject("CLIENT_ORDER_REPORT.countByUdid", udid);
    	return count;
    }
    public List<ClientOrderReport> getTodayOrderByUdid(String udid){
    	return super.queryForList("CLIENT_ORDER_REPORT.getTodayOrderByUdid", udid);
    }
}