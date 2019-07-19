package io.nuls.nulsswitch;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.util.I18nUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("io.nuls.nulsswitch.mapper")
@EnableSwagger2
public class NulsSwitchApplication {
    public static void main(String[] args) {
        SpringApplication.run(NulsSwitchApplication.class, args);
        I18nUtils.loadLanguage(I18nUtils.class, SwitchConstant.LANGUAGE_PATH, SwitchConstant.LANGUAGE);
    }


    //配置mybatis的分页插件pageHelper
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
