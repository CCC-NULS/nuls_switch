package io.nuls.nulsswitch;

import io.nuls.nulsswitch.common.config.IFastProperties;
import io.nuls.nulsswitch.common.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <pre>
 * ifast 入口
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("io.nuls.nulsswitch.**.dao")
@SpringBootApplication
public class Application {
	
	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	/**
	 * <pre>
	 * </pre>
	 * <small> 2018年3月22日 | Aron</small>
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		printProjectConfigs();
	}
	
	private static void printProjectConfigs() {
		ServerProperties serverProperties = SpringContextHolder.getApplicationContext().getBean(ServerProperties.class);
		DataSourceProperties dataSourceProperties = SpringContextHolder.getApplicationContext().getBean(DataSourceProperties.class);
		IFastProperties config = SpringContextHolder.getApplicationContext().getBean(IFastProperties.class);
		log.info("开启演示模式：{}", config.isDemoMode());
		log.info("开启调试模式：{}", config.isDevMode());
		log.info("数据库：{}", dataSourceProperties.getUrl());
		log.info("==================> run at http://localhost:{}  <==================", serverProperties.getPort() + serverProperties.getContextPath());
	}
	
}