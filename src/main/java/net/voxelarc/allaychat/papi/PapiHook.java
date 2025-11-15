package net.voxelarc.allaychat.papi;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.user.ChatUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class PapiHook extends PlaceholderExpansion {

    private final AllayChatPlugin plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "allaychat";
    }

    @Override
    public @NotNull String getAuthor() {
        return "hyperion";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return null;

        ChatUser user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) return null;

        return switch (params.toLowerCase()) {
            case "ignored_players" -> String.join(", ", user.getIgnoredPlayers());
            case "msg_status" -> String.valueOf(user.isMsgEnabled());
            case "staff_status" -> String.valueOf(user.isStaffEnabled());
            case "spy_status" -> String.valueOf(user.isSpyEnabled());
            case "mention_status" -> String.valueOf(user.isMentionsEnabled());
            case "chat_status" -> String.valueOf(user.isChatEnabled());
            case "chat_mute_status" -> String.valueOf(plugin.getChatManager().isChatMuted());
            default -> "Unknown Placeholder";
        };
    }
}
