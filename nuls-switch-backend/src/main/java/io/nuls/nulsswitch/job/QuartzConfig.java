package io.nuls.nulsswitch.job;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    @Bean("quartzSchedulerDB")
    public SchedulerFactoryBean initSchedulerFactoryBean(DataSource dataSource) throws IOException {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        //设置数据源
        bean.setDataSource(dataSource);
        //添加监听
        //bean.setGlobalJobListeners(logJobListener);
        //下上文
        bean.setApplicationContextSchedulerContextKey("applicationContextKey");
        //延迟启动时间
        bean.setStartupDelay(10);
        //设置属性
        bean.setQuartzProperties(quartzProperties());
        return bean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/spring-quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
