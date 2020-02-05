package io.nuls.nulsswitch.common.annotation;

import java.lang.annotation.*;

/**
 * <pre>
 * 系统日志注解
 * </pre>
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Log {
	String value() default "";
}
