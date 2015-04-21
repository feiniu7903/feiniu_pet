package com.lvmama.pet.fin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;
import com.lvmama.comm.pet.vo.Page;

/**
 * 数据访问对象实现类
 * @since 2014-03-10
 * @author taiqichao
 */
@Repository
public class FinGlSubjectCfgDAO extends BaseIbatisDAO{

    /**
     * 插入数据
     * @param finGlSubjectCfgDO
     * @author taiqichao
     * @return 插入数据的主键
     */
    public Long insertFinGlSubjectCfgDO(FinGlSubjectCfg finGlSubjectCfgDO) {
        Object SUBJECT_CONFIG_ID = super.insert("FIN_GL_SUBJECT_CFG.insert", finGlSubjectCfgDO);
        return (Long) SUBJECT_CONFIG_ID;
    }

    /**
     * 统计记录数
     * @param finGlSubjectCfgDO
     * @author taiqichao
     * @return 查出的记录数
     */
    public Integer countFinGlSubjectCfgDOByTerm(FinGlSubjectCfg finGlSubjectCfgDO) {
        Integer count = (Integer) super.queryForObject("FIN_GL_SUBJECT_CFG.countByDOTerm", finGlSubjectCfgDO);
        return count;
    }

    /**
     * 更新记录
     * @param finGlSubjectCfgDO
     * @author taiqichao
     * @return 受影响的行数
     */
    public Integer updateFinGlSubjectCfgDO(FinGlSubjectCfg finGlSubjectCfgDO) {
        int result = super.update("FIN_GL_SUBJECT_CFG.update", finGlSubjectCfgDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param finGlSubjectCfgDO
     * @author taiqichao
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<FinGlSubjectCfg> findListByTerm(FinGlSubjectCfg finGlSubjectCfgDO) {
        List<FinGlSubjectCfg> list = super.queryForList("FIN_GL_SUBJECT_CFG.findListByDO", finGlSubjectCfgDO);
        return list;
    }
    
    /**
     * 分页查询
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<FinGlSubjectCfg> query(Map<String, Object> parameters) {
		return super.queryForList("FIN_GL_SUBJECT_CFG.query", parameters);
	}
    
    /**
     * 查询行数
     * @param paramMap
     * @return
     */
    public Long queryCount(Map<String, Object> paramMap){
		return (Long)super.queryForObject("FIN_GL_SUBJECT_CFG.queryCount", paramMap);
	}
    

    /**
     * 根据主键获取finGlSubjectCfgDO
     * @param subjectConfigId
     * @author taiqichao
     * @return finGlSubjectCfgDO
     */
    public FinGlSubjectCfg findFinGlSubjectCfgDOByPrimaryKey(Long subjectConfigId) {
        FinGlSubjectCfg finGlSubjectCfgDO = (FinGlSubjectCfg) super.queryForObject("FIN_GL_SUBJECT_CFG.findByPrimaryKey", subjectConfigId);
        return finGlSubjectCfgDO;
    }

    /**
     * 删除记录
     * @param subjectConfigId
     * @author taiqichao
     * @return 受影响的行数
     */
    public Integer deleteFinGlSubjectCfgDOByPrimaryKey(Long subjectConfigId) {
        Integer rows = (Integer) super.delete("FIN_GL_SUBJECT_CFG.deleteByPrimaryKey", subjectConfigId);
        return rows;
    }
    
    /**
     * 删除全部记录
     * @author taiqichao
     * @return 受影响的行数
     */
    public Integer deleteAllFinGlSubjectCfg() {
        Integer rows = (Integer) super.delete("FIN_GL_SUBJECT_CFG.deleteAllFinGlSubjectCfg");
        return rows;
    }

}