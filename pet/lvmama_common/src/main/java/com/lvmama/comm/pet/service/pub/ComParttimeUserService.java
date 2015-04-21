package com.lvmama.comm.pet.service.pub;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;

public interface ComParttimeUserService {
	/**
     * 根据渠道类型或渠道标识查询促销员与渠道关系
     * @param parameters
     * @return
     */
    public List<ComParttimeUser> query(Map<String,Object> parameters);
    /**
     * 根据渠道类型或渠道标识查询促销员与渠道关系总数
     * @param parameters
     * @return
     */
    public long count(Map<String,Object> parameters);
    /**
     * 新增促销员与渠道关系信息
     * @param parameters
     */
    public Long insert(ComParttimeUser user);
    /**
     * 根据促销员ID更新用户与渠道关系信息
     * @param parameters
     * @return
     */
    public void update(ComParttimeUser user);
    
    /**
     * 注销人员登录
     * @param userName 用户名
     * @param password 密码
     */
    ComParttimeUser login(String userName, String password);
    
    /**
     * 根据主键查找用户
     * @param id 序列值
     */
    ComParttimeUser queryByPk(Serializable id);    
   
}
