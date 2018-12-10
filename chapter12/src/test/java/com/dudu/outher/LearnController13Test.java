package com.dudu.outher;

import com.dudu.domain.LearnResource;
import com.dudu.domain.User;
import org.apache.commons.lang3.StringUtils;
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

import java.lang.reflect.Field;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnController13Test {

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
     * 对象接收参数，但无注解修饰，字段一样，则赋值
     *
     * @throws Exception
     */
    @Test
    public void qryLearn() throws Exception {
        LearnResource learnResource = new LearnResource();
        learnResource.setId(999L);
        learnResource.setAuthor("zhang");
        learnResource.setTitle("zhang");
        //learnResource.setUrl("http://www.baidu.com");

        Map<String, Object> stringObjectMap = objectToMap(learnResource);
        String requestParams = stringJoinAt(stringObjectMap);

        mvc.perform(MockMvcRequestBuilders.get("/learn/queryLean" + requestParams)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //jsonPath用来获取author字段比对是否为嘟嘟MD独立博客,不是就测试不通过
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("官方SpriongBoot例子"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("官方SpriongBoot例子"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * object 转 map ，字段值为 null 的，则添加
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
                if (Objects.isNull(value)) {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }


    /**
     * map 转为 &　符号进行拼接的 字符串
     *
     * @param map
     * @return
     */
    public static String stringJoinAt(Map<String, Object> map) {
        StringBuffer content = new StringBuffer();
        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i).toString();
            String value = map.get(key).toString();
            // 空串不参与签名
            if (StringUtils.isBlank(value)) {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);

        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }

        return "?" + signSrc;
    }


}