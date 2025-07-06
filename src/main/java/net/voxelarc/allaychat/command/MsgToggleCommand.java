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
@Command(value = "msgtoggle")
public class MsgToggleCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.msgtoggle")
    public void onToggleMsg(Player player) {
        ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.data-not-loaded")
            ));
            return;
        }

        if (!user.isMsgEnabled()) {
            user.setMsgEnabled(true);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getPrivateMessageConfig().getString("messages.toggle-enabled")
            ));
        } else {
            user.setMsgEnabled(false);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getPrivateMessageConfig().getString("messages.toggle-disabled")
            ));
        }
    }

}
