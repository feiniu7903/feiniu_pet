package com.lvmama.search.lucene.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.BaseVerIbatisDAO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean; 
import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.search.vo.VerPlaceTypeVO;
@SuppressWarnings("unchecked")
@Repository
public class IndexVerDAO  extends BaseVerIbatisDAO {
	
	private static final Logger logger=Logger.getLogger(IndexVerDAO.class);

    /*private SqlMapClient sqlMapClient;
    
    @PostConstruct
    public void initSqlMapClient() {  
        super.setSqlMapClient(sqlMapClient);
    } */
	
	
	public List<VerHotelBean> getVerhotelIndexDate(int beginRow, int endRow) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", beginRow);    
		paramMap.put("endRow", endRow);    
//		String id[]={"80039"};
//		paramMap.put("placeId", id); 
		return this.queryForList("createIndex.getVerHotelIndex", paramMap);
	}

	public Integer countVerHotelIndex() {
		Integer count = (Integer) this.queryForObject("createIndex.countVerHotelIndex", null);
		return count;
	}

	public int countVerPlaceIndex() {
		Integer count = (Integer) this.queryForObject("createIndex.countVerPlaceIndex", null);
		return count;
	}

	public List<VerPlaceBean> getVerplaceIndexDate(int beginRow, int endRow) {
		Map<String,Object> paramMap = new HashMap<String,Object>(); 
		paramMap.put("beginRow", beginRow);    
		paramMap.put("endRow", endRow);    
//		String id[]={"80039"};
//		paramMap.put("placeId", id); 
		return this.queryForList("createIndex.getVerPlaceIndex", paramMap);
	}
	
	public List<VerPlaceBean> getVerPlaceByID(String placeId){
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("placeId", placeId);
		return this.queryForList("createIndex.getVerPlace", paramMap);
	}
	
	public List<VerPlaceTypeVO> getPlaceCatageory(HashMap paramMap) {
		
		return this.queryForList("createIndex.getPlaceCatageory", paramMap);
	}
	
	/*public Object queryForObject(String statementName, Object para){ 
		return getSqlMapClientTemplate().queryForObject(statementName, para);
	}

	public List queryForList(String statementName, Object para) { 
		return getSqlMapClientTemplate().queryForList(statementName, para);
	}*/

	
}
