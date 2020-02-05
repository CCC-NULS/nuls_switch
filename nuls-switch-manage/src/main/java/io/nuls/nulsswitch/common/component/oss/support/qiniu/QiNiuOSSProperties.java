package io.nuls.nulsswitch.common.component.oss.support.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 七牛对象存储配置
 * </pre>
 * 

 */
@Data
@Component
@ConfigurationProperties(prefix = "nulsswitch.oss.qiniu")
public class QiNiuOSSProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String accessURL;

}
