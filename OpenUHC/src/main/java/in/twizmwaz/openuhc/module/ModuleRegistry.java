package in.twizmwaz.openuhc.module;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ModuleRegistry {

  @Getter private static final Set<ModuleData> serverModules = new HashSet<>();
  @Getter private static final Set<ModuleData> gameModules = new HashSet<>();

  /**
   * Retrieves modules classes from a loader and adds the classes to the registry.
   *
   * @param loader The loader to retrieve module classes from.
   */
  public static void registerModules(ModuleFactory loader) {
    loader.moduleData.forEach(entry -> {
      switch (entry.getLifeCycle()) {
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

  public static ImmutableSet<ModuleData> getAllModules() {
    return ImmutableSet.<ModuleData>builder().addAll(serverModules).addAll(gameModules).build();
  }
}
