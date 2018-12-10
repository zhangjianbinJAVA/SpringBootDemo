package com.dudu.service;

import com.dudu.domain.LearnResource;
import com.dudu.model.LeanQueryLeanListReq;
import com.dudu.util.Page;

import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */

public interface LearnService extends IService<LearnResource> {

    List<LearnResource> queryLearnResouceList(Page<LeanQueryLeanListReq> page);

    void deleteBatch(Long[] ids);
}
