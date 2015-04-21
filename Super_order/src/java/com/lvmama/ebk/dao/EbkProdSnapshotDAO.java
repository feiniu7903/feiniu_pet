package com.lvmama.ebk.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdSnapshot;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdSnapshotDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdSnapshotDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdSnapshotDO(EbkProdSnapshot ebkProdSnapshotDO) {
        Object PROD_SNAPSHOT_ID = super.insert("EBK_PROD_SNAPSHOT.insert", ebkProdSnapshotDO);
        return (Long) PROD_SNAPSHOT_ID;
    }

    /**
     * 统计记录数
     * @param ebkProdSnapshotDO
     * @return 查出的记录数
     */
    public Integer countEbkProdSnapshotDOByExample(EbkProdSnapshot ebkProdSnapshotDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_SNAPSHOT.countByDOExample", ebkProdSnapshotDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdSnapshotDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdSnapshotDO(EbkProdSnapshot ebkProdSnapshotDO) {
        int result = super.update("EBK_PROD_SNAPSHOT.update", ebkProdSnapshotDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdSnapshotDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdSnapshot> findListByExample(EbkProdSnapshot ebkProdSnapshotDO) {
        List<EbkProdSnapshot> list = super.queryForList("EBK_PROD_SNAPSHOT.findListByDO", ebkProdSnapshotDO);
        return list;
    }
    /**
     * 获取对象列表-(默认使用主键排序)
     * @param ebkProdSnapshotDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdSnapshot> findListByDOAndOrderByProdSnapshotId(EbkProdSnapshot ebkProdSnapshotDO) {
        List<EbkProdSnapshot> list = super.queryForList("EBK_PROD_SNAPSHOT.findListByDOAndOrderByProdSnapshotId", ebkProdSnapshotDO);
        return list;
    }
    

    /**
     * 根据主键获取ebkProdSnapshotDO
     * @param prodSnapshotId
     * @return ebkProdSnapshotDO
     */
    public EbkProdSnapshot findEbkProdSnapshotDOByPrimaryKey(Long prodSnapshotId) {
        EbkProdSnapshot ebkProdSnapshotDO = (EbkProdSnapshot) super.queryForObject("EBK_PROD_SNAPSHOT.findByPrimaryKey", prodSnapshotId);
        return ebkProdSnapshotDO;
    }

    /**
     * 删除记录
     * @param prodSnapshotId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdSnapshotDOByPrimaryKey(Long prodSnapshotId) {
        Integer rows = (Integer) super.delete("EBK_PROD_SNAPSHOT.deleteByPrimaryKey", prodSnapshotId);
        return rows;
    }

}