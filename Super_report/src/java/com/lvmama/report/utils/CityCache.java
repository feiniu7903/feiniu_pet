/**
 * 
 */
package com.lvmama.report.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.lvmama.report.po.DicCity;

/**
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class CityCache implements Serializable {

	
	private Map<String, DicCity> cityMap;
	private Map<String, DicCity> capitalMap;
	
	private static CityCache _cityCache;
	
	protected CityCache()
	{
		cityMap=new HashMap<String, DicCity>();
		capitalMap=new HashMap<String, DicCity>();
	}
	
	public static CityCache getInstance()
	{
		if(_cityCache==null)
		{
			_cityCache=new CityCache();
		}
		return _cityCache;
	}
	
	public DicCity getCity(String key)
	{
		if(!cityMap.containsKey(key))
			return null;
		return cityMap.get(key);
	}
	
	public void putCity(DicCity dc)
	{
		if(dc==null)
			return;
		cityMap.put(dc.getId(), dc);		
	}
	
	public DicCity getCapital(String key)
	{
		if(!capitalMap.containsKey(key))
			return null;
		return capitalMap.get(key);
	}
	
	public void putCapital(DicCity dc)
	{
		if(dc==null)
			return;
		capitalMap.put(dc.getId(), dc);
	}
}
