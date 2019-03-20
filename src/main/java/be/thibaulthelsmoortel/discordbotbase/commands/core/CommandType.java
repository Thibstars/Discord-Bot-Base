package be.thibaulthelsmoortel.discordbotbase.commands.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta decoration to a {@link Command}.
 *
 * @author Thibault Helsmoortel
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandType {

    String name() default "";

    ParameterStrategy strategy() default ParameterStrategy.DISABLED;

}
