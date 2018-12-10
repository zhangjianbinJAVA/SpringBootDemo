package com.dudu;

import com.dudu.util.MyMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@SpringBootApplication
@EnableTransactionManagement

// @ServletComponentScan 注解后，Servlet、Filter、Listener
// 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册
@ServletComponentScan
@MapperScan(basePackages = "com.dudu.dao", markerInterface = MyMapper.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}