package com.lvmama.comm.pet.po.fin;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ufinterface")
public class FinGLInterfaceRsp implements java.io.Serializable {
	private static final long serialVersionUID = -240580352028660221L;
	@XStreamImplicit(itemFieldName="item")
	private List<Item> item;
	
	public List<Item> getItem() {
		return item;
	}


	public void setItem(List<Item> item) {
		this.item = item;
	}

	@XStreamAlias("item")
	public class Item {
		@XStreamAlias("key")
		private String key;
		@XStreamAlias("ino_id")
		private String inoId;
		@XStreamAlias("succeed")
		private String succeed;
		@XStreamAlias("dsc")
		private String dsc;
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getSucceed() {
			return succeed;
		}

		public void setSucceed(String succeed) {
			this.succeed = succeed;
		}

		public String getDsc() {
			return dsc;
		}

		public void setDsc(String dsc) {
			this.dsc = dsc;
		}

		public String getInoId() {
			return inoId;
		}

		public void setInoId(String inoId) {
			this.inoId = inoId;
		}

	}

}
