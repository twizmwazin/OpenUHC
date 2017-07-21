package in.twizmwaz.openuhc.event.player.team;

import in.twizmwaz.openuhc.team.Team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
@Setter
public class PlayerLeaveTeamEvent extends PlayerEvent {

  @Getter private static final HandlerList handlerList = new HandlerList();

  private boolean cancelled;

  private final Team team;

  public PlayerLeaveTeamEvent(Player player, Team team) {
    super(player);
    this.team = team;
  }

  @Override
  public HandlerList getHandlers() {
    return handlerList;
  }

}
