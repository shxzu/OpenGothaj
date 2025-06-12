package xyz.cucumber.base.commands.cmds;

import java.util.concurrent.CopyOnWriteArrayList;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.Command;
import xyz.cucumber.base.commands.CommandInfo;

@CommandInfo(aliases={"friends", "f"}, name="Friends", usage=".f <add / remove> <player name>")
public class FriendsCommand
extends Command {
    public static CopyOnWriteArrayList<String> friends = new CopyOnWriteArrayList();

    @Override
    public void onSendCommand(String[] args) {
        if (args.length != 2) {
            this.sendUsage();
            return;
        }
        if (args[1] == "") {
            this.sendUsage();
            return;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (friends.contains(args[1])) {
                Client.INSTANCE.getCommandManager().sendChatMessage(String.valueOf(args[1]) + " is already your friend");
            } else {
                friends.add(args[1]);
                Client.INSTANCE.getCommandManager().sendChatMessage("Added friend " + args[1]);
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            friends.remove(args[1]);
            Client.INSTANCE.getCommandManager().sendChatMessage("Removed friend " + args[1]);
        } else {
            this.sendUsage();
            return;
        }
    }
}
