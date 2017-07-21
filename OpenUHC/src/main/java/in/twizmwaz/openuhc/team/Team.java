package in.twizmwaz.openuhc.team;

import in.twizmwaz.openuhc.event.player.team.PlayerJoinTeamEvent;
import in.twizmwaz.openuhc.event.player.team.PlayerLeaveTeamEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class Team {

  private final Set<UUID> players = new HashSet<>();
  private final UUID leader;

  private final String prefix;

  /**
   * Adds a player to the team. This may be cancelled if the team is full, if the match is playing, etc.
   *
   * @param player The player joining the team
   * @return If the player successfully joined the team
   */
  public boolean join(Player player) {
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
    PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(player, this);
    Bukkit.getPluginManager().callEvent(event);

    //Player is only attempted to be removed from the team if the event is not cancelled
    return !event.isCancelled() && players.remove(player.getUniqueId());
  }

  public boolean isLeader(Player player) {
    return player.getUniqueId().equals(leader);
  }

}
