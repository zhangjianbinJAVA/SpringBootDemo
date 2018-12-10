package com.dudu.outher;

import com.dudu.domain.LearnResource;
import com.dudu.domain.User;
import com.dudu.service.LearnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnController8Test {

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
        learnResource.setTitle("zhang");
        learnResource.setAuthor("zhang");
        learnResource.setId(10L);

        // 调用 learnService.selectByKey 方法时，抛出异常
        given(this.learnService.selectByKey(Mockito.any())).willThrow(new Exception("查询出错"));

        mvc.perform(MockMvcRequestBuilders.get("/learn/resource/1001")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());


    }


}