package in.twizmwaz.openuhc.module.scenario.switcheroo;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.Lifecycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.ModuleInfo;
import in.twizmwaz.openuhc.module.ScenarioInfo;

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

@ModuleInfo(lifecycle = Lifecycle.GAME)
@ScenarioInfo(name = "Switcheroo", desc = "Players swap locations when one of them is shot by the other.")
public class Switcheroo implements Module, Listener {

  //TODO: Implement as scenario setting
  public boolean swapVelocity = true;

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

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
