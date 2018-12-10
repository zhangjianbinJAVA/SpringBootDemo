package com.dudu.outher;

import com.alibaba.druid.sql.visitor.functions.Isnull;
import com.dudu.domain.LearnResource;
import com.dudu.tools.StringUtil;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;
import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * @author： zhangjianbin <br/>
 * ===============================
 * Created with IDEA.
 * Date： 2018/12/7 16:39
 * ================================
 */

public class Ot {
    @Test
    public void testAnswer1() {
        List<String> mock = Mockito.mock(List.class);

        // 调用 mock.size 时，抛出期望的异常信息
        Mockito.doThrow(new Exception("查询出错")).when(mock).size();

        // 调用 mock 对象的方法
        mock.size();

    }


    @Test(expected = RuntimeException.class)
    public void testException() {
        ArrayList<Object> objects = new ArrayList<>();
        Assert.assertThat(objects, notNullValue());
        throw new RuntimeException("查询失败");
    }


    @Test
    public void testUtils() {
        boolean bbb = StringUtils.isNoneBlank("bbb");
        System.out.println(bbb);
    }

    @Test
    public void testMap() {
        LearnResource learnResource = new LearnResource();
        learnResource.setAuthor("zhang");
        learnResource.setId(34L);
        //learnResource.setTitle("title");
        //learnResource.setUrl("http://www.baidu.com");

        Map<String, Object> stringObjectMap = objectToMap(learnResource);
        System.out.println(stringObjectMap);
        String s = stringJoinAt(stringObjectMap);
        System.out.println(s);
    }


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

        return signSrc;
    }

}


