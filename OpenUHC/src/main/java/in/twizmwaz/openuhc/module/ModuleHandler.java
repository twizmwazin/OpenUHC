package in.twizmwaz.openuhc.module;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import in.twizmwaz.openuhc.OpenUHC;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModuleHandler {

  private final Map<Class<? extends IModule>, IModule> modules = new HashMap<>();

  /**
   * @param clazz The class of module to enable.
   * @param <T>   The module type.
   * @return The success of creating the module.
   */
  public <T extends IModule> boolean enableModule(Class<T> clazz) {
    // Disable if for some reason already enabled.
    disableModule(clazz);
    try {
      T module = clazz.getConstructor().newInstance();
      modules.put(clazz, module);
      module.onEnable();
      return true;
    } catch (Exception e) {
      OpenUHC.getInstance().getLogger().severe(clazz.getCanonicalName() + " failed to build!");
      return false;
    }
  }

  public void enableAllModules(Collection<Class<? extends IModule>> collection) {
    collection.forEach(this::enableModule);
  }

  /**
   * @param clazz The class of the module to disable.
   * @param <T>   The type of the class.
   * @return The success of disabling the module.
   */
  public <T extends IModule> boolean disableModule(Class<T> clazz) {
    if (modules.containsKey(clazz)) {
      getModule(clazz).onDisable();
      modules.remove(clazz);
      return true;
    } else {
      return false;
    }
  }

  public void disableAllModules(Collection<Class<? extends IModule>> collection) {
    collection.forEach(this::disableModule);
  }

  public void disableAllModules() {
    ImmutableSet.copyOf(modules.keySet()).forEach(this::disableModule);
  }

  public boolean isEnabled(Class<? extends IModule> clazz) {
    return modules.containsKey(clazz);
  }

  /**
   * Retrieves an instance of an enabled module.
   *
   * @param clazz The class of module to get.
   * @param <T>   The type of module to get.
   * @return The instance of the module. Returns null if none exists.
   */
  @SuppressWarnings("unchecked")
  public <T extends IModule> T getModule(Class<T> clazz) {
    Object obj = modules.get(clazz);
    if (obj != null && clazz.isInstance(obj)) {
      return (T) obj;
    } else {
      return null;
    }
  }

}
