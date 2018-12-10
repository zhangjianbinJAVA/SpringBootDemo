package com.dudu.dao;

import com.dudu.domain.LearnResource;
import com.dudu.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface LearnResourceMapper extends MyMapper<LearnResource> {

    /**
     * 自定义新增接口
     *
     * @param map
     * @return
     */
    List<LearnResource> queryLearnResouceList(Map<String, Object> map);
}