package in.twizmwaz.openuhc.module;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class ModuleRegistry {

  private final Set<Class<? extends Module>> serverModules = new HashSet<>();
  private final Set<Class<? extends Module>> gameModules = new HashSet<>();

  /**
   * Retrieves modules classes from a loader and adds the classes to the registry.
   *
   * @param loader The loader to retrieve module classes from.
   */
  public void registerModules(ModuleLoader loader) {
    loader.moduleEntries.forEach(entry -> {
      ModuleInfo info = entry.getAnnotation(ModuleInfo.class);
      switch (info.lifecycle()) {
        case SERVER:
          serverModules.add(entry);
          break;
        case GAME:
          gameModules.add(entry);
          break;
        default:
          break;
      }
    });
  }
}
