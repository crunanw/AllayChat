package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Join;
import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(value = "reply", alias = {"r", "respond"})
public class ReplyCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.reply")
    public void onMessage(Player player, @Join String message) {
        String lastPlayer = plugin.getChatManager().getLastMessagedPlayer(player.getName());
        if (lastPlayer == null) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getPrivateMessageConfig().getString("messages.no-reply")
            ));
            return;
        }

        plugin.getChatManager().handlePrivateMessage(player, lastPlayer, message);
    }

}
