package com.lvmama.operate.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmExport;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;

public interface EdmUsersService {

     public abstract List<ComProvince> selectProvinceList();

     public abstract List<ComCity> selectCitiesByProvinceId(String captical);
     
     public abstract ComProvince getProvince(String id);

     public abstract ComCity getCity(String id);

     public abstract List<UserUser> selectUsers(Map parameters);

     public abstract long selectUsersCount(Map parameters);
     
     /**
      * 保存导出日志
      * @param loginName
      * @param total
      */
     public abstract void saveNote(String loginName,long total);
     
     public abstract long selectEdmExportCount(Map<String, Object> parameters);
     public abstract List<EdmExport> selectEdmExportList(Map<String, Object> parameters);
}