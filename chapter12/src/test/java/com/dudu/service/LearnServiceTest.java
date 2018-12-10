package com.dudu.service;

import com.dudu.domain.LearnResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;

/**
 * 使用RANDOM_PORT或DEFINED_PORT这种安排隐式提供了一个真正的servlet环境, 在这种情况下，在服务器上启动的任何事务都不会回滚。
 *
 * @SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LearnServiceTest {

    ///**
    // * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
    // */
    @LocalServerPort
    private int port;

    @Autowired
    private LearnService learnService;

    @Test
    public void getLearn() {
        LearnResource learnResource = learnService.selectByKey(1001L);
        Assert.assertThat(learnResource.getAuthor(), is("嘟嘟MD独立博客"));
    }


    /**
     * 这样测试完数据就会回滚了，不会造成垃圾数据
     * <p>
     *
     * @Transactional :单元个测试的时候如果不想造成垃圾数据，可以开启事物功能，记在方法或者类头部添加
     * <p>
     * @Rollback(false): @Rollback 表示事务执行完回滚，支持传入一个参数value，默认true 即回滚，false不回滚。
     */
    @Test
    @Transactional
    //@Rollback(false)
    public void add() {
        LearnResource bean = new LearnResource();
        bean.setAuthor("测试回滚-2");
        bean.setTitle("回滚用例-2");
        bean.setUrl("http://tengj.top");
        learnService.save(bean);
    }


}