package com.lvmama.comm.pet.po.fin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ufinterface")
public class FinGLInterfaceReq implements java.io.Serializable {
	private static final long serialVersionUID = -240580352028660221L;
	@XStreamAlias("voucher_body")
	private List<FinGLInterfaceXML> finList;

	public List<FinGLInterfaceXML> getFinList() {
		return finList;
	}

	public void setFinList(List<FinGLInterfaceXML> finList) {
		this.finList = finList;
	}

	public void addList(List<FinGLInterface> paraList) throws Exception {
		if (null == finList) {
			finList = new ArrayList<FinGLInterfaceXML>();
		}
		for (FinGLInterface para : paraList) {
			FinGLInterfaceXML obj = new FinGLInterfaceXML();
			obj.setGlInterfaceId(para.getGlInterfaceId());
			if (StringUtils.isNotBlank(para.getAccountBookId())) {
				obj.setAccountBookId(para.getAccountBookId());
			} else {
				obj.setAccountBookId("");
			}
			if (StringUtils.isNotBlank(para.getIsStd())) {
				obj.setIsStd(para.getIsStd());
			} else {
				obj.setIsStd("");
			}
			if (StringUtils.isNotBlank(para.getBatchNo())) {
				obj.setBatchNo(para.getBatchNo());
			} else {
				obj.setBatchNo("");
			}
			if (StringUtils.isNotBlank(para.getProofType())) {
				obj.setProofType(para.getProofType());
			} else {
				obj.setProofType("");
			}
			if (null != para.getMakeBillTime()) {
				obj.setMakeBillTime(DateUtil.formatDate(para.getMakeBillTime(),
						"yyyy-MM-dd"));
			} else {
				obj.setMakeBillTime("");
			}
			obj.setCbill("SYSTEM");
			if (StringUtils.isNotBlank(para.getSummary())) {
				// 如果摘要字节超过60，只截取前面60位的
				obj.setSummary(replaceContent(para.getSummary()));
			} else {
				obj.setSummary("");
			}
			if (StringUtils.isNotBlank(para.getTickedNo())) {
				obj.setTickedNo(para.getTickedNo());
			} else {
				obj.setTickedNo("");
			}
			// 如果借方科目为空，借方金额也发送空
			if (StringUtils.isNotBlank(para.getBorrowerSubjectCode())) {
				obj.setBorrowerSubjectCode(para.getBorrowerSubjectCode());
				obj.setBorrowerAmount(para.getBorrowerAmountFmt());
			} else {
				obj.setBorrowerSubjectCode("");
				obj.setBorrowerAmount("");
			}
			// 如果贷方科目为空，贷方金额也发送空
			if (StringUtils.isNotBlank(para.getLenderSubjectCode())) {
				obj.setLenderSubjectCode(para.getLenderSubjectCode());
				obj.setLenderAmount(para.getLenderAmountFmt());
			} else {
				obj.setLenderSubjectCode("");
				obj.setLenderAmount("");
			}
			if (StringUtils.isNotBlank(para.getOrganizeCode())) {
				obj.setOrganizeCode(para.getOrganizeCode());
			} else {
				obj.setOrganizeCode("");
			}
			if (StringUtils.isNotBlank(para.getPersonCode())) {
				obj.setPersonCode(para.getPersonCode());
			} else {
				obj.setPersonCode("");
			}
			if (StringUtils.isNotBlank(para.getProductCode())) {
				obj.setProductCode(para.getProductCode());
			} else {
				obj.setProductCode("");
			}
			if (StringUtils.isNotBlank(para.getProductName())) {
				obj.setProductName(replaceContent(para.getProductName()));
			} else {
				obj.setProductName("");
			}
			if (StringUtils.isNotBlank(para.getCustomCode())) {
				obj.setCustomCode(para.getCustomCode());
			} else {
				obj.setCustomCode("");
			}
			if (StringUtils.isNotBlank(para.getCustomName())) {
				obj.setCustomName(replaceContent(para.getCustomName()));
			} else {
				obj.setCustomName("");
			}
			if (StringUtils.isNotBlank(para.getSupplierCode())) {
				obj.setSupplierCode(para.getSupplierCode());
			} else {
				obj.setSupplierCode("");
			}
			if (StringUtils.isNotBlank(para.getSupplierName())) {
				obj.setSupplierName(replaceContent(para.getSupplierName()));
			} else {
				obj.setSupplierName("");
			}
			if (StringUtils.isNotBlank(para.getExt1())) {
				obj.setExt1(para.getExt1());
			} else {
				obj.setExt1("");
			}
			if (StringUtils.isNotBlank(para.getExt2())) {
				obj.setExt2(para.getExt2());
			} else {
				obj.setExt2("");
			}
			if (StringUtils.isNotBlank(para.getExt3())) {
				obj.setExt3(para.getExt3());
			} else {
				obj.setExt3("");
			}
			if (StringUtils.isNotBlank(para.getExt4())) {
				obj.setExt4(para.getExt4());
			} else {
				obj.setExt4("");
			}
			if (StringUtils.isNotBlank(para.getExt5())) {
				obj.setExt5(para.getExt5());
			} else {
				obj.setExt5("");
			}
			if (StringUtils.isNotBlank(para.getExt6())) {
				obj.setExt6(para.getExt6());
			} else {
				obj.setExt6("");
			}
			if (StringUtils.isNotBlank(para.getExt7())) {
				obj.setExt7(para.getExt7());
			} else {
				obj.setExt7("");
			}
			if (StringUtils.isNotBlank(para.getExt8())) {
				obj.setExt8(para.getExt8());
			} else {
				obj.setExt8("");
			}
			if (StringUtils.isNotBlank(para.getExt9())) {
				obj.setExt9(para.getExt9());
			} else {
				obj.setExt9("");
			}
			if (StringUtils.isNotBlank(para.getExt10())) {
				obj.setExt10(para.getExt10());
			} else {
				obj.setExt10("");
			}
			if (StringUtils.isNotBlank(para.getExt11())) {
				obj.setExt11(para.getExt11());
			} else {
				obj.setExt11("");
			}
			finList.add(obj);
		}
	}

	/**
	 * 截取字符串指定长度
	 * 
	 * @param str
	 *            字符串
	 * @param len
	 *            长度
	 * 
	 * */
	private static String subStringByByte(String str, int len) {
		String result = null;
		if (str != null) {
			byte[] a = str.getBytes();
			if (a.length <= len) {
				result = str;
			} else if (len > 0) {
				result = new String(a, 0, len);
				int length = result.length();
				if (str.charAt(length - 1) != result.charAt(length - 1)) {
					if (length < 2) {
						result = null;
					} else {
						result = result.substring(0, length - 1);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 去除掉字符串中特殊符号(空格，逗号，单双引号，\，&)
	 * 
	 * @param str
	 *            字符串
	 * 
	 * */
	private static String replaceContent(String param) {
		String rst = param.replace(",", "").replace("'", "").replace("\\", "")
				.replace("&", "").trim();
		rst = subStringByByte(rst, 60);
		return rst;
	}
	
	public static void main(String args[]){
		System.out.println(DateUtil.formatDate(new Date(),"yyyy-MM-dd"));
	}
}
