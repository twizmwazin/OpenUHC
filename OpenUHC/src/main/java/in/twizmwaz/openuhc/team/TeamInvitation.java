package in.twizmwaz.openuhc.team;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class TeamInvitation {

  private final UUID from;
  private final UUID to;

  public TeamInvitation(Player from, Player to) {
    this.from = from.getUniqueId();
    this.to = to.getUniqueId();
  }

}
