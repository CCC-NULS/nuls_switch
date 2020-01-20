package io.nuls.nulsswitch.common.component.oss.config;

import io.nuls.nulsswitch.common.component.oss.support.UploadServer;
import io.nuls.nulsswitch.common.component.oss.support.aliyun.AliyunOSSProperties;
import io.nuls.nulsswitch.common.component.oss.support.aliyun.AliyunUploadServer;
import io.nuls.nulsswitch.common.component.oss.support.local.LocalUploadProperties;
import io.nuls.nulsswitch.common.component.oss.support.local.LocalUploadServer;
import io.nuls.nulsswitch.common.component.oss.support.qiniu.QiNiuOSSProperties;
import io.nuls.nulsswitch.common.component.oss.support.qiniu.QiNiuTokenInMemory;
import io.nuls.nulsswitch.common.component.oss.support.qiniu.QiNiuUploadServer;
import com.qiniu.common.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * </pre>
 * <small> 2018/10/10 16:50 | Aron</small>
 */
@Configuration
@EnableCaching
@Slf4j
public class OSSConfiguration {

    /**
     * 本地上传(默认)，需配合Nginx解析静态资源
     */
    @Bean
    @ConditionalOnProperty(prefix="nulsswitch.oss.local", name="localPath")
    @ConditionalOnMissingBean(UploadServer.class)
    public UploadServer localUploadServer(LocalUploadProperties properties) {
        if(log.isDebugEnabled()){
            log.debug("启用本地上传服务");
        }
        return new LocalUploadServer(properties);
    }

    /**
     * 七牛上传
     */
    @Bean
    @ConditionalOnProperty(prefix="nulsswitch.oss.qiniu", name="accessKey")
    @ConditionalOnMissingBean(UploadServer.class)
    public UploadServer qiNiuUploadServer(QiNiuOSSProperties ossConfig, QiNiuTokenInMemory qiNiuToken) {
        if(log.isDebugEnabled()){
            log.debug("启用七牛云上传服务");
        }
        return new QiNiuUploadServer(ossConfig, Region.region2(),qiNiuToken);
    }

    /**
     * 阿里云OSS上传
     */
    @Bean
    @ConditionalOnProperty(prefix="nulsswitch.oss.aliyun", name="accessKeySecret")
    @ConditionalOnMissingBean(UploadServer.class)
    public UploadServer aliyunUploadServer(AliyunOSSProperties properties) {
        if(log.isDebugEnabled()){
            log.debug("启用阿里云上传服务");
        }
        return new AliyunUploadServer(properties);
    }

}
