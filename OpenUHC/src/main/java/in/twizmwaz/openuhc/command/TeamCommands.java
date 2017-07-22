package in.twizmwaz.openuhc.command;

import in.twizmwaz.openuhc.OpenUHC;
import in.twizmwaz.openuhc.team.Team;
import in.twizmwaz.openuhc.team.TeamInvitation;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TeamCommands implements CommandExecutor {

  private final List<TeamInvitation> invitations = new ArrayList<>();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage(ChatColor.RED + "Console cannot use this command!");
      return true;
    }
    // Verify if teams can be modified
    try {
      Team.checkToModify();
    } catch (Exception e) {
      sender.sendMessage(ChatColor.RED + "Unable to modify teams at this time.");
    }
    if (args[0].equalsIgnoreCase("invite")) {
      Player player = (Player) sender;
      Player to = Bukkit.getPlayer(args[1]);
      if (to == null) {
        player.sendMessage(ChatColor.RED + "No player found.");
        return true;
      }

      invitations.add(new TeamInvitation(player, to));

      player.sendMessage(ChatColor.GRAY + "You have invited " + to.getName() + " to join your team.");
      String from = player.getName();
      to.sendMessage(from + ChatColor.GRAY + " has invited you to join their team. Use "
          + ChatColor.GREEN + "/team accept " + from + ChatColor.GRAY + " to accept or "
          + ChatColor.RED + "/team deny " + from + ChatColor.GRAY + " to deny.");
    } else if (args[0].equalsIgnoreCase("accept")) {
      Player player = (Player) sender;
      Player from = Bukkit.getPlayer(args[1]);
      if (from == null) {
        player.sendMessage(ChatColor.RED + "No player found.");
        return true;
      }

      TeamInvitation invitation = new TeamInvitation(from, player);
      if (invitations.contains(invitation)) {
        invitations.remove(invitation);

        player.sendMessage(ChatColor.GREEN + "You have joined " + from.getName() + "'s team.");
        Team team = OpenUHC.getCurrentGame().getTeam(from);
        if (team == null) {
          team = new Team(from);
        }
        team.sendMessage(player.getName() + ChatColor.GREEN + " has joined your team.");
        team.join(player);
      } else {
        player.sendMessage(from.getName() + ChatColor.RED + " has not invited you to join their team.");
      }
    } else if (args[0].equalsIgnoreCase("deny")) {
      Player player = (Player) sender;
      Player from = Bukkit.getPlayer(args[1]);
      if (from == null) {
        player.sendMessage(ChatColor.RED + "No player found.");
        return true;
      }

      TeamInvitation invitation = new TeamInvitation(from, player);
      if (invitations.contains(invitation)) {
        invitations.remove(invitation);

        player.sendMessage(
            ChatColor.DARK_RED + "You have denied " + from.getName() + "'s invitation to join their team."
        );
        Team team = OpenUHC.getCurrentGame().getTeam(from);
        if (team == null) {
          team = new Team(from);
        }
        team.sendMessage(player.getName() + ChatColor.DARK_RED + " has denied your invitation to join your team.");
        team.join(player);
      } else {
        player.sendMessage(from.getName() + ChatColor.RED + " has not invited you to join their team.");
      }
    } else if (args[0].equalsIgnoreCase("leave")) {
      Player player = (Player) sender;
      Team team = OpenUHC.getCurrentGame().getTeam(player);
      if (team != null) {
        if (!team.isLeader(player)) {
          team.leave(player);

          player.sendMessage(
              ChatColor.DARK_RED + "You have left " + Bukkit.getOfflinePlayer(team.getLeader()).getName() + "'s team."
          );
          team.sendMessage(player.getName() + ChatColor.DARK_RED + " has left the team.");
        } else {
          player.sendMessage(
              ChatColor.RED + "You may not leave a team of which you are the leader."
                  + " Use /team disband to disband the team."
          );
        }
      } else {
        player.sendMessage(ChatColor.RED + "You are not on a team.");
      }
    } else if (args[0].equalsIgnoreCase("disband")) {
      Player player = (Player) sender;
      Team team = OpenUHC.getCurrentGame().getTeam(player);
      if (team != null) {
        if (team.isLeader(player)) {
          if (team.disband()) {
            team.sendMessage(
                Bukkit.getOfflinePlayer(team.getLeader()).getName() + ChatColor.DARK_RED + " has disbanded the team."
            );
          } else {
            player.sendMessage(ChatColor.RED + "The team could not be disbanded at this time.");
          }
        } else {
          player.sendMessage(ChatColor.RED + "You may only disband a team if you are the leader.");
        }
      } else {
        player.sendMessage(ChatColor.RED + "You are not on a team.");
      }
    } else {
      return false;
    }
    return true;
  }

}
