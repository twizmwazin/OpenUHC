package in.twizmwaz.openuhc.game;

import in.twizmwaz.openuhc.module.ModuleHandler;
import in.twizmwaz.openuhc.team.Team;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;

@Getter
@RequiredArgsConstructor
public class Game {

  private final World world;
  private final ModuleHandler moduleHandler = new ModuleHandler();
  private final List<Team> teams = new ArrayList<>();
  private boolean playing = false;
  private boolean complete = false;



}