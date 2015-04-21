package com.lvmama.clutter.service.client.v4_0;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.clutter.service.client.v3_2.ClientUserServiceV321;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtPictureVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * v40
 * @author qinzubo
 *
 */
public class ClientUserServiceV40 extends ClientUserServiceV321 {

	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * 上传的文件 
	 */
	private File[] file;

	/**
	 *  上传的文件名称 .
	 */
	private String[] fileFileName;
	
	/**
	 *  上传的文件路径 .
	 */
	private String[] filePath;
	
	
	/**
	 * 客户端图片上传。 
	 * @param params
	 * @return filePath[] 
	 */
	public List<String> uploadImg(Map<String,Object> params) {
		ArgCheckUtils.validataRequiredArgs("file","fileName", params);
		List<String> imgUrlList = new ArrayList<String>();
		try{
			file = (File[]) params.get("file");
			fileFileName = (String[]) params.get("fileFileName");
			if(null != file){
				for(int i = 0 ;i < file.length;i++) {
					String fileName = new UUIDGenerator().generate().toString()+ getSuffixName(fileFileName[i]);
					
					if(UploadCtrl.checedImgWidthAndHeight(file[i], 180, 180)) {
						String msg = "图片尺寸最大为180*180";
					}
					
					// 1M
					if(UploadCtrl.checkImgSize(file[i], 1024)){
						
					}
					
					
					String imageUrl = postToRemote(file[i],Constant.getInstance().getFckeditorImgContextPath(), fileName);
					imgUrlList.add(imageUrl);
				}
				return imgUrlList;
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return imgUrlList;
	}
	
	
	/**
	 * 提交点评 . 
	 * @throws Exception 
	 */
	@Override
	public String commitComment(Map<String, Object> param) throws Exception{
 		Map<String,Object> m = submitCommitComment(param);
		if(null == m || null == m.get("commentId") || Long.valueOf(m.get("commentId").toString()) < 1) {
			throw new Exception(" 点评提交失败，请重试！ !");
		}
		return "success";
	}
	
	/**
	 * 提交点评 . 
	 * @param param
	 * @param returnType 1:commentId 2:productId 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> submitCommitComment(Map<String, Object> param) throws Exception{
		log.info("before invoke submitCommitComment...");
		ArgCheckUtils.validataRequiredArgs("latitudeInfo","objectId","content", param);
		// 获取用户对象. 
		UserUser users = userUserProxy.getUserUserByUserNo(param.get("userNo").toString());
 		Long commentId = null;
		String saveContent = changeContent(param.get("content").toString()); // 要保存的内容 . 
		String objectId = param.get("objectId").toString(); // 类型
		String[] latitudeInfoArray = String.valueOf(param.get("latitudeInfo")).split(",");
		String[] latitudeIds =  new String[latitudeInfoArray.length];
		int[] scores =  new int[latitudeInfoArray.length];
		initLatitudeInfo(latitudeInfoArray,latitudeIds,scores); // 初始化维度 和 评分  
		
		/**
		 * 1， 根据订单号 得到订单
		 * 2，根据订单得到产品
		 * 3, 根据产品获取其对应的目的地. 
		 * 4, 保存 
		 */
		OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(objectId));
		ProdProduct product = queryProductInfoByProductId(orderOrder.getMainProduct().getProductId());
		if (null == product) {
			throw new Exception(" can not find ProdProduct by productId "+orderOrder.getMainProduct().getProductId()+"!");
		}
		boolean result = checkConditionForProdCmting(product, Long.valueOf(objectId),users.getUserId());
		if(!result){
			throw new Exception(" order doesn't exist or has already commented !");
		}
		CommonCmtCommentVO comment = new CommonCmtCommentVO();
		
		//对于门票产品点评和酒店产品点评需要保存目的地
		if(product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name()) 
				|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())) {
			Long destPlaceId = getProductCommentDestId(product.getProductId());
			if(destPlaceId == null) {
				throw new Exception(" can not find destPlaceId by productId "+product.getProductId()+" !");
			}
			comment.setPlaceId(destPlaceId);
		} 
		comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE);
		comment.setProductId(product.getProductId());
		comment.setOrderId(Long.valueOf(objectId));
		comment.setContent(saveContent);
		if(param.get("firstChannel")!=null){
			comment.setChannel(param.get("firstChannel").toString());
		}
		comment.setCmtLatitudes(getCmtLatitude(latitudeIds,scores));
		//comment.setCmtPictureList(getCmtPicture(param));
		comment.setCmtPictureList(this.wrapCmtPicture(param));
		// 优先使用nickname V3.1.0 
		if(!StringUtils.isEmpty(users.getNickName())) {
			users.setUserName(users.getNickName());
		}
		
		commentId = cmtCommentService.insert(users, comment);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("commentId", commentId);
		resultMap.put("cashRefund", product.getIsRefundable());
		return resultMap;
	}
	
	private List<CmtPictureVO> wrapCmtPicture(Map<String,Object> params){
		Object imageUrlObj = params.get("imageUrls");
		log.info("imageUrlObj="+imageUrlObj);
		if(null!=imageUrlObj){
			List<CmtPictureVO> pictures = new ArrayList<CmtPictureVO>();
			if(imageUrlObj instanceof String){
				try {
					JSONArray jsonArray = JSONArray.fromObject(imageUrlObj);
					for(Object obj:jsonArray){
						String imageUrl = (String) obj;
						log.info("imageUrl="+imageUrl);
						CmtPictureVO cmtPictureVO = new CmtPictureVO();
						cmtPictureVO.setPicUrl(imageUrl);
						pictures.add(cmtPictureVO);
					}
					
				} catch (Exception e) {//当String不能转成json的时候，当String处理
					String imageUrl = (String) imageUrlObj;
					log.info("imageUrl="+imageUrl);
					CmtPictureVO cmtPictureVO = new CmtPictureVO();
					cmtPictureVO.setPicUrl(imageUrl);
					pictures.add(cmtPictureVO);
				}
				
			}else if(imageUrlObj instanceof String[]){
				String[] imageUrls = (String[]) imageUrlObj;
				for(String imageUrl:imageUrls){
					log.info("imageUrl="+imageUrl);
					CmtPictureVO cmtPictureVO = new CmtPictureVO();
					cmtPictureVO.setPicUrl(imageUrl);
					pictures.add(cmtPictureVO);
				}
			}else{
				
			}
			return pictures;
		}
		
		return null;
	}
	
	/**
	 * 获取图片
	 * @return 图片的集合
	 */
	private List<CmtPictureVO> getCmtPicture(Map<String,Object> params) {
		// 获取图片
		List<CmtPictureVO> pictures = new ArrayList<CmtPictureVO>();
		// 如果图片为空 
		if(!(null != filePath && filePath.length > 0)) {
			List<String> imgUrl = uploadImg(params);
			if(null != imgUrl && imgUrl.size() > 0) {
				filePath = (String[]) imgUrl.toArray();
			}
		}
		// 判断是否有图片 
		if(null != filePath && filePath.length > 0) {
			for(int i = 0 ;i < filePath.length;i++) {
				CmtPictureVO cp = new CmtPictureVO();
				cp.setPicUrl(filePath[i]);
				pictures.add(cp);
			}
		}
		return pictures;
	}
	
	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * @param file
	 * @return String
	 */
	private String postToRemote(File f, String filePath, String fileName) {
		try {
			PostMethod filePost = new PostMethod(Constant.getInstance().getUploadUrl());
			String path = filePath + fileName;
			Part[] parts = { new StringPart("fileName", path),new FilePart("ufile", f) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
			HttpClient client = new HttpClient();
			int status = client.executeMethod(filePost);
			if (status == 200) {
				return path;
			} else {
				log.error("ERROR, return: " + status);
			}
		} catch (IOException e) {
			
		}
		return null;
	}
	
	
	/**
	 * 获取后缀名
	 * @param filename 文件名 
	 * @return String
	 */
	private String getSuffixName(final String filename) {
		if (null != filename && filename.indexOf(".") != -1) {
			return filename.substring(filename.lastIndexOf("."));
		} else {
			return "";
		}
	}
	
	
	public File[] getFile() {
		return file;
	}


	public void setFile(File[] file) {
		this.file = file;
	}


	public String[] getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String[] getFilePath() {
		return filePath;
	}


	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}


}
