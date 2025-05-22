package net.voxelarc.allaychat.command;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.user.ChatUser;
import net.voxelarc.allaychat.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(value = "ignore")
public class IgnoreCommand extends BaseCommand {

    private final AllayChatPlugin plugin;

    @Default
    @Permission("allaychat.command.ignore")
    public void onIgnore(Player player, @Suggestion("online-players") String target) {
        ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.data-not-loaded")
            ));
            return;
        }

        if (player.getName().equalsIgnoreCase(target)) {
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.ignore-self")
            ));
            return;
        }

        if (!user.getIgnoredPlayers().contains(target)) {
            user.getIgnoredPlayers().add(target);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.ignore-added"),
                    Placeholder.unparsed("player", target)
            ));
        } else {
            user.getIgnoredPlayers().remove(target);
            ChatUtils.sendMessage(player, ChatUtils.format(
                    plugin.getMessagesConfig().getString("messages.ignore-removed"),
                    Placeholder.unparsed("player", target)
            ));
        }
    }

}
