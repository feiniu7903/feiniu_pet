package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.comm.vo.Constant;

public class SeoUtils {

	public static String getPlaceSeoTitle(String seoTitle, String seoIndexPageRegular) {
		if (seoTitle!=null&&!"".equals(seoTitle)) {
			return seoTitle;
		} else {
			return seoIndexPageRegular = seoIndexPageRegular == null ? "" : seoIndexPageRegular;
		}
	}

	public static String getPlaceSeoKeyword(String seoKeyword, String seoIndexPageRegular) {
		if (seoKeyword!=null&&!"".equals(seoKeyword)) {
			return seoKeyword;
		} else {
			return seoIndexPageRegular = seoIndexPageRegular == null ? "" : seoIndexPageRegular;
		}
	}

	public static String getPlaceSeoDescription(String seoDescription, String seoIndexPageRegular) {
		if (seoDescription!=null&&!"".equals(seoDescription)) {
			return seoDescription;
		} else {
			return seoIndexPageRegular = seoIndexPageRegular == null ? "" : seoIndexPageRegular;
		}
	}
	
	public static String getSeoIndexPageRegular(PlaceVo placeVo,String seoStr){
		String result=seoStr==null?"":seoStr;
		if(placeVo!=null&&placeVo.getPlace()!=null&&seoStr!=null){
		  Place place=placeVo.getPlace();
		  result=seoStr.replace( "{name}",place.getName()==null?"":place.getName())
				  .replace("{seoName}",place.getSeoName()==null?place.getName():place.getSeoName())
				  .replace("{placeType}",place.getPlaceType()==null?"":place.getPlaceType())
				  .replace("{remarkes}",place.getRemarkes()==null?"":place.getRemarkes())
				  .replace("{address}",place.getAddress()==null?"":place.getAddress())
				  .replace("{firstTopic}",place.getFirstTopic()==null?"":place.getFirstTopic())
				  .replace("{hotelStar}",place.getHotelStar()==null?"":place.getHotelStar())
				  .replace("{hotelType}",place.getHotelType()==null?"":place.getHotelType());
		  if(placeVo.getParentPlace()!=null){
			  result=result.replace("{parentName}",placeVo.getParentPlace().getName()==null?"":placeVo.getParentPlace().getName());
		  }
		}
		return result;
	}
	
	public static String getSeoIndexPageHoliday(PlaceSearchInfo search,String seoStr){
		String result=seoStr==null?"":seoStr;
		if(search!=null&&seoStr!=null){
		  result=seoStr.replace("{name}",search.getName()==null?"":search.getName());
		}
		return result;
	}

	public static String getFromPlaceSeoIndexPageRegular(Place fromPlace, String seoStr) {
		String result=seoStr==null?"":seoStr;
		String fromPlaceName="";
		if(fromPlace!=null){
			fromPlaceName=fromPlace.getName()==null?"":fromPlace.getName();
		}
		result=seoStr.replace( "{fromPlaceName}",fromPlaceName);
		return result;
	}
	
	private static String[] queryLocationTypeByStageDest={Constant.PLACE_SEOLINKS.INDEX.getCnName(),Constant.PLACE_SEOLINKS.INDEX.name()};
    public static List<CodeItem> queryLocationTypeByStageDest(){
        return getList(queryLocationTypeByStageDest);
    }
	
	private static String[] queryLocationTypeByStageScenic={Constant.PLACE_SEOLINKS.INDEX.getCnName(),Constant.PLACE_SEOLINKS.INDEX.name()};
    public static List<CodeItem> queryLocationTypeByStageScenic(){
        return getList(queryLocationTypeByStageScenic);
    }
    
    private static String[] queryLocationTypeByStageHotel={Constant.PLACE_SEOLINKS.INDEX.getCnName(),Constant.PLACE_SEOLINKS.INDEX.name()};
    public static List<CodeItem> queryLocationTypeByStageHotel(){
        return getList(queryLocationTypeByStageHotel);
    }
    
    private static List<CodeItem> getList(String subProductTypes[]){
        List<CodeItem> list=new ArrayList<CodeItem>();
        for(int i=0;i<subProductTypes.length;i+=2){
            list.add(new CodeItem(subProductTypes[i+1], subProductTypes[i]));
        }
        return list;
    }
}
