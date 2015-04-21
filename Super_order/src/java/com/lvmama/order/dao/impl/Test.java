package com.lvmama.order.dao.impl;

import java.util.HashSet;
import java.util.Set;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> userSet = new HashSet<String>();
		userSet.add(null);
		
		for (String string : userSet) {
			System.out.println(string);
		}
		

	}

}
