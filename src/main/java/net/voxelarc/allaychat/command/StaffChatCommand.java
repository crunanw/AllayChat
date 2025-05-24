package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Join;
import dev.triumphteam.cmd.core.annotation.Optional;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.user.ChatUser;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(value = "staffchat", alias = {"sc"})
public class StaffChatCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.staffchat")
    public void onMessage(Player player, @Optional @Join String message) {
        if (message == null || message.isEmpty()) {
            ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
            if (user == null) {
                ChatUtils.sendMessage(player, ChatUtils.format(plugin.getMessagesConfig().getString("messages.data-not-loaded")));
                return;
            }

            if (user.isStaffEnabled()) {
                user.setStaffEnabled(false);
                ChatUtils.sendMessage(player, ChatUtils.format(plugin.getStaffChatConfig().getString("messages.disabled")));
            } else {
                user.setStaffEnabled(true);
                ChatUtils.sendMessage(player, ChatUtils.format(plugin.getStaffChatConfig().getString("messages.enabled")));
            }

            return;
        }

        plugin.getChatManager().handleStaffChatMessage(player, message);
    }

}
