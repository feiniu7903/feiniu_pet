package com.lvmama.pet.pub.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComFSConfig;
import com.lvmama.comm.pet.po.pub.ComFSFile;
import com.lvmama.comm.pet.po.pub.ComFSServiceConfig;

/**
 * 文件系统DAO实现类.
 * @author sunruyi
 */
public class ComFSDAO extends BaseIbatisDAO {
	/**
	 * 根据服务类型查询文件系统服务配置.
	 * @param serverType 服务类型.
	 * @return ComFSServiceConfig.
	 */
	public ComFSServiceConfig selectComFSServiceConfigByServerType(String serverType) {
		return (ComFSServiceConfig) super.queryForObject("COM_FS.selectComFSServiceConfigByServerType", serverType);
	}
	/**
	 * 根据ID查询文件系统配置.
	 * @param id ID.
	 * @return ComFSConfig.
	 */
	public ComFSConfig selectFSCofigById(Long id) {
		return (ComFSConfig) super.queryForObject("COM_FS.selectFSCofigById", id);
	}
	/**
	 * 根据ID查询文件系统文件.
	 * @param id ID.
	 * @return ComFSFile.
	 */
	public ComFSFile selectComFSFileById(Long id) {
		return (ComFSFile) super.queryForObject("COM_FS.selectComFSFileById", id);
	}
	/**
	 * 插入文件系统文件.
	 * @param ComFSFile ComFSFile.
	 * @return Long.
	 */
	public Long insertComFSFile(ComFSFile ComFSFile) {
		return (Long) super.insert("COM_FS.insertComFSFile", ComFSFile);
	}

}
