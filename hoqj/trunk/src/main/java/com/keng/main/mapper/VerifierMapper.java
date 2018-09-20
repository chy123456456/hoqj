package com.keng.main.mapper;

import java.util.List;
import java.util.Map;

import com.keng.main.entity.SysUsers;
import com.keng.main.entity.Verifier;
import com.keng.base.common.Pager;

public interface VerifierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Verifier record);

    int insertSelective(Verifier record);

    Verifier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Verifier record);

    int updateByPrimaryKey(Verifier record);

    // 联邦银行审核员查询列表
    List<Map<String, Object>> getAllVerifier(Pager pager);

    // 状态绑定事件限制授权人只有一个启动状态
    Integer countStatus(Map<String, Object> map);

    Integer checkName(Map<String, Object> map);

    /**
     * 查询审核员
     * @return
     */
    SysUsers queryVerifier();

    void update();
}