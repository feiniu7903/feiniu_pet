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

import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.po.usertags.UserTagsSearchLogs;
import com.lvmama.comm.pet.po.usertags.UserTagsType;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.place.PlaceSearchPinyinService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.service.usertags.UserTagsSearchLogsService;
import com.lvmama.comm.pet.service.usertags.UserTagsTypeService;
import com.lvmama.pet.BaseTest;

/**
 * @author zhongshuangxi
 *
 */
public class UserTagsSearchLogsServiceTest extends BaseTest {
	
	@Autowired
    private UserTagsSearchLogsService userTagsSearchLogsService;
	@Autowired
	private PlaceSearchPinyinService placeSearchPinyinService;
	
	Map<String, Object> param = new HashMap<String, Object>();

	
	@Test
	public void testInsert() throws SQLException, IllegalAccessException, InvocationTargetException{
//	    param.put("searchLogsName","韩");
//	    List<UserTagsSearchLogs> list = userTagsSearchLogsService.queryAllUserTagsLogByParam(null);
//	    System.out.println(list.size());
	    
//	    for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).getSearchLogsFrequence());
//        }
//	    UserTagsSearchLogs logs = new UserTagsSearchLogs();
//	    logs = list.get(0);
//	    logs.setSearchLogsName("XX");
	    UserTagsSearchLogs logs = new UserTagsSearchLogs();
	    logs.setSearchLogsId(1);
	    logs.setIsHide(2);
	    userTagsSearchLogsService.updateUserTagsLog(logs);
//	    List<PlaceSearchPinyin> pinyinList; 
//	    for (int i = 0; i < list.size(); i++) {
//	        UserTagsSearchLogs tagLogs = list.get(i);
//	        List<PlaceSearchPinyin> pinyinList = placeSearchPinyinService.queryPinyinListByName(tagLogs.getSearchLogsName());
//	        for (int j = 0; j < pinyinList.size(); j++) {
//	            tagLogs.setSearchLogsPinYin(pinyinList.get(j).getPinYin());
//            }
//        }
//	    
//	    System.out.println(list.get(0).getSearchLogsName()+"--"+list.get(0).getSearchLogsPinYin());
	    
	}



    public UserTagsSearchLogsService getUserTagsSearchLogsService() {
        return userTagsSearchLogsService;
    }
    public void setUserTagsSearchLogsService(
            UserTagsSearchLogsService userTagsSearchLogsService) {
        this.userTagsSearchLogsService = userTagsSearchLogsService;
    }
    public PlaceSearchPinyinService getPlaceSearchPinyinService() {
        return placeSearchPinyinService;
    }
    public void setPlaceSearchPinyinService(
            PlaceSearchPinyinService placeSearchPinyinService) {
        this.placeSearchPinyinService = placeSearchPinyinService;
    }
    
}
