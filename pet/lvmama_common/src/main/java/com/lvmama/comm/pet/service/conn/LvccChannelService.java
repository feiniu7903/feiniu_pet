package com.lvmama.comm.pet.service.conn;

import java.util.List;
import com.lvmama.comm.pet.po.conn.LvccChannel;

/**
 * 市场部推广活动渠道
 * @author shihui
 *
 */
public interface LvccChannelService {
	Long insert(LvccChannel record);
	
	void delete(Long channelId);
	
	List<LvccChannel> selectAll() ;
	
	List<LvccChannel> selectByIds(Long[] channelIds);
	
	boolean checkNameIsExsited(LvccChannel channel);
}
