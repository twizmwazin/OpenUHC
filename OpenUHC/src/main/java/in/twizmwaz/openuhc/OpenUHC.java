package in.twizmwaz.openuhc;

import in.twizmwaz.openuhc.command.TeamCommands;
import in.twizmwaz.openuhc.game.Game;
import in.twizmwaz.openuhc.module.ModuleData;
import in.twizmwaz.openuhc.module.ModuleFactory;
import in.twizmwaz.openuhc.module.ModuleHandler;
import in.twizmwaz.openuhc.module.ModuleRegistry;

import java.util.logging.Logger;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class OpenUHC extends JavaPlugin {

  public static final String WORLD_DIR_PREFIX = "OpenUHC_worlds/";

  @Getter private static OpenUHC instance;

  private ModuleHandler moduleHandler;
  private Game game;

  /**
   * Enables OpenUHC.
   */
  @Override
  public void onEnable() {
    instance = this;
    getLogger().info("OpenUHC has started.");

    // Load modules
    final ModuleFactory loader = new ModuleFactory();
    loader.findEntries(getFile());
    ModuleRegistry.registerModules(loader);
    moduleHandler = new ModuleHandler();
    ModuleRegistry.getServerModules().forEach(data -> {
      moduleHandler.enableModule(data.getClazz());
    });
    // Load default modules
    for (ModuleData data : ModuleRegistry.getServerModules()) {
      if (data.isEnabledOnStart()) {
        moduleHandler.enableModule(data.getClazz());
      }
    }

    registerCommands();

    // Create first game.
    game = new Game();
  }

  @Override
  public void onDisable() {
    moduleHandler.disableAllModules();
  }

  public static void registerEvents(Listener events) {
    Bukkit.getPluginManager().registerEvents(events, instance);
  }

  public static Game getCurrentGame() {
    return getInstance().getGame();
  }

  public static Logger getPluginLogger() {
    return instance.getLogger();
  }

  public void registerCommands() {
    getCommand("team").setExecutor(new TeamCommands());
  }

}
