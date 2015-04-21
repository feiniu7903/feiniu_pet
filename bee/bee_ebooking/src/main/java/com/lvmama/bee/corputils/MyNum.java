package com.lvmama.bee.corputils;

public class MyNum {
	public int roundToInt(String str) {
        // TODO Auto-generated method stub
        if (!str.contains(".")) {
            return Integer.parseInt(str);
        }
 
        String[] arr = str.split("\\.");
        int arg1 = Integer.parseInt(arr[0]);
        int arg2 = Integer.parseInt(arr[1].substring(0,1));
 
        if (str.contains("-")) {
            if (arg2 < 5) {
                arg1--;
            }
        } else {
            if (arg2 > 4) {
                arg1++;
            }
 
        }
 
        return arg1;
    }

}
