package in.twizmwaz.openuhc.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Module {

  /**
   * @return The life cycle of the module. This determines if the module is for a game or for the entire lifetime of the
   * server.
   */
  LifeCycle lifeCycle();

  /**
   * @return The default state of the module
   */
  boolean enableOnStart() default false;



}
