package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
@Command(value = "chatmute", alias = "mutechat")
public class MuteChatCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.chatmute")
    public void onToggleChatMute(CommandSender sender) {
        if (!plugin.getChatManager().isChatMuted()) {
            plugin.getChatManager().setChatMuted(true);
            ChatUtils.sendMessage(sender, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.chat-mute-toggle-enabled",
                            "Could not find messages.chat-mute-toggle-enabled in your messages config.")
            ));

            ChatUtils.sendMessage(plugin.getServer(), ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.chat-mute-broadcast-enabled",
                            "Could not find messages.chat-mute-broadcast-enabled in your messages config.")
            ));
        } else {
            plugin.getChatManager().setChatMuted(false);
            ChatUtils.sendMessage(sender, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.chat-mute-toggle-disabled",
                            "Could not find messages.chat-mute-toggle-disabled in your messages config.")
            ));

            ChatUtils.sendMessage(plugin.getServer(), ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.chat-mute-broadcast-disabled",
                            "Could not find messages.chat-mute-broadcast-disabled in your messages config.")
            ));
        }
    }

}
