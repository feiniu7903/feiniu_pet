package com.lvmama.pet.place.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.vo.Page;
/**
 * 目的地、景点、酒店基础数据操作
 * @author duanshuailiang
 *
 */
public class PlaceDAO extends BaseIbatisDAO{
	
	public Long getNextPlaceId(){
		return (Long) queryForObject("PLACE.getNextPlaceId");
	}
	/**
	 * 增加place
	 * @param place
	 */
	public void insertPlace(Place place) {
		super.insert("PLACE.insert",place);
	}
	public void updatePlace(Place place) {
		super.update("PLACE.updateByPrimaryKeySelective",place);
	}
	/**
	 * 根据placeId获取
	 * @param placeId
	 * @return
	 */
	public Place findByPlaceId(Long placeId){
		return (Place)super.queryForObject("PLACE.findByPlaceId",placeId);
	}
	/**
	 * 根据参数查询符合条件的place列表
	 * 参数支持:是否有效、type类型、名称、iD、使用模板、目的地类型、主题、活动状态
	 * @param parameters
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public List<Place> queryPlaceList(Map<String,Object> param){
		if (null == param || param.isEmpty()) {
			return new ArrayList<Place>(0);
		}
		if (null == param.get("endRows")) {
			param.put("endRows", 100);
		}
		List<Place> placeList=(List<Place>)super.queryForList("PLACE.queryPlaceList",param);
		return placeList;
	}
	/**
	 * 符合条件place数量
	 * @param param
	 * @return
	 */
	public Long countPlaceList(Map<String,Object> param){
		Long totalResultSize = (Long) super.queryForObject("PLACE.countPlaceList", param);
		return totalResultSize;
	}
	/**
	 * 保存排序
	 * @param list
	 */
	public void savePlaceSeq(final List<Map<String,Object>> list){
		super.execute(new SqlMapClientCallback<Object>(){
			@SuppressWarnings("rawtypes")
			public Object doInSqlMapClient(final SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0;i<list.size(); i++) {
					executor.update("PLACE.updatePlaceSeq",(Map)list.get(i));
				}
				executor.executeBatch();
				return null;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> queryPlaceAutocomplate(String word,String stage){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", word);
		param.put("stage", stage);
		return (List<Place>)super.queryForList("PLACE.selectAutoComplatePlaceList",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> getSonPlaceByParentPlaceId(long parentPlaceId, long rownum, Long stage) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentPlaceId", parentPlaceId + "");
		param.put("rownum", rownum + "");
		param.put("stage", stage);
		return super.queryForList("PLACE.getPlaceByParentPlaceId", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> getSonPlaceByPlaceIdAndStage(Place place){
		return (List<Place>)super.queryForList("PLACE.getSonPlaceByPlaceIdAndStage", place);
	}
	
	public long selectSonPlaceCount(Long parentPlaceId, Long stage) {
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("stage", stage); 
		param.put("parentPlaceId", parentPlaceId); 
		return (Long) super.queryForObject("PLACE.selectSonPlaceCount", param);
	}
	
    @SuppressWarnings("unchecked")
	public List<Place> getPlaceBySameParentPlaceId(Long parentPlaceId, Long stage,Long size) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentPlaceId", parentPlaceId);
		param.put("stage", stage);
		param.put("size", size);
		return (List<Place>)super.queryForList("PLACE.getPlaceBySameParentPlaceId", param);
	}
    /**
     * 获取同级place及其关联产品价格信息
     * @param parentPlaceId
     * @param stage
     * @param size
     * @return
     */
    public List<Place> getPlaceInfoBySameParentPlaceId(Long parentPlaceId, String stage,Long size) {
    	return getPlaceInfoBySameParentPlaceId(parentPlaceId,stage,size,null);
    }
    
    @SuppressWarnings("unchecked")
	public List<Place> getPlaceInfoBySameParentPlaceIdTrain(Long parentPlaceId, String stage,Long size) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("parentPlaceId", parentPlaceId);
    	param.put("stage", stage);
    	param.put("size", size);
    	param.put("isTrain",1);
    	return (List<Place>)super.queryForList("PLACE.getPlaceInfoBySameParentPlaceId", param);
    }
    
    /**
     * 获取同级place及其关联产品价格信息 增加一个随机排序
     * @param parentPlaceId
     * @param stage
     * @param size
     * @param placeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Place> getPlaceInfoBySameParentPlaceId(Long parentPlaceId, String stage,Long size,String placeId) {
      Map<String, Object> param = new HashMap<String, Object>();
      param.put("parentPlaceId", parentPlaceId);
      param.put("stage", stage);
      param.put("size", size);
      param.put("placeId", placeId);
      return (List<Place>)super.queryForList("PLACE.getPlaceInfoBySameParentPlaceId", param);
    }
    
    /**
     * 根据条件查找出发地
     * @param map 查询条件
     * @return 出发地列表
     * 根据条件查找出发地列表
     */
	@SuppressWarnings("unchecked")
	public List<Place> getFromPlace(final Map<String, Object> map) {
		return super.queryForList("PLACE.getFromPlace", map);
	}
	
	/**
	 * 根据parentPlaceIds获取景点信息
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Place> getPlaceByParentIds(Map<String,List> param){
		return super.queryForListForReport("PLACE.getPlaceByParentIds", param);
		
	}
	/**
	 * 获取所有国家记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<Place> getCountryRecord(){
		return super.queryForList("PLACE.getCountryRecord", null);
	}
	
	public Place getPlaceByName(String name,String valid){
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("name", name);
		map.put("valid", valid);
		Place place=(Place)super.queryForObject("PLACE.getPlaceByName",map);
		return place;
	}
	
	public Place getPlaceByPinYin(String pinYin) {
		return (Place)super.queryForObject("PLACE.getPlaceByPinYin",pinYin);
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> getPlaceByProductId(Long productId) {
		return (List<Place>)super.queryForList("PLACE.getPlaceByProductId",productId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> getCountryByParentPlaceId(Long parentPlaceId, Integer startRow, Integer endRow) { 
	Map<String, Object> map = new HashMap<String, Object>(); 
	map.put("parentPlaceId", parentPlaceId); 
	map.put("startRow", startRow); 
	map.put("endRow", endRow); 
	List<Place> list = super.queryForList("PLACE.getCountryByDestId", map); 
	return list; 
	}
	public List<Place> selectSuggestPlaceByName(String name) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", name);
		//添加该逻辑主要是为了解决填写多个地址时取不到值.
		if(StringUtils.isNotEmpty(name)&&name.trim().matches("\\d+")){
			map.put("placeId", name);
		}
		return super.queryForList("PLACE.selectSuggestPlace", map);
	}
	public List<Place> selectSuggestPlaceByNameEBK(String name) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", name);
		//添加该逻辑主要是为了解决填写多个地址时取不到值.
		if(StringUtils.isNotEmpty(name)&&name.trim().matches("\\d+")){
			map.put("placeId", name);
		}
		return super.queryForList("PLACE.selectSuggestPlaceEBK", map);
	}
	public List<Place> getRootDest(){
		return super.queryForList("PLACE.selectRootDest");
	}
	public List<Place> selectDestByRootId(Long id) {
		return super.queryForList("PLACE.selectDestByRootId", id);
	}
	public Place getToDestByProductId(Long productId){
		return (Place) super.queryForObject("PLACE.getToDestByProductId",productId);
	}
	
	/**
	 * 网站地图相关
	 */
	@SuppressWarnings("unchecked")
	public Page<Place> getAllPlaceJD(int pageSize, int currentPage) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("stage",2);
		return pageQuery(pageSize,currentPage,"PLACE.getPlaceToWebSite",param);
	}

	@SuppressWarnings("unchecked")
	public Page<Place> getAllPlaceGNY(int pageSize, int currentPage) {
		return pageQuery(pageSize,currentPage,"PLACE.getAllPlaceGNY",null);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Place> getAllPlaceCJY(int pageSize, int currentPage) {
		return pageQuery(pageSize,currentPage,"PLACE.getAllPlaceCJY",null);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Place> getAllPlaceHotel(int pageSize, int currentPage) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("hotelBooking","Y");//代表酒店预订
		return pageQuery(pageSize,currentPage,"PLACE.getPlaceToWebSite",param);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Place> getAllPlacePlace(int pageSize, int currentPage) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("stage",1);
		return pageQuery(pageSize,currentPage,"PLACE.getPlaceToWebSite",param);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Place> getAllPlaceDP(int pageSize, int currentPage) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("stage",3);
		return pageQuery(pageSize,currentPage,"PLACE.getPlaceToWebSite",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<Place> queryPlaceAllMap(Map<String,Object> param) {
		return (List<Place>)super.queryForList("PLACE.getPlaceToWebSiteItems",param);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Page pageQuery(int pageSize, int currentPage,String statement,Map<String,Object> map){
		Integer pageCount=(Integer) super.queryForObject(statement+"Count",map);
		Page<Place> pageConfig=new Page<Place>(pageCount,pageSize,currentPage);
		if(map==null){
			map=new HashMap<String,Object>();
		}
		map.put("startRows", pageConfig.getStartRows());
		map.put("endRows", pageConfig.getEndRows());
		pageConfig.setItems(super.queryForList(statement+"Items",map));
		return pageConfig;
	}
	
	public List<Place> getPlaceByProductIdAndStage(Long productId,Long stage){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("productId", productId);
		map.put("stage", stage);
		return queryForList("PLACE.getPlaceByProductIdAndStage",map);
	}
	public Place queryPlaceAndComSearchTranscodeByPlaceId(Long param) {
 		return  (Place)super.queryForObject("PLACE.queryPlaceAndComSearchTranscodeByPlaceId",param);
	}
	public Place getDescripAndTrafficByPlaceId(long placeId) {
 		return  (Place) super.queryForObject("PLACE.getDescripAndTrafficByPlaceId", placeId);
	}
	public Place getPlaceByPinYinWithOutCLOB(String pinYin) {
 		return (Place)super.queryForObject("PLACE.getPlaceByPinYinWithOutCLOB",pinYin);
	}
	public Place queryPlaceByPlaceIdWithOutCLOB(long placeId) {
 		return (Place)super.queryForObject("PLACE.queryPlaceByPlaceIdWithOutCLOB",placeId);
	}
	
	public List<Place> getListPlaceByParentIds(Long parentPalceId){
	    return 	(List<Place>)super.queryForList("PLACE.getListPlaceByParentIds",parentPalceId);
	}
	
	public void updateHasSensitiveWordByPlaceId(Long placeId, String hasSensitiveWord) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("placeId", placeId);
    	params.put("hasSensitiveWord", hasSensitiveWord);
    	super.update("PLACE.updateHasSensitiveWordByPlaceId", params);
    }
}