package in.twizmwaz.openuhc.module.blindness;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.event.game.GameScatterEvent;
import in.twizmwaz.openuhc.event.game.GameStartEvent;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Module(lifeCycle = LifeCycle.GAME, enableOnStart = true)
public class BlindnessModule implements IModule, Listener {

  private static final PotionEffect[] EFFECTS = {
      new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 127, false),
      new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 127, false),
      new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -128, false),
      new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 127, false),
      new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 127, false),
  };

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler
  public void onScatter(GameScatterEvent event) {
    for (UUID uuid : OpenUHC.getCurrentGame().getPlayers()) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        for (PotionEffect effect : EFFECTS) {
          player.addPotionEffect(effect);
        }
      }
    }
  }

  @EventHandler
  public void onStart(GameStartEvent event) {
    for (UUID uuid : OpenUHC.getCurrentGame().getPlayers()) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        for (PotionEffect effect : EFFECTS) {
          player.removePotionEffect(effect.getType());
        }
      }
    }

  }

}
