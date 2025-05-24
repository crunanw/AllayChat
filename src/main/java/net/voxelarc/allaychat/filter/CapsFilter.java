package net.voxelarc.allaychat.filter;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.voxelarc.allaychat.AllayChatPlugin;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import net.voxelarc.allaychat.api.util.ChatUtils;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CapsFilter implements ChatFilter {

    private final AllayChatPlugin plugin;

    private int maxCaps = 3;
    private boolean enabled = true;

    private Component blockedMessage;

    @Override
    public void onEnable() {
        enabled = plugin.getFilterConfig().getBoolean("caps.enabled", true);
        maxCaps = plugin.getFilterConfig().getInt("caps.max-caps", 3);
        blockedMessage = ChatUtils.format(plugin.getFilterConfig().getString("caps.message"));
    }

    @Override
    public boolean checkMessage(Player player, String message) {
        if (!enabled) return false;
        if (player.hasPermission("allaychat.bypass.caps")) return false;

        if (capsCount(message) >= maxCaps) {
            ChatUtils.sendMessage(player, blockedMessage);
            return true;
        }

        return false;
    }

    public static int capsCount(String text) {
        int capsCount = 0;
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c))
                capsCount++;
        }

        return capsCount;
    }

}
