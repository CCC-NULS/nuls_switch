package io.nuls.nulsswitch.runner;

import io.nuls.v2.NulsSDKBootStrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitNulsSDKRunner implements CommandLineRunner {

    /**
     * the active env
     */
    @Value("${spring.profiles.active}")
    private String active;

    /**
     * nuls api node address
     */
    @Value("${nuls.api.address}")
    private String nulsApiAddress;

    /**
     * 执行初始化NulsSdk工作
     **/
    @Override
    public void run(String... args) throws Exception {
        log.info("the  active env is {}", active);
        if ("pro".equals(active)) {
            NulsSDKBootStrap.initMain(nulsApiAddress);
        } else {
            NulsSDKBootStrap.initTest(nulsApiAddress);
        }
    }
}
