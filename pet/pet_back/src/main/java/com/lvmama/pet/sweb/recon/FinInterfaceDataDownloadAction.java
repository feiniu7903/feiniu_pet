package com.lvmama.pet.sweb.recon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.fin.FinGLInterface;

public class FinInterfaceDataDownloadAction extends FinInterfaceAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8987152390224224067L;
	private static final String DOUBLE_QUOTATION_MARKS = "\"";
	private static final String LINE_FEED = "\r\n";
	private static final String BEANS_REMARK = ",";

	@Action(value = "/recon/downloadFinInterface")
	public void download() {
		Map<String, Object> paraMap = super.getParams();
		pagination = initPage();
		pagination.setPageSize(2000);
		pagination.setTotalResultSize(super.getFinGLService().selectRowCount(
				paraMap));
		Long count = pagination.getTotalPageNum();
		final String BEGIN_TITLE = "\"ID号\",\"票号\",\"批量合并号\",\"制单日期\",\"摘要\",\"借方科目编码\",\"借方科目名称\",\"借方金额\",\"贷方科目编码\",\"贷方科目名称\",\"贷方金额\",\"部门编码\",\"人员编码\",\"产品编码\",\"产品名称\",\"客户编码\",\"客户名称\",\"供应商编码\",\"供应商名称\",\"自定义项1(订单号)\",\"自定义项2\",\"自定义项3\",\"自定义项4\",\"自定义项5\",\"自定义项6\",\"自定义项7\",\"自定义项8\",\"自定义项9\",\"自定义项10\",\"自定义项11\",\"备注\",\"创建时间\",\"凭证类型\",\"账套ID\",\"预收状态(未入账、入账成功、入账失败)\",\"预收入账后产生的提示、警告、失败原因等信息\",\"入账类型\",\"是否是标准凭证，标准凭证借贷科目都有，非标准只有借或贷\",\"U8凭证号\",\"是否废单重下\",\"勾兑ID\"\r\n";
		StringBuffer sb = new StringBuffer();
		sb.append(BEGIN_TITLE);
		String outputFile = "finInterface_"
				+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".csv";
		writeFin(sb,outputFile);
		List<FinGLInterface> finList = null;
		while (pagination.getTotalResultSize() > 0
				&& pagination.getCurrentPage() <= count) {
			paraMap.put("_startRow", pagination.getStartRows());
			paraMap.put("_endRow", pagination.getEndRows());
			finList = super.getFinGLService().query(paraMap);
			sb = new StringBuffer();
			if (null != finList && !finList.isEmpty()) {
				for (FinGLInterface fin : finList) {
					sb.append(getValuesByObject(fin)).append(LINE_FEED);
				}
			}
			pagination.setCurrentPage(pagination.getCurrentPage() + 1);
			writeFin(sb,outputFile);
		}
		exportcsv(outputFile);
	}

	public void writeFin(StringBuffer str, String fileName){
		try{
		String destFileName = System.getProperty("java.io.tmpdir") + "/"
				+ fileName;
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
				destFileName),true),"GB2312"));
		writer.write(str.toString());
		writer.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public void exportcsv( String fileName) {
		try {
			String destFileName = System.getProperty("java.io.tmpdir") + "/"
					+ fileName;
			HttpServletResponse response = getResponse();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName);
			File file = new File(destFileName);
			InputStream inputStream = new FileInputStream(destFileName);
			if (file != null && file.exists()) {
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				inputStream.close();
			} else {
				throw new RuntimeException("Download failed! fileName:"
						+ file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private StringBuffer getValuesByObject(final FinGLInterface object) {
		return new StringBuffer()
				.append(getValueNotNull(object.getGlInterfaceId()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getTickedNo()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getBatchNo()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getMakeBillTimeFmt()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getSummary()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getBorrowerSubjectCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getBorrowerSubjectName()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getBorrowerAmount()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getLenderSubjectCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getLenderSubjectName()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getLenderAmount()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getOrganizeCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getPersonCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getProductCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getProductName()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getCustomCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getCustomName()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getSupplierCode()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getSupplierName()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt1()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt2()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt3()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt4()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt5()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt6()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt7()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt8()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getExt9()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getExt10()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getExt11()))
				.append(BEANS_REMARK).append(getValueNotNull(object.getMemo()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getCreateTimeFmt()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getProofType()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getAccountBookId()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getZhReceivablesStatus()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getReceivablesResult()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getZhAccountType()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getIsStd()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getInoId()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getIsTransfer()))
				.append(BEANS_REMARK)
				.append(getValueNotNull(object.getReconResultId()));
	}

	private String getValueNotNull(Object o) {
		if (null == o) {
			return DOUBLE_QUOTATION_MARKS + DOUBLE_QUOTATION_MARKS;
		}
		return DOUBLE_QUOTATION_MARKS + o.toString() + DOUBLE_QUOTATION_MARKS;
	}
}
