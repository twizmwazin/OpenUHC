package in.twizmwaz.openuhc.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

  /**
   * @return The lifecycle of the module. This determines if the module is for a game or for the entire lifetime of the
   * server.
   */
  Lifecycle lifecycle();

}
