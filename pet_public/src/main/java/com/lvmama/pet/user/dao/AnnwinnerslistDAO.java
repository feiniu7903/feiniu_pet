package com.lvmama.pet.user.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.po.user.Annliping;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.po.user.UserTopic;


/**
 * 
 * 大促抽奖
 *
 */
public class AnnwinnerslistDAO extends BaseIbatisDAO {
	/**
	 * 保存用户信息
	 * @param userUsers 用户
	 */
	public void save(final Annwinnerslist annwinnerlist) {
		super.insert("ANN_WINNERSLIST.insert", annwinnerlist);
	}

	public List<Annwinnerslist> queryAnnwinnerslist(Map map) {
		return super.queryForList("ANN_WINNERSLIST.selectByParam", map);
	}
	
	public Annliping queryAnnliping(Long lpDengjiang) {
		return (Annliping) super.queryForObject("ANN_LIPING.queryByPk", lpDengjiang);
	}

	public void updateWinnerslist(Annwinnerslist annlist) {
		super.update("ANN_WINNERSLIST.updateWinnerslist", annlist);
	}
	
	/**
	 * 5.19专题用表判断新老用户
	 * @param userId
	 * @return
	 */
	public UserTopic queryUserTopicById(Long userId){
		return (UserTopic) super.queryForObject("USER_TOPIC.queryUserTopicById",userId);
	}

	public List<Annhongbao> queryAnnhongbaoByParam(Map<String, Object> map) {
 		return (List<Annhongbao>) super.queryForList("ANN_HONGBAO.queryAnnHongbaoByParam",map);
	}

	public int updateAnnhongbao(Annhongbao ann) {
 		return super.update("ANN_HONGBAO.updateAnnHongbao", ann);
	}
	
	/**
	 * 给秒杀用
	 * @return
	 */
	public int minUpdateAnnHongbao(Map map){
		return super.update("ANN_HONGBAO.minUpdateAnnHongbao", map);
	}

	/**
	 * 大转盘控制库存
	 * @param param
	 * @return
	 */
	public int minUpdateAnnliping(Map param) {
		return super.update("ANN_LIPING.minUpdateAnnliping", param);
	}
	
	public Annliping queryAnnlipingByParam(Map<String, Object> param){
		return (Annliping) super.queryForObject("ANN_LIPING.queryAnnLipinByParam",param);
	}
	
	public int updateAnnliping(Map<String,Object> param) {
		return super.update("ANN_LIPING.updateAnnliping", param);
	}
}
