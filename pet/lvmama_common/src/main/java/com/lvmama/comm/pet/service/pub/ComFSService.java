package com.lvmama.comm.pet.service.pub;

import com.lvmama.comm.pet.po.pub.ComFSConfig;
import com.lvmama.comm.pet.po.pub.ComFSFile;
import com.lvmama.comm.pet.po.pub.ComFSServiceConfig;

/**
 * 文件系统客户端接口.
 * @author Libo Wang
 */
public interface ComFSService {
	
	public ComFSServiceConfig selectComFSServiceConfigByServerType(String serverType);
	
	/**
	 * 根据ID查询文件系统配置.
	 * @param id ID.
	 * @return ComFSConfig.
	 */
	public ComFSConfig selectFSCofigById(Long id);
	/**
	 * 根据ID查询文件系统文件.
	 * @param id ID.
	 * @return ComFSFile.
	 */
	public ComFSFile selectComFSFileById(Long id);
	/**
	 * 插入文件系统文件.
	 * @param ComFSFile ComFSFile.
	 * @return Long.
	 */
	public Long insertComFSFile(ComFSFile ComFSFile);
}
