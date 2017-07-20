package in.twizmwaz.openuhc.module.queue;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.event.game.GameStartEvent;
import in.twizmwaz.openuhc.event.player.PlayerInitEvent;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Module(lifeCycle = LifeCycle.GAME)
public class QueueModule implements IModule, Listener {

  private final List<UUID> initialized = new ArrayList<>();

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler
  public void onGameStart(GameStartEvent event) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      //TODO: Only run if the player is playing in the game (not spectating)
      Bukkit.getPluginManager().callEvent(new PlayerInitEvent(player, false));
      initialized.add(player.getUniqueId());
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    UUID uuid = player.getUniqueId();
    //TODO: Only run if the player is playing in the game (not spectating)
    if (!initialized.contains(uuid)) {
      initialized.add(uuid);
      Bukkit.getPluginManager().callEvent(new PlayerInitEvent(player, true));
    }
  }

}
