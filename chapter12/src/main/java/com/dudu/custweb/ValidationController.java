package com.dudu.custweb;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequestMapping("/validation")
@RestController
@Validated
public class ValidationController {
    /**
     * 如果只有少数对象，直接把参数写到Controller层，然后在Controller层进行验证就可以了。
     * <p>
     * 验证不通过时，抛出了ConstraintViolationException异常
     */
    @Validated
    @RequestMapping(value = "/demo4", method = RequestMethod.GET)
    public void demo4(@Range(min = 1, max = 9, message = "年级只能从1-9")
                      @RequestParam(name = "grade", required = true)
                              int grade,
                      @Min(value = 1, message = "班级最小只能1")
                      @Max(value = 99, message = "班级最大只能99")
                      @RequestParam(name = "classroom", required = true)
                              int classroom) {
        System.out.println(grade + "," + classroom);
    }
}