package io.nuls.nulsswitch;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@SpringBootApplication
@MapperScan("io.nuls.nulsswitch.mapper")
public class NulsSwitchApplication {
    public static void main(String[] args) {
        SpringApplication.run(NulsSwitchApplication.class, args);
    }

    //配置mybatis的分页插件pageHelper
    //@Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
