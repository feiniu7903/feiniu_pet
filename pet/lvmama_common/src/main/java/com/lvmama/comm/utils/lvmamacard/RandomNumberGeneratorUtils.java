package com.lvmama.comm.utils.lvmamacard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机产生八位不重复密码
 * @author nixianjun 2013/11/26
 *
 */
public class RandomNumberGeneratorUtils {  
  
    /** 
     * 这是典型的随机洗牌算法。 
     * 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域） 
     * 算法时间复杂度O(n) 
     * @return 随机8为不重复数组 
     */  
    public static String generateNumber() {  
        String no="";  
        //初始化备选数组  
        int[] defaultNums = new int[10];  
        for (int i = 0; i < defaultNums.length; i++) {  
            defaultNums[i] = i;  
        }  
  
        Random random = new Random();  
        int[] nums = new int[LENGTH];  
        //默认数组中可以选择的部分长度  
        int canBeUsed = 10;  
        //填充目标数组  
        for (int i = 0; i < nums.length; i++) {  
            //将随机选取的数字存入目标数组  
            int index = random.nextInt(canBeUsed);  
            nums[i] = defaultNums[index];  
            //将已用过的数字扔到备选数组最后，并减小可选区域  
            swap(index, canBeUsed - 1, defaultNums);  
            canBeUsed--;  
        }  
        if (nums.length>0) {  
            for (int i = 0; i < nums.length; i++) {  
                no+=nums[i];  
            }  
        }  
  
        return no;  
    }  
    private static final int LENGTH = 8;  
  
    private static void swap(int i, int j, int[] nums) {  
        int temp = nums[i];  
        nums[i] = nums[j];  
        nums[j] = temp;  
    }
    
    /**
     * 批量产出密码
     * @param count
     * @return
     * @author nixianjun 2013-11-26
     */
    public static List<String> getbatchPasswordList(Integer count){
    	 List<String> list=new ArrayList<String>();
         for (int i = 0; i < count*2; i++) {  
      	   String s=generateNumber();
      	   if(!list.contains(s)){
      		   list.add(s);
      	   }
      	   if(list.size()>=count){
      		   break;
      	   }
          } 
         return list;
    }
      
    public static void main(String[] args) {  
        
    	List<String> l=getbatchPasswordList(10000);
    	System.out.println(l.size());
    	String s=l.get(0);l.remove(0);
    	String s2=l.get(0);l.remove(0);
    	System.out.println(s+"\n"+s2+"\n"+l.size());
        
    }  
}