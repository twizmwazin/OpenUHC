package in.twizmwaz.openuhc.module.scenario.blastmining;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.IModule;
import in.twizmwaz.openuhc.module.LifeCycle;
import in.twizmwaz.openuhc.module.Module;
import in.twizmwaz.openuhc.module.Scenario;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Module(lifeCycle = LifeCycle.GAME)
@Scenario(
    name = "Blast Mining",
    desc = "When a player mines ore, there is a small chance of a creeper or TNT appearing."
)
public class BlastMining implements IModule, Listener {

  public static final List<Material> ORES = Arrays.asList(
      Material.COAL_ORE,
      Material.DIAMOND_ORE,
      Material.EMERALD_ORE,
      Material.GOLD_ORE,
      Material.IRON_ORE,
      Material.LAPIS_ORE,
      Material.QUARTZ_ORE,
      Material.REDSTONE_ORE
      );

  @Override
  public void onEnable() {
    OpenUHC.registerEvents(this);
  }

  @Override
  public void onDisable() {
    HandlerList.unregisterAll(this);
  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    if (ORES.contains(block.getType())) {
      double chance = Math.random();
      Location location = block.getLocation().add(0.5, 0.5, 0.5);
      if (0.05 > chance) {
        TNTPrimed tnt = location.getWorld().spawn(location, TNTPrimed.class);
        tnt.setFuseTicks(80);
      } else if (0.1 > chance) {
        Creeper creeper = location.getWorld().spawn(location, Creeper.class);
        creeper.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 2));
      }
    }
  }

}
