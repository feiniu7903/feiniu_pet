package com.lvmama.pet.place.service;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePlaceDest;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.pet.place.dao.PlacePlaceDestDAO;

class PlacePlaceDestServiceImpl implements PlacePlaceDestService {
	private PlacePlaceDestDAO placePlaceDestDAO;

	public void setPlacePlaceDestDAO(PlacePlaceDestDAO placePlaceDestDAO) {
		this.placePlaceDestDAO = placePlaceDestDAO;
	}

	public List<PlaceVo> calculationPlaceSuperior(List<Place> placeList) {
		List<PlaceVo> list = new ArrayList<PlaceVo>();
		if (placeList != null && placeList.size() > 0) {
			PlaceVo placeVo = null;
			for (Place place : placeList) {
				placeVo = new PlaceVo();
				placeVo.setPlace(place);
//				String main=queryPlaceSuperior(place.getPlaceId(),place.getName());
//				placeVo.setPlaceSuperior(main);
				list.add(placeVo);
			}
		}
		return list;
	}
	
	public String queryPlaceSuperior(Long placeId,String currentPlaceName) {
		List<PlacePlaceDest> list=placePlaceDestDAO.calculationPlaceSuperior(placeId);
		StringBuffer resultMainList=new StringBuffer("");
		if(list!=null&&list.size()>0){
			List<PlacePlaceDest> main=new ArrayList<PlacePlaceDest>();
			for(PlacePlaceDest placePlaceDest:list){
				if(placePlaceDest.getPlaceId().longValue()==placeId.longValue()){
					main.add(placePlaceDest);
				}
			}
			if(main!=null&&main.size()>0){
				for(PlacePlaceDest mainDest:main){
					if(!"".equals(resultMainList.toString()))
						resultMainList.append("|");
					if(currentPlaceName!=null&&!"".equals(currentPlaceName)){
						resultMainList.append(currentPlaceName+","+mainDest.getParentPlaceName()+",");
					}else{
						resultMainList.append(mainDest.getParentPlaceName()+",");
					}
					processPlacePlaceDestByParentId(mainDest,list,resultMainList);
					if(mainDest.getIsMaster()!=null&&!"".equals(mainDest.getIsMaster())){
						if(mainDest.getIsMaster().equals("Y")){//计算主层级关系
							resultMainList.append("Y");
						}else{
							resultMainList.append("N");
						}
					}else{
						resultMainList.append("N");
					}
					
				}
			}
		}
		StringBuffer result=new StringBuffer("");
		String resultStr=null;
		if(resultMainList.toString()!=null&&!"".equals(resultMainList.toString())){
			String[] placeArray=resultMainList.toString().split("[|]");
			StringBuffer sb=null;
			for(String placeStr:placeArray){
				if(placeStr!=null&&!"".equals(placeStr)){
					sb=new StringBuffer("");
					String[] temp=placeStr.split(",");
					if(temp!=null&&temp.length>0){
						temp=flipArray(temp);
						for(int i=1;i<temp.length;i++){
							sb.append(temp[i]);
							if(i!=temp.length)
								sb.append(">");
						}
						if(resultStr==null){
							resultStr=sb.toString();
						}else{
							if(temp[0].equals("Y")){
								resultStr=sb.toString()+resultStr;
							}else{
								resultStr=resultStr+sb.toString();
							}
						}
						if(sb.toString()!=null&&!"".equals(sb.toString())){
							result.append(sb.toString().substring(0,sb.toString().length()-1)+",");
						}
					}
					
				}
			}
		}
		if(result.toString()!=null&&!"".equals(result.toString())){
			return result.toString().substring(0,result.toString().length()-1);
		}else{
			return null;
		}
	}
	private StringBuffer delStr(StringBuffer source){
		StringBuffer sb=new StringBuffer("");
		if(source.toString()!=null&&!"".equals(source.toString())){
			sb.append(source.toString().substring(0,source.toString().length()-1));
		}
		return sb;
	}
	//递归获取上级目的地
	private void processPlacePlaceDestByParentId(PlacePlaceDest currentPlace,List<PlacePlaceDest> list,StringBuffer sourceList){
		PlacePlaceDest result=null;
		for(PlacePlaceDest temp:list){
			if(currentPlace.getParentPlaceId().equals(temp.getPlaceId())){
				result=temp;
				sourceList.append(result.getParentPlaceName());
				break;
			}
		}
		if(result!=null){
			sourceList.append(",");
			processPlacePlaceDestByParentId(result,list,sourceList);
		}
		
	}
	//字符串数组反转
	private String[] flipArray(String[] sourceArray){
		if(sourceArray!=null&&sourceArray.length>0){
			String[] resultArray=new String[sourceArray.length];
			for(int n=0, i=sourceArray.length;i>0;i--,n++){
				resultArray[n]=sourceArray[i-1];
			}
			return resultArray;
		}else{
			return null;
		}
	}
	@Override
	public List<PlacePlaceDest> queryParentPlaceList(Long placeId) {
		return placePlaceDestDAO.queryParentPlaceList(placeId);
	}

	/**
	 * 获得此目的地的所有父级Id
	 * @param placeId
	 * @return
	 */
	@Override
	public List<Long> selectParentPlaceIdList(Long placeId) {
		return placePlaceDestDAO.selectParentPlaceIdList(placeId);
	}
	
	@Override
	public boolean isExistInChildrenLevelTree(long placeId, long parentPlaceId) {
		return placePlaceDestDAO.isExistInChildrenLevelTree(placeId, parentPlaceId);
	}

	@Override
	public boolean isExistInParentLevelTree(long placeId, long parentPlaceId) {
		return placePlaceDestDAO.isExistInParentLevelTree(placeId, parentPlaceId);
	}

	@Override
	public void saveOrUpdate(PlacePlaceDest placePlaceDest) {
		placePlaceDestDAO.saveOrUpdate(placePlaceDest);
	}

	@Override
	public void deletePlacePlaceDest(PlacePlaceDest placePlaceDest) {
		placePlaceDestDAO.deletePlacePlaceDest(placePlaceDest);
	}

	@Override
	public void updateMaster(PlacePlaceDest placePlaceDest) {
		placePlaceDestDAO.updateMaster(placePlaceDest);
	}
	@Override
	public  PlacePlaceDest  selectByPrimaryKey(Long placePlaceDestId) {
 			return placePlaceDestDAO.selectByPrimaryKey(placePlaceDestId);
	}

}
