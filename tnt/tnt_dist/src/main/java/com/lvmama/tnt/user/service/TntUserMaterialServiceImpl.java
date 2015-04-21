package com.lvmama.tnt.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.mapper.TntUserDetailMapper;
import com.lvmama.tnt.user.mapper.TntUserMaterialMapper;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.po.TntUserMaterial;

@Repository("tntUserMaterialService")
public class TntUserMaterialServiceImpl implements TntUserMaterialService {

	@Autowired
	private TntUserMaterialMapper tntUserMaterialMapper;

	@Autowired
	private TntUserDetailMapper tntUserDetailMapper;

	@Override
	public Long insert(TntUserMaterial tntUserMaterial) {
		tntUserMaterialMapper.insert(tntUserMaterial);
		return tntUserMaterial.getMaterialId();
	}

	@Override
	public void update(TntUserMaterial tntUserMaterial) {
		tntUserMaterialMapper.update(tntUserMaterial);

	}

	@Override
	public List<TntUserMaterial> query(Map<String, Object> map) {
		return tntUserMaterialMapper.query(map);
	}

	@Override
	public int queryCount(Map<String, Object> map) {
		return tntUserMaterialMapper.queryCount(map);
	}

	@Override
	public List<TntUserMaterial> queryByUserId(Long userId) {
		if (userId != null) {
			return tntUserMaterialMapper.queryByUserId(userId);
		}
		return null;
	}

	@Override
	public boolean agree(Long userId, Long materialId) {
		if (userId != null) {
			TntUserMaterial t = new TntUserMaterial(materialId);
			t.setUserId(userId);
			t.setMaterialStatus(TntConstant.USER_MATERIAL_STATUS.AGREE
					.getValue());
			t.setFailReason("");
			if (tntUserMaterialMapper.updateStatus(t) > 0 && materialId == null) {
				TntUserDetail detail = new TntUserDetail();
				detail.setUserId(userId);
				detail.setMaterialStatus(t.getMaterialStatus());
				return tntUserDetailMapper.update(detail) > 0;
			}
		}
		return false;
	}

	@Override
	public boolean reject(Long userId, String reason) {
		if (userId != null) {
			TntUserDetail detail = new TntUserDetail();
			detail.setUserId(userId);
			detail.setMaterialStatus(TntConstant.USER_MATERIAL_STATUS.REJECT
					.getValue());
			detail.setFailReason(reason);
			return tntUserDetailMapper.update(detail) > 0;
		}
		return false;
	}

	@Override
	public boolean singlReject(Long materialId, String reason) {
		if (materialId != null) {
			TntUserMaterial t = new TntUserMaterial(materialId);
			t.setFailReason(reason);
			t.setMaterialStatus(TntConstant.USER_MATERIAL_STATUS.REJECT
					.getValue());
			return tntUserMaterialMapper.updateStatus(t) > 0;
		}
		return false;
	}

}
