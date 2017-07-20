package in.twizmwaz.openuhc.module;

import com.google.common.collect.ImmutableMap;
import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.util.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import lombok.Data;

@Data
public class ModuleData {

  private final Class<? extends IModule> clazz;
  private final LifeCycle lifeCycle;
  private final boolean enabledOnStart;
  private final ImmutableMap<String, Class> settings;

  /**
   * @param clazz The module class type to set.
   * @param setting The string value for the setting.
   * @param value The string representation to attempt to set.
   */
  public static void setSetting(Class<? extends IModule> clazz, String setting, String value) {
    for (ModuleData data : ModuleRegistry.getAllModules()) {
      if (data.getClazz() == clazz) {
        for (Field field : clazz.getFields()) {
          Annotation annotation = field.getAnnotation(Setting.class);
          if (annotation != null && ((Setting) annotation).value().equals(setting)) {
            ModuleHandler handler = data.getLifeCycle() == LifeCycle.SERVER
                ? OpenUHC.getInstance().getModuleHandler() : OpenUHC.getCurrentGame().getModuleHandler();
            IModule module = handler.getModule(clazz);
            field.setAccessible(true);
            try {
              field.set(module, Types.parseObject(field.getType(), value));
            } catch (IllegalAccessException e) {
              OpenUHC.getInstance().getLogger().warning("Unable to set setting \"" + setting + "\" to value \""
                  + value + "\" in " + clazz.getName());
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

}
