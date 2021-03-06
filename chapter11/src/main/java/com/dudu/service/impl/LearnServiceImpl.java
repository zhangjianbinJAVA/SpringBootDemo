package com.dudu.service.impl;

import com.dudu.dao.LearnResourceMapper;
import com.dudu.domain.LearnResource;
import com.dudu.model.LeanQueryLeanListReq;
import com.dudu.service.LearnService;
import com.dudu.util.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tengj on 2017/4/7.
 */
@Transactional
@Service
public class LearnServiceImpl extends BaseService<LearnResource> implements LearnService {

    @Autowired
    private LearnResourceMapper learnResourceMapper;

    @Override
    public void deleteBatch(Long[] ids) {
        Arrays.stream(ids).forEach(id -> learnResourceMapper.deleteByPrimaryKey(id));
    }


    @Override
    public List<LearnResource> queryLearnResouceList(Page<LeanQueryLeanListReq> page) {
        PageHelper.startPage(page.getPage(), page.getRows());
        return learnResourceMapper.queryLearnResouceList(page.getCondition());
    }
}
