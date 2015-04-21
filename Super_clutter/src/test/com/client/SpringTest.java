package com.client;

import java.io.UnsupportedEncodingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.distribution.service.DistributionCommonService;

public class SpringTest {

	public void createImageXml() {/*
		String herf = "http://www.lvmama.com/guide/place/";
		String src = "http://s1.lvjs.com.cn/580x290/pics/";
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-clutter-beans.xml");
		DistributionPlaceImageDAO distributionPlaceImageDAO = (DistributionPlaceImageDAO)context.getBean("distributionPlaceImageDAO");
		FileOutputStream fout = null;
		OutputStreamWriter writer = null;
		try {
			String analyesFilePath="D://360/";
			File file = new File(analyesFilePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			int fileCount=1;
			int pages = 2;
			for (int i = 1; i <= pages; i++) {
				int start = 1;
				if (i > 1) {
					start = i * 2-1;
				}
				System.out.println("limit:" + start);

				List<DistributionPlaceImage> list =new ArrayList<DistributionPlaceImage>();//distributionPlaceImageDAO.selectPlaceImageByName();

				for (DistributionPlaceImage img : list) {
					file = new File(analyesFilePath, "sightImage_"+fileCount+".xml");
					fileCount=fileCount+1;
					fout = new FileOutputStream(file);
					writer = new OutputStreamWriter(fout, "utf8");
					List<DistributionPlaceImage> images = new ArrayList<DistributionPlaceImage>();
					img.setHref(getEncodeUrl(herf + img.getHref()));
					img.setSrc(getEncodeUrl(src + img.getSrc()));
					img.setTitle(img.getPlacename());
					img.setSize("large");
					images.add(img);
					List<String> imageList = distributionPlaceImageDAO.selectImageByPlaceId(img.getPlacId());
					for (String imgUrl : imageList) {
						DistributionPlaceImage image = new DistributionPlaceImage();
						image.setHref(getEncodeUrl(img.getHref()));
						image.setSize("small");
						image.setTitle(img.getPlacename());
						image.setSrc(getEncodeUrl(src + imgUrl));
						images.add(image);
					}
					Document document = new Document();
					//document.setImages(images);
					document.setPlacename(img.getPlacename());
					String buf = document.buildDocumentForImage();
					System.out.println(buf);
					writer.write(buf.toString());
					writer.flush();
				}
			}
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
	*/}

	private String getEncodeUrl(String url) {
		String str = url;
		try {
			str = java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static void main(String args[]) {
		// ApplicationContext context = new ClassPathXmlApplicationContext(
		// "applicationContext-clutter-beans.xml");
		// InfoQuesUrgentDAO infoQuesCountDAO = (InfoQuesUrgentDAO) context
		// .getBean("infoQuesUrgentDAO");
		// List<Image> list = infoQuesCountDAO.selectPlaceImage(0, 10);
		// System.out.print(list);

		//new SpringTest().createImageXml();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-clutter-beans.xml");
		DistributionCommonService distributionCommonService=(DistributionCommonService)context.getBean("distributionCommonService");
		//distributionCommonService.pushProduct(Long.valueOf(5662));
		//distributionCommonService.pushOrder(Long.valueOf(423539));
		/*DistributionService distributionService=(DistributionService)context.getBean("distributionService");
		Map<String , Object> params=new HashMap<String , Object>();
		params.put("productId", 3205l);
		params.put("distributorInfoId", 6l);
		List<DistributionProduct> distributionProductList = distributionService.getDistributionProductTimePriceList(params);
		System.out.println(distributionProductList.toString());*/
		System.exit(0);
	}

}
