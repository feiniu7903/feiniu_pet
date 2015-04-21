package com.lvmama.search.synonyms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//模糊索引key的value值
@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class LikeHashMap extends HashMap {
	public Set keySet() {
		Set set = super.keySet();
		TreeSet tSet = null;
		if (set != null) {
			// 对已存在的key进行排序
			tSet = new TreeSet(set);
		}
		return tSet;
	}

	public List<Object> get(String key, boolean like) {
		List<Object> value = new ArrayList<Object>();
		// 是否为模糊搜索
		if (like) {
			List<String> keyList = new ArrayList<String>();
			TreeSet<String> treeSet = (TreeSet) this.keySet();
			for (String string : treeSet) {
				// 通过排序后,key是有序的.
				String[] str = string.split("~");
				boolean flg = false;
				for (int i = 0; i < str.length; i++) {
					if(key.equals(str[i])){
						flg =true;
					}
				}
				if (flg) {
					keyList.add(string);
					value.add(this.get(string));
				} else if (string.indexOf(key) == -1 && keyList.size() == 0) {
					// 当不包含这个key时而且key.size()等于0时,说明还没找到对应的key的开始
					continue;
				} else {
					// 当不包含这个key时而且key.size()大于0时,说明对应的key到当前这个key已经结束.不必要在往下找
					break;
				}
			}
			keyList.clear();
			keyList = null;
		} else {
			value.add(this.get(key));
		}
		return value;
	}

	public List<Object> getExcludeSelf(String key, boolean like) {
		List<Object> value = new ArrayList<Object>();
		// 是否为模糊搜索
		if (like) {
			List<String> keyList = new ArrayList<String>();
			TreeSet<String> treeSet = (TreeSet) this.keySet();
			for (String string : treeSet) {
				// 通过排序后,key是有序的.
				String[] str = string.split("~");
				boolean flg = false;
				for (int i = 0; i < str.length; i++) {
					if(key.equals(str[i])){
						flg =true;
					}
				}
				if (flg) {
					keyList.add(string);
					List list = (List)this.get(string);
					//去掉自己的值
					for (int i = 0; i < list.size(); i++) {
						if(key.equals(String.valueOf(list.get(i)))){
							list.remove(i);
						}
					}
					value.add(list);
				} else if (string.indexOf(key) == -1 && keyList.size() == 0) {
					// 当不包含这个key时而且key.size()等于0时,说明还没找到对应的key的开始
					continue;
				} else {
					// 当不包含这个key时而且key.size()大于0时,说明对应的key到当前这个key已经结束.不必要在往下找
					break;
				}
			}
			keyList.clear();
			keyList = null;
		} else {
			value.add(this.get(key));
		}
		return value;
	}
	
	
	public String remove(String key, boolean like) {
		String removeKey = "";
		// 是否为模糊搜索
		if (like) {
			List<String> keyList = new ArrayList<String>();
			TreeSet<String> treeSet = (TreeSet) this.keySet();
			for (String string : treeSet) {
				// 通过排序后,key是有序的.
				String[] str = string.split("~");
				boolean flg = false;
				for (int i = 0; i < str.length; i++) {
					if(key.equals(str[i])){
						flg =true;
					}
				}
				if (flg) {
					removeKey = string;
					this.remove(string);
				} 
			}
			keyList.clear();
			keyList = null;
		} else {
			this.remove(key);
		}
		return removeKey;
	}
	
//	public static void main(String[] args) {
//		LikeHashMap synonymsMap = new LikeHashMap();
//		File file = new File("D:\\opt\\lvmama_index\\synonyms\\synonyms.xlsx");
//		if (file != null) {
//			SynonymsExcelSheetParser parser = new SynonymsExcelSheetParser(file);
//			List<List> datas = parser.getDatasInSheet(0);
//			for (int i = 1; i < datas.size(); i++) {
//				List row = datas.get(i);
//				if (synonymsMap.get(String.valueOf(row.get(1)), true).size()>0) {
//					List<String> tempList = (List<String>) synonymsMap.get(String.valueOf(row.get(1)), true).get(0);
//					tempList.add(String.valueOf(row.get(0)));
//					String removeKey = synonymsMap.remove(String.valueOf(row.get(1)), true);
//					synonymsMap.put(removeKey+"~"+String.valueOf(row.get(0)), tempList);
//				} else {
//					List<String> tempList = new ArrayList<String>();
//					tempList.add(String.valueOf(row.get(1)));//key值也作为value
//					tempList.add(String.valueOf(row.get(0)));
//					synonymsMap.put(String.valueOf(row.get(1))+"~"+String.valueOf(row.get(0)), tempList);
//				}
//			}
//		}
//		System.out.println(synonymsMap.getExcludeSelf("自行车", true));
//	}
}
