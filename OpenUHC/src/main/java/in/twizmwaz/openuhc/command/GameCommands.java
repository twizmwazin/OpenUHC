package in.twizmwaz.openuhc.command;

import in.twizmwaz.openuhc.OpenUHC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommands implements CommandExecutor {

  private static final String ERROR_NO_PERM = ChatColor.RED + "You do not have permission to execute this command.";

  @Override
  public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
    // TODO permissions > op
    if (args.length == 0) {
      // TODO game status?
      return false;
    } else {
      if (!sender.isOp()) {
        sender.sendMessage(ERROR_NO_PERM);
        return true;
      }
      OpenUHC.getPluginLogger().info(OpenUHC.getCurrentGame().getState().toString());
      OpenUHC.getPluginLogger().info("" + OpenUHC.getCurrentGame().isBusy());
      switch (args[0]) {
        case "generate":
        case "gen":
          try {
            OpenUHC.getCurrentGame().generate();
            sender.sendMessage(ChatColor.GREEN + "Generating chunks begun.");
          } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Unable to generate chunks at this time.");
          }
          break;
        case "scatter":
          try {
            OpenUHC.getCurrentGame().scatter();
            sender.sendMessage(ChatColor.GREEN + "Scattering players has begun.");
          } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Unable to scatter players at this time.");
            e.printStackTrace();
          }
          break;
        case "start":
          try {
            OpenUHC.getCurrentGame().start();
            sender.sendMessage(ChatColor.GREEN + "The game has successfully started.");
          } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Unable to start game at this time.");
          }
          break;
        case "stop":
        case "end":
          try {
            OpenUHC.getCurrentGame().end();
            sender.sendMessage(ChatColor.GREEN + "The game has successfully ended.");
          } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Unable to end game at this time.");
          }
          break;
        default:
          return false;
      }
    }
    return true;
  }

}
