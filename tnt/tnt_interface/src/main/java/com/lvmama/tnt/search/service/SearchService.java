package com.lvmama.tnt.search.service;

import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.vst.api.search.vo.SearchResultVo;
import com.lvmama.vst.api.vo.ResultHandleT;


/** 
 * 分销搜索
 * @author gaoxin
 *
 */
public interface SearchService {

	   /**
	    * 门票搜索
	    * @param paramStr 查询条件
	    * @param user 分销商 未登陆时可以为null
	    * @return branch 的valid （是否售卖为Y/N）  distributePrice 分销价
	    */
	   public ResultHandleT<SearchResultVo> searchTicket(String paramStr,TntUser user) throws Exception;

}
