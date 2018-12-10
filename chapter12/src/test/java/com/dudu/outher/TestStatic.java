package com.dudu.outher;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author： zhangjianbin <br/>
 * ===============================
 * Created with IDEA.
 * Date： 2018/12/7 18:45
 * ================================
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class})
public class TestStatic {

    @Test
    public void testStaticMethod() {
        // 对 StringUtils 打桩
        PowerMockito.mockStatic(StringUtils.class);
        PowerMockito.when(StringUtils.isNoneBlank(Mockito.anyString())).thenReturn(false);
        boolean bbb = StringUtils.isNoneBlank("bbb");
        System.out.println(bbb);
    }
}
