package xyz.cucumber.base.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.Command;
import xyz.cucumber.base.commands.cmds.BindCommand;
import xyz.cucumber.base.commands.cmds.ConfigCommand;
import xyz.cucumber.base.commands.cmds.FriendsCommand;
import xyz.cucumber.base.commands.cmds.ToggleCommand;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventChat;

public class CommandManager {
    public Set<Command> commands = new HashSet<Command>();
    private String prefix = "§7[§cGothaj] §r";
    private String cmdprefix = ".";

    public CommandManager() {
        Client.INSTANCE.getEventBus().register(this);
        this.commands.add(new BindCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new FriendsCommand());
        this.commands.add(new ToggleCommand());
    }

    @EventListener
    public void commandHandler(EventChat e) {
        if (!e.getMessage().startsWith(this.cmdprefix)) {
            return;
        }
        ArrayList<String> message = new ArrayList<String>();
        message.addAll(Arrays.asList(e.getMessage().toLowerCase().substring(1).split(" ")));
        e.setCancelled(true);
        String command = (String)message.get(0);
        for (Command cmd : this.commands) {
            String[] stringArray = cmd.getAliases();
            int n = stringArray.length;
            int n2 = 0;
            while (n2 < n) {
                String alias = stringArray[n2];
                if (alias.toLowerCase().equals(command)) {
                    message.remove(0);
                    cmd.onSendCommand(message.toArray(new String[0]));
                    return;
                }
                ++n2;
            }
        }
        this.sendChatMessage("§cCommand not found! Try .help for help.");
    }

    public void sendChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(String.valueOf(this.prefix) + message));
    }
}
