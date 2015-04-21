package com.lvmama.search.bigData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.KeyValue;
//import org.apache.hadoop.hbase.client.Get;
//import org.apache.hadoop.hbase.client.HTablePool;
//import org.apache.hadoop.hbase.client.Result;

public class HbaseData {
	
//	public static Configuration conf;
	
	
//	static {
//		conf = HBaseConfiguration.create();
//		conf.set("hadoop.tmp.dir", "/home/hadoop/data/dfs/tmp");
//	}
	
//	public static Result rowResult(String tableName,String rowKey) throws IOException
//	{
//		Get get = new Get(rowKey.getBytes("utf-8"));
//		@SuppressWarnings("resource")
//		HTablePool hTablePool = new HTablePool(conf,1000);
//		Result result = hTablePool.getTable(tableName).get(get);
//		return result;
//	}
	
	/**
	 * 查询一行固定列簇下所有列
	 * 
	 * @param tableName 表名
	 * @param rowKey 行健
	 * @param family 列簇
	 * @return 列及其对应的值
	 * @throws IOException
	 */
	public static Map<String,String> query(String tableName, String rowKey, String family) throws IOException 
	{
//		Result result = rowResult(tableName,rowKey);
//		
//		if(result.isEmpty())
//		{
//			return null;
//		}
//		
//		Map<String,String> product2Score = new HashMap<String,String>();
//		
//		/*Iterator<Map.Entry<byte[], byte[]>> iter =  result.
//			getFamilyMap(family.getBytes()).entrySet().iterator();
//		
//		while(iter.hasNext())
//		{
//			Map.Entry<byte[], byte[]> entry = iter.next();
//			product2Score.put(new String(entry.getKey(), "utf-8"), new String(entry.getValue(), "utf-8"));
//		}*/
//		
//		for(KeyValue kv:result.raw()){ 
////			System.out.println("column: "+new String(kv.getQualifier())); 
////			System.out.println("value: "+new String(kv.getValue())); 
//			
//			product2Score.put(new String(kv.getQualifier()), new String(kv.getValue()));
//			
//		} 
//		
//		return product2Score;
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param tableName
	 * @param rowkey
	 * @param family
	 * @param productIDs
	 * @return
	 * @throws IOException
	 */
	public static Map<String,String> queryProductScore(String tableName,String rowkey,
			String family,List<String> productIDs) throws IOException
 {
//		if (tableName == null || "" == tableName || rowkey == null
//				|| "" == rowkey || family == null || "" == family
//				|| productIDs == null || productIDs.size() == 0)
//			return null;
//
//		Map<String, String> filterProduct2Score = new HashMap<String, String>();
//		Map<String, String> product2Score = query(tableName, rowkey, family);
//		if (product2Score != null) {
//			for (String productID : productIDs) {
//
//				if (product2Score.containsKey(productID))
//					filterProduct2Score.put(productID,
//							product2Score.get(productID));
//			}
//		} else {
//			return null;
//		}
//
//		return filterProduct2Score;
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String,String>  map = query("feature_classifier", "上海", "label");
		
		for(Map.Entry<String, String> entry : map.entrySet()) 
		{
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}
	
	


}
