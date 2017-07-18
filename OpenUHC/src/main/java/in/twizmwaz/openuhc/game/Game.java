package in.twizmwaz.openuhc.game;

import in.twizmwaz.openuhc.team.Team;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;

@AllArgsConstructor
public class Game {

  @Getter
  private final World world;
  @Getter
  private final List<Team> teams = new ArrayList<>();
  @Getter
  private boolean playing;
  @Getter
  private boolean complete;

}