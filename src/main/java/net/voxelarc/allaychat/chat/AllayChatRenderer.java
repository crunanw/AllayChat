package net.voxelarc.allaychat.chat;

import io.papermc.paper.chat.ChatRenderer;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class AllayChatRenderer implements ChatRenderer.ViewerUnaware {

    private final AllayChatPlugin plugin;

    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component message) {
        return plugin.getChatManager().formatMessage(player, message);
    }

}
