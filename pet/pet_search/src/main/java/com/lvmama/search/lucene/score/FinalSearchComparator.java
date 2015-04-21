package com.lvmama.search.lucene.score;

import java.util.Comparator;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.VerHotelBean;

public class FinalSearchComparator implements Comparator {
	
	private int reserve;// =1那么小的值在前面
	
	public FinalSearchComparator(){
		
	}
	public FinalSearchComparator(int isreserve){
		this.reserve=isreserve;
	}

	@Override
	public int compare(Object o1, Object o2) {
		int result=0;
		if(o1.getClass().getName().equals(PlaceBean.class.getName())){
			PlaceBean p1=(PlaceBean)o1;
			PlaceBean p2=(PlaceBean)o2;
			
			if(p1.getScore()>p2.getScore()){
				result=-1;
			}
			else if(p1.getScore()<p2.getScore()){
				result=1;
			}
		}
		if(o1.getClass().getName().equals(ProductBean.class.getName())){
			ProductBean p1=(ProductBean)o1;
			ProductBean p2=(ProductBean)o2;
			
			if(p1.getScore()>p2.getScore()){
				result=-1;
			}
			else if(p1.getScore()<p2.getScore()){
				result=1;
			}
		}
		if(o1.getClass().getName().equals(PlaceHotelBean.class.getName())){
			PlaceHotelBean p1=(PlaceHotelBean)o1;
			PlaceHotelBean p2=(PlaceHotelBean)o2;
			
			if(p1.getScore()>p2.getScore()){
				result=-1;
			}
			else if(p1.getScore()<p2.getScore()){
				result=1;
			}
		}
		if(o1.getClass().getName().equals(VerHotelBean.class.getName())){
			VerHotelBean p1=(VerHotelBean)o1;
			VerHotelBean p2=(VerHotelBean)o2;
			if(1==reserve){
				if(p1.getScore()>p2.getScore()){
					result=1;
				}
				else if(p1.getScore()<p2.getScore()){
					result=-1;
				}
			}else{
				if(p1.getScore()>p2.getScore()){
					result=-1;
				}
				else if(p1.getScore()<p2.getScore()){
					result=1;
				}
			}
			
		}
		
		
		return result;
	}

}
