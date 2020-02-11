package com.rjgf.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableConfigurationProperties
//@EnableTransactionManagement
//@EnableAdminServer
@MapperScan({"com.rjgf.**.mapper"})
//@ServletComponentScan
@SpringBootApplication(scanBasePackages="com.rjgf.**")
public class RjgfSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RjgfSystemApplication.class, args);
    }
}
