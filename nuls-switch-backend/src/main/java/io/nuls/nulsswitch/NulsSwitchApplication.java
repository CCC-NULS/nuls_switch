package io.nuls.nulsswitch;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.util.I18nUtils;
import io.nuls.v2.NulsSDKBootStrap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("io.nuls.nulsswitch.mapper")
@EnableSwagger2
public class NulsSwitchApplication {
    //@Value("${nuls.api}")
    private static String nulsApiUrl = "https://beta.wallet.nuls.io/api";

    public static void main(String[] args) {
        SpringApplication.run(NulsSwitchApplication.class, args);
        I18nUtils.loadLanguage(I18nUtils.class, SwitchConstant.LANGUAGE_PATH, SwitchConstant.LANGUAGE);
        //NULS-SDK工具连接NULS测试网钱包初始化
        //NulsSDKBootStrap.initMain(nulsApiUrl);
        NulsSDKBootStrap.init(nulsApiUrl);
    }

    //配置mybatis的分页插件pageHelper
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
