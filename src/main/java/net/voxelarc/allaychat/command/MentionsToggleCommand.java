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
@Command(value = "mentionstoggle", alias = "mentiontoggle")
public class MentionsToggleCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.mentionstoggle")
    public void onToggleMention(Player player) {
        ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.data-not-loaded")
            ));
            return;
        }

        if (!user.isMentionsEnabled()) {
            user.setMentionsEnabled(true);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getReplacementConfig().getString("mention.toggle-enabled")
            ));
        } else {
            user.setMentionsEnabled(false);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getReplacementConfig().getString("mention.toggle-disabled")
            ));
        }
    }

}
