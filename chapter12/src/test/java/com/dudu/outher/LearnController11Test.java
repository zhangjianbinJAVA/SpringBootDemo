package com.dudu.outher;

import com.dudu.domain.LearnResource;
import com.dudu.domain.User;
import com.dudu.service.LearnService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;


@RunWith(PowerMockRunner.class)//使用powermock提供的代理来使用
@PowerMockRunnerDelegate(SpringRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})//忽略一些powermock使用的classloader无法处理的类

//@PrepareForTest这个注释告诉PowerMock准备测试某些类
@PrepareForTest({StringUtils.class})// @PrepareForTest 可以 mock 多个静态方法
@SpringBootTest
public class LearnController11Test {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private MockHttpSession session;


    @MockBean
    private LearnService learnService;

    @Before
    public void setupMockMvc() {
        //初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

        //构建session
        session = new MockHttpSession();
        User user = new User("root", "root");
        //拦截器那边会判断用户是否登录，所以这里注入一个用户
        session.setAttribute("user", user);
    }


    /**
     * 获取教程测试用例
     * <p>
     * get 请求
     * <p>
     * controller 依赖 service 的方法，这里给 service 方法打桩，不执行真实的方法
     *
     * @throws Exception
     */
    @Test
    public void qryLearn() throws Exception {

        LearnResource learnResource = new LearnResource();
        learnResource.setUrl("http://www.baidu.com");
        learnResource.setTitle("Spring Boot干货系列");
        learnResource.setAuthor("嘟嘟MD独立博客");
        learnResource.setId(10L);

        // 对 service层中的方法进行 mock
        given(this.learnService.selectByKey(Mockito.any())).willReturn(learnResource);

        // 对 StringUtils 打桩,mock 静态方法
        PowerMockito.mockStatic(StringUtils.class);
        // 当 执行 StringUtils.isNoneBlank 方法时，返回 false
        PowerMockito.when(StringUtils.isNoneBlank(Mockito.anyString())).thenReturn(false);

        // 实际使用中 StringUtils.isNoneBlank("bbb") 返回 true,但这里返回 false
        boolean result = StringUtils.isNoneBlank("bbb");
        System.out.println("StringUtils.isNoneBlank: " + result);


        mvc.perform(MockMvcRequestBuilders.get("/learn/resource/1001")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //jsonPath用来获取author字段比对是否为嘟嘟MD独立博客,不是就测试不通过
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("嘟嘟MD独立博客"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Spring Boot干货系列"))
                .andDo(MockMvcResultHandlers.print());
    }


}