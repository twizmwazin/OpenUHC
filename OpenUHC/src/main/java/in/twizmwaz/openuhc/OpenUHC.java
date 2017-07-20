package in.twizmwaz.openuhc;

import in.twizmwaz.openuhc.game.Game;
import in.twizmwaz.openuhc.module.ModuleHandler;
import in.twizmwaz.openuhc.module.ModuleLoader;
import in.twizmwaz.openuhc.module.ModuleRegistry;

import java.util.UUID;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class OpenUHC extends JavaPlugin {

  @Getter private static OpenUHC instance;

  private ModuleRegistry registry = new ModuleRegistry();
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
    final ModuleLoader loader = new ModuleLoader();
    loader.findEntries(getFile());
    registry.registerModules(loader);
    moduleHandler = new ModuleHandler();
    moduleHandler.enableAllModules(registry.getServerModules());

    // Create first game.
    World world = Bukkit.createWorld(new WorldCreator(UUID.randomUUID().toString()));
    game = new Game(world);
  }

  public static void registerEvents(Listener events) {
    Bukkit.getPluginManager().registerEvents(events, instance);
  }

  public static Game getCurrentGame() {
    return getInstance().getGame();
  }

}
