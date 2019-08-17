package io.nuls.nulsswitch.runner;

import io.nuls.nulsswitch.constant.SwitchConstant;
import io.nuls.nulsswitch.util.I18nUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitI18nRunner implements CommandLineRunner {

    /**
     * 加载国际化资源
     **/
    @Override
    public void run(String... args) throws Exception {
        I18nUtils.loadLanguage(I18nUtils.class, SwitchConstant.LANGUAGE_PATH, SwitchConstant.LANGUAGE);
    }
}
