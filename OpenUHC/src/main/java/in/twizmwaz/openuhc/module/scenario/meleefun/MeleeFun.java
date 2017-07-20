package in.twizmwaz.openuhc.module.scenario.meleefun;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Melee Fun", desc = "There is no hit cooldown, but damage is reduced.")
public class MeleeFun implements IModule, Listener {

  //TODO: Implement as scenario setting
  public double damageReduction = 0.1;

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Removes the hit cooldown upon a player attack and reduces the amount of damage dealt according to a modifier.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    Entity entity = event.getEntity();
    if (entity instanceof Player
        && event.getDamager() instanceof Player
        && event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
      event.setDamage(event.getDamage() * damageReduction);
      Bukkit.getScheduler().runTask(OpenUHC.getInstance(), () -> ((Player) entity).setNoDamageTicks(0));
    }
  }

}
