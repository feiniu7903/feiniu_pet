package com.lvmama.ebk.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdRejectInfoDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdRejectInfoDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdRejectInfoDO(EbkProdRejectInfo ebkProdRejectInfoDO) {
        Object REJECT_INFO_ID = super.insert("EBK_PROD_REJECT_INFO.insert", ebkProdRejectInfoDO);
        return (Long) REJECT_INFO_ID;
    }

    /**
     * 统计记录数
     * @param ebkProdRejectInfoDO
     * @return 查出的记录数
     */
    public Integer countEbkProdRejectInfoDOByExample(EbkProdRejectInfo ebkProdRejectInfoDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_REJECT_INFO.countByDOExample", ebkProdRejectInfoDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdRejectInfoDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdRejectInfoDO(EbkProdRejectInfo ebkProdRejectInfoDO) {
        int result = super.update("EBK_PROD_REJECT_INFO.update", ebkProdRejectInfoDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdRejectInfoDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdRejectInfo> findListByExample(EbkProdRejectInfo ebkProdRejectInfoDO) {
        List<EbkProdRejectInfo> list = super.queryForList("EBK_PROD_REJECT_INFO.findListByDO", ebkProdRejectInfoDO);
        return list;
    }

    /**
     * 根据主键获取ebkProdRejectInfoDO
     * @param rejectInfoId
     * @return ebkProdRejectInfoDO
     */
    public EbkProdRejectInfo findEbkProdRejectInfoDOByPrimaryKey(Long rejectInfoId) {
        EbkProdRejectInfo ebkProdRejectInfoDO = (EbkProdRejectInfo) super.queryForObject("EBK_PROD_REJECT_INFO.findByPrimaryKey", rejectInfoId);
        return ebkProdRejectInfoDO;
    }

    /**
     * 删除记录
     * @param rejectInfoId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdRejectInfoDOByPrimaryKey(Long rejectInfoId) {
        Integer rows = (Integer) super.delete("EBK_PROD_REJECT_INFO.deleteByPrimaryKey", rejectInfoId);
        return rows;
    }
    /**
     * 删除记录
     * @param rejectInfoId
     * @return 受影响的行数
     */
    public Integer delete(EbkProdRejectInfo ebkProdRejectInfoDO) {
        Integer rows = (Integer) super.delete("EBK_PROD_REJECT_INFO.delete", ebkProdRejectInfoDO);
        return rows;
    }
}