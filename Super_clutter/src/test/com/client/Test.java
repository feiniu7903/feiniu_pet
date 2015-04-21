package com.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.distribution.model.qihoo.Document;
import com.lvmama.distribution.model.qihoo.Item;
import com.lvmama.distribution.model.qihoo.QiHooNote;
import com.lvmama.distribution.service.DistributionForQiHooService;

public class Test {

	@org.junit.Test
	public void imgtest() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-clutter-beans.xml");
		DistributionForQiHooService distributionForQiHooService=(DistributionForQiHooService)context.getBean("distributionForQiHooService");
		String placeName="宁波天宫庄园";
		//distributionForQiHooService.creatPlaceImageXml(placeName);
		/*List<DistributionPlaceProduct> placeProducts=distributionForQiHooService.getProductList(placeName);
		for(DistributionPlaceProduct product : placeProducts){
			System.out.println(product.getPlaceName());
			System.out.println(product.getMarketPrice());
		}*/
		//createXmlFile(distributionForQiHooService.getPlaceGuides("绍兴"));"绍兴五泄"
		/*Map<String,Object> map=new HashMap<String, Object>();
		map.put("_startRow", 1);
		map.put("_endRow", 300);
		createXmlFile(distributionForQiHooService.createRouteProductXml(map));*/
		System.out.println(distributionForQiHooService.getRouteProductCount());
		//System.out.println(distributionForQiHooService.getPlaceGuides(placeName));
		
	}
	@org.junit.Test
	public void noteTest() throws Exception {
		Document document=new Document();
		document.setPlacename("placename");
		document.setDownloadurl("downloadurl");
		document.setListurl("listurl");
		List<QiHooNote> qiHooNotes=new ArrayList<QiHooNote>();
		QiHooNote qiHoo=new QiHooNote();
		qiHoo.setAuthorname("authorname");
		qiHoo.setAuthorpic("authorpic");
		qiHoo.setHref("href");
		qiHoo.setOutline("outline");
		qiHoo.setTitle("title");
		qiHoo.setViewcount("viewcount");
		QiHooNote qihonote1=qiHoo;
		qiHooNotes.add(qiHoo);
		qiHooNotes.add(qihonote1);
		document.setQiHooNotes(qiHooNotes);
		System.out.println(document.buildDocumentForTravelNote());
	}
	
	@org.junit.Test
	public void createItems()throws Exception{
		List<Item> items=new ArrayList<Item>();
		Item item=new Item();
		items.add(item);
		Document document=new Document();
		document.setItems(items);
		System.out.println(document.buildDocumentForRoute());
	}
	
	
	/**
	 * 景区图片生成xml文件
	 * 
	 * @param outXml
	 */
	private void createXmlFile(String outXml) {
		FileOutputStream fout = null;
		OutputStreamWriter writer = null;
		String analyesFilePath = "D://360";
		File file = new File(analyesFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			file = new File(analyesFilePath, "sightRoute_360.xml");
			fout = new FileOutputStream(file);
			writer = new OutputStreamWriter(fout, "utf8");
			writer.write(outXml);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
				if (fout != null)
					fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
