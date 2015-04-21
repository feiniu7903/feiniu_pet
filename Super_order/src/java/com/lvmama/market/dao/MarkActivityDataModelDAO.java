package com.lvmama.market.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkActivityDataModel;
import com.lvmama.comm.pet.po.mark.MarkActivityUserData;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-31<p/>
 * Time: 下午2:31<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityDataModelDAO extends BaseIbatisDAO {

    public List<MarkActivityDataModel> getMarkActivityDataModelList() {
        return super.queryForList("MARK_ACTIVITY_DATA_MODEL.selectMarkActivityDataModelList");
    }

    public Long getDataModelTotal(String guid) {
        return (Long) super.queryForObject("MARK_ACTIVITY_DATA_MODEL.selectDataModelTotal", guid);
    }

    public String getDataModelLastGuid(String dataModel) {
        return (String)super.queryForObject("MARK_ACTIVITY_DATA_MODEL.selectDataModelLastGuid",dataModel);
    }

    public List<MarkActivityUserData> getMailListByGuid(Map<String, Object> paramMap) {
        return super.queryForList("MARK_ACTIVITY_DATA_MODEL.selectMailListByGuid",paramMap,true);
    }
}
