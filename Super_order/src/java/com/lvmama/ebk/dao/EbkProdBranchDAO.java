package com.lvmama.ebk.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import java.util.List;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdBranchDAO extends BaseIbatisDAO{

    /**
     * 插入数据
     * @param ebkProdBranchDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdBranchDO(EbkProdBranch ebkProdBranchDO) {
        Object PROD_BRANCH_ID = super.insert("EBK_PROD_BRANCH.insert", ebkProdBranchDO);
        return (Long) PROD_BRANCH_ID;
    }

    /**
     * 统计记录数
     * @param ebkProdBranchDO
     * @return 查出的记录数
     */
    public Integer countEbkProdBranchDOByExample(EbkProdBranch ebkProdBranchDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_BRANCH.countByDOExample", ebkProdBranchDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdBranchDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdBranchDO(EbkProdBranch ebkProdBranchDO) {
        int result = super.update("EBK_PROD_BRANCH.update", ebkProdBranchDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdBranchDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdBranch> findListByTerm(EbkProdBranch ebkProdBranchDO) {
        List<EbkProdBranch> list = super.queryForList("EBK_PROD_BRANCH.findListByDO", ebkProdBranchDO);
        return list;
    }

    /**
     * 根据主键获取ebkProdBranchDO
     * @param prodBranchId
     * @return ebkProdBranchDO
     */
    public EbkProdBranch findEbkProdBranchDOByPrimaryKey(Long prodBranchId) {
        EbkProdBranch ebkProdBranchDO = (EbkProdBranch) super.queryForObject("EBK_PROD_BRANCH.findByPrimaryKey", prodBranchId);
        return ebkProdBranchDO;
    }

    /**
     * 删除记录
     * @param prodBranchId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdBranchDOByPrimaryKey(Long prodBranchId) {
        Integer rows = (Integer) super.delete("EBK_PROD_BRANCH.deleteByPrimaryKey", prodBranchId);
        return rows;
    }

}