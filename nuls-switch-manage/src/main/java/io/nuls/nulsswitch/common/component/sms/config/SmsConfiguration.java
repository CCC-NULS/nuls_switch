package io.nuls.nulsswitch.common.component.sms.config;

import io.nuls.nulsswitch.common.component.sms.support.MapSceneRepository;
import io.nuls.nulsswitch.common.component.sms.support.SceneRepository;
import io.nuls.nulsswitch.common.component.sms.support.SmsManager;
import io.nuls.nulsswitch.common.component.sms.support.SmsSender;
import io.nuls.nulsswitch.common.component.sms.support.aliyun.AliyunProperties;
import io.nuls.nulsswitch.common.component.sms.support.aliyun.AliyunSender;
import io.nuls.nulsswitch.common.component.sms.support.zhutong.ZhuTongProperties;
import io.nuls.nulsswitch.common.component.sms.support.zhutong.ZhuTongSender;
import io.nuls.nulsswitch.common.config.CacheConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *
 * </pre>
 * <small> 2018/8/31 19:37 | Aron</small>
 */
@Configuration
@Slf4j
public class SmsConfiguration {

    @Bean
    MapSceneRepository getSceneRepository(SmsBasicProperties properties) {
        return new MapSceneRepository(properties.getScenes());
    }

    @Bean
    SmsManager smsManager(SmsSender sender, SmsBasicProperties properties, SceneRepository sceneRepository) {
        Cache cache = CacheConfiguration.dynaConfigCache(properties.getCacheKey(), properties.getCodeExpireTime());
        SmsManager smsManager = new SmsManager();
        smsManager.setSmsSender(sender);
        smsManager.setProperties(properties);
        smsManager.setCache(cache);
        smsManager.setSceneRepository(sceneRepository);
        return smsManager;
    }

    @Bean
    @ConditionalOnProperty(prefix = "nulsswitch.sms.zt", name = "passwd")
    @ConditionalOnMissingBean(SmsSender.class)
    SmsSender zhuTongSender(ZhuTongProperties properties) {
        if (log.isDebugEnabled()) {
            log.debug("启用上海助通短信服务");
        }
        SmsSender sender = new ZhuTongSender(properties);
        return sender;
    }

    @Bean
    @ConditionalOnProperty(prefix = "nulsswitch.sms.aliyun", name = "accessKeySecret")
    @ConditionalOnMissingBean(SmsSender.class)
    SmsSender aliyunSender(AliyunProperties properties) {
        if (log.isDebugEnabled()) {
            log.debug("启用阿里云短信服务");
        }
        SmsSender sender = new AliyunSender(properties);
        return sender;
    }

}
