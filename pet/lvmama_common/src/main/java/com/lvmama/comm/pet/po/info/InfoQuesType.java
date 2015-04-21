package com.lvmama.comm.pet.po.info;

import java.io.Serializable;


@SuppressWarnings("static-access")
public class InfoQuesType implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6807437084764617515L;

	private Long typeId;

    private String typeName;

    private String objectType;
    
    private String objectTypeZh;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }
    
    /**
     * @FIXME 这个未能实现
     * @return
     */
	public String getObjectTypeZh(){
    	
		//return InfoConstant.getInstance().getProperty(this.objectType);
    	return null;
    }

	public void setObjectTypeZh(String objectTypeZh) {
		this.objectTypeZh = objectTypeZh;
	}
	
}