package com.dudu.outher;

import com.dudu.domain.LearnResource;
import com.dudu.domain.User;
import com.dudu.service.LearnService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnController2Test {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private MockHttpSession session;


    @Rule
    public OutputCapture capture = new OutputCapture();


    /**
     * 1. 对于不需要返回的任何值的类的所有方法，可以直接使用MockBean
     * 2. @MockBean 会代理已有的bean的方法，不会执行真实 bean 的具体方法。
     */
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

        //当 调用 this.learnService.selectByKey 函数时，无论输入任何参数，都会返回模拟的值。
        //Mockito.when(this.learnService.selectByKey(Mockito.any())).thenReturn(learnResource);


        given(this.learnService.selectByKey(Mockito.any())).willAnswer(new Answer<Object>() {

            /**
             * InvocationOnMock 通过它可以获取打桩方法的实际传入参数清单
             * @param invocationOnMock
             * @return
             * @throws Throwable
             */

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Long argumentAt = invocationOnMock.getArgumentAt(0, Long.class);
                System.out.println("调用方法的实际参数： " + argumentAt);
                if (argumentAt.equals(Long.parseLong("1001"))) {
                    return learnResource;
                }
                return null;
            }
        });

        mvc.perform(MockMvcRequestBuilders.get("/learn/resource/1001")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //jsonPath用来获取author字段比对是否为嘟嘟MD独立博客,不是就测试不通过
                //.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("嘟嘟MD独立博客"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Spring Boot干货系列"))
                .andDo(MockMvcResultHandlers.print());


        // 会获取所有日志
        //System.out.println(capture.toString());

    }


}