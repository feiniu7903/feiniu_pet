/**
 * 
 */
package com.lvmama.service;

import org.apache.jcs.JCS;

import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.train.TrainKey;

/**
 * @author lancey
 *
 */
public class TrainCacheManager {
	
	private static TrainCacheManager instance;
	private JCS cache;
	
	public static TrainCacheManager getInstance(){
		if(null == instance){
			instance = new TrainCacheManager();
		}
		return instance;
	}
	
	private TrainCacheManager(){
		try {
			cache = JCS.getInstance("train");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ProdTrainCache get(final TrainKey key){
		Object obj = cache.get(key.toString());
//		cache.get
		if(obj==null){
			return null;
		}
		ProdTrainCache cache = (ProdTrainCache)obj;
		return cache;
	}
	
	public void set(final TrainKey key,final ProdTrainCache train){
		try {
			cache.put(key.toString(), train);
		} catch (org.apache.jcs.access.exception.CacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean exists(final TrainKey key){
		return get(key)!=null;
	}
	
	public void flush(){
		cache.dispose();
	}
	
	
	public static void main(String[] args) throws Exception{
		TrainKey key=null;
		Station2StationInfo ss = new Station2StationInfo();
		for(int i=0;i<100;i++){
		ss.setDepart_station("上海");
		ss.setArrive_station("北京");
		ss.setTrain_id("1234"+i);
		key = TrainKey.newTrainKey(ss, "203");
		ProdTrainCache cache = new ProdTrainCache();
//		cache.setCategory("112233"+i);
//		getInstance().set(key, cache);
		}
		
		ProdTrainCache cache = getInstance().get(key);
//		getInstance().cache.dispose();
		System.out.println(cache.getCategory());
	}
	
}
