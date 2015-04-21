/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.usertags.UserTags;
import com.lvmama.comm.pet.po.usertags.UserTagsType;
import com.lvmama.comm.pet.service.usertags.UserTagsService;
import com.lvmama.comm.pet.service.usertags.UserTagsTypeService;
import com.lvmama.pet.BaseTest;

/**
 * @author zhongshuangxi
 *
 */
public class UserTagsServiceTest extends BaseTest {
	
	@Autowired
    private UserTagsService userTagsService;
	@Autowired
    private UserTagsTypeService userTagsTypeService;
    private UserTagsType userTagsType;
    List<UserTags> list ;
	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
//	    
	    Map<String,Object> param = new HashMap<String, Object>();
//	    param.put("tagsName","东方之珠");
//	    param.put("tagsName", 1);
//	    UserTags tags = new UserTags();
//	    tags.setTagsName("珠2");
//	    tags.setTagsTypeId(1);
//	    UserTags usertags = userTagsService.queryUserTagsByName(tags);
////	     list = userTagsService.queryAllUserTagsByParam(param);
//	    if(null == usertags){
//	        System.out.println("existn't");
//	        userTagsService.saveUserTags(tags);
//	    }else{
//	        System.out.println("exist");
//	        System.out.println(usertags.getTagsName()+"--"+usertags.getTagsId());
//	    }
//	    System.out.println(list.size());
//	    for (int i = 0; i <list.size(); i++) {
//	        System.out.println(list.get(i).getTagsName());
//        }
	    //and type_id = 1 and tags_name like '%1%' and tags_status = 1
//	    param.put("tagsTypeId",1);
//	    param.put("tagsName", 1);
//	    param.put("tagsStatus",1);
//	    list = userTagsService.queryAllUserTagsByParam(param);
//	    for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).getTagsTypeId()+"=="+list.get(i).getTypeFirstType()+"=="+list.get(i).getTypeSecondType()+"=="+list.get(i).getTagsId());
//        }
	    UserTags tags = new UserTags();
	    tags.setTagsName("oops");
	    tags.setTagsId(139);
	    
	    
	    param = new HashMap<String, Object>();
        tags = userTagsService.queryUserTagsByName(tags);
        param.put("typeId",tags.getTagsTypeId());
        List<UserTagsType> tagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
        userTagsType = tagsTypeList.get(0);
	    
        System.out.println(userTagsType.getTypeFirstType()+"=="+userTagsType.getTypeSecondType());
        
	}


    public UserTagsService getUserTagsService() {
        return userTagsService;
    }
    public void setUserTagsService(UserTagsService userTagsService) {
        this.userTagsService = userTagsService;
    }
  
}
