package com.dudu.outher;

import com.dudu.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnController12Test {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private MockHttpSession session;


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
     * get 请求
     * 注解接收参数 ,也使用于无注解修饰的对象接受参数，如果字段名一样，则自动进行赋值
     *
     * @throws Exception
     */
    @Test
    public void qryLearn() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/learn/queryLean")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
                .param("id", "1001")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //jsonPath用来获取author字段比对是否为嘟嘟MD独立博客,不是就测试不通过
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("嘟嘟MD独立博客"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Spring Boot干货系列"))
                .andDo(MockMvcResultHandlers.print());
    }


}