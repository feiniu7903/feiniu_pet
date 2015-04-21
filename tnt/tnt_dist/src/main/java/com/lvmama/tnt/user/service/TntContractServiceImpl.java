package com.lvmama.tnt.user.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.mapper.TntContractAttachMapper;
import com.lvmama.tnt.user.mapper.TntContractMapper;
import com.lvmama.tnt.user.po.TntContract;
import com.lvmama.tnt.user.po.TntContractAttach;

@Repository("tntContractService")
public class TntContractServiceImpl implements TntContractService {

	@Autowired
	private TntContractMapper tntContractMapper;

	@Autowired
	private TntContractAttachMapper tntContractAttachMapper;

	@Override
	public List<TntContract> queryByUserId(Long userId) {
		if (userId != null) {
			TntContract t = new TntContract();
			t.setUserId(userId);
			t.setStatus(TntContract.CONTACT_STATUS.ACTIVATE.getValue());
			return tntContractMapper.selectListWithAttach(t);
		}
		return null;
	}

	@Override
	public boolean deleteById(Long contractId) {
		if (contractId != null) {
			TntContract t = new TntContract();
			t.setStatus(TntContract.CONTACT_STATUS.DELETED.getValue());
			t.setContractId(contractId);
			return tntContractMapper.updateStatus(t) > 0;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean uploadContract(TntContract tntContract) {
		if (tntContract != null) {
			String fileName = tntContract.getContractName();
			tntContract.setStatus(TntContract.CONTACT_STATUS.ACTIVATE
					.getValue());
			if (tntContractMapper.insert(tntContract) > 0) {
				TntContractAttach t = new TntContractAttach();
				t.setContractId(tntContract.getContractId());
				t.setAttachment(tntContract.getAttachment());
				t.setFileName(fileName);
				return tntContractAttachMapper.insert(t) > 0;
			}
		}
		return false;
	}

	@Override
	public String getContractName(String fileName) {
		int index = fileName.lastIndexOf(".");
		String suffix = "";
		if (index != -1) {
			suffix = fileName.substring(index, fileName.length());
			fileName = fileName.substring(0, index);
		}
		fileName = fileName + "."
				+ DateUtil.formatDate(new Date(), DateUtil.PATTERN_yyyyMMdd)
				+ suffix;
		return fileName;
	}

	@Override
	public List<TntContract> pageQuery(Page<TntContract> page) {
		return tntContractMapper.fetchPageWithAttach(page);
	}

	@Override
	public int count(TntContract tntContract) {
		return tntContractMapper.count(tntContract);
	}

}
