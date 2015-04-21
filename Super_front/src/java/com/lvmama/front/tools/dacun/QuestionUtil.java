package com.lvmama.front.tools.dacun;

import java.io.Serializable;

public class QuestionUtil implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3456321L;

	/**
	 * 分销错误号
	 * @author gaoxin
	 *
	 */
	public static enum QUESTION_ENUM {
		one("1","驴妈妈今年包了几条邮轮独家销售？","1条","5条","2"),
		two("2","驴妈妈包的是哪家邮轮公司的线路？","歌诗达邮轮","浦江邮轮","1"), 
		three("3","驴妈妈旅游网总部在哪里？","上海市普陀区金沙江路1759号B座","铁岭莲花乡池水沟子","1"),
		four("4","驴妈妈旅游网现有多少员工？","11人","780人","2"),
		five("5","驴妈妈旅游网员工福利好不好？","一般","福利那是杠杠的呀，免费旅游那根本不是事儿。","2"),
		six("6","驴妈妈旅游网的广告语是什么？","要旅游，找头牛。","自助游天下，就找驴妈妈。","2"),
		seven("7","驴妈妈旅游网是靠什么发家致富的？","线上销售特价景区门票","天桥下摊煎饼果子","1");
		private final String code;
		private final String qname;
		private final String anwsera;
		private final String anwserb;
		private final String right;
		QUESTION_ENUM(String code, String qname,String anwsera,String anwserb,String right) {
			this.code = code;
			this.qname = qname;
			this.anwsera=anwsera;
			this.anwserb=anwserb;
			this.right=right;
		}

		public String getCode() {
			return this.code;
		}
		public String getQname() {
			return qname;
		}
		public String getAnwsera() {
			return anwsera;
		}
		public String getAnwserb() {
			return anwserb;
		}
		public String getRight() {
			return right;
		}

		public static String getQuestion(String code){
 			for(QUESTION_ENUM item:QUESTION_ENUM.values()){
				if(item.getCode().equals(code))
				{
 					return item.getQname()+","+item.getAnwsera()+","+item.getAnwserb()+","+item.getRight();
				}
			}
			return null;
		}
		public static String getAnwser(String code){
			for(QUESTION_ENUM item:QUESTION_ENUM.values()){
				if(item.getCode().equals(code))
				{
					return item.getRight();
				}
			}
			return null;
		}
		public String toString() {
			return this.name();
		}
	}
}
