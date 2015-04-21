package com.lvmama.distribution.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.distribution.service.DistributionForQiHooService;
import com.lvmama.distribution.util.DistributionUtil;
import com.lvmama.distribution.util.JSONUtil;
import com.lvmama.distribution.model.qihoo.Document;
import com.lvmama.distribution.model.qihoo.QiHooNote;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceImage;
import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;

public class DistributionForQiHooServiceImpl implements
		DistributionForQiHooService {
	private final Log log = LogFactory.getLog(DistributionForQiHooServiceImpl.class);
	private CmtTitleStatistisService cmtTitleStatistisService;
	private DistributionService distributionService;
	private PlaceSearchInfoService placeSearchInfoService;
	private ProdProductPlaceService ProdProductPlaceService;
	private PlacePageService placePageService;
	private String [] topProdIds=new String []{"21192","23178","37383","35916","55620","26474","55022","41657","21166","23090","57280","35396","27271","23607","30858","38106","20311","23356","48655","36939","46265","22175","26196","31431","21561","38026","48560","29805","37877","54716","22247","22200","24796","23218","26478","25660","30521","26519","21312","43872","22410","23114","39832","25596","21172","20814","22950","21313","27672","20266","32057","38342","55420","26687","22245","4619","29019","55089","29396","20202","31361","24797","22234","21349","24052","21545","22992","37739","21341","29320","58686","3623","21772","23107","26357","62129","43624","52500","26908","22662","37843","24495","26807","39611","22215","25956","37951","22336","24488","28310","62760","34525","24265","27197","22785","20604","58474","23000","22382","24819"};
	private String [] saleCount=new String[]{"53514","46729","42770","37245","32243","29351","24726","24384","23664","18675","16445","16285","15505","14395","14385","14020","13215","13195","11890","11865","11070","11065","10990","10840","10520","10390","9865","9780","9560","9225","9080","8890","8740","8660","8660","8290","8280","8210","8190","8175","8120","8080","8055","7950","7900","7755","7625","7590","7460","7245","6770","6765","6655","6635","6585","6535","6425","6215","6130","6110","6080","6040","6035","6005","6005","5930","5810","5700","5650","5570","5515","5480","5380","5215","5155","5115","5075","5065","4895","4825","4800","4780","4700","4645","4625","4610","4575","4430","4410","4380","4315","4185","4160","4155","4120","4095","4070","4045","3985","3985"};
	private Map<String, String> topProdMap=new HashMap<String,String>();
	/*
	 * 酒店线路产品数量
	 * @see com.lvmama.clutter.service.DistributionForQiHooService#getRouteProductCount()
	 */
	@Override
	public Integer getRouteProductCount() {
		return distributionService.selectAllRouteProductCount();
	}
	/**
	 * 酒店线路
	 * @return
	 */
	@Override
	public String createRouteProductXml(Map<String,Object> param){
		if(topProdMap.isEmpty()){
			initTopProd();
		}
		String outXml=createRouteProductsXml(param);
		log.info("creatPlaceImageXml:" + outXml);
		return outXml;
	}
	
	
	/**
	 * 景区图片
	 */
	@Override
	public String creatPlaceImageXml(String placeName) {
		String outXml = "";
		// 360指定列表
		DistributionPlaceImage placeImage = distributionService.selectSightByName(placeName);
		if (placeImage != null) {
			outXml = this.buildDocumentSpecify(placeImage);
		} else {
			outXml = this.createImageXml(placeName);
		}
		log.info("creatPlaceImageXml:" + outXml);
		return outXml;
	}
	
	/**
	 * 景区图片生成xml文件
	 * 
	 * @param outXml
	 */
	@Override
	public void createXmlFile(String outXml,String fileName) {
		FileOutputStream fout = null;
		OutputStreamWriter writer = null;
		String analyesFilePath = DistributionUtil.getPropertiesByKey("lvmamaroute.filepath"); 
		File file = new File(analyesFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			file = new File(analyesFilePath, fileName);
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
	/**
	 * 计算总页数
	 * @param proSum
	 * @return
	 */
	private int getPagesSum(int proSum,int pageSize) {
		return proSum % pageSize > 0 ? (proSum / pageSize) + 1 : proSum / pageSize;
	}
	/**
	 * 更新产品
	 */
	@Override
	public void updateRouteProduct(){
		int prodCountSum = this.getRouteProductCount();
		log.info("routeProductCountSum:"+prodCountSum);
		String url = DistributionUtil.getPropertiesByKey("lvmamaroute.indexurl");
		int pageSum=getPagesSum(prodCountSum,300);
		int pageSize=300;
		StringBuilder indexFile=new StringBuilder();
		for(int i = 0 ; i < pageSum ;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("_startRow", i*pageSize+1);
			map.put("_endRow", (i+1)*pageSize);
			map.put("isUpdate", "update");
			String routeProductXml=this.createRouteProductXml(map);
			String fileName="update_"+(i+1)+".xml";
			createXmlFile(routeProductXml,fileName);
			indexFile.append("").append(url+fileName).append("\n");
		}
		createXmlFile(indexFile.toString(), "updateProductIndex.txt");
	}
	
	
	/**
	 * 产品列表
	 */
	@Override
	public List<DistributionPlaceProduct> getProductList(String placeName) {
		List<DistributionPlaceProduct> distributionPlaceProducts = new ArrayList<DistributionPlaceProduct>();
		List<PlaceSearchInfo> placeSearchInfoList = placeSearchInfoService.getPlaceInfoFor360(placeName);
		
		for(PlaceSearchInfo placeSearchInfo:placeSearchInfoList){
			Long marketPrice=placeSearchInfo.getMarketPrice();
			Long sellPrice=(placeSearchInfo.getProductsPrice()==null)?0l:Long.valueOf(placeSearchInfo.getProductsPrice());
			DistributionPlaceProduct distributionPlaceProduct = new DistributionPlaceProduct();
			distributionPlaceProduct.setPlaceId(""+placeSearchInfo.getPlaceId());
			distributionPlaceProduct.setPlaceName(placeSearchInfo.getName());
			distributionPlaceProduct.setUrl(placeSearchInfo.getPinYinUrl());
			distributionPlaceProduct.setSellPrice(sellPrice);
			distributionPlaceProduct.setMarketPrice(marketPrice);
			distributionPlaceProducts.add(distributionPlaceProduct);
		}
		return distributionPlaceProducts;
	}
	
	/**
	 * 攻略游记列表
	 */
	@Override
	public String getPlaceGuides(String placeName) {
		String outXml = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeName", placeName);
		param.put("stages", new String[]{"1","2"});
		List<DistributionPlaceImage> placeImagelist = distributionService.selectPlaceImageByName(param);
		if (!placeImagelist.isEmpty()) {
			outXml = buildGuides(placeImagelist,param);
		} else {
			log.info("placeImagelist:" + placeImagelist	+ "placeImagelist.size() = 0");
			outXml = buildEmptyResult();
		}
		return outXml;
	}
	
	
	private String buildGuides(List<DistributionPlaceImage> placeList, Map<String, Object> param) {
		String outXml = "";
		Map<String,Object> map=this.getGuideAndNotes(placeList);
		JSONArray notesList=(JSONArray)map.get("notes");
		JSONObject guide=(JSONObject)map.get("guide");
		DistributionPlaceImage place = (DistributionPlaceImage)map.get("place");
		if(notesList != null && guide != null){
			outXml = createGuidesXml(place, notesList, guide);
		}else{
			placeList = distributionService.selectPlaceCityByName(param);
			if (!placeList.isEmpty()) {
				map=this.getGuideAndNotes(placeList);
				notesList=(JSONArray)map.get("notes");
				guide=(JSONObject)map.get("guide");
				place=(DistributionPlaceImage)map.get("place");
				if(notesList != null && guide != null){
					outXml = createGuidesXml(place, notesList, guide);
				}else{
					outXml = buildEmptyResult();
				}
			}else{
				outXml = buildEmptyResult();
			}
		}
		log.info("buildGuides xml:" + outXml);
		return outXml;
	}

	private Map<String,Object> getGuideAndNotes(List<DistributionPlaceImage> placeImagelist){
		Map<String,Object>map=new HashMap<String,Object>();
		JSONArray notesList=null;
		JSONObject guide =null;
		String notesUrl = null;
		String  guideUrl = null;
		try{
			for(DistributionPlaceImage place : placeImagelist){
				Long placeId = place.getPlacId();
				map.put("place", place);
				notesUrl = DistributionUtil.getPropertiesByKey("lvmamaguide.url")+"?action=getOrgLatestArticle&num=10&id=" + placeId;
				guideUrl = DistributionUtil.getPropertiesByKey("lvmamaguide.url")+"?action=getOrgInfo&id="	+ placeId;
				//HttpClient client = new HttpClient();
				//String notesResult= client.sendGet(notesUrl);
				String notesResult = HttpsUtil.requestGet(notesUrl);
				notesList = JSONUtil.getArray(notesResult, "data");
				//String guideResult = client.sendGet(guideUrl);
				String guideResult = HttpsUtil.requestGet(guideUrl);
				guide = JSONUtil.getObject(guideResult, "data");
				log.info("notesList:" + notesList + "placeGuide:" + guide);
				if(notesList != null && guide != null){
					break;
				}
			}
		}catch(Exception e){
			log.error("notesUrl: " + notesUrl);
			log.error("guideUrl: " + guideUrl);
			log.error(this.getClass(), e);
		}
		map.put("notes", notesList);
		map.put("guide", guide);
		
		return map;
	}
	
	private String createGuidesXml(DistributionPlaceImage placeImage,
			JSONArray notesList, JSONObject guide) {
		List<QiHooNote> qiHooNotes = new ArrayList<QiHooNote>();
		for (int i = 0; i < notesList.size(); i++) {
			JSONObject notes = notesList.getJSONObject(i);
			String title = notes.getString("title");
			if(title.length()>20){
				title=title.substring(0,20);
			}
			String guidsurl = notes.getString("url");
			String author = notes.getString("author");
			String description = notes.getString("description");
			if(description.length()>80){
				description=description.substring(0,80)+"...";
			}
			String hits = notes.getString("hits");
			String authPic = DistributionUtil.getPropertiesByKey("lvmamaguide.authorpic");
			QiHooNote qihooNote = new QiHooNote(title, author,getEncodeUrl(authPic), getEncodeUrl(guidsurl), description,hits);
			qiHooNotes.add(qihooNote);
		}
		String guildListUrl = guide.getString("url");
		String picSrc = guide.getString("thumb");
		Document qihooDoc = new Document();
		qihooDoc.setPlacename(placeImage.getPlacename());
		qihooDoc.setPicsrc(getEncodeUrl(picSrc));// 攻略缩略图
		qihooDoc.setListurl(getEncodeUrl(guildListUrl));// 游记列表
		qihooDoc.setDownloadurl(getEncodeUrl(guildListUrl));// 下载页
		qihooDoc.setQiHooNotes(qiHooNotes);
		return qihooDoc.buildDocumentForTravelNote();
	}

	private String buildDocumentSpecify(DistributionPlaceImage img) {
		String herf = "http://www.lvmama.com/dest/";
		List<DistributionPlaceImage> images = new ArrayList<DistributionPlaceImage>();
		String src = "http://pic.lvmama.com/";
		long sightId = img.getPlacId();
		img.setHref(getEncodeUrl(herf + img.getHref() + "/place"));
		img.setSrc(getEncodeUrl(src + "250x281/360/" + sightId + "/1.jpg"));
		img.setTitle(img.getPlacename());
		img.setSize("large");
		images.add(img);
		for (int i = 2; i <= 7; i++) {
			DistributionPlaceImage image = new DistributionPlaceImage();
			image.setHref(img.getHref());
			image.setSize("small");
			image.setTitle(img.getPlacename());
			image.setSrc(getEncodeUrl(src + "250x141/360/" + sightId + "/" + i
					+ ".jpg"));
			images.add(image);
		}
		Document document = new Document();
		document.setImages(images);
		document.setPlacename(img.getPlacename());
		String buf = document.buildDocumentForImage();
		log.info(buf);
		return buf.toString();
	}

	private List<DistributionPlaceProduct> getPlaceProductByIds(List<Long> placeIds,String placeName) {
		List<DistributionPlaceProduct>distributionPlaceProducts = new ArrayList<DistributionPlaceProduct>();
		int index = 0;
		for(Long placeId : placeIds){
			List<DistributionPlaceProduct> placeProducts = distributionService.selectProductByPlaceId(placeId);
			if (placeProducts != null && !placeProducts.isEmpty()) {
				DistributionPlaceProduct distributionPlaceProduct=placeProducts.get(0);
				if(placeName.equals(distributionPlaceProduct.getPlaceName())){	
					distributionPlaceProducts.add(distributionPlaceProduct);
					break;
				}
			}
		}
		for (Long placeId : placeIds) {
			List<DistributionPlaceProduct> placeProducts = distributionService.selectProductByPlaceId(placeId);
			if (placeProducts != null && !placeProducts.isEmpty()) {
				DistributionPlaceProduct distributionPlaceProduct=placeProducts.get(0);
				if(!placeName.equals(distributionPlaceProduct.getPlaceName())){
					if (index > 6)	break;
					index++;// 取八条产品数据
					distributionPlaceProducts.add(distributionPlaceProduct);
				}
			}
		}
		return distributionPlaceProducts;
	}

	/**
	 * 景区图片buildXml
	 * 
	 * @param img
	 * @return
	 */
	private String buildDocument(DistributionPlaceImage img) {
		String herf = "http://www.lvmama.com/dest/";
		String src = "http://s1.lvjs.com.cn/";
		List<DistributionPlaceImage> images = new ArrayList<DistributionPlaceImage>();
		img.setHref(getEncodeUrl(herf + img.getHref() + "/place"));
		img.setSrc(getEncodeUrl(src + "250x281/pics/" + img.getSrc()));
		img.setTitle(img.getPlacename());
		img.setSize("large");
		images.add(img);
		List<String> imageList = distributionService.selectImageByPlaceId(img.getPlacId());
		for (String imgUrl : imageList) {
			DistributionPlaceImage image = new DistributionPlaceImage();
			image.setHref(img.getHref());
			image.setSize("small");
			image.setTitle(img.getPlacename());
			image.setSrc(getEncodeUrl(src + "250x141/pics/" + imgUrl));
			images.add(image);
		}
		Document document = new Document();
		document.setImages(images);
		document.setPlacename(img.getPlacename());
		String buf = document.buildDocumentForImage();
		log.info(buf);
		return buf.toString();
	}

	private String createImageXml(String placeName) {
		String outXml = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeName", placeName);
		param.put("stages", new String[]{"2"});
		List<DistributionPlaceImage> placeImagelist = distributionService.selectPlaceImageByName(param);
		DistributionPlaceImage placeImage = null;
		if (placeImagelist != null && placeImagelist.size() > 0) {
			placeImage = placeImagelist.get(0);
			Place place=new Place();
			place.setPlaceId(placeImage.getPlacId());
			String src = placePageService.getPlacePhotoLargeImg(place);
			placeImage.setSrc(src);
			outXml = buildDocument(placeImage);
		} else {
			outXml = buildEmptyResult();
		}
		return outXml;
	}
	
	private String createRouteProductsXml(Map<String,Object> param){
		List<DistributionPlaceProduct> routeProductList = distributionService.selectAllRouteProduct(param);
		for (DistributionPlaceProduct distributionPlaceProduct : routeProductList) {
			String prodId=distributionPlaceProduct.getProductId();
			List<ProdProductPlace> prodProductPlaces=ProdProductPlaceService.selectByProductId(Long.valueOf(prodId));
			StringBuilder from = new StringBuilder();
			StringBuilder to = new StringBuilder();
			String url = "http://www.lvmama.com/product/"+prodId;
			String imgSrc = "http://pic.lvmama.com/125x94/pics/"+distributionPlaceProduct.getImgsrc();
			Double r=Math.random()*19+1;
			Integer sales = 120;
			Long commentCount=0l;
			Float avgScore=0f;
			for (ProdProductPlace prodProductPlace : prodProductPlaces) {
				if("true".equals(prodProductPlace.getTo())){
					to.append(prodProductPlace.getPlaceName()).append("|");
				}else if("true".equals(prodProductPlace.getFrom())){
					from.append(prodProductPlace.getPlaceName()).append("|");
				}
			}
			if("".equals(from.toString())){
				from.append("北京|上海|广州|深圳|杭州|成都|南京|武汉|厦门长沙|重庆|大连|福州|桂林|合肥|哈尔滨|济南|昆明|兰州|拉萨|丽江|连云港|南宁|青岛|三亚|沈阳|石家庄|苏州|天津|太原|无锡|西安|郑州|张家界|");
			}
			String fromStr = from.substring(0, from.toString().length() - 1);
			String toStr = "";
			if (!"".equals(to.toString())) {
				toStr = to.substring(0, to.toString().length() - 1);
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("productId", prodId);
			List<CmtTitleStatisticsVO> commentStatisticsVOList=cmtTitleStatistisService.getCommentStatisticsList(parameters);
			if(commentStatisticsVOList != null && commentStatisticsVOList.size()>1){
				commentCount = commentStatisticsVOList.get(0).getCommentCount();
				avgScore = commentStatisticsVOList.get(0).getAvgScore();
			}
			if (topProdMap.containsKey(prodId)) {
				sales = Integer.valueOf(topProdMap.get(prodId));
			} else {
				sales = 200 * r.intValue();
			}
			String info = distributionPlaceProduct.getInfo();
			if (info == null) info = "";
			Pattern patternStr; 
		    Matcher matcherStr; 
	        try{
	        	String regEx_Str = "<[^>]+>"; //定义HTML标签的正则表达式       
	        	patternStr = Pattern.compile(regEx_Str,Pattern.CASE_INSENSITIVE); 
	        	matcherStr = patternStr.matcher(info); 
	        	info = matcherStr.replaceAll(""); //过滤html标签 
	        }catch(Exception e){
	        	System.err.println("过滤html标签出错 " + e.getMessage()); 
	        }
	        Pattern p_html;
	        Matcher m_html;
        	String regEx_html = "<[^>]+>"; 
        	p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        	m_html = p_html.matcher(info);
        	info = m_html.replaceAll(""); 
			if (info.length() > 45) {
				info = info.substring(0, 45) + "...";
			}
			distributionPlaceProduct.setInfo(info);
			distributionPlaceProduct.setSales(sales);
			distributionPlaceProduct.setCommcount(commentCount);
			distributionPlaceProduct.setGrade(avgScore);
			distributionPlaceProduct.setDeparture(fromStr);
			distributionPlaceProduct.setArrive(toStr);
			distributionPlaceProduct.setUrl(url);
			distributionPlaceProduct.setImgsrc(imgSrc);
		}
		Document document=new Document(routeProductList);
		if("update".equals(param.get("isUpdate"))){
			return document.buildDocumentForUpdateRoute();
		}else{
			return document.buildDocumentForRoute();
		}
	}
	
	private String buildEmptyResult() {
		String outXml;
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buf.append("<document>");
		buf.append("</document>");
		outXml = buf.toString();
		return outXml;
	}
	private void initTopProd(){
		for(int i=0;i<topProdIds.length;i++){
			topProdMap.put(topProdIds[i], saleCount[i]);
		}
	}
	private String getEncodeUrl(String url) {
		String str = url;
		try {
			str = java.net.URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public void setPlaceSearchInfoService(PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}
	public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
		ProdProductPlaceService = prodProductPlaceService;
	}
	public void setCmtTitleStatistisService(CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}
	public void setPlacePageService(PlacePageService placePageService) {
		this.placePageService = placePageService;
	}

}