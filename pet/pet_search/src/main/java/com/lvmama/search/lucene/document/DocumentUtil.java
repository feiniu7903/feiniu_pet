package com.lvmama.search.lucene.document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.search.util.LuceneCommonDic;

public class DocumentUtil {
		
	private static long seasontime=1000*60*60*24*90;
	static SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	static float currentSeasonScore=1.5f;
	static double salenumScore=1.0f;
	static double remainnumScore=1.3f;
/*
 *获得一般的评分
 */
	public static double normalScore(PlaceBean placeBean) throws Exception {
		double returnscore = 1;
		float salePer = placeBean.getSalePer();//销量比
		float midSalePer = placeBean.getMidSalePer();//中值销量比
		float tagratin=placeBean.getTagratio();//标签比值
		String stage=placeBean.getStage();
		if(stage!=null){
			if(stage.equals("1")){
				returnscore=salePer;
			}else if(stage.equals("2")){
				returnscore=salePer+midSalePer*LuceneCommonDic.PLACETAGA*tagratin;
			}else if(stage.equals("3")){
				returnscore=salePer;
			}else {
				returnscore=0;
			}
		}
		else{
			returnscore=0;
		}
		return returnscore;

	}
	public static double normalScore(ProductBean productBean)throws Exception {
		double returnscore = 1;
		float salePer = productBean.getSalePer();//销量比
		float midSalePer = productBean.getMidSalePer();//中值销量比
		float timediff = productBean.getTimediff();// 时间差
		float tagratio=productBean.getTagratio();//标签比值
		float brandration=productBean.getBrandratio();//品牌比值
		if(timediff==0){
			returnscore=salePer+midSalePer*(LuceneCommonDic.PRODUCTTAGB*tagratio+LuceneCommonDic.PRODUCTBRANDC*brandration);
		}else{
			returnscore=salePer+midSalePer*(LuceneCommonDic.PRODUCTTIMEA/timediff+LuceneCommonDic.PRODUCTTAGB*tagratio+LuceneCommonDic.PRODUCTBRANDC*brandration);
		}
		
		return returnscore;
		
	}
	public static double normalScore(ProductBranchBean productBranchBean) throws Exception  {
		double returnscore=1;
		//获取当季score
		float tmpSeasonScore=1.0f;
		long updated_date=fmt.parse(productBranchBean.getUpdateTime()).getTime();
		long nowtime=new Date().getTime();
		if(Math.abs(updated_date-nowtime)<=seasontime){
			tmpSeasonScore=tmpSeasonScore*currentSeasonScore;
		}
		//获取销量score
		Long tmpsavlenumScore=0l;
		salenumScore=tmpsavlenumScore*0.01;
		
		//获取差几个人员的产品
		double tmpreaminnumScore=1.0;
//		long produtnum=productBean.getpro();
//		if(produtnum<=10 && produtnum>0 ){
//			tmpreaminnumScore=tmpreaminnumScore*remainnumScore;
//		}
		
		//获取seq分数
		double tmpseqScore=1;
		if(tmpseqScore>0){
			for(int i=0;i<5;i++){
				tmpseqScore=Math.sqrt(tmpseqScore);
				
			}
			}
			else if(tmpseqScore<=0){
				tmpseqScore=1;
		}
		returnscore=tmpSeasonScore+tmpsavlenumScore+tmpreaminnumScore+tmpseqScore;
		return returnscore;
		
	}
	//获取lucene系数
	public static float getLuceneFactor(ProductBean productBean) {
		float lucenefactor=1;
		float midSalePer = productBean.getMidSalePer();//中值销量比
		lucenefactor=midSalePer*LuceneCommonDic.PRODUCTLUCENED;
		return lucenefactor;
	}
	public static float getLuceneFactor(PlaceBean placeBean) {
		float lucenefactor=1;
		float midSalePer = placeBean.getMidSalePer();//中值销量比
		String stage=placeBean.getStage();
		if(stage!=null){
			if(stage.equals("1")){
				lucenefactor=1;
			}else if(stage.equals("2")){
				lucenefactor=midSalePer*LuceneCommonDic.PLACELUCENED;
			}else if(stage.equals("3")){
				lucenefactor=1;
			}else {
				lucenefactor=1;
			}
		}
		else{
			lucenefactor=1;
		}
		return lucenefactor;
	}


}
