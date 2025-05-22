package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.util.ChatUtils;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
@Command(value = "allay", alias = {"allaychat"})
public class ReloadCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @SubCommand("reload")
    @Permission("allaychat.command.reload")
    public void onReload(CommandSender sender) {
        plugin.getConfig().reload();
        plugin.getFilterConfig().reload();
        plugin.getMessagesConfig().reload();
        plugin.getFormatConfig().reload();
        plugin.getPrivateMessageConfig().reload();
        plugin.getStaffChatConfig().reload();
        plugin.getReplacementConfig().reload();

        plugin.getChatManager().onEnable();

        for (ChatFilter filter : plugin.getFilters()) {
            filter.onEnable();
        }

        ChatUtils.sendMessage(sender, ChatUtils.format(
                plugin.getMessagesConfig().getString("messages.reloaded")
        ));
    }

}
