package io.nuls.nulsswitch.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年4月6日 | Aron</small>
 */
@Component
@ConfigurationProperties(prefix = "ifast")
@Data
public class IFastProperties {
    /**
     * 项目名，末尾不带 "/"
     */
    private String projectName;
    /**
     * 项目根目录，末尾带 "/"
     */
    private String projectRootURL;

    /**
     * 演示模式
     */
    private boolean demoMode;
    /**
     * 调试模式
     */
    private boolean devMode;

    /**
     * 临时目录，用于存放一些临时文件
     */
    private String tempDir;

    /**
     * Controller日志多行打印输出
     * 日志量大时，不建议使用
     * 开发时使用方便调试
     */
    public boolean isLogPretty = true;

}
