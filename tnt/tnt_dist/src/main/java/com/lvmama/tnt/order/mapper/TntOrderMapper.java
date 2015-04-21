package com.lvmama.tnt.order.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.order.po.TntOrder;

/**
 * 分销订单
 * 
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntOrderMapper {

	public void insert(TntOrder entity);

	public List<TntOrder> findPage(Page<TntOrder> page);

	public int count(TntOrder entity);

	public int update(TntOrder tntOrder);

	public int updateStatus(TntOrder tntOrder);

	public int delete(Long tntOrderId);

	public TntOrder getById(Long tntOrderId);

	public TntOrder getByOrderId(Long orderId);

	public TntOrder selectOne(TntOrder entity);

}
