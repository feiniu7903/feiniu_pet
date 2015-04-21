package com.lvmama.fenxiao.action.tuangou;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.utils.DistributionParseUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.fenxiao.model.tuangou.BaiduAccessToken;
import com.lvmama.fenxiao.service.tuangou.BaiduTuangouService;

public class BaiduTuangouAction extends BaseAction {
	private static final long serialVersionUID = 1506210070949456350L;
	private static final String CHARSET = "UTF-8";
	private BaiduTuangouService baiduTuangouService;

	@Action("/fenxiao/baidu/tuangou/authorize")
	public void authorize() throws IOException {
		String authorizationCode = this.getRequest().getParameter("code");
		BaiduAccessToken accessToken = baiduTuangouService.getAccessTokenByAuthorizationCode(authorizationCode);
		sendAjaxMsg("authorizationCode=" + authorizationCode + ", accessToken=" + accessToken);
	}

	@Action("/fenxiao/baidu/tuangou/xmlstream")
	public void xmlstream() throws Exception {
		HttpServletResponse response = this.getResponse();
		response.setCharacterEncoding(CHARSET);
		response.setContentType("text/xml;charset=" + CHARSET);
		PrintWriter out = response.getWriter();
		baiduTuangouService.outputBaiduTuangouXML(out);
		out.close();
	}

	@Action("/fenxiao/baidu/tuangou/xmlfile")
	public void xmlfile() throws Exception {
		String filename = DistributionParseUtil.getPropertiesByKey("tuangou.baidu.xmlfile");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), CHARSET));
		baiduTuangouService.outputBaiduTuangouXML(bw);
		bw.close();
	}

	@Action("/fenxiao/baidu/tuangou/baiduTuangouDataInit")
	public void baiduTuangouDataInit() throws Exception {
		baiduTuangouService.deleteAllBaiduTuangouProducts();
		Long totalCount = baiduTuangouService.getGroupProdIdCount();
		Long pageSize = 1000L;
		Long pageNum = totalCount % pageSize > 0 ? (totalCount / pageSize) + 1 : totalCount / pageSize;
		for (int pageNo = 0; pageNo < pageNum; pageNo++) {
			Long startRow = pageNo * pageSize + 1;
			Long endRow = (pageNo + 1) * pageSize;
			List<Long> productIdList = baiduTuangouService.selectAllBaiduTuangouProductIds(startRow, endRow);
			for (Long productId : productIdList) {
				baiduTuangouService.saveBaiduTuangouProduct(productId);
			}
		}
		sendAjaxMsg("init done");
	}

	@Action("/fenxiao/baidu/tuangou/removeAuthorizationCodeAccessToken")
	public void removeAuthorizationCodeAccessToken() {
		baiduTuangouService.removeAuthorizationCodeAccessToken();
	}

	@Action("/fenxiao/baidu/tuangou/removeClientCredentialsAccessToken")
	public void removeClientCredentialsAccessToken() {
		baiduTuangouService.removeClientCredentialsAccessToken();
	}

	public void setBaiduTuangouService(BaiduTuangouService baiduTuangouService) {
		this.baiduTuangouService = baiduTuangouService;
	}
}
