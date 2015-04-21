package com.lvmama.search.lucene.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseVerIbatisDAO;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.AutoCompleteVerHotelCity;

/**
 * 客户端自动补全数据查询
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class AutoCompleteVerDao extends BaseVerIbatisDAO {


	/**
	 * ver酒店自动补全
	 * 
	 * @author huangzhi
	 * @return
	 */
	public List<AutoCompleteVerHotel> getAutoCompleteVerHotel() {
		return (List<AutoCompleteVerHotel>) this.queryForListForReport("AutoCompleteVer.getAutoCompleteVerHotel");
	}

	
	public List<AutoCompleteVerHotelCity> getAutoCompleteVerHotelCities() {
		return (List<AutoCompleteVerHotelCity>) this.queryForListForReport("AutoCompleteVer.selectAllCities");
	}

}