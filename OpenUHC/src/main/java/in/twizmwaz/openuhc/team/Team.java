package in.twizmwaz.openuhc.team;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.event.player.team.PlayerJoinTeamEvent;
import in.twizmwaz.openuhc.event.player.team.PlayerLeaveTeamEvent;
import in.twizmwaz.openuhc.game.Game;
import in.twizmwaz.openuhc.game.GameState;
import in.twizmwaz.openuhc.game.exception.BusyGameStateException;
import in.twizmwaz.openuhc.game.exception.InvalidGameStateException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class Team {

  private final Set<UUID> players = new HashSet<>();
  private final UUID leader;

  private final String prefix;

  /**
   * A team with a leader.
   *
   * @param leader The leader of the team
   */
  public Team(Player leader) {
    checkToModify();
    this.leader = leader.getUniqueId();
    join(leader);

    //TODO: Implement colored prefixes
    this.prefix = ChatColor.WHITE.toString();

    OpenUHC.getCurrentGame().getTeams().add(this);
  }

  /**
   * Adds a player to the team. This may be cancelled if the team is full, if the match is playing, etc.
   *
   * @param player The player joining the team
   * @return If the player successfully joined the team
   */
  public boolean join(Player player) {
    checkToModify();
    PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(player, this);
    Bukkit.getPluginManager().callEvent(event);

    //Player is only attempted to be added to the team if the event is not cancelled
    return !event.isCancelled() && players.add(player.getUniqueId());
  }

  /**
   * Removes a player from the team. This may be cancelled if the match is playing, etc.
   *
   * @param player The player leaving the team
   * @return If the player successfully left the team
   */
  public boolean leave(Player player) {
    checkToModify();
    PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(player, this);
    Bukkit.getPluginManager().callEvent(event);

    //Player is only attempted to be removed from the team if the event is not cancelled
    return !event.isCancelled() && players.remove(player.getUniqueId());
  }

  /**
   * Disbands a team, in which all players are removed and the team is removed from the game.
   *
   * @return If the team was successfully disbanded.
   */
  public boolean disband() {
    checkToModify();
    for (UUID uuid : players) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
          return false;
        }
      }
    }
    players.clear();

    return OpenUHC.getCurrentGame().getTeams().remove(this);
  }

  public boolean hasPlayer(Player player) {
    return players.contains(player.getUniqueId());
  }

  public boolean hasPlayer(UUID player) {
    return players.contains(player);
  }

  public boolean isLeader(Player player) {
    return player.getUniqueId().equals(leader);
  }

  /**
   * Finds any online players on the team.
   * @return The online players for the team
   */
  public Set<Player> getOnlinePlayers() {
    Set<Player> result = new HashSet<>();
    players.forEach(uuid -> {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        result.add(player);
      }
    });
    return result;
  }

  /**
   * Sends a message to all players on the team.
   *
   * @param message The message to send
   */
  public void sendMessage(String message) {
    for (UUID uuid : players) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        player.sendMessage(message);
      }
    }
  }

  /**
   * Verifies teams can be changed. Throws an exception when they cannot be modified.
   * @throws BusyGameStateException When the game is busy and teams cannot be modified
   * @throws InvalidGameStateException When the game has started and teams cannot be modified
   */
  public static void checkToModify() throws BusyGameStateException, InvalidGameStateException {
    Game game = OpenUHC.getCurrentGame();
    switch (game.getState()) {
      case NEW:
        return;
      case GENERATED:
        if (game.isBusy()) {
          throw new BusyGameStateException();
        }
        break;
      default:
        throw new InvalidGameStateException(game.getState(), GameState.NEW);
    }
  }

}
