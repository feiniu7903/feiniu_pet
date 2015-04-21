package com.lvmama.tnt.back.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntContract;
import com.lvmama.tnt.user.service.TntContractService;

@Controller
@RequestMapping("/user/contract")
public class ContractController {

	@Autowired
	private TntContractService tntContractService;

	@Autowired
	private FSClient tntFSClient;

	/** 打开合同列表弹窗 **/
	@RequestMapping(value = "/list/{userId}")
	public String list(@PathVariable Long userId, Integer page, Model model,
			HttpServletRequest request) {
		TntContract tntContract = new TntContract();
		tntContract.setUserId(userId);
		tntContract.setStatus(TntContract.CONTACT_STATUS.ACTIVATE.getValue());
		int pageNo = page != null ? page : 1;
		Page<TntContract> p = Page.page(pageNo, tntContract);
		p.desc("contract.CONTRACT_ID");
		List<TntContract> tntContractList = list(p, request);
		model.addAttribute(tntContract);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(tntContractList);
		return "user/contract/list";
	}

	/** 打开合同列表弹窗 **/
	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(TntContract tntContract, Model model) {
		Long contractId = tntContract.getContractId();
		tntContractService.deleteById(contractId);
		return "redirect:contract/list/" + tntContract.getUserId();
	}

	/** 新增合同附件 **/
	@RequestMapping(method = RequestMethod.POST)
	public String addContracAttachment(TntContract tntContract,
			BindingResult bindingResult,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String fileName = file.getOriginalFilename();
		fileName = tntContractService.getContractName(fileName);
		String page = "contract/list";
		if (fileName != null && !fileName.isEmpty()) {
			String serverType = TntConstant.TNT_FILE_TYPE.TNT_FILE_CONTRACT
					.toString();
			try {
				Long attachment = tntFSClient.uploadFile(fileName,
						file.getBytes(), serverType);
				if (attachment == 0) {
					throw new IllegalArgumentException("上传文件失败");
				} else {
					tntContract.setAttachment(attachment);
					tntContract.setContractName(fileName);
					tntContractService.uploadContract(tntContract);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "redirect:" + page + "/" + tntContract.getUserId();
	}

	protected List<TntContract> list(Page<TntContract> page,
			HttpServletRequest request) {
		WebFetchPager<TntContract> pager = new WebFetchPager<TntContract>(page,
				request) {

			@Override
			protected long getTotalCount(TntContract t) {
				return tntContractService.count(t);
			}

			@Override
			protected List<TntContract> fetchDetail(Page<TntContract> page) {
				return tntContractService.pageQuery(page);
			}
		};
		return pager.fetch();
	}
}
