
package io.nuls.nulsswitch.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 *
 * @ClassName: SpringContextUtils
 * @Description: spring工具类  系统启动的时候初始化  在其他地方可以轻松获得spring bean
 * @author Comsys-陈鹏
 * @date 2015-5-7 下午05:48:46
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringContextUtils.applicationContext == null) {
			SpringContextUtils.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBean(String name) {
		return (T)applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

//	private static ApplicationContext applicationContext;
//
//	public static void setApplicationContext(ApplicationContext context)  {
//		applicationContext = context;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T> T getBean(String name)  {
//		return (T) applicationContext.getBean(name);
//	}
}
