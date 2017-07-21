package in.twizmwaz.openuhc.module.state;

import in.twizmwaz.openuhc.event.player.state.PlayerPlayEvent;
import in.twizmwaz.openuhc.event.player.state.PlayerSpectateEvent;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Module(lifeCycle = LifeCycle.GAME)
public class StateModule implements IModule, Listener {

  //Both players and spectators need to be tracked to distinguish between players that have not joined the game and
  //players that rejoined as a spectator
  private final Set<UUID> playing = new HashSet<>();
  private final Set<UUID> spectating = new HashSet<>();

  public StateModule() {
    Bukkit.getOnlinePlayers().forEach(this::play);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!isPlaying(player) && !isSpectating(player)) {
      play(player);
    }
  }

  public boolean isPlaying(Player player) {
    return playing.contains(player.getUniqueId());
  }

  public boolean isSpectating(Player player) {
    return spectating.contains(player.getUniqueId());
  }

  public boolean play(Player player) {
    PlayerPlayEvent event = new PlayerPlayEvent(player);
    Bukkit.getPluginManager().callEvent(event);

    UUID uuid = player.getUniqueId();
    spectating.remove(uuid);
    return !event.isCancelled() && playing.add(uuid);
  }

  public boolean spectate(Player player) {
    PlayerSpectateEvent event = new PlayerSpectateEvent(player);
    Bukkit.getPluginManager().callEvent(event);

    UUID uuid = player.getUniqueId();
    playing.remove(uuid);
    return !event.isCancelled() && spectating.add(uuid);
  }

}
