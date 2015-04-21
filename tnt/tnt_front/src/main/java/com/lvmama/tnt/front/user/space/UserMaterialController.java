package com.lvmama.tnt.front.user.space;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.tnt.comm.vo.ResultMessage;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;
import com.lvmama.tnt.user.po.TntUserMaterial;
import com.lvmama.tnt.user.service.TntUserMaterialService;
import com.lvmama.tnt.user.service.TntUserService;

@Controller
@RequestMapping(value = "/meterial")
public class UserMaterialController extends BaseController{
	private static final Log log = LogFactory.getLog(UserMaterialController.class);

	@Autowired
	private FSClient vstFSClient;
	
	@Autowired
	private TntUserService tntUserService;
	
	@Autowired
	TntUserMaterialService tntUserMaterialService;
	
	@RequestMapping("/index.do")
	public String index(HttpSession session,Model model){
		TntUser tntUser = (TntUser)getLoginUser(session);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId",tntUser.getUserId());
		List<TntUserMaterial> mateList = tntUserMaterialService.query(map);
		if(mateList!=null && mateList.size()>0){
			for(TntUserMaterial tm :mateList){
				model.addAttribute(tm.getMaterialType(), tm);
			}
			model.addAttribute("mateList", mateList);
		}
		model.addAttribute("userDetail", tntUser.getDetail());
		if("true".equalsIgnoreCase(tntUser.getIsCompany())){
			return "userspace/userinfo/myMaterial";
		}
		return "userspace/userinfo/personMaterial";
	}
	
	/**
	 * 上传文件使用
	 */
	@RequestMapping(value = "/file/upload")
	public void upload(@RequestParam(value = "file", required = false) MultipartFile file,
			String serverType, HttpServletRequest request,
			HttpServletResponse response,String type,Long userId)throws IOException {
		ResultMessage result = new ResultMessage();
		if(file.getSize()>10*1024*1024){
			output(response,null,"图片大小超过10M,请现在小点的图片上传",false);
			return ;
		}
		String localFilename="";
		try {
			InputStream in = file.getInputStream();
			File newfile = File.createTempFile("tnt", getFileSuffix(file.getOriginalFilename()));
			FileOutputStream fos =null;
			fos= new FileOutputStream(newfile);
			IOUtils.copy(in, fos);
			fos.close();
			
			if(file.getSize()>0){
				localFilename = UploadCtrl.postToRemote(newfile);
			}
			if(StringUtil.isNotEmptyString(localFilename)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userId", userId);
				List<TntUserMaterial> mateList = tntUserMaterialService.query(map);
				boolean isHave = false;
				for(TntUserMaterial t :mateList){
					if(type.equalsIgnoreCase(t.getMaterialType())){
						t.setMaterialName(file.getOriginalFilename());
						t.setMaterialUrll(localFilename);
						t.setMaterialStatus(TntConstant.USER_MATERIAL_STATUS.WAITING.name());
						tntUserMaterialService.update(t);
						isHave = true;
					}
				}
				if(!isHave){
					TntUserMaterial t = new TntUserMaterial();
					t.setUserId(userId);
					t.setMaterialName(file.getOriginalFilename());
					t.setMaterialType(type);
					t.setMaterialStatus(TntConstant.USER_MATERIAL_STATUS.WAITING.name());
					t.setMaterialUrll(localFilename);
					tntUserMaterialService.insert(t);
				}
				TntUserDetail detail = new TntUserDetail();
				detail.setUserId(userId);
				detail.setMaterialStatus(TntConstant.USER_MATERIAL_STATUS.WAITING.name());
				tntUserService.updateMaterialStatus(detail);
			}
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setResult(ex.getMessage());
			log.error("上传文件报错" + ex);
			
		}
		output(response,localFilename,null,true);
	}
	private String getFileSuffix (String fileName){
		String suf ="";
		if(StringUtil.isNotEmptyString(fileName)){
			int i = fileName.lastIndexOf(".");
			if(i>1 && i<fileName.length()){
				suf = fileName.substring(i);
			}
		}
		return suf;
	}
	
	public void output(HttpServletResponse res,String fileName,String errorText,boolean isOk)
	{
		net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try
		{
			if(!obj.has("code"))
			{
				obj.put("code", 0);
			}
			obj.put("fileName", fileName);
			obj.put("errorText", errorText);
			obj.put("success", obj.getInt("code")==0);
			if(!obj.getBoolean("success")){
				if(!obj.containsKey("msg")||StringUtils.isEmpty(obj.getString("msg"))){
					obj.put("msg", "错误未定义");
				}
			}
			if(isOk){
				obj.put("success",isOk);
			}
			res.getOutputStream().write(obj.toString().getBytes("UTF-8"));
		}catch(Exception ex)
		{			
			
		}
	}
	
	public static void main(String []arg){
		String fileName = "wew.afdw.jpg";
		int i = fileName.lastIndexOf(".")+1;
		System.out.println(fileName.substring(i));
	}
}
