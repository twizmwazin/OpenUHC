package in.twizmwaz.openuhc;

import in.twizmwaz.openuhc.game.Game;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class OpenUHC extends JavaPlugin {

  @Getter
  private static OpenUHC instance;
  @Getter
  private Game game;

  public void onEnable() {
    instance = this;
    getLogger().info("OpenUHC has started.");
  }

}
