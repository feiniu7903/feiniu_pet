package com.lvmama.tnt.prod.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.CHANNEL_CODE;
import com.lvmama.tnt.comm.vo.TntConstant.OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.TRUE_FALSE;
import com.lvmama.tnt.prod.mapper.TntProductMapper;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.user.mapper.TntChannelMapper;
import com.lvmama.tnt.user.po.TntChannel;

@Repository("tntProductService")
public class TntProductServiceImpl implements TntProductService {

	@Autowired
	private TntProductMapper tntProductMapper;
	@Autowired
	private ProdProductService prodProductService;
	
	@Autowired
	private ProdProductBranchService prodProductBranchService; 
		
	@Autowired
	private TntChannelMapper tntChannelMapper;
	
	@Override
	public void sync(Long objectId,OBJECT_TYPE objectType){
		List<ProdProductBranch> productBranchs = new ArrayList<ProdProductBranch>();
		if(OBJECT_TYPE.PRODUCT.equals(objectType)){
			productBranchs = prodProductBranchService.getProductBranchByProductId(objectId, TRUE_FALSE.FALSE.getValue(),null,true);
		}else if(OBJECT_TYPE.METAPRODUCT.equals(objectType)){
			productBranchs = prodProductBranchService.selectProdBranchByMetaProductId(objectId,true);
		}
		List<TntChannel> channels = tntChannelMapper.selectList(new TntChannel(CHANNEL_CODE.DISTRIBUTOR_B2B.name()));
		for (TntChannel tntChannel : channels) {
			Long channelId = tntChannel.getChannelId();
			for (ProdProductBranch productBranch : productBranchs) {
				Long branchId=productBranch.getProdBranchId();
				Long productId=productBranch.getProductId();
				TntProduct product = new TntProduct(productId, branchId, channelId);
				ProdProduct lvmamaProduct = productBranch.getProdProduct();
				if (lvmamaProduct != null) {
					String payToLvmama = lvmamaProduct.getPayToLvmama();
					String isAperiodic = lvmamaProduct.getIsAperiodic();
					String productName = lvmamaProduct.getProductName();
					TntProduct tntProd = tntProductMapper.selectOne(product);
					if(tntProd != null){
						tntProd.setPayToLvmama(payToLvmama);
						tntProd.setIsAperiodic(isAperiodic);
						tntProd.setProductName(productName);
						tntProductMapper.update(tntProd);
					}else{
						product.setPayToLvmama(payToLvmama);
						product.setIsAperiodic(isAperiodic);
						product.setProductName(productName);
						product.setValid("Y");
						tntProductMapper.insert(product);
					}
				}
			}
		}
	}
	
	
	@Override
	public Map<Long, TntProduct> getBlackListMapByBranchId(Long branchId) {
		List<TntProduct> list = tntProductMapper.getByBranchId(branchId);
		Map<Long, TntProduct> map = null;
		if (list != null && !list.isEmpty()) {
			map = new HashMap<Long, TntProduct>();
			for (TntProduct t : list) {
				if (TntConstant.VALID_N.equals(t.getValid()))
					map.put(t.getChannelId(), t);
			}
		}
		return map;
	}

	@Override
	public boolean pullToBlack(TntProduct t) {
		if (t != null && t.getBranchId() != null && t.getChannelId() != null) {
			if (!isInBlack(t.getBranchId(), t.getChannelId())) {
				t.setValid(TntConstant.VALID_N);
				return tntProductMapper.update(t) > 0;
			}
		}
		return false;
	}

	@Override
	public boolean isInBlack(Long branchId, Long channelId) {
		TntProduct t = new TntProduct();
		t.setBranchId(branchId);
		t.setChannelId(channelId);
		t.setValid(TntConstant.VALID_N);
		return tntProductMapper.selectOne(t) != null;
	}

	@Override
	public boolean pullFromBlack(TntProduct t) {
		t.setValid(TntConstant.VALID_Y);
		tntProductMapper.update(t);
		return true;
	}

	@Override
	public List<TntProduct> search(Page<TntProduct> page) {
		return tntProductMapper.findPage(page);
	}

	@Override
	public long count(TntProduct t) {
		return tntProductMapper.count(t);
	}

	@Override
	public TntProduct getByBranchId(Long branchId) {
		if (branchId != null) {
			List<TntProduct> list = tntProductMapper.getByBranchId(branchId);
			if (list != null)
				return list.get(0);
		}
		return null;
	}

	@Override
	public boolean saveOrUpdateProduct(TntProduct t) {
		boolean syncOk=false;
		if(isInBlack(t.getBranchId(), t.getChannelId())){
			syncOk = tntProductMapper.update(t) > 0;
		}else{
			syncOk = tntProductMapper.insert(t) > 0;
		}
		return syncOk;
	}
}
