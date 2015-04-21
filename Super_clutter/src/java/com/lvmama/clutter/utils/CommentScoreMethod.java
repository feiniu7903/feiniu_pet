package com.lvmama.clutter.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 计算点评分数. 
 * @author qinzubo
 *
 */
public class CommentScoreMethod implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		  //得到函数第一个参数,得到的字符串两头会有引号,所以replace
		int scroe = 0;
		String str=(args.get( 0 ).toString()).replace( "'", "" );
		if(StringUtils.isEmpty(str)) {
			return 0;
		}
		try {
			float f = Float.parseFloat(str);
			if(f<=0.5) {
				scroe = 5;
			}else if(f<=1) {
				scroe = 10;
			} else if(f<=1.5) {
				scroe = 15;
			} else if(f<=2) {
				scroe = 20;
			} else if(f<=2.5) {
				scroe = 25;
			} else if(f<=3) {
				scroe = 30;
			} else if(f<=3.5) {
				scroe = 35;
			} else if(f<=4) {
				scroe = 40;
			} else if(f<=4.5) {
				scroe = 45;
			} else{
				scroe = 50;
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return scroe;
	}
}
