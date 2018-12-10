package com.dudu.custweb;

import org.junit.Assert;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author： zhangjianbin <br/>
 * ===============================
 * Created with IDEA.
 * Date： 2018/12/10 17:15
 * ================================
 */
@Service
public class CustomService {

    public String getList( DemoModel demoModel) {

        System.out.println(demoModel);
        return "zhang";
    }

}
