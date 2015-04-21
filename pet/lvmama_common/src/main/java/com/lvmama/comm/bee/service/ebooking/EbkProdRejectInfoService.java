package com.lvmama.comm.bee.service.ebooking;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;

public interface EbkProdRejectInfoService {
	/**
     * 插入数据
     * @param ebkProdRejectInfoDO
     * @return 插入数据的主键
     */
    public Long insert(EbkProdRejectInfo ebkProdRejectInfoDO);
    /**
     * 批量插入数据
     * @author ZHANG Nan
     * @param ebkProdRejectInfoDOList
     * @return
     */
    public List<Long> insertList(Long ebkProdProductId,List<EbkProdRejectInfo> ebkProdRejectInfoDOList);
    /**
     * 更新记录
     * @param ebkProdRejectInfoDO
     * @return 受影响的行数
     */
    public Integer update(EbkProdRejectInfo ebkProdRejectInfoDO);
    /**
     * 删除记录
     * @param rejectInfoId
     * @return 受影响的行数
     */
    public Integer delete(EbkProdRejectInfo ebkProdRejectInfoDO);
    /**
     * 查询记录
     * @param ebkProdRejectInfoDO
     * @return 列表
     */
	public List<EbkProdRejectInfo> query(EbkProdRejectInfo ebkProdRejectInfoDO);
}
