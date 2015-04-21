package com.lvmama.comm.vst.service.search;

import java.util.List;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.AutoCompleteVerHotel;
import com.lvmama.comm.search.vo.AutoCompleteVerHotelCity;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.search.vo.VerPlaceBean;

@RemoteService("clientHotelSearchService")
public interface VstClientHotelSearchService {
	
	/**
	 * 酒店数据查询
	 * @param searchVo
	 * @param sort(
	 * 		1:seq,4:点评分数降序,
	 * 		5:点评分数升序,6:点评数升序,7:点评数降序,8:产品子类型降序,
	 * 		9:一周产品销量,10:距离排序,13:价格降序,14:价格升序
	 * )
	 * @return
	 */
	public Page<VerHotelBean> search(VerHotelSearchVO searchVo, int sort);

	/**
	 * 酒店城市,酒店名称,周边景点 三合一自动补全
	 * @param keyword
	 * @return 
	 */
	public List<AutoCompleteVerHotel> keyWordAutoComplete(String keyword);
	
	/**
	 * 城市列表
	 * @param keyword
	 * @return
	 */
	public List<AutoCompleteVerHotelCity> getCitiesByKeyword(String keyword);
	
	/**
	 * 获取地标、商圈、景区
	 * @param verHotelSearchVO
	 * @return
	 */
	public List<VerPlaceBean> getVerPlaceList(VerHotelSearchVO verHotelSearchVO);

}
