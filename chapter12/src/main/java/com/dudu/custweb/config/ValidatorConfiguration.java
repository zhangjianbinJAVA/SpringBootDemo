package com.dudu.custweb.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory =
                Validation.byProvider(HibernateValidator.class)
                        .configure()
                        //hibernate.validator.fail_fast：true  快速失败返回模式(只要有一个验证失败,就返回) ; false 普通模式
                        //.addProperty("hibernate.validator.fail_fast", "true")
                        .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

    //@Bean
    //public MethodValidationPostProcessor methodValidationPostProcessor() {
    //    MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
    //    /**设置validator模式为快速失败返回*/
    //    postProcessor.setValidator(validator());
    //    return postProcessor;
    //}
}