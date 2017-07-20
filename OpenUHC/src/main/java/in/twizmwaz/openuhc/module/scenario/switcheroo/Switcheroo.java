package in.twizmwaz.openuhc.module.scenario.switcheroo;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import in.twizmwaz.openuhc.module.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(name = "Switcheroo", desc = "Players swap locations when one of them is shot by the other.")
public class Switcheroo implements IModule, Listener {

  @Setting("Swap velocity")
  private boolean swapVelocity = true;

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  /**
   * Swaps player position when a player shoots another player and swaps their velocities as well, if enabled.
   * @param event The event
   */
  @EventHandler(ignoreCancelled = true)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    Entity entity = event.getEntity();
    Entity arrow = event.getDamager();
    if (entity instanceof Player && arrow instanceof Arrow) {
      ProjectileSource source = ((Arrow) arrow).getShooter();
      if (source instanceof Player) {
        Player shooter = (Player) source;

        Bukkit.getScheduler().runTask(OpenUHC.getInstance(), () -> {
          final Location location = entity.getLocation();
          entity.teleport(shooter);
          shooter.teleport(location);

          if (swapVelocity) {
            final Vector velocity = entity.getVelocity();
            entity.setVelocity(shooter.getVelocity());
            shooter.setVelocity(velocity);
          } else {
            Vector velocity = new Vector(0, 0, 0);
            entity.setVelocity(velocity);
            shooter.setVelocity(velocity);
          }
        });
      }
    }
  }

}
