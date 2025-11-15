package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.user.ChatUser;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(value = "chattoggle", alias = "togglechat")
public class ChatToggleCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.chattoggle")
    public void onToggleChat(Player player) {
        ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.data-not-loaded")
            ));
            return;
        }

        if (!user.isChatEnabled()) {
            user.setChatEnabled(true);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.chat-toggle-enabled",
                            "Could not find messages.chat-toggle-enabled in your messages config.")
            ));
        } else {
            user.setChatEnabled(false);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.chat-toggle-disabled",
                            "Could not find messages.chat-toggle-disabled in your messages config.")
            ));
        }
    }

}
