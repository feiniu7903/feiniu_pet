package com.lvmama.clutter.service.client.v4_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.lvmama.clutter.service.client.v3_1.ClientOrderServiceV31;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

public class ClientOrderServiceV40 extends ClientOrderServiceV31 {

	@SuppressWarnings("static-access")
	@Override
	public Map<String, Object> commitOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		if (param.get("isAndroid") != null) {
			boolean isAndroid = Boolean.valueOf(param.get("isAndroid")
					.toString());
			List<String> tempArray = new ArrayList<String>();
			if (isAndroid) {
				String personItem = param.get("personItem").toString();
				List<String> personArray = new ArrayList<String>();
				if (StringUtil.isNotEmptyString(personItem)) {
					StringTokenizer st = new StringTokenizer(personItem, ":");
					while (st.hasMoreTokens()) {
						String dest = st.nextToken();
						personArray.add(dest);
					}

					for (String string : personArray) {
						String p = this.formatArgs(string);
						tempArray.add(p);
					}
					String tempPersonItem = "";
					Iterator<String> it = tempArray.iterator();
					while (it.hasNext()) {
						tempPersonItem += it.next();
						if (it.hasNext()) {
							tempPersonItem += ":";
						}
					}
					param.put("personItem", tempPersonItem);
				}
			}
		}
		return super.commitOrder(param);
	}

	@Override
	public Map<String, Object> validateTravellerInfo(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("firstChannel", "secondChannel",
				"branchItem", "visitTime", "udid", param);

		String branchItem = param.get("branchItem").toString();
		String visitTime = param.get("visitTime").toString();
		String leaveTime = param.get("leaveTime") == null ? null : param.get(
				"leaveTime").toString();

		boolean isTodayOrder = false;

		if (param.get("todayOrder") != null) {
			isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());
		}

		String[] branchArray = branchItem.split("_");

		if (branchArray.length == 0) {
			throw new RuntimeException("类别项构建错误!");
		}

		BuyInfo createOrderBuyInfo = new BuyInfo();

		Map<String, Object> result = this.setOrderItems(branchArray,
				isTodayOrder, visitTime, leaveTime, createOrderBuyInfo, null);
		boolean hasInsurance = (Boolean) result.get("hasInsurance");
		if (result != null
				&& (Boolean) result.get("noEmergencyContact") == true
				&& (Integer) result.get("travellerNumber") == 0) {
			/**
			 * 如果不需要填写紧急联系人和游玩人 直接提交订单。
			 */
			return this.commitOrder(param);
		} else {
			ProdProduct mainProduct = (ProdProduct) result.get("mainPoduct");
			int travellerCount = (Integer) result.get("travellerNumber");
			List<Map<String, Object>> travellerOptions = new ArrayList<Map<String, Object>>();

			/**
			 * 处理游玩人选项。
			 */
			for (int i = 0; i < travellerCount; i++) {
				if (i == 0
						&& mainProduct.getFirstTravellerInfoOptionsList() != null) {
					Map<String, Object> firstTravellerOption = new HashMap<String, Object>();
					for (String option : mainProduct
							.getFirstTravellerInfoOptionsList()) {
						firstTravellerOption.put(option, option);
					}
					travellerOptions.add(firstTravellerOption);
				} else if (mainProduct.getTravellerInfoOptionsList() != null) {
					Map<String, Object> otherTravellerOption = new HashMap<String, Object>();
					for (String option : mainProduct
							.getTravellerInfoOptionsList()) {
						otherTravellerOption.put(option, option);
					}
					travellerOptions.add(otherTravellerOption);
				} else if (mainProduct.isFreeness() && hasInsurance) {
					Map<String, Object> travellerOption = new HashMap<String, Object>();
					travellerOption.put("NAME", "NAME");
					travellerOption.put("CARD_NUMBER", "CARD_NUMBER");
					travellerOptions.add(travellerOption);
				}
			}

			if (mainProduct.isTicket()
					|| (mainProduct.isFreeness() && !hasInsurance)) {
				if (mainProduct.isOnlyFirstTravellerInfoOptionNotEmpty()) {
					travellerOptions.removeAll(travellerOptions);
					/**
					 * 不需要填写紧游玩人 直接提交订单。
					 */
					return this.commitOrder(param);
				}
			}
			result.remove("mainPoduct");
			result.remove("travellerNumber");
			result.put("travellerOptions", travellerOptions);
		}

		return result;
	}

	@Override
	public Map<String, Object> validateCoupon(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.validateCoupon(param);
	}

	@Override
	public void addfeedBack(String content, String email, String userId,
			String firstChannel) {
		// TODO Auto-generated method stub
		super.addfeedBack(content, email, userId, firstChannel);
	}

	@Override
	public String commitComment(UserUser users, CommonCmtCommentVO comment,
			List<ClientCmtLatitude> cmtLatitudeList) {
		// TODO Auto-generated method stub
		return super.commitComment(users, comment, cmtLatitudeList);
	}

	@Override
	protected void personLogic(String[] personArray,
			BuyInfo createOrderBuyInfo, List<Person> personList) {
		// TODO Auto-generated method stub
		super.personLogic(personArray, createOrderBuyInfo, personList);
	}

	@Override
	protected void checkRoutePerson(Map<String, Object> param, ProdProduct pp,
			List<Person> personList) {
		// TODO Auto-generated method stub
		super.checkRoutePerson(param, pp, personList);
	}

	@Override
	public Map<String, Object> queryOrderWaitPayTimeSecond(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryOrderWaitPayTimeSecond(param);
	}

	@Override
	public Map<String, Object> getEContractInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getEContractInfo(param);
	}

	@Override
	public Map<String, Object> cancelOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancelOrder(param);
	}

	@Override
	public Map<String, Object> cancellOrder4Wap(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancellOrder4Wap(param);
	}

	@Override
	public String onlineSign(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.onlineSign(param);
	}

	private static String formatArgs(String args) {
		if (args == null || args.trim().equals("")) {
			return args;
		}
		int position = args.indexOf("TRAVELLER");
		if (position == -1) {
			return args;
		}
		String perStr = args.substring(0, position);
		String afterStr = args.substring(position);
		if (perStr == null || perStr.trim().equals("")) {
			return "--" + args;
		}
		int findNum = findStr(perStr, "-");
		if (findNum >= 2) {
			return args;
		} else {
			String[] arrs = perStr.split("-");
			if (arrs.length > 0) {
				// 判读是手机号还是姓名
				String args1 = arrs[0];
				// 判断一个字符串是否都为数字
				boolean isPhone = args1.matches("[0-9]{1,}");
				if (isPhone && args1.length() == 11) {
					return "-" + args;
				} else {
					return perStr + "-" + afterStr;
				}
			} else {
				return "--" + args;
			}
		}
	}

	/**
	 * 判读出现次数
	 * 
	 * @param srcText
	 * @param keyword
	 * @return
	 */
	private static int findStr(String srcText, String keyword) {
		int count = 0;
		int leng = srcText.length();
		int j = 0;
		for (int i = 0; i < leng; i++) {
			if (srcText.charAt(i) == keyword.charAt(j)) {
				j++;
				if (j == keyword.length()) {
					count++;
					j = 0;
				}
			} else {
				i = i - j;// should rollback when not match
				j = 0;
			}
		}
		return count;
	}

}
