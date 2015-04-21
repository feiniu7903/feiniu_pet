/**
 * 
 */
package com.lvmama.comm.utils;

import com.lvmama.comm.utils.json.ResultHandle;

/**
 * 存在两个值时使用,
 * 支持对结果出现错误信息返回
 * @author yangbin
 *
 */
public class Pair<T1,T2> extends ResultHandle {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5673258223795127420L;
	private T1 first;
	private T2 second;
	
	public T1 getFirst() {
		return first;
	}
	public void setFirst(T1 first) {
		this.first = first;
	}
	public T2 getSecond() {
		return second;
	}
	public void setSecond(T2 second) {
		this.second = second;
	}	
	
	
	
	public Pair() {
		super();
	}
	
	public Pair(T1 first, T2 second) {
		super();
		this.first = first;
		this.second = second;
	}
	public static<T1,T2> Pair<T1,T2> make_pair(T1 t1,T2 t2){
		return new Pair<T1,T2>(t1,t2);
	}
}
