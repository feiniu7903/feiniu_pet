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

import com.lvmama.comm.pet.po.usertags.UserTagsType;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.service.usertags.UserTagsTypeService;
import com.lvmama.pet.BaseTest;

/**
 * @author zhongshuangxi
 *
 */
public class UserTagsTypeServiceTest extends BaseTest {
	
	@Autowired
    private UserTagsTypeService userTagsTypeService;
    
    private List<UserTagsType> usertagsTypeList;

	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
	    
	    Map<String,Object> param = new HashMap<String, Object>();
//	    param.put("typeId", 31);
//        param.put("typeFirstType", "时间");
//        param.put("typeSecondType","双休日");
//        usertagsTypeList = userTagsTypeService.queryTagsTypeByParam(param); 
//        
//        if(usertagsTypeList.size()>0){
//            System.out.println(usertagsTypeList.size());
//            System.out.println("isExits");
//        }else{
//            UserTagsType tagType = new UserTagsType();
//            tagType.setTypeId(31);
//            tagType.setTypeFirstType("时间");
//            tagType.setTypeSecondType("双休日");
//            userTagsTypeService.updateUserTagsType(tagType);
//            userTagsTypeService.deleteUserTagsTypeByParam(param);
	    long count = userTagsTypeService.countUserTagsTypeByParam(param);
	    System.out.println(count);
//        }
	}


    public UserTagsTypeService getUserTagsTypeService() {
        return userTagsTypeService;
    }
    public void setUserTagsTypeService(UserTagsTypeService userTagsTypeService) {
        this.userTagsTypeService = userTagsTypeService;
    }
    public List<UserTagsType> getUsertagsTypeList() {
        return usertagsTypeList;
    }
    public void setUsertagsTypeList(List<UserTagsType> usertagsTypeList) {
        this.usertagsTypeList = usertagsTypeList;
    }
}
