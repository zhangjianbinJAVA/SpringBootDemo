package com.dudu.custweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author： zhangjianbin <br/>
 * ===============================
 * Created with IDEA.
 * Date： 2018/12/10 16:30
 * ================================
 */
@RestController
public class CustomControllerApi {

    /**
     * BindingResult是验证不通过的结果集合
     * <p>
     * 使用 @Valid 检验参数
     *
     * @param demo
     * @param result
     */
    @RequestMapping("/demo2")
    public void demo2(@Valid @RequestBody DemoModel demo) {
        System.out.println(demo);
    }

    /**
     * GET参数校验(@RequestParam参数校验)
     * <p>
     * 使用@Valid注解，对RequestParam对应的参数进行注解，是无效的，需要使用@Validated注解来使得验证生效。
     *
     * @param grade
     * @param classroom
     */
    @RequestMapping(value = "/demo3", method = RequestMethod.GET)
    public void demo3(@RequestParam(name = "grade", required = true) int grade, @RequestParam(name = "classroom", required = true) int classroom) {
        System.out.println(grade + "," + classroom);
    }

    @Autowired
    private CustomService customService;


    @RequestMapping(value = "/demo6", method = RequestMethod.GET)
    public void demo6() {
        DemoModel demoModel = new DemoModel();
        String list = customService.getList(demoModel);
        System.out.println(list);
    }


    @RequestMapping(value = "/demo7", method = RequestMethod.GET)
    public orderModel demo7() {
        orderModel orderModel = new orderModel();

        orderModel.setDate(LocalDateTime.now());
        orderModel.setName("zhang");

        return orderModel;
    }

}
