package in.twizmwaz.openuhc.game;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.module.ModuleHandler;
import in.twizmwaz.openuhc.module.ModuleRegistry;
import in.twizmwaz.openuhc.team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class Game {

  private final World world = Bukkit.createWorld(new WorldCreator(
      OpenUHC.WORLD_DIR_PREFIX + String.valueOf(System.currentTimeMillis())));
  private final ModuleHandler moduleHandler = new ModuleHandler();
  private final List<UUID> players = new ArrayList<>();
  private final List<Team> teams = new ArrayList<>();
  private boolean playing = false;
  private boolean complete = false;

  @Getter(AccessLevel.NONE) private int chunkX;
  @Getter(AccessLevel.NONE) private int chunkY;

  /**
   * Initializes the game object.
   */
  private void initialize() {
    ModuleRegistry.getGameModules().forEach(moduleData -> {
      if (moduleData.isEnabledOnStart()) {
        moduleHandler.enableModule(moduleData.getClazz());
      }
    });
  }

  /**
   * De-initializes the game object.
   */
  private void terminate() {
    moduleHandler.disableAllModules();
  }

  /**
   * Generates the chunks in the game world with a given radius.
   *
   * @param radius The radius of chunks to generate (in block count)
   */
  public void generateChunks(final int radius) {
    final int chunkRadius = (radius / 16) + 4;
    chunkX = -1 * chunkRadius;
    chunkY = -1 * chunkRadius;
    final CompletableFuture<BukkitTask> task = new CompletableFuture<>();
    task.complete(Bukkit.getScheduler().runTaskTimer(OpenUHC.getInstance(), () -> {
      for (int i = 0; i < 200; ++i) {
        world.loadChunk(chunkX, chunkY);
        if (chunkX == chunkRadius && chunkY == chunkRadius) {
          try {
            task.get().cancel();
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
        } else if (chunkX == chunkRadius) {
          chunkX = -1 * chunkRadius;
          ++chunkY;
        } else {
          ++chunkX;
        }
      }
    }, 0, 20));
  }

  /**
   * Gets the team of a player, null if the player is not on a team.
   *
   * @param player The player
   * @return The team that has the player on it
   */
  public Team getTeam(Player player) {
    for (Team team : teams) {
      if (team.hasPlayer(player)) {
        return team;
      }
    }
    return null;
  }

}