package com.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Vector;

public class Radom {
	public Vector<Integer> a; // 定义一个向量，用于动态数组的存储

	public void RandomNum(int num) {
		
		
		String s = new String();
		  String s1 = new String();
		  try {
		   File f = new File("E:\\1234.txt");
		   if(f.exists()){
		    System.out.print("文件存在");
		   }else{
		    System.out.print("文件不存在");
		    f.createNewFile();//不存在则创建
		   }
		   BufferedReader input = new BufferedReader(new FileReader(f));
		   
		  // while((s = input.readLine())!=null){
		  //  s1 += s+"\n";
		  // }
		   input.close();
		   for(int i=0;i<=num;i++){
			   System.out.println(i);
			   Random random = new Random(System.currentTimeMillis()+i);// 指定种子数100
			   Long r = random.nextLong();
			   if(r<0){
				   r=-r;
			   }
			   s1 += "@"+r.toString().substring(0,8)+"\r\n";
		   }
		  
		   BufferedWriter output = new BufferedWriter(new FileWriter(f));
		   output.write(s1);
		   output.close();
		  }catch(Exception ex){
			  ex.printStackTrace();
		  }
		//System.out.println(random.nextLong());
	}
	
	public static void main(String[] args) {
		Radom RN = new Radom();//生成新对象
		RN.RandomNum(100000);
	}




}
