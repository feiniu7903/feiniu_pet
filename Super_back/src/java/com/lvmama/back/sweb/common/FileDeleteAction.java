/**
 * 
 */
package com.lvmama.back.sweb.common;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

/**
 * @author yangbin
 * 
 */
@SuppressWarnings("serial")
public class FileDeleteAction extends BaseAction {

	private Long affixId;

	@Action("/common/file_del")
	public void delete() {
		JSONResult result = new JSONResult();
		try {

			Assert.isTrue(!getRequest().getMethod().equalsIgnoreCase("method"),
					"当前操作不允许");
			Assert.isTrue(!(affixId == null || affixId < 1), "参数不存在");
			ComAffix affix = comAffixService.getAffix(affixId);
			Assert.notNull(affix, "对象不存在");

			comAffixService.removeAffix(affix,getOperatorName());

			//文件不删除
//			File file = new File(root, affix.getPath());
//			if (file.exists()) {
//				file.delete();
//			}
			//TODO 日志记录
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}

	private ComAffixService comAffixService;

	public void setAffixId(Long affixId) {
		this.affixId = affixId;
	}

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}

	

}
