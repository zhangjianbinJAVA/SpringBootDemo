package com.dudu.controller;

import com.dudu.domain.LearnResource;
import com.dudu.domain.User;
import com.google.gson.Gson;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnControllerTest {

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
     * 新增教程测试用例
     * <p>
     * post　请求
     *
     * @throws Exception
     */
    @Test
    //@Transactional
    public void addLearn() throws Exception {
        // 手动写 json
        //String json = "{\"author\":\"HAHAHAA\",\"title\":\"Spring\",\"url\":\"http://tengj.top/\"}";

        // 前端传递的 json 格式，对象 转 json
        LearnResource learnResource = new LearnResource();
        learnResource.setAuthor("HAHAHAAs");
        learnResource.setTitle("Spring");
        learnResource.setUrl("http://tengj.top/");

        Gson gson = new Gson();
        String json = gson.toJson(learnResource);


        //mockMvc.perform执行一个请求
        //MockMvcRequestBuilders构造一个请求
        mvc.perform(MockMvcRequestBuilders.post("/learn/add")
                //发送的数据格式
                .accept(MediaType.APPLICATION_JSON_UTF8)
                //传json参数 通过 @RequestBody注解 接受的参数
                .content(json.getBytes())
                // 注入一个session
                .session(session)
        )
                //andExpect添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                //andDo添加一个结果处理器，表示要对结果做点什么事情
                .andDo(MockMvcResultHandlers.print());//输出整个响应结果信息
    }

    /**
     * 获取教程测试用例
     * <p>
     * get 请求
     *
     * @throws Exception
     */
    @Test
    public void qryLearn() throws Exception {
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

    /**
     * 修改教程测试用例
     *
     * @throws Exception
     */
    @Test
    public void updateLearn() throws Exception {
        String json = "{\"author\":\"测试修改\",\"id\":1031,\"title\":\"Spring Boot干货系列\",\"url\":\"http://tengj.top/\"}";
        mvc.perform(MockMvcRequestBuilders.post("/learn/update")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.getBytes())//传json参数
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 删除教程测试用例
     *
     * @throws Exception
     */
    @Test
    public void deleteLearn() throws Exception {
        String json = "[1031]";
        mvc.perform(MockMvcRequestBuilders.post("/learn/delete")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json.getBytes())//传json参数
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}