package com.lvmama.clutter.service.impl.pad.v1_0;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.service.client.v3_2.ClientUserServiceV32;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.vo.Page;

public class ClientUserServicePadV10 extends ClientUserServiceV32{

	@Override
	public Map<String, Object> getBonusInfo(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusInfo(params);
	}

	@Override
	public Map<String, Object> getBonusIncome(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusIncome(params);
	}

	@Override
	public Map<String, Object> getBonusPayment(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusPayment(params);
	}

	@Override
	public Map<String, Object> getBonusRefund(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getBonusRefund(params);
	}

	@Override
	public String getZhComeFrom(String comeFrom) {
		// TODO Auto-generated method stub
		return super.getZhComeFrom(comeFrom);
	}

	@Override
	public Map<String, Object> reSendSmsCert(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.reSendSmsCert(params);
	}

	@Override
	public Map<String, Object> bonusPay(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.bonusPay(params);
	}

	@Override
	public Map<String, Object> bindingCouponToUser(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.bindingCouponToUser(params);
	}

	@Override
	public Map<String, Object> queryCmtWaitForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCmtWaitForOrder(param);
	}

	@Override
	public Map<String, Object> commitOrderComment(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.commitOrderComment(param);
	}

	@Override
	public MobileUser getUser(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.getUser(param);
	}

	@Override
	public List<MobileUserCoupon> getCoupon(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getCoupon(param);
	}

	@Override
	public String subOrder(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.subOrder(param);
	}

	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getOrder(param);
	}

	@Override
	public Map<String, Object> getOrderList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getOrderList(param);
	}

	@Override
	public String addFavorite(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.addFavorite(params);
	}

	@Override
	public boolean cancelFavorite(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancelFavorite(param);
	}

	@Override
	public Map<String, Object> getFavoriteList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getFavoriteList(param);
	}

	@Override
	public String addContact(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.addContact(param);
	}

	@Override
	public String removeContact(Map<String, String> param) {
		// TODO Auto-generated method stub
		return super.removeContact(param);
	}

	@Override
	public List<MobileReceiver> getContact(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getContact(param);
	}

	@Override
	public List<MobileOrderCmt> queryCommentWaitForOrder(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCommentWaitForOrder(param);
	}

	@Override
	public Map<String, Object> queryCommentForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryCommentForOrder(param);
	}

	@Override
	public List<DicCommentLatitude> getCommentLatitudeInfos(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.getCommentLatitudeInfos(param);
	}

	@Override
	public String commitComment(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.commitComment(param);
	}

	@Override
	public Map<String, Object> submitCommitComment(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.submitCommitComment(param);
	}

	@Override
	protected ProdProduct queryProductInfoByProductId(Long productId) {
		// TODO Auto-generated method stub
		return super.queryProductInfoByProductId(productId);
	}

	

}
