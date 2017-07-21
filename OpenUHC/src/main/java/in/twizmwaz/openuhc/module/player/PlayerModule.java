package in.twizmwaz.openuhc.module.player;

import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

@Module(lifeCycle = LifeCycle.GAME)
public class PlayerModule implements IModule, Listener {

  private final Set<UUID> playing = new HashSet<>();

  public boolean isPlaying(Player player) {
    return playing.contains(player.getUniqueId());
  }

  public void play(Player player) {
    playing.add(player.getUniqueId());
  }



}
